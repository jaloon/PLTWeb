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
	 * @param center {@link Center}
	 * @throws ServiceException
	 */
	Center addCenter(Center center) throws ServiceException;

	/**
	 * 修改用户中心信息
	 * 
	 * @param center {@link Center}
	 */
	Center updateCenter(Center center) throws ServiceException;

	/**
	 * 根据Id删除用户中心
	 * 
	 * @param id 用户中心ID
	 */
	void deleteCenterById(Long id) throws ServiceException;

	/**
	 * 根据Id获取用户中心信息
	 * 
	 * @param id 用户中心ID
	 * @return {@link Center}
	 */
	Center getCenterById(Long id);

	/**
	 * 根据用户中心名称获取用户中心信息
	 * 
	 * @param centerName 用户中心名称
	 * @return {@link Center}
	 */
	Center getByName(String centerName);

    /**
     * 根据ID获取用户中心名称
     * @param id 用户中心ID
     * @return 用户中心名称
     */
	String getCenterNameById(Long id);

	/**
	 * 获取所有用户中心信息
	 * 
	 * @return {@link Center}
	 */
	List<Center> findAllCenters();

	/**
	 * 获取中心数量
	 * 
	 * @param center {@link Center}
	 * @return 中心数量
	 */
	long countCenter(Center center);

	/**
	 * 分页查询中心列表
	 * 
	 * @param center {@link Center}
	 * @param page {@link Page}
	 * @return {@link Center}
	 */
	List<Center> findByPage(Center center, Page page);

	/**
	 * 分页查询中心列表
	 * 
	 * @param center {@link Center}
	 * @param page {@link Page}
	 * @return {@link Center}
	 */
	GridPage<Center> findcenterForPage(Center center, Page page);

    /**
     * 获取用户中心列表
     * @return {@link Center}
     */
	List<Center> getCenterList();

	/**
	 * 通过用户中心ID获取IP地址和Web端口号
	 * @param centerId 用户中心ID
	 * @return IP地址和Web端口号
	 */
	Map<String, Object> getIpAndWebport(Integer centerId);

}
