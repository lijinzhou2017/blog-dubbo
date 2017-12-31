package com.blog.entity.authority;

import com.blog.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Data
@Table(name = "sys_user")
public class SysUser extends BaseEntity {

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机
     */
    private String phone;

    /**
     * 座机,固定电话
     */
    private String telephone;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 0保密 1男 2女
     */
    private Integer sex;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 头像
     */
    private String head;

    /**
     * 密码
     */
    private String password;

    /**
     * 0无效 1有效
     */
    private Integer status;

    /**
     * 登录次数
     */
    private Integer loginNum;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    @Transient
    private Integer userRoleId;  //sys_user_role id
    @Transient
    private Integer roleId;      //用户是否拥有该角色用到
}
