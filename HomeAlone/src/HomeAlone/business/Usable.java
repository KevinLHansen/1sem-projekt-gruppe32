package HomeAlone.business;

/**
 * Class that contains the methods and attributes specific for Usable items.
 * 
 * @author Gruppe 32
 */
public class Usable extends Item {

    private boolean itemCollectible = false;
    private boolean itemUsed = false;
    
    /**
     * Class constructor specifying name and size.
     * @param itemName String
     * @param size int
     */
    public Usable(String itemName, int size) {
        super(itemName, size);
    }
    
    /**
     * Getter method for attribute {@link #itemCollectible}
     * @return boolean
     */
    public boolean isItemCollectible() {
        return itemCollectible;
    }

    /**
     * Setter method for attribute {@link #itemCollectible}
     * @param itemCollectible boolean
     */
    public void setItemCollectible(boolean itemCollectible) {
        this.itemCollectible = itemCollectible;
    }

    /**
     * Setter method for attribute {@link #itemUsed}
     * @param itemUsed boolean
     */
    public void setItemUsed(boolean itemUsed) {
        this.itemUsed = itemUsed;
    }

    /**
     * Getter method for attribute {@link #itemUsed}
     * @return boolean
     */
    public boolean isItemUsed() {
        return itemUsed;
    }
    
}
