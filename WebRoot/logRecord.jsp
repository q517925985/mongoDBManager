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
					<strong class="am-text-primary am-text-lg">日志记录</strong> / <small>Table</small>
				</div>

				<div class="am-u-12 am-margin-top-lg">
					<form action="logRecord_list.action?page=1" method="post">
						<div class="am-u-md-4">
							<div class="am-input-group am-input-group-sm">
								<span class="am-input-group-label">开始时间</span> <input type="text" name="begin" class="am-form-field" placeholder="选择日期" data-am-datepicker readonly />
							</div>
						</div>
						<div class="am-u-md-4">
							<div class="am-input-group am-input-group-sm">
								<span class="am-input-group-label">结束时间</span> <input type="text" name="end" class="am-form-field" placeholder="选择日期" data-am-datepicker readonly />
							</div>
						</div>
						<div class="am-u-md-4">
							<div class="am-input-group am-input-group-sm">
								<input type="text" name="name" class="am-form-field" placeholder="请输入姓名"> <span class="am-input-group-btn">
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
									<th>操作人</th>
									<th>IP地址</th>
									<th>数据库</th>
									<th>表</th>
									<th>操作类型</th>
									<th>操作时间</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="log" items="${list}" varStatus="">

									<tr>
										<td>${log.id}</td>
										<td>${log.nickname}</td>
										<td>${log.connectIp}</td>
										<td>${log.datebaseName}</td>
										<td>${log.tableName}</td>
										<td>${log.operationType?"导出":"导入"}</td>
										<td>${log.operationTime}</td>
										<td><c:if test="${log.remarks!=''&&log.remarks!='1'}">
												<button type="button" class="am-btn am-btn-default am-btn-xs am-text-secondary" data-am-modal="{target: '#lifted${log.id}'}">
													<span class="am-icon-mail-reply-all"></span> 撤销
												</button>
											</c:if> <c:if test="${log.remarks=='1'}">
											已撤销
										</c:if> <c:if test="${log.remarks==''}">
											无
										</c:if>
											<div class="am-modal am-modal-confirm" tabindex="-1" id="lifted${log.id}">
												<div class="am-modal-dialog">
													<div class="am-modal-hd">系统提示</div>
													<div class="am-modal-bd">你，确定要撤销这条记录吗？</div>
													<div class="am-modal-footer">
														<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span class="am-modal-btn" onclick="lifted(${log.id})" data-am-modal-confirm>确定</span>
													</div>
												</div>
											</div></td>
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

		<c:forEach var="log" items="${list}" varStatus="">
			<form action="logRecord_lifted.action" method="post" id="liftedFrom${log.id}">
				<input type="hidden" value="${log.id}" name="logRecord.id" /> <input type="hidden" value="${log.connectId}" name="logRecord.connectId" /> <input type="hidden" value="${log.datebaseName}" name="logRecord.datebaseName" /> <input
					type="hidden" value="${log.tableName}" name="logRecord.tableName" /> <input type="hidden" value="${log.remarks}" name="logRecord.remarks" />
			</form>
		</c:forEach>
		<!-- content end -->

	</div>

	<a class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

	<footer>
		<hr>
		<jsp:include page="footer.jsp" />
	</footer>


	<div class="am-modal am-modal-no-btn" tabindex="-1" id="syncProgressModal">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">
				<a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
			</div>
			<div class="am-modal-bd">
				<div class="am-modal-hd">撤销中...</div>
				<div class="am-modal-bd">
					<i class="am-icon-spinner am-icon-pulse"></i>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">

		//撤销一条日志记录
		function lifted(id) {
			$("#syncProgressModal").modal("open");
			$('#liftedFrom'+id).submit();
		}
	</script>

	<script>
  $(function() {
    var startDate = new Date(2014, 11, 20);
    var endDate = new Date(2014, 11, 25);
    var $alert = $('#my-alert');
    $('#my-start').datepicker().
      on('changeDate.datepicker.amui', function(event) {
        if (event.date.valueOf() > endDate.valueOf()) {
          $alert.find('p').text('开始日期应小于结束日期！').end().show();
        } else {
          $alert.hide();
          startDate = new Date(event.date);
          $('#my-startDate').text($('#my-start').data('date'));
        }
        $(this).datepicker('close');
      });

    $('#my-end').datepicker().
      on('changeDate.datepicker.amui', function(event) {
        if (event.date.valueOf() < startDate.valueOf()) {
          $alert.find('p').text('结束日期应大于开始日期！').end().show();
        } else {
          $alert.hide();
          endDate = new Date(event.date);
          $('#my-endDate').text($('#my-end').data('date'));
        }
        $(this).datepicker('close');
      });
  });
</script>
</body>
</html>
