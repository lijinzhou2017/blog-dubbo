package com.blog.web;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CookieUtils {

    private static final Logger logger = LoggerFactory.getLogger(CookieUtils.class);

    /**
     * 根据名字获取cookie
     *
     * @param name cookie名字
     * @author lijinzhou
     * @since 2017/12/20 19:18
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        logger.info("[CookieUtils][getCookieByName]获取cookie,cookie名{}", name);
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }

    /**
     * 获取所有的cookie
     *
     * @author lijinzhou
     * @since 2017/12/20 19:18
     */
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     * 添加cookie
     *
     * @param cookieName       cookie名
     * @param value            cookie值
     * @param cookiePath       cookie path 属性
     * @param cookieExpiryDate cookie 过期时间单位秒
     * @author lijinzhou
     * @since 2017/12/20 19:18
     */
    public static void addCookie(String cookieName, String value, String domain, String cookiePath,
                                 int cookieExpiryDate, HttpServletResponse response) {
        logger.info("[CookieUtils][addCookie]新增cookie,{}:{}:{}:{}:{}", cookieName, value, domain, cookiePath, cookieExpiryDate);
        Cookie cookie = new Cookie(cookieName, value);
        if (StringUtils.isNotBlank(domain)) {
            cookie.setDomain(domain);
        }
        if (StringUtils.isNotBlank(cookiePath)) {
            cookie.setPath(cookiePath);
        }
        cookie.setMaxAge(cookieExpiryDate);
        response.addCookie(cookie);
    }

    /**
     * 添加cookie
     *
     * @param cookieName       cookie名
     * @param value            cookie值
     * @param cookieExpiryDate cookie 过期时间单位秒
     * @author lijinzhou
     * @since 2017/12/20 19:18
     */
    public static void addCookie(String cookieName, String value, int cookieExpiryDate, HttpServletResponse response) {
        addCookie(cookieName, value, null, null, cookieExpiryDate, response);
    }

    /**
     * 添加cookie
     *
     * @param cookieName cookie名
     * @param value      cookie值
     * @author lijinzhou
     * @since 2017/12/20 19:18
     */
    public static void addCookie(String cookieName, String value, HttpServletResponse response) {
        addCookie(cookieName, value, null, null, -1, response);
    }

    /**
     * 删除cookie
     *
     * @author lijinzhou
     * @since 2017/12/20 19:18
     */
    public static void delCookies(HttpServletResponse rs, String cookieName) {
        logger.info("[CookieUtils][delCookies]删除cookie,cookie名{}", cookieName);
        Cookie cook = new Cookie(cookieName, null);
        cook.setPath("/");
        cook.setMaxAge(0); //cookie设置为不记录,即删除
        rs.addCookie(cook);
    }
}
