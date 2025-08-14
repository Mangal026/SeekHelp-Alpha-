package com.seekhelp.desktop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class HeaderController {

    private MainController mainController;
    private HBox header;
    private Label userLabel;
    private Label adminLabel;
    private Button notificationButton;
    private FontIcon notificationIcon;
    private boolean hasNotifications = true;

    public HeaderController(MainController mainController) {
        this.mainController = mainController;
        initializeHeader();
    }

    private void initializeHeader() {
        header = new HBox();
        header.getStyleClass().add("header");
        header.setPadding(new Insets(10, 20, 10, 20));
        header.setSpacing(15);
        header.setAlignment(Pos.CENTER_LEFT);

        // Menu button (for mobile responsiveness)
        Button menuButton = new Button();
        menuButton.setText("☰");
        menuButton.setStyle("-fx-font-size: 18px;");
        menuButton.getStyleClass().add("menu-button");
        menuButton.setOnAction(e -> toggleSidebar());

        // App title
        Label titleLabel = new Label("Seek Help+");
        titleLabel.getStyleClass().add("app-title");

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // User info
        VBox userInfoBox = new VBox();
        userInfoBox.setAlignment(Pos.CENTER_RIGHT);
        userInfoBox.setSpacing(2);

        userLabel = new Label("Hello, Guest");
        userLabel.getStyleClass().add("user-label");

        adminLabel = new Label("Admin");
        adminLabel.getStyleClass().add("admin-badge");
        adminLabel.setVisible(false);

        userInfoBox.getChildren().addAll(userLabel, adminLabel);

        // Notification button
        notificationButton = new Button();
        notificationButton.setText("🔔");
        notificationButton.setStyle("-fx-font-size: 16px;");
        notificationButton.getStyleClass().add("notification-button");
        notificationButton.setOnAction(e -> handleNotificationClick());

        // Add notification indicator
        if (hasNotifications) {
            notificationButton.getStyleClass().add("has-notifications");
        }

        header.getChildren().addAll(menuButton, titleLabel, spacer, userInfoBox, notificationButton);
    }

    private void toggleSidebar() {
        // This will be handled by the main controller
        mainController.showHomePage(); // For now, just show home
    }

    private void handleNotificationClick() {
        if (hasNotifications) {
            mainController.showNotification("Welcome!", 
                "Thank you for using Seek Help+, your healthcare companion in India.");
            hasNotifications = false;
            notificationButton.getStyleClass().remove("has-notifications");
        }
    }

    public void updateUserInfo(String username, boolean isAdmin) {
        userLabel.setText("Hello, " + username);
        adminLabel.setVisible(isAdmin);
    }

    public void setVisible(boolean visible) {
        header.setVisible(visible);
        header.setManaged(visible);
    }

    public HBox getHeader() {
        return header;
    }
}
