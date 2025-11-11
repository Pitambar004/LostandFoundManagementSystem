package com.teamlostandfound;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class LandingPageController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private ItemDao itemDao = new ItemDao();
    private ObservableList<Item> items = FXCollections.observableArrayList();

    @FXML
    private Button addFoundBtn;

    @FXML
    private Button addLostBtn;

    @FXML
    private Button adminLoginBtn;

    @FXML
    private TableColumn<Item, String> categoryCol;

    @FXML
    private ComboBox<String> categoryFilter;

    @FXML
    private Button dashboardBtn;

    @FXML
    private TableColumn<Item, String> dateCol;

    @FXML
    private TableView<Item> itemTable;

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
    private void initialize() {
        setupTableColumns();
        setupCategoryFilter();
        loadItemsFromDatabase();
    }
    
    private void setupTableColumns() {
        itemTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
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
    
    private void loadItemsFromDatabase() {
        try {
            items.clear();
            List<Item> allItems = itemDao.getAllItems();
            items.addAll(allItems);
            itemTable.setItems(items);
        } catch (SQLException e) {
            App.showAlert("Error loading items: " + e.getMessage());
        }
    }

    public void goToLoginPanel(ActionEvent event){
        App.loadScene("LoginPanel.fxml", "Admin Login");
    }

    public void goToAddLostItem(ActionEvent event){
        App.loadScene("AddLostItem.fxml", "Add Lost Item");
    }

    public void goToAddFoundItem(ActionEvent event){
        App.loadScene("AddFoundItem.fxml", "Add Found Item");
    }

    public void goToAdminPanel(ActionEvent event){
        App.loadScene("AdminPanel.fxml", "Admin Panel");
    }

    public void goToLandingPage(ActionEvent event){
        App.loadScene("LandingPage.fxml", "Landing Page");
    }

    @FXML
    void onSearch(ActionEvent event) {
    }
}
