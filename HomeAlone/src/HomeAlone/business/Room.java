package HomeAlone.business;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that contains methods and attributes for a room.
 * 
 * @author Gruppe 32/World of Zuul framework
 */
public class Room {

    private String description;
    private HashMap<String, Room> exits;
    private List<Trap> trap;
    private String info;
    private List<Item> items = new ArrayList<Item>();
    private int roomID;

    /**
     * Class constructor specifying a description
     * @param description String
     */
    public Room(String description) {
        this.description = description;
        this.info = "";
        exits = new HashMap<String, Room>();
        items = new ArrayList<>();
        trap = new ArrayList<>();
        roomID = 0;
    }

    /**
     * Getter method for the attribute {@link #roomID}
     * @return integer value containing the room id
     */
    public int getRoomID() {
        return roomID;
    }

    /**
     * Setter method for the attribute {@link #roomID}.
     * @param roomID int
     */
    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    /**
     * Adds an item to the rooms inventory.
     * This method adds an item to the List that is used as the rooms inventory.
     * @param item Item object
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Returns a list of available items in the room.
     * This method returns a List of {@link Item}s that is used as the rooms
     * inventory.
     * @return List of {@link HomeAlone.business.Item} objects
     */
    public List<Item> getItemList() {
        return items;
    }

    /**
     * Returns the inventory as a string of item names.
     * This method returns a string of item names, build using the List that is 
     * used as the rooms inventory.
     * @return String of item names
     */
    public String getItems() {
        String items = "";
        int j = 1;
        for (Item item : this.items) {
            items += item.toString() + ((this.items.size() > j) ? ", " : "");
            j++;
        }
        return items;
    }
    
    /**
     * Removes an item from the rooms inventory.
     * This method removes an item from the List that is used as the rooms
     * inventory.
     * @param item Item object
     */
    public void removeItem(Item item) {
        items.remove(item);
    }

    /**
     * Same functionality as {@link #getItems()}.
     * @return String of item names
     */
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
    
    /**
     * Returns a string with the name of all the possible traps for the room.
     * This method returns a string that contains all the traps it is possible
     * to set up in the room.
     * @return empty string, or string of item names
     */
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

    /**
     * Returns the {@link HomeAlone.business.Item} object.
     * This method return the {@link HomeAlone.business.Item} object that has the item name
     * provided.
     * @param itemName String
     * @return null or an Item object
     */
    public Item getRealItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Adds an exit to the HashMap of exits set on the room.
     * This method adds a new exit to the HashMap that contains the exits for 
     * the room. It is only called during the initialisation of the game.
     * @param direction String
     * @param neighbor Room object
     */
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    /**
     * Returns the short description of the room.
     * This method returns the short description that is specified in the class
     * constructor.
     * @return string
     */
    public String getShortDescription() {
        return description;
    }

    /**
     * Returns the short description of the room and a list of exits.
     * This method returns the short description that is specified in the class
     * constructor as well as a list of available exits in the form of a string.
     * @return String
     */
    public String getLongDescription() {
        return "You are at/in the " + description + ".\n" + getExitString();
    }

    /**
     * Returns a list of exits as a string.
     * This method returns the keys from the HashMap of available exits as a
     * comma separated string.
     * @return String
     */
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

    /**
     * Returns the object associated with a specific exit.
     * This method returns the Room object that is associated with the given
     * string. The association is in the HashMap.
     * @param direction String
     * @return Room object
     */
    public Room getExit(String direction) {
        return exits.get(direction);
    }

    /**
     * Setter method for the attribute {@link #info}.
     * @param info String
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Getter method for the attribute {@link #info}.
     * @return String
     */
    public String getInfo() {
        return info;
    }
    
    /**
     * Returns the amount of exits in the room.
     * This method returns the amount of exits available in the room.
     * @return integer value containing the number of available exits
     */
    public int getNumberOfExits() {
        return this.exits.size();
    }

    /**
     * Possible future usage.
     * This method was meant to be used in case the nonplayer entities would be
     * reprogrammed to use a random path and not the fixed paths they are using
     * currently.
     * @param index int
     * @return Room object
     */
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
     * Checks inventory for set traps.
     * This method checks the inventory to see if there are any traps set.
     * if there are it returns the name of the trap, if not then it returns
     * an empty string.
     * @return string
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
    
    /**
     * Returns the {@link HomeAlone.business.Trap} if one is set.
     * This method returns the {@link HomeAlone.business.Trap} object for a trap that have been set
     * in the room or null if no traps have been set.
     * @return Trap object or null
     */
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
    
    /**
     * Adds a trap to the list of possible traps in the room.
     * This method adds a {@link HomeAlone.business.Trap} to the List of possible traps that can
     * be set up in a room.
     * @param item Item object
     */
    public void defineTrap(Item item) {
        Trap trap = new Trap(item);
        this.trap.add(trap);
    }
    
    /**
     * Returns the list of possible traps.
     * This method returns a list of possible traps that can be set up in the
     * room.
     * @return List of traps or null
     */
    public List<Trap> getPossibleTraps() {
        return this.trap;
    }
}
