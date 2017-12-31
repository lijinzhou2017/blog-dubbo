package com.blog.service.authority;


import com.blog.entity.authority.SysAuthority;
import com.blog.helper.LayerResult;

import java.util.List;


/**
 * 权限
 *
 * @author lijinzhou
 * @since 2017/12/23 21:36
 */
public interface ISysAuthorityService {

    /**
     * 获取所有对应状态以及类型的权限树
     *
     * @param status 状态,null表示不限
     * @param type   类型,null表示不限
     * @param level  层级,null表示不限
     */
    List<SysAuthority> selectAllAuthority(Integer status, Integer type, Integer level);

    /**
     * 获取所有对应父类下的对应状态以及类型的权限
     *
     * @param parentId 父类id
     * @param status   状态,null表示不限
     * @param type     类型,null表示不限
     * @param level    层级,null表示不限
     */
    List<SysAuthority> selectAuthorityByParent(Integer parentId, Integer status, Integer type, Integer level);

    /**
     * 保存或者是修改权限
     *
     * @param authority 权限实体
     */
    LayerResult insertOrUpdateAuthority(SysAuthority authority);

    /**
     * 修改状态
     *
     * @param status 修改之后的状态
     * @param id     要修改的数据id
     */
    LayerResult updateStatus(Integer status, Integer id);

    /**
     * 修改当前及其下级的path
     *
     * @param currPath 当前要修改节点的path
     * @param newPath  当前节点修改之后的path
     */
    Integer updatePath(String currPath, String newPath);

    /**
     * 获取对应的菜单权限
     */
    List<SysAuthority> selectMenuAuthority(Integer userId);

    /**
     * 获取用户对应的权限数据
     *
     * @param userId   用户id
     * @param status   状态
     * @param type     权限类型
     * @param parentId 父权限id
     */
    List<SysAuthority> selectUserAuthority(Integer userId, Integer status, Integer type, Integer parentId);

    /**
     * 根据用户,路径,判断用户是否有该权限
     *
     * @param userId 用户id
     * @param code   权限code
     */
    boolean isHasAuthority(Integer userId, String code);

    /**
     * 获取用户对应的权限Code
     */
    List<String> selectCodeByUser(Integer userId, Integer status);


    /**
     * 根据id查询
     */
    SysAuthority selectById(Integer id);
}
