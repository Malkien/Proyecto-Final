package com.project;

import java.io.IOException;

import com.project.classes.UserDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SecondaryController {
    private Stage stage;
    private UserDao user;

    public SecondaryController(UserDao user){
        this.user = user;
        stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("secondary.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            stage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            stage.setTitle("Secondary");
            stage.setResizable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showStage() {
        stage.showAndWait();
    }
    @FXML
    private void switchToPrimary() throws IOException {

    }
}