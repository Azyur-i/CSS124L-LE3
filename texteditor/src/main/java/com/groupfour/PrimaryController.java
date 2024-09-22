package com.groupfour;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PrimaryController implements Initializable{
    FileChooser fileChooser = new FileChooser();
    public String text;

    // They're unused (?????????)
    
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("MainMenu");
    }
    @FXML
    private void handlecloseAppBtn() {
        Platform.exit();
    }
    @FXML
    private void handlecreateFileBtn() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("TextEditorG4" + ".fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void handleloadFileBtn() throws IOException {
        text = "";
        File file = fileChooser.showOpenDialog(new Stage());
        handlecreateFileBtn();  
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                text = scanner.nextLine() + "\n";
            }        
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        fileChooser.setInitialDirectory(new File("C:\\Users\\Cent\\Downloads\\LE3\\le3\\src\\main\\sample"));
    }
}
