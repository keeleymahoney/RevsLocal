package project2;


import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.*;

/**
 * Class to retrieve all ingredients
 * @author Keeley Mahoney
 */
public class InventoryManager{
    private HashMap<String, IngredientDetails> ingredientInventory; // Maps ingredient names to IngredientDetails
    //private HashMap<String, HashMap<String, Integer>> menuIngredients; // Menu item name -> (Ingredient -> Quantity)
    private Vector<IngredientDetails> currentIngredients; // Track current ingredients with details
    private static DatabaseHandler dbHandler; //TEST

    /**
     * Constructor for class
     */
    public InventoryManager() {
        this.ingredientInventory = new HashMap<>();
        //this.menuIngredients = new HashMap<>();
        this.currentIngredients = new Vector<>();
        this.dbHandler = new DatabaseHandler(); //TEST
       
    }

    /**
     * Retrieve all ingredients from SQL database
     */
    public void populateAllIngredients() {
        String sql = "SELECT * FROM Ingredients";
         
        try (Statement stmt = dbHandler.getConn().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String ingredientName = rs.getString("ingredient_name");
                int quantityWarning = rs.getInt("quantity_warning");
                int currentQuantity = rs.getInt("quantity");
                String unit = rs.getString("unit");
                Date expDate = rs.getDate("exp_date");
                String storageLocation = rs.getString("storage_location");
    
                IngredientDetails ingredientDetails = new IngredientDetails(ingredientName, quantityWarning, currentQuantity, unit, expDate, storageLocation);
                ingredientInventory.put(ingredientName, ingredientDetails);
            }
        } catch (SQLException e) {
            //JOptionPane.showMessageDialog(null, "Error populating all ingredients");
            e.printStackTrace();
        }
    }

    /* 
    public void loadIngredientsFromDB() {
        String sqlQuery = "SELECT ingredient_name, quantity_before, quantity, unit, exp_date, storage_location FROM ingredients";
        List<String> columnNames = List.of("ingredient_name", "quantity_before", "quantity", "unit", "exp_date", "storage_location");
        List<List<String>> ingredientsData = dbHandler.fetchData(sqlQuery, columnNames);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (List<String> row : ingredientsData) {
            String name = row.get(0);
            int quantity = Integer.parseInt(row.get(2));
            int quantity_b = Integer.parseInt(row.get(1));
            String unit = row.get(3);
            Date expDate = null;
            try {
                expDate = dateFormat.parse(row.get(4)); // Attempt to parse the string into a Date
            } catch (ParseException e) {
                System.err.println("Error parsing the date: " + row.get(4));
                e.printStackTrace();
            }
            String storageLocation = row.get(5);

            IngredientDetails ingredient = new IngredientDetails(name, quantity_b, quantity, unit, expDate, storageLocation);
            ingredientInventory.put(name, ingredient);
            currentIngredients.add(ingredient);
        }
        
    }
    */

    /**
     * Close the database connection
     */
    public static void close()
    {
        dbHandler.closeConnection();
    }

    /**
     * This function adds new ingredient to the inventory
     * @param name Ingredient Name (String)
     * @param quantity_before Warning Quantity (int)
     * @param quantity - Quantity (int)
     * @param unit - Unit (String)
     * @param expDate Expiration Date (Date)
     * @param storageLocation Storage Location (String)
     */
    public void addIngredient(String name, int quantity_before, int quantity, String unit, Date expDate, String storageLocation) {
        if (!ingredientInventory.containsKey(name)) {
            IngredientDetails newIngredient = new IngredientDetails(name, quantity_before, quantity, unit, expDate, storageLocation);
            ingredientInventory.put(name, newIngredient);
            currentIngredients.add(newIngredient);

            String sql = "INSERT INTO ingredients (ingredient_name, quantity_warning, quantity, unit, exp_date, storage_location) VALUES(\'" + name + "\', " + quantity_before + ", " + quantity +  ", \'" + unit + "\', " + "\'" + expDate + "\', \'" + storageLocation + "\');";
            System.out.println(sql);
            dbHandler.executeSQLCommand(sql);
        } else {
            System.out.println("Ingredient already exists. Use updateIngredient to modify it.");
        }
    }

    /**
     * Updates existing ingredient in the inventory
     * @param name Ingredient Name (String)
     * @param quantity_before Warning Quantity (int)
     * @param quantity - Quantity (int)
     * @param unit - Unit (String)
     * @param expDate Expiration Date (Date)
     * @param storageLocation Storage Location (String)
     */
    public void updateIngredient(String name, int quantity_before, int quantity, String unit, Date expDate, String storageLocation) {
        if (ingredientInventory.containsKey(name)) {
            IngredientDetails existingIngredient = ingredientInventory.get(name);
            existingIngredient.setQuantity(quantity);
            existingIngredient.setQuantityBefore(quantity_before);
            existingIngredient.setUnit(unit);
            existingIngredient.setExpDate(expDate);
            existingIngredient.setStorageLocation(storageLocation);

            String sql = "UPDATE ingredients SET quantity_warning = " + quantity_before + ", quantity = " + quantity + ", unit = \'" + unit + "\', exp_date = \'" + expDate + "\', storage_location = \'" + storageLocation + "\' WHERE ingredient_name = \'" + name + "\';";
            System.out.println(sql);
            dbHandler.executeSQLCommand(sql);
        } else {
            System.out.println("Ingredient does not exist. Use addIngredient to add it first.");
        }
    }

    // // Adds a menu item with its required ingredients and quantities
    // public void addMenuItem(String menuItemName, HashMap<String, Integer> ingredients) {
    //     menuIngredients.put(menuItemName, ingredients);
    // }

    // Example getters for accessing the managed data

    /**
     * Return inventory in hashmap that maps ingredient name (string) and ingredientDetails that contains information about each ingredient
     * @return HashMap of string and Ingredient Details (HashMap(String, IngredientDetails))
     */
    public HashMap<String, IngredientDetails> getIngredientInventory() {
        return ingredientInventory;
    }

    // public HashMap<String, HashMap<String, Integer>> getMenuIngredients() {
    //     return menuIngredients;
    // }

    /**
     * This function returns all the current ingredients in the inventory
     * @return vector of ingredient details (Vector(IngredientDetails))
     */
    public Vector<IngredientDetails> getCurrentIngredients() {
        return currentIngredients;
    }

    /**
     * This is the main function for system testing
     * @param args Command Line args (String[])
     */
    public static void main(String[] args)
    {

    }


    
}