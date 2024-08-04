package project2;

import java.util.Date;
// import java.util.List;

/**
 * This class contains all the details for each ingredient
 * @author Vittoria Cicalese
 */
public class IngredientDetails {
    private String name; 
    private int quantityBefore; // what class needs this
    private int quantity;  
    private String unit;
    private Date expDate;
    private String storageLocation;

    /**
     * Constructor for ingredient details 
     * @param name - String
     * @param quantityBefore - warning level for quantity (Int)
     * @param quantity - Current quantity (Int)
     * @param unit - Unit of measurement (String)
     * @param expDate - expiration date (Date)
     * @param storageLocation - Location (String)
     */
    public IngredientDetails(String name, int quantityBefore, int quantity, String unit, Date expDate, String storageLocation) {
        this.name = name;
        this.quantityBefore = quantityBefore;
        this.quantity = quantity;
        this.unit = unit;
        this.expDate = expDate;
        this.storageLocation = storageLocation;
    }

    /**
     * Default Constructor
     */
    public IngredientDetails() {
        //TODO: empty constructor
    }

    /**
     * Gets name of ingredient
     * @return Ingredient Name (String)
     */
    public String getName() {return name;}

    /**
     * This function modifies the name of ingredient
     * @param name Ingredient Name (String)
     */
    public void setName(String name) {this.name = name;}

    /**
     * This function returns the quantityBefore data member
     * @return Warning Quantity (int)
     */
    public int getQuantityBefore() {return quantityBefore;}

    /**
     * This function modifies the quantityBefore member
     * @param quantityBefore Warning Quantity (int)
     */
    public void setQuantityBefore(int quantityBefore) { this.quantityBefore = quantityBefore;}

    /**
     * This function returns the current quantity of the ingredient
     * @return Quantity of ingredient (int)
     */
    public int getQuantity() { return quantity;}

     /**
     * This function modifes the current quantity of the ingredient
     * @param quantity Ingredient Quantity (int)
     */
    public void setQuantity(int quantity) { this.quantity = quantity;}

    /**
     * This funtion returns the unit of the ingredient
     * @return Unit of ingredient (String)
     */
    public String getUnit() {return unit;}

    /**
     * This function modifies the units of the ingredient
     * @param unit Unit of ingredient (String)
     */
    public void setUnit(String unit) { this.unit = unit;}

    /**
     * This function returns the expiration date of ingredient 
     * @return Expiration Date of ingredient (Date)
     */
    public Date getExpDate() { return expDate;}

    /**
     * The function updates the expiration date
     * @param expDate Expiration Date of ingredient (Date)
     */
    public void setExpDate(Date expDate) {this.expDate = expDate;}

    /**
     * This function returns the storage location string
     * @return Storage Location (String)
     */
    public String getStorageLocation() {return storageLocation;}

    /**
     * This function modifes the storage location
     * @param storageLocation Storage Location (String)
     */
    public void setStorageLocation(String storageLocation) {this.storageLocation = storageLocation;}

    /**
     * This function combines all the attributes of the ingredient into one string
     */
    @Override
    public String toString() {
        return String.format("name: %s, quantity before: %d, current quantity: %d, unit: %s, expiration Date: %s, storage: %s",
                name, quantityBefore, quantity, unit, expDate.toString(), storageLocation);
    }
}
