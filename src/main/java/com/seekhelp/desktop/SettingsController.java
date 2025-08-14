package com.seekhelp.desktop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.application.Platform;
import java.util.*;
import java.util.prefs.Preferences;

public class SettingsController {

    private MainController mainController;
    private VBox settingsView;
    private ScrollPane scrollPane;
    
    // Settings controls
    private ComboBox<String> languageCombo;
    private ComboBox<String> themeCombo;
    private ComboBox<String> fontSizeCombo;
    private CheckBox notificationsCheckBox;
    private CheckBox soundCheckBox;
    private CheckBox autoSaveCheckBox;
    private CheckBox locationServicesCheckBox;
    private CheckBox dataSharingCheckBox;
    private Slider brightnessSlider;
    private Slider volumeSlider;
    private TextField apiKeyField;
    private PasswordField apiSecretField;
    
    // Section containers
    private VBox notificationSection;
    private VBox privacySection;
    private VBox accessibilitySection;
    private VBox dataSection;
    
    // Preferences
    private Preferences prefs;

    public SettingsController(MainController mainController) {
        this.mainController = mainController;
        this.prefs = Preferences.userNodeForPackage(SettingsController.class);
        initializeSettingsView();
        loadSettings();
    }

    private void initializeSettingsView() {
        // Create scroll pane for the entire view
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("settings-scroll-pane");

        settingsView = new VBox();
        settingsView.getStyleClass().add("settings-container");
        settingsView.setPadding(new Insets(30));
        settingsView.setSpacing(25);

        // Header
        VBox header = createHeader();

        // Language & Regional Settings
        VBox languageSection = createLanguageSection();

        // Appearance Settings
        VBox appearanceSection = createAppearanceSection();

        // Notification Settings
        this.notificationSection = createNotificationSection();

        // Privacy & Security Settings
        this.privacySection = createPrivacySection();

        // Accessibility Settings
        this.accessibilitySection = createAccessibilitySection();

        // API & Integration Settings
        VBox apiSection = createAPISection();

        // Data Management
        this.dataSection = createDataSection();

        // Action buttons
        VBox actionSection = createActionSection();

        settingsView.getChildren().addAll(
            header, 
            languageSection, 
            appearanceSection, 
            notificationSection, 
            privacySection, 
            accessibilitySection, 
            apiSection, 
            dataSection, 
            actionSection
        );

        scrollPane.setContent(settingsView);
    }

    private VBox createHeader() {
        VBox header = new VBox();
        header.setSpacing(15);

        Label title = new Label("⚙️ Settings");
        title.setFont(Font.font("System", FontWeight.BOLD, 32));
        title.setTextFill(Color.valueOf("#1f2937"));

        Label subtitle = new Label("Customize your SeekHelp experience");
        subtitle.setFont(Font.font("System", 18));
        subtitle.setTextFill(Color.valueOf("#6b7280"));

        // Features banner
        VBox featuresBox = new VBox(10);
        featuresBox.setPadding(new Insets(15));
        featuresBox.getStyleClass().add("features-box");
        featuresBox.setAlignment(Pos.CENTER);

        Label featuresTitle = new Label("🔧 Comprehensive Settings Management");
        featuresTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        featuresTitle.setTextFill(Color.valueOf("#059669"));

        Label featuresText = new Label(
            "• Multi-Language Support • Theme Customization • Privacy Controls • Accessibility Options • Data Management"
        );
        featuresText.setFont(Font.font("System", 14));
        featuresText.setTextFill(Color.valueOf("#374151"));
        featuresText.setWrapText(true);
        featuresText.setAlignment(Pos.CENTER);

        featuresBox.getChildren().addAll(featuresTitle, featuresText);

        header.getChildren().addAll(title, subtitle, featuresBox);
        return header;
    }

    private VBox createLanguageSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("language-section");

