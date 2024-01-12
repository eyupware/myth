package gov.babalar.commands.s;

import gov.babalar.commands.Command;
import gov.babalar.helpers.Mapper;
import gov.babalar.management.CommandManager;
import gov.babalar.management.ModuleManager;
import gov.babalar.module.Module;
import org.lwjgl.input.Keyboard;

/**
 * ----------
 * 9/15/2023
 * 8:05 PM
 * ----------
 **/
public class Bind extends Command {

    public Bind() {
        super("bind" , "Ables you to change keybindings of specified module");
    }


    @Override
    public void handle(String[] args) {
        if(args.length > 1) {
            Module mod = ModuleManager.getModule(args[1]);
            if (mod != null) {
                if(args.length > 2) {
                    int key = Keyboard.getKeyIndex(args[2].toUpperCase());
                    if (key != -1) {
                        mod.setKey(key);
                        Mapper.addChatMessage("§f" + mod.getName() + " has been set to: " + Keyboard.getKeyName(mod.getKey()));
                    } else {
                        Mapper.addChatMessage("§cKey not found!");
                    }
                } else {
                    Mapper.addChatMessage("§f" +  mod.getName() + "'s bind has been cleared.");
                    mod.setKey(0);
                }
            } else {
                Mapper.addChatMessage("§cModule not found!");
            }
        } else {
            Mapper.addChatMessage("§cWrong Usage!");
        }
    }

}
