package gov.babalar.module.s.misc;

import gov.babalar.helpers.Mapper;
import gov.babalar.module.Category;
import gov.babalar.module.Module;
import org.jnativehook.keyboard.NativeKeyEvent;

public class NoFall extends Module {

    public NoFall()
    {
        super("NoFall", Category.MISC, NativeKeyEvent.VC_X);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void run() {
        if(Mapper.getFallDistance() > 2F || Mapper.getFallDistance1() > 2F)
        {
            Mapper.sendC03(true);
            if(Mapper.getFallDistance() > 2F)
                Mapper.setFallDistance(0.0F);
           else
                Mapper.setFallDistance1(0.0F);
        }
    }
}
