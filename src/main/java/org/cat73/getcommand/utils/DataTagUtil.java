package org.cat73.getcommand.utils;

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
        // NBTTagCompound NBTTagCompound = entity.entity.e(NBTTagCompound)
        final Class<?> craftEntityClass = CraftBukkitReflectUtil.getCraftBukkitClass("entity.CraftEntity");
        final Object handle = ReflectUtil.getFieldValue(craftEntityClass, entity, "entity");
        final Class<?> NBTTagCompoundClass = CraftBukkitReflectUtil.getMinecraftServerClass("NBTTagCompound");
        final Object NBTTagCompound = ReflectUtil.invokeConstructor(NBTTagCompoundClass);
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
        // NBTTagCompound NBTTagCompound = item.handle.save(NBTTagCompound)
        final Object handle = ReflectUtil.getFieldValue(item, "handle");
        final Class<?> NBTTagCompoundClass = CraftBukkitReflectUtil.getMinecraftServerClass("NBTTagCompound");
        final Object NBTTagCompound = ReflectUtil.invokeConstructor(NBTTagCompoundClass);
        ReflectUtil.invokeMethod(handle, "save", NBTTagCompound);

        // 将 NBTTagCompound 序列化成 JSON 并返回
        return DataTagUtil.NBTTagCompoundToJson(NBTTagCompound, "tag");
    }

    public static String getDataTag(final Block block) {
        // TODO getDataTag(Block)
        return "";
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
