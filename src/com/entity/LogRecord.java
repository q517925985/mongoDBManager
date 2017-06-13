package com.entity;

// Generated 2015-5-7 16:11:59 by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * LogRecord generated by hbm2java
 */
public class LogRecord implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3129115777319944137L;
	private Integer id;
	private int userId;
	private int connectId;
	private String datebaseName;
	private String tableName;
	private boolean operationType;
	private Date operationTime;
	private String remarks;

	public LogRecord() {
	}

	public LogRecord(int userId, int connectId, String datebaseName, String tableName, boolean operationType, Date operationTime, String remarks) {
		this.userId = userId;
		this.connectId = connectId;
		this.datebaseName = datebaseName;
		this.tableName = tableName;
		this.operationType = operationType;
		this.operationTime = operationTime;
		this.remarks = remarks;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getConnectId() {
		return this.connectId;
	}

	public void setConnectId(int connectId) {
		this.connectId = connectId;
	}

	public String getDatebaseName() {
		return this.datebaseName;
	}

	public void setDatebaseName(String datebaseName) {
		this.datebaseName = datebaseName;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public boolean isOperationType() {
		return this.operationType;
	}

	public void setOperationType(boolean operationType) {
		this.operationType = operationType;
	}

	public Date getOperationTime() {
		return this.operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}