package com.seekhelp.desktop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import java.util.*;

public class EmergencyController {

    private MainController mainController;
    private VBox emergencyView;
    private ScrollPane scrollPane;
    private Button sosButton;
    private Label statusLabel;
    private Timeline sosAnimation;
    private boolean isEmergencyActive = false;

    public EmergencyController(MainController mainController) {
        this.mainController = mainController;
        initializeEmergencyView();
    }

    private void initializeEmergencyView() {
        emergencyView = new VBox();
        emergencyView.getStyleClass().add("emergency-container");
        emergencyView.setPadding(new Insets(30));
        emergencyView.setSpacing(20);

        // Header
        VBox header = createHeader();

        // Main content
        VBox content = createContent();

        // Scroll pane for content
        scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefHeight(600);

        emergencyView.getChildren().addAll(header, scrollPane);
    }

    private VBox createHeader() {
        VBox header = new VBox();
        header.setSpacing(10);

        Label title = new Label("🚨 Emergency Services");
        title.setFont(Font.font("System", FontWeight.BOLD, 28));
        title.setTextFill(Color.valueOf("#dc2626"));

        Label subtitle = new Label("24/7 Emergency Medical Assistance");
        subtitle.setFont(Font.font("System", 16));
        subtitle.setTextFill(Color.valueOf("#6b7280"));

        header.getChildren().addAll(title, subtitle);
        return header;
    }

    private VBox createContent() {
        VBox content = new VBox();
        content.setSpacing(20);
        content.setPadding(new Insets(20));

        // SOS Emergency Button
        VBox sosSection = createSOSSection();

        // Emergency Contacts
        VBox contactsSection = createEmergencyContactsSection();

        // Emergency Services
        VBox servicesSection = createEmergencyServicesSection();

        // First Aid Guide
        VBox firstAidSection = createFirstAidSection();

        // Emergency Instructions
        VBox instructionsSection = createEmergencyInstructionsSection();

        content.getChildren().addAll(sosSection, contactsSection, servicesSection, firstAidSection, instructionsSection);
        return content;
    }

    private VBox createSOSSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setAlignment(Pos.CENTER);

