package com.example.demospringboot.security;

import com.google.common.base.Strings;
import io.jsonwebtoken.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.example.demospringboot.security.SecurityConstants.*;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String header = request.getHeader(HEADER);
        if (!Strings.isNullOrEmpty(header) && header.startsWith(PREFIX)) {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER);
        if (!Strings.isNullOrEmpty(token)) {
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET.getBytes())
                        .parseClaimsJws(token.replace(PREFIX, ""))
                        .getBody();

                String username = claims.getSubject();

                Collection<? extends GrantedAuthority> authorities =
                        Arrays
                                .stream(claims.get(ROLES).toString().replace("[", "").replace("]", "").split(","))
                                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.trim()))
                                .collect(Collectors.toList());

                User principal = new User(username, "", authorities);

                if (!Strings.isNullOrEmpty(username)) {
                    System.out.println("JWT represents User '" + username + "' with Role: " + authorities);
                    return new UsernamePasswordAuthenticationToken(principal, "", authorities);
                }
            } catch (ExpiredJwtException exception) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Request to parse expired JWT : " + token + " failed : " + exception.getMessage());
            } catch (UnsupportedJwtException exception) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Request to parse unsupported JWT : " + token + " failed : " + exception.getMessage());
            } catch (MalformedJwtException exception) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Request to parse invalid JWT : " + token + " failed : " + exception.getMessage());
            } catch (SecurityException exception) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Request to parse JWT with invalid signature : " + token + " failed : " + exception.getMessage());
            } catch (IllegalArgumentException exception) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Request to parse empty or null JWT : " + token + " failed : " + exception.getMessage());
            }
        }
        return null;
    }
}
