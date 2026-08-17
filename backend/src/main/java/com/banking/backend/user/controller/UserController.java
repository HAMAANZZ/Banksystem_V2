package com.banking.backend.user.controller;

import com.banking.backend.user.dto.CreateUserRequest;
import com.banking.backend.user.dto.UserResponse;
import com.banking.backend.user.service.UserService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST Controller für alle Benutzer Anfragen.
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    // Spring stellt den UserService automatisch bereit.
    public UserController(UserService userService) {
        this.userService = userService;
    }


    // Erstellt einen neuen Benutzer.
    // POST /api/users
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest request) {

        // Übergibt die Benutzerdaten an den Service.
        return userService.createUser(request);
    }


    // Gibt alle Benutzer zurück.
    // GET /api/users
    @GetMapping
    public List<UserResponse> getAllUsers() {
        // Lädt alle Benutzer über den Service.
        return userService.getAllUsers();
    }
}