package project2;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.sql.*;
import java.util.HashMap;
import java.util.Date;


/**
 * Class to retrieve database information during application initialization
 * @author Vittoria Cicalese
 */
public class Menu_Item_Populate {
    // ArrayList<String> entreesMenu = new ArrayList<Str3ing>();
    // ArrayList<String> sidesMenu = new ArrayList<String>();
    // ArrayList<String> beveragesMenu = new ArrayList<String>();
    // ArrayList<String> dessertsMenu = new ArrayList<String>();

    // HashMap<String, Double> menu_items_price = new HashMap<>();
    // HashMap<String, String> menu_items_category = new HashMap<>();
    // HashMap<String, HashMap<String, Integer>> menu_ingredients_quantity = new HashMap<>();



    ArrayList<Menu_Item> entreesMenu = new ArrayList<>();
    ArrayList<Menu_Item> sidesMenu = new ArrayList<>();
    ArrayList<Menu_Item> beveragesMenu = new ArrayList<>();
    ArrayList<Menu_Item> dessertsMenu = new ArrayList<>();
    ArrayList<Menu_Item> seasonalMenu = new ArrayList<>();


    DatabaseHandler db = new DatabaseHandler();
    HashMap<String, Menu_Item> menuItems = new HashMap<>();
    HashMap<String, IngredientDetails> allIngredients = new HashMap<>();
    HashMap<Integer, SupplierOrder> allSupplierOrders = new HashMap<>();

    /**
     * Default Constructor
     */
    public Menu_Item_Populate() {}

