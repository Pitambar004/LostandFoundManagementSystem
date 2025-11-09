package com.teamlostandfound;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AdminPanelController {

    @FXML
    private TableColumn<?, ?> actionsCol;

    @FXML
    private TableView<?> adminTable;

    @FXML
    private TableColumn<?, ?> categoryCol;

    @FXML
    private TableColumn<?, ?> dateCol;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button editBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private TableColumn<?, ?> locationCol;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TextField searchField;

    @FXML
    private TableColumn<?, ?> statusCol;

    @FXML
    private ComboBox<?> statusFilter;

    @FXML
    void goHome(ActionEvent event) {
        App.loadScene("LandingPage.fxml", "Landing Page");
    }

    @FXML
    private void initialize() {
        // Start with buttons disabled
        editBtn.setDisable(true);
        deleteBtn.setDisable(true);

        // Enable buttons when a row is selected
        adminTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean hasSelection = (newSelection != null);
            editBtn.setDisable(!hasSelection);
            deleteBtn.setDisable(!hasSelection);
        });
    }

    @FXML
    void onEdit(ActionEvent event) {
        Object selected = adminTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println("Edit item: " + selected);
            // TODO: Implement edit functionality
            
        }
    }

    @FXML
    void onDelete(ActionEvent event) {
        Object selected = adminTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println("Delete item: " + selected);
            // TODO: Implement delete functionality
        }
    }
}