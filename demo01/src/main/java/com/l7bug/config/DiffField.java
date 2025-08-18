package com.l7bug.config;

import java.lang.annotation.*;

/**
 * DiffField
 *
 * @author Administrator
 * @since 2025/4/15 11:55
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DiffField {
    /**
     * 字段名
     *
     * @return 字段名
     */
    String value();

    boolean needNextDiff() default false;

    record Info(String fieldName, String oldValue, String newValue) {

    }
}
