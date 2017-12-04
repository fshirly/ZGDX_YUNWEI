$(document).ready(function() {
	initSubnetInfoDetail();
});

/*
 * 初始化子网信息
 */
function initSubnetInfoDetail(){
	var subNetId=$('#subNetId').val();
	var path=getRootName();
	var uri=path+"/platform/subnetViewManager/initSubnetInfoDetail";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			data:{
				"subNetId":subNetId,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				iterObj(data,"ipt");
			}
		};
	ajax_(ajax_param);
}

/**
 * 更新子网
 * @return
 */
function toUpdate(){
	var result = checkInfo("#tblModifySubnet");
	if(result == true){
		var subNetId=$('#subNetId').val();
		var gateway = $("#ipt_gateway").val();
		var dnsAddress = $("#ipt_dnsAddress").val();
		var path = getRootName();
		var uri = path + "/platform/subnetViewManager/doUpdateSubnet";
		var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
			"subNetId" : subNetId,
			"gateway" : gateway,
			"dnsAddress" : dnsAddress,
			"descr" : $("#ipt_descr").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (data == true) {
				$.messager.alert("提示", "更新子网成功！", "info");
				$('#popWin').window('close');
				window.frames['component_2'].frames['component_2'].reloadTable();
			} else {
				$.messager.alert("提示", "更新子网失败！", "error");
			}
		}
		};
		ajax_(ajax_param);
	}
}
