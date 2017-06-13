/**
 * Project:mongoDBManager
 * File:LoginInterceptor.java
 * Copyright 2004-2015 Homolo Co., Ltd. All rights reserved.
 */
package com.interceptor;

import com.action.UsersAction;
import com.entity.Users;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @author shihaojie
 * @date 2015年4月24日
 * @version $Id$
 */
public class LoginInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8455597872569428753L;

	/**
	 * 登录拦截。 如果没有登录。除了users_login.action，别的action方法都无法访问
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Users u = (Users) ActionContext.getContext().getSession().get("user");
		if (UsersAction.class == invocation.getAction().getClass() && invocation.getProxy().getMethod().equals("login")) {
			return invocation.invoke();
		}
		if (null == u) {
			return "login";
		}
		return invocation.invoke();
	}

}
