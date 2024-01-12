package gov.babalar.module.s.motion;

import gov.babalar.Myth;
import gov.babalar.helpers.Mapper;
import gov.babalar.mapping.s.MainMapping;
import gov.babalar.module.Category;
import gov.babalar.module.Module;
import org.jnativehook.keyboard.NativeKeyEvent;

public class Step extends Module {
    public Step() {
        super("Step", Category.MOTION, NativeKeyEvent.VC_Y);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
        try {
            MainMapping.map.get("Entity").getFields().get("stepHeight").setFloat(Mapper.getThePlayer(), 0.6f);
        }catch (Exception e) {
           Myth.logger.logException(e);
        }
    }

    @Override
    public void run() {
        try {
            MainMapping.map.get("Entity").getFields().get("stepHeight").setFloat(Mapper.getThePlayer() , 10f);
        }catch (Exception e) {
           Myth.logger.logException(e);
        }
    }
}