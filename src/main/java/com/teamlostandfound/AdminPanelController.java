package com.teamlostandfound;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminPanelController {

    private ItemDao itemDao = new ItemDao();
    private ObservableList<Item> items = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Item, String> actionsCol;

    @FXML
    private TableView<Item> adminTable;

    @FXML
    private TableColumn<Item, String> categoryCol;

    @FXML
    private TableColumn<Item, String> dateCol;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button editBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private TableColumn<Item, String> locationCol;

    @FXML
    private TableColumn<Item, String> nameCol;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchBtn;

    @FXML
    private TableColumn<Item, String> statusCol;

    @FXML
    private ComboBox<String> categoryFilter;

    @FXML
    void goHome(ActionEvent event) {
        App.loadScene("LandingPage.fxml", "Landing Page");
    }

    @FXML
    private void initialize() {
        setupTableColumns();
        setupCategoryFilter();
        setupButtons();
        loadItemsFromDatabase();
    }
    
    private void setupTableColumns() {
        adminTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        dateCol.setCellValueFactory(cellData -> {
            Item item = cellData.getValue();
            if (item.getDate() != null) {
                String formattedDate = item.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                return new javafx.beans.property.SimpleStringProperty(formattedDate);
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });
        
        actionsCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(""));
    }
    
    private void setupCategoryFilter() {
        categoryFilter.getItems().addAll(
            "All Categories",
            "Electronics",
            "Clothing",
            "Books or Documents",
            "Accessories",
            "Others"
        );
        categoryFilter.setValue("All Categories");
    }
    
    private void setupButtons() {
        editBtn.setDisable(false);
        deleteBtn.setDisable(false);
    }
    
    private void loadItemsFromDatabase() {
        try {
            items.clear();
            List<Item> allItems = itemDao.getAllItems();
            items.addAll(allItems);
            adminTable.setItems(items);
        } catch (SQLException e) {
            App.showAlert("Error loading items: " + e.getMessage());
        }
    }

    @FXML
    void onEdit(ActionEvent event) {
        Item selected = adminTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println("Edit item: " + selected);
        }
    }

    @FXML
    void onDelete(ActionEvent event) {
        Item selected = adminTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println("Delete item: " + selected);
        }
    }

    @FXML
    void onSearch(ActionEvent event) {
    }
}