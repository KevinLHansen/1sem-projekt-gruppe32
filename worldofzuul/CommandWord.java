package worldofzuul;

public enum CommandWord
{
    GO("go"), 
    QUIT("quit"), 
    HELP("help"), 
    UNKNOWN("?"), 
    EXAMINE("examine"), 
    COLLECT("collect"), 
    PLACE("place"), 
    SHOW("show"); //Syntax: "show <secondWord>", just like the "go"-command, where it shows something HUD related, like objectives, inventory etc.
    
    
    private String commandString;
    
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    public String toString()
    {
        return commandString;
    }
}
