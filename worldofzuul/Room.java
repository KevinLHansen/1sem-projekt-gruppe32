package worldofzuul;

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

    public void defineTrap(Item item) {
        Trap trap = new Trap(item);
        this.trap = trap;
    }

}
