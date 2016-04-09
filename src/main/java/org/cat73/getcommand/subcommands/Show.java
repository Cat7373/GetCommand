package org.cat73.getcommand.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.cat73.bukkitplugin.command.CommandHandler;
import org.cat73.bukkitplugin.command.ISubCommand;
import org.cat73.bukkitplugin.command.SubCommandInfo;
import org.cat73.getcommand.status.PlayersStatus;

@SubCommandInfo(name = "Show", permission = "getcommand.show", description = "显示上一个获取到的命令", aliases = "s")
public class Show implements ISubCommand {
    @Override
    public boolean handle(final CommandSender sender, final String[] args) throws Exception {
        final String playerName = sender.getName();
        final String command = PlayersStatus.commands.get(playerName);
        if (command != null && !command.isEmpty()) {
            sender.sendMessage(String.format("%s%s------- 当前获取到的命令 ----------------", ChatColor.AQUA, ChatColor.BOLD));
            sender.sendMessage(String.format("%s%s", ChatColor.GREEN, PlayersStatus.commands.get(playerName)));
        } else {
            sender.sendMessage(String.format("%s当前没有已获取到的命令", ChatColor.RED));
        }
        return true;
    }

    @Override
    public void setCommandHandler(final CommandHandler commandHandler) {}
}
