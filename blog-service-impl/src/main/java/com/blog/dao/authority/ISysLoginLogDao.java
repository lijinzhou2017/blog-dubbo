package com.blog.dao.authority;


import com.blog.basedao.IBaseDao;
import com.blog.entity.authority.SysLoginLog;
import org.springframework.stereotype.Repository;

/**
 * 登录记录
 *
 * @author lijinzhou
 * @since 2017/12/23 21:26
 */
@Repository
public interface ISysLoginLogDao extends IBaseDao<SysLoginLog, Integer> {


}
