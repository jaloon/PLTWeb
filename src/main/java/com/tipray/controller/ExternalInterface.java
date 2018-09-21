package com.tipray.controller;

import com.tipray.bean.AppDev;
import com.tipray.bean.AppInfo;
import com.tipray.bean.AppSync;
import com.tipray.bean.AppUpgrade;
import com.tipray.bean.Center;
import com.tipray.bean.Device;
import com.tipray.bean.ResponseMsg;
import com.tipray.constant.reply.AppInfoObtainErrorEnum;
import com.tipray.constant.reply.BarrierInfoObtainErrorEnum;
import com.tipray.constant.reply.CenterInfoObtainErrorEnum;
import com.tipray.constant.reply.CenterRc4ObtainErrorEnum;
import com.tipray.constant.reply.WebAddrObtainErrorEnum;
import com.tipray.service.AppdevService;
import com.tipray.service.AppverService;
import com.tipray.service.CenterDevService;
import com.tipray.service.CenterService;
import com.tipray.service.DeviceService;
import com.tipray.util.EmptyObjectUtil;
import com.tipray.util.HttpServletUtil;
import com.tipray.util.JSONUtil;
import com.tipray.util.RC4Util;
import com.tipray.util.ResponseMsgUtil;
import com.tipray.util.StringUtil;
import com.tipray.util.VersionUtil;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 外部接口
 *
 * @author chenlong
 */
@RestController
@RequestMapping("api")
public class ExternalInterface {
    private final Logger logger = LoggerFactory.getLogger(ExternalInterface.class);
    @Resource
    private DeviceService deviceService;
    @Resource
    private CenterService centerService;
    @Resource
    private AppdevService appdevService;
    @Resource
    private AppverService appverService;
    @Resource
    private CenterDevService centerDevService;
    @Resource
    private HttpServletRequest request;

    /**
     * 用户中心同步设备信息接口
     *
     * @param id 用户中心id
     * @return 用户中心的设备列表
     */
    @RequestMapping(value = "deviceSync.do")
    public List<Device> deviceSync(Long id) {
        logger.info("用户中心【{}】同步设备信息，from: {}", id, HttpServletUtil.getHost(request));
        List<Device> devices = deviceService.findByCenterId(id);
        return devices;
    }

    /**
     * 用户中心同步APP配置信息接口
     *
     * @param id 用户中心id
     * @return 用户中心的APP配置信息列表
     */
    @RequestMapping(value = "appSync.do")
    public AppSync appSync(Long id) {
        logger.info("用户中心【{}】同步APP配置信息，from: {}", id, HttpServletUtil.getHost(request));
        AppSync appsync = centerDevService.findAppSync(id);
        return appsync;
    }

    /**
     * 车载终端、读卡器等获取对应用户中心信息接口
     *
     * @param id  车载终端、读卡器等设备id
     * @param ver 版本号
     * @return {@link ResponseMsg} 用户中心IP地址等信息
     */
    @RequestMapping(value = "getCenterInfo.do")
    public ResponseMsg getCenterInfo(Integer id, Integer ver) {
        logger.info("设备【{}】请求用户中心信息，ver: {}, from: {}", id, ver, HttpServletUtil.getHost(request));
        if (id == null) {
            logger.warn("请求用户中心信息失败：{}", CenterInfoObtainErrorEnum.DEVICE_ID_NULL);
            return ResponseMsgUtil.error(CenterInfoObtainErrorEnum.DEVICE_ID_NULL);
        }
        if (ver == null) {
            logger.warn("请求用户中心信息失败：{}", CenterInfoObtainErrorEnum.VERSION_NULL);
            return ResponseMsgUtil.error(CenterInfoObtainErrorEnum.VERSION_NULL);
        }
        try {
            Map<String, Object> map = deviceService.getCenterByDeviceId(id);
            if (EmptyObjectUtil.isEmptyMap(map)) {
                logger.warn("请求用户中心信息失败：{}", CenterInfoObtainErrorEnum.DEVICE_ID_INVALID);
                return ResponseMsgUtil.error(CenterInfoObtainErrorEnum.DEVICE_ID_INVALID);
            }
            switch (ver) {
                case 1:
                    // v1 RC4加密
                    String json = JSONUtil.stringify(map);
                    byte[] key = RC4Util.getKeyByDeviceId(id);
                    String info = RC4Util.rc4ToHexString(json, key);
                    logger.info("请求用户中心信息成功！return: {}", info);
                    return ResponseMsgUtil.success(info);
                default:
                    logger.warn("请求用户中心信息失败：版本号【{}】超出范围！", ver);
                    return ResponseMsgUtil.error(CenterInfoObtainErrorEnum.VERSION_INVALID);
            }
        } catch (Exception e) {
            logger.error("请求用户中心信息异常！", e);
            return ResponseMsgUtil.exception(e);
        }
    }

