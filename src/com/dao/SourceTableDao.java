/**
 * Project:mongoDBManager
 * File:SourceTable.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.dao;

import java.util.List;

import com.entity.SourceTable;

/**
 * @author shihaojie
 * @date 2015年5月19日
 * @version $Id$
 */
public interface SourceTableDao {
	
	public boolean add(SourceTable sourceTable);//添加数据源table
	
	public List<SourceTable> getList(int taskId);//根据任务id查询对应任务table
	
	public boolean delete(int taskId);//根据任务id删除对应任务table
	
}
