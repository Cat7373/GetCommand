package org.cat73.getcommand.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.cat73.bukkitplugin.command.annotation.Command;
import org.cat73.bukkitplugin.command.command.ICommand;
import org.cat73.getcommand.status.PlayersStatus;
import org.cat73.getcommand.status.Status;

// TODO , usage = "[any]" 带任意参数执行则生成正在看的实体的 summon 指令
@Command(name = "Entity", permission = "getcommand.entity", playerOnly = true, description = "打一下生物来获取 summon 命令", aliases = "e")
public class Entity implements ICommand {
    @Override
    public boolean handle(CommandSender sender, String[] args) {
        // 获取玩家名
        String playerName = sender.getName();
        // 设置玩家状态为等待实体
        PlayersStatus.status.put(playerName, Status.Wait_Entity);

        // 发送提示
        sender.sendMessage(String.format("%s请打一下目标生物来获取 summon 命令(不会真的造成伤害)", ChatColor.GREEN));

        return true;
    }
}
