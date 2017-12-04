var path = getRootName();
$(document).ready(function() {
	initRoomResDetail();
});

/*
 * 初始化机房信息
 */
function initRoomResDetail(){
	var path=getRootName();
	var uri=path+"/platform/supplier/queryProviderSoftHardwareProxy";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			data:{
				"proxyId":$("#proxyId").val(),
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				if(data['productType'] == 2) {
					data['productType'] = "硬件";
				} else if(data['productType'] == 1) {
					data['productType'] = "软件";
				}
				//data['outTime'] = formatDate(new Date(data['outTime']), "yyyy-MM-dd hh:mm:ss");
				data['proxyBeginTime'] = formatDateText2(new Date(data['proxyBeginTime']));
				data['proxyEndTime'] = formatDateText2(new Date(data['proxyEndTime']))
				iterObj(data,"lbl");
				$("#certificateUrl").html(initDownLinkTag("certificateUrl",data['certificateUrl']));
			}
		};
	ajax_(ajax_param);
}
