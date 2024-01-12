package gov.babalar.helpers;

import gov.babalar.Main;
import gov.babalar.Myth;
import gov.babalar.mapping.s.MainMapping;
import gov.babalar.utils.ReflectionUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * ----------
 * 9/4/2023
 * 8:10 PM
 * ----------
 **/
public class Mapper {

    public static HashMap<String, Field> fields = new HashMap<>();
    public static HashMap<String, Method> methods = new HashMap<>();

    public static boolean onGround = true;


    public static void sendCustomPayload(String channel, String[] strings)
    {
        try {
            Class<?> packetBuffer = MainMapping.map.get("PacketBuffer").getClazz();
            Constructor<?> packetBufferConsturactor = packetBuffer.getDeclaredConstructor(ByteBuf.class);
            Object packetBufferInstance = packetBufferConsturactor.newInstance(Unpooled.buffer());
            for (Method method : ReflectionUtils.getAllMethods(packetBuffer)) {
                if(method.getReturnType().equals(packetBuffer) && method.toString().contains("public") && method.getParameterCount() == 1 && method.getParameterTypes()[0].equals(String.class))
                {
                    Mapper.methods.put("writeString", method);
                    break;
                }
            }
            for (String string : strings) {
                Mapper.methods.get("writeString").invoke(packetBufferInstance, string);
            }
            Class<?> customPayloadClass = Myth.SERVERBOUND_packets.get(23).getClass();
            Constructor<?> constructor = customPayloadClass.getDeclaredConstructor(String.class, packetBuffer);
            Object customPayload = constructor.newInstance(channel, packetBufferInstance);
            sendPacket(customPayload);
        } catch (Exception e) {
            Myth.logger.logException(e);
        }
    }

    public static void sendC03(boolean onGround)
    {
        try {
            Class<?> c03class = Myth.SERVERBOUND_packets.get(3).getClass();
            Constructor<?> constructor = c03class.getDeclaredConstructor(boolean.class);
            Object c03 = constructor.newInstance(onGround);
            sendPacket(c03);
        } catch (Exception e) {
            Myth.logger.logException(e);
        }
    }

    public static void sendC08BlockPlace(Object item, Object hitPos, Object side, Object hitVec) {
        try {
            double[] coordinates = Mapper.getCoordinatesFromVec(hitVec);
            float f1 = (float) (coordinates[0] - Mapper.getX(hitPos));
            float f2 = (float) (coordinates[1] - Mapper.getY(hitPos));
            float f3 = (float) (coordinates[2] - Mapper.getZ(hitPos));
            Class<?> c08class = Myth.SERVERBOUND_packets.get(8).getClass();
            Constructor<?> constructor = Arrays.stream(c08class.getDeclaredConstructors()).filter(constructor1 -> constructor1.getParameterCount() == 6).findFirst().orElse(null);
            int index = 0;
             if (side.toString().toUpperCase().contains("UP")) {
                index = 1;
            } else if (side.toString().toUpperCase().contains("NORTH")) {
                index = 2;
            } else if (side.toString().toUpperCase().contains("SOUTH")) {
                index = 3;
            } else if (side.toString().toUpperCase().contains("WEST")) {
                index = 4;
            } else if (side.toString().toUpperCase().contains("EAST")) {
                index = 5;
            }
            Object c08 = constructor.newInstance(hitPos, index, item, f1, f2, f3);
            sendPacket(c08);
        } catch (Exception e) {
            Myth.logger.logException(e);
        }
    }

    public static double[] getCoordinatesFromVec(Object vec)
    {
        try {
            String str = vec.toString().replace("(", "").replace(")", "");
            String[] strArray = str.split(",");
            return new double[]{Double.parseDouble(strArray[0]), Double.parseDouble(strArray[1]), Double.parseDouble(strArray[2])};
        } catch (Exception e) {
            Myth.logger.logException(e);
        }
        return new double[]{-1};
    }

    public static void sendC09HeldItem(int slot)
    {
        try {
            Class<?> c03class = Myth.SERVERBOUND_packets.get(9).getClass();
            Constructor<?> constructor = c03class.getDeclaredConstructor(int.class);
            Object c03 = constructor.newInstance(slot);
            sendPacket(c03);
        } catch (Exception e) {
            Myth.logger.logException(e);
        }
    }

