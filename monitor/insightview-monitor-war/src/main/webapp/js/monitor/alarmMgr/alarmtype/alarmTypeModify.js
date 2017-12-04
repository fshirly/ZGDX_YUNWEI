function  doUpdate(){
	var alarmTypeID = $("#ipt_alarmTypeID").val();
	if (checkForm()) {
		var path = getRootName();
		var uri = path + "/monitor/alarmMgr/alarmtype/updateAlarmType";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"alarmTypeID" : alarmTypeID,
				"alarmTypeName" : $("#ipt_alarmTypeName").val(),
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
}

function checkForm() {
	return checkInfo('#tblEdit');
}

function checkNameUnique2(){
	var alarmTypeName = $("#ipt_alarmTypeName").val();
	var  alarmTypeID =$("#ipt_alarmTypeID").val();
	if(alarmTypeName != null || alarmTypeName != ""){
		var path = getRootName();
		var uri = path + "/monitor/alarmMgr/alarmtype/checkNameBeforeUpdate";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"alarmTypeName" : alarmTypeName,
				"alarmTypeID" : alarmTypeID,
				"t" : Math.random()
			},
			error : function(){
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data){
				if(data == false){
					$.messager.alert("提示", "该类型已存在！", "info");
					$('#ipt_alarmTypeName').val("");
					$('#ipt_alarmTypeName').focus();
				}else{
					return;
				}
			}
		};
		ajax_(ajax_param);
	}
}