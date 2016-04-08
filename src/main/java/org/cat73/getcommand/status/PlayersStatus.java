package org.cat73.getcommand.status;

import java.util.HashMap;

import org.bukkit.plugin.java.JavaPlugin;
import org.cat73.bukkitplugin.IModule;

public class PlayersStatus implements IModule {
    // TODO 属性私有化
    /** 玩家获取到的命令 */
    public static final HashMap<String, String> commands = new HashMap<>();
    /** 玩家的当前状态 */
    public static final HashMap<String, Status> status = new HashMap<>();

    @Override
    public void onEnable(JavaPlugin javaPlugin) {
    }

    @Override
    public void onDisable(JavaPlugin javaPlugin) {
    }
}
