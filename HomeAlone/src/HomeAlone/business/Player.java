package HomeAlone.business;

import java.util.ArrayList;
import java.util.List;

/**
 * Has specific methods for player, Kevin Needs methods for some of the commands
 * like examine, collect
 *
 * @author Gruppe 32
 */
public class Player extends Creature {

    //private Item[] inventory;
    private List<Item> inventory;
    private int maxItems = 3;
    private int itemCount = 0;

    public Player(String name) {
        super(name);
        //this.inventory = new Item[3];
        this.inventory = new ArrayList<>();
    }

    public String getInventory() {
        String items = "";
        // USING ARRAY LIST
        for (int i = 0; i < this.inventory.size(); i++) {
            items += this.inventory.get(i).getName() + ((i<this.inventory.size()-1) ? ", " : "");
        }
        System.out.println(items);
        /* USING ARRAY
        int j = 0;
        for (Item i : inventory) {
            // Check if there is an item
            if (i != null) {
                items += i + ((this.inventory.length > j) ? ", " : "");
            }
            j++;
        }*/
        return items;
    }

    private boolean addToInventory(Item item) {
        boolean saved = true;
        // USING ARRAY LIST
        if(this.itemCount < this.maxItems) {
            this.inventory.add(item);
            this.itemCount++;
        } else {
            saved = false;
        }
        /* USING ARRAY
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
        */
        return saved;
    }

    private void removeFromInventory(Item item) {
        // USING ARRAY LIST
        if(this.itemCount > 0) {
            this.inventory.remove(item);
            this.itemCount--;
            this.errorList.remove("remove");
        } else {
            this.errorList.put("remove", "You can't remove an item from an empty inventory");
        }
        /* USING ARRAY
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
        }*/
    }

    private Item searchInventory(String itemName) {
        // USING ARRAY LIST
        for (Item item : this.inventory) {
            if(item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        /* USING ARRAY
        for(int i=0;i<this.inventory.length;i++)  {
            if (inventory[i] != null) {
                if (itemName.equalsIgnoreCase(inventory[i].getName())) {
                    return inventory[i];
                }
            }
        }*/
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
            this.errorList.put("setup", "What item? " + itemName + " not found.");
            return false;
        } else {
            List<Trap> traps = this.getCurrentRoom().getPossibleTraps();
            
            if(traps.contains(item)) {
                this.setTrap(item);
                this.errorList.remove("setup");
                return true; //"You set up a trap using " + item + "\n";
            } else {
                this.errorList.put("setup", "Can't put " + itemName + " here.\n");
                return false;
            }
        }
    }


    public boolean pickupItem(String secondWord) {
        
        Item item = this.getCurrentRoom().getRealItem(secondWord);
        if (item == null) {
            this.errorList.put("pickup","Invalid item or no item found. Try again.\n");
            return false;
        } else if(item instanceof Trap) {
            this.errorList.put("pickup","Traps can't be picked up once they have been placed.\n");
            return false;
        } else if (item instanceof Usable) {
            if(Game.getInstance().getPhase() == 3) {
                this.errorList.put("pickup","You pick up the phone and call the police. Get to the attic and zipline to safety!\n");
                Usable usable = (Usable)item; // typecast item to Usable
                usable.setItemUsed(true);
            }
            else {
                this.errorList.put("pickup","Why would you call someone now? Setup the traps first!\n");
            }
            return false;
        } else {
            if(this.addToInventory(item)) {
                this.getCurrentRoom().removeItem(item);
                this.errorList.remove("pickup");
                return true;
            } else {
                this.errorList.put("pickup", "Inventory is full. Drop an item to make room.\n");
                return false;
            }
        }
    }
    
    public boolean dropCommand(String itemName) {
        Item item = this.searchInventory(itemName);
        if (item == null) {
            this.errorList.put("drop","What item? " + itemName + " not found.");
            return false;
        } else {
            this.getCurrentRoom().addItem(item);
            this.removeFromInventory(item);
            this.errorList.remove("drop");
            return true;
        }
    }
}
