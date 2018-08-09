package com.tipray.bean;

import java.io.Serializable;

/**
 * APP升级信息
 *
 * @author chenlong
 * @version 1.0 2018-07-17
 */
public class AppUpgrade implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 版本
     */
    private String version;
    /**
     * 标题
     */
    private String title = "普利通中心版本升级";
    /**
     * 升级日志
     */
    private String note;
    /**
     * 新版本安装包路径
     */
    private String url;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AppUpgrade{");
        sb.append("version='").append(version).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", note='").append(note).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
