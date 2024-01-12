package gov.babalar.module.s.motion;

import gov.babalar.Myth;
import gov.babalar.helpers.Mapper;
import gov.babalar.mapping.s.MainMapping;
import gov.babalar.module.Category;
import gov.babalar.module.Module;
import gov.babalar.utils.InventoryUtil;
import gov.babalar.utils.other.BlockInfo;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Scaffold extends Module {

    private BlockInfo blockCache, lastBlockCache;

    public static double keepYCoord;

    public Scaffold()
    {
        super("Scaffold", Category.MOTION, NativeKeyEvent.VC_G);
    }


    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void run() {
        try  {
            blockCache = grab();
            if (blockCache != null) {
                lastBlockCache = grab();
            } else {
                return;
            }
            if(blockCache == null|| lastBlockCache == null)
            {
                return;
            }
            if(Mapper.getFallDistance() == 0.0F && Mapper.getFallDistance1() == 0.0F)
                keepYCoord = Math.floor(Mapper.getY(Mapper.getBlockPos()) - 1.0);

            int slot = Scaffold.grabBlockSlot();
            if (slot == -1)
                return;

            Mapper.sendC09HeldItem(slot);
            Mapper.sendC08BlockPlace(Mapper.getCurrentItem(), lastBlockCache.pos, lastBlockCache.facing,
                    getHypixelVec3(lastBlockCache));
            blockCache = null;
        }catch (Exception e)
        {
            Myth.logger.logException(e);
        }
    }

    public static Object getHypixelVec3(BlockInfo data) {
        try {
            Object pos = data.pos;
            Object face = data.facing;
            String faceString = face.toString().toUpperCase();
            double x = Mapper.getX(pos) + 0.5;
            double y = Mapper.getY(pos) + 0.5;
            double z = Mapper.getZ(pos) + 0.5;
            if (!faceString.contains("UP") && !faceString.contains("DOWN")) {
                y += 0.5;
            } else {
                x += 0.3;
                z += 0.3;
            }
            if (faceString.contains("WEST") || faceString.contains("EAST")) {
                z += 0.15;
            }
            if (faceString.contains("SOUTH") || faceString.contains("NORTH")) {
                x += 0.15;
            }
            Class<?> vec3Class = MainMapping.map.get("Vec3").getClazz();
            Constructor<?> vec3 = vec3Class.getConstructor(double.class, double.class, double.class);
            return vec3.newInstance(x, y, z);
        }catch (Exception e)
        {
            Myth.logger.logException(e);
        }
        return null;
    }


    public static double getYLevel()
    {
        return Mapper.getY(Mapper.getBlockPos()) - 1.0 >= Scaffold.keepYCoord && Math.max(Mapper.getY(Mapper.getBlockPos()), Scaffold.keepYCoord)
                - Math.min(Mapper.getY(Mapper.getBlockPos()), Scaffold.keepYCoord) <= 3.0 ? Scaffold.keepYCoord : Mapper.getY(Mapper.getBlockPos()) - 1.0;
    }

    public static BlockInfo grab() {
        try {
            final Object belowBlockPos = Mapper.createBlockPos(Mapper.getX(Mapper.getBlockPos()), getYLevel(), Mapper.getZ(Mapper.getBlockPos()));
            if (Mapper.getBlockState(belowBlockPos).toString().contains("air")) {
                for (int x = 0; x < 4; x++) {
                    for (int z = 0; z < 4; z++) {
                        for (int i = 1; i > -3; i -= 2) {
                            final Object blockPos = Mapper.createBlockPos(Mapper.getX(belowBlockPos) + (x * i), Mapper.getY(belowBlockPos), Mapper.getZ(belowBlockPos) + (z * i));
                            if (Mapper.getBlockState(blockPos).toString().contains("air")) {
                                for (Map.Entry<String, Field> entry : MainMapping.map.get("EnumFacing").getFields().entrySet()) {
                                    Field field = entry.getValue();
                                    Object direction = field.get(null);
                                    final Object blockPosOffset = Mapper.offset(blockPos, direction);
                                    final Object blockState = Mapper.getBlockState(blockPosOffset);
                                    final Object block = Mapper.getBlock(blockState);
                                    if(Mapper.isSolid(block, blockPosOffset)) {
                                        return new BlockInfo(blockPosOffset, Mapper.getOpposite(direction));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }catch(Exception e)
        {
            Myth.logger.logException(e);
        }
        return null;
    }

    /*public static BlockInfo grab() {
        try {
            final Object belowBlockPos = Mapper.createBlockPos(Mapper.getX(Mapper.getBlockPos()), getYLevel(), Mapper.getZ(Mapper.getBlockPos()));
            if (Mapper.getBlockState(belowBlockPos).toString().contains("air")) {
                for (int x = 0; x < 4; x++) {
                    for (int z = 0; z < 4; z++) {
                        for (int i = 1; i > -3; i -= 2) {
                            final Object blockPos = Mapper.createBlockPos(Mapper.getX(belowBlockPos) + (x * i), Mapper.getY(belowBlockPos), Mapper.getZ(belowBlockPos) + (z * i));
                            if (Mapper.getBlockState(blockPos).toString().contains("air")) {
                                for (Map.Entry<String, Field> entry : MainMapping.map.get("EnumFacing").getFields().entrySet()) {
                                    Field field = entry.getValue();
                                    Object direction = field.get(null);
                                    final Object blockPosOffset = Mapper.offset(blockPos, direction);
                                    final Object blockState = Mapper.getBlockState(blockPosOffset);
                                    final Object block = Mapper.getBlock(blockState);
                                    if (Mapper.isSolid(block, blockPosOffset)) {
                                        return new BlockInfo(blockPosOffset, Mapper.getOpposite(direction));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }catch(Exception e)
        {
            Myth.logger.logException(e);
        }
        return null;
    }*/

    public static int grabBlockSlot() {
        int slot = -1;
        int highestStack = -1;
        boolean didGetHotbar = false;
        for (int i = 0; i < 9; ++i) {
            final Object itemStack = Mapper.getPlayerMainInv()[i];
            if (itemStack != null && itemStack.toString().contains("tile.") && Mapper.getAmountFromString(itemStack.toString()) > 0) {
                if (Mapper.getAmountFromString(Mapper.getPlayerMainInv()[i].toString()) > highestStack && i < 9) {
                    highestStack = Mapper.getAmountFromString(Mapper.getPlayerMainInv()[i].toString());
                    slot = i;
                    if (slot == getLastHotbarSlot()) {
                        didGetHotbar = true;
                    }
                }
                if (i > 8 && !didGetHotbar) {
                    int hotbarNum = getFreeHotbarSlot();
                    if (hotbarNum != -1 && Mapper.getAmountFromString(Mapper.getPlayerMainInv()[i].toString()) > highestStack) {
                        highestStack = Mapper.getAmountFromString(Mapper.getPlayerMainInv()[i].toString());
                        slot = i;
                    }
                }
            }
        }
        if (slot > 8) {
            int hotbarNum = getFreeHotbarSlot();
            if (hotbarNum != -1) {
                InventoryUtil.moveItem(slot, hotbarNum);
                //Client.mc.Q.a(Entity.getThePlayer().bN.e, slot, hotbarNum, 2, Entity.getThePlayer());
            } else {
                return -1;
            }
        }
        return slot;
    }


    public static int getLastHotbarSlot() {
        int hotbarNum = -1;
        for (int k = 0; k < 9; k++) {
            final Object itemStack = Mapper.getPlayerMainInv()[k];
            if (itemStack != null && itemStack.toString().contains("tile.") && Mapper.getAmountFromString(itemStack.toString()) > 1) {
                hotbarNum = k;
            }
        }
        return hotbarNum;
    }

    public static int getFreeHotbarSlot() {
        int hotbarNum = -1;
        for (int k = 0; k < 9; k++) {
            if (Mapper.getPlayerMainInv()[k] == null) {
                hotbarNum = k;
            } else {
                hotbarNum = 7;
            }
        }
        return hotbarNum;
    }

    /*public static BlockInfo grab() {
        BlockPos position = new BlockPos(0, 0, 0);
        boolean keybindJump = (boolean) Entity.getField(li.class, "d", Client.mc.aN.f);
        if (MovementUtil.isMoving() && !keybindJump) {
            for (double n2 = 0.0002, n3 = 0.0; n3 <= n2; n3 += n2 / (Math.floor(n2))) {
                final BlockPos blockPos2 = new BlockPos(
                        Entity.getPosX() - MathHelper.sin(RotationUtils.clampRotation()) * n3,
                        Entity.getPosY() - 1.0,
                        Entity.getPosZ() + MathHelper.cos(RotationUtils.clampRotation()) * n3);
                final yD blockState = WorldClient.getBlockState(blockPos2);
                if (blockState != null && WorldClient.getBlock(blockState) == cl.L) {
                    position = blockPos2;
                    break;
                }
            }
        } else {
            position = new BlockPos(new BlockPos(Entity.getPositionVector().c,
                    Entity.getPositionVector().a, Entity.getPositionVector().b))
                    .a(fI.DOWN);
        }
        if (WorldClient.getBlock(WorldClient.getBlockState(position)) != cl.L
                && !(WorldClient.getBlock(WorldClient.getBlockState(position)) instanceof ly)) {
            return null;
        }

        return ScaffoldUtils.findFacingAndBlockPosForBlock1(position);
    }*/

    /*public static BlockInfo grab() {
        final BlockPos belowBlockPos = new BlockPos(Mapper.getX(Mapper.getBlockPos()), Mapper.getY(Mapper.getBlockPos()), Mapper.getZ(Mapper.getBlockPos()));
        if (Mapper.getBlockState(belowBlockPos).getBlock() instanceof BlockAir) {
            for (int x = 0; x < 4; x++) {
                for (int z = 0; z < 4; z++) {
                    for (int i = 1; i > -3; i -= 2) {
                        final BlockPos blockPos = belowBlockPos.add(x * i, 0, z * i);
                        if (Mapper.getBlockState(blockPos).getBlock() instanceof BlockAir) {
                            for (EnumFacing direction : EnumFacing.values()) {
                                final BlockPos block = blockPos.offset(direction);
                                final Material material = Mapper.getBlockState(block).getBlock().getMaterial();
                                if (material.isSolid() && !material.isLiquid()) {
                                    return new BlockInfo(block, direction.getOpposite());
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }*/

}
