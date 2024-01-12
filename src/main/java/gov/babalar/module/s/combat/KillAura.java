package gov.babalar.module.s.combat;

import gov.babalar.helpers.Mapper;
import gov.babalar.module.Category;
import gov.babalar.module.Module;
import gov.babalar.utils.InventoryUtil;
import gov.babalar.utils.ReflectionUtils;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Objects;

public class  KillAura extends Module {
    public KillAura() {
        super("KillAura", Category.MOTION, NativeKeyEvent.VC_R);
    }
    public short ticks = 0;
    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void run() {
        if(Mapper.getEntityList() == null)
        {
            return;
        }
        /*ticks++;
        if(ticks > 8)
            ticks = 0;*/
       for (Object o : Mapper.getEntityList()) {

           if(o != Mapper.getThePlayer() && Mapper.getDistance(o) < 6.3)
           {
               Mapper.attackEntity(o);
                /*switch (ticks)
                {
                    case 0:
                        Mapper.posPacket(0.062 + (0.05 * Math.random()));
                        Mapper.attackEntity(o);
                        break;
                    case 6:
                        Mapper.posPacket(0.062 + (0.05 * Math.random()));
                        Mapper.posPacket(0);
                        Mapper.attackEntity(o);
                        break;
                }*/
           }
       }
    }

}