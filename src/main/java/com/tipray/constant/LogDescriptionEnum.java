package com.tipray.constant;

/**
 * 日志描述枚举
 * 
 * @author chenlong
 * @version 1.0 2018-04-19
 *
 */
public enum LogDescriptionEnum {
	/** 添加用户中心 */
	CENTER_ADD((short) 0x0101, "添加用户中心"),
	/** 修改用户中心 */
	CENTER_ALTER((short) 0x0102, "修改用户中心"),
	/** 删除用户中心 */
	CENTER_DELETE((short) 0x0103, "删除用户中心"),
	/** 添加设备 */
	DEVICE_ADD((short) 0x0201, "添加设备"),
	/** 修改设备 */
	DEVICE_ALTER((short) 0x0202, "修改设备"),
	/** 删除设备 */
	DEVICE_DELETE((short) 0x0203, "删除设备"),
	/** 添加APP设备信息 */
	APPDEV_ADD((short) 0x0501, "添加APP设备信息"),
	/** 修改APP设备信息 */
	APPDEV_ALTER((short) 0x0502, "修改APP设备信息"),
	/** 删除APP设备信息 */
	APPDEV_DELETE((short) 0x0503, "删除APP设备信息"),
	/** 添加APP归属信息 */
	CENTERDEV_ADD((short) 0x0701, "添加APP归属信息"),
	/** 修改APP归属信息 */
	CENTERDEV_ALTER((short) 0x0702, "修改APP归属信息"),
	/** 删除APP归属信息 */
	CENTERDEV_DELETE((short) 0x0703, "删除APP归属信息"),
	/** 添加APP版本信息 */
	APPVER_ADD((short) 0x0601, "添加APP版本信息"),
	/** 修改APP版本信息 */
	APPVER_ALTER((short) 0x0602, "修改APP版本信息"),
	/** 删除APP版本信息 */
	APPVER_DELETE((short) 0x0603, "删除APP版本信息"),
	/** 添加操作员 */
	USER_ADD((short) 0x0301, "添加操作员"),
	/** 修改操作员 */
	USER_ALTER((short) 0x0302, "修改操作员"),
	/** 删除操作员 */
	USER_DELETE((short) 0x0303, "删除操作员"),
	/** 重置密码 */
	PWD_RESET((short) 0x0304, "重置密码"),
	/** 修改密码 */
	PWD_ALTER((short) 0x0305, "修改密码"),
	/** 添加角色 */
	ROLE_ADD((short) 0x0401, "添加角色"),
	/** 修改角色 */
	ROLE_ALTER((short) 0x0402, "修改角色"),
	/** 删除角色 */
	ROLE_DELETE((short) 0x0403, "删除角色");

	private short type;
	private String description;

	public short type() {
		return type;
	}

	public String description() {
		return description;
	}

	LogDescriptionEnum(short type, String description) {
		this.type = type;
		this.description = description;
	}

	@Override
	public String toString() {
		return description;
	}

}
