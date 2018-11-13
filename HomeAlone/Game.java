package HomeAlone;

import java.util.Scanner;

public class Game {

    private Parser parser;
    private Player kevin;
    private Nonplayer marv, harry;
    private String objective, objectiveDescription;
    public final int WIN = 1;
    public int status;
    public final int LOSE = -1;
    private boolean started = false;

    public Game() {
        parser = new Parser();
        kevin = new Player("Kevin");
        marv = new Nonplayer("Marv");
        harry = new Nonplayer("Harry");
        objective = "1: Prepare yourself. Set up an escape route.\n";
        objectiveDescription = ("Find some rope in the attic.");

        initializeGame();

    }

    private void initializeGame() {
        //Adding instances of rooms with descriptions.
        Room foyer, livingRoom, diningRoom, kitchen, staircase, secondFloor, attic, basement, masterBedroom, porch, nwGarden, nGarden, neGarden, wGarden, swGarden, seGarden, treehouse;
        Item rope, bbGun, bucket, hose, heater, tarAndNail, iron, blowtorch, glue, plasticWrap, fan, pillow, ornaments, toyCars, paintBucket, yarn;

        foyer = new Room("Foyer - Front entrance");
        livingRoom = new Room("Living room");
        diningRoom = new Room("Dining room");
        kitchen = new Room("Kitchen");
        staircase = new Room("Staircase");
        secondFloor = new Room("Second floor corridor");
        attic = new Room("Attic");
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
        foyer.setExit("livingroom", livingRoom);
        foyer.setExit("outside", porch);
        foyer.setExit("upstairs", staircase);
        foyer.setExit("diningroom", diningRoom);
        foyer.setInfo("I could put my toy cars here...");
        foyer.defineTrap(toyCars);

        livingRoom.setExit("foyer", foyer);
        livingRoom.setInfo("I can put some christmas ornaments by the window...");
        livingRoom.defineTrap(ornaments);
        // livingRoom.addItem(fan);
        // livingRoom.addItem(pillow);

        diningRoom.setExit("foyer", foyer);
        diningRoom.setExit("kitchen", kitchen);
//        diningRoom.setInfo("I could setup a chicken-trap here with glue, a fan and some feathers...");
//        diningRoom.defineTrap();

        kitchen.setExit("basement", basement);
        kitchen.setExit("diningroom", diningRoom);
        kitchen.setExit("outside", neGarden);
        kitchen.setInfo("I should get ready for when the crooks arrive. Buzz' BB gun could be useful if they decide to enter the backdoor... \nI could set up a blowtorch trap here...");
        kitchen.defineTrap(blowtorch);
        // kitchen.addItem(plasticWrap);

        staircase.setExit("up", secondFloor);
        staircase.setExit("down", foyer);
        staircase.setInfo("I should set up at least one trap here, so that they won't get upstairs without a fight...\nMaybe the buckets of paint could work out...");
        staircase.defineTrap(paintBucket);

        secondFloor.setExit("masterbedroom", masterBedroom);
        secondFloor.setExit("attic", attic);
        secondFloor.setExit("downstairs", staircase);
        // secondFloor.setExit("room", room);
        // Adding more rooms later depending on the items required and immersive experience.
        secondFloor.setInfo("Upstairs. Maybe a tripwire between the narrow hallway could slow them down...");
        secondFloor.defineTrap(yarn);

        attic.setExit("hallway", secondFloor);
        attic.setInfo("The attic is the perfect way for a zipline escape route to my treehouse! My dad has some rope laying around somewhere...");
        attic.addItem(rope);
        attic.addItem(ornaments);

        basement.setExit("kitchen", kitchen);
        basement.setExit("outside", neGarden);
        basement.setInfo("There's tonnes of stuff down here I can use to set up traps.\nMaybe I could set up a trap with a nail and some tar on the steps. Or even switch out the lightbulb switch with a trapdoor...\nI could also use my dad's heater on the front doorknob.");
        basement.defineTrap(tarAndNail);
        basement.addItem(heater);
        basement.addItem(tarAndNail);
        basement.addItem(blowtorch);
        // basement.addItem(glue);
        basement.addItem(paintBucket);

        masterBedroom.setExit("hallway", secondFloor);
        masterBedroom.setInfo("Mom's and Dad's bedroom. Is there anything in here I can use to make a tripwire?\nI can use the phone to call the cops when the crooks are inside the house...");
        masterBedroom.addItem(yarn);

        porch.setExit("inside", foyer);
        porch.setExit("west", swGarden);
        porch.setExit("east", seGarden);
        porch.setInfo("There's nothing much here, but the door is very undefended. I could pour water over the stairs so that they slip when they try to get in...");
        porch.defineTrap(bucket);

        nwGarden.setExit("east", nGarden);
        nwGarden.setExit("south", wGarden);
        //nwGarden.setExit("window", livingRoom);
        // Marv-only movement "idea" for GUI iteration of the game

        nGarden.setExit("treehouse", treehouse);
        nGarden.setExit("west", nwGarden);
        nGarden.setExit("east", neGarden);
        nGarden.setInfo("My treehouse is nearby. Could make for an excellent escape route from the attic...");

        neGarden.setExit("west", nGarden);
        neGarden.setExit("basement", basement);
        neGarden.setExit("kitchen", kitchen);
        neGarden.setInfo("I know the crooks are trying to come through the kitchen door first.\nThe garden hose could help me set up an ice-slippery trap to the basement.");
        neGarden.defineTrap(hose);
        neGarden.addItem(hose);

        wGarden.setExit("north", nwGarden);
        wGarden.setExit("south", swGarden);

        swGarden.setExit("north", wGarden);
        swGarden.setExit("east", porch);

        seGarden.setExit("west", porch);
        seGarden.setInfo("There's a bucket here. I can fill it with water and pour the water onto the steps at the front porch...");
        seGarden.addItem(bucket);

        treehouse.setExit("down", nGarden);
        treehouse.setInfo("I need to set up an escape route here from the attic. My dad has some rope lying around...");

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

    //Play method to start the game
    public void play() {
        printWelcome();

        startPlaying();

        System.out.println(kevin.getCurrentRoom().getLongDescription());

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
/*
            if (harry.getDelay() <= 0) {
                harry.walkPath();
            }
            if (marv.getDelay() <= 0) {
                marv.walkPath();
            }
            Item item = new Trap("Rope", 1);
            kevin.setTrap(item);
            System.out.println("trap set");
            */
            boolean objective1Complete = false; //Not in use yet, but will be crucial for when we write our win-conditions.
            if (status == WIN) {
                System.out.println("You won!");
                finished = true;
            }

            if (status == LOSE) {
                System.out.println("You lose!");
                finished = true;
            }
        }

        System.out.println("Thank you for playing. Good bye.");
    }

