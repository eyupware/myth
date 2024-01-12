package gov.babalar.management;


import gov.babalar.commands.Command;
import gov.babalar.commands.s.*;
import gov.babalar.helpers.Mapper;

import java.util.ArrayList;

/**
 * ----------
 * 9/15/2023
 * 8:00 PM
 * ----------
 **/
public class CommandManager {
    public static ArrayList<Command> commands = new ArrayList<>();
    static {
        commands.add(new Bind());
        commands.add(new Help());
        commands.add(new Clip());
        commands.add(new Info());
        commands.add(new Toggle());
    }
    public static void handle(String command)
    {
       Command forHandle =  commands.stream().filter(iCommand -> command.toLowerCase().startsWith(iCommand.name.toLowerCase())).findFirst().orElse(null);
       if(forHandle == null)
       {
           Mapper.addChatMessage("That's not even a command you stupid mf!");
           return;
       }
       String[] ary = command.split(" ");
       forHandle.handle(ary);
    }
}
