package project2;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.lang.Math;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This controller class is for the server check-out screen. 
 * @author Joanne Liu
 */
public class SecondaryController {

    /**
     * This function switches the page to the server menu screen. 
     * @throws IOException when page fails to load
     */
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("orderScreen", menuItemQuantities, checkoutClickedButtons);
    }

    /**
     * This FXML VBox is the area where the checkout items will be displayed.
     */
    @FXML
    private VBox checkoutArea;

    /**
     * This next button is for the user to submit order.
     */
    @FXML
    private Button nextButton;

    /**
     * This label is to check whether the correct date and time are displayed.
     */
    @FXML 
    private Label testLabel;

    /**
     * Arraylist of arraylists that store quantities for each menu item
     */
    HashMap<String, ArrayList<String>> quantities = new HashMap<>();

    /**
     * ArrayList of ArrayLists of buttons containing quantities for each menu item
     */
    HashMap<String, ArrayList<Label>> quantitiesLabels = new HashMap<>();

    /**
     * ArrayList of String, storing the menu item names that are on the check-out screen
     */
    protected ArrayList<String> checkoutClickedButtons = new ArrayList<String>();
    
    /**
     * Hashmap of String to Menu_Item, stores the menu items retrieved from the database, with MenuItem mapped to item name
     */
    HashMap<String, Menu_Item> allMenuItems = new HashMap<>();

    /**
     * Hashmap of string to integer, storing the quantities of each menu item on the check-out screen
     */
    HashMap<String, Integer> menuItemQuantities = new HashMap<>();

    /**
     * Double to store running total of price  
     */
    Double totalPrice = 0.0;

    /**
     * FXML Hbox for the bottom bar of the check-out screen.
     */
    @FXML
    private HBox bottomBar;

    /**
     * FXML Label for the total price label at the bottom
     */
    @FXML
    private Label priceLabel;

    /**
     * Hashmap of string and VBox that stores each VBox of each menu item on the checkout screen
     */
    HashMap<String, VBox> allMenuItemBoxes = new HashMap<>();


    /**
     * This function adds one to the quantity of the menu item of which the add button is pressed. 
     * @param quantity - FXML Label that shows the quantity of the menu item
     * @param menuItemName - String
     * @param unitPrice - Double price of the menu item
     * @param price - FXML Label that shows the total price according to quantity
     */    
    @FXML
    private void addQuantity(Label quantity, String menuItemName, double unitPrice, Label price) {
        int quanNum = Integer.parseInt(quantity.getText());
        quanNum += 1;
        quantity.setText(Integer.toString(quanNum));
        quantity.getStyleClass().clear();
        quantity.getStyleClass().add("checkout-quantity-text");
        menuItemQuantities.put(menuItemName, quanNum);
        double newPrice = quanNum * unitPrice;
        String newPriceRounded = String.format("%.2f", newPrice);
        price.setText("$" + newPriceRounded);
        totalPrice += unitPrice;
        String priceRounded = String.format("%.2f", totalPrice);
        priceLabel.setText(priceRounded);
    }

    /**
     * This function subtracts one from the quantity of the menu item of which the subtract button is pressed.
     * @param quantity - FXML Label that shows the quantity of the menu item
     * @param menuItemBox - FXML VBox of where the menu item is displayed on the check-out screen
     * @param menuItemName - String
     * @param unitPrice - Double price of the menu item
     * @param price - FXML Label that shows the total price according to quantity
     */
    @FXML
    private void minusQuantity(Label quantity, VBox menuItemBox, String menuItemName, double unitPrice, Label price) {
        int quanNum = Integer.parseInt(quantity.getText());
        quanNum -= 1;
        totalPrice -= unitPrice;
        String priceRounded = String.format("%.2f", totalPrice);
        priceLabel.setText(priceRounded);
        if(quanNum == 0) { 
            menuItemBox.getChildren().clear();
            checkoutClickedButtons.remove(menuItemName);
            menuItemQuantities.remove(menuItemName);
            // resets the spacing for the checkout area, shifting everything up
            checkoutArea.getChildren().remove(allMenuItemBoxes.get(menuItemName));
            checkoutArea.setSpacing(5);
        } else {
            quantity.getStyleClass().clear();
            quantity.getStyleClass().add("checkout-quantity-text");
            quantity.setText(Integer.toString(quanNum));
            menuItemQuantities.put(menuItemName, menuItemQuantities.get(menuItemName) - 1);
            double newPrice = Double.parseDouble(quantity.getText()) * unitPrice;
            String newPriceRounded = String.format("%.2f", newPrice);
            price.setText("$" + newPriceRounded);
        }
    }


    /**
     * This function is for when the next button is pressed, which submits the order.
     * It captures the exact time and date of when the order is submitted and passes
     * it back, along with menu items, quantities, and ingreidnets used,
     * to the backend function to update the PostgreSQL database.
     * @throws IOException when EmployeeList class fails to create
     */
    @FXML
    private void endOrder() throws IOException {
        Random random = new Random();

        // DELETE LATER: Generate a random order ID from 80,000 to 100,000
        int orderNum = random.nextInt(100000 - 80000 + 1) + 80000;

        LocalDateTime timeClicked = LocalDateTime.now();

        // store date in string
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = timeClicked.format(dateFormatter);

        // store time into string
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String time = timeClicked.format(timeFormatter);

        String orderString = Integer.toString(orderNum) + " " + date + " " + time;
        testLabel.setText(orderString);
        Order orderManager = new Order(orderNum, time, date);

        Menu_Item_Populate menuItemManager = new Menu_Item_Populate();
        menuItemManager.populateMenuItems();
        menuItemManager.populateAllIngredients();
        allMenuItems = menuItemManager.getMenuItems();
        
        // menu item name to ingredient list (ingredient name to quantity)
        HashMap<String, HashMap<String, Integer>> allMenuItemIngredients = new HashMap<>();

        for(Map.Entry<String, Integer> entry : menuItemQuantities.entrySet()) {
            // ingredient name to quantity
            HashMap<String, Integer> ingredientQuantities = new HashMap<>();
            orderManager.addMenuItem(entry.getKey(), entry.getValue());
            HashMap<IngredientDetails, Integer> menuIngredients = menuItemManager.getMenuIngredientsWithQuantity(entry.getKey());

            for(Map.Entry<IngredientDetails, Integer> eachIngredient : menuIngredients.entrySet()) {
                ingredientQuantities.put(eachIngredient.getKey().getName(), eachIngredient.getValue());
            }
            allMenuItemIngredients.put(entry.getKey(), ingredientQuantities);
        }
        // Round total price to 2 decimal places
        totalPrice *= 100;
        long totalPriceLong = Math.round(totalPrice);
        totalPrice = totalPriceLong / 100.0;

        // Modify order instance to update database
        orderManager.setTotalPrice(totalPrice);
        orderManager.setOrderNumber();
        orderManager.submitOrder(allMenuItemIngredients);
        
        // Clear everything in checkout box and call function to display receipt
        menuItemQuantities.clear();
        checkoutClickedButtons.clear();
        orderComplete(orderManager.orderNumber);
    }


    /**
     * This function shows the receipt when order is submitted and builds a button
     * to go back to order screen. 
     * @param orderNum - orderID (int)
     */
    @FXML
    private void orderComplete(int orderNum) {
        checkoutArea.getChildren().clear();
        checkoutArea.setStyle("-fx-alignment: center");
        bottomBar.getChildren().clear();
        VBox receipt = new VBox();
        receipt.getStyleClass().add("checkout-receipt");
        
        Label orderComplete = new Label("Order Complete");
        orderComplete.getStyleClass().add("checkout-receipt-message");
        receipt.getChildren().add(orderComplete);

        String orderID = "Order ID: " + Integer.toString(orderNum);
        Label id = new Label(orderID);
        id.getStyleClass().add("checkout-receipt-id");
        receipt.getChildren().add(id);

        String output = "Total: " + Double.toString(totalPrice);
        Label price = new Label(output);
        price.getStyleClass().add("checkout-receipt-total");
        receipt.getChildren().add(price);

        Button backToMenu = new Button("Back to Menu");
        backToMenu.getStyleClass().add("checkout-receipt-back");
        receipt.getChildren().add(backToMenu);
        backToMenu.setOnAction(event -> {
            try {
                switchToPrimary();
            } catch (IOException e) {
                // Handle the IOException
                e.printStackTrace(); // Or handle it in some other appropriate way
            }
        });

        checkoutArea.getChildren().add(receipt);
    }
    
    /**
     * Default constructor for the class
     */
    public SecondaryController() {
        // default constructor
    }

    /**
     * This function builds screen with all the clicked buttons from the order screen.
     * @param clickedNames - names of all the menu items clicked (ArrayList of Strings)
     * @param itemQuantities - quantities of the items 
     */
    @FXML
    public void buildScreen(ArrayList<String> clickedNames, HashMap<String, Integer> itemQuantities) {
        totalPrice = 0.0;
        Menu_Item_Populate menuItemManager = new Menu_Item_Populate();
        menuItemManager.populateMenuItems();
        menuItemManager.populateAllIngredients();
        allMenuItems = menuItemManager.getMenuItems();
        menuItemQuantities = itemQuantities;

        int count = 1;
        Set<String> buttonNames = new HashSet<>();
        for(String itemName : clickedNames) {
            addItemCheckOut(itemName, count);
            buttonNames.add(itemName);
            count++;
        }
        checkoutClickedButtons = clickedNames;
    }


    /**
     * The function builds the row for the menu item in checkout box.
     * @param menuItem - menu item name (String)
     * @param count - quantity (Int)
     */
    @FXML
    protected void addItemCheckOut(String menuItem, Integer count) {
        // Add new VBox for menu item
        VBox menuItemBox = new VBox();
        menuItemBox.getStyleClass().add("checkout-menu-item");

        // Add new HBox for menu item row
        HBox itemRow = new HBox();
        itemRow.getStyleClass().add("checkout-item-row");

        // variable to store quantity; default is 1
        int quantityCount = 1;

        // load saved quantity fron before
        if(menuItemQuantities.containsKey(menuItem)) {
            quantityCount = menuItemQuantities.get(menuItem);
        } else {
            menuItemQuantities.put(menuItem, quantityCount);
        }

        // Add new label for quantity
        Label quantity = new Label(Integer.toString(quantityCount));
        quantity.getStyleClass().add("checkout-quantity-text");
        // Add label to row hbox
        itemRow.getChildren().add(quantity);
        // Add the quantity label in new arraylist
        ArrayList<Label> allMenuQuantities = new ArrayList<Label>();
        allMenuQuantities.add(quantity);

        // Plus and minus button, add functions for if button was clicked
        Button plus = new Button("+");
        Button minus = new Button("-");
     
        plus.getStyleClass().add("plus-minus-button");
        // setting minus action after menu item box is complete 
        minus.getStyleClass().add("plus-minus-button");
        itemRow.getChildren().add(plus);
        itemRow.getChildren().add(minus);
        
        // Add label for menu item name
        Label itemName = new Label(menuItem);
        itemName.getStyleClass().add("checkout-item-text");
        itemRow.getChildren().add(itemName);

        // Add label for price 
        Double unitPrice = allMenuItems.get(menuItem).getPrice();
        Double priceWithQuantity = unitPrice * Double.parseDouble(quantity.getText());
        Label price = new Label("$" + Double.toString(priceWithQuantity));
        price.getStyleClass().add("checkout-item-price-text");
        itemRow.getChildren().add(price);
        totalPrice += priceWithQuantity;
        String priceRounded = String.format("%.2f", totalPrice);
        priceLabel.setText(priceRounded);

        // Add row to menu item box
        menuItemBox.getChildren().add(itemRow);

        // Passing menu item box for menu item minus button
        minus.setOnAction(event -> minusQuantity(quantity, menuItemBox, menuItem, unitPrice, price));
        plus.setOnAction(event -> addQuantity(quantity, menuItem, unitPrice, price));

        // Map menu item name to the menItemBox vbox 
        allMenuItemBoxes.put(menuItem, menuItemBox);

        // Add menu item to check out area
        checkoutArea.getChildren().add(menuItemBox);
    }
}