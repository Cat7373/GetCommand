package org.cat73.bukkitplugin.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 日志类
 *
 * @author Cat73
 */
public class PluginLogger {
    /* 日志输出流 */
    private final Logger logger;

    /**
     * 实例化日之类
     *
     * @param logger
     */
    public PluginLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * 输出日志
     *
     * @param level 日志的级别
     * @param format 要输出的信息格式
     * @param args 格式化时使用的数据列表
     */
    private void log(Level level, String format, Object... args) {
        this.logger.log(level, String.format(format, args));
    }

    /**
     * 输出信息
     *
     * @param format 要输出的信息格式
     * @param args 格式化时使用的数据列表
     */
    public void info(String format, Object... args) {
        this.log(Level.INFO, format, args);
    }

    /**
     * 输出警告
     *
     * @param format 要输出的信息格式
     * @param args 格式化时使用的数据列表
     */
    public void warn(String format, Object... args) {
        this.warning(format, args);
    }

    /**
     * 输出警告
     *
     * @param format 要输出的信息格式
     * @param args 格式化时使用的数据列表
     */
    public void warning(String format, Object... args) {
        this.log(Level.WARNING, format, args);
    }

    /**
     * 输出错误
     *
     * @param format 要输出的信息格式
     * @param args 格式化时使用的数据列表
     */
    public void error(String format, Object... args) {
        this.log(Level.SEVERE, format, args);
    }

    /**
     * 输出格式化的调试信息
     *
     * @param format 要输出的信息格式
     * @param args 格式化时使用的数据列表
     */
    public void debug(String format, Object... args) {
        this.log(Level.INFO, "[DEBUG] " + format, args);
    }

    /**
     * 输出调试信息
     *
     * @param objs 要输出的数据列表
     */
    public void debugs(Object... objs) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : objs) {
            sb.append(obj.toString()).append(", ");
        }
        String msg = sb.length() > 0 ? sb.substring(0, sb.length() - 2) : "";

        this.log(Level.INFO, "[DEBUG] " + msg);
    }
}
