package org.cat73.getcommand.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.cat73.getcommand.status.PlayersStatus;
import org.cat73.getcommand.status.Status;
import org.cat73.getcommand.utils.GetCommandUtil;

/**
 * 实体触发监听器类
 *
 * @author cat73
 */
public class EntityListener implements Listener {
    @EventHandler
    /**
     * 实体攻击实体的触发
     * @param event
     * @throws Exception
     */
    public void onEntityDamageByEntityEvent(final EntityDamageByEntityEvent event) throws Exception {
        // 获取攻击者
        final Entity damager = event.getDamager();
        // 如果攻击者为玩家
        if (damager instanceof Player) {
            // 获取玩家对象
            final Player player = (Player) damager;
            // 获取玩家名
            final String playerName = player.getName();
            // 如果玩家正在等待实体
            if (PlayersStatus.status.get(playerName) == Status.Wait_Entity) {
                // 取消这次攻击
                event.setCancelled(true);

                // 准备数据
                final Entity entity = event.getEntity();
                final String command = GetCommandUtil.getEntitySummonCommand(entity);

                // 设置状态
                PlayersStatus.commands.put(playerName, command);
                PlayersStatus.status.put(playerName, Status.Finish);

                // 发送提示
                player.sendMessage(String.format("%s获取 summon 命令成功，请用 save 来保存命令", ChatColor.GREEN));
            }
        }
    }
}
