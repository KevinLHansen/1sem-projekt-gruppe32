/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HomeAlone.business;

/**
 *
 * @author Gruppe 32
 */
public class Item {

    private String name;
    private int size; // Used for inventory management - weight, future

    // No argument constructor, in case of extending the class
    public Item() {
        this("", 0);
    }

    // All argument constructor
    public Item(String name, int size) {
        this.name = name;
        this.size = size;
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

    @Override
    public String toString() {
        return this.name;// + "(" + this.type + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        } else {
            Item i = (Item) o;
            return this.name.equals(i.getName());
        }
    }
}
