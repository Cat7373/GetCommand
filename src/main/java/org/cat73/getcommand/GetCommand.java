package org.cat73.getcommand;

import org.bukkit.plugin.PluginManager;
import org.cat73.bukkitplugin.BukkitPlugin;
import org.cat73.bukkitplugin.command.commandhandler.SubCommandHandler;
import org.cat73.getcommand.listeners.EntityListener;
import org.cat73.getcommand.listeners.PlayerListener;
import org.cat73.getcommand.subcommands.Block;
import org.cat73.getcommand.subcommands.Cancel;
import org.cat73.getcommand.subcommands.Entity;
import org.cat73.getcommand.subcommands.Item;
import org.cat73.getcommand.subcommands.Save;

// TODO 多语言
public class GetCommand extends BukkitPlugin {
    public GetCommand() {
        // 添加命令模块
        final SubCommandHandler commandHandler = new SubCommandHandler("getcommand");
        this.modules.add(commandHandler);
        // 注册命令
        commandHandler.registerCommand(new Item());
        commandHandler.registerCommand(new Entity());
        commandHandler.registerCommand(new Block());
        commandHandler.registerCommand(new Save());
        commandHandler.registerCommand(new Cancel());
    }

    @Override
    public void onEnable() {
        super.onEnable();

        // 注册触发器
        final PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new EntityListener(), this);
        pluginManager.registerEvents(new PlayerListener(), this);
    }
}
