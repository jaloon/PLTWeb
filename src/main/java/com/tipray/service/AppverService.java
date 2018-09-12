package com.tipray.service;

import com.tipray.bean.AppVer;
import com.tipray.bean.GridPage;
import com.tipray.bean.Page;
import com.tipray.core.exception.ServiceException;

import java.util.List;

public interface AppverService {
    /**
     * 新增APP版本信息
     *
     * @param appVer APP版本信息
     * @throws ServiceException
     */
    AppVer addAppver(AppVer appVer) throws ServiceException;

    /**
     * 修改APP版本信息
     *
     * @param appVer APP版本信息
     */
    AppVer updateAppver(AppVer appVer) throws ServiceException;

    /**
     * 根据Id删除APP版本信息
     *
     * @param id
     */
    void deleteAppverById(Long id) throws ServiceException;

    /**
     * 根据Id获取APP版本信息
     *
     * @param id 记录id
     * @return APP版本信息
     */
    AppVer getAppverById(Long id);

    /**
     * 用户中心APP版本信息是否存在
     *
     * @param centerId 用户中心ID
     * @param appid    应用标识
     * @param system   手机系统
     * @return APP版本信息数
     */
    boolean isAppverExist(Long centerId, String appid, String system);

    /**
     * 根据用户中心ID和系统类型获取指定版本号
     * @param centerId 用户中心ID
     * @param appid 应用标识
     * @param system   手机系统
     * @return 指定版本号
     */
    String getAssignVerByAppver(Long centerId, String appid, String system);

    /**
     * 获取所有APP版本信息
     *
     * @return 所有APP版本信息
     */
    List<AppVer> findAllAppvers();

    /**
     * 获取APP版本信息数量
     *
     * @param appVer APP版本信息
     * @return APP版本信息数量
     */
    long countAppver(AppVer appVer);

    /**
     * 分页查询APP版本信息列表
     *
     * @param appVer APP版本信息
     * @param page   分页信息
     * @return APP版本信息列表
     */
    List<AppVer> findByPage(AppVer appVer, Page page);

    /**
     * 分页查询APP版本信息列表
     *
     * @param appVer APP版本信息
     * @param page   分页信息
     * @return APP版本信息列表
     */
    GridPage<AppVer> findAppverForPage(AppVer appVer, Page page);

    /**
     * 获取缺省版本
     *
     * @return
     */
    List<AppVer> getDefaultVer();
}
