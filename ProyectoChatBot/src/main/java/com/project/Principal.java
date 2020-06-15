package com.project;

import java.io.IOException;

import com.project.classes.UserDao;
import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * PRINCIPAL SCREEN
 */
public class Principal extends ScreenWithMenuBar{
    /**
     * CONSTRUCTOR
     * @param user THE USER LOGGED
     */
    public Principal(UserDao user){
        super(new Stage(), user, "principal.fxml", "Principal", true);
    }

    /**
     * METHOD ON CLICK TO LAUNCH THE CHATS SCREEN
     * @throws IOException
     */
    @FXML
    private void switchToChats() throws IOException {
        Chats chats = new Chats(getUser(), getStage());
    }

    /**
     * Not in use
     * @throws IOException
     */
    @FXML
    private void switchToNewApi() throws IOException{

    }

}