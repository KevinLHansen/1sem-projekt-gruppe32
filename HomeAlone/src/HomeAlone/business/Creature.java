package HomeAlone.business;

import java.util.HashMap;
import java.util.Map;

/**
 * Needs commands for some of the commands like go
 *
 * @author Gruppe 32
 */
public class Creature {

    private String name;
    private Room currentRoom;
    protected Map<String, String> errorList;

    // No argument constructor, in case of extending the class
    public Creature() {
        // Call to the default constructor, to initiate the variables
        this("");
    }

    // Default constructor to use, when creating a Creature object
    public Creature(String name) {
        this.name = name;
        errorList = new HashMap<>();
        errorList.put("pickup", "");
        //this.currentRoom = new Room();
    }

    // Getter and Setter functions for the class attributes
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }
    
    public String getError(String e) {
        String error = "";
        if(e.equalsIgnoreCase("pickup")) {
            error = errorList.get(e);
        } else if(e.equalsIgnoreCase("setup")) {
            error = errorList.get(e);
        } else if(e.equalsIgnoreCase("drop")) {
            error = errorList.get(e);
        }
        return error;
    }
    
    public boolean goRoom(String direction) {
        Room nextRoom = this.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            return false;
        } else {
            // footstep sound every time player moves between rooms
            AudioFile footStepSound = null;
            footStepSound = new AudioFile("sfx/footStep.wav");
            footStepSound.playFile();
            
            this.setCurrentRoom(nextRoom);
            return true;
        }
    }
}
