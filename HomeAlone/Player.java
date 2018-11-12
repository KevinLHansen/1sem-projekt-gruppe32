package HomeAlone;

/**
 * Has specific methods for player, Kevin Needs methods for some of the commands
 * like examine, collect
 *
 * @author Gruppe 32
 */
public class Player extends Creature {

    private Item[] inventory;

    public Player(String name) {
        super(name);
        this.inventory = new Item[3];
    }

    public String getInventory() {
        String items = "";
        int j = 0;
        for (Item i : inventory) {
            // Check if there is an item
            if (i != null) {
                //                        Check for last item in array, no "," needed if its the last.
                items += i.toString() + ((this.inventory.length < j) ? ", " : "");
            } // If there is no item, no need to finish the loop.
            else {
                break;
            }
            j++;
        }
        return items;
    }

    private boolean addToInventory(Item item) {
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
            this.inventory[tail] = item;
        } else {
            // No room in inventory
            saved = false;
        }
        //super.getCurrentRoom().removeItemFromInventory(item);
        return saved;
    }

    private void removeFromInventory(Item item) {
        for (int i = inventory.length; i > 0; i--) {
            if (item.equals(i)) {
                inventory[i] = null;
            }
        }

    }

    private Item searchInventory(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    private void setTrap(Item item) {
        Trap t = new Trap(item);
        t.setTrap();
        super.getCurrentRoom().addItem(t);
        removeFromInventory(item);
    }

    public void placeTrap(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Place what?");
            this.getInventory();
            return;
        }

        String secondWord = command.getSecondWord();
        Item item = this.searchInventory(secondWord);
        this.setTrap(item);
        System.out.println("You place " + item);
    }

    public void collectItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Collect what?");
            this.getCurrentRoom().getItems().toString();
            return;
        }

        String secondWord = command.getSecondWord();
        Item item = this.getCurrentRoom().getRealItem(secondWord);
        System.out.println(item);
        this.addToInventory(item);
        this.getCurrentRoom().removeItem(item);
        System.out.println("You pick up " + item + " from " + this.getCurrentRoom().getShortDescription());
    }
}
