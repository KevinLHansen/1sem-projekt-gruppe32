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
public class Item {

    private String name;
    private int size; // Used for inventory management, future
    private String type; // Trap, usable item(bb gun), part of trap

    // No argument constructor, in case of extending the class
    public Item() {
        // Call to the all argument constructor, to initiate the variables
        this("", 0, "");
    }

    // All argument constructor
    public Item(String name, int size, String type) {
        this.name = name;
        this.size = size;
        this.type = type;
    }

    // Getter and Setter functions for the class attributes
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.name + "(" + this.type + ")";
    }
}
