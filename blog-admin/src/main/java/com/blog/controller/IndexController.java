package com.blog.controller;


import com.blog.annotation.AuthorityFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController extends BaseController{

    @AuthorityFilter(code = "INDEX")
    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @AuthorityFilter(checkAuthority = false)
    @GetMapping("")
    public String main(){
        return "main";
    }
}
