package com.project;

import com.project.classes.UserDao;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Abstract Class Screen
 */
public abstract class Screen {
    private Stage stage;
    private UserDao user;

    /**
     * Constructor without user
     * @param stage The stage
     * @param loaderResource the name of the fxml
     * @param title the title
     * @param resizable is resizable
     */
    public Screen(Stage stage, String loaderResource, String title, Boolean resizable){
        this.stage = stage;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(loaderResource));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            stage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            stage.setTitle(title);
            stage.setResizable(resizable);
            showStage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor with user
     * @param stage The stage
     * @param user The user
     * @param loaderResource the name of the fxml
     * @param title the title
     * @param resizable is resizable
     */
    public Screen(Stage stage, UserDao user, String loaderResource, String title, Boolean resizable){
        this.stage = stage;
        this.user = user;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(loaderResource));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            stage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            stage.setTitle(title);
            stage.setResizable(resizable);
            showStage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showStage() {
        stage.show();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public UserDao getUser() {
        return user;
    }

    public void setUser(UserDao user) {
        this.user = user;
    }
}