    //Welcome screen for when the player runs the program
    private void printWelcome() {
        System.out.println(
                "  _    _                                 _                  \n"
                + " | |  | |                          /\\   | |                 \n"
                + " | |__| | ___  _ __ ___   ___     /  \\  | | ___  _ __   ___ \n"
                + " |  __  |/ _ \\| '_ ` _ \\ / _ \\   / /\\ \\ | |/ _ \\| '_ \\ / _ \\\n"
                + " | |  | | (_) | | | | | |  __/  / ____ \\| | (_) | | | |  __/\n"
                + " |_|  |_|\\___/|_| |_| |_|\\___| /_/    \\_\\_|\\___/|_| |_|\\___|\n");
        System.out.println("After having a conversation with the old man, Marley, in the church at Christmas Eve, Kevin McCallister rushes home to his house.");
        System.out.println("He overheard earlier that day, that the two burglars, Marv and Harry, also known as \"The Wet Bandits\", are planning a burglary at his house.");
        System.out.println();
        System.out.println("Kevin rushes through the front door, switching the lights on and locking the door behind him.");
        System.out.println();
        System.out.println("  Kevin:");
        System.out.println("  \"This is my house. I have to defend it!\"");
        System.out.println();

        System.out.println("You'll be playing as Kevin McCallister. You must set up booby traps around the house to prevent the burglars from catching you.");
        System.out.println("You can move around the house by typing '" + CommandWord.GO + "' followed up by the available exitpoint.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need any help.");
        System.out.println();
        System.out.println("Your first objective is: " + objective);
    }

    // Method to act as a "start-button" for the game.
    private void startPlaying() {
        System.out.println("\nWhen you are ready, input 'play' to start the game.");
        boolean readyToPlay = false;
        // Will loop until user has input "play"
        while (readyToPlay == false) {
            Command command = parser.getCommand();
            //Scanner scanner = new Scanner(System.in);
            //String nextLine = scanner.nextLine();
            
            readyToPlay = processCommand(command);
        }
    }

