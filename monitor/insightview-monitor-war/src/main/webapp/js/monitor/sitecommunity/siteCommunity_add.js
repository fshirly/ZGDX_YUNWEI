$(document).ready(function() {
	$('#ipt_siteType').combobox({
		editable : false,
        panelHeight : '120',
        textField: 'label',
        valueField: 'value',
        data: [{
            label: 'FTP',
            value: '1',
            "selected":true 
        },{
            label: 'HTTP',
            value: '2'
        }],
        onSelect:function(rec){
	    	setDefualt(rec.value);
		}
	});
	$("#addFtpCommunity").show();
	$("#addHttpCommunity").hide();
	$("#ipt_port").val(21);
});

/**
 * 选择监控类型
 * @return
 */
function setDefualt(siteType){
	if(siteType == 1){//ftp
		$("#addFtpCommunity").show();
		$("#addHttpCommunity").hide();
		$("#ipt_port").val(21);
	}
	else if(siteType == 2){//http
		$("#addFtpCommunity").hide();
		$("#addHttpCommunity").show();
	}
}

/**
 * 新增站点
 */
function toAdd(){
	var siteType = $("#ipt_siteType").combobox("getValue");
	var checkSiteResult = false;
	if(siteType == 1){
		checkSiteResult = checkInfo("#addFtpCommunity");
	}else if(siteType == 2){
		checkSiteResult = checkInfo("#addHttpCommunity");
	}
	if(checkSiteResult == true){
		checkCommunity(siteType);
	}
}

/**
 * 校验站点凭证是否存在
 */
function checkCommunity(siteType){
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
				return false;
			} else {
				doAddCoomunity(siteType);
			}
		}
	};
	ajax_(ajax_param);
}

/**
 * 新增
 * @return
 */
function doAddCoomunity(siteType){
	var requestMethod;
	if(siteType == 1){
		var ipAddress = $("#ipt_ftpIPAddress").val();
	}else if(siteType == 2){
		var ipAddress = $("#ipt_httpIPAddress").val();
		requestMethod = $('input[name="requestMethod"]:checked').val();
	}
	var port = $("#ipt_port").val();
	var path = getRootName();
	var uri = path + "/monitor/siteCommunity/addCommunity";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"siteType" :siteType,
			"ipAddress":ipAddress,
			"port" : port == '' ? 0 : port,
			"userName" : $("#ipt_userName").val(),
			"password" : $("#ipt_password").val(),
			"requestMethod" : requestMethod,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (false == data || "false" == data) {
				$.messager.alert("错误", "新增站点凭证失败！", "error");
			} else {
				$.messager.alert("提示", "新增站点凭证成功！", "info");
				$('#popWin').window('close');
				window.frames['component_2'].reloadTable();
			}
		}
	};
	ajax_(ajax_param);
}