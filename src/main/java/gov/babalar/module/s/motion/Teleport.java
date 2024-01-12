package gov.babalar.module.s.motion;

import gov.babalar.Myth;
import gov.babalar.helpers.Mapper;
import gov.babalar.mapping.s.MainMapping;
import gov.babalar.module.Category;
import gov.babalar.module.Module;
import org.jnativehook.keyboard.NativeKeyEvent;

public class Teleport extends Module {
    public Teleport() {
        super("Teleport", Category.MOTION, NativeKeyEvent.VC_H);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
        try {
            MainMapping.map.get("EntityLivingBase").getFields().get("movementFactor").setFloat(Mapper.getThePlayer(), 0.02f);
        }catch (Exception e) {
            Myth.logger.logException(e);
        }
    }

    @Override
    public void run() {
        try {
            MainMapping.map.get("EntityLivingBase").getFields().get("movementFactor").setFloat(Mapper.getThePlayer() , 3f);
        }catch (Exception e) {
            Myth.logger.logException(e);
        }
    }
}