package com.tipray.service;

import com.tipray.bean.*;
import com.tipray.core.exception.ServiceException;

import java.util.List;

public interface AppdevService {
    /**
     * 新增APP设备信息
     *
     * @param appDev APP设备信息
     * @throws ServiceException
     */
    AppDev addAppdev(AppDev appDev) throws ServiceException;

    /**
     * 新增APP设备信息
     *
     * @param appDev    APP设备信息
     * @param centerIds 用户中心ID，英文逗号“,”分隔
     * @throws ServiceException
     */
    AppDev addAppdev(AppDev appDev, String centerIds) throws ServiceException;

    /**
     * 修改APP设备信息
     *
     * @param appDev    APP设备信息
     * @param centerIds 用户中心ID，英文逗号“,”分隔
     */
    AppDev updateAppdev(AppDev appDev, String centerIds) throws ServiceException;

    /**
     * 根据手机UUID更新手机型号和当前APP版本号
     *
     * @param model      手机型号
     * @param currentVer 当前APP版本号
     * @param uuid       手机UUID
     * @param appid      应用标识
     * @throws ServiceException
     */
    void updateModelAndCurrentVerByUuidAndAppid(String model, String currentVer, String uuid, String appid) throws ServiceException;

    /**
     * 根据Id删除APP设备信息
     *
     * @param appDev APP设备信息
     */
    void deleteAppdevById(Long id) throws ServiceException;

    /**
     * 根据Id获取APP设备信息
     *
     * @param id 记录id
     * @return APP配置信息
     */
    AppDev getAppdevById(Long id);

    /**
     * 根据手机UUID获取APP设备信息
     *
     * @param uuid  手机UUID
     * @param appid 应用标识
     * @return APP设备信息
     */
    AppDev getAppdevByUuidAndAppid(String uuid, String appid);

    /**
     * UUID是否已存在
     *
     * @param uuid
     * @param appid
     * @return
     */
    boolean isAppdevExist(String uuid, String appid);

    /**
     * 获取所有APP设备信息
     *
     * @return 所有APP设备信息
     */
    List<AppDev> findAllAppdev();

    /**
     * 获取APP设备信息数量
     *
     * @param appDev APP设备信息
     * @return APP设备信息数量
     */
    long countAppdev(AppDev appDev);

    /**
     * 分页查询APP设备信息列表
     *
     * @param appDev APP设备信息
     * @param page   分页信息
     * @return APP设备信息列表
     */
    List<AppDev> findByPage(AppDev appDev, Page page);

    /**
     * 分页查询APP设备信息列表
     *
     * @param appDev APP设备信息
     * @param page   分页信息
     * @return APP设备信息列表
     */
    GridPage<AppDev> findAppdevForPage(AppDev appDev, Page page);

    /**
     * 根据APP设备信息记录ID获取用户中心名称
     *
     * @param appdevId APP设备信息记录ID
     * @return
     */
    List<String> findCenterNamesByAppdevId(Long appdevId);

    /**
     * 根据APP设备信息记录ID获取所属用户中心信息
     *
     * @param appdevId APP设备信息记录ID
     * @return
     */
    List<Center> findBelongCentersByAppdevId(Long appdevId);

    /**
     * 根据APP设备信息记录ID获取用户中心信息
     *
     * @param appdevId APP设备信息记录ID
     * @return
     */
    List<AppCenter> findAppCentersByAppdevId(Long appdevId);

    /**
     * 根据APP设备UUID获取用户中心ID列表
     *
     * @param uuid APP设备UUID
     * @param appid 应用标识
     * @return
     */
    List<Long> findCenterIdsByUuidAndAppid(String uuid, String appid);

    /**
     * 根据APP设备UUID获取用户中心列表
     *
     * @param uuid APP设备UUID
     * @return
     */
    List<Center> findCentersByUuid(String uuid);

    /**
     * 根据用户中心ID获取APP设备UUID
     *
     * @param centerId 用户中心ID
     * @return
     */
    List<String> findUuidsByCenterId(Long centerId);

    /**
     * 根据用户中心ID获取APP同步信息
     *
     * @param centerId
     * @return
     */
    AppSync findAppSync(Long centerId);

    /**
     * 根据用户中心ID获取用户中心Web地址
     *
     * @param centerId 用户中心ID
     * @return
     */
    AppInfo getCenterWebAddr(Long centerId);
}
