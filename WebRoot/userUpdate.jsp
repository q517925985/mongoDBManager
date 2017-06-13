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
		<div class="admin-sidebar am-offcanvas" id="admin-offcanvas" style="height: 800px">
			<jsp:include page="menu.jsp" />
		</div>

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">个人资料</strong> / <small>Personal information</small>
				</div>
			</div>

			<hr />

			<div class="am-g">

				<div class="am-u-sm-12 am-u-md-4 am-u-md-push-8"></div>

				<div class="am-u-sm-12 am-u-md-8 am-u-md-pull-4">

					<form action="users_doUpdate.action" method="post" class="am-form">
						<input type="hidden" name="updateUser.userId" value="${updateUser.userId }">
						<fieldset>

							<div class="am-form-group">
								<label for="doc-vld-name-2" style="float: left">用户名：</label> <input readonly="readonly" type="text" id="doc-vld-name-2" name="updateUser.userName" value="${updateUser.userName }" class="addUser_userNname" placeholder="输入用户名" required />
							</div>
							<div class="am-form-group">
								<label for="doc-vld-name-2" style="float: left">昵称：</label> <input type="text" id="doc-vld-name-2" name="updateUser.nickname" value="${updateUser.nickname }" placeholder="输入昵称" required />
							</div>
							
							<div class="am-form-group">
								<label for="doc-vld-pwd-1" style="float: left">密码：</label> <input type="text" id="doc-vld-pwd-1" name="updateUser.password" value="${updateUser.password }" required />
							</div>
							<div class="am-form-group">
								<label style="float: left">性别： </label> <label class="am-radio-inline"> <input type="radio" value="" name="updateUser.gender" value=false ${updateUser.gender?"":"checked" } data-am-ucheck required> 男
								</label> <label class="am-radio-inline"> <input type="radio" name="updateUser.gender" ${updateUser.gender?"checked":"" } value=true data-am-ucheck> 女
								</label>
							</div>

							<div class="am-form-group">
								<label for="doc-vld-age-2" style="float: left">年龄：</label> <input type="number" class="" id="doc-vld-age-2" name="updateUser.age" value="${updateUser.age }" placeholder="输入年龄  18-120" min="18" max="120" required />
							</div>
							<input type="hidden" name="updateUser.depId" value="${updateUser.depId }" /> <input type="hidden" name="updateUser.posiId" value="${updateUser.posiId }" />


							<div class="am-form-group">
								<label for="doc-vld-name-2" style="float: left">手机号：</label> <input type="text" id="doc-vld-name-2" value="${updateUser.phone }" name="updateUser.phone" maxlength="11" placeholder="输入手机号" pattern="^\d{11}$" required />
							</div>

							<div class="am-form-group">
								<label for="doc-vld-email-2" style="float: left">邮箱：</label> <input type="email" id="doc-vld-email-2" value="${updateUser.email }" name="updateUser.email" placeholder="输入邮箱" required />
							</div>

							<button class="am-btn am-btn-secondary" type="submit">保存修改</button>
						</fieldset>
					</form>
				</div>
				<!-- content end -->

			</div>

		</div>
	</div>
	<a class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

	<footer>
		<hr>
		<jsp:include page="footer.jsp" />
	</footer>
	<script type="text/javascript">
		
	</script>
</body>
</html>
