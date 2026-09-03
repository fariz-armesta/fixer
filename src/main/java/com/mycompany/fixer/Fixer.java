/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.fixer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
import javafx.scene.control.ComboBox;
import java.util.List;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.text.Font;
/**
 *
 * @author FARIZ-T14
 */
public class Fixer extends Application {
    private final DatabaseManager db = new DatabaseManager();
    private Contact editingContact = null;
    
    @Override
    public void start(Stage stage) {
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        Font loadedFont = Font.loadFont(getClass().getResourceAsStream("/fonts/LibreBodoni-VariableFont_wght.ttf"), 12);
        Label nameLabel = new Label("Name");
        Label companyLabel = new Label("Company");
        Label emailLabel = new Label("Email");
        Label phoneLabel = new Label("Phone");
        Label tagLabel = new Label("Tag");
        Label socialLabel = new Label("Social");
    
        TextField nameField = new TextField();
        TextField companyField = new TextField();
        TextField emailField = new TextField();
        TextField phoneField = new TextField();
        ComboBox<String> tagField = new ComboBox<>();
        tagField.getItems().addAll(db.getAllTags());
        tagField.setPromptText("Select tag");
        TextField socialField = new TextField();
        
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
        formGrid.add(socialLabel, 5, 0);
        formGrid.add(socialField, 5, 1);
        
        Label descLabel = new Label("Description");
        TextArea descField = new TextArea();
        descField.setPrefRowCount(5);
        descField.setWrapText(true);
        
        Label outputLabel = new Label();

        Button insertButton = new Button("Insert");
        insertButton.setOnAction(event -> {
            if (nameField.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Name is required");
                alert.setContentText("Please enter a name before saving.");
                alert.showAndWait();
                return;
            }

            if (editingContact != null) {
                db.updateContact(
                    editingContact.getId(),
                    nameField.getText(),
                    companyField.getText(),
                    emailField.getText(),
                    phoneField.getText(),
                    tagField.getValue(),
                    socialField.getText(),
                    descField.getText()
                );
                outputLabel.setText("Updated: " + nameField.getText());
                editingContact = null;
                insertButton.setText("Insert");
            } else {
                db.insertContact(
                    nameField.getText(),
                    companyField.getText(),
                    emailField.getText(),
                    phoneField.getText(),
                    tagField.getValue(),
                    socialField.getText(),
                    descField.getText()
                );
                outputLabel.setText("Saved: " + nameField.getText());
            }

            nameField.clear();
            companyField.clear();
            emailField.clear();
            phoneField.clear();
            tagField.getSelectionModel().clearSelection();
            socialField.clear();
            descField.clear();
        });
        
        
        Button viewRecordsButton = new Button("View Records");
        
        VBox inputView = new VBox(15, formGrid, descLabel, descField, insertButton, outputLabel);
        inputView.setAlignment(Pos.TOP_CENTER);
        inputView.setPadding(new Insets(20));

        BorderPane mainLayout = new BorderPane();
        mainLayout.setCenter(inputView);
        
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");

        MenuItem editTagsItem = new MenuItem("Edit Tags");
        editTagsItem.setOnAction(event -> new TagManagerWindow().show(db, tagField));
        
        MenuItem fileItem = new MenuItem("Exit");
        fileItem.setOnAction(event -> Platform.exit());

        editMenu.getItems().add(editTagsItem);
        fileMenu.getItems().add(fileItem);
        
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, editMenu);

        mainLayout.setTop(menuBar);

        Button inputTabButton = new Button("Input");
        Button viewTabButton = new Button("View");
        
        java.util.function.Consumer<Contact> onEdit = contact -> {
            editingContact = contact;
            nameField.setText(contact.getName());
            companyField.setText(contact.getCompany());
            emailField.setText(contact.getEmail());
            phoneField.setText(contact.getPhone());
            tagField.setValue(contact.getTag());
            socialField.setText(contact.getSocial());
            descField.setText(contact.getDesc());
            insertButton.setText("Update");
            mainLayout.setCenter(inputView);
        };
        inputTabButton.setOnAction(event -> mainLayout.setCenter(inputView));
        viewTabButton.setOnAction(event -> {
            List<Contact> contacts = db.getAllContacts();
            mainLayout.setCenter(new RecordsWindow().build(contacts, db, onEdit));
        });

        HBox tabBar = new HBox(10, inputTabButton, viewTabButton);
        tabBar.setAlignment(Pos.CENTER);
        tabBar.setPadding(new Insets(10));
        mainLayout.setBottom(tabBar);

        Scene mainScene = new Scene(mainLayout, 750, 400);
        mainScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setScene(mainScene);
        stage.setTitle("Fixer");
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
