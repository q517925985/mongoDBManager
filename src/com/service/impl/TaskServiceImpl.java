/**
 * Project:mongoDBManager
 * File:TaskServiceImpl.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.dao.impl.ConnectDaoImpl;
import com.dao.impl.SourceTableDaoImpl;
import com.dao.impl.TaskDaoImpl;
import com.entity.SourceTable;
import com.entity.Task;
import com.entity.TaskExtend;
import com.service.TaskService;

/**
 * @author shihaojie
 * @date 2015年5月20日
 * @version $Id$
 */
public class TaskServiceImpl implements TaskService {

	private TaskDaoImpl taskDaoImpl;
	private SourceTableDaoImpl sourceTableDaoImpl;
	private ConnectDaoImpl connectDaoImpl;
	

	/**
	 * {@inheritDoc}
	 */
	public List<TaskExtend> getList(Task task, int page) {
		List<TaskExtend> list=new ArrayList<TaskExtend>();
		list=this.taskDaoImpl.getList(task, page);		
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getCount(Task task, int page) {
		return this.taskDaoImpl.getCount(task);
	}

	/**
	 * {@inheritDoc}
	 */
	public void add(Task task,List<SourceTable> sourceTables) {
		Integer id=this.taskDaoImpl.add(task);
		if(task.isImportIsDb()){
			return;
		}
		for (int i = 0; i < sourceTables.size(); i++) {
			sourceTables.get(i).setTaskId(id);
			this.sourceTableDaoImpl.add(sourceTables.get(i));
			
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void update(Task task) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(Integer taskid) {
		Task task=this.taskDaoImpl.getTask(taskid);
		this.taskDaoImpl.delete(task);
		this.sourceTableDaoImpl.delete(taskid);
		
	}

	/**
	 * {@inheritDoc}
	 */
	public TaskExtend getTask(int id) {
		Task task = this.taskDaoImpl.getTask(id);
		TaskExtend taskExtend = new TaskExtend();
		List<SourceTable> souseSourceTables = this.sourceTableDaoImpl.getList(id);
		taskExtend.setId(task.getId());
		taskExtend.setSourceConnectId(task.getSourceConnectId());
		taskExtend.setSourceConnectIp( this.connectDaoImpl.getConnectById(task.getSourceConnectId()+"" ).getConnectIp() );
		taskExtend.setSourceDatebaseName(task.getSourceDatebaseName());
		taskExtend.setReceivedConnectId(task.getReceivedConnectId());
		taskExtend.setReceivedConnectIp( this.connectDaoImpl.getConnectById(task.getReceivedConnectId()+"" ).getConnectIp() );
		taskExtend.setReceivedDatebaseName(task.getReceivedDatebaseName());
		taskExtend.setRemarks(task.getRemarks());
		taskExtend.setImportType(task.isImportType());
		taskExtend.setImportIsDb(task.isImportIsDb());
		taskExtend.setTableList(souseSourceTables);
		return taskExtend;
	}

	public TaskDaoImpl getTaskDaoImpl() {
		return taskDaoImpl;
	}

	public void setTaskDaoImpl(TaskDaoImpl taskDaoImpl) {
		this.taskDaoImpl = taskDaoImpl;
	}

	public SourceTableDaoImpl getSourceTableDaoImpl() {
		return sourceTableDaoImpl;
	}

	public void setSourceTableDaoImpl(SourceTableDaoImpl sourceTableDaoImpl) {
		this.sourceTableDaoImpl = sourceTableDaoImpl;
	}

	public ConnectDaoImpl getConnectDaoImpl() {
		return connectDaoImpl;
	}

	public void setConnectDaoImpl(ConnectDaoImpl connectDaoImpl) {
		this.connectDaoImpl = connectDaoImpl;
	}
	
}