    //Command processing, checking for a command and executing the associated method if the command exists.
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            // Does not count as an action
            printHelp(command);
        } else if (commandWord == CommandWord.GO) {
            /*goRoom(command) moved to Creature, call with Player object */
            // Counts as an action
            kevin.goRoom(command);
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        } else if (commandWord == CommandWord.EXAMINE) {
            // Counts as an action
            printInfo(kevin.getCurrentRoom().getInfo());
        } else if (commandWord == CommandWord.COLLECT) {
            // Counts as an action
            kevin.collectItem(command);
        } else if (commandWord == CommandWord.PLACE) {
            // Counts as an action
            kevin.placeTrap(command);
        } else if (commandWord == CommandWord.SHOW) {
            // Does not count as an action
            show(command);
        } else if(commandWord == CommandWord.START_GAME) {
            if(!this.started) {
                this.started = true;
                return true;
            }
        } else if(commandWord == CommandWord.DROP) {
            kevin.dropCommand(command);
        }
        return wantToQuit;
    }

    //Checks if a room has a setInfo that contains more than "", and prints the info.
    private void printInfo(String info) {
        if ("".equals(info)) {
            System.out.println("Kevin doesn't think that there's anything he can do here. Maybe try something elsewhere.");
        } else {
            System.out.print("Kevin's thoughts: \"");
            Trap t = kevin.getCurrentRoom().checkTraps();
            if(t != null) {
                System.out.println("I already set up a trap in this room. Better look somewhere else.\"");
                System.out.print("Trap: ");
                System.out.println(kevin.getCurrentRoom().getTrapString());
            } else {
                System.out.println(info + "\"\n");
            }
            System.out.print("Items nearby: ");
            if (kevin.getCurrentRoom().getItemString() == "") {
                System.out.println("There are no items here.");
            } else {
                System.out.println(kevin.getCurrentRoom().getItemString());
            }
            
        }
    }

    //Method used for calling the "help"-command that prints out instructions and commands.
    private void printHelp(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println();
            System.out.println("You're home alone. Your command words are:");
            System.out.println();
            parser.showCommands();
            System.out.println();
            System.out.println("Your current objective: ");
            System.out.println(getObjective());
            System.out.println("Type 'help' followed by the available command to get a detailed description of the command.");
            System.out.println();
            System.out.println("Exits: " + kevin.getCurrentRoom().getExitString());
            return;
        }
        String helpSecond = command.getSecondWord();

        if (null != helpSecond) {
            switch (helpSecond) {
                case "examine":
                    System.out.println("'Examine' will list the items that are currently placed in the room where Kevin's currently located.");
                    System.out.println("Kevin will also give you his thoughts on whether or not you can place a certain trap in the room.");
                    break;
                case "go":
                    System.out.println("'Go' is your primary navigation tool. Use 'go' followed by the available exitpoint to navigate the McCallister estate.");
                    break;
                case "show":
                    System.out.println("'Show' helps you keep track of either your inventory or your game objective.");
                    System.out.println("Combine 'show' with 'inventory' or 'objective', like so:");
                    System.out.println("   '>show inventory'");
                    System.out.println("...to get an overview of your inventory.");
                    break;
                case "setup":
                    System.out.println("'Setup' will try and place the mentioned item from your inventory in the room that you're currently in.");
                    System.out.println("If the item is a valid trap for the room, it will setup the trap.");
                    break;
                case "collect":
                    System.out.println("'Collect' will pick up the mentioned item from the room that you're currently located.");
                    break;
                case "drop":
                    System.out.println("'Drop' will drop an item, so there is space in inventory for another item.");
                    break;
                case "play":
                    System.out.println("'Play' is used for starting the game. It has no function once the game is started.");
                    break;
            }
        }
    }
/* Moved to Creature/Player
    //Method used for walking between rooms and checks for any invalid rooms.
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            System.out.println("Exits: " + kevin.getCurrentRoom().getExitString());
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = kevin.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            kevin.setCurrentRoom(nextRoom);
            System.out.println(kevin.getCurrentRoom().getLongDescription());
        }
    }
*/
    //Quit program method
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;
        }
    }

    private void show(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Show what?");
            System.out.println("show inventory | show objective");
            return;
        }

        String showSecond = command.getSecondWord();

        if (null != showSecond) {
            switch (showSecond) {
                case "inventory":
                    System.out.println(kevin.getInventory());
                    break;
                case "objective":
                    System.out.println(getObjective());
                    break;

            }
        }
    }

    private String getObjective() {
        return objective;
    }

//    private void collect() {
//
//        kevin.addToInventory(kevin.getCurrentRoom().);
//
//    }
}
