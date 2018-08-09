/*
 * Permission.java
 * Copyright(C) 厦门天锐科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2016-01-11 Created
 */
package com.tipray.bean;

import java.util.List;

import com.tipray.core.base.BaseBean;

/**
 * 权限表
 * 
 * @author chends
 * @version 1.0 2016-01-11
 */
public class Permission extends BaseBean {
	private static final long serialVersionUID = 1L;
	/**
	 * 权限中文名称
	 */
	private String cname;
	/**
	 * 权限英文名称
	 */
	private String ename;
	/**
	 * 权限类型{1:代表菜单,0:代表功能}
	 */
	private Integer permissionType;
	/**
	 * 模块名称
	 */
	private String moduleName;
	/**
	 * 是否启用{1:时,0:否}
	 */
	private Boolean enable;
	/**
	 * 父权限Id
	 */
	private Long parentId;
	/**
	 * 权限描述
	 */
	private String description;
	/**
	 * 权限序列号，用于排序
	 */
	private Long indexId;
	/**
	 * 列表页
	 */
	private String gridUrl;
	/**
	 * 子权限
	 */
	private List<Permission> children;
	/** 是否是父节点,运用于树 */
	private Boolean isParent;
	/** 是否选中,运用于树 */
	private Boolean checked = false;
	/** 是否展开,运用于树 */
	private Boolean open;

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname == null ? null : cname.trim();
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename == null ? null : ename.trim();
	}

	public Integer getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(Integer permissionType) {
		this.permissionType = permissionType;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getIndexId() {
		return indexId;
	}

	public void setIndexId(Long indexId) {
		this.indexId = indexId;
	}

	public String getGridUrl() {
		return gridUrl;
	}

	public void setGridUrl(String gridUrl) {
		this.gridUrl = gridUrl;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public List<Permission> getChildren() {
		return children;
	}

	public void setChildren(List<Permission> children) {
		this.children = children;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Permission other = (Permission) obj;
		if (this.getId() == other.getId()) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Permission [cname=" + cname + ", ename=" + ename + ", permissionType=" + permissionType
				+ ", moduleName=" + moduleName + ", enable=" + enable + ", parentId=" + parentId + ", description="
				+ description + ", indexId=" + indexId + ", gridUrl=" + gridUrl + ", children=" + children
				+ ", isParent=" + isParent + ", checked=" + checked + ", open=" + open + "]";
	}

}