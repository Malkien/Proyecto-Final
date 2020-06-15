package com.project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import static com.project.VisualVariables.VisualVariables.DEFAULT_RECEIVER_COLOR;
import static com.project.VisualVariables.VisualVariables.DEFAULT_SENDER_COLOR;

/**
 * ENUM IF IS LEFT OR RIGHT
 */
enum SpeechDirection{
    LEFT, RIGHT
}

/**
 * SPEECHBOX CLASS
 */
public class SpeechBox extends HBox {
    /**
     * The background of the sender and the receiver
     */
    private Background DEFAULT_SENDER_BACKGROUND, DEFAULT_RECEIVER_BACKGROUND;
    /**
     * The message
     */
    private String message;
    /**
     * The direction
     */
    private SpeechDirection direction;
    /**
     * The label
     */
    private Label displayedText;
    /**
     * the indicator direction
     */
    private SVGPath directionIndicator;

    /**
     * CONSTRUCTOR OF THE CLASS
     * @param message the message
     * @param direction the direction
     */
    public SpeechBox(String message, SpeechDirection direction){
        this.message = message;
        this.direction = direction;
        initialiseDefaults();
        setupElements();
    }

    /**
     * The initialization
     */
    private void initialiseDefaults(){
        DEFAULT_SENDER_BACKGROUND = new Background(
                new BackgroundFill(DEFAULT_SENDER_COLOR, new CornerRadii(5,0,5,5,false), Insets.EMPTY));
        DEFAULT_RECEIVER_BACKGROUND = new Background(
                new BackgroundFill(DEFAULT_RECEIVER_COLOR, new CornerRadii(0,5,5,5,false), Insets.EMPTY));
    }

    /**
     * Setup the elements
     */
    private void setupElements(){
        displayedText = new Label(message);
        displayedText.setPadding(new Insets(5));
        displayedText.setWrapText(true);
        directionIndicator = new SVGPath();

        if(direction == SpeechDirection.LEFT){
            configureForReceiver();
        }
        else{
            configureForSender();
        }
    }

    /**
     * Configuration of the sender
     */
    private void configureForSender(){
        displayedText.setBackground(DEFAULT_SENDER_BACKGROUND);
        displayedText.setAlignment(Pos.CENTER_RIGHT);
        directionIndicator.setContent("M10 0 L0 10 L0 0 Z");
        directionIndicator.setFill(DEFAULT_SENDER_COLOR);

        HBox container = new HBox(displayedText, directionIndicator);
        //Use at most 75% of the width provided to the SpeechBox for displaying the message
        container.maxWidthProperty().bind(widthProperty().multiply(0.75));
        getChildren().setAll(container);
        setAlignment(Pos.CENTER_RIGHT);
    }

    /**
     * Configuration os the receiver
     */
    private void configureForReceiver(){
        displayedText.setBackground(DEFAULT_RECEIVER_BACKGROUND);
        displayedText.setAlignment(Pos.CENTER_LEFT);
        directionIndicator.setContent("M0 0 L10 0 L10 10 Z");
        directionIndicator.setFill(DEFAULT_RECEIVER_COLOR);

        HBox container = new HBox(directionIndicator, displayedText);
        //Use at most 75% of the width provided to the SpeechBox for displaying the message
        container.maxWidthProperty().bind(widthProperty().multiply(0.75));
        getChildren().setAll(container);
        setAlignment(Pos.CENTER_LEFT);
    }
}
