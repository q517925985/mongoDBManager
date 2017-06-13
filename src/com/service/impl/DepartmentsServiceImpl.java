/**
 * Project:mongoDBManager
 * File:DepartmentsServiceImpl.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.service.impl;

import java.util.List;

import com.dao.impl.DepartmentsDaoImpl;
import com.entity.Departments;
import com.service.DepartmentsService;

/**
 * @author shihaojie
 * @date 2015年4月15日
 * @version $Id$
 */
public class DepartmentsServiceImpl implements DepartmentsService {

	private DepartmentsDaoImpl departmentsDaoImpl;

	/**
	 * {@inheritDoc}
	 */
	public List<Departments> getAll() {
		return departmentsDaoImpl.getAll();
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Departments> getList(int page) {
		return this.departmentsDaoImpl.getList(page);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws Exception
	 */
	public boolean addDep (Departments d){
		this.departmentsDaoImpl.addDep(d);
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean updateDep(Departments d) {
		return this.departmentsDaoImpl.updateDep(d);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean deleteDep(Departments d) {
		return this.departmentsDaoImpl.deleteDep(d);
	}

	public DepartmentsDaoImpl getDepartmentsDaoImpl() {
		return departmentsDaoImpl;
	}

	public void setDepartmentsDaoImpl(DepartmentsDaoImpl departmentsDaoImpl) {
		this.departmentsDaoImpl = departmentsDaoImpl;
	}

}
