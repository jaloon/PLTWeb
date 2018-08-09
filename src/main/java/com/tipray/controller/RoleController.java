package com.tipray.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tipray.bean.GridPage;
import com.tipray.bean.Message;
import com.tipray.bean.Mode;
import com.tipray.bean.Page;
import com.tipray.bean.Permission;
import com.tipray.bean.Role;
import com.tipray.constant.LogDescriptionEnum;
import com.tipray.core.ThreadVariable;
import com.tipray.core.annotation.LogAnno;
import com.tipray.core.annotation.PermissionAnno;
import com.tipray.core.base.BaseAction;
import com.tipray.core.exception.ServiceException;
import com.tipray.service.PermissionService;
import com.tipray.service.RoleService;
import com.tipray.util.PermissionUtil;

@Controller
@RequestMapping("/manage/role")
@Scope("prototype")
public class RoleController extends BaseAction {
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;

	@PermissionAnno("roleModule")
	@RequestMapping(value = "dispatch.do")
	public String dispatch(String mode, Role role, ModelMap modelMap) {
		if (Mode.EDIT.equals(mode) || Mode.VIEW.equals(mode)) {
			role = roleService.getRoleById(role.getId());
		}
		modelMap.put("mode", mode);
		modelMap.put("role", role);
		return "normal/role/roleEditDlg.jsp";
	}

	@LogAnno(LogDescriptionEnum.ROLE_ADD)
	@PermissionAnno("roleModule")
	@RequestMapping(value = "add.do", method = RequestMethod.POST)
	@ResponseBody
	public Message addRole(@ModelAttribute Role role) {
		try {
			role.setIsSuper(false);
			roleService.addRole(role);
			return Message.success();
		} catch (ServiceException e) {
			return Message.error(e);
		}
	}

	@LogAnno(LogDescriptionEnum.ROLE_ALTER)
	@PermissionAnno("roleModule")
	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	@ResponseBody
	public Message updateRole(@ModelAttribute Role role) {
		try {
			roleService.updateRole(role);
			return Message.success();
		} catch (ServiceException e) {
			return Message.error(e);
		}
	}

	@LogAnno(LogDescriptionEnum.ROLE_DELETE)
	@PermissionAnno("roleModule")
	@RequestMapping(value = "delete.do", method = RequestMethod.POST)
	@ResponseBody
	public Message deleteRoles(String ids) {
		try {
			roleService.deleteRolesByIds(ids);
			return Message.success();
		} catch (ServiceException e) {
			return Message.error(e);
		}
	}

	@RequestMapping("isExist.do")
	@ResponseBody
	public Boolean isRoleExist(@ModelAttribute Role role) {
		Role bean = roleService.getRoleByName(role.getName());
		return bean != null && role.getId() != bean.getId();
	}

	@PermissionAnno("roleModule")
	@RequestMapping(value = "findForPage.do")
	@ResponseBody
	public GridPage<Role> findRolesForPage(@ModelAttribute Role role, @ModelAttribute Page page) {
		return roleService.findRolesForPage(role, page);
	}

	@RequestMapping(value = "findPermissions.do")
	@ResponseBody
	public List<Permission> findPermissions(String mode, Long roleId) {
		Role role = roleService.getRoleById(roleId);
		String permissions = role != null ? role.getPermissionIds() : null;
		List<Permission> list = permissionService.findPermissionsByIds(permissions);
		if (Mode.VIEW.equals(mode)) {
			PermissionUtil.check(list);
		} else {
			list = PermissionUtil.replace(ThreadVariable.getPermissions(), list);
		}
		return PermissionUtil.toTree(list, null, null);
	}

}
