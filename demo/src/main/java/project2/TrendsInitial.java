package project2;

import javafx.fxml.FXML;
import java.io.IOException;
import java.sql.SQLException;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.ComboBox;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.util.Collections;


import javafx.scene.layout.StackPane;


import javafx.scene.layout.GridPane;
/**
 * Class that initializes the trends page and allows the user to select a trend and show the proper data for it
 * @author Keeley Mahoney
 */
public class TrendsInitial {
    

    @FXML
    Label ingredientLabel;

    @FXML
    ComboBox<String> ingredientLook;

    //new scroll pane to allow for people to scroll and see trends
    @FXML
    ScrollPane scrollTrends = new ScrollPane();

    //new stack pane to center the data display
    @FXML
    StackPane centerLineChart;

    //new warning label if dates aren't entered in correctly
    @FXML 
    Label warning;

    //trends button
    @FXML
    Button trendsButton;

    //button to click on product usage
    @FXML
    Button productUsageButton;

    //button to click on sales report
    @FXML
    Button salesButton;

    //button to click on What sellsTogether
    @FXML
    Button sellsTogetherButton;

    //button to click on excess Report
    @FXML
    Button excessButton;

    //button to click on restock rerport
    @FXML
    Button restockButton;

    //create new variable to hold the trends
    Trends trendsData = new Trends();

    //Submit date button
    @FXML
    Button submitDate;


    //Text field for Date 1
    @FXML
    TextField date1TextField;

    //Label for the Date 1
    @FXML
    Label date1Label;

    //text field for Date 2
    @FXML
    TextField date2TextField;

    //Label for the Date 2
    @FXML 
    Label date2Label;

    //booleans to see if the button was selected
    boolean selectedProduct = false;
    boolean selectedExecess = false;
    boolean selectedSales = false;
    boolean selectedRestock = false;
    boolean selectedSellsTogether = false;

    //an instance of inventorymanager that allows for each person to add/delete ingredients from supplier order
    InventoryManager inventory = new InventoryManager();
     //ingredients that will contain ingredient name and ingredient detail
   HashMap<String, IngredientDetails> ingredients = new HashMap<>();

    /**
     * Default constructor for TrendsInitial()
     */
   public TrendsInitial()
   {

   }
    //switch to inventory screen
    @FXML
    /**
     * Loads the inventory screen
     * @throws IOException if the inventory screen does not load
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
     * Loads the edit menu screen
     * @throws IOException if the edit menu screen does not load
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
     * Loads the supplier order screen
     * @throws IOException if the supplier order screen does not load
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
     * This function switches to the employee list screen
     * @throws IOException - if page doesn't load
     */
    private void switchToEmployeeList() throws IOException {
        App.setRoot("employeeList");
    }

    /**
     * Initializes the needed variables for the function and sets the selected value of the buttons to false
     */
    public void initialize()
    {
        //set button style
        trendsButton.setStyle("-fx-background-color: #500000;");
        trendsButton.setTextFill(javafx.scene.paint.Color.WHITE);

        //just make sure everything is clear
        scrollTrends.setContent(null);
        centerLineChart.getChildren().clear();

        boolean selectedProduct = false;
        boolean selectedExecess = false;
        boolean selectedSales = false;
        boolean selectedRestock = false;
        boolean selectedSellsTogether = false;
        


    }

