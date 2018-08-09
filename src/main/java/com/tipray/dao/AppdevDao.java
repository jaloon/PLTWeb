package com.tipray.dao;

import com.tipray.bean.AppCenter;
import com.tipray.bean.AppDev;
import com.tipray.bean.AppInfo;
import com.tipray.bean.Center;
import com.tipray.core.annotation.MyBatisAnno;
import com.tipray.core.base.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisAnno
public interface AppdevDao extends BaseDao<AppDev> {

    /**
     * 根据手机UUID更新手机型号和当前APP版本号
     *
     * @param model      手机型号
     * @param currentVer 当前APP版本号
     * @param uuid       手机UUID
     */
    void updateModelAndCurrentVerByUuid(@Param("model") String model,
                                        @Param("currentVer") String currentVer,
                                        @Param("uuid") String uuid);

    /**
     * 根据手机UUID获取APP配置信息
     *
     * @param uuid 手机UUID
     * @return APP配置信息
     */
    AppDev getByUuid(String uuid);

    /**
     * 统计UUID数目
     *
     * @param uuid
     * @return
     */
    Integer countByUuid(String uuid);

    /**
     * 根据UUID获取记录ID
     *
     * @param uuid
     * @return
     */
    List<Long> findIdsByUuid(String uuid);

    /**
     * 根据手机UUID删除记录
     *
     * @param uuid
     */
    void deleteByUuid(String uuid);

    /**
     * 根据记录ID获取中心ID
     *
     * @param id
     * @return
     */
    Long getCenterIdById(Long id);

    /**
     * 根据记录ID获取中心Web服务器地址
     *
     * @param id
     * @return
     */
    Center getCenterAddrById(Long id);

    /**
     * 根据用户中心ID获取app设备列表
     *
     * @param centerId
     * @return
     */
    List<AppDev> findByCenterId(Long centerId);

    /**
     * 添加APP设备与用户中心对应关系
     * @param uuid
     * @param centerIds
     */
    void addAppCenter(@Param("uuid") String uuid, @Param("centerIds") List<Long> centerIds);

    /**
     * 删除APP设备与用户中心对应关系
     * @param uuid
     * @param centerIds
     */
    void deleteAppCenter(@Param("uuid") String uuid, @Param("centerIds") List<Long> centerIds);

    /**
     * 根据APP设备ID删除与用户中心对应关系
     * @param appdevId
     */
    void deleteAppCenterByAppdevId(Long appdevId);

    /**
     * 根据APP设备信息记录ID获取用户中心名称
     * @param appdevId APP设备信息记录ID
     * @return
     */
    List<Long> findCenterIdsByAppdevId(Long appdevId);

    /**
     * 根据APP设备信息记录ID获取用户中心名称
     * @param appdevId APP设备信息记录ID
     * @return
     */
    List<String> findCenterNamesByAppdevId(Long appdevId);

    /**
     * 根据APP设备信息记录ID获取所属用户中心信息
     * @param appdevId APP设备信息记录ID
     * @return
     */
    List<Center> findBelongCentersByAppdevId(Long appdevId);

    /**
     * 根据APP设备信息记录ID获取用户中心信息
     * @param appdevId APP设备信息记录ID
     * @return
     */
    List<AppCenter> findAppCentersByAppdevId(Long appdevId);

    /**
     * 根据APP设备UUID获取用户中心ID列表
     * @param uuid APP设备UUID
     * @return
     */
    List<Long> findCenterIdsByUuid(String uuid);

    /**
     * 根据APP设备UUID获取用户中心列表
     * @param uuid APP设备UUID
     * @return
     */
    List<Center> findCentersByUuid(String uuid);

    /**
     * 根据用户中心ID获取APP设备UUID
     * @param centerId 用户中心ID
     * @return
     */
    List<String> findUuidsByCenterId(Long centerId);

    /**
     * 根据用户中心ID获取用户中心Web地址
     * @param centerId 用户中心ID
     * @return
     */
    AppInfo getCenterWebAddr(Long centerId);

}
