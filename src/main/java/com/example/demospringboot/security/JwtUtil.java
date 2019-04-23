package com.example.demospringboot.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static com.example.demospringboot.security.SecurityConstants.*;

public class JwtUtil {

    public static String createToken(String accountId, Collection<? extends GrantedAuthority> authorities) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(accountId)
                .claim(ROLES, authorities.stream().map(Object::toString).collect(Collectors.toList()))
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + EXPIRATION_TIME * 1000))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS512)
                .compact();
    }
}
