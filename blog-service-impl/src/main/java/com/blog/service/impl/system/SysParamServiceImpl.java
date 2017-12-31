package com.blog.service.impl.system;

import com.alibaba.dubbo.config.annotation.Service;
import com.blog.dao.system.ISysParamDao;
import com.blog.entity.system.SysParam;
import com.blog.helper.LayerResult;
import com.blog.service.system.ISysParamService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 系统参数
 *
 * @author lijinzhou
 * @date 2017/9/28 21:56
 */
@Service
public class SysParamServiceImpl implements ISysParamService {

    private final Logger logger = LoggerFactory.getLogger(SysParamServiceImpl.class);

    @Autowired
    private ISysParamDao sysParamDao;

    @Override
    public List<SysParam> selectAllByEntity(SysParam sysParam) {
        return sysParamDao.select(sysParam);
    }

    @Override
    public PageInfo<SysParam> pageInfo(SysParam sysParam) {
        PageHelper.startPage(sysParam.getPageNum(), sysParam.getPageSize());
        List<SysParam> sysDictList = this.selectAllByEntity(sysParam);
        PageInfo<SysParam> pageInfo = new PageInfo<>(sysDictList);
        return pageInfo;
    }

    @Override
    public SysParam selectById(Integer id) {
        return sysParamDao.selectByPrimaryKey(id);
    }

    @Override
    public LayerResult insertOrUpdate(SysParam sysParam) {
        if (sysParam == null) {
            return LayerResult.FAIL500("系统参数数据为空");
        }
        Date date = new Date();
        sysParam.setUpdateTime(date);
        int var;
        if (sysParam.getId() != null) {
            var = sysParamDao.updateByPrimaryKeySelective(sysParam);
        } else {
            sysParam.setCreateTime(date);
            var = sysParamDao.insertSelective(sysParam);
        }
        return var > 0 ? LayerResult.SUCCESS() : LayerResult.FAIL();
    }

    @Override
    public LayerResult updateStatus(Integer status, Integer id) {
        if (status == null || id == null) {
            return LayerResult.FAIL();
        }
        SysParam sysParam = sysParamDao.selectByPrimaryKey(id);
        if (status.equals(sysParam.getStatus())) {
            logger.error("[SysParamServiceImpl][updateStatus]修改状态,状态已是要修改后的状态;ID为:" + id);
            return LayerResult.FAIL500("数据异常,请重试");
        }
        sysParam = new SysParam();
        sysParam.setStatus(status);
        sysParam.setId(id);
        int var = sysParamDao.updateByPrimaryKeySelective(sysParam);
        return var > 0 ? LayerResult.SUCCESS() : LayerResult.FAIL();
    }

}
