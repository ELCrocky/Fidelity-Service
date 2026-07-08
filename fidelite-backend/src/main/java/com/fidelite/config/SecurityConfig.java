package com.fidelite.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// Spring Security configuration: CORS, CSRF, session policy, and per-endpoint access rules.
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    // Allowed frontend origins; override via cors.allowed-origins in application.yml.
    @Value("${cors.allowed-origins:http://localhost:4200}")
    private List<String> allowedOrigins;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable()) // stateless JWT — no cookie-based session to protect
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Public: login endpoints and Swagger UI.
                .requestMatchers(
                    "/api/auth/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**"
                ).permitAll()
                // Clearing side: CLEARING role only.
                .requestMatchers(
                    "/api/clearing-companies/**",
                    "/api/clearing-users/**",
                    "/api/pools/**",
                    "/api/settlements/**"
                ).hasRole("CLEARING")
                // Admin operations: merchant admin or clearing.
                .requestMatchers(
                    "/api/merchants/**",
                    "/api/products/**",
                    "/api/tiers/**",
                    "/api/rewards/**",
                    "/api/earning-rules/**",
                    "/api/app-users/**"
                ).hasAnyRole("MERCHANT_ADMIN", "CLEARING")
                // Operational: any authenticated staff.
                .requestMatchers(
                    "/api/cards/**",
                    "/api/customers/**",
                    "/api/transactions/**",
                    "/api/redemptions/**"
                ).hasAnyRole("STAFF", "MERCHANT_ADMIN", "CLEARING")
                // Everything else requires authentication.
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return source;
    }
}
