package com.banking.backend.auth.service;

import com.banking.backend.auth.dto.LoginRequest;
import com.banking.backend.auth.dto.LoginResponse;
import com.banking.backend.user.model.User;
import com.banking.backend.user.model.UserStatus;
import com.banking.backend.user.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

// Enthält die Logik für den Login.
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    // Spring stellt Repository und PasswordEncoder automatisch bereit.
    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    // Prüft Benutzername und Passwort.
    public LoginResponse login(LoginRequest request) {

        // Benutzer anhand des Benutzernamens suchen.
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Benutzername oder Passwort ist falsch."));


        // Eingegebenes Passwort mit dem gespeicherten Hash vergleichen.
        boolean passwordCorrect = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!passwordCorrect) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Benutzername oder Passwort ist falsch.");
        }

        // Gesperrte oder deaktivierte Benutzer dürfen sich nicht anmelden.
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Benutzerkonto ist nicht aktiv."
            );
        }


        // Login war erfolgreich.
        return new LoginResponse(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }
}