    /**
     * Function populates internal supplier order data objects with information from PostgreSQL
     */
    public void populateSupplierOrders () {
        String query = "SELECT * FROM supplier_orders;";
        try (Statement statement = db.getConn().createStatement();
        ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                int supOrderNum = rs.getInt("order_number");
                String supDate = rs.getString("order_date");
                String supTime = rs.getString("order_time");
                Double supCost = rs.getDouble("cost");
                String supSuplier = rs.getString("supplier");
                SupplierOrder supOrder = new SupplierOrder(supOrderNum, supTime, supDate, supSuplier, supCost);
                allSupplierOrders.put(supOrderNum, supOrder);             
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        populateSupplierOrderDetails();
    }

    /**
     * Function populates internal supplier order details data objects with information from PostgreSQL
     */
    public void populateSupplierOrderDetails() {
        for ( int key : allSupplierOrders.keySet() ) {
            String query = "SELECT * FROM supplier_order_details WHERE order_number = " + Integer.toString(key) + ";";
            try (Statement statement = db.getConn().createStatement();
            ResultSet rs = statement.executeQuery(query)) {
                while (rs.next()) {
                    int supOrderNum = rs.getInt("order_number");
                    String ingredient = rs.getString("ingredient");
                    int quantOrdered = rs.getInt("quantity_ordered");
                    int quantRcvd = rs.getInt("quantity_received");
                    boolean rcvd = rs.getBoolean("received");
                    double wholesale = rs.getDouble("wholesale_unit_price");
                    allSupplierOrders.get(supOrderNum).addIngredient(ingredient, quantOrdered, quantRcvd, rcvd, wholesale);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    
        }
    }


    /**
     * Function populates internal menu items data objects with information from PostgreSQL
     */
    public void populateMenuItems() {
        String sqlQuery = "SELECT * FROM menu_items";
        try (Statement statement = db.getConn().createStatement();
            ResultSet rs = statement.executeQuery(sqlQuery)) {
            while (rs.next()) {
                String itemName = rs.getString("item_name");
                double price = rs.getDouble("price");
                String category = rs.getString("menu_category");

                Menu_Item menuItem = new Menu_Item(itemName, price, category);

                HashMap<IngredientDetails, Integer> ingredientQuantities = getItemIngredientQuantities(itemName);

                menuItem.setIngredients(ingredientQuantities);
                menuItems.put(itemName, menuItem);
                categorizeMenuItem(menuItem, category);
                
            }
        } catch (SQLException e) {
            // JOptionPane.showMessageDialog(null, "Error populating menu items");
            e.printStackTrace();
        }
    }

    /**
     * Function populates internal data objects with quantities of a particualr menu item
     * @param menuItemName Menu Item Name (String)
     * @return Map of Ingredient Details Object to Quantity Integer (HashMap(IngredientDetails, Integer))
     */
    public HashMap<IngredientDetails, Integer> getItemIngredientQuantities(String menuItemName) {
        HashMap<IngredientDetails, Integer> ingredientsDetailsWithQuantity = new HashMap<>();
        // is mi/i notation to avoid duplicate var name?
        String sql = "SELECT mi.ingredient_name, mi.quantity AS quantityUsed, i.quantity_warning, i.quantity, i.unit, i.exp_date, i.storage_location " +
                     "FROM menu_item_ingredients mi " +
                     "JOIN Ingredients i ON mi.ingredient_name = i.ingredient_name " +
                     "WHERE mi.menu_item_name = ?";
                     
        try (PreparedStatement pstmt = db.getConn().prepareStatement(sql)) {
            pstmt.setString(1, menuItemName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String ingredientName = rs.getString("ingredient_name");
                int quantityUsed = rs.getInt("quantityUsed");
                int quantityWarning = rs.getInt("quantity_warning");
                int currentQuantity = rs.getInt("quantity");
                String unit = rs.getString("unit");
                Date expDate = rs.getDate("exp_date");
                String storageLocation = rs.getString("storage_location");
                
                IngredientDetails ingredientDetails = new IngredientDetails(ingredientName, quantityWarning, currentQuantity, unit, expDate, storageLocation);
                ingredientsDetailsWithQuantity.put(ingredientDetails, quantityUsed);
            }
        } catch (SQLException e) {
            // JOptionPane.showMessageDialog(null, "Error getting menu item ingredient details");
            e.printStackTrace();
        }
        return ingredientsDetailsWithQuantity;
    }

    /**
     * Deletes a menu item item in the PostgreSQL database
     * @param name Menu Item Name (String)
     */
    public void deleteMenuItemInDatabase(String name)
    {
        String sql = "DELETE FROM Menu_Items where item_name = \'" + name + "\';";
        // System.out.print(sql);
        db.executeSQLCommand(sql);
    }
    

    /**
     * Updates a particular menu item's attributes in the PostgreSQL database
     * @param price Price of the Menu Item (double)
     * @param category Item Category (String)
     * @param oldName Old Item Name (String)
     * @param newName New Item Name (String)
     * @param ingredients_menu_item Map of associated Ingredient Details and Quantity Int (HashMap(IngredientDetails, Integer)) 
     */
    public void updateMenuItem(Double price, String category, String oldName, String newName, HashMap<IngredientDetails, Integer> ingredients_menu_item) {
        if (menuItems.containsKey(newName) && !(oldName.equals("BLANK"))){
            //System.out.println("Menu item already exists.");
            Menu_Item existingItem = getMenuItem(oldName);
            existingItem.setPrice(price);
            existingItem.setCategory(category);
            menuItems.put(oldName, existingItem);
            updateItemIngredients(oldName, ingredients_menu_item);

            String sql = "UPDATE Menu_Items SET price = " + price + ", menu_category = \'" + category + "\' WHERE item_name = \'" + oldName + "\';";
            //System.out.println(sql);
            db.executeSQLCommand(sql);
        } 
        else if (menuItems.containsKey(newName)){
            Menu_Item existingItemBLANK = getMenuItem(oldName);
            existingItemBLANK.setPrice(-2);
            String sql1 = "UPDATE Menu_Items SET price = -2.0 WHERE item_name = \'" + oldName+ "\';";



            Menu_Item existingItem = getMenuItem(newName);
            existingItem.setPrice(price);
            existingItem.setCategory(category);
            existingItem.setIngredients(ingredients_menu_item);
            menuItems.put(newName, existingItem);

            Set<IngredientDetails> details = ingredients_menu_item.keySet();
                ArrayList<IngredientDetails> var = new ArrayList<>(details) ;
                for (IngredientDetails i : var){
                    int quant = ingredients_menu_item.get(i);

                    AddItemIngredients(newName, i, quant);
                }

            String sql2 = "UPDATE Menu_Items SET price = " + price + ", menu_category = \'" + category + "\' WHERE item_name = \'" + newName+ "\';";
            db.executeSQLCommand(sql1);
            db.executeSQLCommand(sql2);

        }
        else {
            //System.out.println("Menu item does not exist. Use addMenuItem to add it first.");
            if (oldName != newName){
                addMenuItem(newName, price, category, ingredients_menu_item );
                Menu_Item old = menuItems.get(oldName);

                Set<IngredientDetails> details = ingredients_menu_item.keySet();
                ArrayList<IngredientDetails> var = new ArrayList<>(details) ;
                for (IngredientDetails i : var){
                    int quant = ingredients_menu_item.get(i);

                    AddItemIngredients(newName, i, quant);
                }
                updateMenuItem(-2.0, category, oldName, oldName, ingredients_menu_item);
            }
        }

    }

    /**
     * Updates the ingredients in a particular menu item
     * @param name Menu Item Name (String)
     * @param ingredients_menu_item Map of associated Ingredient Details and Quantity Int (HashMap(IngredientDetails, Integer)) 
     */
    public void updateItemIngredients(String name, HashMap<IngredientDetails, Integer> ingredients_menu_item) {
        if (menuItems.containsKey(name)){
           
            if(ingredients_menu_item != null)
            {
                Set<IngredientDetails> details = ingredients_menu_item.keySet();
                ArrayList<IngredientDetails> var = new ArrayList<>(details) ;

                for (IngredientDetails i : var){
                    int quant = ingredients_menu_item.get(i);
                    
                    String sql = "UPDATE menu_item_ingredients SET quantity = " + quant + " WHERE menu_item_name = \'" + name + "\' AND ingredient_name = \'" + i.getName() + "\';";
                    //System.out.println(sql);
                    db.executeSQLCommand(sql);
                }

            }
            
            // cehck
        }

    }

    /**
     * Adds an ingredient to a menu item
     * @param name Name of Menu Item (String)
     * @param ingredient Ingredient (IngredientDetails)
     * @param quantity Quantity (int)
     */
    public void AddItemIngredients(String name, IngredientDetails ingredient, int quantity) {
        if (menuItems.containsKey(name)){
             
            String sql = "INSERT INTO menu_item_ingredients (menu_item_name, ingredient_name, quantity) VALUES (\'" + name + "\', \'" + ingredient.getName() + "\', " + quantity  + ");";
            // System.out.println(sql);
            db.executeSQLCommand(sql);
        }
            // cehck
    }

    

    /**
     * Adds a particular menu item to the database
     * @param itemName Menu Item Name (String)
     * @param price Menu Item Price (double)
     * @param menuCategory Menu Item Category (String)
     * @param ingredients_menu_item Map of associated Ingredient Details and Quantity Int (HashMap(IngredientDetails, Integer))
     */
    public void addMenuItem(String itemName, double price, String menuCategory, HashMap<IngredientDetails, Integer> ingredients_menu_item ) {

        Menu_Item newItem = new Menu_Item(itemName, price, menuCategory);
        newItem.setIngredients(ingredients_menu_item);
        menuItems.put(newItem.getItemName(), newItem );
        categorizeMenuItem(newItem, menuCategory);

        String sql = "INSERT INTO Menu_Items (item_name, price, menu_category) VALUES(\'" + itemName + "\', " + price + ", \'"+ menuCategory + "\');";
        // System.out.print(sql);
        updateItemIngredients(newItem.getItemName(), ingredients_menu_item);
        //System.out.println(sql);
        db.executeSQLCommand(sql);

    }

    /**
     * Deletes a menu item from the database
     * @param item Menu Item (Menu_Item Object)
     * @param ingredients_menu_item Map of associated Ingredient Details and Quantity Int (HashMap(IngredientDetails, Integer))
     */
    public void deleteMenuItem(Menu_Item item, HashMap<IngredientDetails, Integer> ingredients_menu_item) {
        this.updateMenuItem(-2.0, item.getCategory(), item.getItemName(), item.getItemName(), ingredients_menu_item);
    }


    /**
     * Gets for Menu_Item object based off name
     * @param itemName Menu Item Name (String)
     * @return Menu Item (Menu_Item Object)
     */
    public Menu_Item getMenuItem(String itemName) {
        return menuItems.get(itemName);
    }

    /**
     * Gets map between item name and Menu_Item object
     * @return Map between Menu Item Names and Menu_Item objects (HashMap(String, Menu_Item))
     */
    public HashMap<String, Menu_Item> getMenuItems() {
        return menuItems;
    }

    /**
     * Populates ingredient list from database needed for application initialization
     */
    public void populateAllIngredients() {
        String sql = "SELECT * FROM Ingredients";
        allIngredients = new HashMap<>();
    
        try (Statement stmt = db.getConn().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String ingredientName = rs.getString("ingredient_name");
                int quantityWarning = rs.getInt("quantity_warning");
                int currentQuantity = rs.getInt("quantity");
                String unit = rs.getString("unit");
                Date expDate = rs.getDate("exp_date");
                String storageLocation = rs.getString("storage_location");
    
                IngredientDetails ingredientDetails = new IngredientDetails(ingredientName, quantityWarning, currentQuantity, unit, expDate, storageLocation);
                allIngredients.put(ingredientName, ingredientDetails);
            }
        } catch (SQLException e) {

            // JOptionPane.showMessageDialog(null, "Error populating all ingredients");
            e.printStackTrace();
        }
    }

    /**
     * Gets a map between Ingredient Details and their quantities
     * @param menuItemName Name of Menu Item (String)
     * @return Map of associated Ingredient Details and Quantity Int (HashMap(IngredientDetails, Integer))
     */
    public HashMap<IngredientDetails, Integer> getMenuIngredientsWithQuantity(String menuItemName) {
        HashMap<IngredientDetails, Integer> ingredientsWithQuantity = new HashMap<>();
        String sql = "SELECT ingredient_name, quantity AS quantityUsed FROM menu_item_ingredients WHERE menu_item_name = ?";
        
        try (PreparedStatement pstmt = db.getConn().prepareStatement(sql)) {
            pstmt.setString(1, menuItemName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String ingredientName = rs.getString("ingredient_name");
                int quantityUsed = rs.getInt("quantityUsed");
                
                // Use the previously populated allIngredients map
                IngredientDetails ingredientDetails = allIngredients.get(ingredientName);
                if (ingredientDetails != null) {
                    ingredientsWithQuantity.put(ingredientDetails, quantityUsed);
                }
            }
        } catch (SQLException e) {

            // JOptionPane.showMessageDialog(null, "Error getting menu item ingredient details");

            e.printStackTrace();
        }
        return ingredientsWithQuantity;
    }
    
    
    /**
     * Puts menu items into their respective category listings
     * @param menuItem Menu Item (Menu_Item object)
     * @param category Menu Item Category (String)
     */
    public void categorizeMenuItem(Menu_Item menuItem, String category) {
        switch (category.toLowerCase()) {
            case "entree":
                entreesMenu.add(menuItem);
                break;
            case "side":
                sidesMenu.add(menuItem);
                break;
            case "beverage":
                beveragesMenu.add(menuItem);
                break;
            case "dessert":
                dessertsMenu.add(menuItem);
                break;
            case "seasonal":
                seasonalMenu.add(menuItem);
                break;
            default:
                // should there be edge case?
                break;
        }
    }
    /**
     * Function to get list of entree items
     * @return List of Entrees (ArrayList(Menu_Item))
     */
    public ArrayList<Menu_Item> getEntreesMenu()
    {
        return entreesMenu;
    }

    /**
     * Function to get list of Sides items
     * @return List of Sides (ArrayList(Menu_Item))
     */
    public ArrayList<Menu_Item> getSidesMenu()
    {
        return sidesMenu;
    }

    /**
     * Function to get list of Bevarages items
     * @return List of Beverages (ArrayList(Menu_Item))
     */
    public ArrayList<Menu_Item> getBeveragesMenu()
    {
        return beveragesMenu;
    }

     /**
     * Function to get list of Desserts items
     * @return List of Desserts (ArrayList(Menu_Item))
     */
    public ArrayList<Menu_Item> getDessertMenu()
    {
        return dessertsMenu;
    }

    /**
     * Function to get list of Seasonal items
     * @return List of Seasonal Items (ArrayList(Menu_Item))
     */
    public ArrayList<Menu_Item> getSeasonalMenu()
    {
        return seasonalMenu;
    }

    /**
     * Returns internal map of Ingredient Name to IngredientDetails
     * @return Map of Ingredient Name (String) to IngredientDetails
     */
    public HashMap<String, IngredientDetails> getIngredients()
    {
        return allIngredients;
    }

    /**
     * Prints all menu items and ingredients by category to console
     */
    public void printMenuWithIngredients() {
        System.out.println("Entrees:");
        printItems(entreesMenu);

        System.out.println("\nSides:");
        printItems(sidesMenu);
        System.out.println("\nBeverages:");
        printItems(beveragesMenu);

        System.out.println("\nDesserts:");
        printItems(dessertsMenu);

        System.out.println("\nSeasonal:");
        printItems(seasonalMenu);
    }

    /**
     * Prints menu items and ingredients
     * @param menuItems List of Menu Items (ArrayList(Menu_Items))
     */
    public void printItems(ArrayList<Menu_Item> menuItems) {
        for (Menu_Item item : menuItems) {
            System.out.println("\nItem: " + item.getItemName() + " - $" + item.getPrice());
            printIngredientDetails(item.getIngredientDetails());
        }
    }

    /**
     * Prints ingredient details
     * @param ingredients Map of Ingredient Details to Quantity (HashMap(IngredientDetails, Integer))
     */
    public void printIngredientDetails(HashMap<IngredientDetails, Integer> ingredients) {
        for (Map.Entry<IngredientDetails, Integer> entry : ingredients.entrySet()) {
            System.out.println("Ingredient Details: " + entry.getKey().toString() + ", Quantity in Item: " + entry.getValue());
        }
    }



    // public static void main(String[] args) {

    //     DatabaseHandler db = new DatabaseHandler();

    //     Menu_Item_Populate value = new Menu_Item_Populate();
    //     try {
    //         value.populateMenuItems();
    //         // value.printMenuItemsByCategory();
    //         value.printMenuWithIngredients(); 
    //     } finally {
    //         db.closeConnection();
    //     }
    // }


    /**
     * Customizes a menu item
     * @param menuItemName Menu Item Name (String)
     * @param ingredientName Ingredient Name (String)
     * @param quantityToAdd Quantity of Ingredient to Add (int)
     */
    public void customizeItem(String menuItemName, String ingredientName, int quantityToAdd) {
        Menu_Item menuItem = getMenuItem(menuItemName);
        if (menuItem == null) {
            System.out.println("Menu item not found.");
            return;
        }
    
        System.out.println("Original ingredients " + menuItem.getItemName() + ":");
        menuItem.getIngredientDetails().forEach((ingredient, quantity) -> {
            System.out.println(ingredient.getName() + ", Quantity: " + quantity);
        });
    
        IngredientDetails ingredientDetail = menuItem.findIngredientByName(ingredientName);
    
        if (ingredientDetail != null) {
            System.out.println("\n" + ingredientName + " already an ingredient of " + menuItem.getItemName() + " ,  updating quantity");
            menuItem.addIngredient(ingredientDetail, menuItem.getIngredientDetails().get(ingredientDetail) + quantityToAdd);
        } else if (allIngredients.containsKey(ingredientName)) {
            System.out.println("\n" + ingredientName + " is a global ingredient, modifying item " + menuItem.getItemName());
            ingredientDetail = allIngredients.get(ingredientName);
            menuItem.addIngredient(ingredientDetail, quantityToAdd); 
        } else {


            System.out.println("\n" + ingredientName + " is not in " + menuItem.getItemName() + " or global ingredients, add?");
        }

        System.out.println("\nCustomized ingredients - " + menuItem.getItemName() + ":");
        menuItem.getIngredientDetails().forEach((ingredient, quantity) -> {
            System.out.println(ingredient.getName() + ", Quantity: " + quantity);
        });
    }

    /**
     * Main Function for system operation
     * @param args Command line arguments (String[])
     */
    public static void main(String[] args) {
        // DatabaseHandler db = new DatabaseHandler();
        // Menu_Item_Populate menuPopulator = new Menu_Item_Populate();
        // IngredientDetails tomato = new IngredientDetails("Tomato", 100, 50, "pcs", new Date(), "Pantry");
        // IngredientDetails lettuce = new IngredientDetails("Lettuce", 50, 25, "pcs", new Date(), "Fridge");
    
        // HashMap<IngredientDetails, Integer> burgerIngredients = new HashMap<>();
        // burgerIngredients.put(tomato, 2); // Using 2 tomatoes
        // burgerIngredients.put(lettuce, 1); // Using 1 lettuce

        // // Test adding a new menu item
        // String newItemName = "Veggie Burger";
        // double price = 7.99;
        // String category = "Entree";
        
        // menuPopulator.addMenuItem(newItemName, price, category, burgerIngredients);

        // // Test updating an existing menu item
        // double newPrice = 8.49;
        // menuPopulator.updateMenuItem(newPrice, "Entree", newItemName, newItemName, burgerIngredients);

        // // Test updating ingredient quantities for an existing menu item
        // burgerIngredients.put(tomato, 3); // Now using 3 tomatoes
        // menuPopulator.updateItemIngredients(newItemName, burgerIngredients);

        // // Test deleting a menu item
        // Menu_Item itemToDelete = menuPopulator.getMenuItem(newItemName);
        // if (itemToDelete != null) {
        //     menuPopulator.deleteMenuItem(itemToDelete, burgerIngredients);
        // }
    }

}