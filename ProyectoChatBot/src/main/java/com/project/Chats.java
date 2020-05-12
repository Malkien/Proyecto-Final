package com.project;

import com.project.VisualVariables.VisualVariables;
import com.project.classes.IbmAssistant;
import com.project.classes.UserDao;
import com.project.database.DataBaseUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class Chats extends Screen{
    private ObservableList<IbmAssistant> apis = FXCollections.<IbmAssistant>observableArrayList();
    private IbmAssistant chatNow;
    @FXML
    private ListView<IbmAssistant> listChats;

    @FXML
    private TextArea textAnswer;

    @FXML
    private VBox containerMessages;

    public Chats(UserDao user, Stage stage){
        super(stage, user, "chats.fxml", "Conversations", true);
        try(Connection connection = DataBaseUtils.createConnection()){
            Statement statement = connection.createStatement();
            ResultSet baseChats = statement.executeQuery(
                    "SELECT name, CAST(AES_DECRYPT(apiKey,'"+user.getAnswer()+"') AS CHAR) AS apiKey, CAST(AES_DECRYPT(apiId,'"+user.getAnswer()+"') AS CHAR) AS apiId " +
                            "FROM api WHERE email = '"+user.getEmail()+"'"
            );
            System.out.println(user.getAnswer());
            while(baseChats.next()){
                apis.add(
                        new IbmAssistant(
                                baseChats.getString("name"),
                                baseChats.getString("apiKey"),
                                baseChats.getString("apiId")
                        )
                );


            }
            for (IbmAssistant i: apis) {
                System.out.println("_---------------------------------------------------------------------------------------");
                System.out.println(String.valueOf(LocalDate.now()));
                System.out.println("_---------------------------------------------------------------------------------------");
            }
            baseChats.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listChats.setItems(apis);
        listChats.setCellFactory(param -> new ListCell<IbmAssistant>() {
            /**
             * METHOD TO THE VISUALIZATION OF THE ITEMS IN THE LISTVIEW
             * @param item THE ITEM
             * @param empty IF IS EMPTY
             */
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
            /**
             * METHOD FOR WHEN THE SELECTED ITEM CHANGE IN THE LISTVIEW
             * @param observable THE OBSERVABLE VALUE
             * @param oldValue THE LAST ITEM SELECTED
             * @param newValue THE NEW ITEM SELECTED
             */
            @Override
            public void changed(ObservableValue<? extends IbmAssistant> observable, IbmAssistant oldValue, IbmAssistant newValue) {
                //if(oldValue.isSessionOpen())
                //    oldValue.deleteSession();
                chatNow = newValue;
                //chatNow.createSession();
            }
        });
        chatNow = listChats.getItems().get(0);

    }

    @FXML
    private void callToTheApi(){
        System.out.println("_---------------------------------------------------------------------------------------");
        System.out.println(chatNow.getApiKey());
        System.out.println(chatNow.getUrl());
        System.out.println("_---------------------------------------------------------------------------------------");
        chatNow.createSession();
        putMessage(textAnswer.getText(),true);
        putMessage(chatNow.chatUp(textAnswer.getText()),false);
        chatNow.deleteSession();
    }
    private void putMessage(String text, Boolean send){
        HBox container = new HBox();
        if(!send){
            container.setAlignment(Pos.TOP_LEFT);
        }else{
            container.setAlignment(Pos.TOP_RIGHT);
        }
        container.setMaxHeight(Region.USE_COMPUTED_SIZE);
        container.setMaxWidth(Double.MAX_VALUE);
        container.setBackground(new Background(new BackgroundFill(Color.PURPLE,null,null)));

        Label message = new Label();
        message.setText(text);
        message.setWrapText(true);
        message.setFont(VisualVariables.messageTextFont);
        message.setTextFill(VisualVariables.messageTextColor);
        container.getChildren().add(message);
        containerMessages.getChildren().add(message);
    }

}

