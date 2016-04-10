package org.cat73.bukkitplugin.command;

import java.util.HashMap;

/**
 * 管理名称与命令表转换的类, 内部会自动将 key 转换为小写, 因此本类是不区分大小写的<br>
 * 使用时需保证 key 不能为 null
 *
 * @author cat73
 */
class CommandHashMap extends HashMap<String, ICommand> {
    private static final long serialVersionUID = 497789192032897236L;

    @Override
    public ICommand put(final String key, final ICommand value) {
        return super.put(key.toLowerCase(), value);
    }

    @Override
    public ICommand get(final Object key) {
        return super.get(((String) key).toLowerCase());
    }

    @Override
    public boolean containsKey(final Object key) {
        return super.containsKey(((String) key).toLowerCase());
    }
}
