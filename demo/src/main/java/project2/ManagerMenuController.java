package project2;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.ScrollPane;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;

// Secondary
/**
 * Class that loads a screen that allows the manager to view all of the menu items that are currently on the menu
 * @author Keeley Mahoney
 */
public class ManagerMenuController {

    //button for edit screen
    @FXML
    private Button edit;

    //button for entrees
    @FXML
    private Button entreesButton;
    @FXML

    //button for sides
    private Button sidesButton;
    @FXML

    //button for beverages
    private Button beveragesButton;

    //button for desserts
    @FXML
    private Button dessertsButton;

    @FXML 
    private Button seasonalButton;

    //scroll pane used in displaying the menu items
    @FXML
    private ScrollPane scroll;

    //new category menu
    private Map<String, ArrayList<Button>> categoryMenu = new HashMap<>();

    //create array lists of menu items for each category
    ArrayList<Menu_Item> entreesMenu = new ArrayList<Menu_Item>();
    ArrayList<Menu_Item> sidesMenu = new ArrayList<Menu_Item>();
    ArrayList<Menu_Item> beveragesMenu = new ArrayList<Menu_Item>();
    ArrayList<Menu_Item> dessertsMenu = new ArrayList<Menu_Item>();
    ArrayList<Menu_Item> seasonalMenu = new ArrayList<Menu_Item>();
    
    //create array list of buttons for each cateogry
    ArrayList<Button> entreesButtons = new ArrayList<Button>();
    ArrayList<Button> sidesButtons = new ArrayList<Button>();
    ArrayList<Button> beveragesButtons = new ArrayList<Button>();
    ArrayList<Button> dessertsButtons = new ArrayList<Button>();
    ArrayList<Button> seasonalButtons = new ArrayList<Button>();
    
    //clicked buttons set
    private Set<Button> clickedButtons = new HashSet<Button>();

    //populator
    private Menu_Item_Populate populator = new Menu_Item_Populate();

    //anchor pane, where menu items are
    @FXML
    private AnchorPane menuItemsArea;


    /**
     * Default constructor for Manager Menu Controller
     */
    public ManagerMenuController()
    {

    }

    //switch back to the inventory screen
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

    @FXML
     /**
     * Switches to the trends screen
     * @throws IOException if the trends screen cannot be loaded
     */
    private void switchToTrends() throws IOException {
        App.setRoot("TrendsInitial");
        
    }

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
    
    @FXML
    /**
     * Switches to the employees screen
     * @throws IOException if the employees screen cannot be loaded
     */
    private void switchToEmployeeList() throws IOException {
        App.setRoot("employeeList");
    }
    
    /**
     * This function gets called once the screen has been loaded. It populates all of the neccesary variables of the class, sets the styles of the buttons, and creates the buttons
     */
    public void initialize()
    {
        menuItemsArea.getChildren().clear();

        scroll.setContent(null);
        //get the menu items
        populator.populateMenuItems();

        //get the various categories and the menu items they belong to
        entreesMenu = populator.getEntreesMenu();
        dessertsMenu = populator.getDessertMenu();
        sidesMenu = populator.getSidesMenu();
        beveragesMenu = populator.getBeveragesMenu();
        seasonalMenu = populator.getSeasonalMenu();

        for(int i = 0; i < seasonalMenu.size(); i++)
        {
            System.out.println(seasonalMenu.get(i).getItemName());
        }


        /* 
        System.out.println("printing the entrees");
        for(int i = 0; i < entreesMenu.size(); i++)
        {
            System.out.println(entreesMenu.get(i).getItemName());
        }
        */



        //set the style of the buttons
        entreesButton.setStyle("-fx-background-color: white; -fx-border-color: black;  -fx-border-width: 1;-fx-text-fill: black;");
        sidesButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        beveragesButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        dessertsButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        seasonalButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");

        //change the background color 
        edit.setStyle("-fx-background-color: #500000;");
        edit.setTextFill(javafx.scene.paint.Color.WHITE);
        // put the menu item names for each category
        categoryMenu.put("Entree", entreesButtons);
        categoryMenu.put("Side", sidesButtons);
        categoryMenu.put("Beverage", beveragesButtons);
        categoryMenu.put("Dessert", dessertsButtons);
        categoryMenu.put("Seasonal", seasonalButtons);

        //make the buttons
        createButtons();
    
    }

