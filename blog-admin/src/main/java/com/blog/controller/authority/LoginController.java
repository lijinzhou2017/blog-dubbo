package com.blog.controller.authority;


import com.alibaba.dubbo.config.annotation.Reference;
import com.blog.Constants;
import com.blog.annotation.AuthorityFilter;
import com.blog.entity.authority.SysUser;
import com.blog.helper.LayerResult;
import com.blog.service.authority.ISysAuthorityService;
import com.blog.service.authority.ISysUserService;
import com.blog.web.WebUtils;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 后台管理系统登录
 *
 * @author lijinzhou
 * @since 2017/12/24 20:03
 */
@Controller
public class LoginController {

    @Reference
    private ISysUserService sysUserService;

    @Reference
    private ISysAuthorityService sysAuthorityService;

    public  ISysAuthorityService getSysAuthorityService(){
        return  sysAuthorityService;
    }
    @AuthorityFilter(checkLogin = false, checkAuthority = false)
    @GetMapping("/toLogin")
    public String toLogin() {
        return "authority/login";
    }

    /**
     * 后台管理系统登录
     *
     * @author linyb
     * create on 2017/7/11 13:57
     */
    @AuthorityFilter(checkLogin = false, checkAuthority = false)
    @RequestMapping("/login")
    @ResponseBody
    public LayerResult login(SysUser sysUser, HttpServletRequest req, HttpSession session) {
        Map<String, Object> result = sysUserService.login(
                sysUser,
                UserAgent.parseUserAgentString(req.getHeader("User-Agent")),
                WebUtils.getIpAddress(req));
        session.setAttribute(Constants.LOGIN_SESSION, result.get(Constants.LOGIN_SESSION));
        session.setAttribute(Constants.AUTHORITY_SESSION, result.get(Constants.AUTHORITY_SESSION));
        return (LayerResult) result.get("layer");
    }

    @AuthorityFilter(checkLogin = false, checkAuthority = false)
    @GetMapping("/loginOut")
    public String loginOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(Constants.LOGIN_SESSION); //登录信息session
        session.removeAttribute(Constants.AUTHORITY_SESSION);//权限信息
        session.removeAttribute(Constants.AUTHORITY_USER_CODE_SESSION);//用户拥有的权限code信息
        return this.toLogin();
    }
}
