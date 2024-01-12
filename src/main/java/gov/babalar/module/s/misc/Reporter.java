package gov.babalar.module.s.misc;

import gov.babalar.helpers.Mapper;
import gov.babalar.module.Category;
import gov.babalar.module.Module;

import java.util.ArrayList;
import java.util.List;

public class Reporter extends Module {

    List<String> reported = new ArrayList<>();


    public Reporter()
    {
        super("Reporter", Category.MISC, 0);
    }


    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        Mapper.addChatMessage(String.format("§eTotal Reported: §6%s", reported.size()));
    }

    @Override
    public void run() {
        for (Object o : Mapper.getEntityList()) {
            String name = Mapper.getPlayerName(o);
            if(o != Mapper.getThePlayer() && !reported.contains(name))
            {
                reported.add(name);
                Mapper.sendChatMessage("/report hile " + name);
            }
        }
    }
}
