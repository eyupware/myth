package gov.babalar.module.s.misc;

import gov.babalar.helpers.Mapper;
import gov.babalar.module.Category;
import gov.babalar.module.Module;
import gov.babalar.utils.InventoryUtil;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.Locale;

public class AutoArmor extends Module {

    public AutoArmor()
    {
        super("AutoArmor", Category.MISC, NativeKeyEvent.VC_K);
    }


    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void run() {
        equipBest();
        /*Integer bestHelmet = null;
        Integer bestChestPlate = null;
        Integer bestLeggings = null;
        Integer bestBoots = null;
        Integer bestSword = null;
        for (int i = 0; i < 40; i++) {
            Object itemStack = Mapper.getStackFromPlayerInv(i);
            if(itemStack != null) {
                if(isArmor(itemStack.toString()))
                {
                    int armorType = getArmorType(itemStack.toString());
                    if(itemStack.toString().contains("helmet") && (bestHelmet == null || armorType > (getArmorType(Mapper.getStackFromPlayerInv(bestHelmet.intValue()).toString()))))
                    {
                        bestHelmet = i;
                    }
                    if(itemStack.toString().contains("chestplate") && (bestChestPlate == null || armorType > (getArmorType(Mapper.getStackFromPlayerInv(bestChestPlate.intValue()).toString()))))
                    {
                        bestChestPlate = i;
                    }
                    if(itemStack.toString().contains("leggings") && (bestLeggings == null || armorType > (getArmorType(Mapper.getStackFromPlayerInv(bestLeggings.intValue()).toString()))))
                    {
                        bestLeggings = i;
                    }
                    if(itemStack.toString().contains("boots") && (bestBoots == null || armorType > (getArmorType(Mapper.getStackFromPlayerInv(bestBoots.intValue()).toString()))))
                    {
                        bestBoots = i;
                    }
                }
                if(itemStack.toString().contains("sword")) {
                    int swordType = getSwordType(itemStack.toString());
                    if (bestSword == null || swordType > (getSwordType(Mapper.getStackFromPlayerInv(bestSword.intValue()).toString()))) {
                        bestSword = i;
                    }
                }
            }
        }
        if (bestHelmet != null) {
            InventoryUtil.equipArmor(InventoryUtil.getSlotId(bestHelmet));
        }
        if (bestChestPlate != null) {
            InventoryUtil.equipArmor(InventoryUtil.getSlotId(bestChestPlate));
        }
        if (bestLeggings != null) {
            InventoryUtil.equipArmor(InventoryUtil.getSlotId(bestLeggings));

        }
        if (bestBoots != null) {
            InventoryUtil.equipArmor(InventoryUtil.getSlotId(bestBoots));
        }
        if (bestSword != null) {
            InventoryUtil.moveItem(InventoryUtil.getSlotId(bestSword), InventoryUtil.getSlotId(-36));
        }*/
            /*Object itemStack = Mapper.getStackFromPlayerInv(bestSword);
            if(itemStack != null) {
                InventoryUtil.drop(InventoryUtil.getSlotId(bestSword));n
            } else {
                InventoryUtil.moveItem(InventoryUtil.getSlotId(bestSword), InventoryUtil.getSlotId(-36));
            }*/
        /*if (bestSword != null) {
            InventoryUtil.moveItem(InventoryUtil.getSlotId(bestSword), InventoryUtil.getSlotId(-36));
        }*/
    }

    private boolean equipBest()
    {
        int equipSlot = -1;
        for (int i = 9; i < 45; i++) {
            Object itemStack = Mapper.getStackFromPlayerInv(i);
            if(itemStack != null) {
                if (isArmor(itemStack.toString()))
                {
                    int armorType = getArmorType(itemStack.toString());
                    if(armorType > getArmorType(Mapper.getStackFromPlayerInv(equipSlot).toString()))
                    {
                        equipSlot = i;
                    }
                }
            }
        }
        if (equipSlot != -1) {
            InventoryUtil.equipArmor(equipSlot);
            return true;
        }
        return false;
    }



    public static int getSwordType(String itemStack)
    {
        String bb = itemStack.toLowerCase(Locale.ENGLISH);
        if(bb.contains("wood"))
        {
            return 0;
        } else if(bb.contains("stone"))
        {
            return 1;
        }else if(bb.contains("iron"))
        {
            return 2;
        }else if(bb.contains("gold"))
        {
            return 3;
        }else if(bb.contains("diamond"))
        {
            return 4;
        }else if(bb.contains("titanium"))
        {
            return 5;
        }
        return -1;
    }

    public static int getArmorType(String itemStack)
    {
        String bb = itemStack.toLowerCase(Locale.ENGLISH);
        if(bb.contains("cloth"))
        {
            return 0;
        } else if(bb.contains("chain"))
        {
            return 1;
        }else if(bb.contains("iron"))
        {
            return 2;
        }else if(bb.contains("gold"))
        {
            return 3;
        }else if(bb.contains("diamond"))
        {
            return 4;
        }else if(bb.contains("titanium"))
        {
            return 5;
        }
        return -1;
    }
    
    public boolean isArmor(String itemStack)
    {
        return itemStack.contains("helmet") || itemStack.contains("chestplate") || itemStack.contains("leggings") || itemStack.contains("boots");
    }

}
