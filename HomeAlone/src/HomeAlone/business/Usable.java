package HomeAlone.business;

/**
 *
 * @author Gruppe 32
 */
public class Usable extends Item {

    private boolean itemCollectible = false;
    private boolean itemUsed = false;
    
    public Usable(String itemName, int size) {
        super(itemName, size);
    }
    
    public boolean isItemCollectible() {
        return itemCollectible;
    }

    public void setItemCollectible(boolean itemCollectible) {
        this.itemCollectible = itemCollectible;
    }

    public void setItemUsed(boolean itemUsed) {
        this.itemUsed = itemUsed;
    }

    public boolean isItemUsed() {
        return itemUsed;
    }
    
}
