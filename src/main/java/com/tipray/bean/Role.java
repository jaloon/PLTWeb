/*
 * Role.java
 * Copyright(C) 厦门天锐科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2016-01-13 Created
 */
package com.tipray.bean;

import java.util.List;

import com.tipray.core.base.BaseBean;

/**
 * 角色表
 * 
 * @author chends
 * @version 1.0 2016-01-13
 */
public class Role extends BaseBean {
	private static final long serialVersionUID = 1L;
	/** 角色名称 */
	private String name;
	/** 是否超级管理员{1:是,0:否} */
	private Boolean isSuper;
	/** 功能权限，权限Id集合，多个权限用逗号相隔 */
	private String permissionIds;
	private List<Permission> permissions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Boolean getIsSuper() {
		return isSuper;
	}

	public void setIsSuper(Boolean isSuper) {
		this.isSuper = isSuper;
	}

	public String getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(String permissionIds) {
		this.permissionIds = permissionIds;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("Role: {");
		if (getId() != null) {
			strBuf.append("id: ").append(getId()).append(',').append(' ');
		}
		if (name != null) {
			strBuf.append("name: ").append(name).append(',').append(' ');
		}
		if (isSuper != null) {
			strBuf.append("isSuper: ").append(isSuper).append(',').append(' ');
		}
		if (permissionIds != null) {
			strBuf.append("permissionIds: ").append(permissionIds).append(',').append(' ');
		}
		if (getRemark() != null) {
			strBuf.append("remark: ").append(getRemark()).append(',').append(' ');
		}
		if (strBuf.toString().endsWith("{")) {
			strBuf.deleteCharAt(strBuf.length() - 1);
			strBuf.append("All fileds are null.");
		} else {
			strBuf.deleteCharAt(strBuf.length() - 2);
			strBuf.append('}');
		}
		return strBuf.toString();
	}

}