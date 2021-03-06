/**
 * 新增告警等级
 * @return
 */
function doAdd(){
	var levelColor = $("#ipt_levelColor").val();
	var alarmLevelName = $("#ipt_alarmLevelName").val();
	var levelIcon = $("#ipt_levelIcon").val();
	if (checkForm()) {
		var path = getRootName();
		var uri = path + "/monitor/alarmMgr/alarmlevel/addAlarmLevel";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"alarmLevelName" : alarmLevelName,
				"levelColor" : levelColor,
				"levelIcon" : levelIcon,
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
					reloadTable();
				} else {
					$.messager.alert("提示", "新增失败！", "error");
					
				}
			}
		};
		ajax_(ajax_param);
	}
}

function checkForm() {
	return checkInfo('#tblEdit');
}


function checkNameUnique(){
	var alarmLevelName = $("#ipt_alarmLevelName").val();
	if(alarmLevelName != null || alarmLevelName != ""){
		var path = getRootName();
		var uri = path + "/monitor/alarmMgr/alarmlevel/checkNameUnique";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"alarmLevelName" : alarmLevelName,
				"t" : Math.random()
			},
			error : function(){
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data){
				if(data == false){
					$.messager.alert("提示", "该等级已存在！", "info");
					$('#ipt_alarmLevelName').val("");
					$('#ipt_alarmLevelName').focus();
				}else{
					return;
				}
			}
		};
		ajax_(ajax_param);
	}
}