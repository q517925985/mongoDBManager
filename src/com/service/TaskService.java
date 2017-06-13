/**
 * Project:mongoDBManager
 * File:TaskService.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.service;

import java.util.List;

import com.entity.SourceTable;
import com.entity.Task;
import com.entity.TaskExtend;

/**
 * @author shihaojie
 * @date 2015年5月20日
 * @version $Id$
 */
public interface TaskService {
	
	public List<TaskExtend> getList(Task task,int page);//查询
	
	public int getCount(Task task,int page);//查询总数
	
	public void add(Task task,List<SourceTable> sourceTables);//添加

	public void update(Task task);//修改
	
	public void delete(Integer task);//删除
	
	public TaskExtend getTask(int id);//根据di查询Task 
	
}
