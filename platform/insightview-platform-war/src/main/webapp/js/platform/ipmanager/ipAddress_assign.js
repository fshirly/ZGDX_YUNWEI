$(document).ready(function() {
	initIPAddressDetail();
	var deptId = $("#addressDeptId").val();
	$('#ipt_userId').createSelect2({
        uri : '/platform/deptViewManager/getDeptUsers?deptId='+deptId,
        name : 'userName',
        id : 'userId'
    });
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
				$("#addressSubNetId").val(data.subNetId);
			}
		};
	ajax_(ajax_param);
}

/*
 * 分配IP
 */
function doGiveIP(){
	$.messager.confirm("提示","确定分配该IP地址?",function(r){
		if (r == true) {
			var result=checkInfo('#tblIPAddressAssign');
			if(result == true){
				var id = $("#id").val();
				var subNetId = $("#addressSubNetId").val();
				var deptId = $("#addressDeptId").val();
				var userId = $("#ipt_userId").select2('val');
				console.log("userId="+userId);
				var path = getRootName();
				var uri = path + "/platform/deptViewManager/doGiveToDevice";
				var ajax_param = {
						url : uri,
						type : "post",
						datdType : "json",
						data : {
					"id" : id,
					"subNetId" : subNetId,
					"deptId" : deptId,
					"userId" : userId,
					"note" : $("#ipt_note").val(),
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (data == true) {
						$.messager.alert("提示", "分配IP成功！", "info");
						window.frames['component_2'].frames['component_2'].reloadTable();
						$('#popWin').window('close');
					} else {
						$.messager.alert("错误", "分配IP失败！", "error");
					}
				}
				};
				ajax_(ajax_param);
			}
		}
	});
}
