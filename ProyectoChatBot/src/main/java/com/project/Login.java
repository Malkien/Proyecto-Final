package com.project;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import com.project.classes.User;
import com.project.classes.UserDao;
import com.project.classes.VariableDebug;
import com.project.database.DataBaseUtils;
import com.project.exceptions.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Login Screen
 */
public class Login extends Screen{
    /**
     * TexField from the email
     */
    @FXML
    private TextField textUser;
    /**
     * PasswordField for the password
     */
    @FXML
    private PasswordField textPassword;

    /**
     * Constructor to the class
     */
    public Login(){
        super(new Stage(), "login.fxml", "Login", false);

    }

    /**
     * METHOD ON CLICK TO VERIFY IF THE EMAIL AND THE PASSWORD MATCH TO A USER IN THE DDBB
     * @throws IOException
     */
    @FXML
    private void verifyUserPass() throws IOException {
        try {
            UserDao user = null;
            if(VariableDebug.DEBUG){
                user = new UserDao("kevin@kevin.com","Kicsnet9*");
            }else{
                user = new UserDao(textUser.getText(),textPassword.getText());
            }
            //UserDao user = new UserDao(textUser.getText(),textPassword.getText());
            getStage().close();
            Principal principal = new Principal(user);
        }catch (UserLoginException e){
            var alertDialog = new Alert(Alert.AlertType.ERROR);
            alertDialog.setTitle("Login Error");
            alertDialog.setHeaderText(e.getMessage());
            alertDialog.setContentText("Confirm than your email or passwort is correct");
            ((Stage)alertDialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image((getClass().getResourceAsStream("images/iconError.png"))));//Set icon to the dialog
            ImageView image = new ImageView( new Image(getClass().getResourceAsStream("images/errorDialog.png")));
            image.setFitHeight(50);
            image.setFitWidth(50);
            alertDialog.setGraphic(image);
            ((Button)alertDialog.getDialogPane().lookupButton(ButtonType.OK)).setText("OK");
            Thread thread1 = new Thread("alertThread"){
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            alertDialog.show();
                        }
                    });

                }
            };
            thread1.start();

        }
    }

    /**
     * METHOD TO LAUNCH THE REMEMBER PASSWORD SCREEN - IN PRODUCTION -
     * @throws IOException
     */
    @FXML
    private void switchToRememberPass() throws IOException {
        PasswordLost passwordLost = new PasswordLost(getStage());
    }

    /**
     * METHOD ON CLICK TO LAUNCH THE REGISTER SCREEN
     * @throws IOException
     */
    @FXML
    private void switchToRegistration() throws  IOException {
        Registration registration = new Registration(getStage());
    }
}
