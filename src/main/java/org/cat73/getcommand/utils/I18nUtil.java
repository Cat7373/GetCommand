package org.cat73.getcommand.utils;

import org.cat73.bukkitplugin.utils.i18n.I18n;

/**
 * 多语言翻译工具
 *
 * @author cat73
 */
public class I18nUtil {
    private static I18n i18n;

    /**
     * 设置 i18n 实例
     *
     * @param i18n
     */
    public static void setI18n(final I18n i18n) {
        I18nUtil.i18n = i18n;
    }

    public static String format(final String key, final Object... args) {
        return I18nUtil.i18n.format(key, args);
    }
}
