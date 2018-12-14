package HomeAlone.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Gruppe 32
 */
public class Nonplayer extends Creature {

    private List<Room> pathList;
    private Room[] currentPath;
    private Map<Integer, Room[]> paths;
    private int step;
    private int delay; // Trap sprung

    public Nonplayer(String name) {
        this(name, 3);

    }

    public Nonplayer(String name, int paths) {
        super(name);
        this.pathList = new ArrayList<>();
        this.step = 0;
        this.delay = -1;
        this.paths = new HashMap<>();
    }

    public void addExitToPath(Room room) {
        this.pathList.add(room);
    }

    public void createPath() {
        Room[] path = this.pathList.toArray(new Room[0]);
        this.paths.put(this.paths.size() + 1, path);
        this.pathList.clear();
    }

    public void setCurrentPath(int path) {
        this.currentPath = this.paths.get(path);
    }

    /**
     * Walk the path created
     */
    public boolean walkPath() {
        if(this.delay <= 0) {
            if (this.currentPath.length > this.step) {
                Room r = this.currentPath[this.step];
                
                if(!checkExitTrap(this.getCurrentRoom().getRoomID(), r)) {
                    super.setCurrentRoom(r);
                    this.step++;
                }
            }
            return true;
        } else {
            this.delay--;
            return false;
        }
    }
    
    public boolean checkExitTrap(int roomID, Room nextRoom) {
        // North-East Gardens
        if (roomID == 15) {

            // Checks if Harry walks into the kitchen and meets the blowtorch trap.
            if (nextRoom.getRoomID() == 4) {
                Trap trap = nextRoom.checkTraps();
                if (!(null == trap)) {
                    if (trap.getName().equals("Blowtorch")) {
                        this.delay = trap.getDelay();
                        return false; // Trap is sprung, but he is actually inside the kitchen when it happens
                    }
                }
            }

            // Checks if there's slippery steps when Marv walks downstairs to the basement.
            if (nextRoom.getRoomID() == 10) {
                Trap trap = super.getCurrentRoom().checkTraps();
                if (!(null == trap)) {
                    this.delay = trap.getDelay();
                    return false; // It is slippery, but he falls down the stairs to the basement door
                }
            }

            // Basement
        } else if (roomID == 10) {

            // Checks if Marv walks into the tar and nail trap, and changes Marv's path if the trap has been sprung
            if (nextRoom.getRoomID() == 4) {
                Trap trap = super.getCurrentRoom().checkTraps();
                if (!(null == trap)) {
                    this.delay = trap.getDelay();
                    this.setCurrentPath(2);
                    step = 0; // Reset the step as the new path starts at 0
                    return true;
                }
            }
            
            // Checks if there's slippery steps when Marv walks up the stairs from the basement.
            if (nextRoom.getRoomID() == 15) {
                Trap trap = nextRoom.checkTraps();
                if (!(null == trap)) {
                    this.delay = trap.getDelay();
                    return false; // It is slippery, but he struggles up using a banister
                }
            }

            // North-West Gardens
        } else if (roomID == 13) {

            // Checks if the ornaments trap has been set when Marv climbs through the window.
            if (nextRoom.getRoomID() == 2) {
                Trap trap = nextRoom.checkTraps();
                if (!(null == trap)) {
                    if (trap.getName().equals("Ornaments")) {
                        this.delay = trap.getDelay();
                        return false;// Trap is sprung, but he is actually in the living room when it happens
                    }
                }
            }

            // Foyer
        } else if (roomID == 1) {
            Trap trap = super.getCurrentRoom().checkTraps();

            // Checks if the enemy collides with the toy car trap, and removes its functionality.
            if (!(null == trap)) {
                if (trap.getName().equals("Toy cars")) {
                    this.delay = trap.getDelay();
                    this.getCurrentRoom().removeItem(trap);
                    return true;
                }
            }

            // Checks if one enemy collides with the swinging paint bucket trap on their way to the second floor, and removes its functionality.
            if (nextRoom.getRoomID() == 5) {
                trap = nextRoom.checkTraps();
                if (!(null == trap)) {
                    if (trap.getName().equals("Paint bucket")) {
                        this.delay = trap.getDelay();
                        nextRoom.removeItem(trap);
                        return true;
                    }
                }
            }

            // Second-floor hallway
        } else if (roomID == 6) {

            //Checks if the enemy collides with the tripwire trap, and removes its functionality
            Trap trap = super.getCurrentRoom().checkTraps();

            if (!(null == trap)) {
                if (trap.getName().equals("Yarn")) {
                    this.delay = trap.getDelay();
                    this.getCurrentRoom().removeItem(trap);
                    return true;
                }
            }

            // Porch
        } else if (roomID == 12) {

            // Checks if Harry slips on the porch steps.
            Trap trap = super.getCurrentRoom().checkTraps();
            if (!(null == trap)) {
                if (trap.getName().equals("Bucket")) {
                    this.delay = trap.getDelay();
                    return true;
                }
            }

            //Checks if the charcoal BBQ starter trap has been put on the front door, and changes Harry's path.
            if (nextRoom.getRoomID() == 1) {
                trap = nextRoom.checkTraps();
                if (!(null == trap)) {
                    if (trap.getName().equalsIgnoreCase("Charcoal BBQ Starter")) {
                        this.delay = trap.getDelay();
                        System.out.println("new path");
                        this.setCurrentPath(2);
                        step = 0; // Reset the step as the new path starts at 0
                        this.delay += 2; // Extra delay since the new path starts in NE Garden
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int getDelay() {
        return this.delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
