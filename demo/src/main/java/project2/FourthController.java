package project2;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.ScrollPane;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashSet;
import javafx.scene.control.TextField;
import java.util.Set;
import javafx.scene.control.Label;
import java.util.Vector;
import javafx.scene.control.ComboBox;

import javafx.scene.control.TextFormatter;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.scene.layout.AnchorPane;

/**
 * Class that allows the manager to enter in the minimum quantity for each ingredient
 * @author Keeley Mahoney
 */

public class FourthController
{
    @FXML
    private ScrollPane scrollPane;

    @FXML
    Button inventory;

    //create new instance of inventory manager that will act as the connection to the databse
    InventoryManager inventoryManager = new InventoryManager();
    
    @FXML
    private AnchorPane dynamicAnchorPaneContainer;

    Vector<Label> labels = new Vector<Label>();

    Vector<TextField> text = new Vector<TextField>();

    HashMap<String, Integer> warning_ingredient = new HashMap<>();

    //stores name of the ingredient and the ingredient details related to it
    Map<String, IngredientDetails> values = new HashMap<String, IngredientDetails>();

    /**
     * Default constructor for inventory minimum amount screen
     */
    public FourthController()
    {

    }
    @FXML
    /**
     * Function that switches to the inventory screen
     * @throws IOException if the inventory screen cannot be loaded
     */
    private void switchToPrimary() throws IOException {
        try
        {
            App.setRoot("inventory");
        }
        catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        
    }

    @FXML
    /**
     * Function that switches to the trends screen
     * @throws IOException if the trends screen cannot be loaded
     */
    private void switchToTrends() throws IOException {
        App.setRoot("TrendsInitial");
        
    }

    @FXML
    /**
     * Function that switches to the supplier orders screen
     * @throws IOException if the supplier orders screen cannot be loaded
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

    @FXML
    /**
     * Function that switches to the manager menu screen
     * @throws IOException if the manager menu screen cannot be loaded
     */
    private void switchToSecondary() throws IOException {
        try
        {
            App.setRoot("managerMenu");
        }
        catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        
    }

    
    /**
     * Function that is automatically called when the screen is loaded. Sets the buttons to the proper values, initializes all of the neccesary variables of the class, and displays each ingredient and its minimum value
     */
    public void initialize()
    {
        inventory.setStyle("-fx-background-color: #500000;");
        inventory.setTextFill(javafx.scene.paint.Color.WHITE);

        inventoryManager.populateAllIngredients();

        //get the inventory of each ingredient
        values = inventoryManager.getIngredientInventory();


        for(String ingredientName: values.keySet())
        {
            if(values.get(ingredientName).getQuantity() >= 0)
            {
                warning_ingredient.put(ingredientName, values.get(ingredientName).getQuantityBefore());
            }
            
        }

        Set<String> value = warning_ingredient.keySet();
        String[] keysArray = value.toArray(new String[0]);

        int x = 0;
        int y = -100;
        for(int i = 0; i < warning_ingredient.size(); i++)
        {
            UnaryOperator<TextFormatter.Change> filter = change -> {
                String newText = change.getControlNewText();
    
                // Use a regular expression to allow only non-negative integers
                if (Pattern.matches("\\d*", newText)) {
                    return change;
                } else {
                    return null;
                }
            };
            TextField t = new TextField();
            t.setStyle("-fx-font-size: 15;");
            TextFormatter<String> textFormatter = new TextFormatter<>(filter);

            t.setTextFormatter(textFormatter);
            if(i % 2 == 0)
            {
                x = 0;
                y+= 100;
            }
            else
            {
                x += 500;
                
            }
            AnchorPane a = new AnchorPane();
            a.setLayoutX(50 + x);
            a.setLayoutY(y);
            a.setPrefWidth(400);  
            a.setPrefHeight(45);
            Label l = new Label();
            l.setStyle("-fx-font-size:15;");
            l.setPrefWidth(300);
            l.setPrefHeight(45);
            
            String item_name = keysArray[i];
            l.setText(item_name);
            
            t.setPrefWidth(100);
            t.setPrefHeight(45);
            t.setText(String.valueOf(warning_ingredient.get(item_name)));

           

            a.setTopAnchor(l, 0.0);
            a.setLeftAnchor(l, 0.0);

            a.setTopAnchor(t, 0.0);
            a.setRightAnchor(t, 0.0);

            text.add(t);
            labels.add(l);




            a.getChildren().addAll(l, t);

            dynamicAnchorPaneContainer.getChildren().add(a);
            
            

        }

        scrollPane.setContent(dynamicAnchorPaneContainer);

    }

    /**
     * Allows the manager to submit changes to the minimum value of each ingredient. Switches back to the invetory screen if not correct
     * @throws IOException if the inventory screen cannot be loaded
     
     */
    public void submitChanges() throws IOException
    {
        HashMap<String, Integer> values2 = new HashMap<String, Integer>();

        for(int i = 0; i < labels.size(); i++)
        {
            values2.put(labels.get(i).getText(), Integer.parseInt(text.get(i).getText()));

        }
        warning_ingredient = values2;

        for(String ingredient_name: warning_ingredient.keySet())
        {

            inventoryManager.updateIngredient(ingredient_name, warning_ingredient.get(ingredient_name), values.get(ingredient_name).getQuantity(),  values.get(ingredient_name).getUnit(),  values.get(ingredient_name).getExpDate(),  values.get(ingredient_name).getStorageLocation());
        }

        try{
            switchToPrimary();
        }
        catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        
    }

}