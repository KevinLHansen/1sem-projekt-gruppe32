package HomeAlone.business;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that contains the methods and attributes of players.
 * 
 * @author Gruppe 32
 */
public class Player extends Creature {

    private List<Item> inventory;
    private int maxItems = 3;
    private int itemCount = 0;
    
    /**
     * Class conctructor specifying the players name.
     * @param name String
     */
    public Player(String name) {
        super(name);
        //this.inventory = new Item[3];
        this.inventory = new ArrayList<>();
    }

    /**
     * Returns a string of items that the player is carrying.
     * This method returns a string containing the names of {@link HomeAlone.business.Item}
     * the player is carrying.
     * @return a string of item names
     */
    public String getInventory() {
        String items = "";
        // USING ARRAY LIST
        for (int i = 0; i < this.inventory.size(); i++) {
            items += this.inventory.get(i).getName() + ((i<this.inventory.size()-1) ? ", " : "");
        }
        return items;
    }

    /**
     * Adds an item to the players inventory.
     * This method takes an {@link HomeAlone.business.Item} as argument and adds to the List that
     * acts as the players inventory.
     * @param item
     * @return true if item is added, false is not
     */
    private boolean addToInventory(Item item) {
        boolean saved = true;
        // USING ARRAY LIST
        if(this.itemCount < this.maxItems) {
            this.inventory.add(item);
            this.itemCount++;
        } else {
            saved = false;
        }
        return saved;
    }

    /**
     * Removes an item from the players inventory.
     * This method takes a {@link HomeAlone.business.Item} as an argument and removes it from the
     * List that acts as the players inventory.
     * @param item 
     */
    private void removeFromInventory(Item item) {
        // USING ARRAY LIST
        if(this.itemCount > 0) {
            this.inventory.remove(item);
            this.itemCount--;
            this.errorList.remove("remove");
        } else {
            this.errorList.put("remove", "You can't remove an item from an empty inventory");
        }
    }

    /**
     * Searches for an item, using the item name.
     * This method takes a String and searches through the List that acts as
     * the players inventory.
     * If the {@link HomeAlone.business.Item} with the name is found, it is returned.
     * If there is no {@link HomeAlone.business.Item} with the given name, null is returned.
     * @param itemName
     * @return Item if one is found, null if it is not
     */
    private Item searchInventory(String itemName) {
        // USING ARRAY LIST
        for (Item item : this.inventory) {
            if(item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Adds a trap to a rooms inventory.
     * This method takes an {@link HomeAlone.business.Item} as argument, and adds it to
     * a {@link HomeAlone.business.Room} as a {@link HomeAlone.business.Trap}.
     * The {@link HomeAlone.business.Item} is then removed from the players inventory using the 
     * method {@link #removeFromInventory(Item item)}.
     * @param item 
     */
    private void setTrap(Item item) {
        Trap t = new Trap(item);
        t.setTrap();
        super.getCurrentRoom().addItem(t);
        removeFromInventory(item);
    }

    /**
     * Sets a trap if it is possible.
     * This method takes an item name and uses the 
     * method {@link #searchInventory(String itemName)} to find the actual {@link HomeAlone.business.Item} object
     * that the item name refers to.
     * Then it checks the {@link HomeAlone.business.Room} to see if the {@link HomeAlone.business.Trap} can be set there.
     * If it can be set it uses the method {@link #setTrap(Item item)} to set the trap and returns true.
     * If it can not be set it returns false.
     * @param itemName String
     * @return true if {@link HomeAlone.business.Trap} is set, false if not
     */
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
                return true;
            } else {
                this.errorList.put("setup", "Can't put " + itemName + " here.\n");
                return false;
            }
        }
    }

    /**
     * Picks up an item from a Room and adds it the Players inventory.
     * This method takes an item name as argument and calls the 
     * method {@link HomeAlone.business.Room#getRealItem(java.lang.String)} to get the {@link HomeAlone.business.Item} object.
     * The method is used differently depending on phases. In phase 3 it checks
     * for the special {@link HomeAlone.business.Usable} item Phone, that is part of the conditions.
     * It also checks if the {@link HomeAlone.business.Item} found is a {@link HomeAlone.business.Trap} as that can not
     * be picked up once it is set.
     * If it finds an {@link HomeAlone.business.Item} it then calls the 
     * method {@link #addToInventory(Item item)}.
     * If the {@link HomeAlone.business.Item} is added to the inventory the method the returns true.
     * If it is not added the method returns false.
     * @param secondWord String
     * @return true item is picked up, false if it is not picked up
     */
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
    
    /**
     * Drops an item.
     * This method takes an item name, calls the method {@link #searchInventory(String itemName)}
     * and if it finds an {@link HomeAlone.business.Item} it removes it from the players inventory
     * using the method {@link #removeFromInventory(Item item)} and adds it the 
     * inventory of the {@link HomeAlone.business.Room} using {@link HomeAlone.business.Room#addItem(HomeAlone.business.Item)}.
     * @param itemName String
     * @return true if {@link HomeAlone.business.Item} is dropped, false if it is not
     */
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
