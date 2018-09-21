package com.tipray.dao;

import com.tipray.bean.AppDev;
import com.tipray.bean.AppInfo;
import com.tipray.core.annotation.MyBatisAnno;
import com.tipray.core.base.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisAnno
public interface AppdevDao extends BaseDao<AppDev> {

    /**
     * 根据手机UUID和appid更新手机型号和当前APP版本号
     *
     * @param model      手机型号
     * @param currentVer 当前APP版本号
     * @param uuid       手机UUID
     * @param appid      应用标识
     */
    void updateModelAndCurrentVerByUuidAndAppid(@Param("model") String model,
                                                @Param("currentVer") String currentVer,
                                                @Param("uuid") String uuid,
                                                @Param("appid") String appid);

    /**
     * 根据手机UUID和appid获取APP配置信息
     *
     * @param uuid  手机UUID
     * @param appid 应用标识
     * @return APP配置信息
     */
    AppDev getByUuidAndAppid(@Param("uuid") String uuid, @Param("appid") String appid);

    /**
     * 统计UUID、appid数目
     *
     * @param uuid  手机UUID
     * @param appid 应用标识
     * @return
     */
    Integer countByUuidAndAppid(@Param("uuid") String uuid, @Param("appid") String appid);

    /**
     * 根据UUID获取记录ID
     *
     * @param uuid  手机UUID
     * @param appid 应用标识
     * @return
     */
    List<Long> findIdsByUuidAndAppid(@Param("uuid") String uuid, @Param("appid") String appid);

    /**
     * 根据手机UUID删除记录
     *
     * @param uuid  手机UUID
     * @param appid 应用标识
     */
    void deleteByUuidAndAppid(@Param("uuid") String uuid, @Param("appid") String appid);

    /**
     * 根据用户中心ID获取用户中心Web地址
     *
     * @param centerId 用户中心ID
     * @return
     */
    AppInfo getCenterWebAddr(Long centerId);

}
