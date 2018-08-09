package com.tipray.util;

import com.tipray.bean.ResponseMsg;
import com.tipray.constant.reply.*;

/**
 * Response信息工具类
 *
 * @author chenlong
 * @version 1.0 2018-06-14
 */
public class ResponseMsgUtil {
    /**
     * 成功ID
     */
    private static final int ID_SUCCESS = 0;
    /**
     * 错误ID
     */
    private static final int ID_ERROR = 1;
    /**
     * 异常ID
     */
    private static final int ID_EXCEPTION = 2;
    /**
     * 成功结果
     */
    public static final String SUCCESS = "success";
    /**
     * 错误或异常结果
     */
    public static final String ERROR = "error";

    /**
     * 成功回复（无附加信息）
     *
     * @return {@link ResponseMsg}
     */
    public static ResponseMsg success() {
        return success("");
    }

    /**
     * 成功回复（有附加信息）
     *
     * @param msg {@link Object} 附加信息
     * @return {@link ResponseMsg}
     */
    public static ResponseMsg success(Object msg) {
        return new ResponseMsg(ID_SUCCESS, SUCCESS, ErrorTagConst.NO_ERROR_TAG, 0, msg);
    }

    /**
     * 错误回复
     *
     * @param errorTag  {@link Byte} 错误标志
     * @param errorCode {@link Integer} 错误码
     * @param errorMsg  {@link Object} 错误信息
     * @return {@link ResponseMsg}
     */
    public static ResponseMsg error(byte errorTag, int errorCode, Object errorMsg) {
        return new ResponseMsg(ID_ERROR, ERROR, errorTag, errorCode, errorMsg);
    }

    /**
     * 错误回复（APP信息错误）
     *
     * @param appInfoError {@link AppInfoObtainErrorEnum} APP信息错误
     * @return {@link ResponseMsg}
     */
    public static ResponseMsg error(AppInfoObtainErrorEnum appInfoError) {
        return error(ErrorTagConst.APP_CONFIG_ERROR_TAG, appInfoError.code(), appInfoError.msg());
    }

    /**
     * 错误回复（道闸信息错误）
     *
     * @param barrierError {@link BarrierInfoObtainErrorEnum} 道闸信息错误
     * @return {@link ResponseMsg}
     */
    public static ResponseMsg error(BarrierInfoObtainErrorEnum barrierError) {
        return error(ErrorTagConst.BARRIER_INFO_ERROR_TAG, barrierError.code(), barrierError.msg());
    }

    /**
     * 错误回复（中心信息错误）
     *
     * @param centerError {@link BarrierInfoObtainErrorEnum} 中心信息错误
     * @return {@link ResponseMsg}
     */
    public static ResponseMsg error(CenterInfoObtainErrorEnum centerError) {
        return error(ErrorTagConst.CENTER_INFO_ERROR_TAG, centerError.code(), centerError.msg());
    }

    /**
     * 错误回复（RC4信息错误）
     *
     * @param rc4Error {@link CenterRc4ObtainErrorEnum} RC4信息错误
     * @return {@link ResponseMsg}
     */
    public static ResponseMsg error(CenterRc4ObtainErrorEnum rc4Error) {
        return error(ErrorTagConst.CENTER_CR4_ERROR_TAG, rc4Error.code(), rc4Error.msg());
    }

    /**
     * 错误回复（设备ID申请错误）
     *
     * @param deviceIdApplyError {@link DeviceIdApplyErrorEnum} 设备ID申请错误
     * @return {@link ResponseMsg}
     */
    public static ResponseMsg error(DeviceIdApplyErrorEnum deviceIdApplyError) {
        return error(ErrorTagConst.DEVICE_ID_APPLY_ERROR_TAG, deviceIdApplyError.code(), deviceIdApplyError.msg());
    }

    /**
     * 错误回复（权限错误）
     *
     * @param permissionError {@link PermissionErrorEnum} 权限错误
     * @return {@link ResponseMsg}
     */
    public static ResponseMsg error(PermissionErrorEnum permissionError) {
        return error(ErrorTagConst.PERMISSION_ERROR_TAG, permissionError.code(), permissionError.msg());
    }

    /**
     * 错误回复（Web服务地址错误）
     *
     * @param webAddrError {@link WebAddrObtainErrorEnum} Web服务地址错误
     * @return {@link ResponseMsg}
     */
    public static ResponseMsg error(WebAddrObtainErrorEnum webAddrError) {
        return error(ErrorTagConst.WEB_ADDRESS_ERROR_TAG, webAddrError.code(), webAddrError.msg());
    }

    /**
     * 异常回复
     *
     * @param errorTag  {@link Byte} 错误标志
     * @param errorCode {@link Integer} 错误码
     * @param errorMsg  {@link Object} 错误信息
     * @return {@link ResponseMsg}
     */
    public static ResponseMsg exception(byte errorTag, int errorCode, Object errorMsg) {
        return new ResponseMsg(ID_EXCEPTION, ERROR, errorTag, errorCode, errorMsg);
    }

    /**
     * 异常回复
     *
     * @param errorTag  {@link Byte} 错误标志
     * @param errorCode {@link Integer} 错误码
     * @param exception {@link Exception} 异常信息
     * @return {@link ResponseMsg}
     */
    public static ResponseMsg exception(byte errorTag, int errorCode, Exception exception) {
        String msg = exception.getMessage() == null ? exception.toString() : exception.getMessage();
        return exception(errorTag, errorCode, msg);
    }

    /**
     * 异常回复
     *
     * @param e {@link Exception} 异常
     * @return {@link ResponseMsg}
     */
    public static ResponseMsg exception(Exception e) {
        return exception(ErrorTagConst.EXCEPTION_MESSAGE_TAG, 10, e);
    }
}
