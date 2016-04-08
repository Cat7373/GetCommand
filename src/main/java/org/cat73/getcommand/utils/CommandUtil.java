package org.cat73.getcommand.utils;

import org.cat73.bukkitplugin.utils.reflect.ReflectUtil;

/**
 * 命令工具类
 *
 * @author cat73
 */
public class CommandUtil {
    /**
     * 将多个参数格式化成一条 give 指令
     *
     * @param playerName 玩家名
     * @param itemName 物品名
     * @param count 物品数量
     * @param damage 物品损害值
     * @param NBTString 附加 NBT 标签
     * @return 格式化的 give 指令
     */
    public static String getGiveCommand(final String playerName, final String itemName, final int count, final int damage, final String NBTString) {
        return String.format("/give %s %s %d %d %s", playerName, itemName, count, damage, NBTString);
    }
    
    /**
     * 将多个参数格式化成一条 summon 指令
     *
     * @param entityName 实体名
     * @param x 位置
     * @param y 位置
     * @param z 位置
     * @param NBTString 附加 NBT 标签
     * @return 格式化的 give 指令
     */
    public static String getSummonCommand(final String entityName, final String x, final String y, final String z, final String NBTString) {
        return String.format("/summon %s %s %s %s %s", entityName, x, y, z, NBTString);
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
