package com.tipray.bean;

import java.util.Date;

import com.tipray.core.base.BaseBean;

/**
 * 保存用户的会话
 * 
 * @author chends
 */
public class Session extends BaseBean {
	private static final long serialVersionUID = 1L;
	private String uuid;
	/** 当前登录用户 */
	private User user;
	/** 登录用户的客户端IP */
	private String ip;
	/** 是否手机登录 */
	private Integer isApp = 0;
	/** 登录时间 */
	private Date loginDate;
	/** 最近一次操作时间 */
	private Date operateDate;
	/** 此用户上一次登录的sessionId，用于判断是否异地登录 */
	private String oldUuid;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getIsApp() {
		return isApp;
	}

	public void setIsApp(Integer isApp) {
		if (isApp != null) {
			this.isApp = isApp;
		}
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	public String getOldUuid() {
		return oldUuid;
	}

	public void setOldUuid(String oldUuid) {
		this.oldUuid = oldUuid;
	}

	@Override
	public String toString() {
		return "Session [uuid=" + uuid + ", user=" + user + "]";
	}

}