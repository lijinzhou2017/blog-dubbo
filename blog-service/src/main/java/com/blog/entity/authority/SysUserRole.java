package com.blog.entity.authority;


import com.blog.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * 用户角色关联实体
 *
 * @author lijinzhou
 * @since 2017/12/12 19:06
 */
@Data
@Table(name = "sys_user_role")
public class SysUserRole extends BaseEntity {

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 更新时间
     */
    private Date createTime;
}
