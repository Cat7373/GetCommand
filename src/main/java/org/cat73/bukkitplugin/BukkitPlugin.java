package org.cat73.bukkitplugin;

import java.util.ArrayList;

import org.apache.commons.lang.Validate;
import org.bukkit.plugin.java.JavaPlugin;
import org.cat73.bukkitplugin.command.CommandHandler;
import org.cat73.bukkitplugin.utils.Log;

/**
 * 插件主类
 *
 * @author Cat73
 */
public class BukkitPlugin extends JavaPlugin {
    /** 所有模块 */
    private final ArrayList<IModule> modules = new ArrayList<>();

    /**
     * 初始化并返回默认的命令模块, 必须在 onEnable 之前调用.
     * 
     * @param baseCommand 主命令名
     * @return 命令模块
     */
    public CommandHandler initCommandHandler(String baseCommand) {
        Validate.notNull(baseCommand);
        baseCommand = baseCommand.trim();
        Validate.notEmpty(baseCommand);

        final CommandHandler commandHandler = new CommandHandler(baseCommand);
        this.modules.add(commandHandler);
        return commandHandler;
    }

    @Override
    public void onEnable() {
        // 初始化 Log
        Log.setLogger(this.getLogger());

        // 保存默认配置
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
