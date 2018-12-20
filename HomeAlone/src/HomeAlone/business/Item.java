package HomeAlone.business;

/**
 * Class that contains the methods and attributes common for Item, Trap and Usable.
 * 
 * @author Gruppe 32
 */
public class Item {

    private String name;
    private int size; // Used for inventory management - weight, future

    /**
     * Class constructor
     */
    public Item() {
        this("", 0);
    }

    /**
     * Class constructor specifying the name and size of the Item.
     * @param name String
     * @param size int
     */
    public Item(String name, int size) {
        this.name = name;
        this.size = size;
    }

    /**
     * Getter method for attribute {@link #name}.
     * @return string containing the items name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for the attribute {@link #name}.
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for attribute {@link #size}.
     * @return integer value containing the items size
     */
    public int getSize() {
        return size;
    }

    /**
     * Setter method for the attribute {@link #size}.
     * @param size int
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Override of the default toString() method.
     * This method overrides the default toString() method, so it returns a 
     * value that is relevant to the object.
     * @return string containing the items name
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Override the default equals() method.
     * This method overrides the default equals() method, so it compares objects
     * in a way that makes sense in the context of this program.
     * @param o Object
     * @return true if successful or false if not
     */
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
