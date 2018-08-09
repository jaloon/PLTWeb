package com.tipray.service.impl;

import com.tipray.bean.*;
import com.tipray.core.exception.ServiceException;
import com.tipray.dao.AppdevDao;
import com.tipray.dao.AppverDao;
import com.tipray.service.AppdevService;
import com.tipray.util.EmptyObjectUtil;
import com.tipray.util.JSONUtil;
import com.tipray.util.OkHttpUtil;
import com.tipray.util.StringUtil;
import okhttp3.FormBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author chenlong
 * @version 1.0 2018-07-30
 */
@Transactional
@Service("appdevService")
public class AppdevServiceImpl implements AppdevService {
    private final Logger logger = LoggerFactory.getLogger(AppdevServiceImpl.class);
    @Resource
    private AppdevDao appdevDao;
    @Resource
    private AppverDao appverDao;

    @Override
    public AppDev addAppdev(AppDev appDev) throws ServiceException {
        if (appDev == null) {
            return null;
        }
        appdevDao.add(appDev);
        return appDev;
    }

    @Override
    public AppDev addAppdev(AppDev appDev, String centerIds) throws ServiceException {
        if (appDev == null) {
            return null;
        }
        String uuid = appDev.getUuid();
        List<Long> ids = appdevDao.findIdsByUuid(uuid);
        if (EmptyObjectUtil.isEmptyList(ids)) {
            appdevDao.add(appDev);
        } else {
            int size = ids.size();
            if (size == 1) {
                appDev.setId(ids.get(0));
                appdevDao.update(appDev);
                return appDev;
            }
            if (size > 1) {
                appdevDao.deleteByUuid(uuid);
                appdevDao.add(appDev);
            }
        }
        List<Long> dbCenterIds = appdevDao.findCenterIdsByUuid(uuid);
        if (!dbCenterIds.isEmpty()) {
            appdevDao.deleteAppCenter(uuid, dbCenterIds);
        }
        String[] centerIdArr = StringUtil.splitStrWithComma(centerIds);
        List<Long> newCenter = new ArrayList<>();
        for (String s : centerIdArr) {
            Long centerId = Long.parseLong(s, 10);
            newCenter.add(centerId);
        }
        if (!newCenter.isEmpty()) {
            appdevDao.addAppCenter(uuid, newCenter);
            for (Long centerId : newCenter) {
                try {
                    AppInfo appInfo = appdevDao.getCenterWebAddr(centerId);
                    String url = new StringBuffer().append("https://").append(appInfo.getIp()).append(':')
                            .append(appInfo.getPort()).append("/api/appdev/add").toString();
                    FormBody formBody = new FormBody.Builder().add("appdev", JSONUtil.stringify(appDev)).build();
                    OkHttpUtil.post(url, formBody);
                } catch (Exception e) {
                    logger.error("向用户中心[{}]同步新增APP设备信息异常！", centerId, e);
                }
            }
        }

        return appDev;
    }

    @Override
    public AppDev updateAppdev(AppDev appDev, String centerIds) throws ServiceException {
        if (appDev == null) {
            return null;
        }
        appdevDao.update(appDev);
        List<Long> dbCenterIds = appdevDao.findCenterIdsByAppdevId(appDev.getId());

        String[] centerIdArr = StringUtil.splitStrWithComma(centerIds);
        List<Long> newCenter = new ArrayList<>();
        List<Long> upCenter = new ArrayList<>();

        for (String s : centerIdArr) {
            Long centerId = Long.parseLong(s, 10);
            if (dbCenterIds.contains(centerId)) {
                upCenter.add(centerId);
                dbCenterIds.remove(centerId);
            } else {
                newCenter.add(centerId);
            }
        }
        String uuid = appDev.getUuid();
        Long  id = appDev.getId();
        if (!newCenter.isEmpty()) {
            appdevDao.addAppCenter(uuid, newCenter);
            appDev = appdevDao.getById(id);
            for (Long centerId : newCenter) {
                try {
                    AppInfo appInfo = appdevDao.getCenterWebAddr(centerId);
                    String url = new StringBuffer().append("https://").append(appInfo.getIp()).append(':')
                            .append(appInfo.getPort()).append("/api/appdev/add").toString();
                    FormBody formBody = new FormBody.Builder().add("appdev", JSONUtil.stringify(appDev)).build();
                    OkHttpUtil.post(url, formBody);
                } catch (Exception e) {
                    logger.error("向用户中心[{}]同步新增APP设备信息异常！", centerId, e);
                }
            }
        }
        if (!dbCenterIds.isEmpty()) {
            appdevDao.deleteAppCenter(uuid, dbCenterIds);
            for (Long centerId : dbCenterIds) {
                try {
                    AppInfo appInfo = appdevDao.getCenterWebAddr(centerId);
                    String url = new StringBuffer().append("https://").append(appInfo.getIp()).append(':')
                            .append(appInfo.getPort()).append("/api/appdev/delete").toString();
                    FormBody formBody = new FormBody.Builder().add("id", id.toString()).build();
                    OkHttpUtil.post(url, formBody);
                } catch (Exception e) {
                    logger.error("向用户中心[{}]同步删除APP设备信息异常！", centerId, e);
                }
            }
        }
        if (!upCenter.isEmpty()) {
            for (Long centerId : upCenter) {
                try {
                    AppInfo appInfo = appdevDao.getCenterWebAddr(centerId);
                    String url = new StringBuffer().append("https://").append(appInfo.getIp()).append(':')
                            .append(appInfo.getPort()).append("/api/appdev/update").toString();
                    FormBody formBody = new FormBody.Builder().add("appdev", JSONUtil.stringify(appDev)).build();
                    OkHttpUtil.post(url, formBody);
                } catch (Exception e) {
                    logger.error("向用户中心[{}]同步更新APP设备信息异常！", centerId, e);
                }
            }
        }
        return appDev;
    }

