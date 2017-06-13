/**
 * Project:mongoDBManager
 * File:MongoAction.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.entity.Connect;
import com.entity.ConnectType;
import com.entity.LogRecord;
import com.entity.MongoDatabase;
import com.entity.MongoTable;
import com.entity.Progress;
import com.entity.Users;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.service.ConnectService;
import com.service.ConnectTypeService;
import com.service.LogRecordService;
import com.service.MongoService;
import com.util.ZipFileUtil;
import com.util.ZipUtil;

/**
 * @author shihaojie
 * @date 2015年4月20日
 * @version $Id$
 */
public class MongoAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7329861972977151625L;

	private MongoService mongoServiceImpl;
	private ConnectService connectServiceImpl;
	private LogRecordService logRecordServiceImpl;
	private ConnectTypeService connectTypeServiceImpl;

	private int page;// 当前页
	private int count;// 总条数
	private int countPage;// 总页数
	private Connect connect;// 数据库连接对象
	private List<Connect> connects;// 数据库连接列表
	private List<ConnectType> connectTypes;// 数据库连接分类列表
	private List<MongoDatabase> dbList;// 数据库 集合
	private Connect connectJson;// 数据库连接json
	private String connectId;// 根据此ip查询数据库连接json
	private MongoDatabase nowDB;// 当前所选数据库
	private List<MongoTable> tbList;// 数据表 集合
	private String exportTbName;// 存储导出的集合
	private InputStream downloadFile;// 导出的文件
	private String fileName;// 导出的文件名字
	private MongoTable importTb;// 准备导入的表名
	private boolean importType;// 导入类型true覆盖，false添加

	// 连接列表
	public String connectList() {
		page = page <= 0 ? 1 : page;
		connectTypes = this.connectTypeServiceImpl.getAll();
		connects = this.connectServiceImpl.getAll();
		return "list";
	}

	// 数据库列表
	public String dbList() {

		tbList = null;
		count = 0;
		countPage = 0;
		if (connect.getConnectId() != null && connect.getConnectId() != 0) {// 如果id是0,不查询数据库列表
			connect = this.connectServiceImpl.getConnectById(connect.getConnectId() + "");
			dbList = this.mongoServiceImpl.getList(connect);
		} else {
			connect = new Connect();
			dbList = new ArrayList<MongoDatabase>();
		}
		// 清空页面显示信息

		return "dbListJson";
	}

	// 集合 列表
	public String tbList() {
		tbList = null;
		count = 0;
		countPage = 0;
		if (nowDB != null && !nowDB.getName().equals("0")) {// 如果id是0,不查询数据库列表
			connect = this.connectServiceImpl.getConnectById(connect.getConnectId() + "");
			nowDB.setConnect(connect);
			List<MongoTable> tbs = this.mongoServiceImpl.getTbList(nowDB);
			page = page <= 0 ? 1 : page;
			if (tbs == null) {
				return "redirect_list";
			}
			tbList = new ArrayList<MongoTable>();
			for (int i = (page - 1) * 20; i < tbs.size() && i < page * 20; i++) {// 获取职位列表
				tbList.add(tbs.get(i));
			}
			this.count = tbs.size();// 设置总条数
			this.countPage = count % 20 == 0 ? count / 20 : count / 20 + 1;// 设置总页数
		} else {
			nowDB = new MongoDatabase();
			page = 1;
		}
		return connectList();
	}

	// 将mongo集合导出成文件并下载
	public String export() {
		File[] files = new File[2];

		MongoTable exportTb = new MongoTable();
		connect = this.connectServiceImpl.getConnectById(connect.getConnectId() + "");
		nowDB.setConnect(connect);
		exportTb.setName(exportTbName);
		exportTb.setMongoDatabase(nowDB);

		Progress progress = new Progress();// 创建进度信息
		ServletActionContext.getRequest().getSession().setAttribute("progress", progress);// 把进度信息存到session
		String url[] = this.mongoServiceImpl.export(exportTb, progress);// 将数据库数据转成bson文件，返回地址
		//String urlIndexes = this.mongoServiceImpl.exportIndexes(exportTb);// 将数据库索引转成json文件，返回地址

		// 关闭进度信息
		ServletActionContext.getRequest().getSession().removeAttribute("progress");
		// 根据时间戳命名创建文件夹
		String formatDate = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String root = this.mongoServiceImpl.getMongoDaoImpl().getProp().getProperty("exportPath") + formatDate;
		File rootDirFile = new File(root);
		if (!rootDirFile.exists()) {
			rootDirFile.mkdir();
		}
		// 重命名json，bson文件并复制到创建的文件夹下面
		File bsonFile = new File(url[0]);
		if (bsonFile.exists()) {
			bsonFile.renameTo(new File(root + "/" + exportTb.getName() + ".bson"));
			bsonFile = new File(root + "/" + exportTb.getName() + ".bson");
			files[0] = bsonFile;
		}
		File jsonFile = new File(url[1]);
		if (jsonFile.exists()) {
			jsonFile.renameTo(new File(root + "/" + exportTb.getName() + ".metadata.json"));
			jsonFile = new File(root + "/" + exportTb.getName() + ".metadata.json");
			files[1] = jsonFile;
		}
		// 设置zip压缩包名字，并将json，bson添加到压缩文件
		zipFileName = this.mongoServiceImpl.getMongoDaoImpl().getProp().getProperty("exportPath") + formatDate + "/" + formatDate + ".zip";
		ZipFileUtil.compressFiles2Zip(files, zipFileName);
		try {
			downloadFiles = new FileInputStream(zipFileName);
			zipFileName = exportTb.getName() + ".zip";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// 添加日志
		LogRecord log = new LogRecord();
		log.setUserId(((Users) ActionContext.getContext().getSession().get("user")).getUserId());
		log.setConnectId(nowDB.getConnect().getConnectId());
		log.setDatebaseName(nowDB.getName());
		log.setTableName(exportTb.getName());
		log.setOperationType(true);
		log.setOperationTime(new Date());
		log.setRemarks("");
		this.logRecordServiceImpl.add(log);
		return "exports";
	}

	// 接收JSP页面传递过来集合名字
	private String attachmentPath;

	// 最终压缩后的zip文件的路径，传递给通用的下载Action
	private String zipFileName;
	// zip文件
	private InputStream downloadFiles;

	/**
	 * 下载多个附件 实现：将多个附近压缩成zip包,然后再下载zip包
	 */
	public String downloadMultiFile() {

		connect = this.connectServiceImpl.getConnectById(connect.getConnectId() + "");
		nowDB.setConnect(connect);
		// 使用当前时间生成文件名称
		String formatDate = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		// 压缩后的zip文件存放路径
		zipFileName = this.mongoServiceImpl.getMongoDaoImpl().getProp().getProperty("exportPath") + formatDate + "/" + formatDate + ".zip";

		if (attachmentPath != null && !"".equals(attachmentPath)) {
			// 将多个附件的路径取出
			String[] attachmentPathArray = attachmentPath.split(",");
			if (attachmentPathArray != null && attachmentPathArray.length > 0) {
				// 使用当前时间创建文件夹
				String root = this.mongoServiceImpl.getMongoDaoImpl().getProp().getProperty("exportPath") + formatDate;
				File rootDirFile = new File(root);
				if (!rootDirFile.exists()) {
					rootDirFile.mkdir();
				}
				File[] files = new File[attachmentPathArray.length * 2];
				Progress progress = new Progress();
				ServletActionContext.getRequest().getSession().setAttribute("progress", progress);
				for (int i = 0, j = 0; i < attachmentPathArray.length; i++, j += 2) {
					if (attachmentPathArray[i] != null) {
						MongoTable exportTb = new MongoTable();
						exportTb.setName(attachmentPathArray[i].trim());
						exportTb.setMongoDatabase(nowDB);

						String[] url = this.mongoServiceImpl.export(exportTb, progress);// 导出

						File bsonFile = new File(url[0]);
						bsonFile.renameTo(new File(root + "/" + exportTb.getName() + ".bson"));
						bsonFile = new File(root + "/" + exportTb.getName() + ".bson");
						if (bsonFile.exists()) {
							files[j] = bsonFile;
						}

						File jsonFile = new File(url[1]);
						jsonFile.renameTo(new File(root + "/" + exportTb.getName() + ".metadata.json"));
						jsonFile = new File(root + "/" + exportTb.getName() + ".metadata.json");
						if (jsonFile.exists()) {
							files[j + 1] = jsonFile;
						}
						// 添加日志记录
						LogRecord log = new LogRecord();
						log.setUserId(((Users) ActionContext.getContext().getSession().get("user")).getUserId());
						log.setConnectId(nowDB.getConnect().getConnectId());
						log.setDatebaseName(nowDB.getName());
						log.setTableName(exportTb.getName());
						log.setOperationType(true);
						log.setOperationTime(new Date());
						log.setRemarks("");
						this.logRecordServiceImpl.add(log);
					}
				}

				ServletActionContext.getRequest().getSession().removeAttribute("progress");
				// 将多个附件压缩成zip
				ZipFileUtil.compressFiles2Zip(files, zipFileName);
				try {
					downloadFiles = new FileInputStream(zipFileName);
					zipFileName = nowDB.getConnect().getConnectIp() + "_" + nowDB.getName() + "_" + formatDate + ".zip";
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					rootDirFile.delete();
				}
			}

		}
		return "exports";
	}

	// 接收将要导入的集合文件
	private File importFile;

	// 导入
	public String importTb() {
		connect = this.connectServiceImpl.getConnectById(connect.getConnectId() + "");// 获取连接对象
		nowDB.setConnect(connect);
		String formatDate = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());// 获取时间戳
		String url = this.mongoServiceImpl.getMongoDaoImpl().getProp().getProperty("exportPath") + formatDate + ".zip";// 保存上传文件的地址

		File saveFile = new File(url);// 创建一个文件。接收客户端上传的zip文件
		if (!saveFile.getParentFile().exists())
			saveFile.getParentFile().mkdirs();
		try {
			FileUtils.copyFile(importFile, saveFile);// 保存zip文件到服务器
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 用当前时间创建文件夹
		String root = this.mongoServiceImpl.getMongoDaoImpl().getProp().getProperty("exportPath") + formatDate + "/";
		File rootDirFile = new File(root);
		if (!rootDirFile.exists()) {
			rootDirFile.mkdir();
		}
		ZipUtil.unZip(url, root, false);// 将zip文件解压

		// 遍历解压后的文件,找出json和bson文件
		String exurl[] ={"",""};
		File rootFile = new File(root);
		for (String fileName : rootFile.list()) {
			if (fileName.substring(fileName.length() - 5).equals(".bson")) {
				exurl[0] = root + fileName;
			}
			if (fileName.substring(fileName.length() - 5).equals(".json")) {
				exurl[1] = root + fileName;
			}
		}
		importTb.setMongoDatabase(nowDB);// 设置集合所属数据库
		Progress progress = new Progress();
		ServletActionContext.getRequest().getSession().setAttribute("progress", progress);
		String backupUrl[] = this.mongoServiceImpl.importTb(importTb, exurl, importType, progress);// 导入集合数据
		//String backupJsonUrl = this.mongoServiceImpl.importTbIndexes(importTb, jsonUrl, importType);// 导入集合索引
		// 将集合数据和集合索引的备份文件放到备份目录下
		// 1.在备份目录下创建一个唯一的文件夹
		String backupRroot = this.mongoServiceImpl.getMongoDaoImpl().getProp().getProperty("backupPath") + formatDate + "/";
		File backupRrootDirFile = new File(backupRroot);
		if (!backupRrootDirFile.exists()) {
			backupRrootDirFile.mkdir();
		}
		// 2.把集合数据和集合索引文件放到新创建的文件夹中。
		File file = new File(backupUrl[0]);
		if (file.exists()) {
			file.renameTo(new File(backupRroot + "/" + importTb.getName() + ".bson"));
			file = new File(backupRroot + "/" + importTb.getName() + ".bson");
		}
		file = new File(backupUrl[1]);
		if (file.exists()) {
			file.renameTo(new File(backupRroot + "/" + importTb.getName() + ".metadata.json"));
			file = new File(backupRroot + "/" + importTb.getName() + ".metadata.json");
		}
		if(backupUrl[0].equals("")&&backupUrl[1].equals("")){
			backupRroot="";
		}
		// 添加日志记录
		LogRecord log = new LogRecord();
		log.setUserId(((Users) ActionContext.getContext().getSession().get("user")).getUserId());
		log.setConnectId(nowDB.getConnect().getConnectId());
		log.setDatebaseName(nowDB.getName());
		log.setTableName(importTb.getName());
		log.setOperationType(false);
		log.setOperationTime(new Date());
		log.setRemarks(backupRroot);
		this.logRecordServiceImpl.add(log);
		return tbList();
	}

	// 进度信息
	private Progress progress;

	// 获取导入导出进度
	public String getProgressJson() {
		progress = (Progress) ServletActionContext.getRequest().getSession().getAttribute("progress");

		return "progressJson";
	}

	/******** get set *********/

	public MongoService getMongoServiceImpl() {
		return mongoServiceImpl;
	}

	public void setMongoServiceImpl(MongoService mongoServiceImpl) {
		this.mongoServiceImpl = mongoServiceImpl;
	}

	public List<MongoDatabase> getDbList() {
		return dbList;
	}

	public void setDbList(List<MongoDatabase> dbList) {
		this.dbList = dbList;
	}

	public Connect getConnect() {
		return connect;
	}

	public void setConnect(Connect connect) {
		this.connect = connect;
	}

	public List<Connect> getConnects() {
		return connects;
	}

	public void setConnects(List<Connect> connects) {
		this.connects = connects;
	}

	public ConnectService getConnectServiceImpl() {
		return connectServiceImpl;
	}

	public void setConnectServiceImpl(ConnectService connectServiceImpl) {
		this.connectServiceImpl = connectServiceImpl;
	}

	public Connect getConnectJson() {
		return connectJson;
	}

	public void setConnectJson(Connect connectJson) {
		this.connectJson = connectJson;
	}

	public String getConnectId() {
		return connectId;
	}

	public void setConnectId(String connectId) {
		this.connectId = connectId;
	}

	public List<MongoTable> getTbList() {
		return tbList;
	}

	public void setTbList(List<MongoTable> tbList) {
		this.tbList = tbList;
	}

	public MongoDatabase getNowDB() {
		return nowDB;
	}

	public void setNowDB(MongoDatabase nowDB) {
		this.nowDB = nowDB;
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

	public InputStream getDownloadFile() {
		return downloadFile;
	}

	public void setDownloadFile(InputStream downloadFile) {
		this.downloadFile = downloadFile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getExportTbName() {
		return exportTbName;
	}

	public void setExportTbName(String exportTbName) {
		this.exportTbName = exportTbName;
	}

	public String getZipFileName() {
		return zipFileName;
	}

	public void setZipFileName(String zipFileName) {
		this.zipFileName = zipFileName;
	}

	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

	public InputStream getDownloadFiles() {
		return downloadFiles;
	}

	public void setDownloadFiles(InputStream downloadFiles) {
		this.downloadFiles = downloadFiles;
	}

	public File getImportFile() {
		return importFile;
	}

	public void setImportFile(File importFile) {
		this.importFile = importFile;
	}

	public MongoTable getImportTb() {
		return importTb;
	}

	public void setImportTb(MongoTable importTb) {
		this.importTb = importTb;
	}

	public LogRecordService getLogRecordServiceImpl() {
		return logRecordServiceImpl;
	}

	public void setLogRecordServiceImpl(LogRecordService logRecordServiceImpl) {
		this.logRecordServiceImpl = logRecordServiceImpl;
	}

	public boolean isImportType() {
		return importType;
	}

	public void setImportType(boolean importType) {
		this.importType = importType;
	}

	public ConnectTypeService getConnectTypeServiceImpl() {
		return connectTypeServiceImpl;
	}

	public void setConnectTypeServiceImpl(ConnectTypeService connectTypeServiceImpl) {
		this.connectTypeServiceImpl = connectTypeServiceImpl;
	}

	public List<ConnectType> getConnectTypes() {
		return connectTypes;
	}

	public void setConnectTypes(List<ConnectType> connectTypes) {
		this.connectTypes = connectTypes;
	}

	public Progress getProgress() {
		return progress;
	}

	public void setProgress(Progress progress) {
		this.progress = progress;
	}

}
