package org.cat73.bukkitplugin;

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
     * @throws Exception 如果启用过程中出现了任何异常
     */
    void onEnable(BukkitPlugin javaPlugin) throws Exception;

    /**
     * 插件禁用时的触发
     *
     * @param javaPlugin 插件主类
     * @throws Exception 如果禁用过程中出现了任何异常
     */
    void onDisable(BukkitPlugin javaPlugin) throws Exception;

    /**
     * 返回模块的名字
     *
     * @return 模块的名字
     */
    String getName();
}
