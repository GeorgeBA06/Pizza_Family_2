package com.example.pizza.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Service
@Slf4j
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.access.validityInSeconds}")
    private Long accessTokenExpiration;

    @Value("${jwt.refresh.validityInDays}")
    private Long refreshTokenExpiration;

    public String generateAccessToken(String email, String role){
        return buildToken(email, Map.of("type", "access", "role", role),
                accessTokenExpiration, ChronoUnit.MINUTES);
    }

    public String generateRefreshToken(String email){
        return buildToken(email, Map.of("type", "refresh"),
                refreshTokenExpiration, ChronoUnit.DAYS);
    }

    private String buildToken(
                              String email,
                              Map<String,Object> claims,
                              Long expiration,
                              ChronoUnit unit
    ) {
        Instant now = Instant.now();
        Instant expirationTime = now.plus(expiration, unit);

        return Jwts.builder()
                .setSubject(email)
                .addClaims(claims)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expirationTime))
                .signWith(getSignInKey())
                .compact();
    }

    public String extractEmail(String token){
        return extractAllClaims(token).getSubject();
    }

    public String extractTokenType(String token){
        return extractAllClaims(token).get("type", String.class);
    }

    public String extractRole(String token){
        return extractAllClaims(token).get("role", String.class);
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) getSignInKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("Token expired: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("Invalid token: {}", e.getMessage());
        } catch (SecurityException e) {
            log.warn("Invalid signature: {}", e.getMessage());
        } catch (Exception e) {
            log.warn("Token validation error: {}", e.getMessage());
        }
        return false;
    }



    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
