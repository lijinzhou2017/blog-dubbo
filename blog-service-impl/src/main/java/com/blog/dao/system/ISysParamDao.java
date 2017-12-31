package com.blog.dao.system;


import com.blog.basedao.IBaseDao;
import com.blog.entity.system.SysParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统参数 Dao
 *
 * @author lijinzhou
 * @date 2017/9/28 21:22
 */
@Repository
public interface ISysParamDao extends IBaseDao<SysParam, Integer> {

    List<SysParam> selectSysParamList(SysParam sysParam);

}
