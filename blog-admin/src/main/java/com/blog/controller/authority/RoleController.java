package com.blog.controller.authority;

import com.alibaba.dubbo.config.annotation.Reference;
import com.blog.annotation.AuthorityFilter;
import com.blog.entity.authority.SysRole;
import com.blog.entity.authority.SysUser;
import com.blog.error.ErrorEnum;
import com.blog.error.ViewException;
import com.blog.helper.BootstrapTableEntity;
import com.blog.helper.LayerResult;
import com.blog.service.authority.ISysRoleAuthorityService;
import com.blog.service.authority.ISysRoleService;
import com.blog.service.authority.ISysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 角色
 *
 * @author lijinzhou
 * @since 2017/12/24 20:04
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Reference
    private ISysRoleService sysRoleService;
    @Reference
    private ISysRoleAuthorityService sysRoleAuthorityService;
    @Reference
    private ISysUserService sysUserService;

    /**
     * 角色管理页面
     */
    @AuthorityFilter(code = "ROLE_MANAGE")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list() {
        return "authority/roleList";
    }

    /**
     * ajax获取
     */
    @AuthorityFilter(code = "ROLE_AJAX")
    @RequestMapping(value = "/ajaxRole", method = RequestMethod.POST)
    @ResponseBody
    public BootstrapTableEntity<SysRole> ajaxRole(SysRole sysRole) {
        PageInfo<SysRole> pageInfo = sysRoleService.pageInfo(sysRole);
        BootstrapTableEntity<SysRole> entity = new BootstrapTableEntity<>(pageInfo);
        return entity;
    }


    /**
     * 跳转至新增或者编辑角色页面
     */
    @AuthorityFilter(code = "ROLE_ADD_EDIT_TO")
    @GetMapping(value = "/addOrEditRole/{id}")
    public String toAddOrEditRole(@PathVariable Integer id, Model model) throws ViewException {
        SysRole sysRole = new SysRole();
        if (id != null && id > 0) {
            sysRole = sysRoleService.selectById(id);
            if (sysRole == null) {
                throw new ViewException(ErrorEnum.EXCEPTION404);
            }
        }
        model.addAttribute("role", sysRole);
        return "authority/addOrEditRole";
    }

    /**
     * 新增或者编辑
     */
    @AuthorityFilter(code = "ROLE_ADD_EDIT")
    @RequestMapping(value = "/addOrEditRole", method = RequestMethod.POST)
    @ResponseBody
    public LayerResult addOrEditRole(@Valid SysRole role, BindingResult result) {
        if (result.hasErrors()) {
            return LayerResult.FAIL500(result.getFieldError().getDefaultMessage());
        }
        return sysRoleService.insertOrUpdateRole(role);
    }

    /**
     * 启用/禁用
     */
    @AuthorityFilter(code = "ROLE_STATUS")
    @RequestMapping(value = "/status/{status}/{id}", method = RequestMethod.POST)
    @ResponseBody
    public LayerResult updateStatus(@PathVariable Integer status, @PathVariable Integer id) {
        return sysRoleService.updateStatus(status, id);
    }

    /**
     * 跳转角色分配权限页面
     */
    @AuthorityFilter(code = "ROLE_ALLOT_AUTHORITY_TO")
    @RequestMapping(value = "/allotAuthority/{roleId}", method = RequestMethod.GET)
    public String allotRole(@PathVariable Integer roleId, Model model) throws ViewException {
        String authorityIds = sysRoleAuthorityService.selectAuthorityIdsByRole(roleId);
        if (StringUtils.isBlank(authorityIds)) {
            authorityIds = "";
        }
        model.addAttribute("authorityIds", authorityIds);
        SysRole role = sysRoleService.selectById(roleId);
        model.addAttribute("role", role);
        return "authority/allotAuthority";
    }

    /**
     * 为角色分配权限
     */
    @AuthorityFilter(code = "ROLE_ALLOT_AUTHORITY")
    @RequestMapping(value = "/allotRoleAuthority/{roleId}", method = RequestMethod.POST)
    @ResponseBody
    public LayerResult allotRolePower(@PathVariable Integer roleId, @RequestParam("authorityIds[]") Integer[] authorityIds) {
        return sysRoleService.allotRoleAuthority(roleId, authorityIds);
    }

    /**
     * 跳转到角色关联用户页面
     */
    @AuthorityFilter(code = "ROLE_ALLOT_USER_TO")
    @RequestMapping(value = "/allotUser/{roleId}", method = RequestMethod.GET)
    public String allotUser(@PathVariable Integer roleId, Model model) throws ViewException {
        SysRole role = sysRoleService.selectById(roleId);
        if (role == null) {
            throw new ViewException(ErrorEnum.EXCEPTION404);
        }
        model.addAttribute("role", role);
        return "authority/allotUser";
    }

    /**
     * 角色关联用户table,加载数据
     */
    @AuthorityFilter(code = "ROLE_USER_AJAX")
    @RequestMapping(value = "/ajaxUser/{roleId}", method = RequestMethod.POST)
    @ResponseBody
    public BootstrapTableEntity<SysUser> ajaxUser(@PathVariable Integer roleId, SysUser user) {
        PageHelper.startPage(user.getPageNum(), user.getPageSize());
        List<SysUser> users = sysUserService.selectAllIsHasRole(user, roleId);
        PageInfo<SysUser> pageInfo = new PageInfo<>(users);
        BootstrapTableEntity<SysUser> entity = new BootstrapTableEntity<>(pageInfo);
        return entity;
    }

    /**
     * 角色关联用户
     */
    @AuthorityFilter(code = "ROLE_ALLOT_USER")
    @RequestMapping(value = "/allotUserRole/{roleId}", method = RequestMethod.POST)
    @ResponseBody
    public LayerResult allotUserRole(@PathVariable Integer roleId, @RequestParam("userIds[]") Integer[] userIds) {
        return sysRoleService.allotUserRole(roleId, userIds);
    }

    /**
     * 角色关联用户
     */
    @AuthorityFilter(code = "ROLE_REMOVE_USER")
    @RequestMapping(value = "/removeUserRole/{roleId}", method = RequestMethod.POST)
    @ResponseBody
    public LayerResult removeUserRole(@PathVariable Integer roleId, @RequestParam("userIds[]") Integer[] userIds) {
        return sysRoleService.removeUserRole(roleId, userIds);
    }
}
