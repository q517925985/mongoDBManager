/**
 * Project:mongoDBManager
 * File:UsersServiceImpl.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.service.impl;

import java.util.List;

import com.dao.impl.UsersDaoImpl;
import com.entity.UserExtend;
import com.entity.Users;
import com.service.UsersService;

/**
 * @author shihaojie
 * @date 2015年4月14日
 * @version $Id$
 */
public class UsersServiceImpl implements UsersService {

	private UsersDaoImpl userDaoImpl;

	/**
	 * {@inheritDoc}
	 */
	public Users getUserByName(Users user) {
		return (Users) this.userDaoImpl.getUserByName(user);
	}

	/**
	 * {@inheritDoc}
	 */
	public int getCount(Users u) {
		return this.userDaoImpl.getCount(u);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<UserExtend> getList(Users u, int page) {
		return this.userDaoImpl.getList(u, page);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean addUser(Users user) {
		return this.userDaoImpl.addUser(user);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean updateUser(Users user) {
		this.userDaoImpl.updateUser(user);
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean deleteUser(Users user) {

		return this.userDaoImpl.deleteUser(user);
	}

	/*** get set ******/
	public UsersDaoImpl getUserDaoImpl() {
		return userDaoImpl;
	}

	public void setUserDaoImpl(UsersDaoImpl userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}

}
