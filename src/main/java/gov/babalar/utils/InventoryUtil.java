package gov.babalar.utils;

import gov.babalar.helpers.Mapper;

public class InventoryUtil {


    public static boolean isWhitelisted(Object itemStack)
    {
        String itemStackName = itemStack.toString().toLowerCase();
        return itemStackName.contains("applegold") || itemStackName.contains("tnt") || itemStackName.contains("chestplate") || itemStackName.contains("sword") || itemStackName.contains("leggings") || itemStackName.contains("pearl") | itemStackName.contains("helmet") || itemStackName.contains("boots") || itemStackName.contains("tile.");
    }

    public static void drop(int slot) {
        Object playerContainer = Mapper.getPlayerContainer();
        InventoryUtil.click(0, playerContainer, slot, 1, 4);
    }


    public static void equipArmor(int slot) {
        try {
            if (slot > 8) {
                Object playerContainer = Mapper.getPlayerContainer();
                InventoryUtil.click(playerContainer, slot, 0, 1);
            }
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {}
    }

    public static void moveItem(int slot, int newSlot) {
        try {
            if (slot != newSlot + 36) {
                Object playerContainer = Mapper.getPlayerContainer();
                // mc.thePlayer.sendQueue.addToSendQueue(new C0EPacketClickWindow(mc.thePlayer.openContainer.windowId, slot, hotbarNum, 2, mc.thePlayer.getHeldItem(), var6));
                InventoryUtil.click(playerContainer, slot, newSlot, 2);
            }
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {}
    }

    public static void throwItem(int slot) {
        try {
            Object playerContainer = Mapper.getPlayerContainer();
            InventoryUtil.click(playerContainer, slot, 1, 4);
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {}
    }
    public static void throwItem1(int slot) {
        try {
            Object playerContainer = Mapper.getPlayerContainer();
            Object lowerInventory = Mapper.getOpenContainer();
            int windowID = Mapper.getWindowIDfromContainer(playerContainer);
            Object clickedItem = Mapper.getItemStackFromSlot(slot, lowerInventory);
            Mapper.sendWindowPacket(windowID, slot, 1, 4, clickedItem, (short) 0);
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {}
    }

    public static void click(Object container, int slot, int usedButton, int mode)
    {
        int windowID = Mapper.getWindowIDfromContainer(container);
        click(windowID, container, slot, usedButton, mode);
        /*Object lowerInventory = Mapper.getLowerInventoryFromContainer(container);
        if(lowerInventory != null) {
            int windowID = Mapper.getWindowIDfromContainer(container);
            Object clickedItem = Mapper.getItemStackFromSlot(slot, lowerInventory);
            Mapper.sendWindowPacket(windowID, slot, usedButton, mode, clickedItem, (short) 0);
        } else {
            lowerInventory = Mapper.getOpenContainer();
            int windowID = Mapper.getWindowIDfromContainer(container);
            Object clickedItem = Mapper.getItemStackFromSlot(slot, lowerInventory);
            Mapper.sendWindowPacket(windowID, slot, usedButton, mode, clickedItem, (short) 0);
        }*/
    }

    public static void click(int windowID, Object container, int slot, int usedButton, int mode)
    {
        Object clickedItem = Mapper.slotClick(slot, usedButton, mode, container);
        Mapper.sendWindowPacket(windowID, slot, usedButton, mode, clickedItem, (short) 0);
    }

    public static void click1(Object container, int slot, int usedButton, int mode, Object clickedItem)
    {
        int windowID = Mapper.getWindowIDfromContainer(container);
        Mapper.sendWindowPacket(windowID, slot, usedButton, mode, clickedItem, (short) 0);
    }


    public static int getSlotId(int slot) {
        if (slot >= 36) {
            return 8 - (slot - 36);
        }
        if (slot < 9) {
            return slot + 36;
        }
        return slot;
    }
}
