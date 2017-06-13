/**
 * Project:mongoDBManager
 * File:ConnectServiceImpl.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.service.impl;

import java.util.List;

import com.dao.impl.ConnectDaoImpl;
import com.entity.Connect;
import com.entity.ConnectExtend;
import com.service.ConnectService;

/**
 * @author shihaojie
 * @date 2015年4月21日
 * @version $Id$
 */
public class ConnectServiceImpl implements ConnectService {

	private ConnectDaoImpl connectDaoImpl;

	/**
	 * {@inheritDoc}
	 */
	public List<Connect> getAll() {
		return this.connectDaoImpl.getAll();
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ConnectExtend> getList(int page) {
		return this.connectDaoImpl.getList(page);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean addConnect(Connect c) {
		return this.connectDaoImpl.addConnect(c);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean updateConnect(Connect c) {
		return this.connectDaoImpl.updateConnect(c);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean deleteConnect(Connect c) {
		return this.connectDaoImpl.deleteConnect(c);
	}

	/** get set **/
	public ConnectDaoImpl getConnectDaoImpl() {
		return connectDaoImpl;
	}

	public void setConnectDaoImpl(ConnectDaoImpl connectDaoImpl) {
		this.connectDaoImpl = connectDaoImpl;
	}

	/**
	 * {@inheritDoc}
	 */
	public Connect getConnectById(String connect) {
		return this.connectDaoImpl.getConnectById(connect);
	}

}
