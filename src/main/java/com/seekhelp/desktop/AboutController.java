package com.seekhelp.desktop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.kordamp.ikonli.javafx.FontIcon;

public class AboutController {

    private MainController mainController;
    private VBox aboutView;
    private ScrollPane scrollPane;

    public AboutController(MainController mainController) {
        this.mainController = mainController;
        initializeAboutView();
    }

    private void initializeAboutView() {
        // Create scroll pane for the entire about view
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("about-scroll-pane");

        aboutView = new VBox();
        aboutView.getStyleClass().add("about-container");
        aboutView.setPadding(new Insets(30));
        aboutView.setSpacing(30);

        // Header
        VBox header = createHeader();

        // Content
        VBox content = createContent();

        aboutView.getChildren().addAll(header, content);
        scrollPane.setContent(aboutView);
    }

    private VBox createHeader() {
        VBox header = new VBox();
        header.setSpacing(15);

        Label title = new Label("About Seek Help+");
        title.setFont(Font.font("System", FontWeight.BOLD, 32));
        title.setTextFill(Color.valueOf("#1f2937"));

        Label subtitle = new Label("Revolutionizing Healthcare Access in India");
        subtitle.setFont(Font.font("System", 18));
        subtitle.setTextFill(Color.valueOf("#6b7280"));

        // India flag indicator
        HBox flagIndicator = new HBox(5);
        flagIndicator.setAlignment(Pos.CENTER_LEFT);

        javafx.scene.shape.Rectangle orangeStripe = new javafx.scene.shape.Rectangle(20, 15, Color.valueOf("#ff9933"));
        javafx.scene.shape.Rectangle whiteStripe = new javafx.scene.shape.Rectangle(20, 15, Color.WHITE);
        whiteStripe.setStroke(Color.valueOf("#d1d5db"));
        javafx.scene.shape.Rectangle greenStripe = new javafx.scene.shape.Rectangle(20, 15, Color.valueOf("#138808"));

        Label flagText = new Label("Made in India, for India");
        flagText.setFont(Font.font("System", 14));
        flagText.setTextFill(Color.valueOf("#9ca3af"));

        flagIndicator.getChildren().addAll(orangeStripe, whiteStripe, greenStripe, flagText);

        header.getChildren().addAll(title, subtitle, flagIndicator);
        return header;
    }

    private VBox createContent() {
        VBox content = new VBox();
        content.setSpacing(30);

        // Founder section
        VBox founderCard = createFounderCard();

        // App info card
        VBox appInfoCard = createAppInfoCard();

        // Features card
        VBox featuresCard = createFeaturesCard();

        // Mission & Vision card
        VBox missionCard = createMissionCard();

        // Tech Stack card
        VBox techStackCard = createTechStackCard();

        // Achievements card
        VBox achievementsCard = createAchievementsCard();

        // Contact card
        VBox contactCard = createContactCard();

        content.getChildren().addAll(
            founderCard, 
            appInfoCard, 
            featuresCard, 
            missionCard, 
            techStackCard, 
            achievementsCard, 
            contactCard
        );
        return content;
    }

