package HomeAlone.business;

import HomeAlone.textUI.Command;
import java.util.ArrayList;
import java.util.List;

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
                items += i + ((this.inventory.length > j) ? ", " : "");
            }
            j++;
        }
        return items;
    }

    private boolean addToInventory(Item item) {
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

    private void removeFromInventory(Item item) {
        for (int i = inventory.length - 1; i >= 0; i--) {
            if (item.equals(inventory[i])) {
                inventory[i] = null;
            }
        }
        for(int i = 0; i < inventory.length;i++) {
            
            if(i < (inventory.length-1)) {
                if(inventory[i] == null) {
                    inventory[i] = inventory[i+1];
                    inventory[i+1] = null;
                }
            }
        }
    }

    private Item searchInventory(String itemName) {
        for(int i=0;i<this.inventory.length;i++)  {
            if (inventory[i] != null) {
                if (itemName.equalsIgnoreCase(inventory[i].getName())) {
                    return inventory[i];
                }
            }
        }
        return null;
    }

    private void setTrap(Item item) {
        Trap t = new Trap(item);
        t.setTrap();
        super.getCurrentRoom().addItem(t);
        removeFromInventory(item);
    }

    public boolean placeTrap(String itemName) {
        
        Item item = this.searchInventory(itemName);
        if (item == null) {
            errorList.put("setup", "What item? " + itemName + " not found.");
            return false;
        } else {
            List<Trap> traps = this.getCurrentRoom().getPossibleTraps();
            
            if(traps.contains(item)) {
                this.setTrap(item);
                return true; //"You set up a trap using " + item + "\n";
            } else {
                errorList.put("setup", "Can't put " + itemName + " here.\n");
                return false;
            }
        }
    }


    public boolean pickupItem(String secondWord) {
        
        Item item = this.getCurrentRoom().getRealItem(secondWord);
        if (item == null) {
            errorList.put("pickup","Invalid item or no item found. Try again.\n");
            return false;
        } else if(item instanceof Trap) {
            errorList.put("pickup","Traps can't be picked up once they have been placed.\n");
            return false;
        } else {
            if(this.addToInventory(item)) {
                this.getCurrentRoom().removeItem(item);
                return true;
            } else {
                errorList.put("pickup", "Inventory is full. Drop an item to make room.\n");
                return false;
            }
        }
    }
    
    public boolean dropCommand(String itemName) {
        Item item = this.searchInventory(itemName);
        if (item == null) {
            errorList.put("drop","What item? " + itemName + " not found.");
            return false;
        } else {
            this.getCurrentRoom().addItem(item);
            this.removeFromInventory(item);
            errorList.put("pickup", "");
            return true;
        }
    }
}
