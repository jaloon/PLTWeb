package com.tipray.service.impl;

import com.tipray.bean.AppCenter;
import com.tipray.bean.AppDev;
import com.tipray.bean.AppInfo;
import com.tipray.bean.AppSync;
import com.tipray.bean.AppVer;
import com.tipray.bean.Center;
import com.tipray.bean.GridPage;
import com.tipray.bean.Page;
import com.tipray.bean.ResponseMsg;
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
import java.util.ArrayList;
import java.util.List;

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
        if (StringUtil.isEmpty(uuid)) {
            throw new IllegalArgumentException("uuid为空！");
        }
        String appid = appDev.getAppid();
        if (StringUtil.isEmpty(appid)) {
            throw new IllegalArgumentException("appid为空！");
        }
        List<Long> ids = appdevDao.findIdsByUuidAndAppid(uuid, appid);
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
                appdevDao.deleteByUuidAndAppid(uuid, appid);
                appdevDao.add(appDev);
            }
        }
        List<Long> dbCenterIds = appdevDao.findCenterIdsByUuidAndAppid(uuid, appid);
        if (!dbCenterIds.isEmpty()) {
            appdevDao.deleteAppCenter(uuid, appid, dbCenterIds);
        }
        String[] centerIdArr = StringUtil.splitStrWithComma(centerIds);
        List<Long> newCenter = new ArrayList<>();
        for (String s : centerIdArr) {
            Long centerId = Long.parseLong(s, 10);
            newCenter.add(centerId);
        }
        if (!newCenter.isEmpty()) {
            appdevDao.addAppCenter(uuid, appid, newCenter);
            for (Long centerId : newCenter) {
                try {
                    AppInfo appInfo = appdevDao.getCenterWebAddr(centerId);
                    String url = new StringBuffer().append("https://").append(appInfo.getIp()).append(':')
                            .append(appInfo.getPort()).append("/api/appdev/add").toString();
                    FormBody formBody = new FormBody.Builder().add("appdev", JSONUtil.stringify(appDev)).build();
                    String reply = OkHttpUtil.post(url, formBody);
                    ResponseMsg responseMsg = JSONUtil.parseToObject(reply, ResponseMsg.class);
                    if (responseMsg.getId() > 0) {
                        logger.warn("向用户中心[{}]同步新增APP设备信息失败：{}", centerId, responseMsg.getMsg());
                    }
                } catch (Exception e) {
                    logger.error("向用户中心[" + centerId + "]同步新增APP设备信息异常！", e);
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
        Long appdevId = appDev.getId();
        List<Long> dbCenterIds = appdevDao.findCenterIdsByAppdevId(appdevId);

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
        appDev = appdevDao.getById(appdevId);
        String uuid = appDev.getUuid();
        String appid = appDev.getAppid();
        if (!newCenter.isEmpty()) {
            appdevDao.addAppCenter(uuid, appid, newCenter);
            for (Long centerId : newCenter) {
                try {
                    AppInfo appInfo = appdevDao.getCenterWebAddr(centerId);
                    String url = new StringBuffer().append("https://").append(appInfo.getIp()).append(':')
                            .append(appInfo.getPort()).append("/api/appdev/add").toString();
                    FormBody formBody = new FormBody.Builder().add("appdev", JSONUtil.stringify(appDev)).build();
                    String reply = OkHttpUtil.post(url, formBody);
                    ResponseMsg responseMsg = JSONUtil.parseToObject(reply, ResponseMsg.class);
                    if (responseMsg.getId() > 0) {
                        logger.warn("向用户中心[{}]同步新增APP设备信息失败：{}", centerId, responseMsg.getMsg());
                    }
                } catch (Exception e) {
                    logger.error("向用户中心[" + centerId + "]同步新增APP设备信息异常！", e);
                }
            }
        }
        if (!dbCenterIds.isEmpty()) {
            appdevDao.deleteAppCenter(uuid, appid, dbCenterIds);
            for (Long centerId : dbCenterIds) {
                try {
                    AppInfo appInfo = appdevDao.getCenterWebAddr(centerId);
                    String url = new StringBuffer().append("https://").append(appInfo.getIp()).append(':')
                            .append(appInfo.getPort()).append("/api/appdev/delete").toString();
                    FormBody formBody = new FormBody.Builder().add("id", appdevId.toString()).build();
                    String reply = OkHttpUtil.post(url, formBody);
                    ResponseMsg responseMsg = JSONUtil.parseToObject(reply, ResponseMsg.class);
                    if (responseMsg.getId() > 0) {
                        logger.warn("向用户中心[{}]同步删除APP设备信息失败：{}", centerId, responseMsg.getMsg());
                    }
                } catch (Exception e) {
                    logger.error("向用户中心[" + centerId + "]同步删除APP设备信息异常！", e);
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
                    String reply = OkHttpUtil.post(url, formBody);
                    ResponseMsg responseMsg = JSONUtil.parseToObject(reply, ResponseMsg.class);
                    if (responseMsg.getId() > 0) {
                        logger.warn("向用户中心[{}]同步更新APP设备信息失败：{}", centerId, responseMsg.getMsg());
                    }
                } catch (Exception e) {
                    logger.error("向用户中心[" + centerId + "]同步更新APP设备信息异常！", e);
                }
            }
        }
        return appDev;
    }

    @Override
    public void updateModelAndCurrentVerByUuidAndAppid(String model, String currentVer, String uuid, String appid) throws ServiceException {
        appdevDao.updateModelAndCurrentVerByUuidAndAppid(model, currentVer, uuid, appid);
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
                    String reply = OkHttpUtil.post(url, formBody);
                    ResponseMsg responseMsg = JSONUtil.parseToObject(reply, ResponseMsg.class);
                    if (responseMsg.getId() > 0) {
                        logger.warn("向用户中心[{}]同步删除APP设备信息失败：{}", centerId, responseMsg.getMsg());
                    }
                } catch (Exception e) {
                    logger.error("向用户中心[" + centerId + "]同步删除APP设备信息异常！", e);
                }
            }
        }
    }

    @Override
    public AppDev getAppdevById(Long id) {
        return null == id ? null : appdevDao.getById(id);
    }

    @Override
    public AppDev getAppdevByUuidAndAppid(String uuid, String appid) {
        return appdevDao.getByUuidAndAppid(uuid, appid);
    }

    @Override
    public boolean isAppdevExist(String uuid, String appid) {
        Integer num = appdevDao.countByUuidAndAppid(uuid, appid);
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
    public List<Long> findCenterIdsByUuidAndAppid(String uuid, String appid) {
        return appdevDao.findCenterIdsByUuidAndAppid(uuid, appid);
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
