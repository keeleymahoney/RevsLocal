package project2;
import java.util.HashMap;
/**
 * 
 * Class to track and submit a Supplier Order locally and in database
 * @author Brandon Cisneros
 * Class to track and submit a Supplier Order locally and in database
 */
public class SupplierOrder {
    /**
     * Order Number (int)
     */
    public int orderNumber;
    /**
     * Order Time (String)
     */
    public String time;
    /**
     * Order Date (String)
     */
    public String date;
    /**
     * Order Cost (double)
     */
    public double totalCost;
    /**
     * Supplier (String)
     */
    public String supplier;
    /**
     * Map of Ingredient Name to SupplierOrderDetails object (HashMap(String, SupplierOrderDetails))
     */
    public HashMap<String, SupplierOrderDetails> details;
    /**
     * Class to track specific food items and other Supplier Order Details
     * @author Brandon Cisneros
     */
    public class SupplierOrderDetails {
        /**
         * Order Number (int)
         */
        public int orderNumber;
        /**
         * Ingredient Name (String)
         */
        public String ingredient;
        /**
         * Ingredient Quantity Ordereed (int)
         */
        public int quantityOrdered;
        /**
         * Ingredient Quantity Received (int)
         */
        public int quantityReceived;
        /**
         * Was order received? (boolean)
         */
        public boolean received;
        /**
         * Ingredient Unit Price (double)
         */
        public double unitPrice;
        
        /**
         * Constructs SupplierOrderDetails subclass
         * @param orderNumberIn Order Number (int)
         * @param ingredientIn Ingredient Name (String)
         * @param quantityOrderedIn Quantity Ordered (int)
         * @param quantityReceivedIn Quantity Received (int)
         * @param receivedIn Received or Not (boolean)
         * @param unitPriceIn Unit Price of Ingredient (double)
         */
        SupplierOrderDetails(int orderNumberIn, String ingredientIn, int quantityOrderedIn, int quantityReceivedIn, boolean receivedIn, double unitPriceIn) {
            orderNumber = orderNumberIn;
            ingredient = ingredientIn;
            quantityOrdered = quantityOrderedIn;
            quantityReceived = quantityReceivedIn;
            received = receivedIn;
            unitPrice = unitPriceIn;
        }
    }

    /**
     * Constructs Supplier Order Class based on entered params
     * @param orderNumberIn Order Number (int)
     * @param timeIn Time (String)
     * @param dateIn Date (String)
     * @param supplierIn Supplier (String)
     * @param costIn Cost (double)
     */
    public SupplierOrder(int orderNumberIn, String timeIn, String dateIn, String supplierIn, double costIn) {
        orderNumber = orderNumberIn;
        time = timeIn;
        date = dateIn;
        totalCost = costIn;
        supplier = supplierIn;
        details = new HashMap<String, SupplierOrderDetails>();
    }  
    
    /**
     * Default Constructor for Supplier Order
     */
    public SupplierOrder()
    {
        orderNumber = 0;
        time = "";
        date = "";
        totalCost = 0;
        supplier = "";
        details = new HashMap<String, SupplierOrderDetails>();
    }

    /**
     * Calculates and sets total price of Supplier Order
     */
    public void calculateTotalPrice () {
        double calcCost = 0.00;
        for ( String key : details.keySet() ) {
            if (details.get(key).received) {
                calcCost += details.get(key).quantityReceived * details.get(key).unitPrice;
            }
        }
        totalCost = calcCost;
    }

    /**
     * Adds ingredient to Supplier Order
     * @param ingredientIn Ingredient Name (String)
     * @param quantityOrderedIn Quantity Ordered (int)
     * @param quantityReceivedIn Quantity Received (int)
     * @param receivedIn Received or Not (boolean)
     * @param unitPriceIn Unit Price of Ingredient (double)
     */
    public void addIngredient (String ingredientIn, int quantityOrderedIn, int quantityReceivedIn, boolean receivedIn, double unitPriceIn) {
        SupplierOrderDetails newDetail = new SupplierOrderDetails(orderNumber, ingredientIn, quantityOrderedIn, quantityReceivedIn, receivedIn, unitPriceIn);
        details.put(ingredientIn, newDetail);
    }

