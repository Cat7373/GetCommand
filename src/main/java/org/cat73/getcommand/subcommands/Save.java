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
        final String playerName = sender.getName();
        final String command = PlayersStatus.commands.get(playerName);
        if (command != null && !command.isEmpty()) {
            if (args.length == 0) {
                args = new String[] { "chat" };
            }

            // 1 chat, 2 file, 3 console, 4 command_block
            int saveType = 0;
            // 判断是否有权执行 准备保存类型 过滤无效参数
            switch (args[0].toLowerCase()) {
                case "chat":
                    if (this.hasPermission(sender, "chat")) {
                        saveType = 1;
                        break;
                    } else {
                        return true;
                    }
                case "f":
                case "file":
                    if (this.hasPermission(sender, "file")) {
                        saveType = 2;
                        break;
                    } else {
                        return true;
                    }
                case "c":
                case "console":
                    if (this.hasPermission(sender, "console")) {
                        saveType = 3;
                        break;
                    } else {
                        return true;
                    }
                case "cb":
                case "command_block":
                    if (this.hasPermission(sender, "command_block")) {
                        saveType = 4;
                        break;
                    } else {
                        return true;
                    }
                default:
                    return false;
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
            }

        } else {
            sender.sendMessage(String.format("%s当前没有已获取到的命令", ChatColor.RED));
        }
        return true;
    }

    private boolean hasPermission(final CommandSender sender, String permission) {
        permission = String.format("getcommand.save.%s", permission);
        if (sender.hasPermission(permission)) {
            return true;
        } else {
            sender.sendMessage(String.format("%s你需要 %s 权限才能执行这个命令", ChatColor.RED, permission));
            return false;
        }
    }
}
