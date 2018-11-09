package worldofzuul;

/**
 *
 * @author Gruppe 32
 */
public class Trap extends Item {
    private boolean isSet;
    private int delay;
    
    //Constructor
    public Trap() {
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
