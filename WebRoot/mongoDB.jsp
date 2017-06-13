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
		<div class="admin-sidebar am-offcanvas" id="admin-offcanvas" style="height: 800px">
			<jsp:include page="menu.jsp" />
		</div>

		<!-- content start -->
		<div class="admin-content">
			<div class="am-cf am-padding">
				<div class="am-fl am-cf">
					<strong class="am-text-primary am-text-lg">数据库</strong> / <small>Table</small>
				</div>
			</div>
			<div class="am-g am-padding">

				<div class="am-u-sm-12 am-u-md-6">
					<div class="am-input-group">
						<span class="am-input-group-label">服务器</span> <select id="connectId" name="connect.connectId"  data-am-selected="{searchBox: 1}">
							<optgroup label="　　">
								<option value="0" selected="selected">请选择</option>
							</optgroup>
							<c:forEach var="t" items="${connectTypes }">
								<optgroup label="${t.typeName }">
									<c:forEach var="c" items="${connects }">
										<c:if test="${t.id==c.connectTypeId }">
											<option ${connect.connectId==c.connectId?"selected='selected'":"" } value="${c.connectId }">${c.connectName } - ${c.connectIp }</option>
										</c:if>
									</c:forEach>
								</optgroup>
							</c:forEach>
						</select>

					</div>

				</div>

				<div class="am-u-sm-12 am-u-md-6">
					<div class="am-input-group">
						<form id="dbInfoForm" action="mongo_tbList.action?" method="post">
							<input id="connectIdHidden" type="hidden" value="${connect.connectId }" name="connect.connectId" />
							<div class="am-input-group" style="width: 500px">
								<span class="am-input-group-label">数据库</span> <select id="dbName" class="am-form-field" name="nowDB.name" data-am-selected="{maxHeight: 500}" data-am-selected>
									<option value="0" selected="selected">请选择数据库</option>

								</select>
								<!-- 
							<span class="am-input-group-btn">
								<button class="am-btn am-btn-default" type="submit">&nbsp;&nbsp;&nbsp;切换数据库&nbsp;&nbsp;&nbsp;</button>
							</span>
							 -->
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="am-g">
				<div class="am-u-sm-12" style="min-height: 700px">

					<br /> <br />
					<div class="am-btn-group am-btn-group-xs">
						<button type="button" onclick="exportTbs('${nowDB.name}','${connect.connectId}')" class="am-btn am-btn-default">
							<span class="am-icon-upload"></span> 导出
						</button>
						<button type="button" class="am-btn am-btn-default" onclick="dbIsNull()">
							<span class="am-icon-download"></span> 导入新的集合中
						</button>
						<div align="left" class="am-modal am-modal-no-btn" tabindex="-1" id="addImportTb">
							<div class="am-modal-dialog">
								<div class="am-modal-hd">
									新建集合并导入数据 <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
								</div>
								<div class="am-modal-bd">
									<form action="mongo_importTb.action" id="newTbForm" method="post" enctype="multipart/form-data" class="am-form">
										<fieldset>
											<input type="hidden" name="nowDB.name" value="${nowDB.name}" /> <input type="hidden" value="${connect.connectId }" name="connect.connectId" />

											<div class="am-form-group">
												<label for="doc-vld-name-2" style="float: left">集合名称：</label> <input type="text" id="doc-vld-name-2" name="importTb.name" placeholder="请输入集合名称" required />
											</div>

											<div class="am-form-group" align="left">
												<label for="doc-vld-name-2">集合文件：</label> <input type="file" id="doc-vld-name-2" name="importFile" class="importFile" required />
											</div>

											<button class="am-btn am-btn-secondary" onclick="openNewImportProgressModal()" type="button">导入</button>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<button class="am-btn am-btn-secondary" type="button" onclick="closeAddImportTb()">取消</button>
										</fieldset>
									</form>

								</div>
							</div>
						</div>


					</div>
					<table class="am-table am-table-striped am-table-hover table-main">
						<thead>
							<tr>
								<th class="table-check"><label class="am-checkbox am-secondary am-danger"> <input type="checkbox" id="allC" onclick="allCheckbox()" data-am-ucheck />
								</label></th>
								<th class="table-id">编号</th>
								<th class="table-userName">名称</th>
								<th class="table-set">数据量</th>
								<th class="table-set">操作</th>
							</tr>
						</thead>
						<tbody id="tbList">
							<c:forEach var="mtb" items="${tbList}" varStatus="s">
								<tr>
									<td><label class="am-checkbox am-secondary am-danger"> <input onclick="ck()" value="${mtb.name}" class="ck" type="checkbox" data-am-ucheck />
									</label></td>
									<td>${s.index+1}</td>
									<td>${mtb.name}</td>
									<td>${mtb.size}&nbsp;(条)</td>
									<td>
										<div class="am-btn-toolbar">
											<div class="am-btn-group am-btn-group-xs">
												<button type="button" class="am-btn am-btn-default am-btn-xs am-text-secondary" data-am-modal="{target: '#exportTb${s.index+1 }'}">
													<span class="am-icon-upload"></span> 导出
												</button>
												<button type="button" class="am-btn am-btn-default am-btn-xs am-text-secondary" data-am-modal="{target: '#importTb${s.index+1 }' ,closeViaDimmer: 0}">
													<span class="am-icon-download"></span> 导入
												</button>
											</div>
										</div>
										<div class="am-modal am-modal-confirm" tabindex="-1" id="exportTb${s.index+1 }">
											<div class="am-modal-dialog">
												<div class="am-modal-hd">系统提示</div>
												<div class="am-modal-bd">你，确定要导出这条记录吗？</div>
												<div class="am-modal-footer">
													<span class="am-modal-btn" data-am-modal-cancel>取消</span> 
													<span class="am-modal-btn" onclick="exportTb('${mtb.name}','${nowDB.name}','${connect.connectId}')" data-am-modal-confirm>确定</span>
												</div>
											</div>
										</div>
									</td>
								</tr>


							</c:forEach>



						</tbody>
					</table>

					<c:forEach var="mtb" items="${tbList}" varStatus="s">
						<div class="am-modal am-modal-no-btn" tabindex="-1" id="importTb${s.index+1 }">
							<div class="am-modal-dialog">
								<div class="am-modal-hd">
									导入数据 <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
								</div>
								<div class="am-modal-bd">
									<form action="mongo_importTb.action" method="post"  enctype="multipart/form-data" class="am-form">
										<fieldset>
											<input type="hidden" name="importTb.name" value="${mtb.name}" /> <input type="hidden" name="nowDB.name" value="${nowDB.name}" /> <input type="hidden" value="${connect.connectId }" name="connect.connectId" />

											<div class="am-form-group" align="left">
												<p style="margin: 0px">
													<label for="doc-vld-name-2">导入类型：</label>
												</p>
												<label class="am-radio-inline "> <input type="radio" name="importType" value="true" data-am-ucheck checked> 覆盖
												</label> <label class="am-radio-inline "> <input type="radio" name="importType" value="false" data-am-ucheck> 添加
												</label>
											</div>

											<div class="am-form-group" align="left">
												<label for="doc-vld-name-2">集合文件：</label> <input type="file" id="doc-vld-name-2" name="importFile" class="addDep_depName" required />
											</div>

											<button onclick="openImportProgressModal(${s.index+1 })" class="am-btn am-btn-secondary" type="button">导入</button>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<button class="am-btn am-btn-secondary" type="button" onclick="closeImportTb(${s.index+1 })">取消</button>
										</fieldset>
									</form>
								</div>
							</div>
						</div>
					</c:forEach>
					<div class="am-cf">
						共 <span id="count">${count }</span> 条记录
						<div class="am-fr">
							<ul id="page" class="am-pagination">
								<li class="${page<=1?'am-disabled':'' }"><a href="mongo_tbList.action?page=${page-1 }&connect.connectId=${connect.connectId}&nowDB.name=${nowDB.name}">上一页</a></li>

								<c:forEach begin="1" end="${countPage }" varStatus="status">
									<li class=" ${status.index==page?'am-active':'' } "><a href="mongo_tbList.action?page=${status.index }&connect.connectId=${connect.connectId}&nowDB.name=${nowDB.name}">${ status.index}</a></li>
								</c:forEach>
								<li class="${page>=countPage?'am-disabled':'' }"><a href="mongo_tbList.action?page=${page+1 }&connect.connectId=${connect.connectId}&nowDB.name=${nowDB.name}">下一页</a></li>
							</ul>
						</div>
					</div>
					<hr />
					<p>注：</p>
					<br /> <br />
				</div>

			</div>
		</div>

		<!-- content end -->

	</div>
	<div class="am-modal am-modal-loading am-modal-no-btn" tabindex="-1" id="lianjie">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">数据库连接中...</div>
			<div class="am-modal-bd">
				<i class="am-icon-spinner am-icon-pulse"></i>
			</div>
		</div>
	</div>
	<div class="am-modal am-modal-no-btn" tabindex="-1" id="progressModal">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">
				<p id="progressModalTitle">name</p><a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
			</div>
			<div class="am-modal-bd">
				<div class="am-progress am-progress-striped  am-active ">
					<div class="am-progress-bar am-progress-bar-secondary" id="progress" style="width: 0%"></div>
				</div>
			</div>
		</div>
	</div>
	<div class="am-modal am-modal-no-btn" tabindex="-1" id="importProgressModal">
		<div class="am-modal-dialog">
			<div class="am-modal-hd">
				<a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
			</div>
			<div class="am-modal-bd">
			<div class="am-modal-hd">导入中...</div>
			<div class="am-modal-bd">
				<i class="am-icon-spinner am-icon-pulse"></i>
			</div>
			</div>
		</div>
	</div>
	<a class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

	<footer>
		<hr>
		<jsp:include page="footer.jsp" />
	</footer>
	<script type="text/javascript">
	//取消 "新建并导入" 操作
	function closeAddImportTb(){
		$("#addImportTb").modal('close');
	}
	//取消导入操作
	function closeImportTb(tbid){
		$("#importTb"+tbid).modal('close');
	}
	//绑定默认事件
	$(function(){
		var $model = $("#lianjie");
		//连接A改变时
		$("#connectId").change(function() {
			
			var connectId = $("#connectId").val();//准备提交的连接id
			$("#connectIdHidden").attr("value",connectId);
			$model.modal('open');
			$.ajax({
				type : "POST",
				url : "mongo_dbList.action",
				data : {
					"connect.connectId" : connectId
				},
				success : function(data) {
					$model.modal('close');
					$("connectIdHidden").attr("value",connectId);
					$("#dbName").empty();//清空下拉框选项。重新赋值
					var str = "<option value='0' selected='selected'>请选择数据库</option>";
					if(data==null){
						$("#tbList").empty();//清空页面列表信息
						$("#count").text("0");//改变总页数
						$("#page").empty();//改变分页信息
						$("#page").append("<li class='am-disabled'><a href='#'>上一页</a></li>"
					+"<li class='am-disabled'><a href='#'>下一页</a></li>");
						isDBSubmit=false;
						alert("连接失败！");
					}else{
						for (var i = 0; i < data.length; i++) {
							if("${nowDB.name}"== data[i].name&&"${nowDB.connect.connectId}"==connectId){
								str += "<option selected='selected' value='"+data[i].name+"' >" + data[i].name + "</option>";									
							}else{
								str+="<option value='"+data[i].name+"' >" + data[i].name + "</option>";
							};
						};
					}
					$("#dbName").append(str);
					//alert("提交id:"+connectId+"，当前:id${connect.connectId}")
					if("${connect.connectId}"==connectId){//判断提交id和当前id是否一样 
						isDBSubmit=false;//重新设置数据库列表内容会导致数据库俩表执行onchange事件。设置isDBSubmit属性为false，可以阻止数据库列表的下次onchange事件执行
					}
				}
			
			});
		});
		
		var isDBSubmit = false;//表示第一次不提交（数据库信息）
		$("#dbName").change(function(){
			//alert("数据库名字改变了");
			if(!isDBSubmit){//如果第一次进入页面。停止提交表单，并且改变状态，表示下次onchange已经不是第一次进入页面，可以提交
				isDBSubmit=true;
				return;
			}
			//isConnectSubmit=false;
			//alert("提交");
			isDBSubmit=false;//开始提交之前把状态改成0，表示准备刷新页面。下次onchange不需要提交了
			var dbName=$("#dbName").val();
			//alert(dbName);
			if(dbName == "0"){//如果未选择连接，清楚数据库列表信息
				//$("#dbName").empty();
				//return;
			}
			$('#dbInfoForm').submit();
		});
	})
	function dbIsNull(){
		var dbName=$("#dbName").val();
		if(dbName=="0"){
			alert("请选择要导入的数据库");
			return false;
		}else{

			$("#addImportTb").closeViaDimmer=false;
			$("#addImportTb").modal('open');
			return true;
		};
	}
	var prog;
	//显示导出进度
	function showProgress(){
		$("#progress").text("");
		$("#progressModalTitle").text("");
		$("#progress").attr("style","width:0%");
		$("#progressModal").modal('open');
		 prog=window.setInterval(function(){
			$.ajax({
				type : "POST",
				url : "mongo_getProgressJson.action",
				success : function(data) {
					if(data==null){
						$("#progress").attr("style","width:100%");
						$("#progressModal").modal('close');
						clearInterval(prog);
					}else{//更新进度信息
						$("#progress").text(data.progresPercentage+"%");
						$("#progressModalTitle").text(data.name);
						$("#progress").attr("style","width:"+data.progresPercentage+"%");
					}
				}
			});
		},1000);
	}
	//导出一个集合
	function exportTb(mtbName,dbName,connectId){
		//'${mtb.name}','${nowDB.name}','${connect.connectId}'
		location.href="mongo_export.action?exportTbName="+encodeURIComponent(mtbName)+"&nowDB.name="+encodeURIComponent(dbName)+"&connect.connectId="+connectId;
		showProgress();
	}
	//打开导入加载窗口（导入新表）
	function openNewImportProgressModal(){
		$("#importProgressModal").modal('open');
		$("#addImportTb").modal('close');
		$("#newTbForm").submit();
	}
	//打开导入加载窗口(导入指定表)
	function openImportProgressModal(modalName){
		$("#importTb"+modalName).modal('close');
		$("#importProgressModal").modal('open');
		$("#importTb"+modalName).find("form").submit();
	}
	//导出多个集合
	function exportTbs(dbName,connectId){
		var attachmentPath="";
		var tbName=$(".ck:checked");
		if(tbName.length<=0){
			alert("请选择要导出的集合！");
			return;
		}
		if(confirm("确定导出选中的"+tbName.length+"个集合？")){//如果是true ，那么就导出
			for (var i = 0; i < tbName.length; i++) {
				attachmentPath+=$(tbName[i]).val();
				if(i<tbName.length-1){
					attachmentPath+=",";
				};
			}
			location.href="mongo_downloadMultiFile.action?attachmentPath="+encodeURIComponent(attachmentPath)+"&nowDB.name="+encodeURIComponent(dbName)+"&connect.connectId="+connectId;
			
			showProgress();
		};
	};
</script>
</body>
</html>
