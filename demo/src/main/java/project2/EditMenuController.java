package project2;

//import everything
import javafx.scene.Node;
import java.util.Date;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.layout.GridPane;
import javafx.scene.control.ScrollPane;
import java.util.HashMap;
import java.util.ArrayList;
import javafx.scene.control.TextField;
import java.util.Set;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;

import javafx.scene.control.TextFormatter;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import java.text.DecimalFormat;
import java.text.ParsePosition;

import javafx.util.converter.NumberStringConverter;


import javafx.scene.layout.AnchorPane;

// third
/**
 * This is a controller that allows for the manager to edit a menu item through changing its values, deleting it, or adding a new one
 * @author Keeley Mahoney
 */
public class EditMenuController {

    //scroll pane for ingredients in menu item
    @FXML
    private ScrollPane scroll;

    //edit button at top
    @FXML
    private Button edit;

    //text field that has name of the menu item
    @FXML 
    private TextField item_name;

    //text field that has price of the menu item
    @FXML 
    private TextField item_price;

    //combo box containing the various menu categories
    @FXML 
    private ComboBox<String> item_category;

    //grid pane for displaying ingredients of menu item
    @FXML 
    private GridPane grid;

    //dropdown box for adding ingredients
    @FXML
    ComboBox<String> dropdownAdd;

    //dropdown box for removing ingredients
    @FXML
    ComboBox<String> dropdownRemove;

    //initialize name of menu item
    String menu_item = "";

    //initialize price of menu item
    double price = 0.0;

    //initailize category of menu item
    String menu_category = "";

    //for the grid, initialize the column and row
    int col = 0;
    int row = 0;

    //initialize all of the needed hash maps for the various things
    private HashMap<String, Double> menu_prices= new HashMap<>();
    private HashMap<String, String> menu_categories= new HashMap<>();

    HashMap<String, Integer> ingredient_num1 = new HashMap<>();
    
    HashMap<IngredientDetails, Integer> ingredients_for_menu_item = new HashMap<>();

    HashMap<String, IngredientDetails> ingredients = new HashMap<>();

    private Menu_Item_Populate populator = new Menu_Item_Populate();


    private HashMap<String, Menu_Item> menuItems = new HashMap<>();




    //switch to the inventory view
    @FXML
    /**
     * Switches to the inventory screen
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

    //switch to trends screen
    @FXML
    /**
     * Switches to the trends screen
     * @throws IOException if the trends screen cannot be loaded
     */
    private void switchToTrends() throws IOException {
        App.setRoot("TrendsInitial");
        
    }

    //switch to edit screen
    @FXML
    /**
     * Switches to the manager menu screen
     * @throws IOException if the manager menu screen cannot be loaded
     */
    private void switchToSecondary() throws IOException {
        try
        {
            if(menu_item.equals("BLANK"))
            {
                populator.deleteMenuItemInDatabase("BLANK");
            }
            App.setRoot("managerMenu");
        }
        catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        
    }

    //switch to supplier orders screen
    @FXML
    /**
     * Switches to the supplier orders screen
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


    /**
     * Default constructor for the controller. Just ensures that an instance is created.
     */
    public EditMenuController() {
        // Default (no-argument) constructor

    }
    
    //initialize the data
    /**
     * Initializes the button text and ensures that the startIN() function is called
     * @param buttonText (String) that contains the text of the button that was clicked on the manager menu screen, so the menu item name
     */
    public void initData(String buttonText) {
        item_name.setText(buttonText);
        startIn();
        

    }


    //instead of the initialize function
    