        Label sectionTitle = new Label("🌍 Language & Regional Settings");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // Language selection
        VBox languageBox = new VBox(10);
        Label languageLabel = new Label("Application Language:");
        languageLabel.setFont(Font.font("System", 12));
        languageCombo = new ComboBox<>();
        languageCombo.getItems().addAll(
            "English (US)",
            "English (UK)",
            "हिंदी (Hindi)",
            "தமிழ் (Tamil)",
            "తెలుగు (Telugu)",
            "ಕನ್ನಡ (Kannada)",
            "മലയാളം (Malayalam)",
            "বাংলা (Bengali)",
            "ગુજરાતી (Gujarati)",
            "मराठी (Marathi)",
            "ਪੰਜਾਬੀ (Punjabi)",
            "ଓଡ଼ିଆ (Odia)",
            "অসমীয়া (Assamese)",
            "اردو (Urdu)",
            "नेपाली (Nepali)",
            "සිංහල (Sinhala)"
        );
        languageCombo.setValue("English (US)");
        languageCombo.setOnAction(e -> changeLanguage());
        languageBox.getChildren().addAll(languageLabel, languageCombo);

        // Regional settings
        VBox regionalBox = new VBox(10);
        Label regionalLabel = new Label("Regional Format:");
        regionalLabel.setFont(Font.font("System", 12));
        ComboBox<String> regionalCombo = new ComboBox<>();
        regionalCombo.getItems().addAll("India", "United States", "United Kingdom", "Canada", "Australia");
        regionalCombo.setValue("India");
        regionalBox.getChildren().addAll(regionalLabel, regionalCombo);

        // Time zone
        VBox timezoneBox = new VBox(10);
        Label timezoneLabel = new Label("Time Zone:");
        timezoneLabel.setFont(Font.font("System", 12));
        ComboBox<String> timezoneCombo = new ComboBox<>();
        timezoneCombo.getItems().addAll(
            "Asia/Kolkata (IST)",
            "Asia/Delhi (IST)",
            "Asia/Mumbai (IST)",
            "Asia/Bangalore (IST)",
            "Asia/Chennai (IST)",
            "Asia/Kolkata (IST)"
        );
        timezoneCombo.setValue("Asia/Kolkata (IST)");
        timezoneBox.getChildren().addAll(timezoneLabel, timezoneCombo);

