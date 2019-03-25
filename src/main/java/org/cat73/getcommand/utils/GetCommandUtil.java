package org.cat73.getcommand.utils;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.cat73.getcommand.utils.v1_8_R3.V1_8_R3_GiveCommandUtil;
import org.cat73.getcommand.utils.v1_8_R3.V1_8_R3_SetblockCommandUtil;
import org.cat73.getcommand.utils.v1_9_R1.V1_9_R1_GiveCommandUtil;
import org.cat73.getcommand.utils.v1_9_R1.V1_9_R1_SetblockCommandUtil;
import org.cat73.getcommand.utils.v1_9_R1.V1_9_R1_SummonCommandUtil;
import org.cat73.getcommand.utils.v_1_11_R1.V1_11_R1_SummonCommandUtil;

/**
 * 通过物品 / 实体 / 方块来获取指令的工具类
 *
 * @author cat73
 */
public class GetCommandUtil {
    /** 获取 give 指令的工具类实例 */
    private static IGiveCommandUtil giveTool;
    /** 获取 summon 指令的工具类实例 */
    private static ISummonCommandUtil summonTool;
    /** 获取 setblock 指令的工具类实例 */
    private static ISetblockCommandUtil setblockTool;

    /**
     * 根据版本进行初始化
     *
     * @param version CraftBukkit 的版本
     * @return 成功返回true, 不支持的版本返回 false
     */
    public static boolean init(String version) {
        switch (version) {
            // case "v1_8_R1":
            case "v1_8_R2":
            case "v1_8_R3":
                GetCommandUtil.giveTool = new V1_8_R3_GiveCommandUtil();
                GetCommandUtil.summonTool = new V1_9_R1_SummonCommandUtil();
                GetCommandUtil.setblockTool = new V1_8_R3_SetblockCommandUtil();
                return true;
            case "v1_9_R1":
            case "v1_9_R2":
            case "v1_10_R1":
                GetCommandUtil.giveTool = new V1_9_R1_GiveCommandUtil();
                GetCommandUtil.summonTool = new V1_9_R1_SummonCommandUtil();
                GetCommandUtil.setblockTool = new V1_9_R1_SetblockCommandUtil();
                return true;
            case "v1_11_R1": // 实体炸
                GetCommandUtil.giveTool = new V1_9_R1_GiveCommandUtil();
                GetCommandUtil.summonTool = new V1_11_R1_SummonCommandUtil();
                GetCommandUtil.setblockTool = new V1_9_R1_SetblockCommandUtil();
                return true;
            default:
                return false;
        }
    }

    /**
     * 通过玩家手上拿的物品获取 give 命令
     *
     * @param player 目标玩家
     * @return 如果手上有物品，则返回对应的 give 命令，如果没有则返回 null
     * @throws Exception
     */
    public static String getPlayerHandItemGiveCommand(Player player) throws Exception {
        return GetCommandUtil.giveTool.getPlayerHandItemGiveCommand(player);
    }

    /**
     * 通过实体获取 summon 命令
     *
     * @param entity 目标实体
     * @return 对应的 summon 命令
     * @throws Exception
     */
    public static String getEntitySummonCommand(Entity entity) throws Exception {
        return GetCommandUtil.summonTool.getEntitySummonCommand(entity);
    }

    /**
     * 通过方块来获取 setblock 命令
     *
     * @param block 目标方块
     * @return 对应的 setblock 命令
     * @throws Exception
     */
    public static String getBlockSetBlockAtCommand(Block block) throws Exception {
        return GetCommandUtil.setblockTool.getBlockSetBlockAtCommand(block);
    }
}
