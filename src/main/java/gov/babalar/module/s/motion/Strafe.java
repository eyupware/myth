package gov.babalar.module.s.motion;

import gov.babalar.module.Category;
import gov.babalar.module.Module;
import gov.babalar.utils.MovementUtil;
import org.jnativehook.keyboard.NativeKeyEvent;

public class Strafe extends Module {

    public Strafe() {
        super("Strafe", Category.MOTION, NativeKeyEvent.VC_U);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void run() {
        MovementUtil.setSpeed(MovementUtil.getSpeed());
    }
}
