package com.authSecurity.Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    // Key for signing the JWT
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    // Token expiration time (1 day)
    private static final long EXPIRATION_TIME = 86400000L; // 1 day in milliseconds

    // Method to generate a JWT token
    public static String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // Subject of the token (email in this case)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Issued at time
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Expiration time
                .signWith(key) // Sign the token with the key
                .compact(); // Build and serialize the token to a compact, URL-safe string
    }

    // Method to get email from the token
    public static String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key) // Set the signing key
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // Get the subject (email) from the token
    }

    // Method to validate the token
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // Log the exception if needed
            return false;
        }
    }
}
