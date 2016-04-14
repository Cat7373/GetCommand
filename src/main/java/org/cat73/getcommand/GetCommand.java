package org.cat73.getcommand;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.plugin.PluginManager;
import org.cat73.bukkitplugin.BukkitPlugin;
import org.cat73.bukkitplugin.command.commandhandler.SubCommandHandler;
import org.cat73.bukkitplugin.utils.i18n.Locale;
import org.cat73.bukkitplugin.utils.reflect.CraftBukkitReflectUtil;
import org.cat73.getcommand.listeners.EntityListener;
import org.cat73.getcommand.listeners.PlayerListener;
import org.cat73.getcommand.subcommands.Block;
import org.cat73.getcommand.subcommands.Cancel;
import org.cat73.getcommand.subcommands.Entity;
import org.cat73.getcommand.subcommands.Item;
import org.cat73.getcommand.subcommands.Save;
import org.cat73.getcommand.utils.GetCommandUtil;
import org.cat73.getcommand.utils.I18nUtil;

public class GetCommand extends BukkitPlugin {
    @Override
    public void onLoad() {
        // 调用父类的 onLoad
        super.onLoad();

        // 读取配置
        final ConfigurationSection config = this.getConfig();

        // 初始化 Units
        // 初始化 i18n
        this.i18n.setLocale(this.getLocale(config));
        I18nUtil.setI18n(this.i18n);

        // 添加模块
        // 添加命令模块
        final SubCommandHandler commandHandler = new SubCommandHandler(this, "getcommand");
        this.modules.add(commandHandler);

        // 注册命令
        commandHandler.registerCommand(new Item());
        commandHandler.registerCommand(new Entity());
        commandHandler.registerCommand(new Block());
        commandHandler.registerCommand(new Save());
        commandHandler.registerCommand(new Cancel());
    }

    @Override
    public void onEnable() {
        // 准备 GetCommandUtil
        if (!GetCommandUtil.init(CraftBukkitReflectUtil.BUKKIT_VERSION)) {
            Log.error("不支持的服务端版本，插件取消启动。");
            return;
        }

        // 调用父类的 onEnable
        super.onEnable();

        // 注册触发器
        final PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new EntityListener(), this);
        pluginManager.registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onReload() {
        // 调用父类的 onReload
        super.onReload();

        // 重新加载 i18n
        this.i18n.setLocale(this.getLocale(this.getConfig()));
    }

    /**
     * 设置 i18n 的 MapPath
     *
     * @param config 配置文件
     */
    private Locale getLocale(final ConfigurationSection config) {
        final String lang = config.getString("lang");
        final String path = String.format("lang_%s", lang);

        try {
            return new Locale(config, path);
        } catch (final ClassCastException e) {
            this.logger.error("Failed to load the i18n, %s is not a Map", path);
            return new Locale();
        }
    }
}
