package com.blog.dao.authority;


import com.blog.basedao.IBaseDao;
import com.blog.entity.authority.SysRoleAuthority;
import org.springframework.stereotype.Repository;


/**
 * 角色-权限
 *
 * @author lijinzhou
 * @since 2017/12/23 21:26
 */
@Repository
public interface ISysRoleAuthorityDao extends IBaseDao<SysRoleAuthority, Integer> {

    /**
     * 根据角色id获取权限的id字符串
     * eg:1,2,3
     */
    String selectAuthorityIdsByRole(Integer roleId);

}
