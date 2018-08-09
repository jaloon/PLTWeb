/*
 * Center.java
 * Copyright(C) 厦门天锐科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2017-11-06 Created
 */
package com.tipray.bean;

import com.tipray.core.base.BaseBean;

/**
 * 用户中心信息表
 * 
 * @author chenlong
 * @version 1.0 2017-11-06
 */
public class Center extends BaseBean {
	private static final long serialVersionUID = 1L;
	/** 用户中心名称 */
	private String name;
	/** IP地址 */
	private String ip;
	/** Web端口 */
	private Integer webPort;
	/** TCP端口号 */
	private Integer tcpPort;
	/** 道闸端口号 */
	private Integer barrierPort;
	/** RC4密码，16个字节 */
	private byte[] rc4;
	/** RC4密码版本，1个字节 */
	private byte[] rc4Version;
	/** ftp配置 */
	private FtpConfig ftpConfig;
	/** 负责人 */
	private String director;
	/** 联系电话 */
	private String phone;
	/** 联系地址 */
	private String address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getWebPort() {
		return webPort;
	}

	public void setWebPort(Integer webPort) {
		this.webPort = webPort;
	}

	public Integer getTcpPort() {
		return tcpPort;
	}

	public void setTcpPort(Integer tcpPort) {
		this.tcpPort = tcpPort;
	}

	public Integer getBarrierPort() {
		return barrierPort;
	}

	public void setBarrierPort(Integer barrierPort) {
		this.barrierPort = barrierPort;
	}

	public byte[] getRc4() {
		return rc4;
	}

	public void setRc4(byte[] rc4) {
		this.rc4 = rc4;
	}

	public byte[] getRc4Version() {
		return rc4Version;
	}

	public void setRc4Version(byte[] rc4Version) {
		this.rc4Version = rc4Version;
	}

	public FtpConfig getFtpConfig() {
		return ftpConfig;
	}

	public void setFtpConfig(FtpConfig ftpConfig) {
		this.ftpConfig = ftpConfig;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("Center: {");
		if (getId() != null) {
			strBuf.append("id: ").append(getId()).append(',').append(' ');
		}
		if (name != null) {
			strBuf.append("name: ").append(name).append(',').append(' ');
		}
		if (ip != null) {
			strBuf.append("ip: ").append(ip).append(',').append(' ');
		}
		if (webPort != null) {
			strBuf.append("webPort: ").append(webPort).append(',').append(' ');
		}
		if (tcpPort != null) {
			strBuf.append("tcpPort: ").append(tcpPort).append(',').append(' ');
		}
		if (barrierPort != null) {
			strBuf.append("barrierPort: ").append(barrierPort).append(',').append(' ');
		}
		if (ftpConfig != null) {
			strBuf.append("ftpConfig: ").append('\"').append(ftpConfig.toString()).append('\"').append(',').append(' ');
		}
		if (director != null) {
			strBuf.append("director: ").append(director).append(',').append(' ');
		}
		if (phone != null) {
			strBuf.append("phone: ").append(phone).append(',').append(' ');
		}
		if (address != null) {
			strBuf.append("address: ").append(address).append(',').append(' ');
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
