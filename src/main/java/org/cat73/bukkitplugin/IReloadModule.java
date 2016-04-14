package org.cat73.bukkitplugin;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * 支持重载的模块接口
 *
 * @author Cat73
 */
public interface IReloadModule extends IModule {
    /**
     * 插件重载时的触发
     *
     * @param javaPlugin 插件主类
     * @throws Exception
     */
    void onReload(JavaPlugin javaPlugin) throws Exception;
}
