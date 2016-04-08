package org.cat73.getcommand.utils;

import org.cat73.bukkitplugin.utils.reflect.BukkitReflectUtil;

/**
 * 命令工具类
 *
 * @author cat73
 */
public class CommandUtil {
    /**
     * 将多个参数格式化成一条 give 指令
     * @param playerName 玩家名
     * @param itemName 物品名
     * @param damage 物品损害值
     * @param NBTString 附加 NBT 标签
     * @return 格式化的 give 指令
     */
    public static String getGiveCommand(String playerName, String itemName, int damage, String NBTString) {
        return String.format("/give %s %s 1 %d %s", playerName, itemName, damage, NBTString);
    }
    
    /**
     * 将 NBT 标签内的指定列表转为 JSON
     * @param NBTTagCompound NBT 标签
     * @param tagName 子标签名, 如为 null 或空则直接转换不取子标签
     * @return NBT 标签内的 tag 转为 JSON 后的结果
     * @throws Exception
     */
    public static String NBTTagCompoundToJson(Object NBTTagCompound, String tagName) throws Exception {
        if(tagName != null && !tagName.isEmpty()) {
            NBTTagCompound = BukkitReflectUtil.invokeMethod(NBTTagCompound, "getCompound", tagName);
        }
        return NBTTagCompound.toString();
    }
}
