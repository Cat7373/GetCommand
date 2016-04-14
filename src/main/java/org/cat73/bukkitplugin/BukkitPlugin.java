package org.cat73.bukkitplugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.plugin.java.JavaPlugin;
import org.cat73.bukkitplugin.utils.PluginLog;

/**
 * 插件主类
 *
 * @author Cat73
 */
public class BukkitPlugin extends JavaPlugin {
    /** 所有模块 */
    protected final List<IModule> modules = new ArrayList<>();

    @Override
    public void onEnable() {
        // 初始化 PluginLog
        PluginLog.setLogger(this.getLogger());

        // 保存默认配置
        try {
            this.saveDefaultConfig();
        } catch (final IllegalArgumentException e) {
            // 项目没有配置文件
        }

        // 启动所有模块
        for (final IModule manager : this.modules) {
            try {
                manager.onEnable(this);
            } catch (final Exception e) {
                Log.error(String.format("启动模块 %s 时出现了一个未处理的错误", manager.getName()));
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDisable() {
        // 停用所有模块
        for (final IModule manager : this.modules) {
            try {
                manager.onDisable(this);
            } catch (final Exception e) {
                Log.error(String.format("关闭模块 %s 时出现了一个未处理的错误", manager.getName()));
                e.printStackTrace();
            }
        }
    }
}
