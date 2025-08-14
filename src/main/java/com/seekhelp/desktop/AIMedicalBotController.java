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
import java.util.*;

public class AIMedicalBotController {

    private MainController mainController;
    private VBox aiMedicalBotView;
    private ScrollPane chatScrollPane;
    private VBox chatContainer;
    private TextField messageField;
    private Button sendButton;
    private List<ChatMessage> chatHistory = new ArrayList<>();

    public AIMedicalBotController(MainController mainController) {
        this.mainController = mainController;
        initializeAIMedicalBotView();
    }

    private void initializeAIMedicalBotView() {
        aiMedicalBotView = new VBox();
        aiMedicalBotView.getStyleClass().add("ai-medical-bot-container");
        aiMedicalBotView.setPadding(new Insets(30));
        aiMedicalBotView.setSpacing(20);

        // Header
        VBox header = createHeader();

        // Chat interface
        VBox chatInterface = createChatInterface();

        aiMedicalBotView.getChildren().addAll(header, chatInterface);
    }

    private VBox createHeader() {
        VBox header = new VBox();
        header.setSpacing(10);

        Label title = new Label("AI Medical Assistant");
        title.setFont(Font.font("System", FontWeight.BOLD, 28));
        title.setTextFill(Color.valueOf("#1f2937"));

        Label subtitle = new Label("Chat with our AI-powered medical assistant for health guidance");
        subtitle.setFont(Font.font("System", 16));
        subtitle.setTextFill(Color.valueOf("#6b7280"));

        // Bot status indicator
        HBox statusBox = new HBox(10);
        statusBox.setAlignment(Pos.CENTER_LEFT);

        Label statusDot = new Label("🟢");
        statusDot.setStyle("-fx-font-size: 16px;");

        Label statusText = new Label("AI Assistant Online");
        statusText.setFont(Font.font("System", 12));
        statusText.setTextFill(Color.valueOf("#15803d"));

        statusBox.getChildren().addAll(statusDot, statusText);

        header.getChildren().addAll(title, subtitle, statusBox);
        return header;
    }

    private VBox createChatInterface() {
        VBox chatInterface = new VBox();
        chatInterface.setSpacing(15);
        chatInterface.setPrefHeight(500);

        // Chat container with scroll
        chatContainer = new VBox();
        chatContainer.setSpacing(10);
        chatContainer.setPadding(new Insets(10));

        chatScrollPane = new ScrollPane(chatContainer);
        chatScrollPane.setFitToWidth(true);
        chatScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        chatScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        chatScrollPane.setPrefHeight(400);

        // Welcome message
        addBotMessage("Hello! I'm your AI medical assistant. I can help you with:\n" +
            "• General health information\n" +
            "• Symptom explanations\n" +
            "• Medication guidance\n" +
            "• First aid advice\n" +
            "• Health tips and recommendations\n\n" +
            "Please note: I'm not a substitute for professional medical advice. Always consult a healthcare provider for serious concerns.");

        // Quick action buttons
        VBox quickActions = createQuickActions();

        // Message input area
        HBox inputArea = createInputArea();

        chatInterface.getChildren().addAll(chatScrollPane, quickActions, inputArea);
        return chatInterface;
    }

    private VBox createQuickActions() {
        VBox quickActions = new VBox();
        quickActions.setSpacing(10);

        Label quickLabel = new Label("Quick Actions:");
        quickLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        FlowPane actionButtons = new FlowPane();
        actionButtons.setHgap(10);
        actionButtons.setVgap(5);

        String[] actions = {
            "Common Symptoms", "First Aid", "Medication Info", 
            "Health Tips", "Emergency", "Find Hospitals"
        };

        for (String action : actions) {
            Button actionButton = new Button(action);
            actionButton.getStyleClass().add("quick-action-button");
            actionButton.setOnAction(e -> handleQuickAction(action));
            actionButtons.getChildren().add(actionButton);
        }

        quickActions.getChildren().addAll(quickLabel, actionButtons);
        return quickActions;
    }

    private HBox createInputArea() {
        HBox inputArea = new HBox();
        inputArea.setSpacing(10);
        inputArea.setAlignment(Pos.CENTER_LEFT);

        messageField = new TextField();
        messageField.setPromptText("Type your health question here...");
        messageField.setPrefWidth(400);
        messageField.setOnAction(e -> sendMessage());

        sendButton = new Button("Send");
        sendButton.getStyleClass().add("send-button");
        sendButton.setOnAction(e -> sendMessage());

        Button clearButton = new Button("Clear Chat");
        clearButton.getStyleClass().add("clear-button");
        clearButton.setOnAction(e -> clearChat());

        inputArea.getChildren().addAll(messageField, sendButton, clearButton);
        return inputArea;
    }

