package com.seekhelp.desktop;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.seekhelp.desktop")
public class SeekHelpDesktopApplication extends Application {

    private ConfigurableApplicationContext springContext;
    private MainController mainController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        // Start Spring Boot backend
        springContext = SpringApplication.run(SeekHelpDesktopApplication.class);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Create main controller
            mainController = springContext.getBean(MainController.class);
            
            // Set up the main stage
            primaryStage.setTitle("Seek Help+ - Medical Assistance Platform");
            primaryStage.setMinWidth(1200);
            primaryStage.setMinHeight(800);
            
            // Create and set the main scene
            Scene scene = new Scene(mainController.getRoot());
            
            // Load CSS styles using the robust CSS loader
            CSSLoader.loadDefaultStyles(scene);
            
            primaryStage.setScene(scene);
            primaryStage.show();
            
            // Handle window close
            primaryStage.setOnCloseRequest(event -> {
                Platform.exit();
                System.exit(0);
            });
            
        } catch (Exception e) {
            e.printStackTrace();
            showError("Application Error", "Failed to start the application", e.getMessage());
        }
    }

    @Override
    public void stop() {
        if (springContext != null) {
            springContext.close();
        }
    }

    private void showError(String title, String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
