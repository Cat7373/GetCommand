package org.cat73.getcommand.utils.v1_9_R1;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.cat73.bukkitplugin.utils.reflect.CraftBukkitReflectUtil;
import org.cat73.bukkitplugin.utils.reflect.ReflectUtil;
import org.cat73.getcommand.utils.CommandUtil;
import org.cat73.getcommand.utils.IGiveCommandUtil;
import org.cat73.getcommand.utils.NBTTagCompoundToJsonUtil;

public class V1_9_R1_GiveCommandUtil implements IGiveCommandUtil {
    @Override
    public String getPlayerHandItemGiveCommand(final Player player) throws Exception {
        // 获取玩家手上的物品
        final ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() != Material.AIR) {
            // 获取玩家名
            final String playerName = player.getName();
            // 获取物品名
            final String itemName = this.getNMSName(item);
            // 获取物品的附加数据值
            final int data = item.getDurability();
            // 获取物品的附加数据标签
            final String dataTag = this.getDataTag(item);

            // 拼凑 give 命令
            final String command = CommandUtil.getGiveCommand(playerName, itemName, 1, data, dataTag);

            // 返回结果
            return command;
        }
        return null;
    }

    /**
     * 获取 Block 的 MinecraftKey
     *
     * @param block 目标方块
     * @return 目标方块的 MinecraftKey
     * @throws Exception
     */
    private String getNMSName(final ItemStack item) throws Exception {
        // RegistryMaterials<MinecraftKey, Item> REGISTRY = Item.REGISTRY;
        final Class<?> ItemClass = CraftBukkitReflectUtil.getMinecraftServerClass("Item");
        final Object REGISTRY = ReflectUtil.getFieldValue(ItemClass, null, "REGISTRY");

        // ItemStack NMSItemStack = item.handle;
        final Object NMSItemStack = ReflectUtil.getFieldValue(item, "handle");

        // Item NMSItem = NMSItemStack.getItem();
        final Object NMSItem = ReflectUtil.invokeMethod(NMSItemStack, "getItem");

        // MinecraftKey minecraftKey = REGISTRY.b(NMSItem);
        final Object minecraftKey = ReflectUtil.invokeMethodLimitArgsTypes(REGISTRY.getClass(), REGISTRY, "b", new Object[] { NMSItem }, new Class<?>[] { Object.class });

        return minecraftKey.toString();
    }

    /**
     * 获取 Item 的附加数据标签
     *
     * @param item 要被获取附加数据标签的物品
     * @return 目标物品的附加数据标签
     * @throws Exception
     */
    private String getDataTag(final ItemStack item) throws Exception {
        // 获取 item 的 NBTTagCompound
        // NBTTagCompound NBTTagCompound = new NBTTagCompound();
        final Class<?> NBTTagCompoundClass = CraftBukkitReflectUtil.getMinecraftServerClass("NBTTagCompound");
        final Object NBTTagCompound = ReflectUtil.invokeConstructor(NBTTagCompoundClass);

        // item.handle.save(NBTTagCompound)
        final Object handle = ReflectUtil.getFieldValue(item, "handle");
        ReflectUtil.invokeMethod(handle, "save", NBTTagCompound);

        // 将 NBTTagCompound 序列化成 JSON 并返回
        return NBTTagCompoundToJsonUtil.NBTTagCompoundToJson(NBTTagCompound, "tag");
    }
}
