package com.blog.service.impl.authority;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.blog.Constants;
import com.blog.dao.authority.ISysLoginLogDao;
import com.blog.dao.authority.ISysUserDao;
import com.blog.entity.authority.SysAuthority;
import com.blog.entity.authority.SysLoginLog;
import com.blog.entity.authority.SysUser;
import com.blog.helper.LayerResult;
import com.blog.security.MD5Utils;
import com.blog.service.authority.ISysAuthorityService;
import com.blog.service.authority.ISysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import eu.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysUserServiceImpl implements ISysUserService {

    private final Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private ISysUserDao sysUserDao;
    @Reference
    private ISysAuthorityService sysAuthorityService;
    @Autowired
    private ISysLoginLogDao sysLoginLogDao;

    @Override
    public List<SysUser> selectAll(SysUser sysUser) {
        return sysUserDao.select(sysUser);
    }

    @Override
    public LayerResult insertOrUpdateUser(SysUser user) {
        if (user == null){
            return LayerResult.FAIL500("用户数据为空");
        }
        Date date = new Date();
        user.setUpdateTime(date);
        int var;
        if (user.getId() != null){
            var = sysUserDao.updateByPrimaryKeySelective(user);
        }else {
            user.setCreateTime(date);
            user.setPassword(MD5Utils.md5(Constants.DEFAULT_PASSWORD)); //默认密码
            var = sysUserDao.insertSelective(user);
        }
        return var > 0 ? LayerResult.SUCCESS() : LayerResult.FAIL();
    }

    @Override
    public LayerResult updateStatus(Integer status, Integer id) {
        if (status == null || id == null){
            return LayerResult.FAIL();
        }
        SysUser user = sysUserDao.selectByPrimaryKey(id);
        if (status.equals(user.getStatus())){
            logger.error("[SysUserServiceImpl][updateStatus]修改状态,状态已是要修改后的状态;ID为:"+id);
            return LayerResult.FAIL500("数据异常,请重试");
        }
        user = new SysUser();
        user.setStatus(status);
        user.setId(id);
        int var = sysUserDao.updateByPrimaryKeySelective(user);
        return var > 0 ? LayerResult.SUCCESS() : LayerResult.FAIL();
    }

    @Override
    public List<SysUser> selectAllIsHasRole(SysUser user, Integer roleId) {
        user.setRoleId(roleId);
        return sysUserDao.selectAllIsHasRole(user);
    }

    @Override
    public Map<String,Object> login(SysUser temp, UserAgent userAgent, String ip) {

        Map<String,Object> map = new HashMap<>();
        //Layer返回
        LayerResult result = LayerResult.FAIL500("");
        if (temp == null){
            result.setMsg("登录信息为空");
        }

        Date now = new Date();
        //登录记录
        SysLoginLog loginLog = new SysLoginLog();
        loginLog.setBrowser(userAgent.getBrowser().getName());
        loginLog.setIp(ip);
        loginLog.setOs(userAgent.getOperatingSystem().getName());
        loginLog.setCreateTime(now);


        SysUser user = this.selectByUsernameAndPassword(temp.getUsername(),temp.getPassword());
        String msg ;
        if (user != null){
            loginLog.setUserId(user.getId());
            if (Constants.COMMON_STATUS_1.equals(user.getStatus())){
                //获取权限信息
                List<SysAuthority> authorities = sysAuthorityService.selectMenuAuthority(user.getId());
                if (authorities != null && !authorities.isEmpty()){
                    msg = "登录成功";
                    result.setResult(200);
                    map.put(Constants.LOGIN_SESSION,user);
                    map.put(Constants.AUTHORITY_SESSION,authorities);

                    SysUser var1 = new SysUser();
                    var1.setId(user.getId());
                    var1.setLastLoginTime(new Date());
                    var1.setLoginNum(user.getLoginNum()==null?0:user.getLoginNum()+1);
                    sysUserDao.updateByPrimaryKeySelective(var1);
                }else{
                    msg = "该用户无权限";
                }
            }else{
                msg = "该用户被禁用";
            }
        }else {
            msg = "用户名密码错误";
        }
        loginLog.setRemark(msg);
        result.setMsg(msg);
        sysLoginLogDao.insert(loginLog);
        map.put("layer",result);
        return map;
    }

    @Override
    public SysUser selectByUsernameAndPassword(String username, String password) {
        SysUser sysUser=new SysUser();
        sysUser.setUsername(username);
        sysUser.setPassword(MD5Utils.md5(password));
        return sysUserDao.selectOne(sysUser);
    }

    @Override
    public PageInfo<SysUser> pageInfo(SysUser sysUser) {
        PageHelper.startPage(sysUser.getPageNum(),sysUser.getPageSize());
        List<SysUser> users = sysUserDao.selectUserList(sysUser);
        PageInfo<SysUser> pageInfo =  new PageInfo<>(users);
        return pageInfo;
    }

    @Override
    public LayerResult resetPassword(Integer id) {
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setPassword(MD5Utils.md5(Constants.DEFAULT_PASSWORD));
        sysUser.setUpdateTime(new Date());
        int var = sysUserDao.updateByPrimaryKeySelective(sysUser);
        return var > 0 ? LayerResult.SUCCESS() : LayerResult.FAIL();
    }

    @Override
    public SysUser selectById(Integer id) {
        return sysUserDao.selectByPrimaryKey(id);
    }
}
