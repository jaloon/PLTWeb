package com.tipray.bean;

import java.io.Serializable;

/**
 * APP信息
 *
 * @author chenlong
 * @version 1.0 2018-07-17
 */
public class AppInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer center_id;
    /**
     * 用户中心ip
     */
    private String ip;
    /**
     * 用户中心Web服务器port
     */
    private Integer port;
    /**
     * 升级状态（0 不升级，1 升级）
     */
    private int update_status;
    /**
     * 升级信息
     */
    private AppUpgrade upgrade_info;

    public Integer getCenter_id() {
        return center_id;
    }

    public void setCenter_id(Integer center_id) {
        this.center_id = center_id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public int getUpdate_status() {
        return update_status;
    }

    public void setUpdate_status(int update_status) {
        this.update_status = update_status;
    }

    public AppUpgrade getUpgrade_info() {
        return upgrade_info;
    }

    public void setUpgrade_info(AppUpgrade upgrade_info) {
        this.upgrade_info = upgrade_info;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AppInfo{");
        sb.append("center_id=").append(center_id);
        sb.append(", ip='").append(ip).append('\'');
        sb.append(", port=").append(port);
        sb.append(", update_status=").append(update_status);
        sb.append(", upgrade_info=").append(upgrade_info);
        sb.append('}');
        return sb.toString();
    }
}
