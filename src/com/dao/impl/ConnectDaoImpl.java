/**
 * Project:mongoDBManager
 * File:ConnectDaoImpl.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dao.ConnectDao;
import com.entity.Connect;
import com.entity.ConnectExtend;

/**
 * @author shihaojie
 * @date 2015年4月15日
 * @version $Id$
 */
@SuppressWarnings("unchecked")
public class ConnectDaoImpl extends HibernateDaoSupport implements ConnectDao {

	/**
	 * {@inheritDoc}
	 */
	public List<Connect> getAll() {
		List<Connect> list = this.getHibernateTemplate().find("from Connect");
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ConnectExtend> getList(int page) {
		List<ConnectExtend> connects = new ArrayList<ConnectExtend>();
		
		String sql = "select c.connectId,c.connectName,c.connectTypeId,c.connectIp,c.connectPort,c.connectUserName,c.connectPassword,ct.typeName from connect as c,connectType as ct where c.connectTypeId=ct.id order by c.connectId LIMIT " + (page - 1) * 8 + "," + 8;
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
			ConnectExtend connect = new ConnectExtend();
			connect.setConnectId(Integer.parseInt(((Object[]) list.get(i))[0].toString()));
			connect.setConnectName(((Object[]) list.get(i))[1].toString());
			connect.setConnectTypeId(Integer.parseInt(((Object[]) list.get(i))[2].toString()));
			connect.setConnectIp(((Object[]) list.get(i))[3].toString());
			connect.setConnectPort(Integer.parseInt(((Object[]) list.get(i))[4].toString()));
			connect.setConnectUserName(((Object[]) list.get(i))[5].toString());
			connect.setConnectPassword(((Object[]) list.get(i))[6].toString());
			connect.setConnectTypeName(((Object[]) list.get(i))[7].toString());
			connects.add(connect);
		}
		return connects;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean addConnect(Connect c) {
		this.getHibernateTemplate().save(c);
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean updateConnect(Connect c) {
		this.getHibernateTemplate().saveOrUpdate(c);
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean deleteConnect(Connect c) {
		Connect connect = (Connect) getHibernateTemplate().find(" from Connect d where d.connectId=? ", c.getConnectId()).get(0);
		this.getHibernateTemplate().delete(connect);
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public Connect getConnectById(String connectId) {
		Connect connect = (Connect) getHibernateTemplate().find(" from Connect d where d.connectId=? ", Integer.parseInt(connectId)).get(0);

		return connect;
	}

}
