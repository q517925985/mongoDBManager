/**
 * Project:mongoDBManager
 * File:SourceTable.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dao.SourceTableDao;
import com.entity.SourceTable;


/**
 * @author shihaojie
 * @date 2015年5月19日
 * @version $Id$
 */
public class SourceTableDaoImpl extends HibernateDaoSupport implements SourceTableDao {

	/**
	 * {@inheritDoc}
	 */
	public boolean add(SourceTable sourceTable) {
		this.getHibernateTemplate().save(sourceTable);
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<SourceTable> getList(int taskId) {
		return this.getHibernateTemplate().find("from SourceTable where taskId="+taskId);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean delete(int taskId) {
		List<SourceTable> list=this.getList(taskId);
		for (int i = 0; i < list.size(); i++) {
			this.getHibernateTemplate().delete(list.get(i));
		}
		return false;
	}

	

}
