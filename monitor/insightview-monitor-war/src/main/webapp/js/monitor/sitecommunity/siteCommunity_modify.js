$(document).ready(function() {
	initSiteCommunity();
});

/**
 * 选择监控类型
 * @return
 */
function setDefualt(siteType){
	if(siteType == 1){//ftp
		$("#editFtpCommunity").show();
		$("#editHttpCommunity").hide();
		$("#ipt_port").val(21);
	}
	else if(siteType == 2){//http
		$("#editFtpCommunity").hide();
		$("#editHttpCommunity").show();
	}
}

/**
 * 初始化站点凭证
 */
function initSiteCommunity(){
	var id = $("#id").val();
	var path = getRootName();
	var uri = path + "/monitor/siteCommunity/initSiteCommunity";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"id" :id,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			iterObj(data,"ipt");
			var siteType = data.siteType;
//			$("#ipt_siteType").combobox('setValue', siteType);
			setDefualt(siteType);
			if(siteType == 1){
				$("#ipt_ftpIPAddress").val(data.ipAddress);
				$("#ipt_siteTypeName").val("FTP");
			}else if(siteType == 2){
				$("#ipt_httpIPAddress").val(data.ipAddress);
				$("#ipt_siteTypeName").val("HTTP");
				var flag = data.requestMethod;
				if(flag ===1){
					$("input:radio[name='requestMethod'][value=1]").attr("checked",'checked');
				}else if(flag ===2){
					$("input:radio[name='requestMethod'][value=2]").attr("checked",'checked');
				}else if(flag ===3){
					$("input:radio[name='requestMethod'][value=3]").attr("checked",'checked');
				}
			}
		}
	};
	ajax_(ajax_param);
}

/**
 * 更新站点
 */
function toUpdate(){
	var siteType = $("#ipt_siteType").val();
	var checkSiteResult = false;
	if(siteType == 1){
		checkSiteResult = checkInfo("#editFtpCommunity");
	}else if(siteType == 2){
		checkSiteResult = checkInfo("#editHttpCommunity");
	}
	if(checkSiteResult == true){
		checkCommunity(siteType);
	}
}

/**
 * 校验站点凭证是否存在
 */
function checkCommunity(siteType){
	var id = $("#id").val();
	if(siteType == 1){
		var ipAddress = $("#ipt_ftpIPAddress").val();
	}else if(siteType == 2){
		var ipAddress = $("#ipt_httpIPAddress").val();
	}
	var port = $("#ipt_port").val();
	var path = getRootName();
	var uri = path + "/monitor/siteCommunity/checkCommunity";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"id":id,
			"siteType" :siteType,
			"ipAddress":ipAddress,
			"port" : port == '' ? 0 : port,
			"userName" : $("#ipt_userName").val(),
			"password" : $("#ipt_password").val(),
			"requestMethod" : $('input[name="requestMethod"]:checked').val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (false == data || "false" == data) {
				$.messager.alert("提示", "该站点凭证已存在！", "info");
			} else {
				if(siteType == 1){
					doUpdateFtpCoomunity(id,ipAddress,siteType);
				}else if(siteType == 2){
					doUpdateHttpCoomunity(id,ipAddress,siteType);
				}
			}
		}
	};
	ajax_(ajax_param);
}

/**
 * 更新FTp
 * @return
 */
function doUpdateFtpCoomunity(id,ipAddress,siteType){
	var ipAddress = $("#ipt_ftpIPAddress").val();
	var port = $("#ipt_port").val();
	var path = getRootName();
	var uri = path + "/monitor/siteCommunity/updateCommunity";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"id":id,
			"siteType" :siteType,
			"ipAddress":ipAddress,
			"port" : port == '' ? 0 : port,
			"userName" : $("#ipt_userName").val(),
			"password" : $("#ipt_password").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (false == data || "false" == data) {
				$.messager.alert("错误", "更新站点凭证失败！", "error");
			} else {
				$.messager.alert("提示", "更新站点凭证成功！", "info");
				$('#popWin').window('close');
				window.frames['component_2'].reloadTable();
			}
		}
	};
	ajax_(ajax_param);
}


/**
 * 更新Http
 * @return
 */
function doUpdateHttpCoomunity(id,ipAddress,siteType){
	var path = getRootName();
	var uri = path + "/monitor/siteCommunity/updateCommunity";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"id":id,
			"siteType" :siteType,
			"ipAddress":ipAddress,
			"requestMethod" : $('input[name="requestMethod"]:checked').val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (false == data || "false" == data) {
				$.messager.alert("错误", "更新站点凭证失败！", "error");
			} else {
				$.messager.alert("提示", "更新站点凭证成功！", "info");
				$('#popWin').window('close');
				window.frames['component_2'].reloadTable();
			}
		}
	};
	ajax_(ajax_param);
}