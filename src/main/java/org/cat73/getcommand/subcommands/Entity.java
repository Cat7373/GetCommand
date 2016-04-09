package org.cat73.getcommand.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.cat73.bukkitplugin.command.CommandHandler;
import org.cat73.bukkitplugin.command.ISubCommand;
import org.cat73.bukkitplugin.command.SubCommandInfo;
import org.cat73.getcommand.status.PlayersStatus;
import org.cat73.getcommand.status.Status;

// TODO , usage = "[any]" 带任意参数执行则生成正在看的实体的 summon 指令
@SubCommandInfo(name = "Entity", permission = "getcommand.entity", description = "打一下生物来获取 summon 命令", aliases = "e")
public class Entity implements ISubCommand {

    @Override
    public boolean handle(final CommandSender sender, final String[] args) throws Exception {
        final String playerName = sender.getName();
        PlayersStatus.status.put(playerName, Status.Wait_Entity);

        sender.sendMessage(String.format("%s请打一下目标生物来获取 summon 命令(不会真的造成伤害)", ChatColor.GREEN));

        return true;
    }

    @Override
    public void setCommandHandler(final CommandHandler commandHandler) {}
}