    @Override
    public void updateModelAndCurrentVerByUuid(String model, String currentVer, String uuid) throws ServiceException {
        appdevDao.updateModelAndCurrentVerByUuid(model, currentVer, uuid);
    }

    @Override
    public void deleteAppdevById(Long id) throws ServiceException {
        List<Long> centerIds = appdevDao.findCenterIdsByAppdevId(id);
        appdevDao.deleteAppCenterByAppdevId(id);
        appdevDao.delete(id);
        if (!centerIds.isEmpty()) {
            for (Long centerId : centerIds) {
                try {
                    AppInfo appInfo = appdevDao.getCenterWebAddr(centerId);
                    String url = new StringBuffer().append("https://").append(appInfo.getIp()).append(':')
                            .append(appInfo.getPort()).append("/api/appdev/delete").toString();
                    FormBody formBody = new FormBody.Builder().add("id", id.toString()).build();
                    OkHttpUtil.post(url, formBody);
                } catch (Exception e) {
                    logger.error("向用户中心[{}]同步删除APP设备信息异常！", centerId, e);
                }
            }
        }
    }

    @Override
    public AppDev getAppdevById(Long id) {
        return null == id ? null : appdevDao.getById(id);
    }

    @Override
    public AppDev getAppdevByUuid(String uuid) {
        return StringUtil.isEmpty(uuid) ? null : appdevDao.getByUuid(uuid);
    }

    @Override
    public boolean isAppdevExist(String uuid) {
        Integer num = appdevDao.countByUuid(uuid);
        if (num == null) {
            return false;
        }
        return num > 0;
    }

    @Override
    public List<AppDev> findAllAppdev() {
        return appdevDao.findAll();
    }

    @Override
    public long countAppdev(AppDev appDev) {
        return appDev == null ? 0 : appdevDao.count(appDev);
    }

    @Override
    public List<AppDev> findByPage(AppDev appDev, Page page) {
        return appdevDao.findByPage(appDev, page);
    }

    @Override
    public GridPage<AppDev> findAppdevForPage(AppDev appDev, Page page) {
        long records = countAppdev(appDev);
        List<AppDev> list = findByPage(appDev, page);
        return new GridPage<>(list, records, page.getPageId(), page.getRows(), list.size(), appDev);
    }

    @Override
    public List<String> findCenterNamesByAppdevId(Long appdevId) {
        return null == appdevId ? null : appdevDao.findCenterNamesByAppdevId(appdevId);
    }

    @Override
    public List<Center> findBelongCentersByAppdevId(Long appdevId) {
        return null == appdevId ? null : appdevDao.findBelongCentersByAppdevId(appdevId);
    }

    @Override
    public List<AppCenter> findAppCentersByAppdevId(Long appdevId) {
        return null == appdevId ? null : appdevDao.findAppCentersByAppdevId(appdevId);
    }

    @Override
    public List<Long> findCenterIdsByUuid(String uuid) {
        return StringUtil.isEmpty(uuid) ? Collections.emptyList() : appdevDao.findCenterIdsByUuid(uuid);
    }

    @Override
    public List<Center> findCentersByUuid(String uuid) {
        return StringUtil.isEmpty(uuid) ? null : appdevDao.findCentersByUuid(uuid);
    }

    @Override
    public List<String> findUuidsByCenterId(Long centerId) {
        return null == centerId ? null : appdevDao.findUuidsByCenterId(centerId);
    }

    @Override
    public AppSync findAppSync(Long centerId) {
        List<AppDev> appDevs = appdevDao.findByCenterId(centerId);
        List<AppVer> appVers = appverDao.findByCenterId(centerId);
        AppSync appSync = new AppSync();
        appSync.setAppdevs(appDevs);
        appSync.setAppvers(appVers);
        return appSync;
    }

    @Override
    public AppInfo getCenterWebAddr(Long centerId) {
        return null == centerId ? null : appdevDao.getCenterWebAddr(centerId);
    }
}
