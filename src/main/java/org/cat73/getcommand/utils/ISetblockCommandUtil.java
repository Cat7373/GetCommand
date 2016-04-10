package org.cat73.getcommand.utils;

import org.bukkit.block.Block;

/**
 * 通过方块来获取 setblock 命令的接口
 *
 * @author cat73
 */
public interface ISetblockCommandUtil {
    /**
     * 通过方块来获取 setblock 命令
     * 
     * @param block 目标方块
     * @return 对应的 setblock 命令
     * @throws Exception
     */
    String getBlockSetBlockAtCommand(Block block) throws Exception;
}
