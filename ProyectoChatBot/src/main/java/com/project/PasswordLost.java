package com.project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PasswordLost extends Screen{
    @FXML
    private TextField emailField;
    @FXML
    private TextField answerField;
    @FXML
    private TextField passField;
    @FXML
    private Label labelQuestion;

    public PasswordLost(Stage stage) {
        super(stage, "passwordLost.fxml", "Remember Password", false);
    }

    @FXML
    private void verifyEmail(){

    }

    @FXML
    private void newPass(){

    }

}
