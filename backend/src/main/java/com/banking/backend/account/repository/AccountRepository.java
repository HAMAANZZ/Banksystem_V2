package com.banking.backend.account.repository;

import com.banking.backend.account.model.Account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


// Zugriff auf die Tabelle "accounts".
public interface AccountRepository extends JpaRepository<Account, Long> {


    // Sucht ein Konto über die Kontonummer.
    Optional<Account> findByAccountNumber(String accountNumber);


    // Sucht das Konto eines bestimmten Benutzers.
    Optional<Account> findByUserId(Long userId);


    // Prüft, ob eine Kontonummer bereits existiert.
    boolean existsByAccountNumber(String accountNumber);
}