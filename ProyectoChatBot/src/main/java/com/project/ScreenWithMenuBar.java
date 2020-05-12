package com.project;

import com.project.classes.UserDao;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

public class ScreenWithMenuBar extends Screen{

    public ScreenWithMenuBar(Stage stage, UserDao user, String loaderResource, String title, Boolean resizable) {
        super(stage, user, loaderResource, title, resizable);
    }

    /**
     * METHOD ON CLICK TO THE MENUBAR TO SIGN OUT
     * @throws IOException
     */
    @FXML
    private void signOutMenuItem(){
        getStage().close();
        Login login = new Login();
    }

    /**
     * METHOD ON CLICK TO THE MAENUBAR TO TURN OFF THE APPLICATION
     */
    @FXML
    private void closeMenuItem(){
        System.exit(0);
    }
}
