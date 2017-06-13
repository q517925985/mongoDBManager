<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
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
					<strong class="am-text-primary am-text-lg">404</strong> / <small>That’s an error</small>
				</div>
			</div>

			<div class="am-g">
				<div class="am-u-sm-12">
					<h2 class="am-text-center am-text-xxxl am-margin-top-lg">404. Not Found</h2>
					<p class="am-text-center">没有找到你要的页面</p>
					<pre class="page-404">
					          .----.
					       _.'__    `.
					   .--($)($$)---/#\
					 .' @          /###\
					 :         ,   #####
					  `-..__.-' _.-\###/
					        `;_:    `"'
					      .'"""""`.
					     /,  ya ,\\
					    //  404!  \\
					    `-._______.-'
					    ___`. | .'___
					   (______|______)
			        </pre>
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

</body>
</html>