    /**
     * Serves as the initializer function. If a menu item that already exists is selected, it loads the data onto the screen. If the menu item does not exist, it sets everything to blank. Also ensures that everything is properly initialized
     */
    @FXML
    public void startIn()
    {


        //initialize menu_item
        menu_item = item_name.getText().trim();

        // if(menu_item.isEmpty()) {
        //     System.out.println("Menu Item: BLANK");
        // } else {    
        //     System.out.println("Menu Item:" + menu_item + ":END");
        // }


        //populate everything
        populator.populateMenuItems();
        populator.populateAllIngredients();

        menuItems = populator.getMenuItems();

        ingredients_for_menu_item = populator.getMenuIngredientsWithQuantity(menu_item);

        ingredients = populator.getIngredients();

         //edit the style
         edit.setStyle("-fx-background-color: #500000;");
         edit.setTextFill(javafx.scene.paint.Color.WHITE);
        

        /* 
        ingredients.add("Burger Buns");
        ingredients.add("Burger Patty");
        ingredients.add("American Cheese");
        ingredients.add("Bacon");
        ingredients.add("Lettuce");
        ingredients.add("Tomatoes");
        ingredients.add("Straws");
        ingredients.add("Napkins");
        */

        //to ensure that only a positive number is correct
        UnaryOperator<TextFormatter.Change> filter1 = change -> {
            String newText = change.getControlNewText();

            
            if (isPositiveNumber(newText) || newText.isEmpty()) {
                return change;
            } else {
                return null;
            }
        };
        //new text formatter
        TextFormatter<Number> textFormatter1 = new TextFormatter<>(new NumberStringConverter(), 0, filter1);

        //set the text formatter
        item_price.setTextFormatter(textFormatter1);

        //if it's not a new item
        if(!menu_item.isEmpty())
        {
            

        
            //System.out.println("This is menu item name : " + menu_item);

            
            /* 
            menu_prices.put("Bacon Cheeseburger", 10.50);
            menu_prices.put("Gig Em Patty Melt", 10.50);
            menu_prices.put("Black Bean Burger", 10.50);
            menu_prices.put("Cheeseburger", 10.50);
            menu_prices.put("Revs Grilled Chicken Sandwich", 10.50);
            menu_prices.put("16 OZ Aquafina Water", 4.00);
            menu_prices.put("Fries", 2.50);
            menu_prices.put("20 OZ Aquafina Water", 6.00);
            menu_prices.put("20 OZ Foundtain Drink", 6.00);
            menu_prices.put("Ice Cream", 3.00);

            menu_categories.put("Bacon Cheeseburger", "Entree");
            menu_categories.put("Gig Em Patty Melt", "Entree");
            menu_categories.put("Black Bean Burger", "Entree");
            menu_categories.put("Cheeseburger", "Entree");
            menu_categories.put("Revs Grilled Chicken Sandwich", "Entree");
            menu_categories.put("16 OZ Aquafina Water", "Beverage");
            menu_categories.put("Fries", "Sides");
            menu_categories.put("20 OZ Aquafina Water", "Beverage");
            menu_categories.put("20 OZ Foundtain Drink", "Beverage");
            menu_categories.put("Ice Cream", "Beverage");

            ingredient_num1.put("Burger Buns", 1);
            ingredient_num1.put("Bacon", 1);
            ingredient_num1.put("American Cheese", 1);
            ingredient_num1.put("Burger Patty", 1);
            */

            //nestedHashMap.put("Bacon Cheeseburger", ingredient_num1);



            //ser the item name
            item_name.setText(menu_item);

            //get the price
            double price = menuItems.get(menu_item).getPrice();

            //set the item price
            item_price.setText(String.valueOf(price));

            //set the category
            menu_category = menuItems.get(menu_item).getCategory();

            //set the category
            item_category.setValue(menu_category);

            //add the various categories
            item_category.getItems().add("Entree");
            item_category.getItems().add("Side");
            item_category.getItems().add("Beverage");
            item_category.getItems().add("Dessert");
            item_category.getItems().add("Seasonal");


            //create hash map
            HashMap<IngredientDetails, Integer> returned_hash = new HashMap<>();
            returned_hash = ingredients_for_menu_item;


            Set<IngredientDetails> ingredientSet = returned_hash.keySet();

            ArrayList<IngredientDetails> listFromSet2 = new ArrayList<>(ingredientSet);

            ArrayList<String> ingredient_names = new ArrayList<String>();

            //add ingredient names
            for(int i = 0; i < ingredientSet.size(); i++)
            {
                ingredient_names.add(listFromSet2.get(i).getName());
            }


            //set the layout for the grid
            grid.setHgap(30);
            grid.setVgap(20);

            //go through each ingredient for the menu item
            for(int i = 0; i < returned_hash.size(); i++)
            {
                //if it's currently being used for menu item
                if(ingredients_for_menu_item.get(listFromSet2.get(i)) >= 0)
                {
                    UnaryOperator<TextFormatter.Change> filter2 = change -> {
                        String newText = change.getControlNewText();
            
                        // Use a regular expression to allow only non-negative integers
                        if (Pattern.matches("\\d*", newText)) {
                            return change;
                        } else {
                            return null;
                        }
                    };
            
                    TextFormatter<String> textFormatter2 = new TextFormatter<>(filter2);
                    

                    //create new text field and set style
                    TextField t = new TextField();
                    t.setStyle("-fx-font-size: 15;");
                    t.setTextFormatter(textFormatter2);
    

                    //create new anchor pane and set style
                    AnchorPane a = new AnchorPane();
                    a.setPrefWidth(309); 
                    
                    
                    //create new label and set style
                    a.setPrefHeight(45);
                    Label l = new Label();
                    l.setStyle("-fx-font-size: 15;");
                    l.setPrefWidth(231);
                    l.setPrefHeight(45);
                    
                    t.setPrefWidth(77);
                    t.setPrefHeight(45);
    

                    //get ingredient name
                    String ingredient = listFromSet2.get(i).getName();
    
                    //set the text of the label to be the ingredient name
                    l.setText(ingredient);


                    //quantity for that ingredient
                    int num_ingredients = returned_hash.get(listFromSet2.get(i));
                    t.setText(String.valueOf(num_ingredients));
    

                    //set layout for anchor pane
                    a.setTopAnchor(l, 0.0);
                    a.setLeftAnchor(l, 0.0);
    
                    a.setTopAnchor(t, 0.0);
                    a.setRightAnchor(t, 0.0);
    
                    //add label and text field to anchor pane
                    a.getChildren().addAll(l, t);
    
                    
                    //add to grid
                    grid.add(a, col, row);
                    col++;
                    if(col == 3)
                    {
                        col = 0;
                        row++;
                    }
                

                }
                
            }
            //add to scroll pane
            scroll.setContent(grid);

            //reset combo box add and combo box remove
            setComboBoxAdd();
            setComboBoxRemove();

        }

        else
        {
            //add categories
            item_category.getItems().add("Entree");
            item_category.getItems().add("Side");
            item_category.getItems().add("Beverage");
            item_category.getItems().add("Dessert");
            item_category.getItems().add("Seasonal");


            //initialize ingredients_for_menu_items
            ingredients_for_menu_item = null;

            //create default menu item
            populator.addMenuItem("BLANK", 0.00, "Entree", ingredients_for_menu_item);

            //name menu item
            menu_item = "BLANK";

            //reset combo box add and combo box remove
            setComboBoxAdd();
            setComboBoxRemove();

        }
        
    }

