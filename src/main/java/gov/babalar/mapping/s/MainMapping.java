package gov.babalar.mapping.s;

import gov.babalar.Myth;
import gov.babalar.helpers.Mapper;
import gov.babalar.helpers.PacketThread;
import gov.babalar.management.packets.PacketReader;
import gov.babalar.management.packets.PacketSent;
import gov.babalar.mapping.clazzes.*;
import gov.babalar.mapping.components.MappingClazz;
import gov.babalar.utils.ClassUtils;
import gov.babalar.utils.ReflectionUtils;
import io.netty.channel.Channel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/**
 * ----------
 * 9/4/2023
 * 7:58 PM
 * ----------
 **/
public class MainMapping {
    public static HashMap<String,MappingClazz> map = new HashMap<>();

    public static final Thread packetThread = new PacketThread();
    public static void generate()
    {
        map.put("EntityPlayerSP", new EntityPlayerSPClazz());
        map.put("Minecraft", new MinecraftClazz());
        map.put("EntityPlayer" , new EntityPlayerClazz());
        map.put("EntityLivingBase" , new AutoClazzGeneration(map.get("EntityPlayer").getClazz().getSuperclass()));
        map.put("Entity" , new AutoClazzGeneration(map.get("EntityPlayer").getClazz().getSuperclass().getSuperclass()));
        map.put("EnumConnectionState", new EnumConnectionStateClazz());
        map.put("PacketRegisterer", new PacketRegistererClazz());
        map.put("WorldClient", new WorldClientClazz());
        map.put("World" , new AutoClazzGeneration(map.get("WorldClient").getClazz().getSuperclass()));
        map.put("PacketBuffer", new PacketBufferClazz());
        map.put("NetworkManager", new NetworkManagerClazz());
        map.put("PlayerCapabilities", new PlayerCapabilitiesClazz());
        map.put("PlayerControllerMP", new PlayerControllerMPClazz());
        map.put("GuiScreen" , new GuiScreenClazz());
        map.put("IChatComponent", new IChatComponentClazz());
        map.put("ChatComponentText", new ChatComponentTextClazz());
        map.put("AxisAllignedBB" , new BoundingBoxClazz());
        map.put("Container", new ContainerClazz());
        map.put("ContainerChest", new ContainerChestClazz());
        //map.put("ContainerPlayer", new ContainerPlayerClazz());
        map.put("ItemStack", new ItemStackClazz());
        map.put("Vec3", new Vec3Clazz());
        map.put("EnumFacing", new EnumFacingClazz());
        map.put("BlockPos", new BlockPosClazz());
        map.put("Material", new MaterialClazz());
        map.put("Block", new BlockClazz());
        map.put("GuiInGame" , new GuiInGameClazz());
        map.put("FontRenderer" , new FontRendererClazz());
        map.put("GuiChest" , new GuiChestClazz());
        generateFieldAndMethods();
    }
    public static void generateFieldAndMethods()
    {
        map.get("Minecraft").getFields().put("guiInGame" , Arrays.stream(ReflectionUtils.getAllFields(map.get("Minecraft").getClazz())).filter(field -> field.getType().equals(map.get("GuiInGame").getClazz())).findFirst().orElse(null));
        map.get("Minecraft").getMethods().put("displayGui", Arrays.stream(ReflectionUtils.getAllMethods(map.get("Minecraft").getClazz())).filter(method -> method.toString().contains("public void") && method.getParameterCount() == 1 && method.getParameters()[0].getType().equals(map.get("GuiScreen").getClazz())).findFirst().orElse(null));
        map.get("Entity").getMethods().put("getDistanceSqToEntity", Arrays.stream(ReflectionUtils.getAllMethods(map.get("Entity").getClazz())).filter(method -> method.toString().contains("public double") && method.getParameterCount() == 1 && method.getParameterTypes()[0].equals(map.get("Entity").getClazz())).findFirst().orElse(null));
        map.get("EntityLivingBase").getFields().put("movementFactor", Arrays.stream(ReflectionUtils.getAllFields(map.get("EntityLivingBase").getClazz()))
                .filter(field -> field.toString().contains("public float"))
                .filter(field -> {
                    try {
                        return field.getFloat(Mapper.getThePlayer()) == 0.02f;
                    } catch (Exception e) {
                        Myth.logger.logException(e);
                    }
                    return false;
                }).findFirst().orElse(null));
        map.get("Entity").getFields().put("stepHeight", Arrays.stream(ReflectionUtils.getAllFields(map.get("Entity").getClazz()))
                .filter(field -> field.toString().contains("public float"))
                .filter(field -> {
                    try {
                        return field.getFloat(Mapper.getThePlayer()) == 0.6F;
                    } catch (Exception e) {
                        Myth.logger.logException(e);
                    }
                    return false;
                }).findFirst().orElse(null));
        try {
            map.get("Entity").getMethods().put("setMotionY" , Arrays.stream(ReflectionUtils.getAllMethods(map.get("Entity").getClazz())).filter(method -> method.toString().contains("public void")).filter(method -> method.getParameterCount() == 1 && method.getParameters()[0].getType().equals(double.class)).findFirst().orElse(null));
            map.get("World").getMethods().put("getBlockState", Arrays.stream(ReflectionUtils.getAllMethods(map.get("World").getClazz()))
                    .filter(method -> method.getReturnType().isInterface())
                            .filter(method -> method.getParameterCount() == 1 && method.getParameterTypes()[0].equals(map.get("BlockPos").getClazz()))
                    .findFirst().orElse(null));
            Field motionX = null;
            Field motionY = null;
            Field motionZ = null;
            for (Field field : ReflectionUtils.getAllFields(map.get("Entity").getClazz())) {
                if (field.toString().contains("public double")) {
                    double fieldValue = field.getDouble(Mapper.getThePlayer());
                    if (fieldValue == 0.0) {
                        if(motionZ == null)
                        {
                            motionZ = field;
                            continue;
                        }
                        if(motionY == null)
                        {
                            motionY = field;
                            continue;
                        }
                        if(motionX == null)
                        {
                            motionX = field;
                        }
                    }
                }
            }
            map.get("Entity").getFields().put("motionX", motionX);
            map.get("Entity").getFields().put("motionY", motionY);
            map.get("Entity").getFields().put("motionZ", motionZ);

            map.get("Minecraft").getFields().put("currentScreen" , Arrays.stream(ReflectionUtils.getAllFields(map.get("Minecraft").getClazz())).filter(field -> field.getType().equals(map.get("GuiScreen").getClazz())).findFirst().orElse(null));

            int i = 0;
            for (Field field : ReflectionUtils.getAllFields(map.get("EntityPlayer").getClazz())) {
                if(field.getType().equals(map.get("Container").getClazz()))
                {
                    i++;
                    if(i == 2) {
                        map.get("EntityPlayer").getFields().put("openContainer", field);
                    } else if (i == 1)
                    {
                        map.get("EntityPlayer").getFields().put("openContainer1", field);
                    }
                }
            }
            //map.get("EntityPlaye").getFields().put("inventory",)
            map.get("EntityPlayer").getFields().put("inventory", Arrays.stream(ReflectionUtils.getAllFields(map.get("EntityPlayer").getClazz())).filter(field -> field.toString().contains("public")).filter(field -> field.getType().getConstructors().length == 1).filter(field -> field.getType().getConstructors()[0].getParameterCount() == 1 && field.getType().getConstructors()[0].getParameterTypes()[0].equals(map.get("EntityPlayer").getClazz())).findFirst().orElse(null));
            map.get("EntityPlayer").getFields().put("currentItemSlot",
                    Arrays.stream(ReflectionUtils.getAllFields(map.get("EntityPlayer").getFields().get("inventory").getType()))
                    .filter(field -> field.toString().contains("public"))
                    .filter(field -> field.getType().equals(int.class))
                    .findFirst().orElse(null));
            Field[] inventories = Arrays.stream(ReflectionUtils.getAllFields(map.get("EntityPlayer").getFields().get("inventory").get(Mapper.getThePlayer()).getClass()))
                    .filter(field -> field.getType().isArray())
                    .filter(field -> field.getType().getName().contains(map.get("ItemStack").getClazz().getName())).toArray(Field[]::new);
            for (Field inventory : inventories) {
                Object[] objArray = (Object[]) inventory.get(map.get("EntityPlayer").getFields().get("inventory").get(Mapper.getThePlayer()));
                if(objArray.length == 4)
                {
                    map.get("EntityPlayer").getFields().put("armorInventory", inventory);
                }
                if(objArray.length == 36)
                {
                    map.get("EntityPlayer").getFields().put("mainInventory", inventory);
                }
            }
            if(Mapper.getOpenContainer() == Mapper.getOpenContainer1())
            {
                map.put("ContainerPlayer" , new AutoClazzGeneration(Mapper.getOpenContainer().getClass()));
            }
            map.get("Entity").getFields().put("fallDistance", Arrays.stream(ReflectionUtils.getAllFields(map.get("Entity").getClazz()))
                    .filter(field -> field.getType().equals(float.class))
                            .filter(field -> {
                                try {
                                    return field.getFloat(Mapper.getThePlayer()) == 0.0F;
                                } catch (Exception e) {
                                    Myth.logger.logException(e);
                                }
                                return false;
                            })
                    .findFirst().orElse(null));
            map.get("Entity").getFields().put("fallDistance1" , Arrays.stream(ReflectionUtils.getAllFields(map.get("Entity").getClazz()))
                    .filter(field -> field.getType().equals(float.class))
                    .filter(field -> !field.equals(map.get("Entity").getFields().get("fallDistance")))
                    .filter(field -> {
                        try {
                            return field.getFloat(Mapper.getThePlayer()) == 0.0F;
                        } catch (Exception e) {
                            Myth.logger.logException(e);
                        }
                        return false;
                    })
                    .findFirst().orElse(null));
            map.get("Entity").getMethods().put("setPosAndRot" , Arrays.stream(ReflectionUtils.getAllMethods(map.get("Entity").getClazz())).filter(method -> method.getParameterCount() == 5 && method.getParameters()[0].getType().equals(double.class) && method.getParameters()[1].getType().equals(double.class) && method.getParameters()[2].getType().equals(double.class) && method.getParameters()[3].getType().equals(float.class) && method.getParameters()[4].getType().equals(float.class)).findFirst().orElse(null));

            Class<?> netHandlerClass = Arrays.stream(ReflectionUtils.getAllFields(MainMapping.map.get("EntityPlayerSP").getClazz()))
                    .filter(field -> field.toString().contains("public final"))
                    .findFirst().orElse(null).get(Mapper.getThePlayer()).getClass();

            Method getNetHandler = Arrays.stream(ReflectionUtils.getAllMethods(MainMapping.map.get("Minecraft").getClazz()))
                    .filter(method -> method.getReturnType().equals(netHandlerClass))
                    .filter(method -> method.getParameterCount() == 0)
                    .findFirst().orElse(null);


            Mapper.methods.put("getNetHandler", getNetHandler);
            Object netHandler = Mapper.methods.get("getNetHandler").invoke(Mapper.getMinecraft());
            for (Field field : ReflectionUtils.getAllFields(netHandler.getClass())) {
                if(field.getType().equals(MainMapping.map.get("NetworkManager").getClazz()))
                {
                    Mapper.fields.put("networkManager", field);
                    break;
                }
            }
            Mapper.fields.put("channel", Arrays.stream(ReflectionUtils.getAllFields(Objects.requireNonNull(Mapper.getNetworkManager()).getClass()))
                    .filter(field -> field.getType().equals(Channel.class)).findFirst().orElse(null));
            map.put("Packet" , new AutoClazzGeneration(Myth.SERVERBOUND_packets.get(0).getClass()));
            /*map.get("Packet").getMethods().put("processPacket", Arrays.stream(ReflectionUtils.getAllMethods(map.get("Packet").getClazz()))
                    .filter(method -> method.getParameterCount() == 1)
                    .filter(method -> {
                        if(method.getParameterTypes()[0].toGenericString().contains("public abstract interface"))
                        {
                            INetHandler.set(method.getParameterTypes()[0]);
                            return true;
                        }
                        return false;
                    })
                    .findFirst().orElse(null));*/
            Field channelField = Mapper.fields.get("channel");
            channelField.setAccessible(true);
            Channel channel = (Channel) channelField.get(Mapper.getNetworkManager());
            float rotationYawSS = 130.12357890f;
            float rotationPitch = 60.9246781f;
            MainMapping.map.get("Entity").getMethods().get("setPosAndRot").invoke(Mapper.getThePlayer() , Mapper.getX(Mapper.getBlockPos()) , Mapper.getY(Mapper.getBlockPos()), Mapper.getZ(Mapper.getBlockPos()) , rotationYawSS , rotationPitch);
            MainMapping.map.get("Entity").getFields().put("rotationYaw",
                    Arrays.stream(ReflectionUtils.getAllFields(MainMapping.map.get("Entity").getClazz()))
                            .filter(field -> field.getType().equals(float.class))
                            .filter(field -> {
                                try {
                                    if(field.getFloat(Mapper.getThePlayer()) == rotationYawSS)
                                    {
                                        return true;
                                    }
                                } catch (Exception e) {
                                    Myth.logger.logException(e);
                                }
                                return false;
                            }).findFirst().orElse(null));
            MainMapping.map.get("Entity").getFields().put("rotationPitch",
                    Arrays.stream(ReflectionUtils.getAllFields(MainMapping.map.get("Entity").getClazz()))
                            .filter(field -> field.getType().equals(float.class))
                            .filter(field -> {
                                try {
                                    if(field.getFloat(Mapper.getThePlayer()) == rotationPitch)
                                    {
                                        return true;
                                    }
                                } catch (Exception e) {
                                    Myth.logger.logException(e);
                                }
                                return false;
                            }).findFirst().orElse(null));
            MainMapping.map.get("EntityPlayerSP").getFields().put("movementInput", Arrays.stream(ReflectionUtils.getAllFields(MainMapping.map.get("EntityPlayerSP").getClazz()))
                    .filter(field -> !field.getType().equals(float.class))
                    .filter(field -> !field.getType().equals(String.class))
                    .filter(field -> !field.getType().equals(int.class))
                    .filter(field -> !field.getType().equals(boolean.class))
                    .filter(field -> !field.toString().contains("final"))
                    .filter(field -> field.toString().contains("public")).findFirst().orElse(null));
            if(Mapper.getMovementInput() != null) {
                int counter = 0;
                for (Field field : ReflectionUtils.getAllFields(MainMapping.map.get("EntityPlayerSP").getFields().get("movementInput").getType())) {
                    if(!field.getType().equals(float.class)) continue;
                    ++counter;
                    if(counter == 1)
                    {
                        MainMapping.map.get("EntityPlayerSP").getFields().put("moveStrafe", field);
                        continue;
                    }
                    if(counter == 2)
                    {
                        MainMapping.map.get("EntityPlayerSP").getFields().put("moveForward", field);
                    }
                }
            }

            Arrays.stream(ReflectionUtils.getAllFields( map.get("Minecraft").getClazz() )).filter(field -> field.getType().equals(map.get("FontRenderer").getClazz())).forEach(field -> {
                for (Field field1 : field.getType().getFields()) {
                    if(field1.getType().equals(boolean.class)) continue;
                    if(field1.getType().equals(int.class)) continue;
                    if(field1.getType().equals(float.class)) continue;
                    if(field1.getType().equals(byte[].class)) continue;
                    if(field1.getType().equals(byte.class)) continue;
                    if(field1.getType().equals(int[].class)) continue;
                    if(field1.getType().equals(float[].class)) continue;
                    try {
                        Object value = field.get(Mapper.getMinecraft());
                        String detailedString = ClassUtils.getDetailStringify(value);
                        if(detailedString.contains("ascii.png"))
                        {
                            map.get("Minecraft").getFields().put("fontRendererObj", field);
                            break;
                        }
                    } catch (Exception e) {
                        Myth.logger.logException(e);
                    }
                }
            });
            /*map.get("Minecraft").getFields().put("fontRendererObj" ,
                    Arrays.stream(ReflectionUtils.getAllFields( map.getn("Minecraft").getClazz() )).filter(field -> field.getType().equals(map.get("FontRenderer").getClazz())).findFirst().orElse(null)
            );*/
            logOut();
            //Myth.logger.log("Before add Channels: " + String.join(", ", channel.pipeline().names()));
            if(channel.pipeline().get("myth_receive") == null) {
                if(channel.pipeline().get("solauncher_receive") != null)
                    channel.pipeline().addBefore("solauncher_receive", "myth_receive", new PacketReader());
                else
                    channel.pipeline().addBefore("packet_handler", "myth_receive", new PacketReader());
            }
            if(channel.pipeline().get("myth_sent") == null)
            {
                channel.pipeline().addAfter("encoder", "myth_sent", new PacketSent());
            }
            if(channel.pipeline().get("solauncher_receive") != null)
            {
                channel.pipeline().remove("solauncher_receive");
            }
            if(channel.pipeline().get("solauncher_sent") != null)
            {
                channel.pipeline().remove("solauncher_sent");
            }
            //Myth.logger.log("After add Channels: " + String.join(", ", channel.pipeline().names()));
            Mapper.addChatMessage("§s" + Myth.VERSION + " activated.", false);
            packetThread.start();
        }catch (Exception e)
        {
            Myth.logger.logException(e);
        }

    }

    public static void logOut()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("{Mapping}\n");
        for (String string : map.keySet()) {
            MappingClazz yuh = map.get(string);
            Class<?> m = yuh.getClazz();
            if(m == null) {
                builder.append(string).append(" [\n");
                builder.append("   ").append("ClassName: ").append(string).append("\n");
                builder.append("   ").append("null").append("\n");
                builder.append("]\n");
                continue;
            }
            builder.append(string).append(" [\n");
            builder.append("   ").append("ClassName: ").append(m.getName()).append("\n");
            for (String s : map.get(string).getMethods().keySet()) {
                builder.append("   ").append(s).append(" --> ").append(yuh.getMethods().get(s)).append("\n");
            }
            for (String s : map.get(string).getFields().keySet()) {
                builder.append("   ").append(s).append(" --> ").append(yuh.getFields().get(s)).append("\n");
            }
            builder.append("]\n");
        }
        Myth.logger.log(builder.toString());
    }
}
