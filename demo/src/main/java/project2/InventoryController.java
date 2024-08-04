//import everything we need
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
import javafx.scene.paint.Color;


import javafx.scene.control.TextFormatter;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.scene.control.ComboBox;

import javafx.util.converter.IntegerStringConverter;

import javafx.scene.control.TextFormatter;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import java.text.DecimalFormat;
import java.text.ParsePosition;

import javafx.util.converter.NumberStringConverter;

// Primary
/**
 * Screen that shows the manager the inventory for each ingredient: including quantity, units, storage location, and displays a warning if the quantity falls below the minimum value
 * @author Keeley Mahoney
 */
public class InventoryController {
    //create new instance of inventory manager that will act as the connection to the databse
    InventoryManager inventoryManager = new InventoryManager();
   

    //initialize the inventory button
    @FXML
    Button inventory;

    //initialize the scroll pane
    @FXML
    private ScrollPane scrollPane;

    
    //initailize the delete item combo box - will delete ingredients
    @FXML
    private ComboBox<String> delete_item;
    
    //the anchor pane that everything is added to
    @FXML
    private AnchorPane dynamicAnchorPaneContainer;

    //stores all the quantity text fields
    Vector<TextField> quantityVec = new Vector<TextField>();

    //stores all of the ingredient name labels
    Vector<Label> ingredientVec = new Vector<Label>();

    //stores all of the storage location labels
    Vector<Label> storeLoc = new Vector<Label>();

    //stores all of the storage location value text fields
    Vector<TextField> storeLocValue = new Vector<TextField>();

    //stores all of the uints labels
    Vector<Label> unitsVec = new Vector<Label>();

    //stores all of the units value text fields
    Vector<TextField> unitValue = new Vector<TextField>();

    //stores name of the ingredient and the ingredient details related to it
    Map<String, IngredientDetails> values = new HashMap<String, IngredientDetails>();

    //warning ingredient NOT NEEDED NOW
    HashMap<String, Integer> warning_ingredient = new HashMap<>();

    /**
     * Default constructor for inventory class
     */
    public InventoryController()
    {

    }
    //switches to the edit screen
    @FXML
    /**
     * Function that switches to the manager menu screen
     * @throws IOException if the manager menu screen cannot be loaded
     */
    private void switchToSecondary() throws IOException {
        App.setRoot("managerMenu");
        
    }

    //switches to the trends screen
    @FXML
     /**
     * Function that switches to the trends screen
     * @throws IOException if the manager trends screen cannot be loaded
     */
    private void switchToTrends() throws IOException {
        App.setRoot("TrendsInitial");
        
    }

     //switch to supplier order screen
     @FXML
      /**
     * Function that switches to the supplier order
     * @throws IOException if the manager supplier order screen cannot be loaded
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

    //switches to add new ingredient screen
    @FXML
     /**
     * Function that switches to the edit inventory screen
     * @throws IOException if the edit inventory screen cannot be loaded
     */
    private void switchToFifth() throws IOException {
        App.setRoot("editInventory");
        
    }

    @FXML
    private void switchToFourth() throws IOException {
        App.setRoot("fourth");
       
    }

    @FXML
    private void switchToEmployeeList() throws IOException {
        App.setRoot("employeeList");
    }
    
