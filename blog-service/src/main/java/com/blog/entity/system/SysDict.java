package com.blog.entity.system;


import com.blog.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * 数据字典 实体
 *
 * @author lijinzhou
 * @since 2017/12/12 19:04
 */
@Data
@Table(name = "sys_dict")
public class SysDict extends BaseEntity {
    /**
     * 数据键
     */
    private String code;

    /**
     * 数据值
     */
    private String text;

    /**
     * 0无效 1有效
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 排序
     */
    private Integer orders;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
