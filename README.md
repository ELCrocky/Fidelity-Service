# Royalty Service

A multi-tenant loyalty card (carte de fidélité) system built for kiosk and point-of-sale use, designed to be integrated into any business — from a single independent shop to a nationwide franchise. Beyond issuing and redeeming points, the system includes a **clearing layer** that settles the financial imbalance points create between branches, so that no branch ever has to invoice another directly.

The backend is built with Spring Boot (Java 21) and the frontend with Angular (TypeScript).

---

## Table of contents

- [The problem this solves](#the-problem-this-solves)
- [System overview](#system-overview)
- [Architecture](#architecture)
- [Core concepts](#core-concepts)
- [The clearing model](#the-clearing-model)
- [Backend](#backend)
- [Frontend](#frontend)
- [Data model](#data-model)
- [Authentication and security](#authentication-and-security)
- [Setup](#setup)
- [Running the project](#running-the-project)
- [Environment variables](#environment-variables)

---

## The problem this solves

A loyalty point is a liability: when a business issues points, it promises something in return later. Because points are issued and redeemed in different months — and, in a franchise, often at different branches — a business's books swing in and out of balance.

Consider two cases. A single shop gives out 3,000 points worth of value in December; in January customers redeem them and the shop absorbs the cost on its books. In a franchise, branch A issues 500 points and 300 are redeemed there, while branch B issues 200 but 400 are redeemed there. Branch B has honored points that branch A profited from — but B cannot reasonably invoice A, and a customer redeeming in Marseille points earned in Drôme makes a direct invoice between two independent franchisees absurd.

This system places a **clearing house** (the operator of the platform) between the branches. Points settle against a shared pool rather than between branches: a branch that issues more than it redeems pays the pool, and a branch that redeems more than it issues is paid by the pool. The clearing house charges a fixed service fee per settlement. No branch ever invoices another — everyone settles only with the pool.

---

## System overview

Fidelity Service delivers a loyalty program as a service. A single deployment serves many businesses; each business sees only its own data. A typical flow runs through a kiosk or checkout device in a store:

1. On startup, the device authenticates with the company's staff account (or API key) and stays connected.
2. When a customer arrives, they scan the QR/barcode on their loyalty card.
3. The system resolves the card and customer from the barcode and displays the point balance and recent transactions.
4. A purchase earns points; the customer can spend accumulated points on a reward.
5. A customer without a card is offered instant card creation.

At no point does the customer enter a password — their identity is the barcode on their card. Separately, at the end of each period, the clearing house settles each pool and produces per-branch financial breakdowns.

---

## Architecture

The system has two main parts: a backend exposing a REST API and a frontend consuming it. All communication happens over JSON with token-based authentication.

```
┌─────────────────┐         REST / JSON          ┌──────────────────────┐
│  Frontend       │  ───────────────────────▶   │  Backend              │
│  (Angular / TS) │   JWT + (optional API key)   │  (Spring Boot / Java)│
│  Kiosk UI       │  ◀───────────────────────   │                      │
└─────────────────┘                              └──────────┬───────────┘
                                                            │ JPA / Hibernate
                                                            ▼
                                                  ┌──────────────────────┐
                                                  │  PostgreSQL          │
                                                  │  (data isolated by   │
                                                  │   tenant_id)         │
                                                  └──────────────────────┘
```

The backend follows a layered architecture: a request passes through a security filter (identity and tenant resolution), reaches a controller, runs its business logic in the service layer, and accesses data through repositories. Every tenant-scoped row carries a `merchant_id`, and queries are filtered by it.

---

## Core concepts

Several distinctions are essential to understanding the system.

**AppUser is not Customer.** An AppUser is a person who logs into the system: a branch administrator or a cashier/kiosk device. They have a password (BCrypt hash) and a role. A Customer is the end consumer; they have no password and are identified by their card's barcode.

**Merchant is the tenant.** Every branch or shop integrated into the system is a Merchant — the operational unit. Customers, products, tiers, rules, and rewards all belong to a Merchant.

**SettlementPool is the brand.** Because points are valid anywhere across a brand, all of a brand's branches settle in a single pool ("BurgerKing France"). The pool is the financial unit. An independent shop is simply a pool with one member.

**ClearingCompany is the operator.** The clearing company runs the platform, manages the pools, issues invoices, and collects commission. It sits above every branch and belongs to none. Its staff (ClearingUser) are entirely separate from branch staff (AppUser) — the two never mix. This separation replaces the earlier idea of a "super admin" role, which blurred the line between branch users and the operator.

---

## The clearing model

The clearing layer rests on three entities working together.

A **SettlementPool** represents a brand and acts like a clearing ledger. It holds the point-to-money rate (each pool sets its own), the running balance, and the fixed commission charged per invoice. Every branch of the brand points to this single pool.

A **Settlement** is the end-of-period reconciliation for a pool — the basis of an invoice. It records the total points issued and redeemed across the pool during the period, the net monetary difference, and the commission taken. A positive net means the pool is owed money (the business is invoiced); a negative net means the pool owes money (a payment is made out).

A **SettlementLine** is the per-branch breakdown within a settlement: one line per Merchant, showing how many points that branch issued, how many were redeemed there, the net point difference, and its monetary value. This is what tells the operator, branch by branch, who is in surplus and who is in deficit. These lines are not new raw data — they are summaries computed from the existing transactions at period close.

Crucially, branches never invoice each other. A branch that issued more than it redeemed owes the pool; a branch that redeemed more than it issued is owed by the pool. Each settles only with the clearing house, while its individual standing against the pool remains visible in its own settlement line.

A branch's current standing — for example, whether it is in surplus this month — is never stored as a field on the Merchant. It is derived on demand: closed periods come from past settlement lines, and the open period is computed by summing the current month's transactions. This keeps a single source of truth and avoids a stored figure drifting out of sync.

---

## Backend

Built on Spring Boot 3 and Java 21, with responsibilities split into clear layers.

The controller layer handles HTTP requests through DTOs and contains no business logic. The service layer holds the business rules: point earning, tier calculation, reward redemption, idempotency checks, and the period settlement computation. The repository layer consists of Spring Data JPA interfaces that abstract database access; queries are scoped by tenant (for example, `findByMerchantIdAndBarcodeEan13`). The mapper layer (MapStruct) converts between entities and DTOs so the database model never leaks outward.

When earning points, the customer's tier multiplier is applied to the base points — a Gold-tier customer with a 1.5 multiplier earns 1.5 times the points of a standard customer for the same purchase. When redeeming a reward, its cost is subtracted from the card balance, which never goes negative. To guard against double-crediting on network retries, each point operation may carry an idempotency key; a second request with the same key adds no new points.

At period close, the settlement process sums each pool's transactions per branch, writes a SettlementLine for every Merchant, computes the pool-level Settlement total, applies the per-invoice commission, and updates the pool balance — all within a single database transaction.

---

## Frontend

A kiosk-oriented interface written in Angular and TypeScript, composed of five main screens: card scanning, customer profile (name, balance, recent transactions), a card-creation offer for customers without a card, the card-creation form (collecting name, email, date of birth, phone, and a stored marketing-consent flag), and personalized promotions filtered by the customer's balance.

A separate operator-facing view lets the clearing company inspect each pool: the pool-level totals and, for franchises, the per-branch breakdown showing how much each store issued and redeemed and whether it stands in surplus or deficit.

The interface is multilingual and performs all server communication through a central API service layer using typed DTOs.

---

## Data model

The system is built from fifteen main entities, split into two groups.

**Loyalty core (per branch):** `Merchant` (the branch/tenant), `AppUser` (branch staff, holds the password), `Customer` (the member, no password), `LoyaltyCard` (card and point balance), `Tier` (loyalty tier with entry threshold and multiplier), `Product` (an item that earns or is bought with points), `EarningRule` (conditional earning rules and campaigns), `Transaction` (every point movement), `Reward` (a benefit claimable with points), and `Redemption` (the moment a reward is used).

**Clearing layer (the operator):** `ClearingCompany` (the operator), `ClearingUser` (operator staff), `SettlementPool` (the brand-level pool), `Settlement` (per-period reconciliation), and `SettlementLine` (per-branch breakdown within a settlement).

### Relationships

A ClearingCompany employs many ClearingUsers and manages many SettlementPools. A SettlementPool groups many Merchants and has many Settlements over time; each Settlement breaks down into many SettlementLines, and each line reports against one Merchant.

A Merchant employs many AppUsers and owns many Customers, Products, Tiers, EarningRules, and Rewards. A Customer holds one or more LoyaltyCards and belongs to a Tier. A LoyaltyCard records many Transactions and Redemptions. A Transaction may contain multiple Products. A Reward can be redeemed many times.

Deleting a customer deletes their cards, and deleting cards deletes the transactions and redemptions on them (composition). The links between a Merchant and its sub-entities, and between a pool and its branches, are looser (aggregation).

### Primary keys

Entities exposed externally or that must be unguessable use UUIDs: ClearingCompany, ClearingUser, SettlementPool, Merchant, AppUser, Customer, LoyaltyCard, Reward. Internal or high-volume records use auto-incrementing numeric (Long) keys: Settlement, SettlementLine, Transaction, Redemption, Tier, Product, EarningRule.

### Money and points

All monetary fields use `BigDecimal`, never floating point, to avoid rounding errors in financial records. Period point totals use `long`, since they can run into the millions.

### Indexing

Frequently filtered columns are indexed. In all tenant-scoped tables `merchant_id` is indexed, since nearly every query filters by tenant. Additional indexes cover `Transaction.card_id`, `Redemption.card_id`, `Redemption.reward_id`, `Merchant.pool_id`, and the foreign keys on settlements. Fields with a unique constraint — card barcode, user email, merchant API key, idempotency key — are indexed automatically.

---

## Authentication and security

There are distinct authentication paths. Branch staff and administrators log in with email and password and receive a JWT; passwords are hashed with BCrypt. Kiosk devices may connect with a company API key. Operator staff (ClearingUser) authenticate through their own separate path and are never tied to a branch.

Authorization is role-based within a branch: MERCHANT_ADMIN manages its own branch, and STAFF can only perform point operations and lookups. The clearing company's access is governed by being a ClearingUser, not by a branch role.

Multi-tenant isolation is central: every request runs within a tenant context and repository queries are scoped to that tenant, preventing one business from accessing another's data. The clearing layer can see across pools it manages, but branch users cannot.

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

Create a database in PostgreSQL. The schema is set up through database migration tools; in production the schema is validated, not altered automatically.

---

## Running the project

### Backend

```bash
cd fidelite-backend
mvn spring-boot:run
```

The API runs on `http://localhost:8080` by default, with Swagger UI at `http://localhost:8080/swagger-ui.html`.

### Frontend

```bash
cd fidelite-frontend
ng serve
```

The interface runs on `http://localhost:4200`.

---

## Environment variables

Sensitive information is supplied through environment variables, never hardcoded: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` for the database, and `JWT_SECRET` for token signing. These values are never committed to version control.

---

## License

This project was developed as part of an internship.
