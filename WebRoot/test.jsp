<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>MongoDB数据库管理</title>
</head>
<body>
	<select id="select" onchange="test()">
		<option value="1">老大</option>
		<option value="2" selected="selected">老二</option>
		<option value="3">老三</option>
	</select>
	<input type="text" id="input" value=""/><br/>
	开始时间：<input type="text" id="kaishi" /><br/>
	结束时间：<input type="text" id="jieshu"/><br/>
	<input type="button" onclick="panduan()" value="判断">
	
</body>
<script type="text/javascript" src="assets/js/jquery.min.js"></script>
<script type="text/javascript">
	function test() {
		alert($("#input").attr("value"));
		alert("哈哈"+($("#input").attr("value")==null)+"哈哈"+($("#input").attr("value")==""));
		var text1 = $("#select").find("option:selected").text();
		alert(text1);
		$("#input").val($("#select").val());
	}
	function panduan(){
		var kaishi=$("#kaishi").val();
		var jieshu=$("#jieshu").val();
		checkInputDate(kaishi,jieshu);
	}
	
	function checkInputDate(inputStartMonth,inputEndMonth){ 
	//1. 是两个文本框都不能为空？ 
	if( inputStartMonth ==null  || inputStartMonth==""){ 
	alert("开始日期为空"); 
	return false; 
	} 
	if( inputEndMonth ==null  || inputEndMonth==""){ 
	alert("结束日期为空"); 
	return false; 
	} 

	//2. 是开始时间不能大于结束时间？ 
	var time = inputStartMonth;
	var strArray=time.split(" ");
	var strDate=strArray[0].split("-"); 
	var strTime=strArray[1].split(":"); 
	var time1=new Date(strDate[0],(strDate[1]-parseInt(1)),strDate[2],strTime[0],strTime[1],strTime[2]);

	var time = inputEndMonth;
	var strArray=time.split(" ");
	var strDate=strArray[0].split("-"); 
	var strTime=strArray[1].split(":"); 
	var time2=new Date(strDate[0],(strDate[1]-parseInt(1)),strDate[2],strTime[0],strTime[1],strTime[2]);

	if(time1>time2){
		alert("第一个时间大")
	}else{
		alert("第二个时间大")
	}
	}
</script>
</html>
