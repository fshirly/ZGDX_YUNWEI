function isNull(arg1) {
	return !arg1 && arg1 !== 0 && typeof arg1 !== "boolean" ? true : false;
}

function reset(){  
	$("#startIP1").val(""); 
	$("#startIP2").val(""); 
	$("#startIP3").val(""); 
	$("#endIP1").val(""); 
}

function saveInfo() {
	var way = $("input[name='selectDiscover']:checked").val();  
	var startIP1 = $("#startIP1").val();
	var endIP1 = $("#endIP1").val();

	var startIP2 = $("#startIP2").val();
	var netMask2 = $("#netMask2").val();

	var startIP3 = $("#startIP3").val();
	var step3 = $("#step3").val();
	if (way == 1) {
		if (startIP1 == null || startIP1.length <= 0 || !isIP(startIP1)) {  
			$.messager.alert("提示","请输入有效的起始IP!","error"); 
			$("#startIP1").focus();
			return;
		}
		
		if (endIP1.length <= 0 || !isIP(endIP1)) {  
			$.messager.alert("提示","请输入有效的结束IP!","error");
			$("#endIP1").focus();
			return;
		}
		
		if (judge_ip(startIP1) > judge_ip(endIP1)) {
			$.messager.alert("提示","请输入有效的IP地址范围!","error"); 
			$("#endIP1").focus();
			return;
		}
	}

	if (way == 2) {
		if (startIP2.length <= 0 || !isIP(startIP2)) { 
			$.messager.alert("提示","请输入有效的起始IP!","error"); 
			$("#startIP2").focus();
			return;
		}
	}

	if (way == 3) {
		if (startIP3.length <= 0 || !isIP(startIP3)) { 
			$.messager.alert("提示","请输入有效的起始IP!","error"); 
			$("#startIP3").focus();
			return;
		}
		if (!checkNum(step3)) { 
			$.messager.alert("提示","请输入有效的发现跳数(1~15)","error"); 
			$("#step3").val('');
			$("#step3").focus(); 
			return;
		}
	}
	
	var uri = getRootName() + "/monitor/discover/saveDiscoverParam";
	var params = "way=" + way + "&startIP1=" + startIP1 + "&endIP1=" + endIP1
			+ "&startIP2=" + startIP2 + "&netMask2=" + netMask2 + "&startIP3="
			+ startIP3 + "&step3=" + step3; 
	/*
	$('#formname').form('submit', {
		url: uri,
		onSubmit: function(){
			var isValid = $(this).form('validate');
			if (!isValid){
				$.messager.progress('close');
			} 
		},
		error : function() {
			$.messager.alert("错误", "TaskDisPatch服务未启动!", "error"); 
		},
		success : function() { 
			alert("11");
			window.location.href = getRootName()+'/monitor/discover/startDiscover?taskID=';
		}
	});
	*/
	$.ajax({
		url : uri, 
		type : "post",
		dateType : "json",
		data : params,
		onSubmit: function(){ 
			var isValid = $(this).form('validate');
			if (!isValid){
				$.messager.progress('close');	// hide progress bar while the form is invalid
			}
			return isValid;	// return false will stop the form submission
		},
		error : function(data) { 
		},
		success : function(data) {
			if(data.errorInfo!=null && data.errorInfo!=''){
				$.messager.alert("错误", "发现任务保存失败,请联系管理员!", "error"); 
			} else {
				window.location.href = getRootName()+'/monitor/discover/startDiscover?taskID=' + data.taskID+'&way='+way+"&navigationBar="+$("#navigationBar").val();
			}
		}
	}); 
}

function selectDiscoverWay(way) {
	if (way == 1) {
		$("#byIPScpoe").show();
		$("#byStartIP").hide();
		$("#byRouteStep").hide();
	} else if (way == 2) {
		$("#byIPScpoe").hide();
		$("#byStartIP").show();
		$("#byRouteStep").hide();
	} else {
		$("#byIPScpoe").hide();
		$("#byStartIP").hide();
		$("#byRouteStep").show();
	}
}

/**
 * 判断是否为合法IP
 * @param strIP
 * @return
 */
function isIP(strIP) {
	if (isNull(strIP)) {
		return false;
	}
	var re = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g //匹配IP地址的正则表达式
	if (re.test(strIP)) {
		if (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256
				&& RegExp.$4 < 256)
			return true;
	}
	return false;
}

/**
 * 判断是否为数字
 * @param strIP
 * @return
 */
function checkNum(step) {
	if(step == null || step ==''){
		return false;
	}
	var re = /^[0-9]+.?[0-9]*$/; // 判断字符串是否为数字 
	if (!re.test(step) || step > 15) {
		return false;
	} 
	return true; 
}

/**
 * IP地址转换为整数
 */
function judge_ip(ip){
	var num = 0;  
    ip = ip.split(".");  
    num = Number(ip[0]) * 256 * 256 * 256 
    	+ Number(ip[1]) * 256 * 256 + Number(ip[2]) * 256 + Number(ip[3]);  
    num = num >>> 0;  
    return num;
}