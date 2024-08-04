package project2;

import java.io.IOException;
import javafx.fxml.FXML;

import java.util.Date;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import java.util.Vector;


import javafx.scene.control.TextFormatter;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.scene.control.ComboBox;


// Fifth
/**
 * This is a controller that allows the manager to edit a particular ingredient, including its quantity, storage location, and units
 * @author Keeley Mahoney
 */
public class EditInventoryController
{

    InventoryManager inventoryManager = new InventoryManager();
    @FXML
    Button inventory;


    @FXML
    TextField item_name;

    @FXML
    TextField item_count;

    @FXML 
    TextField itemStorageLocation;

    @FXML
    TextField itemUnits;

    @FXML
    Label already_exists;

    Map<String, IngredientDetails> values = new HashMap<String, IngredientDetails>();

    /**
     * Default constructor for editInventory
     */
    public EditInventoryController()
    {

    }
    /**
     * This function is automatically called when the screen is loaded. It sets the style of the button and ensures that only nonnegative integers can be entered in for the count
     */
    public void initialize()
    {

        inventory.setStyle("-fx-background-color: #500000;");
        inventory.setTextFill(javafx.scene.paint.Color.WHITE);
        inventoryManager.populateAllIngredients();
        values = inventoryManager.getIngredientInventory();


        already_exists.setVisible(false);
        inventory.setStyle("-fx-background-color: #c7919a;");
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            // Use a regular expression to allow only non-negative integers
            if (Pattern.matches("\\d*", newText)) {
                return change;
            } else {
                return null;
            }
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);

        item_count.setTextFormatter(textFormatter);
        
    }

    @FXML
    /**
     * Function that switches to the manager menu screen
     * @throws IOException if the manager menu screen cannot be loaded
     */
    private void switchToSecondary() throws IOException {
        App.setRoot("managerMenu");
    }

    @FXML
    /**
     * Function that switches to the supplier orders supplier
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

    /**
     * Submits the changes for editing the details on a specific ingredient in the inventory for its quantity, location, and units. Also updates the warning label if the quantity falls below the minimum value set
     */
    public void submitChanges()
    {
        String name = item_name.getText();
        String count = item_count.getText();
        String location = itemStorageLocation.getText();
        String units = itemUnits.getText();

         // Define a UnaryOperator to filter out non-negative-integer characters
         UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            // Use a regular expression to allow only non-negative integers
            if (Pattern.matches("\\d*", newText)) {
                return change;
            } else {
                return null;
            }
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);

        item_count.setTextFormatter(textFormatter);

        if(values.containsKey(name) == true)
        {
            if(values.get(name).getQuantity() > 0)
            {
                already_exists.setVisible(true);
            }

            else if( !(count.equals("")))
            {
                Date currentDate = new Date();

                already_exists.setVisible(false);
                int num_count = Integer.parseInt(count);
                IngredientDetails ingredient = new IngredientDetails(name, num_count, num_count, units, currentDate, location);

                

                inventoryManager.updateIngredient(name, num_count, num_count, units, currentDate, location);
                values.put(name, ingredient);



            try
            {
                this.switchToPrimary();
            }
            catch (Exception e) {
                e.printStackTrace(); // Handle the exception appropriately
            }

            }
            
        }
        else if(!(name.equals("")) && !(count.equals("")))
        {

            Date currentDate = new Date();

            already_exists.setVisible(false);
            int num_count = Integer.parseInt(count);
            IngredientDetails ingredient = new IngredientDetails(name, num_count, num_count, units, currentDate, location);

            

            inventoryManager.addIngredient(name, num_count, num_count, units, currentDate, location);
            values.put(name, ingredient);



            try
            {
                this.switchToPrimary();
            }
            catch (Exception e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
            
        }
    }
}