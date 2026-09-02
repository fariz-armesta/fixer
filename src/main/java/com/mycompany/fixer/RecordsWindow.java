package com.mycompany.fixer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

public class RecordsWindow {

    public Scene build(List<Contact> contacts, Stage stage, Scene mainScene) {
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

        table.getColumns().addAll(nameCol, companyCol, emailCol, phoneCol, tagCol, descCol);

        ObservableList<Contact> data = FXCollections.observableArrayList(contacts);
        table.setItems(data);

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> stage.setScene(mainScene));

        VBox layout = new VBox(10, table, backButton);
        layout.setPadding(new Insets(20));

        return new Scene(layout, 800, 400);
    }
}