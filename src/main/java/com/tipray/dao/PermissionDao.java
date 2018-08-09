/*
 * PermissionDao.java
 * Copyright(C) 厦门天锐科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2016-01-11 Created
 */
package com.tipray.dao;

import com.tipray.bean.Permission;
import com.tipray.core.annotation.MyBatisAnno;
import com.tipray.core.base.BaseDao;

import java.util.List;

@MyBatisAnno
public interface PermissionDao extends BaseDao<Permission>{
	/**
	 * 根据权限名称获取权限
	 * @param ename
	 * @return
	 */
	Permission getByEname(String ename);
	
	/**
	 * 根据id集合获取权限
	 * @param ids
	 * @return
	 */
	List<Permission> findByIds(String ids);
	
}