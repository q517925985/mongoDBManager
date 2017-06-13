/**
 * Project:mongoDBManager
 * File:MongoServiceImpl.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.service.impl;

import java.util.List;

import com.dao.impl.MongoDaoImpl;
import com.entity.Connect;
import com.entity.MongoDatabase;
import com.entity.MongoTable;
import com.entity.Progress;
import com.service.MongoService;

/**
 * @author shihaojie
 * @date 2015年4月20日
 * @version $Id$
 */
public class MongoServiceImpl implements MongoService {

	/**
	 * {@inheritDoc}
	 */
	private MongoDaoImpl mongoDaoImpl;

	public List<MongoDatabase> getList(Connect connect) {
		return this.mongoDaoImpl.getList(connect);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<MongoTable> getTbList(MongoDatabase mongoDatabase) {
		return this.mongoDaoImpl.getTbList(mongoDatabase);
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] export(MongoTable mongoTable,Progress progress) {
		return this.mongoDaoImpl.export(mongoTable,progress);
	}
	/**
	 * {@inheritDoc}
	 */
	public String[] importTb(MongoTable tb, String url[],boolean importType,Progress progress) {
		return this.mongoDaoImpl.importTb(tb, url,importType,progress);
	}

	/******** get set ***********/
	public MongoDaoImpl getMongoDaoImpl() {
		return mongoDaoImpl;
	}

	public void setMongoDaoImpl(MongoDaoImpl mongoDaoImpl) {
		this.mongoDaoImpl = mongoDaoImpl;
	}




}
