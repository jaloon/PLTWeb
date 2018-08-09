package com.tipray.controller;

import com.tipray.bean.*;
import com.tipray.constant.LogDescriptionEnum;
import com.tipray.core.annotation.LogAnno;
import com.tipray.core.annotation.PermissionAnno;
import com.tipray.core.base.BaseAction;
import com.tipray.core.exception.ServiceException;
import com.tipray.service.CenterService;
import com.tipray.service.DeviceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/manage/device")
/* @Scope("prototype") */
public class DeviceController extends BaseAction {
	@Resource
	private DeviceService deviceService;
	@Resource
	private CenterService centerService;

	@RequestMapping(value = "dispatch.do")
	public String dispatch(String mode, Long id, ModelMap modelMap) {
		modelMap.put("mode", mode);
		Device device = new Device();
		if (id > 0) {
			device = deviceService.getDeviceById(id);
		}
        if (Mode.EDIT.equalsIgnoreCase(mode) || Mode.ADD.equalsIgnoreCase(mode)) {
            List<Center> centers = centerService.getCenterList();
            modelMap.put("centers", centers);
        }
		modelMap.put("device", device);
		return "normal/device/deviceEdit.jsp";
	}

	@LogAnno(LogDescriptionEnum.DEVICE_ADD)
	@PermissionAnno("deviceManage")
	@RequestMapping(value = "add.do", method = RequestMethod.POST)
	@ResponseBody
	public Message addDevice(@ModelAttribute Device device) {
		try {
			deviceService.addDevice(device);
			return Message.success();
		} catch (ServiceException e) {
			return Message.error(e);
		}
	}

	@LogAnno(LogDescriptionEnum.DEVICE_ALTER)
	@PermissionAnno("deviceManage")
	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	@ResponseBody
	public Message updateDevice(@ModelAttribute Device device) {
		try {
			deviceService.updateDevice(device);
			return Message.success();
		} catch (ServiceException e) {
			return Message.error(e);
		}
	}

	@LogAnno(LogDescriptionEnum.DEVICE_DELETE)
	@PermissionAnno("deviceManage")
	@RequestMapping(value = "delete.do", method = RequestMethod.POST)
	@ResponseBody
	public Message deleteDevice(Long id) {
		try {
			deviceService.deleteDeviceById(id);
			return Message.success();
		} catch (ServiceException e) {
			return Message.error(e);
		}
	}

	@RequestMapping(value = "isExist.do")
	@ResponseBody
	public Boolean isDeviceExist(Integer deviceId) {
		return deviceService.getDeviceByDeviceId(deviceId) != null;
	}

	@RequestMapping(value = "getNextId.do")
	@ResponseBody
	public Integer getNextDeviceIdByType(Integer type) {
		return deviceService.getNextDeviceIdByType(type);
	}

	@PermissionAnno("deviceManage")
	@RequestMapping(value = "ajaxFindForPage.do")
	@ResponseBody
	public GridPage<Device> ajaxFindDevicesForPage(@ModelAttribute Device device, @ModelAttribute Page page) {
		GridPage<Device> gridPage = deviceService.findDeviceForPage(device, page);
		return gridPage;
	}

	@RequestMapping(value = "getCenterList.do")
	@ResponseBody
	public List<Center> getCenterList() {
		return centerService.getCenterList();
	}

}
