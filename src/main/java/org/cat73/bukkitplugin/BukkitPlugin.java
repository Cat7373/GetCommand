package org.cat73.bukkitplugin;

import java.util.ArrayList;

import org.bukkit.plugin.java.JavaPlugin;
import org.cat73.bukkitplugin.utils.PluginLog;

/**
 * 插件主类
 *
 * @author Cat73
 */
public class BukkitPlugin extends JavaPlugin {
    /** 所有模块 */
    protected final ArrayList<IModule> modules = new ArrayList<>();

    @Override
    public void onEnable() {
        // 初始化 PluginLog
        PluginLog.setLogger(this.getLogger());

        // 保存默认配置 // TODO 无 config.yml 时无法正常工作
        this.saveDefaultConfig();

        // 启动所有模块
        for (final IModule manager : this.modules) {
            manager.onEnable(this);
        }
    }

    @Override
    public void onDisable() {
        // 停用所有模块
        for (final IModule manager : this.modules) {
            manager.onDisable(this);
        }
    }
}
