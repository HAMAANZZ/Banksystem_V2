package com.banking.backend.user.controller;

import com.banking.backend.user.dto.*;
import com.banking.backend.user.service.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.banking.backend.transaction.repository.*;
import com.banking.backend.user.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.*;

// REST Controller für alle Benutzer Anfragen.
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    // Spring stellt den UserService automatisch bereit.
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping("/{userId}/disable")
    public UserResponse disableUser(@PathVariable("userId") Long userId) {

        return userService.disableUser(userId);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("userId") Long userId) {

        userService.deleteUser(userId);
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

    @PatchMapping("/{userId}/activate")
    public UserResponse activateUser(@PathVariable("userId") Long userId) {

        return userService.activateUser(userId);
    }


}