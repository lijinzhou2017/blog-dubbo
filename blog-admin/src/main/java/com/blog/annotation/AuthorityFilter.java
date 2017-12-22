package com.blog.annotation;

import java.lang.annotation.*;

/**
 * 权限验证自定义注解
 *
 * @author lijinzhou
 * @since 2017/12/20 20:18
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorityFilter {

    /**
     * 是否验证登录,默认验证
     */
    boolean checkLogin() default true;

    /**
     * 是否验证拥有权限,默认验证
     */
    boolean checkAuthority() default true;

    /**
     * 权限默认对应的唯一Code
     */
    String code() default "";
}
