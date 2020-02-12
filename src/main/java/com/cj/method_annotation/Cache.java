package com.cj.method_annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 自定义的缓存注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {

    /**
     * 缓存的key名字
     *
     * @return
     */
    String key();

    /**
     * 缓存过期时间
     *
     * @return
     */
    int timeOut() default 10;

    /**
     * 缓存时间单位
     *
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.MINUTES;

}
