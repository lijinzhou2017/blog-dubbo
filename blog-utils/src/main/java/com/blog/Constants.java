package com.blog;

/**
 * 常量
 *
 * @author lijinzhou
 * @since 2017/12/20 19:20
 */
public class Constants {

    //菜单型
    public static final Integer TYPE_MENU    = 1;
    //按钮型
    public static final Integer TYPE_BUTTON  = 0;


    //默认的分页数
    public static final Integer PAGE_SIZE = 10;
    // 通用的状态 1 启用/有效
    public static final Integer COMMON_STATUS_1 = 1;
    // 通用的状态 0 禁用/无效
    public static final Integer COMMON_STATUS_0 = 0;

    // 登录的session name ,thymeleaf
    public static final String LOGIN_SESSION = "MANAGE_LOGIN_SESSION";
    // 用户登录后权限 session name
    public static final String AUTHORITY_SESSION = "MANAGE_AUTHORITY_SESSION";
    // 用户拥有的权限code set集合 session name
    public static final String AUTHORITY_USER_CODE_SESSION = "MANAGE_AUTHORITY_USER_CODE";

    //默认密码
    public static final String DEFAULT_PASSWORD = "123456";

    //登录过期code,不要修改
    public static final int CODE_UN_AUTHORITY = 198;

    //登录过期code,不要修改
    public static final int CODE_LOGIN_TIME_OUT = 199;

    //404,不要修改
    public static final int CODE_VIEW_404 = 404;

    //数据不合法,不要修改
    public static final int CODE_DATA_DISABLE = 405;

    //服务异常,不要修改
    public static final int CODE_SERVICE_EX = 500;
}