    @FXML
    /**
     * Function that sets the styles of the category buttons when the entrees button is clicked and then called loadButtons for entree
     * @param event (ActionEvent) that indicates that a button was clicked
     */
    private void clickEntrees(ActionEvent event) {
        //set entree buttons as colored and others as white
        entreesButton.setStyle("-fx-background-color: #500000; -fx-text-fill: white; -fx-border-width: 0;");
        sidesButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        beveragesButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        dessertsButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        seasonalButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        loadButtons("Entree");

        //add functionality to each button when clicked
        for(Button b: entreesButtons)
        {
            b.setOnAction(this::handleButtonClick);
        }
    }

    @FXML
    /**
     * Function that sets the styles of the category buttons when the sides button is clicked and then called loadButtons for sides
     * @param event (ActionEvent) that indicates that a button was clicked
     */
    private void clickSides(ActionEvent event) {
        //set sides as colored button and all others as non-colored
        sidesButton.setStyle("-fx-background-color: #500000; -fx-text-fill: white; -fx-border-width: 0;");
        entreesButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        beveragesButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        dessertsButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        seasonalButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");

        loadButtons("Side");
    }

    @FXML
    /**
     * Function that sets the styles of the category buttons when the beverages button is clicked and then called loadButtons for beverages
     * @param event (ActionEvent) that indicates that a button was clicked
     */
    private void clickBeverages(ActionEvent event) {
        //set beverages as colored button and all others as non-colored
        beveragesButton.setStyle("-fx-background-color: #500000; -fx-text-fill: white; -fx-border-width: 0;");
        entreesButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        sidesButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        dessertsButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        seasonalButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        loadButtons("Beverage");
    }

    @FXML
    /**
     * Function that sets the styles of the category buttons when the desserts button is clicked and then called loadButtons for desserts
     * @param event (ActionEvent) that indicates that a button was clicked
     */
    private void clickDesserts(ActionEvent event) {
        //set desserts as colored button and all others as non-colored
        dessertsButton.setStyle("-fx-background-color: #500000; -fx-text-fill: white; -fx-border-width: 0;");
        beveragesButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        entreesButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        sidesButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        seasonalButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        loadButtons("Dessert");
    }

    @FXML
    /**
     * Function that sets the styles of the category buttons when the seasonal button is clicked and then called loadButtons for seasonal
     * @param event (ActionEvent) that indicates that a button was clicked
     */
    private void clickSeasonal(ActionEvent event)
    {
        seasonalButton.setStyle("-fx-background-color: #500000; -fx-text-fill: white; -fx-border-width: 0;");
        beveragesButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        entreesButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        dessertsButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        sidesButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        loadButtons("Seasonal");

    }

    
    //if a button was clicked
    @FXML
    private Label clicked;

    /* 
    @FXML
    protected void onButtonClick(Button button) {
        
        // If button already clicked - remove from clickedButtons arraylist
        if(clickedButtons.contains(button)) {
            button.getStyleClass().remove("clicked-button");
            clickedButtons.remove(button);
        } else { // if button has not been clicked
            button.getStyleClass().add("clicked-button");
            clickedButtons.add(button);
        }

        // FOR DEBUGGING: remove later
        String str = "";
        for(Button b : clickedButtons) {
            str += b.getText() + " ";
        }
        clicked.setText(str);
    }
    */

