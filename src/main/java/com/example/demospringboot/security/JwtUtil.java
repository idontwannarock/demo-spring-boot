package com.example.demospringboot.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

import static com.example.demospringboot.security.SecurityConstants.*;

public class JwtUtil {

    public static String createToken(String userId, String username, String rolesJson, String privilegesJson) {
        long now = System.currentTimeMillis();
        return BEARER_PREFIX + Jwts.builder()
                .setSubject(userId)
                .claim(USERNAME, username)
                .claim(ROLES, rolesJson)
                .claim(PRIVILEGES, privilegesJson)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + EXPIRATION_TIME * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
}
