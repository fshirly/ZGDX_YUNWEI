$(function(){
	var domainId=$('#ipt_domainId').val();
	toShowInfo(domainId);
});
 

//查看详情
function toShowInfo(domainId){  
	$('#flag').val("edit");
	$('.input').val("");
	setRead(domainId);
}


//详情信息
function setRead(domainId){
	var path=getRootName();
	var uri=path+"/platform/managedDomain/initManagedDomainInfo";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			async : false,

			data:{
				"domainId":domainId,
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

