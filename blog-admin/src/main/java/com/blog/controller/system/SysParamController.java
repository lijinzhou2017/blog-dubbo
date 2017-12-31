package com.blog.controller.system;

import com.alibaba.dubbo.config.annotation.Reference;
import com.blog.annotation.AuthorityFilter;
import com.blog.entity.system.SysParam;
import com.blog.error.ErrorEnum;
import com.blog.error.ViewException;
import com.blog.helper.BootstrapTableEntity;
import com.blog.helper.LayerResult;
import com.blog.service.system.ISysParamService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * 系统参数
 *
 * @author lijinzhou
 * @date 2017/9/28 21:48
 */
@Controller
@RequestMapping("/param")
public class SysParamController {

    @Reference
    private ISysParamService sysParamService;


    /**
     * 跳转系统参数列表页
     * @author lijinzhou
     * @date 2017/9/28 23:22
     */
    @AuthorityFilter(code = "PARAM_MANAGE")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list() {
        return "/system/paramList";
    }

    /**
     * 异步获取系统参数数据
     * @author lijinzhou
     * @date 2017/9/29 0:00
     */
    @AuthorityFilter(code = "PARAM_AJAX")
    @RequestMapping(value = "/ajaxParam", method = RequestMethod.POST)
    @ResponseBody
    public BootstrapTableEntity<SysParam> ajaxParam(SysParam sysParam) {
        PageInfo<SysParam> pageInfo =  sysParamService.pageInfo(sysParam);
        BootstrapTableEntity<SysParam> entity = new BootstrapTableEntity<>(pageInfo);
        return entity;
    }


    /**
     * 跳转至新增或者编辑页面
     * @author lijinzhou
     * @since 2017/9/29 11:19
     */
    @AuthorityFilter(code = "PARAM_ADD_EDIT_TO")
    @RequestMapping(value = "/addOrEditParam/{id}",method = RequestMethod.GET)
    public String toAddOrEditParam(@PathVariable Integer id, Model model) throws ViewException {
        SysParam sysParam= new SysParam();
        if (id != null && id > 0){
            sysParam = sysParamService.selectById(id);
            if (sysParam == null){
                throw new ViewException(ErrorEnum.EXCEPTION404);
            }
        }
        model.addAttribute("sysParam",sysParam);
        return "/system/addOrEditParam";
    }

    /**
     * 新增编辑
     * @author lijinzhou
     * @since 2017/9/29 15:22
     */
    @AuthorityFilter(code = "PARAM_ADD_EDIT")
    @RequestMapping(value = "/addOrEditParam",method = RequestMethod.POST)
    @ResponseBody
    public LayerResult addOrEditParam(@Valid SysParam sysParam, BindingResult result){
        if (result.hasErrors()){
            return LayerResult.FAIL500(result.getFieldError().getDefaultMessage());
        }
        return sysParamService.insertOrUpdate(sysParam);
    }

    /**
     * 启用/禁用
     * @author lijinzhou
     * @since 2017/9/29 20:16
     */
    @AuthorityFilter(code = "PARAM_STATUS")
    @RequestMapping(value = "/status/{status}/{id}",method = RequestMethod.POST)
    @ResponseBody
    public LayerResult updateStatus(@PathVariable Integer status, @PathVariable Integer id){
        return sysParamService.updateStatus(status,id);
    }

}
