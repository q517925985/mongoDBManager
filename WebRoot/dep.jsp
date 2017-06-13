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
					<strong class="am-text-primary am-text-lg">职位</strong> / <small>Table</small>
				</div>
			</div>

			<div class="am-g">
				<div class="am-u-sm-12 am-u-md-6">
					<div class="am-btn-toolbar">
						<div class="am-btn-group am-btn-group-xs">
							<button type="button" class="am-btn am-btn-default" data-am-modal="{target: '#addDep', closeViaDimmer: 0}">
								<span class="am-icon-plus"></span> 新增
							</button>

							<div align="left" class="am-modal am-modal-no-btn" tabindex="-1" id="addDep">
								<div class="am-modal-dialog">
									<div class="am-modal-hd">
										添加部门 <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
									</div>
									<div class="am-modal-bd">
										<form action="dep_addDep.action" method="post" class="am-form">
											<fieldset>

												<div class="am-form-group">
													<label for="doc-vld-name-2" style="float: left">部门名称：</label> <input type="text" id="doc-vld-name-2" onblur="verificationUserName()" name="addDep.depName" class="addDep_depName" placeholder="请输入部门名称" required />
												</div>

												<button class="am-btn am-btn-secondary" type="submit">添加</button>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<button class="am-btn am-btn-secondary" type="button" onclick="closeAddDep()">取消</button>
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
									<th>部门名称</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="dep" items="${list}" varStatus="">
									<tr>
										<td><label class="am-checkbox am-secondary am-danger"> <input onclick="ck()" value="${dep.id}" class="ck" type="checkbox" data-am-ucheck />

										</label></td>
										<td>${dep.id}</td>
										<td>${dep.depName}</td>
										<td>
											<div class="am-btn-toolbar">
												<div class="am-btn-group am-btn-group-xs">
													<button type="button" class="am-btn am-btn-default am-btn-xs am-text-secondary" data-am-modal="{target: '#updateDep${dep.id }', closeViaDimmer: 0}">
														<span class="am-icon-pencil-square-o"></span> 编辑
													</button>

													<button type="button" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only del_user" id="${dep.id }" data-am-modal="{target: '#delPosiModel${dep.id }', closeViaDimmer: 0}">
														<span class="am-icon-trash-o"></span> 删除
													</button>
													<!-- onclick="delUser(${user.userId})" -->
												</div>
											</div>



											<div class="am-modal am-modal-confirm" tabindex="-1" id="delPosiModel${dep.id }">
												<div class="am-modal-dialog">
													<div class="am-modal-hd">系统提示</div>
													<div class="am-modal-bd">你，确定要删除这条记录吗？</div>
													<div class="am-modal-footer">
														<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span class="am-modal-btn" onclick="del(${dep.id })" data-am-modal-confirm>确定</span>
													</div>
												</div>
											</div>
										</td>
									</tr>

									

								</c:forEach>



							</tbody>
						</table>
						<c:forEach var="dep" items="${list}" varStatus="">

							<div align="left" class="am-modal am-modal-no-btn" tabindex="-1" id="updateDep${dep.id }">
								<div class="am-modal-dialog">
									<div class="am-modal-hd">
										修改部门信息部门 <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
									</div>
									<div class="am-modal-bd">
										<form action="dep_updateDep.action" method="post" class="am-form">
											<fieldset>
												<input type="hidden" name="updateDep.id" value="${dep.id }" />
												<div class="am-form-group">
													<label for="doc-vld-name-2" style="float: left">部门名称：</label> <input type="text" id="doc-vld-name-2" onblur="verificationUserName()" name="updateDep.depName" class="addDep_depName" placeholder="请输入部门名称" value="${dep.depName}"
														required />
												</div>

												<button class="am-btn am-btn-secondary" type="submit">保存</button>
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<button class="am-btn am-btn-secondary" type="button" onclick="closeUpdateDep(${dep.id})">取消</button>
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
									<li class="${page<=1?'am-disabled':'' }"><a href="dep_list.action?page=${page-1 }">上一页</a></li>
									<c:forEach begin="1" end="${countPage }" varStatus="status">
										<li class=" ${status.index==page?'am-active':'' } "><a href="dep_list.action?page=${status.index }">${ status.index}</a></li>
									</c:forEach>
									<li class="${page>=countPage?'am-disabled':'' }"><a href="dep_list.action?page=${page+1 }">下一页</a></li>
								</ul>
							</div>
						</div>
						<hr />
						<p>注：如果所删除部门存在用户，则无法删除！</p>
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
function closeAddDep(){
	$("#addDep").modal('close');
}
//取消修改
function closeUpdateDep(pid){
	$("#updateDep"+pid).modal('close');
	
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
		if(confirm("确定删除选中的"+delUser.length+"个部门信息？")){//如果是true ，那么就删除
		location.href="dep_deleteDep.action?deleteDepsId="+str;
	    }
	}else{
		alert("请选择要删除的部门信息！");
	}
}

//删除一条职位信息
function del(pid){
	location.href="dep_deleteDep.action?deleteDepsId="+pid;
}


</script>

</body>
</html>
