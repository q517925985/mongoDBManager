/**
 * Project:mongoDBManager
 * File:DepartmentsDaoImpl.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dao.DepartmentsDao;
import com.entity.Departments;

/**
 * @author shihaojie
 * @date 2015年4月15日
 * @version $Id$
 */
@SuppressWarnings("unchecked")
public class DepartmentsDaoImpl extends HibernateDaoSupport implements DepartmentsDao {

	/**
	 * {@inheritDoc}
	 */
	public List<Departments> getAll() {
		List<Departments> list = this.getHibernateTemplate().find("from Departments");

		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Departments> getList(int page) {
		List<Departments> deps = new ArrayList<Departments>();
		String sql = "select * from departments as d order by d.id LIMIT " + (page - 1) * 8 + "," + 8;

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

			Departments dep = new Departments();
			dep.setId(Integer.parseInt(((Object[]) list.get(i))[0].toString()));
			dep.setDepName(((Object[]) list.get(i))[1].toString());
			deps.add(dep);
		}
		return deps;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean addDep(Departments d) {
		this.getHibernateTemplate().save(d);
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean updateDep(Departments d) {
		this.getHibernateTemplate().saveOrUpdate(d);
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean deleteDep(Departments d) {
		String sql = "select *from users u,departments d where u.depId=d.id and d.id="+d.getId();
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
			Departments dep = (Departments) getHibernateTemplate().find(" from Departments d where d.id=? ", d.getId()).get(0);
			this.getHibernateTemplate().delete(dep);
		}
		return false;
	}

}
