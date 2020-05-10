package com.project;

import com.project.classes.UserDao;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Chats extends Screen{
    public Chats(UserDao user, Stage stage){
        super(stage, user, "chats.fxml", "Conversations", true);
    }
}
