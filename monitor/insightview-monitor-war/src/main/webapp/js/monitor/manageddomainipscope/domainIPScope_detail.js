$(document).ready(function() {
	initDomainIPScopeDetail();
});

/*
 * 初始化信息
 */
function initDomainIPScopeDetail(){
	var scopeID=$('#scopeID').val();
	var path=getRootName();
	var uri=path+"/monitor/domainipscope/initDomainIPScopeDetail";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			data:{
				"scopeID":scopeID,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				$("#lbl_startIPStr").text(data.ip1);
				if(data.domainDescr!=null && data.domainDescr!="null"){
					$("#lbl_domainDescr").text(data.domainDescr);
				}
				$("#lbl_domainName").text(data.domainName);
				if(data.scopeType == 2 || data.scopeType == "2"){
					$("#lbl_scopeType").text("IP范围");
					$("#lbl_endIPStr").text(data.ip2);
					$("#show1").show();
					$("#show2").hide();
				}else if(data.scopeType == 1 || data.scopeType == "1"){
					$("#lbl_scopeType").text("子网");
					$("#lbl_network").text(data.ip2);
					$("#show1").hide();
					$("#show2").show();
				}
				if(data.status == 1 || data.status =="1"){
					$("#lbl_status").text("有效");
				}else if(data.status == 2 || data.status =="2"){
					$("#lbl_status").text("无效");
				}
			}
		};
	ajax_(ajax_param);
}
