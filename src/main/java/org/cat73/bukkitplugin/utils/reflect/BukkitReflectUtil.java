package org.cat73.bukkitplugin.utils.reflect;

import org.bukkit.Bukkit;

/**
 * Bukkit 反射工具类
 *
 * @author Cat73
 */
public class BukkitReflectUtil extends ReflectUtil {
    /** Bukkit 包的版本 */
    public static final String BUKKIT_VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    /**
     * 根据路径获取一个 CraftBukkit 的类的 Class
     * @param path 在版本号之后的路径, 如获取 org.bukkit.craftbukkit.v1_9_R1.CraftServer 时应该传入 CraftServer
     * @return 对应类的 Class
     * @throws Exception
     */
    public static Class<?> getCraftBukkitClass(String path) throws Exception {
        // 拼凑完整 Path
        path = String.format("org.bukkit.craftbukkit.%s.%s", BUKKIT_VERSION, path);
        // 获取并返回 Class
        return Class.forName(path);
    }
    
    /**
     * 根据路径获取一个 MinecraftServer 的类的 Class
     * @param path 在版本号之后的路径, 如获取 net.minecraft.server.v1_9_R1.World 时应该传入 World
     * @return 对应类的 Class
     * @throws Exception
     */
    public static Class<?> getMinecraftServerClass(String path) throws Exception {
        // 拼凑完整 Path
        path = String.format("net.minecraft.server.%s.%s", BUKKIT_VERSION, path);
        // 获取并返回 Class
        return Class.forName(path);
    }
}
