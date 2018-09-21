package com.tipray.service.impl;

import com.tipray.bean.AppInfo;
import com.tipray.bean.AppSync;
import com.tipray.bean.AppVer;
import com.tipray.bean.CenterDev;
import com.tipray.bean.GridPage;
import com.tipray.bean.Page;
import com.tipray.bean.ResponseMsg;
import com.tipray.dao.AppverDao;
import com.tipray.dao.CenterDevDao;
import com.tipray.service.CenterDevService;
import com.tipray.util.JSONUtil;
import com.tipray.util.OkHttpUtil;
import com.tipray.util.StringUtil;
import okhttp3.FormBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chenlong
 * @version 1.0 2018-09-12
 */
@Transactional
@Service("centerDevService")
public class CenterDevServiceImpl implements CenterDevService {
    private final Logger logger = LoggerFactory.getLogger(CenterDevServiceImpl.class);
    @Resource
    private CenterDevDao centerDevDao;
    @Resource
    private AppverDao appverDao;

    @Override
    public CenterDev addCenterDev(CenterDev centerDev) {
        if (centerDev != null) {
            centerDevDao.add(centerDev);
        }
        Long centerId = centerDev.getCenterId();
        try {
            AppInfo appInfo = appverDao.getCenterWebAddrByCenterId(centerId);
            if (appInfo != null) {
                String url = new StringBuffer().append("https://").append(appInfo.getIp()).append(':')
                        .append(appInfo.getPort()).append("/api/centerdev/add").toString();
                FormBody formBody = new FormBody.Builder().add("centerdev", JSONUtil.stringify(centerDev)).build();
                String reply = OkHttpUtil.post(url, formBody);
                ResponseMsg responseMsg = JSONUtil.parseToObject(reply, ResponseMsg.class);
                if (responseMsg.getId() > 0) {
                    logger.warn("向用户中心[{}]同步新增APP归属信息失败：{}", centerId, responseMsg.getMsg());
                }
            }
        } catch (Exception e) {
            logger.error("向用户中心[" + centerId + "]同步新增APP归属信息异常！", e);
        }
        return centerDev;
    }

    @Override
    public CenterDev updateCenterDev(CenterDev centerDev) {
        if (centerDev != null) {
            Long id = centerDev.getId();
            CenterDev dbCenterDev = centerDevDao.getById(id);
            Long oldCenterId = dbCenterDev.getCenterId();
            Long newCenterId = centerDev.getCenterId();
            if (!oldCenterId.equals(newCenterId)) {
                centerDevDao.update(centerDev);
                try {
                    AppInfo oldAppInfo = appverDao.getCenterWebAddrByCenterId(oldCenterId);
                    if (oldAppInfo != null) {
                        String url = new StringBuffer().append("https://").append(oldAppInfo.getIp()).append(':')
                                .append(oldAppInfo.getPort()).append("/api/centerdev/delete").toString();
                        FormBody formBody = new FormBody.Builder().add("id", id.toString()).build();
                        String reply = OkHttpUtil.post(url, formBody);
                        ResponseMsg responseMsg = JSONUtil.parseToObject(reply, ResponseMsg.class);
                        if (responseMsg.getId() > 0) {
                            logger.warn("向用户中心[{}]同步删除APP归属信息失败：{}", oldCenterId, responseMsg.getMsg());
                        }
                    }
                } catch (Exception e) {
                    logger.error("向用户中心[" + oldCenterId + "]同步删除APP归属信息异常！", e);
                }
                try {
                    AppInfo newAppInfo = appverDao.getCenterWebAddrByCenterId(newCenterId);
                    if (newAppInfo != null) {
                        centerDev.setUuid(dbCenterDev.getUuid());
                        String url = new StringBuffer().append("https://").append(newAppInfo.getIp()).append(':')
                                .append(newAppInfo.getPort()).append("/api/centerdev/add").toString();
                        FormBody formBody = new FormBody.Builder().add("centerdev", JSONUtil.stringify(centerDev)).build();
                        String reply = OkHttpUtil.post(url, formBody);
                        ResponseMsg responseMsg = JSONUtil.parseToObject(reply, ResponseMsg.class);
                        if (responseMsg.getId() > 0) {
                            logger.warn("向用户中心[{}]同步新增APP归属信息失败：{}", newCenterId, responseMsg.getMsg());
                        }
                    }
                } catch (Exception e) {
                    logger.error("向用户中心[" + newCenterId + "]同步新增APP归属信息异常！", e);
                }
            }
        }
        return centerDev;
    }

    @Override
    public void deleteCenterDevById(Long id) {
        if (id != null) {
            AppInfo appInfo = centerDevDao.getCenterWebAddrById(id);
            centerDevDao.delete(id);
            if (appInfo != null) {
                int centerId = appInfo.getCenter_id();
                try {
                    String url = new StringBuffer().append("https://").append(appInfo.getIp()).append(':')
                            .append(appInfo.getPort()).append("/api/centerdev/delete").toString();
                    FormBody formBody = new FormBody.Builder().add("id", id.toString()).build();
                    String reply = OkHttpUtil.post(url, formBody);
                    ResponseMsg responseMsg = JSONUtil.parseToObject(reply, ResponseMsg.class);
                    if (responseMsg.getId() > 0) {
                        logger.warn("向用户中心[{}]同步删除APP归属信息失败：{}", centerId, responseMsg.getMsg());
                    }
                } catch (Exception e) {
                    logger.error("向用户中心[" + centerId + "]同步删除APP归属信息异常！", e);
                }
            }
        }
    }

    @Override
    public CenterDev getCenterDevById(Long id) {
        return null == id ? null : centerDevDao.getById(id);
    }

    @Override
    public List<CenterDev> findAllCenterDev() {
        return centerDevDao.findAll();
    }

    @Override
    public long countCenterDev(CenterDev centerDev) {
        if (centerDev == null) {
            return 0;
        }
        return centerDevDao.count(centerDev);
    }

    @Override
    public List<CenterDev> findByPage(CenterDev centerDev, Page page) {
        return centerDevDao.findByPage(centerDev, page);
    }

    @Override
    public GridPage<CenterDev> findCenterDevForPage(CenterDev centerDev, Page page) {
        long records = countCenterDev(centerDev);
        List<CenterDev> list = findByPage(centerDev, page);
        return new GridPage<>(list, records, page.getPageId(), page.getRows(), list.size(), centerDev);
    }

    @Override
    public List<Long> findCenterIdsByUuid(String uuid) {
        return StringUtil.isEmpty(uuid) ? null : centerDevDao.findCenterIdsByUuid(uuid);
    }

    @Override
    public AppSync findAppSync(Long centerId) {
        List<AppVer> appVers = appverDao.findByCenterId(centerId);
        List<CenterDev> centerDevs = centerDevDao.findCenterDevsByCenterId(centerId);
        AppSync appSync = new AppSync();
        appSync.setAppvers(appVers);
        appSync.setCenterDevs(centerDevs);
        return appSync;
    }

    @Override
    public boolean isCenterdevExist(String uuid, Long centerId) {
        if (StringUtil.isEmpty(uuid)) {
            throw new IllegalArgumentException("uuid为空！");
        }
        if (centerId == null) {
            throw new IllegalArgumentException("centerId为空！");
        }
        Integer num = centerDevDao.countByUuidAndCenterId(uuid, centerId);
        if (num == null) {
            return false;
        }
        return num > 0;
    }
}
