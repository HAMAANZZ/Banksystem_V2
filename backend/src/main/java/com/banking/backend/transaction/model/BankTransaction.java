package com.banking.backend.transaction.model;

import com.banking.backend.account.model.Account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;


// Eine einzelne Kontobewegung.
@Entity
@Table(name = "transactions")
public class BankTransaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // Zu welchem Konto gehört diese Transaktion?
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;


    // Art der Transaktion.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;


    // Betrag der Transaktion.
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;


    // Kontostand direkt nach dieser Transaktion.
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balanceAfter;


    // Zeitpunkt.
    @Column(nullable = false)
    private LocalDateTime createdAt;


    public BankTransaction() {
    }


    @PrePersist
    public void beforeInsert() {

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public Account getAccount() {
        return account;
    }


    public void setAccount(Account account) {
        this.account = account;
    }


    public TransactionType getType() {
        return type;
    }


    public void setType(TransactionType type) {
        this.type = type;
    }


    public BigDecimal getAmount() {
        return amount;
    }


    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }


    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}