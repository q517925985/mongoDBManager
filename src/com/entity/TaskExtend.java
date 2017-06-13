/**
 * Project:mongoDBManager
 * File:TaskExtend.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.entity;

import java.util.List;

/**
 * @author shihaojie
 * @date 2015年5月19日
 * @version $Id$
 */
public class TaskExtend {
	private Integer id;
	private boolean importType;
	private boolean importIsDb;
	private Integer sourceConnectId;
	private String sourceConnectIp;
	private String sourceDatebaseName;
	private Integer receivedConnectId;
	private String receivedConnectIp;
	private String receivedDatebaseName;
	private String remarks;
	private List<SourceTable> tableList;
	
	
	
	public Integer getSourceConnectId() {
		return sourceConnectId;
	}
	public void setSourceConnectId(Integer sourceConnectId) {
		this.sourceConnectId = sourceConnectId;
	}
	public Integer getReceivedConnectId() {
		return receivedConnectId;
	}
	public void setReceivedConnectId(Integer receivedConnectId) {
		this.receivedConnectId = receivedConnectId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSourceConnectIp() {
		return sourceConnectIp;
	}
	public void setSourceConnectIp(String sourceConnectIp) {
		this.sourceConnectIp = sourceConnectIp;
	}
	public String getSourceDatebaseName() {
		return sourceDatebaseName;
	}
	public void setSourceDatebaseName(String sourceDatebaseName) {
		this.sourceDatebaseName = sourceDatebaseName;
	}
	public String getReceivedConnectIp() {
		return receivedConnectIp;
	}
	public void setReceivedConnectIp(String receivedConnectIp) {
		this.receivedConnectIp = receivedConnectIp;
	}
	public String getReceivedDatebaseName() {
		return receivedDatebaseName;
	}
	public void setReceivedDatebaseName(String receivedDatebaseName) {
		this.receivedDatebaseName = receivedDatebaseName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public List<SourceTable> getTableList() {
		return tableList;
	}
	public void setTableList(List<SourceTable> tableList) {
		this.tableList = tableList;
	}
	public boolean isImportType() {
		return importType;
	}
	public void setImportType(boolean importType) {
		this.importType = importType;
	}
	public boolean isImportIsDb() {
		return importIsDb;
	}
	public void setImportIsDb(boolean importIsDb) {
		this.importIsDb = importIsDb;
	}
	
	
}
