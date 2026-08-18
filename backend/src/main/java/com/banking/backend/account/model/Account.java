package com.banking.backend.account.model;

import com.banking.backend.user.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.math.BigDecimal;


// Diese Klasse repräsentiert ein Bankkonto in der Datenbank.
@Entity
// Das Konto wird in der Tabelle "accounts" gespeichert.
@Table(name = "accounts")
public class Account {


    // Primärschlüssel des Kontos.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // Eindeutige Kontonummer.
    @Column(nullable = false, unique = true)
    private String accountNumber;


    // Aktueller Kontostand.
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;


    // Jedes Konto gehört genau einem Benutzer.
    @OneToOne(fetch = FetchType.LAZY, optional = false)

    // In der Tabelle accounts wird die User ID gespeichert.
    // unique = true sorgt dafür, dass ein User nur ein Konto haben kann.
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;


    // JPA benötigt einen leeren Konstruktor.
    public Account() {
    }


    // Wird automatisch ausgeführt, bevor ein neues Konto gespeichert wird.
    @PrePersist
    public void beforeInsert() {

        // Ein neues Konto startet mit 0,00 Euro.
        if (balance == null) {
            balance = BigDecimal.ZERO;
        }
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getAccountNumber() {
        return accountNumber;
    }


    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    public BigDecimal getBalance() {
        return balance;
    }


    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }
}