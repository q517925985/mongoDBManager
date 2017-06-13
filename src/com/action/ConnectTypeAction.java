/**
 * Project:mongoDBManager
 * File:ConnectTypeAction.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.action;

import java.util.List;

import com.entity.ConnectType;
import com.opensymphony.xwork2.ActionSupport;
import com.service.ConnectTypeService;

/**
 * @author shihaojie
 * @date 2015年4月29日
 * @version $Id$
 */
public class ConnectTypeAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6301615137099053008L;
	private ConnectTypeService connectTypeServiceImpl;

	private int page;// 当前页
	private int count;// 总条数
	private int countPage;// 总页数
	private List<ConnectType> list;// 存放部门列表
	private ConnectType addType;// 存放增加部门信息
	private String deleteType;// 存放删除部门id

	private ConnectType updateType;// 存放更新用户信息

	public String list() {
		page = page <= 0 ? 1 : page;
		this.list = this.connectTypeServiceImpl.getList(page);// 获取职位列表
		this.count = this.connectTypeServiceImpl.getAll().size();// 设置总条数
		this.countPage = count % 8 == 0 ? count / 8 : count / 8 + 1;// 设置总页数
		return "list";
	}

	public String addDep() {
		this.connectTypeServiceImpl.addConnectType(addType);
		return "redirect_list";
	}

	public String deleteDep() {
		String id[] = deleteType.split(",");

		for (int i = 0; i < id.length; i++) {
			ConnectType d = new ConnectType();
			d.setId(Integer.parseInt(id[i]));
			this.connectTypeServiceImpl.deleteConnectType(d);
		}
		return "redirect_list";
	}

	public String updateDep() {
		this.connectTypeServiceImpl.updateConnectType(updateType);
		return "redirect_list";
	}
	
	/*****  set get *****/
	public int getCountPage() {
		return countPage;
	}


	public void setCountPage(int countPage) {
		this.countPage = countPage;
	}

	public List<ConnectType> getList() {
		return list;
	}

	public void setList(List<ConnectType> list) {
		this.list = list;
	}

	public ConnectType getAddType() {
		return addType;
	}

	public void setAddType(ConnectType addType) {
		this.addType = addType;
	}

	public String getDeleteType() {
		return deleteType;
	}

	public void setDeleteType(String deleteType) {
		this.deleteType = deleteType;
	}

	public ConnectType getUpdateType() {
		return updateType;
	}

	public void setUpdateType(ConnectType updateType) {
		this.updateType = updateType;
	}

	public ConnectTypeService getConnectTypeServiceImpl() {
		return connectTypeServiceImpl;
	}

	public void setConnectTypeServiceImpl(ConnectTypeService connectTypeServiceImpl) {
		this.connectTypeServiceImpl = connectTypeServiceImpl;
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
}
