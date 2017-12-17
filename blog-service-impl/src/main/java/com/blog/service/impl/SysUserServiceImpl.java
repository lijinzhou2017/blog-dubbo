package com.blog.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.blog.dao.ISysUserDao;
import com.blog.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author lijinzhou
 * @since 2017/12/12 19:12
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    ISysUserDao sysUserDao;

    @Override
    public List<Map<String, Object>> getUserAll() {
        return sysUserDao.getUserAll();
    }
}
