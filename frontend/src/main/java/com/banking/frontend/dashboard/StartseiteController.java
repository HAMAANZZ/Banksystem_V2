package com.banking.frontend.dashboard;

import com.banking.frontend.account.dto.AccountResponse;

import com.banking.frontend.transaction.dto.TransactionResponse;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;


// Controller für die Startseite des Banksystems.
public class StartseiteController {


    @FXML
    private Label greetingLabel;


    @FXML
    private Label usernameLabel;


    @FXML
    private Label roleLabel;


    @FXML
    private Label balanceLabel;


    @FXML
    private Label accountNumberLabel;


    @FXML
    private ListView<String> transactionsListView;


    @FXML
    private Button depositButton;


    @FXML
    private Button withdrawButton;


    @FXML
    private Button transferButton;


    @FXML
    private Button logoutButton;


    // ID des eingeloggten Benutzers.
    private Long userId;


    // HTTP Client für Anfragen an das Backend.
    private final HttpClient httpClient = HttpClient.newHttpClient();
    // Wandelt JSON in Java Objekte um.
    private final ObjectMapper objectMapper = new ObjectMapper();


    @FXML
    private void initialize() {

        greetingLabel.setText("Guten Tag");
        usernameLabel.setText("Benutzer wird geladen...");
        roleLabel.setText("Rolle wird geladen...");
        balanceLabel.setText("0,00 €");
        accountNumberLabel.setText("Kontonummer wird geladen...");
    }


    // Wird nach erfolgreichem Login aufgerufen.
    public void setUserData(String username, String role, Long id) {

        // User ID speichern.
        this.userId = id;
        // Benutzerinformationen anzeigen.
        greetingLabel.setText("Guten Tag, " + username);
        usernameLabel.setText("Benutzer: " + username);
        roleLabel.setText("Rolle: " + role);
        // Echtes Konto vom Backend laden.
        loadAccount();
        loadTransactions();
    }


    // Lädt die echten Kontodaten vom Backend.
    private void loadAccount() {

        try {

            // GET Anfrage erstellen.
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/accounts/user/" + userId))
                    .GET()
                    .build();
            // Anfrage an Backend senden.
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Erfolgreiche Antwort.
            if (response.statusCode() == 200) {
                // JSON in AccountResponse umwandeln.
                AccountResponse account = objectMapper.readValue(response.body(), AccountResponse.class);
                // Kontodaten anzeigen.
                updateAccountView(account);

                return;
            }


            // Backend hat einen Fehler zurückgegeben.
            showError(response.body());


        } catch (Exception exception) {
            exception.printStackTrace();
            showError("Kontodaten konnten nicht geladen werden.");
        }
    }


