package com.banking.backend.transaction.dto;

import com.banking.backend.transaction.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;


// Daten einer Transaktion für das Frontend.
public class TransactionResponse {


    private Long id;
    private TransactionType type;
    private BigDecimal amount;
    private BigDecimal balanceAfter;
    private LocalDateTime createdAt;

    public TransactionResponse() {
    }

    public TransactionResponse(
            Long id,
            TransactionType type,
            BigDecimal amount,
            BigDecimal balanceAfter,
            LocalDateTime createdAt
    ) {

        this.id = id;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.createdAt = createdAt;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
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