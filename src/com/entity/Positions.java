package com.entity;

// Generated 2015-5-7 16:11:59 by Hibernate Tools 4.0.0

/**
 * Positions generated by hbm2java
 */
public class Positions implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -100439815758311470L;
	private Integer id;
	private String posiName;

	public Positions() {
	}

	public Positions(String posiName) {
		this.posiName = posiName;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPosiName() {
		return this.posiName;
	}

	public void setPosiName(String posiName) {
		this.posiName = posiName;
	}

}