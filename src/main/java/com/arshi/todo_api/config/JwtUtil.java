package com.arshi.todo_api.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
    private final long expirationMs = 1000 * 60 * 60; // 1 hour

    //Creates a new signed token containing the username, valid for 1 hour
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(secretKey)
                .compact();
    }

    //Reads a token and pulls the username back out (only works if signature is valid)
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    //Tries to read the token — if it fails (expired, tampered, malformed), returns false
    public boolean isTokenValid(String token) {
        try {
            extractUsername(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*A JWT token is just a long string with 3 parts separated by dots, like:
    eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcnNoaTE5In0.4f8K9s...
    PartContainsHeaderAlgorithm used to sign itPayloadData you put in it — usually the username,
    expiry timeSignatureA cryptographic proof that the token wasn't tampered with — created using
    a secret key only YOUR server knows   */
}