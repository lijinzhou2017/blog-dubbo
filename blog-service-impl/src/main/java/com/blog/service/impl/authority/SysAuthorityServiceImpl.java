package com.blog.service.impl.authority;

import com.alibaba.dubbo.config.annotation.Service;
import com.blog.Constants;
import com.blog.cache.CacheUtils;
import com.blog.dao.authority.ISysAuthorityDao;
import com.blog.entity.authority.SysAuthority;
import com.blog.helper.LayerResult;
import com.blog.service.authority.ISysAuthorityService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 权限
 *
 * @author lijinzhou
 * @since 2017/12/23 23:52
 */
@Service
public class SysAuthorityServiceImpl implements ISysAuthorityService {

    private final Logger logger = LoggerFactory.getLogger(SysAuthorityServiceImpl.class);

    @Autowired
    private CacheUtils<SysAuthority> cacheUtil;

    @Autowired
    private ISysAuthorityDao sysAuthorityDao;

    @Override
    public List<SysAuthority> selectAllAuthority(Integer status, Integer type, Integer level) {
        //内存缓存里面查找先
        String key = "AUTHORITY_MANAGE_" + (status == null ? "NULL" : status) + "_" + (type == null ? "NULL" : type) + "_" + (level == null ? "NULL" : level);
        logger.info("[SysAuthorityServiceImpl][selectAllAuthority]key值:" + key);

        if (cacheUtil.getAuthorities(key) != null && !cacheUtil.getAuthorities(key).isEmpty()) {
            logger.info("[SysAuthorityServiceImpl][selectAllAuthority]从内存缓存获取");
            return cacheUtil.getAuthorities().get(key);
        }

        //获取最顶级的权限 父类id为0
        List<SysAuthority> parent = selectAuthorityByParent(0, status, type, level);
        //递归迭代
        List<SysAuthority> treeAuthorities = recursiveTree(parent, status, type, level);
        //扔到缓存中去
        cacheUtil.putAuthorities(key, treeAuthorities);
        logger.info("[SysAuthorityServiceImpl][selectAllAuthority]从库获取");
        return treeAuthorities;
    }


    @Override
    public List<SysAuthority> selectAuthorityByParent(Integer parentId, Integer status, Integer type, Integer level) {
        if (parentId == null) {
            return null;
        }
        SysAuthority sysAuthority = new SysAuthority();
        sysAuthority.setParentId(parentId);
        sysAuthority.setStatus(status);
        sysAuthority.setType(type);
        sysAuthority.setLevel(level);
        return sysAuthorityDao.select(sysAuthority);
    }

    @Override
    public LayerResult insertOrUpdateAuthority(SysAuthority sysAuthority) {
        if (sysAuthority == null) {
            return LayerResult.FAIL500("权限数据为空");
        }
        Date date = new Date();
        sysAuthority.setUpdateTime(date);
        int var;
        boolean isUpdate = false;
        boolean valid = this.validCode(sysAuthority.getCode(), sysAuthority.getId());
        if (valid) {
            return LayerResult.FAIL500("code值已存在");
        }
        if (sysAuthority.getId() != null) {
            //修改
            var = sysAuthorityDao.updateByPrimaryKeySelective(sysAuthority);
            isUpdate = true;
        } else {
            //新增
            sysAuthority.setCreateTime(date);
            var = sysAuthorityDao.insertSelective(sysAuthority);
        }
        if (var > 0) {
            //修改path信息
            String parentPath = "";
            Integer parentId = sysAuthority.getParentId();
            if (parentId != null && parentId > 0) {
                SysAuthority parent = sysAuthorityDao.selectByPrimaryKey(parentId);
                if (parent == null) {
                    return LayerResult.FAIL500("找不到父权限信息");
                }
                parentPath = parent.getPath();
            }
            Integer authorityId = sysAuthority.getId();
            String newPath = StringUtils.isNotBlank(parentPath) ? (parentPath + authorityId + "_") : "_" + authorityId + "_";
            if (isUpdate) {
                //如果是修改,修改当前及其子类的path
                SysAuthority curr = sysAuthorityDao.selectByPrimaryKey(authorityId);
                String currPath = curr.getPath();
                this.updatePath(currPath, newPath);
            } else {
                //如果是新增,只要修改当前新增的path
                SysAuthority var1 = new SysAuthority();
                var1.setPath(newPath);
                var1.setId(authorityId);
                var1.setUpdateTime(new Date());
                sysAuthorityDao.updateByPrimaryKeySelective(var1);
            }
        }
        cacheUtil.clearAuthorities(); //清除缓存
        logger.info("[SysAuthorityServiceImpl][insertOrUpdateAuthority]清除权限内存缓存");
        return LayerResult.SUCCESS();
    }

