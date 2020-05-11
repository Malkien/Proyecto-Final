package com.project;

import com.project.classes.IbmAssistant;
import com.project.classes.UserDao;
import com.project.database.DataBaseUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Chats extends Screen{
    private ObservableList<IbmAssistant> apis = FXCollections.<IbmAssistant>observableArrayList();
    @FXML
    private ListView<IbmAssistant> listChats;

    public Chats(UserDao user, Stage stage){
        super(stage, user, "chats.fxml", "Conversations", true);
        try(Connection connection = DataBaseUtils.createConnection()){
            Statement statement = connection.createStatement();
            ResultSet baseChats = statement.executeQuery("SELECT name, aes_decrypt(apiKey,'"+user.getAnswer()+"') as apiKey, aes_decrypt(apiId,'"+user.getAnswer()+"') as apiId FROM api WHERE email = '"+user.getEmail()+"'");
            while(baseChats.next()){
                apis.add(
                        new IbmAssistant(
                                baseChats.getString("name"),
                                baseChats.getString("apiKey"),
                                baseChats.getString("apiId")
                        )
                );
            }
            baseChats.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listChats.setItems(apis);
        listChats.setCellFactory(param -> new ListCell<IbmAssistant>() {
            @Override
            protected void updateItem(IbmAssistant item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
        listChats.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IbmAssistant>() {
            @Override
            public void changed(ObservableValue<? extends IbmAssistant> observable, IbmAssistant oldValue, IbmAssistant newValue) {
                System.out.println("has pulsado "+newValue.getName());
            }
        });
    }
}
