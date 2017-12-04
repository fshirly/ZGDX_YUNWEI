$(document).ready(function() {
	if ($("#ipt_middleWareName").val() == 2) {
		$("#isShowUserAndPwd").hide();
		$("#ipt_middleWareType").val("tomcat");
		$("#ipt_port").val("8999");
	} else if ($("#ipt_middleWareName").val() == 3) {
		$("#isShowUserAndPwd").hide();
		$("#ipt_middleWareType").val("websphere");
		$("#ipt_port").val("8880");
	} else if ($("#ipt_middleWareName").val() == 1) {
		$("#isShowUserAndPwd").show();
		$("#ipt_middleWareType").val("weblogic");
		$("#ipt_port").val("7001");
	} 
});

function ipChange(){
	var ipAddress = $("#ipt_deviceIp").val();
	var port = $("#ipt_port").val();
	if(ipAddress!=null && ipAddress!=""){
		if ($("#ipt_middleWareName").val() == 2) {
			$("#isShowUserAndPwd").hide();
			$("#ipt_url").val("http://"+ipAddress+":"+"8080");
		} else if ($("#ipt_middleWareName").val() == 3) {
			$("#isShowUserAndPwd").hide();
			$("#ipt_url").val("http://"+ipAddress+":"+"9060"+"/ibm/console/unsecureLogon.jsp");
		} else if ($("#ipt_middleWareName").val() == 1) {
			$("#isShowUserAndPwd").show();
			$("#ipt_url").val("http://"+ipAddress+":"+"7001"+"/console/login/LoginForm.jsp");
		}
	}else{
		$("#ipt_url").val("");
	}
}

function resetType(){
	var ipAddress = $("#ipt_deviceIp").val();
	if ($("#ipt_middleWareName").val() == 2) {
		$("#isShowUserAndPwd").hide();
		$("#ipt_middleWareType").val("tomcat");
		$("#ipt_port").val("8999");
		if(ipAddress!=null && ipAddress!=""){
			$("#ipt_url").val("http://"+ipAddress+":8080");
		}
	} else if ($("#ipt_middleWareName").val() == 3) {
		$("#isShowUserAndPwd").hide();
		$("#ipt_middleWareType").val("websphere");
		$("#ipt_port").val("8880");
		if(ipAddress!=null && ipAddress!=""){
			$("#ipt_url").val("http://"+ipAddress+":9060/ibm/console/unsecureLogon.jsp");
		}
	} else if ($("#ipt_middleWareName").val() == 1) {
		$("#isShowUserAndPwd").show();
		$("#ipt_middleWareType").val("weblogic");
		$("#ipt_port").val("7001");
		if(ipAddress!=null && ipAddress!=""){
			$("#ipt_url").val("http://"+ipAddress+":"+"7001"+"/console/login/LoginForm.jsp");
		}
	} 
}

function portChange(){
	var ipAddress = $("#ipt_deviceIp").val();
	var port = $("#ipt_port").val(); 
	if(ipAddress != null && ipAddress !=""){
		if ($("#ipt_middleWareName").val() == 2) {
			$("#isShowUserAndPwd").hide();
			$("#ipt_middleWareType").val("tomcat");
			$("#ipt_url").val("http://"+ipAddress+":"+"8080");
		} else if ($("#ipt_middleWareName").val() == 3) {
			$("#isShowUserAndPwd").hide();
			$("#ipt_middleWareType").val("websphere");
			$("#ipt_url").val("http://"+ipAddress+":"+"9060"+"/ibm/console/unsecureLogon.jsp");
		} else if ($("#ipt_middleWareName").val() == 1) {
			$("#isShowUserAndPwd").show();
			$("#ipt_middleWareType").val("weblogic");
			$("#ipt_url").val("http://"+ipAddress+":"+"7001"+"/console/login/LoginForm.jsp");
		} 
	}
}

function toAdd(){
	var result = checkInfo("#tblMiddlewareAdd");
	if(result == true){
		var checkIPRS = checkIP();
		if(checkIPRS==true){
			checkCommunity();
		}
	}
}

/*
 * 验证IP格式
 */
function checkIP() {
	var deviceIP = $("#ipt_deviceIp").val();
	if (!(/^(\*|(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*)\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*)\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*))$/
			.test(deviceIP))) {
		$.messager.alert("提示", "ip地址错误，请填写正确的ip地址！", "info", function(e) {
			$("#ipt_deviceIP").focus();
		});
		return false;
	}
	var port =null;
	var message = "";
		port = $("#ipt_port").val();
		message = "端口只能输入正整数！";
	if (!(/^[0-9]*[1-9][0-9]*$/.test(port))) {
		$.messager.alert("提示", message, "info", function(e) {
			$("#ipt_port").focus();
		});
		return false;
	}
	return true;
}



/**
 * 验证中间件凭证是否存在
 * @return
 */
function checkCommunity(){
	var deviceIP = $("#ipt_deviceIp").val();
	var middleWareName = $("#ipt_middleWareName").val();
	var path = getRootName();
	var uri = path + "/monitor/MiddleWareCommunity/checkCommunity?flag=add";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"ipAddress" : deviceIP,
			"middleWareName" : middleWareName,
			"port" : $("#ipt_port").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (false == data || "false" == data) {
				$.messager.alert("提示", "该中间件凭证已存在！", "error");
			} else {
				addCommunity();
				return;
			}
		}
	};
	ajax_(ajax_param);
}

/**
 * 新增
 * @return
 */
function addCommunity(){
	var middleWareType = $("#ipt_middleWareType").val();
	if(middleWareType == "weblogic"){
		var userName = $("#ipt_userName").val();
		var passWord = $("#ipt_passWord").val();
	}else{
		var userName = "";
		var passWord = "";
	}
	var ipAddress = $("#ipt_deviceIp").val();
	var port = $("#ipt_port").val();
	var middleWareName = $("#ipt_middleWareType").val();
	var url = $("#ipt_url").val();
	var path = getRootName();
	var uri = path + "/monitor/MiddleWareCommunity/addCommunity";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"ipAddress" :$("#ipt_deviceIp").val(),
			"middleWareName" : $("#ipt_middleWareName").val(),
			"middleWareType" :middleWareType,
			"port" : $("#ipt_port").val(),
			"userName" : userName,
			"passWord" : passWord,
			"domainID" : $("#ipt_domainID").val(),
			"url" : $("#ipt_url").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				$.messager.alert("提示", "中间件凭证添加成功！", "info");
				$('#popWin').window('close');
				window.frames['component_2'].reloadTable();
			} else {
				$.messager.alert("提示", "中间件凭证添加失败！", "error");
			}

		}
	};
	ajax_(ajax_param);
}