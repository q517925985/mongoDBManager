/**
 * Project:mongoDBManager
 * File:LogRecordServiceImpl.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.service.impl;

import java.sql.Date;
import java.util.List;

import com.dao.impl.LogRecordDaoImpl;
import com.entity.LogRecordExtend;
import com.entity.LogRecord;
import com.service.LogRecordService;

/**
 * @author shihaojie
 * @date 2015年4月24日
 * @version $Id$
 */
public class LogRecordServiceImpl implements LogRecordService {

	private LogRecordDaoImpl logRecordDaoImpl;

	/**
	 * {@inheritDoc}
	 */
	public List<LogRecordExtend> getList(int page,Date begin,Date end,String name) {
		return this.logRecordDaoImpl.getList(page,begin,end,name);
	}

	/**
	 * {@inheritDoc}
	 */
	public int getCount(Date begin,Date end,String name) {
		return this.logRecordDaoImpl.getCount(begin,end,name);
	}
	/**
	 * {@inheritDoc}
	 */
	public boolean add(LogRecord logRecord) {
		return this.logRecordDaoImpl.add(logRecord);
	}

	public LogRecordDaoImpl getLogRecordDaoImpl() {
		return logRecordDaoImpl;
	}

	public void setLogRecordDaoImpl(LogRecordDaoImpl logRecordDaoImpl) {
		this.logRecordDaoImpl = logRecordDaoImpl;
	}


	/**
	 * {@inheritDoc}
	 */
	public void update(LogRecord log) {
		this.logRecordDaoImpl.update(log);

	}

}
