$(document).ready(function() {
	initIPAddressDetail();
});

/*
 * 初始化信息
 */
function initIPAddressDetail(){
	var id = $("#id").val();
	var path=getRootName();
	var uri=path+"/platform/deptViewManager/initIPAddressDetail";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			data:{
				"id":id,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				iterObj(data,"ipt");
				$('#ipt_userId').val(data.deptId);
				var deptId = data.deptId;
				$('#ipt_userId').createSelect2({
			        uri : '/platform/deptViewManager/getDeptUsers?deptId='+deptId,
			        name : 'userName',
			        id : 'userId',
			        initVal : {
						ipt_userId : $("#ipt_userId").attr("value")
					}
			    });
			}
		};
	ajax_(ajax_param);
}

/*
 * 编辑
 */
function doUpdate(){
	var result=checkInfo('#tblIPAddressModify');
	if(result == true){
		var path = getRootName();
		var uri=path+"/platform/deptViewManager/updateIPAddress";
		var ajax_param={
				url:uri,
				type:"post",
				dateType:"json",
				data:{
			"id" : $("#id").val(),
			"userId" : $("#ipt_userId").val(),
			"note" : $("#ipt_note").val(),
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			if (data == true) {
				$.messager.alert("提示", "IP地址更新成功！", "info");
				window.frames['component_2'].frames['component_2'].reloadTable();
				$('#popWin').window('close');
			} else {
				$.messager.alert("提示", "IP地址更新失败！", "error");
			}
		}
		};	
		ajax_(ajax_param);
	}
}
