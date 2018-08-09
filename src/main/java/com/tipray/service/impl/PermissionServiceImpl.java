package com.tipray.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tipray.bean.Permission;
import com.tipray.dao.PermissionDao;
import com.tipray.service.PermissionService;
import com.tipray.util.StringUtil;

@Transactional
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

	@Resource
	private PermissionDao permissionDao;
	
	@Override
	public Permission addPermission(Permission permission){
		if(permission!=null){
			permissionDao.add(permission);
		}
		return permission;
	}

	@Override
	public Permission updatePermission(Permission permission){
		if(permission!=null){
			permissionDao.update(permission);
		}
		return permission;
	}

	@Override
	public Permission getPermissionById(Long id){
		if(id==null){
			return null;
		}
		return permissionDao.getById(id);
	}

	@Override
	public List<Permission> findAllPermissions(){
		return permissionDao.findAll();
	}

	@Override
	public Permission getPermissionByEname(String ename) {
		if(StringUtil.isNotEmpty(ename)){
			return permissionDao.getByEname(ename);
		}
		return null;
	}

	@Override
	public List<Permission> findPermissionsByIds(String ids) {
		if(StringUtil.isEmpty(ids)){
			return null;
		}
		if(ids.endsWith(",")){
			ids = ids.substring(0, ids.length()-1);
		}
		return permissionDao.findByIds(ids);
	}
}