    //set the dropdown for the ingredients you can add to a menu item
    /**
     * For a menu item, sets the available ingredients to add to the menu item that aren't already apart of the menu item
     */
    public void setComboBoxAdd()
    {
        //clear
        dropdownAdd.getItems().clear();

        //create hash map
        HashMap<IngredientDetails, Integer> returned_hash = new HashMap<>();
        returned_hash = ingredients_for_menu_item;

        //get all of the keys in an array list
        Set<String> ingredientSet = ingredients.keySet();

        ArrayList<String> listFromSet3 = new ArrayList<>(ingredientSet);

        //if there's nothing in the ingredients, add all of the ingredients
        if(returned_hash == null)
        {
            for(int k = 0; k < ingredients.size(); k++)
            {
                dropdownAdd.getItems().add(listFromSet3.get(k));
            }
            
        }

        else
        {
                //go through and find ingredients taht aren't in the ingredints for the menu item or have negative values

            Set<IngredientDetails> ingredientSet2 = returned_hash.keySet();

            ArrayList<IngredientDetails> listFromSet = new ArrayList<>(ingredientSet2);

            ArrayList<String> ingredient_names = new ArrayList<String>();

            //go through all of the ingredients that are present and add their names to an array list
            for(int i = 0; i < ingredientSet2.size(); i++)
            {
                ingredient_names.add(listFromSet.get(i).getName());
            }

            //for each ingredient
            for(int i = 0; i < ingredients.size(); i++)
            {
                //if the ingredient is not currently in the menu item
            if(!(listFromSet.contains(ingredients.get(listFromSet3.get(i)))))
            {
                    dropdownAdd.getItems().add(listFromSet3.get(i));
            }
            else
            {
                //get specific ingredient
                IngredientDetails ingredient = ingredients.get(listFromSet3.get(i));

                //if the ingredient has a negative quantity for this menu item, add it to the dropdown
                if(ingredients_for_menu_item.get(ingredient) < 0)
                {
                    dropdownAdd.getItems().add(listFromSet3.get(i));
                }
            }
            }
        }
    }

//set the 
/**
 * Sets the combo box to contain ingredients that are already apart of the menu item and can be deleted
 */
    public void setComboBoxRemove()
    {
        //get the menu item name
        String menu_item_name = item_name.getText();

        //clear the dropdown remove
        dropdownRemove.getItems().clear();

        //get the current ingredients in the menu item
        HashMap<IngredientDetails, Integer> returned_hash = new HashMap<>();
        returned_hash = ingredients_for_menu_item;

        //nothign should be in removed if the returned hash is null
        if(returned_hash == null)
        {
            return;
        }

        Set<IngredientDetails> ingredientSet = returned_hash.keySet();

        //this is a list of all of the ingredients that are present in the current menu item
        ArrayList<IngredientDetails> listFromSet = new ArrayList<>(ingredientSet);

        ArrayList<String> ingredient_names = new ArrayList<String>();


        //add all of the current ingredient names to an array list
        for(int i = 0; i < ingredientSet.size(); i++)
        {
            ingredient_names.add(listFromSet.get(i).getName());
        }

        //if the quantity for the ingreident is greater than 0, add to reemove
        for(int i = 0; i < listFromSet.size(); i++)
        {
            int quantity = ingredients_for_menu_item.get(listFromSet.get(i));
            if(quantity > 0)
            {
                dropdownRemove.getItems().add(listFromSet.get(i).getName());
            }
                
            

        }

    }

