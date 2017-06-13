/**
 * Project:mongoDBManager
 * File:TaskActioin.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.entity.Connect;
import com.entity.LogRecord;
import com.entity.MongoDatabase;
import com.entity.MongoTable;
import com.entity.Progress;
import com.entity.SourceTable;
import com.entity.Task;
import com.entity.TaskExtend;
import com.entity.Users;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.service.ConnectService;
import com.service.LogRecordService;
import com.service.MongoService;
import com.service.TaskService;

/**
 * @author shihaojie
 * @date 2015年5月20日
 * @version $Id$
 */
public class TaskAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2936405318632658896L;

	private TaskService taskServiceImpl;
	private ConnectService connectServiceImpl;
	private MongoService mongoServiceImpl;
	private LogRecordService logRecordServiceImpl;

	private int page;// 当前页
	private int count;// 总条数
	private int countPage;// 总页数
	private List<TaskExtend> list;// 日志列表
	private Task search;// 搜索条件
	private TaskExtend execute;// 执行任务
	private Integer taskId;// 任务id
	private Integer deleteId;//根据这个id删除

	//任务列表
	public String list() {
		page = page <= 0 ? 1 : page;
		if (search == null) {
			search = new Task();
			search.setRemarks("");
		} else if (search.getRemarks() == null) {
			search.setRemarks("");
		}
		list = this.taskServiceImpl.getList(search, page);

		this.count = this.taskServiceImpl.getCount(search, countPage);// 设置总条数
		this.countPage = count % 10 == 0 ? count / 10 : count / 10 + 1;// 设置总页数
		return "list";
	}

	// 获取任务详情
	public String getTaskJson() {

		execute = this.taskServiceImpl.getTask(taskId);
		return "json";
	}

	// 执行任务
	public String executeTask() {
		//根据ID获取任务
		execute = this.taskServiceImpl.getTask(taskId);
		// 获取源对象
		Connect sourceConnect = this.connectServiceImpl.getConnectById(execute.getSourceConnectId() + "");
		MongoDatabase sourceMdb = new MongoDatabase();
		sourceMdb.setName(execute.getSourceDatebaseName());
		sourceMdb.setConnect(sourceConnect);
		// 获取接收对象
		Connect receivedConnect = this.connectServiceImpl.getConnectById(execute.getSourceConnectId() + "");
		MongoDatabase receivedMdb = new MongoDatabase();
		receivedMdb.setName(execute.getReceivedDatebaseName());
		receivedMdb.setConnect(receivedConnect);
		//判断是否同步整个数据库
		if (execute.isImportIsDb()) {
			importDateBase(sourceMdb, receivedMdb, execute.isImportType());//同步整个数据库
		} else {
			importTable(sourceMdb, receivedMdb, execute.getTableList(), execute.isImportType());//同步多张表
		}

		return "redirect_list";
	}
	
	//删除任务
	public String deleteTask(){
		
		this.taskServiceImpl.delete(deleteId);
		return "redirect_list";
	}

	/** 自定义方法 */

	// 同步多张表
	public void importTable(MongoDatabase sourceMdb, MongoDatabase receivedMdb, List<SourceTable> sourceTables, boolean importType) {

		for (int i = 0; i < sourceTables.size(); i++) {

			MongoTable sourceMtb = new MongoTable();
			sourceMtb.setName(sourceTables.get(i).getSourceTableName());
			sourceMtb.setMongoDatabase(sourceMdb);
			String[] url = mongoServiceImpl.export(sourceMtb, new Progress());

			MongoTable receivedMtb = new MongoTable();
			receivedMtb.setName(sourceTables.get(i).getSourceTableName());
			receivedMtb.setMongoDatabase(receivedMdb);
			String backupUrl[] = this.mongoServiceImpl.importTb(receivedMtb, url, importType, new Progress());

			String rootUrl = newRoot();// 创建文件夹

			inRoot(rootUrl, backupUrl, sourceTables.get(i).getSourceTableName());// 将备份文件改名并放到新创建文件夹
			if (backupUrl[0].equals("") && backupUrl[1].equals("")) {
				rootUrl = "";
			}
			addLog(receivedMtb, rootUrl);// 添加日志记录

		}

	}

	// 同步整个数据库
	public void importDateBase(MongoDatabase sourceMdb, MongoDatabase receivedMdb, boolean importType) {
		List<MongoTable> sourceTables = this.mongoServiceImpl.getTbList(sourceMdb);
		String rootUrl = newRoot();// 创建文件夹
		for (int i = 0; i < sourceTables.size(); i++) {
			MongoTable sourceMtb = sourceTables.get(i);
			sourceMtb.setMongoDatabase(sourceMdb);
			String[] url = mongoServiceImpl.export(sourceMtb, new Progress());

			MongoTable receivedMtb = sourceTables.get(i);
			receivedMtb.setMongoDatabase(receivedMdb);
			String backupUrl[] = this.mongoServiceImpl.importTb(receivedMtb, url, importType, new Progress());

			inRoot(rootUrl, backupUrl, sourceTables.get(i).getName());// 将备份文件改名并放到新创建文件夹
			if (backupUrl[0].equals("") && backupUrl[1].equals("")) {
				rootUrl = "";
			}
		}
		MongoTable mtb = new MongoTable();
		mtb.setMongoDatabase(receivedMdb);
		mtb.setName("");
		addLog(mtb, rootUrl);// 添加日志记录
	}
	
	// 创建文件夹
	public String newRoot() {
		// 时间戳
		String formatDate = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		// 文件夹名字
		String remarks = this.mongoServiceImpl.getMongoDaoImpl().getProp().getProperty("backupPath") + formatDate;
		// 创建文件夹
		File rootDirFile = new File(remarks);
		if (!rootDirFile.exists()) {
			rootDirFile.mkdir();
		}
		return remarks;
	}

	// 将json和bson复制到指定文件夹下面
	public void inRoot(String rootUrl, String url[], String tbName) {
		File file1 = new File(url[0]);
		if (file1.exists()) {
			file1.renameTo(new File(rootUrl + "/" + tbName + ".bson"));
		}
		File file2 = new File(url[1]);
		if (file2.exists()) {
			file2.renameTo(new File(rootUrl + "/" + tbName + ".metadata.json"));
		}
	}

	// 添加日志记录
	public void addLog(MongoTable mtb, String backupRroot) {
		LogRecord log = new LogRecord();
		log.setUserId(((Users) ActionContext.getContext().getSession().get("user")).getUserId());
		log.setConnectId(mtb.getMongoDatabase().getConnect().getConnectId());
		log.setDatebaseName(mtb.getMongoDatabase().getName());
		log.setTableName(mtb.getName());
		log.setOperationType(false);
		log.setOperationTime(new Date());
		log.setRemarks(backupRroot);
		this.logRecordServiceImpl.add(log);
	}

	/** get set */
	public TaskService getTaskServiceImpl() {
		return taskServiceImpl;
	}

	public void setTaskServiceImpl(TaskService taskServiceImpl) {
		this.taskServiceImpl = taskServiceImpl;
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

	public List<TaskExtend> getList() {
		return list;
	}

	public void setList(List<TaskExtend> list) {
		this.list = list;
	}

	public Task getSearch() {
		return search;
	}

	public void setSearch(Task search) {
		this.search = search;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public TaskExtend getExecute() {
		return execute;
	}

	public void setExecute(TaskExtend execute) {
		this.execute = execute;
	}

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

	public LogRecordService getLogRecordServiceImpl() {
		return logRecordServiceImpl;
	}

	public void setLogRecordServiceImpl(LogRecordService logRecordServiceImpl) {
		this.logRecordServiceImpl = logRecordServiceImpl;
	}

	public Integer getDeleteId() {
		return deleteId;
	}

	public void setDeleteId(Integer deleteId) {
		this.deleteId = deleteId;
	}

}
