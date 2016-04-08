package org.cat73.getcommand.subcommands;

import org.bukkit.command.CommandSender;
import org.cat73.bukkitplugin.command.CommandHandler;
import org.cat73.bukkitplugin.command.ISubCommand;
import org.cat73.bukkitplugin.command.SubCommandInfo;
import org.cat73.getcommand.status.PlayersStatus;
import org.cat73.getcommand.status.Status;

@SubCommandInfo(name = "Clear", permission = "getcommand.clear", description = "取消当前操作并清空已获取到的命令", aliases = "c")
public class Clear implements ISubCommand {

    @Override
    public boolean handle(final CommandSender sender, final String[] args) throws Exception {
        final String playerName = sender.getName();
        PlayersStatus.commands.put(playerName, "");
        PlayersStatus.status.put(playerName, Status.Finish);
        return true;
    }

    @Override
    public void setCommandHandler(final CommandHandler commandHandler) {}
}
