package com.project;

import com.project.VisualVariables.VisualVariables;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;

public class Settings extends Screen{
    @FXML
    private Slider sliderText;
    @FXML
    private ColorPicker colorPickerTextApp;
    @FXML
    private ChoiceBox choiceFamily;
    @FXML
    private ColorPicker colorPickerSender;
    @FXML
    private ColorPicker colorPickerReceiver;

    public Settings() {
        super(new Stage(), "setting.fxml", "Settings", true);
        sliderText.setValue(VisualVariables.messageTextSize);
        colorPickerTextApp.setValue(VisualVariables.messageTextColor);
        colorPickerSender.setValue(VisualVariables.DEFAULT_SENDER_COLOR);
        colorPickerReceiver.setValue(VisualVariables.DEFAULT_RECEIVER_COLOR);
        choiceFamily.getItems().setAll(Font.getFamilies());
        choiceFamily.getSelectionModel().select(VisualVariables.messageTextFont.getName());
    }

    @FXML
    private void changeSetting(){
        VisualVariables.messageTextSize = sliderText.getValue();
        VisualVariables.messageTextColor = colorPickerTextApp.getValue();
        VisualVariables.messageTextFont = new Font(choiceFamily.getValue().toString(),VisualVariables.messageTextSize);
        VisualVariables.DEFAULT_SENDER_COLOR = colorPickerSender.getValue();
        VisualVariables.DEFAULT_RECEIVER_COLOR = colorPickerReceiver.getValue();
        System.out.println(sliderText.getValue()+"\n"+
                colorPickerTextApp.getValue()+"\n"+
                choiceFamily.getValue().toString()+"\n" +
                colorPickerReceiver);
        try(BufferedWriter br = new BufferedWriter(new FileWriter("visual.txt"))) {

            br.write(sliderText.getValue()+"");
            br.newLine();
            br.write(VisualVariables.convertToHex(colorPickerTextApp));
            br.newLine();
            br.write(choiceFamily.getValue().toString());
            br.newLine();
            br.write(VisualVariables.convertToHex(colorPickerSender));
            br.newLine();
            br.write(VisualVariables.convertToHex(colorPickerReceiver));
            br.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