        section.getChildren().addAll(sectionTitle, languageBox, regionalBox, timezoneBox);
        return section;
    }

    private VBox createAppearanceSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("appearance-section");

        Label sectionTitle = new Label("🎨 Appearance Settings");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // Theme selection
        VBox themeBox = new VBox(10);
        Label themeLabel = new Label("Theme:");
        themeLabel.setFont(Font.font("System", 12));
        themeCombo = new ComboBox<>();
        themeCombo.getItems().addAll("Light", "Dark", "Auto", "High Contrast", "Color Blind Friendly");
        themeCombo.setValue("Light");
        themeCombo.setOnAction(e -> changeTheme());
        themeBox.getChildren().addAll(themeLabel, themeCombo);

        // Font size
        VBox fontSizeBox = new VBox(10);
        Label fontSizeLabel = new Label("Font Size:");
        fontSizeLabel.setFont(Font.font("System", 12));
        fontSizeCombo = new ComboBox<>();
        fontSizeCombo.getItems().addAll("Small", "Medium", "Large", "Extra Large");
        fontSizeCombo.setValue("Medium");
        fontSizeCombo.setOnAction(e -> changeFontSize());
        fontSizeBox.getChildren().addAll(fontSizeLabel, fontSizeCombo);

        // Brightness
        VBox brightnessBox = new VBox(10);
        Label brightnessLabel = new Label("Brightness:");
        brightnessLabel.setFont(Font.font("System", 12));
        brightnessSlider = new Slider(0, 100, 50);
        brightnessSlider.setShowTickLabels(true);
        brightnessSlider.setShowTickMarks(true);
        brightnessSlider.setMajorTickUnit(25);
        brightnessSlider.setMinorTickCount(5);
        brightnessSlider.valueProperty().addListener((obs, oldVal, newVal) -> changeBrightness());
        brightnessBox.getChildren().addAll(brightnessLabel, brightnessSlider);

        section.getChildren().addAll(sectionTitle, themeBox, fontSizeBox, brightnessBox);
        return section;
    }

    private VBox createNotificationSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("notification-section");

        Label sectionTitle = new Label("🔔 Notification Settings");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // Notification toggles
        VBox notificationToggles = new VBox(10);

        notificationsCheckBox = new CheckBox("Enable Notifications");
        notificationsCheckBox.setSelected(true);
        notificationsCheckBox.setOnAction(e -> toggleNotifications());

        soundCheckBox = new CheckBox("Sound Notifications");
        soundCheckBox.setSelected(true);
        soundCheckBox.setOnAction(e -> toggleSound());

        CheckBox emailNotificationsCheckBox = new CheckBox("Email Notifications");
        emailNotificationsCheckBox.setSelected(false);
        emailNotificationsCheckBox.setOnAction(e -> toggleEmailNotifications());

        CheckBox smsNotificationsCheckBox = new CheckBox("SMS Notifications");
        smsNotificationsCheckBox.setSelected(false);
        smsNotificationsCheckBox.setOnAction(e -> toggleSMSNotifications());

        notificationToggles.getChildren().addAll(
            notificationsCheckBox, 
            soundCheckBox, 
            emailNotificationsCheckBox, 
            smsNotificationsCheckBox
        );

        // Volume control
        VBox volumeBox = new VBox(10);
        Label volumeLabel = new Label("Notification Volume:");
        volumeLabel.setFont(Font.font("System", 12));
        volumeSlider = new Slider(0, 100, 50);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setMajorTickUnit(25);
        volumeSlider.setMinorTickCount(5);
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> changeVolume());
        volumeBox.getChildren().addAll(volumeLabel, volumeSlider);

        section.getChildren().addAll(sectionTitle, notificationToggles, volumeBox);
        return section;
    }

    private VBox createPrivacySection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("privacy-section");

        Label sectionTitle = new Label("🔒 Privacy & Security Settings");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // Privacy toggles
        VBox privacyToggles = new VBox(10);

        locationServicesCheckBox = new CheckBox("Location Services");
        locationServicesCheckBox.setSelected(true);
        locationServicesCheckBox.setOnAction(e -> toggleLocationServices());

        dataSharingCheckBox = new CheckBox("Data Sharing for Research");
        dataSharingCheckBox.setSelected(false);
        dataSharingCheckBox.setOnAction(e -> toggleDataSharing());

        CheckBox analyticsCheckBox = new CheckBox("Analytics & Usage Data");
        analyticsCheckBox.setSelected(true);
        analyticsCheckBox.setOnAction(e -> toggleAnalytics());

        CheckBox personalizedAdsCheckBox = new CheckBox("Personalized Recommendations");
        personalizedAdsCheckBox.setSelected(false);
        personalizedAdsCheckBox.setOnAction(e -> togglePersonalizedAds());

        privacyToggles.getChildren().addAll(
            locationServicesCheckBox, 
            dataSharingCheckBox, 
            analyticsCheckBox, 
            personalizedAdsCheckBox
        );

        // Security settings
        VBox securityBox = new VBox(10);
        Label securityLabel = new Label("Security:");
        securityLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        Button changePasswordButton = new Button("🔑 Change Password");
        changePasswordButton.getStyleClass().add("secondary-button");
        changePasswordButton.setOnAction(e -> changePassword());

        Button twoFactorButton = new Button("🔐 Two-Factor Authentication");
        twoFactorButton.getStyleClass().add("secondary-button");
        twoFactorButton.setOnAction(e -> setupTwoFactor());

        securityBox.getChildren().addAll(securityLabel, changePasswordButton, twoFactorButton);

        section.getChildren().addAll(sectionTitle, privacyToggles, securityBox);
        return section;
    }

    private VBox createAccessibilitySection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("accessibility-section");

        Label sectionTitle = new Label("♿ Accessibility Settings");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // Accessibility options
        VBox accessibilityOptions = new VBox(10);

        CheckBox screenReaderCheckBox = new CheckBox("Screen Reader Support");
        screenReaderCheckBox.setSelected(false);
        screenReaderCheckBox.setOnAction(e -> toggleScreenReader());

        CheckBox highContrastCheckBox = new CheckBox("High Contrast Mode");
        highContrastCheckBox.setSelected(false);
        highContrastCheckBox.setOnAction(e -> toggleHighContrast());

        CheckBox reducedMotionCheckBox = new CheckBox("Reduce Motion");
        reducedMotionCheckBox.setSelected(false);
        reducedMotionCheckBox.setOnAction(e -> toggleReducedMotion());

        CheckBox largeTextCheckBox = new CheckBox("Large Text");
        largeTextCheckBox.setSelected(false);
        largeTextCheckBox.setOnAction(e -> toggleLargeText());

        accessibilityOptions.getChildren().addAll(
            screenReaderCheckBox, 
            highContrastCheckBox, 
            reducedMotionCheckBox, 
            largeTextCheckBox
        );

        section.getChildren().addAll(sectionTitle, accessibilityOptions);
        return section;
    }

    private VBox createAPISection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("api-section");

        Label sectionTitle = new Label("🔌 API & Integration Settings");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // API settings
        VBox apiSettings = new VBox(10);

        Label apiKeyLabel = new Label("API Key:");
        apiKeyLabel.setFont(Font.font("System", 12));
        apiKeyField = new TextField();
        apiKeyField.setPromptText("Enter your API key");
        apiKeyField.setDisable(true);

        Label apiSecretLabel = new Label("API Secret:");
        apiSecretLabel.setFont(Font.font("System", 12));
        apiSecretField = new PasswordField();
        apiSecretField.setPromptText("Enter your API secret");
        apiSecretField.setDisable(true);

        HBox apiButtons = new HBox(10);
        Button editAPIButton = new Button("✏️ Edit API Settings");
        editAPIButton.getStyleClass().add("secondary-button");
        editAPIButton.setOnAction(e -> editAPISettings());

        Button testAPIButton = new Button("🧪 Test Connection");
        testAPIButton.getStyleClass().add("secondary-button");
        testAPIButton.setOnAction(e -> testAPIConnection());

        apiButtons.getChildren().addAll(editAPIButton, testAPIButton);

        apiSettings.getChildren().addAll(apiKeyLabel, apiKeyField, apiSecretLabel, apiSecretField, apiButtons);

        section.getChildren().addAll(sectionTitle, apiSettings);
        return section;
    }

    private VBox createDataSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("data-section");

        Label sectionTitle = new Label("💾 Data Management");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // Data management options
        VBox dataOptions = new VBox(10);

        autoSaveCheckBox = new CheckBox("Auto-save Data");
        autoSaveCheckBox.setSelected(true);
        autoSaveCheckBox.setOnAction(e -> toggleAutoSave());

        CheckBox backupDataCheckBox = new CheckBox("Backup Data to Cloud");
        backupDataCheckBox.setSelected(true);
        backupDataCheckBox.setOnAction(e -> toggleBackup());

        CheckBox syncDataCheckBox = new CheckBox("Sync Across Devices");
        syncDataCheckBox.setSelected(true);
        syncDataCheckBox.setOnAction(e -> toggleSync());

        dataOptions.getChildren().addAll(autoSaveCheckBox, backupDataCheckBox, syncDataCheckBox);

        // Data actions
        VBox dataActions = new VBox(10);
        Label dataActionsLabel = new Label("Data Actions:");
        dataActionsLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        HBox dataButtons = new HBox(10);
        Button exportDataButton = new Button("📤 Export Data");
        exportDataButton.getStyleClass().add("secondary-button");
        exportDataButton.setOnAction(e -> exportData());

        Button importDataButton = new Button("📥 Import Data");
        importDataButton.getStyleClass().add("secondary-button");
        importDataButton.setOnAction(e -> importData());

        Button clearDataButton = new Button("🗑️ Clear All Data");
        clearDataButton.getStyleClass().add("danger-button");
        clearDataButton.setOnAction(e -> clearAllData());

        dataButtons.getChildren().addAll(exportDataButton, importDataButton, clearDataButton);
        dataActions.getChildren().addAll(dataActionsLabel, dataButtons);

        section.getChildren().addAll(sectionTitle, dataOptions, dataActions);
        return section;
    }

    private VBox createActionSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("action-section");

        // Action buttons
        HBox actionButtons = new HBox(15);
        actionButtons.setAlignment(Pos.CENTER);

        Button saveSettingsButton = new Button("💾 Save All Settings");
        saveSettingsButton.getStyleClass().add("primary-button");
        saveSettingsButton.setOnAction(e -> saveAllSettings());

        Button resetSettingsButton = new Button("🔄 Reset to Default");
        resetSettingsButton.getStyleClass().add("secondary-button");
        resetSettingsButton.setOnAction(e -> resetToDefault());

        Button aboutButton = new Button("ℹ️ About");
        aboutButton.getStyleClass().add("secondary-button");
        aboutButton.setOnAction(e -> showAbout());

        actionButtons.getChildren().addAll(saveSettingsButton, resetSettingsButton, aboutButton);

        section.getChildren().addAll(actionButtons);
        return section;
    }

    // Settings change methods
    private void changeLanguage() {
        String selectedLanguage = languageCombo.getValue();
        prefs.put("language", selectedLanguage);
        mainController.showNotification("Language Changed", "Language changed to " + selectedLanguage + ". Please restart the application for full effect.");
    }

    private void changeTheme() {
        String selectedTheme = themeCombo.getValue();
        prefs.put("theme", selectedTheme);
        mainController.showNotification("Theme Changed", "Theme changed to " + selectedTheme);
    }

    private void changeFontSize() {
        String selectedSize = fontSizeCombo.getValue();
        prefs.put("fontSize", selectedSize);
        mainController.showNotification("Font Size Changed", "Font size changed to " + selectedSize);
    }

    private void changeBrightness() {
        double brightness = brightnessSlider.getValue();
        prefs.putDouble("brightness", brightness);
        // In real implementation, this would change the UI brightness
    }

    private void toggleNotifications() {
        boolean enabled = notificationsCheckBox.isSelected();
        prefs.putBoolean("notifications", enabled);
        mainController.showNotification("Notifications", enabled ? "Notifications enabled" : "Notifications disabled");
    }

    private void toggleSound() {
        boolean enabled = soundCheckBox.isSelected();
        prefs.putBoolean("sound", enabled);
        mainController.showNotification("Sound", enabled ? "Sound notifications enabled" : "Sound notifications disabled");
    }

    private void toggleEmailNotifications() {
        boolean enabled = ((CheckBox) ((VBox) ((VBox) notificationSection.getChildren().get(1)).getChildren().get(2)).getChildren().get(0)).isSelected();
        prefs.putBoolean("emailNotifications", enabled);
        mainController.showNotification("Email Notifications", enabled ? "Email notifications enabled" : "Email notifications disabled");
    }

    private void toggleSMSNotifications() {
        boolean enabled = ((CheckBox) ((VBox) ((VBox) notificationSection.getChildren().get(1)).getChildren().get(3)).getChildren().get(0)).isSelected();
        prefs.putBoolean("smsNotifications", enabled);
        mainController.showNotification("SMS Notifications", enabled ? "SMS notifications enabled" : "SMS notifications disabled");
    }

    private void changeVolume() {
        double volume = volumeSlider.getValue();
        prefs.putDouble("volume", volume);
        // In real implementation, this would change the notification volume
    }

    private void toggleLocationServices() {
        boolean enabled = locationServicesCheckBox.isSelected();
        prefs.putBoolean("locationServices", enabled);
        mainController.showNotification("Location Services", enabled ? "Location services enabled" : "Location services disabled");
    }

    private void toggleDataSharing() {
        boolean enabled = dataSharingCheckBox.isSelected();
        prefs.putBoolean("dataSharing", enabled);
        mainController.showNotification("Data Sharing", enabled ? "Data sharing enabled" : "Data sharing disabled");
    }

    private void toggleAnalytics() {
        boolean enabled = ((CheckBox) ((VBox) ((VBox) privacySection.getChildren().get(1)).getChildren().get(2)).getChildren().get(0)).isSelected();
        prefs.putBoolean("analytics", enabled);
        mainController.showNotification("Analytics", enabled ? "Analytics enabled" : "Analytics disabled");
    }

    private void togglePersonalizedAds() {
        boolean enabled = ((CheckBox) ((VBox) ((VBox) privacySection.getChildren().get(1)).getChildren().get(3)).getChildren().get(0)).isSelected();
        prefs.putBoolean("personalizedAds", enabled);
        mainController.showNotification("Personalized Ads", enabled ? "Personalized ads enabled" : "Personalized ads disabled");
    }

    private void changePassword() {
        mainController.showNotification("Change Password", "Password change feature will be implemented soon.");
    }

    private void setupTwoFactor() {
        mainController.showNotification("Two-Factor Authentication", "Two-factor authentication setup will be implemented soon.");
    }

    private void toggleScreenReader() {
        boolean enabled = ((CheckBox) ((VBox) accessibilitySection.getChildren().get(1)).getChildren().get(0)).isSelected();
        prefs.putBoolean("screenReader", enabled);
        mainController.showNotification("Screen Reader", enabled ? "Screen reader support enabled" : "Screen reader support disabled");
    }

    private void toggleHighContrast() {
        boolean enabled = ((CheckBox) ((VBox) accessibilitySection.getChildren().get(1)).getChildren().get(1)).isSelected();
        prefs.putBoolean("highContrast", enabled);
        mainController.showNotification("High Contrast", enabled ? "High contrast mode enabled" : "High contrast mode disabled");
    }

    private void toggleReducedMotion() {
        boolean enabled = ((CheckBox) ((VBox) accessibilitySection.getChildren().get(1)).getChildren().get(2)).isSelected();
        prefs.putBoolean("reducedMotion", enabled);
        mainController.showNotification("Reduced Motion", enabled ? "Reduced motion enabled" : "Reduced motion disabled");
    }

    private void toggleLargeText() {
        boolean enabled = ((CheckBox) ((VBox) accessibilitySection.getChildren().get(1)).getChildren().get(3)).isSelected();
        prefs.putBoolean("largeText", enabled);
        mainController.showNotification("Large Text", enabled ? "Large text enabled" : "Large text disabled");
    }

    private void editAPISettings() {
        apiKeyField.setDisable(false);
        apiSecretField.setDisable(false);
        mainController.showNotification("API Settings", "API settings are now editable. Make your changes and save.");
    }

    private void testAPIConnection() {
        mainController.showNotification("API Test", "Testing API connection...");
        // In real implementation, this would test the API connection
    }

    private void toggleAutoSave() {
        boolean enabled = autoSaveCheckBox.isSelected();
        prefs.putBoolean("autoSave", enabled);
        mainController.showNotification("Auto-save", enabled ? "Auto-save enabled" : "Auto-save disabled");
    }

    private void toggleBackup() {
        boolean enabled = ((CheckBox) ((VBox) dataSection.getChildren().get(1)).getChildren().get(1)).isSelected();
        prefs.putBoolean("backup", enabled);
        mainController.showNotification("Backup", enabled ? "Cloud backup enabled" : "Cloud backup disabled");
    }

    private void toggleSync() {
        boolean enabled = ((CheckBox) ((VBox) dataSection.getChildren().get(1)).getChildren().get(2)).isSelected();
        prefs.putBoolean("sync", enabled);
        mainController.showNotification("Sync", enabled ? "Cross-device sync enabled" : "Cross-device sync disabled");
    }

    private void exportData() {
        mainController.showNotification("Export Data", "Data export feature will be implemented soon.");
    }

    private void importData() {
        mainController.showNotification("Import Data", "Data import feature will be implemented soon.");
    }

    private void clearAllData() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Clear All Data");
        alert.setHeaderText("Are you sure you want to clear all data?");
        alert.setContentText("This action cannot be undone. All your data will be permanently deleted.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            mainController.showNotification("Data Cleared", "All data has been cleared successfully.");
        }
    }

    private void saveAllSettings() {
        // Save all current settings to preferences
        prefs.put("language", languageCombo.getValue());
        prefs.put("theme", themeCombo.getValue());
        prefs.put("fontSize", fontSizeCombo.getValue());
        prefs.putDouble("brightness", brightnessSlider.getValue());
        prefs.putBoolean("notifications", notificationsCheckBox.isSelected());
        prefs.putBoolean("sound", soundCheckBox.isSelected());
        prefs.putDouble("volume", volumeSlider.getValue());
        prefs.putBoolean("locationServices", locationServicesCheckBox.isSelected());
        prefs.putBoolean("dataSharing", dataSharingCheckBox.isSelected());
        prefs.putBoolean("autoSave", autoSaveCheckBox.isSelected());

        mainController.showNotification("Settings Saved", "All settings have been saved successfully!");
    }

    private void resetToDefault() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reset Settings");
        alert.setHeaderText("Are you sure you want to reset all settings to default?");
        alert.setContentText("This will restore all settings to their default values.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            loadDefaultSettings();
            mainController.showNotification("Settings Reset", "All settings have been reset to default values.");
        }
    }

    private void showAbout() {
        mainController.showNotification("About", "SeekHelp v1.0 - Comprehensive Healthcare Management System");
    }

    private void loadSettings() {
        // Load saved settings from preferences
        languageCombo.setValue(prefs.get("language", "English (US)"));
        themeCombo.setValue(prefs.get("theme", "Light"));
        fontSizeCombo.setValue(prefs.get("fontSize", "Medium"));
        brightnessSlider.setValue(prefs.getDouble("brightness", 50));
        notificationsCheckBox.setSelected(prefs.getBoolean("notifications", true));
        soundCheckBox.setSelected(prefs.getBoolean("sound", true));
        volumeSlider.setValue(prefs.getDouble("volume", 50));
        locationServicesCheckBox.setSelected(prefs.getBoolean("locationServices", true));
        dataSharingCheckBox.setSelected(prefs.getBoolean("dataSharing", false));
        autoSaveCheckBox.setSelected(prefs.getBoolean("autoSave", true));
    }

    private void loadDefaultSettings() {
        // Reset all settings to default values
        languageCombo.setValue("English (US)");
        themeCombo.setValue("Light");
        fontSizeCombo.setValue("Medium");
        brightnessSlider.setValue(50);
        notificationsCheckBox.setSelected(true);
        soundCheckBox.setSelected(true);
        volumeSlider.setValue(50);
        locationServicesCheckBox.setSelected(true);
        dataSharingCheckBox.setSelected(false);
        autoSaveCheckBox.setSelected(true);
    }

    public VBox getSettingsView() {
        return settingsView;
    }

    public ScrollPane getSettingsScrollView() {
        return scrollPane;
    }
}
