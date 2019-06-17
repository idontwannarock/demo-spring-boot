package com.example.demospringboot.persistence.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Roles implements Serializable {

    private static final long serialVersionUID = 8906166702356978193L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 100, unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<Users> users = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "role_privileges",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_role_privileges_roles")),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_role_privileges_privileges")))
    private Set<Privileges> privileges = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Users> getUsers() {
        return users;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }

    public Set<Privileges> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<Privileges> privileges) {
        this.privileges = privileges;
    }
}
