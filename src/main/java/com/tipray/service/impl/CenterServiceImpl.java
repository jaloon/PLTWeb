package com.tipray.service.impl;

import com.tipray.bean.Center;
import com.tipray.bean.FtpConfig;
import com.tipray.bean.GridPage;
import com.tipray.bean.Page;
import com.tipray.core.exception.ServiceException;
import com.tipray.dao.CenterDao;
import com.tipray.dao.DeviceDao;
import com.tipray.service.CenterService;
import com.tipray.util.RC4Util;
import com.tipray.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Transactional
@Service("centerService")
public class CenterServiceImpl implements CenterService {
	@Resource
	private CenterDao centerDao;
	@Resource
	private DeviceDao deviceDao;

	@Override
	public Center addCenter(Center center) throws ServiceException {
		if (center != null) {
		    String name = center.getName();
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("中心名称为空！");
            }
			// 自动生成rc4密码
			center.setRc4(RC4Util.createBinaryKey());
			byte[] rc4Version = { 1 };
			center.setRc4Version(rc4Version);
			Integer count = centerDao.countByCenterName(name);
            if (count == null || count == 0) {
                centerDao.add(center);
            } else if (count == 1) {
                centerDao.updateByCenterName(center);
            } else {
                centerDao.deleteByCenterName(name);
                centerDao.add(center);
            }
			FtpConfig ftpConfig = center.getFtpConfig();
			ftpConfig.setCenterId(center.getId());
			centerDao.addFtp(ftpConfig);
		}
		return center;
	}

	@Override
	public Center updateCenter(Center center) throws ServiceException {
		if (center != null) {
			// updateRc4Version(center.code());
			centerDao.update(center);
			FtpConfig ftpConfig = center.getFtpConfig();
			ftpConfig.setCenterId(center.getId());
			centerDao.updateFtp(ftpConfig);
		}
		return center;
	}

	@Override
	public void deleteCenterById(Long id) throws ServiceException {
		centerDao.delete(id);
		centerDao.deleteFtp(id);
		deviceDao.deleteDevicesWithCenterId(id);
	}

	@Override
	public Center getCenterById(Long id) {
		return id == null ? null : centerDao.getById(id);
	}

	@Override
	public Center getByName(String centerName) {
		return StringUtil.isEmpty(centerName) ? null : centerDao.getByName(centerName);
	}

	@Override
	public List<Center> findAllCenters() {
		return centerDao.findAll();
	}

	@Override
	public long countCenter(Center center) {
		return centerDao.count(center);
	}

	@Override
	public List<Center> findByPage(Center center, Page page) {
		return centerDao.findByPage(center, page);
	}

	@Override
	public GridPage<Center> findcenterForPage(Center center, Page page) {
		long records = countCenter(center);
		List<Center> list = findByPage(center, page);
		return new GridPage<Center>(list, records, page.getPageId(), page.getRows(), list.size(), center);
	}

	@Override
    public List<Center> getCenterList() {
        return centerDao.getCenterList();
    }

	@Override
	public Map<String, Object> getIpAndWebport(Integer centerId) {
		return centerId == null ? null : centerDao.getIpAndWebport(centerId);
	}

	/**
	 * 根据用户中心ID更新RC4秘钥版本号
	 * 
	 * @param centerId
	 *            用户中心ID
	 * @return 新的RC4秘钥版本号
	 */
	@SuppressWarnings("unused")
	private byte[] updateRc4Version(Long centerId) {
		String rc4Info = deviceDao.getCenterRc4ByCenterId(centerId.intValue());
		String verhex = rc4Info.substring(rc4Info.length() - 2);
		byte ver = Byte.parseByte(verhex, 16);
		if (ver == -1) {
			ver = 1;
		} else {
			ver = (byte) (ver + 1);
		}
		byte[] rc4Version = { ver };
		return rc4Version;
	}
}
