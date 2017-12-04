function doSubmit() {
	var path = getRootName();
	var uri = path + "fable_security";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"userAccount" : $("#iptUserAccount").val(),
			"userPassword" : $("#iptUserPassword").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				var path = getRootName();
				var uri = path + "/commonLogin/toMain";
				sendRequest(uri);
			} else {
				$.messager.alert("用户名或密码错误！");
			}
		}
	}
	ajax_(ajax_param);
}

$(function(){
	var uri = getRootName() + "/commonLogin/getGoogleDownload";
	var ajax_param = {
			url : uri,
			type : "post",
			dataType : "json",
			error : function (){
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data){
				var url = getRootName() + "/commonFileUpload/CosDownload?fileDir=";
				$("#downloadForWin7").attr("href",url+data.forWin7);
				$("#downloadForXp").attr("href",url+data.forXp);
			}
	};
	ajax_(ajax_param);
});

