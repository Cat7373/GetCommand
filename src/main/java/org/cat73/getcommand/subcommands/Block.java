package org.cat73.getcommand.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.cat73.bukkitplugin.command.annotation.Command;
import org.cat73.bukkitplugin.command.command.ICommand;
import org.cat73.getcommand.status.PlayersStatus;
import org.cat73.getcommand.status.Status;

@Command(name = "Block", permission = "getcommand.block", playerOnly = true, description = "点一下方块来获取 setblock 命令", aliases = "b")
public class Block implements ICommand {
    @Override
    public boolean handle(CommandSender sender, String[] args)  {
        // 获取玩家名
        String playerName = sender.getName();
        // 设置玩家状态为等待方块
        PlayersStatus.status.put(playerName, Status.Wait_Block);

        // 发送提示
        sender.sendMessage(String.format("%s请左键点一下目标方块来获取 setblock 命令(不会真的破坏方块)", ChatColor.GREEN));

        return true;
    }
}
