package com.banking.backend.auth.controller;

import com.banking.backend.auth.dto.LoginRequest;
import com.banking.backend.auth.dto.LoginResponse;
import com.banking.backend.auth.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

// Er kommt von SecirotyConfig und geht zu login(...)
// REST Controller für Login und Authentifizierung.
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Prüft die Login Daten.
    // POST /api/auth/login
    // @RequestBody JSON => LoginRequest
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}