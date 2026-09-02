/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.fixer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
/**
 *
 * @author FARIZ-T14
 */
public class Fixer extends Application {
    private final DatabaseManager db = new DatabaseManager();
    @Override
    public void start(Stage stage) {
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        
        Label nameLabel = new Label("Name");
        Label companyLabel = new Label("Company");
        Label emailLabel = new Label("Email");
        Label phoneLabel = new Label("Phone");
        Label tagLabel = new Label("Tag");
    
        TextField nameField = new TextField();
        TextField companyField = new TextField();
        TextField emailField = new TextField();
        TextField phoneField = new TextField();
        ComboBox<String> tagField = new ComboBox<>();
        tagField.getItems().addAll("Recruiters", "Professional", "Personal", "Other");
        tagField.setPromptText("Select tag");
        
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setAlignment(Pos.CENTER);
        
        formGrid.add(nameLabel, 0, 0);
        formGrid.add(companyLabel, 1, 0);
        formGrid.add(emailLabel, 2, 0);
        formGrid.add(phoneLabel, 3, 0);
        formGrid.add(tagLabel, 4, 0);

        formGrid.add(nameField, 0, 1);
        formGrid.add(companyField, 1, 1);
        formGrid.add(emailField, 2, 1);
        formGrid.add(phoneField, 3, 1);
        formGrid.add(tagField, 4, 1);
        
        Label descLabel = new Label("Desc");
        TextArea descField = new TextArea();
        descField.setPrefRowCount(5);
        descField.setWrapText(true);
        
        Label outputLabel = new Label();

        Button insertButton = new Button("Insert");
        insertButton.setOnAction(event -> {
            db.insertContact(
                nameField.getText(),
                companyField.getText(),
                emailField.getText(),
                phoneField.getText(),
                tagField.getValue(),
                descField.getText()
            );

            outputLabel.setText("Saved: " + nameField.getText());

            // Clear fields after saving
            nameField.clear();
            companyField.clear();
            emailField.clear();
            phoneField.clear();
            tagField.getSelectionModel().clearSelection();
            descField.clear();
        });
        
        Button deleteAllButton = new Button("Delete All");
        deleteAllButton.setOnAction(event -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Delete");
            confirm.setHeaderText("Delete all records?");
            confirm.setContentText("This cannot be undone.");

            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    db.deleteAllContacts();
                    outputLabel.setText("All records deleted.");
                }
            });
        });

        VBox root = new VBox(15, formGrid, descLabel, descField, insertButton, deleteAllButton, outputLabel);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 750, 450);
        stage.setScene(scene);
        stage.setTitle("Fixer");
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
