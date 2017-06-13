/**
 * Project:mongoDBManager
 * File:TaskDaoImpl.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dao.TaskDao;
import com.entity.Task;
import com.entity.TaskExtend;

/**
 * @author shihaojie
 * @date 2015年5月20日
 * @version $Id$
 */
public class TaskDaoImpl extends HibernateDaoSupport implements TaskDao {

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "unchecked" })
	public List<TaskExtend> getList(Task task, int page) {
		List<TaskExtend> tasks = new ArrayList<TaskExtend>();
		String sql = "select t.id,sc.connectIp,t.sourceDatebaseName,rc.connectIp,t.receivedDatebaseName,t.remarks,t.importType,t.importIsDb from task as t,connect as sc,connect as rc where t.sourceConnectId=sc.connectId and t.receivedConnectId=rc.connectId and t.remarks like '%"+task.getRemarks()+"%'  LIMIT "
				+ (page - 1) * 10 + "," + 10;
		Session session = this.getSession();
		List<Object[]> list = null;
		try {
			list = session.createSQLQuery(sql).list();
		} catch (Exception e) {
			return null;
		} finally {
			//session.close();
		}
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = list.get(i);
			TaskExtend taskExtend = new TaskExtend();
			taskExtend.setId(Integer.parseInt(obj[0].toString()));
			taskExtend.setSourceConnectIp(obj[1].toString());
			taskExtend.setSourceDatebaseName(obj[2].toString());
			taskExtend.setReceivedConnectIp(obj[3].toString());
			taskExtend.setReceivedDatebaseName(obj[4].toString());
			taskExtend.setRemarks(obj[5].toString());
			taskExtend.setImportType( Boolean.parseBoolean(obj[6].toString()));
			taskExtend.setImportIsDb( Boolean.parseBoolean(obj[7].toString()));
			tasks.add(taskExtend);
		}
		return tasks;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public int getCount(Task task) {
		String sql = "select *from task where remarks like '%"+ task.getRemarks() + "%'";
		Session session = this.getSession();
		List<Object[]> list=null;
		try {
			list = session.createSQLQuery(sql).list();
		} catch (Exception e) {
			return 0;
		}finally{
			//session.close();
		}
		return list.size();
	}

	/**
	 * {@inheritDoc}
	 */
	public Task getTask(int id) {
		return (Task)this.getHibernateTemplate().find("from Task where id="+id).get(0);
	}
	/**
	 * {@inheritDoc}
	 */
	public Integer add(Task task) {
		return (Integer)this.getHibernateTemplate().save(task);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean update(Task task) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean delete(Task task) {
		this.getHibernateTemplate().delete(task);
		return false;
	}


}
