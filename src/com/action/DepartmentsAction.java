/**
 * Project:mongoDBManager
 * File:DepartmentsAction.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.action;

import java.util.List;

import com.entity.Departments;
import com.opensymphony.xwork2.ActionSupport;
import com.service.DepartmentsService;

/**
 * @author shihaojie
 * @date 2015年4月17日
 * @version $Id$
 */
public class DepartmentsAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7507259046215073446L;

	private DepartmentsService departmentsServiceImpl;
	private int page;// 当前页
	private int count;// 总条数
	private int countPage;// 总页数
	private List<Departments> list;// 存放部门列表
	private Departments addDep;// 存放增加部门信息
	private String deleteDepsId;// 存放删除部门id

	private Departments updateDep;// 存放更新用户信息

	public String list() {
		page = page <= 0 ? 1 : page;
		this.list = this.departmentsServiceImpl.getList(page);// 获取职位列表
		this.count = this.departmentsServiceImpl.getAll().size();// 设置总条数
		this.countPage = count % 8 == 0 ? count / 8 : count / 8 + 1;// 设置总页数
		return "list";
	}

	public String addDep() {
		this.departmentsServiceImpl.addDep(addDep);
		return "redirect_list";
	}

	public String deleteDep() {
		String id[] = deleteDepsId.split(",");

		for (int i = 0; i < id.length; i++) {
			Departments d = new Departments();
			d.setId(Integer.parseInt(id[i]));
			this.departmentsServiceImpl.deleteDep(d);
		}
		return "redirect_list";
	}

	public String updateDep() {
		this.departmentsServiceImpl.updateDep(updateDep);
		return "redirect_list";
	}

	/********* get set *********/
	public DepartmentsService getDepartmentsServiceImpl() {
		return departmentsServiceImpl;
	}

	public void setDepartmentsServiceImpl(DepartmentsService departmentsServiceImpl) {
		this.departmentsServiceImpl = departmentsServiceImpl;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCountPage() {
		return countPage;
	}

	public void setCountPage(int countPage) {
		this.countPage = countPage;
	}

	public List<Departments> getList() {
		return list;
	}

	public void setList(List<Departments> list) {
		this.list = list;
	}

	public Departments getAddDep() {
		return addDep;
	}

	public void setAddDep(Departments addDep) {
		this.addDep = addDep;
	}

	public String getDeleteDepsId() {
		return deleteDepsId;
	}

	public void setDeleteDepsId(String deleteDepsId) {
		this.deleteDepsId = deleteDepsId;
	}

	public Departments getUpdateDep() {
		return updateDep;
	}

	public void setUpdateDep(Departments updateDep) {
		this.updateDep = updateDep;
	}

}
