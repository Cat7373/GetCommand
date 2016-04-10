package org.cat73.getcommand.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.cat73.bukkitplugin.command.CommandHandler;
import org.cat73.bukkitplugin.command.ISubCommand;
import org.cat73.bukkitplugin.command.SubCommandInfo;
import org.cat73.getcommand.status.PlayersStatus;
import org.cat73.getcommand.status.Status;

@SubCommandInfo(name = "Save", permission = "getcommand.save", usage = "[chat | file | console | command_block]", description = "保存上一个获取到的命令", aliases = "s", help = { "chat: 将指令在聊天信息输出(默认)", "file: 将指令保存到 log 文件里, 简写: f(未实现)", "console: 将指令输出到控制台, 简写: c", "command_block: 将指令输出到空白的命令方块中, 简写: cb" })
public class Save implements ISubCommand {
    @Override
    public boolean handle(CommandSender sender, String[] args) throws Exception {
        final String playerName = sender.getName();
        final String command = PlayersStatus.commands.get(playerName);
        if (command != null && !command.isEmpty()) {
            if (args.length == 0) {
                args = new String[] { "chat" };
            }
            switch (args[0].toLowerCase()) {
                case "c":
                case "console":
                    sender = Bukkit.getConsoleSender();
                case "chat":
                    sender.sendMessage(String.format("%s%s------- 当前获取到的命令 ----------------", ChatColor.AQUA, ChatColor.BOLD));
                    sender.sendMessage(String.format("%s%s", ChatColor.GREEN, PlayersStatus.commands.get(playerName)));
                    break;
                case "cb":
                case "command_block":
                    PlayersStatus.status.put(playerName, Status.Wait_CommandBlock);
                    sender.sendMessage(String.format("%s请在创造模式打一下一个空白的命令方块来保存命令", ChatColor.GREEN));
                    break;
                case "f":
                case "file": // TODO save file
                default:
                    return false;
            }

        } else {
            sender.sendMessage(String.format("%s当前没有已获取到的命令", ChatColor.RED));
        }
        return true;
    }

    @Override
    public void setCommandHandler(final CommandHandler commandHandler) {}
}
