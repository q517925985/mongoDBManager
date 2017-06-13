/**
 * Project:mongoDBManager
 * File:Progress.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.entity;

import java.text.DecimalFormat;

/**
 * @author shihaojie
 * @date 2015年4月30日
 * @version $Id$
 * 
 */

@SuppressWarnings("unused")
public class Progress {
	private String name;//进度名字
	private int size;//总大小
	private	int progres;//当前进度
	private int rest;//剩余
	private int progresPercentage;//加载百分比
	private int restPercentage;//剩余百分比
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getProgres() {
		return progres;
	}
	public void setProgres(int progres) {
		this.progres = progres;
	}
	public int getRest() {
		return size-progres;
	}
	public void setRest(int rest) {
		this.rest = rest;
	}
	
	 
	public int getProgresPercentage() {
		return progres*100/size;
	}
	public void setProgresPercentage(int progresPercentage) {
		this.progresPercentage = progresPercentage;
	}
	public int getRestPercentage() {
		return 100-this.getProgresPercentage();
	}
	public void setRestPercentage(int restPercentage) {
		this.restPercentage = restPercentage;
	}
	
	
}
