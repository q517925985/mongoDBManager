/**
 * Project:mongoDBManager
 * File:ConnectDao.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.service;

import java.util.List;

import com.entity.Connect;
import com.entity.ConnectExtend;

/**
 * @author shihaojie
 * @date 2015年4月21日
 * @version $Id$
 */
public interface ConnectService {
	public List<Connect> getAll();// 获取全部数据库连接信息

	public List<ConnectExtend> getList(int page);// 分页查询数据库连接列表

	public boolean addConnect(Connect c);// 添加数据库连接

	public boolean updateConnect(Connect c);// 修改数据库连接

	public boolean deleteConnect(Connect c);// 删除数据库连接

	public Connect getConnectById(String connectIp);// 根据数据库连接Id查询对象
}
