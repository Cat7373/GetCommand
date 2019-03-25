package org.cat73.getcommand.utils.v1_13_R2;

import org.bukkit.entity.Entity;
import org.cat73.bukkitplugin.utils.reflect.CraftBukkitReflectUtil;
import org.cat73.bukkitplugin.utils.reflect.ReflectUtil;
import org.cat73.getcommand.utils.CommandUtil;
import org.cat73.getcommand.utils.ISummonCommandUtil;
import org.cat73.getcommand.utils.NBTTagCompoundToJsonUtil;

public class V1_13_R2_SummonCommandUtil implements ISummonCommandUtil {
    @Override
    public String getEntitySummonCommand(Entity entity) throws Exception {
        // 获取实体名
        String entityName = this.getNMSName(entity);
        // 获取实体附加数据标签
        String dataTag = this.getDataTag(entity);
        // 拼凑 summon 命令并返回结果
        return CommandUtil.getSummonCommand(entityName, "~", "~1", "~", dataTag);
    }

    /**
     * 获取 Entity 的 MinecraftKey
     *
     * @param entity 目标实体
     * @return 目标实体的 MinecraftKey
     * @throws Exception
     */
    private String getNMSName(Entity entity) throws Exception {
        // IRegistry<EntityTypes> REGISTRY = IRegistry.ENTITY_TYPE;
        Class<?> IRegistryClass = CraftBukkitReflectUtil.minecraftServerClass("IRegistry");
        Object REGISTRY = ReflectUtil.getFieldValue(IRegistryClass, null, "ENTITY_TYPE");

        // Entity NMSEntity = entity.entity
        Object NMSEntity = ReflectUtil.getFieldValue(entity, "entity");

        // EntityTypes type = NMSEntity.P()
        Object type = ReflectUtil.invokeMethod(NMSEntity, "P");

        // return REGISTRY.getKey(handle.getClass());
        return ReflectUtil.invokeMethodLimitArgTypes(IRegistryClass, REGISTRY, "getKey", new Object[] { type }, new Class<?>[] { Object.class }).toString();
    }

    /**
     * 获取 Entity 的附加数据标签
     *
     * @param entity 要被获取附加数据标签的实体
     * @return 目标实体的附加数据标签
     * @throws Exception
     */
    private String getDataTag(Entity entity) throws Exception {
        // 获取 entity 的 NBTTagCompound
        // NBTTagCompound NBTTagCompound = new NBTTagCompound();
        Class<?> NBTTagCompoundClass = CraftBukkitReflectUtil.minecraftServerClass("NBTTagCompound");
        Object NBTTagCompound = ReflectUtil.invokeConstructor(NBTTagCompoundClass);

        // Entity NMSEntity = entity.entity
        Object NMSEntity = ReflectUtil.getFieldValue(entity, "entity");
        // NMSEntity.b(NBTTagCompound);
        ReflectUtil.invokeMethod(NMSEntity, "b", NBTTagCompound);

        // 将 NBTTagCompound 序列化成 YAML 并返回
        return NBTTagCompoundToJsonUtil.NBTTagCompoundToYaml(NBTTagCompound, null);
    }
}
