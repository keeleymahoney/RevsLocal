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
import javafx.scene.Node;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.Priority;

import javafx.scene.control.TextFormatter;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import java.text.DecimalFormat;
import java.text.ParsePosition;

import javafx.util.converter.NumberStringConverter;


import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

/**
 * Class that loads the list of supplier orders that are currently in the database
 * 
 * @author Keeley Mahoney
 */
public class SupplierOrdersScreen {
    
    //new grid pane for storing each order neatly
    @FXML 
    GridPane grid = new GridPane();

    //new scroll pane to allow for people to scroll and see all orders
    @FXML
    ScrollPane scrollSupplierOrders = new ScrollPane();

    @FXML
    Button supplierButton;

    Menu_Item_Populate populator = new Menu_Item_Populate();

    //switch to inventory screen
    @FXML
    /**
     * Switches to the inventory screen
     * @throws IOException if the inventory screen is not loaded
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

    //switch to edit screen
    @FXML
    /**
     * Switches to the manager menu screen
     * @throws IOException if the manager menu screen is not loaded
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

    //switch to supplier order screen
    @FXML
    /**
     * Switches to the supplier order screen
     * @throws IOException if the supplier order screen is not loaded
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

    //switch to add supplier order screen
    @FXML
    /**
     * Switches to the add supplier order screen
     * @throws IOException if the add supplier order screen is not loaded
     */
    private void switchToAddSupplierOrder() throws IOException {
        try
        {
            App.setRoot("SupplierAdd");
        }
        catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        
    }

    @FXML
    /**
     * Switches to the trends screen
     * @throws IOException if the trends screen is not loaded
     */
    private void switchToTrends() throws IOException {
        App.setRoot("TrendsInitial");
        
    }

    @FXML
    /**
     * Switches to the employee screen
     * @throws IOException if the employee screen is not loaded
     */
    private void switchToEmployeeList() throws IOException {
        App.setRoot("employeeList");
    }

    /**
     * Default constructor for SupplierOrdersScreen()
     */
    public SupplierOrdersScreen()
    {

    }

    /**
     * Function is called when screen is loaded. It populates all of the supplier orders and displays them to show the order number, ingredient, quantity ordered, quantity received and wholesale unit price
     */
    public void initialize()
    {
        grid.getChildren().clear();
        scrollSupplierOrders.setContent(null);
        populator.populateSupplierOrders();
        supplierButton.setStyle("-fx-background-color: #500000;");
        supplierButton.setTextFill(javafx.scene.paint.Color.WHITE);
        //initailize row of grid
        int row = 0;

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.NEVER); // or set to Priority.ALWAYS, depending on your requirements
        grid.getRowConstraints().add(rowConstraints);

        //go through each ingredient in each supplier order
    
         
        for(int key: populator.allSupplierOrders.keySet())
        {
            SupplierOrder order = populator.allSupplierOrders.get(key);

            for(String ingredient: order.details.keySet())
            {
                //create new anchor pane for order and set its size
                AnchorPane supplierOrderPane = new AnchorPane();
                supplierOrderPane.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
                

                //create order number label and set its size
                Label orderNumber = new Label();
                orderNumber.setPrefSize(160, 50);
                orderNumber.setText(Integer.toString(order.orderNumber));

                //create provider label and set its size
                Label provider = new Label();
                provider.setPrefSize(160, 50);
                provider.setText(order.supplier);

                //create ingredient label and set its size
                Label ingredientLabel = new Label();
                ingredientLabel.setText(ingredient);
                ingredientLabel.setPrefSize(250, 50);

                //create quantity ordered label and set its size
                Label quantOrdered = new Label();
                quantOrdered.setText(Integer.toString(order.details.get(ingredient).quantityOrdered));
                quantOrdered.setPrefSize(70, 50);

                //create quantity receieved label and set its size
                Label quantReceieved = new Label();
                quantReceieved.setText(Integer.toString(order.details.get(ingredient).quantityReceived));
                quantReceieved.setPrefSize(160, 50);

                //create wholesale unit price label and set its size
                Label priceLabel = new Label();
                priceLabel.setText(Double.toString(order.details.get(ingredient).unitPrice));
                priceLabel.setPrefSize(160, 50);

                //add everything to anchor pane
                supplierOrderPane.getChildren().addAll(orderNumber, provider, ingredientLabel, quantOrdered, quantReceieved, priceLabel);

                //set location of each label in relation to the anchor pane
                supplierOrderPane.setLeftAnchor(orderNumber, 0.0);
                supplierOrderPane.setLeftAnchor(provider, 166.6);
                supplierOrderPane.setLeftAnchor(ingredientLabel, 333.2);
                supplierOrderPane.setLeftAnchor(quantOrdered, 550.0);
                supplierOrderPane.setLeftAnchor(quantReceieved, 666.4);
                supplierOrderPane.setLeftAnchor(priceLabel, 850.0);


                //add to the grid
                grid.add(supplierOrderPane, 0, row);
                //increase the row
                row ++;

            }
            
        }
        
        
        //add to the scrollPane
        scrollSupplierOrders.setContent(grid);

    }
}

