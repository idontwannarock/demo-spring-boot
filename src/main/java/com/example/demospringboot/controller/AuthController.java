package com.example.demospringboot.controller;

import com.example.demospringboot.dto.UserDto;
import com.example.demospringboot.service.PrivilegeService;
import com.example.demospringboot.service.RoleService;
import com.example.demospringboot.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Set;

@Api(tags = "Security")
@RequestMapping("auth")
@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Signup with username, password, email and role.", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestParam String username, @RequestParam String password, @RequestParam String email, @RequestParam(defaultValue = "1") Integer roleId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(username, password, email, roleId));
    }

    @ApiOperation(value = "Login with username and password.", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String username, @RequestParam(required = false, defaultValue = "") String password) {
        return ResponseEntity.ok(userService.login(username, password));
    }

    @ApiOperation(value = "Add roles to a user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("admin/role/user")
    public ResponseEntity<?> addRolesToUser(
            @RequestParam(defaultValue = "[]") List<String> roleNames, @ApiIgnore @AuthenticationPrincipal UserDto user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addRolesToUser(roleNames, user.getUserId()));
    }

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "Add new role with privileges.", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("admin/role")
    public ResponseEntity<?> addNewRoleWithPrivileges(
            @RequestParam String roleName, @RequestParam(required = false, defaultValue = "[]") Set<Integer> privilegeIds) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.addNewRoleWithPrivileges(roleName, privilegeIds));
    }

    @ApiOperation(value = "Get all roles.", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("admin/roles")
    public ResponseEntity<?> getRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @Autowired
    private PrivilegeService privilegeService;

    @ApiOperation(value = "Add new privilege.", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("admin/privilege")
    public ResponseEntity<?> addNewPrivilege(
            @RequestParam String privilegeName) {
        return ResponseEntity.status(HttpStatus.CREATED).body(privilegeService.addNewPrivilege(privilegeName));
    }

    @ApiOperation(value = "Get all privileges.", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("admin/privileges")
    public ResponseEntity<?> getPrivileges() {
        return ResponseEntity.ok(privilegeService.getAllPrivileges());
    }
}
