package com.seekhelp.desktop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

public class SidebarController {

    private MainController mainController;
    private VBox sidebar;
    private Button currentActiveButton;

    public SidebarController(MainController mainController) {
        this.mainController = mainController;
        initializeSidebar();
    }

    private void initializeSidebar() {
        sidebar = new VBox();
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(250);
        sidebar.setPadding(new Insets(20));
        sidebar.setSpacing(10);

        // App logo and title
        VBox logoSection = new VBox();
        logoSection.setAlignment(Pos.CENTER);
        logoSection.setSpacing(10);
        logoSection.setPadding(new Insets(0, 0, 20, 0));

        // Logo icon
        Label logoIcon = new Label("🏥");
        logoIcon.setStyle("-fx-font-size: 32px;");

        Label logoLabel = new Label("Seek Help+");
        logoLabel.getStyleClass().add("sidebar-title");

        logoSection.getChildren().addAll(logoIcon, logoLabel);

        // Navigation buttons
        Button profileButton = createNavButton("Profile", "mdi-account", () -> mainController.showProfilePage());
        Button homeButton = createNavButton("Seek Help", "mdi-lifebuoy", () -> mainController.showHomePage());
        Button emergencyButton = createNavButton("🚨 Emergency", "mdi-alert", () -> mainController.showEmergencyServices());
        Button messageCenterButton = createNavButton("💬 Message Center & Care", "mdi-message-text", () -> mainController.showMessageCenterPage());
        Button aboutButton = createNavButton("About the App", "mdi-information", () -> mainController.showAboutPage());
        Button settingsButton = createNavButton("Settings", "mdi-cog", () -> mainController.showSettingsPage());
        
        // Admin button (only show for admin users)
        Button adminButton = createNavButton("👨‍💼 Admin Panel", "mdi-shield", () -> mainController.showAdminDashboard());
        adminButton.setVisible(mainController.isAdmin());
        adminButton.setManaged(mainController.isAdmin());

        // Separator
        Separator separator = new Separator();
        separator.getStyleClass().add("sidebar-separator");

        // Logout button
        Button logoutButton = createNavButton("Logout", "mdi-logout", () -> mainController.logout());
        logoutButton.getStyleClass().add("logout-button");

        sidebar.getChildren().addAll(
            logoSection,
            profileButton,
            homeButton,
            emergencyButton,
            messageCenterButton,
            aboutButton,
            settingsButton,
            adminButton,
            separator,
            logoutButton
        );

        // Set home as default active
        setActiveButton(homeButton);
    }

    private Button createNavButton(String text, String iconCode, Runnable action) {
        Button button = new Button();
        button.getStyleClass().add("nav-button");
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setPadding(new Insets(12.0, 15.0, 12.0, 15.0));

        Label icon = new Label(getIconText(iconCode));
        icon.setStyle("-fx-font-size: 16px;");
        button.setGraphic(icon);

        Label label = new Label(text);
        label.getStyleClass().add("nav-button-text");
        
        // Create HBox to hold icon and text
        javafx.scene.layout.HBox content = new javafx.scene.layout.HBox(10);
        content.setAlignment(Pos.CENTER_LEFT);
        content.getChildren().addAll(icon, label);
        
        button.setGraphic(content);
        button.setOnAction(e -> {
            setActiveButton(button);
            action.run();
        });

        return button;
    }

    private void setActiveButton(Button button) {
        if (currentActiveButton != null) {
            currentActiveButton.getStyleClass().remove("active");
        }
        button.getStyleClass().add("active");
        currentActiveButton = button;
    }

    private void showComingSoon(String feature) {
        mainController.showNotification("Coming Soon", feature + " feature will be available in the next update!");
    }

    public void setVisible(boolean visible) {
        sidebar.setVisible(visible);
        sidebar.setManaged(visible);
    }

    public VBox getSidebar() {
        return sidebar;
    }
    
    private String getIconText(String iconCode) {
        switch (iconCode) {
            case "mdi-account": return "👤";
            case "mdi-lifebuoy": return "🆘";
            case "mdi-alert": return "🚨";
            case "mdi-message-text": return "💬";
            case "mdi-phone": return "📞";
            case "mdi-information": return "ℹ️";
            case "mdi-cog": return "⚙️";
            case "mdi-shield": return "👨‍💼";
            case "mdi-logout": return "🚪";
            default: return "•";
        }
    }
}
