package com.example.demospringboot.persistence.repository;

import com.example.demospringboot.persistence.domain.Privileges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privileges, Integer> {

    Boolean existsByName(String privilegeName);
}
