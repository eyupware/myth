package gov.babalar.module.s.motion;

import gov.babalar.helpers.Mapper;
import gov.babalar.module.Category;
import gov.babalar.module.Module;
import gov.babalar.utils.MovementUtil;
import org.jnativehook.keyboard.NativeKeyEvent;

public class Speed extends Module {

    public Speed()
    {
        super("Speed", Category.MOTION, NativeKeyEvent.VC_Z);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void run() {
        if (!MovementUtil.isMoving())
            return;
        if (Mapper.isOnGround()) {
            MovementUtil.setSpeed(0.8D);
            return;
        }
        MovementUtil.setSpeed(1.0);
    }
}
