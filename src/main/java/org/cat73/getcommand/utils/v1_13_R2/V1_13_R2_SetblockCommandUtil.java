package org.cat73.getcommand.utils.v1_13_R2;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.cat73.bukkitplugin.utils.reflect.CraftBukkitReflectUtil;
import org.cat73.bukkitplugin.utils.reflect.ReflectUtil;
import org.cat73.getcommand.utils.CommandUtil;
import org.cat73.getcommand.utils.ISetblockCommandUtil;
import org.cat73.getcommand.utils.NBTTagCompoundToJsonUtil;

// TODO 不支持获取 block_name[state] 的 state 部分
public class V1_13_R2_SetblockCommandUtil implements ISetblockCommandUtil {
    @Override
    public String getBlockSetBlockAtCommand(Block block) throws Exception {
        // 获取方块名
        String TileName = this.getNMSName(block);
        // 获取附加数据标签
        String dataTag = this.getDataTag(block);
        // 拼凑 setblock 命令并返回结果
        return CommandUtil.getSetblockCommand13("~", "~1", "~", TileName, "replace", dataTag);
    }

    /**
     * 获取 Block 的 MinecraftKey
     *
     * @param block 目标方块
     * @return 目标方块的 MinecraftKey
     * @throws Exception
     */
    private String getNMSName(Block block) throws Exception {
        // IRegistry<Block> REGISTRY = IRegistry.BLOCK;
        Class<?> IRegistryClass = CraftBukkitReflectUtil.minecraftServerClass("IRegistry");
        Object REGISTRY = ReflectUtil.getFieldValue(IRegistryClass, null, "BLOCK");

        // Block NMSBlock = block.getNMSBlock();
        Object NMSBlock = ReflectUtil.invokeMethod(block, "getNMSBlock");

        // MinecraftKey minecraftKey = REGISTRY.getKey(NMSBlock);
        Object minecraftKey = ReflectUtil.invokeMethodLimitArgTypes(REGISTRY.getClass(), REGISTRY, "getKey", new Object[] { NMSBlock }, new Class<?>[] { Object.class });

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

        // WorldServer worldServer = world.world;
        Object worldServer = ReflectUtil.getFieldValue(world, "world");

        // BlockPosition pos = block.position
        Object pos = ReflectUtil.getFieldValue(block, "position");

        // TileEntity tileEntity = worldServer.getTileEntity(pos);
        Object tileEntity = ReflectUtil.invokeMethod(worldServer, "getTileEntity", pos);

        System.out.println(worldServer);
        System.out.println(pos);
        System.out.println(tileEntity);

        // 如果没有附加数据标签则直接返回
        if (tileEntity == null) {
            return "";
        }

        // NBTTagCompound NBTTagCompound = new NBTTagCompound();
        Class<?> NBTTagCompoundClass = CraftBukkitReflectUtil.minecraftServerClass("NBTTagCompound");
        Object NBTTagCompound = ReflectUtil.invokeConstructor(NBTTagCompoundClass);

        // tileEntity.save(NBTTagCompound);
        ReflectUtil.invokeMethod(tileEntity, "save", NBTTagCompound);

        // 将 NBTTagCompound 序列化成 YAML 并返回
        return NBTTagCompoundToJsonUtil.NBTTagCompoundToYaml(NBTTagCompound, null);
    }
}
