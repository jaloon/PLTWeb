package com.tipray.service;

import com.tipray.bean.AppSync;
import com.tipray.bean.CenterDev;
import com.tipray.bean.GridPage;
import com.tipray.bean.Page;

import java.util.List;

/**
 * @author chenlong
 * @version 1.0 2018-09-12
 */
public interface CenterDevService {
    /**
     * 添加uuid归属信息
     * @param centerDev uuid归属信息
     * @return uuid归属信息
     */
    CenterDev addCenterDev(CenterDev centerDev);

    /**
     * 更新uuid归属信息
     * @param centerDev uuid归属信息
     * @return uuid归属信息
     */
    CenterDev updateCenterDev(CenterDev centerDev);

    /**
     * 删除uuid归属信息
     * @param id 序号
     */
    void deleteCenterDevById(Long id);

    /**
     * 根据序号获取uuid归属信息
     * @param id 序号
     * @return uuid归属信息
     */
    CenterDev getCenterDevById(Long id);

    /**
     * 获取所有uuid归属信息
     * @return uuid归属信息
     */
    List<CenterDev> findAllCenterDev();

    /**
     * 获取uuid归属信息数量
     *
     * @param centerDev uuid归属信息
     * @return uuid归属信息数量
     */
    long countCenterDev(CenterDev centerDev);

    /**
     * 分页查询uuid归属信息列表
     *
     * @param centerDev uuid归属信息
     * @param page      分页信息
     * @return uuid归属信息列表
     */
    List<CenterDev> findByPage(CenterDev centerDev, Page page);

    /**
     * 分页查询uuid归属信息列表
     *
     * @param centerDev uuid归属信息
     * @param page      分页信息
     * @return uuid归属信息列表
     */
    GridPage<CenterDev> findCenterDevForPage(CenterDev centerDev, Page page);

    /**
     * 根据APP设备UUID获取用户中心ID列表
     *
     * @param uuid APP设备UUID
     * @return 用户中心ID列表
     */
    List<Long> findCenterIdsByUuid(String uuid);

    /**
     * 根据用户中心ID获取APP同步信息
     *
     * @param centerId
     * @return
     */
    AppSync findAppSync(Long centerId);

    /**
     * uuid归属记录是否存在
     * @param uuid APP设备UUID
     * @param centerId 用户中心ID
     * @return
     */
    boolean isCenterdevExist(String uuid, Long centerId);
}
