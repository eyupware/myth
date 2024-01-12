package gov.babalar.commands.s;

import gov.babalar.commands.Command;
import gov.babalar.helpers.Mapper;
import gov.babalar.management.ModuleManager;
import gov.babalar.module.Module;
import org.lwjgl.input.Keyboard;

public class Toggle extends Command {

    public Toggle() {
        super("toggle" , "Gives info but this shit is for debugging so don't use if ur not a dev!");
    }


    @Override
    public void handle(String[] args) {//.toggle İSİM
        if(args.length > 1) {
            Module mod = ModuleManager.getModule(args[1]);
            if (mod != null) {
                mod.toggle();
            } else {
                Mapper.addChatMessage("§cModule not found!");
            }
        } else {
            Mapper.addChatMessage("§cWrong Usage!");
        }
    }

}

