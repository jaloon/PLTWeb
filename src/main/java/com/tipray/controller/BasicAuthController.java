package com.tipray.controller;

import com.tipray.bean.ResponseMsg;
import com.tipray.constant.DeviceConst;
import com.tipray.constant.reply.DeviceIdApplyErrorEnum;
import com.tipray.service.DeviceService;
import com.tipray.util.HttpServletUtil;
import com.tipray.util.ResponseMsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 基本认证相关接口控制器
 *
 * @author chenlong
 * @version 1.0 2018-08-06
 */
@RestController
@RequestMapping("basic")
public class BasicAuthController {
    private static Logger logger = LoggerFactory.getLogger(BasicAuthController.class);
    @Resource
    private DeviceService deviceService;
    @Resource
    private HttpServletRequest request;

    /**
     * 申请设备ID
     * @param type 设备类型
     * @return {@link ResponseMsg}
     */
    @RequestMapping(value = "applyForDeviceId.do", method = RequestMethod.POST)
    public ResponseMsg applyForDeviceId(Integer type) {
        logger.info("申请设备类型【{}】的设备ID，from: {}", type, HttpServletUtil.getHost(request));
        if (type == null) {
            logger.warn("申请设备ID失败：{}", DeviceIdApplyErrorEnum.DEVICE_TYPE_NULL);
            return ResponseMsgUtil.error(DeviceIdApplyErrorEnum.DEVICE_TYPE_NULL);
        }
        if (type < DeviceConst.TYPE_1_TERMINAL || type > DeviceConst.TYPE_4_HANDSET) {
            logger.warn("申请设备ID失败：{}", DeviceIdApplyErrorEnum.DEVICE_TYPE_INVALID);
            return ResponseMsgUtil.error(DeviceIdApplyErrorEnum.DEVICE_TYPE_INVALID);
        }
        try {
            Integer deviceId = deviceService.applyForDeviceId(type);
            logger.info("申请设备ID成功！return: {}", deviceId);
            return ResponseMsgUtil.success(deviceId);
        } catch (Exception e) {
            logger.error("申请设备ID异常！", e);
            return ResponseMsgUtil.exception(e);
        }
    }

    /**
     * 同步设备ID使用状态
     * @param deviceId 设备ID
     * @return {@link ResponseMsg}
     */
    @RequestMapping(value = "syncDeviceIdStatus.do", method = RequestMethod.POST)
    public ResponseMsg syncDeviceIdStatus(Integer deviceId) {
        logger.info("更新设备ID【{}】的状态为使用中，from: {}", deviceId, HttpServletUtil.getHost(request));
        if (deviceId == null) {
            logger.warn("更新设备ID使用状态失败：{}", DeviceIdApplyErrorEnum.DEVICE_ID_NULL);
            return ResponseMsgUtil.error(DeviceIdApplyErrorEnum.DEVICE_ID_NULL);
        }
        try {
            Integer status = deviceService.getDeviceIdStatus(deviceId);
            if (status == DeviceConst.DEVICE_ID_STATUS_2_USING) {
                logger.warn("更新设备ID使用状态失败：{}", DeviceIdApplyErrorEnum.DEVICE_ID_USING);
                return ResponseMsgUtil.error(DeviceIdApplyErrorEnum.DEVICE_ID_USING);
            }
            deviceService.upDeviceIdStatus(deviceId);
            logger.info("更新设备ID使用状态[{} -> 2]成功！", status);
            return ResponseMsgUtil.success();
        } catch (Exception e) {
            logger.error("更新设备ID使用状态异常！", e);
            return ResponseMsgUtil.exception(e);
        }
    }
}
