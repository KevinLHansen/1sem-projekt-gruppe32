package HomeAlone.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Main class for the game.
 * This class contains all the methods for initialising the game as well as the
 * methods called by the presentation layer.
 * It is using the singleton pattern.
 * 
 * @author Gruppe 32
 */

public class Game {

    private Player kevin;
    private Nonplayer marv, harry;
    private String objective;
    private List<Room> rooms;
    public final int WIN = 1;
    public int status;
    public final int LOSE = -1;
    private boolean finished = false;
    private int phase;

    private Usable phone;
    private String resultReason = "";

    // Singleton pattern for GUI purposes
    private static Game instance = null;

    /**
     * Method used to get access to the {@link Game} object.
     * @return Game object
     */
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    /**
     * Method called in case the player wishes to restart the game.
     * It deletes the connection to the {@link Game} object so a new have to be created.
     */
    public static void restart() {
        instance = null;
    }

    /**
     * Class constructor
     * Initialises {@link HomeAlone.business.Player} and {@link HomeAlone.business.Nonplayer} objects as well as a few attributes 
     * that are used when playing the game.
     */
    private Game() {
        this.rooms = new ArrayList<>();
        this.status = 0;
        this.phase = 1;

        kevin = new Player("Kevin");
        marv = new Nonplayer("Marv");
        harry = new Nonplayer("Harry");
        objective = "Prepare an escape route and set up traps before the burglars arrive.";

        initializeGame();

    }