    /**
     * This function is called when the product usage button is selected. It loads the neccesary text fields and submit button in order to load the product usage report
     */
    public void productUsage()
    {

        //populate all ingredients
        inventory.populateAllIngredients();

        //get all of the ingredients and thier ingredietn names
       ingredients = inventory.getIngredientInventory();

       //add all ingredients to combo box
       for(String ingredientName: ingredients.keySet())
       {
           if(ingredients.get(ingredientName).getQuantity() >= 0)
           {
               ingredientLook.getItems().add(ingredientName);
           }
       }


        //clear everything
        scrollTrends.setContent(null);
        centerLineChart.getChildren().clear();

        //set buttons to their proper value
        selectedProduct = true;
        selectedExecess = false;
        selectedRestock = false;
        selectedSales = false;
        selectedSellsTogether = false;


        //make sure buttons and text fields are visible to enter in time window
        submitDate.setVisible(true);
        date1TextField.setVisible(true);
        date1Label.setVisible(true);
        date2TextField.setVisible(true);
        date2Label.setVisible(true);
        ingredientLabel.setVisible(true);
        ingredientLook.setVisible(true);

        //change color of selected button and set others back to default
        productUsageButton.setStyle("-fx-background-color: #500000;");
        productUsageButton.setTextFill(javafx.scene.paint.Color.WHITE);

        salesButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        salesButton.setTextFill(javafx.scene.paint.Color.BLACK);

        sellsTogetherButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        sellsTogetherButton.setTextFill(javafx.scene.paint.Color.BLACK);

        excessButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        excessButton.setTextFill(javafx.scene.paint.Color.BLACK);

        restockButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        restockButton.setTextFill(javafx.scene.paint.Color.BLACK);


       
        
    }

