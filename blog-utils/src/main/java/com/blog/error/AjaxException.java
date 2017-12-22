package com.blog.error;

import lombok.Data;

/**
 * 定义ajax请求异常信息类
 *
 * @author lijinzhou
 * @since 2017/12/20 19:12
 */
@Data
public class AjaxException extends Exception {

    private Integer code;
    private String message;


    public AjaxException(ErrorEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }

    public AjaxException(Exception e) {
        this(ErrorEnum.EXCEPTION500);
        this.setStackTrace(e.getStackTrace());
    }
}
