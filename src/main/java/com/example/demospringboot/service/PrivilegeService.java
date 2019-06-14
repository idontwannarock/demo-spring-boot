package com.example.demospringboot.service;

import com.example.demospringboot.persistence.domain.Privileges;
import com.example.demospringboot.persistence.repository.PrivilegeRepository;
import com.example.demospringboot.vo.PrivilegePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PrivilegeService {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    public PrivilegePayload addNewPrivilege(String privilegeName) {
        if (privilegeRepository.existsByName(privilegeName)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The privilege, " + privilegeName + ", has already been taken. Please consider edit privilege or provide another privilege name.");
        }
        Privileges privilege = new Privileges();
        privilege.setName(privilegeName);
        privilegeRepository.save(privilege);
        return PrivilegePayload.init().id(privilege.getId()).name(privilege.getName()).build();
    }

    public List<PrivilegePayload> getAllPrivileges() {
        return privilegeRepository.findAll(new Sort(Sort.Direction.ASC, "id"))
                .stream()
                .map(privilege -> PrivilegePayload.init().id(privilege.getId()).name(privilege.getName()).build())
                .collect(Collectors.toList());
    }
}
