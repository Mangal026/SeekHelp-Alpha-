package com.seekhelp.desktop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.util.*;

public class AdminController {

    private MainController mainController;
    private VBox adminView;
    private ScrollPane scrollPane;
    private TabPane adminTabs;
    private TableView<HospitalData> hospitalsTable;
    private TableView<DonorData> donorsTable;
    private TableView<UserData> usersTable;

    public AdminController(MainController mainController) {
        this.mainController = mainController;
        initializeAdminView();
    }

    private void initializeAdminView() {
        adminView = new VBox();
        adminView.getStyleClass().add("admin-container");
        adminView.setPadding(new Insets(30));
        adminView.setSpacing(20);

        // Header
        VBox header = createHeader();

        // Admin Dashboard with tabs
        adminTabs = new TabPane();
        adminTabs.getStyleClass().add("admin-tabs");

        // Create tabs
        Tab dashboardTab = new Tab("📊 Dashboard", createDashboardTab());
        Tab hospitalsTab = new Tab("🏥 Hospitals", createHospitalsTab());
        Tab donorsTab = new Tab("🩸 Blood Donors", createDonorsTab());
        Tab usersTab = new Tab("👥 Users", createUsersTab());
        Tab analyticsTab = new Tab("📈 Analytics", createAnalyticsTab());
        Tab settingsTab = new Tab("⚙️ Settings", createSettingsTab());

        dashboardTab.setClosable(false);
        hospitalsTab.setClosable(false);
        donorsTab.setClosable(false);
        usersTab.setClosable(false);
        analyticsTab.setClosable(false);
        settingsTab.setClosable(false);

        adminTabs.getTabs().addAll(dashboardTab, hospitalsTab, donorsTab, usersTab, analyticsTab, settingsTab);

        adminView.getChildren().addAll(header, adminTabs);
    }

    private VBox createHeader() {
        VBox header = new VBox();
        header.setSpacing(10);

        HBox titleRow = new HBox(15);
        titleRow.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("👨‍💼 Admin Dashboard");
        title.setFont(Font.font("System", FontWeight.BOLD, 28));
        title.setTextFill(Color.valueOf("#1f2937"));

        Label adminBadge = new Label("ADMIN");
        adminBadge.getStyleClass().add("admin-badge");

        titleRow.getChildren().addAll(title, adminBadge);

        Label subtitle = new Label("Hospital Management System - Full Access Control");
        subtitle.setFont(Font.font("System", 16));
        subtitle.setTextFill(Color.valueOf("#6b7280"));

        // Quick stats
        HBox statsRow = new HBox(20);
        statsRow.setAlignment(Pos.CENTER_LEFT);

        statsRow.getChildren().addAll(
            createStatCard("🏥 Hospitals", "156", "+12%"),
            createStatCard("🩸 Donors", "2,847", "+8%"),
            createStatCard("👥 Users", "15,234", "+15%"),
            createStatCard("🚨 Emergencies", "23", "Today")
        );

        header.getChildren().addAll(titleRow, subtitle, statsRow);
        return header;
    }

    private VBox createStatCard(String title, String value, String change) {
        VBox card = new VBox();
        card.setSpacing(5);
        card.setPadding(new Insets(15));
        card.getStyleClass().add("stat-card");

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", 12));
        titleLabel.setTextFill(Color.valueOf("#6b7280"));

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        valueLabel.setTextFill(Color.valueOf("#1f2937"));

        Label changeLabel = new Label(change);
        changeLabel.setFont(Font.font("System", 10));
        changeLabel.setTextFill(Color.valueOf("#15803d"));

        card.getChildren().addAll(titleLabel, valueLabel, changeLabel);
        return card;
    }

    private VBox createDashboardTab() {
        VBox dashboard = new VBox();
        dashboard.setSpacing(20);
        dashboard.setPadding(new Insets(20));

        // Recent Activity
        VBox recentActivity = createRecentActivitySection();

        // Quick Actions
        VBox quickActions = createQuickActionsSection();

        // System Status
        VBox systemStatus = createSystemStatusSection();

        dashboard.getChildren().addAll(recentActivity, quickActions, systemStatus);
        return dashboard;
    }

