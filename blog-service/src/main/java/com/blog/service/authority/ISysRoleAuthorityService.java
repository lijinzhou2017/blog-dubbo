package com.blog.service.authority;


import com.blog.error.ViewException;

/**
 * 角色-权限
 *
 * @author lijinzhou
 * @since 2017/12/23 21:35
 */
public interface ISysRoleAuthorityService {

    /**
     * 获取角色对应的权限id
     * eg:1,2,3,4
     */
    String selectAuthorityIdsByRole(Integer roleId) throws ViewException;
}
