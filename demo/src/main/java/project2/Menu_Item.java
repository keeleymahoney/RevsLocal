package project2;
import java.util.HashMap;

 /* Class that tracks attributes of a particular Menu Item
 * @author Keeley Mahoney
 */
public class Menu_Item {
    //name of the menu item
    private String itemName;

    //menu item price
    private double price;

    //menu item category: entree, sides, beverage, dessert, seasonal item
    private String menuCategory;
    
    //hash map that contains the ingredient details and quantity for each ingredient in the menu item
    private HashMap<IngredientDetails, Integer> ingredients;

    /**
     * Constructor that initializes a menu item given an item name, its price, and its category
     * @param itemName (String) name of menu item
     * @param price (double) price of menu item
     * @param menuCategory (String) menu item category
     */
    public Menu_Item(String itemName, double price, String menuCategory) {
        //set everything to its proper value
        this.itemName = itemName;
        this.price = price;
        this.menuCategory = menuCategory;

        //create new hash map for ingredients
        this.ingredients = new HashMap<>(); 
    }

    /**
     * Default constructor that just creates the ingredients hash map
     */
    public Menu_Item(){
        this.ingredients = new HashMap<>();
    };

    /**
     * Sets the ingredients of the menu item given a hash map containing all of the ingredients
     * @param ingredients HashMap of IngredientDetails and its quantity in menu item for each ingredient in menu item
     */
    public void setIngredients(HashMap<IngredientDetails, Integer> ingredients) {
        this.ingredients= ingredients;
    }

    /**
     * Getter function that returns the current ingredients in the menu item
     * @return ingredients, which is a Hash Map of IngredientDetails and integer
     */
    public HashMap<IngredientDetails, Integer> getIngredientDetails() {
        return this.ingredients;
    }


    /**
     * Sees if the given ingredient is present in the current menu item
     * @param name (String) which is the name of the ingredient
     * @return IngredientDetails which contains the IngredientDetails of the specified ingredient, or null if the ingredient does not exist
     */
    public IngredientDetails findIngredientByName(String name) {
        //for each ingredient in the current menu item
        for (IngredientDetails ingredient : this.ingredients.keySet()) {
            //if the ingredient already exists, return it
            if (ingredient.getName().equalsIgnoreCase(name)) {
                return ingredient;
            }
        }
        //if not, return null
        return null;
    }
    
    /**
     * Adds a specified ingredient to the menu item for a certain quantity
     * @param ingredient (IngredientDetails) ingredient details of the ingredient to add to the menu item
     * @param quantity (int) the amount of the ingredient in the menu item
     */
    public void addIngredient(IngredientDetails ingredient, int quantity) {
        //add to hash map
        ingredients.put(ingredient, quantity);
    }

    /**
     * Removes the ingredient from the ingredients list
     * @param ingredient (IngredientDetails) ingredient to remove
     */
    public void removeIngredient(IngredientDetails ingredient) {
        ingredients.remove(ingredient);
    }


    /**
     * Getter function for the itemName
     * @return String of the item name
     */
    public String getItemName() { return itemName; }

    /**
     * Sets the item name to the specified name
     * @param itemName (String) name of menu item
     */
    public void setItemName(String itemName) { this.itemName = itemName; }

    /**
     * Gets the price of the menu item
     * @return (Double) the price of the menu item
     */
    public double getPrice() { return price; }

    /**
     * Sets the menu item's price to be the specified value
     * @param price (double) price to be set to
     */
    public void setPrice(double price) { this.price = price; }

    /**
     * Gets the category of the menu item in the menu
     * @return (String) the menu category of the menu item
     */
    public String getCategory() { return menuCategory; }

    /**
     * Updates the menuCategory to be the specified value
     * @param menuCategory (String) name of category that menu item should be set to
     */
    public void setCategory(String menuCategory) { this.menuCategory = menuCategory; }

}