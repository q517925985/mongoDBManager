/**
 * Project:mongoDBManager
 * File:MongoDao.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.dao;

import java.util.List;

import com.entity.Connect;
import com.entity.MongoDatabase;
import com.entity.MongoTable;
import com.entity.Progress;

/**
 * @author shihaojie
 * @date 2015年4月20日
 * @version $Id$
 */
public interface MongoDao {
	public List<MongoDatabase> getList(Connect connect);// 获取全部数据库信息

	public List<MongoTable> getTbList(MongoDatabase mongoDatabase);// 获取全部集合信息

	public String[] export(MongoTable tb,Progress progress);// 将指定表导出
	public String[] importTb(MongoTable tb, String[] url,boolean importType,Progress progress);// 将集合文件导入到指定表

	public String CopyMongoDB(MongoTable tbA,MongoDatabase dbB,boolean importType);// 复制数据库到其他数据库中,返回备份url
}
