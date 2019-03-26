package org.cat73.bukkitplugin.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.cat73.bukkitplugin.command.annotation.Command;
import org.cat73.bukkitplugin.command.command.ICommand;
import org.cat73.bukkitplugin.command.commandhandler.ICommandHandler;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 帮助类
 *
 * @author cat73
 */
@Command(name = "Help", usage = "[page | commandName]", description = "显示帮助信息", aliases = "h")
public class Help implements ICommand {
    // TODO 私有化
    /** 每页输出多少条帮助 */
    public int pageCommandCount = 8;
    /** 子命令所属的命令模块 */
    private final ICommandHandler commandHandler;

    public Help(ICommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    /**
     * 打印某一页帮助信息
     *
     * @param sender
     * @param page 要打印的页码
     */
    private void sendHelpPage(CommandSender sender, int page) {
        // 所有子命令
        Collection<ICommand> commands = this.commandHandler.getCommands();

        // 获取有权执行的子命令
        Set<ICommand> hasPermissionCommands = new HashSet<>();
        Iterator<ICommand> it = commands.iterator();
        while (it.hasNext()) {
            ICommand command = it.next();
            if (ICommandHandler.hasPermission(command, sender)) {
                hasPermissionCommands.add(command);
            }
        }

        // 命令的总量
        int helpCommandCount = hasPermissionCommands.size();
        // 计算总页数
        int maxPage = helpCommandCount / this.pageCommandCount + (helpCommandCount % this.pageCommandCount == 0 ? 0 : 1);
        // 防止超出总数
        page = page > maxPage || page < 1 ? 1 : page;

        sender.sendMessage(String.format("%s%s------- 命令列表 (" + page + "/" + maxPage + ") ----------------", ChatColor.AQUA, ChatColor.BOLD));

        // 获取命令列表的迭代器
        it = hasPermissionCommands.iterator();
        // 跳过前几页的内容
        for (int i = 0; i < (page - 1) * this.pageCommandCount; i++) {
            it.next();
        }
        // 输出目标页的内容
        for (int i = 0; i < this.pageCommandCount && it.hasNext(); i++) {
            ICommand commandExecer = it.next();
            Command info = ICommandHandler.getCommandInfo(commandExecer);
            sender.sendMessage(ChatColor.GREEN + String.format("%s -- %s", info.name(), info.description()));
        }
    }

    /**
     * 打印某个命令的帮助信息
     *
     * @param sender
     * @param command 命令的执行器
     */
    public void sendCommandHelp(CommandSender sender, ICommand command) {
        Command info = ICommandHandler.getCommandInfo(command);
        sender.sendMessage(String.format("%s%s------- help %s ----------------", ChatColor.AQUA, ChatColor.BOLD, info.name()));
        // 命令的用法 / 参数
        sender.sendMessage(ChatColor.GREEN + this.commandHandler.getUsage(command));
        // 命令的说明
        sender.sendMessage(ChatColor.GREEN + info.description());
        // 命令的帮助信息
        for (String line : info.help()) {
            if (!line.isEmpty()) {
                sender.sendMessage(ChatColor.GREEN + line);
            }
        }

        // 命令的简写列表
        StringBuilder aliases = new StringBuilder("aliases: ");
        int aliaseCount = 0;
        for (String aliase : info.aliases()) {
            if (!aliase.isEmpty()) {
                aliaseCount++;
                aliases.append(aliase);
                aliases.append(" ");
            }
        }
        if (aliaseCount > 0) {
            sender.sendMessage(ChatColor.GREEN + aliases.toString());
        }
    }

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        // 首先来判断是不是有参数 没参数就打印第一页
        if (args.length >= 1) {
            // 判断是不是请求某个已存在命令的帮助
            ICommand commandExecer = this.commandHandler.getCommand(args[0]);
            if (commandExecer != null && ICommandHandler.hasPermission(commandExecer, sender)) {
                // 如果是且有权限执行则打印该命令的帮助信息
                this.sendCommandHelp(sender, commandExecer);
            } else {
                // 如果不是则视为页码并打印
                int page = 1;

                // 尝试将参数转为整数
                try {
                    page = Integer.parseInt(args[0]);
                } catch (Exception e) {
                    // quiet
                }

                // 根据页码输出帮助
                this.sendHelpPage(sender, page);
            }
        } else {
            // 如果没有参数则打印第一页帮助
            this.sendHelpPage(sender, 1);
        }

        return true;
    }
}
