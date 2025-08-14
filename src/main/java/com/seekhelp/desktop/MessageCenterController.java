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
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageCenterController {

    private MainController mainController;
    private VBox messageCenterView;
    private ScrollPane scrollPane;
    private VBox messagesContainer;
    private TextArea messageInput;
    private ComboBox<String> recipientCombo;
    private ComboBox<String> priorityCombo;
    private VBox customerCareContainer;
    private WebView chatWebView;
    private WebEngine chatWebEngine;
    private VBox chatPlaceholder;

    // Message data
    private List<Message> messages;
    private List<CustomerCareTicket> supportTickets;

    public MessageCenterController(MainController mainController) {
        this.mainController = mainController;
        this.messages = new ArrayList<>();
        this.supportTickets = new ArrayList<>();
        initializeMessageCenterView();
        loadSampleData();
    }

    private void initializeMessageCenterView() {
        // Create scroll pane for the entire view
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("message-center-scroll-pane");

        messageCenterView = new VBox();
        messageCenterView.getStyleClass().add("message-center-container");
        messageCenterView.setPadding(new Insets(30));
        messageCenterView.setSpacing(25);

        // Header
        VBox header = createHeader();

        // Message Center section
        VBox messageSection = createMessageSection();

        // Customer Care section
        VBox customerCareSection = createCustomerCareSection();

        // Live Chat section
        VBox liveChatSection = createLiveChatSection();

        // Quick Actions
        VBox quickActionsSection = createQuickActionsSection();

        messageCenterView.getChildren().addAll(
            header, 
            messageSection, 
            customerCareSection, 
            liveChatSection, 
            quickActionsSection
        );

        scrollPane.setContent(messageCenterView);
    }

    private VBox createHeader() {
        VBox header = new VBox();
        header.setSpacing(15);

        Label title = new Label("💬 Message Center & Customer Care");
        title.setFont(Font.font("System", FontWeight.BOLD, 32));
        title.setTextFill(Color.valueOf("#1f2937"));

        Label subtitle = new Label("Stay connected with healthcare providers and get support when you need it");
        subtitle.setFont(Font.font("System", 18));
        subtitle.setTextFill(Color.valueOf("#6b7280"));

        // Features banner
        VBox featuresBox = new VBox(10);
        featuresBox.setPadding(new Insets(15));
        featuresBox.getStyleClass().add("features-box");
        featuresBox.setAlignment(Pos.CENTER);

        Label featuresTitle = new Label("🔗 Integrated Communication Hub");
        featuresTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        featuresTitle.setTextFill(Color.valueOf("#059669"));

        Label featuresText = new Label(
            "• Secure Messaging • Customer Support • Live Chat • Emergency Contacts • Appointment Reminders"
        );
        featuresText.setFont(Font.font("System", 14));
        featuresText.setTextFill(Color.valueOf("#374151"));
        featuresText.setWrapText(true);
        featuresText.setAlignment(Pos.CENTER);

        featuresBox.getChildren().addAll(featuresTitle, featuresText);

        header.getChildren().addAll(title, subtitle, featuresBox);
        return header;
    }

    private VBox createMessageSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("message-section");

        Label sectionTitle = new Label("📨 Secure Messaging");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // Message input area
        VBox inputArea = new VBox(10);
        inputArea.setPadding(new Insets(15));
        inputArea.getStyleClass().add("message-input-area");

        // Recipient selection
        HBox recipientRow = new HBox(15);
        recipientRow.setAlignment(Pos.CENTER_LEFT);

        Label recipientLabel = new Label("To:");
        recipientLabel.setFont(Font.font("System", 12));
        recipientCombo = new ComboBox<>();
        recipientCombo.getItems().addAll(
            "Select Recipient",
            "Dr. Sarah Johnson (Cardiologist)",
            "Dr. Michael Chen (General Medicine)",
            "Dr. Priya Patel (Pediatrics)",
            "Dr. Rajesh Kumar (Orthopedics)",
            "Nurse Maria Garcia",
            "Hospital Admin",
            "Emergency Contact"
        );
        recipientCombo.setValue("Select Recipient");
        recipientCombo.setPrefWidth(250);

        // Priority selection
        Label priorityLabel = new Label("Priority:");
        priorityLabel.setFont(Font.font("System", 12));
        priorityCombo = new ComboBox<>();
        priorityCombo.getItems().addAll("Normal", "Urgent", "Emergency");
        priorityCombo.setValue("Normal");
        priorityCombo.setPrefWidth(120);

        recipientRow.getChildren().addAll(recipientLabel, recipientCombo, priorityLabel, priorityCombo);

        // Message input
        Label messageLabel = new Label("Message:");
        messageLabel.setFont(Font.font("System", 12));
        messageInput = new TextArea();
        messageInput.setPromptText("Type your message here...");
        messageInput.setPrefRowCount(4);
        messageInput.setWrapText(true);
        messageInput.getStyleClass().add("message-text-input");

        // Send button
        Button sendButton = new Button("📤 Send Message");
        sendButton.getStyleClass().add("primary-button");
        sendButton.setOnAction(e -> sendMessage());

        inputArea.getChildren().addAll(recipientRow, messageLabel, messageInput, sendButton);

        // Messages container
        messagesContainer = new VBox();
        messagesContainer.setSpacing(10);

        Label messagesTitle = new Label("📥 Recent Messages");
        messagesTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        messagesTitle.setTextFill(Color.valueOf("#1f2937"));

        section.getChildren().addAll(sectionTitle, inputArea, messagesTitle, messagesContainer);
        return section;
    }

    private VBox createCustomerCareSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("customer-care-section");

        Label sectionTitle = new Label("🛟 Customer Care & Support");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // Support ticket creation
        VBox ticketArea = new VBox(10);
        ticketArea.setPadding(new Insets(15));
        ticketArea.getStyleClass().add("ticket-area");

        Label ticketTitle = new Label("Create Support Ticket");
        ticketTitle.setFont(Font.font("System", FontWeight.BOLD, 14));

        // Ticket form
        HBox ticketForm = new HBox(15);
        ticketForm.setAlignment(Pos.CENTER_LEFT);

        ComboBox<String> issueTypeCombo = new ComboBox<>();
        issueTypeCombo.getItems().addAll(
            "Technical Issue",
            "Account Problem",
            "Billing Question",
            "Feature Request",
            "Bug Report",
            "General Inquiry"
        );
        issueTypeCombo.setValue("Technical Issue");
        issueTypeCombo.setPrefWidth(150);

        TextField subjectField = new TextField();
        subjectField.setPromptText("Subject");
        subjectField.setPrefWidth(200);

        Button createTicketButton = new Button("🎫 Create Ticket");
        createTicketButton.getStyleClass().add("secondary-button");
        createTicketButton.setOnAction(e -> createSupportTicket(issueTypeCombo.getValue(), subjectField.getText()));

        ticketForm.getChildren().addAll(issueTypeCombo, subjectField, createTicketButton);

        // Support tickets container
        customerCareContainer = new VBox();
        customerCareContainer.setSpacing(10);

        Label ticketsTitle = new Label("📋 Your Support Tickets");
        ticketsTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        ticketsTitle.setTextFill(Color.valueOf("#1f2937"));

        ticketArea.getChildren().addAll(ticketTitle, ticketForm);

        section.getChildren().addAll(sectionTitle, ticketArea, ticketsTitle, customerCareContainer);
        return section;
    }

    private VBox createLiveChatSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("live-chat-section");

        Label sectionTitle = new Label("💬 Live Chat Support");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // Chat container
        VBox chatContainer = new VBox();
        chatContainer.setPrefHeight(300);
        chatContainer.getStyleClass().add("chat-container");
        chatContainer.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #e5e7eb; -fx-border-radius: 8;");

        // Initialize WebView for chat (deferred)
        chatWebView = null;
        chatWebEngine = null;
        
        // Create a placeholder for the chat interface
        VBox chatPlaceholder = new VBox();
        chatPlaceholder.setPrefHeight(250);
        chatPlaceholder.setAlignment(Pos.CENTER);
        chatPlaceholder.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #e5e7eb; -fx-border-radius: 8;");
        
        Label placeholderLabel = new Label("💬 Live Chat Interface\nClick 'Start Chat' to initialize");
        placeholderLabel.setFont(Font.font("System", 14));
        placeholderLabel.setTextFill(Color.valueOf("#6b7280"));
        placeholderLabel.setAlignment(Pos.CENTER);
        placeholderLabel.setWrapText(true);
        
        chatPlaceholder.getChildren().add(placeholderLabel);
        
        // Store the placeholder for later replacement
        this.chatPlaceholder = chatPlaceholder;

        // Chat controls
        HBox chatControls = new HBox(10);
        chatControls.setAlignment(Pos.CENTER_LEFT);

        TextField chatInput = new TextField();
        chatInput.setPromptText("Type your message...");
        chatInput.setPrefWidth(300);

        Button sendChatButton = new Button("💬 Send");
        sendChatButton.getStyleClass().add("primary-button");
        sendChatButton.setOnAction(e -> sendChatMessage(chatInput.getText(), chatInput));

        Button startChatButton = new Button("🟢 Start Chat");
        startChatButton.getStyleClass().add("success-button");
        startChatButton.setOnAction(e -> startLiveChat());

        chatControls.getChildren().addAll(chatInput, sendChatButton, startChatButton);

        chatContainer.getChildren().addAll(chatPlaceholder, chatControls);

        section.getChildren().addAll(sectionTitle, chatContainer);
        return section;
    }

    private VBox createQuickActionsSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("quick-actions-section");

        Label sectionTitle = new Label("⚡ Quick Actions");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // Quick action buttons
        HBox quickActions = new HBox(15);
        quickActions.setAlignment(Pos.CENTER);

        Button emergencyButton = new Button("🚨 Emergency Contact");
        emergencyButton.getStyleClass().add("emergency-button");
        emergencyButton.setOnAction(e -> contactEmergency());

        Button appointmentButton = new Button("📅 Schedule Appointment");
        appointmentButton.getStyleClass().add("primary-button");
        appointmentButton.setOnAction(e -> scheduleAppointment());

        Button feedbackButton = new Button("📝 Give Feedback");
        feedbackButton.getStyleClass().add("secondary-button");
        feedbackButton.setOnAction(e -> giveFeedback());

        Button helpButton = new Button("❓ Help & FAQ");
        helpButton.getStyleClass().add("secondary-button");
        helpButton.setOnAction(e -> showHelp());

        quickActions.getChildren().addAll(emergencyButton, appointmentButton, feedbackButton, helpButton);

        section.getChildren().addAll(sectionTitle, quickActions);
        return section;
    }

    private void sendMessage() {
        String recipient = recipientCombo.getValue();
        String priority = priorityCombo.getValue();
        String messageText = messageInput.getText().trim();

        if (recipient.equals("Select Recipient")) {
            mainController.showNotification("Error", "Please select a recipient.");
            return;
        }

        if (messageText.isEmpty()) {
            mainController.showNotification("Error", "Please enter a message.");
            return;
        }

        // Create new message
        Message message = new Message(
            "You",
            recipient,
            messageText,
            priority,
            LocalDateTime.now()
        );

        messages.add(0, message); // Add to beginning
        displayMessages();

        // Clear input
        messageInput.clear();
        recipientCombo.setValue("Select Recipient");
        priorityCombo.setValue("Normal");

        mainController.showNotification("Message Sent", "Message sent to " + recipient);
    }

    private void createSupportTicket(String issueType, String subject) {
        if (subject.trim().isEmpty()) {
            mainController.showNotification("Error", "Please enter a subject for the ticket.");
            return;
        }

        CustomerCareTicket ticket = new CustomerCareTicket(
            "TICKET-" + System.currentTimeMillis(),
            issueType,
            subject,
            "Open",
            LocalDateTime.now()
        );

        supportTickets.add(0, ticket);
        displaySupportTickets();

        mainController.showNotification("Ticket Created", "Support ticket created successfully. Ticket ID: " + ticket.getId());
    }

    private void sendChatMessage(String message, TextField inputField) {
        if (message.trim().isEmpty()) {
            return;
        }

        // Initialize WebView if not already done
        if (chatWebView == null) {
            initializeChatWebView();
            // Wait a bit for the WebView to initialize
            new Thread(() -> {
                try {
                    Thread.sleep(500);
                    Platform.runLater(() -> sendChatMessage(message, inputField));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            return;
        }

        // Add user message to chat
        String userMessageHtml = "<div class='message user-message'><strong>You:</strong> " + message + "</div>";
        if (chatWebEngine != null) {
            chatWebEngine.executeScript("document.getElementById('chatMessages').innerHTML += '" + userMessageHtml + "';");
        }

        // Simulate agent response
        String agentResponse = getAgentResponse(message);
        String agentMessageHtml = "<div class='message agent-message'><strong>Support Agent:</strong> " + agentResponse + "</div>";
        
        // Delay the response to simulate real chat
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                Platform.runLater(() -> {
                    if (chatWebEngine != null) {
                        chatWebEngine.executeScript("document.getElementById('chatMessages').innerHTML += '" + agentMessageHtml + "';");
                        chatWebEngine.executeScript("document.getElementById('chatMessages').scrollTop = document.getElementById('chatMessages').scrollHeight;");
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        inputField.clear();
    }

    private String getAgentResponse(String userMessage) {
        String lowerMessage = userMessage.toLowerCase();
        
        if (lowerMessage.contains("hello") || lowerMessage.contains("hi")) {
            return "Hello! How can I assist you today?";
        } else if (lowerMessage.contains("help")) {
            return "I'm here to help! What specific issue are you experiencing?";
        } else if (lowerMessage.contains("appointment")) {
            return "I can help you schedule an appointment. Would you like me to connect you with our scheduling system?";
        } else if (lowerMessage.contains("billing") || lowerMessage.contains("payment")) {
            return "For billing questions, I can connect you with our billing department. What's your specific concern?";
        } else if (lowerMessage.contains("technical") || lowerMessage.contains("bug")) {
            return "I understand you're having a technical issue. Let me create a support ticket for you.";
        } else {
            return "Thank you for your message. I'll do my best to help you. Could you provide more details?";
        }
    }

    private void startLiveChat() {
        mainController.showNotification("Live Chat", "Live chat session started. An agent will be with you shortly.");
        
        // Initialize WebView if not already done
        if (chatWebView == null) {
            initializeChatWebView();
        }
        
        // Update chat status
        if (chatWebEngine != null) {
            String statusUpdate = "document.querySelector('.chat-header').innerHTML = '<strong>🟢 Live Chat Support</strong> - Connected to agent';";
            chatWebEngine.executeScript(statusUpdate);
        }
    }
    
    private void initializeChatWebView() {
        // Create WebView on JavaFX thread
        Platform.runLater(() -> {
            chatWebView = new WebView();
            chatWebEngine = chatWebView.getEngine();
            chatWebView.setPrefHeight(250);
            chatWebView.getStyleClass().add("chat-web-view");

            // Load chat interface
            String chatHtml = """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Live Chat</title>
                    <style>
                        body { margin: 0; padding: 10px; font-family: Arial, sans-serif; }
                        .chat-header { background: #059669; color: white; padding: 10px; border-radius: 5px; margin-bottom: 10px; }
                        .chat-messages { height: 180px; overflow-y: auto; border: 1px solid #e5e7eb; padding: 10px; background: white; }
                        .message { margin: 5px 0; padding: 8px; border-radius: 5px; }
                        .user-message { background: #dbeafe; text-align: right; }
                        .agent-message { background: #f3f4f6; }
                        .status { color: #059669; font-size: 12px; }
                    </style>
                </head>
                <body>
                    <div class="chat-header">
                        <strong>🟢 Live Chat Support</strong> - Agent available
                    </div>
                    <div class="chat-messages" id="chatMessages">
                        <div class="message agent-message">
                            <strong>Support Agent:</strong> Hello! How can I help you today?
                        </div>
                    </div>
                    <div class="status">
                        💬 Type your message below to start chatting
                    </div>
                </body>
                </html>
                """;
            chatWebEngine.loadContent(chatHtml);
            
            // Replace placeholder with WebView
            if (chatPlaceholder != null && chatPlaceholder.getParent() != null) {
                VBox parent = (VBox) chatPlaceholder.getParent();
                int index = parent.getChildren().indexOf(chatPlaceholder);
                if (index >= 0) {
                    parent.getChildren().set(index, chatWebView);
                }
            }
        });
    }

    private void contactEmergency() {
        mainController.showNotification("Emergency", "Connecting to emergency services...");
        // In real implementation, this would initiate emergency contact
    }

    private void scheduleAppointment() {
        mainController.showNotification("Appointment", "Opening appointment scheduling system...");
        // In real implementation, this would open appointment booking
    }

    private void giveFeedback() {
        mainController.showNotification("Feedback", "Opening feedback form...");
        // In real implementation, this would open feedback form
    }

    private void showHelp() {
        mainController.showNotification("Help", "Opening help and FAQ section...");
        // In real implementation, this would open help documentation
    }

    private void displayMessages() {
        messagesContainer.getChildren().clear();

        if (messages.isEmpty()) {
            Label noMessagesLabel = new Label("No messages yet. Send your first message!");
            noMessagesLabel.setFont(Font.font("System", 14));
            noMessagesLabel.setTextFill(Color.valueOf("#6b7280"));
            noMessagesLabel.setAlignment(Pos.CENTER);
            noMessagesLabel.setPadding(new Insets(20));
            messagesContainer.getChildren().add(noMessagesLabel);
            return;
        }

        for (Message message : messages) {
            VBox messageCard = createMessageCard(message);
            messagesContainer.getChildren().add(messageCard);
        }
    }

    private void displaySupportTickets() {
        customerCareContainer.getChildren().clear();

        if (supportTickets.isEmpty()) {
            Label noTicketsLabel = new Label("No support tickets yet.");
            noTicketsLabel.setFont(Font.font("System", 14));
            noTicketsLabel.setTextFill(Color.valueOf("#6b7280"));
            noTicketsLabel.setAlignment(Pos.CENTER);
            noTicketsLabel.setPadding(new Insets(20));
            customerCareContainer.getChildren().add(noTicketsLabel);
            return;
        }

        for (CustomerCareTicket ticket : supportTickets) {
            VBox ticketCard = createTicketCard(ticket);
            customerCareContainer.getChildren().add(ticketCard);
        }
    }

    private VBox createMessageCard(Message message) {
        VBox card = new VBox();
        card.setSpacing(8);
        card.setPadding(new Insets(15));
        card.getStyleClass().add("message-card");

        // Message header
        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);

        Label fromLabel = new Label("From: " + message.getFrom());
        fromLabel.setFont(Font.font("System", FontWeight.BOLD, 12));

        Label toLabel = new Label("To: " + message.getTo());
        toLabel.setFont(Font.font("System", 12));

        Label priorityLabel = new Label(message.getPriority());
        priorityLabel.setFont(Font.font("System", 10));
        priorityLabel.setPadding(new Insets(2, 8, 2, 8));
        priorityLabel.getStyleClass().add("priority-badge");

        Label timeLabel = new Label(message.getTime().format(DateTimeFormatter.ofPattern("MMM dd, HH:mm")));
        timeLabel.setFont(Font.font("System", 10));
        timeLabel.setTextFill(Color.valueOf("#6b7280"));

        header.getChildren().addAll(fromLabel, toLabel, priorityLabel, timeLabel);

        // Message content
        Label contentLabel = new Label(message.getContent());
        contentLabel.setFont(Font.font("System", 12));
        contentLabel.setWrapText(true);

        card.getChildren().addAll(header, contentLabel);
        return card;
    }

    private VBox createTicketCard(CustomerCareTicket ticket) {
        VBox card = new VBox();
        card.setSpacing(8);
        card.setPadding(new Insets(15));
        card.getStyleClass().add("ticket-card");

        // Ticket header
        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);

        Label idLabel = new Label("ID: " + ticket.getId());
        idLabel.setFont(Font.font("System", FontWeight.BOLD, 12));

        Label typeLabel = new Label(ticket.getType());
        typeLabel.setFont(Font.font("System", 10));
        typeLabel.setPadding(new Insets(2, 8, 2, 8));
        typeLabel.getStyleClass().add("ticket-type-badge");

        Label statusLabel = new Label(ticket.getStatus());
        statusLabel.setFont(Font.font("System", 10));
        statusLabel.setPadding(new Insets(2, 8, 2, 8));
        statusLabel.getStyleClass().add("status-badge");

        Label timeLabel = new Label(ticket.getCreatedTime().format(DateTimeFormatter.ofPattern("MMM dd, HH:mm")));
        timeLabel.setFont(Font.font("System", 10));
        timeLabel.setTextFill(Color.valueOf("#6b7280"));

        header.getChildren().addAll(idLabel, typeLabel, statusLabel, timeLabel);

        // Ticket subject
        Label subjectLabel = new Label(ticket.getSubject());
        subjectLabel.setFont(Font.font("System", 12));
        subjectLabel.setWrapText(true);

        card.getChildren().addAll(header, subjectLabel);
        return card;
    }

    private void loadSampleData() {
        // Load sample messages
        messages.add(new Message("Dr. Sarah Johnson", "You", "Your test results are ready. Please schedule a follow-up appointment.", "Normal", LocalDateTime.now().minusHours(2)));
        messages.add(new Message("You", "Dr. Michael Chen", "I have a question about my medication dosage.", "Urgent", LocalDateTime.now().minusHours(4)));
        messages.add(new Message("Hospital Admin", "You", "Your appointment for tomorrow has been confirmed.", "Normal", LocalDateTime.now().minusDays(1)));

        // Load sample support tickets
        supportTickets.add(new CustomerCareTicket("TICKET-001", "Technical Issue", "App not loading properly", "Open", LocalDateTime.now().minusHours(1)));
        supportTickets.add(new CustomerCareTicket("TICKET-002", "Account Problem", "Can't reset password", "In Progress", LocalDateTime.now().minusDays(1)));

        displayMessages();
        displaySupportTickets();
    }

    public VBox getMessageCenterView() {
        return messageCenterView;
    }

    public ScrollPane getMessageCenterScrollView() {
        return scrollPane;
    }

    // Inner classes for data
    public static class Message {
        private String from, to, content, priority;
        private LocalDateTime time;

        public Message(String from, String to, String content, String priority, LocalDateTime time) {
            this.from = from;
            this.to = to;
            this.content = content;
            this.priority = priority;
            this.time = time;
        }

        public String getFrom() { return from; }
        public String getTo() { return to; }
        public String getContent() { return content; }
        public String getPriority() { return priority; }
        public LocalDateTime getTime() { return time; }
    }

    public static class CustomerCareTicket {
        private String id, type, subject, status;
        private LocalDateTime createdTime;

        public CustomerCareTicket(String id, String type, String subject, String status, LocalDateTime createdTime) {
            this.id = id;
            this.type = type;
            this.subject = subject;
            this.status = status;
            this.createdTime = createdTime;
        }

        public String getId() { return id; }
        public String getType() { return type; }
        public String getSubject() { return subject; }
        public String getStatus() { return status; }
        public LocalDateTime getCreatedTime() { return createdTime; }
    }
}
