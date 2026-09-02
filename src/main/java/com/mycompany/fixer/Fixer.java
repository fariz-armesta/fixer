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
/**
 *
 * @author FARIZ-T14
 */
public class Fixer extends Application {

    @Override
    public void start(Stage stage) {
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        
        Label nameLabel = new Label("Name");
        Label companyLabel = new Label("Company");
        Label emailLabel = new Label("Email");
        Label phoneLabel = new Label("Phone");
        Label tagLabel = new Label("Tag");
        Label descLabel = new Label("Desc");
    
        TextField nameField = new TextField();
        TextField companyField = new TextField();
        TextField emailField = new TextField();
        TextField phoneField = new TextField();
        TextField tagField = new TextField();
        TextField descField = new TextField();
        
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setAlignment(Pos.CENTER);
        
        formGrid.add(nameLabel, 0, 0);
        formGrid.add(companyLabel, 1, 0);
        formGrid.add(emailLabel, 2, 0);
        formGrid.add(phoneLabel, 3, 0);
        formGrid.add(tagLabel, 4, 0);
        formGrid.add(descLabel, 5, 0);

        formGrid.add(nameField, 0, 1);
        formGrid.add(companyField, 1, 1);
        formGrid.add(emailField, 2, 1);
        formGrid.add(phoneField, 3, 1);
        formGrid.add(tagField, 4, 1);
        formGrid.add(descField, 5, 1);
        
        Label outputLabel = new Label();

        Button insertButton = new Button("Insert");
        insertButton.setOnAction(event -> {
            String summary = nameField.getText() + " | " + companyField.getText() + " | "
                    + emailField.getText() + " | " + phoneField.getText() + " | "
                    + tagField.getText() + " | " + descField.getText();
            outputLabel.setText(summary);
        });

        VBox root = new VBox(15, formGrid, insertButton, outputLabel);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 700, 250);
        stage.setScene(scene);
        stage.setTitle("Fixer");
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
