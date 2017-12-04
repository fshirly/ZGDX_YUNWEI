/**
 * 批量告警确认处理
 * @return
 */
function doProBathConfirm(){	    
		var path=getRootName();
		var uri=path+"/monitor/alarmActive/doBathAlarmActiveConfirm";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"id" : $("#id").val(),					
				"confirmInfo" : $("#confirmInfo").val()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					$.messager.alert("提示", "告警确认成功！", "info");
					$('#popWin').window('close');
					//window.frames['component_2'].reloadTable();
					reloadTable();
				} else {
					$.messager.alert("提示", "告警确认失败！", "error");
				}
			}
		};
		ajax_(ajax_param);
}

/**
 * 批量告警清除处理
 * @return
 */
function doProBathClear(){	
		var path=getRootName();
		var uri=path+"/monitor/alarmActive/doBathClearAlarmActive";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"id" : $("#id").val(),					
				"cleanInfo" : $("#cleanInfo").val()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					$.messager.alert("提示", "告警清除成功！", "info");
					$('#popWin').window('close');
					//window.frames['component_2'].reloadTable();
					reloadTable();
				} else {
					$.messager.alert("提示", "告警清除失败！", "error");
				}
			}
		};
		ajax_(ajax_param);
}