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
				iterObj(data,"lbl");
			}
		};
	ajax_(ajax_param);
}
