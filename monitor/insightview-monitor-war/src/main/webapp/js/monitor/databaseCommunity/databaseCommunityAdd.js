$(document).ready(function() {
	$("#dbNameForDb2").hide();
});

function checkForm() {
	var checkControlAttr = new Array('ipt_dbmsType','ipt_ip','ipt_dbName','ipt_userName','ipt_password' );
	var checkMessagerAttr = new Array('请先选择数据库类型！','请输入设备IP！','请输入数据库名！','请输入用户名！','请输入密码！');
	return checkFormCommon(checkControlAttr, checkMessagerAttr);
}

function toAdd(){
	var dbmsType=$("#ipt_dbmsType").val();
	if(dbmsType == "Mysql"){
		$("#ipt_dbName").val("mysql");
	} else if(dbmsType == "Sybase"){
		$("#ipt_dbName").val("sybase");
	} else if(dbmsType == "MsSql"){
		$("#ipt_dbName").val("mssql");
	}
	var result = checkInfo("#tblDatabaseCommunity");
	if(result == true){
		checkAddDBMSCommunity();
	}
}

/*
 * 新增操作
 */
function doAdd(){
	var ip = $("#ipt_ip").val();
	var dbName = $("#ipt_dbName").val();
	var dbmsType = $("#ipt_dbmsType").val();
	var userName = $("#ipt_userName").val();
	var password = $("#ipt_password").val();
	var port = $("#ipt_port").val();
	var path = getRootName();
	var uri = path + "/monitor/databaseCommunity/addDatabaseCommunity";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
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
				$.messager.alert("提示", "新增成功！", "info");
				$('#popWin').window('close');
				window.frames['component_2'].reloadTable();
			} else {
				$.messager.alert("提示", "新增失败！", "error");
				
			}
		}
	};
	ajax_(ajax_param);
}

/*
 * 验证数据库验证中该设备是否存在
 */
function checkAddDBMSCommunity() {
	var deviceIP = $("#ipt_ip").val();
	var flag = 3;
	if (checkIP(flag)) {
		var path = getRootName();
		var uri = path + "/monitor/databaseCommunity/checkAddDBMSCommunity";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
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
					doAdd();
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
		$.messager.alert("提示", "设备IP格式错误，请填写正确的设备IP！", "info", function(e) {
			$("#ipt_ip").focus();
		});
		return false;
	}
	var port = $("#ipt_port").val(); 
	if (!(/^[0-9]*[1-9][0-9]*$/.test(port))) {
		$.messager.alert("提示", "端口只能输入正整数！", "info", function(e) {
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
		$("#dbNameForDb2").hide();
		$("#ipt_port").val(3306);
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
