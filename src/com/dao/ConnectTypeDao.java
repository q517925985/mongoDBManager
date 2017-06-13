/**
 * Project:mongoDBManager
 * File:connectTypeDao.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.dao;

import java.util.List;

import com.entity.ConnectType;

/**
 * @author shihaojie
 * @date 2015年4月29日
 * @version $Id$
 */
public interface ConnectTypeDao {
	// 查询全部连接类型信息
	public List<ConnectType> getAll();

	// 按分页查询连接类型
	public List<ConnectType> getList(int page);

	// 添加连接类型
	public boolean addConnectType(ConnectType c);

	// 更新连接类型
	public boolean updateConnectType(ConnectType c);

	// 删除连接类型
	public boolean deleteConnectType(ConnectType c);
	
	// 根据Id查对象
	public ConnectType getConnectTypeById(String ConnectTypeId);
}
