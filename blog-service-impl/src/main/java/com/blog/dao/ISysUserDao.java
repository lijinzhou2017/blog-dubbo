package com.blog.dao;


import com.blog.baseDao.IBaseDao;
import com.blog.entity.authority.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author lijinzhou
 * @since 2017/12/11 10:02
 */
@Repository
public interface ISysUserDao extends IBaseDao<SysUser> {

     List<Map<String,Object>> getUserAll();

}
