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
        return String.format("/give %s %s %d %d %s", playerName, itemName, amount, data, dataTag);
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
        return String.format("/summon %s %s %s %s %s", entityName, x, y, z, dataTag);
    }

    /**
     * 将多个参数格式化成一条 setblock 指令
     *
     * @param x 位置
     * @param y 位置
     * @param z 位置
     * @param TileName 方块名
     * @param dataValue 损害值
     * @param oldBlockHandling 旧方块处理方式
     * @param dataTag 附加数据标签
     * @return 格式化的 setblock 指令
     */
    public static String getSetblockCommand(String x, String y, String z, String TileName, byte dataValue, String oldBlockHandling, String dataTag) {
        return String.format("/setblock %s %s %s %s %d %s %s", x, y, z, TileName, dataValue, oldBlockHandling, dataTag);
    }
}
