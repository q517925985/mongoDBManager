/**
 * Project:mongoDBManager
 * File:userDao.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.dao;

import java.util.List;

import com.entity.UserExtend;
import com.entity.Users;

/**
 * @author shihaojie
 * @date 2015年4月14日
 * @version $Id$
 */
public interface UsersDao {
	// 按条件分页查询用户
	public List<UserExtend> getList(Users u, int page);

	// 获取用户总条数
	public int getCount(Users u);

	// 根据用户名获取用户对象
	public Users getUserByName(Users user);

	// 添加用户
	public boolean addUser(Users u);

	// 更新用户
	public boolean updateUser(Users u);

	// 删除用户
	public boolean deleteUser(Users u);

}
