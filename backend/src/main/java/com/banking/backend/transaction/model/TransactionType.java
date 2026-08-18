package com.banking.backend.transaction.model;


// Welche Art von Kontobewegung wurde durchgeführt?
public enum TransactionType {

    DEPOSIT,

    WITHDRAWAL,

    TRANSFER_IN,

    TRANSFER_OUT
}