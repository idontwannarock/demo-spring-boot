package com.example.demospringboot.persistence.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Users implements Serializable {

    private static final long serialVersionUID = 5243048183842420115L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, unique = true, nullable = false)
    private String username;
    private String password;
    @Column(length = 150, unique = true, nullable = false)
    private String email;

    @ManyToMany
    @JoinTable(name = "role_members",
            joinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_role_members_users")),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_role_members_roles")))
    private Set<Roles> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }
}
