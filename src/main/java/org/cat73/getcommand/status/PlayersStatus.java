package org.cat73.getcommand.status;

import java.util.HashMap;
import java.util.Map;

public class PlayersStatus {
    // TODO 属性私有化
    // TODO 玩家进入服务器自动创建PlayerStatus 退出自动清理
    /** 玩家获取到的命令 */
    public static final Map<String, String> commands = new HashMap<>();
    /** 玩家的当前状态 */
    public static final Map<String, Status> status = new HashMap<>();
}
