package com.blog.config;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.blog.Constants;
import com.blog.annotation.AuthorityFilter;
import com.blog.controller.authority.LoginController;
import com.blog.entity.authority.SysUser;
import com.blog.error.ErrorEnum;
import com.blog.helper.LayerResult;
import com.blog.service.authority.ISysAuthorityService;
import com.blog.web.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * AuthorityFilter 注解规则定义
 *
 * @author lijinzhou
 * @since 2017/12/24 21:09
 */
@Configuration
public class AuthorityFilterHandler extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(AuthorityFilterHandler.class);

    @Autowired
    private LoginController loginController;

    @Reference
    private ISysAuthorityService sysAuthorityService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod methodHandler = (HandlerMethod) handler;
        AuthorityFilter authorityFilter = methodHandler.getMethodAnnotation(AuthorityFilter.class);

        String path = request.getServletPath();
        logger.info("[AuthorityFilterHandler][doFilter]" + path);
        //如果没有加要过滤的,默认过
        if (authorityFilter == null) {
            return true;
        }

        //需要登录验证
        if (authorityFilter.checkLogin()) {
            HttpSession session = request.getSession();
            Object objUser = session.getAttribute(Constants.LOGIN_SESSION);
            if (objUser == null) {
                filter(request, response, ErrorEnum.EXCEPTION199);
                return false;
            }

            //需要验证权限,没有登录,就没有需要验证权限一说
            if (authorityFilter.checkAuthority()) {
                //权限唯一code
                String code = authorityFilter.code();
                if (StringUtils.isBlank(code)) {
                    filter(request, response, ErrorEnum.EXCEPTION198);
                    return false;
                }
                SysUser sysUser = (SysUser) objUser;
                boolean isHas = loginController.getSysAuthorityService().isHasAuthority(sysUser.getId(), code);
                if (!isHas) {
                    filter(request, response, ErrorEnum.EXCEPTION198);
                }
                return isHas;
            }
        }
        return super.preHandle(request, response, handler);
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    private void filter(HttpServletRequest request, HttpServletResponse response, ErrorEnum errorEnum) throws IOException, ServletException {
        if (WebUtils.isAjax(request)) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.append(JSONObject.toJSONString(LayerResult.FAIL(errorEnum)));
            writer.flush();
            writer.close();
        } else {
            response.sendRedirect("/toLogin");
        }
    }
}
