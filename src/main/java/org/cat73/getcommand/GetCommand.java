package org.cat73.getcommand;

import org.cat73.bukkitplugin.BukkitPlugin;
import org.cat73.bukkitplugin.command.CommandHandler;
import org.cat73.getcommand.listeners.EntityListener;
import org.cat73.getcommand.subcommands.Clear;
import org.cat73.getcommand.subcommands.Entity;
import org.cat73.getcommand.subcommands.Item;
import org.cat73.getcommand.subcommands.Show;

public class GetCommand extends BukkitPlugin {
    public GetCommand() {
        // 注册命令
        final CommandHandler commandHandler = this.initCommandHandler("getcommand");
        commandHandler.registerCommand(new Item());
        commandHandler.registerCommand(new Entity());
        // Block:
        commandHandler.registerCommand(new Show()); // TODO Remove
        // Save [chat | file | console | defaule: command_block]
        commandHandler.registerCommand(new Clear());
    }

    @Override
    public void onEnable() {
        super.onEnable();

        // 注册触发器
        this.getServer().getPluginManager().registerEvents(new EntityListener(), this);
    }
}
