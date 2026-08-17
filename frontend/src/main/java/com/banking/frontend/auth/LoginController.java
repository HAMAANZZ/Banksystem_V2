package com.banking.frontend.auth;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label messageLabel;

    @FXML
    public void initialize() {
        loginButton.setOnAction(event -> login());
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Bitte Benutzername und Passwort eingeben.");
            return;
        }

        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText("Anmeldung erfolgreich.");
    }
}