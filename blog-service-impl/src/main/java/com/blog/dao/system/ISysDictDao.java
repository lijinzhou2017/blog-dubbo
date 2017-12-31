package com.blog.dao.system;


import com.blog.basedao.IBaseDao;
import com.blog.entity.system.SysDict;
import org.springframework.stereotype.Repository;

/**
 * 数据字典 Dao
 *
 * @author lijinzhou
 * @date 2017/9/28 21:22
 */
@Repository
public interface ISysDictDao extends IBaseDao<SysDict, Integer> {


}
