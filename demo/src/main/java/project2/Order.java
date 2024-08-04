package project2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
/**
 * Class for tracking food items and metadata associated with a particular Order
 * @author Brandon Cisneros
 */
public class Order {
    /**
     * Integer that is the order number for the customer order
     */
    public int orderNumber;

    /**
     * Time of the order represented as a string
     */
    public String time;

    /**
     * Date of the order represented as a string
     */
    public String date;

    /**
     * Total cost of the order represented as a double
     */
    public double totalCost;

    // Date, time, to be generated on front end
    /**
     * Constructs Order class
     * @param orderNumberIn Order Number (int)
     * @param timeIn Time (String)
     * @param dateIn Date (String)
     */
    public Order(int orderNumberIn, String timeIn, String dateIn) {
        orderNumber = orderNumberIn;
        time = timeIn;
        date = dateIn;
        totalCost = 0;
        menuItems = new HashMap<String, Integer>();
        customizations = new HashMap<String, Integer>();

    }

    /**
     * Hash map of all of the menu items in the customer order and its quanaity
     */
    public HashMap<String, Integer> menuItems;

    /**
     * Hash map of the customizations that can happen to a specific ingredient in a menu item for a customer order
     */
    public HashMap<String, Integer> customizations;


    // public void calculateTotalPrice (HashMap<String, Double> menu_items_price) {
    //     Double calcCost = 0.00;
    //     for ( String key : menuItems.keySet() ) {
    //         calcCost += menuItems.get(key) * menu_items_price.get(key);
    //     }
    //     totalCost = calcCost;
    // }
    
    /**
     * Sets the total price of the order
     * @param calcCost Calculated Cost to set as Order Price (Double)
    */
    public void setTotalPrice (Double calcCost) {
        totalCost = calcCost;
    }
    
    /**
     * Adds a given Menu Item to the order
     * @param item Added Menu Item Name (String)
     * @param quantity Quantity of Added Menu Item (int)
     */
    public void addMenuItem (String item, int quantity) {
        if (menuItems.get(item) == null) {
            menuItems.put(item, 0);
        }
        menuItems.put(item, menuItems.get(item) + quantity);

    }
    
    /**
     * Adds a given ingredient customization to the Order
     * @param ingredient Added Customization Name (String)
     * @param quantity Quantity of Customization (int)
     */
    public void addCustomization (String ingredient, int quantity) {
        if (customizations.get(ingredient) == null) {
            customizations.put(ingredient, 0);
        }
        customizations.put(ingredient, customizations.get(ingredient) + quantity);
    }