    //initalizes everything
    /**
     * Initializes the inventory screen with the ingredient, ingredient quantity, storage location, units, and potential warning label. It also populates the needed variables
     */
    @FXML
    public void initialize()
    {
        dynamicAnchorPaneContainer.getChildren().clear();
        scrollPane.setContent(null);
        //System.out.println("does this print");
        //populates inventory Manager with all of the ingredients in the databse
        inventoryManager.populateAllIngredients();
        //System.out.println("this should print");
        
        //get the inventory of each ingredient
        values = inventoryManager.getIngredientInventory();

        

        //set the style
        inventory.setStyle("-fx-background-color: #500000;");
        inventory.setTextFill(javafx.scene.paint.Color.WHITE);

        for(String ingredientName: values.keySet())
        {
            warning_ingredient.put(ingredientName, values.get(ingredientName).getQuantityBefore());
        }
        

        
        //way to get all of the keys
        Set<String> value = values.keySet();
        String[] keysArray = value.toArray(new String[0]);

        

        
        //used for putting the anchor panes where they need to go
        int x = 0;
        int y = -200;

        //location variable to keep track of where the anchor panes are
        int location = 0;

        //go through each ingredient
        for(int i = 0; i < values.size(); i++)
        {
            //if it's quantity is positive
            if(!(values.get(keysArray[i]).getQuantity() < 0))
            {

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
                

                //create new text field
                TextField t = new TextField();
                t.setStyle("-fx-font-size: 15;");
    
                t.setTextFormatter(textFormatter);

                //set the location
                if(location % 2 == 0)
                {
                    x = 0;
                    y+= 200;
                }
                else
                {
                    x += 500;
                    
                }

                //create new anchor pane
                AnchorPane a = new AnchorPane();

                //add layout to anchor pane
                a.setLayoutX(50 + x);
                a.setLayoutY(y);
                a.setPrefWidth(400);  
                a.setPrefHeight(200);

                //create label, set location of label and add style
                Label l = new Label();
                l.setStyle("-fx-font-size: 15;");
                l.setPrefWidth(300);
                l.setPrefHeight(50);
                
                //get the name of the ingredient and set the text field to be that value
                String item_name = keysArray[i];
                l.setText(item_name);
               
                //set style for the text field
                t.setPrefWidth(100);
                t.setPrefHeight(50);

                //set the text field to be the quantity of the ingredient
                t.setText(String.valueOf(values.get(item_name).getQuantity()));

                //Create new label for storage location
                Label storageLoc = new Label();
                storageLoc.setPrefSize(300, 50);
                storageLoc.setText("Storage Location: ");

                //create new text field for value of storage location
                TextField storageLocInput = new TextField();
                storageLocInput.setPrefSize(100, 50);
                storageLocInput.setText(String.valueOf(values.get(item_name).getStorageLocation()));

                //create new label for unit
                Label unitLabel = new Label();
                unitLabel.setPrefSize(300, 50);
                unitLabel.setText("Units: ");

                //create new text field for value of unit
                TextField unit = new TextField();
                unit.setPrefSize(100, 50);
                unit.setText(String.valueOf(values.get(item_name).getUnit()));

                //
                

                //NOT NEEDED NOW
                //System.out.println("for "+ keysArray[i] + " the quantity warning is " + values.get(keysArray[i]).getQuantityBefore() + " and the current quantity is " + values.get(keysArray[i]).getQuantity());
                if((values.get(keysArray[i]).getQuantity()) < (values.get(keysArray[i]).getQuantityBefore()))
                {
                    //System.out.println("this should print for: " + values.get(keysArray[i]));
                    Label warning = new Label();
                    warning.setPrefWidth(300);
                    warning.setPrefHeight(50);
                    warning.setTextFill(Color.RED);
                    warning.setStyle("-fx-background-color: yellow;");
                    warning.setText("Warning: " + item_name + " is running low.");
                    warning.setStyle("-fx-font-size: 15;");
    
    
                    a.getChildren().addAll(warning);
                    a.setTopAnchor(warning, 150.0);
                    a.setLeftAnchor(warning, 0.0);
                    
                }
    
                
    
                
                //set location for anchor
                a.setTopAnchor(l, 0.0);
                a.setLeftAnchor(l, 0.0);
    
                a.setTopAnchor(t, 0.0);
                a.setRightAnchor(t, 0.0);

                //set location for anchor for storigae location label
                a.setTopAnchor(storageLoc, 50.0);
                a.setLeftAnchor(storageLoc, 0.0);

                //set location for anchor for storage location text field
                a.setTopAnchor(storageLocInput, 50.0);
                a.setLeftAnchor(storageLocInput, 300.0);

                //set location for anchor for unit label
                a.setTopAnchor(unitLabel, 100.0);
                a.setLeftAnchor(unitLabel, 0.0);

                //set location for anchor for unit text field
                a.setTopAnchor(unit, 100.0);
                a.setLeftAnchor(unit, 300.0);
    
                //add the labels and the text fields to the anchor
                a.getChildren().addAll(l, t, storageLoc, storageLocInput, unitLabel, unit);
    
                //add the anchor to the big anchor pane
                dynamicAnchorPaneContainer.getChildren().add(a);

                //add to respective vectors
                quantityVec.add(t);
                ingredientVec.add(l);
                storeLoc.add(storageLoc);
                storeLocValue.add(storageLocInput);
                unitsVec.add(unitLabel);
                unitValue.add(unit);

                location ++;
            }

            }
            
            //set the scroll pane to be the content of the big anchor pane
        scrollPane.setContent(dynamicAnchorPaneContainer);

        delete_item.getItems().clear();

        for(int i = 0; i < values.size(); i++)
        {
            if(values.get(keysArray[i]).getQuantity() > 0)
            {
                delete_item.getItems().add(keysArray[i]);
            }
            
        }
    }

