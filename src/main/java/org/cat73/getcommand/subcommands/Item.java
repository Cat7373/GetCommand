package org.cat73.getcommand.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.cat73.bukkitplugin.command.CommandHandler;
import org.cat73.bukkitplugin.command.ISubCommand;
import org.cat73.bukkitplugin.command.SubCommandInfo;
import org.cat73.bukkitplugin.utils.reflect.CraftBukkitReflectUtil;
import org.cat73.bukkitplugin.utils.reflect.ReflectUtil;
import org.cat73.getcommand.status.PlayersStatus;
import org.cat73.getcommand.status.Status;
import org.cat73.getcommand.utils.CommandUtil;

@SubCommandInfo(name = "Item", permission = "getcommand.item", description = "获取手上物品的 Give 命令", aliases = "i")
public class Item implements ISubCommand {
    private final Server server = Bukkit.getServer();

    @Override
    public boolean handle(final CommandSender sender, final String[] args) throws Exception {
        // 获取玩家名
        final String playerName = sender.getName();
        // 获取玩家对象
        final Player player = this.server.getPlayer(playerName);
        // 获取玩家手上的物品
        final ItemStack item = player.getInventory().getItemInMainHand();

        // 准备数据
        // 获取物品名 TODO 找找有没有更好的获取方案
        final String itemName = item.getType().name().toLowerCase();
        // 获取物品的损害值
        final int damage = item.getDurability();
        // 获取物品的 NBT 标签的 JSON 字符串
        final String NBTString = this.getNBTString(item);

        // 获取对应的 give 指令
        final String command = CommandUtil.getGiveCommand(playerName, itemName, 1, damage, NBTString);

        // 设置状态
        PlayersStatus.commands.put(playerName, command);
        PlayersStatus.status.put(playerName, Status.Finish);

        sender.sendMessage(String.format("%s获取 give 命令成功，请用 save 来保存命令", ChatColor.GREEN));

        return true;
    }

    private String getNBTString(final ItemStack item) throws Exception {
        // 获取 item 的 NBTTagCompound
        // NBTTagCompound NBTTagCompound = item.handle.save(NBTTagCompound)
        final Object handle = ReflectUtil.getFieldValue(item, "handle");
        final Class<?> NBTTagCompoundClass = CraftBukkitReflectUtil.getMinecraftServerClass("NBTTagCompound");
        final Object NBTTagCompound = ReflectUtil.invokeConstructor(NBTTagCompoundClass);
        ReflectUtil.invokeMethod(handle, "save", NBTTagCompound);

        // 将 NBTTagCompound 序列化成 JSON 并返回
        return CommandUtil.NBTTagCompoundToJson(NBTTagCompound, "tag");
    }

    @Override
    public void setCommandHandler(final CommandHandler commandHandler) {}
}
