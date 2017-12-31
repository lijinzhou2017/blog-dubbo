package com.blog.dao.authority;


import com.blog.basedao.IBaseDao;
import com.blog.entity.authority.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lijinzhou
 * @since 2017/12/11 10:02
 */
@Repository
public interface ISysUserDao extends IBaseDao<SysUser,Integer> {

    /**
     * 获取用户的信息,包括是否拥有roleId的角色
     *
     * @author lijinzhou
     * @since 2017/12/11 10:02
     */
    List<SysUser> selectAllIsHasRole(SysUser sysUser);

    List<SysUser> selectAllByEntity(SysUser sysUser);

}
