$(function(){
	var userId=$('#ipt_userID').val();
//	alert("userId= "+userId);
	toModifyPwd(userId);
});

function toModifyPwd(userId){
	var path = getRootName();
	var uri = path + "/permissionSysUser/findSysUser";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"userID" : userId,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			iterObj(data, "ipt");
			var oldPwd=$("#ipt_userPassword").val();
			$("#oldPwd").val(oldPwd);
			$("#newPwd").val("");
			$("#repeatPwd").val("");
			
		}
	};
	ajax_(ajax_param);
}

//修改密码
function doModifyPwd(){
	var sendEmail = $('[name=sendEmail]:checked').val();
	var path = getRootName();
	var uri = path + "/permissionSysUser/modifyPwd";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"userID" : $("#ipt_userID").val(),
			"userPassword":$("#newPwd").val(),
			"sendEmail":sendEmail,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (data == 1) {
//				$.messager.alert("提示", "密码修改成功！", "info");
				$('#popWin').window('close');
				window.frames['component_2'].reloadTable();
			} else if (data == -1){
				$.messager.alert("提示", "邮件发送失败,邮箱不可达！", "error");
			}else {
				$.messager.alert("提示", "密码修改失败,数据库操作异常！", "error");
			}
		}
	};
	ajax_(ajax_param);
}

//新密码是否为空
function checkIsNull(){
	var newPwd=$("#newPwd").val();
	if(newPwd=="" || newPwd==null){
		$.messager.alert("提示", "新密码不能为空！", "error");
	}
}

//检查新密码与重复密码是否一致
function checkPwd(){
	var newPwd=$("#newPwd").val();
	var repeatPwd=$("#repeatPwd").val();
	if(repeatPwd != newPwd){
		$.messager.alert("提示", "两次密码输入不一致，请重新输入！", "error");
		$("#repeatPwd").val("");
	}
}

//检查新密码与重复密码是否一致
function checkConfirmPwd(){
	var pwd=$("#ipt_userPassword").val();
	if(pwd=="" || pwd==null){
		$.messager.alert("提示", "用户密码不能为空！", "error");
	}else{
		var confirmPwd=$("#confirmPassword").val();
		if(confirmPwd != pwd){
			$.messager.alert("提示", "两次密码输入不一致，请重新输入！", "error");
			$("#confirmPassword").val("");
		}
	}
	
}