package HomeAlone.business;

import HomeAlone.textUI.CommandWord;
import HomeAlone.textUI.Parser;
import HomeAlone.textUI.Command;
import HomeAlone.textUI.PresentationControl;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Game {

    //private Parser parser;
    private Player kevin;
    private Nonplayer marv, harry;
    private String objective, objectiveDescription;
    private List<Room> rooms;
    public final int WIN = 1;
    public int status;
    public final int LOSE = -1;
    private boolean started = false;
    
//    private PresentationControl out = new PresentationControl();

    public Game() {
        this.rooms = new ArrayList<>();
        this.status = 0;
        
        kevin = new Player("Kevin");
        marv = new Nonplayer("Marv");
        harry = new Nonplayer("Harry");
        objective = "1: Prepare yourself. Set up an escape route.\n";
        objectiveDescription = ("Find some rope in the attic.");

        initializeGame();

    }

    private void initializeGame() {
        //Adding instances of rooms with descriptions.
        Room foyer, livingRoom, diningRoom, kitchen, staircase, secondFloor, attic, kevinRoom, buzzRoom, basement, masterBedroom, porch, nwGarden, nGarden, neGarden, wGarden, swGarden, seGarden, treehouse;
        Item rope, bbGun, bucket, hose, heater, tarAndNail, iron, blowtorch, glue, plasticWrap, fan, pillow, ornaments, toyCars, paintBucket, yarn;

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
        heater = new Item("Heater", 1);
        tarAndNail = new Item("Tar and nail", 1);
        iron = new Item("Clothes iron", 1);
        blowtorch = new Item("Blowtorch", 1);
//        glue = new Item("Glue", 1);
//        plasticWrap = new Item("Plastic wrap", 1);
//        fan = new Item("Fan", 1);
//        pillow = new Item("Pillow", 1);
        ornaments = new Item("Ornaments", 1);
        toyCars = new Item("Toy cars", 1);
        paintBucket = new Item("Paint bucket", 1);
        yarn = new Item("Yarn", 1);

        //Setting exits and infos to rooms.
        //Infos are called through the "examine" command for the current room that the player currently is located.
        foyer.setExit("living room", livingRoom);
        foyer.setExit("outside", porch);
        foyer.setExit("upstairs", staircase);
        foyer.setExit("dining room", diningRoom);
        foyer.setInfo("I could put my toy cars here...");
        foyer.defineTrap(toyCars);
        foyer.defineTrap(heater);
        foyer.setRoomID(1);

        livingRoom.setExit("foyer", foyer);
        livingRoom.setInfo("I can put some christmas ornaments by the window...");
        livingRoom.defineTrap(ornaments);
        livingRoom.setRoomID(2);
        // livingRoom.addItem(fan);
        // livingRoom.addItem(pillow);

        diningRoom.setExit("foyer", foyer);
        diningRoom.setExit("kitchen", kitchen);
        diningRoom.setRoomID(3);
//        diningRoom.setInfo("I could setup a chicken-trap here with glue, a fan and some feathers...");
//        diningRoom.defineTrap();

        kitchen.setExit("basement", basement);
        kitchen.setExit("dining room", diningRoom);
        kitchen.setExit("outside", neGarden);
        kitchen.setInfo("I should get ready for when the crooks arrive. Buzz' BB gun could be useful if they decide to enter the backdoor... \nI could set up a blowtorch trap here...");
        kitchen.defineTrap(blowtorch);
        kitchen.setRoomID(4);
        // kitchen.addItem(plasticWrap);

        staircase.setExit("up", secondFloor);
        staircase.setExit("down", foyer);
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
        attic.addItem(rope);
        attic.addItem(ornaments);
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
        basement.setInfo("There's tonnes of stuff down here I can use to set up traps.\nMaybe I could set up a trap with a nail and some tar on the steps. Or even switch out the lightbulb switch with a trapdoor...\nI could also use my dad's heater on the front doorknob.");
        basement.defineTrap(tarAndNail);
        basement.addItem(heater);
        basement.addItem(tarAndNail);
        basement.addItem(blowtorch);
        // basement.addItem(glue);
        basement.addItem(paintBucket);
        basement.setRoomID(10);

        masterBedroom.setExit("hallway", secondFloor);
        masterBedroom.setInfo("Mom's and Dad's bedroom. Is there anything in here I can use to make a tripwire?\nI can use the phone to call the cops when the crooks are inside the house...");
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
        //nwGarden.setExit("window", livingRoom);
        // Marv-only movement "idea" for GUI iteration of the game

        nGarden.setExit("treehouse", treehouse);
        nGarden.setExit("west", nwGarden);
        nGarden.setExit("east", neGarden);
        nGarden.setInfo("My treehouse is nearby. Could make for an excellent escape route from the attic...");
        nGarden.setRoomID(14);

        neGarden.setExit("west", nGarden);
        neGarden.setExit("basement", basement);
        neGarden.setExit("kitchen", kitchen);
        neGarden.setInfo("I know the crooks are trying to come through the kitchen door first.\nThe garden hose could help me set up an ice-slippery trap to the basement.");
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
        treehouse.setInfo("I need to set up an escape route here from the attic. My dad has some rope lying around...");
        treehouse.setRoomID(19);

        /*harry.addExitToPath(foyer);
        harry.addExitToPath(diningRoom);
        harry.addExitToPath(kitchen);
        harry.addExitToPath(basement);
        harry.addExitToPath(neGarden);
        harry.addExitToPath(kitchen);
        harry.createPath();
        harry.setCurrentPath(1);

        marv.addExitToPath(kitchen);
        marv.addExitToPath(diningRoom);
        marv.addExitToPath(foyer);
        marv.addExitToPath(staircase);
        marv.addExitToPath(secondFloor);
        marv.createPath();
        marv.setCurrentPath(1);*/


        //Setting starting-point to be inside at the front door, after Kevin returns from church and prepares his traps.
        kevin.setCurrentRoom(foyer);
    }

    public String getCurrentRoomItems() {
        return kevin.getCurrentRoom().getItems()+"\n";
    }       
    
    public boolean goRoom(String command) {
        return kevin.goRoom(command);
    }
    
    public String getCurrentRoomLongDescription() {
        return kevin.getCurrentRoom().getLongDescription()+"\n";
    }
    
    public boolean pickupItem(String command) {
        return kevin.pickupItem(command);
    }
    
    public String getError(String e) {
        String error = "";
        if(e.equalsIgnoreCase("pickup")) {
            error = kevin.getError(e);
        }
        return error;
    }
    
    public String getCurrentRoomShortDescription() {
        return kevin.getCurrentRoom().getShortDescription();
    }

    //Checks if a room has a setInfo that contains more than "", and prints the info.
    public String getCurrentRoomInfo() {
        return kevin.getCurrentRoom().getInfo();
    }
    
    public boolean setTrap(String trapName) {
        return kevin.placeTrap(trapName);
    }
    
    public String getItemString(String command) {
        String s = "";
        if(command.equals("room")) {
            s = kevin.getCurrentRoom().getItemString();
        } else if(command.equals("inventory")) {
            s = kevin.getInventory();
        }
        return s;
    }
    
    public String getTrapString() {
        return kevin.getCurrentRoom().getTrapString();
    }
    
    public String getTrapInfo() {
        return kevin.getCurrentRoom().checkTrapSet();
    }
    
    public boolean dropItem(String itemName) {
        return kevin.dropCommand(itemName);
    }
    
    public ObservableList getExitsObservableList() {
        String[] exits = kevin.getCurrentRoom().getExitString().split(", ");
        ObservableList<String> exitList = FXCollections.observableArrayList();
        for (String exit : exits) {
            exitList.add(exit);
        }
        return exitList;
    }
    
    public ObservableList getItemsObservableList() {
        String[] items = kevin.getCurrentRoom().getItems().split(", ");
        ObservableList<String> itemList = FXCollections.observableArrayList();
        for (String item : items) {
            itemList.add(item);
        }
        return itemList;
    }
    
    //Method used for calling the "help"-command that prints out instructions and commands.
    public String printHelp() {
        return kevin.getCurrentRoom().getExitString() + "\n";
    }

    //Quit program method
    public boolean quit() {
        return true;
    }

    public String show(String command) {
        if (command.equalsIgnoreCase("inventory")) {
            return kevin.getInventory();
        } else {
            return getObjective();
        }
    }

    public String getObjective() {
        return objective;
    }
    
    /* Need a list of traps that need to be set, temporary win condition for 1st game stage */
    public void checkObjectives() {
        for (Room room : rooms) {
            if(room.getRoomID() == 1){
                List<Trap> trapList = room.getPossibleTraps();
                if(trapList != null) {
                    for (Trap trap : trapList) {
                        
                        if(trap.checkTrapSet()) {
                            this.status = WIN;
                        } else {
                            this.status = LOSE;
                        }
                    }
                } else {
                    this.status = LOSE;
                }
            }
        }
    }
}
