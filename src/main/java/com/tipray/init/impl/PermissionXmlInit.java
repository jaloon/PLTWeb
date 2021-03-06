package com.tipray.init.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tipray.bean.Permission;
import com.tipray.bean.PermissionConfig;
import com.tipray.init.Initialization;
import com.tipray.service.PermissionService;
import com.tipray.util.ConvertXmlAndBeanUtil;
import com.tipray.util.SpringBeanUtil;

/**
 * 权限树的初始化 读取permissionConfig.xml文件
 * 
 * @author chends
 *
 */
public class PermissionXmlInit extends Initialization {
	private static Logger logger = LoggerFactory.getLogger(PermissionXmlInit.class);

	private PermissionService permissionService;

	@Override
	public void init() throws Exception {
		this.permissionService = SpringBeanUtil.getPermissionService();
		initPermission("permissionConfig.xml");
	}

	@Override
	public void update() throws Exception {
	}

	private void initPermission(String xml) throws Exception {
		PermissionConfig permissionConfig = getPermissionConfig(xml);
		if (null != permissionConfig) {
			createDataInDB(permissionConfig.getPermission(), null);
		}
	}

	/**
	 * set data in DB,parentid is null;
	 * 
	 * @throws Exception
	 */
	private void createDataInDB(List<Permission> permissionObjects, Permission parent) throws Exception {
		if (permissionObjects != null) {
			for (int i = 0; i < permissionObjects.size(); i++) {
				Permission permission = permissionObjects.get(i);
				boolean enable = permission.getEnable() == null ? (parent == null ? true : parent.getEnable())
						: permission.getEnable();
				permission.setEnable(enable);
				permission.setIndexId(new Long(i));
				permission.setParentId(parent != null ? parent.getId() : 0L);
				List<Permission> childrenPermissions = permission.getChildren();
				permission.setIsParent(childrenPermissions != null ? true : false);
				permissionService.addPermission(permission);
				createDataInDB(permission.getChildren(), permission);
			}
		}
	}

	/**
	 * @return permissionConfig object
	 */
	private PermissionConfig getPermissionConfig(String xml) {
		try {
			PermissionConfig permissionConfig = (PermissionConfig) ConvertXmlAndBeanUtil
					.xmlToBean(getPermissionConfigPath(xml), PermissionConfig.class, getMapInputPermissionPath());
			return permissionConfig;
		} catch (Exception e) {
			logger.error("problem in execute!", e);
			return null;
		}
	}

	private String getPermissionConfigPath(String xml) {
		return xml;
	}

	private String getMapInputPermissionPath() {
		return PermissionXmlInit.class.getClassLoader().getResource("permissionMapping.xml").getPath();
	}

}
