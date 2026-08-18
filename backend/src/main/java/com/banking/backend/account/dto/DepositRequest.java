package com.banking.backend.account.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


// Daten, die das Backend für eine Einzahlung bekommt.
public class DepositRequest {


    // Betrag darf nicht leer sein.
    @NotNull(message = "Betrag darf nicht leer sein.")

    // Mindestens 0,01 Euro.
    @DecimalMin(value = "0.01", message = "Der Betrag muss mindestens 0,01 Euro sein.")

    // Maximal zwei Nachkommastellen.
    @Digits(integer = 17, fraction = 2, message = "Der Betrag darf maximal zwei Nachkommastellen haben.")
    private BigDecimal amount;


    // Leerer Konstruktor für Jackson.
    public DepositRequest() {
    }


    public BigDecimal getAmount() {
        return amount;
    }


    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}