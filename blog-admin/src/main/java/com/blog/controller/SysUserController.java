package com.blog.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.blog.service.ISysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class SysUserController {

    @Reference
    private ISysUserService sysUserService;

    @RequestMapping(value="/user2")
    @ResponseBody
    public List<Map<String,Object>> ajaxUser2(){
        List<Map<String,Object>> userlist=sysUserService.getUserAll();
        return userlist;
    }

}
