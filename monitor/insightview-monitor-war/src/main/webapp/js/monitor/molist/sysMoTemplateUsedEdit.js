var flag = false;
$(document).ready(function() {
	showUsedTemplate();
	$('#ipt_monitorTemplateName').combobox({   
        //相当于html >> select >> onChange事件   
        onSelect:function(rec){
//			console.log("templateID = "+rec.value);
			if(rec.value == "-1"){
//				console.log("###########");
				doInitMoList($("#moClassID").val());
			}else{
//				console.log("************");
				initMoListByTemplateID(rec.value);
			}
			
	}
   })
});


/**
 * 根据moClassId获取监测器信息
 * @param moClassId
 * @return
 */
function doInitMoList(moClassID){
	$('#monitor').html("");
	var length=$("#monitor").find("tr").length-1;
	if(length>0){
		var trs=length/2+length%2+1;
		for(var i=1;i<trs;i++){
			$("#monitor tr:last").remove();
		}
	}
	var path=getRootName();
	var uri=path+"/monitor/sysMoTemplate/listMoList";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"text",
		async : false,
		data:{
			"moClassID":moClassID,
			"t" : Math.random()
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			if(data.length>0){
				$("#monitorTitle").show();
			}else{
				$("#monitorTitle").hide();
			}
			var html='';
			var trHTML1 = "<tr>"
			var trHTML2 = "</tr>"
			for (var i=0;i<data.length;i++){ 
				html+="<td><input type='checkbox' id='ipt_mo"+i+"' name='moType' value='"+data[i].split(",")[0]+"' checked/>&nbsp;&nbsp;&nbsp;&nbsp;"+data[i].split(",")[1]+"</td>" 
						+"<td><input id='ipt_doIntervals"+data[i].split(",")[0]+"' value='20' style='width:60px;' validator='{\"default\":\"ptInteger\"}'/></td>"
						+"<td><select class='inputs' id='ipt_timeUnit"+data[i].split(",")[0]+"' name='sel"+data[i].split(",")[0]+"' style='width:60px'>";
						if(data[i].split(",")[0]=="29"){
							html+="<option value='0'>秒</option><option value='1'>分</option><option value='2'>时</option><option value='3'>天</option>" +
								"</select></td>";
						}else{
							html+="<option value='1'>分</option><option value='2'>时</option><option value='3'>天</option>" +
								"</select></td>";
						}
				if((i+1)%2 != 0){
					html=trHTML1+html;
				}else{
					html=html+trHTML2;
				}
				}
			$('#monitor').append(html);
			for (var i=0;i<data.length;i++){
				$("#ipt_timeUnit"+data[i].split(",")[0]+" ").attr("selected",true);
			}
			
			
		}
	};	
	ajax_(ajax_param);	

}


/**
 * 默认勾选某个监测对象类型包含的监测器
 * @param moClassId
 * @return
 */
function initMoListByTemplateID(templateID){
	$('#monitor').html("");
	var path=getRootName();
	var uri=path+"/monitor/sysMoTemplate/listMoTypeByTemplateID";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"text",
		data:{
			"templateID":templateID,
			"t" : Math.random()
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			var moTypeLstJson = JSON.stringify(data);
			$("#moTypeLstJson").val(moTypeLstJson);
			var html='';
			var trHTML1 = "<tr>"
			var trHTML2 = "</tr>"
			for (var i=0;i<data.length;i++){ 
				html+="<td><input type='checkbox' id='ipt_mo"+i+"' name='moType' value='"+data[i].split(",")[0]+"' checked disabled='disabled'/>&nbsp;&nbsp;&nbsp;&nbsp;"+data[i].split(",")[1]+"</td>" 
						+"<td><input id='ipt_doIntervals"+data[i].split(",")[0]+"' value='"+data[i].split(",")[2]+"' style='width:60px;' readonly/></td>"
						+"<td><select class='inputs' id='ipt_timeUnit"+data[i].split(",")[0]+"' name='sel"+data[i].split(",")[0]+"' style='width:60px' disabled='disabled'>" 
						if(data[i].split(",")[0]=="29"){
							html+="<option value='0'>秒</option><option value='1'>分</option><option value='2'>时</option><option value='3'>天</option>" +
								"</select></td>";
						}else{
							html+="<option value='1'>分</option><option value='2'>时</option><option value='3'>天</option>" +
							"</select></td>";
						}
				if((i+1)%2 != 0){
					html=trHTML1+html;
				}else{
					html=html+trHTML2;
				}
				}
			$('#monitor').append(html);
			for (var i=0;i<data.length;i++){
				$("#ipt_timeUnit"+data[i].split(",")[0]+"  option[value='"+data[i].split(",")[3]+"'] ").attr("selected",true);
			}
		}
	};	
	ajax_(ajax_param);	
}

