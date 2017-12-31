package com.blog.dao.authority;

import com.blog.basedao.IBaseDao;
import com.blog.entity.authority.SysRole;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 后台管理 角色
 *
 * @author lijinzhou
 * @since 2017/12/23 21:25
 */
@Repository
public interface ISysRoleDao extends IBaseDao<SysRole, Integer> {

    List<SysRole> selectRoleList(SysRole sysRole);
}
