package org.cat73.getcommand.utils;

import org.cat73.bukkitplugin.utils.reflect.ReflectUtil;

public class NBTTagCompoundToJsonUtil {
    /**
     * 将 NBT 标签内的指定列表转为 JSON
     *
     * @param NBTTagCompound NBT 标签
     * @param tagName 子标签名, 如为 null 或空则直接转换不取子标签
     * @return NBT 标签内的 tag 转为 JSON 后的结果
     * @throws Exception
     */
    public static String NBTTagCompoundToJson(Object NBTTagCompound, final String tagName) throws Exception {
        if (tagName != null && !tagName.isEmpty()) {
            NBTTagCompound = ReflectUtil.invokeMethod(NBTTagCompound, "getCompound", tagName);
        }
        return NBTTagCompound.toString();
    }
}
