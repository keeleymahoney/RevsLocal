package project2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import java.awt.Graphics;
import java.sql.PreparedStatement;

/**
 * Creates Trends into Hashmaps and Arrays
 * @author Alyan Tharani
 */
 public class Trends {
     private DatabaseHandler dbHandler;
 
    /** 
         * Initialize DatabaseHandler here for the rest of the class. Will be auto called up
         * 
     */
     public Trends() {
         this.dbHandler = new DatabaseHandler(); 
     }



/** 
 * Product Usage Chart:  Given a time window, display a chart (table, graph, diagram) that depicts the amount of inventory used during that time period.
 * @param startDate - Start Date (Date)
 * @param endDate - End Date (Date)
 * @return Map<String, Map<Date, Integer>> - Map with table output (HashMap)
 * @throws SQLException if an error in the database is thrown
 */
     //Product Usage - COMPLETE
     public Map<String, Map<Date, Integer>> getIngredientUsage(Date startDate, Date endDate) throws SQLException {
        /* 
        TESTING: SELECT i.ingredient_name, co.order_date, SUM(cod.quantity * mii.Quantity) AS TotalUsed FROM Customer_Order_Details cod JOIN Customer_Orders co ON cod.order_number = co.order_number JOIN Menu_Item_Ingredients mii ON cod.menu_item = mii.Menu_Item_Name JOIN Ingredients i ON mii.Ingredient_Name = i.ingredient_name WHERE co.order_date BETWEEN '11-14-2023' AND '11-28-2023' GROUP BY i.ingredient_name, co.order_date ORDER BY co.order_date
        Query Data Sources - Coustomer_Order_Details, Coustomer Orders, Menu_Item_Ingredients, Ingredients
        Calculation: Multiply quantity of each menu item ordered by quantity of each ingredient required for that item. Sum this up to recive output. 
        Filter - Filtered by the start and end dates. 
        Simplificaion/Clarity - Used Class Alias (JOIN) to simplify SQL Command
        */
        
        // Adjusted SQL query to focus on ingredients
        String query = "SELECT i.ingredient_name, co.order_date, SUM(cod.quantity * mii.Quantity) AS TotalUsed FROM Customer_Order_Details cod JOIN Customer_Orders co ON cod.order_number = co.order_number JOIN Menu_Item_Ingredients mii ON cod.menu_item = mii.Menu_Item_Name JOIN Ingredients i ON mii.Ingredient_Name = i.ingredient_name WHERE co.order_date BETWEEN ? AND ? GROUP BY i.ingredient_name, co.order_date ORDER BY co.order_date;";
        Map<String, Map<Date, Integer>> ingredientUsage = new HashMap<>();
        try (PreparedStatement statement = dbHandler.getConn().prepareStatement(query)) {
            statement.setDate(1, new java.sql.Date(startDate.getTime()));
            statement.setDate(2, new java.sql.Date(endDate.getTime()));
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    String ingredientName = rs.getString("ingredient_name");
                    Date orderDate = rs.getDate("order_date");
                    int totalUsed = rs.getInt("TotalUsed");
                    
                    ingredientUsage.computeIfAbsent(ingredientName, k -> new HashMap<>()).put(orderDate, totalUsed);
                }
            }
        }
        return ingredientUsage;
    }
    

    
    /** 
     * Sales Report: This function retrieves the sales details by menu items from customer orders within a specified time frame.
     * @param startDate - Start Date (Date)
     * @param endDate - End Date (Date)
     * @return Map<String, Integer> - Map with table output (HashMap)
     * @throws SQLException if an error in the database is thrown
     */
        public Map<String, Integer> getSalesReport(Date startDate, Date endDate) throws SQLException {
            /*
            TESTING: SELECT mi.Item_Name AS MenuItemName, SUM(cod.quantity) AS TotalUnitsSold, SUM(cod.quantity * mi.Price) AS TotalSalesAmount FROM Menu_Items mi JOIN  Customer_Order_Details cod ON mi.Item_Name = cod.menu_item JOIN  Customer_Orders co ON cod.order_number = co.order_number WHERE  co.order_date BETWEEN '11-14-2023' AND CURRENT_DATE GROUP BY mi.Item_Name;
            Query Data Sources - Menu_Items, Customer_Order_Details, Customer_Orders
            Calculation: Adds up the quantity of each menu item ordered (Total Units) - 
              multiply the quantity of each menu item ordered by its price, then sums these amounts (Total Sales Amount)
            Filter - Filtered by the start and end dates. 
            Simplificaion/Clarity - Used Class Alias (JOIN) to simplify SQL Command
            */
            String query = "SELECT mi.Item_Name AS MenuItemName, SUM(cod.quantity) AS TotalUnitsSold FROM Menu_Items mi JOIN Customer_Order_Details cod ON mi.Item_Name = cod.menu_item JOIN Customer_Orders co ON cod.order_number = co.order_number WHERE co.order_date BETWEEN ? AND ? GROUP BY mi.Item_Name;";
            Map<String, Integer> salesReport = new HashMap<>();
            try (PreparedStatement statement = dbHandler.getConn().prepareStatement(query)) {
                statement.setDate(1, new java.sql.Date(startDate.getTime()));
                statement.setDate(2, new java.sql.Date(endDate.getTime()));
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        String menuItem = rs.getString("MenuItemName");
                        int totalSold = rs.getInt("TotalUnitsSold");
                        //We are ignoring the total sales amount generated by query
                        salesReport.put(menuItem, totalSold);
                    }
                }
            }
            return salesReport;
        }
    
        
        /** xcess Report: Given a timestamp, display the list of items that only sold less than 10% of their inventory between the timestamp and the current time, assuming no restocks have happened during the window.
         * @param startDate - Start Date (Date)
         * @return Map<String, Map<String, Object>> - Map with table output (HashMap)
         * @throws SQLException if an error in the database is thrown
         */

        public Map<String, Map<String, Object>> getExcessReport(Date startDate) throws SQLException {
          /*
          Query Data Sources - supplier_order_details, supplier_orders, customer_order_details, menu_item_ingredients, customer_orders
          Calculation: 
            Used Two Common Table Expressions (CTE's)
              Supplied
                Sources - supplier_orders and supplier_order_details
                Objective - Calculates total quantities of ingredients received from suppliers.
                Calculation - Sums up quantity_received for each ingredient (Due to multiple orders before date)
                Filter - Order dates up to either CURRENT_DATE or @startDate
              Used
                Sources - customer_order_details, menu_item_ingredients, and customer_orders
                Objective - Calculates total quantities of ingredients used in customer orders.
                Calculation - Multiplies quantity of each menu_item ordered by the quantity of each ingredient required for that item and sums this up.
                Filter - Order dates between @startDate and CURRENT_DATE
            Combined CTE Tables 
              Final Calculation - Join both CTE's to calculate 10% minimum (Did not work - used code below to complete calculation)
          Filter - Filtered by the start and end dates. 
          Simplificaion/Clarity - Used Class Alias (JOIN) to simplify SQL Command and CTE's
          */
          String query = "WITH Supplied AS (SELECT sod.ingredient, SUM(sod.quantity_received) AS total_quantity_received " +
          "FROM supplier_order_details sod JOIN supplier_orders so ON sod.order_number = so.order_number " +
          "WHERE so.order_date <= ? AND so.order_date <= CURRENT_DATE GROUP BY sod.ingredient), Used AS ( " +
          "SELECT mii.Ingredient_Name AS ingredient_name, SUM(mii.Quantity * cod.quantity) AS total_quantity_used " +
          "FROM customer_order_details cod JOIN menu_item_ingredients mii ON cod.menu_item = mii.Menu_Item_Name " +
          "JOIN customer_orders co ON cod.order_number = co.order_number WHERE co.order_date >= ? AND co.order_date <= CURRENT_DATE  " +
          "GROUP BY mii.Ingredient_Name) " +
          "SELECT Supplied.ingredient, COALESCE(Supplied.total_quantity_received, 0) AS total_quantity_received, "  +
          "COALESCE(Used.total_quantity_used, 0) AS total_quantity_used,  " +
          "(COALESCE(Used.total_quantity_used, 0) >= (0.1 * COALESCE(Supplied.total_quantity_received, 0))) AS UsageWithin10Percent " +
          "FROM Supplied LEFT JOIN Used ON Supplied.ingredient = Used.ingredient_name ORDER BY Supplied.ingredient; ";
          Map<String, Map<String, Object>> excessReport = new HashMap<>();
      
          try (PreparedStatement statement = dbHandler.getConn().prepareStatement(query)) {
              statement.setDate(1, new java.sql.Date(startDate.getTime()));
              statement.setDate(2, new java.sql.Date(startDate.getTime()));
      
              try (ResultSet rs = statement.executeQuery()) {
                  while (rs.next()) {
                      String ingredientName = rs.getString("ingredient");
                      int totalQuantityReceived = rs.getInt("total_quantity_received");
                      int totalQuantityUsed = rs.getInt("total_quantity_used");
                      boolean usageWithin10Percent = false;
                      double percentage = ((double) totalQuantityUsed) / ((double) (totalQuantityReceived));
                      if(percentage <= .1)
                      {
                        usageWithin10Percent = true;
                      }
                      else
                      {
                        usageWithin10Percent = false;
                      }
      
                      Map<String, Object> details = new HashMap<>();
                      details.put("TotalQuantityReceived", totalQuantityReceived);
                      details.put("TotalQuantityUsed", totalQuantityUsed);
                      details.put("UsageWithin10Percent", usageWithin10Percent);
      
                      excessReport.put(ingredientName, details);
                  }
              }
          }
      return excessReport;
    }
      
        
    
    /** 
     * Restock Report: This function lists all ingredients that are below their minimum required amounts.
     * @return Map<String, Map<String, Integer>> - Map with table output (HashMap)
     * @throws SQLException if an error in the database is thrown
     */
    //NOTE - Do we need ot add a new column for minimum beofre calling error column
    public Map<String, Map<String, Integer>> getRestockReport() throws SQLException {
        /*
      TESTING: SELECT ingredient_name, quantity_warning, quantity FROM ingredients WHERE quantity <= quantity_warning;
      Query Data Sources - ingredients
      Calculation: If current quantity is lower than quantity_warning, then ingredient will be listed
      */
        String query = "SELECT ingredient_name, quantity_warning, quantity FROM ingredients WHERE quantity <= quantity_warning;";
        Map<String, Map<String, Integer>> restockMap = new HashMap<>();
        try (Statement statement = dbHandler.getConn().createStatement(); ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                String ingredientName = rs.getString("ingredient_name");
                int quantityWarning = rs.getInt("quantity_warning");
                int quantity = rs.getInt("quantity");
                Map<String, Integer> details = new HashMap<>();
                details.put("quantity_warning", quantityWarning);
                details.put("quantity", quantity);
                restockMap.put(ingredientName, details);
            }
        }
        return restockMap;
    }
    

    
    /** 
     * Double Food Order Report - What Sells Together (ordering trend report): Given a time window, display a list of pairs of menu items that sell together often, popular or not, sorted by most frequent.
     * @param startDate - Start Date (Date)
     * @param endDate - End Date (Date)
     * @return List<Map<String, Object>> - Map with table output (HashMap)
     * @throws SQLException if an error in the database is thrown
     */
    //Double Order
    public List<Map<String, Object>> getDoubleOrder(Date startDate, Date endDate) throws SQLException {
        /*
        TESTING: SELECT cod1.menu_item AS Item1, cod2.menu_item AS Item2, COUNT(*) AS Frequency FROM  Customer_Order_Details cod1 JOIN Customer_Order_Details cod2 ON cod1.order_number = cod2.order_number AND cod1.menu_item < cod2.menu_item JOIN Customer_Orders co ON cod1.order_number = co.order_number WHERE co.order_date BETWEEN '11-14-2023' AND '11-15-2023' GROUP BY cod1.menu_item, cod2.menu_item ORDER BY Frequency DESC;
        Query Data Sources - Customer_Order_Details, Customer_Orders
        Calculation - Join two Customer_Order_Details tables with one Customer_Orders table. Use the two Customer_Order_Details
          tables to find all unique pairs of menu_items using condiiton 'cod1.menu_item < cod2.menu_item'. Use WHERE clause to 
          specify pairs within peramater dates. Then, GROUP BY clause used to count instances of each order. THen use COUNT(*) 
          to count the rows within each group.
        Filter - Filtered by the start and end dates. 
        Simplificaion/Clarity - Used Class Alias (JOIN) to simplify SQL Command
        */        
        String query = "SELECT cod1.menu_item AS Item1, cod2.menu_item AS Item2, COUNT(*) AS Frequency FROM  Customer_Order_Details cod1 JOIN Customer_Order_Details cod2 ON cod1.order_number = cod2.order_number AND cod1.menu_item < cod2.menu_item JOIN Customer_Orders co ON cod1.order_number = co.order_number WHERE co.order_date BETWEEN ? AND ? GROUP BY cod1.menu_item, cod2.menu_item ORDER BY Frequency DESC;";
        List<Map<String, Object>> DoubleTrend = new ArrayList<>();
        try (PreparedStatement statement = dbHandler.getConn().prepareStatement(query)) {
            statement.setDate(1, new java.sql.Date(startDate.getTime()));
            statement.setDate(2, new java.sql.Date(endDate.getTime()));
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("Item1", rs.getString("Item1"));
                    row.put("Item2", rs.getString("Item2"));
                    row.put("Frequency", rs.getInt("Frequency"));
                    DoubleTrend.add(row);
                }
            }
        }
        return DoubleTrend;
    }





     // ---------------------------- TESTING ----------------------------


