package org.cat73.getcommand.utils.v1_13_R1;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.cat73.bukkitplugin.utils.reflect.CraftBukkitReflectUtil;
import org.cat73.bukkitplugin.utils.reflect.ReflectUtil;
import org.cat73.getcommand.utils.CommandUtil;
import org.cat73.getcommand.utils.IGiveCommandUtil;
import org.cat73.getcommand.utils.NBTTagCompoundToJsonUtil;

public class V1_13_R1_GiveCommandUtil implements IGiveCommandUtil {
    @Override
    public String getPlayerHandItemGiveCommand(Player player) throws Exception {
        // 获取玩家手上的物品
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() != Material.AIR) {
            // 获取玩家名
            String playerName = player.getName();
            // 获取物品名
            String itemName = this.getNMSName(item);
            // 获取物品的附加数据标签
            String dataTag = this.getDataTag(item);

            // 拼凑 give 命令并返回
            return CommandUtil.getGiveCommand13(playerName, itemName, 1, dataTag);
        }
        return null;
    }

    /**
     * 获取 Item 的 MinecraftKey
     *
     * @param item 目标物品
     * @return 目标方块的 MinecraftKey
     * @throws Exception
     */
    private String getNMSName(ItemStack item) throws Exception {
        // RegistryMaterials<MinecraftKey, Item> REGISTRY = Item.REGISTRY;
        Class<?> ItemClass = CraftBukkitReflectUtil.minecraftServerClass("Item");
        Object REGISTRY = ReflectUtil.getFieldValue(ItemClass, null, "REGISTRY");

        // ItemStack NMSItemStack = item.handle;
        Object NMSItemStack = ReflectUtil.getFieldValue(item, "handle");

        // Item NMSItem = NMSItemStack.getItem();
        Object NMSItem = ReflectUtil.invokeMethod(NMSItemStack, "getItem");

        // MinecraftKey minecraftKey = REGISTRY.b(NMSItem);
        Object minecraftKey = ReflectUtil.invokeMethodLimitArgTypes(REGISTRY.getClass(), REGISTRY, "b", new Object[] { NMSItem }, new Class<?>[] { Object.class });

        return minecraftKey.toString();
    }

    /**
     * 获取 Item 的附加数据标签
     *
     * @param item 要被获取附加数据标签的物品
     * @return 目标物品的附加数据标签
     * @throws Exception
     */
    private String getDataTag(ItemStack item) throws Exception {
        // 获取 item 的 NBTTagCompound
        // NBTTagCompound NBTTagCompound = new NBTTagCompound();
        Class<?> NBTTagCompoundClass = CraftBukkitReflectUtil.minecraftServerClass("NBTTagCompound");
        Object NBTTagCompound = ReflectUtil.invokeConstructor(NBTTagCompoundClass);

        // item.handle.save(NBTTagCompound)
        Object handle = ReflectUtil.getFieldValue(item, "handle");
        ReflectUtil.invokeMethod(handle, "save", NBTTagCompound);

        // 将 NBTTagCompound 序列化成 YAML 并返回
        return NBTTagCompoundToJsonUtil.NBTTagCompoundToYaml(NBTTagCompound, "tag");
    }
}
