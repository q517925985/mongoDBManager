/**
 * Project:mongoDBManager
 * File:DdepartmentsDao.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.dao;

import java.util.List;

import com.entity.Departments;

/**
 * @author shihaojie
 * @date 2015年4月15日
 * @version $Id$
 */
public interface DepartmentsDao {
	// 查询全部部门信息
	public List<Departments> getAll();

	// 按条件分页查询部门
	public List<Departments> getList(int page);

	// 添加部门
	public boolean addDep(Departments d);

	// 更新部门
	public boolean updateDep(Departments d);

	// 删除部门
	public boolean deleteDep(Departments ds);
}
