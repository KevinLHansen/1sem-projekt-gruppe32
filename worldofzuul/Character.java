package worldofzuul;

/**
 * Needs commands for some of the commands like go
 *
 * @author Mathias
 */
public class Character {

    private String name;
    public Room currentRoom;

    // No argument constructor, in case of extending the class
    public Character() {
        // Call to the default constructor, to initiate the variables
        this("");
    }

    // Default constructor to use, when creating a Character object
    public Character(String name) {
        this.name = name;
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
}
