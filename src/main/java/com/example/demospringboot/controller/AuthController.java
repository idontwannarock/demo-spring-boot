package com.example.demospringboot.controller;

import com.example.demospringboot.security.JwtUtil;
import com.example.demospringboot.security.SecurityConstants;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RequestMapping("auth")
@RestController
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        if (("user".equals(username) && "userPass".equals(password)) || ("admin".equals(username) && "adminPass".equals(password))) {
            Map<String, String> body = Maps.newHashMap();
            GrantedAuthority grantedAuthority;
            if ("user".equals(username))
                grantedAuthority = new SimpleGrantedAuthority("USER");
            else
                grantedAuthority = new SimpleGrantedAuthority("ADMIN");
            body.put("jwt", SecurityConstants.PREFIX + JwtUtil.createToken(username, Lists.newArrayList(grantedAuthority)));
            return ResponseEntity.ok(body);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