    /**
     * Initialises the game, creating the Room and Item object needed to play.
     * This method creates all the various {@link HomeAlone.business.Room} objects and sets the information
     * that is used during gameplay.
     * It also creates all the {@link HomeAlone.business.Item} objects and links them to the appropriate 
     * {@link HomeAlone.business.Room} objects, then is creates the paths for the two {@link HomeAlone.business.Nonplayer}
     * objects("the wet bandits") are going to be using.
     * It sets the current path and current room for the "the wet bandits" as 
     * well as the current room for the {@link HomeAlone.business.Player}
     */
    private void initializeGame() {
        //Adding instances of rooms with descriptions.
        Room foyer, livingRoom, diningRoom, kitchen, staircase, secondFloor, attic, kevinRoom, buzzRoom, basement, masterBedroom, porch, nwGarden, nGarden, neGarden, wGarden, swGarden, seGarden, treehouse;
        Item rope, bbGun, bucket, hose, heater, tarAndNail, blowtorch, ornaments, toyCars, paintBucket, yarn;

        foyer = new Room("Foyer - Front entrance");
        livingRoom = new Room("Living room");
        diningRoom = new Room("Dining room");
        kitchen = new Room("Kitchen");
        staircase = new Room("Staircase");
        secondFloor = new Room("Second floor corridor");
        attic = new Room("Attic");
        kevinRoom = new Room("Kevin's room");
        buzzRoom = new Room("Buzz' room");
        basement = new Room("Basement");
        masterBedroom = new Room("Master bedroom - Mom and dad's bedroom");
        porch = new Room("Porch - Southern outside area");
        nwGarden = new Room("North west gardens - North western outside area");
        nGarden = new Room("North gardens - Northern outside area");
        neGarden = new Room("North east gardens - North eastern outside area.");
        wGarden = new Room("West gardens - Western outside area");
        swGarden = new Room("South west gardens - South western outside area");
        seGarden = new Room("South east gardens - South eastern outside area");
        treehouse = new Room("Treehouse - Northern outside area");

        Room[] phoneRooms = {
            masterBedroom, livingRoom
        };

        rooms.add(foyer);
        rooms.add(livingRoom);
        rooms.add(diningRoom);
        rooms.add(kitchen);
        rooms.add(staircase);
        rooms.add(secondFloor);
        rooms.add(attic);
        rooms.add(kevinRoom);
        rooms.add(buzzRoom);
        rooms.add(basement);
        rooms.add(masterBedroom);
        rooms.add(porch);
        rooms.add(nwGarden);
        rooms.add(nGarden);
        rooms.add(neGarden);
        rooms.add(wGarden);
        rooms.add(swGarden);
        rooms.add(seGarden);
        rooms.add(treehouse);

        rope = new Item("Rope", 1);
        bbGun = new Item("bbGun", 1);
        bucket = new Item("Bucket", 1);
        hose = new Item("Hose", 1);
        heater = new Item("Charcoal BBQ starter", 1);
        tarAndNail = new Item("Tar and nail", 1);
        blowtorch = new Item("Blowtorch", 1);
        ornaments = new Item("Ornaments", 1);
        toyCars = new Item("Toy cars", 1);
        paintBucket = new Item("Paint bucket", 1);
        yarn = new Item("Yarn", 1);
        phone = new Usable("Phone", 1);

        int index = new Random().nextInt(phoneRooms.length);
        phoneRooms[index].addItem(phone);

        //Setting exits and infos to rooms.
        //Infos are called through the "examine" command for the current room that the player currently is located.
        foyer.setExit("living room", livingRoom);
        foyer.setExit("outside", porch);
        foyer.setExit("upstairs", staircase);
        foyer.setExit("dining room", diningRoom);
        foyer.setInfo("I could put my toy cars here...\n Maybe dad's charcoal BBQ starter could fit on the door handle");
        foyer.defineTrap(toyCars);
        foyer.defineTrap(heater);
        foyer.setRoomID(1);

        livingRoom.setExit("foyer", foyer);
        livingRoom.setInfo("I can put some christmas ornaments by the window...");
        livingRoom.defineTrap(ornaments);
        livingRoom.setRoomID(2);

        diningRoom.setExit("foyer", foyer);
        diningRoom.setExit("kitchen", kitchen);
        diningRoom.setRoomID(3);

        kitchen.setExit("basement", basement);
        kitchen.setExit("dining room", diningRoom);
        kitchen.setExit("outside", neGarden);
        kitchen.setInfo("I should get ready for when the crooks arrive. Buzz' BB gun could be useful if they decide to enter the backdoor... \nI could set up a blowtorch trap here...");
        kitchen.defineTrap(blowtorch);
        kitchen.setRoomID(4);

        staircase.setExit("up to hallway", secondFloor);
        staircase.setExit("down to foyer", foyer);
        staircase.setInfo("I should set up at least one trap here, so that they won't get upstairs without a fight...\nMaybe the buckets of paint could work out...");
        staircase.defineTrap(paintBucket);
        staircase.setRoomID(5);

        secondFloor.setExit("master bedroom", masterBedroom);
        secondFloor.setExit("attic", attic);
        secondFloor.setExit("downstairs", staircase);
        secondFloor.setExit("kevins room", kevinRoom);
        secondFloor.setExit("buzz room", buzzRoom);
        secondFloor.setInfo("Upstairs. Maybe a tripwire between the narrow hallway could slow them down...");
        secondFloor.defineTrap(yarn);
        secondFloor.setRoomID(6);

        attic.setExit("hallway", secondFloor);
        attic.setInfo("The attic is the perfect way for a zipline escape route to my treehouse! My dad has some rope laying around somewhere...");
        attic.addItem(ornaments);
        attic.defineTrap(rope);
        attic.setRoomID(7);

        kevinRoom.setExit("hallway", secondFloor);
        kevinRoom.setInfo("Mom is always upset that I leave the toy cars around. Maybe they could be of use downstairs?");
        kevinRoom.addItem(toyCars);
        kevinRoom.setRoomID(8);

        buzzRoom.setExit("hallway", secondFloor);
        buzzRoom.setInfo("Buzz will never let me walk in here. There must be something in here I can use to defend myself with...");
        buzzRoom.addItem(bbGun);
        buzzRoom.setRoomID(9);

        basement.setExit("kitchen", kitchen);
        basement.setExit("outside", neGarden);
        basement.setInfo("There's tonnes of stuff down here I can use to set up traps.\nMaybe I could set up a trap with a nail and some tar on the steps. Or even switch out the lightbulb switch with a trapdoor...\nI could also use my dad's BBQ starter on the front doorknob.");
        basement.defineTrap(tarAndNail);
        basement.addItem(heater);
        basement.addItem(tarAndNail);
        basement.addItem(blowtorch);
        basement.addItem(paintBucket);
        basement.addItem(rope);
        basement.setRoomID(10);

        masterBedroom.setExit("hallway", secondFloor);
        masterBedroom.setInfo("Mom's and Dad's bedroom. Is there anything in here I can use to make a tripwire?");
        masterBedroom.addItem(yarn);
        masterBedroom.setRoomID(11);

        porch.setExit("inside", foyer);
        porch.setExit("west", swGarden);
        porch.setExit("east", seGarden);
        porch.setInfo("There's nothing much here, but the door is very undefended. I could pour water over the stairs so that they slip when they try to get in...");
        porch.defineTrap(bucket);
        porch.setRoomID(12);

        nwGarden.setExit("east", nGarden);
        nwGarden.setExit("south", wGarden);
        nwGarden.setRoomID(13);

        nGarden.setExit("treehouse", treehouse);
        nGarden.setExit("west", nwGarden);
        nGarden.setExit("east", neGarden);
        nGarden.setInfo("My treehouse is nearby. Could make for an excellent escape route from the attic...");
        nGarden.setRoomID(14);

        neGarden.setExit("west", nGarden);
        neGarden.setExit("basement", basement);
        neGarden.setExit("kitchen", kitchen);
        neGarden.setInfo("I know the crooks will try to come through the kitchen door first.\nThe garden hose could help me set up an ice-slippery trap to the basement.");
        neGarden.defineTrap(hose);
        neGarden.addItem(hose);
        neGarden.setRoomID(15);

        wGarden.setExit("north", nwGarden);
        wGarden.setExit("south", swGarden);
        wGarden.setRoomID(16);

        swGarden.setExit("north", wGarden);
        swGarden.setExit("east", porch);
        swGarden.setRoomID(17);

        seGarden.setExit("west", porch);
        seGarden.setInfo("There's a bucket here. I can fill it with water and pour the water onto the steps at the front porch...");
        seGarden.addItem(bucket);
        seGarden.setRoomID(18);

        treehouse.setExit("down", nGarden);
        treehouse.setInfo("I need to set up an escape route from the attic to this treehouse. My dad has some rope lying around...");
        treehouse.setRoomID(19);

        //Harry path 1
        harry.addExitToPath(swGarden); //DELAY - Run from kitchen door
        harry.addExitToPath(porch); //DELAY - Ice
        harry.addExitToPath(foyer); //DELAY - Toy cars
        harry.addExitToPath(diningRoom);
        harry.addExitToPath(kitchen);
        harry.addExitToPath(diningRoom);
        harry.addExitToPath(foyer);
        harry.addExitToPath(livingRoom);
        harry.addExitToPath(foyer);
        harry.addExitToPath(staircase); //DELAY - Paint bucket
        harry.addExitToPath(secondFloor); //DELAY - tripwire
        harry.addExitToPath(masterBedroom);
        harry.addExitToPath(secondFloor);
        harry.addExitToPath(kevinRoom);
        harry.addExitToPath(secondFloor);
        harry.addExitToPath(buzzRoom);
        harry.addExitToPath(secondFloor);
        harry.addExitToPath(attic);

        harry.createPath();
        harry.setCurrentPath(1);

        //Harry path 2
        harry.addExitToPath(neGarden); //DELAY - Run from frontdoor and Charcoal BBQ starter trap
        harry.addExitToPath(kitchen); //DELAY - Blowtorch
        harry.addExitToPath(diningRoom);
        harry.addExitToPath(foyer); //DELAY - Toy cars
        harry.addExitToPath(livingRoom);
        harry.addExitToPath(foyer);
        harry.addExitToPath(staircase); //DELAY - Paint bucket
        harry.addExitToPath(secondFloor); //DELAY - Tripwire
        harry.addExitToPath(masterBedroom);
        harry.addExitToPath(secondFloor);
        harry.addExitToPath(kevinRoom);
        harry.addExitToPath(secondFloor);
        harry.addExitToPath(buzzRoom);
        harry.addExitToPath(secondFloor);
        harry.addExitToPath(attic);

        harry.createPath();

        //Marv path 1
        marv.addExitToPath(basement);
        marv.addExitToPath(kitchen);
        marv.addExitToPath(diningRoom);
        marv.addExitToPath(foyer);
        marv.addExitToPath(livingRoom);
        marv.addExitToPath(foyer);
        marv.addExitToPath(staircase);
        marv.addExitToPath(secondFloor);
        marv.addExitToPath(kevinRoom);
        marv.addExitToPath(secondFloor);
        marv.addExitToPath(buzzRoom);
        marv.addExitToPath(secondFloor);
        marv.addExitToPath(masterBedroom);
        marv.addExitToPath(secondFloor);
        marv.addExitToPath(attic);

        marv.createPath();
        marv.setCurrentPath(1);

        //Marv path 2
        marv.addExitToPath(neGarden);
        marv.addExitToPath(nGarden);
        marv.addExitToPath(nwGarden);
        marv.addExitToPath(livingRoom);
        marv.addExitToPath(foyer);
        marv.addExitToPath(staircase);
        marv.addExitToPath(secondFloor);
        marv.addExitToPath(kevinRoom);
        marv.addExitToPath(secondFloor);
        marv.addExitToPath(buzzRoom);
        marv.addExitToPath(secondFloor);
        marv.addExitToPath(masterBedroom);
        marv.addExitToPath(secondFloor);
        marv.addExitToPath(attic);

        marv.createPath();

        //Setting starting-point to be inside at the front door, after Kevin returns from church and prepares his traps.
        kevin.setCurrentRoom(foyer);

        // Setting the first room for the 2 bandits
        marv.setCurrentRoom(neGarden);
        harry.setCurrentRoom(swGarden);
        // Delay from running from kitchen door
        harry.setDelay(4);
    }

