package com.blog.entity.system;

import com.blog.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * 系统参数 实体
 *
 * @author lijinzhou
 * @date 2017/9/28 21:13
 */
@Data
@Table(name = "sys_param")
public class SysParam extends BaseEntity {

    /**
     * 参数编码
     */
    private String code;

    /**
     * 参数值
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
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
