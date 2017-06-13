/**
 * Project:mongoDBManager
 * File:PositionsDao.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.dao;

import java.util.List;

import com.entity.Positions;

/**
 * @author shihaojie
 * @date 2015年4月16日
 * @version $Id$
 */
public interface PositionsDao {
	// 查询全部职位信息
	public List<Positions> getAll();

	// 按条件分页查询职位
	public List<Positions> getList(int page);

	// 添加职位
	public boolean addPosi(Positions p);

	// 更新职位
	public boolean updatePosi(Positions p);

	// 删除职位
	public boolean deletePosi(Positions p);
}
