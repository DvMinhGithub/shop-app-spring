package com.project.shopapp.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.project.shopapp.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtConfig {

    // unit in milliseconds
    @Value("${jwt.expiration}")
    private int expiration;

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        Map<String, Object> claims = new HashMap<>();
        claims.put("phoneNumber", user.getPhoneNumber());

        return Jwts.builder()
                .claims(claims)
                .subject(user.getPhoneNumber())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        SecretKey key = Jwts.SIG.HS256.key().build();
        byte[] keyBytes = key.getEncoded();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
}
