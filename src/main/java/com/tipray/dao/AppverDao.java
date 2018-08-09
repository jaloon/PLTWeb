package com.tipray.dao;

import com.tipray.bean.AppInfo;
import com.tipray.bean.AppVer;
import com.tipray.core.annotation.MyBatisAnno;
import com.tipray.core.base.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisAnno
public interface AppverDao extends BaseDao<AppVer> {

    /**
     * 统计用户中心APP版本信息数
     *
     * @param centerId 用户中心ID
     * @param system   手机系统
     * @return APP版本信息数
     */
    Integer countCenterAppVer(@Param("centerId") Long centerId, @Param("system") String system);

    /**
     * 根据用户中心ID获取APP版本信息
     * @param centerId 用户中心ID
     * @return
     */
    List<AppVer> findByCenterId(Long centerId);

    /**
     * 根据用户中心ID和系统类型获取指定版本号
     * @param centerId 用户中心ID
     * @param system   手机系统
     * @return 指定版本号
     */
    String getAssignVerByCenterIdAndSystem(@Param("centerId") Long centerId, @Param("system") String system);

    /**
     * 根据APP版本信息记录ID获取用户中心Web地址
     * @param id APP版本信息记录ID
     * @return 用户中心Web地址
     */
    AppInfo getCenterWebAddrById(Long id);

    /**
     * 根据用户中心ID获取用户中心Web地址
     * @param centerId 用户中心ID
     * @return 用户中心Web地址
     */
    AppInfo getCenterWebAddrByCenterId(Long centerId);
}
