package org.cat73.getcommand.listeners;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.cat73.getcommand.status.PlayersStatus;
import org.cat73.getcommand.status.Status;
import org.cat73.getcommand.utils.GetCommandUtil;

import java.util.Optional;

/**
 * 玩家触发监听器类
 *
 * @author cat73
 */
public class PlayerListener implements Listener {
    /**
     * 玩家打方块的触发
     *
     * @param event
     * @throws Exception
     */
    @EventHandler
    public void onEntityDamageByEntityEvent(PlayerInteractEvent event) throws Exception {
        if (event.useItemInHand() == Result.DENY) {
            return;
        }

        // 如果是左键点击
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            // 获取玩家对象
            Player player = event.getPlayer();
            // 获取玩家名
            String playerName = player.getName();
            // 获取玩家当前的状态
            Status status = PlayersStatus.status.get(playerName);
            if (status == Status.Wait_Block) {
                // 如果玩家正在等待方块
                // 取消这次操作
                event.setCancelled(true);

                // 准备数据
                Block block = event.getClickedBlock();
                String command = GetCommandUtil.getBlockSetBlockAtCommand(block);

                // 设置状态
                PlayersStatus.commands.put(playerName, command);
                PlayersStatus.status.put(playerName, Status.Finish);

                // 发送提示
                player.sendMessage(String.format("%s获取 setblock 命令成功，请用 save 来保存命令", ChatColor.GREEN));
            } else if (status == Status.Wait_CommandBlock) {
                // 如果玩家正在等待方块命令方块
                // 获取被点击的方块
                Block block = event.getClickedBlock();

                // 判断是否为命令方块
                if (Optional.ofNullable(block).map(Block::getState).map(Object::getClass).filter(CommandBlock.class::isAssignableFrom).isPresent()) {
                    // 取消这次操作
                    event.setCancelled(true);

                    // 判断玩家是否在创造模式
                    if (player.getGameMode() == GameMode.CREATIVE) {
                        // 获取命令方块的附加数据
                        CommandBlock commandBlock = (CommandBlock) block.getState();

                        // 判断目标命令方块中的命令是否为空
                        if (commandBlock.getCommand().trim().isEmpty()) {
                            // 设置目标命令方块的命令
                            commandBlock.setCommand(PlayersStatus.commands.get(playerName));
                            commandBlock.update();

                            // 设置状态
                            PlayersStatus.status.put(playerName, Status.Finish);

                            // 输出提示
                            player.sendMessage(String.format("%s保存命令到命令方块成功", ChatColor.GREEN));
                        } else {
                            player.sendMessage(String.format("%s必须选择一个没有命令的命令方块作为目标", ChatColor.RED));
                        }
                    } else {
                        player.sendMessage(String.format("%s保存命令到命令方块需要在创造模式", ChatColor.RED));
                    }
                }
            }
        }
    }
}
