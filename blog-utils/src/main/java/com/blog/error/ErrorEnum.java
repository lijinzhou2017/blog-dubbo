package com.blog.error;


import com.blog.Constants;

/**
 * 异常编码
 *
 * @author lijinzhou
 * @since 2017/12/20 19:13
 */
public enum ErrorEnum {

    //比如要求id大于0,传过来id异常等,跟404页面区分开
    EXCEPTION403(Constants.CODE_DATA_DISABLE, "数据不合法"),
    EXCEPTION404(Constants.CODE_VIEW_404, "页面丢失了"),
    EXCEPTION500(Constants.CODE_SERVICE_EX, "服务异常"),
    EXCEPTION199(Constants.CODE_LOGIN_TIME_OUT, "登录过期,重新登录再操作"),
    EXCEPTION198(Constants.CODE_UN_AUTHORITY, "无权限操作");

    private int code;   //code 404  500
    private String message; //异常信息

    ErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
