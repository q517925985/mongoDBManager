/**
 * Project:mongoDBManager
 * File:UsersDaoImpl.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dao.UsersDao;
import com.entity.UserExtend;
import com.entity.Users;

/**
 * @author shihaojie
 * @date 2015年4月14日
 * @version $Id$
 */
public class UsersDaoImpl extends HibernateDaoSupport implements UsersDao {

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<UserExtend> getList(Users u, int page) {
		List<UserExtend> users = new ArrayList<UserExtend>();
		String depid = u.getDepId() == 0 ? "depId>0 " : "depId=" + u.getDepId();// 设置部门条件
		String sql = "select ua.userId,ua.userName,ua.gender,ua.age,ua.phone,ua.email,ua.depId,ua.posiId ,dep.depName,posi.posiName,ua.password,ua.nickname  from users as ua, departments as dep,positions as posi  where ua.posiId=posi.id and ua.depId=dep.id and ua.username like '%"
				+ u.getUserName() + "%' and ua." + depid + " order by ua.userId  LIMIT " + (page - 1) * 8 + "," + 8;
		
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
			UserExtend user = new UserExtend();
			user.setUserId(Integer.parseInt(((Object[]) list.get(i))[0].toString()));
			user.setUserName(((Object[]) list.get(i))[1].toString());
			user.setGender(Boolean.parseBoolean(((Object[]) list.get(i))[2].toString()));
			user.setAge(Integer.parseInt(((Object[]) list.get(i))[3].toString()));
			user.setPhone((((Object[]) list.get(i))[4].toString()));
			user.setEmail(((Object[]) list.get(i))[5].toString());
			user.setDepId(Integer.parseInt(((Object[]) list.get(i))[6].toString()));
			user.setPosiId(Integer.parseInt(((Object[]) list.get(i))[7].toString()));
			user.setDepName(((Object[]) list.get(i))[8].toString());
			user.setPosiName(((Object[]) list.get(i))[9].toString());
			user.setPassword(((Object[]) list.get(i))[10].toString());
			user.setNickname(((Object[]) list.get(i))[11].toString());
			users.add(user);
		}
		return users;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public int getCount(Users u) {
		String depid = u.getDepId() == 0 ? "depId>0 " : "depId=" + u.getDepId();// 设置部门条件
		String sql = "select uc.userId from users as uc where uc.username like '%" + u.getUserName() + "%' and uc." + depid;
		Session session = this.getSession();
		List<Object[]> list=null;
		try {
			list = session.createSQLQuery(sql).list();
		} catch (Exception e) {
			return 0;
		}finally{
			//session.close();
		}
		return list.size();
	}

	/**
	 * {@inheritDoc}
	 */
	public Users getUserByName(Users user) {
		@SuppressWarnings("unchecked")
		List<Users> list = getHibernateTemplate().find(" from Users u where u.userName=? ", user.getUserName());
		if (list == null || list.size() <= 0) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean addUser(Users u) {
		this.getHibernateTemplate().save(u);
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean updateUser(Users u) {
		this.getHibernateTemplate().saveOrUpdate(u);
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean deleteUser(Users u) {
		Users user = (Users) getHibernateTemplate().find(" from Users u where u.userId=? ", u.getUserId()).get(0);
		this.getHibernateTemplate().delete(user);
		return false;
	}

}