    /**
     * Adds an ingredient to the menu item. Ensures that the proper quantity buttons are initialized so that the user can enter in the quantity of the ingredient in the menu item
     */
    public void addIngredient()
    {
        //get name of ingredient to add
        Object ingredient_add_obj = dropdownAdd.getValue();

        //get name of menu item
        String menu_item_name = item_name.getText();

        //if there's actually an ingredient to add
        if(ingredient_add_obj != null)
        {
            String ingredient_add = (String) dropdownAdd.getValue();
            //only non negative integers
            UnaryOperator<TextFormatter.Change> filter2 = change -> {
                String newText = change.getControlNewText();
    
                // Use a regular expression to allow only non-negative integers
                if (Pattern.matches("\\d*", newText) ) {
                    return change;
                } else {
                    return null;
                }
            };
    
            TextFormatter<String> textFormatter2 = new TextFormatter<>(filter2);
            
            //set text field, anchor pane, and label and their properties for the ingredients in menu item
            TextField t = new TextField();
            t.setStyle("-fx-font-size: 15;");
            t.setTextFormatter(textFormatter2);
            AnchorPane a = new AnchorPane();
            a.setPrefWidth(309);  
   
            a.setPrefHeight(45);
            Label l = new Label();
            l.setPrefWidth(231);
            l.setPrefHeight(45);
            
            t.setPrefWidth(77);
            t.setPrefHeight(45);

            l.setText(ingredient_add);
            l.setStyle("-fx-font-size: 15;");
            t.setText("1");

            a.setTopAnchor(l, 0.0);
            a.setLeftAnchor(l, 0.0);

            a.setTopAnchor(t, 0.0);
            a.setRightAnchor(t, 0.0);

            //add the label and text field to the anchor pane
            a.getChildren().addAll(l, t);

            //add to grid
            grid.add(a, col, row);
            col++;
            if(col == 3)
            {
                col = 0;
                row++;
            }

            IngredientDetails i = ingredients.get(ingredient_add);

            //create new hash map if there's nothing in there
            if(ingredients_for_menu_item == null)
            {
                ingredients_for_menu_item = new HashMap<>();
            }
            //add ingredeint
            

            /* 
            for(int p = 0; p < ingredients_for_menu_item.size(); p++)
            {
                System.out.println(var.get(p).getName());
            }
            */

            //if the ingredient is already there, update it
            if(ingredients_for_menu_item.containsKey(i))
            {
                populator.updateItemIngredients(menu_item_name, ingredients_for_menu_item);

            }
            else
            {
                //else add it
                populator.AddItemIngredients(menu_item_name, i, 1);

            }

            //place the ingredient into the current list of ingredients for the menu item
            ingredients_for_menu_item.put(i, 1);

            

            
            //set combo box add and combo box remove
            setComboBoxAdd();
            setComboBoxRemove();


            
        }
    }

