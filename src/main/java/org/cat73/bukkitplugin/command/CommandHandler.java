package org.cat73.bukkitplugin.command;

import java.util.Collection;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.java.JavaPlugin;
import org.cat73.bukkitplugin.IModule;
import org.cat73.bukkitplugin.command.subcommands.Help;
import org.cat73.bukkitplugin.utils.PluginLog;

/**
 * 命令的执行器
 *
 * @author cat73
 */
public class CommandHandler implements CommandExecutor, IModule {

    /**
     * 管理名称与命令表转换的类, 内部会自动将 key 转换为小写, 因此本类是不区分大小写的<br>
     * 使用时需保证 key 不能为 null
     *
     * @author cat73
     */
    private class SubCommandHashMap extends HashMap<String, ISubCommand> {
        private static final long serialVersionUID = 497789192032897236L;

        @Override
        public ISubCommand put(final String key, final ISubCommand value) {
            return super.put(key.toLowerCase(), value);
        }

        @Override
        public ISubCommand get(final Object key) {
            return super.get(((String) key).toLowerCase());
        }

        @Override
        public boolean containsKey(final Object key) {
            return super.containsKey(((String) key).toLowerCase());
        }
    }

    /** 基础命令名 */
    public final String baseCommand;
    // TODO 私有化
    /** 帮助子命令的实例 */
    public final Help help;
    /** 存储的子命令列表 */
    private final HashMap<String, ISubCommand> commandList = new SubCommandHashMap();
    /** 子命令的简写缓存 */
    private final HashMap<String, ISubCommand> aliaseCache = new SubCommandHashMap();

    /**
     * 获取一个子命令的信息
     *
     * @param command 子命令的执行器
     * @return 该子命令的信息
     */
    public static SubCommandInfo getCommandInfo(final ISubCommand command) {
        return command.getClass().getAnnotation(SubCommandInfo.class);
    }

    /**
     * 判断命令执行者有没有一个子命令的执行权限
     *
     * @param command 子命令的执行器
     * @param sender 执行者
     * @return 该执行者有无权限执行这条子命令
     */
    public static boolean hasPermission(final ISubCommand command, final Permissible sender) {
        // 获取子命令的信息
        final SubCommandInfo info = CommandHandler.getCommandInfo(command);

        // 判断有无权限执行这个子命令
        if (info.permission().isEmpty()) {
            return true;
        } else {
            if (sender.hasPermission(info.permission())) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 构造命令模块的实例
     *
     * @param baseCommand 主命令名
     */
    public CommandHandler(final String baseCommand) {
        this.baseCommand = baseCommand;
        this.help = new Help();
        this.registerCommand(this.help);
    }

    /**
     * 注册一个子命令
     *
     * @param command 子命令的执行器
     */
    public void registerCommand(final ISubCommand command) {
        // 设置命令模块
        command.setCommandHandler(this);

        // 获取子命令的信息
        final SubCommandInfo info = CommandHandler.getCommandInfo(command);
        final String name = info.name();

        // 检查是否存在命令覆盖的情况
        if (this.commandList.containsKey(name)) {
            PluginLog.warn("%s 的命令管理器已存在的子命令 %s 被覆盖，建议检查代码", this.baseCommand, name);
        }
        // 加入命令列表
        this.commandList.put(name, command);

        // 保存所有简写
        for (final String aliase : info.aliases()) {
            // 检查是否存在简写覆盖的情况
            if (this.aliaseCache.containsKey(aliase)) {
                final SubCommandInfo info2 = CommandHandler.getCommandInfo(this.aliaseCache.get(aliase));
                PluginLog.warn("%s 的命令管理器的子命令 %s 的简写 %s 被 %s 覆盖，建议检查代码", this.baseCommand, info2.name(), this.aliaseCache, name);
            }
            // 加入简写列表
            this.aliaseCache.put(aliase, command);
        }
    }

    /**
     * 获取子命令列表
     *
     * @return 子命令列表
     */
    public Collection<ISubCommand> getCommands() {
        return this.commandList.values();
    }

    /**
     * 根据名称或简写获取一个子命令的执行器
     *
     * @param nameOrAliase 子命令的名称或者简写
     * @return 子命令的执行器, 如果未找到则返回 null
     */
    public ISubCommand getCommandByNameOrAliase(final String nameOrAliase) {
        final ISubCommand command = this.commandList.get(nameOrAliase);
        return command != null ? command : this.aliaseCache.get(nameOrAliase);
    }

    @Override
    public void onEnable(final JavaPlugin javaPlugin) {
        javaPlugin.getCommand(this.baseCommand).setExecutor(this);
    }

    @Override
    public void onDisable(final JavaPlugin javaPlugin) {}

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String commandLabel, String[] args) {
        if (command.getName().equals(this.baseCommand)) {
            // 如果没有参数则执行帮助
            if (args == null || args.length < 1) {
                args = new String[] { "help" };
            }

            // 获取目标子命令的执行器
            ISubCommand commandExecer = this.getCommandByNameOrAliase(args[0]);

            // 获取失败则执行帮助
            if (commandExecer == null) {
                commandExecer = this.getCommandByNameOrAliase("help");
            }

            // 判断有无权限执行这个子命令
            final SubCommandInfo info = CommandHandler.getCommandInfo(commandExecer);
            if (!CommandHandler.hasPermission(commandExecer, sender)) {
                sender.sendMessage(String.format("%s%s你需要 %s 权限才能执行 %s 命令.", ChatColor.RED, ChatColor.BOLD, info.permission(), info.name()));
                return true;
            }

            // 判断 playerOnly
            if (info.playerOnly() && !(sender instanceof Player)) {
                sender.sendMessage(String.format("%s%s这个命令仅限玩家执行.", ChatColor.RED, ChatColor.BOLD));
                return true;
            }

            // 修剪参数 (删除子命令名)
            final String[] tmp = new String[args.length - 1];
            for (int i = 1; i < args.length; i++) {
                tmp[i - 1] = args[i];
            }

            try {
                // 执行子命令
                if (!commandExecer.handle(sender, tmp)) {
                    // 如果返回 false 则打印该子命令的帮助
                    this.help.sendCommandHelp(sender, commandExecer);
                }
            } catch (final Exception e) {
                // 如果出现任何未捕获的异常则打印提示
                sender.sendMessage(String.format("%s%s执行命令的过程中出现了一个未处理的错误.", ChatColor.RED, ChatColor.BOLD));
                e.printStackTrace();
            }

            return true;
        } else {
            return false;
        }
    }
}