    private VBox createRecentActivitySection() {
        VBox section = new VBox();
        section.setSpacing(15);

        Label sectionTitle = new Label("📋 Recent Activity");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));

        ListView<String> activityList = new ListView<>();
        activityList.getItems().addAll(
            "🆕 New hospital registered: City Medical Center",
            "🩸 Blood donation drive completed: 45 donors",
            "👤 New user registration: Dr. Priya Sharma",
            "🚨 Emergency response: Ambulance dispatched to Mumbai",
            "📊 Monthly report generated: January 2024",
            "⚙️ System maintenance completed",
            "🏥 Hospital profile updated: Apollo Hospital",
            "🩺 AI consultation session: 127 queries handled"
        );

        activityList.setPrefHeight(200);
        section.getChildren().addAll(sectionTitle, activityList);
        return section;
    }

    private VBox createQuickActionsSection() {
        VBox section = new VBox();
        section.setSpacing(15);

        Label sectionTitle = new Label("⚡ Quick Actions");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));

        GridPane actionsGrid = new GridPane();
        actionsGrid.setHgap(15);
        actionsGrid.setVgap(15);

        String[][] actions = {
            {"🏥 Add Hospital", "Register new hospital"},
            {"🩸 Blood Drive", "Schedule donation drive"},
            {"👤 Add User", "Create new user account"},
            {"📊 Generate Report", "Create system report"},
            {"🚨 Emergency", "Emergency management"},
            {"⚙️ Settings", "System configuration"}
        };

        for (int i = 0; i < actions.length; i++) {
            VBox actionCard = createActionCard(actions[i][0], actions[i][1]);
            actionsGrid.add(actionCard, i % 3, i / 3);
        }

        section.getChildren().addAll(sectionTitle, actionsGrid);
        return section;
    }

    private VBox createActionCard(String title, String description) {
        VBox card = new VBox();
        card.setSpacing(10);
        card.setPadding(new Insets(15));
        card.getStyleClass().add("action-card");
        card.setPrefWidth(200);
        card.setPrefHeight(120);

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

        Label descLabel = new Label(description);
        descLabel.setWrapText(true);
        descLabel.setFont(Font.font("System", 12));
        descLabel.setTextFill(Color.valueOf("#6b7280"));

        Button actionButton = new Button("Go");
        actionButton.getStyleClass().add("action-button");
        actionButton.setMaxWidth(Double.MAX_VALUE);
        actionButton.setOnAction(e -> handleQuickAction(title));

        card.getChildren().addAll(titleLabel, descLabel, actionButton);
        return card;
    }

    private VBox createSystemStatusSection() {
        VBox section = new VBox();
        section.setSpacing(15);

        Label sectionTitle = new Label("🔧 System Status");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));

        VBox statusItems = new VBox();
        statusItems.setSpacing(10);

        String[][] statusData = {
            {"🟢 Database", "Online", "All systems operational"},
            {"🟢 AI Assistant", "Online", "Processing queries normally"},
            {"🟢 Emergency Services", "Online", "24/7 monitoring active"},
            {"🟡 Backup System", "Maintenance", "Scheduled maintenance"},
            {"🟢 User Authentication", "Online", "Secure login active"}
        };

        for (String[] status : statusData) {
            HBox statusRow = new HBox(15);
            statusRow.setAlignment(Pos.CENTER_LEFT);
            statusRow.setPadding(new Insets(10));
            statusRow.getStyleClass().add("status-row");

            Label statusIcon = new Label(status[0]);
            statusIcon.setFont(Font.font("System", 16));

            VBox statusInfo = new VBox();
            statusInfo.setSpacing(2);

            Label statusName = new Label(status[1]);
            statusName.setFont(Font.font("System", FontWeight.BOLD, 14));

            Label statusDesc = new Label(status[2]);
            statusDesc.setFont(Font.font("System", 12));
            statusDesc.setTextFill(Color.valueOf("#6b7280"));

            statusInfo.getChildren().addAll(statusName, statusDesc);

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            statusRow.getChildren().addAll(statusIcon, statusInfo, spacer);
            statusItems.getChildren().add(statusRow);
        }

        section.getChildren().addAll(sectionTitle, statusItems);
        return section;
    }

    private VBox createHospitalsTab() {
        VBox hospitalsTab = new VBox();
        hospitalsTab.setSpacing(20);
        hospitalsTab.setPadding(new Insets(20));

        // Hospital management controls
        HBox controls = new HBox(15);
        controls.setAlignment(Pos.CENTER_LEFT);

        Button addHospitalButton = new Button("🏥 Add Hospital");
        addHospitalButton.getStyleClass().add("admin-button");
        addHospitalButton.setOnAction(e -> addHospital());

        Button editHospitalButton = new Button("✏️ Edit Hospital");
        editHospitalButton.getStyleClass().add("admin-button");
        editHospitalButton.setOnAction(e -> editHospital());

        Button deleteHospitalButton = new Button("🗑️ Delete Hospital");
        deleteHospitalButton.getStyleClass().add("admin-button-danger");
        deleteHospitalButton.setOnAction(e -> deleteHospital());

        TextField searchField = new TextField();
        searchField.setPromptText("Search hospitals...");
        searchField.setPrefWidth(300);

        controls.getChildren().addAll(addHospitalButton, editHospitalButton, deleteHospitalButton, searchField);

        // Hospitals table
        hospitalsTable = new TableView<>();
        hospitalsTable.setPrefHeight(400);

        TableColumn<HospitalData, String> nameCol = new TableColumn<>("Hospital Name");
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));

        TableColumn<HospitalData, String> locationCol = new TableColumn<>("Location");
        locationCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getLocation()));

        TableColumn<HospitalData, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getType()));

        TableColumn<HospitalData, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getStatus()));

        hospitalsTable.getColumns().addAll(nameCol, locationCol, typeCol, statusCol);

        // Load sample data
        loadHospitalData();

        hospitalsTab.getChildren().addAll(controls, hospitalsTable);
        return hospitalsTab;
    }

    private VBox createDonorsTab() {
        VBox donorsTab = new VBox();
        donorsTab.setSpacing(20);
        donorsTab.setPadding(new Insets(20));

        // Donor management controls
        HBox controls = new HBox(15);
        controls.setAlignment(Pos.CENTER_LEFT);

        Button addDonorButton = new Button("🩸 Add Donor");
        addDonorButton.getStyleClass().add("admin-button");
        addDonorButton.setOnAction(e -> addDonor());

        Button scheduleDriveButton = new Button("📅 Schedule Drive");
        scheduleDriveButton.getStyleClass().add("admin-button");
        scheduleDriveButton.setOnAction(e -> scheduleBloodDrive());

        TextField searchField = new TextField();
        searchField.setPromptText("Search donors...");
        searchField.setPrefWidth(300);

        controls.getChildren().addAll(addDonorButton, scheduleDriveButton, searchField);

        // Donors table
        donorsTable = new TableView<>();
        donorsTable.setPrefHeight(400);

        TableColumn<DonorData, String> nameCol = new TableColumn<>("Donor Name");
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));

        TableColumn<DonorData, String> bloodTypeCol = new TableColumn<>("Blood Type");
        bloodTypeCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getBloodType()));

        TableColumn<DonorData, String> locationCol = new TableColumn<>("Location");
        locationCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getLocation()));

        TableColumn<DonorData, String> lastDonationCol = new TableColumn<>("Last Donation");
        lastDonationCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getLastDonation()));

        donorsTable.getColumns().addAll(nameCol, bloodTypeCol, locationCol, lastDonationCol);

        // Load sample data
        loadDonorData();

        donorsTab.getChildren().addAll(controls, donorsTable);
        return donorsTab;
    }

    private VBox createUsersTab() {
        VBox usersTab = new VBox();
        usersTab.setSpacing(20);
        usersTab.setPadding(new Insets(20));

        // User management controls
        HBox controls = new HBox(15);
        controls.setAlignment(Pos.CENTER_LEFT);

        Button addUserButton = new Button("👤 Add User");
        addUserButton.getStyleClass().add("admin-button");
        addUserButton.setOnAction(e -> addUser());

        Button manageRolesButton = new Button("🔐 Manage Roles");
        manageRolesButton.getStyleClass().add("admin-button");
        manageRolesButton.setOnAction(e -> manageRoles());

        TextField searchField = new TextField();
        searchField.setPromptText("Search users...");
        searchField.setPrefWidth(300);

        controls.getChildren().addAll(addUserButton, manageRolesButton, searchField);

        // Users table
        usersTable = new TableView<>();
        usersTable.setPrefHeight(400);

        TableColumn<UserData, String> nameCol = new TableColumn<>("User Name");
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));

        TableColumn<UserData, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));

        TableColumn<UserData, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getRole()));

        TableColumn<UserData, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getStatus()));

        usersTable.getColumns().addAll(nameCol, emailCol, roleCol, statusCol);

        // Load sample data
        loadUserData();

        usersTab.getChildren().addAll(controls, usersTable);
        return usersTab;
    }

    private VBox createAnalyticsTab() {
        VBox analyticsTab = new VBox();
        analyticsTab.setSpacing(20);
        analyticsTab.setPadding(new Insets(20));

        // Analytics overview
        HBox analyticsOverview = new HBox(20);
        analyticsOverview.setAlignment(Pos.CENTER_LEFT);

        analyticsOverview.getChildren().addAll(
            createAnalyticsCard("Total Users", "15,234", "+12%"),
            createAnalyticsCard("Active Hospitals", "156", "+5%"),
            createAnalyticsCard("Blood Donations", "2,847", "+8%"),
            createAnalyticsCard("Emergency Calls", "1,234", "+15%")
        );

        // Charts section
        VBox chartsSection = new VBox();
        chartsSection.setSpacing(20);

        Label chartsTitle = new Label("📈 Analytics Charts");
        chartsTitle.setFont(Font.font("System", FontWeight.BOLD, 20));

        // Sample chart
        VBox chartContainer = new VBox();
        chartContainer.setPadding(new Insets(20));
        chartContainer.getStyleClass().add("chart-container");
        chartContainer.setPrefHeight(300);

        Label chartLabel = new Label("User Growth Over Time");
        chartLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

        // Simple chart representation
        VBox chartPlaceholder = new VBox();
        chartPlaceholder.setAlignment(Pos.CENTER);
        chartPlaceholder.setPrefHeight(200);
        chartPlaceholder.getStyleClass().add("chart-placeholder");

        Label chartText = new Label("📊 Interactive Charts\n(Charts will be implemented with JavaFX Charts)");
        chartText.setAlignment(Pos.CENTER);
        chartPlaceholder.getChildren().add(chartText);

        chartContainer.getChildren().addAll(chartLabel, chartPlaceholder);

        chartsSection.getChildren().addAll(chartsTitle, chartContainer);

        analyticsTab.getChildren().addAll(analyticsOverview, chartsSection);
        return analyticsTab;
    }

    private VBox createAnalyticsCard(String title, String value, String change) {
        VBox card = new VBox();
        card.setSpacing(5);
        card.setPadding(new Insets(20));
        card.getStyleClass().add("analytics-card");

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", 12));
        titleLabel.setTextFill(Color.valueOf("#6b7280"));

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        valueLabel.setTextFill(Color.valueOf("#1f2937"));

        Label changeLabel = new Label(change);
        changeLabel.setFont(Font.font("System", 10));
        changeLabel.setTextFill(Color.valueOf("#15803d"));

        card.getChildren().addAll(titleLabel, valueLabel, changeLabel);
        return card;
    }

    private VBox createSettingsTab() {
        VBox settingsTab = new VBox();
        settingsTab.setSpacing(20);
        settingsTab.setPadding(new Insets(20));

        Label settingsTitle = new Label("⚙️ System Settings");
        settingsTitle.setFont(Font.font("System", FontWeight.BOLD, 20));

        // Settings sections
        VBox generalSettings = createSettingsSection("General Settings", new String[][]{
            {"System Name", "Seek Help+ Hospital Management"},
            {"Admin Email", "admin@seekhelp.com"},
            {"Emergency Contact", "+91-1800-123-4567"}
        });

        VBox securitySettings = createSettingsSection("Security Settings", new String[][]{
            {"Password Policy", "Strong passwords required"},
            {"Session Timeout", "30 minutes"},
            {"Two-Factor Auth", "Enabled"}
        });

        VBox notificationSettings = createSettingsSection("Notification Settings", new String[][]{
            {"Email Notifications", "Enabled"},
            {"SMS Alerts", "Enabled"},
            {"Emergency Alerts", "Always On"}
        });

        Button saveSettingsButton = new Button("💾 Save Settings");
        saveSettingsButton.getStyleClass().add("save-button");
        saveSettingsButton.setOnAction(e -> saveSettings());

        settingsTab.getChildren().addAll(settingsTitle, generalSettings, securitySettings, notificationSettings, saveSettingsButton);
        return settingsTab;
    }

    private VBox createSettingsSection(String title, String[][] settings) {
        VBox section = new VBox();
        section.setSpacing(15);

        Label sectionTitle = new Label(title);
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 16));

        VBox settingsList = new VBox();
        settingsList.setSpacing(10);

        for (String[] setting : settings) {
            HBox settingRow = new HBox(15);
            settingRow.setAlignment(Pos.CENTER_LEFT);

            Label settingLabel = new Label(setting[0] + ":");
            settingLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
            settingLabel.setPrefWidth(150);

            TextField settingField = new TextField(setting[1]);
            settingField.setPrefWidth(300);

            settingRow.getChildren().addAll(settingLabel, settingField);
            settingsList.getChildren().add(settingRow);
        }

        section.getChildren().addAll(sectionTitle, settingsList);
        return section;
    }

    // Action handlers
    private void handleQuickAction(String action) {
        switch (action) {
            case "🏥 Add Hospital":
                addHospital();
                break;
            case "🩸 Blood Drive":
                scheduleBloodDrive();
                break;
            case "👤 Add User":
                addUser();
                break;
            case "📊 Generate Report":
                generateReport();
                break;
            case "🚨 Emergency":
                mainController.showEmergencyServices();
                break;
            case "⚙️ Settings":
                adminTabs.getSelectionModel().select(5); // Settings tab
                break;
        }
    }

    private void addHospital() {
        mainController.showNotification("Add Hospital", "Hospital registration form will open...");
    }

    private void editHospital() {
        mainController.showNotification("Edit Hospital", "Hospital editing interface will open...");
    }

    private void deleteHospital() {
        mainController.showNotification("Delete Hospital", "Hospital deletion confirmation will open...");
    }

    private void addDonor() {
        mainController.showNotification("Add Donor", "Donor registration form will open...");
    }

    private void scheduleBloodDrive() {
        mainController.showNotification("Schedule Blood Drive", "Blood drive scheduling interface will open...");
    }

    private void addUser() {
        mainController.showNotification("Add User", "User registration form will open...");
    }

    private void manageRoles() {
        mainController.showNotification("Manage Roles", "Role management interface will open...");
    }

    private void generateReport() {
        mainController.showNotification("Generate Report", "Report generation in progress...");
    }

    private void saveSettings() {
        mainController.showNotification("Settings Saved", "All settings have been saved successfully!");
    }

    // Data loading methods
    private void loadHospitalData() {
        List<HospitalData> hospitals = Arrays.asList(
            new HospitalData("Apollo Hospital", "Mumbai", "Private", "Active"),
            new HospitalData("Fortis Hospital", "Delhi", "Private", "Active"),
            new HospitalData("AIIMS Delhi", "Delhi", "Government", "Active"),
            new HospitalData("Manipal Hospital", "Bangalore", "Private", "Active"),
            new HospitalData("Civil Hospital", "Ahmedabad", "Government", "Active")
        );
        hospitalsTable.getItems().addAll(hospitals);
    }

    private void loadDonorData() {
        List<DonorData> donors = Arrays.asList(
            new DonorData("Rahul Sharma", "A+", "Mumbai", "2024-01-15"),
            new DonorData("Priya Patel", "B+", "Delhi", "2024-01-10"),
            new DonorData("Amit Kumar", "O+", "Bangalore", "2024-01-20"),
            new DonorData("Neha Singh", "AB+", "Hyderabad", "2024-01-05"),
            new DonorData("Vikram Mehta", "A-", "Ahmedabad", "2024-01-12")
        );
        donorsTable.getItems().addAll(donors);
    }

    private void loadUserData() {
        List<UserData> users = Arrays.asList(
            new UserData("Dr. Sharma", "dr.sharma@hospital.com", "Doctor", "Active"),
            new UserData("Nurse Patel", "nurse.patel@hospital.com", "Nurse", "Active"),
            new UserData("Admin Kumar", "admin.kumar@seekhelp.com", "Admin", "Active"),
            new UserData("Dr. Singh", "dr.singh@hospital.com", "Doctor", "Inactive"),
            new UserData("Manager Mehta", "manager.mehta@seekhelp.com", "Manager", "Active")
        );
        usersTable.getItems().addAll(users);
    }

    public VBox getAdminView() {
        return adminView;
    }

    // Data classes
    public static class HospitalData {
        private String name, location, type, status;

        public HospitalData(String name, String location, String type, String status) {
            this.name = name;
            this.location = location;
            this.type = type;
            this.status = status;
        }

        public String getName() { return name; }
        public String getLocation() { return location; }
        public String getType() { return type; }
        public String getStatus() { return status; }
    }

    public static class DonorData {
        private String name, bloodType, location, lastDonation;

        public DonorData(String name, String bloodType, String location, String lastDonation) {
            this.name = name;
            this.bloodType = bloodType;
            this.location = location;
            this.lastDonation = lastDonation;
        }

        public String getName() { return name; }
        public String getBloodType() { return bloodType; }
        public String getLocation() { return location; }
        public String getLastDonation() { return lastDonation; }
    }

    public static class UserData {
        private String name, email, role, status;

        public UserData(String name, String email, String role, String status) {
            this.name = name;
            this.email = email;
            this.role = role;
            this.status = status;
        }

        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getRole() { return role; }
        public String getStatus() { return status; }
    }
}
