package org.cat73.getcommand.utils;

import org.bukkit.entity.Entity;

/**
 * 通过实体获取 summon 命令的接口
 *
 * @author cat73
 */
public interface ISummonCommandUtil {
    /**
     * 通过实体获取 summon 命令
     * 
     * @param entity 目标实体
     * @return 对应的 summon 命令
     * @throws Exception
     */
    String getEntitySummonCommand(Entity entity) throws Exception;
}
