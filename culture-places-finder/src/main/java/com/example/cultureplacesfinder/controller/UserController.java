package com.example.cultureplacesfinder.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.cultureplacesfinder.model.User;
import com.example.cultureplacesfinder.service.UserService;

import org.springframework.http.ResponseEntity;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        user.setId(UUID.randomUUID()); // Generálj egyedi azonosítót
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully with ID: " + user.getId());
    }
}
