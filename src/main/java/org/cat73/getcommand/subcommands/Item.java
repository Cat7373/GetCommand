package org.cat73.getcommand.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.cat73.bukkitplugin.command.command.CommandInfo;
import org.cat73.bukkitplugin.command.command.ICommand;
import org.cat73.getcommand.status.PlayersStatus;
import org.cat73.getcommand.status.Status;
import org.cat73.getcommand.utils.CommandUtil;
import org.cat73.getcommand.utils.DataTagUtil;

@CommandInfo(name = "Item", permission = "getcommand.item", playerOnly = true, description = "获取手上物品的 Give 命令", aliases = "i")
public class Item implements ICommand {
    private final Server server = Bukkit.getServer();

    @Override
    public boolean handle(final CommandSender sender, final String[] args) throws Exception {
        // 获取玩家名
        final String playerName = sender.getName();
        // 获取玩家对象
        final Player player = this.server.getPlayer(playerName);
        // 获取玩家手上的物品
        final ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() != Material.AIR) {
            // 准备数据
            // 获取物品名 TODO 找找有没有更好的获取方案
            final String itemName = item.getType().name().toLowerCase();
            // 获取物品的损害值
            final int data = item.getDurability();
            // 获取物品的 NBT 标签的 JSON 字符串
            final String dataTag = DataTagUtil.getDataTag(item);

            // 获取对应的 give 指令
            final String command = CommandUtil.getGiveCommand(playerName, itemName, 1, data, dataTag);

            // 设置状态
            PlayersStatus.commands.put(playerName, command);
            PlayersStatus.status.put(playerName, Status.Finish);

            sender.sendMessage(String.format("%s获取 give 命令成功，请用 save 来保存命令", ChatColor.GREEN));
        } else {
            sender.sendMessage(String.format("%s必须手持一个物品才能执行这个命令", ChatColor.RED));
        }

        return true;
    }
}
