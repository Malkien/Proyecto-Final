package com.project;

import com.project.classes.User;
import com.project.classes.UserDao;
import com.project.exceptions.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * REGISTRATION CLASS
 */
public class Registration extends Screen implements Initializable {
    /**
     * the choiceBox of the question
     */
    @FXML
    private ChoiceBox<String> resQuestion;
    /**
     * The textfield to the answer
     */
    @FXML
    private TextField resAnswer;
    /**
     * The textfield to the question
     */
    @FXML
    private TextField resQuestion2;
    /**
     * The textfield to the usermane
     */
    @FXML
    private TextField resUsername;
    /**
     * The passwordFiel to the password
     */
    @FXML
    private PasswordField resPassword;
    /**
     * The passwordField to verify the password
     */
    @FXML
    private PasswordField resPassword2;
    /**
     * The textfield to the email
     */
    @FXML
    private TextField resEmail;
    /**
     * the DatePicker to the birthday
     */
    @FXML
    private DatePicker resDate;
    /**
     * The checkBox to accept the terms
     */
    @FXML
    private CheckBox resCheck;

    /**
     * Override the initialize of the screen
     * @param location the url location
     * @param resources the resources
     */
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

    /**
     * The constuctor od the class
     * @param stage the stage
     */
    public Registration(Stage stage){
        super(stage, "registration.fxml", "Registration", false);
    }

    /**
     * The click of the register button to registrate the new user in the bbdd
     */
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
            if(!User.checkPassword(resPassword.getText())){
                throw new PasswordInvalidException();
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
                goBack();
            }else{
                throw new PasswordNotMatchException();
            }
        } catch (PasswordNotMatchException e) {
            errorTextField(resPassword2,"Password don't match");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (EmailInvalidException e) {
            errorTextField(resEmail,"Invalid email");
        } catch (UsernameLengthException e) {
            errorTextField(resUsername,"Length between 5-20");
        } catch (PasswordInvalidException e) {
           errorTextField(resPassword, "Invalid password");
        } catch (LengthAnswerException e) {
            errorTextField(resAnswer,"Length between 5-100");
        } catch (LengthQuestionException e) {
            errorTextField(resQuestion2, "Length between 5-100");
        } catch (UnderAgeException e) {
            resDate.setValue(null);
            resDate.setPromptText("Age under 18");
            resDate.setStyle("-fx-prompt-text-fill: red;");
        } catch (EmailUsedException e) {
            errorTextField(resEmail,"This email is in use");
        } catch (AcceptRulesException e) {
            resCheck.setTextFill(Color.RED);
        } catch (BirthdayNullException e) {
            resDate.setValue(null);
            resDate.setPromptText("Date invalid");
            resDate.getEditor().setBackground(new Background(new BackgroundFill(Color.DARKRED,null,null)));
        }
    }

    /**
     * To write a prompt text in the field with red color
     * @param field the TextField
     * @param text the text
     */
    private void errorTextField(TextField field, String text){
        field.clear();
        field.setPromptText(text);
        field.setStyle("-fx-prompt-text-fill: red;");
    }

    /**
     * Go to the Login Screen
     */
    @FXML
    private void goBack(){
        Login login = new Login(getStage());
    }
}
