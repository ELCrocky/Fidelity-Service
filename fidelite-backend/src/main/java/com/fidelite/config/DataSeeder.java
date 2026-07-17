package com.fidelite.config;

import com.fidelite.enums.*;
import com.fidelite.models.*;
import com.fidelite.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Seeds the database with 3-merchant test data for multi-tenant isolation verification.
 * Only active under the "dev" Spring profile — add spring.profiles.active=dev to
 * application.yml (or pass --spring.profiles.active=dev at startup).
 *
 * Idempotent: skips seeding if admin@comptoir.fr already exists.
 *
 * Accounts (all share password: Password123!):
 *   admin@comptoir.fr    / kasiyer@comptoir.fr    → Le Comptoir Français
 *   admin@sakura.fr      / kasiyer@sakura.fr      → Sushi Sakura
 *   admin@burgerhouse.fr / kasiyer@burgerhouse.fr → Burger House
 */
@Slf4j
@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final ClearingCompanyRepository clearingCompanyRepo;
    private final SettlementPoolRepository settlementPoolRepo;
    private final MerchantRepository merchantRepo;
    private final AppUserRepository appUserRepo;
    private final TierRepository tierRepo;
    private final ProductRepository productRepo;
    private final RewardRepository rewardRepo;
    private final CustomerRepository customerRepo;
    private final LoyaltyCardRepository loyaltyCardRepo;
    private final TransactionRepository transactionRepo;
    private final RedemptionRepository redemptionRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (appUserRepo.findByEmail("admin@comptoir.fr").isPresent()) {
            log.info("[DataSeeder] Seed data already present — skipping.");
            return;
        }

        log.info("[DataSeeder] Seeding test data for 3 merchants...");

        final String pwHash = passwordEncoder.encode("Password123!");

        // ── 1. Clearing infrastructure ──────────────────────────────────────
        ClearingCompany cc = clearingCompanyRepo.save(
            ClearingCompany.builder()
                .name("Fidelite Clearing SA")
                .build()
        );

        SettlementPool pool = settlementPoolRepo.save(
            SettlementPool.builder()
                .name("Pool Principal")
                .clearingCompany(cc)
                .pointValue(new BigDecimal("0.0100"))
                .build()
        );

        // ── 2. Merchants ─────────────────────────────────────────────────────
        Merchant comptoir = merchantRepo.save(Merchant.builder()
            .name("Le Comptoir Français")
            .apiKey("ak_live_comptoir_a1b2c3")
            .city("Valence")
            .region("Auvergne-Rhône-Alpes")
            .status(MerchantStatus.ACTIVE)
            .createdAt(LocalDateTime.of(2026, 3, 1, 9, 0))
            .pool(pool)
            .build());

        Merchant sakura = merchantRepo.save(Merchant.builder()
            .name("Sushi Sakura")
            .apiKey("ak_live_sakura_d4e5f6")
            .city("Lyon")
            .region("Auvergne-Rhône-Alpes")
            .status(MerchantStatus.ACTIVE)
            .createdAt(LocalDateTime.of(2026, 3, 5, 9, 0))
            .pool(pool)
            .build());

        Merchant burger = merchantRepo.save(Merchant.builder()
            .name("Burger House")
            .apiKey("ak_live_burgerhouse_g7h8i9")
            .city("Grenoble")
            .region("Auvergne-Rhône-Alpes")
            .status(MerchantStatus.ACTIVE)
            .createdAt(LocalDateTime.of(2026, 3, 10, 9, 0))
            .pool(pool)
            .build());

        log.info("[DataSeeder] Merchants created:");
        log.info("  Le Comptoir Français  → {}", comptoir.getIdMerchant());
        log.info("  Sushi Sakura          → {}", sakura.getIdMerchant());
        log.info("  Burger House          → {}", burger.getIdMerchant());

        // ── 3. App users (admin + cashier per merchant) ──────────────────────
        appUserRepo.save(AppUser.builder().email("admin@comptoir.fr").passwordHash(pwHash).role(Role.MERCHANT_ADMIN).merchant(comptoir).build());
        appUserRepo.save(AppUser.builder().email("kasiyer@comptoir.fr").passwordHash(pwHash).role(Role.STAFF).merchant(comptoir).build());
        appUserRepo.save(AppUser.builder().email("admin@sakura.fr").passwordHash(pwHash).role(Role.MERCHANT_ADMIN).merchant(sakura).build());
        appUserRepo.save(AppUser.builder().email("kasiyer@sakura.fr").passwordHash(pwHash).role(Role.STAFF).merchant(sakura).build());
        appUserRepo.save(AppUser.builder().email("admin@burgerhouse.fr").passwordHash(pwHash).role(Role.MERCHANT_ADMIN).merchant(burger).build());
        appUserRepo.save(AppUser.builder().email("kasiyer@burgerhouse.fr").passwordHash(pwHash).role(Role.STAFF).merchant(burger).build());

        // ── 4. Tiers ─────────────────────────────────────────────────────────
        Tier cBronze = tierRepo.save(Tier.builder().name("Bronze").minPoints(0).multiplier(new BigDecimal("1.00")).merchant(comptoir).build());
        Tier cSilver = tierRepo.save(Tier.builder().name("Silver").minPoints(500).multiplier(new BigDecimal("1.25")).merchant(comptoir).build());
        Tier cGold   = tierRepo.save(Tier.builder().name("Gold").minPoints(1000).multiplier(new BigDecimal("1.50")).merchant(comptoir).build());

        Tier sBronze = tierRepo.save(Tier.builder().name("Bronze").minPoints(0).multiplier(new BigDecimal("1.00")).merchant(sakura).build());
        Tier sSilver = tierRepo.save(Tier.builder().name("Silver").minPoints(600).multiplier(new BigDecimal("1.30")).merchant(sakura).build());
        Tier sGold   = tierRepo.save(Tier.builder().name("Gold").minPoints(1200).multiplier(new BigDecimal("1.60")).merchant(sakura).build());

        Tier bBronze = tierRepo.save(Tier.builder().name("Bronze").minPoints(0).multiplier(new BigDecimal("1.00")).merchant(burger).build());
        Tier bSilver = tierRepo.save(Tier.builder().name("Silver").minPoints(400).multiplier(new BigDecimal("1.20")).merchant(burger).build());
        Tier bGold   = tierRepo.save(Tier.builder().name("Gold").minPoints(900).multiplier(new BigDecimal("1.40")).merchant(burger).build());

        // ── 5. Products (no @Builder — use setters) ──────────────────────────
        productRepo.save(product("Menu du Jour", ProductType.REPAS, 120, false, comptoir));
        productRepo.save(product("Steak Frites", ProductType.REPAS, 140, false, comptoir));
        productRepo.save(product("Tarte Tatin", ProductType.DESSERT, 40, false, comptoir));
        productRepo.save(product("Verre de Vin", ProductType.BOISSON, 25, false, comptoir));
        productRepo.save(product("Menu Découverte Promo", ProductType.REPAS, 160, true, comptoir));

        productRepo.save(product("Plateau Sushi Saumon", ProductType.REPAS, 150, false, sakura));
        productRepo.save(product("Maki California", ProductType.REPAS, 90, false, sakura));
        productRepo.save(product("Mochi Glacé", ProductType.DESSERT, 35, false, sakura));
        productRepo.save(product("Thé Vert", ProductType.BOISSON, 15, false, sakura));
        productRepo.save(product("Menu Duo Sushi Promo", ProductType.REPAS, 200, true, sakura));

        productRepo.save(product("Burger Classic", ProductType.REPAS, 90, false, burger));
        productRepo.save(product("Burger Bacon Cheese", ProductType.REPAS, 110, false, burger));
        productRepo.save(product("Frites Maison", ProductType.DESSERT, 30, false, burger));
        productRepo.save(product("Milkshake", ProductType.BOISSON, 35, false, burger));
        productRepo.save(product("Menu Méga Burger Promo", ProductType.REPAS, 180, true, burger));

        // ── 6. Rewards ───────────────────────────────────────────────────────
        rewardRepo.save(Reward.builder().name("Café Offert").costPoints(100).merchant(comptoir).build());
        Reward rewardMoins10 = rewardRepo.save(Reward.builder().name("-10% Prochaine Addition").costPoints(200).merchant(comptoir).build());
        rewardRepo.save(Reward.builder().name("Menu du Jour Offert").costPoints(500).merchant(comptoir).build());

        rewardRepo.save(Reward.builder().name("Maki Offert").costPoints(150).merchant(sakura).build());
        Reward rewardThe = rewardRepo.save(Reward.builder().name("Thé Vert Offert").costPoints(80).merchant(sakura).build());
        rewardRepo.save(Reward.builder().name("Plateau Sushi Offert").costPoints(600).merchant(sakura).build());

        Reward rewardFrites = rewardRepo.save(Reward.builder().name("Frites Offertes").costPoints(120).merchant(burger).build());
        rewardRepo.save(Reward.builder().name("Milkshake Offert").costPoints(150).merchant(burger).build());
        rewardRepo.save(Reward.builder().name("Menu Méga Burger Offert").costPoints(450).merchant(burger).build());

        // ── 7. Customers (tier assigned by pointsBalance against merchant thresholds) ──
        // Comptoir: Bronze=0, Silver=500, Gold=1000
        Customer marie    = customer("Dupont", "Marie", "marie.dupont@email.fr", "0611110001", LocalDate.of(1990, 4, 12), true, comptoir, cGold);
        Customer jp       = customer("Martin", "Jean-Pierre", "jp.martin@gmail.com", "0611110002", LocalDate.of(1985, 11, 3), true, comptoir, cSilver);
        Customer sophie   = customer("Leclerc", "Sophie", "sophie.l@outlook.fr", "0611110003", LocalDate.of(1998, 7, 22), false, comptoir, cBronze);
        Customer antoine  = customer("Rousseau", "Antoine", "a.rousseau@free.fr", "0611110004", LocalDate.of(1992, 1, 30), true, comptoir, cSilver);
        Customer isabelle = customer("Bernard", "Isabelle", "isa.bernard@sfr.fr", "0611110005", LocalDate.of(1988, 9, 15), true, comptoir, cGold);
        customerRepo.saveAll(java.util.List.of(marie, jp, sophie, antoine, isabelle));

        // Sakura: Bronze=0, Silver=600, Gold=1200
        Customer yuki     = customer("Tanaka", "Yuki", "yuki.tanaka@email.fr", "0622220001", LocalDate.of(1993, 5, 18), true, sakura, sGold);
        Customer thomas   = customer("Petit", "Thomas", "t.petit@laposte.net", "0622220002", LocalDate.of(2000, 2, 28), false, sakura, sBronze);
        Customer celine   = customer("Moreau", "Céline", "c.moreau@orange.fr", "0622220003", LocalDate.of(1995, 6, 19), true, sakura, sSilver);
        Customer nicolas  = customer("Girard", "Nicolas", "n.girard@gmail.com", "0622220004", LocalDate.of(1991, 12, 5), true, sakura, sGold);
        Customer julie    = customer("Fontaine", "Julie", "j.fontaine@yahoo.fr", "0622220005", LocalDate.of(1997, 3, 27), false, sakura, sBronze);
        customerRepo.saveAll(java.util.List.of(yuki, thomas, celine, nicolas, julie));

        // Burger House: Bronze=0, Silver=400, Gold=900
        Customer kevin    = customer("Lambert", "Kevin", "kevin.lambert@email.fr", "0633330001", LocalDate.of(1994, 8, 9), true, burger, bSilver);
        Customer laura    = customer("Simon", "Laura", "laura.simon@gmail.com", "0633330002", LocalDate.of(1999, 10, 14), true, burger, bGold);
        Customer alex     = customer("Michel", "Alexandre", "a.michel@outlook.fr", "0633330003", LocalDate.of(1987, 4, 2), false, burger, bBronze);
        Customer manon    = customer("Garcia", "Manon", "manon.garcia@free.fr", "0633330004", LocalDate.of(1996, 11, 21), true, burger, bGold);
        Customer lucas    = customer("David", "Lucas", "lucas.david@sfr.fr", "0633330005", LocalDate.of(1989, 7, 8), true, burger, bBronze);
        customerRepo.saveAll(java.util.List.of(kevin, laura, alex, manon, lucas));

        // ── 8. Loyalty cards ────────────────────────────────────────────────
        LoyaltyCard cardMarie    = card("CPT-2024-001", 1240, CardStatus.ACTIVE, marie);
        LoyaltyCard cardJp       = card("CPT-2024-002", 580, CardStatus.ACTIVE, jp);
        LoyaltyCard cardSophie   = card("CPT-2024-003", 210, CardStatus.ACTIVE, sophie);
        LoyaltyCard cardAntoine  = card("CPT-2024-004", 890, CardStatus.BLOCKED, antoine);
        LoyaltyCard cardIsabelle = card("CPT-2024-005", 1650, CardStatus.ACTIVE, isabelle);

        LoyaltyCard cardYuki    = card("SAK-2024-001", 1320, CardStatus.ACTIVE, yuki);
        LoyaltyCard cardThomas  = card("SAK-2024-002", 95, CardStatus.ACTIVE, thomas);
        LoyaltyCard cardCeline  = card("SAK-2024-003", 720, CardStatus.ACTIVE, celine);
        LoyaltyCard cardNicolas = card("SAK-2024-004", 1980, CardStatus.ACTIVE, nicolas);
        LoyaltyCard cardJulie   = card("SAK-2024-005", 340, CardStatus.ACTIVE, julie);

        LoyaltyCard cardKevin  = card("BGH-2024-001", 460, CardStatus.ACTIVE, kevin);
        LoyaltyCard cardLaura  = card("BGH-2024-002", 1050, CardStatus.ACTIVE, laura);
        LoyaltyCard cardAlex   = card("BGH-2024-003", 180, CardStatus.ACTIVE, alex);
        LoyaltyCard cardManon  = card("BGH-2024-004", 920, CardStatus.BLOCKED, manon);
        LoyaltyCard cardLucas  = card("BGH-2024-005", 60, CardStatus.ACTIVE, lucas);

        loyaltyCardRepo.saveAll(java.util.List.of(
            cardMarie, cardJp, cardSophie, cardAntoine, cardIsabelle,
            cardYuki, cardThomas, cardCeline, cardNicolas, cardJulie,
            cardKevin, cardLaura, cardAlex, cardManon, cardLucas
        ));

        // ── 9. Transactions ──────────────────────────────────────────────────
        transactionRepo.saveAll(java.util.List.of(
            tx(120,  TransactionType.EARN,   "seed-tx-c001", LocalDateTime.of(2026, 7, 10, 12, 15), cardMarie),
            tx(-200, TransactionType.REDEEM, "seed-tx-c002", LocalDateTime.of(2026, 7, 8,  13, 40), cardMarie),
            tx(140,  TransactionType.EARN,   "seed-tx-c003", LocalDateTime.of(2026, 7, 9,  19, 20), cardJp),
            tx(40,   TransactionType.EARN,   "seed-tx-c004", LocalDateTime.of(2026, 7, 11, 9,  0),  cardSophie),
            tx(150,  TransactionType.EARN,   "seed-tx-c005", LocalDateTime.of(2026, 7, 10, 12, 30), cardYuki),
            tx(-150, TransactionType.REDEEM, "seed-tx-c006", LocalDateTime.of(2026, 7, 9,  18, 45), cardYuki),
            tx(90,   TransactionType.EARN,   "seed-tx-c007", LocalDateTime.of(2026, 7, 11, 13, 10), cardCeline),
            tx(200,  TransactionType.EARN,   "seed-tx-c008", LocalDateTime.of(2026, 7, 8,  20, 0),  cardNicolas),
            tx(90,   TransactionType.EARN,   "seed-tx-c009", LocalDateTime.of(2026, 7, 10, 13, 0),  cardKevin),
            tx(110,  TransactionType.EARN,   "seed-tx-c010", LocalDateTime.of(2026, 7, 11, 12, 45), cardLaura),
            tx(-120, TransactionType.REDEEM, "seed-tx-c011", LocalDateTime.of(2026, 7, 9,  14, 20), cardLaura),
            tx(35,   TransactionType.EARN,   "seed-tx-c012", LocalDateTime.of(2026, 7, 10, 19, 15), cardAlex)
        ));

        // ── 10. Redemptions ──────────────────────────────────────────────────
        redemptionRepo.saveAll(java.util.List.of(
            redemption(LocalDateTime.of(2026, 7, 8,  13, 40), rewardMoins10, cardMarie),
            redemption(LocalDateTime.of(2026, 7, 9,  18, 45), rewardThe,     cardYuki),
            redemption(LocalDateTime.of(2026, 7, 9,  14, 20), rewardFrites,  cardLaura)
        ));

        log.info("[DataSeeder] Done. 3 merchants, 6 users, 9 tiers, 15 products, 9 rewards, 15 customers, 15 cards, 12 transactions, 3 redemptions.");
        log.info("[DataSeeder] Login: admin@comptoir.fr / Password123! (MERCHANT_ADMIN)");
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private Product product(String name, ProductType type, int points, boolean promo, Merchant merchant) {
        Product p = new Product();
        p.setName(name);
        p.setProductType(type);
        p.setProductPoint(points);
        p.setPromotion(promo);
        p.setMerchant(merchant);
        return p;
    }

    private Customer customer(String nom, String prenom, String email, String phone,
                               LocalDate dob, boolean consent, Merchant merchant, Tier tier) {
        return Customer.builder()
            .nom(nom)
            .prenom(prenom)
            .email(email)
            .phone(phone)
            .dateDeNaissance(dob)
            .marketingConsent(consent)
            .merchant(merchant)
            .tier(tier)
            .build();
    }

    private LoyaltyCard card(String barcode, int points, CardStatus status, Customer customer) {
        return LoyaltyCard.builder()
            .barcodeEan13(barcode)
            .pointsBalance(points)
            .status(status)
            .customer(customer)
            .build();
    }

    private Transaction tx(int points, TransactionType type, String key, LocalDateTime at, LoyaltyCard card) {
        return Transaction.builder()
            .points(points)
            .type(type)
            .idempotencyKey(key)
            .createdAt(at)
            .card(card)
            .build();
    }

    private Redemption redemption(LocalDateTime at, Reward reward, LoyaltyCard card) {
        return Redemption.builder()
            .reddemedAt(at)
            .reward(reward)
            .card(card)
            .build();
    }
}