    /**
     * This function utilizes its parameters to call a function that connects to the database and gets the ingredient product usage for a specfic ingredient given a time window as a line chart
     * @param date1 (String) first date in time window
     * @param date2 (String) second date in time window
     * @param ingredientObject (Object) the value of the selected ingredient
     */
    public void loadProductUsage(String date1, String date2, Object ingredientObject)
    {
        //initialize hash map containing the needed data
        Map<String, Map<Date, Integer>> ingredientsUsage = new HashMap<>();

        //set there being a problem to being false
        boolean problem = false;


        //if nothing was selected, produce warning
        if(ingredientObject == null)
        {
            warning.setVisible(true);
            problem = true;
            warning.setText("Please select an ingredient name");
        }

        //initiailze date 1 values
        Date date1Value = new Date();
        Date date2Value = new Date();


        //ensure that dates are entered in correctly, if they aren't display warning and ensure that nothing is loaded
        String pattern = "yyyy-MM-dd";

        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        

        try {
            date1Value = dateFormat.parse(date1);
            date2Value = dateFormat.parse(date2);
        } catch (ParseException e) {
            warning.setVisible(true);
            warning.setText("Error: Please enter in yyyy-mm-dd for Date");
            e.printStackTrace();
            problem = true;

        }
        try {
            ingredientsUsage = trendsData.getIngredientUsage(date1Value, date2Value);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //if dates are entered in correct
        if(problem == false)
        {
            //get string of ingredient name
            String ingredient_name = (String) ingredientObject;

            //create new x and y axises
            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();

            // Set X and Y axis titles
            xAxis.setLabel("Date");
            yAxis.setLabel("Amount of Ingredient Used");

            //create new line chart
            LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
            lineChart.setTitle("Product Usage Chart");

            //for every ingredient name in ingredient usage
            
            //create a series for the first ingredient
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(ingredient_name);

            List<Date> sortedDates = new ArrayList<>(ingredientsUsage.get(ingredient_name).keySet());
            Collections.sort(sortedDates);
            for(Date orderDate: sortedDates)
            {
                //turn date into a string
                String dateString = dateFormat.format(orderDate);

                //add the date and the amount of usage for each ingredient
                series.getData().add(new XYChart.Data<>(dateString, ingredientsUsage.get(ingredient_name).get(orderDate)));
            }
                // Add the series to the chart
            lineChart.getData().add(series);
            lineChart.setPrefHeight(1500);
            lineChart.setPrefWidth(441);
        
            centerLineChart.setPrefHeight(1500);
            centerLineChart.setPrefWidth(441);
            //create new stack pane for centering data
            centerLineChart = new StackPane(lineChart);
            centerLineChart.setAlignment(javafx.geometry.Pos.CENTER);

            //add content to the scroll pane
            scrollTrends.setContent(centerLineChart);
    
            //make sure the scrollTrends properly aligns itself with the contnt
            scrollTrends.setFitToWidth(true);
            scrollTrends.setFitToHeight(true);
    

            }
            
        }

        /**
     * This function is called when the sales report button is selected. It loads the neccesary text fields and submit button in order to load the sales report
     */
        public void salesReport()
        {
            //reset the data
            scrollTrends.setContent(null);
            centerLineChart.getChildren().clear();

            //set boolean values to proper values
            selectedProduct = false;
            selectedExecess = false;
            selectedRestock = false;
            selectedSales = true;
            selectedSellsTogether = false;

            //make sure buttons and text fields are visible to enter in time window
            submitDate.setVisible(true);
            date1TextField.setVisible(true);
            date1Label.setVisible(true);
            date2TextField.setVisible(true);
            date2Label.setVisible(true);
            ingredientLabel.setVisible(false);
            ingredientLook.setVisible(false);

            //change color of selected button and set others back to default
            salesButton.setStyle("-fx-background-color: #500000;");
            salesButton.setTextFill(javafx.scene.paint.Color.WHITE);

            productUsageButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
            productUsageButton.setTextFill(javafx.scene.paint.Color.BLACK);

            sellsTogetherButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
            sellsTogetherButton.setTextFill(javafx.scene.paint.Color.BLACK);

            excessButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
            excessButton.setTextFill(javafx.scene.paint.Color.BLACK);

            restockButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
            restockButton.setTextFill(javafx.scene.paint.Color.BLACK);
    }

    /**
     * This function loads the sales report given two time windows using functions found in the Trends class as a grid
     * @param date1 (String) first date in time window
     * @param date2 (String) second date in time window
     */
    public void loadSales(String date1, String date2)
    {

        //create new hash map that has menu item name and how many items it was ordered
        Map<String, Integer> salesReport = new HashMap<>();

        //new date values, ensure that they are entered in properly. if not display an error on screen and do not load chart
        Date date1Value = new Date();
        Date date2Value = new Date();

        boolean problem = false;

        String pattern = "yyyy-MM-dd";

        //ensure date format is correct
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        try {
            date1Value = dateFormat.parse(date1);
            date2Value = dateFormat.parse(date2);
        } catch (ParseException e) {
            warning.setVisible(true);
            warning.setText("Error: Please enter in yyyy-mm-dd for Date");
            problem = true;
            e.printStackTrace();
        }
        try {
            salesReport = trendsData.getSalesReport(date1Value, date2Value);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    
        //if there's no problem
        if(problem == false)
        {
                //create new grid pane for displaying the sales report
            GridPane salesReportGrid = new GridPane();

            //set its width
            salesReportGrid.setPrefWidth(989);
    
            //declare row for location in grid pane
            int row = 1;
    
            //create new label listing menu item name and set its size
            Label nameLabel = new Label();
            nameLabel.setText("Menu Item Name");
            nameLabel.setStyle("-fx-font-size: 25;");
            nameLabel.setPrefSize(731.25, 50);
    
            //create new label listing sales and set its size
            Label salesLabel = new Label();
            salesLabel.setText("Sales");
            salesLabel.setStyle("-fx-font-size: 25;");
            salesLabel.setPrefSize(257.75, 50);
    
            //add to grid
            salesReportGrid.add(nameLabel, 0, 0);
            salesReportGrid.add(salesLabel, 1, 0);
    
            //for every menu item in the sales report
            
            for(String menuItem: salesReport.keySet())
            {
                //create new label for the name of the item and set its size
                Label itemName = new Label();
                itemName.setText(menuItem);
                itemName.setPrefSize(731.25, 50);
    
                //create new label for the sales amount of the item and set its size
                Label sales= new Label();
                sales.setText(Double.toString(salesReport.get(menuItem)));
                sales.setPrefSize(257.75, 50);
    
                //add to the grid
                salesReportGrid.add(itemName, 0, row);
    
                salesReportGrid.add(sales, 1, row);
    
                //increase row
                row++;
            }
    
            //create new stack pane for sizing
            centerLineChart = new StackPane(salesReportGrid);
            
            //center the stack pane
            centerLineChart.setAlignment(javafx.geometry.Pos.CENTER);


            //add to scroll trends
            scrollTrends.setContent(centerLineChart);

            //make sure the scrollTrends properly aligns itself with the contnt
            scrollTrends.setFitToWidth(true);
            //scrollTrends.setFitToHeight(true);

            }
         

    }


    /**
     * This function is called when the excess report button is selected. It loads the neccesary text fields and submit button in order to load the excess report
     */
    public void excessReport()
    {
        //clear content
        scrollTrends.setContent(null);
        centerLineChart.getChildren().clear();

        selectedProduct = false;
        selectedExecess = true;
        selectedRestock = false;
        selectedSales = false;
        selectedSellsTogether = false;
        //make sure date 1 text field and buttons are visible to enter in time window
        //make sure buttons and text fields are visible to enter in time window
        submitDate.setVisible(true);
        date1TextField.setVisible(true);
        date1Label.setVisible(true);
        date2TextField.setVisible(false);
        date2Label.setVisible(false);
        ingredientLabel.setVisible(false);
        ingredientLook.setVisible(false);

        //change color of selected button and set others back to default
        excessButton.setStyle("-fx-background-color: #500000;");
        excessButton.setTextFill(javafx.scene.paint.Color.WHITE);

        productUsageButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        productUsageButton.setTextFill(javafx.scene.paint.Color.BLACK);

        sellsTogetherButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        sellsTogetherButton.setTextFill(javafx.scene.paint.Color.BLACK);

        salesButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        salesButton.setTextFill(javafx.scene.paint.Color.BLACK);

        restockButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        restockButton.setTextFill(javafx.scene.paint.Color.BLACK);
        

    }

    /**
     * This function loads the excess report given a starting date. It then utilizes functions in the Trends class to get the neccesary data and display it as a grid
     * @param date1 (String) the starting date of the excess report, with the time window being from then till now
     */
    public void loadExcess(String date1)
    {

        //create new date value. ensure it's entered in properly. if it's not, dispaly error message and do not load chart
        Date date1Value = new Date();

        boolean problem = false;

        String pattern = "yyyy-MM-dd";

        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        try {
            date1Value = dateFormat.parse(date1);
           
        } catch (ParseException e) {
            warning.setVisible(true);
            warning.setText("Please input proper date in yyyy-mm-dd format.");
            problem = true;
            e.printStackTrace();
        }

        //if there's no probelm
        if(problem == false)
        {
            //create new grid pane for displaying the excess reports
            GridPane excessReportGrid = new GridPane();

            //set its width
            excessReportGrid.setPrefWidth(989);

            //declare row for location in grid pane
            int row = 1;

            //create new label listing catgoery and set its size
            Label nameLabel = new Label();
            nameLabel.setText("Menu Item Name");
            nameLabel.setPrefSize(494.5, 50);
            nameLabel.setStyle("-fx-font-size: 25;");

            Label remainingLabel = new Label();
            remainingLabel.setText("Initial Amount");
            remainingLabel.setPrefSize(247.25, 50);
            remainingLabel.setStyle("-fx-font-size: 25;");

            Label currentLabel = new Label();
            currentLabel.setText("Amount Used");
            currentLabel.setPrefSize(247.25, 50);
            currentLabel.setStyle("-fx-font-size: 25;");

            
            //add to grid
            excessReportGrid.add(nameLabel, 0, 0);
            excessReportGrid.add(remainingLabel, 1, 0);
            excessReportGrid.add(currentLabel, 2, 0);



            //new hash map, populate it with the name of the ingredient and its quantities
            Map<String, Map<String, Object>>excessReport = new HashMap<>();
            try {
                excessReport = trendsData.getExcessReport(date1Value);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            
            //for every ingredient
            for(String ingredientName: excessReport.keySet())
            {
                if(Boolean.valueOf(String.valueOf(excessReport.get(ingredientName).get("UsageWithin10Percent"))))
                {
                    //create new label for the name of the ingredient and set its size
                    Label itemName = new Label();
                    itemName.setText(ingredientName);
                    itemName.setPrefSize(494.5, 50);

                    //create new label for the remaining value of the stock and set its size
                    Label remaining = new Label();
                    remaining.setText(Integer.toString((Integer)(excessReport.get(ingredientName).get("TotalQuantityReceived"))));
                    remaining.setPrefSize(167.5, 50);

                    //create new label for the current value of the stock and set its size
                    Label current = new Label();
                    current.setText(Integer.toString((Integer)(excessReport.get(ingredientName).get("TotalQuantityUsed"))));
                    current.setPrefSize(167.5, 50);

                    //add to the grid
                    excessReportGrid.add(itemName, 0, row);

                    excessReportGrid.add(remaining, 1, row);

                    excessReportGrid.add(current, 2, row);

                    
                    //increase row
                    row++;
                }
            }
            //create new stack pane for dispaly
            centerLineChart = new StackPane(excessReportGrid);

            //center the stack pane
            centerLineChart.setAlignment(javafx.geometry.Pos.CENTER);

            scrollTrends.setContent(centerLineChart);

            //make sure the scrollTrends properly aligns itself with the contnt
            scrollTrends.setFitToWidth(true);
           // scrollTrends.setFitToHeight(true);

        }
                


    }
        
        

    /**
     * This function is called when the load restock button is selected. It then utilizes an sql command in the databse to find the items that have a quantity lower than or equal to their minimum value as a grid
     */
    public void loadRestock()
    {
        // populate all ingredients
        inventory.populateAllIngredients();

        // get all of the ingredients and their ingredient names
        ingredients = inventory.getIngredientInventory();

        //set dates to be false
        date1Label.setVisible(false);
        date1TextField.setVisible(false);
        date2Label.setVisible(false);
        date2TextField.setVisible(false);
        submitDate.setVisible(false);
        ingredientLabel.setVisible(false);
        ingredientLook.setVisible(false);


        //clear content
        scrollTrends.setContent(null);
        centerLineChart.getChildren().clear();

        //ensure booleans are correct
        selectedProduct = false;
        selectedExecess = false;
        selectedRestock = true;
        selectedSales = false;
        selectedSellsTogether = false;

        //initialize hash map
        Map<String, Map<String, Integer>> restock = new HashMap<>();

        //change color of selected button and set others back to default
        restockButton.setStyle("-fx-background-color: #500000;");
        restockButton.setTextFill(javafx.scene.paint.Color.WHITE);

        productUsageButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        productUsageButton.setTextFill(javafx.scene.paint.Color.BLACK);

        sellsTogetherButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        sellsTogetherButton.setTextFill(javafx.scene.paint.Color.BLACK);

        salesButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        salesButton.setTextFill(javafx.scene.paint.Color.BLACK);

        excessButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        excessButton.setTextFill(javafx.scene.paint.Color.BLACK);

        //try to get the restock report
        try {
            restock = trendsData.getRestockReport();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //create new grid pane for displaying the restock report
        GridPane restockGrid = new GridPane();

        //set its width
        restockGrid.setPrefWidth(989);

        //declare row for location in grid pane
        int row = 1;

        //create new label listing catgoery and set its size
        Label nameLabel = new Label();
        nameLabel.setText("Ingredient Name");
        nameLabel.setPrefSize(593.4, 50);
        nameLabel.setStyle("-fx-font-size: 25;");

         //create new label listing catgoery and set its size
        Label quantityLabel = new Label();
        quantityLabel.setText("Quantity");
        quantityLabel.setPrefSize(197.8, 50);
        quantityLabel.setStyle("-fx-font-size: 25;");

        //create new label listing catgoery and set its size
        Label warningLabel = new Label();
        warningLabel.setText("Minimum Amount");
        warningLabel.setPrefSize(197.8, 50);
        warningLabel.setStyle("-fx-font-size: 25;");

        //add to grid
        restockGrid.add(nameLabel, 0, 0);
        restockGrid.add(quantityLabel, 1, 0);
        restockGrid.add(warningLabel, 2, 0);


        //for every ingredient present whose used less than 10%
        for(String ingredientName: restock.keySet())
        {

            if(ingredients.get(ingredientName).getQuantity() >= 0) {
                //create new label for the name of the ingredient and set its size
                Label itemName = new Label();
                itemName.setText(ingredientName);
                itemName.setPrefSize(593.4, 50);

                //create new label for the present quantity of the ingredient and set its size
                Label quantityAvail = new Label();
                quantityAvail.setText(Integer.toString(restock.get(ingredientName).get("quantity")));
                quantityAvail.setPrefSize(197.8, 50);

                //create new label for the minimum quantity of the ingredient and set its size
                Label quantityMin = new Label();
                quantityMin.setText(Integer.toString(restock.get(ingredientName).get("quantity_warning")));
                quantityMin.setPrefSize(197.8, 50);

                //add to the grid
                restockGrid.add(itemName, 0, row);
                restockGrid.add(quantityAvail, 1, row);
                restockGrid.add(quantityMin, 2, row);

                //increase row
                row++;
            }
        }

        //create new stack pane for display
        centerLineChart = new StackPane(restockGrid);

        //center the stack pane
        centerLineChart.setAlignment(javafx.geometry.Pos.CENTER);

        //set content
        scrollTrends.setContent(centerLineChart);

         //make sure the scrollTrends properly aligns itself with the contnt
         scrollTrends.setFitToWidth(true);
         //scrollTrends.setFitToHeight(true);

    }

    /**
     * This function is called when the what sells together button is selected. It loads the neccesary text fields and submit button in order to load the what sells together report
     */
    public void whatSells()
    {
        //get rid of current contet
        scrollTrends.setContent(null);
        centerLineChart.getChildren().clear();

        //set boolean variables to their proper values
        selectedProduct = false;
        selectedExecess = false;
        selectedRestock = false;
        selectedSales = false;
        selectedSellsTogether = true;


        //make sure buttons and text fields are visible to enter in time window
        submitDate.setVisible(true);
        date1TextField.setVisible(true);
        date1Label.setVisible(true);
        date2TextField.setVisible(true);
        date2Label.setVisible(true);
        ingredientLabel.setVisible(false);
        ingredientLook.setVisible(false);

        //change color of selected button and set others back to default
        sellsTogetherButton.setStyle("-fx-background-color: #500000;");
        sellsTogetherButton.setTextFill(javafx.scene.paint.Color.WHITE);

        productUsageButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        productUsageButton.setTextFill(javafx.scene.paint.Color.BLACK);

        excessButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        excessButton.setTextFill(javafx.scene.paint.Color.BLACK);

        salesButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        salesButton.setTextFill(javafx.scene.paint.Color.BLACK);

        restockButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        restockButton.setTextFill(javafx.scene.paint.Color.BLACK);

       

    }

    /**
     * This function loads what two items sell together, and how many times they sell together during a time window as a grid. It uses functions in the Trend class to get the data
     * @param date1 (String) first date in time window
     * @param date2 (String) second date in time window
     */
    public void loadWhatSells(String date1, String date2)
    {
        
        //initialize the list that will have a hash map of items that sell together during specific date
        List<Map<String, Object>> doubleTrend = new ArrayList<>();

        //create new date variables, ensure that they are entered in the correct format. if not, display warning and do not load grid
        Date date1Value = new Date();
        Date date2Value = new Date();

        String pattern = "yyyy-MM-dd";

        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        boolean problem = false;

        try {
            date1Value = dateFormat.parse(date1);
            date2Value = dateFormat.parse(date2);
        } catch (ParseException e) {
            warning.setVisible(true);
            warning.setText("Error: Please enter in yyyy-mm-dd for Date");
            problem = true;
            e.printStackTrace();
        }
        try {
            doubleTrend= trendsData.getDoubleOrder(date1Value, date2Value);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //if there's no problem
        if(problem == false)
        {
            //create new grid pane for displaying what sells together
         GridPane sellsTogetherGrid= new GridPane();

         //set its width
         sellsTogetherGrid.setPrefWidth(989);
 
         //declare row for location in grid pane
         int row = 1;
 
         //create new label listing catgoery and set its size
         Label name1Label = new Label();
         name1Label.setText("Menu Item 1 Name");
         name1Label.setPrefSize(370.875, 50);
         name1Label.setStyle("-fx-font-size: 25;");
 
          //create new label listing catgoery and set its size
         Label name2Label= new Label();
         name2Label.setText("Menu Item 2 Name");
         name2Label.setPrefSize(370.875, 50);
         name2Label.setStyle("-fx-font-size: 25;");
 
         //create new label listing catgoery and set its size
         Label amountLabel = new Label();
         amountLabel.setText("Number of Times Sold");
         amountLabel.setPrefSize(247.25, 50);
         amountLabel.setStyle("-fx-font-size: 25;");
 
         //add to grid
 
         sellsTogetherGrid.add(name1Label, 0, 0);
         sellsTogetherGrid.add(name2Label, 1, 0);
         sellsTogetherGrid.add(amountLabel, 2, 0);
 
 
         //for every pair of ingredients
         for(int i = 0; i < doubleTrend.size(); i++)
         {
             //create label for item 1 and set its size
            Label itemName1 = new Label();
            itemName1.setPrefSize(370.875, 50);

            //create label for item 2 and set its size
            Label itemName2 = new Label();
            itemName2.setPrefSize(370.875, 50);

            //create label for amount sold together and set its size
            Label soldTogether = new Label();
            soldTogether.setPrefSize(247.25, 50);

            //for each key in the hash map
             for(String labelOfCol: doubleTrend.get(i).keySet())
             {
                 //if it's item 1, set the label to be the name of item 1
                 if(labelOfCol == "Item1")
                 { 
                    itemName1.setText((String)doubleTrend.get(i).get("Item1")); 
                    itemName1.setPrefHeight(50);
                 }

                 //if it's item 2, set the label to be the name of item 2
                 else if(labelOfCol == "Item2")
                 {
                    itemName2.setText((String) doubleTrend.get(i).get("Item2"));
                    itemName2.setPrefHeight(50);
                 }

                 //if it's the frequency, set the label to be the amount of times sold together
                 else if(labelOfCol == "Frequency")
                 {
                    soldTogether.setText(Integer.toString((Integer)doubleTrend.get(i).get("Frequency")));
                    soldTogether.setPrefHeight(50);
                 }
             }
             //add to the grid
             sellsTogetherGrid.add(itemName1, 0, row);
 
             sellsTogetherGrid.add(itemName2, 1, row);
 
             sellsTogetherGrid.add(soldTogether, 2, row);
 
             //increase row
             row++;
         }
         
         //create new stack pane to align everything
         centerLineChart = new StackPane(sellsTogetherGrid);
 
         //center the stack pane
         centerLineChart.setAlignment(javafx.geometry.Pos.CENTER);
 
         scrollTrends.setContent(centerLineChart);
 
          //make sure the scrollTrends properly aligns itself with the contnt
          scrollTrends.setFitToWidth(true);
          //scrollTrends.setFitToHeight(true);
          

        }
        
        
    }
    

    //on buttton click
    /**
     * When the submit button is pressed, it utilizes the pressed button to pass in the proper data from the text fields and dropdown box to generate the required report
     */
    public void submiButton()
    {
        //get the possible date1 and date 2 values and possible ingredinend name
        String date1 = date1TextField.getText();
        String date2 = date2TextField.getText();
        String name = (String) ingredientLook.getValue();

        //if the restock button is selected, load the restock report
        if(selectedRestock)
        {
            loadRestock();
        }

        //if the product usage button is selected, load the product usage report with time window
        else if(selectedProduct)
        {
            loadProductUsage(date1, date2, name);
        }

        //if the excess report button is selected, load the excess report with date
        else if(selectedExecess)
        {
            loadExcess(date1);
        }

        //if the sales report button is selected, load the sales report with time window
        else if(selectedSales)
        {
            loadSales(date1, date2);
        }

        //if the what sells together is selected, load it with time window
        else if(selectedSellsTogether)
        {
            loadWhatSells(date1, date2);
        }

    }
}