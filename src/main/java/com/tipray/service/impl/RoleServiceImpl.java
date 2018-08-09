package com.tipray.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tipray.bean.GridPage;
import com.tipray.bean.Page;
import com.tipray.bean.Role;
import com.tipray.core.exception.ServiceException;
import com.tipray.dao.RoleDao;
import com.tipray.service.PermissionService;
import com.tipray.service.RoleService;
import com.tipray.util.StringUtil;

@Transactional
@Service("roleService")
public class RoleServiceImpl implements RoleService {
	@Resource
	private RoleDao roleDao;
	@Autowired
	private PermissionService permissionService;

	@Override
	public Role addRole(Role bean) throws ServiceException {
		if (bean != null) {
			roleDao.add(bean);
		}
		return bean;
	}

	@Override
	public Role updateRole(Role bean) throws ServiceException {
		if (bean != null) {
			roleDao.update(bean);
		}
		return bean;
	}

	@Override
	public void deleteRolesByIds(String ids) throws ServiceException {
		if (ids != null) {
			for (String id : ids.split(",")) {
				Long roleId = Long.parseLong(id);
				roleDao.delete(roleId);
			}
		}
	}

	@Override
	public Role getRoleById(Long id) {
		if (id == null) {
			return null;
		}
		Role role = roleDao.getById(id);
		role.setPermissions(permissionService.findPermissionsByIds(role.getPermissionIds()));
		return role;
	}

	@Override
	public List<Role> findAllRoles() {
		List<Role> roles = roleDao.findAll();
		for (Role role : roles) {
			role.setPermissions(permissionService.findPermissionsByIds(role.getPermissionIds()));
		}
		return roles;
	}

	@Override
	public GridPage<Role> findRolesForPage(Role searchVo, Page page) {
		List<Role> list = roleDao.findByVo(searchVo, page);
		return new GridPage<>(list, findAllRoles().size(), page.getPageId(), page.getRows(), list.size(), searchVo);
	}

	@Override
	public Role getRoleByName(String name) {
		if (StringUtil.isNotEmpty(name)) {
			Role role = roleDao.getByName(name);
			role.setPermissions(permissionService.findPermissionsByIds(role.getPermissionIds()));
			return role;
		}
		return null;
	}

}
