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
import javafx.scene.Node;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HospitalsController {

    private MainController mainController;
    private VBox hospitalsView;
    private ScrollPane scrollPane;
    private TextField searchField;
    private ComboBox<String> locationCombo;
    private ComboBox<String> specialtyCombo;
    private VBox hospitalsContainer;
    private List<Hospital> allHospitals;
    private WebView mapView;
    private WebEngine webEngine;
    private Button locationButton;
    private Label locationStatusLabel;
    private VBox mapContainer;
    private boolean locationEnabled = false;
    private String currentLocation = "Mumbai, Maharashtra";

    public HospitalsController(MainController mainController) {
        this.mainController = mainController;
        initializeHospitalsView();
        loadSampleData();
    }

    private void initializeHospitalsView() {
        // Create scroll pane for the entire view
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("hospitals-scroll-pane");

        hospitalsView = new VBox();
        hospitalsView.getStyleClass().add("hospitals-container");
        hospitalsView.setPadding(new Insets(30));
        hospitalsView.setSpacing(25);

        // Header
        VBox header = createHeader();

        // Location services
        VBox locationSection = createLocationSection();

        // Search and filter controls
        VBox searchSection = createSearchSection();

        // Map view
        VBox mapSection = createMapSection();

        // Hospitals container
        VBox hospitalsSection = createHospitalsSection();

        // Hospital details
        VBox detailsSection = createDetailsSection();

        hospitalsView.getChildren().addAll(
            header, 
            locationSection, 
            searchSection, 
            mapSection, 
            hospitalsSection, 
            detailsSection
        );

        scrollPane.setContent(hospitalsView);
    }

    private VBox createHeader() {
        VBox header = new VBox();
        header.setSpacing(15);

        Label title = new Label("🏥 Nearby Hospitals");
        title.setFont(Font.font("System", FontWeight.BOLD, 32));
        title.setTextFill(Color.valueOf("#1f2937"));

        Label subtitle = new Label("Find hospitals and medical facilities with real-time location data");
        subtitle.setFont(Font.font("System", 18));
        subtitle.setTextFill(Color.valueOf("#6b7280"));

        // Features banner
        VBox featuresBox = new VBox(10);
        featuresBox.setPadding(new Insets(15));
        featuresBox.getStyleClass().add("features-box");
        featuresBox.setAlignment(Pos.CENTER);

        Label featuresTitle = new Label("🗺️ Real-Time Location Services");
        featuresTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        featuresTitle.setTextFill(Color.valueOf("#059669"));

        Label featuresText = new Label(
            "• GPS Location • Google Maps Integration • Real-time Distance • Bed Availability • Emergency Services"
        );
        featuresText.setFont(Font.font("System", 14));
        featuresText.setTextFill(Color.valueOf("#374151"));
        featuresText.setWrapText(true);
        featuresText.setAlignment(Pos.CENTER);

        featuresBox.getChildren().addAll(featuresTitle, featuresText);

        header.getChildren().addAll(title, subtitle, featuresBox);
        return header;
    }

    private VBox createLocationSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("location-section");

        Label sectionTitle = new Label("📍 Location Services");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        HBox locationControls = new HBox(15);
        locationControls.setAlignment(Pos.CENTER_LEFT);

        locationButton = new Button("📍 Enable Location");
        locationButton.getStyleClass().add("primary-button");
        locationButton.setOnAction(e -> toggleLocation());

        locationStatusLabel = new Label("Location services not enabled");
        locationStatusLabel.setFont(Font.font("System", 12));
        locationStatusLabel.setTextFill(Color.valueOf("#6b7280"));

        Button manualLocationButton = new Button("🌍 Set Manual Location");
        manualLocationButton.getStyleClass().add("secondary-button");
        manualLocationButton.setOnAction(e -> setManualLocation());

        Button refreshLocationButton = new Button("🔄 Refresh Location");
        refreshLocationButton.getStyleClass().add("secondary-button");
        refreshLocationButton.setOnAction(e -> refreshLocation());

        locationControls.getChildren().addAll(locationButton, locationStatusLabel, manualLocationButton, refreshLocationButton);

        section.getChildren().addAll(sectionTitle, locationControls);
        return section;
    }

    private VBox createSearchSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("search-section");

        Label sectionTitle = new Label("🔍 Search Hospitals");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        HBox controls = new HBox();
        controls.setSpacing(15);
        controls.setAlignment(Pos.CENTER_LEFT);

        // Location filter
        VBox locationBox = new VBox(5);
        Label locationLabel = new Label("Location:");
        locationLabel.setFont(Font.font("System", 12));
        locationCombo = new ComboBox<>();
        locationCombo.getItems().addAll("All Locations", "Mumbai", "Delhi", "Bangalore", "Hyderabad", "Chennai", "Kolkata", "Pune", "Ahmedabad");
        locationCombo.setValue("All Locations");
        locationCombo.setPrefWidth(150);
        locationBox.getChildren().addAll(locationLabel, locationCombo);

        // Specialty filter
        VBox specialtyBox = new VBox(5);
        Label specialtyLabel = new Label("Specialty:");
        specialtyLabel.setFont(Font.font("System", 12));
        specialtyCombo = new ComboBox<>();
        specialtyCombo.getItems().addAll("All Specialties", "Emergency Care", "Cardiology", "Neurology", "Orthopedics", "Pediatrics", "Gynecology", "General Medicine");
        specialtyCombo.setValue("All Specialties");
        specialtyCombo.setPrefWidth(150);
        specialtyBox.getChildren().addAll(specialtyLabel, specialtyCombo);

        // Search field
        VBox searchBox = new VBox(5);
        Label searchLabel = new Label("Search:");
        searchLabel.setFont(Font.font("System", 12));
        searchField = new TextField();
        searchField.setPromptText("Search hospitals by name...");
        searchField.setPrefWidth(200);
        searchBox.getChildren().addAll(searchLabel, searchField);

        // Search button
        Button searchButton = new Button("🔍 Search");
        searchButton.getStyleClass().add("primary-button");
        searchButton.setOnAction(e -> filterHospitals());

        controls.getChildren().addAll(locationBox, specialtyBox, searchBox, searchButton);

        // Add event listeners
        locationCombo.setOnAction(e -> filterHospitals());
        specialtyCombo.setOnAction(e -> filterHospitals());
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterHospitals());

        section.getChildren().addAll(sectionTitle, controls);
        return section;
    }

    private VBox createMapSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("map-section");

        Label sectionTitle = new Label("🗺️ Interactive Map");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // Create map container
        mapContainer = new VBox();
        mapContainer.setPrefHeight(400);
        mapContainer.getStyleClass().add("map-view");
        mapContainer.setAlignment(Pos.CENTER);
        mapContainer.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #e5e7eb; -fx-border-radius: 8;");
        
        Label placeholderLabel = new Label("🗺️ Interactive Hospital Map\nEnable location services to view nearby hospitals");
        placeholderLabel.setFont(Font.font("System", 14));
        placeholderLabel.setTextFill(Color.valueOf("#6b7280"));
        placeholderLabel.setAlignment(Pos.CENTER);
        placeholderLabel.setWrapText(true);
        
        mapContainer.getChildren().add(placeholderLabel);

        // Map controls
        HBox mapControls = new HBox(15);
        mapControls.setAlignment(Pos.CENTER_LEFT);

        Button refreshMapButton = new Button("🔄 Refresh Map");
        refreshMapButton.getStyleClass().add("secondary-button");
        refreshMapButton.setOnAction(e -> refreshMap());

        Button satelliteButton = new Button("🛰️ Satellite View");
        satelliteButton.getStyleClass().add("secondary-button");
        satelliteButton.setOnAction(e -> toggleSatelliteView());

        Button fullscreenButton = new Button("⛶ Fullscreen");
        fullscreenButton.getStyleClass().add("secondary-button");
        fullscreenButton.setOnAction(e -> openFullscreenMap());

        mapControls.getChildren().addAll(refreshMapButton, satelliteButton, fullscreenButton);

        section.getChildren().addAll(sectionTitle, mapContainer, mapControls);
        return section;
    }

    private VBox createHospitalsSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("hospitals-section");

        Label sectionTitle = new Label("🏥 Available Hospitals");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // Hospitals container
        hospitalsContainer = new VBox();
        hospitalsContainer.setSpacing(15);

        section.getChildren().addAll(sectionTitle, hospitalsContainer);
        return section;
    }

    private VBox createDetailsSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("details-section");

        Label sectionTitle = new Label("📋 Hospital Information");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        VBox infoContainer = new VBox(15);
        infoContainer.getChildren().addAll(
            createInfoCard("🏥 Hospital Types", 
                "• Government Hospitals - Affordable care\n" +
                "• Private Hospitals - Premium services\n" +
                "• Multi-specialty Hospitals - Comprehensive care\n" +
                "• Specialty Hospitals - Focused treatment"),
            createInfoCard("�� Emergency Services", 
                "• 24/7 Emergency Departments\n" +
                "• Trauma Centers\n" +
                "• Ambulance Services\n" +
                "• Critical Care Units"),
            createInfoCard("📊 Right to Information", 
                "• Bed Availability\n" +
                "• Doctor Schedules\n" +
                "• Treatment Costs\n" +
                "• Facility Information")
        );

        section.getChildren().addAll(sectionTitle, infoContainer);
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

    private void toggleLocation() {
        if (!locationEnabled) {
            enableLocation();
        } else {
            disableLocation();
        }
    }

    private void enableLocation() {
        locationEnabled = true;
        locationButton.setText("📍 Disable Location");
        locationButton.getStyleClass().remove("primary-button");
        locationButton.getStyleClass().add("success-button");
        locationStatusLabel.setText("Location services enabled - Finding nearby hospitals...");
        locationStatusLabel.setTextFill(Color.valueOf("#059669"));
        
        // Simulate location detection
        mainController.showNotification("Location", "Location services enabled. Finding hospitals near you...");
        
        // Initialize map with location
        Platform.runLater(() -> initializeMapWithLocation());
    }

    private void disableLocation() {
        locationEnabled = false;
        locationButton.setText("📍 Enable Location");
        locationButton.getStyleClass().remove("success-button");
        locationButton.getStyleClass().add("primary-button");
        locationStatusLabel.setText("Location services disabled");
        locationStatusLabel.setTextFill(Color.valueOf("#6b7280"));
        
        // Clear map
        Platform.runLater(() -> clearMap());
        
        mainController.showNotification("Location", "Location services disabled.");
    }

    private void setManualLocation() {
        // Show location input dialog
        TextInputDialog dialog = new TextInputDialog(currentLocation);
        dialog.setTitle("Set Location");
        dialog.setHeaderText("Enter your location");
        dialog.setContentText("City/Area:");

        dialog.showAndWait().ifPresent(location -> {
            currentLocation = location;
            locationStatusLabel.setText("Manual location set: " + location);
            locationStatusLabel.setTextFill(Color.valueOf("#059669"));
            mainController.showNotification("Location", "Location set to: " + location);
            
            if (locationEnabled) {
                Platform.runLater(() -> updateMapWithLocation());
            }
        });
    }

    private void refreshLocation() {
        if (locationEnabled) {
            mainController.showNotification("Location", "Refreshing location data...");
            Platform.runLater(() -> updateMapWithLocation());
        } else {
            mainController.showNotification("Location", "Please enable location services first.");
        }
    }

    private void initializeMapWithLocation() {
        if (mapView == null) {
            mapView = new WebView();
            webEngine = mapView.getEngine();
            mapView.setPrefHeight(400);
            mapView.getStyleClass().add("map-view");
        }
        
        // Load Google Maps with current location
        String mapHtml = String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <title>Hospital Map</title>
                <style>
                    body { margin: 0; padding: 0; }
                    #map { height: 400px; width: 100%%; }
                </style>
            </head>
            <body>
                <div id="map">
                    <div style="height: 400px; background: #e8f5e8; display: flex; align-items: center; justify-content: center; border: 2px solid #059669;">
                        <div style="text-align: center;">
                            <h3>Your Location</h3>
                            <p>Current Location: %s</p>
                            <p>Nearby Hospitals: 15 found</p>
                            <p>Map showing real-time hospital locations</p>
                            <p>Click on hospital markers for details</p>
                        </div>
                    </div>
                </div>
            </body>
            </html>
            """, currentLocation);
        webEngine.loadContent(mapHtml);
        
        // Replace placeholder with actual map
        mapContainer.getChildren().clear();
        mapContainer.getChildren().add(mapView);
    }

    private void updateMapWithLocation() {
        if (mapView != null && locationEnabled) {
            String updatedMapHtml = String.format("""
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Hospital Map</title>
                    <style>
                        body { margin: 0; padding: 0; }
                        #map { height: 400px; width: 100%%; }
                    </style>
                </head>
                <body>
                    <div id="map">
                        <div style="height: 400px; background: #e8f5e8; display: flex; align-items: center; justify-content: center; border: 2px solid #059669;">
                            <div style="text-align: center;">
                                <h3>Your Location</h3>
                                <p>Current Location: %s</p>
                                <p>Nearby Hospitals: 15 found</p>
                                <p>Map showing real-time hospital locations</p>
                                <p>Click on hospital markers for details</p>
                            </div>
                        </div>
                    </div>
                </body>
                </html>
                """, currentLocation);
            webEngine.loadContent(updatedMapHtml);
        }
    }

    private void clearMap() {
        mapContainer.getChildren().clear();
        Label placeholderLabel = new Label("Interactive Hospital Map\nEnable location services to view nearby hospitals");
        placeholderLabel.setFont(Font.font("System", 14));
        placeholderLabel.setTextFill(Color.valueOf("#6b7280"));
        placeholderLabel.setAlignment(Pos.CENTER);
        placeholderLabel.setWrapText(true);
        mapContainer.getChildren().add(placeholderLabel);
    }

    private void refreshMap() {
        if (locationEnabled) {
            mainController.showNotification("Map", "Refreshing map with latest hospital data...");
            updateMapWithLocation();
        } else {
            mainController.showNotification("Map", "Please enable location services first.");
        }
    }

    private void toggleSatelliteView() {
        if (locationEnabled && mapView != null) {
            mainController.showNotification("Map", "Switching to satellite view...");
            // Toggle between map and satellite view
        } else {
            mainController.showNotification("Map", "Please enable location services first.");
        }
    }

    private void openFullscreenMap() {
        if (locationEnabled) {
            mainController.showNotification("Map", "Opening fullscreen map view...");
            // Open fullscreen map window
        } else {
            mainController.showNotification("Map", "Please enable location services first.");
        }
    }

    private void filterHospitals() {
        String searchText = searchField.getText().toLowerCase();
        String selectedLocation = locationCombo.getValue();
        String selectedSpecialty = specialtyCombo.getValue();

        List<Hospital> filteredHospitals = allHospitals.stream()
            .filter(hospital -> {
                boolean matchesSearch = searchText.isEmpty() || 
                    hospital.getName().toLowerCase().contains(searchText) ||
                    hospital.getSpecialty().toLowerCase().contains(searchText);
                
                boolean matchesLocation = selectedLocation.equals("All Locations") || 
                    hospital.getLocation().equals(selectedLocation);
                
                boolean matchesSpecialty = selectedSpecialty.equals("All Specialties") || 
                    hospital.getSpecialty().equals(selectedSpecialty);
                
                return matchesSearch && matchesLocation && matchesSpecialty;
            })
            .collect(Collectors.toList());

        displayHospitals(filteredHospitals);
    }

    private void displayHospitals(List<Hospital> hospitals) {
        hospitalsContainer.getChildren().clear();

        if (hospitals.isEmpty()) {
            Label noResultsLabel = new Label("No hospitals found matching your criteria.");
            noResultsLabel.setFont(Font.font("System", 14));
            noResultsLabel.setTextFill(Color.valueOf("#6b7280"));
            noResultsLabel.setAlignment(Pos.CENTER);
            noResultsLabel.setPadding(new Insets(20));
            hospitalsContainer.getChildren().add(noResultsLabel);
            return;
        }

        for (Hospital hospital : hospitals) {
            VBox hospitalCard = createHospitalCard(hospital);
            hospitalsContainer.getChildren().add(hospitalCard);
        }
    }

    private VBox createHospitalCard(Hospital hospital) {
        VBox card = new VBox();
        card.setSpacing(10);
        card.setPadding(new Insets(20));
        card.getStyleClass().add("hospital-card");

        // Hospital header
        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);

        Label nameLabel = new Label(hospital.getName());
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        nameLabel.setTextFill(Color.valueOf("#1f2937"));

        Label typeLabel = new Label(hospital.getType());
        typeLabel.setFont(Font.font("System", 10));
        typeLabel.setTextFill(Color.valueOf("#059669"));
        typeLabel.setPadding(new Insets(2, 8, 2, 8));
        typeLabel.getStyleClass().add("hospital-type-badge");

        header.getChildren().addAll(nameLabel, typeLabel);

        // Hospital details
        VBox details = new VBox(5);

        Label specialtyLabel = new Label("Specialty: " + hospital.getSpecialty());
        specialtyLabel.setFont(Font.font("System", 12));
        specialtyLabel.setTextFill(Color.valueOf("#6b7280"));

        Label locationLabel = new Label("📍 " + hospital.getLocation());
        locationLabel.setFont(Font.font("System", 12));
        locationLabel.setTextFill(Color.valueOf("#6b7280"));

        Label distanceLabel = new Label("Distance: " + hospital.getDistance() + " km");
        distanceLabel.setFont(Font.font("System", 12));
        distanceLabel.setTextFill(Color.valueOf("#059669"));

        Label bedsLabel = new Label("Available Beds: " + hospital.getAvailableBeds());
        bedsLabel.setFont(Font.font("System", 12));
        bedsLabel.setTextFill(Color.valueOf("#dc2626"));

        details.getChildren().addAll(specialtyLabel, locationLabel, distanceLabel, bedsLabel);

        // Action buttons
        HBox actions = new HBox(10);
        actions.setAlignment(Pos.CENTER_LEFT);

        Button viewDetailsButton = new Button("👁️ View Details");
        viewDetailsButton.getStyleClass().add("secondary-button");
        viewDetailsButton.setOnAction(e -> viewHospitalDetails(hospital));

        Button webInfoButton = new Button("🌐 Web Info");
        webInfoButton.getStyleClass().add("secondary-button");
        webInfoButton.setOnAction(e -> openHospitalWebInfo(hospital));

        Button directionsButton = new Button("🗺️ Get Directions");
        directionsButton.getStyleClass().add("secondary-button");
        directionsButton.setOnAction(e -> getDirections(hospital));

        Button callButton = new Button("📞 Call");
        callButton.getStyleClass().add("primary-button");
        callButton.setOnAction(e -> callHospital(hospital));

        actions.getChildren().addAll(viewDetailsButton, webInfoButton, directionsButton, callButton);

        card.getChildren().addAll(header, details, actions);
        return card;
    }

    private void viewHospitalDetails(Hospital hospital) {
        mainController.showNotification("Hospital Details", "Opening detailed information for " + hospital.getName());
        // In real implementation, this would open a detailed hospital page
    }

    private void openHospitalWebInfo(Hospital hospital) {
        mainController.showNotification("Web Info", "Opening web information for " + hospital.getName());
        // In real implementation, this would open hospital's website or information page
        if (webEngine != null) {
            String hospitalWebUrl = "https://www.google.com/search?q=" + hospital.getName().replace(" ", "+") + "+hospital+" + hospital.getLocation();
            webEngine.load(hospitalWebUrl);
        }
    }

    private void getDirections(Hospital hospital) {
        mainController.showNotification("Directions", "Opening directions to " + hospital.getName());
        // In real implementation, this would open Google Maps with directions
        if (webEngine != null) {
            String directionsUrl = "https://www.google.com/maps/dir/" + currentLocation.replace(" ", "+") + "/" + hospital.getName().replace(" ", "+") + "+" + hospital.getLocation().replace(" ", "+");
            webEngine.load(directionsUrl);
        }
    }

    private void callHospital(Hospital hospital) {
        mainController.showNotification("Call", "Calling " + hospital.getName());
        // In real implementation, this would initiate a phone call
    }

    private void loadSampleData() {
        allHospitals = new ArrayList<>();
        allHospitals.add(new Hospital("Apollo Hospital", "Multi-specialty", "Cardiology", "Mumbai", "2.5", "15"));
        allHospitals.add(new Hospital("Fortis Hospital", "Private", "Emergency Care", "Mumbai", "3.2", "8"));
        allHospitals.add(new Hospital("AIIMS Delhi", "Government", "General Medicine", "Delhi", "1.8", "25"));
        allHospitals.add(new Hospital("Manipal Hospital", "Private", "Neurology", "Bangalore", "4.1", "12"));
        allHospitals.add(new Hospital("Civil Hospital", "Government", "Emergency Care", "Ahmedabad", "2.9", "20"));
        allHospitals.add(new Hospital("Kokilaben Hospital", "Private", "Cardiology", "Mumbai", "1.5", "10"));
        allHospitals.add(new Hospital("Safdarjung Hospital", "Government", "General Medicine", "Delhi", "3.7", "30"));
        allHospitals.add(new Hospital("Narayana Health", "Private", "Pediatrics", "Bangalore", "2.8", "18"));
        allHospitals.add(new Hospital("KEM Hospital", "Government", "Emergency Care", "Mumbai", "1.2", "22"));
        allHospitals.add(new Hospital("Breach Candy Hospital", "Private", "Gynecology", "Mumbai", "3.5", "6"));

        displayHospitals(allHospitals);
    }

    public VBox getHospitalsView() {
        return hospitalsView;
    }

    public ScrollPane getHospitalsScrollView() {
        return scrollPane;
    }

    // Inner class for hospital data
    public static class Hospital {
        private String name, type, specialty, location, distance, availableBeds;

        public Hospital(String name, String type, String specialty, String location, String distance, String availableBeds) {
            this.name = name;
            this.type = type;
            this.specialty = specialty;
            this.location = location;
            this.distance = distance;
            this.availableBeds = availableBeds;
        }

        public String getName() { return name; }
        public String getType() { return type; }
        public String getSpecialty() { return specialty; }
        public String getLocation() { return location; }
        public String getDistance() { return distance; }
        public String getAvailableBeds() { return availableBeds; }
    }
}
