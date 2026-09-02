/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fixer;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author FARIZ-T14
 */
public class TagManagerWindow {

    public void show(DatabaseManager db, ComboBox<String> tagField) {
        Stage stage = new Stage();
        stage.setTitle("Manage Tags");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));

        ListView<String> tagListView = new ListView<>();
        tagListView.setItems(FXCollections.observableArrayList(db.getAllTags()));

        TextField newTagField = new TextField();
        newTagField.setPromptText("Tag name");

        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            String newTag = newTagField.getText().trim();
            if (!newTag.isEmpty() && !tagListView.getItems().contains(newTag)) {
                db.addTag(newTag);
                tagListView.getItems().add(newTag);
                tagField.getItems().add(newTag);
                newTagField.clear();
            }
        });

        Button renameButton = new Button("Rename Selected");
        renameButton.setOnAction(event -> {
            String selected = tagListView.getSelectionModel().getSelectedItem();
            String newName = newTagField.getText().trim();

            if (selected == null || newName.isEmpty()) return;

            db.updateTag(selected, newName);

            int listIndex = tagListView.getItems().indexOf(selected);
            tagListView.getItems().set(listIndex, newName);

            int comboIndex = tagField.getItems().indexOf(selected);
            if (comboIndex != -1) {
                tagField.getItems().set(comboIndex, newName);
            }

            newTagField.clear();
        });

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(event -> {
            String selected = tagListView.getSelectionModel().getSelectedItem();
            if (selected == null) return;

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setHeaderText("Delete tag \"" + selected + "\"?");
            confirm.setContentText("Existing records using this tag won't be changed.");

            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    db.deleteTag(selected);
                    tagListView.getItems().remove(selected);
                    tagField.getItems().remove(selected);
                }
            });
        });

        HBox actionRow = new HBox(10, newTagField, addButton, renameButton, removeButton);

        VBox layout = new VBox(10, new Label("Tags:"), tagListView, actionRow);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 450, 400));
        stage.show();
    }
}
