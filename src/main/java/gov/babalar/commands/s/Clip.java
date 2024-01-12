package gov.babalar.commands.s;

import gov.babalar.Myth;
import gov.babalar.commands.Command;
import gov.babalar.helpers.Mapper;
import gov.babalar.management.ModuleManager;
import gov.babalar.mapping.s.MainMapping;
import gov.babalar.module.Module;
import org.lwjgl.input.Keyboard;

/**
 * ----------
 * 9/15/2023
 * 8:05 PM
 * ----------
 **/
public class Clip extends Command {

    public Clip() {
        super("clip" , "Makes you clip!");
    }


    @Override
    public void handle(String[] args) {
            if(args.length > 1) {
                double inc = Double.parseDouble(args[1]);
                try {
                    Mapper.teleport(Mapper.getX(Mapper.getBlockPos()), Mapper.getY(Mapper.getBlockPos()) + inc, Mapper.getZ(Mapper.getBlockPos()));
                    Mapper.addChatMessage(String.format("§aTeleported to §e%s §a%s blocks", inc, inc > 0 ? "up" : "down"));
                }catch (Exception e) {
                    Myth.logger.logException(e);
                }
            } else {
                Mapper.addChatMessage("§cWrong Usage!");
            }
    }

}
