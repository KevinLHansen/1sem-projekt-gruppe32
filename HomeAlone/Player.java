package HomeAlone;

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
                //                        Check for last item in array, no "," needed if its the last.
                items += i + ((this.inventory.length > j) ? ", " : "");
            } // If there is no item, no need to finish the loop.
            else {
                //break;
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

    public void placeTrap(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Set up what?");
            System.out.println(this.getInventory());
            return;
        }

        String secondWord = command.getSecondWord();
        Item item = this.searchInventory(secondWord);
        if (item == null) {
            System.out.println("What item? " + secondWord + " not found.");
        } else {
            List<Trap> traps = this.getCurrentRoom().getPossibleTraps();
            
            if(traps.contains(item)) {
                this.setTrap(item);
                System.out.println("You set up a trap using " + item);
            } else {
                System.out.println("Can't put " + item + " here.");
            }
        }
    }

    public void collectItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Collect what?");
            System.out.println(this.getCurrentRoom().getItems());
            return;
        }

        String secondWord = command.getSecondWord();
        Item item = this.getCurrentRoom().getRealItem(secondWord);
        if (item == null) {
            System.out.println("Invalid item or no item found. Try again.");
        } else if(item instanceof Trap) {
            System.out.println("Traps can't be picked up once they have been placed.");
        } else {
            if(this.addToInventory(item)) {
                this.getCurrentRoom().removeItem(item);
                System.out.println("You pick up " + item + " from " + this.getCurrentRoom().getShortDescription());
            } else {
                System.out.println("Inventory is full. Drop an item to make room.");
            }
        }
    }
    
    public void dropCommand(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Drop what?");
            System.out.println(this.getInventory());
            return;
        }

        String secondWord = command.getSecondWord();
        Item item = this.searchInventory(secondWord);
        if (item == null) {
            System.out.println("What item? " + secondWord + " not found.");
        } else {
            this.getCurrentRoom().addItem(item);
            this.removeFromInventory(item);
            System.out.println("You dropped " + item);
        }
    }
}
