package com.glanway.jty.annotation;

import java.lang.annotation.*;

/**
 * 日志注解
 * @author tianxuan
 * @Time 2016/4/13
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Logger {
        String value();
        int operateType() default 99;
}
