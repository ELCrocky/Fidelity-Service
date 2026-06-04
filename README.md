# Loyalty Service

A multi-tenant loyalty card (carte de fidélité) system built for kiosk and point-of-sale use, designed to be integrated into any business. Each company connects with its own account and manages its own customers, products, tiers, and rewards. Customers are not identified by a password — they are recognized by scanning the QR/barcode on their loyalty card.

The backend is built with Spring Boot (Java 21) and the frontend with Angular (TypeScript).

---

## Table of contents

- [System overview](#system-overview)
- [Architecture](#architecture)
- [Core concepts](#core-concepts)
- [Backend](#backend)
- [Frontend](#frontend)
- [Data model](#data-model)
- [Authentication and security](#authentication-and-security)
- [Setup](#setup)
- [Running the project](#running-the-project)
- [Environment variables](#environment-variables)

---

## System overview

Fidelity Service delivers a loyalty program as a service. A single deployment serves many businesses; each business (Merchant) sees only its own data and cannot access another's. This isolation is the foundation of the system.

A typical flow runs through a kiosk or checkout device in a store:

1. On startup, the device authenticates with the company's staff account (or API key) and stays connected throughout the day.
2. When a customer arrives, they scan the QR/barcode on their loyalty card.
3. The system resolves the card and customer from the barcode, then displays the point balance and recent transactions.
4. A purchase earns points; the customer can spend accumulated points on a reward.
5. A customer without a card is offered instant card creation.

At no point does the customer enter a password. Their identity is the barcode on their card.

---

## Architecture

The system has two main parts: a backend exposing a REST API and a frontend consuming it. All communication happens over JSON with token-based authentication.

```
┌─────────────────┐         REST / JSON          ┌──────────────────────┐
│  Frontend        │  ───────────────────────▶   │  Backend              │
│  (Angular / TS)  │   JWT + (optional API key)   │  (Spring Boot / Java) │
│  Kiosk UI        │  ◀───────────────────────    │                       │
└─────────────────┘                              └──────────┬───────────┘
                                                            │ JPA / Hibernate
                                                            ▼
                                                  ┌──────────────────────┐
                                                  │  PostgreSQL           │
                                                  │  (data isolated by    │
                                                  │   tenant_id)          │
                                                  └──────────────────────┘
```

The backend follows a layered architecture: an incoming request first passes through a security filter (identity and tenant resolution), reaches a controller, runs its business logic in the service layer, and accesses data through repositories. Every tenant-scoped row in the database carries a `merchant_id`, and queries are filtered by it.

---

## Core concepts

Two distinctions are essential to understanding the system.

**AppUser is not Customer.** An AppUser is a person who logs into the system: a company administrator, a cashier, or a kiosk device. They have a password (BCrypt hash) and are authorized by role. A Customer is the end consumer of the loyalty program; they have no password and are identified by their card's barcode. In a store, the cashier (AppUser) processes the points of the customer (Customer) — the two are never the same person.

**Merchant is the tenant.** Every business integrated into the system is a Merchant and is the root of multi-tenancy. Customers, products, tiers, rules, and rewards all belong to a Merchant. When a company's kiosk runs a query, it sees only the data belonging to its own Merchant.

---

## Backend

Built on Spring Boot 3 and Java 21, with responsibilities split into clear layers.

### Layers

The controller layer handles HTTP requests and exchanges data through DTOs; it contains no business logic. The service layer holds the actual business rules: point earning, tier calculation, reward redemption, and idempotency checks all run here. The repository layer consists of Spring Data JPA interfaces that abstract database access; queries are filtered by tenant (for example, `findByMerchantIdAndBarcodeEan13`). The mapper layer (MapStruct) converts between entities and DTOs, so the database model never leaks directly to the outside.

### Request flow

A point-earning request is processed in this order: the request is authenticated with an API key or JWT, the tenant context is resolved, the controller receives the request, the service layer finds the card, checks the idempotency key (rejecting it if the same operation has already been processed), calculates points by applying the appropriate tier multiplier, writes the transaction record, and updates the card balance. All of these steps occur atomically within a single database transaction.

### Business rules

When earning points, the customer's tier comes into play: the base points are multiplied by the tier's multiplier. For example, a customer in the Gold tier with a 1.5 multiplier earns 1.5 times the points a standard customer would for the same purchase. When redeeming a reward, the reward's cost is subtracted from the card balance; if the balance is insufficient the operation is rejected, and the balance never goes negative. Each redemption produces both a Redemption record and a REDEEM-type Transaction that decreases the balance.

### Duplicate-operation protection (idempotency)

To prevent the same point operation from being recorded twice in case of a network failure or retransmission, each operation can carry an idempotency key. A second request with the same key adds no new points and returns the result of the first operation. This prevents accidental double-crediting.

---

## Frontend

A kiosk-oriented interface written in Angular and TypeScript, composed of five main screens.

The first screen is the card scanning screen: the customer brings their card close to the reader, and if no card is found they are routed to the card creation flow. The second screen is the customer profile: it lists the customer's name, current point balance, and recent transactions. The third screen offers card creation to a customer who has no card. The fourth screen is the card creation form: it collects first name, last name, email, date of birth, and phone number; the marketing consent checkbox is stored for legal record-keeping. The fifth screen shows personalized promotions: rewards the customer can afford are highlighted based on their balance.

The interface is multilingual (for example, switching between French and English) and performs all server communication through a central API service layer using typed DTOs.

---

## Data model

The system consists of ten main entities. Every tenant-scoped entity references a Merchant.

`Merchant` is the business integrated into the system; the tenant root. `AppUser` is the staff member or device that logs in and holds the password. `Customer` is the loyalty program member; they have no password. `LoyaltyCard` is the physical/digital card carrying the point balance and barcode. A customer can have more than one card. `Tier` is a loyalty tier (such as Bronze/Silver/Gold), defining the entry threshold and point multiplier. `Product` is an item that earns or is bought with points. `EarningRule` defines conditional point-earning rules and campaigns. `Transaction` is the record of every point movement (earn, redeem, adjust, expire). `Reward` is a benefit that can be claimed with points. `Redemption` is the moment a reward is actually used.

### Relationships

A Merchant has many AppUsers, Customers, Products, Tiers, EarningRules, and Rewards. A Customer has one or more LoyaltyCards and belongs to a Tier. A LoyaltyCard has many Transactions and Redemptions. A Transaction may contain multiple Products. A Reward can be redeemed many times.

When a customer is deleted, their cards are deleted along with them, and when cards are deleted, the transactions and redemptions belonging to those cards are deleted too (composition). The link between a Merchant and its sub-entities is looser (aggregation).

### Primary keys

Entities that are exposed externally, travel through the API or other systems, and must be unguessable use UUIDs: Merchant, AppUser, Customer, LoyaltyCard, Reward. Records that stay internal or have very high volume use auto-incrementing numeric (Long) keys: Transaction, Redemption, Tier, Product, EarningRule.

### Indexing

Database indexes are defined on frequently filtered columns. In all tenant-scoped tables, `merchant_id` is indexed, because nearly every query filters by tenant. In addition, `Transaction.card_id` (transaction history queries), `Redemption.card_id`, and `Redemption.reward_id` are indexed. Fields carrying a unique constraint — card barcode, user email, merchant API key, idempotency key — are already indexed automatically.

---

## Authentication and security

The system has two distinct authentication mechanisms.

Staff and administrators log in with an email and password; on successful login they receive a JWT (JSON Web Token) and carry it on subsequent requests. Passwords are never stored in plain text — they are hashed with BCrypt. Kiosk and checkout devices can connect with an API key belonging to the company.

Authorization is role-based (RBAC). SUPER_ADMIN manages the entire platform and can see all tenants. MERCHANT_ADMIN manages only its own company. STAFF (cashier/kiosk) can only perform point operations and lookups.

Multi-tenant isolation is at the center of security: every request runs within a tenant context, and repository queries are scoped to that tenant, preventing one company from accessing another company's data.

---

## Setup

### Requirements

The backend requires Java 21 and Maven, and PostgreSQL for the database. The frontend requires Node.js and the Angular CLI.

### Installing backend dependencies

```bash
cd fidelite-backend
mvn clean install
```

### Installing frontend dependencies

```bash
cd fidelite-frontend
npm install
```

### Database

Create a database in PostgreSQL. The schema is set up on first startup through database migration tools; in production the schema is not altered automatically, only validated.

---

## Running the project

### Backend

```bash
cd fidelite-backend
mvn spring-boot:run
```

The API runs on `http://localhost:8080` by default. Interactive API documentation (Swagger UI) is available at `http://localhost:8080/swagger-ui.html`.

### Frontend

```bash
cd fidelite-frontend
ng serve
```

The interface runs on `http://localhost:4200`.

---

## Environment variables

Sensitive information is not hardcoded; it is supplied through environment variables. The backend expects at least the following: `DB_URL` for the database connection address, `DB_USERNAME` for the database username, `DB_PASSWORD` for the database password, and `JWT_SECRET` for the token signing key. These values are never committed to version control.

---

## License

This project was developed as part of an internship.
