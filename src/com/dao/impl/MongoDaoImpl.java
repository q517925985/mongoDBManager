/**
 * Project:mongoDBManager
 * File:MongoDaoImpl.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.dao.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.bson.BSONDecoder;
import org.bson.BSONEncoder;
import org.bson.BSONObject;
import org.bson.BasicBSONDecoder;
import org.bson.BasicBSONEncoder;

import com.dao.MongoDao;
import com.entity.Connect;
import com.entity.MongoDatabase;
import com.entity.MongoTable;
import com.entity.Progress;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

/**
 * @author shihaojie
 * @date 2015年4月20日
 * @version $Id$
 */
public class MongoDaoImpl implements MongoDao {

	private Properties prop = new Properties();

	/**
	 * {@inheritDoc}
	 */
	public List<MongoDatabase> getList(Connect connect) {
		List<MongoDatabase> list = null;
		try {
			MongoClient mongo = new MongoClient(connect.getConnectIp(), connect.getConnectPort());
			List<String> dbNames = mongo.getDatabaseNames();
			list = new ArrayList<MongoDatabase>();
			for (int i = 0; i < dbNames.size(); i++) {
				MongoDatabase m = new MongoDatabase();
				DB db = mongo.getDB(dbNames.get(i));
				m.setName(db.getName());
				list.add(m);

			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}

		Collections.sort(list);
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<MongoTable> getTbList(MongoDatabase mongoDatabase) {
		List<MongoTable> list = new ArrayList<MongoTable>();
		try {
			MongoClient mongo = new MongoClient(mongoDatabase.getConnect().getConnectIp(), mongoDatabase.getConnect().getConnectPort());
			DB db = mongo.getDB(mongoDatabase.getName());
			Iterator<String> tbName = db.getCollectionNames().iterator();
			while (tbName.hasNext()) {

				MongoTable m = new MongoTable();
				m.setMongoDatabase(mongoDatabase);
				DBCollection dbCollection = db.getCollection(tbName.next());
				m.setName(dbCollection.getName());
				m.setSize((int) dbCollection.getCount());
				if (m.getName().equals("system.indexes")) {
					continue;
				}
				list.add(m);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
		Collections.sort(list);
		return list;
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	public String[] export(MongoTable tb, Progress progress) {
		UUID uuid = UUID.randomUUID();// 生成唯一文件名
		String url[] = { this.getProp().getProperty("exportPath") + uuid + ".bson", this.getProp().getProperty("exportPath") + uuid + ".metadata.json" };
		try {
			MongoClient mongo = new MongoClient(tb.getMongoDatabase().getConnect().getConnectIp(), tb.getMongoDatabase().getConnect().getConnectPort());
			DB db = mongo.getDB(tb.getMongoDatabase().getName());
			DBCollection col = db.getCollection(tb.getName());
			progress.setName(tb.getName());
			progress.setSize((int) col.getCount());
			DBCursor cursor = col.find();
			BSONEncoder encoder = new BasicBSONEncoder();
			int count = 0;
			progress.setProgres(0);
			while (cursor.hasNext()) {
				Files.write(Paths.get(url[0]), encoder.encode(cursor.next()), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
				count++;
				progress.setProgres(count);
			}
			List<DBObject> list = col.getIndexInfo();
			Files.write(Paths.get(url[1]), list.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return url;

	}

	/**
	 * {@inheritDoc}
	 */
	public String[] importTb(MongoTable tb, String url[], boolean importType, Progress progress) {

		UUID uuid = UUID.randomUUID();
		String backupUrl[] = { "", "" };
		Connect connect = tb.getMongoDatabase().getConnect();
		try {
			MongoClient mongo = new MongoClient(connect.getConnectIp(), connect.getConnectPort());
			DB db = mongo.getDB(tb.getMongoDatabase().getName());
			DBCollection dbCollection = db.getCollection(tb.getName());// 获取准备导入的集合
			if ((!url[0].equals("")) && dbCollection.getCount() > 0) {// 如果集合中有数据就备份

				backupUrl[0] = this.getProp().getProperty("backupPath") + uuid + ".bson";
				DBCursor cursor = dbCollection.find();
				BSONEncoder encoder = new BasicBSONEncoder();
				while (cursor.hasNext()) {
					Files.write(Paths.get(backupUrl[0]), encoder.encode(cursor.next()), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
				}
				if (importType) {// 如果覆蓋就刪除表中数据
					dbCollection.remove(new BasicDBObject());
					dbCollection = db.getCollection(tb.getName());// 获取准备导入的集合;
				}
			}
			List<DBObject> indexs = dbCollection.getIndexInfo();
			if ((!url[1].equals("")) && indexs.size() > 0) {// 如果集合中有索引就备份
				backupUrl[1] = this.getProp().getProperty("backupPath") + uuid + ".metadata.json";
				Files.write(Paths.get(backupUrl[1]), indexs.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
				if (importType) {// 如果覆蓋就刪除表中数据
					dbCollection.dropIndexes();
					dbCollection = db.getCollection(tb.getName());// 获取准备导入的集合;
				}
			}

			// 导入数据
			if (!url[0].equals("")) {
				File file = new File(url[0]);
				if (file.exists()) {// 判断文件是否存在
					InputStream in = new BufferedInputStream(new FileInputStream(url[0]));
					BSONDecoder decoder = new BasicBSONDecoder();
					while (in.available() > 0) {
						BSONObject obj = decoder.readObject(in);
						dbCollection.save((DBObject) JSON.parse(obj.toString()));
					}
				}
			}
			// 导入索引
			if (!url[1].equals("")) {
				File file = new File(url[1]);
				if (file.exists()) {// 判断文件是否存在
					@SuppressWarnings("resource")
					InputStream inIndex = new BufferedInputStream(new FileInputStream(url[1]));//
					byte[] buffer = new byte[1024 * 10];
					StringBuffer sb = new StringBuffer();
					while (inIndex.read(buffer) != -1) {
						sb.append(new String(buffer, "utf-8"));
					}
					@SuppressWarnings("unchecked")
					List<DBObject> list = (List<DBObject>) JSON.parse(sb.toString());
					for (int i = 0; i < list.size(); i++) {
						Map<String, Object> map = new HashMap<String, Object>();
						DBObject dbo = (DBObject) list.get(i).get("key");
						for (String k : dbo.keySet()) {
							map.put(k, dbo.get(k));
						}
						if (dbo.get("unique") != null && dbo.get("unique").equals("true")) {
							dbCollection.createIndex(new BasicDBObject(map), list.get(i).get("name").toString(), true);
						} else {
							dbCollection.createIndex(new BasicDBObject(map), list.get(i).get("name").toString());
						}
					}

				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
		return backupUrl;
	}

	// 获取properties
	public Properties getProp() {
		try {
			// System.out.println(this.getClass().getResource("/").toString().substring(5));
			this.prop.load(new FileInputStream(this.getClass().getResource("/").toString().substring(5) + "filepage.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	/**
	 * {@inheritDoc}
	 */
	public String CopyMongoDB(MongoTable tbA, MongoDatabase dbB, boolean importType) {

		return "";
	}

}
