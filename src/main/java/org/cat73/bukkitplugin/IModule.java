package org.cat73.bukkitplugin;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * 模块接口
 *
 * @author Cat73
 */
public interface IModule {
    /**
     * 插件启用时的触发
     *
     * @param javaPlugin 插件主类
     * @throws Exception
     */
    void onEnable(JavaPlugin javaPlugin) throws Exception;

    /**
     * 插件禁用时的触发
     *
     * @param javaPlugin 插件主类
     * @throws Exception
     */
    void onDisable(JavaPlugin javaPlugin) throws Exception;

    /**
     * 返回模块的名字
     *
     * @return 模块的名字
     */
    String getName();
}