    /**
     * Getter method for the attribute phase
     * @return phase
     */
    public int getPhase() {
        return phase;
    }

    /**
     * Changes the phase and checks certain conditions for losing the game.
     * This method is used to change the phases during gameplay, including 
     * writing new objectives.
     * When it changes the phases, it checks the conditions are met for the next
     * phase, and changes the attribute {@link #status} if needed as well as 
     * writing a reason for losing or winning.
     * Part of the "interface" to the presentaion layer.
     * @return phase
     */
    public int changePhase() {
        this.phase++;
        if (this.phase == 2) {
            if (rooms.get(6).getItemList().isEmpty()) {
                this.status = LOSE;
                resultReason = "No escape route when burglars arrived.";
            } else { // checks if zipline is assembled in attic
                for (Item item : rooms.get(6).getItemList()) {
                    if (item instanceof Trap) {
                        Trap t = (Trap) item;
                        if (t.getName().equalsIgnoreCase("Rope") && !t.checkTrapSet()) {
                            this.status = LOSE;
                            resultReason = "No escape route when burglars arrived.";
                        } else {
                            this.status = WIN;
                            resultReason = "Kevin succesfully defended his house!";
                            break;
                        }
                    } else {
                        this.status = LOSE;
                        resultReason = "No escape route when burglars arrived.";
                    }
                }
            }
            this.objective = "Hurry!\nFind the BB gun and bring it to the kitchen.";
            // LOSE - outside when the wet bandits arrives
            int[] restrictedRoomIDs = {12, 13, 14, 15, 16, 17, 18, 19};
            if (IntStream.of(restrictedRoomIDs).anyMatch(x -> x == kevin.getCurrentRoom().getRoomID())) {
                this.status = LOSE;
                resultReason = "You got caught outside by the burglars.";
            }
        } else if (this.phase == 3) {
            this.objective = "The burglars are inside the house!\nCall the police and use the escape route before you get caught.";

            // LOSE - not in kitchen with BB gun
            if (kevin.getCurrentRoom().getRoomID() != 4) {
                this.status = LOSE;
                resultReason = "You weren't prepared to defend the kitchen.";
            } else {
                String[] inventory = kevin.getInventory().split(", ");
                for (String item : inventory) {
                    if (item.equalsIgnoreCase("bbGun")) {
                        this.status = WIN;
                        resultReason = "Kevin succesfully defended his house!";
                        break;
                    } else {
                        this.status = LOSE;
                        resultReason = "You weren't prepared to defend the kitchen.";
                    }
                }
            }
        }

        return this.phase;
    }

