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
import com.banking.backend.transaction.repository.TransactionRepository;
import com.banking.backend.user.model.Role;
import com.banking.backend.user.model.UserStatus;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
    private final TransactionRepository transactionRepository;

    public UserService(
            UserRepository userRepository,
            AccountRepository accountRepository,
            TransactionRepository transactionRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse disableUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Benutzer wurde nicht gefunden."));

        if (user.getRole() == Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ein Administrator kann nicht deaktiviert werden.");
        }

        user.setStatus(UserStatus.DISABLED);

        User savedUser = userRepository.save(user);

        return convertToResponse(savedUser);
    }

    @Transactional
    public void deleteUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Benutzer wurde nicht gefunden."));

        if (user.getRole() == Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ein Administrator kann nicht gelöscht werden.");
        }

        accountRepository.findByUserId(userId).ifPresent(account -> {

            transactionRepository.deleteAllByAccountId(account.getId());

            accountRepository.delete(account);
        });

        userRepository.delete(user);
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

    @Transactional
    public UserResponse activateUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Benutzer wurde nicht gefunden."
                ));

        if (user.getRole() == Role.ADMIN) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Der Administrator muss nicht aktiviert werden."
            );
        }

        user.setStatus(UserStatus.ACTIVE);

        User savedUser = userRepository.save(user);

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