package com.tipray.test;

import com.tipray.bean.Device;
import com.tipray.core.exception.ServiceException;
import com.tipray.service.DeviceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class DeviceTest {
	@Resource
	private DeviceService deviceService;

	@Test
	public void addDevice() {
		Device device = new Device();
		device.setDeviceId(1);
		device.setType(2);
		device.setCenterId(2);

		Device device1 = new Device();
		device1.setDeviceId(2);
		device1.setType(1);
		device1.setCenterId(1);

		Device device2 = new Device();
		device2.setDeviceId(3);
		device2.setType(3);
		device2.setCenterId(3);

		Device device3 = new Device();
		device3.setDeviceId(4);
		device3.setType(4);
		device3.setCenterId(3);

		Device device4 = new Device();
		device4.setDeviceId(5);
		device4.setType(5);
		device4.setCenterId(4);

		Device device5 = new Device();
		device5.setDeviceId(6);
		device5.setType(2);
		device5.setCenterId(1);

		try {
			deviceService.addDevice(device);
			deviceService.addDevice(device1);
			deviceService.addDevice(device2);
			deviceService.addDevice(device3);
			deviceService.addDevice(device4);
			deviceService.addDevice(device5);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void updateDevice() {
		Device device = new Device();
		device.setId(4l);
		device.setType(2);
		device.setCenterId(2);
		try {
			deviceService.updateDevice(device);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void findAllDevices() {
		List<Device> dlist = deviceService.findAllDevices();
		System.out.println();
		for (Device device : dlist) {
			System.out.println(device.getId() + "：" + device);
		}
		System.out.println();
	}

	@Test
	public void deleteUser() {
		try {
			deviceService.deleteDeviceById(1l);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getById() {
		System.out.println(deviceService.getDeviceById(3l));
	}

	@Test
	public void findByCenter() {
		List<Device> dlist = deviceService.findByCenter("广西中心");
		System.out.println();
		for (Device device : dlist) {
			System.out.println(device.getId() + "：" + device);
		}
		System.out.println();
	}

	@Test
	public void findByType() {
		List<Device> dlist = deviceService.findByType(2);
		System.out.println();
		for (Device device : dlist) {
			System.out.println(device.getId() + "：" + device);
		}
		System.out.println();
	}

	@Test
	public void findByCenterAndType() {
		List<Device> dlist = deviceService.findByCenterAndType("广西中心", null);
		System.out.println();
		for (Device device : dlist) {
			System.out.println(device.getId() + "：" + device);
		}
		System.out.println();
	}
	
	@Test
	public void getNextDeviceIdByType() {
		Integer id=deviceService.getNextDeviceIdByType(6);
		String idHex=Integer.toHexString(id);
	}
}
