package gov.babalar.module.s.misc;

import gov.babalar.Myth;
import gov.babalar.helpers.Mapper;
import gov.babalar.mapping.s.MainMapping;
import gov.babalar.module.Category;
import gov.babalar.module.Module;
import gov.babalar.utils.InventoryUtil;
import gov.babalar.utils.ReflectionUtils;
import gov.babalar.utils.TimerUtil;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ChestStealer extends Module {

    public ChestStealer()
    {
        super("ChestStealer", Category.MISC, NativeKeyEvent.VC_I);
    }


    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void run() {
        try {
            boolean bl1 = Mapper.getOpenContainer() != null && (MainMapping.map.get("ContainerChest").getClazz().isAssignableFrom(Mapper.getOpenContainer().getClass()) || MainMapping.map.get("ContainerChest").getClazz().isInstance(Mapper.getOpenContainer()));
            boolean bl = Mapper.getOpenContainer1() != null && (MainMapping.map.get("ContainerChest").getClazz().isAssignableFrom(Mapper.getOpenContainer1().getClass()) || MainMapping.map.get("ContainerChest").getClazz().isInstance(Mapper.getOpenContainer1()));
            if(bl || bl1) {
                Object openedContainer = bl ? Mapper.getOpenContainer1() : Mapper.getOpenContainer();
                int windowId = Mapper.getWindowIDfromContainer(openedContainer);
                Object lowerInventory = Mapper.getLowerInventoryFromContainer(openedContainer);
                if(lowerInventory == null)
                {
                    Myth.logger.log("nullptr lowerinventory");
                    return;
                }
                if (MainMapping.map.get("ContainerChest").getFields().get("inventorySize") == null) {
                    Field f = Arrays.stream(ReflectionUtils.getAllFields(openedContainer.getClass())).filter(field -> field.toString().contains("private int")).findFirst().orElse(null);
                    MainMapping.map.get("ContainerChest").getFields().put("inventorySize", f);
                }
                if (MainMapping.map.get("ContainerChest").getFields().get("inventoryContents") == null) {
                    MainMapping.map.get("ContainerChest").getFields().put("inventoryContents", Arrays.stream(ReflectionUtils.getAllFields1(lowerInventory.getClass())).filter(field -> field.getType().isArray())
                            .filter(field -> field.toString().contains("net.minecraft.")).findFirst().orElse(null));
                }
                Field inventoryContentsF = MainMapping.map.get("ContainerChest").getFields().get("inventoryContents");
                inventoryContentsF.setAccessible(true);
                Object[] inventoryContents = (Object[]) inventoryContentsF.get(lowerInventory);
                Field inventorySizeField = MainMapping.map.get("ContainerChest").getFields().get("inventorySize");
                inventorySizeField.setAccessible(true);
                int sizeInventory = (int) (inventorySizeField.get(Mapper.getCurrentScreen())) * 9;
                for (int i = 0; i < sizeInventory; i++) {
                    Object clickedItem = inventoryContents[i];
                    if (clickedItem != null && InventoryUtil.isWhitelisted(clickedItem) && TimerUtil.delayTimer(28.0)) {
                        Mapper.sendWindowPacket(windowId, i, 0, 1, clickedItem, (short) 0);
                        TimerUtil.resetTimer();
                    }
                }
            }
            /*boolean bl1 = Mapper.getOpenContainer() != null && (MainMapping.map.get("ContainerChest").getClazz().isAssignableFrom(Mapper.getOpenContainer().getClass()) || MainMapping.map.get("ContainerChest").getClazz().isInstance(Mapper.getOpenContainer()));
            boolean bl = Mapper.getOpenContainer1() != null && (MainMapping.map.get("ContainerChest").getClazz().isAssignableFrom(Mapper.getOpenContainer1().getClass()) || MainMapping.map.get("ContainerChest").getClazz().isInstance(Mapper.getOpenContainer1()));
            if(bl || bl1)
            {
                Object openedContainer = bl ? Mapper.getOpenContainer1() : Mapper.getOpenContainer();
                //Object lowerInventory = Mapper.getLowerInventoryFromContainer(openedContainer);
                int windowId = Mapper.getWindowIDfromContainer(openedContainer);

                Field inventorySizeField = Arrays.stream(ReflectionUtils.getAllFields(openedContainer.getClass())).filter(field -> field.toString().contains("private int")).findFirst().orElse(null);
                if(inventorySizeField == null)
                {
                    Myth.logger.log("InventorySizeField NULLPTR");
                    return;
                }
                inventorySizeField.setAccessible(true);
                int sizeInventory = (int) (inventorySizeField.get(openedContainer))*9;

                for(int i = 0; i < sizeInventory; i++)
                {
                    Object clickedItem;
                    if((clickedItem = Mapper.getItemStackFromSlot(i, lowerInventory)) != null && InventoryUtil.isWhitelisted(clickedItem) && TimerUtil.delayTimer(40.0) )
                    {
                        Mapper.sendWindowPacket(windowId, i, 0, 1, clickedItem, (short) 0 );
                        TimerUtil.resetTimer();
                    }
                }
            }*/
        }catch(Exception e)
        {
            Myth.logger.logException(e);
        }
    }


}
