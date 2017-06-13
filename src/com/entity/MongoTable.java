/**
 * Project:mongoDBManager
 * File:MongoTable.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.entity;

/**
 * @author shihaojie
 * @date 2015年4月22日
 * @version $Id$
 */
public class MongoTable implements Comparable<MongoTable>{
	private MongoDatabase mongoDatabase;// 所属数据库
	private String name;
	private int size;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public MongoDatabase getMongoDatabase() {
		return mongoDatabase;
	}

	public void setMongoDatabase(MongoDatabase mongoDatabase) {
		this.mongoDatabase = mongoDatabase;
	}

	/**
	 * {@inheritDoc}
	 */
	public int compareTo(MongoTable o) {
		if (null == o)
			return 1;
		else {
			return this.name.compareTo(o.name);
		}
	}
}
