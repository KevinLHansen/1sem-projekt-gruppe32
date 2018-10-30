/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

/**
 *
 * @author Mathias
 */
public class Character {

    private String name;
    private boolean npc; // Candidate for subclass?
    private Item[] inventory; // Only PC(Kevin)

    // No argument constructor, in case of extending the class
    public Character() {
        // Call to the default constructor, to initiate the variables
        this("", false);
    }

    // Default constructor to use, when creating a Person object
    public Character(String name, boolean npc) {
        this.name = name;
        this.npc = npc;
        this.inventory = new Item[3];
    }

    // Getter and Setter functions for the class attributes
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNpc() {
        return npc;
    }

    public void setNpc(boolean npc) {
        this.npc = npc;
    }

    public String getInventory() {
        String items = "";
        for (Item i : inventory) {
            if (i != null) {
                // Need check if last item
                items += i.toString() + ", ";
            }
        }
        return items;
    }

    public boolean addToInventory(Item inventory) {
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
            this.inventory[tail] = inventory;
        } else {
            // No room in inventory
            saved = false;
        }
        return saved;
    }
}
