package org.cat73.getcommand.utils;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.cat73.bukkitplugin.utils.reflect.CraftBukkitReflectUtil;
import org.cat73.bukkitplugin.utils.reflect.ReflectUtil;

public class DataTagUtil {
    /**
     * 获取 Entity 的附加数据标签
     *
     * @param entity 要被获取附加数据标签的实体
     * @return 目标实体的附加数据标签
     * @throws Exception
     */
    public static String getDataTag(final Entity entity) throws Exception {
        // 获取 entity 的 NBTTagCompound
        // NBTTagCompound NBTTagCompound = new NBTTagCompound();
        final Class<?> NBTTagCompoundClass = CraftBukkitReflectUtil.getMinecraftServerClass("NBTTagCompound");
        final Object NBTTagCompound = ReflectUtil.invokeConstructor(NBTTagCompoundClass);

        // entity.entity.b(NBTTagCompound);
        final Class<?> craftEntityClass = CraftBukkitReflectUtil.getCraftBukkitClass("entity.CraftEntity");
        final Object handle = ReflectUtil.getFieldValue(craftEntityClass, entity, "entity");
        final Class<?> minecraftEntity = CraftBukkitReflectUtil.getMinecraftServerClass("Entity");
        ReflectUtil.invokeMethod(minecraftEntity, handle, "b", NBTTagCompound);

        // 将 NBTTagCompound 序列化成 JSON 并返回
        return DataTagUtil.NBTTagCompoundToJson(NBTTagCompound, null);
    }

    /**
     * 获取 Item 的附加数据标签
     *
     * @param item 要被获取附加数据标签的物品
     * @return 目标物品的附加数据标签
     * @throws Exception
     */
    public static String getDataTag(final ItemStack item) throws Exception {
        // 获取 item 的 NBTTagCompound

        // NBTTagCompound NBTTagCompound = new NBTTagCompound();
        final Class<?> NBTTagCompoundClass = CraftBukkitReflectUtil.getMinecraftServerClass("NBTTagCompound");
        final Object NBTTagCompound = ReflectUtil.invokeConstructor(NBTTagCompoundClass);

        // item.handle.save(NBTTagCompound)
        final Object handle = ReflectUtil.getFieldValue(item, "handle");
        ReflectUtil.invokeMethod(handle, "save", NBTTagCompound);

        // 将 NBTTagCompound 序列化成 JSON 并返回
        return DataTagUtil.NBTTagCompoundToJson(NBTTagCompound, "tag");
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
        return DataTagUtil.NBTTagCompoundToJson(NBTTagCompound, null);
    }

    /**
     * 将 NBT 标签内的指定列表转为 JSON
     *
     * @param NBTTagCompound NBT 标签
     * @param tagName 子标签名, 如为 null 或空则直接转换不取子标签
     * @return NBT 标签内的 tag 转为 JSON 后的结果
     * @throws Exception
     */
    public static String NBTTagCompoundToJson(Object NBTTagCompound, final String tagName) throws Exception {
        if (tagName != null && !tagName.isEmpty()) {
            NBTTagCompound = ReflectUtil.invokeMethod(NBTTagCompound, "getCompound", tagName);
        }
        return NBTTagCompound.toString();
    }
}
