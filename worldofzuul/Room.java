package worldofzuul;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.List;

public class Room {

    private String description;
    private HashMap<String, Room> exits;
    private String info;
    private boolean trapSet;
    private List<Item> items = new ArrayList<Item>();

    public Room(String description) {
        this.description = description;
        this.info = "";
        exits = new HashMap<String, Room>();
        items = new ArrayList<Item>();
    }    

    public void addItem (Item item){
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }
    
    public void removeItems(Item item){
        items.remove(item);
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

}
