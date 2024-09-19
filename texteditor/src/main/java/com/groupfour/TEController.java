package com.groupfour;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.Optional;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import java.io.*;

public class TEController {

    @FXML ComboBox<String> drpdFamily;
    @FXML ComboBox<String> drpdColor;
    @FXML ComboBox<String> drpdSize;
    @FXML ButtonType defFamily;
    @FXML ButtonType defColor;
    @FXML ButtonType defSize;
    @FXML TextArea textArea;

    private boolean isModified = false;

    @FXML
    
    public void initialize() {
        drpdFamily.getItems().setAll("Arial", "Times New Roman", "Comic Sans");
        drpdColor.getItems().setAll("Red", "Green", "Blue");
        drpdSize.getItems().setAll("5", "10", "15");
        textArea.textProperty().addListener((observable, oldValue, newValue) -> isModified = true);
    }

    public boolean getChanges() {
        return isModified; 
    }
    
    @FXML
    private void handledrpdFamily() {
        String fontFamily = drpdFamily.getValue();
        switch(fontFamily) {
            case "Arial": 
                textArea.setFont(Font.font("Arial")); break;
            case "Times New Roman":
                textArea.setFont(Font.font("Times New Roman")); break;
            case "Comic Sans": 
                textArea.setFont(Font.font("Comic Sans MS")); break;
        }
    }

    @FXML
    private void handledefFamily() {
        textArea.setFont(Font.getDefault());
    }

    @FXML
    private void handledefColor() {
        textArea.setStyle("-fx-text-fill: black;");
    }

    @FXML
    private void handledefSize() {
        textArea.setFont(Font.font(11.5));
    }

    @FXML
    private void handledrpdColor() {
        String fontColor = drpdColor.getValue();
        switch(fontColor) {
            case "Red": 
                textArea.setStyle("-fx-text-fill: red;"); break;
            case "Green":
                textArea.setStyle("-fx-text-fill: green;"); break;
            case "Blue": 
                textArea.setStyle("-fx-text-fill: blue;"); break;
        }
    }

    @FXML
    private void handledrpdSize() {
        String fontSize = drpdSize.getValue();
        switch(fontSize) {
            case "5": 
                textArea.setFont(Font.font(5)); break;
            case "10":
                textArea.setFont(Font.font(10)); break;
            case "15": 
                textArea.setFont(Font.font(15)); break;
        }
    }

    @FXML
    private void handlebtnSave() throws IOException {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Saved Successfully");
        alert.setHeaderText("Your work has been saved");
        alert.setContentText("You may now continue working");
        alert.showAndWait();
        FileWriter myWriter = new FileWriter("TextFile.txt");
        myWriter.write(textArea.getText());
        myWriter.close();
        isModified = false;
    }

    // I have no idea how to save the changes in font stuff

    @FXML
    private void handlebtnExit() throws IOException {

        if (getChanges()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("You have unsaved changes");
            alert.setContentText("Would you like to save your changes?");
            ButtonType buttonYes = new ButtonType("Confirm");
            ButtonType buttonNo = new ButtonType("Discard");
            
            alert.getButtonTypes().setAll(buttonYes, buttonNo);
    
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.isPresent() && result.get() == buttonYes){
                FileWriter myWriter = new FileWriter("TextFile.txt");
                myWriter.write(textArea.getText());
                myWriter.close();
                isModified = false;

            } else if (result.isPresent() && result.get() == buttonNo) {
                System.exit(1);
            }
        }
        else
        System.out.println(getChanges());
        System.exit(1);
    }
}
