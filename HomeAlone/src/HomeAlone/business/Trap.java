package HomeAlone.business;

/**
 *
 * @author Gruppe 32
 */
public class Trap extends Item {
    private boolean isSet;
    private int delay;
    
    //Constructor
    public Trap() {
        this("", 0, false, 4);
    }
    
    public Trap(Item item) {
        this(item.getName(), item.getSize(), false, 4);
    }
    
    public Trap(String name, int size) {
        this(name, size, false, 4);
    }
    
    public Trap(String name, int size, boolean isSet, int delay) {
        super(name, size);
        this.isSet = false;
        this.delay = delay;
    }
    
    public boolean checkTrapSet() {
        return this.isSet;
    }
    
    // setTrap()
    public void setTrap() {
        this.isSet = true;
    }
    
    public void setDelay(int delay) {
        this.delay = delay;
    }
    
    public int getDelay() {
        return this.delay;
    }
}
