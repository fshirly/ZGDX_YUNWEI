$(document).ready(function() {
	if ($("#ipt_middleWareName").val() == 2) {
		$("#ipt_middleWareType").val("tomcat");
	} else if ($("#ipt_middleWareName").val() == 3) {
		$("#ipt_middleWareType").val("websphere");
	} else if ($("#ipt_middleWareName").val() == 1) {
		$("#ipt_middleWareType").val("weblogic");
	} 
});

function setMonitor(){
	var result = checkInfo("#tblMiddlewareAdd");
	if(result == true){
		checkCommunity();
	}
}

/**
 * 验证中间件凭证是否存在
 * @return
 */
function checkCommunity(){
	var deviceIP = $("#ipt_deviceIp").val();alert("ip:"+deviceIP)
	var middleWareName = $("#ipt_middleWareName").val();
	var path = getRootName();
	var uri = path + "/monitor/MiddleWareCommunity/checkCommunity?flag=add";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"ipAddress" : deviceIP,
			"middleWareName" : middleWareName,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (false == data || "false" == data) {
				$.messager.alert("提示", "该IP在中间件凭证中已存在！", "error");
			} else {
				addCommunity();
				return;
			}
		}
	};
	ajax_(ajax_param);
}

/**
 * 新增
 * @return
 */
function addCommunity(){
	var path = getRootName();
	var uri = path + "/monitor/MiddleWareCommunity/addCommunity";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"ipAddress" :$("#ipt_deviceIp").val(),
			"middleWareName" : $("#ipt_middleWareName").val(),
			"middleWareType" : $("#ipt_middleWareType").val(),
			"port" : $("#ipt_port").val(),
			"userName" : $("#ipt_userName").val(),
			"passWord" : $("#ipt_passWord").val(),
			"domainID" : $("#ipt_domainID").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				$.messager.alert("提示", "中间件凭证添加成功！", "info");
				$('#popWin').window('close');
				window.frames['component_2'].reloadTable();
			} else {
				$.messager.alert("提示", "中间件凭证添加失败！", "error");
			}

		}
	};
	ajax_(ajax_param);
}



//阈值配置
function setThreshold(){
	doAdd();
}

/*
 * 加载管理对象
 */
function loadMoRescource(){
	var classID = $("#ipt_classID").val();alert(classID);
	var sourceMOID = $("#ipt_sourceMOID").val();
	var index = 1;
//	console.log("加载对象classID=="+classID);
	var parentClassID = $("#parentId").val();
	if(null != classID && ""!= classID){
		var path=getRootName();
		if(classID==2||classID==3||classID==4||classID==5||classID==6||classID==7||classID==8||classID==9){
			var uri=path+"/monitor/discover/toDiscoverDeviceList?flag=choose&index="+index;
			window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
		}else if(null != sourceMOID && ""!= sourceMOID){
			if(classID==10){
				var uri=path+"/monitor/alarmmgr/moKPIThreshold/toNetworkIf?flag=choose&deviceMOID="+sourceMOID;
			}else if(classID ==11){
				var uri=path+"/monitor/alarmmgr/moKPIThreshold/toVolumeList?flag=choose&deviceMOID="+sourceMOID;
			}else if(classID==12){
				var uri=path+"/monitor/alarmmgr/moKPIThreshold/toCPUsList?flag=choose&deviceMOID="+sourceMOID;
			}else if(classID==13){
				var uri=path+"/monitor/alarmmgr/moKPIThreshold/toMemoriesList?flag=choose&deviceMOID="+sourceMOID;
			}
			window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
		}else{
			$.messager.alert('提示', '请先选择源对象', 'error');
		}
	}else{
		$.messager.alert('提示', '请先选择对象类型', 'error');
	}
}

/*
 * 加载指标
 */
function loadPerfKPIDef(){
	var classID =$("#ipt_classID").val();
		var path=getRootName();
		var uri=path+"/monitor/alarmmgr/moKPIThreshold/toSelectPerfKPIDef?flag=choose&classID="+classID;
		window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
}

/*
 * 指标详情
 */
function findPerfKPIDefInfo(){
	var kpiID = $("#ipt_kpiID").val();
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/findPerfKPIDef";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"kpiID" : kpiID,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			$("#ipt_kpiName").val(data.name);
		}
	};
	ajax_(ajax_param);		
}

/*
 * 新增
 */
function doAdd(){
	var classID =$("#ipt_classID").val();
	var sourceMOID = $("#ipt_sourceMOID").val();
	var moID = -1;
	if(classID==2||classID==3||classID==4||classID==5||classID==6||classID==7||classID==8||classID==9){
		moID=-1;
	}else{
		moID = $("#ipt_moID").val();
	}
	var kpiID = $("#ipt_kpiID").val();
	var upThreshold = $("#ipt_upThreshold").val();
	var downThreshold = $("#ipt_downThreshold").val();
	var descr = $("#ipt_descr").val();
	var result=checkInfo('#divThresholdAdd');
	var path = getRootName();
	var uri=path+"/monitor/alarmmgr/moKPIThreshold/addThreshold";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"json",
		data:{
			"classID" : classID,
			"moID" : moID,
			"sourceMOID" : sourceMOID,
			"kpiID" : kpiID,
			"upThreshold" : upThreshold,
			"downThreshold" : downThreshold,
			"descr" : descr,
			"t" : Math.random() 
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			if (data == true) {
				$.messager.alert("提示", "阈值规则定义新增成功！", "info");
			} else {
				$.messager.alert("提示", "阈值规则定义新增失败！", "error");
			}
		}
	};	
	if(result == true ){
			ajax_(ajax_param);
	}
}


