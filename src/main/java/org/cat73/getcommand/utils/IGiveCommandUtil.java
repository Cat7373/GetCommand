package org.cat73.getcommand.utils;

import org.bukkit.entity.Player;

/**
 * 通过玩家手上拿的物品获取 give 命令的接口
 *
 * @author cat73
 */
public interface IGiveCommandUtil {
    /**
     * 通过玩家手上拿的物品获取 give 命令
     *
     * @param player 目标玩家
     * @return 如果手上有物品，则返回对应的 give 命令，如果没有则返回 null
     * @throws Exception
     */
    String getPlayerHandItemGiveCommand(Player player) throws Exception;
}