    /**
     * 根据用户中心ID获取用户中心RC4秘钥接口
     *
     * @param id  用户中心ID
     * @param ver 版本号
     * @return {@link ResponseMsg} 用户中心IP地址等信息
     */
    @RequestMapping(value = "getCenterRc4.do")
    public ResponseMsg getCenterRc4(Integer id, Integer ver) {
        logger.info("请求用户中心【{}】的RC4秘钥信息，ver: {}, from: {}", id, ver, HttpServletUtil.getHost(request));
        if (id == null) {
            logger.warn("请求RC4秘钥信息失败：{}", CenterRc4ObtainErrorEnum.CENTER_ID_NULL);
            return ResponseMsgUtil.error(CenterRc4ObtainErrorEnum.CENTER_ID_NULL);
        }
        if (ver == null) {
            logger.warn("请求RC4秘钥信息失败：{}", CenterRc4ObtainErrorEnum.VERSION_NULL);
            return ResponseMsgUtil.error(CenterRc4ObtainErrorEnum.VERSION_NULL);
        }
        try {
            byte[] rc4 = deviceService.getCenterRc4ByCenterId(id);
            if (EmptyObjectUtil.isEmptyArray(rc4)) {
                logger.warn("请求RC4秘钥信息失败：{}", CenterRc4ObtainErrorEnum.CENTER_ID_INVALID);
                return ResponseMsgUtil.error(CenterRc4ObtainErrorEnum.CENTER_ID_INVALID);
            }
            switch (ver) {
                case 1:
                    // v1 RC4加密
                    byte[] key = RC4Util.getKeyByDeviceId(id);
                    String rc4Info = RC4Util.rc4ToHexString(rc4, key);
                    logger.info("请求RC4秘钥信息成功！return: {}", rc4Info);
                    return ResponseMsgUtil.success(rc4Info);
                default:
                    logger.warn("请求RC4秘钥信息失败：版本号【{}】超出范围！", ver);
                    return ResponseMsgUtil.error(CenterRc4ObtainErrorEnum.VERSION_INVALID);
            }
        } catch (Exception e) {
            logger.error("请求RC4秘钥信息异常！", e);
            return ResponseMsgUtil.exception(e);
        }
    }

    /**
     * 根据道闸接口所属用户中心ID获取道闸接口信息
     *
     * @param id  用户中心ID
     * @param ver 版本号
     * @return {@link ResponseMsg} 道闸接口信息
     */
    @RequestMapping(value = "getBarrierInfo.do")
    public ResponseMsg getBarrierInfo(Integer id, Integer ver) {
        logger.info("请求用户中心【{}】的道闸接口信息，ver: {}, from: {}", id, ver, HttpServletUtil.getHost(request));
        if (id == null) {
            logger.warn("请求道闸接口信息失败：{}", BarrierInfoObtainErrorEnum.CENTER_ID_NULL);
            return ResponseMsgUtil.error(BarrierInfoObtainErrorEnum.CENTER_ID_NULL);
        }
        if (ver == null) {
            logger.warn("请求道闸接口信息失败：{}", BarrierInfoObtainErrorEnum.VERSION_NULL);
            return ResponseMsgUtil.error(BarrierInfoObtainErrorEnum.VERSION_NULL);
        }
        try {
            Map<String, Object> map = deviceService.getBarrierByCenterId(id);
            if (EmptyObjectUtil.isEmptyMap(map)) {
                logger.warn("请求道闸接口信息失败：{}", BarrierInfoObtainErrorEnum.CENTER_ID_INVALID);
                return ResponseMsgUtil.error(BarrierInfoObtainErrorEnum.CENTER_ID_INVALID);
            }
            switch (ver) {
                case 1:
                    // v1 RC4加密
                    String json = JSONUtil.stringify(map);
                    byte[] key = RC4Util.getKeyByDeviceId(id);
                    String info = RC4Util.rc4ToHexString(json, key);
                    logger.info("请求道闸接口信息成功！return: {}", info);
                    return ResponseMsgUtil.success(info);
                default:
                    logger.warn("请求道闸接口信息失败：版本号【{}】超出范围！", ver);
                    return ResponseMsgUtil.error(BarrierInfoObtainErrorEnum.VERSION_INVALID);
            }
        } catch (Exception e) {
            logger.error("请求道闸接口信息异常！", e);
            return ResponseMsgUtil.exception(e);
        }
    }

