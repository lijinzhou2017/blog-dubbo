package com.blog.controller.authority;

import com.alibaba.dubbo.config.annotation.Reference;
import com.blog.annotation.AuthorityFilter;
import com.blog.entity.authority.SysUser;
import com.blog.error.ErrorEnum;
import com.blog.error.ViewException;
import com.blog.helper.BootstrapTableEntity;
import com.blog.helper.LayerResult;
import com.blog.service.authority.ISysUserService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户
 *
 * @author lijinzhou
 * @since 2017/12/24 20:14
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Reference
    private ISysUserService sysUserService;

    /**
     * 后台用户列表页
     */
    @AuthorityFilter(code = "USER_MANAGE")
    @GetMapping(value = "")
    public String list() {
        return "authority/userList";
    }

    /**
     * 异步获取用户数据
     */
    @AuthorityFilter(code = "USER_AJAX")
    @PostMapping(value = "/ajaxUser")
    @ResponseBody
    public BootstrapTableEntity<SysUser> ajaxUser(SysUser user) {
        PageInfo<SysUser> pageInfo = sysUserService.pageInfo(user);
        BootstrapTableEntity<SysUser> entity = new BootstrapTableEntity<>(pageInfo);
        return entity;
    }

    /**
     * 跳转至新增或者编辑用户页面
     */
    @AuthorityFilter(code = "USER_ADD_EDIT_TO")
    @RequestMapping(value = "/addOrEditUser/{id}", method = RequestMethod.GET)
    public String toAddOrEditAuthority(@PathVariable Integer id, Model model) throws ViewException {
        SysUser sysUser = new SysUser();
        if (id != null && id > 0) {
            sysUser = sysUserService.selectById(id);
            if (sysUser == null) {
                throw new ViewException(ErrorEnum.EXCEPTION404);
            }
        }
        model.addAttribute("user", sysUser);
        return "authority/addOrEditUser";
    }

    /**
     * 用户新增编辑
     */
    @AuthorityFilter(code = "USER_ADD_EDIT")
    @RequestMapping(value = "/addOrEditUser", method = RequestMethod.POST)
    @ResponseBody
    public LayerResult addOrEditUser(@Valid SysUser user, BindingResult result) {
        if (result.hasErrors()) {
            return LayerResult.FAIL500(result.getFieldError().getDefaultMessage());
        }
        return sysUserService.insertOrUpdateUser(user);
    }

    /**
     * 启用/禁用
     */
    @AuthorityFilter(code = "USER_STATUS")
    @PostMapping(value = "/status/{status}/{id}")
    @ResponseBody
    public LayerResult updateStatus(@PathVariable Integer status, @PathVariable Integer id) {
        return sysUserService.updateStatus(status, id);
    }

    /**
     * 重置用户的密码
     */
    @AuthorityFilter(code = "USER_RESET_PASSWORD")
    @PostMapping(value = "/reset/password/{id}")
    @ResponseBody
    public LayerResult resetPassword(@PathVariable Integer id) {
        return sysUserService.resetPassword(id);
    }
}
