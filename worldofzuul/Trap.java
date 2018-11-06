package worldofzuul;

/**
 *
 * @author Mathias
 */
public class Trap extends Item {
    private boolean isSet;
    
    //Constructor
    public Trap() {
        this.isSet = false;
    }
    
    public boolean checkTrapSet() {
        return this.isSet;
    }
    
    // setTrap()
    public void setTrap() {
        this.isSet = true;
    }
}
