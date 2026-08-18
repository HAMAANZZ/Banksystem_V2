package com.banking.backend.user.service;

import com.banking.backend.user.dto.CreateUserRequest;
import com.banking.backend.user.dto.UserResponse;
import com.banking.backend.user.model.User;
import com.banking.backend.user.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import com.banking.backend.account.model.Account;
import com.banking.backend.account.repository.AccountRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import com.banking.backend.account.model.Account;

/**
 * CreateUserRequest → UserService → Username vorhanden? → E-Mail vorhanden?
 * → User Objekt erstellen → Passwort hashen → userRepository.save → PostgreSQL
 *
 */
// Enthält die Logik für Benutzer.
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    public UserService(
            UserRepository userRepository,
            AccountRepository accountRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Erstellt einen neuen Benutzer.
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {

        // Benutzername darf nicht doppelt vorkommen.
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException(
                    "Benutzername existiert bereits."
            );
        }

        // E Mail Adresse darf nicht doppelt vorkommen.
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(
                    "E Mail Adresse existiert bereits."
            );
        }


        // Neues User Objekt erstellen.
        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());


        // Passwort wird vor dem Speichern gehasht.
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Geburtstag wird Speichern.
        user.setGeburtstag(request.getGeburtstag());


        // Benutzer in PostgreSQL speichern.
        User savedUser = userRepository.save(user);
        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setUser(savedUser);
        accountRepository.save(account);

        // Sichere Antwort ohne Passwort zurückgeben.
        return convertToResponse(savedUser);
    }


    // Lädt alle Benutzer.
    public List<UserResponse> getAllUsers() {

        return userRepository
                .findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    // Wandelt User in UserResponse um.
    private UserResponse convertToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getGeburtstag(),
                user.getRole(),
                user.getStatus(),
                user.getCreatedAt());
    }

    private String generateAccountNumber() {

        String accountNumber;

        do {
            accountNumber = "BK"
                    + UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 12)
                    .toUpperCase();
        } while (accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }
}