package com.teamlostandfound;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddLostItemController {

    @FXML
    private ComboBox<String> categoryBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Button homeBtn;

    @FXML
    private TextField itemNameField;

    @FXML
    private TextField locationField;

    @FXML
    private Button submitBtn;

    @FXML
    void goHome(ActionEvent event) {
        App.loadScene("LandingPage.fxml", "Landing Page");
    }

    @FXML
    public void initialize(){
        categoryBox.getItems().addAll(
            "Electronics",
            "Clothing",
            "Books or Documents",
            "Accessories",
            "Others"
        );
    }

    @FXML
    void handleLostItemSubmission(ActionEvent event) {
        
        String itemName = itemNameField.getText();
        String location = locationField.getText();
        String description = descriptionArea.getText();

        if (itemName.isEmpty() || location.isEmpty() || description.isEmpty()) {
            App.showAlert("Please fill in all fields before submitting.");
            return;
        } 

        if (categoryBox.getSelectionModel().getSelectedItem() == null) {
            App.showAlert("Please select a category.");
            return;
        }

        String category = categoryBox.getSelectionModel().getSelectedItem();

        if (datePicker.getValue() == null){
            App.showAlert("Please select a date.");
            return;
        }

        LocalDate dateLost = datePicker.getValue();
        LocalDate currentDate = LocalDate.now();

        if (dateLost.isAfter(currentDate)) {
                App.showAlert("Date for lost Item cannot be in the future.");
                return;
            }

        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
            "INSERT INTO items (name, category, location, date, description, status) VALUES (?, ?, ?, ?, ?, ?)")) {
                stmt.setString(1, itemName);
                stmt.setString(2, category);
                stmt.setString(3, location);
                stmt.setDate(4, java.sql.Date.valueOf(dateLost));
                stmt.setString(5, description);
                stmt.setString(6, "LOST");

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    App.showAlert("Lost item submitted successfully");
                    clearFormFields();
                }
                else {
                    App.showAlert("Error submitting lost item. Please try again.");
                }
            
        } catch (SQLException e) {
            App.showAlert("Database Error: " + e.getMessage());
        }
        
    }
    private void clearFormFields() {
        itemNameField.clear();
        locationField.clear();
        descriptionArea.clear();
        categoryBox.getSelectionModel().clearSelection();
        datePicker.setValue(null);  
    }
}