package com.tipray.service;

import com.tipray.bean.Permission;

import java.util.List;

public interface PermissionService {
	/**
	 * 新增权限
	 * @param user
	 * @throws ServiceException 
	 */
	Permission addPermission(Permission permission);
	/**
	 * 修改权限
	 * @param user
	 */
	Permission updatePermission(Permission permission);
	/**
	 * 根据Id获取权限
	 * @param id
	 * @return
	 */
	Permission getPermissionById(Long id);
	/**
	 * 获取所有的权限
	 * @param id
	 * @return
	 */
	List<Permission> findAllPermissions();
	/**
	 * 根据权限名称获取权限
	 * @param string
	 * @return
	 */
	Permission getPermissionByEname(String string);
	/**
	 * 根据ID集合获取权限
	 * @param ids
	 * @return
	 */
	List<Permission> findPermissionsByIds(String ids);
}
