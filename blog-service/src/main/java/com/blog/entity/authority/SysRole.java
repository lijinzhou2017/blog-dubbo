package com.blog.entity.authority;

import com.blog.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * 后台管理  角色
 *
 * @author lijinzhou
 * @since 2017/12/12 18:50
 */
@Data
@Table(name = "sys_role")
public class SysRole extends BaseEntity {

    /**
     * 角色名
     */
    private String name;

    /**
     * 状态 0无效 1有效
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}

