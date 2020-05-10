package com.project;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import com.project.classes.User;
import com.project.classes.UserDao;
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

public class Login extends Screen{
    @FXML
    private TextField textUser;
    @FXML
    private PasswordField textPassword;

    public Login(){
        super(new Stage(), "login.fxml", "Login", false);
        //getStage().initStyle(StageStyle.DECORATED);
        getStage().getIcons().add(new Image(this.getClass().getResourceAsStream("images/icon.png")));
        getStage().sizeToScene();
    }

    @FXML
    private void verifyUserPass() throws IOException {
        try {
            UserDao user = new UserDao(textUser.getText(),textPassword.getText());
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
    @FXML
    private void switchToRememberPass() throws IOException {
        //RememberPass HERE
    }
    @FXML
    private void switchToRegistration() throws  IOException {
        Registration registration = new Registration(getStage());
    }
}
