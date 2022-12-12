package com.example.demospringboot.service;

import com.example.demospringboot.persistence.domain.Roles;
import com.example.demospringboot.persistence.repository.PrivilegeRepository;
import com.example.demospringboot.persistence.repository.RoleRepository;
import com.example.demospringboot.vo.RolePayload;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    public RolePayload addNewRoleWithPrivileges(String roleName, Set<Integer> privilegeIds) {
        if (roleRepository.existsByName(roleName)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The role, " + roleName + ", has already been taken. Please consider edit role or provide another role name.");
        }
        Roles role = new Roles();
        role.setName(roleName);
        role.setPrivileges(Sets.newHashSet(privilegeRepository.findAllById(privilegeIds)));
        roleRepository.save(role);
        return RolePayload.init().id(role.getId()).name(role.getName()).build();
    }

    public List<RolePayload> getAllRoles() {
        return roleRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(role -> RolePayload.init().id(role.getId()).name(role.getName()).build())
                .collect(Collectors.toList());
    }
}
