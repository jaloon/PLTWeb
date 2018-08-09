package com.tipray.service;

import com.tipray.bean.Center;
import com.tipray.bean.GridPage;
import com.tipray.bean.Page;
import com.tipray.core.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface CenterService {
	/**
	 * 新增用户中心
	 * 
	 * @param center
	 * @throws ServiceException
	 */
	Center addCenter(Center center) throws ServiceException;

	/**
	 * 修改用户中心信息
	 * 
	 * @param center
	 */
	Center updateCenter(Center center) throws ServiceException;

	/**
	 * 根据Id删除用户中心
	 * 
	 * @param center
	 */
	void deleteCenterById(Long id) throws ServiceException;

	/**
	 * 根据Id获取用户中心信息
	 * 
	 * @param id
	 * @return
	 */
	Center getCenterById(Long id);

	/**
	 * 根据用户中心名称获取用户中心信息
	 * 
	 * @param centerName
	 * @return
	 */
	Center getByName(String centerName);

	/**
	 * 获取所有用户中心信息
	 * 
	 * @return
	 */
	List<Center> findAllCenters();

	/**
	 * 获取中心数量
	 * 
	 * @param center
	 * @return
	 */
	long countCenter(Center center);

	/**
	 * 分页查询中心列表
	 * 
	 * @param center
	 * @param page
	 * @return
	 */
	List<Center> findByPage(Center center, Page page);

	/**
	 * 分页查询中心列表
	 * 
	 * @param center
	 * @param page
	 * @return
	 */
	GridPage<Center> findcenterForPage(Center center, Page page);

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
