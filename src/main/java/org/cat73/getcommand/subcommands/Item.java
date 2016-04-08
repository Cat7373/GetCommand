package org.cat73.getcommand.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.cat73.bukkitplugin.command.CommandHandler;
import org.cat73.bukkitplugin.command.ISubCommand;
import org.cat73.bukkitplugin.command.SubCommandInfo;
import org.cat73.bukkitplugin.utils.reflect.BukkitReflectUtil;
import org.cat73.getcommand.status.PlayersStatus;
import org.cat73.getcommand.status.Status;
import org.cat73.getcommand.utils.CommandUtil;

@SubCommandInfo(name = "Item", permission = "getcommand.item", description="获取手上物品的命令", aliases="i")
public class Item implements ISubCommand {
    private final Server server = Bukkit.getServer();

    @Override
    public boolean handle(CommandSender sender, String[] args) throws Exception {
        String playerName = sender.getName();
        Player player = server.getPlayer(playerName);
        ItemStack item = player.getInventory().getItemInMainHand();
        
        String itemName = item.getType().name().toLowerCase();
        int damage = item.getDurability();
        String NBTString = getNBTString(item);
        
        String command = CommandUtil.getGiveCommand(playerName, itemName, damage, NBTString);
        
        PlayersStatus.commands.put(playerName, command);
        PlayersStatus.status.put(playerName, Status.Finish);

        Log.debug(command);

        return true;
    }
    
    private String getNBTString(ItemStack item) throws Exception {
        // 获取 item 的 NBTTagCompound
        // NBTTagCompound NBTTagCompound = item.handle.save(NBTTagCompound)
        Object handle = BukkitReflectUtil.getFieldValue(item, "handle");
        Class<?> NBTTagCompoundClass = BukkitReflectUtil.getMinecraftServerClass("NBTTagCompound");
        Object NBTTagCompound = BukkitReflectUtil.invokeConstructor(NBTTagCompoundClass);
        BukkitReflectUtil.invokeMethod(handle, "save", NBTTagCompound);
        
        // 将 NBTTagCompound 序列化成 JSON 并返回
        return CommandUtil.NBTTagCompoundToJson(NBTTagCompound, null);
    }

    @Override
    public void setCommandHandler(CommandHandler commandHandler) {
    }
}
