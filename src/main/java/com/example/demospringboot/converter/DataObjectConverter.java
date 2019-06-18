package com.example.demospringboot.converter;

import com.example.demospringboot.persistence.domain.Attendance;
import com.example.demospringboot.persistence.domain.Privileges;
import com.example.demospringboot.persistence.domain.Roles;
import com.example.demospringboot.persistence.domain.Users;
import com.example.demospringboot.security.JwtUtil;
import com.example.demospringboot.vo.ClockingPayload;
import com.example.demospringboot.vo.JwtPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DataObjectConverter {

    @Autowired
    private ObjectMapper mapper;

    public JwtPayload convertDomainToPayload(Users user) {
        Set<String> roles = user.getRoles().stream().map(Roles::getName).collect(Collectors.toSet());
        Set<String> privileges = user.getRoles().stream().flatMap(role -> role.getPrivileges().stream()).map(Privileges::getName).collect(Collectors.toSet());

        String rolesJson = writeValueAsString(roles);
        String privilegesJson = writeValueAsString(privileges);

        return JwtPayload.init().jwt(JwtUtil.createToken(user.getId().toString(), user.getUsername(), rolesJson, privilegesJson)).build();
    }

    private String writeValueAsString(Set<String> set) {
        try {
            return mapper.writer().writeValueAsString(set);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    public ClockingPayload convertDomainToPayload(Attendance attendance) {
        return ClockingPayload.init().id(attendance.getId()).clockInAt(attendance.getClockInTime()).clockOutAt(attendance.getClockOutTime()).build();
    }
}
