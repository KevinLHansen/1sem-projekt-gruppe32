package HomeAlone.business;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room {

    private String description;
    private HashMap<String, Room> exits;
    private List<Trap> trap;
    private String info;
    private List<Item> items = new ArrayList<Item>();
    private int roomID;

    public Room(String description) {
        this.description = description;
        this.info = "";
        exits = new HashMap<String, Room>();
        items = new ArrayList<>();
        trap = new ArrayList<>();
        roomID = 0;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItemList() {
        return items;
    }

    public String getItems() {
        String items = "";
        int j = 1;
        for (Item item : this.items) {
            items += item.toString() + ((this.items.size() > j) ? ", " : "");
            j++;
        }
        return items;
    }
    
    public void removeItem(Item item) {
        items.remove(item);
    }

    public String getItemString() {
        if (items.isEmpty()) {
            return "";
        }
        String returnString = "";
        int j = 1;
        for (Item i : items) {
            if(!(i instanceof Trap)) {
            returnString += " " + i + ((this.items.size() > j) ? ", " : "");
            j++;
            }
        }
        return returnString;
    }
    
    public String getTrapString() {
        if (items.isEmpty()) {
            return "";
        }
        String returnString = "";
        int j = 1;
        for (Item i : items) {
            if(i instanceof Trap) {
            returnString += " " + i + ((this.items.size() > j) ? ", " : "");
            j++;
            }
        }
        return returnString;
    }

    public Item getRealItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
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
        return "You are at/in the " + description + ".\n" + getExitString();
    }

    public String getExitString() {
        String returnString = "";
        Set<String> keys = exits.keySet();
        int j = 1;
        for (String exit : keys) {
            returnString += exit + ((this.exits.size() > j) ? ", " : "");
            j++;
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
        return "Exit not found";
    }
    /**
     * Checks inventory for set traps, return true if any found
     * @return
     */
    public String checkTrapSet() {
        Trap trap;
        for (Item item : this.items) {
            if(item instanceof Trap) {
                trap = (Trap)item;
                if(trap.checkTrapSet()) {
                    return trap.getName();
                }
            }
        }
        return "";
    }
    
    public Trap checkTraps() {
        Trap trap;
        for (Item item : this.items) {
            if(item instanceof Trap) {
                trap = (Trap)item;
                if(trap.checkTrapSet()) {
                    return trap;
                }
            }
        }
        return null;
    }
    
    public void defineTrap(Item item) {
        Trap trap = new Trap(item);
        this.trap.add(trap);
    }
    
    public List<Trap> getPossibleTraps() {
        return this.trap;
    }
}
