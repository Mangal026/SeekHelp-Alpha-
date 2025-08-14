package com.seekhelp.desktop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.*;

public class OrganDonorsController {

    private MainController mainController;
    private VBox organDonorsView;
    private ScrollPane scrollPane;
    private TableView<OrganDonor> donorsTable;
    private ComboBox<String> organTypeFilter;
    private ComboBox<String> bloodTypeFilter;
    private TextField locationFilter;

    public OrganDonorsController(MainController mainController) {
        this.mainController = mainController;
        initializeOrganDonorsView();
    }

    private void initializeOrganDonorsView() {
        // Create scroll pane for the entire view
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("organ-donors-scroll-pane");

        organDonorsView = new VBox();
        organDonorsView.getStyleClass().add("organ-donors-container");
        organDonorsView.setPadding(new Insets(30));
        organDonorsView.setSpacing(25);

        // Header
        VBox header = createHeader();

        // Information section
        VBox infoSection = createInfoSection();

        // Search and filters
        VBox searchSection = createSearchSection();

        // Donors table
        VBox tableSection = createTableSection();

        // Register as donor
        VBox registerSection = createRegisterSection();

        // Statistics
        VBox statsSection = createStatsSection();

        organDonorsView.getChildren().addAll(
            header, 
            infoSection, 
            searchSection, 
            tableSection, 
            registerSection, 
            statsSection
        );

        scrollPane.setContent(organDonorsView);
    }

    private VBox createHeader() {
        VBox header = new VBox();
        header.setSpacing(15);

        Label title = new Label("❤️ Organ Donation Network");
        title.setFont(Font.font("System", FontWeight.BOLD, 32));
        title.setTextFill(Color.valueOf("#1f2937"));

        Label subtitle = new Label("Connect with organ donors and save lives");
        subtitle.setFont(Font.font("System", 18));
        subtitle.setTextFill(Color.valueOf("#6b7280"));

        // Impact message
        VBox impactBox = new VBox(10);
        impactBox.setPadding(new Insets(15));
        impactBox.getStyleClass().add("impact-box");
        impactBox.setAlignment(Pos.CENTER);

        Label impactTitle = new Label("💝 Every Donor Saves Lives");
        impactTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        impactTitle.setTextFill(Color.valueOf("#dc2626"));

        Label impactText = new Label(
            "Organ donation is a selfless act that can save up to 8 lives. " +
            "Your decision to donate can give someone a second chance at life."
        );
        impactText.setFont(Font.font("System", 14));
        impactText.setTextFill(Color.valueOf("#374151"));
        impactText.setWrapText(true);
        impactText.setAlignment(Pos.CENTER);

        impactBox.getChildren().addAll(impactTitle, impactText);

        header.getChildren().addAll(title, subtitle, impactBox);
        return header;
    }

    private VBox createInfoSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("info-section");

        Label sectionTitle = new Label("📚 About Organ Donation");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        VBox infoContent = new VBox(15);
        infoContent.getChildren().addAll(
            createInfoCard("What is Organ Donation?", 
                "Organ donation is the process of giving an organ or a part of an organ for transplantation into another person. " +
                "It can be done while the donor is alive (living donation) or after death (deceased donation)."),
            createInfoCard("Organs That Can Be Donated", 
                "• Kidneys • Liver • Heart • Lungs • Pancreas • Intestines • Corneas • Skin • Bone • Bone marrow"),
            createInfoCard("Living vs Deceased Donation", 
                "Living donation involves donating organs while alive (like one kidney or part of liver). " +
                "Deceased donation occurs after brain death or cardiac death."),
            createInfoCard("Privacy Protection", 
                "Donor information is kept confidential. Only authorized medical professionals can access donor details " +
                "when there's a potential match.")
        );

