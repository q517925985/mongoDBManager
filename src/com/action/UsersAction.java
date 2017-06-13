/**
 * Project:mongoDBManager
 * File:UsersAction.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.entity.Departments;
import com.entity.Positions;
import com.entity.UserExtend;
import com.entity.Users;
import com.opensymphony.xwork2.ActionSupport;
import com.service.DepartmentsService;
import com.service.PositionsService;
import com.service.UsersService;

/**
 * @author shihaojie
 * @date 2015年4月14日
 * @version $Id$
 */
public class UsersAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7303125929627648724L;

	private UsersService usersServiceImpl;// 用户服务层
	private DepartmentsService departmentsServiceImpl;// 部门服务层
	private PositionsService positionsServiceImpl;// 职位服务层
	private int num;
	private Users user;// 存放登录
	private List<UserExtend> list;// 存放用户列表
	private Users tiaojian;// ，查询条件
	private int page;// 当前页
	private int count;// 总条数
	private int countPage;// 总页数
	private List<Departments> depList;// 部门列表
	private List<Positions> posiList;// 职位列表
	private Users addUser;// 存放添加用户信息
	private String deleteUsersId;// 存放删除用户信息id
	private Users updateUser;// 存放更新个人资料的用户信息
	private Users listUpdateUser;// 存放更新用户列表页面用户信息
	private String message;// 检查用户是否存在。返回结果
	
	public boolean userIsValidty;
	
	public String index(){
		return "index";
	}
	
	//验证用户名是否存在
	public String validityUserName(){
		Users u = this.usersServiceImpl.getUserByName(user);
		if(u != null ){
			userIsValidty=true;
		}else{
			userIsValidty=false;
		}
		return "userIsValidty";
	}
	// 登录
	@SuppressWarnings("finally")
	public String login() {
		Users u = this.usersServiceImpl.getUserByName(user);
		if (u != null && u.getPassword().equals(user.getPassword())) {
			ServletActionContext.getRequest().getSession().setAttribute("user", u);
			return "success";
		} else {
			HttpServletResponse response = ServletActionContext.getResponse();
			PrintWriter out;
			try {
				response.setContentType("text/html;charset=utf-8");
				out = response.getWriter();
				out.print("<script>alert('帐号或密码不正确！ ');location.href='login.html'</script>");
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				return "toLogin";
			}
		}
	}

	// 退出系统
	public String exit() {
		ServletActionContext.getRequest().getSession().setAttribute("user", null);
		
		return "toLogin";
	}

	// 显示列表
	public String list() {
		page = page <= 0 ? 1 : page;
		if (tiaojian == null) {
			tiaojian = new Users();
			tiaojian.setDepId(0);
			tiaojian.setUserName("");
		}
		depList = this.departmentsServiceImpl.getAll();// 获取部门列表
		posiList = this.positionsServiceImpl.getAll();// 获取职位列表
		list = this.usersServiceImpl.getList(tiaojian, page);// 获取用户列表
		count = this.usersServiceImpl.getCount(tiaojian);// 用户总条数
		countPage = count % 8 == 0 ? count / 8 : count / 8 + 1;// 用户总页数
		return "list";
	}

	// 添加用户
	public String addUser() {
		this.usersServiceImpl.addUser(addUser);
		return "redirect_list";
	}

	// 删除用户
	public String deleteUser() {
		String id[] = deleteUsersId.split(",");

		for (int i = 0; i < id.length; i++) {
			Users u = new Users();
			u.setUserId(Integer.parseInt(id[i]));
			this.usersServiceImpl.deleteUser(u);
		}
		message = "删除成功！";
		return "redirect_list";
	}

	private String json;

	public String s() {
		json = "1234";
		return "json";
	}

	// 用户列表页面修改用户资料
	public String update() {

		this.usersServiceImpl.updateUser(listUpdateUser);
		return "redirect_list";
	}

	// 进入个人资料页面
	public String toUpdate() {
		updateUser=(Users)ServletActionContext.getRequest().getSession().getAttribute("user");
		updateUser = this.usersServiceImpl.getUserByName(updateUser);
		depList = this.departmentsServiceImpl.getAll();// 获取部门列表
		posiList = this.positionsServiceImpl.getAll();// 获取职位列表
		return "toUpdate";
	}

	// 修改个人资料页面
	@SuppressWarnings("finally")
	public String doUpdate() {
		this.usersServiceImpl.updateUser(updateUser);
		ServletActionContext.getRequest().getSession().setAttribute("user", updateUser);
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out;
		try {
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			out.print("<script>alert('修改成功！ ');location.href='users_toUpdate.action';</script>");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			return "redirect_toUpdate";
		}
	}

	/************ get set ************/
	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public UsersService getUsersServiceImpl() {
		return usersServiceImpl;
	}

	public void setUsersServiceImpl(UsersService usersServiceImpl) {
		this.usersServiceImpl = usersServiceImpl;
	}

	public List<UserExtend> getList() {
		return list;
	}

	public void setList(List<UserExtend> list) {
		this.list = list;
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

	public List<Departments> getDepList() {
		return depList;
	}

	public void setDepList(List<Departments> depList) {
		this.depList = depList;
	}

	public DepartmentsService getDepartmentsServiceImpl() {
		return departmentsServiceImpl;
	}

	public void setDepartmentsServiceImpl(DepartmentsService departmentsServiceImpl) {
		this.departmentsServiceImpl = departmentsServiceImpl;
	}

	public Users getTiaojian() {
		return tiaojian;
	}

	public void setTiaojian(Users tiaojian) {
		this.tiaojian = tiaojian;
	}

	public int getCountPage() {
		return countPage;
	}

	public void setCountPage(int countPage) {
		this.countPage = countPage;
	}

	public PositionsService getPositionsServiceImpl() {
		return positionsServiceImpl;
	}

	public void setPositionsServiceImpl(PositionsService positionsServiceImpl) {
		this.positionsServiceImpl = positionsServiceImpl;
	}

	public List<Positions> getPosiList() {
		return posiList;
	}

	public void setPosiList(List<Positions> posiList) {
		this.posiList = posiList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Users getAddUser() {
		return addUser;
	}

	public void setAddUser(Users addUser) {
		this.addUser = addUser;
	}

	public Users getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Users updateUser) {
		this.updateUser = updateUser;
	}

	public String getDeleteUsersId() {
		return deleteUsersId;
	}

	public void setDeleteUsersId(String deleteUsersId) {
		this.deleteUsersId = deleteUsersId;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public Users getListUpdateUser() {
		return listUpdateUser;
	}

	public void setListUpdateUser(Users listUpdateUser) {
		this.listUpdateUser = listUpdateUser;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
