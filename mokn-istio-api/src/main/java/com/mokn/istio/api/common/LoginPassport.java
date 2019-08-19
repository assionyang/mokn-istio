package com.mokn.istio.api.common;

import java.lang.annotation.*;

/**
 * 登录验证注解
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginPassport {
    boolean valid() default true;
    String role() default "";
}
