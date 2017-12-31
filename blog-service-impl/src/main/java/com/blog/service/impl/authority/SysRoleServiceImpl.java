package com.blog.service.impl.authority;


import com.alibaba.dubbo.config.annotation.Service;
import com.blog.Constants;
import com.blog.cache.CacheUtils;
import com.blog.dao.authority.ISysRoleAuthorityDao;
import com.blog.dao.authority.ISysRoleDao;
import com.blog.dao.authority.ISysUserDao;
import com.blog.dao.authority.ISysUserRoleDao;
import com.blog.entity.authority.SysRole;
import com.blog.entity.authority.SysRoleAuthority;
import com.blog.entity.authority.SysUser;
import com.blog.entity.authority.SysUserRole;
import com.blog.helper.LayerResult;
import com.blog.service.authority.ISysRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 角色
 *
 * @author lijinzhou
 * @since 2017/12/23 22:24
 */
@Service
public class SysRoleServiceImpl  implements ISysRoleService {

    private final Logger logger = LoggerFactory.getLogger(SysRoleServiceImpl.class);

    @Autowired
    private ISysRoleDao sysRoleDao;
    @Autowired
    private ISysRoleAuthorityDao sysRoleAuthorityDao;
    @Autowired
    private ISysUserDao sysUserDao;
    @Autowired
    private ISysUserRoleDao sysUserRoleDao;
    @Autowired
    private CacheUtils<?> cacheUtil;

    @Override
    public List<SysRole> selectAll(SysRole sysRole) {
        return sysRoleDao.select(sysRole);
    }

    @Override
    public LayerResult insertOrUpdateRole(SysRole sysRole) {
        if (sysRole == null) {
            return LayerResult.FAIL500("角色数据为空");
        }
        Date date = new Date();
        sysRole.setUpdateTime(date);
        int var;
        if (sysRole.getId() != null) {
            var = sysRoleDao.updateByPrimaryKeySelective(sysRole);
        } else {
            sysRole.setCreateTime(date);
            var = sysRoleDao.insertSelective(sysRole);
        }
        return var > 0 ? LayerResult.SUCCESS() : LayerResult.FAIL();
    }

    @Override
    public LayerResult updateStatus(Integer status, Integer id) {
        if (status == null || id == null) {
            return LayerResult.FAIL();
        }
        SysRole sysRole = sysRoleDao.selectByPrimaryKey(id);
        if (status.equals(sysRole.getStatus())) {
            logger.error("[SysRoleServiceImpl][updateStatus]修改状态,状态已是要修改后的状态;ID为:" + id);
            return LayerResult.FAIL500("数据异常,请重试");
        }
        sysRole = new SysRole();
        sysRole.setStatus(status);
        sysRole.setId(id);
        int var = sysRoleDao.updateByPrimaryKeySelective(sysRole);
        return var > 0 ? LayerResult.SUCCESS() : LayerResult.FAIL();
    }

    @Override
    public LayerResult allotRoleAuthority(Integer roleId, Integer[] authorityIds) {
        //清除用户已分配权限缓存
        cacheUtil.clearUserAuthority();
        //1、删除该角色对应的权限
        SysRoleAuthority roleAuthority = new SysRoleAuthority();
        roleAuthority.setRoleId(roleId);
        sysRoleAuthorityDao.delete(roleAuthority);
        //2、重新分配该角色权限
        List<SysRoleAuthority> roleAuthorities = new ArrayList<>();
        if (authorityIds != null && authorityIds.length > 0) {
            Date now = new Date();
            for (int i = 0; i < authorityIds.length; i++) {
                roleAuthority = new SysRoleAuthority();
                roleAuthority.setCreateTime(now);
                roleAuthority.setRoleId(roleId);
                roleAuthority.setAuthorityId(authorityIds[i]);
                roleAuthorities.add(roleAuthority);
            }
            Integer result = sysRoleAuthorityDao.insertList(roleAuthorities);
            return result > 0 ? LayerResult.SUCCESS() : LayerResult.FAIL();
        }
        return LayerResult.SUCCESS();
    }

    @Override
    public LayerResult allotUserRole(Integer roleId, Integer[] userIds) {
        if (userIds == null || userIds.length < 1) {
            return LayerResult.FAIL500("请选择要关联的用户");
        }
        //用户的状态验证
        StringBuilder sb = new StringBuilder();
        for (Integer id : userIds) {
            sb.append(id).append(",");
        }
        String idStr = sb.substring(0, sb.length() - 1);
        List<SysUser> users = sysUserDao.selectByIds(idStr);

        for (SysUser user : users) {
            if (!Constants.COMMON_STATUS_1.equals(user.getStatus())) {
                return LayerResult.FAIL500("用户状态禁用的不能关联,请刷新页面重试");
            }
        }
        //重复数据过滤  roleId userId唯一,防止重复入库
        SysUserRole userRole = new SysUserRole();
        userRole.setRoleId(roleId);
        List<SysUserRole> userRoles = sysUserRoleDao.select(userRole);
        List<Integer> userIdList = Arrays.asList(userIds);
        List<Integer> var = new ArrayList<>();
        for (Integer userId : userIdList) {
            var.add(userId);
        }
        for (int i = 0; i < userRoles.size(); i++) {
            SysUserRole var1 = userRoles.get(i);
            Integer userId = var1.getUserId();
            if (userIdList.contains(userId)) {
                var.remove(userId);
            }
        }
        //符合条件的入库
        if (var.isEmpty()) {
            return LayerResult.FAIL500("请选择未关联的用户");
        }
        userRoles = new ArrayList<>();
        Date now = new Date();
        for (Integer userId : var) {
            userRole = new SysUserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(userId);
            userRole.setCreateTime(now);
            userRoles.add(userRole);
        }
        int count = sysUserRoleDao.insertList(userRoles);
        //清除用户已分配权限缓存
        cacheUtil.clearUserAuthority();
        return count > 0 ? LayerResult.SUCCESS() : LayerResult.FAIL();
    }

    @Override
    public PageInfo<SysRole> pageInfo(SysRole role) {
        PageHelper.startPage(role.getPageNum(), role.getPageSize());
        List<SysRole> roles = this.selectAll(role);
        return new PageInfo<>(roles);
    }

    @Override
    public LayerResult removeUserRole(Integer roleId, Integer[] userIds) {
        if (userIds == null || userIds.length < 1) {
            return LayerResult.FAIL500("请选择要解除关联的用户");
        }

        SysUserRole userRole = new SysUserRole();
        userRole.setRoleId(roleId);
        List<SysUserRole> userRoles = sysUserRoleDao.select(userRole);
        boolean flag = false;
        outer:
        for (SysUserRole var : userRoles) {
            Integer userId = var.getUserId();
            for (Integer var1 : userIds) {
                if (userId.equals(var1)) {
                    flag = true;
                }
                if (flag) {
                    break outer; //一个符合,就表示表里面有符合条件的数据可删,跳出循环
                }
            }
        }

        if (!flag) {
            return LayerResult.FAIL500("请选择已关联的用户");
        }

        Example example = new Example(SysUserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId", roleId);
        criteria.andIn("userId", Arrays.asList(userIds));

        int var = sysUserRoleDao.deleteByExample(example);
        //清除用户已分配权限缓存
        cacheUtil.clearUserAuthority();
        return var > 0 ? LayerResult.SUCCESS() : LayerResult.FAIL();
    }

    @Override
    public SysRole selectById(Integer id) {
        return sysRoleDao.selectByPrimaryKey(id);
    }
}