//监测器配置

/**
 * 显示根据任务ID列表出来的监测器
 * @param taskId
 * @return
 */
function toShowMo(taskId){
	$('#monitorSetting').dialog('open');
	doInitMoList(taskId);
	toCheckMo(taskId);
}

/**
 * 默认勾选某条任务包含的监测器
 * @param taskId
 * @return
 */
function toCheckMo(taskId){
	var path=getRootName();
	var uri=path+"/monitor/perfTask/listMoListByTaskId";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"text",
		data:{
			"taskId":taskId,
			"t" : Math.random()
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			for (var i=0;i<data.length;i++){ 
				var checkboxs=document.getElementsByName('moType');
				for(var j = 0; j < checkboxs.length;j++){
					var mid=data[i].split(",")[0];
					var interval=data[i].split(",")[1];
					 if(checkboxs[j].value==mid)
				     { 
						 checkboxs[j].checked=true;
						 document.getElementsByName('sel'+mid)[0].value=interval/60;
				     }
				}
				}
		}
	};	
	ajax_(ajax_param);	
}


/**
 * 根据任务ID获取监测器信息
 * @param taskId
 * @return
 */
function doInitMoList(){
	var deviceId=$("#ipt_moid").val(); 
	var	moID = $("#ipt_moID").val();
	var path=getRootName();
	var uri=path+"/monitor/perfTask/listMoList";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"text",
		async : false,
		data:{
			"moId":moID,
			
			"t" : Math.random()
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			/**
			var interval='';
			var html='';
			for (var i=0;i<data.length;i++){ 
				html+="<input type='checkbox' id='ipt_mo"+i+"' name='moType' value='"+data[i].split(",")[0]+"' checked/>&nbsp;&nbsp;&nbsp;&nbsp;"+data[i].split(",")[1]+"</br></br>"; 
				interval+="<select class='inputs' id='ipt_doIntervals"+data[i].split(",")[0]+"' name='sel"+data[i].split(",")[0]+"' style='width:60px'>" +
						"<option value='2'>2</option><option value='5'>5</option><option value='10'>10</option>" +
						"<option value='15'>15</option><option value='20'>20</option><option value='30'>30</option>" +
						"<option value='60'>60</option><option value='90'>90</option><option value='120'>120</option>" +
						"</select>&nbsp;min"+"</br></br>";
				}
			document.getElementById("divEditMo").innerHTML=html;
			document.getElementById("divEditMoInterval").innerHTML=interval;
			*/
			var html='';
			var trHTML1 = "<tr>"
			var trHTML2 = "</tr>"
			for (var i=0;i<data.length;i++){ 
				html+="<td><input type='checkbox' id='ipt_mo"+i+"' name='moType' value='"+data[i].split(",")[0]+"' checked/>&nbsp;&nbsp;&nbsp;&nbsp;"+data[i].split(",")[1]+"</td>" 
						+"<td><select class='inputs' id='ipt_doIntervals"+data[i].split(",")[0]+"' name='sel"+data[i].split(",")[0]+"' style='width:60px'>" +
						"<option value='2'>2</option><option value='5'>5</option><option value='10'>10</option>" +
						"<option value='15'>15</option><option value='20'>20</option><option value='30'>30</option>" +
						"<option value='60'>60</option><option value='90'>90</option><option value='120'>120</option>" +
						"</select>&nbsp;min</td>";
				if((i+1)%2 != 0){
					html=trHTML1+html;
				}else{
					html=html+trHTML2;
				}
				}
			$('#monitor').append(html);
			
			var flag=$("#flag").val();
			if(flag=="edit"){
				var checkboxs=document.getElementsByName('moType');
				for(var j = 0; j < checkboxs.length;j++){
					checkboxs[j].checked=false;
				}
				toCheckMo(taskId);
			}
			
		}
	};	
	ajax_(ajax_param);	
}

function doSet(){
	doAddTask();
}

/**
 * 新增监测器配置信息
 * @return
 */
function doAddTask(){
	var arrChk= [];
	var selectList= [];
	var molist='';
	var mointerval='';
	var moClassId = $('#ipt_moClassId').val(); 
	var taskId = $('#ipt_taskId').val(); 
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
		var path=getRootName();
		var uri=path+"/monitor/deviceManager/addPerfTask";
		
		var ajax_param={
				url:uri,
				type:"post",
				dateType:"json",
				data:{									
					"moList" : molist,	
					"moIntervalList":mointerval,
					"taskId" : taskId,
					"t" : Math.random()
		},
		error:function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			if(data==-1){
				$.messager.alert("提示","新增配置成功！","info");
			}else{
				$.messager.alert("提示","新增配置失败！","error");
			}
		}
		};
			ajax_(ajax_param);
	}

function closeWindow(){
	$('#popWin').window('close');
}
