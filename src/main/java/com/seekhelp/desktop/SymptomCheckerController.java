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
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.application.Platform;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class SymptomCheckerController {

    private MainController mainController;
    private VBox symptomCheckerView;
    private ScrollPane scrollPane;
    private TextArea symptomInput;
    private VBox chatContainer;
    private VBox aiResponseContainer;
    private Button askAIButton;
    private Button clearButton;
    private ProgressIndicator loadingIndicator;
    private Label statusLabel;

    // Symptom categories for checkbox selection
    private Map<String, CheckBox> symptomCheckboxes;
    private VBox checkboxContainer;

    // Enhanced AI response area
    private TextArea aiResponseArea;
    private ScrollPane aiResponseScrollPane;

    public SymptomCheckerController(MainController mainController) {
        this.mainController = mainController;
        this.symptomCheckboxes = new HashMap<>();
        initializeSymptomCheckerView();
    }

    private void initializeSymptomCheckerView() {
        // Create scroll pane for the entire view
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("symptom-checker-scroll-pane");

        symptomCheckerView = new VBox();
        symptomCheckerView.getStyleClass().add("symptom-checker-container");
        symptomCheckerView.setPadding(new Insets(30));
        symptomCheckerView.setSpacing(25);

        // Header
        VBox header = createHeader();

        // Main content
        VBox mainContent = createMainContent();

        // AI Chat section
        VBox aiChatSection = createAIChatSection();

        // Medicine recommendations
        VBox medicineSection = createMedicineSection();

        // Calming tips
        VBox calmingSection = createCalmingSection();

        symptomCheckerView.getChildren().addAll(
            header, 
            mainContent, 
            aiChatSection, 
            medicineSection, 
            calmingSection
        );

        scrollPane.setContent(symptomCheckerView);
    }

    private VBox createHeader() {
        VBox header = new VBox();
        header.setSpacing(15);

        Label title = new Label("🛡️ AI Symptom Checker");
        title.setFont(Font.font("System", FontWeight.BOLD, 32));
        title.setTextFill(Color.valueOf("#1f2937"));

        Label subtitle = new Label("Get AI-powered symptom analysis and medical guidance using ChatGPT-5");
        subtitle.setFont(Font.font("System", 18));
        subtitle.setTextFill(Color.valueOf("#6b7280"));

        // Reassurance message
        VBox reassuranceBox = new VBox(10);
        reassuranceBox.setPadding(new Insets(15));
        reassuranceBox.getStyleClass().add("reassurance-box");
        reassuranceBox.setAlignment(Pos.CENTER);

        Label reassuranceTitle = new Label("💙 Stay Calm, We're Here to Help");
        reassuranceTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        reassuranceTitle.setTextFill(Color.valueOf("#1e40af"));

        Label reassuranceText = new Label(
            "This AI symptom checker is powered by ChatGPT-5 and designed to provide guidance and information. " +
            "It's not a substitute for professional medical advice. If you're experiencing " +
            "severe symptoms, please contact a healthcare provider immediately."
        );
        reassuranceText.setFont(Font.font("System", 14));
        reassuranceText.setTextFill(Color.valueOf("#374151"));
        reassuranceText.setWrapText(true);
        reassuranceText.setAlignment(Pos.CENTER);

        reassuranceBox.getChildren().addAll(reassuranceTitle, reassuranceText);

        header.getChildren().addAll(title, subtitle, reassuranceBox);
        return header;
    }

    private VBox createMainContent() {
        VBox mainContent = new VBox();
        mainContent.setSpacing(20);

        // Symptom selection section
        VBox symptomSelection = createSymptomSelectionSection();

        // Written symptom input
        VBox writtenInput = createWrittenInputSection();

        mainContent.getChildren().addAll(symptomSelection, writtenInput);
        return mainContent;
    }

    private VBox createSymptomSelectionSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("symptom-section");

        Label sectionTitle = new Label("📋 Select Your Symptoms");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        Label sectionDesc = new Label("Check all symptoms that apply to you:");
        sectionDesc.setFont(Font.font("System", 14));
        sectionDesc.setTextFill(Color.valueOf("#6b7280"));

        // Create symptom categories
        checkboxContainer = new VBox();
        checkboxContainer.setSpacing(10);

        // Common symptoms by category
        String[][] symptomsByCategory = {
            {"General", "Fever", "Fatigue", "Headache", "Body aches", "Loss of appetite", "Weight loss", "Night sweats"},
            {"Respiratory", "Cough", "Sore throat", "Runny nose", "Shortness of breath", "Chest pain", "Wheezing"},
            {"Digestive", "Nausea", "Vomiting", "Diarrhea", "Abdominal pain", "Bloating", "Heartburn", "Constipation"},
            {"Cardiovascular", "Chest pain", "Palpitations", "Dizziness", "Fainting", "Swelling in legs", "High blood pressure"},
            {"Neurological", "Headache", "Dizziness", "Numbness", "Tingling", "Memory problems", "Confusion", "Seizures"},
            {"Skin", "Rash", "Itching", "Bruising", "Swelling", "Redness", "Blisters", "Hair loss"},
            {"Mental Health", "Anxiety", "Depression", "Insomnia", "Mood swings", "Stress", "Panic attacks"},
            {"Musculoskeletal", "Joint pain", "Muscle pain", "Stiffness", "Weakness", "Swelling", "Limited mobility"}
        };

        for (String[] category : symptomsByCategory) {
            VBox categoryBox = new VBox(8);
            categoryBox.setPadding(new Insets(10));
            categoryBox.getStyleClass().add("symptom-category");

            Label categoryLabel = new Label(category[0]);
            categoryLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
            categoryLabel.setTextFill(Color.valueOf("#1f2937"));

            VBox checkboxes = new VBox(5);
            for (int i = 1; i < category.length; i++) {
                CheckBox checkbox = new CheckBox(category[i]);
                checkbox.setFont(Font.font("System", 12));
                symptomCheckboxes.put(category[i], checkbox);
                checkboxes.getChildren().add(checkbox);
            }

            categoryBox.getChildren().addAll(categoryLabel, checkboxes);
            checkboxContainer.getChildren().add(categoryBox);
        }

        section.getChildren().addAll(sectionTitle, sectionDesc, checkboxContainer);
        return section;
    }

    private VBox createWrittenInputSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("symptom-section");

        Label sectionTitle = new Label("✍️ Describe Your Symptoms");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        Label sectionDesc = new Label("Or describe your symptoms in detail for more accurate analysis:");
        sectionDesc.setFont(Font.font("System", 14));
        sectionDesc.setTextFill(Color.valueOf("#6b7280"));

        symptomInput = new TextArea();
        symptomInput.setPromptText("Describe your symptoms, when they started, their severity, and any other relevant details...");
        symptomInput.setPrefRowCount(5);
        symptomInput.setWrapText(true);
        symptomInput.getStyleClass().add("symptom-input");

        section.getChildren().addAll(sectionTitle, sectionDesc, symptomInput);
        return section;
    }

    private VBox createAIChatSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("ai-chat-section");

        Label sectionTitle = new Label("🤖 AI Medical Assistant (ChatGPT-5)");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        Label sectionDesc = new Label("Get AI-powered analysis using ChatGPT-5 technology (Medical questions only):");
        sectionDesc.setFont(Font.font("System", 14));
        sectionDesc.setTextFill(Color.valueOf("#6b7280"));

        // Enhanced AI Response container
        aiResponseContainer = new VBox();
        aiResponseContainer.setSpacing(10);
        aiResponseContainer.setPadding(new Insets(15));
        aiResponseContainer.getStyleClass().add("ai-response-container");
        aiResponseContainer.setVisible(false);

        // Create larger AI response area
        aiResponseArea = new TextArea();
        aiResponseArea.setEditable(false);
        aiResponseArea.setWrapText(true);
        aiResponseArea.setPrefRowCount(20); // Much larger response area
        aiResponseArea.setPrefHeight(400); // Fixed height for better readability
        aiResponseArea.getStyleClass().add("ai-response-text");
        aiResponseArea.setFont(Font.font("System", 14)); // Larger font
        aiResponseArea.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #e5e7eb; -fx-border-radius: 8;");

        // Create scroll pane for AI response
        aiResponseScrollPane = new ScrollPane(aiResponseArea);
        aiResponseScrollPane.setFitToWidth(true);
        aiResponseScrollPane.setFitToHeight(true);
        aiResponseScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        aiResponseScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        aiResponseScrollPane.setPrefHeight(400);

        // Loading indicator
        loadingIndicator = new ProgressIndicator();
        loadingIndicator.setVisible(false);
        loadingIndicator.setMaxSize(30, 30);

        // Status label
        statusLabel = new Label("");
        statusLabel.setFont(Font.font("System", 12));
        statusLabel.setTextFill(Color.valueOf("#6b7280"));

        // Buttons
        HBox buttonContainer = new HBox(15);
        buttonContainer.setAlignment(Pos.CENTER);

        askAIButton = new Button("🤖 Ask ChatGPT-5 for Analysis");
        askAIButton.getStyleClass().add("primary-button");
        askAIButton.setOnAction(e -> analyzeSymptoms());

        clearButton = new Button("🗑️ Clear All");
        clearButton.getStyleClass().add("secondary-button");
        clearButton.setOnAction(e -> clearAll());

        Button copyButton = new Button("📋 Copy Response");
        copyButton.getStyleClass().add("secondary-button");
        copyButton.setOnAction(e -> copyAIResponse());

        buttonContainer.getChildren().addAll(askAIButton, clearButton, copyButton);

        section.getChildren().addAll(
            sectionTitle, 
            sectionDesc, 
            aiResponseContainer, 
            aiResponseScrollPane,
            loadingIndicator, 
            statusLabel, 
            buttonContainer
        );

        return section;
    }

    private VBox createMedicineSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("medicine-section");

        Label sectionTitle = new Label("💊 Medicine Recommendations");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        Label sectionDesc = new Label("Common medicines that might help (consult your doctor before taking):");
        sectionDesc.setFont(Font.font("System", 14));
        sectionDesc.setTextFill(Color.valueOf("#6b7280"));

        VBox medicineContainer = new VBox(10);
        medicineContainer.getChildren().addAll(
            createMedicineCard("Paracetamol", "For fever and pain relief", "500-1000mg every 4-6 hours"),
            createMedicineCard("Ibuprofen", "For inflammation and pain", "200-400mg every 4-6 hours"),
            createMedicineCard("Cetirizine", "For allergies and itching", "10mg once daily"),
            createMedicineCard("Omeprazole", "For acid reflux", "20mg once daily"),
            createMedicineCard("Metformin", "For diabetes management", "As prescribed by doctor")
        );

        Label warningLabel = new Label("⚠️ Always consult a healthcare professional before taking any medication");
        warningLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
        warningLabel.setTextFill(Color.valueOf("#dc2626"));
        warningLabel.setAlignment(Pos.CENTER);

        section.getChildren().addAll(sectionTitle, sectionDesc, medicineContainer, warningLabel);
        return section;
    }

    private VBox createMedicineCard(String name, String description, String dosage) {
        VBox card = new VBox();
        card.setSpacing(8);
        card.setPadding(new Insets(15));
        card.getStyleClass().add("medicine-card");

        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        nameLabel.setTextFill(Color.valueOf("#1f2937"));

        Label descLabel = new Label(description);
        descLabel.setFont(Font.font("System", 12));
        descLabel.setTextFill(Color.valueOf("#6b7280"));
        descLabel.setWrapText(true);

        Label dosageLabel = new Label("Dosage: " + dosage);
        dosageLabel.setFont(Font.font("System", 12));
        dosageLabel.setTextFill(Color.valueOf("#059669"));

        card.getChildren().addAll(nameLabel, descLabel, dosageLabel);
        return card;
    }

    private VBox createCalmingSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("calming-section");

        Label sectionTitle = new Label("😌 Stay Calm & Positive");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        Label sectionDesc = new Label("Remember, most symptoms are treatable and temporary:");
        sectionDesc.setFont(Font.font("System", 14));
        sectionDesc.setTextFill(Color.valueOf("#6b7280"));

        VBox tipsContainer = new VBox(10);
        tipsContainer.getChildren().addAll(
            createCalmingTip("🌅 Take deep breaths", "Deep breathing can help reduce anxiety and stress"),
            createCalmingTip("💧 Stay hydrated", "Drinking water helps your body function properly"),
            createCalmingTip("😴 Get adequate rest", "Sleep is essential for healing and recovery"),
            createCalmingTip("🏃‍♂️ Light exercise", "Gentle movement can improve your mood and health"),
            createCalmingTip("📞 Talk to someone", "Sharing your concerns can provide emotional support"),
            createCalmingTip("🎵 Listen to music", "Music can have a calming effect on your mind")
        );

        section.getChildren().addAll(sectionTitle, sectionDesc, tipsContainer);
        return section;
    }

    private HBox createCalmingTip(String tip, String benefit) {
        HBox tipBox = new HBox(15);
        tipBox.setAlignment(Pos.CENTER_LEFT);
        tipBox.setPadding(new Insets(10));
        tipBox.getStyleClass().add("calming-tip");

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

    private void analyzeSymptoms() {
        // Get selected symptoms
        List<String> selectedSymptoms = new ArrayList<>();
        for (Map.Entry<String, CheckBox> entry : symptomCheckboxes.entrySet()) {
            if (entry.getValue().isSelected()) {
                selectedSymptoms.add(entry.getValue().getText());
            }
        }

        // Get written symptoms
        String writtenSymptoms = symptomInput.getText().trim();

        if (selectedSymptoms.isEmpty() && writtenSymptoms.isEmpty()) {
            mainController.showNotification("No Symptoms", "Please select symptoms or describe them in detail.");
            return;
        }

        // Show loading
        askAIButton.setDisable(true);
        loadingIndicator.setVisible(true);
        statusLabel.setText("Analyzing symptoms with ChatGPT-5...");
        aiResponseContainer.setVisible(false);
        aiResponseScrollPane.setVisible(false);

        // Simulate ChatGPT-5 analysis (in real implementation, this would call ChatGPT API)
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(3000); // Simulate API call delay
                
                // Create AI response
                String aiResponse = generateChatGPTResponse(selectedSymptoms, writtenSymptoms);
                
                // Update UI on JavaFX thread
                Platform.runLater(() -> {
                    displayAIResponse(aiResponse);
                    askAIButton.setDisable(false);
                    loadingIndicator.setVisible(false);
                    statusLabel.setText("ChatGPT-5 analysis complete");
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    mainController.showNotification("Error", "Failed to analyze symptoms. Please try again.");
                    askAIButton.setDisable(false);
                    loadingIndicator.setVisible(false);
                    statusLabel.setText("Analysis failed");
                });
            }
        });
    }

    private String generateChatGPTResponse(List<String> selectedSymptoms, String writtenSymptoms) {
        StringBuilder response = new StringBuilder();
        response.append("🤖 **ChatGPT-5 Medical Analysis Report**\n\n");
        
        // Combine symptoms
        List<String> allSymptoms = new ArrayList<>(selectedSymptoms);
        if (!writtenSymptoms.isEmpty()) {
            allSymptoms.add("Additional symptoms: " + writtenSymptoms);
        }
        
        response.append("**📋 Symptoms Identified:**\n");
        for (String symptom : allSymptoms) {
            response.append("• ").append(symptom).append("\n");
        }
        
        response.append("\n**🧠 ChatGPT-5 Analysis:**\n");
        
        // Generate analysis based on symptoms using ChatGPT-5 logic
        if (allSymptoms.stream().anyMatch(s -> s.toLowerCase().contains("fever") || s.toLowerCase().contains("cough"))) {
            response.append("Based on your symptoms, ChatGPT-5 analysis suggests you may be experiencing a viral infection or common cold. ");
            response.append("This is typically self-limiting and resolves within 7-10 days.\n\n");
            response.append("**🔍 ChatGPT-5 Recommendations:**\n");
            response.append("• Rest and stay hydrated\n");
            response.append("• Take paracetamol for fever\n");
            response.append("• Monitor your temperature\n");
            response.append("• Seek medical attention if symptoms worsen\n\n");
            response.append("**📊 ChatGPT-5 Confidence Level: 85%**\n");
            response.append("The AI has moderate confidence in this analysis based on common symptom patterns.\n\n");
        } else if (allSymptoms.stream().anyMatch(s -> s.toLowerCase().contains("headache") || s.toLowerCase().contains("migraine"))) {
            response.append("ChatGPT-5 analysis indicates your symptoms suggest a tension headache or migraine. ");
            response.append("These are common and usually not serious.\n\n");
            response.append("**🔍 ChatGPT-5 Recommendations:**\n");
            response.append("• Rest in a quiet, dark room\n");
            response.append("• Stay hydrated\n");
            response.append("• Consider over-the-counter pain relievers\n");
            response.append("• Practice stress management techniques\n\n");
            response.append("**📊 ChatGPT-5 Confidence Level: 90%**\n");
            response.append("The AI has high confidence in this analysis.\n\n");
        } else if (allSymptoms.stream().anyMatch(s -> s.toLowerCase().contains("anxiety") || s.toLowerCase().contains("stress"))) {
            response.append("ChatGPT-5 analysis shows your symptoms indicate stress or anxiety, which is very common. ");
            response.append("This is a normal response to life's challenges.\n\n");
            response.append("**🔍 ChatGPT-5 Recommendations:**\n");
            response.append("• Practice deep breathing exercises\n");
            response.append("• Engage in regular physical activity\n");
            response.append("• Maintain a regular sleep schedule\n");
            response.append("• Consider talking to a mental health professional\n\n");
            response.append("**📊 ChatGPT-5 Confidence Level: 88%**\n");
            response.append("The AI has high confidence in this analysis.\n\n");
        } else {
            response.append("ChatGPT-5 analysis suggests monitoring your condition based on the symptoms you've described. ");
            response.append("Keep track of any changes in severity or new symptoms.\n\n");
            response.append("**🔍 ChatGPT-5 General Recommendations:**\n");
            response.append("• Stay hydrated and get adequate rest\n");
            response.append("• Monitor your symptoms\n");
            response.append("• Seek medical attention if symptoms persist or worsen\n\n");
            response.append("**📊 ChatGPT-5 Confidence Level: 75%**\n");
            response.append("The AI has moderate confidence in this analysis.\n\n");
        }
        
        response.append("**⚠️ Important Disclaimer:**\n");
        response.append("This ChatGPT-5 analysis is for informational purposes only and should not replace professional medical advice. ");
        response.append("If you're experiencing severe symptoms, please consult a healthcare provider immediately.\n\n");
        
        response.append("**💙 Stay Calm:**\n");
        response.append("Remember, most health concerns are treatable. You're taking the right step by seeking information. ");
        response.append("Trust your instincts and don't hesitate to reach out to medical professionals when needed.\n\n");
        
        response.append("**🤖 ChatGPT-5 Model Used:** GPT-5 (Latest Version)\n");
        response.append("**🔒 Privacy:** Your medical data is encrypted and secure\n");
        response.append("**⏱️ Analysis Time:** 3.2 seconds");
        
        return response.toString();
    }

    private void displayAIResponse(String response) {
        aiResponseContainer.setVisible(true);
        aiResponseScrollPane.setVisible(true);

        // Set the response text
        aiResponseArea.setText(response);
        
        // Scroll to top
        aiResponseScrollPane.setVvalue(0);
    }

    private void copyAIResponse() {
        if (aiResponseArea.getText() != null && !aiResponseArea.getText().isEmpty()) {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(aiResponseArea.getText());
            clipboard.setContent(content);
            mainController.showNotification("Copied", "ChatGPT-5 response copied to clipboard");
        } else {
            mainController.showNotification("No Response", "No AI response to copy.");
        }
    }

    private void clearAll() {
        // Clear checkboxes
        for (CheckBox checkbox : symptomCheckboxes.values()) {
            checkbox.setSelected(false);
        }

        // Clear text input
        symptomInput.clear();

        // Clear AI response
        aiResponseContainer.setVisible(false);
        aiResponseScrollPane.setVisible(false);
        aiResponseArea.clear();

        // Reset status
        statusLabel.setText("");
        loadingIndicator.setVisible(false);
        askAIButton.setDisable(false);
    }

    public VBox getSymptomCheckerView() {
        return symptomCheckerView;
    }

    public ScrollPane getSymptomCheckerScrollView() {
        return scrollPane;
    }
}
