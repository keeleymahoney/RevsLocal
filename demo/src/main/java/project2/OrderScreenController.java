package project2;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.control.Label;

/**
 * This class is for the order screen for servers.
 * @author Joanne Liu
 */
public class OrderScreenController {

  
    /**
     * This function switches to the checkout screen
     * @throws IOException if checkout screen does not load
     */
    @FXML
    private void switchToSecondary() throws IOException {
        // App.setRoot("checkout", clickedNames, quantities);
        App.setRoot("checkout", menuItemQuantities, clickedNames);
    }

    /**
     * OrderScreen default controller
     */
    public OrderScreenController() {
        // default constructor
    }

    /**
     * FXML Button to navigate to entrees menu
     */
    @FXML
    private Button entrees;

    /**
     * FXML Button to navigate to sides menu
     */
    @FXML
    private Button sides;

    /**
     * FXML Button to navigate to beverages menu
     */
    @FXML
    private Button beverages;

    /**
     * FXML Button to navigate to desserts menu
     */
    @FXML
    private Button desserts;

    /**
     * FXML Button to navigate to seasonal menu
     */
    @FXML
    private Button seasonal;

    /**
     * FXML Button to reload the menu
     */
    @FXML
    private Button reload;

    /**
     * FXML StackPane to display the names of menu items
     */
    @FXML
    private StackPane menuItemsArea;
    /**
     * Hashmap of string to ArrayList of buttons to map category name to an ArrayList of menu items
     */
    private Map<String, ArrayList<Button>> categoryMenu = new HashMap<>();

    /**
     * ArrayList of strings of the menu items in the entrees category
     */
    ArrayList<String> entreesMenu = new ArrayList<String>();

    /**
     * ArrayList of strings of the menu items in the sides category
     */
    ArrayList<String> sidesMenu = new ArrayList<String>();

    /**
     * ArrayList of strings of the menu items in the beverages category
     */
    ArrayList<String> beveragesMenu = new ArrayList<String>();

    /**
     * ArrayList of strings of the menu items in the desserts category
     */
    ArrayList<String> dessertsMenu = new ArrayList<String>();

    /**
     * ArrayList of strings of the menu items in the seasonal category
     */
    ArrayList<String> seasonalMenu = new ArrayList<String>();

    
    /**
     * ArrayList of buttons of the entrees that the user pressed
     */
    ArrayList<Button> entreesButtons = new ArrayList<Button>();

    /**
     * ArrayList of buttons of the sides that the user pressed
     */
    ArrayList<Button> sidesButtons = new ArrayList<Button>();

    /**
     * ArrayList of buttons of the beverages that the user pressed
     */
    ArrayList<Button> beveragesButtons = new ArrayList<Button>();

    /**
     * ArrayList of buttons of the desserts that the user pressed
     */
    ArrayList<Button> dessertsButtons = new ArrayList<Button>();

    /**
     * ArrayList of buttons of the sesonal menu items that the user pressed
     */
    ArrayList<Button> seasonalButtons = new ArrayList<Button>();
    
    /**
     * Hashmap of buttons of menu items that the user pressed
     */
    protected Set<Button> clickedButtons = new HashSet<Button>();

    /**
     * ArrayList of strings of the menu item names that the user pressed
     */
    ArrayList<String> clickedNames = new ArrayList<String>();

    /**
     * Hashmap of string to integers mapping the menu item name to the quantity of the menu item.
     */
    HashMap<String, Integer> menuItemQuantities = new HashMap<>();
 
    /**
     * This function places all the buttons for menu items in their respective categories 
     * into the big categoryMenu hashmap.
     */
    @FXML
    private void initialize() {
        // put the menu item names for each category
        categoryMenu.put("Entrees", entreesButtons);
        categoryMenu.put("Sides", sidesButtons);
        categoryMenu.put("Beverages", beveragesButtons);
        categoryMenu.put("Desserts", dessertsButtons);
        categoryMenu.put("Seasonal", seasonalButtons);
        createButtons();
    }


    /**
     * This function is for the entrees button on the side bar is clicked. The entree buttons are loaded
     * and the entrees button is also highlighted. 
     */
    @FXML
    private void clickEntrees() {
        entrees.setStyle("-fx-background-color: #500000; -fx-text-fill: white; -fx-border-width: 0;");
        sides.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        beverages.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        desserts.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        seasonal.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        loadButtons("Entrees");
    }

    /**
     * This function is for the sides button on the side bar is clicked. The sides buttons are loaded
     * and the sides button is also highlighted. 
     */
    @FXML
    private void clickSides() {
        sides.setStyle("-fx-background-color: #500000; -fx-text-fill: white; -fx-border-width: 0;");
        entrees.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        beverages.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        desserts.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        seasonal.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        loadButtons("Sides");
    }

    /**
     * /**
     * This function is for the beverages button on the side bar is clicked. The beverages buttons are loaded
     * and the beverages button is also highlighted. 
     */
    @FXML
    private void clickBeverages() {
        beverages.setStyle("-fx-background-color: #500000; -fx-text-fill: white; -fx-border-width: 0;");
        entrees.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        sides.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        desserts.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        seasonal.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        loadButtons("Beverages");
    }

    /**
     * This function is for the desserts button on the side bar is clicked. The dessert buttons are loaded
     * and the dessert button is also highlighted. 
     */
    @FXML
    private void clickDesserts(ActionEvent event) {
        desserts.setStyle("-fx-background-color: #500000; -fx-text-fill: white; -fx-border-width: 0;");
        beverages.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        entrees.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        sides.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        seasonal.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        loadButtons("Desserts");
    }

