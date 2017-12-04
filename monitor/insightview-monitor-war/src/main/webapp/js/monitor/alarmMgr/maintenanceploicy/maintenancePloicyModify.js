function toUpdate(){
	var maintainTitle = $("#ipt_maintainTitle").val();
	if(maintainTitle == null || maintainTitle == ""){
		$.messager.alert("提示", "必填项不能为空！", "info");
	}else{
		checkTime();
	}
}

/**
 * 判断维护期标题是否唯一
 * @return
 */
function checkNameUnique2(){
	var ploicyID = $("#ipt_ploicyID").val();
	var maintainTitle = $("#ipt_maintainTitle").val();
	//alert("ploicyID:"+ploicyID+",maintainTitle:"+maintainTitle);
	
	if(maintainTitle != null || maintainTitle != ""){
		var path = getRootName();
		var uri = path + "/monitor/alarmMgr/mPloicy/checkNameUnique2";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"ploicyID" : ploicyID,
				"maintainTitle" : maintainTitle ,
				"t" : Math.random()
			},
			error : function(){
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data){
				if(data == false){
					$.messager.alert("提示", "该维护期标题已存在！", "info");
					$('#ipt_maintainTitle').val("");
					$('#ipt_maintainTitle').focus();
				}else{
					return;
				}
			}
		};
		ajax_(ajax_param);
	}
}

/**
 * 判断维护期开始时间是否晚于结束时间
 * @return
 */
function checkTime(){
	var startTime = $("#ipt_startTime").datetimebox("getValue");
	var endTime  = $("#ipt_endTime").datetimebox("getValue");
	if(endTime != null && startTime != null && endTime >= startTime){
		doUpdate();
	}else{
		$.messager.alert("错误","结束时间早于开始时间,请重新选择！","error");
	}
}

/**
 * 维护期策略修改
 * @param ploicyID
 * @return
 */
function  doUpdate(){
	var ploicyID = $("#ipt_ploicyID").val();
	var moID = $("#ipt_moid").val();
		var path = getRootName();
		var uri = path + "/monitor/alarmMgr/mPloicy/updateMaintenancePloicy";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"ploicyID" : ploicyID,
				"sourceMOID" : moID,
				"maintainTitle" : $("#ipt_maintainTitle").val(),
				"maintainType" : $("#ipt_maintainType").val() ,
				"startTime" : $("#ipt_startTime").datetimebox("getValue") ,
				"endTime" : $("#ipt_endTime").datetimebox("getValue") ,
				"isUsed" : $('input[name="isUsed"]:checked').val()  ,
				"maintainDesc" :  $("#ipt_descr").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					$.messager.alert("提示", "信息修改成功！", "info");
					$('#popWin').window('close');
					window.frames['component_2'].reloadTable();
				} else {
					$.messager.alert("提示", "信息修改失败！", "error");
				}
			}
		}
		ajax_(ajax_param);
}

/**
 * 加载告警源页面
 */
function loadMoRescource(){
	var path=getRootName();
	var uri=path+"/monitor/alarmMgr/mPloicy/toSelectMoRescource?flag=choose&dif=cause";
	window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
}

/**
 * 告警源详情
 */
function findMODeviceInfo(flag){
	var moID = $("#ipt_moid").val();
	var path = getRootName();
	var uri=path+"/monitor/discover/findMODeviceInfo";
	var ajax_param={
		url:uri,
		type:"post",
		datdType:"json",
		data:{
			"moid" : moID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_moname").val(data.moname);
		}
	};
	ajax_(ajax_param);		
}
