<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="/struts-tags" prefix="s"%>

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
		<div class="admin-sidebar am-offcanvas" id="admin-offcanvas" style="height: 950px">
			<jsp:include page="menu.jsp" />
		</div>

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">将数据库A中的数据同步到数据库B中</strong>
				</div>
			</div>
			<hr />
			<form id="syncForm" action="mongoSync_sync.action" onsubmit="openSyncProgressModal()" method="post">
				<div class="am-u-md-7">
					<div class="am-panel am-panel-default" style="height: 610px">
						<div class="am-panel-hd am-cf">数据库A</div>
						<div class="am-u-md-5">
							<div class="am-panel-bd am-collapse am-in" id="collapse-panel-1">

								<div class="am-input-group">
									<span class="am-input-group-label">服务器</span> <select id="connectAId" name="connectA.connectId" data-am-selected="{btnSize: 'xm',maxHeight: 500}">
										<option value="0">请选择IP地址</option>
										<c:forEach var="item" items="${connects }">
											<option ${connectA.connectId==item.connectId?"selected='selected'":"" } value="${item.connectId }">${item.connectName }- ${item.connectIp }</option>
										</c:forEach>
									</select>
								</div>
								<br>
								<div class="am-input-group">
									<span class="am-input-group-label">数据库</span> <select id="dbNameA" class="am-form-field" name="nowDBA.name" data-am-selected="{maxHeight: 500}" data-am-selected>
										<option value="0" selected="selected">请选择数据库</option>
									</select>
								</div>
								<br>
								<!-- 
								<div class="am-input-group">
									<span class="am-input-group-label">集&nbsp;&nbsp;&nbsp;合</span> <select onchange="ckSubmit()" id="tbNameA" name="nowTb.name" multiple data-am-selected="{searchBox: 1}">
										<option value="0" selected="selected">请选择集合</option>
									</select>
								</div> 
								-->
							</div>
						</div>

						<div class="am-u-md-7 am-scrollable-vertical" style="height: 550px; border: 1px solid #ccc; margin-top: 15px; padding: 15px">
							<input type="text" class="am-form-field" id="searchInput" oninput="search()" placeholder="输入集合名称搜索" />
							<table class="am-table am-table-bordered">
								<thead>
									<tr style="height: 20px;">
										<th class="table-check"><label class="am-checkbox am-secondary am-danger"> <input type="checkbox" id="allC" onclick="allCheckbox()" data-am-ucheck />
										</label></th>
										<th width="600">集合名称</th>
										<th width="150">数据量</th>
									</tr>
								</thead>
								<tbody id="tbListA">
								</tbody>
							</table>
						</div>

					</div>

				</div>
				<div class="am-u-md-5">
					<div class="am-panel am-panel-default" style="height: 610px">
						<div class="am-panel-hd am-cf">数据库B</div>
						<div id="collapse-panel-4" class="am-panel-bd am-collapse am-in">

							<div class="am-input-group">
								<span class="am-input-group-label">服务器</span> <select id="connectBId" name="connectB.connectId" data-am-selected="{btnSize: 'xm',maxHeight: 500}">
									<option value="0">请选择IP地址</option>
									<c:forEach var="item" items="${connects }">
										<option ${connectB.connectId==item.connectId?"selected='selected'":"" } value="${item.connectId }">${item.connectName }- ${item.connectIp }</option>
									</c:forEach>
								</select>

							</div>
							<br>
							<div class="am-g">
								<div class="am-input-group ">
									<span class="am-input-group-label">数据库</span> <select id="dbNameB" class="am-form-field" name="nowDBB.name" data-am-selected="{maxHeight: 500}" data-am-selected>
										<option value="0" selected="selected">请选择数据库</option>
									</select>

								</div>
							</div>
							<br>
							<div class="am-input-group">
									<span class="am-input-group-label">新创建</span> <input type="text" style="width:200px" oninput="ckSubmit()" id="addDBInput" disabled="disabled" class="am-form-field" placeholder="数据库名称">
								

							</div>
							<!-- 
							<div class="am-input-group">
								<span class="am-input-group-label">集&nbsp;&nbsp;&nbsp;合</span> <select onchange="ckSubmit()" id="tbNameB" class="am-form-field" name="nowDB.name" data-am-selected="{maxHeight: 160}" data-am-selected>
									<option value="0" selected="selected">请选择集合</option>
								</select>
							</div>
 							-->
						</div>
					</div>
				</div>
				<hr />


				<div class="am-cf" align="center" style="height:150px; clear: both ;line-height: 35px">
					如果集合名称一样，是否覆盖： <label class="am-radio-inline"> <input type="radio" id="radioImportType1" name="importType" value="true" checked="checked" data-am-ucheck> 是
					</label> <label class="am-radio-inline"> <input type="radio" name="importType" value="false" data-am-ucheck> 否
					</label> <br />  
					是否添加本次操作到任务列表： <label class="am-radio-inline"> <input type="radio" class="addTask"  name="addTask" value="true" data-am-ucheck> 是
					</label> <label class="am-radio-inline"> <input type="radio" name="addTask" class="addTask" value="false" checked="checked" data-am-ucheck> 否
					</label> <br />
					<div style="width: 350px;height: px;text-align: center;padding:0px;margin: 0px; ">
						<span style="float:left">任务描述：</span><input style="width:250px;height: 25px;margin: 0px " type="text" name="taskName" disabled value="" class="am-form-field taskName"   >
					</div><br/>
					
					<button type="button"  onclick="submitForm()" id="syncBtn" class="am-btn am-btn-default" disabled="disabled">&nbsp;&nbsp;&nbsp;同步&nbsp;&nbsp;&nbsp;</button>
				</div>
				<input type="hidden" name="taskName=" value="" />
			</form>
			<hr />
		</div>
		<!-- content end -->

	</div>
	<a class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

	<footer>
		<hr>
		<jsp:include page="footer.jsp" />
	</footer>

	<div class="am-modal am-modal-loading am-modal-no-btn" tabindex="-1" id="lianjie">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">数据库连接中...</div>
			<div class="am-modal-bd">
				<i class="am-icon-spinner am-icon-pulse"></i>
			</div>
		</div>
	</div>

	<div class="am-modal am-modal-confirm" id="importType1" tabindex="-1" id="my-confirm">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">系統提示</div>
			<div class="am-modal-bd">你，确定要删除重名集合的原数据吗？</div>
			<div class="am-modal-footer">
				<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span class="am-modal-btn" onclick="submitSync()" data-am-modal-confirm>确定</span>
			</div>
		</div>
	</div>
	<div class="am-modal am-modal-confirm" id="importType2" tabindex="-1" id="my-confirm">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">系統提示</div>
			<div class="am-modal-bd">你，确定要保留重名集合的原数据吗？</div>
			<div class="am-modal-footer">
				<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span class="am-modal-btn" onclick="submitSync()" data-am-modal-confirm>确定</span>
			</div>
		</div>
	</div>

	<div class="am-modal am-modal-no-btn" tabindex="-1" id="syncProgressModal">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">
				<a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
			</div>
			<div class="am-modal-bd">
				<div class="am-modal-hd">同步中...</div>
				<div class="am-modal-bd">
					<i class="am-icon-spinner am-icon-pulse"></i>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
	//绑定默认事件
	$(function() {
		var $model = $("#lianjie");
		//连接A改变时
		$("#connectAId").change(function() {
			var connectAId = $("#connectAId").val();//连接id
			$model.modal('open');
			$.ajax({
				type : "POST",
				url : "mongoSync_dbListA.action",
				data : {
					"connectA.connectId" : connectAId
				},
				success : function(data) {
					$model.modal('close');
					$("#dbNameA").empty();//清空下拉框选项。重新赋值

					var str = "<option value='0'  selected='selected'>请选择数据库</option>";
					if (data == null) {
						alert("连接失败");
					} else {
						for (var i = 0; i < data.length; i++) {
							str += "<option value='"+data[i].name+"' >" + data[i].name + "</option>";
							//alert(data[i].name);
						}
					}
					$("#dbNameA").append(str);
				}

			});
		});

		//数据库A改变时
		$("#dbNameA").change(
				function() {
					var connectAId = $("#connectAId").val();//连接id
					var dbNameA = $("#dbNameA").val();//连接id
					$.ajax({
						type : "POST",
						url : "mongoSync_tbListA.action",
						data : {
							"connectA.connectId" : connectAId,
							"nowDBA.name" : dbNameA
						},
						success : function(data) {
							$("#tbListA").empty();//清空下拉框选项。重新赋值
							//$("#tbNameA").empty();//清空下拉框选项。重新赋值
							//var str = "<option value='0'  >请选择集合</option>";
							var str = "";
							for (var i = 0; i < data.length; i++) {
								//str += "<option value='"+data[i].name+"' >" + data[i].name + "</option>";
								str += "<tr>" + "<td><label class='am-checkbox am-secondary am-danger'> <input onchange='ckSubmit()' value='" + data[i].name + "' name = 'tbName' class='ck' type='checkbox' data-am-ucheck />"
										+ "</label></td>" + "<td class='tdName' >" + data[i].name + "</td>" + "<td width='150'>" + data[i].size + "</td>" + "</tr>";
							}
							$("#tbListA").append(str);
							$('#tbListA .ck').uCheck();
							$("#allC").uCheck('uncheck');
							//$("#tbNameA").append(str);
						}
					});
				});
		//连接B改变时
		$("#connectBId").change(function() {
			var connectBId = $("#connectBId").val();
			$model.modal('open');
			$.ajax({
				type : "POST",
				url : "mongoSync_dbListB.action",
				data : {
					"connectB.connectId" : connectBId
				},
				success : function(data) {
					$model.modal('close');
					if (data == null) {
						alert("连接失败");
						$("#addDBInput").attr("disabled", "disabled");
					} else if ((connectBId == '0')) {
						$("#addDBInput").attr("disabled", "disabled");
					} else {
						$("#addDBInput").removeAttr("disabled");
					}
					$("#dbNameB").empty();
					var str = "<option value='0'  selected='selected'>请选择数据库</option>";
					for (var i = 0; i < data.length; i++) {
						str += "<option value='"+data[i].name+"' >" + data[i].name + "</option>";
					}
					$("#dbNameB").append(str);
				}
			});
		});

		$("#dbNameB").change(function() {
			var connectBId = $("#connectBId").val();//当前选的的ip地址的id
			var dbNameB = $("#dbNameB").val();//当前选择的数据库id
			if (strIsNull(dbNameB) && !strIsNull(connectBId)) {
				$("#addDBInput").removeAttr("disabled");
			} else {
				$("#addDBInput").val("");
				$("#addDBInput").attr("disabled", "disabled");
			}
			ckSubmit();
		});
		$(".addTask").change(function(){
			var isAddTask=$(".addTask:checked").val();
			if(isAddTask=="true"){
				$(".taskName").removeAttr("disabled");
			}else{
				$(".taskName").val("");
				$(".taskName").attr("disabled", "disabled");
			}
		})
	});
	
		//确认同步打开 同步窗口
		function openSyncProgressModal() {
			
			$("#syncProgressModal").modal("open");
			
		}

		//同步的时候，根据是否全选来改变from 的action属性
		function submitSync() {
			
			
			if ($("#allC:checked").length == 0) {
				$("#syncForm").attr("action", "mongoSync_syncTb.action");
			} else {
				$("#syncForm").attr("action", "mongoSync_syncDb.action");
			}
			if ($("#addDBInput").val() != "") {
				$("#addDBInput").attr("name", "nowDBB.name");
				$("#dbNameB").attr("name", "");
			}
			$("#syncForm").submit();
		}

		/**
		//添加数据库
		function addDB() {
			dbName = $("#addDBInput").val().trim();

			if (dbName == "") {
				alert("请输入数据库名字");
				return;
			}
			var dbNameB = $("#dbNameB option");
			for (var i = 0; i < dbNameB.length; i++) {
				if ($(dbNameB[i]).val() == dbName) {
					alert("该数据库已存在,请不要重复添加！");
					return
				}
			}
			var str = "<option value='"+dbName+"'  selected='selected'>" + dbName + "</option>";
			$("#dbNameB").append(str);
		}*/

		//同步提醒
		function submitForm() {
			if($(".addTask:checked").val()=="true"&&$(".taskName").val()==""){
			alert("请填写任务描述！");
			
			return ;
			}
			if ($("#radioImportType1:checked").length == 1) {
				$("#importType1").modal('open');
			} else {
				$("#importType2").modal('open');
			}
		}
		

		//让contains不区分大小写
		jQuery.expr[':'].Contains = function(a, i, m) {
			return jQuery(a).text().toUpperCase().indexOf(m[3].toUpperCase()) >= 0;
		};

		// OVERWRITES old selecor    
		jQuery.expr[':'].contains = function(a, i, m) {
			return jQuery(a).text().toUpperCase().indexOf(m[3].toUpperCase()) >= 0;
		};
		//搜索
		function search() {
			var str = $("#searchInput").val();
			//alert(str);
			if (strIsNull(str)) {
				$('.tdName:contains("' + str + '")').parent().show();
				return;
			} else {
				$('.tdName').parent().hide();
				$('.tdName:contains("' + str + '")').parent().show();
			}
		}

		//判断是否可以导入
		function ckSubmit() {
			ck();
			var tbNameA = $(".ck:checked");
			var dbNameB = $("#dbNameB").val();
			var addDBInput = $("#addDBInput").val();
			if (tbNameA.length <= 0 || (strIsNull(dbNameB) && addDBInput == "")) {
				$("#syncBtn").attr("disabled", "disabled");
			} else {
				$("#syncBtn").removeAttr("disabled");
			}
		}
		//判断是否为空。如果是返回true
		function strIsNull(str) {
			if (str == null) {
				return true;
			} else if (str == "") {
				return true;
			} else if (str == "0") {
				return true;
			} else if (str == 0) {
				return true;
			} else {
				return false;
			}
		}
	</script>
</body>
</html>
