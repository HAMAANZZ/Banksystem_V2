package com.banking.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Startklasse des Spring Boot Backends
@SpringBootApplication
public class BankingApplication {

    public static void main(String[] args) {

        // Startet den Backend Server
        SpringApplication.run(BankingApplication.class, args);
    }
}
