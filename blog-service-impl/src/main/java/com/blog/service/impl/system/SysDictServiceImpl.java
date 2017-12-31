package com.blog.service.impl.system;

import com.alibaba.dubbo.config.annotation.Service;
import com.blog.dao.system.ISysDictDao;
import com.blog.entity.system.SysDict;
import com.blog.helper.LayerResult;
import com.blog.service.system.ISysDictService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 数据字典
 *
 * @author lijinzhou
 * @date 2017/9/28 21:56
 */
@Service
public class SysDictServiceImpl implements ISysDictService {

    private final Logger logger = LoggerFactory.getLogger(SysDictServiceImpl.class);

    @Autowired
    private ISysDictDao sysDictDao;

    @Override
    public List<SysDict> selectAllByEntity(SysDict sysDict) {
        return sysDictDao.select(sysDict);
    }

    @Override
    public PageInfo<SysDict> pageInfo(SysDict sysDict) {
        PageHelper.startPage(sysDict.getPageNum(), sysDict.getPageSize());
        List<SysDict> sysDictList = sysDictDao.selectSysDictList(sysDict);
        PageInfo<SysDict> pageInfo = new PageInfo<>(sysDictList);
        return pageInfo;
    }

    @Override
    public SysDict selectById(Integer id) {
        return sysDictDao.selectByPrimaryKey(id);
    }

    @Override
    public LayerResult insertOrUpdate(SysDict sysDict) {
        if (sysDict == null) {
            return LayerResult.FAIL500("要插入的数据字典数据为空");
        }
        Date date = new Date();
        sysDict.setUpdateTime(date);
        int var;
        if (sysDict.getId() != null) {
            var = sysDictDao.updateByPrimaryKeySelective(sysDict);
        } else {
            sysDict.setCreateTime(date);
            var = sysDictDao.insertSelective(sysDict);
        }
        return var > 0 ? LayerResult.SUCCESS() : LayerResult.FAIL();
    }

    @Override
    public LayerResult updateStatus(Integer status, Integer id) {
        if (status == null || id == null) {
            return LayerResult.FAIL();
        }
        SysDict sysDict = sysDictDao.selectByPrimaryKey(id);
        if (status.equals(sysDict.getStatus())) {
            logger.error("[SysDictServiceImpl][updateStatus]修改状态,状态已是要修改后的状态;ID为:" + id);
            return LayerResult.FAIL500("数据异常,请重试");
        }
        sysDict = new SysDict();
        sysDict.setStatus(status);
        sysDict.setId(id);
        int var = sysDictDao.updateByPrimaryKeySelective(sysDict);
        return var > 0 ? LayerResult.SUCCESS() : LayerResult.FAIL();
    }

}
