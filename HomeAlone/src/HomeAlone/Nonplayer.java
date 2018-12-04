package HomeAlone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Needs methods specific to the wet bandits, Harry and Marv multiple paths
 * depending on traps set or not? set new path at each trap?
 *
 * @author Gruppe 32
 */
public class Nonplayer extends Creature {

    private List<Room> pathList;
    private Room[] currentPath;
    private Map<Integer, Room[]> paths;
    private int step;
    private int delay; // Trap sprung

    // If we decide on random paths
    private Room previousRoom;

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

    /**
     * Create the array that represents the path. - Depends on the answers to
     * the 2 questions in the top
     */
    public void createPath() {
        /*int i = 0;
         for (Room room : pathList) {
         this.path[i] = room;
         i += 10;
         }*/
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
    public void walkPath() {
        if (this.currentPath.length > this.step) {
            Room nextRoom = this.currentPath[this.step];

            checkExitTrap(this.getCurrentRoom().getRoomID(), this.getCurrentRoom().getRoomID());

            this.setPreviousRoom();
            super.setCurrentRoom(nextRoom);
            this.step++;
            Trap trap = super.getCurrentRoom().checkTraps();
            if (!(null == trap)) {
                this.delay = trap.getDelay();
            }
            System.out.println(this.getName() + " is in: " + super.getCurrentRoom().getShortDescription());
        } else {
            System.out.println(this.getName() + " has reach the end. Reciding in " + super.getCurrentRoom().getShortDescription());
        }
    }

    public boolean checkExitTrap(int roomID, int nextRoomID) {
        // North-East Gardens
        if (roomID == 15) {

            // Checks if Harry walks into the kitchen and meets the blowtorch trap.
            if (nextRoomID == 4) {
                Trap trap = super.getCurrentRoom().checkTraps();
                if (!(null == trap)) {
                    if (trap.getName().equals("Blowtorch")) {
                        this.delay = trap.getDelay();
                        return true;
                    }
                }
            }

            // Checks if there's slippery steps when Marv walks downstairs to the basement.
            if (nextRoomID == 10) {
                Trap trap = super.getCurrentRoom().checkTraps();
                if (!(null == trap)) {
                    this.delay = trap.getDelay();
                    return true;
                }
            }

            // Basement
        } else if (roomID == 10) {

            // Checks if Marv walks into the tar and nail trap, and changes Marv's path if the trap has been sprung
            if (nextRoomID == 4) {
                Trap trap = super.getCurrentRoom().checkTraps();
                if (!(null == trap)) {
                    this.delay = trap.getDelay();
                    // TO-DO: Change path
                    return true;
                }
            }

            // North-West Gardens
        } else if (roomID == 13) {

            // Checks if the ornaments trap has been set when Marv climbs through the window.
            if (nextRoomID == 2) {
                Trap trap = super.getCurrentRoom().checkTraps();
                if (!(null == trap)) {
                    if (trap.getName().equals("Ornaments")) {
                        this.delay = trap.getDelay();
                        return true;
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
                    // TO-DO: Remove trap
                    return true;
                }
            }

            // Checks if one enemy collides with the swinging paint bucket trap on their way to the second floor, and removes its functionality.
            if (nextRoomID == 5) {
                trap = super.getCurrentRoom().checkTraps();
                if (!(null == trap)) {
                    if (trap.getName().equals("Paint bucket")) {
                        this.delay = trap.getDelay();
                        // TO-DO: Remove trap
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
                    // TO-DO: Remove trap
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

            //Checks if the heater trap has been put on the front door, and changes Harry's path.
            if (nextRoomID == 1) {
                trap = super.getCurrentRoom().checkTraps();
                if (!(null == trap)) {
                    if (trap.getName().equals("Heater")) {
                        this.delay = trap.getDelay();
                        // TO-DO: Change path
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void decreaseDelay() {
        this.delay--;
    }

    public int getDelay() {
        int tmpDelay = this.delay;
        this.decreaseDelay();
        return tmpDelay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    private void setPreviousRoom() {
        this.previousRoom = super.getCurrentRoom();
    }
}