    public static void attackEntity(Object entity)
    {
        try {
            Class<?> chatPacketClass = Myth.SERVERBOUND_packets.get(2).getClass();
            Object actionAttack = Mapper.fields.get("actionAttack").get(null);
            Constructor<?> constructor = chatPacketClass.getDeclaredConstructor(MainMapping.map.get("Entity").getClazz(), actionAttack.getClass());
            Object instance = constructor.newInstance(entity, actionAttack);
            for (Field field : ReflectionUtils.getAllFields(instance.getClass())) {
                if(field.toString().contains("private double"))
                {
                    field.setAccessible(true);
                    field.setDouble(instance, Math.min((Double)field.get(instance), 7.0795358018912395));
                }
            }
            sendPacket(instance);
        } catch (Exception e) {
            Myth.logger.logException(e);
        }
    }

    public static void sendChatMessage(String msg)
    {

        try {
            Class<?> chatPacketClass = Myth.SERVERBOUND_packets.get(1).getClass();
            Constructor<?> constructor = chatPacketClass.getDeclaredConstructor(String.class);
            Object instance = constructor.newInstance(msg);
            sendPacket(instance);
        } catch (Exception e) {
            Myth.logger.logException(e);
        }
    }

    public static float getFallDistance()
    {
        try {
            Field fallDistanceF = MainMapping.map.get("Entity").getFields().get("fallDistance");
            return fallDistanceF.getFloat(Mapper.getThePlayer());
        }catch(Exception e)
        {
            Myth.logger.logException(e);
        }
        return -1;
    }

    public static float getFallDistance1()
    {
        try {
            Field fallDistanceF = MainMapping.map.get("Entity").getFields().get("fallDistance1");
            return fallDistanceF.getFloat(Mapper.getThePlayer());
        }catch(Exception e)
        {
            Myth.logger.logException(e);
        }
        return -1;
    }

    public static void setFallDistance(float fallDistance)
    {
        try {
            Field fallDistanceF = MainMapping.map.get("Entity").getFields().get("fallDistance");
            fallDistanceF.setFloat(Mapper.getThePlayer(), fallDistance);
        }catch(Exception e)
        {
            Myth.logger.logException(e);
        }
    }

    public static void setFallDistance1(float fallDistance)
    {
        try {
            Field fallDistanceF = MainMapping.map.get("Entity").getFields().get("fallDistance1");
            fallDistanceF.setFloat(Mapper.getThePlayer(), fallDistance);
        }catch(Exception e)
        {
            Myth.logger.logException(e);
        }
    }


    public static void sendPacket(Object packet)
    {
        try {
            Object networkManager = getNetworkManager();

            for (Method method : ReflectionUtils.getAllMethods(networkManager.getClass())) {
                if(method.toString().contains("GenericFutureListener") && method.getParameterCount() == 2 && method.getParameterTypes()[0].equals(Myth.SERVERBOUND_packets.get(0).getClass().getInterfaces()[0]))
                {
                    method.setAccessible(true);
                    method.invoke(networkManager, packet, null);
                }
            }
        } catch (Exception e) {
            Myth.logger.logException(e);
        }
    }


    public static Object getNetworkManager()
    {
        try {
            Object netHandler = Mapper.methods.get("getNetHandler").invoke(Mapper.getMinecraft());
            Field networkManagerF = Mapper.fields.get("networkManager");
            networkManagerF.setAccessible(true);
            return networkManagerF.get(netHandler);
        }catch (Exception e)
        {
            Myth.logger.logException(e);
        }
        return null;
    }

    public static List<Object> getEntityList()
    {
        try {
            Object theWorld = MainMapping.map.get("WorldClient").getFields().get("theWorld").get(Mapper.getMinecraft());
            Field entityListField = MainMapping.map.get("WorldClient").getFields().get("entityList");
            return (List<Object>) entityListField.get(theWorld);
        }catch(Exception e)
        {
            Myth.logger.logException(e);
            return null;
        }
    }

    public static void netChannelRead0(ChannelHandlerContext ctx, Object packet)
    {
        try {
            if(Mapper.methods.get("channelRead0") == null)
            {
                for(Method m : ReflectionUtils.getAllMethods(MainMapping.map.get("NetworkManager").getClazz()))
                {
                    if(m.getParameterCount() == 2 && m.getParameterTypes()[0].equals(ChannelHandlerContext.class))
                    {
                        Mapper.methods.put("channelRead0", m);
                    }
                }
            }
            Mapper.methods.get("channelRead0").setAccessible(true);
            Mapper.methods.get("channelRead0").invoke(Mapper.getNetworkManager(), ctx, packet);
        }catch(Exception e)
        {
            Myth.logger.logException(e);
        }
    }

