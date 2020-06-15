package com.project;

import com.project.classes.User;
import com.project.classes.UserDao;
import com.project.database.DataBaseUtils;
import com.project.exceptions.EmailUsedException;
import com.project.exceptions.UserNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * PASSWORDLOST CLASS
 */
public class PasswordLost extends Screen{
    /**
     * Save the answer
     */
    private String answerUser;
    /**
     * Save the last email
     */
    private String emailLast;
    /**
     * The textfield of the email
     */
    @FXML
    private TextField emailField;
    /**
     * The textfield of the answer
     */
    @FXML
    private TextField answerField;
    /**
     * The textfield of the password
     */
    @FXML
    private TextField passField;
    /**
     * The label with the question of the user
     */
    @FXML
    private Label labelQuestion;
    /**
     * The label with new password:
     */
    @FXML
    private Label labelNewPass;

    /**
     * CONSTRUCTOR OF THE CLASS
     * @param stage the stage
     */
    public PasswordLost(Stage stage) {
        super(stage, "passwordLost.fxml", "Remember Password", false);
    }

    /**
     * The click of the button to verify if the email exist in the bbdd
     */
    @FXML
    private void verifyEmail(){
        try( Connection connection = DataBaseUtils.createConnection() ) {
            Statement statement = connection.createStatement();
            ResultSet user = statement.executeQuery("SELECT question, answer FROM " + DataBaseUtils.nameTableUser + " WHERE email=\"" + emailField.getText() + "\"");
            if(user.next()){
                System.out.println("HOLAAAAAAAAAAAAAAAAAAAAA");
                labelQuestion.setText(user.getString("question"));
                answerUser = user.getString("answer");
                labelQuestion.setVisible(true);
                answerField.setVisible(true);
                passField.setVisible(true);
                labelNewPass.setVisible(true);
                emailLast = emailField.getText();
            }else{
                throw new UserNotFoundException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UserNotFoundException e) {
            errorField(emailField, "Insert other email");
        }
    }

    /**
     * THe click of the button to set a new password to the user found in the verify button
     */
    @FXML
    private void newPass(){
        if(answerUser.equalsIgnoreCase(answerField.getText())){
            if(User.checkPassword(passField.getText())){
                try(Connection connection = DataBaseUtils.createConnection()){
                    connection.createStatement().executeUpdate(
                            "UPDATE "+DataBaseUtils.nameTableUser+" SET password =AES_ENCRYPT('"+passField.getText()+"','"+answerUser+"') WHERE email='"+emailLast+"'"
                    );
                    goBack();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                errorField(passField,"Invalid Password");
            }
        }else{
            errorField(passField,"Incorrect Answer");
        }
    }
    private void errorField(TextField textField, String text){
        textField.clear();
        textField.setPromptText(text);
        textField.setStyle("-fx-prompt-text-fill: red;");
    }

    /**
     * Go to the login screen
     */
    @FXML
    private void goBack(){Login login = new Login(getStage());}

}
