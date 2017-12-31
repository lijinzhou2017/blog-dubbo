package com.blog.dao.authority;


import com.blog.basedao.IBaseDao;
import com.blog.entity.authority.SysUserRole;
import org.springframework.stereotype.Repository;


/**
 * 用户角色关联
 *
 * @author lijinzhou
 * @since 2017/12/23 21:27
 */
@Repository
public interface ISysUserRoleDao extends IBaseDao<SysUserRole, Integer> {


}
