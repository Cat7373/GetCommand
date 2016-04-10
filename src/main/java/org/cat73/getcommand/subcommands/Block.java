package org.cat73.getcommand.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.cat73.bukkitplugin.command.CommandHandler;
import org.cat73.bukkitplugin.command.ISubCommand;
import org.cat73.bukkitplugin.command.SubCommandInfo;
import org.cat73.getcommand.status.PlayersStatus;
import org.cat73.getcommand.status.Status;

@SubCommandInfo(name = "Block", permission = "getcommand.block", playerOnly = true, description = "点一下方块来获取 setblock 命令", aliases = "b")
public class Block implements ISubCommand {
    @Override
    public boolean handle(final CommandSender sender, final String[] args) throws Exception {
        final String playerName = sender.getName();
        PlayersStatus.status.put(playerName, Status.Wait_Block);

        sender.sendMessage(String.format("%s请左键点一下目标方块来获取 setblock 命令(不会真的破坏方块)", ChatColor.GREEN));

        return true;
    }

    @Override
    public void setCommandHandler(final CommandHandler commandHandler) {}
}