    public static double getX(Object blockPos)
    {
        return Double.parseDouble(blockPos.toString().split("x=")[1].split(",")[0]);
    }

    public static double getY(Object blockPos)
    {
        return Double.parseDouble(blockPos.toString().split("y=")[1].split(",")[0]);
    }

    public static double getZ(Object blockPos)
    {
        String zPos = blockPos.toString().split("z=")[1];
        return Double.parseDouble(zPos.replaceAll("[^0-9,-]", ""));
    }



    public static Object getBlockPos()
    {
        try {
            Constructor<?> blockPosInstance = MainMapping.map.get("BlockPos").getClazz().getConstructor(MainMapping.map.get("Entity").getClazz());
            return blockPosInstance.newInstance(Mapper.getThePlayer());
        }catch(Exception e)
        {
            Myth.logger.logException(e);
            return null;
        }
    }

    public static Object createBlockPos(double x, double y, double z)
    {
        try {
            Constructor<?> blockPosInstance = MainMapping.map.get("BlockPos").getClazz().getConstructor(double.class, double.class, double.class);
            return blockPosInstance.newInstance(x, y, z);
        }catch(Exception e)
        {
            Myth.logger.logException(e);
            return null;
        }
    }

    public static Double getDistance(Object entity)
    {
        try {
            return Math.sqrt((Double) MainMapping.map.get("Entity").getMethods().get("getDistanceSqToEntity").invoke(Mapper.getThePlayer(), entity));
        } catch (Exception e) {
            Myth.logger.logException(e);
            return null;
        }
    }
    public static void posPacket(double yAmp)
    {
        try {
            Class<?> c04 = Myth.SERVERBOUND_packets.get(4).getClass();
            Constructor<?> constr = c04.getDeclaredConstructor(double.class, double.class, double.class, boolean.class , float.class);

                double x = getX(Mapper.getBlockPos());
                double y = getY(Mapper.getBlockPos()) + yAmp;
                double z = getZ(Mapper.getBlockPos());
                Object c04obj = constr.newInstance(x, y, z, false, 0f);
                sendPacket(c04obj);

        }catch (Exception e)
        {
            Myth.logger.logException(e);
        }

    }
    public static Object getCurrentScreen()
    {
        try {
            return MainMapping.map.get("Minecraft").getFields().get("currentScreen").get(Mapper.getMinecraft());
        } catch (Exception e) {
            Myth.logger.logException(e);
            return null;
        }
    }
    public static void setCurrentScreen(Object object)
    {
        try {
            MainMapping.map.get("Minecraft").getMethods().get("displayGui").invoke(Mapper.getMinecraft(), object);
        } catch (Exception e) {
            Myth.logger.logException(e);
        }
    }

    public static Object getOpenContainer()
    {
        try {
            return MainMapping.map.get("EntityPlayer").getFields().get("openContainer").get(Mapper.getThePlayer());
        } catch (Exception e) {
            Myth.logger.logException(e);
            return null;
        }
    }

    public static Object getPlayerContainer()
    {
        try {
            Object playerContainer;
            boolean bl1 = Mapper.getOpenContainer() != null && (MainMapping.map.get("ContainerPlayer").getClazz().isAssignableFrom(Mapper.getOpenContainer().getClass()) || MainMapping.map.get("ContainerPlayer").getClazz().isInstance(Mapper.getOpenContainer()));
            boolean bl = Mapper.getOpenContainer1() != null && (MainMapping.map.get("ContainerPlayer").getClazz().isAssignableFrom(Mapper.getOpenContainer1().getClass()) || MainMapping.map.get("ContainerPlayer").getClazz().isInstance(Mapper.getOpenContainer1()));
            if(bl || bl1) {
                playerContainer = bl ? Mapper.getOpenContainer1() : Mapper.getOpenContainer();
                return playerContainer;
            }
            Myth.logger.log("player container cant found!");
            return null;
        } catch (Exception e) {
            Myth.logger.logException(e);
        }
        return null;
    }

