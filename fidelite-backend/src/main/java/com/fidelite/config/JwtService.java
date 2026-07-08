package com.fidelite.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// Handles JWT generation, validation, and claim extraction.
@Service
public class JwtService {

    // Read from application.yml; must be supplied via JWT_SECRET environment variable.
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms:86400000}") // default 24 hours
    private long expirationMs;

    private SecretKey key() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Generates a signed JWT. subject = email; claims carry userType, role, and tenant id.
    public String generateToken(String email, Map<String, Object> claims) {
        return Jwts.builder()
                .subject(email)
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key())
                .compact();
    }

    // Parses and validates the token; throws if the signature is invalid or the token has expired.
    public Claims validateAndParse(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractEmail(String token) {
        return validateAndParse(token).getSubject();
    }
}
