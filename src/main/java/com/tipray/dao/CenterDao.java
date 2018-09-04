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
     * 统计用户中心名称
     * @param centerNamne
     * @return
     */
	Integer countByCenterName(String centerNamne);

    /**
     * 根据用户中心名称更新中心
     * @param center
     */
	void updateByCenterName(Center center);

    /**
     * 根据用户中心名称删除中心
     * @param centerName
     */
	void deleteByCenterName(String centerName);

	/**
	 * 根据用户中心名称获取用户中心信息
	 * 
	 * @param centerName
	 * @return
	 */
	Center getByName(String centerName);

	/**
	 * 根据ID获取用户中心名称
	 * @param id 用户中心ID
	 * @return 用户中心名称
	 */
	String getCenterNameById(Long id);

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
