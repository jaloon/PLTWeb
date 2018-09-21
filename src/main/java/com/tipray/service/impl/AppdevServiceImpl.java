package com.tipray.service.impl;

import com.tipray.bean.AppDev;
import com.tipray.bean.AppInfo;
import com.tipray.bean.GridPage;
import com.tipray.bean.Page;
import com.tipray.core.exception.ServiceException;
import com.tipray.dao.AppdevDao;
import com.tipray.service.AppdevService;
import com.tipray.util.EmptyObjectUtil;
import com.tipray.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chenlong
 * @version 1.0 2018-07-30
 */
@Transactional
@Service("appdevService")
public class AppdevServiceImpl implements AppdevService {
    @Resource
    private AppdevDao appdevDao;

    @Override
    public AppDev addAppdev(AppDev appDev) throws ServiceException {
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
        return appDev;
    }

    public AppDev updateAppdev(AppDev appDev) throws ServiceException {
        if (appDev != null) {
            appdevDao.update(appDev);
        }
        return appDev;
    }

    @Override
    public void updateModelAndCurrentVerByUuidAndAppid(String model, String currentVer, String uuid, String appid) throws ServiceException {
        appdevDao.updateModelAndCurrentVerByUuidAndAppid(model, currentVer, uuid, appid);
    }

    @Override
    public void deleteAppdevById(Long id) throws ServiceException {
        if (id != null) {
            appdevDao.delete(id);
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
    public AppInfo getCenterWebAddr(Long centerId) {
        return null == centerId ? null : appdevDao.getCenterWebAddr(centerId);
    }
}
