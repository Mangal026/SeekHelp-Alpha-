package com.seekhelp.desktop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BloodDonorsController {

    private MainController mainController;
    private VBox bloodDonorsView;
    private TextField searchField;
    private ComboBox<String> bloodTypeCombo;
    private ComboBox<String> locationCombo;
    private VBox donorsContainer;
    private List<Donor> allDonors;

    public BloodDonorsController(MainController mainController) {
        this.mainController = mainController;
        initializeBloodDonorsView();
        loadSampleData();
    }

    private void initializeBloodDonorsView() {
        bloodDonorsView = new VBox();
        bloodDonorsView.getStyleClass().add("blood-donors-container");
        bloodDonorsView.setPadding(new Insets(30));
        bloodDonorsView.setSpacing(20);

        // Header
        VBox header = createHeader();

        // Search and filter controls
        HBox controls = createControls();

        // Donors container
        donorsContainer = new VBox();
        donorsContainer.setSpacing(15);

        ScrollPane scrollPane = new ScrollPane(donorsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        bloodDonorsView.getChildren().addAll(header, controls, scrollPane);
    }

    private VBox createHeader() {
        VBox header = new VBox();
        header.setSpacing(10);

        Label title = new Label("🩸 Blood Donors");
        title.setFont(Font.font("System", FontWeight.BOLD, 28));
        title.setTextFill(Color.valueOf("#1f2937"));

        Label subtitle = new Label("Find blood donors in your area");
        subtitle.setFont(Font.font("System", 16));
        subtitle.setTextFill(Color.valueOf("#6b7280"));

        // Security notice
        VBox securityNotice = new VBox(5);
        securityNotice.setPadding(new Insets(15));
        securityNotice.getStyleClass().add("security-notice");
        securityNotice.setStyle("-fx-background-color: #fef3c7; -fx-border-color: #f59e0b; -fx-border-radius: 8; -fx-background-radius: 8;");

        Label securityTitle = new Label("🔒 Privacy & Security");
        securityTitle.setFont(Font.font("System", FontWeight.BOLD, 14));
        securityTitle.setTextFill(Color.valueOf("#92400e"));

        Label securityText = new Label(
            "Donor personal information (names, contact details) is only visible to authorized medical professionals. " +
            "Regular users can only see blood type and location for privacy protection."
        );
        securityText.setFont(Font.font("System", 12));
        securityText.setTextFill(Color.valueOf("#92400e"));
        securityText.setWrapText(true);

        securityNotice.getChildren().addAll(securityTitle, securityText);

        header.getChildren().addAll(title, subtitle, securityNotice);
        return header;
    }

    private HBox createControls() {
        HBox controls = new HBox();
        controls.setSpacing(15);
        controls.setAlignment(Pos.CENTER_LEFT);

        // Blood type filter
        Label bloodTypeLabel = new Label("Blood Type:");
        bloodTypeCombo = new ComboBox<>();
        bloodTypeCombo.getItems().addAll("All Blood Types", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
        bloodTypeCombo.setValue("All Blood Types");
        bloodTypeCombo.setPrefWidth(150);

        // Location filter
        Label locationLabel = new Label("Location:");
        locationCombo = new ComboBox<>();
        locationCombo.getItems().addAll("All Locations", "Mumbai", "Delhi", "Bangalore", "Hyderabad", "Ahmedabad");
        locationCombo.setValue("All Locations");
        locationCombo.setPrefWidth(150);

        // Search field (only for doctors)
        Label searchLabel = new Label("Search:");
        searchField = new TextField();
        searchField.setPromptText("Search donors by name...");
        searchField.setPrefWidth(200);
        searchField.setDisable(!isDoctorUser());

        // Add event listeners
        bloodTypeCombo.setOnAction(e -> filterDonors());
        locationCombo.setOnAction(e -> filterDonors());
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterDonors());

        controls.getChildren().addAll(
            bloodTypeLabel, bloodTypeCombo,
            locationLabel, locationCombo,
            searchLabel, searchField
        );

        return controls;
    }

    private boolean isDoctorUser() {
        // Check if current user is a doctor or admin
        return mainController.isAdmin() || 
               mainController.getCurrentUser().toLowerCase().contains("doctor") ||
               mainController.getCurrentUser().toLowerCase().contains("dr.");
    }

    private void loadSampleData() {
        allDonors = new ArrayList<>();
        
        allDonors.add(new Donor(1, "Rahul Sharma", "A+", "Mumbai", "+91-9876543210", "2024-01-15", "rahul@email.com"));
        allDonors.add(new Donor(2, "Priya Patel", "B+", "Delhi", "+91-9876543211", "2024-01-10", "priya@email.com"));
        allDonors.add(new Donor(3, "Amit Kumar", "O+", "Bangalore", "+91-9876543212", "2024-01-20", "amit@email.com"));
        allDonors.add(new Donor(4, "Neha Singh", "AB+", "Hyderabad", "+91-9876543213", "2024-01-05", "neha@email.com"));
        allDonors.add(new Donor(5, "Vikram Mehta", "A-", "Ahmedabad", "+91-9876543214", "2024-01-12", "vikram@email.com"));
        allDonors.add(new Donor(6, "Sneha Reddy", "B-", "Mumbai", "+91-9876543215", "2024-01-18", "sneha@email.com"));
        allDonors.add(new Donor(7, "Rajesh Verma", "O-", "Delhi", "+91-9876543216", "2024-01-08", "rajesh@email.com"));
        allDonors.add(new Donor(8, "Anjali Gupta", "AB-", "Bangalore", "+91-9876543217", "2024-01-14", "anjali@email.com"));

        displayDonors(allDonors);
    }

    private void filterDonors() {
        String searchTerm = searchField.getText().toLowerCase();
        String selectedBloodType = bloodTypeCombo.getValue();
        String selectedLocation = locationCombo.getValue();

        List<Donor> filteredDonors = allDonors.stream()
            .filter(donor -> {
                boolean matchesSearch = searchTerm.isEmpty() || 
                    (isDoctorUser() && donor.getName().toLowerCase().contains(searchTerm));
                boolean matchesBloodType = "All Blood Types".equals(selectedBloodType) || 
                    donor.getBloodType().equals(selectedBloodType);
                boolean matchesLocation = "All Locations".equals(selectedLocation) || 
                    donor.getLocation().equals(selectedLocation);
                
                return matchesSearch && matchesBloodType && matchesLocation;
            })
            .collect(Collectors.toList());

        displayDonors(filteredDonors);
    }

    private void displayDonors(List<Donor> donors) {
        donorsContainer.getChildren().clear();

        if (donors.isEmpty()) {
            Label noResults = new Label("No donors found matching your criteria.");
            noResults.setFont(Font.font("System", 16));
            noResults.setTextFill(Color.valueOf("#6b7280"));
            noResults.setAlignment(Pos.CENTER);
            donorsContainer.getChildren().add(noResults);
            return;
        }

        // Create grid layout
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);

        for (int i = 0; i < donors.size(); i++) {
            Donor donor = donors.get(i);
            VBox donorCard = createDonorCard(donor);
            grid.add(donorCard, i % 3, i / 3);
        }

        donorsContainer.getChildren().add(grid);
    }

    private VBox createDonorCard(Donor donor) {
        VBox card = new VBox();
        card.getStyleClass().add("donor-card");
        card.setPadding(new Insets(20));
        card.setSpacing(10);
        card.setPrefWidth(300);
        card.setPrefHeight(200);

        // Header with name and blood type
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(10);

        Label bloodIcon = new Label("🩸");
        bloodIcon.setStyle("-fx-font-size: 24px;");

        // Show name only for doctors
        Label nameLabel;
        if (isDoctorUser()) {
            nameLabel = new Label(donor.getName());
        } else {
            nameLabel = new Label("Donor ID: " + donor.getId());
        }
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

        Label bloodTypeLabel = new Label(donor.getBloodType());
        bloodTypeLabel.getStyleClass().add("blood-type-badge");

        header.getChildren().addAll(bloodIcon, nameLabel, bloodTypeLabel);

        // Details
        VBox details = new VBox();
        details.setSpacing(5);

        // Always show location and last donation
        addDetailRow(details, "Location:", donor.getLocation());
        addDetailRow(details, "Last Donation:", donor.getLastDonation());

        // Show contact details only for doctors
        if (isDoctorUser()) {
            addDetailRow(details, "Contact:", donor.getContactNumber());
            addDetailRow(details, "Email:", donor.getEmail());
        } else {
            addDetailRow(details, "Contact:", "🔒 Contact info hidden");
            addDetailRow(details, "Email:", "🔒 Email hidden");
        }

        // Contact button (only for doctors)
        if (isDoctorUser()) {
            Button contactButton = new Button("📞 Contact Donor");
            contactButton.getStyleClass().add("contact-button");
            contactButton.setOnAction(e -> {
                mainController.showNotification("Contact", 
                    "Contacting " + donor.getName() + " at " + donor.getContactNumber());
            });
            card.getChildren().addAll(header, details, contactButton);
        } else {
            Button requestContactButton = new Button("🔐 Request Contact (Doctors Only)");
            requestContactButton.getStyleClass().add("secondary-button");
            requestContactButton.setOnAction(e -> {
                mainController.showNotification("Access Restricted", 
                    "Only authorized medical professionals can access donor contact information. " +
                    "Please contact your hospital administrator for access.");
            });
            card.getChildren().addAll(header, details, requestContactButton);
        }

        return card;
    }

    private void addDetailRow(VBox container, String label, String value) {
        HBox row = new HBox();
        row.setSpacing(10);
        row.setAlignment(Pos.CENTER_LEFT);

        Label labelNode = new Label(label);
        labelNode.setFont(Font.font("System", FontWeight.BOLD, 12));
        labelNode.setTextFill(Color.valueOf("#6b7280"));
        labelNode.setPrefWidth(80);

        Label valueNode = new Label(value);
        valueNode.setFont(Font.font("System", 12));
        valueNode.setTextFill(Color.valueOf("#1f2937"));

        row.getChildren().addAll(labelNode, valueNode);
        container.getChildren().add(row);
    }

    public VBox getBloodDonorsView() {
        return bloodDonorsView;
    }

    // Inner class for donor data
    private static class Donor {
        private int id;
        private String name;
        private String bloodType;
        private String location;
        private String contactNumber;
        private String lastDonation;
        private String email;

        public Donor(int id, String name, String bloodType, String location, 
                    String contactNumber, String lastDonation, String email) {
            this.id = id;
            this.name = name;
            this.bloodType = bloodType;
            this.location = location;
            this.contactNumber = contactNumber;
            this.lastDonation = lastDonation;
            this.email = email;
        }

        // Getters
        public int getId() { return id; }
        public String getName() { return name; }
        public String getBloodType() { return bloodType; }
        public String getLocation() { return location; }
        public String getContactNumber() { return contactNumber; }
        public String getLastDonation() { return lastDonation; }
        public String getEmail() { return email; }
    }
}
