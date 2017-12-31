package com.blog.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpSession;

@Controller
public class BaseController {

    //@Autowired
    //private CacheUtils<?> cacheUtils;

    @InitBinder
    public void init(HttpSession session){
        //cacheUtils.setSession(session);
    }
}
