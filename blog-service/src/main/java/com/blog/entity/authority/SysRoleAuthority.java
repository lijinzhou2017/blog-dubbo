package com.blog.entity.authority;


import com.blog.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * 角色-权限 实体
 *
 * @author lijinzhou
 * @since 2017/12/12 19:08
 */
@Data
@Table(name = "sys_role_authority")
public class SysRoleAuthority extends BaseEntity {


    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 权限id
     */
    private Integer authorityId;

    /**
     * 创建时间
     */
    private Date createTime;

}

