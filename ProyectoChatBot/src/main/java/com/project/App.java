package com.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    protected static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Login login = new Login();
        login.showStage();
    }

    public static void main(String[] args) {
        launch();
    }

}