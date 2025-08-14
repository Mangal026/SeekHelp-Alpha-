package com.seekhelp.desktop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.kordamp.ikonli.javafx.FontIcon;

public class AuthController {

    private MainController mainController;
    private VBox authView;
    private TabPane tabPane;
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField registerUsernameField;
    private TextField emailField;
    private PasswordField registerPasswordField;
    private PasswordField confirmPasswordField;

    public AuthController(MainController mainController) {
        this.mainController = mainController;
        initializeAuthView();
    }

    private void initializeAuthView() {
        authView = new VBox();
        authView.getStyleClass().add("auth-container");
        // Add inline style as backup to ensure green background
        authView.setStyle("-fx-background-color: #f0fdf4; -fx-font-family: 'System';");
        authView.setAlignment(Pos.CENTER);
        authView.setPadding(new Insets(40));
        authView.setSpacing(20);

        // Main content container
        HBox mainContent = new HBox();
        mainContent.setAlignment(Pos.CENTER);
        mainContent.setSpacing(40);
        mainContent.setMaxWidth(800);

        // Left side - Login/Register form
        VBox formContainer = createFormContainer();
        
        // Right side - Hero section
        VBox heroSection = createHeroSection();

        mainContent.getChildren().addAll(formContainer, heroSection);
        authView.getChildren().add(mainContent);
    }

    private VBox createFormContainer() {
        VBox formContainer = new VBox();
        formContainer.getStyleClass().add("auth-form-container");
        // Add inline style as backup for white background with shadow
        formContainer.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");
        formContainer.setPrefWidth(400);
        formContainer.setAlignment(Pos.CENTER);
        formContainer.setSpacing(20);

        // App logo and title
        VBox logoSection = new VBox();
        logoSection.setAlignment(Pos.CENTER);
        logoSection.setSpacing(15);

        // Logo
        Label logoIcon = new Label("🏥");
        logoIcon.setStyle("-fx-font-size: 48px;"); // Forest green

        Label titleLabel = new Label("Seek Help+");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
        titleLabel.setTextFill(Color.valueOf("#15803d"));

        Label subtitleLabel = new Label("Your Medical Assistance Platform");
        subtitleLabel.setFont(Font.font("System", 14));
        subtitleLabel.setTextFill(Color.GRAY);

        logoSection.getChildren().addAll(logoIcon, titleLabel, subtitleLabel);

        // Tab pane for login/register
        tabPane = new TabPane();
        tabPane.getStyleClass().add("auth-tabs");

        Tab loginTab = new Tab("Login", createLoginForm());
        loginTab.setClosable(false);
        
        Tab registerTab = new Tab("Register", createRegisterForm());
        registerTab.setClosable(false);

        tabPane.getTabs().addAll(loginTab, registerTab);

        formContainer.getChildren().addAll(logoSection, tabPane);
        return formContainer;
    }

    private VBox createLoginForm() {
        VBox loginForm = new VBox();
        loginForm.setSpacing(15);
        loginForm.setPadding(new Insets(20));

        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.getStyleClass().add("auth-input");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.getStyleClass().add("auth-input");

        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("auth-button");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setOnAction(e -> handleLogin());

        Button guestLoginButton = new Button("Login as Guest");
        guestLoginButton.getStyleClass().add("auth-button-secondary");
        guestLoginButton.setMaxWidth(Double.MAX_VALUE);
        guestLoginButton.setOnAction(e -> handleGuestLogin());

        loginForm.getChildren().addAll(usernameField, passwordField, loginButton, guestLoginButton);
        return loginForm;
    }

    private VBox createRegisterForm() {
        VBox registerForm = new VBox();
        registerForm.setSpacing(15);
        registerForm.setPadding(new Insets(20));

        registerUsernameField = new TextField();
        registerUsernameField.setPromptText("Username");
        registerUsernameField.getStyleClass().add("auth-input");

        emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.getStyleClass().add("auth-input");

        registerPasswordField = new PasswordField();
        registerPasswordField.setPromptText("Password");
        registerPasswordField.getStyleClass().add("auth-input");

        confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");
        confirmPasswordField.getStyleClass().add("auth-input");

        Button registerButton = new Button("Register");
        registerButton.getStyleClass().add("auth-button");
        registerButton.setMaxWidth(Double.MAX_VALUE);
        registerButton.setOnAction(e -> handleRegister());

        registerForm.getChildren().addAll(
            registerUsernameField, emailField, registerPasswordField, 
            confirmPasswordField, registerButton
        );
        return registerForm;
    }

    private VBox createHeroSection() {
        VBox heroSection = new VBox();
        heroSection.getStyleClass().add("auth-hero");
        // Add inline style as backup for green background
        heroSection.setStyle("-fx-background-color: #15803d; -fx-background-radius: 0 10 10 0;");
        heroSection.setPrefWidth(400);
        heroSection.setAlignment(Pos.CENTER);
        heroSection.setPadding(new Insets(40));
        heroSection.setSpacing(20);

        Label heroTitle = new Label("Welcome to Seek Help+");
        heroTitle.setFont(Font.font("System", FontWeight.BOLD, 32));
        heroTitle.setTextFill(Color.WHITE);

        Label heroSubtitle = new Label("Your healthcare companion in India");
        heroSubtitle.setFont(Font.font("System", 18));
        heroSubtitle.setTextFill(Color.WHITE);

        VBox featuresList = new VBox();
        featuresList.setSpacing(10);

        String[] features = {
            "Find and connect with hospitals across India",
            "Join our blood donation network",
            "Check symptoms with our AI-powered tool",
            "Get 24/7 medical assistance"
        };

        for (String feature : features) {
            HBox featureItem = new HBox(10);
            featureItem.setAlignment(Pos.CENTER_LEFT);

            Label checkIcon = new Label("✓");
            checkIcon.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

            Label featureLabel = new Label(feature);
            featureLabel.setFont(Font.font("System", 14));
            featureLabel.setTextFill(Color.WHITE);

            featureItem.getChildren().addAll(checkIcon, featureLabel);
            featuresList.getChildren().add(featureItem);
        }

        Label footerText = new Label("Join thousands of users across India who trust Seek Help+ for their healthcare needs.");
        footerText.setFont(Font.font("System", 12));
        footerText.setTextFill(Color.WHITE);
        footerText.setWrapText(true);

        heroSection.getChildren().addAll(heroTitle, heroSubtitle, featuresList, footerText);
        return heroSection;
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            mainController.showError("Login Error", "Please fill in all fields");
            return;
        }

        mainController.login(username, password);
    }

    private void handleGuestLogin() {
        mainController.login("guest", "guest123");
    }

    private void handleRegister() {
        String username = registerUsernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = registerPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            mainController.showError("Registration Error", "Please fill in all fields");
            return;
        }

        if (!password.equals(confirmPassword)) {
            mainController.showError("Registration Error", "Passwords do not match");
            return;
        }

        // For demo purposes, auto-login after registration
        mainController.showNotification("Registration Successful", "Account created successfully!");
        mainController.login(username, password);
    }

    public VBox getAuthView() {
        return authView;
    }
}
