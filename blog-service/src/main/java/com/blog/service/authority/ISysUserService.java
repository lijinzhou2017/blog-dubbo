package com.blog.service.authority;

import com.blog.entity.authority.SysUser;
import com.blog.helper.LayerResult;
import com.github.pagehelper.PageInfo;
import eu.bitwalker.useragentutils.UserAgent;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author lijinzhou
 * @since 2017/12/12 19:10
 */
public interface ISysUserService {

    /**
     * 根据条件获取符合条件的数据
     */
    List<SysUser> selectAll(SysUser user);

    /**
     * 新增或者修改用户
     */
    LayerResult insertOrUpdateUser(SysUser user);

    /**
     * 启用/禁用
     */
    LayerResult updateStatus(Integer status, Integer id);

    /**
     * 获取用户的信息,包括是否拥有roleId的角色
     */
    List<SysUser> selectAllIsHasRole(SysUser user, Integer roleId);

    /**
     * 登录操作
     */
    Map<String, Object> login(SysUser user, UserAgent userAgent, String ip);

    /**
     * 根据用户名或者手机号获取
     *
     * @param username 用户名或者手机号
     * @param password 密码
     */
    SysUser selectByUsernameAndPassword(String username, String password);

    /**
     * 根据user分页获取
     */
    PageInfo<SysUser> pageInfo(SysUser user);

    /**
     * 重置用户的密码
     */
    LayerResult resetPassword(Integer id);

    /**
     * 根据id查询
     */
    SysUser selectById(Integer id);
}
