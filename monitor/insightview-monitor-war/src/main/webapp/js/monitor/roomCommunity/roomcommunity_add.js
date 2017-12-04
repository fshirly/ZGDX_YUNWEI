function toAdd() {
	var result = checkInfo("#tblRoomAdd");
	if (result == true) {
		var checkIPRS = checkIP();
		if (checkIPRS == true) {
			checkCommunity();
		}
	}
}

/*
 * 验证IP格式
 */
function checkIP() {
	var deviceIP = $("#ipt_ipAddress").val();
	if (!(/^(\*|(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*)\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*)\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*))$/
			.test(deviceIP))) {
		$.messager.alert("提示", "IP地址错误，请填写正确的IP地址！", "info", function(e) {
			$("#ipt_ipAddress").focus();
		});
		return false;
	}
	var port = null;
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

/*
 * 验证中间件凭证是否存在
 */
function checkCommunity() {
	var deviceIP = $("#ipt_ipAddress").val();
	var port = $("#ipt_port").val();
	var path = getRootName();
	var uri = path + "/monitor/roomCommunity/checkCommunity?flag=add";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"ipAddress" : deviceIP,
			"port" : port,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (false == data || "false" == data) {
				$.messager.alert("提示", "该动环系统凭证已存在！", "error");
			} else {
				addCommunity();
				return;
			}
		}
	};
	ajax_(ajax_param);
}

/*
 * 新增
 */
function addCommunity() {
	var path = getRootName();
	var uri = path + "/monitor/roomCommunity/addRoomCommunity";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"ipAddress" : $("#ipt_ipAddress").val(),
			"port" : $("#ipt_port").val(),
			"userName" : $("#ipt_userName").val(),
			"passWord" : $("#ipt_passWord").val(),
			"domainID" : $("#ipt_domainID").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				// $.messager.alert("提示", "动环系统凭证添加成功！", "info");
				$('#popWin').window('close');
				window.frames['component_2'].reloadTable();
			} else {
				$.messager.alert("提示", "动环系统凭证添加失败！", "error");
			}
			}
	};
	ajax_(ajax_param);
}