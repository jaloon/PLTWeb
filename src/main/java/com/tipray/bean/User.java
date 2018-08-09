/*
 * User.java
 * Copyright(C) 厦门天锐科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2017-11-06 Created
 */
package com.tipray.bean;

import com.tipray.core.base.BaseBean;

/**
 * 操作员信息表
 * 
 * @author chenlong
 * @version 1.0 2017-11-06
 */
public class User extends BaseBean {
	private static final long serialVersionUID = 1L;
	/** 角色 */
	private Role role;
	/** 账号 */
	private String account;
	/** 密码 */
	private String password;
	/** 姓名 */
	private String name;
	/** 联系电话 */
	private String phone;
	/** 身份证号 */
	private String identityCard;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("User: {");
		if (getId() != null) {
			strBuf.append("id: ").append(getId()).append(',').append(' ');
		}
		if (account != null) {
			strBuf.append("account: ").append(account).append(',').append(' ');
		}
		if (password != null) {
			strBuf.append("password: ").append("******").append(',').append(' ');
		}
		if (name != null) {
			strBuf.append("name: ").append(name).append(',').append(' ');
		}
		if (role != null) {
			strBuf.append(role.toString()).append(',').append(' ');
		}
		if (phone != null) {
			strBuf.append("phone: ").append(phone).append(',').append(' ');
		}
		if (identityCard != null) {
			strBuf.append("identityCard: ").append(identityCard).append(',').append(' ');
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