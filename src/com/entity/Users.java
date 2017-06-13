package com.entity;

// Generated 2015-5-7 16:11:59 by Hibernate Tools 4.0.0

/**
 * Users generated by hbm2java
 */
public class Users implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5875219998688640515L;
	private Integer userId;
	private String userName;
	private String password;
	private String nickname;
	private boolean gender;
	private int age;
	private int depId;
	private int posiId;
	private String phone;
	private String email;

	public Users() {
	}

	public Users(String userName, String password, String nickname, boolean gender, int age, int depId, int posiId, String phone, String email) {
		this.userName = userName;
		this.password = password;
		this.nickname = nickname;
		this.gender = gender;
		this.age = age;
		this.depId = depId;
		this.posiId = posiId;
		this.phone = phone;
		this.email = email;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public boolean isGender() {
		return this.gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		if(age==null){
			this.age = 0;
		}else{
			this.age = age;
		}
	}

	public int getDepId() {
		return this.depId;
	}

	public void setDepId(int depId) {
		this.depId = depId;
	}

	public int getPosiId() {
		return this.posiId;
	}

	public void setPosiId(int posiId) {
		this.posiId = posiId;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}