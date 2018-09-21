package com.tipray.controller;

import com.tipray.bean.AppDev;
import com.tipray.bean.GridPage;
import com.tipray.bean.Message;
import com.tipray.bean.Page;
import com.tipray.constant.LogDescriptionEnum;
import com.tipray.core.annotation.LogAnno;
import com.tipray.core.annotation.PermissionAnno;
import com.tipray.core.exception.ServiceException;
import com.tipray.service.AppdevService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/manage/appdev")
public class AppdevController {
    @Resource
    private AppdevService appdevService;

    @RequestMapping(value = "dispatch.do")
    public String dispatch(String mode, Long id, ModelMap modelMap) {
        modelMap.put("mode", mode);
        AppDev appDev = new AppDev();
        if (id > 0) {
            appDev = appdevService.getAppdevById(id);
        }
        modelMap.put("appdev", appDev);
        return "normal/appdev/appdevEdit.jsp";
    }

    @LogAnno(LogDescriptionEnum.APPDEV_ADD)
    @PermissionAnno("appdevManage")
    @RequestMapping(value = "add.do", method = RequestMethod.POST)
    @ResponseBody
    public Message addAppdev(@ModelAttribute AppDev appDev) {
        try {
            appdevService.addAppdev(appDev);
            return Message.success();
        } catch (ServiceException e) {
            return Message.error(e);
        }
    }

    @LogAnno(LogDescriptionEnum.APPDEV_ALTER)
    @PermissionAnno("appdevManage")
    @RequestMapping(value = "update.do", method = RequestMethod.POST)
    @ResponseBody
    public Message updateAppdev(@ModelAttribute AppDev appDev) {
        try {
            appdevService.updateAppdev(appDev);
            return Message.success();
        } catch (ServiceException e) {
            return Message.error(e);
        }
    }

    @LogAnno(LogDescriptionEnum.APPDEV_DELETE)
    @PermissionAnno("appdevManage")
    @RequestMapping(value = "delete.do", method = RequestMethod.POST)
    @ResponseBody
    public Message deleteAppdev(Long id) {
        try {
            appdevService.deleteAppdevById(id);
            return Message.success();
        } catch (ServiceException e) {
            return Message.error(e);
        }
    }

    @RequestMapping(value = "isExist.do")
    @ResponseBody
    public Boolean isAppdevExist(String uuid, String appid) {
        return appdevService.isAppdevExist(uuid, appid);
    }

    @PermissionAnno("appdevManage")
    @RequestMapping(value = "ajaxFindForPage.do")
    @ResponseBody
    public GridPage<AppDev> ajaxFindAppConfigsForPage(@ModelAttribute AppDev appDev, @ModelAttribute Page page) {
        GridPage<AppDev> gridPage = appdevService.findAppdevForPage(appDev, page);
        return gridPage;
    }

}
