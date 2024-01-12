package gov.babalar.commands.s;

import gov.babalar.commands.Command;
import gov.babalar.helpers.Mapper;
import gov.babalar.management.CommandManager;

/**
 * ----------
 * 9/15/2023
 * 8:05 PM
 * ----------
 **/
public class Help extends Command {

    public Help() {
        super("help");
    }

    public static void sendCommandInfo(Command command)
    {
        Mapper.addChatMessage(String.format("Name: %s" , command.name));
        Mapper.addChatMessage(String.format("Explanatory: %s" , command.desc));
    }

    @Override
    public void handle(String[] args) {
        CommandManager.commands.forEach(Help::sendCommandInfo);
    }
}
