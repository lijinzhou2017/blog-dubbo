package com.blog.service.authority;

import com.blog.entity.authority.SysRole;
import com.blog.helper.LayerResult;
import com.github.pagehelper.PageInfo;

import java.util.List;


/**
 * 角色
 *
 * @author lijinzhou
 * @since 2017/12/23 21:35
 */
public interface ISysRoleService {

    /**
     * 获取符合条件的数据
     */
    List<SysRole> selectAll(SysRole role);

    /**
     * 新增或者编辑角色
     */
    LayerResult insertOrUpdateRole(SysRole role);

    /**
     * 修改状态
     */
    LayerResult updateStatus(Integer status, Integer id);

    /**
     * 角色分配权限
     *
     * @param roleId       角色id
     * @param authorityIds 权限id数组
     */
    LayerResult allotRoleAuthority(Integer roleId, Integer[] authorityIds);

    /**
     * 角色关联用户
     */
    LayerResult allotUserRole(Integer roleId, Integer[] userIds);

    /**
     * 分页获取角色信息
     */
    PageInfo<SysRole> pageInfo(SysRole role);

    /**
     * 解除角色关联的用户
     */
    LayerResult removeUserRole(Integer roleId, Integer[] userIds);

    /**
     * 根据id查询
     */
    SysRole selectById(Integer id);
}
