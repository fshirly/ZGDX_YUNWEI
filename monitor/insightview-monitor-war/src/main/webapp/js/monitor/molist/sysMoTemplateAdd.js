/*
 * 选择对象类型
 */
function choseMObjectTree(){
		var path = getRootPatch();
		var uri = path + "/monitor/addDevice/initTree";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"trmnlBrandNm" : "",
				"qyType" : "brandName",
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				dataTreeOrg = new dTree("dataTreeOrg", path
						+ "/plugin/dTree/img/");
				dataTreeOrg.add(0, -1, "选择对象类型", "");

				// 得到树的json数据源
				var datas = eval('(' + data.menuLstJson + ')');
				// 遍历数据
				var gtmdlToolList = datas;
				for (var i = 0; i < gtmdlToolList.length; i++) {
					var _id = gtmdlToolList[i].classId;
					var _nameTemp = gtmdlToolList[i].classLable;
					var _parent = gtmdlToolList[i].parentClassId;
					var className = gtmdlToolList[i].className;
					if((_parent == "1" && _id != 44 && _id != 133 && _id != 134) || _id == 18){
						dataTreeOrg.add(_id, _parent, _nameTemp);
					}else{
						dataTreeOrg.add(_id, _parent, _nameTemp, "javascript:hiddenMObjectTreeSetValEasyUi('divMObject','ipt_moClassId','"
								+ _id + "','"+ className +  "','" + _nameTemp + "');");
					}
					
				}
				$('#dataMObjectTreeDiv').empty();
				$('#dataMObjectTreeDiv').append(dataTreeOrg + "");
				$('#divMObject').dialog('open');
			}
		}
		ajax_(ajax_param);
}


function hiddenMObjectTreeSetValEasyUi(divId, controlId, showId, className ,showVal) {
	$("#" + controlId).val(showVal);
	$("#" + controlId).attr("alt", showId);
	$("#" + divId).dialog('close');
	$("#ipt_classId").val(showId);
	doInitMoList(showId);
	//判断是否为叶子节点
	/**
	var path = getRootName();
	var uri=path+"/monitor/addDevice/isLeaf";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"classID" : showId,
				"t" : Math.random() 
			},
			error : function(){
				$.messager.alert("错误", "ajax_error", "error");
			},
			success:function(data){
				if (true == data || "true" == data) {
					$("#" + controlId).val(showVal);
					$("#" + controlId).attr("alt", showId);
					$("#" + divId).dialog('close');
					$("#ipt_classId").val(showId);
					doInitMoList(showId);
				}
			}
		};
		ajax_(ajax_param);	
		*/
}

/**
 * 根据moClassId获取监测器信息
 * @param moClassId
 * @return
 */
function doInitMoList(moClassID){
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
						+"<td><select class='inputs' id='ipt_timeUnit"+data[i].split(",")[0]+"' name='sel"+data[i].split(",")[0]+"' style='width:60px'>" ;
						if(data[i].split(",")[0]=="29"){
							html+="<option value='0'>秒</option><option value='1'>分</option><option value='2'>时</option><option value='3'>天</option>" +
								"</select></td>";
						}else{
							html+= "<option value='1'>分</option><option value='2'>时</option><option value='3'>天</option>" +
							"</select></td>";
						}
//				html+="<td><input type='checkbox' id='ipt_mo"+i+"' name='moType' value='"+data[i].split(",")[0]+"' checked/>&nbsp;&nbsp;&nbsp;&nbsp;"+data[i].split(",")[1]
//				       +"&nbsp;&nbsp;&nbsp;&nbsp;<input id='ipt_doIntervals"+data[i].split(",")[0]+"' value='120' style='width:60px;'/>"
//				       +"&nbsp;&nbsp;&nbsp;&nbsp;<select class='inputs' id='ipt_timeUnit"+data[i].split(",")[0]+"' name='sel"+data[i].split(",")[0]+"' style='width:60px'>" +
//				       "<option value='1'>分</option><option value='2'>时</option><option value='3'>天</option>" +
//				       "</select></td>";
				if((i+1)%2 != 0){
					html=trHTML1+html;
				}else{
					html=html+trHTML2;
				}
				}
			$('#monitor').append(html);
			for (var i=0;i<data.length;i++){
				$("#ipt_timeUnit"+data[i].split(",")[0]+"  option[value='1'] ").attr("selected",true);
			}
			
			//下拉框默认展示采集周期默认值
			var collectPeriod = $("#collectPeriod").val();
			if(collectPeriod != -1){
				$("#monitor").find("select").val(collectPeriod);
			}
			
		}
	};	
	ajax_(ajax_param);	

}



function toAdd(){
	var arrChk= [];
	var selectList= [];
	var molist='';
	var mointerval='';
	var motimeunit='';
	var path=getRootName();
	var result = checkInfo('#monitor');
	if (result == true) {
		if ($("#ipt_templateName").val() == "") {
			$.messager.alert("提示","模板名称不能为空！","info");
		} else {
			$('input:checked[name=moType]').each(function() { 
				arrChk.push($(this).val());
			});
			if(arrChk.length==0){
				$.messager.alert("提示","至少选择一个监测器！","info");
			}else{
				for ( var int = 0; int < arrChk.length; int++) {
					molist+=arrChk[int]+",";
					mointerval+=$("#ipt_doIntervals"+arrChk[int]).val()+",";
					var select=$("#ipt_timeUnit"+arrChk[int]).val();
					selectList.push(select);
				}
				for ( var int = 0; int < selectList.length; int++) {
					motimeunit+=selectList[int]+",";
					
				}
				var uri=path+"/monitor/sysMoTemplate/checkMoTemplateName";
				var ajax_param={
					url:uri,
					type:"post",
					dateType:"text",
					data:{                
						"templateName":$("#ipt_templateName").val(),
						"t" : Math.random()
					},
					error : function(){
						$.messager.alert("错误","ajax_error","error");
					},
					success:function(data){
//						console.log("data == "+data);
						if (data > 0) {
							$.messager.alert("提示","模板名称重复！","info");
						} else {
							var uri=path+"/monitor/sysMoTemplate/addMoTemplate";
							var ajax_param={
								url:uri,
								type:"post",
								dateType:"text",
								data:{                
									"templateName":$("#ipt_templateName").val(),
									"moClassID":$("#ipt_moClassId").attr("alt"),
									"descr":$("#ipt_descr").val(),
									"moList":molist,
									"moIntervalList":mointerval,
									"moTimeUnitList":motimeunit,
									"t" : Math.random()
								},
								error : function(){
									$.messager.alert("错误","ajax_error","error");
								},
								success:function(data){
									if (data == true) {
										$.messager.alert("提示","监测模板新增成功！","info");
										$('#popWin').window('close');
										window.frames['component_2'].reloadTable();
									} else {
										$.messager.alert("提示","监测模板新增失败！","info");
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
}