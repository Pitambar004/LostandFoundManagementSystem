package com.teamlostandfound;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.net.URL;
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
        
        // Set initial window properties for responsive design
        mainStage.setMinWidth(850);
        mainStage.setMinHeight(550);
        mainStage.setResizable(true);
        
        loadScene("LandingPage.fxml", "Lost and Found System");
    }

    public static void loadScene(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/" + fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            // Ensure the global stylesheet is applied. Load from classpath root `/styles.css`.
            URL cssUrl = App.class.getResource("/styles.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            } else {
                System.err.println("Warning: styles.css not found on classpath. Checked '/styles.css'");
            }
            
            // Make window responsive - smaller size for login panel
            if (fxmlFile.equals("LoginPanel.fxml")) {
                mainStage.setMinWidth(350);
                mainStage.setMinHeight(400);
                mainStage.setWidth(400);
                mainStage.setHeight(450);
            } else {
                mainStage.setMinWidth(850);
                mainStage.setMinHeight(550);
                mainStage.setWidth(900);
                mainStage.setHeight(600);
            }
            mainStage.setResizable(true);
            
            mainStage.setTitle(title);
            mainStage.setScene(scene);
            mainStage.centerOnScreen();
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