        Label sectionTitle = new Label("🚨 SOS EMERGENCY");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 24));
        sectionTitle.setTextFill(Color.valueOf("#dc2626"));

        // SOS Button
        sosButton = new Button("🚨 SOS - CALL AMBULANCE");
        sosButton.getStyleClass().add("sos-button");
        sosButton.setPrefSize(300, 80);
        sosButton.setFont(Font.font("System", FontWeight.BOLD, 18));
        sosButton.setOnAction(e -> activateSOS());

        // Status Label
        statusLabel = new Label("Emergency services are ready");
        statusLabel.setFont(Font.font("System", 14));
        statusLabel.setTextFill(Color.valueOf("#15803d"));

        // Emergency Info
        VBox emergencyInfo = new VBox();
        emergencyInfo.setSpacing(10);
        emergencyInfo.setAlignment(Pos.CENTER);

        Label infoTitle = new Label("Emergency Numbers:");
        infoTitle.setFont(Font.font("System", FontWeight.BOLD, 16));

        HBox numbersBox = new HBox(20);
        numbersBox.setAlignment(Pos.CENTER);

        VBox ambulanceBox = createEmergencyNumberBox("🚑 Ambulance", "108", "National Emergency");
        VBox policeBox = createEmergencyNumberBox("👮 Police", "100", "Police Emergency");
        VBox fireBox = createEmergencyNumberBox("🚒 Fire", "101", "Fire Emergency");

        numbersBox.getChildren().addAll(ambulanceBox, policeBox, fireBox);

        emergencyInfo.getChildren().addAll(infoTitle, numbersBox);

        section.getChildren().addAll(sectionTitle, sosButton, statusLabel, emergencyInfo);
        return section;
    }

    private VBox createEmergencyNumberBox(String service, String number, String description) {
        VBox box = new VBox();
        box.setSpacing(5);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(15));
        box.getStyleClass().add("emergency-number-box");

        Label serviceLabel = new Label(service);
        serviceLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        Label numberLabel = new Label(number);
        numberLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
        numberLabel.setTextFill(Color.valueOf("#dc2626"));

        Label descLabel = new Label(description);
        descLabel.setFont(Font.font("System", 10));
        descLabel.setTextFill(Color.valueOf("#6b7280"));

        Button callButton = new Button("📞 Call Now");
        callButton.getStyleClass().add("call-button");
        callButton.setOnAction(e -> callEmergency(number, service));

        box.getChildren().addAll(serviceLabel, numberLabel, descLabel, callButton);
        return box;
    }

    private VBox createEmergencyContactsSection() {
        VBox section = new VBox();
        section.setSpacing(15);

        Label sectionTitle = new Label("📞 Emergency Contacts");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // Personal emergency contacts
        VBox contactsList = new VBox();
        contactsList.setSpacing(10);

        String[][] contacts = {
            {"👨‍⚕️ Dr. Sharma", "+91-9876543210", "Family Doctor"},
            {"🏥 City Hospital", "+91-22-12345678", "Nearest Hospital"},
            {"👨‍👩‍👧‍👦 Family Contact", "+91-9876543211", "Emergency Contact"},
            {"🚑 Ambulance Service", "+91-22-108", "Local Ambulance"}
        };

        for (String[] contact : contacts) {
            HBox contactRow = new HBox(15);
            contactRow.setAlignment(Pos.CENTER_LEFT);
            contactRow.setPadding(new Insets(10));
            contactRow.getStyleClass().add("contact-row");

            VBox contactInfo = new VBox();
            contactInfo.setSpacing(2);

            Label nameLabel = new Label(contact[0]);
            nameLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

            Label phoneLabel = new Label(contact[1]);
            phoneLabel.setFont(Font.font("System", 12));
            phoneLabel.setTextFill(Color.valueOf("#6b7280"));

            Label descLabel = new Label(contact[2]);
            descLabel.setFont(Font.font("System", 10));
            descLabel.setTextFill(Color.valueOf("#9ca3af"));

            contactInfo.getChildren().addAll(nameLabel, phoneLabel, descLabel);

            Button callButton = new Button("📞 Call");
            callButton.getStyleClass().add("contact-call-button");
            callButton.setOnAction(e -> callEmergency(contact[1], contact[0]));

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            contactRow.getChildren().addAll(contactInfo, spacer, callButton);
            contactsList.getChildren().add(contactRow);
        }

        section.getChildren().addAll(sectionTitle, contactsList);
        return section;
    }

    private VBox createEmergencyServicesSection() {
        VBox section = new VBox();
        section.setSpacing(15);

        Label sectionTitle = new Label("🏥 Emergency Services");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        GridPane servicesGrid = new GridPane();
        servicesGrid.setHgap(15);
        servicesGrid.setVgap(15);

        String[][] services = {
            {"🚑 Ambulance", "Emergency medical transport", "Find nearest ambulance"},
            {"🏥 Trauma Center", "24/7 emergency care", "Locate trauma centers"},
            {"💊 Pharmacy", "Emergency medications", "Find 24/7 pharmacies"},
            {"🩺 Telemedicine", "Online emergency consultation", "Connect with doctors"},
            {"🚁 Air Ambulance", "Emergency air transport", "Air ambulance services"},
            {"🩸 Blood Bank", "Emergency blood supply", "Find blood banks"}
        };

        for (int i = 0; i < services.length; i++) {
            VBox serviceCard = createServiceCard(services[i][0], services[i][1], services[i][2]);
            servicesGrid.add(serviceCard, i % 3, i / 3);
        }

        section.getChildren().addAll(sectionTitle, servicesGrid);
        return section;
    }

    private VBox createServiceCard(String title, String description, String action) {
        VBox card = new VBox();
        card.setSpacing(10);
        card.setPadding(new Insets(15));
        card.getStyleClass().add("service-card");
        card.setPrefWidth(200);
        card.setPrefHeight(150);

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

        Label descLabel = new Label(description);
        descLabel.setWrapText(true);
        descLabel.setFont(Font.font("System", 12));
        descLabel.setTextFill(Color.valueOf("#6b7280"));

        Button actionButton = new Button(action);
        actionButton.getStyleClass().add("service-action-button");
        actionButton.setMaxWidth(Double.MAX_VALUE);
        actionButton.setOnAction(e -> handleServiceAction(title));

        card.getChildren().addAll(titleLabel, descLabel, actionButton);
        return card;
    }

    private VBox createFirstAidSection() {
        VBox section = new VBox();
        section.setSpacing(15);

        Label sectionTitle = new Label("🩹 First Aid Guide");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        Accordion firstAidAccordion = new Accordion();

        String[][] firstAidTopics = {
            {"🩸 Bleeding", "Apply direct pressure with clean cloth. Elevate if possible. Call emergency if severe."},
            {"🔥 Burns", "Cool with running water for 10-20 minutes. Don't pop blisters. Cover with sterile bandage."},
            {"💔 Heart Attack", "Call emergency immediately. Have person sit down. Loosen tight clothing."},
            {"🫁 Choking", "Perform Heimlich maneuver. Call emergency if person can't breathe or speak."},
            {"🦴 Broken Bones", "Don't move the person. Immobilize the area. Call emergency immediately."},
            {"😵 Unconsciousness", "Check breathing. Call emergency. Perform CPR if trained."}
        };

        for (String[] topic : firstAidTopics) {
            TitledPane pane = new TitledPane();
            pane.setText(topic[0]);
            pane.setExpanded(false);

            Label content = new Label(topic[1]);
            content.setWrapText(true);
            content.setPadding(new Insets(10));

            pane.setContent(content);
            firstAidAccordion.getPanes().add(pane);
        }

        section.getChildren().addAll(sectionTitle, firstAidAccordion);
        return section;
    }

    private VBox createEmergencyInstructionsSection() {
        VBox section = new VBox();
        section.setSpacing(15);

        Label sectionTitle = new Label("📋 Emergency Instructions");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        VBox instructions = new VBox();
        instructions.setSpacing(10);

        String[] steps = {
            "1. Stay calm and assess the situation",
            "2. Call emergency services immediately if needed",
            "3. Provide clear information about the emergency",
            "4. Follow dispatcher instructions",
            "5. Keep the person comfortable and safe",
            "6. Don't move seriously injured people",
            "7. Apply first aid if trained",
            "8. Stay with the person until help arrives"
        };

        for (String step : steps) {
            Label stepLabel = new Label(step);
            stepLabel.setFont(Font.font("System", 14));
            stepLabel.setWrapText(true);
            instructions.getChildren().add(stepLabel);
        }

        section.getChildren().addAll(sectionTitle, instructions);
        return section;
    }

    private void activateSOS() {
        if (isEmergencyActive) {
            deactivateSOS();
            return;
        }

        isEmergencyActive = true;
        sosButton.setText("🛑 CANCEL SOS");
        sosButton.getStyleClass().remove("sos-button");
        sosButton.getStyleClass().add("sos-button-active");
        statusLabel.setText("🚨 EMERGENCY ACTIVATED - Calling ambulance...");
        statusLabel.setTextFill(Color.valueOf("#dc2626"));

        // Start SOS animation
        sosAnimation = new Timeline(
            new KeyFrame(Duration.ZERO, e -> sosButton.setStyle("-fx-background-color: #dc2626; -fx-text-fill: white;")),
            new KeyFrame(Duration.millis(500), e -> sosButton.setStyle("-fx-background-color: #991b1b; -fx-text-fill: white;")),
            new KeyFrame(Duration.millis(1000), e -> sosButton.setStyle("-fx-background-color: #dc2626; -fx-text-fill: white;"))
        );
        sosAnimation.setCycleCount(Timeline.INDEFINITE);
        sosAnimation.play();

        // Simulate emergency call
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                javafx.application.Platform.runLater(() -> {
                    mainController.showNotification("🚨 Emergency Services", 
                        "Ambulance dispatched! ETA: 8-12 minutes. Stay calm and follow instructions.");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void deactivateSOS() {
        isEmergencyActive = false;
        sosButton.setText("🚨 SOS - CALL AMBULANCE");
        sosButton.getStyleClass().remove("sos-button-active");
        sosButton.getStyleClass().add("sos-button");
        statusLabel.setText("Emergency services are ready");
        statusLabel.setTextFill(Color.valueOf("#15803d"));

        if (sosAnimation != null) {
            sosAnimation.stop();
        }

        mainController.showNotification("Emergency Cancelled", "SOS emergency has been cancelled.");
    }

    private void callEmergency(String number, String service) {
        mainController.showNotification("📞 Calling " + service, 
            "Dialing " + number + "...\nPlease have your emergency details ready.");
    }

    private void handleServiceAction(String service) {
        switch (service) {
            case "🚑 Ambulance":
                mainController.showNotification("Ambulance Service", "Finding nearest ambulance service...");
                break;
            case "🏥 Trauma Center":
                mainController.showHospitalsPage();
                break;
            case "💊 Pharmacy":
                mainController.showNotification("Pharmacy", "Finding 24/7 pharmacies in your area...");
                break;
            case "🩺 Telemedicine":
                mainController.showAIMedicalBotPage();
                break;
            case "🚁 Air Ambulance":
                mainController.showNotification("Air Ambulance", "Contacting air ambulance services...");
                break;
            case "🩸 Blood Bank":
                mainController.showNotification("Blood Bank", "Finding nearest blood banks...");
                break;
        }
    }

    public VBox getEmergencyView() {
        return emergencyView;
    }
}
