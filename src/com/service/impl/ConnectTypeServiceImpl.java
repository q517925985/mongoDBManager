/**
 * Project:mongoDBManager
 * File:ConnectTypeServiceImpl.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.service.impl;

import java.util.List;

import com.dao.impl.ConnectTypeDaoImpl;
import com.entity.ConnectType;
import com.service.ConnectTypeService;

/**
 * @author shihaojie
 * @date 2015年4月29日
 * @version $Id$
 */
public class ConnectTypeServiceImpl implements ConnectTypeService {

	private ConnectTypeDaoImpl connectTypeDaoImpl;
	/**
	 * {@inheritDoc}
	 */
	public List<ConnectType> getAll() {
		return this.connectTypeDaoImpl.getAll();
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ConnectType> getList(int page) {
		return this.connectTypeDaoImpl.getList(page);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean addConnectType(ConnectType c) {
		return this.connectTypeDaoImpl.addConnectType(c);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean updateConnectType(ConnectType c) {
		return this.connectTypeDaoImpl.updateConnectType(c);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean deleteConnectType(ConnectType c) {
		return this.connectTypeDaoImpl.deleteConnectType(c);
	}

	public ConnectTypeDaoImpl getConnectTypeDaoImpl() {
		return connectTypeDaoImpl;
	}

	public void setConnectTypeDaoImpl(ConnectTypeDaoImpl connectTypeDaoImpl) {
		this.connectTypeDaoImpl = connectTypeDaoImpl;
	}

}
