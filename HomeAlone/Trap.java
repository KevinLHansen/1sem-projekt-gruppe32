package HomeAlone;

/**
 *
 * @author Gruppe 32
 */
public class Trap extends Item {
    private boolean isSet;
    private int delay;
    
    //Constructor
    public Trap() {
        this("", 0, false, 10);
    }
    
    public Trap(String name, int size) {
        this(name, size, false, 10);
    }
    
    public Trap(String name, int size, boolean isSet, int delay) {
        super(name, size);
        this.isSet = false;
        this.delay = 10;
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
