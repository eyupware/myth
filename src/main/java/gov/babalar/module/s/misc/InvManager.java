package gov.babalar.module.s.misc;

import gov.babalar.helpers.Mapper;
import gov.babalar.module.Category;
import gov.babalar.module.Module;
import gov.babalar.utils.InventoryUtil;
import org.jnativehook.keyboard.NativeKeyEvent;

public class InvManager extends Module {

    public InvManager()
    {
        super("InvManager", Category.MISC, NativeKeyEvent.VC_Y);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void run() {
        for (int i = 0; i < 40; i++) {
            Object itemStack2 = Mapper.getStackFromPlayerInv(i);
            if (itemStack2 != null && !InventoryUtil.isWhitelisted(itemStack2)) {
                InventoryUtil.throwItem1(InventoryUtil.getSlotId(i));
            }
        }
    }

}
