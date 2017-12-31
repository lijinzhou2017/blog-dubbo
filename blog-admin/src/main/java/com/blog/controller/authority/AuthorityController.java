package com.blog.controller.authority;

import com.alibaba.dubbo.config.annotation.Reference;
import com.blog.annotation.AuthorityFilter;
import com.blog.entity.authority.SysAuthority;
import com.blog.error.ErrorEnum;
import com.blog.error.ViewException;
import com.blog.helper.LayerResult;
import com.blog.service.authority.ISysAuthorityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 权限
 *
 * @author lijinzhou
 * @since 2017/12/24 20:04
 */
@Controller
@RequestMapping("/authority")
public class AuthorityController {

    @Reference
    private ISysAuthorityService sysAuthorityService;


    /**
     * 跳转至权限管理页面
     */
    @AuthorityFilter(code = "AUTHORITY_MANAGE")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list() {
        return "authority/authorityList";
    }

    /**
     * 获取全部的权限树
     */
    @AuthorityFilter(code = "AUTHORITY_AJAX")
    @GetMapping(value = "/ajaxAuthorityTree")
    @ResponseBody
    public List<SysAuthority> ajaxAuthorityTree() {
        // 不限状态,类型,层级
        return this.ajaxAuthorityTree(null, null, null);
    }

    /**
     * 根据条件获取权限树
     */
    @AuthorityFilter(code = "AUTHORITY_AJAX")
    @RequestMapping(value = "/ajaxAuthorityTree/{status}", method = RequestMethod.GET)
    @ResponseBody
    public List<SysAuthority> ajaxAuthorityTree(@PathVariable Integer status) {
        return sysAuthorityService.selectAllAuthority(status, null, null);
    }

    /**
     * 根据条件获取权限树
     */
    @AuthorityFilter(code = "AUTHORITY_AJAX")
    @RequestMapping(value = "/ajaxAuthorityTree/{status}/{type}", method = RequestMethod.GET)
    @ResponseBody
    public List<SysAuthority> ajaxAuthorityTree(@PathVariable Integer status, @PathVariable Integer type) {
        return sysAuthorityService.selectAllAuthority(status, type, null);
    }

    /**
     * 根据条件获取权限树
     */
    @AuthorityFilter(code = "AUTHORITY_AJAX")
    @RequestMapping(value = "/ajaxAuthorityTree/{status}/{type}/{level}", method = RequestMethod.GET)
    @ResponseBody
    public List<SysAuthority> ajaxAuthorityTree(@PathVariable Integer status, @PathVariable Integer type, @PathVariable Integer level) {
        return sysAuthorityService.selectAllAuthority(status, type, level);
    }

    /**
     * 跳转至新增或者编辑权限页面
     */
    @AuthorityFilter(code = "AUTHORITY_ADD_EDIT_TO")
    @RequestMapping(value = "/addOrEditAuthority/{id}", method = RequestMethod.GET)
    public String toAddOrEditAuthority(@PathVariable Integer id, Model model) throws ViewException {
        SysAuthority authority = new SysAuthority();
        if (id != null && id > 0) {
            authority = sysAuthorityService.selectById(id);
            if (authority == null) {
                throw new ViewException(ErrorEnum.EXCEPTION404);
            }
        }
        model.addAttribute("authority", authority);
        return "authority/addOrEditAuthority";
    }

    /**
     * 新增或者修改权限
     */
    @AuthorityFilter(code = "AUTHORITY_ADD_EDIT")
    @RequestMapping(value = "/addOrEditAuthority", method = RequestMethod.POST)
    @ResponseBody
    public LayerResult addOrEditAuthority(@Valid SysAuthority authority, BindingResult result) {
        if (result.hasErrors()) {
            return LayerResult.FAIL500(result.getFieldError().getDefaultMessage());
        }
        return sysAuthorityService.insertOrUpdateAuthority(authority);
    }

    /**
     * 启用/禁用
     */
    @AuthorityFilter(code = "AUTHORITY_STATUS")
    @RequestMapping(value = "/status/{status}/{id}", method = RequestMethod.POST)
    @ResponseBody
    public LayerResult updateStatus(@PathVariable Integer status, @PathVariable Integer id) {
        return sysAuthorityService.updateStatus(status, id);
    }
}
