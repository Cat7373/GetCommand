package org.cat73.getcommand.utils.v1_9_R1;

import org.bukkit.entity.Entity;
import org.cat73.bukkitplugin.utils.reflect.CraftBukkitReflectUtil;
import org.cat73.bukkitplugin.utils.reflect.ReflectUtil;
import org.cat73.getcommand.utils.CommandUtil;
import org.cat73.getcommand.utils.ISummonCommandUtil;

public class V1_9_R1_SummonCommandUtil implements ISummonCommandUtil {
    @Override
    public String getEntitySummonCommand(final Entity entity) throws Exception {
        // 获取实体名
        final String entityName = entity.getType().getName();
        // 获取实体附加数据标签
        final String dataTag = V1_9_R1_SummonCommandUtil.getDataTag(entity);
        // 拼凑 summon 命令
        final String command = CommandUtil.getSummonCommand(entityName, "~", "~", "~", dataTag);

        // 返回结果
        return command;
    }

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
        return NBTTagCompoundToJsonUtil.NBTTagCompoundToJson(NBTTagCompound, null);
    }
}
