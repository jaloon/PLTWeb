package com.tipray.dao;

import com.tipray.bean.AppInfo;
import com.tipray.bean.CenterDev;
import com.tipray.core.base.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenlong
 * @version 1.0 2018-09-12
 */
public interface CenterDevDao extends BaseDao<CenterDev> {

    /**
     * 根据APP设备UUID获取用户中心ID列表
     *
     * @param uuid APP设备UUID
     * @return 用户中心ID列表
     */
    List<Long> findCenterIdsByUuid(String uuid);

    /**
     * 根据用户中心ID获取uuid归属信息列表
     * @param centerId 用户中心ID
     * @return uuid归属信息
     */
    List<CenterDev> findCenterDevsByCenterId(Long centerId);

    /**
     * 根据APP归属信息记录ID获取用户中心Web地址
     * @param id 序号
     * @return 用户中心Web地址
     */
    AppInfo getCenterWebAddrById(Long id);

    /**
     * 统计uuid归属记录数目
     * @param uuid APP设备UUID
     * @param centerId 用户中心ID
     * @return uuid归属记录数目
     */
    Integer countByUuidAndCenterId(@Param("uuid") String uuid, @Param("centerId") Long centerId);
}