    /**
     * Allows the manager to remove an ingredient from the menu item. It disappears from the GUI and is added to the list of ingredients that can be added to the menu item
     */
    public void RemoveIngredient()
    {
        //get name of ingredient remove
        Object ingredient_remove_obj = dropdownRemove.getValue();

        //get the menu item name
        String menu_item_name= item_name.getText();

        
        //if there's an ingredient to remove
        if(ingredient_remove_obj != null)
        {
            String ingredient_remove = (String) dropdownRemove.getValue();
            //clear
            grid.getChildren().clear();

            
            //set the quantity of the deleted ingredient to negative for the menu item
            IngredientDetails ingredient = ingredients.get(ingredient_remove);
            ingredients_for_menu_item.put(ingredient, -1);

            //populator.customizeItem(menu_item_name, ingredient_remove, -1);

            //update the menu item
            populator.updateItemIngredients(menu_item_name, ingredients_for_menu_item);




            //reset new col and menu
            int col1 = 0;
            int row2= 0;
            //go through each ingredient in ingredient menu
            for(int i = 0; i < ingredients_for_menu_item.size(); i++)
            {
                //for each ingredient, add it to the anchor pane
                Set<IngredientDetails> value = ingredients_for_menu_item.keySet();

                ArrayList<IngredientDetails> listFromSet = new ArrayList<>(value);

                ArrayList<String> ingredient_names = new ArrayList<String>();

                for(int j = 0; j < value.size(); j++)
                {
                    ingredient_names.add(listFromSet.get(j).getName());
                }
                

                //System.out.println("Number of " + ingredient_names.get(i) + " in the menu item is " + ingredients_for_menu_item.get(listFromSet.get(i)));
                //if the current ingredient has a quantity greater than 0
                if(ingredients_for_menu_item.get(listFromSet.get(i)) > 0)
                {
                    UnaryOperator<TextFormatter.Change> filter2 = change -> {
                        String newText = change.getControlNewText();
            
                        // Use a regular expression to allow only non-negative integers
                        if (Pattern.matches("\\d*", newText) ) {
                            return change;
                        } else {
                            return null;
                        }
                    };
            
                    //create text field, label, and anchor pane
                    TextFormatter<String> textFormatter2 = new TextFormatter<>(filter2);
                    TextField t = new TextField();
                    t.setStyle("-fx-font-size: 15;");
                    t.setTextFormatter(textFormatter2);
                    AnchorPane a = new AnchorPane();
                    a.setPrefWidth(309);  
        
                    a.setPrefHeight(45);
                    Label l = new Label();
                    l.setStyle("-fx-font-size: 15;");
                    l.setPrefWidth(231);
                    l.setPrefHeight(45);
                    
                    t.setPrefWidth(77);
                    t.setPrefHeight(45);
    
                    String ingredient_name = ingredient_names.get(i);
    
                    l.setText(ingredient_name);
    
                    int num_ingredients = ingredients_for_menu_item.get(listFromSet.get(i));
                    t.setText(String.valueOf(num_ingredients));
    
                    a.setTopAnchor(l, 0.0);
                    a.setLeftAnchor(l, 0.0);
    
                    a.setTopAnchor(t, 0.0);
                    a.setRightAnchor(t, 0.0);
    
                    a.getChildren().addAll(l, t);
    
                    
    
                    //make sure grid works
                    grid.add(a, col1, row2);
                    col1++;
                    if(col1 == 3)
                    {
                        col1 = 0;
                        row2++;
                    }
  
                }

            }
            
            //set content of scroll pane to grid, reset combo box add and combo box delete
            scroll.setContent(grid);
            setComboBoxAdd();
            setComboBoxRemove();




        }
    }

