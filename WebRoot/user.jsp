<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>MongoDB数据库管理</title>
<meta name="description" content="这是一个form页面">
<meta name="keywords" content="form">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
</head>
<body>
	<header class="am-topbar admin-header">
		<jsp:include page="header.jsp" />
	</header>

	<div class="am-cf admin-main">
		<!-- sidebar start -->
		<div class="admin-sidebar am-offcanvas" id="admin-offcanvas" style="height: 800px">
			<jsp:include page="usermenu.jsp" />
		</div>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">用户</strong> / <small>Table</small>
				</div>
			</div>

			<div class="am-g">
				<div class="am-u-sm-12 am-u-md-6">
					<div class="am-btn-toolbar">
						<div class="am-btn-group am-btn-group-xs">
							<button type="button" class="am-btn am-btn-default" data-am-modal="{target: '#addUser', closeViaDimmer: 0}">
								<span class="am-icon-plus"></span> 新增
							</button>

							<div align="left" class="am-modal am-modal-no-btn" tabindex="-1" id="addUser">
								<div class="am-modal-dialog">
									<div class="am-modal-hd">
										添加用户 <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
									</div>
									<div class="am-modal-bd">
										<form action="users_addUser.action" method="post" class="am-form">
											<fieldset>

												<div class="am-form-group">
													<label for="doc-vld-name-2" style="float: left">用户名：<span id="validityUserName" style="color: red"></span></label> <input type="text" oninput="validityUserName(this)" id="doc-vld-name-2"  name="addUser.userName" class="addUser_userNname" placeholder="输入用户名" required />
												</div>

												<div class="am-form-group">
													<label for="doc-vld-pwd-1" style="float: left">密码：</label> <input type="password" id="doc-vld-pwd-1" name="addUser.password" placeholder="请输入密码" required />
												</div>


												<div class="am-form-group">
													<label for="doc-vld-name-2" style="float: left">昵称：</label> <input type="text" id="doc-vld-name-2" name="addUser.nickname"  placeholder="输入昵称" required />
												</div>

												<div class="am-form-group">
													<label style="float: left">性别： </label> <label class="am-radio-inline"> <input type="radio" value="" checked="checked" name="addUser.gender" value=false data-am-ucheck required> 男
													</label> <label class="am-radio-inline"> <input type="radio" name="addUser.gender" value=true data-am-ucheck> 女
													</label>
												</div>

												<div class="am-form-group">
													<label for="doc-vld-age-2" style="float: left">年龄：</label> <input type="number" class="" id="doc-vld-age-2" name="addUser.age" placeholder="输入年龄  18-120" min="18" max="120" value=""/>
												</div>

												<div class="am-form-group">
													<label for="doc-select-1" style="float: left">部门：</label> <select id="doc-select-1" name="addUser.depId" required>
														<option value="">请选择部门</option>
														<c:forEach var="dep" items="${depList }">
															<option value="${dep.id }">${dep.depName }</option>
														</c:forEach>
													</select> <span class="am-form-caret"></span>
												</div>

												<div class="am-form-group">
													<label for="doc-select-1" style="float: left">职位：</label> <select id="doc-select-1" name="addUser.posiId" required>
														<option value="">请选择职位</option>
														<c:forEach var="posi" items="${posiList }">
															<option value="${posi.id }">${posi.posiName }</option>
														</c:forEach>
													</select> <span class="am-form-caret"></span>
												</div>

												<div class="am-form-group">
													<label for="doc-vld-name-2" style="float: left">手机号：</label> <input type="text" id="doc-vld-name-2" name="addUser.phone" maxlength="11" placeholder="输入手机号" pattern="^\d{11}$" value="" />
												</div>

												<div class="am-form-group">
													<label for="doc-vld-email-2" style="float: left">邮箱：</label> <input type="email" id="doc-vld-email-2" name="addUser.email" placeholder="输入邮箱" value=""/>
												</div>

												<button class="am-btn am-btn-secondary" disabled="disabled" id="addUserBtn" type="submit">添加</button>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<button class="am-btn am-btn-secondary" type="button" onclick="closeAddUser()">取消</button>
											</fieldset>
										</form>

									</div>
								</div>
							</div>

							<c:if test="${sessionScope.user.posiId=='3' }">
								<button type="button" onclick="deleteUsers()" class="am-btn am-btn-default">
									<span class="am-icon-trash-o"></span> 删除
								</button>
							</c:if>
						</div>
					</div>
				</div>
				<form action="users_list.action?page=1" method="post">
					<div class="am-u-sm-12 am-u-md-3">
						<div class="am-form-group">
							<select name="tiaojian.depId" data-am-selected="{btnSize: 'sm'}">
								<option value="0">所有部门</option>
								<c:forEach var="dep" items="${depList }">
									<option value="${dep.id }">${dep.depName }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="am-u-sm-12 am-u-md-3">
						<div class="am-input-group am-input-group-sm">
							<input type="text" name="tiaojian.userName" class="am-form-field" placeholder="请输入姓名"> <span class="am-input-group-btn">
								<button class="am-btn am-btn-default" type="submit">搜索</button>
							</span>
						</div>
					</div>
				</form>
			</div>

			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form" action="users_update.action" method="post">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th class="table-check"><label class="am-checkbox am-secondary am-danger"> <input type="checkbox" id="allC" onclick="allCheckbox()" data-am-ucheck />
									</label></th>
									<th class="table-id">帐号</th>
									<th class="table-userName">用户名</th>
									<th class="table-userName">昵称</th>
									<th class="table-gender">性别</th>
									<th class="table-age">年龄</th>
									<th class="table-dep">部门</th>
									<th class="table-posi">职位</th>
									<th class="table-email">手机号</th>
									<th class="table-email">Email</th>
										<th class="table-set">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="user" items="${list}" varStatus="">
									<tr>
										<td><label class="am-checkbox am-secondary am-danger"> <input onclick="ck()" value="${user.userId}" class="ck" type="checkbox" data-am-ucheck />

										</label></td>
										<td>${user.userId }</td>
										<td>${user.userName }</td>
										<td>${user.nickname }</td>
										<td>${user.gender?"女":"男"}</td>
										<td>${user.age==0?null:user.age}</td>
										<td>${user.depName}</td>
										<td>${user.posiName}</td>
										<td>${user.phone}</td>
										<td>${user.email}</td>
											<td>
												<div class="am-btn-toolbar">
													<div class="am-btn-group am-btn-group-xs">
														<button type="button" class="am-btn am-btn-default am-btn-xs am-text-secondary" data-am-modal="{target: '#updateUser${user.userId }', closeViaDimmer: 0}">
															<span class="am-icon-pencil-square-o"></span> 编辑
														</button>
														<button type="button" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only del_user" id="${user.userId }" data-am-modal="{target: '#delUserModel${user.userId }'}">
															<span class="am-icon-trash-o"></span> 删除
														</button>
														<!-- onclick="delUser(${user.userId})" -->
													</div>

													<div class="am-modal am-modal-confirm" tabindex="-1" id="delUserModel${user.userId }">
														<div class="am-modal-dialog">
															<div class="am-modal-hd">系统提示</div>
															<div class="am-modal-bd">你，确定要删除昵称为 ${user.nickname } 的用户吗？</div>
															<div class="am-modal-footer">
																<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span class="am-modal-btn" onclick="del(${user.userId })" data-am-modal-confirm>确定</span>
															</div>
														</div>
													</div>

												</div>


											</td>

											<div align="left" class="am-modal am-modal-no-btn" tabindex="-1" id="updateUser${user.userId }">
												<div class="am-modal-dialog">
													<div class="am-modal-hd">
														修改用户资料 <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
													</div>
													<div class="am-modal-bd">
														<form action="users_update.action" method="post" class="am-form">
															<fieldset>
																<input type="hidden" name="listUpdateUser.userId" value="${user.userId }">

																<div class="am-form-group">
																	<label for="doc-vld-name-2" style="float: left">用户名：</label> <input readonly="readonly" type="text" id="doc-vld-name-2" name="listUpdateUser.userName" value="${user.userName }" class="addUser_userNname" placeholder="输入用户名"
																		required />
																</div>

																<div class="am-form-group">
																	<label for="doc-vld-pwd-1" style="float: left">密码：</label> <input type="text" id="doc-vld-pwd-1" name="listUpdateUser.password" value="${user.password }" required />
																</div>
																

																<div class="am-form-group">
																	<label for="doc-vld-name-2" style="float: left">昵称：</label> <input  type="text" id="doc-vld-name-2" name="listUpdateUser.nickname" value="${user.nickname }" class="addUser_userNname" placeholder="输入昵称"
																		required />
																</div>
																<div class="am-form-group">
																	<label style="float: left">性别： </label> <label class="am-radio-inline"> <input type="radio" value="" name="listUpdateUser.gender" value=false ${user.gender?"":"checked" } data-am-ucheck required> 男
																	</label> <label class="am-radio-inline"> <input type="radio" name="listUpdateUser.gender" ${user.gender?"checked":"" } value=true data-am-ucheck> 女
																	</label>
																</div>

																<div class="am-form-group">
																	<label for="doc-vld-age-2" style="float: left">年龄：</label> <input type="number" class="" id="doc-vld-age-2" name="listUpdateUser.age" value="${user.age==0?null:user.age }" placeholder="输入年龄  18-120" min="18" max="120" />	
																</div>

																<div class="am-form-group">
																	<label for="doc-select-1" style="float: left">部门：</label> <select id="doc-select-1" name="listUpdateUser.depId" required>
																		<option value="0">请选择部门</option>
																		<c:forEach var="dep" items="${depList }">
																			<option ${user.depId==dep.id?"selected='selected'":"" } value="${dep.id }">${dep.depName }</option>
																		</c:forEach>
																	</select> <span class="am-form-caret"></span>
																</div>

																<div class="am-form-group">
																	<label for="doc-select-1" style="float: left">职位：</label> <select id="doc-select-1" name="listUpdateUser.posiId" required>
																		<option value="0">请选择职位</option>
																		<c:forEach var="posi" items="${posiList }">
																			<option ${user.posiId==posi.id?"selected='selected'":"" } value="${posi.id }">${posi.posiName }</option>
																		</c:forEach>
																	</select> <span class="am-form-caret"></span>
																</div>

																<div class="am-form-group">
																	<label for="doc-vld-name-2" style="float: left">手机号：</label> <input type="text" id="doc-vld-name-2" value="${user.phone }" name="listUpdateUser.phone" maxlength="11" placeholder="输入手机号" pattern="^\d{11}$" value="" />
																</div>

																<div class="am-form-group">
																	<label for="doc-vld-email-2" style="float: left">邮箱：</label> <input type="email" id="doc-vld-email-2" value="${user.email }" name="listUpdateUser.email" placeholder="输入邮箱" value="" />
																</div>

																<button class="am-btn am-btn-secondary" type="submit">保存修改</button>
															</fieldset>
														</form>

													</div>
												</div>

											</div>

									</tr>




								</c:forEach>
							</tbody>
						</table>
						<div class="am-cf">
							共 ${count } 条记录
							<div class="am-fr">
								<ul class="am-pagination">
									<li class="${page<=1?'am-disabled':'' }"><a href="users_list.action?page=${page-1 }">上一页</a></li>
									<c:forEach begin="1" end="${countPage }" varStatus="status">
										<li class=" ${status.index==page?'am-active':'' } "><a href="users_list.action?page=${status.index }">${ status.index}</a></li>
									</c:forEach>
									<li class="${page>=countPage?'am-disabled':'' }"><a href="users_list.action?page=${page+1 }">下一页</a></li>
								</ul>
							</div>
						</div>
						<hr />
						<p>注：.....</p>
					</form>
				</div>

			</div>
		</div>
		<!-- content end -->

	</div>

	<a class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

	<footer>
		<hr>
		<jsp:include page="footer.jsp" />
	</footer>


	<script type="text/javascript">
	//用户名验证
	function validityUserName(inputName){
		var username=$(inputName).val();
		$.ajax({
			type : "POST",
			url : "users_validityUserName.action",
			data : {
				"user.userName" : username
			},
			success : function(data) {
				if(data){
					$("#validityUserName").text("（用户名已存在）");
					$("#addUserBtn").attr("disabled", "disabled");
				}else{
					$("#validityUserName").text("");
					$("#addUserBtn").removeAttr("disabled");
				}
			}
		});
	}

//取消添加
function closeAddUser(){
	$("#addUser").modal('close');
}
//取消修改
function closeUpdateUser(uid){
	$("#updateUser"+uid).modal('close');
	
}

//删除多条用户信息
function deleteUsers(){
	var str="";
	var delUser=$(".ck:checked");
	for (var i = 0; i < delUser.length; i++) {
		str=str+$(delUser[i]).val();
		if(i<delUser.length-1){
			str+=",";
			
		}
	}
	if(str!=""){
		if(confirm("确定删除选中的"+delUser.length+"个用户信息？")){//如果是true ，那么就把页面转向thcjp.cnblogs.com
			location.href="users_deleteUser.action?deleteUsersId="+str;
	    }
	}else{
		alert("请选择要删除的用户信息！");
	}
}

//删除一条用户信息
function del(uid){
	location.href="users_deleteUser.action?deleteUsersId="+uid;
}
</script>

</body>
</html>
