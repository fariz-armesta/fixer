package com.mycompany.fixer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.control.TextField;
import java.util.function.Consumer;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import java.util.List;

public class RecordsWindow {

    public Parent build(List<Contact> contacts, DatabaseManager db, Consumer<Contact> onEdit) {
        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.setMaxWidth(300);
        
        TableView<Contact> table = new TableView<>();

        TableColumn<Contact, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Contact, String> companyCol = new TableColumn<>("Company");
        companyCol.setCellValueFactory(new PropertyValueFactory<>("company"));

        TableColumn<Contact, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Contact, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<Contact, String> tagCol = new TableColumn<>("Tag");
        tagCol.setCellValueFactory(new PropertyValueFactory<>("tag"));

        TableColumn<Contact, String> descCol = new TableColumn<>("Desc");
        descCol.setCellValueFactory(new PropertyValueFactory<>("desc"));
        
        TableColumn<Contact, String> socialCol = new TableColumn<>("Social");
        socialCol.setCellValueFactory(new PropertyValueFactory<>("social"));

        table.getColumns().addAll(nameCol, companyCol, emailCol, phoneCol, tagCol, socialCol, descCol);

        ObservableList<Contact> data = FXCollections.observableArrayList(contacts);
        FilteredList<Contact> filteredData = new FilteredList<>(data, p -> true);

        searchField.textProperty().addListener((obs, oldValue, newValue) -> {
            filteredData.setPredicate(contact -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerFilter = newValue.toLowerCase();

                if (contact.getName() != null && contact.getName().toLowerCase().contains(lowerFilter)) {
                    return true;
                } else if (contact.getCompany() != null && contact.getCompany().toLowerCase().contains(lowerFilter)) {
                    return true;
                } else if (contact.getEmail() != null && contact.getEmail().toLowerCase().contains(lowerFilter)) {
                    return true;
                } else if (contact.getPhone() != null && contact.getPhone().toLowerCase().contains(lowerFilter)) {
                    return true;
                } else if (contact.getTag() != null && contact.getTag().toLowerCase().contains(lowerFilter)) {
                    return true;
                } else if (contact.getSocial() != null && contact.getSocial().toLowerCase().contains(lowerFilter)) {
                    return true;
                } else if (contact.getDesc() != null && contact.getDesc().toLowerCase().contains(lowerFilter)) {
                    return true;
                }
                return false;
            });
        });

        table.setItems(filteredData);
        
        table.setRowFactory(tv -> {
            TableRow<Contact> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    showDetailWindow(row.getItem());
                }
            });
            return row;
        });

        Button deleteSelectedButton = new Button("Delete Selected");
        deleteSelectedButton.setOnAction(event -> {
            Contact selected = table.getSelectionModel().getSelectedItem();

            if (selected == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("No record selected");
                alert.setContentText("Please click a row first.");
                alert.showAndWait();
                return;
            }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Delete");
            confirm.setHeaderText("Delete this record?");
            confirm.setContentText(selected.getName() + " will be permanently deleted.");

            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    db.deleteContactById(selected.getId());
                    data.remove(selected);
                }
            });
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
                    data.clear();
                }
            });
        });
      
        Button editSelectedButton = new Button("Edit Selected");
        editSelectedButton.setOnAction(event -> {
            Contact selected = table.getSelectionModel().getSelectedItem();

            if (selected == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("No record selected");
                alert.setContentText("Please click a row first.");
                alert.showAndWait();
                return;
            }

            onEdit.accept(selected);
        });

        HBox buttonRow = new HBox(10, editSelectedButton, deleteSelectedButton, deleteAllButton);
        buttonRow.setAlignment(Pos.CENTER);
        VBox layout = new VBox(10, searchField, table, buttonRow);
        layout.setPadding(new Insets(20));
        VBox.setVgrow(table, Priority.ALWAYS);

        return layout;
    }
    
    private void showDetailWindow(Contact contact) {
        Stage detailStage = new Stage();
        detailStage.setTitle(contact.getName());
        detailStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));

        Label nameDetail = new Label("Name: " + contact.getName());
        Label companyDetail = new Label("Company: " + contact.getCompany());
        Label emailDetail = new Label("Email: " + contact.getEmail());
        Label phoneDetail = new Label("Phone: " + contact.getPhone());
        Label tagDetail = new Label("Tag: " + contact.getTag());
        Label socialDetail = new Label("Social: " + contact.getSocial());
        Label descDetail = new Label("Description: " + contact.getDesc());
        descDetail.setWrapText(true);

        VBox detailLayout = new VBox(10,
            nameDetail, companyDetail, emailDetail,
            phoneDetail, tagDetail, socialDetail, descDetail
        );
        detailLayout.setPadding(new Insets(20));

        Scene detailScene = new Scene(detailLayout, 400, 350);
        detailStage.setScene(detailScene);
        detailStage.show();
    }
}