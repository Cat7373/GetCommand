package org.cat73.getcommand.utils.v1_8_R3;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.cat73.bukkitplugin.utils.reflect.CraftBukkitReflectUtil;
import org.cat73.bukkitplugin.utils.reflect.ReflectUtil;
import org.cat73.getcommand.utils.CommandUtil;
import org.cat73.getcommand.utils.ISetblockCommandUtil;
import org.cat73.getcommand.utils.NBTTagCompoundToJsonUtil;

public class V1_8_R3_SetblockCommandUtil implements ISetblockCommandUtil {
    @Override
    public String getBlockSetBlockAtCommand(Block block) throws Exception {
        // 获取方块名
        String TileName = this.getNMSName(block);
        // 获取附加数据值
        byte dataValue = block.getData();
        // 获取附加数据标签
        String dataTag = this.getDataTag(block);
        // 拼凑 setblock 命令，并返回结果
        return CommandUtil.getSetblockCommand("~", "~1", "~", TileName, dataValue, "replace", dataTag);
    }

    /**
     * 获取 Block 的 MinecraftKey
     *
     * @param block 目标方块
     * @return 目标方块的 MinecraftKey
     * @throws Exception
     */
    private String getNMSName(Block block) throws Exception {
        // Block NMSBlock = block.getNMSBlock();
        Object NMSBlock = ReflectUtil.invokeMethod(block, "getNMSBlock");

        // RegistryBlocks<MinecraftKey, Block> REGISTRY = Block.REGISTRY;
        Class<?> BlockClass = CraftBukkitReflectUtil.minecraftServerClass("Block");
        Object REGISTRY = ReflectUtil.getFieldValue(BlockClass, null, "REGISTRY");

        // MinecraftKey minecraftKey = REGISTRY.b(NMSBlock);
        Object minecraftKey = ReflectUtil.invokeMethodLimitArgsTypes(REGISTRY.getClass(), REGISTRY, "c", new Object[] { NMSBlock }, new Class<?>[] { Object.class });

        return minecraftKey.toString();
    }

    /**
     * 获取 Block 的附加数据标签
     *
     * @param block 目标方块
     * @return 目标方块的附加数据标签
     * @throws Exception
     */
    private String getDataTag(Block block) throws Exception {
        // 方块所在的世界
        World world = block.getWorld();

        // 方块的位置
        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();

        // TileEntity tileEntity = world.getTileEntityAt(x, y, z);
        Object tileEntity = ReflectUtil.invokeMethodLimitArgsTypes(world.getClass(), world, "getTileEntityAt", new Object[] { x, y, z }, new Class<?>[] { int.class, int.class, int.class });

        // 如果没有附加数据标签则直接返回
        if (tileEntity == null) {
            return "";
        }

        // NBTTagCompound NBTTagCompound = new NBTTagCompound();
        Class<?> NBTTagCompoundClass = CraftBukkitReflectUtil.minecraftServerClass("NBTTagCompound");
        Object NBTTagCompound = ReflectUtil.invokeConstructor(NBTTagCompoundClass);

        // tileEntity.save(NBTTagCompound);
        ReflectUtil.invokeMethod(tileEntity, "b", NBTTagCompound);

        // 将 NBTTagCompound 序列化成 JSON 并返回
        return NBTTagCompoundToJsonUtil.NBTTagCompoundToJson(NBTTagCompound, null);
    }
}
