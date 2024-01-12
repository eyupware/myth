package gov.babalar.commands.s;

import gov.babalar.Myth;
import gov.babalar.classdefiner.ClassDefiner;
import gov.babalar.commands.Command;
import gov.babalar.fordefine.Gui;
import gov.babalar.helpers.Mapper;
import gov.babalar.management.ModuleManager;
import gov.babalar.mapping.s.MainMapping;
import org.jnativehook.keyboard.NativeKeyEvent;

/**
 * ----------
 * 9/15/2023
 * 8:05 PM
 * ----------
 **/
public class Info extends Command {

    public Info() {
        super("info" , "Gives info but this shit is for debugging so don't use if ur not a dev!");
    }


    @Override
    public void handle(String[] args) {
        ModuleManager.modules.forEach(module ->
        {
            Mapper.addChatMessage(String.format("§a%s §8- §f%s §8- §6%s" , module.getName() , module.isEnabled() ? "§aEnabled" : "§cDisabled", (NativeKeyEvent.getKeyText(module.getKey()))));
        });
    }

}
