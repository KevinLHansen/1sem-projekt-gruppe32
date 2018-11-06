package worldofzuul;

/**
 * Needs commands for some of the commands like go
 *
 * @author Gruppe 32
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

    public String getInventory() {
        String items = "";
        for (Item i : inventory) {
            if (i != null) {
                // Need check if last item
                items += i.toString() + ", ";
            }
        }
        return items;
    }

    public boolean addToInventory(Item inventory) {
        boolean saved = true;
        // Traverse inventory array to find first available spot
        int tail = -1;
        for (int i = 0; i < this.inventory.length; i++) {
            if (this.inventory[i] == null) {
                tail = i;
                break;
            }
        }
        if (tail >= 0) {
            this.inventory[tail] = inventory;
        } else {
            // No room in inventory
            saved = false;
        }
        return saved;
    }
}
