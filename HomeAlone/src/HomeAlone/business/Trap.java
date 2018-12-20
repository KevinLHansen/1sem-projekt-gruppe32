package HomeAlone.business;

/**
 * Class that contains the method and attributes specific for a trap.
 * 
 * @author Gruppe 32
 */
public class Trap extends Item {
    private boolean isSet;
    private int delay;
    
    /**
     * Class constructor
     */
    public Trap() {
        this("", 0, false, 4);
    }
    
    /**
     * Class constructor that converts an Item to a Trap
     * @param item Item object
     */
    public Trap(Item item) {
        this(item.getName(), item.getSize(), false, 4);
    }
    
    /**
     * Class constructor specifying the name and size of the trap.
     * @param name String
     * @param size int
     */
    public Trap(String name, int size) {
        this(name, size, false, 4);
    }
    
    /**
     * Class constructor specifying name, size, isSet and delay of the trap.
     * @param name String
     * @param size int
     * @param isSet boolean
     * @param delay int
     */
    public Trap(String name, int size, boolean isSet, int delay) {
        super(name, size);
        this.isSet = false;
        this.delay = delay;
    }
    
    /**
     * Getter method for the attribute {@link #isSet}.
     * @return true or false
     */
    public boolean checkTrapSet() {
        return this.isSet;
    }
    
    /**
     * Setter method for the attribute {@link #isSet}.
     */
    public void setTrap() {
        this.isSet = true;
    }
    
    /**
     * setter method for the attribute {@link #delay}
     * @param delay int
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }
    
    /**
     * Getter method for the attribute {@link #delay}
     * @return integer value, containing the delay the trap caused
     */
    public int getDelay() {
        return this.delay;
    }
}
