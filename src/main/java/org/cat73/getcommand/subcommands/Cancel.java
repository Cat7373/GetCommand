package org.cat73.getcommand.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.cat73.bukkitplugin.command.CommandHandler;
import org.cat73.bukkitplugin.command.ISubCommand;
import org.cat73.bukkitplugin.command.SubCommandInfo;
import org.cat73.getcommand.status.PlayersStatus;
import org.cat73.getcommand.status.Status;

@SubCommandInfo(name = "Cancel", permission = "getcommand.cancel", description = "取消当前操作", aliases = "c")
public class Cancel implements ISubCommand {

    @Override
    public boolean handle(final CommandSender sender, final String[] args) throws Exception {
        final String playerName = sender.getName();

        PlayersStatus.status.put(playerName, Status.Finish);
        sender.sendMessage(String.format("%s成功取消当前操作", ChatColor.GREEN));

        return true;
    }

    @Override
    public void setCommandHandler(final CommandHandler commandHandler) {}
}
