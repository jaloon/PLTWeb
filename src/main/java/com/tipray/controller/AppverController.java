package com.tipray.controller;

import com.tipray.bean.*;
import com.tipray.constant.LogDescriptionEnum;
import com.tipray.core.annotation.LogAnno;
import com.tipray.core.annotation.PermissionAnno;
import com.tipray.core.exception.ServiceException;
import com.tipray.service.AppverService;
import com.tipray.service.CenterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/manage/appver")
public class AppverController {
    @Resource
    private AppverService appverService;
    @Resource
    private CenterService centerService;

    @RequestMapping(value = "dispatch.do")
    public String dispatch(String mode, Long id, ModelMap modelMap) {
        modelMap.put("mode", mode);
        AppVer appVer = new AppVer();
        if (id > 0) {
            appVer = appverService.getAppverById(id);
        }
        if (Mode.ADD.equalsIgnoreCase(mode)) {
            List<Center> centers = centerService.getCenterList();
            modelMap.put("centers", centers);
        }
        modelMap.put("appVer", appVer);
        return "normal/appver/appverEdit.jsp";
    }

    @LogAnno(LogDescriptionEnum.APPVER_ADD)
    @PermissionAnno("appverManage")
    @RequestMapping(value = "add.do", method = RequestMethod.POST)
    @ResponseBody
    public Message addAppver(@ModelAttribute AppVer appVer) {
        try {
            appverService.addAppver(appVer);
            return Message.success();
        } catch (ServiceException e) {
            return Message.error(e);
        }
    }

    @LogAnno(LogDescriptionEnum.APPVER_ALTER)
    @PermissionAnno("appverManage")
    @RequestMapping(value = "update.do", method = RequestMethod.POST)
    @ResponseBody
    public Message updateAppver(@ModelAttribute AppVer appVer) {
        try {
            appverService.updateAppver(appVer);
            return Message.success();
        } catch (ServiceException e) {
            return Message.error(e);
        }
    }

    @LogAnno(LogDescriptionEnum.APPVER_DELETE)
    @PermissionAnno("appverManage")
    @RequestMapping(value = "delete.do", method = RequestMethod.POST)
    @ResponseBody
    public Message deleteAppConfig(Long id) {
        try {
            appverService.deleteAppverById(id);
            return Message.success();
        } catch (ServiceException e) {
            return Message.error(e);
        }
    }

    @RequestMapping(value = "isExist.do")
    @ResponseBody
    public Boolean isAppConfigExist(Long centerId, String appid, String system) {
        return appverService.isAppverExist(centerId, appid, system);
    }

    @PermissionAnno("appverManage")
    @RequestMapping(value = "ajaxFindForPage.do")
    @ResponseBody
    public GridPage<AppVer> ajaxFindAppverForPage(@ModelAttribute AppVer appVer, @ModelAttribute Page page) {
        GridPage<AppVer> gridPage = appverService.findAppverForPage(appVer, page);
        return gridPage;
    }

    @PermissionAnno("appverManage")
    @RequestMapping(value = "getDefaultVer.do")
    @ResponseBody
    public List<AppVer> getDefaultVer() {
        return appverService.getDefaultVer();
    }
}