package com.banking.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Startklasse der JavaFX Benutzeroberfläche
public class BankingClientApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // Lädt die erste FXML Oberfläche
        FXMLLoader loader = new FXMLLoader(
                BankingClientApplication.class.getResource("/views/Login.fxml")
        );

        // Erstellt die Szene aus der FXML Datei
        Scene scene = new Scene(loader.load());

        // Konfiguriert und öffnet das Fenster
        stage.setTitle("Banksystem");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        // Startet JavaFX
        launch(args);
    }
}