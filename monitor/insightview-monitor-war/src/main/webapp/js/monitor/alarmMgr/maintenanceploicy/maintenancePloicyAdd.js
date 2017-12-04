/**
 * 新增维护期策略
 * @return
 */
function toAdd(){
	checkMONameUnique();
}

/**
 * 判断维护期标题是否唯一
 * @return
 */
function checkNameUnique(){
	var maintainTitle = $("#ipt_maintainTitle").val();
	if(maintainTitle != null || maintainTitle != ""){
		var path = getRootName();
		var uri = path + "/monitor/alarmMgr/mPloicy/checkNameUnique";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
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
		doAdd();
	}else{
		$.messager.alert("错误","结束时间早于开始时间 ,请重新选择！","error");
	}
}

/**
 * 判断事件源是否唯一
 * @return
 */
function checkMONameUnique(){
	var moName = $("#ipt_moname").val();
	var moid = $("#ipt_moid").val();
	var maintainTitle = $("#ipt_maintainTitle").val();
	var startTime = $("#ipt_startTime").datetimebox("getValue");
	var endTime  = $("#ipt_endTime").datetimebox("getValue");
	var maintainType = $("#ipt_maintainType").val();
	if(checkInfo('#divAddInfo')){
//	if(moName != null && moName != "" && maintainTitle != null && maintainTitle != "" && maintainType != null && maintainType != "" &&
//			startTime != null && startTime != "" && endTime != null && endTime != ""){
		var path = getRootName();
		var uri = path + "/monitor/alarmMgr/mPloicy/checkMONameUnique";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"moname" : moName,
				"sourceMOID" : moid,
				"t" : Math.random()
			},
			error : function(){
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data){
				if(data == false){
					$.messager.alert("提示", "该事件源对象已存在！", "info");
					$('#ipt_moname').val("");
					$('#ipt_moname').focus();
				}else{
					checkTime();
					return;
				}
			}
		};
		ajax_(ajax_param);
	}
//	else{
//		$.messager.alert("提示","必填项不能为空！","info");
//	}
}

function doAdd(){
	var moid = $("#ipt_moid").val();
		var path = getRootName();
		var uri = path + "/monitor/alarmMgr/mPloicy/addMaintenancePloicy";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"maintainTitle" : $("#ipt_maintainTitle").val(),
				"sourceMOID" : $("#ipt_moid").val(),
				"maintainType" : $("#ipt_maintainType").val(),
				"startTime" : $("#ipt_startTime").datetimebox("getValue"),
				"endTime" : $("#ipt_endTime").datetimebox("getValue"),
				"isUsed" : $('input[name="isUsed"]:checked').val(),
				"maintainDesc" : $("#ipt_descr").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
					$.messager.alert("提示", "新增成功！", "info");
					$('#popWin').window('close');
					window.frames['component_2'].reloadTable();
				} else {
					$.messager.alert("提示", "新增失败！", "error");
				}
			}
		};
			ajax_(ajax_param);
}

/**
 * 加载告警源页面
 */
function loadMoRescource(){
	var path=getRootName();
	var uri=path+"/monitor/alarmMgr/mPloicy/toSelectMoRescource?flag=choose&dif=cause";
//	window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	$('#event_select_dlg').dialog({
	    title: '告警源选择',
	    width: 620,
	    height: 560,
	    modal: true,
		    onBeforeOpen : function(){
            if($(".event_select_dlg").size() > 1){
                $('.event_select_dlg:first').parent().remove();
            }
        },
	    href: uri
	});	
}

function showDeviceInfo(moid,moname){
//	console.log("moid===="+moid+" ,moname="+moname);
	$("#ipt_moid").val(moid);
	$("#ipt_moname").val(moname);
}
/**
 * 告警源详情
 */
function findMODeviceInfo(flag){
	var moid = $("#ipt_moid").val();
	var path = getRootName();
	var uri=path+"/monitor/discover/findMODeviceInfo";
	var ajax_param={
		url:uri,
		type:"post",
		datdType:"json",
		data:{
			"moid" : moid,
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