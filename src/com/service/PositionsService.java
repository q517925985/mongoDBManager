/**
 * Project:mongoDBManager
 * File:PositionsService.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.service;

import java.util.List;

import com.entity.Positions;

/**
 * @author shihaojie
 * @date 2015年4月16日
 * @version $Id$
 */
public interface PositionsService {
	public List<Positions> getAll();// 获取全部职位

	public List<Positions> getList(int page);// 获取职位列表

	public boolean addPosi(Positions p);// 添加职位

	public boolean updatePosi(Positions p);// 修改职位

	public boolean deletePosi(Positions p);// 删除职位
}
