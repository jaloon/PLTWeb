/*
 * Device.java
 * Copyright(C) 厦门天锐科技有限公司
 * All rights reserved.
 * -----------------------------------------------
 * 2017-11-06 Created
 */
package com.tipray.bean;

import com.tipray.core.base.BaseBean;

/**
 * 设备信息表
 * 
 * @author chenlong
 * @version 1.0 2017-11-06
 */
public class Device extends BaseBean {
	private static final long serialVersionUID = 1L;
	private Integer deviceId;
	/** 设备类型值 */
	private Integer type;
	/** 设备类型 */
	private String typeName;
	/** 设备版本 */
	private Integer ver;
	/** 用户中心ID */
	private Integer centerId;
	/** 用户中心名称 */
	private String centerName;
	/** 型号 */
	private String model;
	/** 出厂时间 */
	private String produce;
	/** 发货时间 */
	private String delivery;

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getVer() {
		return ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public Integer getCenterId() {
		return centerId;
	}

	public void setCenterId(Integer centerId) {
		this.centerId = centerId;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getProduce() {
		return produce;
	}

	public void setProduce(String produce) {
		this.produce = produce;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("Device: {");
		if (getId() != null) {
			strBuf.append("id: ").append(getId()).append(',').append(' ');
		}
		if (deviceId != null) {
			strBuf.append("deviceId: ").append(deviceId).append(',').append(' ');
		}
		if (type != null) {
			strBuf.append("type: ").append(type).append(',').append(' ');
		}
		if (typeName != null) {
			strBuf.append("typeName: ").append(typeName).append(',').append(' ');
		}
		if (ver != null) {
			strBuf.append("ver: ").append(ver).append(',').append(' ');
		}
		if (centerId != null) {
			strBuf.append("centerId: ").append(centerId).append(',').append(' ');
		}
		if (centerName != null) {
			strBuf.append("centerName: ").append(centerName).append(',').append(' ');
		}
		if (model != null) {
			strBuf.append("model: ").append(model).append(',').append(' ');
		}
		if (produce != null) {
			strBuf.append("produce: ").append(produce).append(',').append(' ');
		}
		if (delivery != null) {
			strBuf.append("delivery: ").append(delivery).append(',').append(' ');
		}
		if (getRemark() != null) {
			strBuf.append("remark: ").append(getRemark()).append(',').append(' ');
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