    public static int getWindowIDfromContainer(Object container)
    {
        try {
            Field windowIDField = Arrays.stream(ReflectionUtils.getAllFields(container.getClass().getSuperclass()))
                    .filter(field -> field.toString().contains("public int")).findFirst().orElse(null);
            if (windowIDField == null) {
                Myth.logger.log("windowIDField NULLPTR");
                return 0;
            }
            return (int) windowIDField.get(container);
        }catch (Exception e)
        {
            Myth.logger.logException(e);
        }
        return 0;
    }


    public static Object getBlockState(Object blockPos)
    {
        try {
            Method blockStateMethod = MainMapping.map.get("World").getMethods().get("getBlockState");
            return blockStateMethod.invoke(Mapper.getTheWorld(), blockPos);
        }catch(Exception e)
        {
            Myth.logger.logException(e);
        }
        return null;
    }

    public static Object getBlock(Object blockState)
    {
        try {
            Field blockField = Arrays.stream(ReflectionUtils.getAllFields(blockState.getClass()))
                    .filter(field -> field.getType().equals(MainMapping.map.get("Block").getClazz()))
                    .findFirst().orElse(null);
            return ReflectionUtils.getValue(blockField, blockState);
            /*Method getBlockMethod = Arrays.stream(ReflectionUtils.getAllMethods1(blockState.getClass()))
            .filter(method -> method.getReturnType().equals(MainMapping.map.get("Block").getClazz()))
            .filter(method -> method.getParameterCount() == 0)
            .findFirst().orElse(null);
            getBlockMethod.setAccessible(true);
            return getBlockMethod.invoke(blockState);*/
        }catch(Exception e)
        {
            Myth.logger.logException(e);
        }
        return null;
    }

    public static boolean isSolid(Object block, Object blockPos)
    {
        if(block == null || blockPos == null)
            return false;
        try {
            /* isBlockSolid if throws error use isblocksolid1 */
            try {
                if (MainMapping.map.get("Block").getMethods().get("isBlockSolid") != null) {
                    Method blockStateMethod = MainMapping.map.get("Block").getMethods().get("isBlockSolid");
                    return (boolean) blockStateMethod.invoke(block, getTheWorld(), blockPos, null);
                }
            }catch (NullPointerException e)
            {
                if (MainMapping.map.get("Block").getMethods().get("isBlockSolid1") != null) {
                    MainMapping.map.get("Block").getMethods().remove("isBlockSolid");
                    Method blockStateMethod = MainMapping.map.get("Block").getMethods().get("isBlockSolid1");
                    return (boolean) blockStateMethod.invoke(block, getTheWorld(), blockPos, null);
                }
            }

            try {
                if (MainMapping.map.get("Block").getMethods().get("isBlockSolid1") != null) {
                    Method blockStateMethod = MainMapping.map.get("Block").getMethods().get("isBlockSolid1");
                    return (boolean) blockStateMethod.invoke(block, getTheWorld(), blockPos, null);
                }
            }catch (NullPointerException e)
            {
                if (MainMapping.map.get("Block").getMethods().get("isBlockSolid") != null) {
                    MainMapping.map.get("Block").getMethods().remove("isBlockSolid1");
                    Method blockStateMethod = MainMapping.map.get("Block").getMethods().get("isBlockSolid");
                    return (boolean) blockStateMethod.invoke(block, getTheWorld(), blockPos, null);
                }
            }

        }catch(Exception e)
        {
            Myth.logger.logException(e);
        }
        return false;
    }

    public static Object offset(Object blockPos, Object face)
    {
        try {
            Method offsetMethod = MainMapping.map.get("BlockPos").getMethods().get("offset");
            return offsetMethod.invoke(blockPos, face);
        }catch(Exception e)
        {
            Myth.logger.logException(e);
        }
        return null;
    }
    public static void teleport(double x , double y , double z) throws Exception
    {
        if(Mapper.getRotationYaw() != -1 && Mapper.getRotationPitch() != -1)
            MainMapping.map.get("Entity").getMethods().get("setPosAndRot").invoke(Mapper.getThePlayer(), x , y , z, Mapper.getRotationYaw(), Mapper.getRotationPitch());
        else
            MainMapping.map.get("Entity").getMethods().get("setPosAndRot").invoke(Mapper.getThePlayer(), x , y , z, 0f, 0f);
    }
    public static Object getOpposite(Object facing)
    {
        try {
            if (facing.toString().toUpperCase().contains("DOWN")) {
                return MainMapping.map.get("EnumFacing").getFields().get("UP").get(null);
            } else if (facing.toString().toUpperCase().contains("UP")) {
                return MainMapping.map.get("EnumFacing").getFields().get("DOWN").get(null);
            } else if (facing.toString().toUpperCase().contains("NORTH")) {
                return MainMapping.map.get("EnumFacing").getFields().get("SOUTH").get(null);
            } else if (facing.toString().toUpperCase().contains("SOUTH")) {
                return MainMapping.map.get("EnumFacing").getFields().get("NORTH").get(null);
            } else if (facing.toString().toUpperCase().contains("WEST")) {
                return MainMapping.map.get("EnumFacing").getFields().get("EAST").get(null);
            } else if (facing.toString().toUpperCase().contains("EAST")) {
                return MainMapping.map.get("EnumFacing").getFields().get("WEST").get(null);
            }
        }catch (Exception e)
        {
            Myth.logger.logException(e);
        }
        return null;
    }

