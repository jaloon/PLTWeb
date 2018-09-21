package com.tipray.controller;

import com.tipray.bean.Center;
import com.tipray.bean.CenterDev;
import com.tipray.bean.GridPage;
import com.tipray.bean.Message;
import com.tipray.bean.Mode;
import com.tipray.bean.Page;
import com.tipray.constant.LogDescriptionEnum;
import com.tipray.core.annotation.LogAnno;
import com.tipray.core.annotation.PermissionAnno;
import com.tipray.service.CenterDevService;
import com.tipray.service.CenterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chenlong
 * @version 1.0 2018-09-12
 */
@Controller
@RequestMapping("/manage/centerdev")
public class CenterDevController {
    @Resource
    private CenterDevService centerDevService;
    @Resource
    private CenterService centerService;

    @RequestMapping(value = "dispatch.do")
    public String dispatch(String mode, Long id, ModelMap modelMap) {
        modelMap.put("mode", mode);
        CenterDev centerDev = new CenterDev();
        if (id > 0) {
            centerDev = centerDevService.getCenterDevById(id);
        }
        if(Mode.ADD.equalsIgnoreCase(mode) || Mode.EDIT.equalsIgnoreCase(mode)) {
            List<Center> centers = centerService.getCenterList();
            modelMap.put("centers", centers);
        }
        modelMap.put("centerDev", centerDev);
        return "normal/centerdev/centerdevEdit.jsp";
    }

    @LogAnno(LogDescriptionEnum.CENTERDEV_ADD)
    @PermissionAnno("centerdevManage")
    @RequestMapping(value = "add.do", method = RequestMethod.POST)
    @ResponseBody
    public Message addAppdev(@ModelAttribute CenterDev centerDev) {
        try {
            centerDevService.addCenterDev(centerDev);
            return Message.success();
        } catch (Exception e) {
            return Message.error(e);
        }
    }

    @LogAnno(LogDescriptionEnum.CENTERDEV_ALTER)
    @PermissionAnno("centerdevManage")
    @RequestMapping(value = "update.do", method = RequestMethod.POST)
    @ResponseBody
    public Message updateAppdev(@ModelAttribute CenterDev centerDev) {
        try {
            centerDevService.updateCenterDev(centerDev);
            return Message.success();
        } catch (Exception e) {
            return Message.error(e);
        }
    }

    @LogAnno(LogDescriptionEnum.CENTERDEV_DELETE)
    @PermissionAnno("centerdevManage")
    @RequestMapping(value = "delete.do", method = RequestMethod.POST)
    @ResponseBody
    public Message deleteAppdev(Long id) {
        try {
            centerDevService.deleteCenterDevById(id);
            return Message.success();
        } catch (Exception e) {
            return Message.error(e);
        }
    }

    @RequestMapping(value = "isExist.do")
    @ResponseBody
    public Boolean isCenterdevExist(String uuid, Long centerId) {
        return centerDevService.isCenterdevExist(uuid, centerId);
    }

    @PermissionAnno("centerdevManage")
    @RequestMapping(value = "ajaxFindForPage.do")
    @ResponseBody
    public GridPage<CenterDev> ajaxFindAppConfigsForPage(@ModelAttribute CenterDev centerDev, @ModelAttribute Page page) {
        GridPage<CenterDev> gridPage = centerDevService.findCenterDevForPage(centerDev, page);
        return gridPage;
    }
}
