/**
 * Project:mongoDBManager
 * File:LogRecordAction.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.action;

import java.io.File;
import java.sql.Date;
import java.util.List;

import com.entity.Connect;
import com.entity.LogRecord;
import com.entity.LogRecordExtend;
import com.entity.MongoDatabase;
import com.entity.MongoTable;
import com.entity.Progress;
import com.opensymphony.xwork2.ActionSupport;
import com.service.ConnectService;
import com.service.LogRecordService;
import com.service.MongoService;

/**
 * @author shihaojie
 * @date 2015年4月24日
 * @version $Id$
 */
public class LogRecordAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4030724696808809866L;

	private LogRecordService logRecordServiceImpl;
	private ConnectService connectServiceImpl;
	private MongoService mongoServiceImpl;
	private int page;// 当前页
	private int count;// 总条数
	private int countPage;// 总页数
	private List<LogRecordExtend> list;// 日志列表
	private LogRecord logRecord;// 撤销记录

	private Date begin;
	private Date end;
	private String name;

	// 列表
	public String list() {
		page = page <= 0 ? 1 : page;
		if (name == null) {
			name = "";
		}
		if (begin == null) {
			begin = new Date(0);
		}
		if (end == null) {
			end = new Date(System.currentTimeMillis());
		}

		this.list = this.logRecordServiceImpl.getList(page, begin, end, name);// 获取职位列表
		this.count = this.logRecordServiceImpl.getCount(begin, end, name);// 设置总条数
		this.countPage = count % 10 == 0 ? count / 10 : count / 10 + 1;// 设置总页数
		return "list";
	}

	// 撤销
	public String lifted() {
		// 还原数据库为导入前数据
		Connect connect = this.connectServiceImpl.getConnectById(logRecord.getConnectId() + "");
		MongoDatabase mdb = new MongoDatabase();
		MongoTable mtb = new MongoTable();
		mdb.setConnect(connect);
		mdb.setName(logRecord.getDatebaseName());
		mtb.setMongoDatabase(mdb);
		mtb.setName(logRecord.getTableName());
		if (!mtb.getName().equals("")) {// 表名字为“” 说明是同步整个数据库。要撤销整个数据库
			// 将备份重新导入
			String [] url={logRecord.getRemarks() + "/" + mtb.getName() + ".bson",logRecord.getRemarks() + "/" + mtb.getName() + ".metadata.json"};
			this.mongoServiceImpl.importTb(mtb, url, true, new Progress());
			//this.mongoServiceImpl.importTbIndexes(mtb, logRecord.getRemarks() + "/" + mtb.getName() + ".metadata.json", true);
		} else {
			File file = new File(logRecord.getRemarks());// 获取文件夹
			String[] fileList = file.list();// 获取文件夹下所有文件
			for (int i = 0; i < fileList.length; i++) {// 循环得到备份文件地址并还原
				if (fileList[i].substring(fileList[i].length() - 5).equals(".json")) {
					String[] url = {"",logRecord.getRemarks() + "/" + fileList[i]};
					mtb.setName(fileList[i].substring(0, fileList[i].length() - 14));// 获取并设置准备还原的集合名
					this.mongoServiceImpl.importTb(mtb, url, true, new Progress());// 还原索引
				}
				if (fileList[i].substring(fileList[i].length() - 5).equals(".bson")) {
					
					String url[]={logRecord.getRemarks() + "/" + fileList[i],""};
					mtb.setName(fileList[i].substring(0, fileList[i].length() - 5));// 获取并设置准备还原的集合名
					this.mongoServiceImpl.importTb(mtb, url, true, new Progress());// 还原数据
				}
			}
		}

		// 修改日志记录描述为已撤销
		this.logRecordServiceImpl.update(logRecord);
		return "redirect_list";
	}

	public LogRecordService getLogRecordServiceImpl() {
		return logRecordServiceImpl;
	}

	public void setLogRecordServiceImpl(LogRecordService logRecordServiceImpl) {
		this.logRecordServiceImpl = logRecordServiceImpl;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCountPage() {
		return countPage;
	}

	public void setCountPage(int countPage) {
		this.countPage = countPage;
	}

	public List<LogRecordExtend> getList() {
		return list;
	}

	public void setList(List<LogRecordExtend> list) {
		this.list = list;
	}

	public ConnectService getConnectServiceImpl() {
		return connectServiceImpl;
	}

	public void setConnectServiceImpl(ConnectService connectServiceImpl) {
		this.connectServiceImpl = connectServiceImpl;
	}

	public MongoService getMongoServiceImpl() {
		return mongoServiceImpl;
	}

	public void setMongoServiceImpl(MongoService mongoServiceImpl) {
		this.mongoServiceImpl = mongoServiceImpl;
	}

	public LogRecord getLogRecord() {
		return logRecord;
	}

	public void setLogRecord(LogRecord logRecord) {
		this.logRecord = logRecord;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
