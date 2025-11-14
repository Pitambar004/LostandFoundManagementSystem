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
    private TextField contactNameField;

    @FXML
    private TextField contactPhoneField;

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
        
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.isAfter(LocalDate.now())) {
                App.showAlert("Not allowed future date");
                datePicker.setValue(null);
            }
        });
        
        // Phone number validation - only digits, max 10 characters
        contactPhoneField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                // Remove any non-digit characters
                String digitsOnly = newValue.replaceAll("[^0-9]", "");
                if (!digitsOnly.equals(newValue)) {
                    contactPhoneField.setText(digitsOnly);
                }
                // Limit to 10 digits
                if (digitsOnly.length() > 10) {
                    contactPhoneField.setText(digitsOnly.substring(0, 10));
                }
            }
        });
    }

    @FXML
    void handleLostItemSubmission(ActionEvent event) {
        
        String itemName = itemNameField.getText();
        String location = locationField.getText();
        String description = descriptionArea.getText();
        String contactName = contactNameField.getText();
        String contactPhone = contactPhoneField.getText();

        if (itemName.isEmpty() || location.isEmpty() || description.isEmpty()) {
            App.showAlert("Please fill in all required fields before submitting.");
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
            App.showAlert("Not allowed future date");
            datePicker.setValue(null);
            return;
        }

        // Validate phone number if provided
        if (!contactPhone.isEmpty()) {
            if (contactPhone.length() != 10) {
                App.showAlert("Phone number must be exactly 10 digits.");
                return;
            }
            if (contactPhone.charAt(0) == '0') {
                App.showAlert("Phone number cannot start with zero.");
                return;
            }
            // Check if all digits
            if (!contactPhone.matches("\\d{10}")) {
                App.showAlert("Phone number must contain only digits.");
                return;
            }
        }

        try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
            "INSERT INTO items (name, category, location, date, description, status, contact_name, contact_phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
                stmt.setString(1, itemName);
                stmt.setString(2, category);
                stmt.setString(3, location);
                stmt.setDate(4, java.sql.Date.valueOf(dateLost));
                stmt.setString(5, description);
                stmt.setString(6, "LOST");
                stmt.setString(7, contactName.isEmpty() ? null : contactName);
                stmt.setString(8, contactPhone.isEmpty() ? null : contactPhone);

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
        contactNameField.clear();
        contactPhoneField.clear();
        categoryBox.getSelectionModel().clearSelection();
        datePicker.setValue(null);  
    }
}