package com.banking.frontend.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {

    private static Stage stage;

    public static void setStage(Stage stage) {
        SceneController.stage = stage;
    }

    protected void loadScene(String fxmlFile, Object controller) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));

            loader.setController(controller);

            Scene scene = new Scene(loader.load());

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException("FXML konnte nicht geladen werden: " + fxmlFile, e);
        }
    }
}