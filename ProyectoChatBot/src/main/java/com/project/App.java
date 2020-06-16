package com.project;

import com.project.VisualVariables.VisualVariables;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    protected static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {



        Login login = new Login();
        login.showStage();
    }

    public static void main(String[] args) {
        try(BufferedReader br = new BufferedReader(new FileReader("visual.txt"))){
            VisualVariables.messageTextSize = Double.parseDouble(br.readLine());
            VisualVariables.messageTextColor = Color.web(br.readLine());
            VisualVariables.messageTextFont = new Font(br.readLine(),VisualVariables.messageTextSize);
            VisualVariables.DEFAULT_SENDER_COLOR = Color.web(br.readLine());
            VisualVariables.DEFAULT_RECEIVER_COLOR = Color.web(br.readLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        launch();
    }

}