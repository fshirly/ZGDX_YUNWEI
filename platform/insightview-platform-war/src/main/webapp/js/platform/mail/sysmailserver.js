$(document).ready(function() {
	initUpdateVal();

});
/**
 * 验证表单信息
 * 
 * @author 王淑平
 */
function checkForm() {
	var checkControlAttr = new Array('ipt_mailServer', 'ipt_port');
	var checkMessagerAttr = new Array('邮件服务器地址不能为空！', '端口不能为空');
	return checkFormCommon(checkControlAttr, checkMessagerAttr);
}

function isOrnoCheck() {
	if ($("input[type='checkbox']").is(':checked') == false) {
		$("#name").hide();
		$("#pwd").hide();
	} else {
		$("#name").show();
		$("#pwd").show();
	}
}

function initUpdateVal() {
	var path = getRootName();
	var uri = path + "/platform/mailServer/listMailServerConfig";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			iterObj(data, "ipt");
		}
	};
	ajax_(ajax_param);
}

/*
 * 新增服务配置 @author 王淑平
 */
function doAdd() {
	// initUpdateVal();
	if (checkForm() && checkMailServerInfo()) {
		var path = getRootName();
		var uri = "";
		var id = $("#ipt_id").val();
	var isAuth= $("#ipt_isAuth").val();
	if ($("input[type='checkbox']").is(':checked') == false) {
		isAuth=0;
	}else
	{
		isAuth=1;
	}
		var timeout = $("#ipt_timeout").val();
		if (timeout == '') {
			timeout = 10;
		}
		if (id.length == 0) {
			uri = path + "/platform/mailServer/addMailServer";
		} else {
			uri = path + "/platform/mailServer/updateMailServer?id=" + id;
		}
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				// "id": $("#ipt_id").val(),
				"mailServer" : $("#ipt_mailServer").val(),
				"port" : $("#ipt_port").val(),
				"timeout" : timeout,
				"senderAccount" : $("#ipt_senderAccount").val(),
				"isAuth" : isAuth,
				"userName" : $("#ipt_userName").val(),
				"password" : $("#ipt_password").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					$.messager.alert("提示", "邮件服务器设置成功！", "info");
					initUpdateVal();
				} else {
					$.messager.alert("提示", "邮件服务器设置失败！", "error");
				}

			}
		};
		ajax_(ajax_param);
	}

}

/**
 * 验证表单信息
 * 
 * @author 王淑平
 */
function checkMailServerInfo() {

	var ports = $("#ipt_port").val();
	if (!(/^[0-9]*[1-9][0-9]*$/.test(ports))) {
		$.messager.alert("提示", "端口只能输入正整数！", "info", function(e) {
			$("#ipt_port").focus();
		});
		return false;
	}
	var timeout = $("#ipt_timeout").val();
	if (!(/^[0-9]*[1-9][0-9]*$/.test(timeout))) {
		$.messager.alert("提示", "超时时间只能输入正整数！", "info", function(e) {
			$("#ipt_timeout").focus();
		});
		return false;
	}
	var email = $("#ipt_senderAccount").val();

	if (!(/^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w_]+)+$/.test(email))) {
		$.messager.alert("提示", "请输入正确的邮箱地址！", "info", function(e) {
			$("#ipt_senderAccount").focus();
		});
		return false;
	}
	/**
	if ($("input[type='checkbox']").is(':checked') == true) {
		var userName = $("#ipt_userName").val();
		if (!(/[\w_]{4,20}/.test(userName)) || userName.indexOf('\.') >= 0) {
			$.messager.alert("提示", "用户名至少输入4个字符，且由a-z0-9A-Z_组成！", "info",
					function(e) {
						$("#ipt_userName").focus();
					});
			return false;
		}
		var userNames = $("#ipt_userName").val();
		if (userNames.length <= 0) {
			$.messager.alert("提示", "请输入用户姓名！", "info", function(e) {
				$("#ipt_userName").focus();
			});
			return false;
		}
		var userPassword = $("#ipt_password").val();
		if (!(/[\w]{6,30}/.test(userPassword))) {
			$.messager.alert("提示", "密码至少为六位字符！", "info", function(e) {
				$("#ipt_password").focus();
			});
			return false;
		}
	}*/

	return true;
}

function cancle() {
	$('#divMailInfo').dialog('close');
	/*var id=$("#ipt_id").val();
	if (id.length == 0) {
		resetForm('tblMailServerInfo');
	} else {
		initUpdateVal();
	}*/

}