    /**
     * Called during second phase to force a phase shift if player is fast.
     * This method is used in phase 2 to check if the player gets to the kitchen
     * before the 1 minute timer runs out. If that happens the method returns
     * true and the game changes to phase 3 instead of having to wait out
     * the timer.
     * Part of the "interface" to the presentation layer.
     * @return true if Player is in the kitchen with the BB-gun, false if not
     */
    public boolean inKitchenWithGun() { // checks if player is in kitchen with BB gun to kickstart next phase
        boolean inKitchen = false;
        if (kevin.getCurrentRoom().getRoomID() == 4) {
            String[] inventory = kevin.getInventory().split(", ");
            for (String item : inventory) {
                if (item.equalsIgnoreCase("bbGun")) {
                    inKitchen = true;
                }
            }
        }
        return inKitchen;
    }

    /**
     * Checks the rooms around the players room for "the wet bandits".
     * This method is used in phase 3 to check if the 2 {@link HomeAlone.business.Nonplayer} objects
     * are located in one of the rooms next to the room the player is 
     * currently in.
     * If it finds a nonplayer entity in any of the neighbouring rooms, it returns
     * a string indicating that the room is occupied.
     * If it finds more than one, both rooms are added to the string.
     * Part of the "interface" to the presentation layer.
     * @return empty string if neighbouring rooms are empty, warning if not
     */
    public String checkNeighbourRoom() {
        String s = "", exitString = "";
        List<String> neighbourRooms = new ArrayList<>();
        String[] exits = kevin.getCurrentRoom().getExitString().split(", ");
        int exitsLen = exits.length;
        for (int i = 0; i < exitsLen; i++) {
            if (kevin.getCurrentRoom().getExit(exits[i]).equals(marv.getCurrentRoom()) || kevin.getCurrentRoom().getExit(exits[i]).equals(harry.getCurrentRoom())) {
                neighbourRooms.add(exits[i]);
            }
        }
        if (neighbourRooms.size() > 1) {
            int j = 0;
            for (String neighbourRoom : neighbourRooms) {
                exitString += neighbourRoom + ((j < neighbourRooms.size()) ? " and " : "");
                j++;
            }
        } else if (neighbourRooms.size() == 1) {
            exitString += neighbourRooms.get(0);
        }
        if (!exitString.equals("")) {
            s = "\n\nYou hear footsteps coming from " + exitString + ".";
        }
        return s;
    }

