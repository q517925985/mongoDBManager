/**
 * Project:mongoDBManager
 * File:LogRecordService.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.service;

import java.sql.Date;
import java.util.List;

import com.entity.LogRecordExtend;
import com.entity.LogRecord;

/**
 * @author shihaojie
 * @date 2015年4月24日
 * @version $Id$
 */
public interface LogRecordService {
	public List<LogRecordExtend> getList(int page,Date begin,Date end,String name);// 根据页数获取列表

	public boolean add(LogRecord logRecord);// 添加日志记录

	public int getCount(Date begin,Date end,String name);// 获取总数
	public void update(LogRecord log);//撤销以后把备注改成已撤销

}
