/**
 * Project:mongoDBManager
 * File:ConnectAction.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.action;

import java.util.List;

import com.entity.Connect;
import com.entity.ConnectExtend;
import com.entity.ConnectType;
import com.opensymphony.xwork2.ActionSupport;
import com.service.ConnectService;
import com.service.ConnectTypeService;

/**
 * @author shihaojie
 * @date 2015年4月22日
 * @version $Id$
 */
public class ConnectAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2410008546142584626L;
	private ConnectService connectServiceImpl;
	private ConnectTypeService connectTypeServiceImpl;
	private int page;// 当前页
	private int count;// 总条数
	private int countPage;// 总页数
	private List<ConnectExtend> list;// 存放数据库连接列表
	private List<ConnectType> typeList;// 存放数据库连接分类列表
	
	private Connect addConnect;// 存放增加数据库连接信息
	private String deleteConnectId;// 存放删除数据库连接id
	private Connect updateConnect;// 存放更新数据库连接

	// 列表
	public String list() {
		page = page <= 0 ? 1 : page;
		typeList=this.connectTypeServiceImpl.getAll();//获取分类列表
		this.list = this.connectServiceImpl.getList(page);// 获取连接列表
		this.count = this.connectServiceImpl.getAll().size();// 设置总条数
		this.countPage = count % 8 == 0 ? count / 8 : count / 8 + 1;// 设置总页数
		return "list";
	}

	// 添加
	public String addConnect() {
		this.connectServiceImpl.addConnect(addConnect);
		return "redirect_list";
	}

	// 修改
	public String updateConnect() {
		this.connectServiceImpl.updateConnect(updateConnect);
		return "redirect_list";
	}

	// 删除
	public String deleteConnect() {
		String id[] = deleteConnectId.split(",");

		for (int i = 0; i < id.length; i++) {
			Connect c = new Connect();
			c.setConnectId(Integer.parseInt(id[i]));
			this.connectServiceImpl.deleteConnect(c);
		}
		return "redirect_list";
	}

	/******** get set **********/
	public ConnectService getConnectServiceImpl() {
		return connectServiceImpl;
	}

	public void setConnectServiceImpl(ConnectService connectServiceImpl) {
		this.connectServiceImpl = connectServiceImpl;
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

	public List<ConnectExtend> getList() {
		return list;
	}

	public void setList(List<ConnectExtend> list) {
		this.list = list;
	}

	public Connect getAddConnect() {
		return addConnect;
	}

	public void setAddConnect(Connect addConnect) {
		this.addConnect = addConnect;
	}

	public String getDeleteConnectId() {
		return deleteConnectId;
	}

	public void setDeleteConnectId(String deleteConnectId) {
		this.deleteConnectId = deleteConnectId;
	}

	public Connect getUpdateConnect() {
		return updateConnect;
	}

	public void setUpdateConnect(Connect updateConnect) {
		this.updateConnect = updateConnect;
	}

	public ConnectTypeService getConnectTypeServiceImpl() {
		return connectTypeServiceImpl;
	}

	public void setConnectTypeServiceImpl(ConnectTypeService connectTypeServiceImpl) {
		this.connectTypeServiceImpl = connectTypeServiceImpl;
	}

	public List<ConnectType> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<ConnectType> typeList) {
		this.typeList = typeList;
	}

}
