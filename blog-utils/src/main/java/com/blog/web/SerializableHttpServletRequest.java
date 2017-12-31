package com.blog.web;

import lombok.Data;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * dubbo传输需要序列化
 * HttpServletRequest无法序列化问题,所以如果需要传输request给dubbo,需要把request传化为这个类
 * 封装一些常用的,如果下面的属性没用,可继续扩展
 */
@Data
public class SerializableHttpServletRequest implements Serializable {

    private Map<String, String> headerMap;
    private String remoteAddr;
    private Cookie[] cookies;
    //private HttpSession httpSession; //session也无法序列化

    public SerializableHttpServletRequest(HttpServletRequest httpServletRequest) {
        headerMap = new HashMap<>();
        headerMap.put("User-Agent", httpServletRequest.getHeader("User-Agent"));
        headerMap.put("X-Real-IP", httpServletRequest.getHeader("X-Real-IP"));
        headerMap.put("X-Forwarded-For", httpServletRequest.getHeader("X-Forwarded-For"));
        //...等等其它的header..

        this.remoteAddr = httpServletRequest.getRemoteAddr();
        this.cookies = httpServletRequest.getCookies();
    }
}
