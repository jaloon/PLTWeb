package com.tipray.service.impl;

import com.tipray.bean.Device;
import com.tipray.bean.GridPage;
import com.tipray.bean.Page;
import com.tipray.constant.DeviceConst;
import com.tipray.core.exception.ServiceException;
import com.tipray.dao.DeviceDao;
import com.tipray.service.DeviceService;
import com.tipray.util.BytesUtil;
import com.tipray.util.EmptyObjectUtil;
import com.tipray.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Transactional
@Service("deviceService")
public class DeviceServiceImpl implements DeviceService {
	@Resource
	private DeviceDao deviceDao;

	@Override
	public Device addDevice(Device device) throws ServiceException {
		if (device != null) {
			Integer type = device.getType();
			Integer deviceId = getUnusedDeviceId(type);
            if (deviceId == null) {
                device.setDeviceId(getNewDeviceId(type));
                deviceDao.add(device);
            } else {
                device.setDeviceId(deviceId);
                deviceDao.updateByDeviceId(device);
            }
		}
		return device;
	}

	@Override
	public Device updateDevice(Device device) throws ServiceException {
		if (device != null) {
			deviceDao.update(device);
		}
		return device;
	}

	@Override
	public void deleteDeviceById(Long id) throws ServiceException {
		deviceDao.delete(id);
	}

	@Override
	public Device getDeviceById(Long id) {
        return id == null ? null : deviceDao.getById(id);
	}

	@Override
	public List<Device> findAllDevices() {
        return deviceDao.findAll();
	}

	@Override
	public List<Device> findByCenter(String centerName) {
        return StringUtil.isEmpty(centerName) ? null : deviceDao.findByCenter(centerName);
	}

	@Override
	public List<Device> findByCenterId(Long centerId) {
        return centerId == null ? null : deviceDao.findByCenterId(centerId);
	}

	@Override
	public List<Device> findByType(Integer deviceType) {
        return deviceType == null ? null : deviceDao.findByType(deviceType);
	}

	@Override
	public List<Device> findByCenterAndType(String centerName, Integer deviceType) {
        return deviceDao.findByCenterAndType(centerName, deviceType);
	}

	@Override
	public long countDevice(Device device) {
		return deviceDao.count(device);
	}

	@Override
	public List<Device> findByPage(Device device, Page page) {
        return deviceDao.findByPage(device, page);
	}

	@Override
	public GridPage<Device> findDeviceForPage(Device device, Page page) {
		long records = countDevice(device);
		List<Device> list = findByPage(device, page);
		return new GridPage<Device>(list, records, page.getPageId(), page.getRows(), list.size(), device);
	}

	@Override
	public Device getDeviceByDeviceId(Integer deviceId) {
		return deviceDao.getByDeviceId(deviceId);
	}

	@Override
	public Map<String, Object> getCenterByDeviceId(Integer id) {
		Map<String, Object> center = deviceDao.getCenterByDeviceId(id);
//		Map<String, Object> ftp = deviceDao.getFtpConfigByDeviceId(id);
//		if (center != null) {
//			center.put("ftp", ftp);
//		}
		return center;
	}

	@Override
	public List<Device> findMaxDeviceIdsOfType() {
		return deviceDao.findMaxDeviceIdsOfType();
	}

	@Override
	public Integer getNextDeviceIdByType(Integer type) {
		Integer deviceId = getUnusedDeviceId(type);
		if (deviceId != null) {
            return deviceId;
		}
		return getNewDeviceId(type);
	}

    /**
     * 获取一个未使用设备ID
     * @param type 设备类型
     * @return 一个未使用设备ID
     */
	private Integer getUnusedDeviceId(Integer type) {
        List<Integer> unusedDeviceIds = deviceDao.findUnusedDeviceIdByType(type);
        if (EmptyObjectUtil.isEmptyList(unusedDeviceIds)) {
            return null;
        }
        return unusedDeviceIds.get(0);
    }

    /**
     * 获取一个新的设备ID
     * @param type 设备类型
     * @return 一个新的设备ID
     */
    private Integer getNewDeviceId(Integer type) {
        Integer deviceId = deviceDao.getMaxDeviceIdByType(type);
        if (deviceId == null) {
            return (type << 24) | 1;
        }
        return deviceId + 1;
    }

	@Override
	public byte[] getCenterRc4ByCenterId(Integer id) {
		String rc4Hex = deviceDao.getCenterRc4ByCenterId(id);
		return BytesUtil.hexStringToBytes(rc4Hex);
	}

	@Override
	public Map<String, Object> getBarrierByCenterId(Integer id) {
		Map<String, Object> barrier = deviceDao.getBarrierByCenterId(id);
		return barrier;
	}

    @Override
    public Integer applyForDeviceId(Integer type) {
        Integer deviceId = getUnusedDeviceId(type);
        if (deviceId == null) {
            deviceId = getNewDeviceId(type);
            Device device = new Device();
            device.setDeviceId(deviceId);
            device.setType(type);
            deviceDao.addToUseDeviceId(device);
        } else {
            deviceDao.upDeviceIdStatus(deviceId, DeviceConst.DEVICE_ID_STATUS_1_TO_USE);
        }
        return deviceId;
    }

    @Override
    public void upDeviceIdStatus(Integer deviceId) {
        deviceDao.upDeviceIdStatus(deviceId, DeviceConst.DEVICE_ID_STATUS_2_USING);
    }

    @Override
    public Integer getDeviceIdStatus(Integer deviceId) {
        Integer count = deviceDao.countDeviceId(deviceId);
        if (count == null || count == 0) {
            throw new IllegalStateException("设备ID不存在");
        }
        if (count > 1) {
            throw new IllegalStateException("设备ID不唯一");
        }
        return deviceDao.getDeviceIdStatus(deviceId);
    }

    @Override
    public boolean isDeviceIdExist(Integer deviceId) {
        Integer count = deviceDao.countDeviceId(deviceId);
        if (count == null) {
            return false;
        }
        return count > 0;
    }

    @Override
    public void upToUseDeviceIdUnused() {
        deviceDao.upToUseDeviceIdUnused();
    }

}
