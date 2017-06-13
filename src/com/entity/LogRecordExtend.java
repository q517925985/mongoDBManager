/**
 * Project:mongoDBManager
 * File:Log.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author shihaojie
 * @date 2015年4月24日
 * @version $Id$
 */
public class LogRecordExtend {
	private Integer id;
	private int userId;
	private int connectId;
	private String datebaseName;
	private String tableName;
	private boolean operationType;
	private Date operationTime;
	private String nickname;
	private String remarks;
	private String connectIp;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getConnectId() {
		return connectId;
	}

	public void setConnectId(int connectId) {
		this.connectId = connectId;
	}

	public String getDatebaseName() {
		return datebaseName;
	}

	public void setDatebaseName(String datebaseName) {
		this.datebaseName = datebaseName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public boolean isOperationType() {
		return operationType;
	}

	public void setOperationType(boolean operationType) {
		this.operationType = operationType;
	}

	public String getOperationTime() {
		return new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒").format(operationTime);
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getConnectIp() {
		return connectIp;
	}

	public void setConnectIp(String connectIp) {
		this.connectIp = connectIp;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
