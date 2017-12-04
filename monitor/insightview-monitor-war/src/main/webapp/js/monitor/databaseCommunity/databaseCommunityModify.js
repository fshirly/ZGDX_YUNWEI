$(document).ready(function() {
	var dbmsType = $("#ipt_dbmsType").val();
	if(dbmsType == 'Mysql' || dbmsType == 'Sybase' || dbmsType == 'MsSql'){
		$("#dbNameForDb2").hide();
	}
	var docDBName = document.getElementById('ipt_dbName');
	if(dbmsType == 'DB2' ){
		docDBName.readOnly = true;
	}else{
		docDBName.readOnly = false;
	}
		
});

function toUpdate(){
	var dbmsType=$("#ipt_dbmsType").val();
	if(dbmsType == "Mysql"){
		$("#ipt_dbName").val("mysql");
	} else if(dbmsType == "Sybase"){
		$("#ipt_dbName").val("sybase");
	} else if(dbmsType == "MsSql"){
		$("#ipt_dbName").val("msSql");
	}
	var result = checkInfo("#tblDatabaseCommunity");
	if(result == true){
		checkAddDBMSCommunity();
	}	
}

function  doUpdate(){
	var id = $("#ipt_id").val();
	var ip = $("#ipt_ip").val();
	var dbName = $("#ipt_dbName").val();
	var userName = $("#ipt_userName").val();
	var password = $("#ipt_password").val();
	var port = $("#ipt_port").val();
	var dbmsType=$("#ipt_dbmsType").val();
	
	var path = getRootName();
	var uri = path + "/monitor/databaseCommunity/updateDatabaseCommunity";
	var ajax_param = {
		url : uri,
		type : "post",
		dataType : "json",
		data : {
			"id" : id,
			"ip" : ip, 
			"dbName" : dbName,
			"dbmsType" : dbmsType,
			"userName" : userName,
			"password" : password,
			"port" : port,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				$.messager.alert("提示", "信息修改成功！", "info");
				$('#popWin').window('close');
				window.frames['component_2'].reloadTable();
			} else {
				$.messager.alert("提示", "信息修改失败！", "error");
			}
		}
	}
	ajax_(ajax_param);
}

/*
 * 验证数据库验证中该设备是否存在
 */
function checkAddDBMSCommunity() {
	var id = $("#ipt_id").val();
	var deviceIP = $("#ipt_ip").val();
	var flag = 3;
	if (checkIP(flag)) {
		var path = getRootName();
		var uri = path + "/monitor/databaseCommunity/checkUpdateDBMSCommunity";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"id" : id,
				"ip" : deviceIP,
				"dbName" : $("#ipt_dbName").val(),
				"dbmsType" : $("#ipt_dbmsType").val(),
				"userName" : $("#ipt_userName").val(),
				"password" : $("#ipt_password").val(),
				"port" : $("#ipt_port").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (false == data || "false" == data) {
					$.messager.alert("提示", "该设备凭证已存在！", "error");
				} else {
					doUpdate();
					return;
				}
			}
		};
		ajax_(ajax_param);
	}
}

/*
 * 验证IP格式
 */
function checkIP(flag) {
	var deviceIP = $("#ipt_ip").val();
	if (!(/^(\*|(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*)\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*)\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]|\*))$/
			.test(deviceIP))) {
		$.messager.alert("提示", "ip地址错误，请填写正确的ip地址！", "info", function(e) {
			$("#ipt_ip").focus();
		});
		return false;
	}
	var port = $("#ipt_port").val(); 
	if (!(/^[0-9]*[1-9][0-9]*$/.test(port))) {
		$.messager.alert("提示", "端口号只能输入正整数！", "info", function(e) {
			$("#ipt_port").focus();
		});
		return false;
	}
	return true;
}

/*
 * 数据库凭证，展示默认的端口号
 */
function showDefaultPort(){
	if ($("#ipt_dbmsType").val() == "Mysql") {
		$("#ipt_port").val(3306);
		$("#dbNameForDb2").hide();
	} else if ($("#ipt_dbmsType").val() == "Oracle") {
		$("#dbNameForDb2").show();
		$("#ipt_port").val(1521);
	} else if ($("#ipt_dbmsType").val() == "DB2") {
		$("#dbNameForDb2").show();
		$("#ipt_port").val(50000);
	} else if ($("#ipt_dbmsType").val() == "Sybase") {
		$("#dbNameForDb2").hide();
		$("#ipt_port").val(5000);
	} else if ($("#ipt_dbmsType").val() == "MsSql") {
		$("#dbNameForDb2").hide();
		$("#ipt_port").val(1433);
	}
}
