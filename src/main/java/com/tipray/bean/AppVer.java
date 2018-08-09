package com.tipray.bean;

import com.tipray.core.base.BaseBean;

/**
 * APP版本信息
 *
 * @author chenlong
 * @version 1.0 2018-07-26
 */
public class AppVer extends BaseBean {
    private static final long serialVersionUID = 1L;
    /** 用户中心ID */
    private Long centerId;
    /** 用户中心名称 */
    private String centerName;
    /** 设备系统（Android | iOS | ...） */
    private String system;
    /** 指定WebAPP版本 */
    private String assignVer;
    /** 允许运行的最低WebAPP版本 */
    private String minVer;

    public Long getCenterId() {
        return centerId;
    }

    public void setCenterId(Long centerId) {
        this.centerId = centerId;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getAssignVer() {
        return assignVer;
    }

    public void setAssignVer(String assignVer) {
        this.assignVer = assignVer;
    }

    public String getMinVer() {
        return minVer;
    }

    public void setMinVer(String minVer) {
        this.minVer = minVer;
    }

    @Override
    public String toString() {
        StringBuffer strBuf = new StringBuffer();
        strBuf.append("AppVer: {");
        if (getId() != null) {
            strBuf.append("id: ").append(getId()).append(',').append(' ');
        }
        if (centerId != null) {
            strBuf.append("centerId: ").append(centerId).append(',').append(' ');
        }
        if (centerName != null) {
            strBuf.append("centerName: ").append(centerName).append(',').append(' ');
        }
        if (system != null) {
            strBuf.append("system: ").append(system).append(',').append(' ');
        }
        if (assignVer != null) {
            strBuf.append("assignVer: ").append(assignVer).append(',').append(' ');
        }
        if (minVer != null) {
            strBuf.append("minVer: ").append(minVer).append(',').append(' ');
        }
        if (strBuf.toString().endsWith("{")) {
            strBuf.deleteCharAt(strBuf.length() - 1);
            strBuf.append("All fileds are null.");
        } else {
            strBuf.deleteCharAt(strBuf.length() - 2);
            strBuf.append('}');
        }
        return strBuf.toString();
    }
}
