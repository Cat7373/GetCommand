package org.cat73.getcommand;

import org.cat73.bukkitplugin.BukkitPlugin;
import org.cat73.bukkitplugin.command.CommandHandler;
import org.cat73.getcommand.subcommands.Item;

public class GetCommand extends BukkitPlugin {
    public GetCommand() {
        CommandHandler commandHandler = this.initCommandHandler("getcommand");
        commandHandler.registerCommand(new Item());
//      getcommand.block:
//      getcommand.entity:
//      getcommand.show:
//      getcommand.save:
//      getcommand.cancel:
    }
}
