$(function(){
	var deviceIP=$('#ipt_deviceIP').val();
	getHostDetail(deviceIP);
});
 
//详情信息
function getHostDetail(deviceIP){
	var path = getRootName();
	var uri = path + "/monitor/hostManage/findHostDetail";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		async : false,
		data : {
			"deviceIP" : deviceIP,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			iterObj(data, "lbl");
		}
	};
	ajax_(ajax_param);
}

