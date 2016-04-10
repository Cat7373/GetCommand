package org.cat73.getcommand.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.cat73.getcommand.status.PlayersStatus;
import org.cat73.getcommand.status.Status;
import org.cat73.getcommand.utils.CommandUtil;
import org.cat73.getcommand.utils.DataTagUtil;

public class EntityListener implements Listener {
    @EventHandler
    public void onEntityDamageByEntityEvent(final EntityDamageByEntityEvent event) throws Exception {
        final Entity damager = event.getDamager();
        if (damager instanceof Player) {
            final Player player = (Player) damager;
            final String playerName = player.getName();
            if (PlayersStatus.status.get(playerName) == Status.Wait_Entity) {
                event.setCancelled(true);
                final Entity entity = event.getEntity();

                // 准备数据
                @SuppressWarnings("deprecation")
                final String entityName = entity.getType().getName();
                final String dataTag = DataTagUtil.getDataTag(entity);
                final String command = CommandUtil.getSummonCommand(entityName, "~", "~", "~", dataTag);

                // 设置状态
                PlayersStatus.commands.put(playerName, command);
                PlayersStatus.status.put(playerName, Status.Finish);

                player.sendMessage(String.format("%s获取 summon 命令成功，请用 save 来保存命令", ChatColor.GREEN));
            }
        }
    }
}
