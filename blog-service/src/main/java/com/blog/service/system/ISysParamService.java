package com.blog.service.system;


import com.blog.entity.system.SysParam;
import com.blog.helper.LayerResult;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 系统参数
 * @author lijinzhou
 * @date 2017/9/28 21:32
 */
public interface ISysParamService {

    /**
     * 根据条件获取符合条件的数据
     * @author lijinzhou
     * @since 2017/9/29 19:11
     */
    List<SysParam> selectAllByEntity(SysParam sysParam);

    /**
     * 新增或者修改
     * @author lijinzhou
     * @since 2017/9/29 17:28
     */
    LayerResult insertOrUpdate(SysParam sysParam);

    /**
     * 启用/禁用
     * @author lijinzhou
     * @since 2017/9/29 20:16
     */
    LayerResult updateStatus(Integer status, Integer id);

    /**
     * 分页获取
     * @author lijinzhou
     * @since 2017/11/18 23:03
     */
    PageInfo<SysParam> pageInfo(SysParam sysParam);

    /**
     * 根据id获取数据
     * @author lijinzhou
     * @since 2017/9/29 19:11
     */
    SysParam selectById(Integer id);

}
