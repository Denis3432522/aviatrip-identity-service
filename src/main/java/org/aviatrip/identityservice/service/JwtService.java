package org.aviatrip.identityservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aviatrip.identityservice.config.security.JwtProperties;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    private Key SIGNING_KEY;

    @PostConstruct
    private void initMethod() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        SIGNING_KEY = Keys.hmacShaKeyFor(keyBytes);
    }

    public void validateToken(String token) {
        Jwts.parserBuilder().setSigningKey(SIGNING_KEY).build().parseClaimsJws(token);
    }

    public String generateToken(String role, String userId) {
        return createToken(Map.of("role", role), userId);
    }

    private String createToken(Map<String, Object> claims, String userId) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getTokenExpiration()))
                .signWith(SIGNING_KEY, SignatureAlgorithm.HS256).compact();
    }
}
