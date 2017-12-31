package com.blog.config;


import com.blog.config.bind.SelfDefinitionDateBindingInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * BindingResult 时间格式字符串转日期异常问题
 * @author Linyb
 * @since 2017/9/14 19:16
 */
@Configuration
public class SelfDefinitionBindingConfiguration {

    @Autowired
    public void setWebBindingInitializer(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        requestMappingHandlerAdapter.setWebBindingInitializer(new SelfDefinitionDateBindingInitializer());
    }

}
