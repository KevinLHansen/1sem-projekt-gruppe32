package worldofzuul;

import java.util.ArrayList;
import java.util.List;

/**
 * Needs methods specific to the wet bandits, Harry and Marv
 *
 * @author Mathias
 */
public class Nonplayer extends Creature {

    private List<Room> pathList;
    private Room[] path;

    public Nonplayer(String name) {
        super(name);
        this.pathList = new ArrayList<>();
    }

    public void setPathList(Room room) {
        this.pathList.add(room);
    }

    public void setPath() {
        this.path = this.pathList.toArray(new Room[0]);
    }
}