    @Override
    public LayerResult updateStatus(Integer status, Integer id) {
        if (status == null || id == null) {
            return LayerResult.FAIL();
        }
        SysAuthority sysAuthority = sysAuthorityDao.selectByPrimaryKey(id);
        if (status.equals(sysAuthority.getStatus())) {
            logger.error("[SysAuthorityServiceImpl][updateStatus]修改状态,状态已是要修改后的状态;ID为:" + id);
            return LayerResult.FAIL500("该数据已是对应状态");
        }
        sysAuthority = new SysAuthority();
        sysAuthority.setStatus(status);
        sysAuthority.setId(id);
        int var = sysAuthorityDao.updateByPrimaryKeySelective(sysAuthority);
        cacheUtil.clearAuthorities(); //清除缓存
        return var > 0 ? LayerResult.SUCCESS() : LayerResult.FAIL();
    }

    /**
     * 修改当前及其下级的path
     */
    @Override
    public Integer updatePath(String currPath, String newPath) {
        int subIndex = currPath.length() + 1;
        return sysAuthorityDao.updatePath(currPath, newPath, subIndex);
    }

    @Override
    public List<SysAuthority> selectMenuAuthority(Integer userId) {
        return this.selectUserAuthority(userId, Constants.COMMON_STATUS_1, Constants.TYPE_MENU, 0);
    }

    @Override
    public List<SysAuthority> selectUserAuthority(Integer userId, Integer status, Integer type, Integer parentId) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("parentId", parentId);
        map.put("userId", userId);
        map.put("type", type);
        List<SysAuthority> parentAuthorities = sysAuthorityDao.selectUserAuthority(map);
        if (parentAuthorities != null && !parentAuthorities.isEmpty()) {
            return recursiveTreeByUser(parentAuthorities, type, userId);
        }
        return null;
    }

    @Override
    public boolean isHasAuthority(Integer userId, String code) {
        Set<String> userAuthorities = cacheUtil.getUserAuthority(userId);
        if (userAuthorities == null) {
            List<String> urls = this.selectCodeByUser(userId, Constants.COMMON_STATUS_1);
            Set<String> var = new HashSet<>();
            for (String temp : urls) {
                var.add(temp);
            }
            userAuthorities = var;
            cacheUtil.putUserAuthority(userId, userAuthorities);
        }
        return userAuthorities.contains(code);
    }

    @Override
    public List<String> selectCodeByUser(Integer userId, Integer status) {
        return sysAuthorityDao.selectCodeByUser(userId, status);
    }

    @Override
    public SysAuthority selectById(Integer id) {
        return sysAuthorityDao.selectByPrimaryKey(id);
    }

    private List<SysAuthority> recursiveTree(List<SysAuthority> parentList, Integer status, Integer type, Integer level) {
        //构造成 bootstrap tree view格式的数据
        parentList = this.constructionBootstrapTreeViewData(parentList);
        for (int i = 0; i < parentList.size(); i++) {
            SysAuthority parent = parentList.get(i);
            if (parent != null) {
                List<SysAuthority> parentTemp = this.selectAuthorityByParent(parent.getId(), status, type, level);
                if (parentTemp != null && !parentTemp.isEmpty()) {
                    parent.setNodes(parentTemp);
                    recursiveTree(parentTemp, status, type, level);
                }
            }
        }
        return parentList;

    }

    private List<SysAuthority> constructionBootstrapTreeViewData(List<SysAuthority> parentList) {
        if (parentList == null || parentList.isEmpty()) {
            return parentList;
        }
        for (int i = 0; i < parentList.size(); i++) {
            SysAuthority authority = parentList.get(i);
            authority.setText(authority.getName());
        }
        return parentList;
    }

    /**
     * 验证code是否存在
     */
    private boolean validCode(String code, Integer authorityId) {
        SysAuthority valid = new SysAuthority();
        valid.setCode(code);
        SysAuthority var = sysAuthorityDao.selectOne(valid);
        if (var == null) {
            return false;
        }
        if (authorityId != null) {
            return !authorityId.equals(var.getId());
        }
        return true;
    }

    /**
     * 递归 缔造树结构权限数据(加入用户)---菜单
     */
    private List<SysAuthority> recursiveTreeByUser(List<SysAuthority> parentList, Integer type, Integer userId) {
        for (int i = 0; i < parentList.size(); i++) {
            SysAuthority teamp0 = parentList.get(i);
            if (teamp0 != null) {
                List<SysAuthority> parentTemp = this.selectUserAuthority(userId, Constants.COMMON_STATUS_1, type, teamp0.getId());
                if (parentTemp != null && !parentTemp.isEmpty()) {
                    teamp0.setNodes(parentTemp);
                    recursiveTreeByUser(parentTemp, type, userId);
                }
            }
        }
        return parentList;
    }
}
