package com.banking.backend.account.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


// Daten für eine Auszahlung.
public class WithdrawRequest {


    @NotNull(message = "Betrag darf nicht leer sein.")
    @DecimalMin(value = "0.01", message = "Der Betrag muss mindestens 0,01 Euro sein.")
    @Digits(integer = 17, fraction = 2, message = "Der Betrag darf maximal zwei Nachkommastellen haben.")
    private BigDecimal amount;

    public WithdrawRequest() {
    }


    public BigDecimal getAmount() {
        return amount;
    }


    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}