package com.blog.entity.authority;


import com.blog.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * 系统权限实体
 *
 * @author lijinzhou
 * @since 2017/12/12 19:02
 */
@Data
@Table(name = "sys_authority")
public class SysAuthority extends BaseEntity {

    /**
     * 父权限id
     */
    private Integer parentId;

    /**
     * 唯一标识
     */
    private String code;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 菜单对应的路径
     */
    private String url;

    /**
     * 0无效 1有效
     */
    private Integer status;

    /**
     * 图标样式
     */
    private String icon;

    /**
     * 层级 一级菜单 1,二级菜单 2,三级按钮  3
     */
    private Integer level;

    /**
     * 类型 1菜单型 0按钮型
     */
    private Integer type;

    /**
     * 路径 如: _1_2_3_
     */
    private String path;

    /**
     * 备注
     */
    private String remark;

    /**
     * 排序
     */
    private Integer orders;

    /**
     * 创建人id
     */
    private Integer createId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @Transient
    private List<SysAuthority> nodes; //该权限的下级集合
    @Transient
    private String text; //bootstrap tree view 字段名称
    @Transient
    private Integer userId; //查询某个用户的权限信息用到

}
