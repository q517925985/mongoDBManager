/**
 * Project:mongoDBManager
 * File:PositionsDaoImpl.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dao.PositionsDao;
import com.entity.Positions;

/**
 * @author shihaojie
 * @date 2015年4月16日
 * @version $Id$
 */
@SuppressWarnings("unchecked")
public class PositionsDaoImpl extends HibernateDaoSupport implements PositionsDao {

	/**
	 * {@inheritDoc}
	 */
	public List<Positions> getAll() {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().find("from Positions");
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Positions> getList(int page) {
		List<Positions> posis = new ArrayList<Positions>();
		String sql = "select * from positions as pa order by pa.id LIMIT " + (page - 1) * 8 + "," + 8;
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

			Positions posi = new Positions();
			posi.setId(Integer.parseInt(((Object[]) list.get(i))[0].toString()));
			posi.setPosiName(((Object[]) list.get(i))[1].toString());
			posis.add(posi);
		}
		return posis;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean addPosi(Positions p) {
		this.getHibernateTemplate().save(p);
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean updatePosi(Positions p) {
		this.getHibernateTemplate().saveOrUpdate(p);
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean deletePosi(Positions p) {
		String sql = "select *from users u,positions p where u.posiId=p.id and p.id="+p.getId();
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
			Positions posi = (Positions) getHibernateTemplate().find(" from Positions p where p.id=? ", p.getId()).get(0);
			this.getHibernateTemplate().delete(posi);
		}
		return false;
	}

}
