package com.project;

import com.project.classes.UserDao;
import com.project.exceptions.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
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
        resQuestion.getSelectionModel().selectFirst();
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
        String question = "";
        if(resQuestion.isVisible()){
            question = resQuestion.getValue();
        }else{
            question = resQuestion2.getText();
        }

        try{
            if(!resCheck.isSelected()){
                throw new AcceptRulesException();
            }
            if(resPassword.getText().equalsIgnoreCase(resPassword2.getText())){
                UserDao userDao = new UserDao(
                        resUsername.getText(),
                        resPassword.getText(),
                        resEmail.getText(),
                        resDate.getValue(),
                        question,
                        resAnswer.getText()
                );
            }else{
                throw new PasswordNotMatchException();
            }
        } catch (PasswordNotMatchException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (EmailInvalidException e) {
            e.printStackTrace();
        } catch (UsernameLengthException e) {
            e.printStackTrace();
        } catch (PasswordInvalidException e) {
            e.printStackTrace();
        } catch (LengthAnswerException e) {
            e.printStackTrace();
        } catch (LengthQuestionException e) {
            e.printStackTrace();
        } catch (UnderAgeException e) {
            e.printStackTrace();
        } catch (EmailUsedException e) {
            e.printStackTrace();
        } catch (AcceptRulesException e) {
            e.printStackTrace();
        }
    }
}