        section.getChildren().addAll(sectionTitle, infoContent);
        return section;
    }

    private VBox createInfoCard(String title, String content) {
        VBox card = new VBox();
        card.setSpacing(8);
        card.setPadding(new Insets(15));
        card.getStyleClass().add("info-card");

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        titleLabel.setTextFill(Color.valueOf("#1f2937"));

        Label contentLabel = new Label(content);
        contentLabel.setFont(Font.font("System", 12));
        contentLabel.setTextFill(Color.valueOf("#6b7280"));
        contentLabel.setWrapText(true);

        card.getChildren().addAll(titleLabel, contentLabel);
        return card;
    }

    private VBox createSearchSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("search-section");

        Label sectionTitle = new Label("🔍 Search Organ Donors");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // Filters
        HBox filters = new HBox(15);
        filters.setAlignment(Pos.CENTER_LEFT);

        // Organ type filter
        VBox organTypeBox = new VBox(5);
        Label organTypeLabel = new Label("Organ Type:");
        organTypeLabel.setFont(Font.font("System", 12));
        organTypeFilter = new ComboBox<>();
        organTypeFilter.getItems().addAll("All", "Kidney", "Liver", "Heart", "Lungs", "Pancreas", "Cornea", "Bone Marrow");
        organTypeFilter.setValue("All");
        organTypeBox.getChildren().addAll(organTypeLabel, organTypeFilter);

        // Blood type filter
        VBox bloodTypeBox = new VBox(5);
        Label bloodTypeLabel = new Label("Blood Type:");
        bloodTypeLabel.setFont(Font.font("System", 12));
        bloodTypeFilter = new ComboBox<>();
        bloodTypeFilter.getItems().addAll("All", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
        bloodTypeFilter.setValue("All");
        bloodTypeBox.getChildren().addAll(bloodTypeLabel, bloodTypeFilter);

        // Location filter
        VBox locationBox = new VBox(5);
        Label locationLabel = new Label("Location:");
        locationLabel.setFont(Font.font("System", 12));
        locationFilter = new TextField();
        locationFilter.setPromptText("Enter city or state");
        locationBox.getChildren().addAll(locationLabel, locationFilter);

        // Search button
        Button searchButton = new Button("🔍 Search");
        searchButton.getStyleClass().add("primary-button");
        searchButton.setOnAction(e -> searchDonors());

        filters.getChildren().addAll(organTypeBox, bloodTypeBox, locationBox, searchButton);

        section.getChildren().addAll(sectionTitle, filters);
        return section;
    }

    private VBox createTableSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("table-section");

        Label sectionTitle = new Label("👥 Available Donors");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // Privacy notice
        VBox privacyNotice = new VBox(5);
        privacyNotice.setPadding(new Insets(10));
        privacyNotice.getStyleClass().add("privacy-notice");

        Label privacyTitle = new Label("🔒 Privacy Protected");
        privacyTitle.setFont(Font.font("System", FontWeight.BOLD, 12));
        privacyTitle.setTextFill(Color.valueOf("#059669"));

        Label privacyText = new Label("Donor names are hidden for privacy. Contact information is only shared with authorized medical professionals.");
        privacyText.setFont(Font.font("System", 10));
        privacyText.setTextFill(Color.valueOf("#6b7280"));
        privacyText.setWrapText(true);

        privacyNotice.getChildren().addAll(privacyTitle, privacyText);

        // Donors table
        donorsTable = new TableView<>();
        donorsTable.setPrefHeight(300);

        // Table columns
        TableColumn<OrganDonor, String> donorIdCol = new TableColumn<>("Donor ID");
        donorIdCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDonorId()));

        TableColumn<OrganDonor, String> organTypeCol = new TableColumn<>("Organ Type");
        organTypeCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getOrganType()));

        TableColumn<OrganDonor, String> bloodTypeCol = new TableColumn<>("Blood Type");
        bloodTypeCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getBloodType()));

        TableColumn<OrganDonor, String> locationCol = new TableColumn<>("Location");
        locationCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getLocation()));

        TableColumn<OrganDonor, String> availabilityCol = new TableColumn<>("Availability");
        availabilityCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAvailability()));

        TableColumn<OrganDonor, String> contactCol = new TableColumn<>("Contact");
        contactCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty("Contact Medical Team"));

        donorsTable.getColumns().addAll(donorIdCol, organTypeCol, bloodTypeCol, locationCol, availabilityCol, contactCol);

        // Load sample data
        loadDonorData();

        section.getChildren().addAll(sectionTitle, privacyNotice, donorsTable);
        return section;
    }

    private VBox createRegisterSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("register-section");

        Label sectionTitle = new Label("📝 Register as Organ Donor");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        Label sectionDesc = new Label("Join the noble cause of saving lives through organ donation:");
        sectionDesc.setFont(Font.font("System", 14));
        sectionDesc.setTextFill(Color.valueOf("#6b7280"));

        // Registration form
        VBox form = new VBox(15);
        form.setPadding(new Insets(20));
        form.getStyleClass().add("registration-form");

        // Personal info
        HBox personalInfo = new HBox(15);
        personalInfo.setAlignment(Pos.CENTER_LEFT);

        VBox nameBox = new VBox(5);
        Label nameLabel = new Label("Full Name:");
        nameLabel.setFont(Font.font("System", 12));
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your full name");
        nameBox.getChildren().addAll(nameLabel, nameField);

        VBox ageBox = new VBox(5);
        Label ageLabel = new Label("Age:");
        ageLabel.setFont(Font.font("System", 12));
        TextField ageField = new TextField();
        ageField.setPromptText("Enter your age");
        ageBox.getChildren().addAll(ageLabel, ageField);

        personalInfo.getChildren().addAll(nameBox, ageBox);

        // Medical info
        HBox medicalInfo = new HBox(15);
        medicalInfo.setAlignment(Pos.CENTER_LEFT);

        VBox bloodTypeBox = new VBox(5);
        Label bloodTypeLabel = new Label("Blood Type:");
        bloodTypeLabel.setFont(Font.font("System", 12));
        ComboBox<String> bloodTypeCombo = new ComboBox<>();
        bloodTypeCombo.getItems().addAll("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
        bloodTypeBox.getChildren().addAll(bloodTypeLabel, bloodTypeCombo);

        VBox locationBox = new VBox(5);
        Label locationLabel = new Label("Location:");
        locationLabel.setFont(Font.font("System", 12));
        TextField locationField = new TextField();
        locationField.setPromptText("Enter your city/state");
        locationBox.getChildren().addAll(locationLabel, locationField);

        medicalInfo.getChildren().addAll(bloodTypeBox, locationBox);

        // Organs to donate
        VBox organsBox = new VBox(10);
        Label organsLabel = new Label("Organs I'm willing to donate:");
        organsLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        CheckBox kidneyCheck = new CheckBox("Kidney");
        CheckBox liverCheck = new CheckBox("Liver");
        CheckBox heartCheck = new CheckBox("Heart");
        CheckBox lungsCheck = new CheckBox("Lungs");
        CheckBox corneaCheck = new CheckBox("Cornea");
        CheckBox boneMarrowCheck = new CheckBox("Bone Marrow");

        organsBox.getChildren().addAll(organsLabel, kidneyCheck, liverCheck, heartCheck, lungsCheck, corneaCheck, boneMarrowCheck);

        // Contact info
        VBox contactBox = new VBox(5);
        Label contactLabel = new Label("Contact Number:");
        contactLabel.setFont(Font.font("System", 12));
        TextField contactField = new TextField();
        contactField.setPromptText("Enter your contact number");
        contactBox.getChildren().addAll(contactLabel, contactField);

        // Register button
        Button registerButton = new Button("❤️ Register as Donor");
        registerButton.getStyleClass().add("primary-button");
        registerButton.setOnAction(e -> registerDonor());

        form.getChildren().addAll(personalInfo, medicalInfo, organsBox, contactBox, registerButton);

        section.getChildren().addAll(sectionTitle, sectionDesc, form);
        return section;
    }

    private VBox createStatsSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("stats-section");

        Label sectionTitle = new Label("📊 Organ Donation Statistics");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(20);
        statsGrid.setVgap(20);

        statsGrid.add(createStatCard("🏥 Registered Donors", "25,000+", "Across India"), 0, 0);
        statsGrid.add(createStatCard("💝 Lives Saved", "15,000+", "Through donations"), 1, 0);
        statsGrid.add(createStatCard("⏰ Waiting List", "50,000+", "Patients waiting"), 2, 0);
        statsGrid.add(createStatCard("🏥 Transplant Centers", "200+", "Authorized centers"), 0, 1);
        statsGrid.add(createStatCard("📱 This Month", "500+", "New registrations"), 1, 1);
        statsGrid.add(createStatCard("🎯 Success Rate", "95%", "Transplant success"), 2, 1);

        section.getChildren().addAll(sectionTitle, statsGrid);
        return section;
    }

    private VBox createStatCard(String title, String value, String subtitle) {
        VBox card = new VBox();
        card.setSpacing(8);
        card.setPadding(new Insets(20));
        card.getStyleClass().add("stat-card");
        card.setAlignment(Pos.CENTER);

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", 14));
        titleLabel.setTextFill(Color.valueOf("#6b7280"));
        titleLabel.setAlignment(Pos.CENTER);

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        valueLabel.setTextFill(Color.valueOf("#1f2937"));
        valueLabel.setAlignment(Pos.CENTER);

        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.setFont(Font.font("System", 12));
        subtitleLabel.setTextFill(Color.valueOf("#059669"));
        subtitleLabel.setAlignment(Pos.CENTER);

        card.getChildren().addAll(titleLabel, valueLabel, subtitleLabel);
        return card;
    }

    private void searchDonors() {
        // Implement search functionality
        mainController.showNotification("Search", "Searching for organ donors...");
    }

    private void registerDonor() {
        mainController.showNotification("Registration", "Thank you for registering as an organ donor! We'll contact you soon.");
    }

    private void loadDonorData() {
        List<OrganDonor> donors = Arrays.asList(
            new OrganDonor("DON001", "Kidney", "A+", "Mumbai", "Available"),
            new OrganDonor("DON002", "Liver", "B+", "Delhi", "Available"),
            new OrganDonor("DON003", "Heart", "O+", "Bangalore", "Available"),
            new OrganDonor("DON004", "Cornea", "AB+", "Hyderabad", "Available"),
            new OrganDonor("DON005", "Bone Marrow", "A-", "Chennai", "Available"),
            new OrganDonor("DON006", "Kidney", "O-", "Kolkata", "Available"),
            new OrganDonor("DON007", "Liver", "B-", "Pune", "Available"),
            new OrganDonor("DON008", "Heart", "AB-", "Ahmedabad", "Available")
        );
        donorsTable.getItems().addAll(donors);
    }

    public VBox getOrganDonorsView() {
        return organDonorsView;
    }

    public ScrollPane getOrganDonorsScrollView() {
        return scrollPane;
    }

    // Inner class for organ donor data
    public static class OrganDonor {
        private String donorId, organType, bloodType, location, availability;

        public OrganDonor(String donorId, String organType, String bloodType, String location, String availability) {
            this.donorId = donorId;
            this.organType = organType;
            this.bloodType = bloodType;
            this.location = location;
            this.availability = availability;
        }

        public String getDonorId() { return donorId; }
        public String getOrganType() { return organType; }
        public String getBloodType() { return bloodType; }
        public String getLocation() { return location; }
        public String getAvailability() { return availability; }
    }
}
