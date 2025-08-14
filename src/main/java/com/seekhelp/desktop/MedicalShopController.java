package com.seekhelp.desktop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.*;

public class MedicalShopController {

    private MainController mainController;
    private VBox medicalShopView;
    private ScrollPane scrollPane;
    private TableView<Medicine> cartTable;
    private VBox productsContainer;
    private Label totalLabel;
    private double totalAmount = 0.0;

    public MedicalShopController(MainController mainController) {
        this.mainController = mainController;
        initializeMedicalShopView();
    }

    private void initializeMedicalShopView() {
        // Create scroll pane for the entire view
        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("medical-shop-scroll-pane");

        medicalShopView = new VBox();
        medicalShopView.getStyleClass().add("medical-shop-container");
        medicalShopView.setPadding(new Insets(30));
        medicalShopView.setSpacing(25);

        // Header
        VBox header = createHeader();

        // Categories
        VBox categoriesSection = createCategoriesSection();

        // Products
        VBox productsSection = createProductsSection();

        // Shopping cart
        VBox cartSection = createCartSection();

        // Prescription upload
        VBox prescriptionSection = createPrescriptionSection();

        // Checkout
        VBox checkoutSection = createCheckoutSection();

        medicalShopView.getChildren().addAll(
            header, 
            categoriesSection, 
            productsSection, 
            cartSection, 
            prescriptionSection, 
            checkoutSection
        );

        scrollPane.setContent(medicalShopView);
    }

    private VBox createHeader() {
        VBox header = new VBox();
        header.setSpacing(15);

        Label title = new Label("💊 Medical Shop");
        title.setFont(Font.font("System", FontWeight.BOLD, 32));
        title.setTextFill(Color.valueOf("#1f2937"));

        Label subtitle = new Label("Buy medicines and health products with prescription verification");
        subtitle.setFont(Font.font("System", 18));
        subtitle.setTextFill(Color.valueOf("#6b7280"));

        // Features banner
        VBox featuresBox = new VBox(10);
        featuresBox.setPadding(new Insets(15));
        featuresBox.getStyleClass().add("features-box");
        featuresBox.setAlignment(Pos.CENTER);

        Label featuresTitle = new Label("🛒 Your Health Partner");
        featuresTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        featuresTitle.setTextFill(Color.valueOf("#059669"));

        Label featuresText = new Label(
            "• Genuine medicines • Fast delivery • Prescription verification • Best prices • 24/7 support"
        );
        featuresText.setFont(Font.font("System", 14));
        featuresText.setTextFill(Color.valueOf("#374151"));
        featuresText.setWrapText(true);
        featuresText.setAlignment(Pos.CENTER);

        featuresBox.getChildren().addAll(featuresTitle, featuresText);

        header.getChildren().addAll(title, subtitle, featuresBox);
        return header;
    }

    private VBox createCategoriesSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("categories-section");

        Label sectionTitle = new Label("📂 Product Categories");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        HBox categories = new HBox(15);
        categories.setAlignment(Pos.CENTER_LEFT);

        String[][] categoryData = {
            {"💊 Medicines", "Prescription & OTC"},
            {"🩺 First Aid", "Emergency supplies"},
            {"🧴 Personal Care", "Hygiene products"},
            {"💪 Health Supplements", "Vitamins & minerals"},
            {"👶 Baby Care", "Infant products"},
            {"👴 Senior Care", "Elderly products"}
        };

        for (String[] category : categoryData) {
            VBox categoryCard = new VBox(5);
            categoryCard.setPadding(new Insets(15));
            categoryCard.getStyleClass().add("category-card");
            categoryCard.setAlignment(Pos.CENTER);

            Label categoryIcon = new Label(category[0]);
            categoryIcon.setFont(Font.font("System", 16));

            Label categoryName = new Label(category[1]);
            categoryName.setFont(Font.font("System", 12));
            categoryName.setTextFill(Color.valueOf("#6b7280"));
            categoryName.setAlignment(Pos.CENTER);

            categoryCard.getChildren().addAll(categoryIcon, categoryName);
            categories.getChildren().add(categoryCard);
        }