    /**
     * Sets the next order number according to the PostgreSQL databse
     */
    public void setOrderNumber () {
        String query = "SELECT MAX(order_number) from Customer_Orders;";
        DatabaseHandler db = new DatabaseHandler();
        try (Statement statement = db.getConn().createStatement();
        ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                int orderNum = rs.getInt("max") + 1;
                orderNumber = orderNum;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Submits Order to Database, reducing inventory and recording order details
     * @param menu_item_ingredients Record of Ingredient Count per Menu Item (HashMap of string and (HashMap of String and Integer))
     */
    public void submitOrder (HashMap<String, HashMap<String, Integer>> menu_item_ingredients) {
        // Create DatabaseHandler
        DatabaseHandler db = new DatabaseHandler();
        String orderSQL = "INSERT INTO Customer_Orders (order_number, total_cost, order_time, order_date) VALUES ('" + orderNumber + "', '" + totalCost + "', '" + time + "', '" + date + "');";
        // Execute SQL Command
        db.executeSQLCommand(orderSQL);
        // System.out.println(orderSQL);

        StringBuilder customerOrderDetailsSQL = new StringBuilder();
        StringBuilder ingredientsSQL = new StringBuilder();
        StringBuilder defaultIngredientsSQL = new StringBuilder();

        
        // customerOrderDetailsSQL
        // INSERT INTO Customer_Order_Details (menu_item, order_number, quantity) VALUES ('Aggie Chicken Club', '1', '1'),
        customerOrderDetailsSQL.append("INSERT INTO Customer_Order_Details (menu_item, order_number, quantity) VALUES ");
        for ( String key : menuItems.keySet() ) {
            customerOrderDetailsSQL.append("('"+ key + "',  '" + Integer.toString(orderNumber) + "', '"  + Integer.toString(menuItems.get(key)) + "'), ");
        }
        customerOrderDetailsSQL.deleteCharAt(customerOrderDetailsSQL.length() - 1);
        customerOrderDetailsSQL.deleteCharAt(customerOrderDetailsSQL.length() - 1);
        customerOrderDetailsSQL.append(";");
        // Execute SQL Command
        db.executeSQLCommand(customerOrderDetailsSQL.toString());
        // System.out.println(customerOrderDetailsSQL);


        // Customizations SQL Command
        // UPDATE config
        // SET quantity = 
        //     CASE 
        //         WHEN ingredient_name = 'NEWNAME' THEN quantity + 1
        //         WHEN ingredient_name = 'NEWNAMEX' THEN quantity + 2
        //         ELSE quantity
        //     END
        // WHERE ingredient_name IN ('NEWNAME', 'NEWNAMEX');
        ingredientsSQL.append("UPDATE Ingredients SET quantity = CASE ");
        for ( String key : customizations.keySet() ) {
            ingredientsSQL.append("WHEN ingredient_name = '"+ key + "' THEN quantity - " + Integer.toString(customizations.get(key)) + " ");
        }
        ingredientsSQL.append("END WHERE ingredient_name IN (");
        for ( String key : customizations.keySet() ) {
            ingredientsSQL.append("'" + key + "', ");
        }
        ingredientsSQL.deleteCharAt(ingredientsSQL.length() - 1);
        ingredientsSQL.deleteCharAt(ingredientsSQL.length() - 1);
        ingredientsSQL.append(");");
        // Execute SQL Command
        db.executeSQLCommand(ingredientsSQL.toString());
        // System.out.println(ingredientsSQL);

        // Default Ingredients SQL Command
        // Have:
        // MenuItemsOrdered -> QuantityOrdered
        // MenuItems->Ingredients->Quanity
        // Want:
        // IngredientsOrdered->QuantityOrdered
        // Fill in with initialized data        
        HashMap<String, Integer>defaultIngredients = new HashMap<String, Integer>();

        for ( String key1 : menu_item_ingredients.keySet() ) {
            if (menuItems.containsKey(key1)) {
                for ( String key2 : menu_item_ingredients.get(key1).keySet() ) {
                    if (menu_item_ingredients.get(key1).get(key2) >= 0) {
                        if (defaultIngredients.get(key2) == null) {
                            defaultIngredients.put(key2, 0);
                        }
                        defaultIngredients.put(key2, defaultIngredients.get(key2) + (menuItems.get(key1))*(menu_item_ingredients.get(key1).get(key2)));
                    }
                }
            }
        }

        defaultIngredientsSQL.append("UPDATE Ingredients SET quantity = CASE ");
        for ( String key : defaultIngredients.keySet() ) {
            defaultIngredientsSQL.append("WHEN ingredient_name = '"+ key + "' THEN quantity - " + Integer.toString(defaultIngredients.get(key)) + " ");
        }
        defaultIngredientsSQL.append("END WHERE ingredient_name IN (");
        for ( String key : defaultIngredients.keySet() ) {
            defaultIngredientsSQL.append("'" + key + "', ");
        }
        defaultIngredientsSQL.deleteCharAt(defaultIngredientsSQL.length() - 1);
        defaultIngredientsSQL.deleteCharAt(defaultIngredientsSQL.length() - 1);
        defaultIngredientsSQL.append(");");
        // Execute SQL Command
        db.executeSQLCommand(defaultIngredientsSQL.toString());
        // System.out.println(defaultIngredientsSQL);
        db.closeConnection();
    }

    // Test Main
    // public static void main (String[] args) {
    //     Order myOrder = new Order(74435, "19:54:11", "2023-02-24");
    //     System.out.println(myOrder.orderNumber);
    //     myOrder.addCustomization ("Napkin", -1);
    //     myOrder.addCustomization ("Straws", 2);
    //     myOrder.addMenuItem("French Fries", 3);
    //     myOrder.addMenuItem("Chocolate Aggie Shake", 2);
    //     HashMap<String, HashMap<String, Integer>> menu_items_ingredients = new HashMap<String, HashMap<String, Integer>>();
    //     HashMap<String, Integer> lewIngs = new HashMap<String, Integer>();
    //     lewIngs.put("Fries", 1);
    //     lewIngs.put("Napkins", 2);
    //     lewIngs.put("Bowls - Entrees", 1);
    //     HashMap<String, Integer> kahIngs = new HashMap<String, Integer>();
    //     kahIngs.put("Ice Cream - Chocolate", 1);
    //     kahIngs.put("Milk", 1);
    //     kahIngs.put("Cups - Milkshake", 1);
    //     kahIngs.put("Napkins", 2);
    //     kahIngs.put("Straws", 2);
    //     menu_items_ingredients.put("French Fries", lewIngs);
    //     menu_items_ingredients.put("Chocolate Aggie Shake", kahIngs);

    //     // Always do this before submitting
    //     HashMap<String, Double> menuItemsPrice = new HashMap<String, Double>();
    //     menuItemsPrice.put("French Fries", 1.99);
    //     menuItemsPrice.put("Chocolate Aggie Shake", 4.49);
    //     myOrder.calculateTotalPrice(menuItemsPrice);
    //     myOrder.submitOrder(menu_items_ingredients);
    // }
}