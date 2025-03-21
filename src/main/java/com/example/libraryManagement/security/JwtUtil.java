package com.example.libraryManagement.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    private static final String SECRET_KEY = "your_secret_key_your_secret_key_your_"; // Должен быть длиной 256 бит
    private static final long EXPIRATION_TIME = 86400000; // 1 день

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        try {
            String username = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            System.out.println("Extracted username from token: " + username);
            return username;
        } catch (Exception e) {
            System.out.println("Ошибка при разборе JWT: " + e.getMessage());
            return null;
        }
    }

    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    public boolean isTokenValid(String username) {
        try {
            // 🔹 Проверяем, что токен существует и не истёк
            String extractedUsername = extractUsername(username);
            return extractedUsername != null && !isTokenExpired(username);
        } catch (Exception e) {
            log.warn("Ошибка проверки токена: {}", e.getMessage());
            return false;
        }
    }
}