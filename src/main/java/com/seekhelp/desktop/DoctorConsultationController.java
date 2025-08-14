package com.seekhelp.desktop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.*;

public class DoctorConsultationController {

    private MainController mainController;
    private VBox doctorConsultationView;
    private ScrollPane scrollPane;
    private TableView<Doctor> doctorsTable;
    private ComboBox<String> specialtyFilter;
    private ComboBox<String> locationFilter;

    public DoctorConsultationController(MainController mainController) {
        this.mainController = mainController;
        initializeDoctorConsultationView();
    }

    private void initializeDoctorConsultationView() {
        // Create scroll pane for the entire view
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("doctor-consultation-scroll-pane");

        doctorConsultationView = new VBox();
        doctorConsultationView.getStyleClass().add("doctor-consultation-container");
        doctorConsultationView.setPadding(new Insets(30));
        doctorConsultationView.setSpacing(25);

        // Header
        VBox header = createHeader();

        // Services section
        VBox servicesSection = createServicesSection();

        // Search and filters
        VBox searchSection = createSearchSection();

        // Doctors list
        VBox doctorsSection = createDoctorsSection();

        // Booking section
        VBox bookingSection = createBookingSection();

        // Consultation types
        VBox consultationTypesSection = createConsultationTypesSection();

        doctorConsultationView.getChildren().addAll(
            header, 
            servicesSection, 
            searchSection, 
            doctorsSection, 
            bookingSection, 
            consultationTypesSection
        );

        scrollPane.setContent(doctorConsultationView);
    }

    private VBox createHeader() {
        VBox header = new VBox();
        header.setSpacing(15);

        Label title = new Label("👨‍⚕️ Doctor Consultation");
        title.setFont(Font.font("System", FontWeight.BOLD, 32));
        title.setTextFill(Color.valueOf("#1f2937"));

        Label subtitle = new Label("Connect with qualified doctors for online and offline consultations");
        subtitle.setFont(Font.font("System", 18));
        subtitle.setTextFill(Color.valueOf("#6b7280"));

        // Features banner
        VBox featuresBox = new VBox(10);
        featuresBox.setPadding(new Insets(15));
        featuresBox.getStyleClass().add("features-box");
        featuresBox.setAlignment(Pos.CENTER);

        Label featuresTitle = new Label("🏥 Professional Healthcare");
        featuresTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        featuresTitle.setTextFill(Color.valueOf("#2563eb"));

        Label featuresText = new Label(
            "• Verified doctors • Online & offline consultations • Video calls • Prescription services • Follow-up care"
        );
        featuresText.setFont(Font.font("System", 14));
        featuresText.setTextFill(Color.valueOf("#374151"));
        featuresText.setWrapText(true);
        featuresText.setAlignment(Pos.CENTER);

        featuresBox.getChildren().addAll(featuresTitle, featuresText);

        header.getChildren().addAll(title, subtitle, featuresBox);
        return header;
    }

    private VBox createServicesSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("services-section");

        Label sectionTitle = new Label("🩺 Our Services");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        GridPane servicesGrid = new GridPane();
        servicesGrid.setHgap(20);
        servicesGrid.setVgap(20);

        servicesGrid.add(createServiceCard("💻 Online Consultation", "Video call with doctors", "₹500-1000"), 0, 0);
        servicesGrid.add(createServiceCard("🏥 Clinic Visit", "In-person consultation", "₹800-1500"), 1, 0);
        servicesGrid.add(createServiceCard("📋 Health Checkup", "Comprehensive health assessment", "₹2000-5000"), 2, 0);
        servicesGrid.add(createServiceCard("📞 Phone Consultation", "Audio consultation", "₹300-600"), 0, 1);
        servicesGrid.add(createServiceCard("🏠 Home Visit", "Doctor visits your home", "₹1500-3000"), 1, 1);
        servicesGrid.add(createServiceCard("📊 Second Opinion", "Expert medical opinion", "₹1000-2000"), 2, 1);

