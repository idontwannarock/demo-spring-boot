package com.example.demospringboot.service;

import com.example.demospringboot.converter.DataObjectConverter;
import com.example.demospringboot.persistence.domain.Roles;
import com.example.demospringboot.persistence.domain.Users;
import com.example.demospringboot.persistence.repository.RoleRepository;
import com.example.demospringboot.persistence.repository.UserRepository;
import com.example.demospringboot.vo.JwtPayload;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private DataObjectConverter converter;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public JwtPayload login(final String username, final String password) {
        Users target = userRepository.findByUsername(username);
        if (target == null || (!target.getPassword().isEmpty() && !encoder.matches(password, target.getPassword()))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login failed. Please check your username and password again.");
        }
        return converter.convertDomainToPayload(target);
    }

    public JwtPayload register(final String username, final String password, final String email, final Integer roleId) {
        Users target = userRepository.findByUsername(username);
        if (target != null && target.getUsername().equals(username) && target.getEmail().equals(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The username or email has been taken.");
        }

        Roles roleToAdd = roleRepository.getOne(roleId);

        target = new Users();
        target.setUsername(username);
        target.setPassword(encoder.encode(password));
        target.setEmail(email);
        target.setRoles(Sets.newHashSet(roleToAdd));
        userRepository.save(target);

        return converter.convertDomainToPayload(target);
    }

    public JwtPayload addRolesToUser(List<String> roleNames, Long userId) {
        Set<Roles> rolesToAdd = roleRepository.findByNameIn(roleNames);
        if (rolesToAdd == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The role you wish to add does not exist.");
        }
        Users target = userRepository.getOne(userId);
        Set<Roles> roles = target.getRoles();
        roles.addAll(rolesToAdd);
        target.setRoles(roles);
        userRepository.save(target);
        return converter.convertDomainToPayload(target);
    }
}
