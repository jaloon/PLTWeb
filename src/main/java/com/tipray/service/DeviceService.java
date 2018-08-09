package com.tipray.service;

import com.tipray.bean.Device;
import com.tipray.bean.GridPage;
import com.tipray.bean.Page;
import com.tipray.core.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface DeviceService {
	/**
	 * 新增设备
	 * 
	 * @param device
	 * @throws ServiceException
	 */
	Device addDevice(Device device) throws ServiceException;

	/**
	 * 修改设备信息
	 * 
	 * @param device
	 */
	Device updateDevice(Device device) throws ServiceException;

	/**
	 * 根据Id删除设备
	 * 
	 * @param id
	 */
	void deleteDeviceById(Long id) throws ServiceException;

	/**
	 * 根据Id获取设备信息
	 * 
	 * @param id
	 * @return
	 */
	Device getDeviceById(Long id);

	/**
	 * 获取所有的设备信息
	 * 
	 * @param
	 * @return
	 */
	List<Device> findAllDevices();

	/**
	 * 根据用户中心名称获取设备列表
	 * 
	 * @param centerName
	 * @return
	 */
	List<Device> findByCenter(String centerName);

	/**
	 * 根据用户中心ID获取设备列表
	 * 
	 * @param centerId
	 * @return
	 */
	List<Device> findByCenterId(Long centerId);

	/**
	 * 根据设备类型获取设备列表
	 * 
	 * @param type
	 * @return
	 */
	List<Device> findByType(Integer deviceType);

	/**
	 * 根据用户中心名称和设备类型获取设备列表
	 * 
	 * @param centerName
	 * @param typeValue
	 * @return
	 */
	List<Device> findByCenterAndType(String centerName, Integer deviceType);

	/**
	 * 获取设备数量
	 * 
	 * @return
	 */
	long countDevice(Device device);

	/**
	 * 分页查询设备列表
	 * 
	 * @param device
	 * @param page
	 * @return
	 */
	List<Device> findByPage(Device device, Page page);

	/**
	 * 分页查询设备列表
	 * 
	 * @param device
	 * @param page
	 * @return
	 */
	GridPage<Device> findDeviceForPage(Device device, Page page);

	/**
	 * 根据设备ID获取设备信息
	 * 
	 * @param did
	 * @return
	 */
	Device getDeviceByDeviceId(Integer deviceId);

	/**
	 * 根据设备ID获取设备所属中心信息
	 * 
	 * @param id
	 * @return
	 */
	Map<String, Object> getCenterByDeviceId(Integer id);

	/**
	 * 获取各类型设备的最大设备ID值
	 * 
	 * @return
	 */
	List<Device> findMaxDeviceIdsOfType();

	/**
	 * 根据设备类型获取最大设备ID
	 * 
	 * @param type
	 * @return
	 */
	Integer getNextDeviceIdByType(Integer type);

	/**
	 * 根据用户中心ID获取用户中心RC4秘钥
	 * 
	 * @param id
	 *            用户中心ID
	 * @return
	 */
	byte[] getCenterRc4ByCenterId(Integer id);
	
	/**
	 * 根据用户中心ID获取道闸接口信息
	 * 
	 * @param id
	 *            用户中心ID
	 * @return
	 */
	Map<String, Object> getBarrierByCenterId(Integer id);

    /**
     * 申请设备ID
     * @param type 设备类型
     * @return 设备ID
     */
	Integer applyForDeviceId(Integer type);

    /**
     * 更新设备ID使用状态
     * @param deviceId 设备ID
     */
    void upDeviceIdStatus(Integer deviceId);

    /**
     * 获取设备ID使用状态
     * @param deviceId 设备ID
     * @return 设备ID使用状态
     */
    Integer getDeviceIdStatus(Integer deviceId);

    /**
     * 设备ID是否存在
     * @param deviceId 设备ID
     * @return
     */
    boolean isDeviceIdExist(Integer deviceId);

    /**
     * 将超过24小时待使用的设备ID状态更新为未使用
     */
    void upToUseDeviceIdUnused();
}
