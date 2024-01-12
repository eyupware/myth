package gov.babalar.module.s.misc;

import gov.babalar.helpers.Mapper;
import gov.babalar.module.Category;
import gov.babalar.module.Module;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.Map;

/**
 * ----------
 * 9/15/2023
 * 11:52 PM
 * ----------
 **/
public class Phase extends Module {
    public Phase() {
        super("Phase", Category.MISC, NativeKeyEvent.VC_H);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
    public int ticks;
    public double lastX= Double.NaN;
    public double lastZ= Double.NaN;
    public double lastY= Double.NaN;
    public double xClip;
    public double zClip;

    @Override
    public void run() {
        if(Double.isNaN(lastX) || Double.isNaN(lastZ) || Double.isNaN(lastY)) {
            prepareLastValues();
            return;
        }
        Object blockPos = Mapper.getBlockPos();
        double currentX = Mapper.getX(blockPos);
        double currentZ = Mapper.getZ(blockPos);
        if(xClip == 0)
            xClip = Double.compare(currentX, lastX);
        if(zClip == 0)
             zClip = Double.compare(currentZ, lastZ);
        ticks++;
        if(ticks > 25)
        {
            ticks = 0;
            zClip = 0;
            xClip = 0;
            try {
                Mapper.teleport(currentX, Mapper.getY(Mapper.getBlockPos()), currentZ + zClip);
            }catch (Exception e)
            {

            }
        }
    }
    public void prepareLastValues()
    {
        Object blockPos = Mapper.getBlockPos();
        lastX = Mapper.getX(blockPos);
        lastY = Mapper.getY(blockPos);
        lastZ = Mapper.getZ(blockPos);
    }
}