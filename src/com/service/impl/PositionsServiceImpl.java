/**
 * Project:mongoDBManager
 * File:PositionsServiceImpl.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.service.impl;

import java.util.List;

import com.dao.impl.PositionsDaoImpl;
import com.entity.Positions;
import com.service.PositionsService;

/**
 * @author shihaojie
 * @date 2015年4月16日
 * @version $Id$
 */
public class PositionsServiceImpl implements PositionsService {

	private PositionsDaoImpl positionsDaoImpl;

	/**
	 * {@inheritDoc}
	 */
	public List<Positions> getAll() {
		return this.positionsDaoImpl.getAll();
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Positions> getList(int page) {

		return this.positionsDaoImpl.getList(page);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean addPosi(Positions p) {
		this.positionsDaoImpl.addPosi(p);
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean updatePosi(Positions p) {
		this.positionsDaoImpl.updatePosi(p);
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean deletePosi(Positions p) {
		this.positionsDaoImpl.deletePosi(p);
		return false;
	}

	/** get set ***/
	public PositionsDaoImpl getPositionsDaoImpl() {
		return positionsDaoImpl;
	}

	public void setPositionsDaoImpl(PositionsDaoImpl positionsDaoImpl) {
		this.positionsDaoImpl = positionsDaoImpl;
	}
}