    /**
     * Function that allows a menu item to be deleted. Its price is set to a negative value, indicating that it no longer exists
     */
    public void deleteItem()
    {
        //set price to negative
        menuItems.get(menu_item).setPrice(-2);

        //delete the menu item
        populator.deleteMenuItem(menuItems.get(menu_item), ingredients_for_menu_item);

        //switch to edit screen
        try
        {
            switchToSecondary();
        }
        catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }


    }

    /**
     * Saves the changes in the databse for any edits that occured to the menu item, including name, price, category, and ingredients
     */
    public void saveChanges()
    {

        //get new name of item
        String new_name = item_name.getText();

        //get new price, and new category
        String new_price= item_price.getText();
        double double_price = Double.parseDouble(new_price);
        String new_category = item_category.getValue();


        //for each anchor pane in the grid (that contains ingredient name and quantity)
        for(Node n : grid.getChildren())
        {
            //basically go into the grid, get all of the labels and textfields corresponding to each ingredient in the menu item
           
            if(n instanceof AnchorPane)
            {
                //initialize data
                String ingredient_name = "";
                String quantity = "";
                AnchorPane a = (AnchorPane) n;
                Node labelNode = a.getChildren().get(0);
                Node textNode = a.getChildren().get(1);

                //get the text of the label and set ingredient name to it
                if(labelNode instanceof Label)
                {
                    Label l = (Label) labelNode;
                    ingredient_name = l.getText();
                }

                //get the text of the text field and set quantity to it
                if(textNode instanceof TextField)
                {
                    TextField t = (TextField) textNode;
                    quantity = t.getText();
                }

                //change string to int
                int int_quantity = Integer.parseInt(quantity);

                //get current ingredient in menu item
                IngredientDetails i = ingredients.get(ingredient_name);

                //set the quanaity for it
                ingredients_for_menu_item.put(i, int_quantity);


            }
            
            
        }

        /* 
        HashMap<String, Integer> new_ingredients = new HashMap<>();
        for(int i = 0; i < ingredient_num1.size(); i++)
        {
            int ingredient_value = (ingredient_num1.get(keysArray[i]));
            new_ingredients.put(keysArray[i], ingredient_value);
        }
        */


        //if you rename the menu item or it's a new item
        if(new_name != menu_item)
        {
            //set the old price to -2
            menuItems.get(menu_item).setPrice(-2.00);
            //menuItems.put(new_name, new_menu_item);

            //update old menu item
            populator.updateMenuItem(double_price, new_category, menu_item, new_name, ingredients_for_menu_item);

            //if it's a new menu item
            if(menu_item == "BLANK")
            {
                //remove the dummy held in place for it
                menuItems.remove("BLANK");
                //delete it from database
                populator.deleteMenuItemInDatabase(menu_item);
            }



        }

        //if it's the same name
        else
        {
            //set new price and catetgory and update it
            menuItems.get(new_name).setPrice(double_price);
            menuItems.get(new_name).setCategory(new_category);

            populator.updateMenuItem(double_price, new_category, menu_item, new_name, ingredients_for_menu_item);

        }

        

        

        //switch back to secondary screen
        try
        {
            switchToSecondary();
        }
        catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }


    }

    //ensure that only positive numbers are counted
    /**
     * Function that determines if a string, when converted to an int, is a positive number
     * @param text (String) that serves as the input to see if it's a positive number
     * @return a boolean indicating if the string as a double is a positive number
     */
    private boolean isPositiveNumber(String text) {
        try {
            // Use a DecimalFormat to parse the text and check if it's a positive number
            DecimalFormat format = new DecimalFormat();
            ParsePosition parsePosition = new ParsePosition(0);
            Number number = format.parse(text, parsePosition);

            return parsePosition.getIndex() == text.length() && number.doubleValue() >= 0;
        } catch (Exception e) {
            return false;
        }
    }

    



   

    

}