/**
 * 新增告警类型
 * @return
 */
function doAdd(){
	if (checkForm()) {
		var path = getRootName();
		var uri = path + "/monitor/alarmMgr/alarmcategory/addAlarmCategory";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"categoryName" : $("#ipt_categoryName").val(),
				"alarmOID" : $("#ipt_alarmOID").val(),
				"descr" : $("#ipt_descr").val(),
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
}

function checkForm() {
	return checkInfo('#tblEdit');
}


function checkNameUnique(){
	var categoryName = $("#ipt_categoryName").val();
	if(categoryName != null || categoryName != ""){
		var path = getRootName();
		var uri = path + "/monitor/alarmMgr/alarmcategory/checkNameUnique";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"categoryName" : categoryName,
				"t" : Math.random()
			},
			error : function(){
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data){
				if(data == false){
					$.messager.alert("提示", "该类型已存在！", "info");
					$('#ipt_categoryName').val("");
					$('#ipt_categoryName').focus();
				}else{
					return;
				}
			}
		};
		ajax_(ajax_param);
	}
}