/**
 * Project:mongoDBManager
 * File:PositionsAction.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.action;

import java.util.List;

import com.entity.Positions;
import com.opensymphony.xwork2.ActionSupport;
import com.service.PositionsService;

/**
 * @author shihaojie
 * @date 2015年4月17日
 * @version $Id$
 */
public class PositionsAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7857956651945037954L;

	private PositionsService positionsServiceImpl;
	private int page;// 当前页
	private int count;// 总条数
	private int countPage;// 总页数
	private List<Positions> list;// 存放职位列表
	private Positions addPosi;// 存放增加职位信息
	private String deletePosisId;// 存放删除职位id

	private Positions updatePosi;// 存放需要修改的职位信息

	public String list() {
		page = page <= 0 ? 1 : page;
		this.list = this.positionsServiceImpl.getList(page);// 获取职位列表
		this.count = this.positionsServiceImpl.getAll().size();// 设置总条数
		this.countPage = count % 8 == 0 ? count / 8 : count / 8 + 1;// 设置总页数
		return "list";
	}

	public String addPosi() {
		this.positionsServiceImpl.addPosi(addPosi);
		return "redirect_list";
	}

	public String deletePosi() {
		String id[] = deletePosisId.split(",");

		for (int i = 0; i < id.length; i++) {
			Positions p = new Positions();
			p.setId(Integer.parseInt(id[i]));
			this.positionsServiceImpl.deletePosi(p);
		}
		return "redirect_list";
	}

	public String updatePosi() {
		this.positionsServiceImpl.updatePosi(updatePosi);
		return "redirect_list";
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

	public List<Positions> getList() {
		return list;
	}

	public void setList(List<Positions> list) {
		this.list = list;
	}

	public PositionsService getPositionsServiceImpl() {
		return positionsServiceImpl;
	}

	public void setPositionsServiceImpl(PositionsService positionsServiceImpl) {
		this.positionsServiceImpl = positionsServiceImpl;
	}

	public Positions getAddPosi() {
		return addPosi;
	}

	public void setAddPosi(Positions addPosi) {
		this.addPosi = addPosi;
	}

	public String getDeletePosisId() {
		return deletePosisId;
	}

	public void setDeletePosisId(String deletePosisId) {
		this.deletePosisId = deletePosisId;
	}

	public Positions getUpdatePosi() {
		return updatePosi;
	}

	public void setUpdatePosi(Positions updatePosi) {
		this.updatePosi = updatePosi;
	}

}
