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

    @FXML
    private void buttonPrincipalMenu(){
        if(!this.getClass().getName().equals("com.project.Principal")){
            getStage().close();
            Principal principal = new Principal(this.getUser());
        }
    }

    @FXML
    private void buttonChatsMenu(){
        if(!this.getClass().getName().equals("com.project.Chats")){
            getStage().close();
            Chats chats = new Chats(getUser(), getStage());
        }

    }

    /**
     * METHOD ON CLICK TO THE MENUBAR TO TURN OFF THE APPLICATION
     */
    @FXML
    private void buttonCloseMenu(){
        System.exit(0);
    }

    @FXML
    private void buttonSettingMenu(){

    }

}
