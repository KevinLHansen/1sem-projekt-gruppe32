package HomeAlone.textUI;

import HomeAlone.business.AudioFile;
import HomeAlone.business.Game;
import java.time.LocalTime;

/**
 *
 * @author Gruppe 32
 */
public class PresentationControl {
    private Game game = new Game();
    private Parser parser;
    
    /*if(!this.started) {
        this.started = true;
        return true;
    }*/
    /*
    if (harry.getDelay() <= 0) {
        harry.walkPath();
    }
    if (marv.getDelay() <= 0) {
        marv.walkPath();
    }
    Item item = new Trap("Rope", 1);
    kevin.setTrap(item);
    out.printToScreen("trap set");
    */
    
    public PresentationControl() {
        parser = new Parser();
    }
    
    //Command processing, checking for a command and executing the associated method if the command exists.
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;
        String returnString = "";
        CommandWord commandWord = command.getCommandWord();

        if (commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            // Should not count as an action, but does for now
            printHelp(command);
        } else if (commandWord == CommandWord.GO) {
            // Counts as an action
            if (!command.hasSecondWord()) {
                System.out.println("Go where?\n");
            } else {
                String direction = command.getSecondWord();
                if(game.goRoom(direction)) {
                    returnString = game.getCurrentRoomLongDescription();
                    System.out.println(returnString);
                } else {
                    System.out.println("That is not an exit!");
                }
            }
        } else if (commandWord == CommandWord.QUIT) {
            if (command.hasSecondWord()) {
                System.out.println("Quit what?\n");
            } else {
                wantToQuit = game.quit();
            }
        } else if (commandWord == CommandWord.EXAMINE) {
            // Counts as an action
            returnString = game.getCurrentRoomInfo();
            if ("".equals(returnString)) {
                System.out.println("Kevin doesn't think that there's anything he can do here. Maybe try something elsewhere.");
            } else {
                System.out.print("Kevin's thoughts: \"");
                String t = game.getTrapInfo();
                if(t.equalsIgnoreCase("")) {
                    System.out.println(returnString + "\"");
                } else {
                    System.out.println("I already set up a trap in this room. Better look somewhere else.\"");
                    System.out.println("Trap: ");
                    System.out.println(game.getTrapString());
                }
                System.out.println("Items nearby: ");
                if (game.getItemString("room").equals("")) {
                    System.out.println("There are no items here.");
                } else {
                    System.out.println(game.getItemString("room"));
                }
            }
        } else if (commandWord == CommandWord.PICKUP) {
            // Counts as an action
            if (!command.hasSecondWord()) {
                System.out.println("Pickup what?\n" + game.getCurrentRoomItems()+"\n");
            } else {
                String secondWord = command.getSecondWord();
                if(game.pickupItem(secondWord)) {
                    returnString = "You pick up " + secondWord + " from " + game.getCurrentRoomShortDescription();
                } else {
                    returnString = game.getError("pickup");
                }
                System.out.println(returnString);
            }
        } else if (commandWord == CommandWord.PLACE) {
            // Counts as an action
            if (!command.hasSecondWord()) {
                System.out.println("Set up what?\n" + game.getItemString("inventory"));
            } else {
                String itemName = command.getSecondWord();
                if(game.setTrap(itemName)) {
                    returnString = "You set up a trap using " + itemName;
                } else {
                    returnString = game.getError("setup");
                }
            }
            System.out.println(returnString);
        } else if (commandWord == CommandWord.SHOW) {
            // Does not count as an action
            if (!command.hasSecondWord()) {
                System.out.println("Show what?\n");
                System.out.println("show inventory | show objective\n");
            } else {
                String showSecond = command.getSecondWord();
                returnString = game.show(showSecond);
                System.out.println(returnString);
            }
        } else if(commandWord == CommandWord.START_GAME) {
            System.out.println("\n  Kevin:\n");
            System.out.println("  \"This is my house. I have to defend it!\"\n");
            System.out.println("\n");
            
            System.out.println("Type '" + CommandWord.HELP + "' if you need any help.");
            System.out.println();
            System.out.println("Your first objective is: " + game.getObjective());
            System.out.println(game.getCurrentRoomLongDescription());

            play();
        } else if(commandWord == CommandWord.DROP) {
            if (!command.hasSecondWord()) {
                System.out.println("Drop what?\n" + game.getItemString("inventory"));
            } else {
                String secondWord = command.getSecondWord();
                if(game.dropItem(secondWord)) {
                    System.out.println("You dropped " + secondWord);
                } else {
                    System.out.println(game.getError("drop"));
                }
            }
        }
        return wantToQuit;
    }
    
    //Play method to start the game
    public void play() {
        // The time before phase 1 ends. Phase 1 = set up traps
        LocalTime endGame = LocalTime.now().plusMinutes(2);
        //endGame.plusMinutes(this.time);

        // instantiate AudioFile object for startquote sound
        AudioFile startQuote = null;
        startQuote = new AudioFile("sfx/startQuote.wav");
        // play the sound file
        startQuote.playFile();
        
        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);

            LocalTime time = LocalTime.now();
            if(time.isAfter(endGame)) {
                System.out.println("Time has run out. The wet bandits have arrived!");
                finished = true;
            }

            boolean objective1Complete = false; //Not in use yet, but will be crucial for when we write our win-conditions.

        }
        /**
         * Check if traps have been set, change status accordingly
         */
        game.checkObjectives();
        /*if (status == WIN) {
            System.out.println("You won!\n");
        }

        if (status == LOSE) {
            System.out.println("You lose!\n");
        }*/
        System.out.println("Thank you for playing. Good bye.\n");
    }
    
    // Method to act as a "start-button" for the game.
    public void startPlaying() {
        printWelcome();
        System.out.println("\nWhen you are ready, input 'play' to start the game.\n");
        //boolean readyToPlay = false;
        // Will loop until user has input "play"
        //while (readyToPlay == false) {
            Command command = parser.getCommand();
            processCommand(command);
            //Scanner scanner = new Scanner(System.in);
            //String nextLine = scanner.nextLine();

            //readyToPlay = processCommand(command);
        //}
    }
    
    public void printHelp(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println();
            System.out.println("You're home alone. Your command words are:");
            System.out.println();
            parser.showCommands();
            System.out.println();
            System.out.println("Your current objective: ");
            System.out.println(game.getObjective());
            System.out.println("Type 'help' followed by the available command to get a detailed description of the command.");
            System.out.println();
            System.out.println(game.printHelp());
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
                case "pickup":
                    System.out.println("'Pickup' will pick up the mentioned item from the room that you're currently located.");
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
    
    public void printWelcome() {
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
        
        System.out.println("You'll be playing as Kevin McCallister. You must set up booby traps around the house to prevent the burglars from catching you.");
        System.out.println("You can move around the house by typing '" + CommandWord.GO + "' followed up by the available exitpoint.");
        System.out.println("Use '" + CommandWord.EXAMINE + "' to examine the rooms you're in.");
        System.out.println();
        System.out.println("Type '" + CommandWord.HELP + "' if you need any help.");
        System.out.println();
        System.out.println("Your first objective is: " + game.getObjective());
    }
}
