package com.banking.backend.transaction.repository;

import com.banking.backend.transaction.model.BankTransaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


// Zugriff auf die Tabelle transactions.
public interface TransactionRepository extends JpaRepository<BankTransaction, Long> {


    // Alle Transaktionen eines Kontos laden.
    // Neueste Transaktion steht zuerst.
    List<BankTransaction> findAllByAccountIdOrderByCreatedAtDesc(Long accountId);

    void deleteAllByAccountId(Long accountId);
}