    /**
     * 获取所有用户中心ID和名称
     *
     * @return {@link ResponseMsg} 所有用户中心ID和名称信息
     */
    @RequestMapping(value = "getAllCenter.do")
    public ResponseMsg getAllCenter() {
        logger.info("客户端【{}】请求所有用户中心ID和名称，from: {}",
                request.getHeader("User-Agent"), HttpServletUtil.getHost(request));
        try {
            List<Center> centers = centerService.getCenterList();
            logger.info("请求所有用户中心ID和名称成功！return: {}", centers);
            return ResponseMsgUtil.success(centers);
        } catch (Exception e) {
            logger.error("请求所有用户中心ID和名称异常！", e);
            return ResponseMsgUtil.exception(e);
        }
    }

    /**
     * 通过用户中心ID获取IP地址和端口号
     *
     * @param id 用户中心ID
     * @return {@link ResponseMsg} IP地址和端口号
     */
    @RequestMapping(value = "getIPAndPort.do")
    public ResponseMsg getIPAndPort(Integer id) {
        logger.info("请求用户中心【{}】的IP地址和端口号，from: {}", id, HttpServletUtil.getHost(request));
        if (id == null) {
            logger.warn("请求IP地址和端口号信息失败：{}", WebAddrObtainErrorEnum.CENTER_ID_NULL);
            return ResponseMsgUtil.error(WebAddrObtainErrorEnum.CENTER_ID_NULL);
        }
        try {
            Map<String, Object> map = centerService.getIpAndWebport(id);
            if (EmptyObjectUtil.isEmptyMap(map)) {
                logger.warn("请求IP地址和端口号信息失败：{}", WebAddrObtainErrorEnum.CENTER_ID_INVALID);
                return ResponseMsgUtil.error(WebAddrObtainErrorEnum.CENTER_ID_INVALID);
            }
            logger.info("请求IP地址和端口号信息成功！return: {}", map);
            return ResponseMsgUtil.success(map);
        } catch (Exception e) {
            logger.error("请求IP地址和端口号信息异常！", e);
            return ResponseMsgUtil.exception(e);
        }
    }

