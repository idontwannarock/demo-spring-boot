package com.example.demospringboot.persistence.repository;

import com.example.demospringboot.persistence.domain.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {

    Boolean existsByName(String roleName);

    Set<Roles> findByNameIn(List<String> roleNames);
}