    /**
     * This function is for the seasonal button on the side bar is clicked. The sesonal entree buttons are loaded
     * and the seasonal button is also highlighted. 
     */
    @FXML
    private void clickSeasonal(ActionEvent event) {
        seasonal.setStyle("-fx-background-color: #500000; -fx-text-fill: white; -fx-border-width: 0;");
        beverages.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        entrees.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        sides.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        desserts.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width: 1;");
        loadButtons("Seasonal");
    }


    /**
     * This function returns all the menu item buttons that are clicked
     * @return a hashset of buttons
     */
    @FXML
    public Set<Button> getClickedButtons() {
        return clickedButtons;
    }

    // function to load screen if switching from checkout screen to order screen
    /**
     * This function updates the menu item buttons that are already pressed
     * when switching between checkout screen back to order screen
     * @param checkoutClickedButtons - names of menu items already in cart (ArrayList of string)
     * @param itemQuantities - names mapped to quantities of menu items (HashMap of String and Integer)
     */
    @FXML
    public void loadScreen(ArrayList<String> checkoutClickedButtons, HashMap<String, Integer> itemQuantities) {
        clickedButtons.clear();
        clickedNames = checkoutClickedButtons;
        menuItemQuantities = itemQuantities;
    }

    /**
     * Label to display clicked menu item names
     */
    @FXML
    private Label clicked;

    /**
     * Update list of items pressed if button is pressed or unpressed.
     * @param button - menu item button that is pressed
     */
    @FXML
    protected void onButtonClick(Button button) {
        // If button already clicked - remove from clickedButtons arraylist
        if(clickedNames.contains(button.getText())) {
            button.getStyleClass().clear();
            button.getStyleClass().add("menu-item-button");
            clickedButtons.remove(button);
            clickedNames.remove(button.getText());
            menuItemQuantities.remove(button.getText());
        } else { // if button has not been clicked
            button.getStyleClass().add("clicked-button");
            clickedButtons.add(button);
            clickedNames.add(button.getText());
        }
    }

    /**
     * Create buttons for each menu, retrieving from PostgreSQL database
     */    
    @FXML
    protected void createButtons() {
        Menu_Item_Populate menuItemManager = new Menu_Item_Populate();
        menuItemManager.populateMenuItems();
        menuItemManager.populateAllIngredients();
        ArrayList<Menu_Item> entrees_menuItem = menuItemManager.getEntreesMenu();
        ArrayList<Menu_Item> sides_menuItem = menuItemManager.getSidesMenu();
        ArrayList<Menu_Item> beverages_menuItem = menuItemManager.getBeveragesMenu();
        ArrayList<Menu_Item> dessert_menuItem = menuItemManager.getDessertMenu();
        ArrayList<Menu_Item> seasonal_menuItem = menuItemManager.getSeasonalMenu();

        // Add current menu items to each category
        entreesMenu.clear();
        for(Menu_Item item : entrees_menuItem) {
            if(item.getPrice() >= 0) {
                entreesMenu.add(item.getItemName());
            }
        }

        sidesMenu.clear();
        for(Menu_Item item : sides_menuItem) {
            if(item.getPrice() >= 0) {
                sidesMenu.add(item.getItemName());
            }
        }

        beveragesMenu.clear();
        for(Menu_Item item : beverages_menuItem) {
            if(item.getPrice() >= 0) {
                beveragesMenu.add(item.getItemName());
            }
        }
        dessertsMenu.clear();
        for(Menu_Item item : dessert_menuItem) {
            if(item.getPrice() >= 0) {
                dessertsMenu.add(item.getItemName());
            }
        }

        seasonalMenu.clear();
        for(Menu_Item item : seasonal_menuItem) {
            if(item.getPrice() >= 0) {
                seasonalMenu.add(item.getItemName());
            }
        }

        // Create and store buttons for each menu item in each category
        entreesButtons.clear();
        for(String buttonName : entreesMenu) {
            Button menuButton = new Button(buttonName);
            menuButton.setOnAction(event -> onButtonClick(menuButton));
            menuButton.getStyleClass().add("menu-item-button");
            entreesButtons.add(menuButton);
        }

        sidesButtons.clear();
        for(String buttonName : sidesMenu) {
            Button menuButton = new Button(buttonName);
            menuButton.setOnAction(event -> onButtonClick(menuButton));
            menuButton.getStyleClass().add("menu-item-button");
            sidesButtons.add(menuButton);
        }

        beveragesButtons.clear();
        for(String buttonName : beveragesMenu) {
            Button menuButton = new Button(buttonName);
            menuButton.setOnAction(event -> onButtonClick(menuButton));
            menuButton.getStyleClass().add("menu-item-button");
            beveragesButtons.add(menuButton);
        }
        dessertsButtons.clear();
        for(String buttonName : dessertsMenu) {
            Button menuButton = new Button(buttonName);
            menuButton.setOnAction(event -> onButtonClick(menuButton));
            menuButton.getStyleClass().add("menu-item-button");
            dessertsButtons.add(menuButton);
        }
        seasonalButtons.clear();
        for(String buttonName : seasonalMenu) {
            Button menuButton = new Button(buttonName);
            menuButton.setOnAction(event -> onButtonClick(menuButton));
            menuButton.getStyleClass().add("menu-item-button");
            seasonalButtons.add(menuButton);
        }

    }

    // Places the buttons of the category in the menu items area 
    /**
     * This function places the buttons of the specified category on the screen
     * @param category - category name (string)
     */
    @FXML
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
                button.getStyleClass().add("clicked-button");
            } else if (clickedNames.contains(button.getText())) {
                button.getStyleClass().add("clicked-button");
                clickedButtons.add(button);
            }

            menuGrid.add(button, col, row);
            col++;
            // Making sure the grid is 2 columns
            if(col == 2) {
                col = 0;
                row++;
            }
        }
        menuItemsArea.getChildren().add(menuGrid);
    }
}

