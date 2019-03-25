package org.cat73.getcommand.utils;

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
     * @param amount 数量
     * @param data 物品附加数据值
     * @param dataTag 附加数据标签
     * @return 格式化的 give 指令
     */
    public static String getGiveCommand(String playerName, String itemName, int amount, int data, String dataTag) {
        return String.format("/minecraft:give %s %s %d %d %s", playerName, itemName, amount, data, dataTag);
    }

    /**
     * 将多个参数格式化成一条 give 指令(1.13+)
     *
     * @param playerName 玩家名
     * @param itemName 物品名
     * @param amount 数量
     * @param dataTag 附加数据标签
     * @return 格式化的 give 指令
     */
    public static String getGiveCommand13(String playerName, String itemName, int amount, String dataTag) {
        return String.format("/minecraft:give %s %s%s %d", playerName, itemName, dataTag, amount);
    }

    /**
     * 将多个参数格式化成一条 summon 指令
     *
     * @param entityName 实体名
     * @param x 位置
     * @param y 位置
     * @param z 位置
     * @param dataTag 附加数据标签
     * @return 格式化的 summon 指令
     */
    public static String getSummonCommand(String entityName, String x, String y, String z, String dataTag) {
        return String.format("/minecraft:summon %s %s %s %s %s", entityName, x, y, z, dataTag);
    }

    /**
     * 将多个参数格式化成一条 setblock 指令
     *
     * @param x 位置
     * @param y 位置
     * @param z 位置
     * @param tileName 方块名
     * @param dataValue 损害值
     * @param oldBlockHandling 旧方块处理方式
     * @param dataTag 附加数据标签
     * @return 格式化的 setblock 指令
     */
    public static String getSetblockCommand(String x, String y, String z, String tileName, byte dataValue, String oldBlockHandling, String dataTag) {
        return String.format("/minecraft:setblock %s %s %s %s %d %s %s", x, y, z, tileName, dataValue, oldBlockHandling, dataTag);
    }

    /**
     * 将多个参数格式化成一条 setblock 指令(1.13+)
     *
     * @param x 位置
     * @param y 位置
     * @param z 位置
     * @param nameAndState 方块名和 State
     * @param oldBlockHandling 旧方块处理方式
     * @param dataTag 附加数据标签
     * @return 格式化的 setblock 指令
     */
    public static String getSetblockCommand13(String x, String y, String z, String nameAndState, String oldBlockHandling, String dataTag) {
        return String.format("/minecraft:setblock %s %s %s %s%s %s", x, y, z, nameAndState, dataTag, oldBlockHandling);
    }
}
