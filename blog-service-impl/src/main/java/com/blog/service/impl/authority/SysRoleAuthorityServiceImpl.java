package com.blog.service.impl.authority;

import com.alibaba.dubbo.config.annotation.Service;
import com.blog.dao.authority.ISysRoleAuthorityDao;
import com.blog.error.ErrorEnum;
import com.blog.error.ViewException;
import com.blog.service.authority.ISysRoleAuthorityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 角色-权限
 *
 * @author lijinzhou
 * @since 2017/12/23 22:23
 */
@Service
public class SysRoleAuthorityServiceImpl  implements ISysRoleAuthorityService {

    private final Logger logger = LoggerFactory.getLogger(SysRoleAuthorityServiceImpl.class);

    @Autowired
    private ISysRoleAuthorityDao sysRoleAuthorityDao;

    @Override
    public String selectAuthorityIdsByRole(Integer roleId) throws ViewException {
        if (roleId == null || roleId < 1) {
            throw new ViewException(ErrorEnum.EXCEPTION403);
        }
        return sysRoleAuthorityDao.selectAuthorityIdsByRole(roleId);
    }
}