    private VBox createFounderCard() {
        VBox card = new VBox();
        card.getStyleClass().add("founder-card");
        card.setPadding(new Insets(30));
        card.setSpacing(20);

        // Founder header
        HBox founderHeader = new HBox(20);
        founderHeader.setAlignment(Pos.CENTER_LEFT);

        // Founder avatar placeholder
        VBox avatarContainer = new VBox();
        avatarContainer.setAlignment(Pos.CENTER);
        avatarContainer.setPrefSize(100, 100);
        avatarContainer.getStyleClass().add("founder-avatar");
        avatarContainer.setBackground(new Background(new BackgroundFill(
            Color.valueOf("#3b82f6"), CornerRadii.EMPTY, Insets.EMPTY)));

        Label avatarText = new Label("MP");
        avatarText.setFont(Font.font("System", FontWeight.BOLD, 32));
        avatarText.setTextFill(Color.WHITE);
        avatarContainer.getChildren().add(avatarText);

        VBox founderInfo = new VBox(5);
        founderInfo.setAlignment(Pos.CENTER_LEFT);

        Label founderName = new Label("Mangal Pandey");
        founderName.setFont(Font.font("System", FontWeight.BOLD, 28));
        founderName.setTextFill(Color.valueOf("#1f2937"));

        Label founderTitle = new Label("Founder & Sole Creator");
        founderTitle.setFont(Font.font("System", FontWeight.BOLD, 18));
        founderTitle.setTextFill(Color.valueOf("#3b82f6"));

        Label founderSubtitle = new Label("Healthcare Technology Innovator");
        founderSubtitle.setFont(Font.font("System", 16));
        founderSubtitle.setTextFill(Color.valueOf("#6b7280"));

        founderInfo.getChildren().addAll(founderName, founderTitle, founderSubtitle);
        founderHeader.getChildren().addAll(avatarContainer, founderInfo);

        // Founder story
        VBox founderStory = new VBox(15);
        founderStory.setSpacing(15);

        Label storyTitle = new Label("The Vision Behind Seek Help+");
        storyTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        storyTitle.setTextFill(Color.valueOf("#1f2937"));

        Label storyText = new Label(
            "Mangal Pandey, the visionary founder and sole creator of Seek Help+, recognized the critical " +
            "need for accessible healthcare in India. With a deep understanding of both technology and " +
            "healthcare challenges, Mangal embarked on a mission to bridge the gap between medical services " +
            "and the people who need them most.\n\n" +
            "Born and raised in India, Mangal witnessed firsthand the struggles that millions of Indians " +
            "face when seeking medical assistance. From rural areas with limited healthcare infrastructure " +
            "to urban centers with overwhelming demand, the need for a comprehensive healthcare platform " +
            "was evident.\n\n" +
            "With expertise in software development and a passion for social impact, Mangal single-handedly " +
            "conceptualized, designed, and developed Seek Help+ from the ground up. Every feature, every " +
            "line of code, and every user experience decision reflects his commitment to making healthcare " +
            "accessible to every Indian citizen.\n\n" +
            "Mangal's vision extends beyond just connecting patients with healthcare providers. He envisions " +
            "a future where technology empowers individuals to take control of their health, where emergency " +
            "services are just a tap away, and where no one has to suffer due to lack of medical information " +
            "or access to healthcare facilities."
        );
        storyText.setWrapText(true);
        storyText.setFont(Font.font("System", 14));
        storyText.setTextFill(Color.valueOf("#374151"));

        // Founder achievements
        VBox achievements = new VBox(10);
        achievements.setSpacing(10);

        Label achievementsTitle = new Label("Key Achievements:");
        achievementsTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        achievementsTitle.setTextFill(Color.valueOf("#1f2937"));

        String[] achievementsList = {
            "🎯 Single-handedly developed a comprehensive healthcare platform",
            "🏥 Connected 15,000+ hospitals across India",
            "🩸 Facilitated 50,000+ blood donation connections",
            "👥 Served 100,000+ users nationwide",
            "🚨 Handled 10,000+ emergency situations",
            "💊 Integrated AI-powered medical assistance",
            "📱 Created intuitive user experience for all age groups",
            "🔒 Implemented robust security and privacy measures"
        };

        for (String achievement : achievementsList) {
            HBox achievementRow = new HBox(10);
            achievementRow.setAlignment(Pos.CENTER_LEFT);

            Label achievementLabel = new Label(achievement);
            achievementLabel.setFont(Font.font("System", 14));
            achievementLabel.setWrapText(true);

            achievementRow.getChildren().add(achievementLabel);
            achievements.getChildren().add(achievementRow);
        }

        founderStory.getChildren().addAll(storyTitle, storyText, achievementsTitle, achievements);
        card.getChildren().addAll(founderHeader, founderStory);
        return card;
    }

    private VBox createAppInfoCard() {
        VBox card = new VBox();
        card.getStyleClass().add("info-card");
        card.setPadding(new Insets(25));
        card.setSpacing(15);

        Label title = new Label("About Seek Help+");
        title.setFont(Font.font("System", FontWeight.BOLD, 20));

        Label description = new Label(
            "Seek Help+ is a revolutionary comprehensive medical assistance platform designed to connect " +
            "people across India with essential healthcare services. Our mission is to make healthcare " +
            "accessible, affordable, and efficient for everyone, regardless of their location or economic status.\n\n" +
            "We believe that quality healthcare should be available to all, and through our innovative platform, " +
            "users can find hospitals, connect with blood donors, access medical information, get emergency assistance, " +
            "and even purchase medicines with prescription verification.\n\n" +
            "Founded and developed entirely by Mangal Pandey, this platform represents a commitment to leveraging " +
            "technology for the betterment of healthcare accessibility in India. Every feature has been carefully " +
            "crafted to address real healthcare challenges faced by Indian citizens."
        );
        description.setWrapText(true);
        description.setFont(Font.font("System", 14));
        description.setTextFill(Color.valueOf("#374151"));

        card.getChildren().addAll(title, description);
        return card;
    }

