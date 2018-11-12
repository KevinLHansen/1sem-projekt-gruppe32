package HomeAlone;

/**
 * Has specific methods for player, Kevin Needs methods for some of the commands
 * like examine, collect
 *
 * @author Gruppe 32
 */
public class Player extends Creature {

    private Item[] inventory;

    public Player(String name) {
        super(name);
        this.inventory = new Item[3];
    }

    public String getInventory() {
        String items = "";
        int j = 0;
        for (Item i : inventory) {
            // Check if there is an item
            if (i != null) {
                //                        Check for last item in array, no "," needed if its the last.
                items += i.toString() + ((this.inventory.length < j) ? ", " : "");
            } // If there is no item, no need to finish the loop.
            else {
                break;
            }
            j++;
        }
        return items;
    }

    public boolean addToInventory(Item item) {
        boolean saved = true;
        // Traverse inventory array to find first available spot
        int tail = -1;
        for (int i = 0; i < this.inventory.length; i++) {
            if (this.inventory[i] == null) {
                tail = i;
                break;
            }
        }
        if (tail >= 0) {
            this.inventory[tail] = item;
        } else {
            // No room in inventory
            saved = false;
        }
        //super.getCurrentRoom().removeItemFromInventory(item);
        return saved;
    }

    public void removeFromInventory(Item item) {
        for (int i = inventory.length; i > 0; i--) {
            if (item.equals(i)) {
                inventory[i] = null;
            }
        }

    }

    public Item searchInventory(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void setTrap(Item item) {
        Trap t = (Trap)item;
        t.setTrap();
        //super.getCurrentRoom().addToInventory(t);
        removeFromInventory(item);
    }
}
