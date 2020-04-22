package com.project;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Registration implements Initializable {
    @FXML
    private ChoiceBox<String> resQuestion;
    @FXML
    private TextField resAnswer;
    @FXML
    private TextField resQuestion2;
    @FXML
    private TextField resUsername;
    @FXML
    private PasswordField resPassword;
    @FXML
    private PasswordField resPassword2;
    @FXML
    private TextField resEmail;
    @FXML
    private DatePicker resDate;
    @FXML
    private CheckBox resCheck;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resQuestion.getItems().setAll("What was your childhood's pet?", "What was the name of your first love?", "Custom question");
        resQuestion.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        switch (newValue){
                            case "Custom question":
                                resQuestion.setVisible(false);
                                resQuestion2.setVisible(true);
                                resQuestion2.setFocusTraversable(true);
                            break;
                            default:
                                resAnswer.setFocusTraversable(true);
                        }
                    }
                }
        );
    }
    @FXML
    private void registerAction(){

    }
}
