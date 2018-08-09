package com.tipray.core.job;

import com.tipray.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 定时任务
 *
 * @author chenlong
 * @version 1.0 2018-08-07
 */
@Component
public class ScheduledJob {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledJob.class);
    @Resource
    private DeviceService deviceService;

    /**
     * 定时检查更新未使用的设备ID状态
     */
    public void executeCheckDeviceIdStatus() {
        try {
            deviceService.upToUseDeviceIdUnused();
        } catch (Exception e) {
            logger.error("定时检查更新未使用的设备ID状态异常！", e);
        }
    }
}
