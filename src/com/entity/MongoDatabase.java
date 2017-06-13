/**
 * Project:mongoDBManager
 * File:MongoDatabase.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.entity;

/**
 * @author shihaojie
 * @date 2015年4月17日
 * @version $Id$
 */
public class MongoDatabase implements Comparable<MongoDatabase> {
	private Connect connect;// 所属连接
	private String name;// 数据库名字

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Connect getConnect() {
		return connect;
	}

	public void setConnect(Connect connect) {
		this.connect = connect;
	}

	public int compareTo(MongoDatabase mongoDatabase) {
		if (null == mongoDatabase)
			return 1;
		else {
			return this.name.compareTo(mongoDatabase.name);
		}

	}

}
