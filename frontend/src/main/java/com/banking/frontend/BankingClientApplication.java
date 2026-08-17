package com.banking.frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class BankingClientApplication extends Application {

    @Override
    public void start(Stage stage) {

        Label label = new Label("Banksystem");

        Scene scene = new Scene(label, 1000, 700);

        stage.setTitle("Banksystem");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}