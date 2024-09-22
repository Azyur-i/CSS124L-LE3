package com.groupfour;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class SecondaryController{

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private static TextArea textArea;

    @FXML
    private static void initialize(){
        textArea.appendText("aaaaaa");
    }
}