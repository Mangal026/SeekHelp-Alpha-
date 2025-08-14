package com.seekhelp.desktop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MainController {

    @Autowired
    private AuthService authService;

    private BorderPane root;
    private HeaderController headerController;
    private SidebarController sidebarController;
    private HomeController homeController;
    private BloodDonorsController bloodDonorsController;
    private HospitalsController hospitalsController;
    private SymptomCheckerController symptomCheckerController;
    private AIMedicalBotController aiMedicalBotController;
    private ProfileController profileController;
    private AboutController aboutController;
    private AuthController authController;
    private EmergencyController emergencyController;
    private AdminController adminController;
    private OrganDonorsController organDonorsController;
    private StemCellController stemCellController;
    private MedicalShopController medicalShopController;
    private PrescriptionAnalyzerController prescriptionAnalyzerController;
    private DoctorConsultationController doctorConsultationController;
    private MessageCenterController messageCenterController;
    private SettingsController settingsController;

    private String currentUser = "Guest";
    private boolean isAdmin = false;
    private boolean isAuthenticated = false;

    public MainController() {
        initializeUI();
    }

    private void initializeUI() {
        root = new BorderPane();
        root.getStyleClass().add("main-container");
        
        // Initialize controllers
        headerController = new HeaderController(this);
        sidebarController = new SidebarController(this);
        homeController = new HomeController(this);
        bloodDonorsController = new BloodDonorsController(this);
        hospitalsController = new HospitalsController(this);
        symptomCheckerController = new SymptomCheckerController(this);
        aiMedicalBotController = new AIMedicalBotController(this);
        profileController = new ProfileController(this);
        aboutController = new AboutController(this);
        authController = new AuthController(this);
        emergencyController = new EmergencyController(this);
        adminController = new AdminController(this);
        organDonorsController = new OrganDonorsController(this);
        stemCellController = new StemCellController(this);
        medicalShopController = new MedicalShopController(this);
        prescriptionAnalyzerController = new PrescriptionAnalyzerController(this);
        doctorConsultationController = new DoctorConsultationController(this);
        messageCenterController = new MessageCenterController(this);
        settingsController = new SettingsController(this);

        // Set up initial layout
        setupLayout();
    }

    private void setupLayout() {
        // Add header
        root.setTop(headerController.getHeader());
        
        // Add sidebar
        root.setLeft(sidebarController.getSidebar());
        
        // Show auth page initially
        showAuthPage();
    }

    public void showAuthPage() {
        root.setCenter(authController.getAuthView());
        headerController.setVisible(false);
        sidebarController.setVisible(false);
    }

    public void showHomePage() {
        if (!isAuthenticated) {
            showAuthPage();
            return;
        }
        
        root.setCenter(homeController.getHomeScrollView());
        headerController.setVisible(true);
        sidebarController.setVisible(true);
        headerController.updateUserInfo(currentUser, isAdmin);
    }

    public void showBloodDonorsPage() {
        if (!isAuthenticated) {
            showAuthPage();
            return;
        }
        
        root.setCenter(bloodDonorsController.getBloodDonorsView());
        headerController.setVisible(true);
        sidebarController.setVisible(true);
    }

    public void showHospitalsPage() {
        if (!isAuthenticated) {
            showAuthPage();
            return;
        }
        
        root.setCenter(hospitalsController.getHospitalsScrollView());
        headerController.setVisible(true);
        sidebarController.setVisible(true);
    }

    public void showSymptomCheckerPage() {
        if (!isAuthenticated) {
            showAuthPage();
            return;
        }
        
        root.setCenter(symptomCheckerController.getSymptomCheckerScrollView());
        headerController.setVisible(true);
        sidebarController.setVisible(true);
    }

    public void showAIMedicalBotPage() {
        if (!isAuthenticated) {
            showAuthPage();
            return;
        }
        
        root.setCenter(aiMedicalBotController.getAIMedicalBotView());
        headerController.setVisible(true);
        sidebarController.setVisible(true);
    }

    public void showProfilePage() {
        if (!isAuthenticated) {
            showAuthPage();
            return;
        }
        
        root.setCenter(profileController.getProfileScrollView());
        headerController.setVisible(true);
        sidebarController.setVisible(true);
    }

    public void showAboutPage() {
        root.setCenter(aboutController.getAboutScrollView());
        headerController.setVisible(true);
        sidebarController.setVisible(true);
    }

    public void showEmergencyServices() {
        if (!isAuthenticated) {
            showAuthPage();
            return;
        }
        
        root.setCenter(emergencyController.getEmergencyView());
        headerController.setVisible(true);
        sidebarController.setVisible(true);
    }

    public void showOrganDonorsPage() {
        if (!isAuthenticated) {
            showAuthPage();
            return;
        }
        
        root.setCenter(organDonorsController.getOrganDonorsScrollView());
        headerController.setVisible(true);
        sidebarController.setVisible(true);
    }

    public void showStemCellPage() {
        if (!isAuthenticated) {
            showAuthPage();
            return;
        }
        
        root.setCenter(stemCellController.getStemCellScrollView());
        headerController.setVisible(true);
        sidebarController.setVisible(true);
    }

    public void showMedicalShopPage() {
        if (!isAuthenticated) {
            showAuthPage();
            return;
        }
        
        root.setCenter(medicalShopController.getMedicalShopScrollView());
        headerController.setVisible(true);
        sidebarController.setVisible(true);
    }

    public void showPrescriptionAnalyzerPage() {
        if (!isAuthenticated) {
            showAuthPage();
            return;
        }
        
        root.setCenter(prescriptionAnalyzerController.getPrescriptionAnalyzerScrollView());
        headerController.setVisible(true);
        sidebarController.setVisible(true);
    }

    public void showDoctorConsultationPage() {
        if (!isAuthenticated) {
            showAuthPage();
            return;
        }
        
        root.setCenter(doctorConsultationController.getDoctorConsultationScrollView());
        headerController.setVisible(true);
        sidebarController.setVisible(true);
    }

    public void showMessageCenterPage() {
        if (!isAuthenticated) {
            showAuthPage();
            return;
        }
        
        root.setCenter(messageCenterController.getMessageCenterScrollView());
        headerController.setVisible(true);
        sidebarController.setVisible(true);
    }

    public void showSettingsPage() {
        if (!isAuthenticated) {
            showAuthPage();
            return;
        }
        
        root.setCenter(settingsController.getSettingsScrollView());
        headerController.setVisible(true);
        sidebarController.setVisible(true);
    }

    public void showAdminDashboard() {
        if (!isAuthenticated || !isAdmin) {
            showError("Access Denied", "Admin access required.");
            return;
        }
        
        root.setCenter(adminController.getAdminView());
        headerController.setVisible(true);
        sidebarController.setVisible(true);
    }

    public void login(String username, String password) {
        try {
            // Simulate authentication
            if ("admin".equals(username) && "admin123".equals(password)) {
                currentUser = username;
                isAdmin = true;
                isAuthenticated = true;
                showHomePage();
                showNotification("Welcome Admin!", "Successfully logged in as administrator.");
            } else if ("guest".equals(username) && "guest123".equals(password)) {
                currentUser = "Guest User";
                isAdmin = false;
                isAuthenticated = true;
                showHomePage();
                showNotification("Welcome!", "Successfully logged in as guest user.");
            } else {
                showError("Login Failed", "Invalid username or password.");
            }
        } catch (Exception e) {
            showError("Login Error", "An error occurred during login: " + e.getMessage());
        }
    }

    public void logout() {
        isAuthenticated = false;
        currentUser = "Guest";
        isAdmin = false;
        showAuthPage();
        showNotification("Logged Out", "You have been successfully logged out.");
    }

    public void showNotification(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public BorderPane getRoot() {
        return root;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }
}