    public static Object getItemStackFromSlot(int slot, Object lowerInventory)
    {
        try {
            Method getStackFromSlot = Arrays.stream(ReflectionUtils.getAllMethods1(lowerInventory.getClass()))
                    .filter(method -> method.getReturnType().toString().contains("net.minecraft."))
                    .filter(method -> method.getParameterCount() == 1)
                    .filter(method -> method.getParameterTypes()[0].equals(int.class))
                    .findFirst().orElse(null);

            if (getStackFromSlot == null) {
                Myth.logger.log("getStackFromSlot NULLPTR");
                return null;
            }
            return getStackFromSlot.invoke(lowerInventory, slot);
        }catch(Exception e)
        {
            Myth.logger.logException(e);
        }
        return null;
    }

    public static Object getLowerInventoryFromContainer(Object container)
    {
        try {
            Method lowerInventoryMethod = Arrays.stream(ReflectionUtils.getAllMethods(container.getClass()))
                    .filter(method -> method.getReturnType().isInterface())
                    .findFirst().orElse(null);
            if(lowerInventoryMethod == null)
            {
                Myth.logger.log("lowerInventory NULLPTR");
                return null;
            }
            return lowerInventoryMethod.invoke(container);
        }catch(Exception e)
        {
            Myth.logger.logException(e);
        }
        return null;
    }

    public static Object slotClick(int slotId, int clickedButton, int mode, Object container)
    {
        try {
            if(MainMapping.map.get("Container").getMethods().get("slotClick") == null) {
                MainMapping.map.get("Container").getMethods().put("slotClick", Arrays.stream(ReflectionUtils.getAllMethods1(container.getClass()))
                        .filter(method -> method.getReturnType().equals(MainMapping.map.get("ItemStack").getClazz()))
                        .filter(method -> method.getParameterCount() == 4)
                        .filter(method -> method.getParameterTypes()[0].equals(int.class))
                        .filter(method -> method.getParameterTypes()[1].equals(int.class))
                        .filter(method -> method.getParameterTypes()[2].equals(int.class))
                        .filter(method -> method.getParameterTypes()[3].equals(MainMapping.map.get("EntityPlayer").getClazz()))
                        .findFirst().orElse(null));
            }
            Method slotClickMethod = MainMapping.map.get("Container").getMethods().get("slotClick");
            if(slotClickMethod == null)
            {
                Myth.logger.log("SLOT CLICK NUL!!");
                return null;
            }
            return slotClickMethod.invoke(container, slotId, clickedButton, mode, Mapper.getThePlayer());
        }catch(Exception e)
        {
            Myth.logger.logException(e);
        }
        return null;
    }

    public static Object getOpenContainer1()
    {
        try {
            return MainMapping.map.get("EntityPlayer").getFields().get("openContainer1").get(Mapper.getThePlayer());
        } catch (Exception e) {
            Myth.logger.logException(e);
            return null;
        }
    }

    public static void sendWindowPacket(int windowId, int slot, int usedButton, int mode, Object clickedItem, short actionNumber)
    {
        try {
            Class<?> windowPacketClass = Myth.SERVERBOUND_packets.get(14).getClass();
            Constructor<?> constructor = Arrays.stream(windowPacketClass.getDeclaredConstructors()).filter(constructor31 -> constructor31.getParameterCount() == 6).filter(constructor1 -> constructor1.getParameterTypes()[0].equals(int.class)).findFirst().orElse(null);
            //Constructor<?> constructor = windowPacketClass.getDeclaredConstructor(int.class, int.class, int.class, int.class, clickedItem.getClass(), short.class);
            Object window = constructor.newInstance(windowId, slot, usedButton, mode, clickedItem, actionNumber);
            Mapper.sendPacket(window);
        }catch (Exception e)
        {
            Myth.logger.logException(e);
        }
    }

