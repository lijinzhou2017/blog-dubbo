package com.blog.dao.authority;

import com.blog.basedao.IBaseDao;
import com.blog.entity.authority.SysAuthority;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * 权限
 *
 * @author lijinzhou
 * @since 2017/12/23 21:26
 */
@Repository
public interface ISysAuthorityDao extends IBaseDao<SysAuthority, Integer> {

    /**
     * 修改当前及其下级的path
     *
     * @param currPath 当前要修改节点的path
     * @param newPath  当前节点修改之后的path
     * @param subIndex 当前path,length+1
     */
    Integer updatePath(@Param("currPath") String currPath, @Param("newPath") String newPath, @Param("subIndex") int subIndex);

    /**
     * 获取权限信息
     * 包括父权限,状态,用户,类型
     */
    List<SysAuthority> selectUserAuthority(Map<String, Object> map);

    /**
     * 获取用户对应的权限Code
     */
    List<String> selectCodeByUser(@Param("userId") Integer userId, @Param("status") Integer status);
}
