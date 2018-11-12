package HomeAlone;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Room {

    private String description;
    private HashMap<String, Room> exits;
    private Trap trap;
    private String info;
    private List<Item> items = new ArrayList<Item>();
    private static final String ITEM_STRING = "Items:";

    public Room(String description) {
        this.description = description;
        this.info = "";
        exits = new HashMap<String, Room>();
        items = new ArrayList<Item>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    private String getItemString() {
        if (items.isEmpty()) {
            return "";
        }
        String returnString = ITEM_STRING;
        for (Item i : items) {
            returnString += " " + i;
        }
        return returnString + "\n";
    }

    public Item getRealItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public String getShortDescription() {
        return description;
    }

    public String getLongDescription() {
        return "You are at/in the " + description + ".\n" + getExitString() + getItemString();
    }

    private String getExitString() {
        String returnString = "Exits: ";
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString += exit + ", ";
        }
        return returnString;
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
    
    public int getNumberOfExits() {
        return this.exits.size();
    }

    public String getExitFromIndex(int index) {
        int i=0;
        for(Map.Entry<String, Room> entry: this.exits.entrySet()) {
            if(index==i) {
                return entry.getKey();
            }
            i++;
        }
        /* Possible exception point */
        return "Exit not found";
    }
    /**
     * Checks inventory for set traps, return true if any found
     * @return
     */
    public Trap checkTraps() {
        Trap trap;
        //boolean returnVal = false;
        for (Item item : this.inventory) {
            if(item instanceof Trap) {
                trap = (Trap)item;
                if(trap.checkTrapSet()) {
                    return trap;
                }
            }
        }
        return null;
    }
}

}
