$(function(){
	doInitLRselect();
	
	$("#selLeft").empty();
	$("#selRight").empty();
	$.post(
			getRootName()+"/permissionSysUserGroup/findGroupRole",
			{groupID:$("#groupID").val()},
			function(data){
				var roleLst = eval('(' + data.roleLstJson + ')');
				for (var i = 0; i < roleLst.length; i++) {
					var sysRole = roleLst[i];
					$("#selLeft").append(
							"<option value='" + sysRole.roleId + "'>"
									+ sysRole.roleName + "</option>");
				}

				var groupRoleLst = eval('(' + data.groupRoleLstJson + ')');
				for (var i = 0; i < groupRoleLst.length; i++) {
					var groupRole = groupRoleLst[i];
					$("#selRight").append(
							"<option value='" + groupRole.roleId + "'>"
									+ groupRole.roleName + "</option>");
				}
			}
	);
	
});
function doInitLRselect() {
	$.fn.LRSelect("selLeft", "selRight", "img_L_AllTo_R", "img_L_To_R","img_R_To_L", "img_R_AllTo_L");
	$.fn.UpDownSelOption("imgUp", "imgDown", "selRight");
}
function resetGroupRole() {
	var valArr = "";
	$("#selRight option").each(function() {
		var val = $(this).val(); // 获取单个value
		valArr += val + ",";
	});
	var path = getRootName();
	var uri = path + "/permissionSysUserGroup/updateGroupRole";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"groupID" : $("#groupID").val(),
			"roleIdArr" : valArr,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
//				$.messager.alert("提示", "角色分配成功！", "info");
				$('#popWin').window('close');
			} else {
				$.messager.alert("提示", "角色分配失败！", "error");
			}
		}
	};
	ajax_(ajax_param);
}