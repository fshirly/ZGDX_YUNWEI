$(document).ready(function() {
	$('#txtSplitNum').combobox({
		editable : false,
	});
});

function toPreview(){
	var result = checkInfo('#tblSubnetSplit');
	if(result == true){
		var splitNum = $("#txtSplitNum").combobox('getValue');
		var ipAddress = $("#ipAddress").val();
		var subNetId = $("#subNetId").val();
		var subNetMark = $("#subNetMark").val();
		
		var path = getRootName();
		var uri = path + '/platform/subnetViewManager/isSplit?subNetId='+subNetId+'&splitNum='+splitNum+'&ipAddress='+ipAddress+'&subNetMark='+subNetMark
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
				if (false == data || "false" == data) {
					$.messager.alert("提示", "该子网不能满足所选拆分数量的拆分！", "info");
				} else{
					parent.parent.$('#popWin2').window({
				    	title:'拆分预览',
				        width:800,
				        height : 450,
				        minimizable:false,
				        maximizable:false,
				        collapsible:false,
				        modal:true,
				        href: getRootName() + '/platform/subnetViewManager/toSplitAddressInfo?subNetId='+subNetId+'&splitNum='+splitNum+'&ipAddress='+ipAddress+'&subNetMark='+subNetMark
				    });
				}
			}
		};
		ajax_(ajax_param);
		
	}
}
