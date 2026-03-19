package com.rgt.ebanking.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;


@Component
public class JWTUtil{

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long accessExpMs;

    @Value("${jwt.refresh.expiration:604800000}")
    private long refreshExpMs;

    private SecretKey key;

    // Initializes the key after the class is instantiated and the jwtSecret is injected, 
    // preventing the repeated creation of the key and enhancing performance
    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    // Genrate JWT token
    public String generateToken(String username, long expirationMs){
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(Date.from(Instant.now().plusMillis(expirationMs)))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public String generateAccessToken(String username){
        return generateToken(username, accessExpMs);
    }

    public String generateRefreshToken(String username){
        return generateToken(username, refreshExpMs);
    }

    // username from JWT token
    public String getUsernameFromToken(String token){
        return Jwts.parserBuilder()
        .setSigningKey(key).build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
    }

    // validate JWT token
    public boolean validateJwtToken(String token){
        try{
            Jwts.parserBuilder()
            .setSigningKey(key).build()
            .parseClaimsJws(token);
            return true;
        } catch(SecurityException e){
            System.out.println("Invalid JWT signature: " + e.getMessage());
        } catch(MalformedJwtException e){
            System.out.println("Invalid JWT token: " + e.getMessage());
        } catch(ExpiredJwtException e){
            System.out.println("Expired JWT token: " + e.getMessage());
        } catch (UnsupportedJwtException e){
            System.out.println("Unsupprted JWT token: " + e.getMessage());
        } catch (IllegalArgumentException e){
            System.out.println("JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }
}