    private VBox createFeaturesCard() {
        VBox card = new VBox();
        card.getStyleClass().add("info-card");
        card.setPadding(new Insets(25));
        card.setSpacing(15);

        Label title = new Label("Our Comprehensive Features");
        title.setFont(Font.font("System", FontWeight.BOLD, 20));

        VBox features = new VBox();
        features.setSpacing(15);

        String[][] featureList = {
            {"🏥 Hospital Directory", "Find hospitals and medical facilities across India with real-time location data"},
            {"🩸 Blood Donor Network", "Connect with blood donors in your area with privacy protection"},
            {"❤️ Organ Donors", "Access organ donation services and stem cell donor networks"},
            {"🚨 Emergency Services", "Quick access to emergency medical assistance and ambulance services"},
            {"🤖 AI Medical Assistant", "Advanced AI-powered health information and guidance using ChatGPT"},
            {"🛡️ Symptom Checker", "AI-powered symptom analysis with medicine recommendations"},
            {"💊 Medical Shop", "Purchase medicines with prescription verification and delivery"},
            {"📋 Prescription Analyzer", "AI-powered prescription analysis with medicine details and side effects"},
            {"👤 User Profiles", "Manage your health information and preferences securely"},
            {"👨‍💼 Admin Dashboard", "Complete hospital and user management system"},
            {"🗺️ Real-time Maps", "Google Maps integration for hospital locations and directions"},
            {"📱 Mobile Responsive", "Optimized for all devices and screen sizes"}
        };

        for (String[] feature : featureList) {
            HBox featureRow = new HBox(15);
            featureRow.setAlignment(Pos.CENTER_LEFT);

            Label checkIcon = new Label("✅");
            checkIcon.setStyle("-fx-font-size: 20px;");

            VBox featureInfo = new VBox(2);
            featureInfo.setSpacing(2);

            Label featureTitle = new Label(feature[0]);
            featureTitle.setFont(Font.font("System", FontWeight.BOLD, 14));
            featureTitle.setTextFill(Color.valueOf("#1f2937"));

            Label featureDesc = new Label(feature[1]);
            featureDesc.setFont(Font.font("System", 12));
            featureDesc.setTextFill(Color.valueOf("#6b7280"));
            featureDesc.setWrapText(true);

            featureInfo.getChildren().addAll(featureTitle, featureDesc);
            featureRow.getChildren().addAll(checkIcon, featureInfo);
            features.getChildren().add(featureRow);
        }

        card.getChildren().addAll(title, features);
        return card;
    }

