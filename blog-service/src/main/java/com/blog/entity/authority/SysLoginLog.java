package com.blog.entity.authority;

import com.blog.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * 登录记录
 *
 * @author lijinzhou
 * @since 2017/12/12 19:07
 */
@Data
@Table(name = "sys_login_log")
public class SysLoginLog extends BaseEntity {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户民
     */
    private String username;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;
}
