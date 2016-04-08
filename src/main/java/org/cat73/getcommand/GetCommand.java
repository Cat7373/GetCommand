package org.cat73.getcommand;

import org.cat73.bukkitplugin.BukkitPlugin;
import org.cat73.bukkitplugin.command.CommandHandler;
import org.cat73.getcommand.subcommands.Clear;
import org.cat73.getcommand.subcommands.Item;
import org.cat73.getcommand.subcommands.Show;

public class GetCommand extends BukkitPlugin {
    public GetCommand() {
        // 注册命令
        final CommandHandler commandHandler = this.initCommandHandler("getcommand");
        commandHandler.registerCommand(new Item());
        commandHandler.registerCommand(new Show());
        commandHandler.registerCommand(new Clear());
        // getcommand.block:
        // getcommand.entity:
        // getcommand.savetofile:
        // getcommand.savetocommandblock:
        // 注册触发器

    }
}
