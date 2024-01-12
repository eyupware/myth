package gov.babalar.commands;

/**
 * ----------
 * 9/15/2023
 * 8:07 PM
 * ----------
 **/
public abstract class Command {
    public String name;
    public String desc;
    public abstract void handle(String[] args);
    public Command(String name) {
        this.name = name;
        this.desc = "Self explanatory.";
    }
    public Command(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
}
