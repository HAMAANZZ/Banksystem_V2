package com.banking.backend.transaction.service;

import com.banking.backend.account.model.Account;
import com.banking.backend.account.repository.AccountRepository;
import com.banking.backend.transaction.dto.TransactionResponse;
import com.banking.backend.transaction.model.BankTransaction;
import com.banking.backend.transaction.repository.TransactionRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


// Geschäftslogik für Transaktionen.
@Service
public class TransactionService {


    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;


    public TransactionService(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository) {

        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }


    // Lädt alle Transaktionen eines Benutzers.
    public List<TransactionResponse> getTransactionsByUserId(Long userId) {


        Account account = accountRepository
                .findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Konto wurde nicht gefunden."
                ));


        return transactionRepository
                .findAllByAccountIdOrderByCreatedAtDesc(account.getId())
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    private TransactionResponse convertToResponse(
            BankTransaction transaction
    ) {

        return new TransactionResponse(
                transaction.getId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getBalanceAfter(),
                transaction.getCreatedAt()
        );
    }
}