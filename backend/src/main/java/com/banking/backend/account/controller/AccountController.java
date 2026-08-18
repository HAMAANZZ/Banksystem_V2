package com.banking.backend.account.controller;

import com.banking.backend.account.dto.AccountResponse;
import com.banking.backend.account.dto.DepositRequest;
import com.banking.backend.account.service.AccountService;
import com.banking.backend.account.dto.TransferRequest;

import jakarta.validation.Valid;

import com.banking.backend.account.dto.WithdrawRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.backend.transaction.dto.TransactionResponse;
import com.banking.backend.transaction.service.TransactionService;

import java.util.List;

// REST Controller für Konto Funktionen.
@RestController
@RequestMapping("/api/accounts")
public class AccountController {


    private final AccountService accountService;
    private final TransactionService transactionService;

    // Spring gibt uns den AccountService automatisch.
    public AccountController(
            AccountService accountService,
            TransactionService transactionService) {

        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping("/user/{userId}/transactions")
    public List<TransactionResponse> getTransactions(@PathVariable("userId") Long userId) {
        return transactionService.getTransactionsByUserId(userId);
    }

    // Konto eines Benutzers laden.
    // Beispiel: GET /api/accounts/user/4
    @GetMapping("/user/{userId}")
    public AccountResponse getAccount(@PathVariable("userId") Long userId) {

        return accountService.getAccountByUserId(userId);
    }

    @PostMapping("/user/{userId}/withdraw")
    public AccountResponse withdraw(
            @PathVariable("userId") Long userId,
            @Valid @RequestBody WithdrawRequest request) {

        return accountService.withdraw(userId, request.getAmount());
    }

    // Geld auf das Konto eines Benutzers einzahlen.
    // Beispiel: POST /api/accounts/user/4/deposit
    @PostMapping("/user/{userId}/deposit")
    public AccountResponse deposit(
            @PathVariable("userId") Long userId,
            @Valid @RequestBody DepositRequest request) {

        return accountService.deposit(userId, request.getAmount());
    }

    @PostMapping("/user/{userId}/transfer")
    public AccountResponse transfer(
            @PathVariable("userId") Long userId,
            @Valid @RequestBody TransferRequest request
    ) {

        return accountService.transfer(
                userId,
                request.getReceiverAccountNumber(),
                request.getAmount()
        );
    }
}