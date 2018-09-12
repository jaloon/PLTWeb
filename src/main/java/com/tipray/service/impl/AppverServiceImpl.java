package com.tipray.service.impl;

import com.tipray.bean.AppInfo;
import com.tipray.bean.AppVer;
import com.tipray.bean.GridPage;
import com.tipray.bean.Page;
import com.tipray.bean.ResponseMsg;
import com.tipray.core.exception.ServiceException;
import com.tipray.dao.AppverDao;
import com.tipray.service.AppverService;
import com.tipray.util.JSONUtil;
import com.tipray.util.OkHttpUtil;
import okhttp3.FormBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chenlong
 * @version 1.0 2018-07-27
 */
@Transactional
@Service("appverService")
public class AppverServiceImpl implements AppverService {
    private final Logger logger = LoggerFactory.getLogger(AppverServiceImpl.class);
    @Resource
    private AppverDao appverDao;

    @Override
    public AppVer addAppver(AppVer appVer) throws ServiceException {
        if (appVer == null) {
            return null;
        }
        appverDao.add(appVer);
        Long centerId = appVer.getCenterId();
        if (centerId > 0) {
            try {
                AppInfo appInfo = appverDao.getCenterWebAddrByCenterId(centerId);
                String url = new StringBuffer().append("https://").append(appInfo.getIp()).append(':')
                        .append(appInfo.getPort()).append("/api/appver/add").toString();
                FormBody formBody = new FormBody.Builder().add("appver", JSONUtil.stringify(appVer)).build();
                String reply = OkHttpUtil.post(url, formBody);
                ResponseMsg responseMsg = JSONUtil.parseToObject(reply, ResponseMsg.class);
                if (responseMsg.getId() > 0) {
                    logger.warn("向用户中心[{}]同步新增APP版本信息失败：{}", centerId, responseMsg.getMsg());
                }
            } catch (Exception e) {
                logger.error("向用户中心[" + centerId + "]同步新增APP版本信息异常！", e);
            }
        }
        return appVer;
    }

    @Override
    public AppVer updateAppver(AppVer appVer) throws ServiceException {
        if (appVer == null) {
            return null;
        }
        appverDao.update(appVer);
        int centerId = 0;
        try {
            AppInfo appInfo = appverDao.getCenterWebAddrById(appVer.getId());
            if (appInfo != null) {
                centerId = appInfo.getCenter_id();
                String url = new StringBuffer().append("https://").append(appInfo.getIp()).append(':')
                        .append(appInfo.getPort()).append("/api/appver/update").toString();
                FormBody formBody = new FormBody.Builder().add("appver", JSONUtil.stringify(appVer)).build();
                String reply = OkHttpUtil.post(url, formBody);
                ResponseMsg responseMsg = JSONUtil.parseToObject(reply, ResponseMsg.class);
                if (responseMsg.getId() > 0) {
                    logger.warn("向用户中心[{}]同步新增APP版本信息失败：{}", centerId, responseMsg.getMsg());
                }
            }
        } catch (Exception e) {
            logger.error("向用户中心[" + centerId + "]同步更新APP版本信息异常！", e);
        }
        return appVer;
    }

    @Override
    public void deleteAppverById(Long id) throws ServiceException {
        if (id == null) {
            return;
        }
        AppInfo appInfo = appverDao.getCenterWebAddrById(id);
        appverDao.delete(id);
        if (appInfo != null) {
            int centerId = appInfo.getCenter_id();
            try {
                String url = new StringBuffer().append("https://").append(appInfo.getIp()).append(':')
                        .append(appInfo.getPort()).append("/api/appver/delete").toString();
                FormBody formBody = new FormBody.Builder().add("id", id.toString()).build();
                String reply = OkHttpUtil.post(url, formBody);
                ResponseMsg responseMsg = JSONUtil.parseToObject(reply, ResponseMsg.class);
                if (responseMsg.getId() > 0) {
                    logger.warn("向用户中心[{}]同步删除APP版本信息失败：{}", centerId, responseMsg.getMsg());
                }
            } catch (Exception e) {
                logger.error("向用户中心[" + centerId + "]同步删除APP版本信息异常！", e);
            }
        }
    }

    @Override
    public AppVer getAppverById(Long id) {
        if (id == null) {
            return null;
        }
        AppVer appVer = appverDao.getById(id);
        if (appVer.getCenterName() == null) {
            Long centerId = appVer.getCenterId();
            if (!centerId.equals(0L)) {
                return null;
            }
            appVer.setCenterName("所有中心");
        }
        return appVer;
    }

    @Override
    public boolean isAppverExist(Long centerId, String appid, String system) {
        if (centerId == null || appid == null || system == null) {
           throw new IllegalArgumentException("参数为空！");
        }
        Integer num = appverDao.countCenterAppVer(centerId, appid, system);
        if (num == null) {
            return false;
        }
        return num > 0;
    }

    @Override
    public String getAssignVerByAppver(Long centerId, String appid, String system) {
        AppVer appVer = new AppVer();
        appVer.setCenterId(centerId);
        appVer.setAppid(appid);
        appVer.setSystem(system);
        return appverDao.getAssignVerByAppver(appVer);
    }

    @Override
    public List<AppVer> findAllAppvers() {
        return appverDao.findAll();
    }

    @Override
    public long countAppver(AppVer appVer) {
        return appVer == null ? 0 : appverDao.count(appVer);
    }

    @Override
    public List<AppVer> findByPage(AppVer appVer, Page page) {
        return appverDao.findByPage(appVer, page);
    }

    @Override
    public GridPage<AppVer> findAppverForPage(AppVer appVer, Page page) {
        long records = countAppver(appVer);
        List<AppVer> list = findByPage(appVer, page);
        return new GridPage<>(list, records, page.getPageId(), page.getRows(), list.size(), appVer);
    }

    @Override
    public List<AppVer> getDefaultVer() {
        return appverDao.getDefaultVer();
    }
}
