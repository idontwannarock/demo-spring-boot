package com.example.demospringboot.controller;

import com.example.demospringboot.security.JwtUtil;
import com.example.demospringboot.security.SecurityConstants;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Api(tags = "Authentication | Authorization")
@RequestMapping("auth")
@RestController
public class AuthController {

    @ApiOperation(value = "Login with Username and Password", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        if (("user".equals(username) && "userPass".equals(password)) || ("admin".equals(username) && "adminPass".equals(password))) {
            Map<String, String> body = Maps.newHashMap();
            List<SimpleGrantedAuthority> authorities;
            if ("user".equals(username))
                authorities = Lists.newArrayList(new SimpleGrantedAuthority("USER"));
            else
                authorities = Lists.newArrayList(new SimpleGrantedAuthority("USER"), new SimpleGrantedAuthority("ADMIN"));
            body.put("jwt", SecurityConstants.PREFIX + JwtUtil.createToken(username, authorities));
            return ResponseEntity.ok(body);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
