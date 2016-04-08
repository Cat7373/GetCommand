package org.cat73.bukkitplugin.utils.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 通用反射工具类
 *
 * @author Cat73
 */
public class ReflectUtil {
    // - 属性
    /**
     * 获取一个属性的值
     * @param class_ 属性的声明 Class
     * @param object 目标对象, 如果是静态属性, 则忽略此参数
     * @param fieldName 目标属性名
     * @return 该属性的值
     * @throws Exception
     */
    public static Object getFieldValue(Class<?> class_, Object object, String fieldName) throws Exception {
        // 查找属性
        Field field = class_.getDeclaredField(fieldName);
        // 保存原访问权限
        boolean accessible = field.isAccessible();
        // 设置允许通过反射访问
        field.setAccessible(true);
        // 获取值
        Object result = field.get(object);
        // 恢复原访问权限
        field.setAccessible(accessible);
        // 返回结果
        return result;
    }
    
    /**
     * 获取一个属性的值
     * @param object 目标对象
     * @param fieldName 目标属性名
     * @return 该属性的值
     * @throws Exception
     */
    public static Object getFieldValue(Object object, String fieldName) throws Exception {
        return getFieldValue(object.getClass(), object, fieldName);
    }

    /**
     * 设置一个属性的值
     * @param class_ 属性的声明 Class
     * @param object 目标对象, 如果是静态属性, 则忽略此参数
     * @param fieldName 目标属性名
     * @param value 目标值
     * @throws Exception
     */
    public static void setFieldValue(Class<?> class_, Object object, String fieldName, Object value) throws Exception {
        // 查找属性
        Field field = class_.getDeclaredField(fieldName);
        // 保存原访问权限
        boolean accessible = field.isAccessible();
        // 设置允许通过反射访问
        field.setAccessible(true);
        // 设置值
        field.set(object, value);
        // 恢复原访问权限
        field.setAccessible(accessible);
    }
    
    /**
     * 设置一个属性的值
     * @param object 目标对象
     * @param fieldName 目标属性名
     * @param value 目标值
     * @throws Exception
     */
    public static void setFieldValue(Object object, String fieldName, Object value) throws Exception {
        setFieldValue(object.getClass(), object, fieldName, value);
    }

    // - 方法
    /**
     * 调用一个方法
     * @param class_ 方法的声明 Class
     * @param object 目标对象, 如果是静态方法, 则忽略此参数
     * @param methodName 目标方法名
     * @param args 参数列表
     * @return 方法的返回值
     * @throws Exception
     */
    public static Object invokeMethod(Class<?> class_, Object object, String methodName, Object... args) throws Exception {
        // 过滤参数
        if(args == null) {
            args = new Object[0];
        }
        // 查找方法
        Class<?>[] parameterTypes = new Class<?>[args.length];
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                parameterTypes[i] = args[i].getClass();
            }
        }
        Method method = class_.getDeclaredMethod(methodName, parameterTypes);
        // 保存原访问权限
        boolean accessible = method.isAccessible();
        // 设置允许通过反射访问
        method.setAccessible(true);
        // 调用方法
        Object result = method.invoke(object, args);
        // 恢复原访问权限
        method.setAccessible(accessible);
        // 返回结果
        return result;
    }
    
    /**
     * 调用一个方法
     * @param object 目标对象
     * @param methodName 目标方法名
     * @param args 参数列表
     * @return 方法的返回值
     * @throws Exception
     */
    public static Object invokeMethod(Object object, String methodName, Object... args) throws Exception {
        return invokeMethod(object.getClass(), object, methodName, args);
    }
    
    // - 构造方法
    /**
     * 调用一个构造函数
     * @param class_ 构造函数的声明 Class
     * @param args 参数列表
     * @return 实例化后的对象
     * @throws Exception
     */
    public static Object invokeConstructor(Class<?> class_, Object... args) throws Exception {
        // 过滤参数
        if(args == null) {
            args = new Object[0];
        }
        // 查找构造函数
        Class<?>[] parameterTypes = new Class<?>[args.length];
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                parameterTypes[i] = args[i].getClass();
            }
        }
        Constructor<?> constructor = class_.getDeclaredConstructor(parameterTypes);
        // 保存原访问权限
        boolean accessible = constructor.isAccessible();
        // 设置允许通过反射访问
        constructor.setAccessible(true);
        // 调用构造函数
        Object result = constructor.newInstance(args);
        // 恢复原访问权限
        constructor.setAccessible(accessible);
        // 返回结果
        return result;
    }
}
