package org.cat73.getcommand.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.cat73.bukkitplugin.command.annotation.Command;
import org.cat73.bukkitplugin.command.command.ICommand;
import org.cat73.getcommand.status.PlayersStatus;
import org.cat73.getcommand.status.Status;

@Command(name = "Cancel", permission = "getcommand.cancel", playerOnly = true, description = "取消当前操作", aliases = "c")
public class Cancel implements ICommand {
    @Override
    public boolean handle(CommandSender sender, String[] args) {
        // 获取玩家名
        String playerName = sender.getName();
        // 设置玩家状态为完成
        PlayersStatus.status.put(playerName, Status.Finish);

        // 发送提示
        sender.sendMessage(String.format("%s成功取消当前操作", ChatColor.GREEN));

        return true;
    }
}
