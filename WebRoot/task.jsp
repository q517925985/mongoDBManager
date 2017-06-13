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
			<jsp:include page="menu.jsp" />
		</div>
		<!-- sidebar end -->

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">任务列表</strong> / <small>Table</small>
				</div>

				<div class="am-u-12 am-margin-top-lg">
					<form action="task_list.action?page=1" method="post">

						<div class="am-u-md-3">
							<div class="am-input-group am-input-group-sm">
								<input type="text" name="search.remarks" class="am-form-field" placeholder="请输入任务描述"> <span class="am-input-group-btn">
									<button class="am-btn am-btn-default" type="submit">搜索</button>
								</span>
							</div>
						</div>
					</form>
				</div>



			</div>
			<hr />

			<div class="am-g">
				<div class="am-u-sm-12">
					<form class="am-form" action="logRecord_lifted.action" method="post">
						<table class="am-table am-table-striped am-table-hover table-main">
							<thead>
								<tr>
									<th class="table-id">编号</th>
									<th>类型</th>
									<th>是否同步整个库</th>
									<th>源IP地址</th>
									<th>源数据库</th>
									<th>接收IP地址</th>
									<th>接收数据库</th>
									<th>任务描述</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="task" items="${list}" varStatus="">

									<tr>
										<td>${task.id}</td>
										<td>${task.importType?"覆盖":"添加"}</td>
										<td>${task.importIsDb?"是":"否"}</td>
										<td>${task.sourceConnectIp}</td>
										<td>${task.sourceDatebaseName}</td>
										<td>${task.receivedConnectIp}</td>
										<td>${task.receivedDatebaseName}</td>
										<td>${task.remarks}</td>
										<td>
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<button type="button" class="am-btn am-btn-default am-btn-xs am-text-secondary" onclick="toExecute(${task.id})">执行</button>

												<button type="button" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only del_user" onclick="toDeleteTask(${task.id})">删除</button>
												<!-- onclick="delUser(${user.userId})" -->
											</div>
									</tr>
								</c:forEach>
							</tbody>
						</table>

						<div class="am-cf">
							共 ${count } 条记录
							<div class="am-fr">
								<ul class="am-pagination">
									<li class="${page<=1?'am-disabled':'' }"><a href="logRecord_list.action?page=${page-1 }">上一页</a></li>
									<c:forEach begin="1" end="${countPage }" varStatus="status">
										<li class=" ${status.index==page?'am-active':'' } "><a href="logRecord_list.action?page=${status.index }">${ status.index}</a></li>
									</c:forEach>
									<li class="${page>=countPage?'am-disabled':'' }"><a href="logRecord_list.action?page=${page+1 }">下一页</a></li>
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

	<footer>
		<hr>
		<jsp:include page="footer.jsp" />
	</footer>
	<div class="am-modal am-modal-confirm" tabindex="-1" id="executeModal">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">确认任务信息</div>
			<div class="am-modal-bd">
				<input id="execute_id" type="hidden" name="execute.id" value="">
				<div style="border: 1px solid #ccc; width: 320px; margin: 0px auto; padding: 20px" align="left">
					源服务器：<span class="task_sourceip_span"></span> <input class="task_sourceid" type="hidden" name="" value=""> <br /> 源数据库：<span class="task_sourcedb_span"></span> <input class="task_sourcedb" type="hidden" name="" value="">
					<br />
					<div class="am-scrollable-vertical am-text-nowrap" style="min-height: 0">
						<table class="am-table am-table-bordered am-scrollable-horizontal">
							<thead>
								<tr>
									<th>编号</th>
									<th>源集合</th>
								</tr>
							</thead>
							<tbody id="sourceTablestbody">
							</tbody>
						</table>
					</div>
				</div>
				<i class="am-icon-angle-double-down"></i>
				<div style="border: 1px solid #ccc; width: 320px; margin: 0px auto; padding: 20px" align="left">
					接收服务器：<span class="task_receivedip_span"></span> <input class="task_receivedid" type="hidden" name="" value=""> <br /> 接收数据库：<span class="task_receiveddb_span"></span> <input class="task_receiveddb" type="hidden" name=""
						value=""> <br />
				</div>
			</div>
			<div class="am-modal-footer">
				<span class="am-modal-btn" data-am-modal-cancel>返回列表</span> <span class="am-modal-btn" onclick="doExecute()" data-am-modal-confirm>开始执行</span>
			</div>
		</div>
	</div>
	
	<div class="am-modal am-modal-confirm" tabindex="-1" id="deleteTaskModel">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">系统提示</div>
			<div class="am-modal-bd">你，确定要删除这条记录吗？</div>
			<div class="am-modal-footer">
				<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span class="am-modal-btn" onclick="doDeleteTask()" data-am-modal-confirm>确定</span>
			</div>
		</div>
	</div>
	
	<div class="am-modal am-modal-no-btn" tabindex="-1" id="doExecuteTaskModal">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">
				<a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
			</div>
			<div class="am-modal-bd">
				<div class="am-modal-hd">任务执行中...</div>
				<div class="am-modal-bd">
					<i class="am-icon-spinner am-icon-pulse"></i>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	function toDeleteTask(deleteTask){
		$("#execute_id").val(deleteTask);
		$("#deleteTaskModel").modal("open");
	}function doDeleteTask(deleteTask){
		var execute_id=$("#execute_id").val();
		location.href="task_deleteTask.action?deleteId="+execute_id;
	}
		function toExecute(taskid) {
			$.ajax({
			type : "POST",
			url : "task_getTaskJson.action",
			data : {
				"taskId" : taskid
			},
			success : function(data) {
				$("#execute_id").val(data.id);
				$("#sourceTablestbody").empty();
				var str="";
				if(data.importIsDb){
					str="<tr><td colspan='2' style='text-align:center'>同步整个数据库</td></tr>";
				}else{
					for(var i=0;i<data.tableList.length;i++){
						str+="<tr><td>"+(i+1)+"</td><td>"+data.tableList[i].sourceTableName+"</td></tr>";
					}
				}
				$("#sourceTablestbody").append(str);
				$(".task_sourceip_span").text(data.sourceConnectIp);
				$(".task_sourcedb_span").text(data.sourceDatebaseName);
				$(".task_receivedip_span").text(data.receivedConnectIp);
				$(".task_receiveddb_span").text(data.receivedDatebaseName);
				$("#executeModal").modal("open");
			}
			});
		}
		function doExecute(){
			$("#doExecuteTaskModal").modal("open");
			var execute_id=$("#execute_id").val();
			location.href="task_executeTask.action?taskId="+execute_id;
		}
	</script>

</body>
</html>