    /**
     * Removes ingredient from supplier order
     * @author Keeley Mahoney
     * @param ingredientIn Ingredient Name (String)
     */
    public void RemoveIngredient (String ingredientIn) {
        details.remove(ingredientIn);
    }

    /**
     * Submits Supplier Order to PostgreSQL database
     */
    public void submitSupplierOrder () {
        // Create DatabaseHandler
        DatabaseHandler db = new DatabaseHandler();
        String orderSQL = "INSERT INTO Supplier_Orders (order_number, order_date, order_time, cost, supplier) VALUES ('" + orderNumber + "', '" + date + "', '" + time + "', '" + totalCost + "', '" + supplier + "');";
        // Execute SQL Command
        db.executeSQLCommand(orderSQL);
        // System.out.println(orderSQL);

        StringBuilder supplierOrderDetailsSQL = new StringBuilder();
        StringBuilder ingredientsSQL = new StringBuilder();

        
        // supplierOrderDetailsSQL
        // INSERT INTO Supplier_Order_Details(Order_Number, Ingredient, Quantity_Ordered, Quantity_Received, Received, Wholesale_Unit_Price) VALUES (2837461, 'Tomatoes', 100, 100, true, .25),
        supplierOrderDetailsSQL.append("INSERT INTO Supplier_Order_Details(Order_Number, Ingredient, Quantity_Ordered, Quantity_Received, Received, Wholesale_Unit_Price) VALUES ");
        for ( String key : details.keySet() ) {
            supplierOrderDetailsSQL.append("('"+ orderNumber + "',  '" + details.get(key).ingredient + "', '"  + details.get(key).quantityOrdered +  "', '"  + details.get(key).quantityReceived +  "', '"  + details.get(key).received +  "', '"  + details.get(key).unitPrice + "'), ");
        }
        supplierOrderDetailsSQL.deleteCharAt(supplierOrderDetailsSQL.length() - 1);
        supplierOrderDetailsSQL.deleteCharAt(supplierOrderDetailsSQL.length() - 1);
        supplierOrderDetailsSQL.append(";");
        // Execute SQL Command
        db.executeSQLCommand(supplierOrderDetailsSQL.toString());
        // System.out.println(supplierOrderDetailsSQL);


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
        for ( String key : details.keySet() ) {
            if (details.get(key).received) {
                ingredientsSQL.append("WHEN ingredient_name = '"+ details.get(key).ingredient + "' THEN quantity + " + Integer.toString(details.get(key).quantityReceived) + " ");
            }
        }
        ingredientsSQL.append("END WHERE ingredient_name IN (");
        for ( String key : details.keySet() ) {
            if (details.get(key).received) {
            ingredientsSQL.append("'" + details.get(key).ingredient + "', ");
            }
        }
        ingredientsSQL.deleteCharAt(ingredientsSQL.length() - 1);
        ingredientsSQL.deleteCharAt(ingredientsSQL.length() - 1);
        ingredientsSQL.append(");");
        // Execute SQL Command
        db.executeSQLCommand(ingredientsSQL.toString());
        // System.out.println(ingredientsSQL);

        db.closeConnection();
    }


    // Test Main
    // public static void main (String[] args) {
    //     SupplierOrder myOrder = new SupplierOrder(777, "01:02:03", "2024-03-02", "Los Pollos Hermanos");
    //     myOrder.addIngredient ("Milk", 700, 611, true, .01);
    //     myOrder.addIngredient ("Onions", 200, 301, true, .05);
    //     myOrder.addIngredient ("Ranch", 700, 611, false, .01);
        

    //     // Always do this before submitting
    //     myOrder.calculateTotalPrice();
    //     myOrder.submitSupplierOrder();
    // }

}