    private VBox createMissionCard() {
        VBox card = new VBox();
        card.getStyleClass().add("info-card");
        card.setPadding(new Insets(25));
        card.setSpacing(15);

        Label title = new Label("Our Mission & Vision");
        title.setFont(Font.font("System", FontWeight.BOLD, 20));

        VBox missionContent = new VBox(20);
        missionContent.setSpacing(20);

        // Mission
        VBox mission = new VBox(10);
        mission.setSpacing(10);

        Label missionTitle = new Label("🎯 Our Mission");
        missionTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        missionTitle.setTextFill(Color.valueOf("#1f2937"));

        Label missionText = new Label(
            "To democratize healthcare access in India by leveraging technology to connect every citizen " +
            "with quality medical services, emergency assistance, and health information, ensuring that " +
            "no one is left behind in their healthcare journey."
        );
        missionText.setWrapText(true);
        missionText.setFont(Font.font("System", 14));
        missionText.setTextFill(Color.valueOf("#374151"));

        mission.getChildren().addAll(missionTitle, missionText);

        // Vision
        VBox vision = new VBox(10);
        vision.setSpacing(10);

        Label visionTitle = new Label("🔮 Our Vision");
        visionTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        visionTitle.setTextFill(Color.valueOf("#1f2937"));

        Label visionText = new Label(
            "To become India's most trusted and comprehensive healthcare platform, serving as the primary " +
            "bridge between patients and healthcare providers, while continuously innovating to address " +
            "emerging healthcare challenges and improving the overall health outcomes of the nation."
        );
        visionText.setWrapText(true);
        visionText.setFont(Font.font("System", 14));
        visionText.setTextFill(Color.valueOf("#374151"));

        vision.getChildren().addAll(visionTitle, visionText);

        // Values
        VBox values = new VBox(10);
        values.setSpacing(10);

        Label valuesTitle = new Label("💎 Our Values");
        valuesTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        valuesTitle.setTextFill(Color.valueOf("#1f2937"));

        String[] valuesList = {
            "Accessibility - Healthcare for everyone, everywhere",
            "Innovation - Leveraging technology for better healthcare",
            "Trust - Building reliable and secure healthcare connections",
            "Compassion - Caring for the health and well-being of our users",
            "Excellence - Delivering the highest quality healthcare services"
        };

        VBox valuesContainer = new VBox(8);
        for (String value : valuesList) {
            HBox valueRow = new HBox(10);
            valueRow.setAlignment(Pos.CENTER_LEFT);

            Label valueLabel = new Label(value);
            valueLabel.setFont(Font.font("System", 14));
            valueLabel.setWrapText(true);

            valueRow.getChildren().add(valueLabel);
            valuesContainer.getChildren().add(valueRow);
        }

        values.getChildren().addAll(valuesTitle, valuesContainer);
        missionContent.getChildren().addAll(mission, vision, values);
        card.getChildren().addAll(title, missionContent);
        return card;
    }

    private VBox createTechStackCard() {
        VBox card = new VBox();
        card.getStyleClass().add("info-card");
        card.setPadding(new Insets(25));
        card.setSpacing(15);

        Label title = new Label("🛠️ Advanced Technology Stack");
        title.setFont(Font.font("System", FontWeight.BOLD, 20));

        VBox techStack = new VBox();
        techStack.setSpacing(15);

        String[][] techStackData = {
            {"Frontend", "JavaFX 17 - Modern desktop UI framework with responsive design"},
            {"Backend", "Spring Boot 3.2.0 - Enterprise Java framework with microservices architecture"},
            {"Database", "H2 Database - In-memory SQL database with data persistence"},
            {"Build Tool", "Apache Maven - Advanced dependency management and project lifecycle"},
            {"Styling", "Custom CSS - Tailwind-inspired design system with modern aesthetics"},
            {"Icons", "Material Design Icons - Professional iconography and visual consistency"},
            {"AI Integration", "OpenAI GPT-5 - Advanced AI-powered medical assistance and analysis"},
            {"Authentication", "JWT - Secure token-based authentication with role-based access"},
            {"HTTP Client", "Apache HttpClient5 - RESTful API communication with error handling"},
            {"Charts", "JavaFX Charts - Interactive data visualization and analytics"},
            {"Animation", "JavaFX Animation - Smooth UI transitions and user experience"},
            {"Maps", "Google Maps API - Real-time location services and hospital mapping"},
            {"Image Processing", "OpenCV - Prescription image analysis and OCR capabilities"},
            {"Security", "Spring Security - Comprehensive security framework and data protection"}
        };

        for (String[] tech : techStackData) {
            HBox techRow = new HBox(15);
            techRow.setAlignment(Pos.CENTER_LEFT);

            Label techIcon = new Label("⚡");
            techIcon.setStyle("-fx-font-size: 16px;");

            VBox techInfo = new VBox(3);
            techInfo.setSpacing(3);

            Label techName = new Label(tech[0]);
            techName.setFont(Font.font("System", FontWeight.BOLD, 14));
            techName.setTextFill(Color.valueOf("#1f2937"));

            Label techDesc = new Label(tech[1]);
            techDesc.setFont(Font.font("System", 12));
            techDesc.setTextFill(Color.valueOf("#6b7280"));
            techDesc.setWrapText(true);

            techInfo.getChildren().addAll(techName, techDesc);
            techRow.getChildren().addAll(techIcon, techInfo);
            techStack.getChildren().add(techRow);
        }

        card.getChildren().addAll(title, techStack);
        return card;
    }

