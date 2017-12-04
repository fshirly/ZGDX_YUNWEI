var path = getRootName();
$(document).ready(function() {
	doInitView();
});

/*
 * 初始化页面数据
 */
function doInitView(){
	var monitorTypeId = $("#ipt_monitorTypeId").val();
	var uri=path+"/monitor/sysMo/toGetMonitorTypeById";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"monitorTypeID":monitorTypeId,
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
//		console.log("moTypeData = "+data);
		if(data != null){
			$("#ipt_monitorTypeName").val(data.monitorTypeName);
			$("#ipt_monitorTypeNameTemp").val(data.monitorTypeName);
		}
	}
	};
	ajax_(ajax_param);
}
/*
 * 修改
 */
function doModify(){
	var result = checkInfo('#tblEditMoType');
	if (result == true) {
		var monitorTypeName = $("#ipt_monitorTypeName").val();
		var monitorTypeId = $("#ipt_monitorTypeId").val();
		var monitorTypeNameTemp = $("#ipt_monitorTypeNameTemp").val();
		if (monitorTypeName == monitorTypeNameTemp) {
			var uri=path+"/monitor/sysMo/updateMonitorsType";
			var ajax_param={
					url:uri,
					type:"post",
					dateType:"json",
					data:{
						"monitorTypeName":monitorTypeName,
						"monitorTypeID":monitorTypeId,
						"t" : Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				if(data==true){
					$.messager.alert("提示","修改监测器类型成功！","info");
				}else{
					$.messager.alert("提示","修改监测器类型失败！","error");
				}
			}
			};
			ajax_(ajax_param);

		} else {
			var uri=path+"/monitor/sysMoTemplate/isUsedTempByTypeID";
			var ajax_param={
					url:uri,
					type:"post",
					dateType:"json",
					data:{
						"monitorTypeID":monitorTypeId,
						"t" : Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				if(data==true){
					$.messager.alert("提示","该监测器类型关联的模板已被套用，类型名称不能修改！","info");
				}else{
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
						if (data==false) {
							var uri=path+"/monitor/sysMo/updateMonitorsType";
							var ajax_param={
									url:uri,
									type:"post",
									dateType:"json",
									data:{
										"monitorTypeName":monitorTypeName,
										"monitorTypeID":monitorTypeId,
										"t" : Math.random()
							},
							error:function(){
								$.messager.alert("错误","ajax_error","error");
							},
							success:function(data){
								if(data==true){
									$.messager.alert("提示","修改监测器类型成功！","info");
									$('#popWin').window('close');
									window.frames['component_2'].reloadTable();
								}else{
									$.messager.alert("提示","修改监测器类型失败！","error");
								}
							}
							};
							ajax_(ajax_param);
						} else {
							$.messager.alert("提示","该监测器类型已经存在！","info");
						}
					}
					};
					ajax_(ajax_param);
				}
			}
			};
			ajax_(ajax_param);
		}
		
	}
}