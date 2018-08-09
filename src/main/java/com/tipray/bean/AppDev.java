package com.tipray.bean;

import com.tipray.core.base.BaseBean;

/**
 * APP设备信息
 *
 * @author chenlong
 * @version 1.0 2018-07-26
 */
public class AppDev extends BaseBean {
    private static final long serialVersionUID = 1L;
    /** 手机唯一码 */
    private String uuid;
    /** 手机系统（Android/iOS） */
    private String system;
    /** 手机型号 */
    private String model;
    /** 当前WebApp版本 */
    private String currentVer;
    /** 机主 */
    private String owner;
    /** 联系电话 */
    private String phone;
    /** 职务 */
    private String duty;
    /** 机构 */
    private String institution;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCurrentVer() {
        return currentVer;
    }

    public void setCurrentVer(String currentVer) {
        this.currentVer = currentVer;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    @Override
    public String toString() {
        StringBuffer strBuf = new StringBuffer();
        strBuf.append("AppDev: {");
        if (getId() != null) {
            strBuf.append("id: ").append(getId()).append(',').append(' ');
        }
        if (uuid != null) {
            strBuf.append("uuid: ").append(uuid).append(',').append(' ');
        }
        if (system != null) {
            strBuf.append("system: ").append(system).append(',').append(' ');
        }
        if (model != null) {
            strBuf.append("model: ").append(model).append(',').append(' ');
        }
        if (currentVer != null) {
            strBuf.append("currentVer: ").append(currentVer).append(',').append(' ');
        }
        if (owner != null) {
            strBuf.append("owner: ").append(owner).append(',').append(' ');
        }
        if (phone != null) {
            strBuf.append("phone: ").append(phone).append(',').append(' ');
        }
        if (duty != null) {
            strBuf.append("duty: ").append(duty).append(',').append(' ');
        }
        if (institution != null) {
            strBuf.append("institution: ").append(institution).append(',').append(' ');
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
