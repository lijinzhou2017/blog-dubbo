package com.blog.helper;

import com.blog.error.ErrorEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 兼容layer插件返回封装
 *
 * @author lijinzhou
 * @since 2017/12/20 19:31
 */
@Data
public class LayerResult implements Serializable {


    private Integer result = 200;//返回结果码
    private String msg = "操作成功";   //返回提示信息
    private Integer icon = 1; //layer插件的icon


    public LayerResult(Integer result, String msg, Integer icon) {
        this.result = result;
        this.msg = msg;
        this.icon = icon;
    }

    /**
     * 成功默认构造
     *
     * @author Linyb
     * @since 2017/9/7 19:38
     */
    public static LayerResult SUCCESS() {
        return new LayerResult(200, "操作成功", 1);
    }

    /**
     * 失败默认构造
     *
     * @author Linyb
     * @since 2017/9/7 19:38
     */
    public static LayerResult FAIL() {
        return FAIL(ErrorEnum.EXCEPTION500);
    }

    public static LayerResult FAIL(ErrorEnum errorEnum) {
        return new LayerResult(errorEnum.getCode(), errorEnum.getMessage(), 2);
    }

    public static LayerResult FAIL500(String msg) {
        return new LayerResult(ErrorEnum.EXCEPTION500.getCode(), msg, 2);
    }
}