    private void handleQuickAction(String action) {
        String message = "";
        switch (action) {
            case "Common Symptoms":
                message = "Tell me about common symptoms and when to seek medical help.";
                break;
            case "First Aid":
                message = "What are the basic first aid procedures I should know?";
                break;
            case "Medication Info":
                message = "How should I safely take medications and what are common side effects?";
                break;
            case "Health Tips":
                message = "Give me some general health and wellness tips.";
                break;
            case "Emergency":
                message = "What are the signs of a medical emergency and what should I do?";
                break;
            case "Find Hospitals":
                mainController.showHospitalsPage();
                return;
        }
        
        messageField.setText(message);
        sendMessage();
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (message.isEmpty()) return;

        // Add user message
        addUserMessage(message);
        messageField.clear();

        // Simulate AI response
        sendButton.setDisable(true);
        sendButton.setText("Thinking...");

        new Thread(() -> {
            try {
                Thread.sleep(1000); // Simulate processing time
                String response = generateAIResponse(message);
                
                javafx.application.Platform.runLater(() -> {
                    addBotMessage(response);
                    sendButton.setDisable(false);
                    sendButton.setText("Send");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void addUserMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, true);
        chatHistory.add(chatMessage);
        
        VBox messageBox = createMessageBox(message, true);
        chatContainer.getChildren().add(messageBox);
        
        scrollToBottom();
    }

    private void addBotMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, false);
        chatHistory.add(chatMessage);
        
        VBox messageBox = createMessageBox(message, false);
        chatContainer.getChildren().add(messageBox);
        
        scrollToBottom();
    }

    private VBox createMessageBox(String message, boolean isUser) {
        VBox messageBox = new VBox();
        messageBox.setSpacing(5);
        messageBox.setAlignment(isUser ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

        HBox messageContainer = new HBox();
        messageContainer.setSpacing(10);
        messageContainer.setAlignment(isUser ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

        if (!isUser) {
            Label botIcon = new Label("🤖");
            botIcon.setStyle("-fx-font-size: 16px;");
            messageContainer.getChildren().add(botIcon);
        }

        VBox textContainer = new VBox();
        textContainer.setSpacing(5);

        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(300);
        messageLabel.setPadding(new Insets(10));
        messageLabel.getStyleClass().add(isUser ? "user-message" : "bot-message");

        Label timeLabel = new Label(java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")));
        timeLabel.setFont(Font.font("System", 10));
        timeLabel.setTextFill(Color.valueOf("#9ca3af"));

        textContainer.getChildren().addAll(messageLabel, timeLabel);

        if (isUser) {
            Label userIcon = new Label("👤");
            userIcon.setStyle("-fx-font-size: 16px;");
            messageContainer.getChildren().addAll(textContainer, userIcon);
        } else {
            messageContainer.getChildren().add(textContainer);
        }

        messageBox.getChildren().add(messageContainer);
        return messageBox;
    }

    private void scrollToBottom() {
        chatScrollPane.setVvalue(1.0);
    }

    private void clearChat() {
        chatContainer.getChildren().clear();
        chatHistory.clear();
        addBotMessage("Chat cleared. How can I help you today?");
    }

    private String generateAIResponse(String userMessage) {
        String lowerMessage = userMessage.toLowerCase();
        
        // AI response logic based on user input
        if (lowerMessage.contains("symptom") || lowerMessage.contains("pain") || lowerMessage.contains("fever")) {
            return "I understand you're asking about symptoms. While I can provide general information, " +
                   "it's important to consult with a healthcare provider for proper diagnosis. " +
                   "Could you tell me more about your specific symptoms and their duration?";
        }
        
        if (lowerMessage.contains("medication") || lowerMessage.contains("medicine") || lowerMessage.contains("drug")) {
            return "Medication safety is crucial. Always:\n" +
                   "• Follow your doctor's prescription exactly\n" +
                   "• Read the medication guide\n" +
                   "• Be aware of potential side effects\n" +
                   "• Don't share medications with others\n" +
                   "• Store medications properly\n\n" +
                   "For specific medication questions, consult your pharmacist or doctor.";
        }
        
        if (lowerMessage.contains("emergency") || lowerMessage.contains("urgent")) {
            return "🚨 EMERGENCY SIGNS that require immediate medical attention:\n" +
                   "• Chest pain or pressure\n" +
                   "• Severe difficulty breathing\n" +
                   "• Unconsciousness\n" +
                   "• Severe bleeding\n" +
                   "• Sudden severe headache\n" +
                   "• Signs of stroke (FAST: Face, Arms, Speech, Time)\n\n" +
                   "If you're experiencing any of these, call emergency services immediately!";
        }
        
        if (lowerMessage.contains("first aid") || lowerMessage.contains("injury")) {
            return "Basic First Aid Tips:\n" +
                   "• For cuts: Clean with soap and water, apply pressure to stop bleeding\n" +
                   "• For burns: Cool with running water for 10-20 minutes\n" +
                   "• For sprains: RICE (Rest, Ice, Compression, Elevation)\n" +
                   "• For choking: Perform Heimlich maneuver\n" +
                   "• For unconscious person: Check breathing, call emergency services\n\n" +
                   "Consider taking a certified first aid course for comprehensive training.";
        }
        
        if (lowerMessage.contains("health") && lowerMessage.contains("tip")) {
            return "General Health & Wellness Tips:\n" +
                   "• Stay hydrated (8 glasses of water daily)\n" +
                   "• Exercise regularly (150 minutes/week)\n" +
                   "• Eat a balanced diet with fruits and vegetables\n" +
                   "• Get 7-9 hours of sleep\n" +
                   "• Manage stress through meditation or hobbies\n" +
                   "• Regular health check-ups\n" +
                   "• Avoid smoking and limit alcohol\n" +
                   "• Practice good hygiene\n\n" +
                   "Remember: Prevention is better than cure!";
        }
        
        // Default response
        return "Thank you for your question. I'm here to provide general health information and guidance. " +
               "For specific medical advice, diagnosis, or treatment, please consult with a qualified healthcare provider. " +
               "Is there anything specific about your health that you'd like to discuss?";
    }

    public VBox getAIMedicalBotView() {
        return aiMedicalBotView;
    }

    // Inner class for chat messages
    private static class ChatMessage {
        private String message;
        private boolean isUser;
        private long timestamp;

        public ChatMessage(String message, boolean isUser) {
            this.message = message;
            this.isUser = isUser;
            this.timestamp = System.currentTimeMillis();
        }

        public String getMessage() { return message; }
        public boolean isUser() { return isUser; }
        public long getTimestamp() { return timestamp; }
    }
}
