package com.project;

import com.ibm.cloud.sdk.core.service.exception.BadRequestException;
import com.project.VisualVariables.VisualVariables;
import com.project.classes.IbmAssistant;
import com.project.classes.UserDao;
import com.project.database.DataBaseUtils;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * CHATS SCREEN
 */
public class Chats extends ScreenWithMenuBar{
    /**
     * LIST WITH THE IBM ASSISTANT
     */
    private ObservableList<IbmAssistant> apis = FXCollections.<IbmAssistant>observableArrayList();
    /**
     * THE IBM ASSISTANT IN USE
     */
    private IbmAssistant chatNow;
    /**
     * THE LISTVIEW WITH THE IBM ASSISTANT
     */
    @FXML
    private ListView<IbmAssistant> listChats;
    /**
     * THE TEXTAREA
     */
    @FXML
    private TextArea textAnswer;
    /**
     * THE CONTAINER OF THE MENSAJES
     */
    @FXML
    private ScrollPane containerMessages;
    /**
     * THE CONTAINER TO THE HEADER CHAT
     */
    @FXML
    private HBox chatHeader;
    /**
     * LABEL WITH THE TITLE OF THE HEADER CHAT
     */
    private Label contactHeader;
    /**
     * CONTAINER FOR THE MESSAGES
     */
    private VBox messageContainer;
    /**
     * LIST TO THE MESSAGES IN THE MESSAGECONTAINER
     */
    private ObservableList<Node> speechBubbles = FXCollections.observableArrayList();

    public Chats(UserDao user, Stage stage){
        super(stage, user, "chats.fxml", "Conversations", true);
        try(Connection connection = DataBaseUtils.createConnection()){
            Statement statement = connection.createStatement();
            ResultSet baseChats = statement.executeQuery(
                    "SELECT name, assistantId, CAST(AES_DECRYPT(apiKey,'"+user.getAnswer()+"') AS CHAR) AS apiKey, CAST(AES_DECRYPT(apiId,'"+user.getAnswer()+"') AS CHAR) AS apiId " +
                            "FROM api WHERE email = '"+user.getEmail()+"'"
            );
            while(baseChats.next()){
                apis.add(
                        new IbmAssistant(
                                baseChats.getString("name"),
                                baseChats.getString("apiKey"),
                                baseChats.getString("apiId"),
                                baseChats.getString("assistantId")
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
                try{
                    if(oldValue.isSessionOpen())
                        oldValue.deleteSession();
                } catch (NullPointerException e) {
                    //error no encuentro sentido solo se dispara la primera vez que se pulsa
                }
                chatNow = newValue;
                setupContactHeader();
                chatNow.createSession();
            }
        });
        chatNow = listChats.getItems().get(0);
        setupContactHeader();
        setupMessageDisplay();
        chatHeader.getChildren().add(contactHeader);
        chatNow.createSession();

        textAnswer.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            /**
             * Even Filter for the textarea
             * @param event the keyEvent
             */
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    callToTheApi();
                    event.consume();
                }
            }
        });
    }

    private void setupContactHeader(){
        contactHeader = new Label(chatNow.getName());
        contactHeader.setAlignment(Pos.CENTER);
        contactHeader.setFont(VisualVariables.messageTextFont);
        contactHeader.setTextFill(VisualVariables.messageTextColor);
    }




    /**
     * ONCLICK FOR SEND THE MESSAGE
     */
    @FXML
    private void callToTheApi(){
        speechBubbles.add(new SpeechBox(textAnswer.getText(), SpeechDirection.RIGHT));
        try{
            speechBubbles.add(new SpeechBox(chatNow.chatUp(textAnswer.getText()), SpeechDirection.LEFT));
        }catch (BadRequestException ex){

        }
        textAnswer.clear();
    }

    /**
     *
     */
    private void setupMessageDisplay(){
        messageContainer = new VBox(5);
        Bindings.bindContentBidirectional(speechBubbles, messageContainer.getChildren());

        containerMessages.setContent(messageContainer);
        containerMessages.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        containerMessages.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        //Make the scroller scroll to the bottom when a new message is added
        speechBubbles.addListener((ListChangeListener<Node>) change -> {
            while (change.next()) {
                if(change.wasAdded()){
                    containerMessages.setVvalue(containerMessages.getVmax());
                }
            }
        });
    }


}
