package worldofzuul;

public class Game {

    
    
    private Parser parser;
    private Player kevin;
    private Nonplayer marv, harry;
    private String objective;
    public final int WIN = 1;
    public int status;
    public final int LOSE = -1;

    public Game() {
        createRooms();
        parser = new Parser();
        kevin = new Player("Kevin");
        marv = new Nonplayer("Marv");
        harry = new Nonplayer("Harry");
    }

    private void createRooms() {
        //Adding instances of rooms with descriptions.
        Room foyer, livingRoom, diningRoom, kitchen, staircase, secondFloor, attic, basement, masterBedroom, porch, nwGarden, nGarden, neGarden, wGarden, swGarden, seGarden, treehouse;

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

        //Setting exits and infos to rooms.
        //Infos are called through the "examine" command for the current room that the player currently is located.
        foyer.setExit("livingroom", livingRoom);
        foyer.setExit("outside", porch);
        foyer.setExit("upstairs", staircase);
        foyer.setExit("diningroom", diningRoom);
        foyer.setInfo("I can put my toy cars here...");

        livingRoom.setExit("foyer", foyer);
        livingRoom.setInfo("I can put some christmas ornaments by the window...");

        diningRoom.setExit("foyer", foyer);
        diningRoom.setExit("kitchen", kitchen);
        diningRoom.setInfo("I could setup a trap here with glue, a fan and some feathers...");

        kitchen.setExit("basement", basement);
        kitchen.setExit("diningroom", diningRoom);
        kitchen.setExit("outside", neGarden);
        kitchen.setInfo("I should get ready for when the crooks arrive. Buzz' BB gun could be useful if they decide to enter the backdoor... \nI could set up a blowtorch trap here...");

        staircase.setExit("up", secondFloor);
        staircase.setExit("down", foyer);
        staircase.setInfo("I should set up at least one trap here, so that they won't get upstairs without a fight...\nMaybe the buckets of paint could work out...");

        secondFloor.setExit("masterbedroom", masterBedroom);
        secondFloor.setExit("attic", attic);
        // secondFloor.setExit("room", room); 
        // Adding more rooms later depending on the items required and immersive experience.
        secondFloor.setInfo("Upstairs. Maybe a tripwire between the narrow hallway could slow them down...");

        attic.setExit("hallway", secondFloor);
        attic.setInfo("The attic is the perfect way for a zipline escape route to my treehouse! My dad has some rope laying around somewhere...");

        basement.setExit("kitchen", kitchen);
        basement.setExit("outside", neGarden);
        basement.setInfo("There's tonnes of stuff down here I can use to set up traps.\nMaybe I could set up a trap with a nail and some tar on the steps. Or even switch out the lightbulb switch with a trapdoor...\nI could also use my dad's heater on the front doorknob.");

        masterBedroom.setExit("hallway", secondFloor);
        masterBedroom.setInfo("Mom's and Dad's bedroom. There's a phone here I can use to call the cops when the crooks are inside the house...");

        porch.setExit("inside", foyer);
        porch.setExit("west", swGarden);
        porch.setExit("east", seGarden);
        porch.setInfo("There's nothing much here, but the door is very undefended. I could pour water over the stairs so that they slip when they try to get in...");

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

        wGarden.setExit("north", nwGarden);
        wGarden.setExit("south", swGarden);

        swGarden.setExit("north", wGarden);
        swGarden.setExit("east", porch);

        seGarden.setExit("west", porch);
        seGarden.setInfo("There's a bucket here. I can fill it with water and pour the water onto the steps at the front porch...");

        treehouse.setExit("down", nGarden);
        treehouse.setInfo("I need to set up an escape route here from the attic. My dad has some rope lying around...");

        //Setting starting-point to be inside at the front door, after Kevin returns from church and prepares his traps.
        kevin.currentRoom = foyer;
    }

    //Play method to start the game
    public void play() {
        printWelcome();

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
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
        System.out.println();
        System.out.println("  _    _                                 _                  \n"
                + " | |  | |                          /\\   | |                 \n"
                + " | |__| | ___  _ __ ___   ___     /  \\  | | ___  _ __   ___ \n"
                + " |  __  |/ _ \\| '_ ` _ \\ / _ \\   / /\\ \\ | |/ _ \\| '_ \\ / _ \\\n"
                + " | |  | | (_) | | | | | |  __/  / ____ \\| | (_) | | | |  __/\n"
                + " |_|  |_|\\___/|_| |_| |_|\\___| /_/    \\_\\_|\\___/|_| |_|\\___|\n"
                + "                                                            \n"
                + "                                                            ");
        System.out.println("After having a conversation with the old man, Marley, in the church at Christmas Eve, Kevin McCallister rushes home to his house.");
        System.out.println("He overheard earlier that day, that the two burglars, Marv and Harry, also known as \"The Wet Bandits\", planned a robbery at his house.");
        System.out.println();
        System.out.println("Kevin rushes through the front door, switching the lights on and locks the door behind him.");
        System.out.println();
        System.out.println("  Kevin:");
        System.out.println("  \"This is my house. I have to defend it!\"");
        System.out.println();
        System.out.println("You'll be taking the role as Kevin McCallister. You must set up booby traps around the house to prevent the burglars from catching you.");
        System.out.println("You can move around the house by typing '" + CommandWord.GO + "' followed up by the available exitpoint.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need any help.");
        System.out.println();
        System.out.println(kevin.currentRoom.getLongDescription());
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
            printHelp();
        } else if (commandWord == CommandWord.GO) {
            goRoom(command);
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        } else if (commandWord == CommandWord.EXAMINE) {
            printInfo(kevin.currentRoom.getInfo());
        } else if (commandWord == CommandWord.COLLECT) {

        } else if (commandWord == CommandWord.PLACE) {

        } else if (commandWord == CommandWord.SHOW) {
            show(command);
        }
        return wantToQuit;
    }

    //Checks if a room has a setInfo that contains more than "", and prints the info.
    private void printInfo(String info) {
        if (info == "") {
            System.out.println("Kevin doesn't think that there's anything he can do here. Maybe try something elsewhere.");
        } else {
            System.out.print("Kevin's thoughts: \"");
            System.out.print(info + "\"\n");
            
        }
    }

    //Method used for calling the "help"-command that prints out instructions and commands.
    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    //Method used for walking between rooms and checks for any invalid rooms.
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = kevin.currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            kevin.currentRoom = nextRoom;
            System.out.println(kevin.currentRoom.getLongDescription());
        }
    }

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

        Character Kevin = new Character();
        Game objective = new Game();

        if ("inventory".equals(showSecond)) {
            System.out.println(Kevin.getInventory());
        } else if ("objective".equals(showSecond)) {
            System.out.println(getObjective());
        }
    }

    private String getObjective() {
        return objective;
    }
}
