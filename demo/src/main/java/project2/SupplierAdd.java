package project2;

import javafx.fxml.FXML;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.event.ActionEvent;
import java.util.HashMap;
import java.util.ArrayList;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import java.util.Vector;
import javafx.scene.control.ComboBox;
import java.util.Set;
import java.util.Date;
import javafx.scene.Node;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javafx.scene.control.TextFormatter;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import java.text.DecimalFormat;
import java.text.ParsePosition;

import javafx.util.converter.NumberStringConverter;


import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.converter.IntegerStringConverter;

/**
 * This is a class that allows the manager to add a supplier order
 * @author Keeley Mahoney
 */
public class SupplierAdd {

    //an instance of inventorymanager that allows for each person to add/delete ingredients from supplier order
    InventoryManager inventory = new InventoryManager();

   HashMap<String, IngredientDetails> ingredients = new HashMap<>();

    //WILL NEED BUT COMMENT OUT FOR IT TO WORK NOW
    //public HashMap<String, SupplierOrderDetails> ingredientDetails = new HashMap<>();

    //an instance of SupplierOrder which allows for individuals to submit a new supplier order
    SupplierOrder supply_order = new SupplierOrder();

    //button that allows for individuals to add an ingredient to their supplier order
    @FXML
    Button addIngredient;

    @FXML 
    Label warning;

    //button that allows for individuals to delete an ingredient from their supplier order
    @FXML
    Button deleteIngredient;

    //button to change color of supplier orders button


    //combo box that lists ingredients that are currently not in the supplier order
    @FXML
    ComboBox<String> addIngredientsComboBox;

    //combo box that lists ingredients that are currently in supplier order (can be deleted)
    @FXML
    ComboBox<String> deleteIngredientsComboBox;

    //Change to edit button
    @FXML
    Button editScreen;


    //Change to Inventory button
    @FXML
    Button inventoryButton;

    //Change to Trends button
    @FXML
    Button trendsButton;

    //Change to Supplier Orders Button
    @FXML
    Button supplierOrdersButton;

    //Back Button
    @FXML
    Button back;

    //Scroll pane for the ingredients
    @FXML
    ScrollPane ingredients_space;

    //Grid for ingredients
    @FXML
    GridPane grid;

    //Text Field for order number
    @FXML
    TextField orderNumber;

    //Text Field for order date
    @FXML
    TextField orderDate;

    //Text field for order time
    @FXML
    TextField orderTime;

    //Text field for Provider
    @FXML
    TextField provider;

    Vector<String> ingredientsInOrder = new Vector<>();

    int row = 0;
    

    /**
      * Default constructor the SupplierAdd
      */
      public SupplierAdd()
      {
 
      }

      
    //switch to the inventory screen
    @FXML
    /**
     * This function switches to the invetnroy screen
     * @throws IOException if the inventory screen cannot be loaded
     * 
     */

    private void switchToIntentory() throws IOException {
        try
        {
            App.setRoot("inventory");
        }
        catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        
    }

    //switch to the edit screen
    @FXML
    /**
     * This is a function that switches to the manager menu screen
     * @throws IOException if the manager menu screen cannot be loaded
     */
    private void switchToEdit() throws IOException {
        try
        {
            App.setRoot("managerMenu");
        }
        catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        
    }

    //switch to the Supplier Order screen
    @FXML
    /**
     * This is a function that switches to the supplier order screen
     * @throws IOException if the supplier order screen cannot be loaded
     */
    private void switchToSupplierOrder() throws IOException {
        try
        {
            App.setRoot("SupplierOrdersScren");
        }
        catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        
    }

    //switch to the trends page
    @FXML
    /**
     * This is a function that switches to the trends page
     * @throws IOException if the trends page cannot be loaded
     */
    private void switchToTrends() throws IOException {
        App.setRoot("TrendsInitial");
        
    }

    /**
     * This function is first called when the Supplier Add page is called. It sets the style of the buttons, initializes all of the needed variables, and ensures that only positive integers can be entered in for the order numbers
     */
    public void initialize()
    {
        warning.setVisible(false);
        supplierOrdersButton.setStyle("-fx-background-color: #500000;");
        supplierOrdersButton.setTextFill(javafx.scene.paint.Color.WHITE);
        //populate all ingredients
        inventory.populateAllIngredients();

        //get vector of all current ingredients
        ingredients = inventory.getIngredientInventory();

        //set combo boxes
        comboBoxAdd();

        comboBoxDelete();

        //ensure only positive integers can be entered in
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            // Use a regular expression to allow only positive integers
            if (Pattern.matches("\\d*", newText)) {
                
                return change;
            } else {
                
                return null;
            }
        };
        
        TextFormatter<Integer> textFormatter = new TextFormatter<>(new IntegerStringConverter(), 0, filter);

