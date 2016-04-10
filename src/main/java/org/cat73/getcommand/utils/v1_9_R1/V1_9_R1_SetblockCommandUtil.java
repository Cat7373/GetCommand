package org.cat73.getcommand.utils.v1_9_R1;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.cat73.bukkitplugin.utils.reflect.CraftBukkitReflectUtil;
import org.cat73.bukkitplugin.utils.reflect.ReflectUtil;
import org.cat73.getcommand.utils.CommandUtil;
import org.cat73.getcommand.utils.ISetblockCommandUtil;

public class V1_9_R1_SetblockCommandUtil implements ISetblockCommandUtil {
    @Override
    public String getBlockSetBlockAtCommand(final Block block) throws Exception {
        // 获取方块名
        final String TileName = block.getType().name().toLowerCase();
        // 获取附加数据值
        final byte dataValue = block.getData();
        // 获取附加数据标签
        final String dataTag = V1_9_R1_SetblockCommandUtil.getDataTag(block);
        // 拼凑 setblock 命令
        final String command = CommandUtil.getSetblockCommand("~", "~", "~", TileName, dataValue, "replace", dataTag);

        // 返回结果
        return command;
    }

    /**
     * 获取 Block 的附加数据标签
     *
     * @param block 要被获取附加数据标签的方块
     * @return 目标方块的附加数据标签
     * @throws Exception
     */
    public static String getDataTag(final Block block) throws Exception {
        // 方块所在的世界
        final World world = block.getWorld();

        // 方块的位置
        final int x = block.getX();
        final int y = block.getY();
        final int z = block.getZ();

        // TileEntity tileEntity = world.getTileEntityAt(x, y, z);
        final Class<?> craftWorldClass = CraftBukkitReflectUtil.getCraftBukkitClass("CraftWorld");
        final Object tileEntity = ReflectUtil.invokeMethodLimitArgsTypes(craftWorldClass, world, "getTileEntityAt", new Integer[] { x, y, z }, new Class<?>[] { int.class, int.class, int.class });

        // 如果没有附加数据标签则直接返回
        if (tileEntity == null) {
            return "";
        }

        // NBTTagCompound NBTTagCompound = new NBTTagCompound();
        final Class<?> NBTTagCompoundClass = CraftBukkitReflectUtil.getMinecraftServerClass("NBTTagCompound");
        final Object NBTTagCompound = ReflectUtil.invokeConstructor(NBTTagCompoundClass);

        // tileEntity.save(NBTTagCompound);
        final Class<?> tileEntityClass = CraftBukkitReflectUtil.getMinecraftServerClass("TileEntity");
        ReflectUtil.invokeMethod(tileEntityClass, tileEntity, "save", NBTTagCompound);

        // 将 NBTTagCompound 序列化成 JSON 并返回
        return NBTTagCompoundToJsonUtil.NBTTagCompoundToJson(NBTTagCompound, null);
    }
}
