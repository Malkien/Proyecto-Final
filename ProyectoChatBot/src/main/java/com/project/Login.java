package com.project;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class Login {

    @FXML
    private void verifyUserPass() throws IOException {
        String name = ((TextField) App.scene.lookup("#textUser")).getText();
        String password = ((TextField) App.scene.lookup("#textPassword")).getText();
        if(name.equalsIgnoreCase("kevin") && name.equalsIgnoreCase("kevin")) {
            App.setRoot("secondary");
        }else {
            new Alert(Alert.AlertType.ERROR).showAndWait();
        }
    }
    @FXML
    private void switchToRememberPass() throws IOException {
        //RememberPass HERE
    }
    @FXML
    private void switchToRegistration() throws  IOException {
        App.setRoot("registration");
    }
}