function toAdd(){
	var result = checkInfo('#monitor');
	if (result == true) {
		var moIDs = $("#moIDs").val();
		var path=getRootName();
		var templateID = $("#ipt_monitorTemplateName").combobox('getValue');
		if (templateID == "-1") {
			if (moIDs.indexOf(",") <= 0) {
				if (flag == true) {
					$.messager.confirm("提示","监测对象已套用过模板，本次设置将取消原有模板套用设置，是否继续操作？",function(r){
						if (r == true) {
							var uri=path+"/monitor/sysMoTemplate/delMoTemplateOfMoDevice";
							var ajax_param={
								url:uri,
								type:"post",
								dateType:"text",
								data:{                
									"moIDs":moIDs,
									"t" : Math.random()
								},
								error : function(){
									$.messager.alert("错误","ajax_error","error");
								},
								success:function(data){
									if (data == true) {
										$.messager.alert("提示","解除模板套用成功！","info");
										$('#popWin').window('close');
									} else {
										$.messager.alert("提示","解除模板套用失败！","info");
									}
								}
							};	
							ajax_(ajax_param);	
						}
					});
				} else {
					$.messager.alert("提示","请选择模板！","info");
				}
			} else {
				$.messager.confirm("提示","部分监测对象已套用过模板，本次设置将取消原有模板套用设置，是否继续操作？",function(r){
					if (r == true) {
						var uri=path+"/monitor/sysMoTemplate/delMoTemplateOfMoDevice";
						var ajax_param={
							url:uri,
							type:"post",
							dateType:"text",
							data:{                
								"moIDs":moIDs,
								"t" : Math.random()
							},
							error : function(){
								$.messager.alert("错误","ajax_error","error");
							},
							success:function(data){
								if (data == true) {
									$.messager.alert("提示","解除模板套用成功！","info");
									$('#popWin').window('close');
								} else {
									$.messager.alert("提示","解除模板套用失败！","info");
								}
							}
						};	
						ajax_(ajax_param);	
					}
				});
			}
		} else {
			var uri=path+"/monitor/sysMoTemplate/addMoTemplateOfMoDevice";
			var ajax_param={
				url:uri,
				type:"post",
				dateType:"text",
				async:false,
				data:{                
					"templateID":templateID,
					"moClassID":$("#moClassID").val(),
					"moIDs":moIDs,
					"t" : Math.random()
				},
				error : function(){
					$.messager.alert("错误","ajax_error","error");
				},
				success:function(data){
//					console.log(data);
					if (data == true) {
						var moClassID =$("#moClassID").val();
						//配置采集任务
						doSetPerfTask(moIDs,templateID,moClassID);
//						$.messager.alert("提示","模板套用成功！","info");
//						$('#popWin').window('close');
					} else {
						$.messager.alert("提示","模板套用失败！","info");
					}
				}
			};	
			ajax_(ajax_param);	
		}
	}

}


function showUsedTemplate(){
	var moIDs = $("#moIDs").val();
	if(moIDs.indexOf(",")<0){
		var path=getRootName();
		var uri=path+"/monitor/sysMoTemplate/getDeviceUsedTemplate";
		var ajax_param={
			url:uri,
			type:"post",
			dateType:"text",
			data:{                
				"moID":moIDs,
				"t" : Math.random()
			},
			error : function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				if(data != null && data != ""){
					initMoListByTemplateID(data.templateID);
					$("#ipt_monitorTemplateName").combobox('setValue',data.templateID);
					flag = true;
				}else{
					var moClassID = $("#moClassID").val();
					doInitMoList(moClassID);
					$("#ipt_monitorTemplateName").combobox('setValue',"-1");
				}
			}
		};	
		ajax_(ajax_param);	
	}else{
		var moClassID = $("#moClassID").val();
		doInitMoList(moClassID);
	}
}

/**
 * 配置采集任务
 */
function doSetPerfTask(moIDs,templateID,moClassID){
	var arrChk= [];
	var selectList= [];
	var molist='';
	var mointerval='';
	$('input:checked[name=moType]').each(function() { 
		arrChk.push($(this).val());
	});
	for ( var int = 0; int < arrChk.length; int++) {
		molist+=arrChk[int]+",";
		var select=$("#ipt_doIntervals"+arrChk[int]).val();
		selectList.push(select);
	}
	for ( var int = 0; int < selectList.length; int++) {
		mointerval+=selectList[int]+",";
	}
//	console.log(molist);
//	console.log(mointerval);
	var moTypeLstJson = $("#moTypeLstJson").val();
//	console.log(moTypeLstJson);
	if(moClassID != 54){
		var path=getRootName();
		var uri=path+"/monitor/perfTask/doSetPerfTask?moIDs="+moIDs+"&templateID="+templateID+"&moTypeLstJson="+moTypeLstJson;
		var ajax_param={
				url:uri,
				type:"post",
				dateType:"text",
				data:{                
			"moClassId":moClassID,
			"status":1,
			"moList" : molist,	
			"moIntervalList":mointerval,
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			if(data == true || data == "true"){
				$.messager.alert("提示","模板套用成功，并且已生效！","info");
				$('#popWin').window('close');
			}else{
				$.messager.alert("提示","应用模板失败！","error");
			}
		}
		};	
		ajax_(ajax_param);
	}else{
		$.messager.confirm("提示","所选中的DB2下的所有实例下的有凭证的数据库都将被创建任务，是否继续？",function(r){
			if (r == true) {
				var path=getRootName();
				var uri=path+"/monitor/perfTask/doSetDb2PerfTask?moIDs="+moIDs+"&templateID="+templateID+"&moTypeLstJson="+moTypeLstJson;
				var ajax_param={
						url:uri,
						type:"post",
						dateType:"text",
						data:{                
					"moClassId":moClassID,
					"status":1,
					"moList" : molist,	
					"moIntervalList":mointerval,
				},
				error : function(){
					$.messager.alert("错误","ajax_error","error");
				},
				success:function(data){
					if(data == true || data == "true"){
						$.messager.alert("提示","模板套用成功，并且已生效！","info");
						$('#popWin').window('close');
					}else{
						$.messager.alert("提示","应用模板失败！","error");
					}
				}
				};	
				ajax_(ajax_param);
			}
		});
	}
}