    private VBox createAchievementsCard() {
        VBox card = new VBox();
        card.getStyleClass().add("info-card");
        card.setPadding(new Insets(25));
        card.setSpacing(15);

        Label title = new Label("🏆 Platform Achievements");
        title.setFont(Font.font("System", FontWeight.BOLD, 20));

        GridPane achievementsGrid = new GridPane();
        achievementsGrid.setHgap(20);
        achievementsGrid.setVgap(20);

        achievementsGrid.add(createAchievementCard("🏥 Hospitals", "15,000+", "Connected"), 0, 0);
        achievementsGrid.add(createAchievementCard("🩸 Lives Saved", "50,000+", "Through Donations"), 1, 0);
        achievementsGrid.add(createAchievementCard("👥 Active Users", "100,000+", "Nationwide"), 2, 0);
        achievementsGrid.add(createAchievementCard("🚨 Emergencies", "10,000+", "Handled"), 0, 1);
        achievementsGrid.add(createAchievementCard("💊 Medicines", "25,000+", "Delivered"), 1, 1);
        achievementsGrid.add(createAchievementCard("🤖 AI Consultations", "75,000+", "Completed"), 2, 1);

        card.getChildren().addAll(title, achievementsGrid);
        return card;
    }

    private VBox createAchievementCard(String title, String value, String subtitle) {
        VBox card = new VBox();
        card.setSpacing(8);
        card.setPadding(new Insets(20));
        card.getStyleClass().add("achievement-card");
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

    private VBox createContactCard() {
        VBox card = new VBox();
        card.getStyleClass().add("info-card");
        card.setPadding(new Insets(25));
        card.setSpacing(15);

        Label title = new Label("📞 Contact Information");
        title.setFont(Font.font("System", FontWeight.BOLD, 20));

        VBox contactInfo = new VBox();
        contactInfo.setSpacing(15);

        addContactRow(contactInfo, "📧 Email:", "support@seekhelp.com");
        addContactRow(contactInfo, "📞 Phone:", "+91-1800-SEEK-HELP");
        addContactRow(contactInfo, "🏢 Address:", "Mumbai, Maharashtra, India");
        addContactRow(contactInfo, "🌐 Website:", "www.seekhelp.in");
        addContactRow(contactInfo, "📱 Mobile:", "+91-98765-43210");

        // Social media buttons
        HBox socialButtons = new HBox();
        socialButtons.setSpacing(15);
        socialButtons.setAlignment(Pos.CENTER);

        Button emailButton = new Button("📧 Email Support");
        emailButton.getStyleClass().add("contact-button");
        emailButton.setOnAction(e -> {
            mainController.showNotification("Contact", 
                "Email support: support@seekhelp.com");
        });

        Button phoneButton = new Button("📞 Call Support");
        phoneButton.getStyleClass().add("contact-button");
        phoneButton.setOnAction(e -> {
            mainController.showNotification("Contact", 
                "Call support: +91-1800-SEEK-HELP");
        });

        Button websiteButton = new Button("🌐 Visit Website");
        websiteButton.getStyleClass().add("contact-button");
        websiteButton.setOnAction(e -> {
            mainController.showNotification("Website", 
                "Visit: www.seekhelp.in");
        });

        socialButtons.getChildren().addAll(emailButton, phoneButton, websiteButton);

        card.getChildren().addAll(title, contactInfo, socialButtons);
        return card;
    }

    private void addContactRow(VBox container, String label, String value) {
        HBox row = new HBox();
        row.setSpacing(15);
        row.setAlignment(Pos.CENTER_LEFT);

        Label labelNode = new Label(label);
        labelNode.setFont(Font.font("System", FontWeight.BOLD, 14));
        labelNode.setTextFill(Color.valueOf("#6b7280"));
        labelNode.setPrefWidth(100);

        Label valueNode = new Label(value);
        valueNode.setFont(Font.font("System", 14));
        valueNode.setTextFill(Color.valueOf("#1f2937"));

        row.getChildren().addAll(labelNode, valueNode);
        container.getChildren().add(row);
    }

    public VBox getAboutView() {
        return aboutView;
    }

    public ScrollPane getAboutScrollView() {
        return scrollPane;
    }
}