// ---------------------------- DISPLAY METHOD ----------------------------
// public static void displayReport(String title, List<Map<String, Object>> data) {
//         SwingUtilities.invokeLater(() -> {
//             JFrame frame = new JFrame(title);
//             frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//             frame.setSize(600, 400);

//             if (!data.isEmpty()) {
//                 Vector<String> columnNames = new Vector<>(data.get(0).keySet());
//                 Vector<Vector<Object>> rowData = new Vector<>();
                
//                 for (Map<String, Object> rowMap : data) {
//                     Vector<Object> row = new Vector<>();
//                     columnNames.forEach(col -> row.add(rowMap.get(col)));
//                     rowData.add(row);
//                 }

//                 JTable table = new JTable(rowData, columnNames);
//                 JScrollPane scrollPane = new JScrollPane(table);
//                 frame.add(scrollPane, BorderLayout.CENTER);
//             } else {
//                 frame.add(new JLabel("No data available"), BorderLayout.CENTER);
//             }

//             frame.setLocationRelativeTo(null);
//             frame.setVisible(true);
//         });
//     }


//      public Map<Integer, Integer> getCountOfOrdersByWeek() throws SQLException {
//         String query = "SELECT EXTRACT(WEEK FROM order_date) AS week_number, COUNT(order_number) AS total_orders_per_week FROM Customer_Orders GROUP BY EXTRACT(WEEK FROM order_date) ORDER BY week_number;";
//         Map<Integer, Integer> ordersByWeek = new HashMap<>();
//         //Statment alloes me to edit the SQL Query and do the things below to it.
//         try (Statement statement = dbHandler.getConn().createStatement(); ResultSet rs = statement.executeQuery(query)) {
//             while (rs.next()) {
//                 //If you look at the 'String query' you can see that there are tow '?' values which match uop 
//                 int weekNumber = rs.getInt("week_number");
//                 int totalOrders = rs.getInt("total_orders_per_week");
//                 ordersByWeek.put(weekNumber, totalOrders);
//             }
//         }
//         //returns a hashmap type
//         return ordersByWeek;
//     }

//     public static void main(String[] args) {
//         Trends trends = new Trends();
//         try {
//             Map<String, Integer> salesReportData = trends.getSalesReport(new Date(System.currentTimeMillis() - (10L * 24 * 60 * 60 * 1000)), new Date(System.currentTimeMillis()));
//             salesReportData.forEach((key, value) -> System.out.println(key + ": " + value));
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }
    
 }