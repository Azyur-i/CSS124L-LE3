package com.groupfour;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.util.Optional;
import java.io.*;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("TextEditorG4.fxml"));
        
        Parent root = fxmlLoader.load();
        scene = new Scene(root, 640, 400);
        stage.setScene(scene);
        stage.show();

        TEController tc = fxmlLoader.getController();
        System.out.println("Controller: " + tc);

        stage.setOnCloseRequest(event -> {
          
            if (tc.textChanges()) {
    
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Warning!");
                alert.setHeaderText("You have unsaved changes");
                alert.setContentText("Would you like to save your changes?");
                ButtonType buttonYes = new ButtonType("Confirm");
                ButtonType buttonNo = new ButtonType("Discard");
                
                alert.getButtonTypes().setAll(buttonYes, buttonNo);
        
                Optional<ButtonType> result = alert.showAndWait();
                
                try {
                    if (result.isPresent() && result.get() == buttonYes){
                        FileWriter myWriter = new FileWriter("TextFile.txt");
                        myWriter.write(tc.textArea.getText());
                            myWriter.close();
                        } else if (result.isPresent() && result.get() == buttonNo) {
                            stage.close();
                    } 
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            stage.close();
        });
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}