/**
 * Project:mongoDBManager
 * File:DConnectDao.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.dao;

import java.util.List;

import com.entity.Connect;
import com.entity.ConnectExtend;

/**
 * @author shihaojie
 * @date 2015年4月15日
 * @version $Id$
 */
public interface ConnectDao {
	// 查询全部连接信息
	public List<Connect> getAll();

	// 按分页查询连接
	public List<ConnectExtend> getList(int page);

	// 添加连接
	public boolean addConnect(Connect c);

	// 更新连接
	public boolean updateConnect(Connect c);

	// 删除连接
	public boolean deleteConnect(Connect c);

	// 根据Id查对象
	public Connect getConnectById(String connectId);
}
