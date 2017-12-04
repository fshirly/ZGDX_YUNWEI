$(function(){
	var userId=$('#ipt_userID').val();
//	alert("userId= "+userId);
	toLock(userId);
});

function toLock(userId){
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
			var userAccount=$("#ipt_userAccount").val();
			$("#lockAccountName").val(userAccount);
			$("#lockedReason").val("");
		}
	};
	ajax_(ajax_param);
}


function doLock(){
	var result=checkFormInfo();
	var lockedReason=$("#lockedReason").val();
	$("#ipt_lockedReason").val(lockedReason);
	var path = getRootName();
	var uri = path + "/permissionSysUser/lockSysUser";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"userID" : $("#ipt_userID").val(),
			"status" : 0,
			"lockedReason" : $("#ipt_lockedReason").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data) {
//				$.messager.alert("提示", "用户锁定成功！", "info");
				$('#popWin').window('close');
				window.frames['component_2'].reloadTable();
			} else {
				$.messager.alert("提示", "用户锁定失败！", "error");
				window.frames['component_2'].reloadTable();
			}
		}
	};
	if (result == true || result == 'true') {
		ajax_(ajax_param);
	}
	
}


function checkFormInfo()
{	
	return checkInfo('#tblLockUser');
}