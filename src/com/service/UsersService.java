/**
 * Project:mongoDBManager
 * File:UsersService.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.service;

import java.util.List;

import com.entity.UserExtend;
import com.entity.Users;

/**
 * @author shihaojie
 * @date 2015年4月14日
 * @version $Id$
 */
public interface UsersService {
	public Users getUserByName(Users user);// 登录

	public List<UserExtend> getList(Users u, int page);// 获取用户列表

	public int getCount(Users u);// 获取用户总条数

	public boolean addUser(Users user);// 添加用户

	public boolean updateUser(Users user);// 修改用户

	public boolean deleteUser(Users user);// 删除用户

}
