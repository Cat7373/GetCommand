package org.cat73.bukkitplugin;

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
     * @throws Exception 如果重载过程中出现了任何异常
     */
    void onReload(BukkitPlugin javaPlugin) throws Exception;
}
