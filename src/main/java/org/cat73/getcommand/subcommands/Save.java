package org.cat73.getcommand.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.cat73.bukkitplugin.command.command.CommandInfo;
import org.cat73.bukkitplugin.command.command.ICommand;
import org.cat73.getcommand.status.PlayersStatus;
import org.cat73.getcommand.status.Status;

@CommandInfo(name = "Save", permission = "getcommand.save", playerOnly = true, usage = "[chat | file | console | command_block]", description = "保存上一个获取到的命令", aliases = "s", help = { "chat: 将指令在聊天信息输出(默认)", "file: 将指令保存到 log 文件里, 简写: f(未实现)", "console: 将指令输出到控制台, 简写: c", "command_block: 将指令输出到空白的命令方块中, 简写: cb" })
public class Save implements ICommand {
    @Override
    public boolean handle(CommandSender sender, String[] args) throws Exception {
        // 获取玩家名
        final String playerName = sender.getName();
        // 获取上一个获取到的命令
        final String command = PlayersStatus.commands.get(playerName);
        // 判断命令是否为空
        if (command != null && !command.isEmpty()) {
            // 如果无参数则默认为 chat
            if (args.length == 0) {
                args = new String[] { "chat" };
            }

            // 保存类型 0 什么也不做, 1 chat, 2 file, 3 console, 4 command_block
            int saveType = 0;
            // 所需权限
            String permission;
            // 获取保存类型 获取所需权限名 过滤无效参数
            switch (args[0].toLowerCase()) {
                case "chat":
                    saveType = 1;
                    permission = "getcommand.save.chat";
                    break;
                case "f":
                case "file":
                    saveType = 2;
                    permission = "getcommand.save.file";
                    break;
                case "c":
                case "console":
                    saveType = 3;
                    permission = "getcommand.save.console";
                    break;
                case "cb":
                case "command_block":
                    saveType = 4;
                    permission = "getcommand.save.command_block";
                    break;
                default:
                    return false;
            }
            // 判断是否有权限 无权限则什么也不做, 并将保存类型设置为什么也不做
            if (!sender.hasPermission(permission)) {
                sender.sendMessage(String.format("%s你需要 %s 权限才能执行这个命令", ChatColor.RED, permission));
                saveType = 0;
            }

            // 执行具体命令
            switch (saveType) {
                case 3: // console
                    sender = Bukkit.getConsoleSender();
                case 1: // chat
                    sender.sendMessage(String.format("%s%s------- 当前获取到的命令 ----------------", ChatColor.AQUA, ChatColor.BOLD));
                    sender.sendMessage(String.format("%s%s", ChatColor.GREEN, PlayersStatus.commands.get(playerName)));
                    break;
                case 2: // file // TODO save file
                    sender.sendMessage(String.format("%s保存到文件的功能暂未实现", ChatColor.RED));
                    break;
                case 4: // command_block
                    PlayersStatus.status.put(playerName, Status.Wait_CommandBlock);
                    sender.sendMessage(String.format("%s请在创造模式打一下一个空白的命令方块来保存命令", ChatColor.GREEN));
                    break;
                case 0: // 什么也不做
                    break;
            }
        } else {
            sender.sendMessage(String.format("%s当前没有已获取到的命令", ChatColor.RED));
        }
        return true;
    }
}
