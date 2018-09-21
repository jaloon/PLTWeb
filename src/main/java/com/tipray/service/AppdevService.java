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
     * 修改APP设备信息
     *
     * @param appDev    APP设备信息
     */
    AppDev updateAppdev(AppDev appDev) throws ServiceException;

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
     * 根据用户中心ID获取用户中心Web地址
     *
     * @param centerId 用户中心ID
     * @return
     */
    AppInfo getCenterWebAddr(Long centerId);
}