    /**
     * Saves the changes made to the inventory for each ingredient: including quantity, units, and storage location. Displays warning label if the quantity is below the minimum value
     */
    public void saveChanges()
    {
        Map<String, IngredientDetails> values2 = new HashMap<String, IngredientDetails>();

        //add the new ingredient details in a hash map
        for(int i = 0; i < ingredientVec.size(); i++)
        {
            String name = ingredientVec.get(i).getText();
            int quantity = Integer.parseInt(quantityVec.get(i).getText());
            int warning_value = values.get(name).getQuantityBefore();
            String storageLoc = storeLocValue.get(i).getText();
            String units = unitValue.get(i).getText();
            IngredientDetails details = values.get(name);
            details.setQuantity(quantity);
            details.setQuantityBefore(warning_value);
            details.setStorageLocation(storageLoc);
            details.setUnit(units);
            values2.put(name, details);

        }

        //set the og ingredient details to be this new one
        values = values2;

        //get rid of the dynamic anchor pane
        dynamicAnchorPaneContainer.getChildren().clear();

        //to get the keys of the ingredients
        Set<String> value = values.keySet();
        String[] keysArray = value.toArray(new String[0]);

        //for location purposes
        int x = 0;
        int y = -200;
        int location = 0;

        //get rid of the current text fields and labels
        quantityVec.clear();
        ingredientVec.clear();
        storeLoc.clear();
        storeLocValue.clear();
        unitsVec.clear();
        unitValue.clear();

        //go through each ingredient
        for(int i = 0; i < values.size(); i++)
        {

            //if the quantity is positive
            if(values.get(keysArray[i]).getQuantity() > 0)
            {
                //only allow positive integers
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
                //create new text field and set style
                TextField t = new TextField();
                t.setStyle("-fx-font-size: 15;");
    
                t.setTextFormatter(textFormatter);
            // Set the TextFormatter to the TextField
                
            //anchor pane locations
                if(location % 2 == 0)
                {
                    x = 0;
                    y+= 200;
                }
                else
                {
                    x += 500;
                    
                }

                //create new anchor pane and set its style and location
                AnchorPane a = new AnchorPane();
                a.setLayoutX(50 + x);
                a.setLayoutY(y);
                a.setPrefWidth(400);  
                a.setPrefHeight(200);

                //create new label and set its style and location
                Label l = new Label();
                l.setStyle("-fx-font-size: 15;");
                l.setPrefWidth(300);
                l.setPrefHeight(50);
                
                //get item name and set the label to be the item name
                String item_name = keysArray[i];
                l.setText(item_name);
                
                //set the text box
                t.setPrefWidth(100);
                t.setPrefHeight(50);
                t.setText(String.valueOf(values.get(item_name).getQuantity()));

                //Create new label for storage location
                Label storageLoc = new Label();
                storageLoc.setPrefSize(300, 50);
                storageLoc.setText("Storage Location: ");

                //create new text field for value of storage location
                TextField storageLocInput = new TextField();
                storageLocInput.setPrefSize(100, 50);
                storageLocInput.setText(String.valueOf(values.get(item_name).getStorageLocation()));

                //create new label for unit
                Label unitLabel = new Label();
                unitLabel.setPrefSize(300, 50);
                unitLabel.setText("Units: ");

                //create new text field for value of unit
                TextField unit = new TextField();
                unit.setPrefSize(100, 50);
                unit.setText(String.valueOf(values.get(item_name).getUnit()));
                
                if(values.get(keysArray[i]).getQuantity() < values.get(keysArray[i]).getQuantityBefore())
                {
                    //values.get(item_name).getQuantity() < warning_ingredient.get(item_name)
                    Label warning = new Label();
                    warning.setPrefWidth(300);
                    warning.setPrefHeight(50);
                    warning.setTextFill(Color.RED);
                    warning.setStyle("-fx-background-color: yellow;");
                    warning.setText("Warning: " + item_name + " is running low.");
                    warning.setStyle("-fx-font-size: 15;");
    
    
                    a.getChildren().addAll(warning);
                    a.setTopAnchor(warning, 150.0);
                    a.setLeftAnchor(warning, 0.0);
                    
                }
    
                
                //set the location of the anchor
                a.setTopAnchor(l, 0.0);
                a.setLeftAnchor(l, 0.0);
    
                a.setTopAnchor(t, 0.0);
                a.setRightAnchor(t, 0.0);
                //set location for anchor

                //set location for anchor for storigae location label
                a.setTopAnchor(storageLoc, 50.0);
                a.setLeftAnchor(storageLoc, 0.0);

                //set location for anchor for storage location text field
                a.setTopAnchor(storageLocInput, 50.0);
                a.setLeftAnchor(storageLocInput, 300.0);

                //set location for anchor for unit label
                a.setTopAnchor(unitLabel, 100.0);
                a.setLeftAnchor(unitLabel, 0.0);

                //set location for anchor for unit text field
                a.setTopAnchor(unit, 100.0);
                a.setLeftAnchor(unit, 300.0);
    
                //add the labels and the text fields to the anchor
                a.getChildren().addAll(l, t, storageLoc, storageLocInput, unitLabel, unit);
    

                //set anchor pane
                dynamicAnchorPaneContainer.getChildren().add(a);
                quantityVec.add(t);
                ingredientVec.add(l);
                storeLoc.add(storageLoc);
                storeLocValue.add(storageLocInput);
                unitsVec.add(unitLabel);
                unitValue.add(unit);
                location ++;
            }
            //set scroll pane to be dynamic anchor pane
            scrollPane.setContent(dynamicAnchorPaneContainer);

        }
            

        //get ingredient names
        Set<String> value2 = values.keySet();
        String[] keysArray2 = value.toArray(new String[0]);

        for(String ingredientName: values.keySet())
        {
            int quantity = values.get(ingredientName).getQuantity();
            int warning_val = values.get(ingredientName).getQuantityBefore();
            String unit = values.get(ingredientName).getUnit();
            Date expDate = values.get(ingredientName).getExpDate();
            String storage_loc = values.get(ingredientName).getStorageLocation();
            //update each ingredient
            inventoryManager.updateIngredient(ingredientName, warning_val, quantity, unit, expDate, storage_loc);
        }



        
    }

