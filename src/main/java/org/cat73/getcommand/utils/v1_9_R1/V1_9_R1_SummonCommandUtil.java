package org.cat73.getcommand.utils.v1_9_R1;

import org.bukkit.entity.Entity;
import org.cat73.bukkitplugin.utils.reflect.CraftBukkitReflectUtil;
import org.cat73.bukkitplugin.utils.reflect.ReflectUtil;
import org.cat73.getcommand.utils.CommandUtil;
import org.cat73.getcommand.utils.ISummonCommandUtil;
import org.cat73.getcommand.utils.NBTTagCompoundToJsonUtil;

public class V1_9_R1_SummonCommandUtil implements ISummonCommandUtil {
    @Override
    public String getEntitySummonCommand(final Entity entity) throws Exception {
        // 获取实体名
        final String entityName = entity.getType().getName();
        // 获取实体附加数据标签
        final String dataTag = this.getDataTag(entity);
        // 拼凑 summon 命令
        final String command = CommandUtil.getSummonCommand(entityName, "~", "~1", "~", dataTag);

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
    private String getDataTag(final Entity entity) throws Exception {
        // 获取 entity 的 NBTTagCompound
        // NBTTagCompound NBTTagCompound = new NBTTagCompound();
        final Class<?> NBTTagCompoundClass = CraftBukkitReflectUtil.getMinecraftServerClass("NBTTagCompound");
        final Object NBTTagCompound = ReflectUtil.invokeConstructor(NBTTagCompoundClass);

        // entity.entity.b(NBTTagCompound);
        final Object handle = ReflectUtil.getFieldValue(entity, "entity");
        ReflectUtil.invokeMethod(handle, "b", NBTTagCompound);

        // 将 NBTTagCompound 序列化成 JSON 并返回
        return NBTTagCompoundToJsonUtil.NBTTagCompoundToJson(NBTTagCompound, null);
    }
}
