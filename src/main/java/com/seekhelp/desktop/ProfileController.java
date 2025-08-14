package com.seekhelp.desktop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProfileController {

    private MainController mainController;
    private VBox profileView;
    private ScrollPane scrollPane;
    
    // Profile fields
    private TextField nameField;
    private TextField emailField;
    private TextField phoneField;
    private TextField ageField;
    private ComboBox<String> genderCombo;
    private TextArea addressArea;
    private TextArea medicalHistoryArea;
    private TextArea allergiesArea;
    private ComboBox<String> bloodGroupCombo;
    private TextField emergencyContactField;
    private TextField emergencyPhoneField;
    private TextArea notesArea;
    
    // Profile image
    private ImageView profileImageView;
    private Button uploadImageButton;
    private Button removeImageButton;
    
    // Edit mode
    private boolean isEditMode = false;
    private Button editButton;
    private Button saveButton;
    private Button cancelButton;

    public ProfileController(MainController mainController) {
        this.mainController = mainController;
        initializeProfileView();
        loadProfileData();
    }

    private void initializeProfileView() {
        // Create scroll pane for the entire view
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("profile-scroll-pane");

        profileView = new VBox();
        profileView.getStyleClass().add("profile-container");
        profileView.setPadding(new Insets(30));
        profileView.setSpacing(25);

        // Header
        VBox header = createHeader();

        // Profile Image section
        VBox imageSection = createImageSection();

        // Personal Information section
        VBox personalInfoSection = createPersonalInfoSection();

        // Medical Information section
        VBox medicalInfoSection = createMedicalInfoSection();

        // Emergency Contact section
        VBox emergencySection = createEmergencySection();

        // Additional Information section
        VBox additionalSection = createAdditionalSection();

        // Action buttons
        VBox actionSection = createActionSection();

        profileView.getChildren().addAll(
            header, 
            imageSection, 
            personalInfoSection, 
            medicalInfoSection, 
            emergencySection, 
            additionalSection, 
            actionSection
        );

        scrollPane.setContent(profileView);
    }

    private VBox createHeader() {
        VBox header = new VBox();
        header.setSpacing(15);

        Label title = new Label("👤 My Profile");
        title.setFont(Font.font("System", FontWeight.BOLD, 32));
        title.setTextFill(Color.valueOf("#1f2937"));

        Label subtitle = new Label("Manage your personal and medical information");
        subtitle.setFont(Font.font("System", 18));
        subtitle.setTextFill(Color.valueOf("#6b7280"));

        // Features banner
        VBox featuresBox = new VBox(10);
        featuresBox.setPadding(new Insets(15));
        featuresBox.getStyleClass().add("features-box");
        featuresBox.setAlignment(Pos.CENTER);

        Label featuresTitle = new Label("🔒 Secure Profile Management");
        featuresTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        featuresTitle.setTextFill(Color.valueOf("#059669"));

        Label featuresText = new Label(
            "• Profile Photo Upload • Personal Information • Medical History • Emergency Contacts • Privacy Controls"
        );
        featuresText.setFont(Font.font("System", 14));
        featuresText.setTextFill(Color.valueOf("#374151"));
        featuresText.setWrapText(true);
        featuresText.setAlignment(Pos.CENTER);

        featuresBox.getChildren().addAll(featuresTitle, featuresText);

        header.getChildren().addAll(title, subtitle, featuresBox);
        return header;
    }

    private VBox createImageSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("profile-image-section");

        Label sectionTitle = new Label("📸 Profile Photo");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // Image container
        VBox imageContainer = new VBox();
        imageContainer.setAlignment(Pos.CENTER);
        imageContainer.setSpacing(10);

        // Profile image view
        profileImageView = new ImageView();
        profileImageView.setFitWidth(150);
        profileImageView.setFitHeight(150);
        profileImageView.setPreserveRatio(true);
        profileImageView.getStyleClass().add("profile-image");
        profileImageView.setStyle("-fx-border-color: #e5e7eb; -fx-border-radius: 75; -fx-border-width: 3;");

        // Default profile image
        try {
            // Create a simple default avatar
            profileImageView.setImage(createDefaultAvatar());
        } catch (Exception e) {
            // If image loading fails, create a placeholder
            profileImageView.setStyle("-fx-background-color: #f3f4f6; -fx-border-color: #e5e7eb; -fx-border-radius: 75; -fx-border-width: 3;");
        }

        // Image action buttons
        HBox imageButtons = new HBox(10);
        imageButtons.setAlignment(Pos.CENTER);

        uploadImageButton = new Button("📁 Upload Photo");
        uploadImageButton.getStyleClass().add("primary-button");
        uploadImageButton.setOnAction(e -> uploadProfileImage());

        removeImageButton = new Button("🗑️ Remove");
        removeImageButton.getStyleClass().add("secondary-button");
        removeImageButton.setOnAction(e -> removeProfileImage());

        imageButtons.getChildren().addAll(uploadImageButton, removeImageButton);

        imageContainer.getChildren().addAll(profileImageView, imageButtons);

        section.getChildren().addAll(sectionTitle, imageContainer);
        return section;
    }

    private VBox createPersonalInfoSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("personal-info-section");

        Label sectionTitle = new Label("👤 Personal Information");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // Personal info form
        VBox form = new VBox(15);

        // Name field
        VBox nameBox = new VBox(5);
        Label nameLabel = new Label("Full Name:");
        nameLabel.setFont(Font.font("System", 12));
        nameField = new TextField();
        nameField.setPromptText("Enter your full name");
        nameField.setDisable(true);
        nameBox.getChildren().addAll(nameLabel, nameField);

        // Email field
        VBox emailBox = new VBox(5);
        Label emailLabel = new Label("Email Address:");
        emailLabel.setFont(Font.font("System", 12));
        emailField = new TextField();
        emailField.setPromptText("Enter your email address");
        emailField.setDisable(true);
        emailBox.getChildren().addAll(emailLabel, emailField);

        // Phone field
        VBox phoneBox = new VBox(5);
        Label phoneLabel = new Label("Phone Number:");
        phoneLabel.setFont(Font.font("System", 12));
        phoneField = new TextField();
        phoneField.setPromptText("Enter your phone number");
        phoneField.setDisable(true);
        phoneBox.getChildren().addAll(phoneLabel, phoneField);

        // Age and Gender row
        HBox ageGenderRow = new HBox(15);
        
        VBox ageBox = new VBox(5);
        Label ageLabel = new Label("Age:");
        ageLabel.setFont(Font.font("System", 12));
        ageField = new TextField();
        ageField.setPromptText("Enter your age");
        ageField.setDisable(true);
        ageBox.getChildren().addAll(ageLabel, ageField);

        VBox genderBox = new VBox(5);
        Label genderLabel = new Label("Gender:");
        genderLabel.setFont(Font.font("System", 12));
        genderCombo = new ComboBox<>();
        genderCombo.getItems().addAll("Male", "Female", "Other", "Prefer not to say");
        genderCombo.setDisable(true);
        genderBox.getChildren().addAll(genderLabel, genderCombo);

        ageGenderRow.getChildren().addAll(ageBox, genderBox);

        // Address field
        VBox addressBox = new VBox(5);
        Label addressLabel = new Label("Address:");
        addressLabel.setFont(Font.font("System", 12));
        addressArea = new TextArea();
        addressArea.setPromptText("Enter your address");
        addressArea.setPrefRowCount(3);
        addressArea.setWrapText(true);
        addressArea.setDisable(true);
        addressBox.getChildren().addAll(addressLabel, addressArea);

        form.getChildren().addAll(nameBox, emailBox, phoneBox, ageGenderRow, addressBox);

        section.getChildren().addAll(sectionTitle, form);
        return section;
    }

    private VBox createMedicalInfoSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("medical-info-section");

        Label sectionTitle = new Label("🏥 Medical Information");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // Medical info form
        VBox form = new VBox(15);

        // Blood group
        VBox bloodGroupBox = new VBox(5);
        Label bloodGroupLabel = new Label("Blood Group:");
        bloodGroupLabel.setFont(Font.font("System", 12));
        bloodGroupCombo = new ComboBox<>();
        bloodGroupCombo.getItems().addAll("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-", "Unknown");
        bloodGroupCombo.setDisable(true);
        bloodGroupBox.getChildren().addAll(bloodGroupLabel, bloodGroupCombo);

        // Medical history
        VBox medicalHistoryBox = new VBox(5);
        Label medicalHistoryLabel = new Label("Medical History:");
        medicalHistoryLabel.setFont(Font.font("System", 12));
        medicalHistoryArea = new TextArea();
        medicalHistoryArea.setPromptText("Enter your medical history, surgeries, chronic conditions...");
        medicalHistoryArea.setPrefRowCount(4);
        medicalHistoryArea.setWrapText(true);
        medicalHistoryArea.setDisable(true);
        medicalHistoryBox.getChildren().addAll(medicalHistoryLabel, medicalHistoryArea);

        // Allergies
        VBox allergiesBox = new VBox(5);
        Label allergiesLabel = new Label("Allergies:");
        allergiesLabel.setFont(Font.font("System", 12));
        allergiesArea = new TextArea();
        allergiesArea.setPromptText("Enter any allergies (medications, food, environmental...");
        allergiesArea.setPrefRowCount(3);
        allergiesArea.setWrapText(true);
        allergiesArea.setDisable(true);
        allergiesBox.getChildren().addAll(allergiesLabel, allergiesArea);

        form.getChildren().addAll(bloodGroupBox, medicalHistoryBox, allergiesBox);

        section.getChildren().addAll(sectionTitle, form);
        return section;
    }

    private VBox createEmergencySection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("emergency-section");

        Label sectionTitle = new Label("🚨 Emergency Contact");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // Emergency contact form
        VBox form = new VBox(15);

        // Emergency contact name
        VBox emergencyNameBox = new VBox(5);
        Label emergencyNameLabel = new Label("Emergency Contact Name:");
        emergencyNameLabel.setFont(Font.font("System", 12));
        emergencyContactField = new TextField();
        emergencyContactField.setPromptText("Enter emergency contact name");
        emergencyContactField.setDisable(true);
        emergencyNameBox.getChildren().addAll(emergencyNameLabel, emergencyContactField);

        // Emergency contact phone
        VBox emergencyPhoneBox = new VBox(5);
        Label emergencyPhoneLabel = new Label("Emergency Contact Phone:");
        emergencyPhoneLabel.setFont(Font.font("System", 12));
        emergencyPhoneField = new TextField();
        emergencyPhoneField.setPromptText("Enter emergency contact phone number");
        emergencyPhoneField.setDisable(true);
        emergencyPhoneBox.getChildren().addAll(emergencyPhoneLabel, emergencyPhoneField);

        form.getChildren().addAll(emergencyNameBox, emergencyPhoneBox);

        section.getChildren().addAll(sectionTitle, form);
        return section;
    }

    private VBox createAdditionalSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("additional-section");

        Label sectionTitle = new Label("📝 Additional Notes");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // Notes area
        VBox notesBox = new VBox(5);
        Label notesLabel = new Label("Additional Information:");
        notesLabel.setFont(Font.font("System", 12));
        notesArea = new TextArea();
        notesArea.setPromptText("Enter any additional information, preferences, or notes...");
        notesArea.setPrefRowCount(4);
        notesArea.setWrapText(true);
        notesArea.setDisable(true);
        notesBox.getChildren().addAll(notesLabel, notesArea);

        section.getChildren().addAll(sectionTitle, notesBox);
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

        editButton = new Button("✏️ Edit Profile");
        editButton.getStyleClass().add("primary-button");
        editButton.setOnAction(e -> enableEditMode());

        saveButton = new Button("💾 Save Changes");
        saveButton.getStyleClass().add("success-button");
        saveButton.setVisible(false);
        saveButton.setOnAction(e -> saveProfile());

        cancelButton = new Button("❌ Cancel");
        cancelButton.getStyleClass().add("secondary-button");
        cancelButton.setVisible(false);
        cancelButton.setOnAction(e -> cancelEdit());

        Button exportButton = new Button("📄 Export Profile");
        exportButton.getStyleClass().add("secondary-button");
        exportButton.setOnAction(e -> exportProfile());

        Button privacyButton = new Button("🔒 Privacy Settings");
        privacyButton.getStyleClass().add("secondary-button");
        privacyButton.setOnAction(e -> openPrivacySettings());

        actionButtons.getChildren().addAll(editButton, saveButton, cancelButton, exportButton, privacyButton);

        section.getChildren().addAll(actionButtons);
        return section;
    }

    private Image createDefaultAvatar() {
        // Create a simple default avatar image
        // In a real implementation, this would load a default avatar image
        return null; // Return null to use the placeholder styling
    }

    private void uploadProfileImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Photo");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );

        Stage stage = (Stage) profileView.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
                profileImageView.setImage(image);
                mainController.showNotification("Success", "Profile photo uploaded successfully!");
            } catch (Exception e) {
                mainController.showNotification("Error", "Failed to load image. Please try again.");
            }
        }
    }

    private void removeProfileImage() {
        profileImageView.setImage(createDefaultAvatar());
        mainController.showNotification("Success", "Profile photo removed.");
    }

    private void enableEditMode() {
        isEditMode = true;
        
        // Enable all fields
        nameField.setDisable(false);
        emailField.setDisable(false);
        phoneField.setDisable(false);
        ageField.setDisable(false);
        genderCombo.setDisable(false);
        addressArea.setDisable(false);
        medicalHistoryArea.setDisable(false);
        allergiesArea.setDisable(false);
        bloodGroupCombo.setDisable(false);
        emergencyContactField.setDisable(false);
        emergencyPhoneField.setDisable(false);
        notesArea.setDisable(false);

        // Show/hide buttons
        editButton.setVisible(false);
        saveButton.setVisible(true);
        cancelButton.setVisible(true);

        mainController.showNotification("Edit Mode", "Profile is now in edit mode. Make your changes and click Save.");
    }

    private void saveProfile() {
        // Validate required fields
        if (nameField.getText().trim().isEmpty()) {
            mainController.showNotification("Error", "Name is required.");
            return;
        }

        if (emailField.getText().trim().isEmpty()) {
            mainController.showNotification("Error", "Email is required.");
            return;
        }

        // Save profile data (in real implementation, this would save to database)
        mainController.showNotification("Success", "Profile saved successfully!");

        // Disable edit mode
        disableEditMode();
    }

    private void cancelEdit() {
        // Reload original data
        loadProfileData();
        
        // Disable edit mode
        disableEditMode();
        
        mainController.showNotification("Cancelled", "Changes cancelled. Profile restored to original state.");
    }

    private void disableEditMode() {
        isEditMode = false;
        
        // Disable all fields
        nameField.setDisable(true);
        emailField.setDisable(true);
        phoneField.setDisable(true);
        ageField.setDisable(true);
        genderCombo.setDisable(true);
        addressArea.setDisable(true);
        medicalHistoryArea.setDisable(true);
        allergiesArea.setDisable(true);
        bloodGroupCombo.setDisable(true);
        emergencyContactField.setDisable(true);
        emergencyPhoneField.setDisable(true);
        notesArea.setDisable(true);

        // Show/hide buttons
        editButton.setVisible(true);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
    }

    private void exportProfile() {
        mainController.showNotification("Export", "Profile export feature will be implemented soon.");
        // In real implementation, this would export profile data to PDF or other format
    }

    private void openPrivacySettings() {
        mainController.showNotification("Privacy", "Privacy settings will be implemented soon.");
        // In real implementation, this would open privacy settings dialog
    }

    private void loadProfileData() {
        // Load sample profile data (in real implementation, this would load from database)
        nameField.setText("Mangal Pandey");
        emailField.setText("mangal.pandey@example.com");
        phoneField.setText("+91 98765 43210");
        ageField.setText("25");
        genderCombo.setValue("Male");
        addressArea.setText("123 Main Street, Mumbai, Maharashtra, India");
        medicalHistoryArea.setText("No major medical history. Regular check-ups only.");
        allergiesArea.setText("No known allergies.");
        bloodGroupCombo.setValue("O+");
        emergencyContactField.setText("Priya Pandey");
        emergencyPhoneField.setText("+91 98765 43211");
        notesArea.setText("Prefers morning appointments. Vegetarian diet.");
    }

    public VBox getProfileView() {
        return profileView;
    }

    public ScrollPane getProfileScrollView() {
        return scrollPane;
    }
}