        section.getChildren().addAll(sectionTitle, servicesGrid);
        return section;
    }

    private VBox createServiceCard(String title, String description, String price) {
        VBox card = new VBox();
        card.setSpacing(8);
        card.setPadding(new Insets(15));
        card.getStyleClass().add("service-card");

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        titleLabel.setTextFill(Color.valueOf("#1f2937"));

        Label descLabel = new Label(description);
        descLabel.setFont(Font.font("System", 12));
        descLabel.setTextFill(Color.valueOf("#6b7280"));
        descLabel.setWrapText(true);

        Label priceLabel = new Label(price);
        priceLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
        priceLabel.setTextFill(Color.valueOf("#059669"));

        card.getChildren().addAll(titleLabel, descLabel, priceLabel);
        return card;
    }

    private VBox createSearchSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("search-section");

        Label sectionTitle = new Label("🔍 Find Doctors");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // Filters
        HBox filters = new HBox(15);
        filters.setAlignment(Pos.CENTER_LEFT);

        // Specialty filter
        VBox specialtyBox = new VBox(5);
        Label specialtyLabel = new Label("Specialty:");
        specialtyLabel.setFont(Font.font("System", 12));
        specialtyFilter = new ComboBox<>();
        specialtyFilter.getItems().addAll("All", "Cardiology", "Dermatology", "Orthopedics", "Pediatrics", "Gynecology", "Neurology", "Psychiatry", "General Medicine");
        specialtyFilter.setValue("All");
        specialtyBox.getChildren().addAll(specialtyLabel, specialtyFilter);

        // Location filter
        VBox locationBox = new VBox(5);
        Label locationLabel = new Label("Location:");
        locationLabel.setFont(Font.font("System", 12));
        locationFilter = new ComboBox<>();
        locationFilter.getItems().addAll("All", "Mumbai", "Delhi", "Bangalore", "Hyderabad", "Chennai", "Kolkata", "Pune", "Ahmedabad");
        locationFilter.setValue("All");
        locationBox.getChildren().addAll(locationLabel, locationFilter);

        // Search button
        Button searchButton = new Button("🔍 Search");
        searchButton.getStyleClass().add("primary-button");
        searchButton.setOnAction(e -> searchDoctors());

        filters.getChildren().addAll(specialtyBox, locationBox, searchButton);

        section.getChildren().addAll(sectionTitle, filters);
        return section;
    }

    private VBox createDoctorsSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("doctors-section");

        Label sectionTitle = new Label("👨‍⚕️ Available Doctors");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // Doctors table
        doctorsTable = new TableView<>();
        doctorsTable.setPrefHeight(300);

        // Table columns
        TableColumn<Doctor, String> nameCol = new TableColumn<>("Doctor Name");
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));

        TableColumn<Doctor, String> specialtyCol = new TableColumn<>("Specialty");
        specialtyCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getSpecialty()));

        TableColumn<Doctor, String> experienceCol = new TableColumn<>("Experience");
        experienceCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getExperience()));

        TableColumn<Doctor, String> locationCol = new TableColumn<>("Location");
        locationCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getLocation()));

        TableColumn<Doctor, String> ratingCol = new TableColumn<>("Rating");
        ratingCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getRating()));

        TableColumn<Doctor, String> feeCol = new TableColumn<>("Consultation Fee");
        feeCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty("₹" + data.getValue().getFee()));

        TableColumn<Doctor, String> bookCol = new TableColumn<>("Book");
        bookCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty("Book Now"));

        doctorsTable.getColumns().addAll(nameCol, specialtyCol, experienceCol, locationCol, ratingCol, feeCol, bookCol);

        // Load sample data
        loadDoctorsData();

        section.getChildren().addAll(sectionTitle, doctorsTable);
        return section;
    }

    private VBox createBookingSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("booking-section");

        Label sectionTitle = new Label("📅 Book Consultation");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        Label sectionDesc = new Label("Schedule your consultation with our qualified doctors:");
        sectionDesc.setFont(Font.font("System", 14));
        sectionDesc.setTextFill(Color.valueOf("#6b7280"));

        // Booking form
        VBox form = new VBox(15);
        form.setPadding(new Insets(20));
        form.getStyleClass().add("booking-form");

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

        // Contact info
        HBox contactInfo = new HBox(15);
        contactInfo.setAlignment(Pos.CENTER_LEFT);

        VBox phoneBox = new VBox(5);
        Label phoneLabel = new Label("Phone:");
        phoneLabel.setFont(Font.font("System", 12));
        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter your phone number");
        phoneBox.getChildren().addAll(phoneLabel, phoneField);

        VBox emailBox = new VBox(5);
        Label emailLabel = new Label("Email:");
        emailLabel.setFont(Font.font("System", 12));
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailBox.getChildren().addAll(emailLabel, emailField);

        contactInfo.getChildren().addAll(phoneBox, emailBox);

        // Consultation details
        VBox consultationBox = new VBox(10);
        Label consultationLabel = new Label("Consultation Type:");
        consultationLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        RadioButton onlineConsultation = new RadioButton("💻 Online Consultation (Video Call)");
        RadioButton clinicVisit = new RadioButton("🏥 Clinic Visit");
        RadioButton phoneConsultation = new RadioButton("📞 Phone Consultation");

        ToggleGroup consultationGroup = new ToggleGroup();
        onlineConsultation.setToggleGroup(consultationGroup);
        clinicVisit.setToggleGroup(consultationGroup);
        phoneConsultation.setToggleGroup(consultationGroup);
        onlineConsultation.setSelected(true);

        consultationBox.getChildren().addAll(consultationLabel, onlineConsultation, clinicVisit, phoneConsultation);

        // Symptoms
        VBox symptomsBox = new VBox(5);
        Label symptomsLabel = new Label("Symptoms/Reason for Visit:");
        symptomsLabel.setFont(Font.font("System", 12));
        TextArea symptomsField = new TextArea();
        symptomsField.setPromptText("Describe your symptoms or reason for consultation");
        symptomsField.setPrefRowCount(3);
        symptomsField.setWrapText(true);
        symptomsBox.getChildren().addAll(symptomsLabel, symptomsField);

        // Book button
        Button bookButton = new Button("📅 Book Consultation");
        bookButton.getStyleClass().add("primary-button");
        bookButton.setOnAction(e -> bookConsultation());

        form.getChildren().addAll(personalInfo, contactInfo, consultationBox, symptomsBox, bookButton);

        section.getChildren().addAll(sectionTitle, sectionDesc, form);
        return section;
    }

    private VBox createConsultationTypesSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("consultation-types-section");

        Label sectionTitle = new Label("🏥 Consultation Types");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        VBox typesContainer = new VBox(15);
        typesContainer.getChildren().addAll(
            createConsultationTypeCard("💻 Online Consultation", 
                "Connect with doctors via video call from the comfort of your home. " +
                "Perfect for follow-ups, minor consultations, and prescription renewals.", 
                "₹500-1000", "Available 24/7"),
            createConsultationTypeCard("🏥 Clinic Visit", 
                "Visit our partner clinics for in-person consultations. " +
                "Ideal for physical examinations and detailed health assessments.", 
                "₹800-1500", "By appointment"),
            createConsultationTypeCard("📞 Phone Consultation", 
                "Audio consultation with doctors for quick medical advice. " +
                "Suitable for simple queries and follow-up discussions.", 
                "₹300-600", "Available 24/7"),
            createConsultationTypeCard("🏠 Home Visit", 
                "Doctor visits your home for consultation. " +
                "Perfect for elderly patients and those with mobility issues.", 
                "₹1500-3000", "By appointment")
        );

        section.getChildren().addAll(sectionTitle, typesContainer);
        return section;
    }

    private VBox createConsultationTypeCard(String title, String description, String price, String availability) {
        VBox card = new VBox();
        card.setSpacing(10);
        card.setPadding(new Insets(20));
        card.getStyleClass().add("consultation-type-card");

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        titleLabel.setTextFill(Color.valueOf("#1f2937"));

        Label descLabel = new Label(description);
        descLabel.setFont(Font.font("System", 12));
        descLabel.setTextFill(Color.valueOf("#6b7280"));
        descLabel.setWrapText(true);

        HBox details = new HBox(20);
        details.setAlignment(Pos.CENTER_LEFT);

        Label priceLabel = new Label("Price: " + price);
        priceLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
        priceLabel.setTextFill(Color.valueOf("#059669"));

        Label availabilityLabel = new Label("Availability: " + availability);
        availabilityLabel.setFont(Font.font("System", 12));
        availabilityLabel.setTextFill(Color.valueOf("#6b7280"));

        details.getChildren().addAll(priceLabel, availabilityLabel);

        card.getChildren().addAll(titleLabel, descLabel, details);
        return card;
    }

    private void searchDoctors() {
        // Implement search functionality
        mainController.showNotification("Search", "Searching for doctors...");
    }

    private void bookConsultation() {
        mainController.showNotification("Booking", "Consultation booking feature will be implemented with payment gateway integration.");
    }

    private void loadDoctorsData() {
        List<Doctor> doctors = Arrays.asList(
            new Doctor("Dr. Priya Sharma", "Cardiology", "15 years", "Mumbai", "4.8", 1200),
            new Doctor("Dr. Rajesh Kumar", "Dermatology", "12 years", "Delhi", "4.7", 1000),
            new Doctor("Dr. Meera Patel", "Pediatrics", "18 years", "Bangalore", "4.9", 800),
            new Doctor("Dr. Amit Singh", "Orthopedics", "20 years", "Hyderabad", "4.6", 1500),
            new Doctor("Dr. Sunita Reddy", "Gynecology", "16 years", "Chennai", "4.8", 1200),
            new Doctor("Dr. Vikram Mehta", "Neurology", "22 years", "Kolkata", "4.9", 2000),
            new Doctor("Dr. Anjali Desai", "Psychiatry", "14 years", "Pune", "4.7", 1000),
            new Doctor("Dr. Ramesh Gupta", "General Medicine", "25 years", "Ahmedabad", "4.8", 800)
        );
        doctorsTable.getItems().addAll(doctors);
    }

    public VBox getDoctorConsultationView() {
        return doctorConsultationView;
    }

    public ScrollPane getDoctorConsultationScrollView() {
        return scrollPane;
    }

    // Inner class for doctor data
    public static class Doctor {
        private String name, specialty, experience, location, rating;
        private int fee;

        public Doctor(String name, String specialty, String experience, String location, String rating, int fee) {
            this.name = name;
            this.specialty = specialty;
            this.experience = experience;
            this.location = location;
            this.rating = rating;
            this.fee = fee;
        }

        public String getName() { return name; }
        public String getSpecialty() { return specialty; }
        public String getExperience() { return experience; }
        public String getLocation() { return location; }
        public String getRating() { return rating; }
        public int getFee() { return fee; }
    }
}
