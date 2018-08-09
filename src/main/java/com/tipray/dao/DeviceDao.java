package com.tipray.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tipray.bean.Device;
import com.tipray.core.annotation.MyBatisAnno;
import com.tipray.core.base.BaseDao;

@MyBatisAnno
public interface DeviceDao extends BaseDao<Device> {
    /**
     * 根据设备ID更新设备
     * @param device
     */
    void updateByDeviceId(Device device);

    /**
     * 添加待使用的设备ID
     * @param device deviceId 设备ID，type 设备类型
     */
    void addToUseDeviceId(Device device);

    /**
     * 更新设备ID使用状态
     * @param deviceId 设备ID
     * @param status 设备ID使用状态（0 未使用，1 待使用，2 使用中）
     */
    void upDeviceIdStatus(@Param("deviceId") Integer deviceId, @Param("status") Byte status);

    /**
     * 统计设备ID
     * @param deviceId 设备ID
     * @return 设备ID数目
     */
    Integer countDeviceId(Integer deviceId);

    /**
     * 获取设备ID使用状态
     * @param deviceId 设备ID
     * @return 设备ID使用状态
     */
    Integer getDeviceIdStatus(Integer deviceId);

    /**
     * 将超过24小时待使用的设备ID状态更新为未使用
     */
    void upToUseDeviceIdUnused();

    /**
     * 根据用户中心名称获取设备列表
     *
     * @param centerName 用户中心名称
     * @return 设备列表
     */
    List<Device> findByCenter(String centerName);

    /**
     * 根据用户中心ID获取设备列表
     *
     * @param centerId 用户中心ID
     * @return 设备列表
     */
    List<Device> findByCenterId(Long centerId);

    /**
     * 根据设备类型获取设备列表
     *
     * @param type 设备类型
     * @return 设备列表
     */
    List<Device> findByType(Integer deviceType);

    /**
     * 根据用户中心名称和设备类型获取设备列表
     *
     * @param centerName 用户中心名称
     * @param deviceType 设备类型
     * @return 设备列表
     */
    List<Device> findByCenterAndType(@Param("centerName") String centerName,
                                     @Param("deviceType") Integer deviceType);

    /**
     * 根据设备ID获取设备信息
     *
     * @param deviceId 设备ID
     * @return 设备信息
     */
    Device getByDeviceId(Integer deviceId);

    /**
     * 获取各类型设备的最大设备ID值
     *
     * @return 最大设备ID值
     */
    List<Device> findMaxDeviceIdsOfType();

    /**
     * 根据设备类型查询未使用的设备ID
     *
     * @param type 设备类型
     * @return 未使用的设备ID
     */
    List<Integer> findUnusedDeviceIdByType(Integer type);

    /**
     * 根据设备类型获取最大设备ID
     *
     * @param type 设备类型
     * @return 最大设备ID
     */
    Integer getMaxDeviceIdByType(Integer type);

    /**
     * 删除用户中心时，根据中心ID删除相关设备
     *
     * @param centerId 中心ID
     */
    void deleteDevicesWithCenterId(Long centerId);

    /**
     * 根据设备ID获取设备所属中心ftp配置信息
     *
     * @param deviceId 设备ID
     * @return 设备所属中心ftp配置信息
     */
    Map<String, Object> getFtpConfigByDeviceId(Integer deviceId);

    /**
     * 根据设备ID获取设备所属中心信息
     *
     * @param deviceId 设备ID
     * @return 设备所属中心信息
     */
    Map<String, Object> getCenterByDeviceId(Integer deviceId);

    /**
     * 根据用户中心ID获取用户中心RC4秘钥
     *
     * @param centerId 用户中心ID
     * @return 用户中心RC4秘钥
     */
    String getCenterRc4ByCenterId(Integer centerId);

    /**
     * 根据用户中心ID获取所属中心道闸接口信息
     *
     * @param centerId 用户中心ID
     * @return 道闸接口信息
     */
    Map<String, Object> getBarrierByCenterId(Integer centerId);

}