        orderNumber.setTextFormatter(textFormatter);

        



    }
    //populates all of the possible values for ingredients that can be added to the supplier order
    /**
     * This populates the combo box in the screen that allows individuals to add ingredients to an order that don't already exist
     */
    public void comboBoxAdd()
    {
        addIngredientsComboBox.getItems().clear();
        if(ingredientsInOrder.isEmpty())
        {
            System.out.println("this should only print once");
            for(String ingredientName: ingredients.keySet())
            {
                addIngredientsComboBox.getItems().add(ingredientName);
            }
            return;

        }

        //loop through all of the ingredients
        for(String ingredientName: ingredients.keySet())
        {
            //if it's currently avaialble
            if(ingredients.get(ingredientName).getQuantity() >= 0)
            {
                System.out.println("is the ingredient in the order for ingredient " + ingredientName + " " + (ingredientsInOrder.contains(ingredientName)));
                //if it is not currently in the supplier order, add it to the combo box
                if(!(ingredientsInOrder.contains(ingredientName)))
                {
                    addIngredientsComboBox.getItems().add(ingredientName);
                }
            }
        }
        



    }

    //populates all of the possible values for ingredients that can be deleted to the supplier order
    /**
     * This function populates a combo box that contains all of the current ingredients in the supplier order, that can be deleted
     */
    public void comboBoxDelete()
    {
        deleteIngredientsComboBox.getItems().clear();
        if(ingredientsInOrder.isEmpty())
        {
            return;

        }

        //loop through all of the ingredients
        for(String ingredientName: ingredients.keySet())
        {
            //if it is a current valid ingredient
            if(ingredients.get(ingredientName).getQuantity() >= 0)
            {
                //if it exists in the supplier order, add it to the delete box
                if((ingredientsInOrder.contains(ingredientName)))
                {
                    deleteIngredientsComboBox.getItems().add(ingredientName);
                }
            }
        }
         

    }

    //add an ingredient to the supplier order
    /**
     * This function adds an ingredient to the new supplier order, and allows the manager to enter in the quantity received, the quantity ordered, and the wholesale unit price
     */
    public void addIngredient()
    {
        //initialize row of grid pane
        //get the name of the ingredient to add
        Object ingredientAdd = addIngredientsComboBox.getValue();
        
        //if it's not empty
        if(ingredientAdd != null)
        {

            String ingredientNameAdd = (String) addIngredientsComboBox.getValue();
            //create new anchor pane to add to grid
            AnchorPane ingredientPane = new AnchorPane();

            ingredientPane.setPrefSize(989, 50);

            //create new label and set its text
            Label ingredient_name = new Label();
            ingredient_name.setText(ingredientNameAdd);
            ingredient_name.setPrefSize(128, 50);
            
            //create new label for quantity orderd and set its location
            Label quantityOrdered = new Label();
            quantityOrdered.setText("Quantity Ordered: ");
            quantityOrdered.setPrefSize(128, 50);

            //create new text field for amount ordered and set its location
            TextField amountOrdered = new TextField();
            amountOrdered.setPrefSize(128, 50);

            //create new label for quantity received and set its location
            Label quantityReceived = new Label();
            quantityReceived.setText("Quantity Received: ");
            quantityReceived.setPrefSize(128, 50);

            //create new text field for amount Received and set its location
            TextField amountReceived = new TextField();
            amountReceived.setPrefSize(128, 50);

            //create new label for unit whole price
            Label wholesalePrice = new Label();
            wholesalePrice.setText("Wholesale Unit Price: ");
            wholesalePrice.setPrefSize(128, 50);

            //create new text field for inputting unit whole price
            TextField wholesalePriceValue = new TextField();
            wholesalePriceValue.setPrefSize(128, 50);


            //add all items to the anchor pane
            ingredientPane.getChildren().addAll(ingredient_name, quantityOrdered, amountOrdered, quantityReceived, amountReceived, wholesalePrice, wholesalePriceValue);

            //set the position of each item within the anchor pane
            ingredientPane.setLeftAnchor(ingredient_name, 0.0);

            ingredientPane.setLeftAnchor(quantityOrdered, 143.5);

            ingredientPane.setLeftAnchor(amountOrdered, 287.0);

            ingredientPane.setLeftAnchor(quantityReceived, 430.5);

            ingredientPane.setLeftAnchor(amountReceived, 574.0);

            ingredientPane.setLeftAnchor(wholesalePrice, 717.5);

            ingredientPane.setLeftAnchor(wholesalePriceValue, 861.0);

            grid.add(ingredientPane, 0, row);
            row++;
            ingredientsInOrder.add(ingredientNameAdd);
            //System.out.println("this is the size of the vector: " + ingredientsInOrder.size());
            //System.out.println("This is the value of the vector: " + ingredientsInOrder.get(0) + ",");
        }
        ingredients_space.setContent(grid);
        //reset combo boxes
        comboBoxAdd();
        comboBoxDelete();

    }

    //delete an ingredient from the supplier order
    /**
     * This function allows an ingredient to be deleted from a new supplier order, assuming it is already added to it
     */
    public void deleteIngredient()
    {
        //get location of the deleted ingredient in grid, use i to keep track of current loc
        int i = 0;
        int loc = -1;
        //get the name of the ingredient to delete
        
        Object ingredientDelete = deleteIngredientsComboBox.getValue();
        
        //if it's not empty
        if(ingredientDelete != null)
        {

            String ingredientNameDelete= (String) deleteIngredientsComboBox.getValue();
            AnchorPane a = new AnchorPane();
            //loop through each child in the anchor pane until the ingredientName label is equal to the deleted ingredient

            for(Node childNode: grid.getChildren())
            {
                
                if(childNode instanceof AnchorPane)
                {
                    //get name of ingredient through the label
                    AnchorPane childAnchor = (AnchorPane) childNode;
                    Label ingredientNameLabel = (Label) childAnchor.getChildren().get(0);
                    String ingredientName = ingredientNameLabel.getText();

                    if(ingredientName == ingredientNameDelete)
                    {
                        //update location variable
                        loc = i;
                        //remove it from the grid if it is the correct ingredient
                        a = childAnchor;
                        
                    }
                }
                //add i
                i ++;
            }
            grid.getChildren().remove(a);


            //go through each anchor pane in grid and update the row index in order to properly shift
            for(int j = loc; j < grid.getChildren().size(); j++)
            {
                grid.setRowIndex(grid.getChildren().get(loc), loc);
            }

            ingredientsInOrder.remove(ingredientNameDelete);
            row --;

           // System.out.print("this is the size of the vector: " + ingredientsInOrder.size());
            
        }

        ingredients_space.setContent(grid);
        //reset combo boxes
        comboBoxAdd();
        comboBoxDelete();

    }

    /**
     * This allows the manager to submit the supplier order. It ensures that all values are entered in correctly and that the order is added to the database
     */
    public void submitOrder()
    {
        //get the order number
        int orderNumberInt = Integer.parseInt(orderNumber.getText());


        //get the orderDate
        String orderDateValue = orderDate.getText();

        Date date1Value = new Date();

        String pattern = "yyyy-MM-dd";

        boolean problem = false;

        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        try {
            date1Value = dateFormat.parse(orderDateValue);
        } catch (ParseException e) {
            warning.setVisible(true);
            warning.setText("Error: please enter in yyyy-mm-dd for Date");
            problem = true;
            e.printStackTrace();
           

        }

        //get the order time
        String orderTimeValue = orderTime.getText();

        try {
            // Create a SimpleDateFormat object with the desired format
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

            // Parse the string and convert it to a Date object
            Date date = sdf.parse(orderTimeValue);

            // Format the Date object as a string in the desired format
            String formattedTime = sdf.format(date);

            System.out.println(formattedTime);

        } catch (ParseException e) {
            warning.setVisible(true);
            problem = true;
            warning.setText("Error: please enter time variable in hh:mm:ss");
            System.out.println("Error: " + e.getMessage() + ". Please provide a valid time string in the format 'hh:mm:ss'.");
            
        }

        if(problem == false)
        {
            //get the supplier/provider
            String providerValue = provider.getText();

            //create instance of supplier order
            SupplierOrder thisOrder = new SupplierOrder(orderNumberInt, orderTimeValue, orderDateValue, providerValue, 0.0);
            thisOrder.calculateTotalPrice();


            //go through each node in grid pane (each ingredient) and add it to supplier order

            for(Node anchor: grid.getChildren())
            {
                if(anchor instanceof AnchorPane)
                {
                    AnchorPane anchorPane = (AnchorPane) anchor;

                    Label ingredientNameLabel = (Label) anchorPane.getChildren().get(0);

                    String ingredientName = ingredientNameLabel.getText();

                    TextField quantityOrderedTxt = (TextField) anchorPane.getChildren().get(2);

                    int quantityOrd = Integer.parseInt(quantityOrderedTxt.getText());

                    TextField quantityRecTxt = (TextField) anchorPane.getChildren().get(4);

                    int quantityRec = Integer.parseInt(quantityRecTxt.getText());

                    TextField wholesale = (TextField) anchorPane.getChildren().get(6);

                    double wholesalePrice = Double.parseDouble(wholesale.getText());

                    thisOrder.addIngredient(ingredientName, quantityOrd, quantityRec, (quantityRec != 0), wholesalePrice);


                }
                
            }

            //submit the supplier order
            thisOrder.calculateTotalPrice();
            thisOrder.submitSupplierOrder();

            //switch to the other screen
            try {
                switchToSupplierOrder();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
            


    }

    
    
}


