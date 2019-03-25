package org.cat73.getcommand.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.cat73.bukkitplugin.command.command.CommandInfo;
import org.cat73.bukkitplugin.command.command.ICommand;
import org.cat73.getcommand.status.PlayersStatus;
import org.cat73.getcommand.status.Status;
import org.cat73.getcommand.utils.GetCommandUtil;

@CommandInfo(name = "Item", permission = "getcommand.item", playerOnly = true, description = "获取手上物品的 Give 命令", aliases = "i")
public class Item implements ICommand {
    private final Server server = Bukkit.getServer();

    @Override
    public boolean handle(CommandSender sender, String[] args) throws Exception {
        // 获取玩家名
        String playerName = sender.getName();
        // 获取玩家对象
        Player player = this.server.getPlayer(playerName);
        // 获取玩家手上物品的 give 命令
        String command = GetCommandUtil.getPlayerHandItemGiveCommand(player);
        // 根据接口定义 返回 null 视为手上没有物品
        if (command == null) {
            sender.sendMessage(String.format("%s必须手持一个物品才能执行这个命令", ChatColor.RED));
        } else {
            // 设置状态
            PlayersStatus.commands.put(playerName, command);
            PlayersStatus.status.put(playerName, Status.Finish);

            // 发送提示
            sender.sendMessage(String.format("%s获取 give 命令成功，请用 save 来保存命令", ChatColor.GREEN));
        }
        return true;
    }
}
