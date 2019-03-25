package org.cat73.getcommand.utils.v_1_11_R1;

import org.bukkit.entity.Entity;
import org.cat73.bukkitplugin.utils.reflect.CraftBukkitReflectUtil;
import org.cat73.bukkitplugin.utils.reflect.ReflectUtil;
import org.cat73.getcommand.utils.CommandUtil;
import org.cat73.getcommand.utils.ISummonCommandUtil;
import org.cat73.getcommand.utils.NBTTagCompoundToJsonUtil;

import java.util.Map;

public class V1_11_R1_SummonCommandUtil implements ISummonCommandUtil {
    @Override
    public String getEntitySummonCommand(final Entity entity) throws Exception {
        // 获取实体名
        final String entityName = this.getNMSName(entity);
        // 获取实体附加数据标签
        final String dataTag = this.getDataTag(entity);
        // 拼凑 summon 命令
        final String command = CommandUtil.getSummonCommand(entityName, "~", "~1", "~", dataTag);

        // 返回结果
        return command;
    }

    /**
     * 获取 Entity 的 MinecraftKey
     *
     * @param entity 目标实体
     * @return 目标实体的 MinecraftKey
     * @throws Exception
     */
    private String getNMSName(final Entity entity) throws Exception {
        // RegistryMaterials<MinecraftKey, Class<? extends Entity>> registry = EntityTypes.b;
        final Class<?> EntityTypesClass = CraftBukkitReflectUtil.getMinecraftServerClass("EntityTypes");
        final Class<?> RegistryMaterialsClass = CraftBukkitReflectUtil.getMinecraftServerClass("RegistryMaterials");
        final Object registry = ReflectUtil.getFieldValue(EntityTypesClass, null, "b");

        // Entity NMSEntity = entity.entity
        final Object NMSEntity = ReflectUtil.getFieldValue(entity, "entity");

        // return registry.b(handle.getClass());
        return ReflectUtil.invokeMethodLimitArgsTypes(RegistryMaterialsClass, registry, "b", new Object[] { NMSEntity.getClass() }, new Class<?>[] { Object.class }).toString();
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

        // Entity NMSEntity = entity.entity
        final Object NMSEntity = ReflectUtil.getFieldValue(entity, "entity");
        // NMSEntity.b(NBTTagCompound);
        ReflectUtil.invokeMethod(NMSEntity, "b", NBTTagCompound);

        // 将 NBTTagCompound 序列化成 JSON 并返回
        return NBTTagCompoundToJsonUtil.NBTTagCompoundToJson(NBTTagCompound, null);
    }
}