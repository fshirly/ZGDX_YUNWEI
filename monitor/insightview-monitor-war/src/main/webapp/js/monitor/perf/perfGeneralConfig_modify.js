$(document).ready(function() {
	var className =  window.frames['component_2']._className;
	var classLable = window.frames['component_2']._classLable;
	var collectPeriod = window.frames['component_2']._collectPeriod;
	$("#ipt_mobjectClassID").val(classLable);
	$("#ipt_collectPeriod").val(collectPeriod);
});


/**
 * 获得对象的初始默认采集周期
 */
function initCollectPeriodVal(className){
	var path = getRootName();
	var uri = path + "/monitor/perfGeneralConfig/initCollectPeriodVal?className="+className;
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
			if(data!= -1){
				$("#ipt_collectPeriod").val(data);
			}
		}
	};
	ajax_(ajax_param);
}

/**
 * 更新采集周期的默认值 
 */
function doSetCollectPeriod(){
	var result = checkInfo("#tblPerfSetting");
	var className = window.frames['component_2']._className;
	var collectPeriod = $("#ipt_collectPeriod").val();
	var path = getRootName();
	var uri = path + "/monitor/perfGeneralConfig/doSetCollectPeriod?className="+className+"&collectPeriod="+collectPeriod;
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
			if (data == true) {
				$.messager.alert("提示", "更新采集周期默认值成功！", "info");
				$('#popWin').window('close');
				window.frames['component_2'].reloadTable();
			} else {
				$.messager.alert("错误", "更新采集周期默认值失败！", "error");
			}
		}
	};
	if(result == true){
		ajax_(ajax_param);
	}
}

