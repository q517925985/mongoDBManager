/**
 * Project:mongoDBManager
 * File:LogRecordDao.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.dao;

import java.sql.Date;
import java.util.List;

import com.entity.LogRecordExtend;
import com.entity.LogRecord;

/**
 * @author shihaojie
 * @date 2015年4月24日
 * @version $Id$
 */
public interface LogRecordDao {
	// 分页查询日志记录
	public List<LogRecordExtend> getList(int page,Date begin,Date end,String name);

	// 获取记录总数
	public int getCount(Date begin,Date end,String name);

	// 添加日志记录
	public boolean add(LogRecord log);
	
	//将指定日志记录修改为已撤销
	public void update(LogRecord log);

}