    public static String getPlayerName(Object entity)
    {
        try {
            Object gameProfile = null;
            for (Method method : ReflectionUtils.getAllMethods(MainMapping.map.get("EntityPlayer").getClazz())) {
                if(method.toGenericString().contains("public com.mojang.authlib.GameProfile"))
                {
                    gameProfile = method.invoke(entity);
                    break;
                }
            }
            if(gameProfile == null)
            {
                return null;
            }
            for (Method method : ReflectionUtils.getAllMethods(gameProfile.getClass())) {
                if(method.getReturnType().equals(String.class) && method.getName().contains("getName"))
                {
                    return (String) method.invoke(gameProfile);
                }
            }
        }catch(Exception e)
        {
            Myth.logger.logException(e);
        }
        return null;
    }

    public static void addChatMessage(String msg)
    {
        addChatMessage(msg, true);
    }

    public static void addChatMessage(String msg, boolean prefix)
    {

        msg = String.format(prefix ? "§s[Myth]§f %s" : "%s" , msg);
        try {
            Class<?> chatComponentText = MainMapping.map.get("ChatComponentText").getClazz();
            Constructor<?> stringChatText = chatComponentText.getConstructor(String.class);
            Object chatComponent = stringChatText.newInstance(msg);
            Method addMessage = MainMapping.map.get("IChatComponent").getMethods().get("addChatMessage");
            addMessage.invoke(getThePlayer(), chatComponent);
        } catch (Exception e) {
            Myth.logger.logException(e);
        }
    }

    public static Object getTheWorld()
    {
        try{
            return  MainMapping.map.get("WorldClient").getFields().get("theWorld").get(Mapper.getMinecraft());
        }catch(Exception e)
        {
            Myth.logger.logException(e);
            return null;
        }
    }
    
    public static Object getMinecraft()
    {
        try {
            return MainMapping.map.get("Minecraft").getMethods().get("getMinecraft").invoke(null);
        }catch (Exception e)
        {
            Myth.logger.logException(e);
            return null;
        }
    }

    public static Object getThePlayer()
    {
        try {
            return MainMapping.map.get("Minecraft").getFields().get("thePlayer").get(getMinecraft());
        }catch (Exception e)
        {
            Myth.logger.logException(e);
            return null;
        }
    }

    public static Object getStackFromPlayerInv(int index)
    {
        try {
            Object pInv = MainMapping.map.get("EntityPlayer").getFields().get("inventory").get(Mapper.getThePlayer());
            Object[] aitemstack = (Object[]) MainMapping.map.get("EntityPlayer").getFields().get("mainInventory").get(pInv);

            if (index >= aitemstack.length) {
                index -= aitemstack.length;
                aitemstack = (Object[]) MainMapping.map.get("EntityPlayer").getFields().get("armorInventory").get(pInv);
            }

            return aitemstack[index];
        }catch (Exception e)
        {
            Myth.logger.logException(e);
        }
        return null;
    }

    public Object getEquipmentInSlot(int slotIn) {
        try {
            Object pInv = MainMapping.map.get("EntityPlayer").getFields().get("inventory").get(Mapper.getThePlayer());
            Object[] armorInv = (Object[]) MainMapping.map.get("EntityPlayer").getFields().get("armorInventory").get(pInv);
            return armorInv[slotIn - 1];
        }catch (Exception e)
        {
            Myth.logger.logException(e);
        }
        return null;
    }

    public static Object[] getPlayerMainInv()
    {
        try {
            Object pInv = MainMapping.map.get("EntityPlayer").getFields().get("inventory").get(Mapper.getThePlayer());
            return (Object[]) MainMapping.map.get("EntityPlayer").getFields().get("mainInventory").get(pInv);
        } catch (Exception e) {
            Myth.logger.logException(e);
        }
        return null;
    }

    public static Object getPlayerInventory()
    {
        try {
            return MainMapping.map.get("EntityPlayer").getFields().get("inventory").get(Mapper.getThePlayer());
        } catch (Exception e) {
            Myth.logger.logException(e);
        }
        return null;
    }

