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
			<jsp:include page="connectMenu.jsp" />
		</div>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">数据库连接</strong> / <small>Table</small>
				</div>
			</div>

			<div class="am-g">
				<div class="am-u-sm-12 am-u-md-6">
					<div class="am-btn-toolbar">
						<div class="am-btn-group am-btn-group-xs">
							<button type="button" class="am-btn am-btn-default" data-am-modal="{target: '#addConnect', closeViaDimmer: 0}">
								<span class="am-icon-plus"></span> 新增
							</button>

							<div align="left" class="am-modal am-modal-no-btn" tabindex="-1" id="addConnect">
								<div class="am-modal-dialog">
									<div class="am-modal-hd">
										添加数据库连接 <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
									</div>
									<div class="am-modal-bd">
										<form action="connect_addConnect.action" method="post" class="am-form">
											<fieldset>

												<div class="am-form-group">
												
													<label for="doc-select-1" style="float: left">分类：</label> <select id="doc-select-1" name="addConnect.connectTypeId" required>
														<option value="">请选择分类</option>
														<c:forEach var="item" items="${typeList }">
															<option value="${item.id }">${item.typeName }</option>
														</c:forEach>
													</select> <span class="am-form-caret"></span>
												</div>

												<div class="am-form-group">
													<label for="doc-vld-name-2" style="float: left">名称：</label> <input type="text" id="doc-vld-name-2" name="addConnect.connectName"  placeholder="请输入名称" required />
												</div>

												<div class="am-form-group">
													<label for="doc-vld-name-2" style="float: left">IP地址：</label> <input type="text" id="doc-vld-name-2" name="addConnect.connectIp"  placeholder="请输入IP地址" required />
												</div>
												<div class="am-form-group">
													<label for="doc-vld-name-2" style="float: left">端口号：</label> <input type="number" id="doc-vld-name-2" name="addConnect.connectPort" value="27017"  />
												</div>
												<div class="am-form-group">
													<label for="doc-vld-name-2" style="float: left">用户名：</label> <input type="text" id="doc-vld-name-2" name="addConnect.connectUserName" placeholder="请输入用户名"  />
												</div>
												<div class="am-form-group">
													<label for="doc-vld-name-2" style="float: left">密码：</label> <input type="text" id="doc-vld-name-2" name="addConnect.connectPassword" placeholder="请输入密码"  />
												</div>

												<button class="am-btn am-btn-secondary" type="submit">添加</button>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<button class="am-btn am-btn-secondary" type="button" onclick="closeAddConnect()">取消</button>
											</fieldset>
										</form>

									</div>
								</div>
							</div>

							<button type="button" onclick="deleteUsers()" class="am-btn am-btn-default">
								<span class="am-icon-trash-o"></span> 删除
							</button>
						</div>
					</div>
				</div>

			</div>

			<div class="am-g">
				<div class="am-u-sm-12">
					<table class="am-table am-table-striped am-table-hover table-main">
						<thead>
							<tr>
								<th class="table-check"><label class="am-checkbox am-secondary am-danger"> <input type="checkbox" id="allC" onclick="allCheckbox()" data-am-ucheck />
								</label></th>
								<th class="table-id">编号</th>
								<th>名称</th>
								<th>分类</th>
								<th>IP地址</th>
								<th>端口</th>
								<th>用户名</th>
								<th>密码</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="connect" items="${list}" varStatus="">
								<tr>
									<td><label class="am-checkbox am-secondary am-danger"> <input onclick="ck()" value="${connect.connectId}" class="ck" type="checkbox" data-am-ucheck />

									</label></td>
									<td>${connect.connectId}</td>
									<td>${connect.connectName}</td>
									<td>${connect.connectTypeName}</td>
									<td>${connect.connectIp}</td>
									<td>${connect.connectPort}</td>
									<td>${connect.connectUserName}</td>
									<td>${connect.connectPassword}</td>
									<td>
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<button type="button" class="am-btn am-btn-default am-btn-xs am-text-secondary" data-am-modal="{target: '#updateConnect${connect.connectId }', closeViaDimmer: 0}">
													<span class="am-icon-pencil-square-o"></span> 编辑
												</button>

												<button type="button" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only del_user" data-am-modal="{target: '#delConnectModel${connect.connectId }', closeViaDimmer: 0}">
													<span class="am-icon-trash-o"></span> 删除
												</button>
												<!-- onclick="delUser(${user.userId})" -->
											</div>
										</div>



										<div class="am-modal am-modal-confirm" tabindex="-1" id="delConnectModel${connect.connectId }">
											<div class="am-modal-dialog">
												<div class="am-modal-hd">系统提示</div>
												<div class="am-modal-bd">你，确定要删除这条记录吗？</div>
												<div class="am-modal-footer">
													<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span class="am-modal-btn" onclick="del(${connect.connectId })" data-am-modal-confirm>确定</span>
												</div>
											</div>
										</div>
									</td>
								</tr>

								
							</c:forEach>



						</tbody>
					</table>

					<c:forEach var="connect" items="${list}" varStatus="">
					<div align="left" class="am-modal am-modal-no-btn" tabindex="-1" id="updateConnect${connect.connectId }">
					
						<div class="am-modal-dialog">
							<div class="am-modal-hd">
								修改部门信息部门 <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
							</div>
							<div class="am-modal-bd">
								<form action="connect_updateConnect.action" method="post" class="am-form">
									<fieldset>
										<input type="hidden" name="updateConnect.connectId" value="${connect.connectId }" />
										<div class="am-form-group">
													<label for="doc-select-1" style="float: left">分类：</label> <select id="doc-select-1" name="updateConnect.connectTypeId" required>
														<option value="">请选择分类</option>
														<c:forEach var="item" items="${typeList }">
															<option ${connect.connectTypeId==item.id?"selected='selected'":"" } value="${item.id }">${item.typeName }</option>
														</c:forEach>
													</select> <span class="am-form-caret"></span>
												</div>
										<div class="am-form-group">
											<label for="doc-vld-name-2" style="float: left">名称：</label> <input type="text" id="doc-vld-name-2" name="updateConnect.connectName" placeholder="请输入名称" value="${connect.connectName}" required />
										</div>
										<div class="am-form-group">
											<label for="doc-vld-name-2" style="float: left">IP地址：</label> <input type="text" id="doc-vld-name-2" name="updateConnect.connectIp" placeholder="请输入IP地址" value="${connect.connectIp}" required />
										</div>
										<div class="am-form-group">
											<label for="doc-vld-name-2" style="float: left">端口：</label> <input type="text" id="doc-vld-name-2" name="updateConnect.connectPort" placeholder="请输入端口号" value="${connect.connectPort}" required />
										</div>
										<div class="am-form-group">
											<label for="doc-vld-name-2" style="float: left">用户名：</label> <input type="text" id="doc-vld-name-2" name="updateConnect.connectUserName" placeholder="请输入用户名" value="${connect.connectUserName}"  />
										</div>
										<div class="am-form-group">
											<label for="doc-vld-name-2" style="float: left">密码：</label> <input type="text" id="doc-vld-name-2" name="updateConnect.connectPassword" placeholder="请输入密码" value="${connect.connectPassword}"  />
										</div>

										<button class="am-btn am-btn-secondary" type="submit">保存</button>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<button class="am-btn am-btn-secondary" type="button" onclick="closeUpdateConnect(${connect.connectId})">取消</button>
									</fieldset>
								</form>

							</div>
						</div>
						</div>
					</c:forEach>
					
					<div class="am-cf">
						共 ${count } 条记录
						<div class="am-fr">
							<ul class="am-pagination">
								<li class="${page<=1?'am-disabled':'' }"><a href="connect_list.action?page=${page-1 }">上一页</a></li>
								<c:forEach begin="1" end="${countPage }" varStatus="status">
									<li class=" ${status.index==page?'am-active':'' } "><a href="connect_list.action?page=${status.index }">${ status.index}</a></li>
								</c:forEach>
								<li class="${page>=countPage?'am-disabled':'' }"><a href="connect_list.action?page=${page+1 }">下一页</a></li>
							</ul>
						</div>
					</div>
					<hr />
					<p>注：.....</p>
					
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

//取消添加
function closeAddConnect(){
	$("#addConnect").modal('close');
}
//取消修改
function closeUpdateConnect(pid){
	$("#updateConnect"+pid).modal('close');
	
}
//删除多条用户信息
function deleteUsers(){
	var str="";
	var delUser=$(".ck:checked");
	for (var i = 0; i < delUser.length; i++) {
		str=str+$(delUser[i]).val();
		if(i<delUser.length-1){
			str+=",";
		};
	}
	if(str!=""){
		if(confirm("确定删除选中的"+delUser.length+"个数据库连接信息？")){//如果是true ，那么就删除
		location.href="connect_deleteConnect.action?deleteConnectId="+str;
	    };
	}else{
		alert("请选择要删除的数据库连接信息信息！");
	};
}

//删除一条职位信息
function del(pid){
	location.href="connect_deleteConnect.action?deleteConnectId="+pid;
};


</script>

</body>
</html>