    /**
     * Checks to see if player is in the same room as any of "the wet bandits".
     * This method is used during phase 3 to check if the player is in the 
     * same {@link HomeAlone.business.Room} as any of "the wet bandits" and if the nonplayer
     * entity in question does not have a delay the game is lost.
     * If the nonplayer entity does have a delay, the player is free to move 
     * through the room.
     * @param npc Nonplayer object
     */
    public void checkForKevin(Nonplayer npc) {
        if (npc.getCurrentRoom().equals(kevin.getCurrentRoom())) {
            if (npc.getDelay() < 1) {
            this.status = LOSE;
            resultReason = "You ran into a burglar and got caught.";
            }
        }
    }

    /**
     * Checks the status of the game.
     * This method checks the attribute {@link #status} to see if the game is lost.
     * The default setting is that the game is won, so this method just checks to
     * see if the status is changed to lose in which case it returns false else
     * it will return true.
     * Part of the "interface" to the presentation layer.
     * @return false is game is lost, true if it is not
     */
    public boolean checkStatus() {
        if (this.status == this.LOSE) {
            return false;
        }
        return true;
    }

    /**
     * Left over from the CLI version of the game, before we split the code up
     * in layers
     * @return string of item names in a room
     */
    public String getCurrentRoomItems() {
        return kevin.getCurrentRoom().getItems() + "\n";
    }

