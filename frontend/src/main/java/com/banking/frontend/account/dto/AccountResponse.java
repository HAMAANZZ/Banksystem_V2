package com.banking.frontend.account.dto;

import java.math.BigDecimal;


// Antwort vom Backend mit den Kontodaten.
public class AccountResponse {


    private Long id;

    private String accountNumber;

    private BigDecimal balance;

    private Long userId;


    public AccountResponse() {
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


    public Long getUserId() {
        return userId;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }
}