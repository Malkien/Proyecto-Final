package com.project;

import java.io.IOException;

import com.project.classes.UserDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Principal extends Screen{
    public Principal(UserDao user){
        super(new Stage(), user, "principal.fxml", "Principal", true);
    }

    @FXML
    private void switchToChats() throws IOException {
        Chats chats = new Chats(getUser(), getStage());
    }
    @FXML
    private void switchToLogin() throws IOException {
        getStage().close();
        Login login = new Login();
    }
}