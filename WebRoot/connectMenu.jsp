<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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

	<!-- sidebar start -->

	<div class="am-offcanvas-bar admin-offcanvas-bar">
		<ul class="am-list admin-sidebar-list">
			<li><a href="users_index.action"><span class="am-icon-home"></span> 首页</a></li>
			<li class="admin-parent"><a class="am-cf" data-am-collapse="{target: '#basic-nav'}"><span class="am-icon-file"></span> 基本信息管理 <span class="am-icon-angle-right am-fr am-margin-right"></span></a>
				<ul class="am-list am-collapse admin-sidebar-sub" id="basic-nav">
					<li><a href="users_list.action" class="am-cf "><span class="am-icon-check"></span> 用户管理<span class="am-icon-star am-fr am-margin-right admin-icon-yellow"></span></a></li>
					<li><a href="dep_list.action"><span class="am-icon-puzzle-piece"></span> 部门管理</a></li>
					<li><a href="posi_list.action"><span class="am-icon-puzzle-piece"></span> 职位管理</a></li>
				</ul></li>
				<li><a href="users_toUpdate.action"><span class="am-icon-user"></span> 个人资料</a></li>
			<li class="admin-parent"><a class="am-cf" data-am-collapse="{target: '#connecta-nav'}"><span class="am-icon-wifi"></span> 数据连接管理 <span class="am-icon-angle-right am-fr am-margin-right"></span></a>
				<ul class="am-list am-collapse admin-sidebar-sub am-in" id="connecta-nav">
					<li><a href="connect_list.action"><span class="am-icon-gear"></span>数据连接管理 </a></li>
					<li><a href="connectType_list.action"><span class="am-icon-gear"></span>数据连接分类管理 </a></li>
				</ul></li>
			<li class="admin-parent"><a href="mongo_connectList.action"><span class="am-icon-database"></span> 导入导出</a></li>
			<li class="admin-parent"><a href="mongoSync_connectList.action"><span class="am-icon-database"></span> 数据同步 </a></li>
			
			<li class="admin-parent"><a href="task_list.action"><span class="am-icon-table"></span> 任务列表 </a></li>
			<li class="admin-parent"><a href="logRecord_list.action"><span class="am-icon-table"></span> 日志列表 </a></li>
			<li><a  href="javascript:void(0)" onclick="exit()" ><span class="am-icon-sign-out"></span> 注销</a></li>
		</ul>

		<div class="am-panel am-panel-default admin-sidebar-panel">
			<div class="am-panel-bd">
				<p>
					<span class="am-icon-bookmark"></span> 公告
				</p>
				<p>..........</p>
			</div>
		</div>

	</div>

</body>
</html>