    //creates the buttons
    /**
     * Function that creates the buttons for each category of the menu and adds them to an array list that stores those buttons
     */
    protected void createButtons() {

        

        // Create and store buttons for each menu item in each category
        entreesButtons.clear();
        
        for(Menu_Item menu_item : entreesMenu) {
            if(menu_item.getPrice() >= 0)
            {
                Button menuButton = new Button(menu_item.getItemName());
                //menuButton.setOnAction(event -> handleButtonClick(menuButton));
                menuButton.getStyleClass().add("menu-item-button");
                menuButton.setOnAction(this::handleButtonClick);
                entreesButtons.add(menuButton);

            }
            
        }

        sidesButtons.clear();
        for(Menu_Item menu_item  : sidesMenu) {
            if(menu_item.getPrice() >= 0)
            {
                Button menuButton = new Button(menu_item.getItemName());
                //menuButton.setOnAction(event -> handleButtonClick(menuButton));
                menuButton.getStyleClass().add("menu-item-button");
                menuButton.setOnAction(this::handleButtonClick);
                sidesButtons.add(menuButton);

            }
        }

        beveragesButtons.clear();
        for(Menu_Item menu_item  : beveragesMenu) {
            if(menu_item.getPrice() >= 0)
            {
                Button menuButton = new Button(menu_item.getItemName());
                //menuButton.setOnAction(event -> handleButtonClick(menuButton));
                menuButton.getStyleClass().add("menu-item-button");
                menuButton.setOnAction(this::handleButtonClick);
                beveragesButtons.add(menuButton);

            }
        }
        dessertsButtons.clear();
        for(Menu_Item menu_item  : dessertsMenu) {
            if(menu_item.getPrice() >= 0)
            {
                Button menuButton = new Button(menu_item.getItemName());
                //menuButton.setOnAction(event -> handleButtonClick(menuButton));
                menuButton.getStyleClass().add("menu-item-button");
                menuButton.setOnAction(this::handleButtonClick);
                dessertsButtons.add(menuButton);

            }
        }

        seasonalButtons.clear();
        for(Menu_Item menu_item  : seasonalMenu) {
            if(menu_item.getPrice() >= 0)
            {
                Button menuButton = new Button(menu_item.getItemName());
                //menuButton.setOnAction(event -> handleButtonClick(menuButton));
                menuButton.getStyleClass().add("menu-item-button");
                menuButton.setOnAction(this::handleButtonClick);
                seasonalButtons.add(menuButton);

            }
        }

    }

    // Places the buttons of the category in the menu items area 
    /**
     * Function that loads the buttons onto the Gui for a given menu category
     * @param category (String) that dictates what menu category will be loaded onto the grid
     */
    protected void loadButtons(String category) {
        // clear current menu item area
        menuItemsArea.getChildren().clear();

        // create a new grid for display
        GridPane menuGrid = new GridPane();
        menuGrid.setHgap(10);
        menuGrid.setVgap(20);

        int col = 0;
        int row = 0;

        for(Button button : categoryMenu.get(category)) {
            if(clickedButtons.contains(button)) {
                //button.getStyleClass().add("clicked-button");
            }

            menuGrid.add(button, col, row);
            col++;
            // Making sure the grid is 2 columns
            if(col == 2) {
                col = 0;
                row++;
            }

            button.setOnAction(this::handleButtonClick);

            //button.setOnAction(event -> handleButtonClick(button));
            

            
        }

        //add menu grid to the anchor pane
        menuItemsArea.getChildren().add(menuGrid);

        //add to the scroll
        scroll.setContent(menuItemsArea);
    }

    //when a button is clicked go to ThirdController
    
    /**
     * When a button is clicked for a menu item, the edit menu screen for the menu item is loaded that already exists
     * @param e (Action event) that is a button being clicked
     */
    @FXML
    public void handleButtonClick(ActionEvent e)
    {
        //System.out.println("does handle button click happen ? ");
        //get button that was clicked
        Button clickedButton = (Button) e.getSource();
        
        //get text of button that was clicked
        String text = clickedButton.getText();
        //System.out.println("The button value is " + text + "k");
    
        //try catch to switch to third
        try
        {
            App.setRoot("editMenu", text);
        }
        catch (Exception error) {
            error.printStackTrace(); // Handle the exception appropriately
        }
    }

    //when you want to add a new menu item
   
    /**
     * Function that loads the edit menu screen when you want to add a new menu item
     */
    @FXML
    public void addMenuItem()
    {
        try
        {
            App.setRoot("editMenu");
        }
        catch (Exception error) {
            error.printStackTrace(); // Handle the exception appropriately
        }

    }

}