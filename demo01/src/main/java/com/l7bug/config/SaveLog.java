package com.l7bug.config;

import java.lang.annotation.*;

/**
 * Log
 *
 * @author Administrator
 * @since 2025/4/15 10:48
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SaveLog {
    String content();

    Business business();

    String id();

    String type() default "'编辑'";

    String saveMethod() default "@la.saveLog(#logObj)";

    enum Business {
        /**
         * 协同单
         */
        COORDINATE
    }
}
