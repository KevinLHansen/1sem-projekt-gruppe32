package HomeAlone.business;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that contains methods and attributes common for both Player and Nonplayer.
 * 
 * @author Gruppe 32
 */
public class Creature {

    private String name;
    private Room currentRoom;
    protected Map<String, String> errorList;

    /**
     * Constructor
     */
    public Creature() {
        this("");
    }
    
    /**
     * Class constructor specifying the name of the Creature.
     * @param name String
     */
    public Creature(String name) {
        this.name = name;
        errorList = new HashMap<>();
    }
    
    /**
     * Getter method for attribute name.
     * @return the String name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for attribute name.
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for attribute currentRoom.
     * @return Room object
     */
    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    /**
     * Setter method for the attribute currentRoom.
     * @param room Room object
     */
    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }
    
    /**
     * Method for getting an error message.
     * This method is called if there is an error from certain methods 
     * in {@link HomeAlone.business.Player}.
     * @param e String
     * @return empty string if no errors, else it returns the correct error-message
     */
    public String getError(String e) {
        String error = "";

        if(errorList.containsKey(e)) {
            error = errorList.get(e);
        }
        return error;
    }
    
    /**
     * Method used for travelling between rooms.
     * This method takes a string containing the name of a {@link HomeAlone.business.Room}, it then
     * fetches the actual object using {@link Room#getExit(java.lang.String)}.
     * If it finds the object, the method [@link #setCurrentRoom(Room room)} is called
     * and the method returns true.
     * If the object is not found the method returns false.
     * @param direction String
     * @return true if currentRoom is changed, false if it is not
     */
    public boolean goRoom(String direction) {
        Room nextRoom = this.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            return false;
        } else {
            // footstep sound every time player moves between rooms
            AudioFile footStepSound = null;
            footStepSound = new AudioFile("/resources/sfx/footStep.wav");
            footStepSound.playFile();
            
            this.setCurrentRoom(nextRoom);
            return true;
        }
    }
}
