package com.blog.config.bind;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

public class SelfDefinitionDateBindingInitializer implements WebBindingInitializer {

    @Override
    public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
        //日期格式的 使用自定义的规则转换
        webDataBinder.registerCustomEditor(Date.class, new SelfDateEditor());
    }
}
