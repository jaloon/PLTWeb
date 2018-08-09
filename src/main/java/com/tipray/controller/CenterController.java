package com.tipray.controller;

import com.tipray.bean.*;
import com.tipray.constant.LogDescriptionEnum;
import com.tipray.core.annotation.LogAnno;
import com.tipray.core.annotation.PermissionAnno;
import com.tipray.core.base.BaseAction;
import com.tipray.core.exception.ServiceException;
import com.tipray.service.CenterService;
import com.tipray.util.JSONUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/manage/center")
/* @Scope("prototype") */
public class CenterController extends BaseAction {
	@Resource
	private CenterService centerService;

	@RequestMapping(value = "dispatch.do")
	public String dispatch(String mode, Long id, ModelMap modelMap) {
		modelMap.put("mode", mode);
		Center center = new Center();
		if (id > 0) {
			center = centerService.getCenterById(id);
		}
		modelMap.put("center", center);
		return "normal/center/centerEdit.jsp";
	}

	@LogAnno(LogDescriptionEnum.CENTER_ADD)
	@PermissionAnno("centerManage")
	@RequestMapping(value = "add.do", method = RequestMethod.POST)
	@ResponseBody
	public Message addcenter(@ModelAttribute Center center, String ftp) {
		try {
			FtpConfig ftpConfig = JSONUtil.parseToObject(ftp, FtpConfig.class);
			center.setFtpConfig(ftpConfig);
			centerService.addCenter(center);
			return Message.success();
		} catch (Exception e) {
			return Message.error(e);
		}
	}

	@LogAnno(LogDescriptionEnum.CENTER_ALTER)
	@PermissionAnno("centerManage")
	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	@ResponseBody
	public Message updatecenter(@ModelAttribute Center center, String ftp) {
		try {
			FtpConfig ftpConfig = JSONUtil.parseToObject(ftp, FtpConfig.class);
			center.setFtpConfig(ftpConfig);
			centerService.updateCenter(center);
			return Message.success();
		} catch (Exception e) {
			return Message.error(e);
		}
	}

	@LogAnno(LogDescriptionEnum.CENTER_DELETE)
	@PermissionAnno("centerManage")
	@RequestMapping(value = "delete.do", method = RequestMethod.POST)
	@ResponseBody
	public Message deletecenter(Long id) {
		try {
			centerService.deleteCenterById(id);
			return Message.success();
		} catch (ServiceException e) {
			return Message.error(e);
		}
	}

	@RequestMapping(value = "isExist.do")
	@ResponseBody
	public Boolean isCenterExist(String centerName) {
		return centerService.getByName(centerName) != null;
	}

	@PermissionAnno("centerManage")
	@RequestMapping(value = "ajaxFindForPage.do")
	@ResponseBody
	public GridPage<Center> ajaxFindcentersForPage(@ModelAttribute Center center, @ModelAttribute Page page) {
		GridPage<Center> gridPage = centerService.findcenterForPage(center, page);
		return gridPage;
	}

	@RequestMapping(value = "getCenterList.do")
	@ResponseBody
	public List<Center> getCenterList() {
		return centerService.findAllCenters();
	}

}