    // Wird ausgeführt, wenn auf Einzahlen geklickt wird.
    @FXML
    private void handleDeposit() {

        // Fenster zur Eingabe des Betrags.
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Einzahlung");
        dialog.setHeaderText("Geld einzahlen");
        dialog.setContentText("Betrag in Euro:");


        // Dialog öffnen.
        Optional<String> result = dialog.showAndWait();


        // Benutzer hat Abbrechen gedrückt.
        if (result.isEmpty()) {
            return;
        }


        // Eingabe holen.
        String input = result.get().trim();
        // Deutsches Komma erlauben.
        input = input.replace(",", ".");
        try {

            // Text in BigDecimal umwandeln.
            BigDecimal amount = new BigDecimal(input);

            // Betrag muss größer als 0 sein.
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                showError("Der Betrag muss größer als 0 sein.");
                return;
            }


            // Maximal zwei Nachkommastellen erlauben.
            if (amount.scale() > 2) {
                showError("Der Betrag darf maximal zwei Nachkommastellen haben.");
                return;
            }

            // Einzahlung an Backend senden.
            deposit(amount);


        } catch (NumberFormatException exception) {
            showError("Bitte einen gültigen Betrag eingeben.");
        }
    }


    // Sendet die Einzahlung an das Backend.
    private void deposit(BigDecimal amount) {

        try {

            // JSON für das Backend.
            String json = """
                    {
                        "amount": %s
                    }
                    """.formatted(amount.toPlainString());


            // POST Anfrage erstellen.
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/accounts/user/" + userId + "/deposit"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            // Anfrage senden.
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());


            // Einzahlung erfolgreich.
            if (response.statusCode() == 200) {

                // Neue Kontodaten aus Backend Antwort lesen.
                AccountResponse account = objectMapper.readValue(response.body(), AccountResponse.class);
                // Anzeige sofort aktualisieren.
                updateAccountView(account);
                // Erfolgsmeldung anzeigen.

                loadTransactions();

                showInfo("Einzahlung erfolgreich.");

                return;
            }

            // Fehler vom Backend anzeigen.
            showError(response.body());


        } catch (Exception exception) {

            exception.printStackTrace();
            showError("Einzahlung konnte nicht durchgeführt werden.");
        }
    }


    // Aktualisiert Kontonummer und Kontostand.
    private void updateAccountView(AccountResponse account) {

        // Deutsches Geldformat.
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.GERMANY);

        // Kontostand anzeigen.
        balanceLabel.setText(currencyFormat.format(account.getBalance()));

        // Echte Kontonummer anzeigen.
        accountNumberLabel.setText("Kontonummer: " + account.getAccountNumber());
    }


    // Zeigt eine Fehlermeldung.
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    // Zeigt eine Erfolgsmeldung.
    private void showInfo(String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Banksystem");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    // Benutzer abmelden.
    @FXML
    private void handleLogout() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Banksystem Login");
            stage.show();
        } catch (Exception exception) {

            exception.printStackTrace();
        }
    }

    @FXML
    private void handleWithdraw() {

        TextInputDialog dialog = new TextInputDialog();


        dialog.setTitle("Auszahlung");
        dialog.setHeaderText("Geld auszahlen");
        dialog.setContentText("Betrag in Euro:");

        Optional<String> result = dialog.showAndWait();


        if (result.isEmpty()) {
            return;
        }


        String input = result.get().trim();

        input = input.replace(",", ".");

        try {

            BigDecimal amount = new BigDecimal(input);


            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                showError("Der Betrag muss größer als 0 sein.");
                return;
            }


            if (amount.scale() > 2) {
                showError("Der Betrag darf maximal zwei Nachkommastellen haben.");
                return;
            }
            withdraw(amount);


        } catch (NumberFormatException exception) {
            showError("Bitte einen gültigen Betrag eingeben.");
        }
    }

    private void withdraw(BigDecimal amount) {


        try {

            String json = """
                    {
                        "amount": %s
                    }
                    """.formatted(amount.toPlainString());


            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(
                            "http://localhost:8080/api/accounts/user/"
                                    + userId
                                    + "/withdraw"))
                    .header("Content-Type",
                            "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                AccountResponse account = objectMapper.readValue(response.body(), AccountResponse.class);
                updateAccountView(account);
                loadTransactions();
                showInfo("Auszahlung erfolgreich.");
                return;
            }


            showError(response.body());


        } catch (Exception exception) {

            exception.printStackTrace();


            showError("Auszahlung konnte nicht durchgeführt werden.");
        }
    }


    @FXML
    private void handleTransfer() {
        TextInputDialog accountDialog = new TextInputDialog();
        accountDialog.setTitle("Überweisung");
        accountDialog.setHeaderText("Empfänger");
        accountDialog.setContentText("Kontonummer des Empfängers:");

        Optional<String> accountResult = accountDialog.showAndWait();
        if (accountResult.isEmpty()) {
            return;
        }

        String receiverAccountNumber = accountResult.get().trim();
        if (receiverAccountNumber.isBlank()) {
            showError("Bitte eine Kontonummer eingeben.");
            return;
        }

        TextInputDialog amountDialog = new TextInputDialog();
        amountDialog.setTitle("Überweisung");
        amountDialog.setHeaderText("Überweisungsbetrag");
        amountDialog.setContentText("Betrag in Euro:");

        Optional<String> amountResult = amountDialog.showAndWait();
        if (amountResult.isEmpty()) {
            return;
        }

        String input = amountResult.get().trim().replace(",", ".");
        try {
            BigDecimal amount = new BigDecimal(input);

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                showError("Der Betrag muss größer als 0 sein.");
                return;
            }

            if (amount.scale() > 2) {
                showError("Der Betrag darf maximal zwei Nachkommastellen haben.");
                return;
            }

            transfer(receiverAccountNumber, amount);

        } catch (NumberFormatException exception) {
            showError("Bitte einen gültigen Betrag eingeben.");
        }
    }

    private void transfer(String receiverAccountNumber, BigDecimal amount) {

        try {

            String json = """
                    {
                        "receiverAccountNumber": "%s",
                        "amount": %s
                    }
                    """.formatted(receiverAccountNumber, amount.toPlainString());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/accounts/user/" + userId + "/transfer"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                AccountResponse account = objectMapper.readValue(response.body(), AccountResponse.class);
                updateAccountView(account);
                loadTransactions();
                showInfo("Überweisung erfolgreich.");
                return;
            }

            showError(response.body());

        } catch (Exception exception) {
            exception.printStackTrace();
            showError("Überweisung konnte nicht durchgeführt werden.");
        }
    }


    private void loadTransactions() {

        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/accounts/user/"
                            + userId
                            + "/transactions")).GET().build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());


            if (response.statusCode() != 200) {
                showError(response.body());
                return;
            }

            TransactionResponse[] transactions = objectMapper.readValue(response.body(), TransactionResponse[].class);
            transactionsListView.getItems().clear();

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.GERMANY);


            for (TransactionResponse transaction : transactions) {
                String symbol = "";
                String typeText = "";
                switch (transaction.getType()) {
                    case "DEPOSIT" -> {
                        symbol = "+";
                        typeText = "Einzahlung";
                    }

                    case "WITHDRAWAL" -> {
                        symbol = "-";
                        typeText = "Auszahlung";
                    }

                    case "TRANSFER_IN" -> {
                        symbol = "+";
                        typeText = "Überweisung erhalten";
                    }

                    case "TRANSFER_OUT" -> {
                        symbol = "-";
                        typeText = "Überweisung gesendet";
                    }

                    default -> {
                        typeText = transaction.getType();
                    }
                }


                String line =
                        typeText
                        + "    "
                        + symbol
                        + currencyFormat.format(transaction.getAmount())
                        + "    "
                        + transaction.getCreatedAt().format(dateFormatter)
                        + "    Kontostand: "
                        + currencyFormat.format(transaction.getBalanceAfter())
                        + "    ID: "
                        + transaction.getId();

                transactionsListView.getItems().add(line);
            }


        } catch (Exception exception) {
            exception.printStackTrace();
            showError("Transaktionen konnten nicht geladen werden.");
        }
    }
}