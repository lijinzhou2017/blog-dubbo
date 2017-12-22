package com.blog.error;

import lombok.Data;

/**
 * 定义返回页面的异常控制类
 *
 * @author lijinzhou
 * @since 2017/12/20 19:14
 */
@Data
public class ViewException extends Exception {

    private Integer code;
    private String message;

    public ViewException(ErrorEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }

    public ViewException(Exception e) {
        this(ErrorEnum.EXCEPTION500);
        this.setStackTrace(e.getStackTrace());
    }
}
