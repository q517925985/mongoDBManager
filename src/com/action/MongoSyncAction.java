/**
 * Project:mongoDBManager
 * File:mongoSyncAction.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.entity.Connect;
import com.entity.LogRecord;
import com.entity.MongoDatabase;
import com.entity.MongoTable;
import com.entity.Progress;
import com.entity.SourceTable;
import com.entity.Task;
import com.entity.Users;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.service.ConnectService;
import com.service.LogRecordService;
import com.service.MongoService;
import com.service.TaskService;

/**
 * @author shihaojie
 * @date 2015年4月27日
 * @version $Id$
 */
public class MongoSyncAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3204236182652964201L;

	private ConnectService connectServiceImpl;
	private MongoService mongoServiceImpl;
	private LogRecordService logRecordServiceImpl;
	private TaskService taskServiceImpl;

	private List<Connect> connects;// 连接列表
	private Connect connectA;// 连接信息A
	private List<MongoDatabase> dbListA;// 数据库列表A
	private MongoDatabase nowDBA;// 当前的数据库A信息
	private List<MongoTable> tbListA;// 集合列表A
	private List<String> tbName;// 选中的集合名称
	private Connect connectB;// 连接信息B
	private List<MongoDatabase> dbListB;// 数据库列表B
	private MongoDatabase nowDBB;// 当前的数据库B信息
	private List<MongoTable> tbListB;// 集合列表B
	private boolean importType;// 导入类型true覆盖，false添加

	private String taskName;// 任务

	// 连接列表
	public String connectList() {
		connects = this.connectServiceImpl.getAll();
		return "list";
	}

	// 数据库列表A
	public String dbListA() {
		if (connectA.getConnectId() != 0) {// 如果id是0,不查询数据库列表
			connectA = this.connectServiceImpl.getConnectById(connectA.getConnectId() + "");
			dbListA = this.mongoServiceImpl.getList(connectA);
		} else {
			dbListA = new ArrayList<MongoDatabase>();
		}

		return "dbListAJson";
	}

	// 集合列表A
	public String tbListA() {
		if (!nowDBA.getName().equals("0")) {// 如果id是0,不查询集合列表
			connectA = this.connectServiceImpl.getConnectById(connectA.getConnectId() + "");
			nowDBA.setConnect(connectA);
			tbListA = this.mongoServiceImpl.getTbList(nowDBA);
		} else {
			tbListA = new ArrayList<MongoTable>();
		}

		return "tbListAJson";
	}

	// 数据库列表B
	public String dbListB() {
		if (connectB.getConnectId() != 0) {// 如果id是0,不查询数据库列表
			connectB = this.connectServiceImpl.getConnectById(connectB.getConnectId() + "");
			dbListB = this.mongoServiceImpl.getList(connectB);
		} else {
			connectB = new Connect();
			dbListB = new ArrayList<MongoDatabase>();
		}

		return "dbListBJson";
	}

	// 集合列表B
	public String tbListB() {
		if (!nowDBB.getName().equals("0")) {// 如果id是0,不查询集合列表
			connectB = this.connectServiceImpl.getConnectById(connectB.getConnectId() + "");
			nowDBB.setConnect(connectB);
			tbListB = this.mongoServiceImpl.getTbList(nowDBB);
		} else {
			tbListB = new ArrayList<MongoTable>();
		}

		return "tbListBJson";
	}

	// 同步一张或多张表
	public String syncTb() {
		List<SourceTable> sourceTables = new ArrayList<SourceTable>();
		for (int i = 0; i < tbName.size(); i++) {
			// 1.将数据库A的集合导出到服务器，获取文件url
			connectA = connectServiceImpl.getConnectById(connectA.getConnectId() + "");
			nowDBA.setConnect(connectA);
			MongoTable mtb = new MongoTable();
			mtb.setMongoDatabase(nowDBA);
			mtb.setName(tbName.get(i));
			String[] url = mongoServiceImpl.export(mtb, new Progress());
			// String bsontUrl = mongoServiceImpl.export(mtb,new Progress());
			// String jsonUrl = mongoServiceImpl.exportIndexes(mtb);
			// 2.将数据库A的集合文件导入到数据库B
			connectB = connectServiceImpl.getConnectById(connectB.getConnectId() + "");
			nowDBB.setConnect(connectB);
			mtb.setMongoDatabase(nowDBB);
			String backupUrl[] = this.mongoServiceImpl.importTb(mtb, url, importType, new Progress());
			// String backupJsonUrl = this.mongoServiceImpl.importTbIndexes(mtb,jsonUrl, importType);

			// 将集合数据和集合索引的备份文件放到备份目录下
			// 1.在备份目录下创建一个唯一的文件夹
			String formatDate = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());// 获取时间戳
			String backupRroot = this.mongoServiceImpl.getMongoDaoImpl().getProp().getProperty("backupPath") + formatDate + "/";
			File backupRrootDirFile = new File(backupRroot);
			if (!backupRrootDirFile.exists()) {
				backupRrootDirFile.mkdir();
			}
			// 2.把集合数据和集合索引文件放到新创建的文件夹中。
			File file = new File(backupUrl[0]);
			if (file.exists()) {
				file.renameTo(new File(backupRroot + "/" + mtb.getName() + ".bson"));
				file = new File(backupRroot + "/" + mtb.getName() + ".bson");
			}
			file = new File(backupUrl[1]);
			if (file.exists()) {
				file.renameTo(new File(backupRroot + "/" + mtb.getName() + ".metadata.json"));
				file = new File(backupRroot + "/" + mtb.getName() + ".metadata.json");
			}
			if (backupUrl[0].equals("") && backupUrl[1].equals("")) {
				backupRroot = "";
			}
			// 3. 添加日志记录
			LogRecord log = new LogRecord();
			log.setUserId(((Users) ActionContext.getContext().getSession().get("user")).getUserId());
			log.setConnectId(nowDBB.getConnect().getConnectId());
			log.setDatebaseName(nowDBB.getName());
			log.setTableName(mtb.getName());
			log.setOperationType(false);
			log.setOperationTime(new Date());
			log.setRemarks(backupRroot);
			this.logRecordServiceImpl.add(log);

			SourceTable sourceTable = new SourceTable();
			sourceTable.setSourceTableName(tbName.get(i));
			sourceTables.add(sourceTable);
		}
		// 添加任务
		if (!(taskName == null || taskName.equals(""))) {
			Task task = new Task();
			task.setRemarks(taskName);
			task.setSourceConnectId(connectA.getConnectId());
			task.setSourceDatebaseName(nowDBA.getName());
			task.setReceivedConnectId(connectB.getConnectId());
			task.setReceivedDatebaseName(nowDBB.getName());
			task.setImportType(importType);
			task.setImportIsDb(false);
			taskServiceImpl.add(task, sourceTables);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out;
		try {
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			out.print("<script>alert('数据同步成功！ ');location.href='mongoSync_connectList.action'</script>");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect_list";

	}

	// 同步整个库
	public String syncDb() {

		// 时间戳
		String formatDate = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		// 备份数据库文件夹
		String remarks = this.getMongoServiceImpl().getMongoDaoImpl().getProp().getProperty("backupPath") + formatDate;
		// 创建文件夹
		File rootDirFile = new File(remarks);
		if (!rootDirFile.exists()) {
			rootDirFile.mkdir();
		}
		boolean isCoverage = false;// 是否有覆盖的
		for (int i = 0; i < tbName.size(); i++) {
			// 1.将数据库A的集合导出到服务器，获取文件url
			connectA = connectServiceImpl.getConnectById(connectA.getConnectId() + "");
			nowDBA.setConnect(connectA);
			MongoTable mtb = new MongoTable();
			mtb.setMongoDatabase(nowDBA);
			mtb.setName(tbName.get(i));

			String[] url = mongoServiceImpl.export(mtb, new Progress());
			// 2.将数据库A的集合文件导入到数据库B
			connectB = connectServiceImpl.getConnectById(connectB.getConnectId() + "");
			nowDBB.setConnect(connectB);
			mtb.setMongoDatabase(nowDBB);
			
			String backupUrl[] = this.mongoServiceImpl.importTb(mtb, url, importType, new Progress());
			// String backupJsonUrl = this.mongoServiceImpl.importTbIndexes(mtb, exportJsonUrl, importType);
			if (!backupUrl[0].equals("")) {// 返回“”说明没有重名集合。不用备份
				isCoverage = true;
				File file = new File(backupUrl[0]);
				file.renameTo(new File(remarks + "/" + mtb.getName() + ".bson"));
			}
			if (!backupUrl[1].equals("")) {// 返回“”说明没有重名集合。不用备份
				isCoverage = true;
				File file = new File(backupUrl[1]);
				file.renameTo(new File(remarks + "/" + mtb.getName() + ".metadata.json"));
			}

		}
		remarks = isCoverage ? remarks : "";

		// 3. 添加日志记录
		LogRecord log = new LogRecord();
		log.setUserId(((Users) ActionContext.getContext().getSession().get("user")).getUserId());
		log.setConnectId(nowDBB.getConnect().getConnectId());
		log.setDatebaseName(nowDBB.getName());
		log.setTableName("");
		log.setOperationType(false);
		log.setOperationTime(new Date());
		log.setRemarks(remarks);
		this.logRecordServiceImpl.add(log);

		// 添加任务
		if (!(taskName == null || taskName.equals(""))) {
			Task task = new Task();
			task.setRemarks(taskName);
			task.setSourceConnectId(connectA.getConnectId());
			task.setSourceDatebaseName(nowDBA.getName());
			task.setReceivedConnectId(connectB.getConnectId());
			task.setReceivedDatebaseName(nowDBB.getName());
			task.setImportType(importType);
			task.setImportIsDb(true);
			taskServiceImpl.add(task, null);
		}

		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out;

		try {
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			out.print("<script>alert('数据同步成功！ ');location.href='mongoSync_connectList.action'</script>");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect_list";
	}

	/******* get set *********/
	public ConnectService getConnectServiceImpl() {
		return connectServiceImpl;
	}

	public void setConnectServiceImpl(ConnectService connectServiceImpl) {
		this.connectServiceImpl = connectServiceImpl;
	}

	public MongoService getMongoServiceImpl() {
		return mongoServiceImpl;
	}

	public void setMongoServiceImpl(MongoService mongoServiceImpl) {
		this.mongoServiceImpl = mongoServiceImpl;
	}

	public List<Connect> getConnects() {
		return connects;
	}

	public void setConnects(List<Connect> connects) {
		this.connects = connects;
	}

	public LogRecordService getLogRecordServiceImpl() {
		return logRecordServiceImpl;
	}

	public void setLogRecordServiceImpl(LogRecordService logRecordServiceImpl) {
		this.logRecordServiceImpl = logRecordServiceImpl;
	}

	public Connect getConnectA() {
		return connectA;
	}

	public void setConnectA(Connect connectA) {
		this.connectA = connectA;
	}

	public List<MongoDatabase> getDbListA() {
		return dbListA;
	}

	public void setDbListA(List<MongoDatabase> dbListA) {
		this.dbListA = dbListA;
	}

	public Connect getConnectB() {
		return connectB;
	}

	public void setConnectB(Connect connectB) {
		this.connectB = connectB;
	}

	public List<MongoDatabase> getDbListB() {
		return dbListB;
	}

	public void setDbListB(List<MongoDatabase> dbListB) {
		this.dbListB = dbListB;
	}

	public MongoDatabase getNowDBA() {
		return nowDBA;
	}

	public void setNowDBA(MongoDatabase nowDBA) {
		this.nowDBA = nowDBA;
	}

	public MongoDatabase getNowDBB() {
		return nowDBB;
	}

	public void setNowDBB(MongoDatabase nowDBB) {
		this.nowDBB = nowDBB;
	}

	public List<MongoTable> getTbListA() {
		return tbListA;
	}

	public void setTbListA(List<MongoTable> tbListA) {
		this.tbListA = tbListA;
	}

	public List<MongoTable> getTbListB() {
		return tbListB;
	}

	public void setTbListB(List<MongoTable> tbListB) {
		this.tbListB = tbListB;
	}

	public List<String> getTbName() {
		return tbName;
	}

	public void setTbName(List<String> tbName) {
		this.tbName = tbName;
	}

	public boolean isImportType() {
		return importType;
	}

	public void setImportType(boolean importType) {
		this.importType = importType;
	}

	public TaskService getTaskServiceImpl() {
		return taskServiceImpl;
	}

	public void setTaskServiceImpl(TaskService taskServiceImpl) {
		this.taskServiceImpl = taskServiceImpl;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
}