    /**
     * Sets up the zipline escape route and moves the player to Treehouse.
     * This method is used in phase 1 to set up the zipline from Attic to
     * Treehouse.
     * It takes a string with the item name, then checks its the right {@link HomeAlone.business.Item}
     * calls {@link #setTrap(String trapName)} and moves the player to the Treehouse
     * using the method {@link HomeAlone.business.Creature#setCurrentRoom(HomeAlone.business.Room)}.
     * Part of the "interface" to the presentation layer.
     * @param item String
     * @return true if the zipline is set up, false if en error occurred
     */
    public boolean setZipline(String item) {
        if (kevin.getCurrentRoom().getRoomID() == 7 && item != null) {
            if (item.equalsIgnoreCase("rope")) {
                setTrap(item);
                kevin.setCurrentRoom(rooms.get(18));
                return true;
            }
        }
        return false;
    }

    /**
     * Intermediary between presentation layer and {@link HomeAlone.business.Player}.
     * This method is called from the presentation layer to move the player to
     * a different room. 
     * After checking the various conditions on the phases, it calls the method
     * in {@link HomeAlone.business.Player} that moves the player to a new room.
     * Part of the "interface" to the presentation layer.
     * @param command String
     * @return true if successful, false if not
     */
    public boolean goRoom(String command) {
        boolean returnVal = true;
        if (checkStatus()) {
            returnVal = kevin.goRoom(command);
            if (this.phase == 2) {
                int[] restrictedRoomIDs = {12, 13, 14, 15, 16, 17, 18, 19};
                if (IntStream.of(restrictedRoomIDs).anyMatch(x -> x == kevin.getCurrentRoom().getRoomID())) {
                    this.status = LOSE;
                    resultReason = "You got caught outside by the burglars.";
                }
            }
            if (phase == 3) {
                if (kevin.getCurrentRoom().getRoomID() == 7 && phone.isItemUsed()) {
                    this.status = WIN;
                    resultReason = "Kevin succesfully defended his house!";
                    this.finished = true;
                }
                checkForKevin(marv);
                checkForKevin(harry);

                walkPath(marv);
                walkPath(harry);
            }
        }
        return returnVal;
    }

    /**
     * Getter method for attribute {@link #finished}
     * Part of the "interface" to the presentation layer.
     * @return finished, boolean value
     */
    public boolean getFinished() {
        return this.finished;
    }

    /**
     * Calls the method in {@link HomeAlone.business.Nonplayer} that moves "the wet bandits".
     * This method is called everytime the player uses the commands for moving,
     * picking an item up, setting a trap or dropping an item.
     * It calls the method in {@link HomeAlone.business.Nonplayer} which actually moves the entity.
     * After the entity has moved to a new room, this method checks if the new
     * room is Attic, in which case the game is over and the player lost.
     * @param npc 
     */
    private void walkPath(Nonplayer npc) {
        if (checkStatus()) {
            npc.walkPath();
            // If 1 of the wet bandits reach the attic the player loses the game.
            if (npc.getCurrentRoom().getRoomID() == 7) {
                this.status = LOSE;
                resultReason = "A burglar found your escape route.";
            }
            checkForKevin(npc);
            System.out.println(npc.getName() + " | " + npc.getDelay() + " | " + npc.getCurrentRoom().getShortDescription());
        }
    }

    /**
     * Left over from CLI
     * @return String containing the long description of a room.
     */
    public String getCurrentRoomLongDescription() {
        return kevin.getCurrentRoom().getLongDescription() + "\n";
    }

    /**
     * Intermediary between presentation layer and {@link HomeAlone.business.Player}.
     * This method is called by the presentation layer, to pickup an item from
     * the room the player is currently in.
     * Calls the methods {@link #checkForKevin(Nonplayer npc)}
     * and {@link #walkPath(Nonplayer npc)} during phase 3.
     * Returns true if the item is picked up and false if it is not.
     * Part of the "interface" to the presentation layer.
     * @param command String
     * @return true if successful, false if not
     */
    public boolean pickupItem(String command) {
        if (checkStatus()) {
            if (phase == 3) {
                checkForKevin(marv);
                checkForKevin(harry);

                walkPath(marv);
                walkPath(harry);
            }
            return kevin.pickupItem(command);
        } else {
            return false;
        }
    }

