
/**
 * 告警分类修改
 * @param alarmCategoryID
 * @return
 */
function  doUpdate(){
	var categoryID = $("#ipt_categoryID").val();
	if (checkForm()) {
		var path = getRootName();
		var uri = path + "/monitor/alarmMgr/alarmcategory/updateAlarmCategory";
		var ajax_param = {
			url : uri,
			type : "post",
			dataType : "json",
			data : {
				"categoryID" : categoryID,
				"categoryName" : $("#ipt_categoryName").val(),
				"alarmOID" : $("#ipt_alarmOID").val(),
				"descr" :$("#ipt_descr").val(),
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
					$.messager.alert("提示", "该分类为系统定义,信息修改失败！", "error");
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
	var categoryName = $("#ipt_categoryName").val();
	var  categoryID =$("#ipt_categoryID").val();
	if(categoryName != null || categoryName != ""){
		var path = getRootName();
		var uri = path + "/monitor/alarmMgr/alarmcategory/checkNameUniqueBeforeUpdate";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"categoryName" : categoryName,
				"categoryID" :categoryID,
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