package HomeAlone.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Needs methods specific to the wet bandits, Harry and Marv 
 * multiple paths depending on traps set or not? 
 * set new path at each trap?
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
     * Create the array that represents the path.
     * - Depends on the answers to the 2 questions in the top
     */
    public void createPath() {
        /*int i = 0;
        for (Room room : pathList) {
            this.path[i] = room;
            i += 10;
        }*/
        Room[] path = this.pathList.toArray(new Room[0]);
        this.paths.put(this.paths.size() + 1, path);
    }

    public void setCurrentPath(int path) {
        this.currentPath = this.paths.get(path);
    }

    /**
     * Walk the path created
     */
    public String walkPath() {
        if (this.currentPath.length > this.step) {
            Room r = this.currentPath[this.step];

            this.setPreviousRoom();
            super.setCurrentRoom(r);
            this.step++;
            Trap trap = super.getCurrentRoom().checkTraps();
            if (!(null == trap)) {
                this.delay = trap.getDelay();
            }
            return this.getName() + " is in: " + super.getCurrentRoom().getShortDescription();
        } else {
            return this.getName() + " has reach the end. Standing in " + super.getCurrentRoom().getShortDescription();
        }
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
