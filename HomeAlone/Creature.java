package worldofzuul;

/**
 * Needs commands for some of the commands like go
 *
 * @author Mathias
 */
public class Creature {

    private String name;
    private Room currentRoom;

    // No argument constructor, in case of extending the class
    public Creature() {
        // Call to the default constructor, to initiate the variables
        this("");
    }

    // Default constructor to use, when creating a Creature object
    public Creature(String name) {
        this.name = name;
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
}
