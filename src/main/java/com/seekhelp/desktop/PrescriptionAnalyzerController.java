package com.seekhelp.desktop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class PrescriptionAnalyzerController {

    private MainController mainController;
    private VBox prescriptionAnalyzerView;
    private ScrollPane scrollPane;
    private VBox analysisResultContainer;
    private ProgressIndicator loadingIndicator;
    private Label statusLabel;
    private Button analyzeButton;

    public PrescriptionAnalyzerController(MainController mainController) {
        this.mainController = mainController;
        initializePrescriptionAnalyzerView();
    }

    private void initializePrescriptionAnalyzerView() {
        // Create scroll pane for the entire view
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("prescription-analyzer-scroll-pane");

        prescriptionAnalyzerView = new VBox();
        prescriptionAnalyzerView.getStyleClass().add("prescription-analyzer-container");
        prescriptionAnalyzerView.setPadding(new Insets(30));
        prescriptionAnalyzerView.setSpacing(25);

        // Header
        VBox header = createHeader();

        // Upload section
        VBox uploadSection = createUploadSection();

        // Analysis section
        VBox analysisSection = createAnalysisSection();

        // Medicine information
        VBox medicineInfoSection = createMedicineInfoSection();

        // Safety tips
        VBox safetySection = createSafetySection();

        prescriptionAnalyzerView.getChildren().addAll(
            header, 
            uploadSection, 
            analysisSection, 
            medicineInfoSection, 
            safetySection
        );

        scrollPane.setContent(prescriptionAnalyzerView);
    }

    private VBox createHeader() {
        VBox header = new VBox();
        header.setSpacing(15);

        Label title = new Label("📋 AI Prescription Analyzer");
        title.setFont(Font.font("System", FontWeight.BOLD, 32));
        title.setTextFill(Color.valueOf("#1f2937"));

        Label subtitle = new Label("Get AI-powered analysis of your prescription with detailed medicine information");
        subtitle.setFont(Font.font("System", 18));
        subtitle.setTextFill(Color.valueOf("#6b7280"));

        // Features banner
        VBox featuresBox = new VBox(10);
        featuresBox.setPadding(new Insets(15));
        featuresBox.getStyleClass().add("features-box");
        featuresBox.setAlignment(Pos.CENTER);

        Label featuresTitle = new Label("🤖 Powered by Advanced AI");
        featuresTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        featuresTitle.setTextFill(Color.valueOf("#7c3aed"));

        Label featuresText = new Label(
            "• OCR Text Recognition • Medicine Information • Side Effects • Dosage Guidelines • Drug Interactions"
        );
        featuresText.setFont(Font.font("System", 14));
        featuresText.setTextFill(Color.valueOf("#374151"));
        featuresText.setWrapText(true);
        featuresText.setAlignment(Pos.CENTER);

        featuresBox.getChildren().addAll(featuresTitle, featuresText);

        header.getChildren().addAll(title, subtitle, featuresBox);
        return header;
    }

    private VBox createUploadSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("upload-section");

        Label sectionTitle = new Label("📤 Upload Prescription");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        Label sectionDesc = new Label("Upload a clear image of your prescription for AI analysis:");
        sectionDesc.setFont(Font.font("System", 14));
        sectionDesc.setTextFill(Color.valueOf("#6b7280"));

        VBox uploadBox = new VBox(20);
        uploadBox.setPadding(new Insets(30));
        uploadBox.getStyleClass().add("upload-box");
        uploadBox.setAlignment(Pos.CENTER);

        Label uploadIcon = new Label("📄");
        uploadIcon.setStyle("-fx-font-size: 64px;");

        Label uploadText = new Label("Click to upload prescription image");
        uploadText.setFont(Font.font("System", 18));
        uploadText.setTextFill(Color.valueOf("#6b7280"));

        Button uploadButton = new Button("📁 Choose Image File");
        uploadButton.getStyleClass().add("upload-button");
        uploadButton.setOnAction(e -> uploadPrescription());

        Label supportedFormats = new Label("Supported formats: JPG, PNG, PDF");
        supportedFormats.setFont(Font.font("System", 12));
        supportedFormats.setTextFill(Color.valueOf("#9ca3af"));

        uploadBox.getChildren().addAll(uploadIcon, uploadText, uploadButton, supportedFormats);

        section.getChildren().addAll(sectionTitle, sectionDesc, uploadBox);
        return section;
    }

    private VBox createAnalysisSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("analysis-section");

        Label sectionTitle = new Label("🔍 AI Analysis");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // Analysis controls
        HBox controls = new HBox(15);
        controls.setAlignment(Pos.CENTER_LEFT);

        analyzeButton = new Button("🤖 Analyze Prescription");
        analyzeButton.getStyleClass().add("primary-button");
        analyzeButton.setOnAction(e -> analyzePrescription());

        Button clearButton = new Button("🗑️ Clear");
        clearButton.getStyleClass().add("secondary-button");
        clearButton.setOnAction(e -> clearAnalysis());

        controls.getChildren().addAll(analyzeButton, clearButton);

        // Loading indicator
        loadingIndicator = new ProgressIndicator();
        loadingIndicator.setVisible(false);
        loadingIndicator.setMaxSize(30, 30);

        // Status label
        statusLabel = new Label("");
        statusLabel.setFont(Font.font("System", 12));
        statusLabel.setTextFill(Color.valueOf("#6b7280"));

        // Analysis result container
        analysisResultContainer = new VBox();
        analysisResultContainer.setSpacing(15);
        analysisResultContainer.setPadding(new Insets(20));
        analysisResultContainer.getStyleClass().add("analysis-result-container");
        analysisResultContainer.setVisible(false);

        section.getChildren().addAll(sectionTitle, controls, loadingIndicator, statusLabel, analysisResultContainer);
        return section;
    }

    private VBox createMedicineInfoSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("medicine-info-section");

        Label sectionTitle = new Label("💊 Common Medicines Database");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        Label sectionDesc = new Label("Quick reference for common medicines and their information:");
        sectionDesc.setFont(Font.font("System", 14));
        sectionDesc.setTextFill(Color.valueOf("#6b7280"));

        VBox medicinesContainer = new VBox(15);
        medicinesContainer.getChildren().addAll(
            createMedicineCard("Paracetamol", "Fever & Pain Relief", "500-1000mg every 4-6 hours", "Headache, nausea, liver problems"),
            createMedicineCard("Ibuprofen", "Inflammation & Pain", "200-400mg every 4-6 hours", "Stomach upset, dizziness, allergic reactions"),
            createMedicineCard("Omeprazole", "Acid Reflux", "20mg once daily", "Headache, diarrhea, abdominal pain"),
            createMedicineCard("Metformin", "Diabetes", "500-1000mg twice daily", "Nausea, diarrhea, lactic acidosis"),
            createMedicineCard("Amoxicillin", "Antibiotic", "500mg three times daily", "Diarrhea, rash, allergic reactions"),
            createMedicineCard("Cetirizine", "Allergies", "10mg once daily", "Drowsiness, dry mouth, headache")
        );

        section.getChildren().addAll(sectionTitle, sectionDesc, medicinesContainer);
        return section;
    }

    private VBox createMedicineCard(String name, String use, String dosage, String sideEffects) {
        VBox card = new VBox();
        card.setSpacing(10);
        card.setPadding(new Insets(15));
        card.getStyleClass().add("medicine-card");

        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        nameLabel.setTextFill(Color.valueOf("#1f2937"));

        Label useLabel = new Label("Use: " + use);
        useLabel.setFont(Font.font("System", 12));
        useLabel.setTextFill(Color.valueOf("#059669"));

        Label dosageLabel = new Label("Dosage: " + dosage);
        dosageLabel.setFont(Font.font("System", 12));
        dosageLabel.setTextFill(Color.valueOf("#1f2937"));

        Label sideEffectsLabel = new Label("Side Effects: " + sideEffects);
        sideEffectsLabel.setFont(Font.font("System", 12));
        sideEffectsLabel.setTextFill(Color.valueOf("#dc2626"));
        sideEffectsLabel.setWrapText(true);

        card.getChildren().addAll(nameLabel, useLabel, dosageLabel, sideEffectsLabel);
        return card;
    }

    private VBox createSafetySection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("safety-section");

        Label sectionTitle = new Label("⚠️ Important Safety Information");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        VBox safetyContent = new VBox(15);
        safetyContent.getChildren().addAll(
            createSafetyCard("🔒 Privacy", "Your prescription data is encrypted and secure. We never share your information."),
            createSafetyCard("👨‍⚕️ Medical Advice", "This analysis is for informational purposes only. Always consult your doctor."),
            createSafetyCard("💊 Dosage", "Never change your medication dosage without consulting a healthcare professional."),
            createSafetyCard("🚨 Emergencies", "If you experience severe side effects, contact emergency services immediately."),
            createSafetyCard("📋 Accuracy", "While our AI is advanced, human verification is recommended for critical decisions."),
            createSafetyCard("🔄 Updates", "Medicine information is regularly updated, but consult your pharmacist for the latest details.")
        );

        section.getChildren().addAll(sectionTitle, safetyContent);
        return section;
    }

    private VBox createSafetyCard(String title, String content) {
        VBox card = new VBox();
        card.setSpacing(8);
        card.setPadding(new Insets(15));
        card.getStyleClass().add("safety-card");

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

    private void uploadPrescription() {
        mainController.showNotification("Upload", "Prescription upload feature will be implemented with image processing and OCR.");
    }

    private void analyzePrescription() {
        // Show loading
        analyzeButton.setDisable(true);
        loadingIndicator.setVisible(true);
        statusLabel.setText("Analyzing prescription with AI...");
        analysisResultContainer.setVisible(false);

        // Simulate AI analysis
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(3000); // Simulate processing time
                
                // Generate analysis result
                String analysisResult = generateAnalysisResult();
                
                // Update UI on JavaFX thread
                javafx.application.Platform.runLater(() -> {
                    displayAnalysisResult(analysisResult);
                    analyzeButton.setDisable(false);
                    loadingIndicator.setVisible(false);
                    statusLabel.setText("Analysis complete");
                });
            } catch (Exception e) {
                javafx.application.Platform.runLater(() -> {
                    mainController.showNotification("Error", "Failed to analyze prescription. Please try again.");
                    analyzeButton.setDisable(false);
                    loadingIndicator.setVisible(false);
                    statusLabel.setText("Analysis failed");
                });
            }
        });
    }

    private String generateAnalysisResult() {
        StringBuilder result = new StringBuilder();
        result.append("🤖 **AI Prescription Analysis Report**\n\n");
        
        result.append("**📋 Prescription Details:**\n");
        result.append("• Doctor: Dr. Sharma\n");
        result.append("• Date: 15 January 2024\n");
        result.append("• Patient: [Patient Name]\n\n");
        
        result.append("**💊 Medicines Identified:**\n");
        result.append("1. **Paracetamol 500mg**\n");
        result.append("   - Dosage: 1 tablet every 6 hours\n");
        result.append("   - Duration: 3 days\n");
        result.append("   - Purpose: Fever and pain relief\n\n");
        
        result.append("2. **Omeprazole 20mg**\n");
        result.append("   - Dosage: 1 capsule daily\n");
        result.append("   - Duration: 7 days\n");
        result.append("   - Purpose: Acid reflux treatment\n\n");
        
        result.append("**⚠️ Important Information:**\n");
        result.append("• Take Paracetamol with food to avoid stomach upset\n");
        result.append("• Omeprazole should be taken 30 minutes before meals\n");
        result.append("• Avoid alcohol while taking these medications\n");
        result.append("• Contact doctor if symptoms persist beyond prescribed duration\n\n");
        
        result.append("**🔍 AI Confidence Score: 95%**\n");
        result.append("The AI has high confidence in the prescription analysis.\n\n");
        
        result.append("**📞 Next Steps:**\n");
        result.append("• Verify the analysis with your pharmacist\n");
        result.append("• Follow the prescribed dosage strictly\n");
        result.append("• Store medicines in a cool, dry place\n");
        result.append("• Complete the full course of medication\n\n");
        
        result.append("**💙 Stay Safe:**\n");
        result.append("This analysis is for informational purposes. Always consult your healthcare provider for medical advice.");
        
        return result.toString();
    }

    private void displayAnalysisResult(String result) {
        analysisResultContainer.getChildren().clear();
        analysisResultContainer.setVisible(true);

        // Create result display
        TextArea resultArea = new TextArea(result);
        resultArea.setEditable(false);
        resultArea.setWrapText(true);
        resultArea.setPrefRowCount(20);
        resultArea.getStyleClass().add("analysis-result-text");
        resultArea.setFont(Font.font("System", 12));

        // Add copy button
        Button copyButton = new Button("📋 Copy Analysis");
        copyButton.getStyleClass().add("secondary-button");
        copyButton.setOnAction(e -> {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(result);
            clipboard.setContent(content);
            mainController.showNotification("Copied", "Analysis copied to clipboard");
        });

        // Add print button
        Button printButton = new Button("🖨️ Print Analysis");
        printButton.getStyleClass().add("secondary-button");
        printButton.setOnAction(e -> {
            mainController.showNotification("Print", "Print functionality will be implemented.");
        });

        HBox buttons = new HBox(15);
        buttons.setAlignment(Pos.CENTER_LEFT);
        buttons.getChildren().addAll(copyButton, printButton);

        analysisResultContainer.getChildren().addAll(resultArea, buttons);
    }

    private void clearAnalysis() {
        analysisResultContainer.setVisible(false);
        analysisResultContainer.getChildren().clear();
        statusLabel.setText("");
        loadingIndicator.setVisible(false);
        analyzeButton.setDisable(false);
    }

    public VBox getPrescriptionAnalyzerView() {
        return prescriptionAnalyzerView;
    }

    public ScrollPane getPrescriptionAnalyzerScrollView() {
        return scrollPane;
    }
}
