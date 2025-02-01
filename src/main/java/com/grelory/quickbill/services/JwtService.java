package com.grelory.quickbill.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class JwtService {

    private final Supplier<SecretKey> secretKey;
    private final Function<String, Claims> claims;

    public JwtService() {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey key = generator.generateKey();
            secretKey = () -> Keys.hmacShaKeyFor(key.getEncoded());
            claims = (token) -> Jwts.parser()
                    .verifyWith(secretKey.get())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .and()
                .signWith(secretKey.get())
                .compact();

    }

    public boolean isNotExpired(String token) {
        try {
            return claims.apply(token).getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String extractEmail(@Nullable String token) {
        if (token == null) return null;
        try {
            return claims.apply(token).getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return token != null
                && userDetails != null
                && Objects.equals(extractEmail(token), userDetails.getUsername())
                && isNotExpired(token);
    }
}
