package com.banking.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BankingClientApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(BankingClientApplication.class.getResource("/views/Login.fxml"));

        Scene scene = new Scene(loader.load());

        stage.setTitle("Banksystem");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}