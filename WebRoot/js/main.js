//全选，取消
function allCheckbox(){
	if($("#allC:checked").length==0){
		$("#allC").uCheck('uncheck');//取消选中
		$(".ck").uCheck('uncheck');//取消选中
	}else{
		$("#allC").uCheck('check');//选中
		$(".ck").uCheck('check');//选中
	}
}
//单个取消,选中
function ck(){
	//alert("一共"+$(".ck").length+"个，选中"+ $(".ck:checked").length+"个.");
	if($(".ck").length!=0 && $(".ck").length==$(".ck:checked").length){
		$("#allC").uCheck('check');
	}else {
		$("#allC").uCheck('uncheck');
	}
}
function exit(){
	var number = Math.random();
	number = Math.ceil(number * 100); 
	location.href="users_exit.action?num="+ number;
	return false;
}