    /**
     * 获取手机APP配置信息
     *
     * @param uuid     手机UUID
     * @param centerId 用户中心ID
     * @param appid    APP标识ID，“pltone_e_seal”
     * @param system   手机系统
     * @param model    手机型号
     * @param curver   当前WebApp版本
     * @return {@link ResponseMsg} 手机APP配置信息
     */
    @RequestMapping(value = "getAppInfo.do")
    public ResponseMsg getAppInfo(@RequestParam("uuid") String uuid,
                                  @RequestParam("center_id") Long centerId,
                                  @RequestParam("appid") String appid,
                                  @RequestParam("system") String system,
                                  @RequestParam("model") String model,
                                  @RequestParam("current_version") String curver) {
        logger.info("手机【{}】请求APP配置，centerId: {}, appid: {}, system: {}, model: {}, curver: {}, from: {}",
                uuid, centerId, appid, system, model, curver, HttpServletUtil.getHost(request));
        try {
            if (StringUtil.isEmpty(uuid)) {
                logger.warn("请求APP配置失败：{}", AppInfoObtainErrorEnum.UUID_NULL);
                return ResponseMsgUtil.error(AppInfoObtainErrorEnum.UUID_NULL);
            }
            if (centerId == null) {
                logger.warn("请求APP配置失败：{}", AppInfoObtainErrorEnum.CENTER_ID_NULL);
                return ResponseMsgUtil.error(AppInfoObtainErrorEnum.CENTER_ID_NULL);
            }
            if (StringUtil.isEmpty(appid)) {
                logger.warn("请求APP配置失败：{}", AppInfoObtainErrorEnum.APPID_NULL);
                return ResponseMsgUtil.error(AppInfoObtainErrorEnum.APPID_NULL);
            }
            if (StringUtil.isEmpty(system)) {
                logger.warn("请求APP配置失败：{}", AppInfoObtainErrorEnum.SYSTEM_NULL);
                return ResponseMsgUtil.error(AppInfoObtainErrorEnum.SYSTEM_NULL);
            }
            if (StringUtil.isEmpty(model)) {
                logger.warn("请求APP配置失败：{}", AppInfoObtainErrorEnum.MODEL_NULL);
                return ResponseMsgUtil.error(AppInfoObtainErrorEnum.MODEL_NULL);
            }
            if (StringUtil.isEmpty(curver)) {
                logger.warn("请求APP配置失败：{}", AppInfoObtainErrorEnum.VERSION_NULL);
                return ResponseMsgUtil.error(AppInfoObtainErrorEnum.VERSION_NULL);
            }

            if (!VersionUtil.isVerSion(curver)) {
                logger.warn("请求APP配置失败：{}", AppInfoObtainErrorEnum.VERSION_INVALID);
                return ResponseMsgUtil.error(AppInfoObtainErrorEnum.VERSION_INVALID);
            }

            if (appdevService.isAppdevExist(uuid, appid)) {
                // 更新当前版本和型号
                appdevService.updateModelAndCurrentVerByUuidAndAppid(model, curver, uuid, appid);
            } else {
                // APP设备记录不存在，添加一条记录
                AppDev appDev = new AppDev();
                appDev.setUuid(uuid);
                appDev.setAppid(appid);
                appDev.setSystem(system);
                appDev.setModel(model);
                appDev.setCurrentVer(curver);
                appdevService.addAppdev(appDev);
            }
            String assignVer = appverService.getAssignVerByAppver(0L, appid, system);
            List<Long> centerIds = centerDevService.findCenterIdsByUuid(uuid);
            int centerNum = centerIds.size();
            if (centerNum == 0) {
                if (StringUtil.isEmpty(assignVer)) {
                    logger.warn("请求APP配置失败：{}", AppInfoObtainErrorEnum.CENTER_NULL);
                    return ResponseMsgUtil.error(AppInfoObtainErrorEnum.CENTER_NULL);
                }
                // centerId = 0L;
            } else if (centerNum == 1) {
                centerId = centerIds.get(0);
                String ver = appverService.getAssignVerByAppver(centerId, appid, system);
                if (ver != null) {
                    assignVer = ver;
                }
            } else {
                if (centerId > 0 && centerIds.contains(centerId)) {
                    String ver = appverService.getAssignVerByAppver(centerId, appid, system);
                    if (ver != null) {
                        assignVer = ver;
                    }
                } else {
                    if (StringUtil.isEmpty(assignVer)) {
                        if (centerId == 0) {
                            logger.warn("请求APP配置失败：{}", AppInfoObtainErrorEnum.CENTER_NOT_UNIQUE);
                            return ResponseMsgUtil.error(AppInfoObtainErrorEnum.CENTER_NOT_UNIQUE);
                        }
                        if (!centerIds.contains(centerId)) {
                            logger.warn("请求APP配置失败：{}", AppInfoObtainErrorEnum.CENTER_ID_INVALID);
                            return ResponseMsgUtil.error(AppInfoObtainErrorEnum.CENTER_ID_INVALID);
                        }
                    }
                    // centerId = 0L;
                }
            }
            AppInfo appInfo = appdevService.getCenterWebAddr(centerId);
            if (appInfo == null) {
                appInfo = new AppInfo();
                if (centerId > 0) {
                    logger.warn("用户中心【{}】不存在！", centerId);
                    // appInfo.setCenter_id(centerId.intValue());
                }
            }
            if (assignVer == null) {
                logger.warn(AppInfoObtainErrorEnum.UP_VER_NULL.msg());
            } else {
                if (VersionUtil.compareVer(curver, assignVer) < 0) {
                    AppUpgrade appUpgrade = new AppUpgrade();
                    appUpgrade.setVersion(assignVer);
                    assignVer = assignVer.replace(".", "_");
                    // 版本信息json文件存放路径
                    Properties properties = new Properties();
                    properties.load(this.getClass().getClassLoader().getResourceAsStream("appconf.properties"));
                    String parentPath = properties.getProperty("file.path");
                    if (parentPath.endsWith("/")) {
                        parentPath = parentPath.substring(0, parentPath.length() - 1);
                    }
                    String downloadFilePath = new StringBuffer().append(parentPath).append('/').append(appid).append('/')
                            .append(system).append('/').append(assignVer).append("/update.json").toString();
                    File file = new File(downloadFilePath);
                    if (!file.exists()) {
                        logger.warn("app upgrade info json file is not existed!");
                        return ResponseMsgUtil.error(AppInfoObtainErrorEnum.JSON_FILE_NO_EXIST);
                    }
                    String json = FileUtils.readFileToString(file, "UTF-8");
                    //将读取的数据转换为JSONObject
                    JSONObject jsonObject = JSONObject.fromObject(json);
                    if (jsonObject != null) {
                        JSONObject systemObj = jsonObject.getJSONObject(system);
                        if (systemObj == null) {
                            logger.warn("请求APP配置失败：{}", AppInfoObtainErrorEnum.SYSTEM_NOT_IN_JSON);
                            return ResponseMsgUtil.error(AppInfoObtainErrorEnum.SYSTEM_NOT_IN_JSON);
                        }
                        String note = systemObj.getString("note");
                        String url = systemObj.getString("url");
                        appUpgrade.setNote(note);
                        appUpgrade.setUrl(url);
                    }
                    appInfo.setUpdate_status(1);
                    appInfo.setUpgrade_info(appUpgrade);
                }
            }
            logger.info("请求APP配置成功！appInfo：{}", appInfo);
            return ResponseMsgUtil.success(appInfo);
        } catch (Exception e) {
            logger.error("请求APP配置异常！", e);
            return ResponseMsgUtil.exception(e);
        }
    }
}