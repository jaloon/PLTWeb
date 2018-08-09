package com.tipray.bean;

import com.tipray.core.base.BaseBean;

/**
 * 用户中心ftp服务器配置信息
 * 
 * @author chenlong
 * @version 1.0 2018-04-19
 *
 */
public class FtpConfig extends BaseBean {
	private static final long serialVersionUID = 1L;
	/** 用户中心ID */
	private Long centerId;
	/** ftp协议，默认ftp */
	private String protocol = "ftp";
	/** ftp服务器主机地址 */
	private String host;
	/** ftp服务器端口，默认21 */
	private Integer port = 21;
	/** 文件目录，默认根目录(若为用户根目录，为空字符串；若为子目录，以“/”开头，不以“/”结尾，并且不含“//”，例如：/user) */
	private String directory = "";
	/** ftp用户账号 */
	private String account;
	/** ftp用户密码(Base64加密) */
	private String password;

	public Long getCenterId() {
		return centerId;
	}

	public void setCenterId(Long centerId) {
		this.centerId = centerId;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		if (protocol != null && !protocol.trim().isEmpty()) {
			this.protocol = protocol;
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		if (port != null) {
			this.port = port;
		}
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		if (directory != null) {
			this.directory = directory;
		}
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

	/** ftp登录全路径（ftp://admin:admin@192.168.7.20） */
	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(protocol).append(':').append('/').append('/');
		if (account != null) {
			strBuf.append(account);
			// if (password != null) {
				strBuf.append(':').append("******");
			// }
			strBuf.append('@');
		}
		strBuf.append(host).append(':').append(port).append(directory);
		return strBuf.toString();
	}

}
