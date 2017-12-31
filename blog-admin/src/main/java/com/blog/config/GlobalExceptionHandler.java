package com.blog.config;


import com.alibaba.fastjson.JSONObject;
import com.blog.error.AjaxException;
import com.blog.error.ViewException;
import com.blog.helper.LayerResult;
import com.blog.web.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 异常配置
 *
 * @author lijinzhou
 * @since 2017/12/20 19:32
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    private LayerResult ajaxErrorHandler(HttpServletRequest request) {
        logger.error("[GlobalExceptionHandler][ajaxErrorHandler]{}", request.getServletPath());
        LayerResult result = new LayerResult(500, "服务异常,请稍后再试", 2);
        //这里还可以做一些其它操作,比如可以把异常信息存表,做记录
        return result;
    }

    private ModelAndView viewErrorHandler(HttpServletRequest request, ViewException e) {
        logger.error("异常:" + e.getCode());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", e);
        modelAndView.setViewName("/error"); //返回error页面
        modelAndView.addObject("url", request.getRequestURL().toString());
        //这里还可以做一些其它操作,比如可以把异常信息存表,做记录
        return modelAndView;
    }

    @ExceptionHandler(value = Exception.class)
    public Object global(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {

        logger.error("[GlobalExceptionHandler][全局异常]{}", e);
        if (WebUtils.isAjax(request) || e instanceof AjaxException) {
            LayerResult layerResult = this.ajaxErrorHandler(request);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.append(JSONObject.toJSONString(layerResult));
            writer.flush();
            writer.close();
            return layerResult;
        } else {
            return viewErrorHandler(request, new ViewException(e));
        }
    }

}