    public static Object getCurrentItem()
    {
        return Mapper.getCurrentItemSlot() < 9 && Mapper.getCurrentItemSlot() >= 0 ? Mapper.getPlayerMainInv()[Mapper.getCurrentItemSlot()] : null;
    }

    public static int getCurrentItemSlot()
    {
        try {
            Object pInv = MainMapping.map.get("EntityPlayer").getFields().get("inventory").get(Mapper.getThePlayer());
            return (int) MainMapping.map.get("EntityPlayer").getFields().get("currentItemSlot").get(pInv);
        } catch (Exception e) {
            Myth.logger.logException(e);
        }
        return -1;
    }

    public static int getAmountFromString(String itemStack)
    {
        return Integer.parseInt(itemStack.split("x")[0]);
    }

    public static Channel getChannel()
    {
        try {
            Field channelField = Mapper.fields.get("channel");
            channelField.setAccessible(true);
            return (Channel) channelField.get(Mapper.getNetworkManager());
        }catch (Exception e)
        {
            Myth.logger.logException(e);
        }
        return null;
    }


    public static Object invokeMethod(Method method, Object instance, Object... args)
    {
        try {
            return method.invoke(instance, args);
        }catch (Exception e)
        {
            Myth.logger.logException(e);
        }
        return null;
    }


    public static void setMotionX(double x) {
        try {
            Field motionX = MainMapping.map.get("Entity").getFields().get("motionX");
            motionX.setDouble(Mapper.getThePlayer(), x);
        } catch (Exception e) {
            Myth.logger.logException(e);
        }
    }

    public static void setMotionY(double y) {
        try {
            MainMapping.map.get("Entity").getMethods().get("setMotionY").invoke(Mapper.getThePlayer() , y);
         //   motionY.setDouble(Mapper.getThePlayer(), y);
        } catch (Exception e) {
            Myth.logger.logException(e);
        }
    }

    public static void setMotionZ(double z) {
        try {
            Field motionZ = MainMapping.map.get("Entity").getFields().get("motionZ");
            motionZ.setDouble(Mapper.getThePlayer(), z);
        } catch (Exception e) {
            Myth.logger.logException(e);
        }
    }

    public static double getMotionX() {
        try {
            return MainMapping.map.get("Entity").getFields().get("motionX").getDouble(Mapper.getThePlayer());
        } catch (Exception e) {
            Myth.logger.logException(e);
        }
        return -1;
    }


    public static double getMotionY() {
        try {
            return MainMapping.map.get("Entity").getFields().get("motionY").getDouble(Mapper.getThePlayer());
        } catch (Exception e) {
            Myth.logger.logException(e);
        }
        return -1;
    }

    public static double getMotionZ() {
        try {
            return MainMapping.map.get("Entity").getFields().get("motionZ").getDouble(Mapper.getThePlayer());
        } catch (Exception e) {
            Myth.logger.logException(e);
        }
        return -1;
    }

    public static float getRotationYaw() {
        try {
            return MainMapping.map.get("Entity").getFields().get("rotationYaw").getFloat(Mapper.getThePlayer());
        } catch (Exception e) {
            Myth.logger.logException(e);
        }
        return -1;
    }

    public static float getRotationPitch() {
        try {
            return MainMapping.map.get("Entity").getFields().get("rotationPitch").getFloat(Mapper.getThePlayer());
        } catch (Exception e) {
            Myth.logger.logException(e);
        }
        return -1;
    }

    public static Object getMovementInput()
    {
        try {
            return MainMapping.map.get("EntityPlayerSP").getFields().get("movementInput").get(Mapper.getThePlayer());
        } catch (Exception e) {
            Myth.logger.logException(e);
        }
        return null;
    }

    public static float getMIStrafe()
    {
        try {
            return MainMapping.map.get("EntityPlayerSP").getFields().get("moveStrafe").getFloat(Mapper.getMovementInput());
        } catch (Exception e) {
            Myth.logger.logException(e);
        }
        return 0;
    }

    public static float getMIForward()
    {
        try {
            return MainMapping.map.get("EntityPlayerSP").getFields().get("moveForward").getFloat(Mapper.getMovementInput());
        } catch (Exception e) {
            Myth.logger.logException(e);
        }
        return 0;
    }

    public static boolean isOnGround()
    {
        return Mapper.getFallDistance() == 0.0F && Mapper.getFallDistance1() == 0.0F;
    }

}
