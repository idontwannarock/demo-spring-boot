package com.example.demospringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("home")
@RestController
public class HomeController {

    @Autowired
    private Environment environment;

    @GetMapping
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello from home running at port: " + environment.getProperty("local.server.port"));
    }

    @PostMapping("/saying")
    public ResponseEntity<?> helloSaying(@RequestParam String text) {
        return ResponseEntity.ok("Hello from home saying " + text);
    }

    @GetMapping("/admin")
    public ResponseEntity<String> helloAdmin() {
        return ResponseEntity.ok("This is the admin area of home running at port: " + environment.getProperty("local.server.port"));
    }
}
