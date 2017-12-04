$(function(){
	showInfoInit();
})

// 查看详情
function showInfoInit() {
	var paramId = $('#paramId').val();
	$('.input').val("");
	setRead(paramId);
	$("#btnClose2").unbind();
	$("#btnClose2").bind("click", function() {
		$('.input').val("");
		parent.$('#popWin').window('close');
	});

	var name = $("#lbl_paramName").val();
	if(name.indexOf("Icon")!=-1){
		if(name=="LoginIcon"){
			$("#scanImg").attr("onerror","this.src='/insightview/style/images/logo.png'");
		}else if(name=="ProductSmallIcon"){
			$("#scanImg").attr("onerror","this.src='/insightview/style/images/logos.png'");
		}else if(name=="LogoIcon"){
			$("#scanImg").attr("onerror","this.src='/insightview/favicon.ico'");
			$("#scanImg").attr("style","width:16px; height:16px;");
		}
		$("#lbl_paramValue").text("");
		$("#scanImg").show();
	}else{
		$("#scanImg").hide();
	}
}

// 详情信息
function setRead(paramId) {
	var path = getRootName();
	var uri = path + "/permissionSystemParam/findDepartmentByParamId?paramId="
			+ paramId;
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		async : false,

		data : {
			"paramId" : paramId,
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
