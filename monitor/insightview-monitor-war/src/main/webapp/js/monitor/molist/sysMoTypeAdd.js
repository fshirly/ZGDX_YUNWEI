
var path = getRootPatch();
function doAdd(){
	var result = checkInfo('#tblAddMoType');
	if (result == true) {
		var monitorTypeName = $("#ipt_monitorTypeName").val();
//		console.log(monitorTypeName);
		var uri=path+"/monitor/sysMo/isExistMonitorsType";
		var ajax_param={
				url:uri,
				type:"post",
				dateType:"json",
				data:{
					"monitorTypeName":monitorTypeName,
					"t" : Math.random()
		},
		error:function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			if(data==false){
				var uri=path+"/monitor/sysMo/addMonitorsType";
				var ajax_param={
						url:uri,
						type:"post",
						dateType:"json",
						data:{
							"monitorTypeName":monitorTypeName,
							"t" : Math.random()
				},
				error:function(){
					$.messager.alert("错误","ajax_error","error");
				},
				success:function(data){
					if(data==true){
						$.messager.alert("提示","新增监测器类型成功！","info");
						$('#popWin').window('close');
						window.frames['component_2'].reloadTable();
					}else{
						$.messager.alert("提示","新增监测器类型失败！","error");
					}
				}
				};
				ajax_(ajax_param);

			}else{
				$.messager.alert("提示","该监测器类型已经存在！","info");
			}
		}
		};
		ajax_(ajax_param);
	}
	
}