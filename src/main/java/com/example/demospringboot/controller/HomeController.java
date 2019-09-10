package com.example.demospringboot.controller;

import com.example.demospringboot.annotation.ApiVersion;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@ApiVersion
@Api(tags = "Home")
@RequestMapping("home")
@RestController
public class HomeController {

    @Autowired
    private Environment environment;

    @ApiVersion(2)
    @ApiOperation(value = "Hello", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello from home running at port: " + environment.getProperty("local.server.port"));
    }

    @ApiOperation(value = "Hello and Saying", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/saying")
    public ResponseEntity<?> helloSaying(@RequestParam String text) {
        return ResponseEntity.ok("Hello from home saying " + text);
    }

    @ApiOperation(value = "Hello to a Admin", notes = "Only user with ADMIN authority could access this endpoint.", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/admin")
    public ResponseEntity<String> helloAdmin() {
        return ResponseEntity.ok("This is the admin area of home running at port: " + environment.getProperty("local.server.port"));
    }

    @ApiOperation(value = "Get Current Username", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/username")
    public ResponseEntity<String> helloUser(@AuthenticationPrincipal String username) {
        return ResponseEntity.ok("Hello " + username + "!");
    }
}
