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
    /**
     * 用户中心ID
     */
    private int center_id;
    /**
     * 用户中心名称
     */
    private String center_name = "";
    /**
     * 用户中心ip
     */
    private String ip = "";
    /**
     * 用户中心Web服务器port
     */
    private int port;
    /**
     * 升级状态（0 不升级，1 升级）
     */
    private int update_status;
    /**
     * 升级信息
     */
    private AppUpgrade upgrade_info;

    public int getCenter_id() {
        return center_id;
    }

    public void setCenter_id(int center_id) {
        this.center_id = center_id;
    }

    public String getCenter_name() {
        return center_name;
    }

    public void setCenter_name(String center_name) {
        if (center_name == null) {
            return;
        }
        this.center_name = center_name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        if (ip == null) {
            return;
        }
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
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
        if (!center_name.isEmpty()) {
            sb.append(", center_name='").append(center_name).append('\'');
        }
        if (!ip.isEmpty()) {
            sb.append(", ip='").append(ip).append('\'');
        }
        if (port > 0) {
            sb.append(", port=").append(port);
        }
        sb.append(", update_status=").append(update_status);
        if (upgrade_info != null) {
            sb.append(", upgrade_info=").append(upgrade_info);
        }
        sb.append('}');
        return sb.toString();
    }
}
