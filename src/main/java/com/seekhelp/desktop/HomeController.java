package com.seekhelp.desktop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.kordamp.ikonli.javafx.FontIcon;

public class HomeController {

    private MainController mainController;
    private VBox homeView;
    private ScrollPane scrollPane;

    public HomeController(MainController mainController) {
        this.mainController = mainController;
        initializeHomeView();
    }

    private void initializeHomeView() {
        // Create scroll pane for the entire home view
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("home-scroll-pane");

        homeView = new VBox();
        homeView.getStyleClass().add("home-container");
        homeView.setPadding(new Insets(30));
        homeView.setSpacing(30);

        // Welcome header
        VBox welcomeSection = createWelcomeSection();

        // Services grid
        GridPane servicesGrid = createServicesGrid();

        // Emergency banner
        VBox emergencyBanner = createEmergencyBanner();

        // Medical Shop Section
        VBox medicalShopSection = createMedicalShopSection();

        // Prescription Analyzer Section
        VBox prescriptionAnalyzerSection = createPrescriptionAnalyzerSection();

        // Health Tips Section
        VBox healthTipsSection = createHealthTipsSection();

        // Statistics Section
        VBox statisticsSection = createStatisticsSection();

        // Footer
        VBox footerSection = createFooterSection();

        homeView.getChildren().addAll(
            welcomeSection, 
            servicesGrid, 
            emergencyBanner,
            medicalShopSection,
            prescriptionAnalyzerSection,
            healthTipsSection,
            statisticsSection,
            footerSection
        );

        scrollPane.setContent(homeView);
    }

    private VBox createWelcomeSection() {
        VBox welcomeSection = new VBox();
        welcomeSection.setSpacing(15);

        Label welcomeTitle = new Label("Welcome to Seek Help+ India");
        welcomeTitle.setFont(Font.font("System", FontWeight.BOLD, 32));
        welcomeTitle.setTextFill(Color.valueOf("#1f2937")); // Gray-800

        Label welcomeSubtitle = new Label("Find quality medical assistance across India with just a few taps");
        welcomeSubtitle.setFont(Font.font("System", 18));
        welcomeSubtitle.setTextFill(Color.valueOf("#6b7280")); // Gray-500

        // India flag indicator
        HBox flagIndicator = new HBox(5);
        flagIndicator.setAlignment(Pos.CENTER_LEFT);

        Rectangle orangeStripe = new Rectangle(20, 15, Color.valueOf("#ff9933"));
        Rectangle whiteStripe = new Rectangle(20, 15, Color.WHITE);
        whiteStripe.setStroke(Color.valueOf("#d1d5db"));
        Rectangle greenStripe = new Rectangle(20, 15, Color.valueOf("#138808"));

        Label flagText = new Label("Proudly serving all states and union territories of India");
        flagText.setFont(Font.font("System", 14));
        flagText.setTextFill(Color.valueOf("#9ca3af")); // Gray-400

        flagIndicator.getChildren().addAll(orangeStripe, whiteStripe, greenStripe, flagText);

        // Quick stats
        HBox quickStats = new HBox(20);
        quickStats.setAlignment(Pos.CENTER_LEFT);
        quickStats.getChildren().addAll(
            createQuickStat("🏥 Hospitals", "15,000+"),
            createQuickStat("🩸 Donors", "50,000+"),
            createQuickStat("👥 Users", "100,000+"),
            createQuickStat("🚨 Emergencies", "24/7")
        );

        welcomeSection.getChildren().addAll(welcomeTitle, welcomeSubtitle, flagIndicator, quickStats);
        return welcomeSection;
    }

    private VBox createQuickStat(String label, String value) {
        VBox stat = new VBox(5);
        stat.setAlignment(Pos.CENTER);
        stat.setPadding(new Insets(10));
        stat.getStyleClass().add("quick-stat");

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
        valueLabel.setTextFill(Color.valueOf("#1f2937"));

        Label labelText = new Label(label);
        labelText.setFont(Font.font("System", 12));
        labelText.setTextFill(Color.valueOf("#6b7280"));

        stat.getChildren().addAll(valueLabel, labelText);
        return stat;
    }

