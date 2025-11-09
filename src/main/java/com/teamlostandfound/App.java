package com.teamlostandfound;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class App extends Application {

    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        try {
            Database.initialize();
            // Create default admin user
            UserDao userDao = new UserDao();
            userDao.createDefaultAdmin();
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadScene("LandingPage.fxml", "Lost and Found System");
    }

    public static void loadScene(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/" + fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            mainStage.setTitle(title);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showAlert(String message){
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
