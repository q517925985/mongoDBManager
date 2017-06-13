/**
 * Project:mongoDBManager
 * File:ConnectExtend.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.entity;

/**
 * @author shihaojie
 * @date 2015年4月29日
 * @version $Id$
 */
public class ConnectExtend {

	private Integer connectId;
	private String connectName;
	private int connectTypeId;
	private String connectIp;
	private int connectPort;
	private String connectUserName;
	private String connectPassword;
	private String connectTypeName;
	
	public Integer getConnectId() {
		return connectId;
	}
	public void setConnectId(Integer connectId) {
		this.connectId = connectId;
	}
	public int getConnectTypeId() {
		return connectTypeId;
	}
	public void setConnectTypeId(int connectTypeId) {
		this.connectTypeId = connectTypeId;
	}
	public String getConnectIp() {
		return connectIp;
	}
	public void setConnectIp(String connectIp) {
		this.connectIp = connectIp;
	}
	public int getConnectPort() {
		return connectPort;
	}
	public void setConnectPort(int connectPort) {
		this.connectPort = connectPort;
	}
	public String getConnectName() {
		return connectName;
	}
	public void setConnectName(String connectName) {
		this.connectName = connectName;
	}
	public String getConnectPassword() {
		return connectPassword;
	}
	public void setConnectPassword(String connectPassword) {
		this.connectPassword = connectPassword;
	}
	public String getConnectTypeName() {
		return connectTypeName;
	}
	public void setConnectTypeName(String connectTypeName) {
		this.connectTypeName = connectTypeName;
	}
	public String getConnectUserName() {
		return connectUserName;
	}
	public void setConnectUserName(String connectUserName) {
		this.connectUserName = connectUserName;
	}

}
