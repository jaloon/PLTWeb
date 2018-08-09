package com.tipray.service;

import com.tipray.bean.GridPage;
import com.tipray.bean.Page;
import com.tipray.bean.Role;
import com.tipray.core.exception.ServiceException;

import java.util.List;

public interface RoleService {
	/**
	 * 新增角色
	 * @param bean
	 * @throws ServiceException 
	 */
	Role addRole(Role bean) throws ServiceException;
	/**
	 * 修改角色
	 * @param bean
	 */
	Role updateRole(Role bean) throws ServiceException;
	/**
	 * 根据Id删除角色
	 * @param ids
	 */
	void deleteRolesByIds(String ids) throws ServiceException;
	/**
	 * 根据Id获取角色
	 * @param id
	 * @return
	 */
	Role getRoleById(Long id);
	
	/**
	 * 获取所有的角色
	 * @param id
	 * @return
	 */
	List<Role> findAllRoles();
	/**
	 * 分页查询角色集合
	 * @param role
	 * @param pageVo
	 * @return
	 */
	GridPage<Role> findRolesForPage(Role searchVo, Page pageVo);
	/**
	 * 根据角色名称获取角色
	 * @param name
	 * @return
	 */
	Role getRoleByName(String name);
}