    private GridPane createServicesGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);

        // Service cards
        ServiceCard bloodDonorsCard = new ServiceCard(
            "Blood Donors",
            "Find donors for your blood type",
            "🩸",
            Color.valueOf("#dc2626"), // Red
            Color.valueOf("#fef2f2"), // Red-50
            () -> mainController.showBloodDonorsPage()
        );

        ServiceCard organDonorsCard = new ServiceCard(
            "Organ Donors",
            "Connect with organ donation services",
            "❤️",
            Color.valueOf("#ec4899"), // Pink
            Color.valueOf("#fdf2f8"), // Pink-50
            () -> mainController.showOrganDonorsPage()
        );

        ServiceCard stemCellCard = new ServiceCard(
            "Stem Cell Donors",
            "Find stem cell donation options",
            "🧬",
            Color.valueOf("#a855f7"), // Purple
            Color.valueOf("#faf5ff"), // Purple-50
            () -> mainController.showStemCellPage()
        );

        ServiceCard hospitalsCard = new ServiceCard(
            "Nearby Hospitals",
            "Find hospitals in your area with real-time data",
            "🏥",
            Color.valueOf("#15803d"), // Forest green
            Color.valueOf("#f0fdf4"), // Green-50
            () -> mainController.showHospitalsPage()
        );

        ServiceCard symptomCheckerCard = new ServiceCard(
            "AI Symptom Checker",
            "Advanced AI-powered symptom analysis with ChatGPT",
            "🛡️",
            Color.valueOf("#d97706"), // Amber
            Color.valueOf("#fffbeb"), // Amber-50
            () -> mainController.showSymptomCheckerPage()
        );

        ServiceCard consultDoctorCard = new ServiceCard(
            "Consult a Doctor",
            "Premium online consultations",
            "👤",
            Color.valueOf("#2563eb"), // Blue
            Color.valueOf("#eff6ff"), // Blue-50
            () -> mainController.showDoctorConsultationPage()
        );

        ServiceCard aiBotCard = new ServiceCard(
            "AI Medical Bot",
            "Chat with our medical assistant",
            "🤖",
            Color.valueOf("#7c3aed"), // Indigo
            Color.valueOf("#f5f3ff"), // Indigo-50
            () -> mainController.showAIMedicalBotPage()
        );

        ServiceCard govtHospitalsCard = new ServiceCard(
            "Govt Hospitals",
            "Find affordable government care",
            "🏦",
            Color.valueOf("#475569"), // Slate
            Color.valueOf("#f8fafc"), // Slate-50
            () -> mainController.showHospitalsPage()
        );

        ServiceCard medicalShopCard = new ServiceCard(
            "Medical Shop",
            "Buy medicines and health products",
            "💊",
            Color.valueOf("#059669"), // Emerald
            Color.valueOf("#ecfdf5"), // Emerald-50
            () -> mainController.showMedicalShopPage()
        );

        ServiceCard prescriptionAnalyzerCard = new ServiceCard(
            "Prescription Analyzer",
            "AI-powered prescription analysis",
            "📋",
            Color.valueOf("#be185d"), // Rose
            Color.valueOf("#fff1f2"), // Rose-50
            () -> mainController.showPrescriptionAnalyzerPage()
        );

        // Add cards to grid (3x4 layout)
        grid.add(bloodDonorsCard.getCard(), 0, 0);
        grid.add(organDonorsCard.getCard(), 1, 0);
        grid.add(stemCellCard.getCard(), 2, 0);
        grid.add(hospitalsCard.getCard(), 0, 1);
        grid.add(symptomCheckerCard.getCard(), 1, 1);
        grid.add(consultDoctorCard.getCard(), 2, 1);
        grid.add(aiBotCard.getCard(), 0, 2);
        grid.add(govtHospitalsCard.getCard(), 1, 2);
        grid.add(medicalShopCard.getCard(), 2, 2);
        grid.add(prescriptionAnalyzerCard.getCard(), 0, 3);

        return grid;
    }

    private VBox createMedicalShopSection() {
        VBox section = new VBox();
        section.setSpacing(20);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("feature-section");

        Label sectionTitle = new Label("💊 Medical Shop - Your Health Partner");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 24));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        Label sectionDesc = new Label("Buy medicines, health products, and get prescription verification");
        sectionDesc.setFont(Font.font("System", 16));
        sectionDesc.setTextFill(Color.valueOf("#6b7280"));

        HBox features = new HBox(20);
        features.setAlignment(Pos.CENTER_LEFT);

        features.getChildren().addAll(
            createFeatureItem("🛒 Daily Medicines", "Over-the-counter medicines"),
            createFeatureItem("📋 Prescription Required", "Upload prescription for verification"),
            createFeatureItem("🚚 Fast Delivery", "Same day delivery available"),
            createFeatureItem("💰 Best Prices", "Competitive pricing guaranteed")
        );

        Button shopButton = new Button("🛒 Visit Medical Shop");
        shopButton.getStyleClass().add("primary-button");
        shopButton.setOnAction(e -> mainController.showMedicalShopPage());

        section.getChildren().addAll(sectionTitle, sectionDesc, features, shopButton);
        return section;
    }

    private VBox createPrescriptionAnalyzerSection() {
        VBox section = new VBox();
        section.setSpacing(20);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("feature-section");

        Label sectionTitle = new Label("📋 AI Prescription Analyzer");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 24));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        Label sectionDesc = new Label("Upload your prescription and get AI-powered analysis with medicine details");
        sectionDesc.setFont(Font.font("System", 16));
        sectionDesc.setTextFill(Color.valueOf("#6b7280"));

        HBox features = new HBox(20);
        features.setAlignment(Pos.CENTER_LEFT);

        features.getChildren().addAll(
            createFeatureItem("🤖 AI Analysis", "Powered by advanced AI"),
            createFeatureItem("💊 Medicine Info", "Detailed medicine information"),
            createFeatureItem("⚠️ Side Effects", "Know potential side effects"),
            createFeatureItem("📱 Easy Upload", "Just click and upload")
        );

        Button analyzerButton = new Button("📋 Analyze Prescription");
        analyzerButton.getStyleClass().add("primary-button");
        analyzerButton.setOnAction(e -> mainController.showPrescriptionAnalyzerPage());

        section.getChildren().addAll(sectionTitle, sectionDesc, features, analyzerButton);
        return section;
    }

    private VBox createHealthTipsSection() {
        VBox section = new VBox();
        section.setSpacing(20);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("feature-section");

        Label sectionTitle = new Label("💡 Daily Health Tips");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 24));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        VBox tipsContainer = new VBox(15);
        tipsContainer.getChildren().addAll(
            createHealthTip("🌅 Start your day with a glass of warm water with lemon", "Boosts metabolism and immunity"),
            createHealthTip("🏃‍♂️ Exercise for at least 30 minutes daily", "Improves cardiovascular health"),
            createHealthTip("🥗 Include more vegetables in your diet", "Provides essential nutrients"),
            createHealthTip("😴 Get 7-8 hours of quality sleep", "Essential for body repair and recovery"),
            createHealthTip("💧 Stay hydrated throughout the day", "Drink at least 8 glasses of water")
        );

        section.getChildren().addAll(sectionTitle, tipsContainer);
        return section;
    }

    private HBox createHealthTip(String tip, String benefit) {
        HBox tipBox = new HBox(15);
        tipBox.setAlignment(Pos.CENTER_LEFT);
        tipBox.setPadding(new Insets(10));
        tipBox.getStyleClass().add("health-tip");

        Label tipLabel = new Label(tip);
        tipLabel.setFont(Font.font("System", 14));
        tipLabel.setWrapText(true);

        Label benefitLabel = new Label(benefit);
        benefitLabel.setFont(Font.font("System", 12));
        benefitLabel.setTextFill(Color.valueOf("#059669"));
        benefitLabel.setWrapText(true);

        VBox textContent = new VBox(5);
        textContent.getChildren().addAll(tipLabel, benefitLabel);

        tipBox.getChildren().add(textContent);
        return tipBox;
    }

    private VBox createStatisticsSection() {
        VBox section = new VBox();
        section.setSpacing(20);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("feature-section");

        Label sectionTitle = new Label("📊 Seek Help+ Impact");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 24));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(20);
        statsGrid.setVgap(20);

        statsGrid.add(createStatCard("🏥 Hospitals Connected", "15,000+", "+12%"), 0, 0);
        statsGrid.add(createStatCard("🩸 Lives Saved", "50,000+", "+25%"), 1, 0);
        statsGrid.add(createStatCard("👥 Active Users", "100,000+", "+18%"), 2, 0);
        statsGrid.add(createStatCard("🚨 Emergencies Handled", "10,000+", "+30%"), 0, 1);
        statsGrid.add(createStatCard("💊 Medicines Delivered", "25,000+", "+15%"), 1, 1);
        statsGrid.add(createStatCard("🤖 AI Consultations", "75,000+", "+40%"), 2, 1);

        section.getChildren().addAll(sectionTitle, statsGrid);
        return section;
    }

    private VBox createStatCard(String title, String value, String growth) {
        VBox card = new VBox();
        card.setSpacing(10);
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

        Label growthLabel = new Label(growth);
        growthLabel.setFont(Font.font("System", 12));
        growthLabel.setTextFill(Color.valueOf("#059669"));
        growthLabel.setAlignment(Pos.CENTER);

        card.getChildren().addAll(titleLabel, valueLabel, growthLabel);
        return card;
    }

    private VBox createFooterSection() {
        VBox footer = new VBox();
        footer.setSpacing(15);
        footer.setPadding(new Insets(30));
        footer.getStyleClass().add("footer-section");
        footer.setAlignment(Pos.CENTER);

        Label footerTitle = new Label("Seek Help+ - Your Health Companion");
        footerTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        footerTitle.setTextFill(Color.valueOf("#1f2937"));

        Label creatorInfo = new Label("Created with ❤️ by Mangal Pandey - Founder & Sole Creator");
        creatorInfo.setFont(Font.font("System", 16));
        creatorInfo.setTextFill(Color.valueOf("#6b7280"));

        Label mission = new Label("Mission: Making healthcare accessible to every Indian");
        mission.setFont(Font.font("System", 14));
        mission.setTextFill(Color.valueOf("#9ca3af"));

        HBox contactInfo = new HBox(20);
        contactInfo.setAlignment(Pos.CENTER);
        contactInfo.getChildren().addAll(
            createContactItem("📧 support@seekhelp.com"),
            createContactItem("📞 1800-SEEK-HELP"),
            createContactItem("🌐 www.seekhelp.in")
        );

        footer.getChildren().addAll(footerTitle, creatorInfo, mission, contactInfo);
        return footer;
    }

    private Label createContactItem(String text) {
        Label item = new Label(text);
        item.setFont(Font.font("System", 12));
        item.setTextFill(Color.valueOf("#6b7280"));
        return item;
    }

    private VBox createFeatureItem(String title, String description) {
        VBox item = new VBox(5);
        item.setAlignment(Pos.CENTER);
        item.setPadding(new Insets(15));
        item.getStyleClass().add("feature-item");

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        titleLabel.setAlignment(Pos.CENTER);

        Label descLabel = new Label(description);
        descLabel.setFont(Font.font("System", 12));
        descLabel.setTextFill(Color.valueOf("#6b7280"));
        descLabel.setAlignment(Pos.CENTER);
        descLabel.setWrapText(true);

        item.getChildren().addAll(titleLabel, descLabel);
        return item;
    }

    private VBox createEmergencyBanner() {
        VBox banner = new VBox();
        banner.getStyleClass().add("emergency-banner");
        banner.setPadding(new Insets(20));
        banner.setSpacing(10);
        banner.setAlignment(Pos.CENTER);

        HBox bannerContent = new HBox(15);
        bannerContent.setAlignment(Pos.CENTER);

        Label emergencyIcon = new Label("🚨");
        emergencyIcon.setStyle("-fx-font-size: 32px;");

        VBox textContent = new VBox(5);
        textContent.setAlignment(Pos.CENTER_LEFT);

        Label emergencyTitle = new Label("Emergency Services");
        emergencyTitle.setFont(Font.font("System", FontWeight.BOLD, 18));
        emergencyTitle.setTextFill(Color.WHITE);

        Label emergencySubtitle = new Label("Call 108 for immediate medical assistance");
        emergencySubtitle.setFont(Font.font("System", 14));
        emergencySubtitle.setTextFill(Color.WHITE);

        textContent.getChildren().addAll(emergencyTitle, emergencySubtitle);

        Button callButton = new Button("Call Now");
        callButton.getStyleClass().add("emergency-button");
        callButton.setOnAction(e -> {
            mainController.showNotification("Emergency", "Dialing emergency services...");
        });

        bannerContent.getChildren().addAll(emergencyIcon, textContent, callButton);
        banner.getChildren().add(bannerContent);

        return banner;
    }

    public VBox getHomeView() {
        return homeView;
    }

    public ScrollPane getHomeScrollView() {
        return scrollPane;
    }

    // Inner class for service cards
    private static class ServiceCard {
        private VBox card;
        private Runnable action;
        private boolean isDisabled;
        private boolean isPremium;

        public ServiceCard(String title, String description, String iconCode, 
                          Color iconColor, Color bgColor, Runnable action) {
            this(title, description, iconCode, iconColor, bgColor, action, false, false);
        }

        public ServiceCard(String title, String description, String iconCode, 
                          Color iconColor, Color bgColor, Runnable action, boolean isDisabled) {
            this(title, description, iconCode, iconColor, bgColor, action, isDisabled, false);
        }

        public ServiceCard(String title, String description, String iconCode, 
                          Color iconColor, Color bgColor, Runnable action, boolean isDisabled, boolean isPremium) {
            this.action = action;
            this.isDisabled = isDisabled;
            this.isPremium = isPremium;
            createCard(title, description, iconCode, iconColor, bgColor);
        }

        private void createCard(String title, String description, String iconCode, Color iconColor, Color bgColor) {
            card = new VBox();
            card.getStyleClass().add("service-card");
            card.setPadding(new Insets(20));
            card.setSpacing(15);
            card.setAlignment(Pos.CENTER);
            card.setPrefWidth(280);
            card.setPrefHeight(220);

            if (isDisabled) {
                card.getStyleClass().add("disabled");
            }

            // Icon container
            VBox iconContainer = new VBox();
            iconContainer.setAlignment(Pos.CENTER);
            iconContainer.setPrefSize(60, 60);
            iconContainer.setBackground(new Background(new BackgroundFill(bgColor, CornerRadii.EMPTY, Insets.EMPTY)));

            Label icon = new Label(iconCode);
            icon.setStyle("-fx-font-size: 30px;");
            iconContainer.getChildren().add(icon);

            // Title
            Label titleLabel = new Label(title);
            titleLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
            titleLabel.setTextFill(Color.valueOf("#1f2937"));
            titleLabel.setAlignment(Pos.CENTER);

            // Description
            Label descLabel = new Label(description);
            descLabel.setFont(Font.font("System", 12));
            descLabel.setTextFill(Color.valueOf("#6b7280"));
            descLabel.setWrapText(true);
            descLabel.setAlignment(Pos.CENTER);

            // Button
            Button actionButton = new Button();
            if (isPremium) {
                actionButton.setText("Premium");
                actionButton.getStyleClass().add("premium-button");
                Label lockIcon = new Label("🔒");
                lockIcon.setStyle("-fx-font-size: 16px;");
                actionButton.setGraphic(lockIcon);
            } else if (isDisabled) {
                actionButton.setText("Coming Soon");
                actionButton.getStyleClass().add("disabled-button");
                actionButton.setDisable(true);
            } else {
                actionButton.setText("Explore");
                actionButton.getStyleClass().add("explore-button");
                actionButton.setOnAction(e -> {
                    if (action != null) {
                        action.run();
                    }
                });
            }

            card.getChildren().addAll(iconContainer, titleLabel, descLabel, actionButton);
        }

        public VBox getCard() {
            return card;
        }
    }
}
