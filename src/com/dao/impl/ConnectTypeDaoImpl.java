/**
 * Project:mongoDBManager
 * File:ConnectTypeDaoImpl.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dao.ConnectTypeDao;
import com.entity.ConnectType;

/**
 * @author shihaojie
 * @date 2015年4月29日
 * @version $Id$
 */
@SuppressWarnings("unchecked")
public class ConnectTypeDaoImpl extends HibernateDaoSupport implements ConnectTypeDao {

	/**
	 * {@inheritDoc}
	 */
	public List<ConnectType> getAll() {
		return this.getHibernateTemplate().find("from ConnectType");
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ConnectType> getList(int page) {
		List<ConnectType> types = new ArrayList<ConnectType>();
		String sql = "select * from connectType as c order by c.id LIMIT " + (page - 1) * 8 + "," + 8;
		Session session = this.getSession();
		List<Object[]> list=null;
		try {
			list = session.createSQLQuery(sql).list();
		} catch (Exception e) {
			return null;
		}finally{
			//session.close();
		}
		for (int i = 0; i < list.size(); i++) {
			ConnectType type = new ConnectType();
			type.setId(Integer.parseInt(((Object[]) list.get(i))[0].toString()));
			type.setTypeName(((Object[]) list.get(i))[1].toString());
			types.add(type);
		}
		return types;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean addConnectType(ConnectType c) {
		this.getHibernateTemplate().save(c);
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean updateConnectType(ConnectType c) {
		this.getHibernateTemplate().update(c);
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean deleteConnectType(ConnectType c) {
		String sql = "select *from connect p,connectType t where p.connectTypeId=t.id and t.id="+c.getId();
		Session session = this.getSession();
		List<Object[]> list=null;
		try {
			list = session.createSQLQuery(sql).list();
		} catch (Exception e) {
			return false;
		}finally{
			//session.close();
		}
		if(list.size()<=0){
			c=(ConnectType)getHibernateTemplate().find(" from ConnectType c where c.id=? ", c.getId()).get(0);
			this.getHibernateTemplate().delete(c);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public ConnectType getConnectTypeById(String ConnectTypeId) {
		
		return null;
	}

}
