package com.groupfour;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.scene.Node;
import javafx.event.Event;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainMenuController implements Initializable{
    
    private Stage currentStage;

    @FXML
    private VBox MainMenuVboxContainer;

    @FXML
    private FlowPane flowpane;
    
    private List<String> recentFiles = new ArrayList<>();
    FileChooser fileChooser = new FileChooser();

    private void loadRecentFiles(){
        try{
            String filePath = "recentfiles.txt";
                List<String> lines = Files.readAllLines(Paths.get(filePath));
                recentFiles.addAll(lines);
        } 
        catch (IOException e) {
            System.err.println("Error loading recent files: " + e.getMessage());
        }
    }

    private void displayRecentFiles() {
        flowpane.getChildren().clear();
        for (String filePath : recentFiles) {
            File file = new File(filePath);
            Button button = new Button(file.getName());
            button.setPrefWidth(218);
            button.setPrefHeight(50);
            button.setOnAction(e -> {
                try {
                    loadFile(file);
                } catch (IOException e1) {
                    System.err.println("Error loading file: " + e1.getMessage());
                }
                saveRecentFiles();
                displayRecentFiles();
            });
            flowpane.getChildren().add(button);
        }
    }

    private void saveRecentFiles() {
        try (FileWriter writer = new FileWriter("recentfiles.txt")) {
            for (String file : recentFiles) {
                writer.write(file + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error saving recent files: " + e.getMessage());
        }
    }

    @FXML
    private void switchToMainMenu() throws IOException {
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

    private void loadFile(File file) throws IOException{
        String loadedText = "";
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("TextEditorG4" + ".fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        TEController teController = loader.getController();
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                loadedText += scanner.nextLine()+"\n";
            }
            teController.textArea.appendText(loadedText);
            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
        stage.setScene(scene);
        stage.show();
        
        currentStage= (Stage)MainMenuVboxContainer.getScene().getWindow();

        currentStage.close();
        if(recentFiles.contains(file.getAbsolutePath())) {
            recentFiles.remove(file.getAbsolutePath());
        }
        recentFiles.add(0, file.getAbsolutePath());
        saveRecentFiles();
        displayRecentFiles();
    }
    

    @FXML
    private void handleloadFileBtn() throws IOException {
        File file = fileChooser.showOpenDialog(MainMenuVboxContainer.getScene().getWindow());
        loadFile(file);
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        fileChooser.setInitialDirectory(new File("C:\\Users\\Cent\\Desktop\\CSS124L-LE3\\CSS124L-LE3"));
        loadRecentFiles();
        displayRecentFiles();
    }
}
