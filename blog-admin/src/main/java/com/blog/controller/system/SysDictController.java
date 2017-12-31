package com.blog.controller.system;

import com.alibaba.dubbo.config.annotation.Reference;
import com.blog.annotation.AuthorityFilter;
import com.blog.entity.system.SysDict;
import com.blog.error.ErrorEnum;
import com.blog.error.ViewException;
import com.blog.helper.BootstrapTableEntity;
import com.blog.helper.LayerResult;
import com.blog.service.system.ISysDictService;
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
 * 数据字典
 * @author lijinzhou
 * @date 2017/9/28 21:48
 */
@Controller
@RequestMapping("/dict")
public class SysDictController{

    @Reference
    private ISysDictService sysDictService;

    /**
     * 跳转数据字典列表页
     * @author lijinzhou
     * @date 2017/9/28 23:22
     */
    @AuthorityFilter(code = "DICT_MANAGE")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public String list(){
        return "/system/dictList";
    }

    /**
     * 异步获取数据
     * @author lijinzhou
     * @date 2017/9/30 0:00
     */
    @AuthorityFilter(code = "DICT_AJAX")
    @RequestMapping(value = "/ajaxDict", method = RequestMethod.POST)
    @ResponseBody
    public BootstrapTableEntity<SysDict> ajaxDictCode(SysDict sysDict) {
        PageInfo<SysDict> pageInfo =  sysDictService.pageInfo(sysDict);
        BootstrapTableEntity<SysDict> entity = new BootstrapTableEntity<>(pageInfo);
        return entity;
    }


    /**
     * 跳转至新增或者编辑页面
     * @author lijinzhou
     * @since 2017/9/29 11:19
     */
    @AuthorityFilter(code = "DICT_ADD_EDIT_TO")
    @RequestMapping(value = "/addOrEditDict/{id}",method = RequestMethod.GET)
    public String toAddOrEditDict(@PathVariable Integer id, Model model) throws ViewException {
        SysDict sysDict= new SysDict();
        if (id != null && id > 0){
            sysDict = sysDictService.selectById(id);
            if (sysDict == null){
                throw new ViewException(ErrorEnum.EXCEPTION404);
            }
        }
        model.addAttribute("sysDict",sysDict);
        return "/system/addOrEditDict";
    }

    /**
     * 新增编辑
     * @author lijinzhou
     * @since 2017/9/29 15:22
     */
    @AuthorityFilter(code = "DICT_ADD_EDIT")
    @RequestMapping(value = "/addOrEditDict",method = RequestMethod.POST)
    @ResponseBody
    public LayerResult addOrEditDict(@Valid SysDict sysDict, BindingResult result){
        if (result.hasErrors()){
            return LayerResult.FAIL500(result.getFieldError().getDefaultMessage());
        }
        return sysDictService.insertOrUpdate(sysDict);
    }

    /**
     * 启用/禁用
     * @author lijinzhou
     * @since 2017/9/29 20:16
     */
    @AuthorityFilter(code = "DICT_STATUS")
    @RequestMapping(value = "/status/{status}/{id}",method = RequestMethod.POST)
    @ResponseBody
    public LayerResult updateStatus(@PathVariable Integer status, @PathVariable Integer id){
        return sysDictService.updateStatus(status,id);
    }



}
