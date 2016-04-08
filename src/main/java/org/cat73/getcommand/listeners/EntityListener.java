package org.cat73.getcommand.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.cat73.bukkitplugin.utils.reflect.CraftBukkitReflectUtil;
import org.cat73.bukkitplugin.utils.reflect.ReflectUtil;
import org.cat73.getcommand.status.PlayersStatus;
import org.cat73.getcommand.status.Status;
import org.cat73.getcommand.utils.CommandUtil;

public class EntityListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) throws Exception {
        Entity damager = event.getDamager();
        if (damager instanceof Player) {
            Player player = (Player) damager;
            String playerName = player.getName();
            if(PlayersStatus.status.get(playerName) == Status.Wait_Entity) {
                event.setCancelled(true);
                Entity entity = event.getEntity();
                
                // 准备数据
                String entityName = entity.getType().getName();
                final String NBTString = this.getNBTString(entity);
                String command = CommandUtil.getSummonCommand(entityName, "~", "~", "~", NBTString);
                
                // 设置状态
                PlayersStatus.commands.put(playerName, command);
                PlayersStatus.status.put(playerName, Status.Finish);
            }
        }
    }

    private String getNBTString(Entity entity) throws Exception {
        // 获取 entity 的 NBTTagCompound
        // NBTTagCompound NBTTagCompound = entity.entity.e(NBTTagCompound)
        final Class<?> craftEntityClass = CraftBukkitReflectUtil.getCraftBukkitClass("entity.CraftEntity");
        final Object handle = ReflectUtil.getFieldValue(craftEntityClass, entity, "entity");
        final Class<?> NBTTagCompoundClass = CraftBukkitReflectUtil.getMinecraftServerClass("NBTTagCompound");
        final Object NBTTagCompound = ReflectUtil.invokeConstructor(NBTTagCompoundClass);
        final Class<?> minecraftEntity = CraftBukkitReflectUtil.getMinecraftServerClass("Entity");
        ReflectUtil.invokeMethod(minecraftEntity, handle, "b", NBTTagCompound);

        // 将 NBTTagCompound 序列化成 JSON 并返回
        return CommandUtil.NBTTagCompoundToJson(NBTTagCompound, null);
    }
}
