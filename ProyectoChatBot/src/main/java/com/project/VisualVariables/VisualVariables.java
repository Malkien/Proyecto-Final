package com.project.VisualVariables;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * CLASS WITH VISUAL VARIABLES THAN THE USER CAN CHANGE
 */
public class VisualVariables {
    /**
     * VARIABLE FOR THE TEXTSIZE
     */
    public static int messageTextSize = 16;
    /**
     * VARIABLE FOR THE TEXTCOLOR
     */
    public static Color messageTextColor = Color.WHITE;
    /**
     * VARIABLE FOR THE TEXTFONT
     */
    public static Font messageTextFont = new Font("Arial", messageTextSize);

    public static Color DEFAULT_SENDER_COLOR = Color.web("667CE3");

    public static Color DEFAULT_RECEIVER_COLOR = Color.web("6BD6BB");
}