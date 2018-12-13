package HomeAlone.business;

/**
 *
 * @author Mathias
 */
public class Usable extends Item {

// BB gun, for now - could be trap-components as well later on
    // Limited amount of shot on BB gun?
    // Limit where Usable Item can be used?

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
    
}
