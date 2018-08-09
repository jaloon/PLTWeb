package com.tipray.dao;

import com.tipray.bean.Center;
import com.tipray.bean.FtpConfig;
import com.tipray.core.annotation.MyBatisAnno;
import com.tipray.core.base.BaseDao;

import java.util.List;
import java.util.Map;

@MyBatisAnno
public interface CenterDao extends BaseDao<Center> {
	/**
	 * 根据用户中心名称获取用户中心信息
	 * 
	 * @param centerName
	 * @return
	 */
	Center getByName(String centerName);

	/**
	 * 添加ftp配置信息
	 * 
	 * @param ftpConfig
	 */
	void addFtp(FtpConfig ftpConfig);

	/**
	 * 更新ftp配置信息
	 * 
	 * @param ftpConfig
	 */
	void updateFtp(FtpConfig ftpConfig);

	/**
	 * 删除ftp配置信息
	 * 
	 * @param centerId
	 */
	void deleteFtp(Long centerId);

    /**
     * 获取用户中心列表
     * @return
     */
    List<Center> getCenterList();

    /**
     * 通过用户中心ID获取IP地址和Web端口号
     * @param centerId 用户中心ID
     * @return IP地址和Web端口号
     */
    Map<String, Object> getIpAndWebport(Integer centerId);

}
