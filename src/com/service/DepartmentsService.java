/**
 * Project:mongoDBManager
 * File:DepartmentsService.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.service;

import java.util.List;

import com.entity.Departments;

/**
 * @author shihaojie
 * @date 2015年4月15日
 * @version $Id$
 */
public interface DepartmentsService {
	public List<Departments> getAll();// 获取全部部门信息

	public List<Departments> getList(int page);// 分页查询部门列表

	public boolean addDep(Departments d);// 添加部门

	public boolean updateDep(Departments d);// 修改部门

	public boolean deleteDep(Departments d);// 删除部门
}
