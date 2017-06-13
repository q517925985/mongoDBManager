/**
 * Project:mongoDBManager
 * File:ConnectTypeService.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.service;

import java.util.List;

import com.entity.ConnectType;

/**
 * @author shihaojie
 * @date 2015年4月29日
 * @version $Id$
 */
public interface ConnectTypeService {

	public List<ConnectType> getAll();// 获取全部连接分类信息

	public List<ConnectType> getList(int page);// 分页查询连接分类列表

	public boolean addConnectType(ConnectType c);// 添加连接分类

	public boolean updateConnectType(ConnectType c);// 修改连接分类

	public boolean deleteConnectType(ConnectType c);// 删除连接分类

}
