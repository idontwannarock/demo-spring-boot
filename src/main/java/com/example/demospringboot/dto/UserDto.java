package com.example.demospringboot.dto;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Set;

public class UserDto extends User {

    private static final long serialVersionUID = 3270703865633598338L;
    private Long userId;
    private String email;
    private Set<String> roles;
    private Set<String> privileges;

    public UserDto() {
        this(null, null, null, null, null, null, false, false, false, false, null);
    }

    public UserDto(Long userId, String username,
                   boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
                   Set<GrantedAuthority> authorities) {
        this(userId, username, "", "", Sets.newHashSet(), Sets.newHashSet(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public UserDto(Long userId, String username, String password, String email, Set<String> roles, Set<String> privileges,
                   boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
                   Set<GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
        this.email = email;
        this.roles = roles;
        this.privileges = privileges;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<String> privileges) {
        this.privileges = privileges;
    }
}