    /**
     * Intermediary between presentation layer and {@link HomeAlone.business.Creature}.
     * This method returns a string if an error occured when using these methods:
     * {@link HomeAlone.business.Player#pickupItem(java.lang.String)}
     * {@link HomeAlone.business.Player#placeTrap(java.lang.String)}
     * {@link HomeAlone.business.Player#dropCommand(java.lang.String)}
     * Part of the "interface" to the presentation layer.
     * @param e String
     * @return empty string if no errors found, or a string containing the error
     */
    public String getError(String e) {
        String error = "";
        error = kevin.getError(e);
        return error;
    }

    /**
     * Returns the short description from {@link HomeAlone.business.Room} to the presentation layer.
     * This method is used to return the short description of the current room
     * to the presentation layer.
     * Part of the "interface" to the presentation layer.
     * @return string containing room description
     */
    public String getCurrentRoomShortDescription() {
        return kevin.getCurrentRoom().getShortDescription();
    }

    /**
     * Intermediary between presentation layer and {@link HomeAlone.business.Room}.
     * This method returns the information for the current room.
     * Part of the "interface" to the presentation layer.
     * @return string containing information about a room, if game is lost
     */
    public String getCurrentRoomInfo() {
        if (checkStatus()) {
            return kevin.getCurrentRoom().getInfo();
        } else {
            return "";
        }
    }

    /**
     * Intermediary between presentation layer and {@link HomeAlone.business.Player}.
     * This method checks {@link #status} using {@link #checkStatus()}.
     * In phase 3 it calls the methods {@link #checkForKevin(Nonplayer npc)} and
     * {@link #walkPath(Nonplayer npc)} for both {@link HomeAlone.business.Nonplayer} objects.
     * It calls the method {@link HomeAlone.business.Player#placeTrap(java.lang.String)}.
     * Part of the "interface" to the presentation layer.
     * @param trapName String
     * @return true if successful, false if not
     */
    public boolean setTrap(String trapName) {
        if (checkStatus()) {
            if (phase == 3) {
                checkForKevin(marv);
                checkForKevin(harry);

                walkPath(marv);
                walkPath(harry);
            }
            return kevin.placeTrap(trapName);
        } else {
            return false;
        }
    }

    /**
     * Left over from CLI
     * @param command String
     * @return epmty string if no items found, else a string with list of items
     */
    public String getItemString(String command) {
        String s = "";
        if (command.equals("room")) {
            s = kevin.getCurrentRoom().getItemString();
        } else if (command.equals("inventory")) {
            s = kevin.getInventory();
        }
        return s;
    }

    /**
     * Intermediary between presentation layer and {@link HomeAlone.business.Room}.
     * This method is used to get a list of the possible traps for the room the
     * player is currently in. This is done by calling {@link HomeAlone.business.Room#getTrapString()}.
     * Part of the "interface" to the presentation layer.
     * @return empty string or list of traps as string
     */
    public String getTrapString() {
        if (checkStatus()) {
            return kevin.getCurrentRoom().getTrapString();
        } else {
            return "";
        }
    }

    /**
     * Intermediay between presentation layer and {@link HomeAlone.business.Room}.
     * This method returns the traps that have been set in a room.
     * Part of the "interface" to the presentation layer.
     * @return string containing traps set or empty string if no traps are set
     */
    public String getTrapInfo() {
        return kevin.getCurrentRoom().checkTrapSet();
    }

    /**
     * Intermediary between presentation layer and {@link HomeAlone.business.Player}.
     * This method calls the method {@link HomeAlone.business.Player#dropCommand(java.lang.String)}.
     * Part of the "interface" to the presentation layer.
     * @param itemName String
     * @return true if successful, false if not
     */
    public boolean dropItem(String itemName) {
        if (checkStatus()) {
            return kevin.dropCommand(itemName);
        } else {
            return false;
        }
    }