        section.getChildren().addAll(sectionTitle, categories);
        return section;
    }

    private VBox createProductsSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("products-section");

        Label sectionTitle = new Label("🛍️ Available Products");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // Products grid
        productsContainer = new VBox();
        productsContainer.setSpacing(15);

        // Load products
        loadProducts();

        section.getChildren().addAll(sectionTitle, productsContainer);
        return section;
    }

    private VBox createCartSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("cart-section");

        Label sectionTitle = new Label("🛒 Shopping Cart");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // Cart table
        cartTable = new TableView<>();
        cartTable.setPrefHeight(200);

        TableColumn<Medicine, String> nameCol = new TableColumn<>("Medicine");
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));

        TableColumn<Medicine, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty("₹" + data.getValue().getPrice()));

        TableColumn<Medicine, String> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getQuantity())));

        TableColumn<Medicine, String> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty("₹" + (data.getValue().getPrice() * data.getValue().getQuantity())));

        cartTable.getColumns().addAll(nameCol, priceCol, quantityCol, totalCol);

        // Total amount
        totalLabel = new Label("Total: ₹0.00");
        totalLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        totalLabel.setTextFill(Color.valueOf("#1f2937"));

        section.getChildren().addAll(sectionTitle, cartTable, totalLabel);
        return section;
    }

    private VBox createPrescriptionSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("prescription-section");

        Label sectionTitle = new Label("📋 Prescription Upload");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        Label sectionDesc = new Label("Upload prescription for medicines that require doctor's approval:");
        sectionDesc.setFont(Font.font("System", 14));
        sectionDesc.setTextFill(Color.valueOf("#6b7280"));

        VBox uploadBox = new VBox(15);
        uploadBox.setPadding(new Insets(20));
        uploadBox.getStyleClass().add("upload-box");
        uploadBox.setAlignment(Pos.CENTER);

        Label uploadIcon = new Label("📄");
        uploadIcon.setStyle("-fx-font-size: 48px;");

        Label uploadText = new Label("Click to upload prescription");
        uploadText.setFont(Font.font("System", 16));
        uploadText.setTextFill(Color.valueOf("#6b7280"));

        Button uploadButton = new Button("📁 Choose File");
        uploadButton.getStyleClass().add("upload-button");
        uploadButton.setOnAction(e -> uploadPrescription());

        Label noteLabel = new Label("Note: Prescription will be verified by our medical team");
        noteLabel.setFont(Font.font("System", 12));
        noteLabel.setTextFill(Color.valueOf("#dc2626"));

        uploadBox.getChildren().addAll(uploadIcon, uploadText, uploadButton, noteLabel);

        section.getChildren().addAll(sectionTitle, sectionDesc, uploadBox);
        return section;
    }

    private VBox createCheckoutSection() {
        VBox section = new VBox();
        section.setSpacing(15);
        section.setPadding(new Insets(20));
        section.getStyleClass().add("checkout-section");

        Label sectionTitle = new Label("💳 Checkout");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        sectionTitle.setTextFill(Color.valueOf("#1f2937"));

        // Delivery options
        VBox deliveryOptions = new VBox(10);
        deliveryOptions.setPadding(new Insets(15));
        deliveryOptions.getStyleClass().add("delivery-options");

        Label deliveryTitle = new Label("🚚 Delivery Options");
        deliveryTitle.setFont(Font.font("System", FontWeight.BOLD, 14));

        RadioButton standardDelivery = new RadioButton("Standard Delivery (2-3 days) - ₹50");
        RadioButton expressDelivery = new RadioButton("Express Delivery (Same day) - ₹150");
        RadioButton pickup = new RadioButton("Store Pickup - Free");

        ToggleGroup deliveryGroup = new ToggleGroup();
        standardDelivery.setToggleGroup(deliveryGroup);
        expressDelivery.setToggleGroup(deliveryGroup);
        pickup.setToggleGroup(deliveryGroup);
        standardDelivery.setSelected(true);

        deliveryOptions.getChildren().addAll(deliveryTitle, standardDelivery, expressDelivery, pickup);

        // Payment options
        VBox paymentOptions = new VBox(10);
        paymentOptions.setPadding(new Insets(15));
        paymentOptions.getStyleClass().add("payment-options");

        Label paymentTitle = new Label("💳 Payment Options");
        paymentTitle.setFont(Font.font("System", FontWeight.BOLD, 14));

        RadioButton cod = new RadioButton("Cash on Delivery");
        RadioButton online = new RadioButton("Online Payment (Cards/UPI)");

        ToggleGroup paymentGroup = new ToggleGroup();
        cod.setToggleGroup(paymentGroup);
        online.setToggleGroup(paymentGroup);
        cod.setSelected(true);

        paymentOptions.getChildren().addAll(paymentTitle, cod, online);

        // Checkout button
        Button checkoutButton = new Button("🛒 Proceed to Checkout");
        checkoutButton.getStyleClass().add("checkout-button");
        checkoutButton.setOnAction(e -> proceedToCheckout());

        section.getChildren().addAll(sectionTitle, deliveryOptions, paymentOptions, checkoutButton);
        return section;
    }

    private void loadProducts() {
        List<Medicine> products = Arrays.asList(
            new Medicine("Paracetamol 500mg", "Fever & pain relief", 15.0, "OTC", 100),
            new Medicine("Ibuprofen 400mg", "Inflammation & pain", 25.0, "OTC", 50),
            new Medicine("Cetirizine 10mg", "Allergy relief", 30.0, "OTC", 75),
            new Medicine("Omeprazole 20mg", "Acid reflux", 45.0, "Prescription", 25),
            new Medicine("Metformin 500mg", "Diabetes", 35.0, "Prescription", 30),
            new Medicine("Amoxicillin 500mg", "Antibiotic", 80.0, "Prescription", 20),
            new Medicine("Vitamin D3", "Bone health", 120.0, "OTC", 60),
            new Medicine("Iron Supplements", "Anemia", 95.0, "OTC", 40),
            new Medicine("First Aid Kit", "Emergency supplies", 250.0, "OTC", 15),
            new Medicine("Digital Thermometer", "Temperature monitoring", 180.0, "OTC", 25),
            new Medicine("Blood Pressure Monitor", "BP monitoring", 1200.0, "OTC", 10),
            new Medicine("Glucose Monitor", "Diabetes monitoring", 800.0, "OTC", 8)
        );

        GridPane productsGrid = new GridPane();
        productsGrid.setHgap(20);
        productsGrid.setVgap(20);

        int row = 0, col = 0;
        for (Medicine product : products) {
            VBox productCard = createProductCard(product);
            productsGrid.add(productCard, col, row);
            
            col++;
            if (col > 2) {
                col = 0;
                row++;
            }
        }

        productsContainer.getChildren().add(productsGrid);
    }

    private VBox createProductCard(Medicine product) {
        VBox card = new VBox();
        card.setSpacing(10);
        card.setPadding(new Insets(15));
        card.getStyleClass().add("product-card");

        Label nameLabel = new Label(product.getName());
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        nameLabel.setWrapText(true);

        Label descLabel = new Label(product.getDescription());
        descLabel.setFont(Font.font("System", 12));
        descLabel.setTextFill(Color.valueOf("#6b7280"));
        descLabel.setWrapText(true);

        Label priceLabel = new Label("₹" + product.getPrice());
        priceLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        priceLabel.setTextFill(Color.valueOf("#059669"));

        Label stockLabel = new Label("Stock: " + product.getStock());
        stockLabel.setFont(Font.font("System", 10));
        stockLabel.setTextFill(Color.valueOf("#6b7280"));

        Label typeLabel = new Label(product.getType());
        typeLabel.setFont(Font.font("System", 10));
        typeLabel.setTextFill(product.getType().equals("Prescription") ? Color.valueOf("#dc2626") : Color.valueOf("#059669"));

        Button addButton = new Button("Add to Cart");
        addButton.getStyleClass().add("add-to-cart-button");
        addButton.setOnAction(e -> addToCart(product));

        card.getChildren().addAll(nameLabel, descLabel, priceLabel, stockLabel, typeLabel, addButton);
        return card;
    }

    private void addToCart(Medicine product) {
        // Check if already in cart
        for (Medicine item : cartTable.getItems()) {
            if (item.getName().equals(product.getName())) {
                item.setQuantity(item.getQuantity() + 1);
                updateTotal();
                cartTable.refresh();
                return;
            }
        }

        // Add new item to cart
        Medicine cartItem = new Medicine(product.getName(), product.getDescription(), product.getPrice(), product.getType(), product.getStock());
        cartItem.setQuantity(1);
        cartTable.getItems().add(cartItem);
        updateTotal();
    }

    private void updateTotal() {
        totalAmount = 0.0;
        for (Medicine item : cartTable.getItems()) {
            totalAmount += item.getPrice() * item.getQuantity();
        }
        totalLabel.setText("Total: ₹" + String.format("%.2f", totalAmount));
    }

    private void uploadPrescription() {
        mainController.showNotification("Prescription Upload", "Prescription upload feature will be implemented with image processing.");
    }

    private void proceedToCheckout() {
        if (cartTable.getItems().isEmpty()) {
            mainController.showNotification("Empty Cart", "Please add items to your cart before checkout.");
            return;
        }
        mainController.showNotification("Checkout", "Proceeding to checkout... Order will be processed soon.");
    }

    public VBox getMedicalShopView() {
        return medicalShopView;
    }

    public ScrollPane getMedicalShopScrollView() {
        return scrollPane;
    }

    // Inner class for medicine data
    public static class Medicine {
        private String name, description, type;
        private double price;
        private int stock, quantity;

        public Medicine(String name, String description, double price, String type, int stock) {
            this.name = name;
            this.description = description;
            this.price = price;
            this.type = type;
            this.stock = stock;
            this.quantity = 1;
        }

        public String getName() { return name; }
        public String getDescription() { return description; }
        public double getPrice() { return price; }
        public String getType() { return type; }
        public int getStock() { return stock; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }
}
