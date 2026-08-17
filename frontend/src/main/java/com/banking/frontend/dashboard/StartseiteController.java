package com.banking.frontend.dashboard;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Controller für die Startseite des Banksystems.
public class StartseiteController {

    // Begrüßung des Benutzers.
    @FXML
    private Label greetingLabel;

    // Benutzername.
    @FXML
    private Label usernameLabel;

    // Rolle, zum Beispiel USER oder ADMIN.
    @FXML
    private Label roleLabel;

    // Aktueller Kontostand.
    @FXML
    private Label balanceLabel;

    // Kontonummer oder später IBAN.
    @FXML
    private Label accountNumberLabel;

    // Letzte Transaktionen.
    @FXML
    private ListView<String> transactionsListView;

    // Geld einzahlen.
    @FXML
    private Button depositButton;

    // Geld auszahlen.
    @FXML
    private Button withdrawButton;

    // Geld überweisen.
    @FXML
    private Button transferButton;

    // Benutzer abmelden.
    @FXML
    private Button logoutButton;


    // Wird automatisch ausgeführt, nachdem die FXML geladen wurde.
    @FXML
    private void initialize() {

        // Vorläufige Werte, bis wir die echten Daten vom Backend laden.
        greetingLabel.setText("Guten Tag");
        usernameLabel.setText("Benutzer wird geladen...");
        roleLabel.setText("Rolle wird geladen...");
        balanceLabel.setText("0,00 €");
        accountNumberLabel.setText("Kontonummer: ");
    }


    // Wird später nach erfolgreichem Login aufgerufen.
    public void setUserData(String username, String role, Long id) {
        greetingLabel.setText("Guten Tag, " + username);
        usernameLabel.setText("Benutzer: " + username);
        roleLabel.setText("Rolle: " + role);
        accountNumberLabel.setText("Kontonummer: " + id);
    }

    // Meldet den Benutzer ab und öffnet wieder die Login Seite.
    @FXML
    private void handleLogout() {

        try {

            // Login.fxml laden.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
            Parent root = loader.load();

            // Das aktuelle Fenster holen.
            Stage stage = (Stage) logoutButton.getScene().getWindow();


            // Startseite durch Login ersetzen.
            stage.setScene(new Scene(root));
            stage.setTitle("Banksystem Login");
            stage.show();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}