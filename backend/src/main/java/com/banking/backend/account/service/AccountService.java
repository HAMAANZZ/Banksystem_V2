package com.banking.backend.account.service;

import com.banking.backend.account.dto.AccountResponse;
import com.banking.backend.account.model.Account;
import com.banking.backend.account.repository.AccountRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

import com.banking.backend.transaction.model.BankTransaction;
import com.banking.backend.transaction.model.TransactionType;
import com.banking.backend.transaction.repository.TransactionRepository;

// Enthält die Geschäftslogik für Bankkonten.
@Service
public class AccountService {


    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository) {

        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }


    // Lädt das Konto eines Benutzers.
    public AccountResponse getAccountByUserId(Long userId) {

        Account account = accountRepository.findByUserId(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Konto wurde nicht gefunden."));

        return convertToResponse(account);
    }

    @Transactional
    public AccountResponse transfer(Long senderUserId, String receiverAccountNumber, BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Der Überweisungsbetrag muss größer als 0 sein.");
        }

        if (receiverAccountNumber == null || receiverAccountNumber.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Die Empfänger Kontonummer darf nicht leer sein.");
        }

        receiverAccountNumber = receiverAccountNumber.trim();

        Account senderAccount = accountRepository.findByUserId(senderUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Senderkonto wurde nicht gefunden."));

        Account receiverAccount = accountRepository.findByAccountNumber(receiverAccountNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empfängerkonto wurde nicht gefunden."));

        if (senderAccount.getId().equals(receiverAccount.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Eine Überweisung auf das eigene Konto ist nicht möglich.");
        }

        if (senderAccount.getBalance().compareTo(amount) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nicht genügend Guthaben.");
        }

        BigDecimal senderNewBalance = senderAccount.getBalance().subtract(amount);
        BigDecimal receiverNewBalance = receiverAccount.getBalance().add(amount);

        senderAccount.setBalance(senderNewBalance);
        receiverAccount.setBalance(receiverNewBalance);

        Account savedSender = accountRepository.save(senderAccount);
        Account savedReceiver = accountRepository.save(receiverAccount);

        BankTransaction senderTransaction = new BankTransaction();
        senderTransaction.setAccount(savedSender);
        senderTransaction.setType(TransactionType.TRANSFER_OUT);
        senderTransaction.setAmount(amount);
        senderTransaction.setBalanceAfter(senderNewBalance);
        transactionRepository.save(senderTransaction);

        BankTransaction receiverTransaction = new BankTransaction();
        receiverTransaction.setAccount(savedReceiver);
        receiverTransaction.setType(TransactionType.TRANSFER_IN);
        receiverTransaction.setAmount(amount);
        receiverTransaction.setBalanceAfter(receiverNewBalance);
        transactionRepository.save(receiverTransaction);

        return convertToResponse(savedSender);
    }

    // Zahlt Geld auf das Konto eines Benutzers ein.
    @Transactional
    public AccountResponse deposit(Long userId, BigDecimal amount) {

        // Zusätzliche Sicherheitsprüfung.
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Der Einzahlungsbetrag muss größer als 0 sein.");
        }


        // Konto des Benutzers suchen.
        Account account = accountRepository.findByUserId(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Konto wurde nicht gefunden."));
        // Aktuellen Kontostand holen.
        BigDecimal currentBalance = account.getBalance();
        // Einzahlung zum Kontostand addieren.
        BigDecimal newBalance = currentBalance.add(amount);
        // Neuen Kontostand im Account setzen.
        account.setBalance(newBalance);
        // Konto in PostgreSQL speichern.
        Account savedAccount = accountRepository.save(account);

        BankTransaction transaction = new BankTransaction();
        transaction.setAccount(savedAccount);
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setAmount(amount);
        transaction.setBalanceAfter(newBalance);
        transactionRepository.save(transaction);

        // Sichere Antwort zurückgeben.
        return convertToResponse(savedAccount);
    }


    // Wandelt ein Account Objekt in eine sichere Antwort um.
    private AccountResponse convertToResponse(Account account) {

        return new AccountResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getUser().getId()
        );
    }

    @Transactional
    public AccountResponse withdraw(Long userId, BigDecimal amount) {
        // Betrag prüfen.
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Der Auszahlungsbetrag muss größer als 0 sein.");
        }


        // Konto des Benutzers laden.
        Account account = accountRepository
                .findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Konto wurde nicht gefunden."));


        // Prüfen, ob genug Geld vorhanden ist.
        if (account.getBalance().compareTo(amount) < 0) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nicht genügend Guthaben.");
        }


        // Neuen Kontostand berechnen.
        BigDecimal newBalance = account.getBalance().subtract(amount);

        // Kontostand setzen.
        account.setBalance(newBalance);

        // Konto speichern.
        Account savedAccount = accountRepository.save(account);

        // Auszahlung als Transaktion speichern.
        BankTransaction transaction = new BankTransaction();


        transaction.setAccount(savedAccount);
        transaction.setType(TransactionType.WITHDRAWAL);
        transaction.setAmount(amount);
        transaction.setBalanceAfter(newBalance);
        transactionRepository.save(transaction);
        return convertToResponse(savedAccount);
    }
}