    /**
     * Function that allows an ingredient to be deleted from the inventory - sets its quantity to negative indicating it no longer exists
     */
    public void deleteIngredient()
    {
        //get the deleted ingredient
        String deleted_ingredient = delete_item.getValue();

        //get location
        int x = 0;
        int y = -200;

        //if it's not empty
        if(!(deleted_ingredient.equals("")))
        {
            //set its quantity to -1
            values.get(deleted_ingredient).setQuantity(-1);

            //get the values of everything
            String name = deleted_ingredient;
            int quantity_before = -1;
            int quantity = -1;
            String unit = values.get(deleted_ingredient).getUnit();
            Date date = values.get(deleted_ingredient).getExpDate();
            String loc = values.get(deleted_ingredient).getStorageLocation();

            //set the quantity before to also be -1
            values.get(deleted_ingredient).setQuantityBefore(-1);

            //update the ingredient
            inventoryManager.updateIngredient(name, quantity_before, quantity, unit, date, loc);

            
            //remove the deleted ingredient from the labels and text fields
            for(int i = 0; i < ingredientVec.size(); i++)
            {
                if(ingredientVec.get(i).getText() == deleted_ingredient)
                {
                    ingredientVec.remove(i);
                    quantityVec.remove(i);
                    storeLoc.remove(i);
                    storeLocValue.remove(i);
                    unitValue.remove(i);
                    unitsVec.remove(i);
                }
            }



            //clear the anchor pane
            dynamicAnchorPaneContainer.getChildren().clear();

            //get the keys of the ingredients hash map
            Set<String> value = values.keySet();
            String[] keysArray = value.toArray(new String[0]);

            //set location
            int location = 0;
            for(int i = 0; i < values.size(); i++)
            {
                //if the quantity is greater than 0
                if(values.get(keysArray[i]).getQuantity() > 0)
                {
                    
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
                    
                    //create new text field and set its style
                    TextField t = new TextField();
                    t.setStyle("-fx-font-size: 15;");
        
                    t.setTextFormatter(textFormatter);

                    //update location of anchor pane x and y
                    if(location % 2 == 0)
                    {
                        x = 0;
                        y+= 200;
                    }
                    else
                    {
                        x += 500;
                        
                    }

                    //create new anchor pane, set its layout and style
                    AnchorPane a = new AnchorPane();
                    a.setLayoutX(50 + x);
                    a.setLayoutY(y);
                    a.setPrefWidth(400);  
                    a.setPrefHeight(45);

                    //create new label, set style and location
                    Label l = new Label();
                    l.setStyle("-fx-font-size: 15;");
                    l.setPrefWidth(300);
                    l.setPrefHeight(45);
                    
                    //get the name
                    String item_name = keysArray[i];
                    l.setText(item_name);
                   
                    //set the text field 
                    t.setPrefWidth(100);
                    t.setPrefHeight(45);
                    t.setText(String.valueOf(values.get(item_name).getQuantity()));
                    t.setStyle("-fx-font-size:15;");

                    //Create new label for storage location
                    Label storageLoc = new Label();
                    storageLoc.setPrefSize(300, 50);
                    storageLoc.setText("Storage Location: ");

                    //create new text field for value of storage location
                    TextField storageLocInput = new TextField();
                    storageLocInput.setPrefSize(100, 50);
                    storageLocInput.setText(String.valueOf(values.get(item_name).getStorageLocation()));

                    //create new label for unit
                    Label unitLabel = new Label();
                    unitLabel.setPrefSize(300, 50);
                    unitLabel.setText("Units: ");

                    //create new text field for value of unit
                    TextField unitVal = new TextField();
                    unitVal.setPrefSize(100, 50);
                    unitVal.setText(String.valueOf(values.get(item_name).getUnit()));
    
                    //NOT NEEDED
                    if(values.get(keysArray[i]).getQuantity() < values.get(keysArray[i]).getQuantityBefore())
                    {
                        //values.get(item_name).getQuantity() < warning_ingredient.get(item_name)
                        Label warning = new Label();
                        
                        warning.setPrefWidth(300);
                        warning.setPrefHeight(200);
                        warning.setTextFill(Color.RED);
                       
                        warning.setStyle("-fx-background-color: yellow;");
                        warning.setText("Warning: " + item_name + " is running low.");
                        warning.setStyle("-fx-font-size: 15;");
    
    
                        a.getChildren().addAll(warning);
                        a.setTopAnchor(warning, 50.0);
                        a.setLeftAnchor(warning, 0.0);
                        
                    }
    
    
                    
                    //set location of anchor pane
                    a.setTopAnchor(l, 0.0);
                    a.setLeftAnchor(l, 0.0);
    
                    a.setTopAnchor(t, 0.0);
                    a.setRightAnchor(t, 0.0);

                    //set location for anchor for storigae location label
                    a.setTopAnchor(storageLoc, 50.0);
                    a.setLeftAnchor(storageLoc, 0.0);

                    //set location for anchor for storage location text field
                    a.setTopAnchor(storageLocInput, 50.0);
                    a.setLeftAnchor(storageLocInput, 300.0);

                    //set location for anchor for unit label
                    a.setTopAnchor(unitLabel, 100.0);
                    a.setLeftAnchor(unitLabel, 0.0);

                    //set location for anchor for unit text field
                    a.setTopAnchor(unitVal, 100.0);
                    a.setLeftAnchor(unitVal, 300.0);
    
                    //add the labels and the text fields to the anchor
                    a.getChildren().addAll(l, t, storageLoc, storageLocInput, unitLabel, unitVal);
    
            
                    //add the anchor pane to the dynamic anchor pane
                    dynamicAnchorPaneContainer.getChildren().add(a);

                    //increase the location
                    location ++;
                }
                delete_item.getItems().clear();

                for(int z = 0; z < values.size(); z++)
                {
                    if(values.get(keysArray[z]).getQuantity() > 0)
                    {
                        delete_item.getItems().add(keysArray[z]);
                    }
            
                }

            }
                //set the scroll pane content to be the dynamic anchor pane
            scrollPane.setContent(dynamicAnchorPaneContainer);
        }
    }
    
}