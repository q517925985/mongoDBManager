/**
 * Project:mongoDBManager
 * File:MongoService.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.service;

import java.util.List;

import com.dao.impl.MongoDaoImpl;
import com.entity.Connect;
import com.entity.MongoDatabase;
import com.entity.MongoTable;
import com.entity.Progress;

/**
 * @author shihaojie
 * @date 2015年4月20日
 * @version $Id$
 */
public interface MongoService {
	public List<MongoDatabase> getList(Connect connect);// 获取数据库列表

	public List<MongoTable> getTbList(MongoDatabase mongoDatabase);// 获取集合列表

	public String[] export(MongoTable mongoTable,Progress progress);// 以文件形式存储集合并返回url;

	public String[] importTb(MongoTable tb, String[] url,boolean importType,Progress progress);// 将集合文件导入指定表。
	public MongoDaoImpl  getMongoDaoImpl();

}