    /**
     * Intermediary between presentation layer and {@link HomeAlone.business.Room}.
     * This method return an ObservableList with the available exits from the 
     * players current room. This is done by calling {@link HomeAlone.business.Room#getExitString()}
     * and splitting the returned string in to a String array, iterate through
     * the array and adding each exit to the ObservableList.
     * Part of the "interface" to the presentation layer.
     * @return an ObservableList with available exits as strings
     */
    public ObservableList getExitsObservableList() {
        String[] exits = kevin.getCurrentRoom().getExitString().split(", ");
        ObservableList<String> exitList = FXCollections.observableArrayList();
        for (String exit : exits) {
            exitList.add(exit);
        }
        return exitList;
    }

    /**
     * Intermediary between presentation layer and {@link HomeAlone.business.Room}.
     * This method returns an ObservableList with the available items in the
     * players current room. This is done by calling {@link HomeAlone.business.Room#getItems()}
     * and splitting the returned string in to a String array, iterate through
     * the array and adding each item name to the ObservableList.
     * Part of the "interface" to the presentation layer.
     * @return an ObservableList with available item names
     */
    public ObservableList getItemsObservableList() {
        String[] items = kevin.getCurrentRoom().getItems().split(", ");
        ObservableList<String> itemList = FXCollections.observableArrayList();
        for (String item : items) {
            itemList.add(item);
        }
        return itemList;
    }

    /**
     * Intermediary between presentation layer and {@link HomeAlone.business.Player}.
     * This method returns an ObservableList with the items the player has
     * picked up. This is done by calling {@link HomeAlone.business.Player#getInventory()}
     * and splitting the returned string in to a String array, iterate through
     * the array and adding each item name to the ObservableList.
     * Part of the "interface" to the presentation layer.
     * @return an ObservableList with item names the player has picked up
     */
    public ObservableList getInventoryObservableList() {
        String[] items = kevin.getInventory().split(", ");
        ObservableList<String> itemList = FXCollections.observableArrayList();
        for (String item : items) {
            itemList.add(item);
        }
        return itemList;
    }

    /**
     * Left over from CLI
     * @return string with the available exits or empty string
     */
    public String printHelp() {
        if (checkStatus()) {
            return kevin.getCurrentRoom().getExitString() + "\n";
        } else {
            return "";
        }
    }

    /**
     * Left over from CLI
     * @return true
     */
    public boolean quit() {
        return true;
    }

    /**
     * left over from CLI
     * @param command String
     * @return String containing either the inventory or objective
     */
    public String show(String command) {
        if (command.equalsIgnoreCase("inventory")) {
            return kevin.getInventory();
        } else {
            return getObjective();
        }
    }

    /**
     * Returns the objective to be displayed in presentation layer.
     * This method returns {@link #objective} for the players current phase.
     * Part of the "interface" to the presentation layer.
     * @return string containing the objective for a phase
     */
    public String getObjective() {
        return objective;
    }

    /**
     * Returns the result of the players effort.
     * This method is called when the game is over and the end screen is shown.
     * Part of the "interface" to the presentation layer.
     * @return string indication win or lose
     */
    public String getResult() {
        if (checkStatus()) {
            return "WIN";
        } else {
            return "LOSE";
        }
    }

    /**
     * Checks if the item Phone is in the room.
     * This method is used in phase 3 to check if the phone is in the same room
     * as the player.
     * @return true if the phone is present, false if it is not
     */
    public boolean isPhoneHere() {
        for (Item item : kevin.getCurrentRoom().getItemList()) {
            if (item.getName().equals("Phone")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Getter method for the attribute {@link #resultReason}.
     * This method is using in the end screen to give a reason for the player 
     * winning or losing the game.
     * Part of the "interface" to the presentation layer.
     * @return String
     */
    public String getResultReason() {
        return resultReason;
    }

    /**
     * Unused code, should be deleted
     * @param index int
     * @return Room object
     */
    public Room getRoomObject(int index) {
        return rooms.get(index);
    }
}
