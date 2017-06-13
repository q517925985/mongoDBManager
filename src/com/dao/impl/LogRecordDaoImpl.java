/**
 * Project:mongoDBManager
 * File:LogRecordDaoImpl.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dao.LogRecordDao;
import com.entity.LogRecordExtend;
import com.entity.LogRecord;

/**
 * @author shihaojie
 * @date 2015年4月24日
 * @version $Id$
 */
@SuppressWarnings("unchecked")
public class LogRecordDaoImpl extends HibernateDaoSupport implements LogRecordDao {

	/**
	 * {@inheritDoc}
	 */
	public List<LogRecordExtend> getList(int page,Date begin,Date end,String name) {
		List<LogRecordExtend> logs = new ArrayList<LogRecordExtend>();

		String sql = "select l.id,l.userId,l.connectId,l.datebaseName,l.tableName,l.operationType,l.operationTime,u.nickname,l.remarks,c.connectIp  from log_record as l ,users as u,connect as c where l.connectId=c.connectId and l.userId=u.userId and  unix_timestamp( l.operationTime ) between unix_timestamp( '"+begin+"') and unix_timestamp( '"+end+" 23:59:59' ) and u.nickname like '%"
				+ name + "%' order by l.id desc LIMIT "
				+ (page - 1) * 10 + "," + 10;
		Session session = this.getSession();
		List<Object[]> list=null;
		try {
			list = session.createSQLQuery(sql).list();
		} catch (Exception e) {
			return null;
		}finally{
			//session.close();
		}
		for (int i = 0; i < list.size(); i++) {
			LogRecordExtend log = new LogRecordExtend();
			log.setId(Integer.parseInt(((Object[]) list.get(i))[0].toString()));
			log.setUserId(Integer.parseInt(((Object[]) list.get(i))[1].toString()));
			log.setConnectId(Integer.parseInt(((Object[]) list.get(i))[2].toString()));
			log.setDatebaseName(((Object[]) list.get(i))[3].toString());
			log.setTableName(((Object[]) list.get(i))[4].toString());
			log.setOperationType(Boolean.parseBoolean(((Object[]) list.get(i))[5].toString()));
			log.setOperationTime(Timestamp.valueOf(((Object[]) list.get(i))[6].toString()));
			log.setNickname(((Object[]) list.get(i))[7].toString());
			log.setRemarks(list.get(i)[8] == null ? "" : ((Object[]) list.get(i))[8].toString());
			log.setConnectIp(((Object[]) list.get(i))[9].toString());
			logs.add(log);
		}
		return logs;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean add(LogRecord log) {
		this.getHibernateTemplate().save(log);
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getCount(Date begin,Date end,String name) {
		
		String sql = "select l.id,l.userId,l.connectId,l.datebaseName,l.tableName,l.operationType,l.operationTime,u.userName,l.remarks,c.connectIp  from log_record as l ,users as u,connect as c where l.connectId=c.connectId and l.userId=u.userId and  unix_timestamp( l.operationTime ) between unix_timestamp( '"+begin+"') and unix_timestamp( '"+end+" 23:59:59' ) and u.nickname like '%"
				+ name + "%'";
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
	public void update(LogRecord log) {
		LogRecord logRecord = (LogRecord) (this.getHibernateTemplate().find(" from LogRecord  l where l.id=? ", log.getId()).get(0));
		logRecord.setRemarks("1");
		this.getHibernateTemplate().saveOrUpdate(logRecord);

	}

}
