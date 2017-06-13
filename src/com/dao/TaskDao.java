/**
 * Project:mongoDBManager
 * File:TaskDao.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.dao;

import java.util.List;

import com.entity.Task;
import com.entity.TaskExtend;

/**
 * @author shihaojie
 * @date 2015年5月19日
 * @version $Id$
 */
public interface TaskDao {
	
	public List<TaskExtend> getList(Task task, int page);//按条件分页查询
	
	public int getCount(Task task);//按条件查询列表总数
	
	public Task getTask(int id);//根据id查询Task
	
	public Integer add(Task task);//添加任务
	
	public boolean update(Task task);//修改任务
	
	public boolean delete(Task task);//删除任务
	
}
