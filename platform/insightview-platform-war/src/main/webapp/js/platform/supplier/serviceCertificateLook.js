var path = getRootName();
$(document).ready(function() {
	initRoomResDetail();
});

/*
 * 初始化机房信息
 */
function initRoomResDetail(){
	var path=getRootName();
	var uri=path+"/platform/supplier/queryProviderServiceCertificate";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			data:{
				"serviceId":$("#serviceId").val(),
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				//data['outTime'] = formatDate(new Date(data['outTime']), "yyyy-MM-dd hh:mm:ss");
				data['serviceBeginTime'] = formatDateText2(new Date(data['serviceBeginTime']));
				data['serviceEndTime'] = formatDateText2(new Date(data['serviceEndTime']))
				iterObj(data,"lbl");
				$("#certificateUrl").html(initDownLinkTag("certificateUrl",data['certificateUrl']));
			}
		};
	ajax_(ajax_param);
}
