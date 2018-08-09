package com.tipray.controller;

import com.tipray.bean.*;
import com.tipray.constant.LogDescriptionEnum;
import com.tipray.core.annotation.LogAnno;
import com.tipray.core.annotation.PermissionAnno;
import com.tipray.core.exception.ServiceException;
import com.tipray.service.AppdevService;
import com.tipray.service.CenterService;
import com.tipray.util.EmptyObjectUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/manage/appdev")
public class AppdevController {
    @Resource
    private AppdevService appdevService;
    @Resource
    private CenterService centerService;

    @RequestMapping(value = "dispatch.do")
    public String dispatch(String mode, Long id, ModelMap modelMap) {
        modelMap.put("mode", mode);
        AppDev appDev = new AppDev();
        if (id > 0) {
            appDev = appdevService.getAppdevById(id);
        }
        List<?> centers = new ArrayList<>();
        if (Mode.VIEW.equalsIgnoreCase(mode)) {
            centers = appdevService.findCenterNamesByAppdevId(id);
            StringBuffer strBuf = new StringBuffer();
            if (!EmptyObjectUtil.isEmptyList(centers)) {
                for (Object center : centers) {
                    strBuf.append('、').append(center);
                }
                strBuf.deleteCharAt(0);
            }
            modelMap.put("centerStr", strBuf.toString());
        } else if (Mode.ADD.equalsIgnoreCase(mode)) {
            centers = centerService.getCenterList();
        } else if (Mode.EDIT.equalsIgnoreCase(mode)) {
            centers = appdevService.findCenterNamesByAppdevId(id);
            StringBuffer strBuf = new StringBuffer();
            if (!EmptyObjectUtil.isEmptyList(centers)) {
                for (Object center : centers) {
                    strBuf.append('、').append(center);
                }
                strBuf.deleteCharAt(0);
            }
            modelMap.put("centerStr", strBuf.toString());
            centers = appdevService.findAppCentersByAppdevId(id);
        }
        modelMap.put("centers", centers);
        modelMap.put("appdev", appDev);
        return "normal/appdev/appdevEdit.jsp";
    }

    @LogAnno(LogDescriptionEnum.APPDEV_ADD)
    @PermissionAnno("appdevManage")
    @RequestMapping(value = "add.do", method = RequestMethod.POST)
    @ResponseBody
    public Message addAppdev(@ModelAttribute AppDev appDev, String centerIds) {
        try {
            appdevService.addAppdev(appDev, centerIds);
            return Message.success();
        } catch (ServiceException e) {
            return Message.error(e);
        }
    }

    @LogAnno(LogDescriptionEnum.APPDEV_ALTER)
    @PermissionAnno("appdevManage")
    @RequestMapping(value = "update.do", method = RequestMethod.POST)
    @ResponseBody
    public Message updateAppdev(@ModelAttribute AppDev appDev, String centerIds) {
        try {
            appdevService.updateAppdev(appDev, centerIds);
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
    public Boolean isAppdevExist(String uuid) {
        return appdevService.isAppdevExist(uuid);
    }

    @PermissionAnno("appdevManage")
    @RequestMapping(value = "ajaxFindForPage.do")
    @ResponseBody
    public GridPage<AppDev> ajaxFindAppConfigsForPage(@ModelAttribute AppDev appDev, @ModelAttribute Page page) {
        GridPage<AppDev> gridPage = appdevService.findAppdevForPage(appDev, page);
        return gridPage;
    }

}