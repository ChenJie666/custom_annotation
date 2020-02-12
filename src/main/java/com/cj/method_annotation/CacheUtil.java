package com.cj.method_annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通过反射调用方法，检查注解和缓存并返回结果，具体步骤如下：
 * 1.调用方法时检查是否有注解，无@Cache注解直接返回结果
 * 2.有@Cache注解，则检查缓存是否命中，命中则返回缓存值
 * 3.如未命中，则调用方法得到结果，对结果进行缓存并返回结果
 */
public class CacheUtil {

    //TODO 创建用于缓存的多线程安全的Map集合
    private static Map<String, Object> cacheMap = new ConcurrentHashMap<String, Object>();

    private static final String CUSTOM_SEPARATION = "::";

    public static Object invokeMethod(Object obj, String methodName, Object... params) {
        Object result = null;
        try {
            Class<?> clazz = obj.getClass();
            if (params == null) {
                //TODO 如果方法没有参数
                Method method = clazz.getDeclaredMethod(methodName);
                method.setAccessible(true);
                Cache annotation = method.getAnnotation(Cache.class);
                if (annotation != null) {
                    String key = annotation.key();
                    Object cache = cacheMap.get(key);
                    if (cache != null) {
                        return cache;
                    }
                    result = method.invoke(obj);
                    cacheMap.put(key,result);
                }
            } else {
                //TODO 如果方法有参数
                int size = params.length;
                Class[] classes = new Class[size];
                Object[] objs = new Object[size];
                for (int i = 0; i < size; i++) {
                    classes[i] = params[i].getClass();
                    objs[i] = params[i];
                }
                Method method = clazz.getDeclaredMethod(methodName, classes);
                method.setAccessible(true);
                Cache annotation = method.getAnnotation(Cache.class);
                if (annotation != null) {
                    String key = annotation.key() + CUSTOM_SEPARATION + params[0];
                    Object cache = cacheMap.get(key);
                    if(cache != null) {
                        return cache;
                    }
                    result = method.invoke(obj, params);
                    cacheMap.put(key, result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
