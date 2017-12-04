$(document).ready(function() {
	var deviceip = $("#deviceipForDb2").val();
	var dbmstype = $("#dbmstypeForDb2").val();
	var port = $("#portForDb2").val();
	var moid = $("#moidForDb2").val();
	var isForPerfSet = $("#isForPerfSet").val();
	if(isForPerfSet == "true" || isForPerfSet == true){
		$('#selectDbName').combobox({
			panelHeight : '120',
			url : getRootName() + '/monitor/deviceManager/getAllDbNames?moid='+moid,
			valueField : 'databaseName',
			textField : 'databaseName',
			editable : false,
			value : '请选择',
		});
	}else{
		$('#selectDbName').combobox({
			panelHeight : '120',
			url : getRootName() + '/monitor/databaseCommunity/getAllDbNames?deviceip='+deviceip+'&dbmstype='+dbmstype+'&port='+port,
			valueField : 'dbName',
			textField : 'dbName',
			editable : false,
			value : '请选择',
		});
	}
	
});
function doAction(){
	var isForPerfSet = $("#isForPerfSet").val();
//	console.log("isForPerfSet="+isForPerfSet);
	if(isForPerfSet == "true" || isForPerfSet == true){
		toSet();
	}else{
		toRedisCover();
	}
}
function toRedisCover(){
	var dbName = $("#selectDbName").combobox("getValue");
	if(dbName == "请选择" || dbName == "" || dbName == ''){
		$.messager.alert("提示", "数据库名不能为空，请选择！", "info");
	}else{
		$.messager.confirm("提示","确定选择该数据库名进行重新发现?",function(r){
			if (r == true) {
				$('#popWin2').window('close');
				var deviceip = $("#deviceipForDb2").val();
				var moClassId = $("#moClassIdForDb2").val();
				var port = $("#portForDb2").val();
				var path = getRootPatch();
				var uri = path + "/monitor/deviceManager/doRediscover?moClassId="+moClassId+"&deviceip="+deviceip+"&port="+port+"&dbName="+dbName;
				var ajax_param = {
						url : uri,
						type : "post",
						datdType : "json",
						data : {
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error"); 
				},
				success : function(data) {
					if(true == data.flag || "true" == data.flag){
						$('#popWin2').window('close');
						var taskid = data.taskid;
						var dbmstype = $("#dbmstypeForDb2").val();
						var moid = $("#moidForDb2").val();
						showDeviceTask(taskid,moid,deviceip,dbmstype,port);
					}
				}
				}
				ajax_(ajax_param);
			}
		});
	}
}

function showDeviceTask(taskid,moid,deviceip,dbmstype,port){
	var moClassId = $("#moClassIdForDb2").val();
	var dbName = $("#selectDbName").combobox("getValue");
	parent.parent.$('#popWin').window({
		title:'设备任务',
	    width:800,
	    height:300,
	    minimizable:false,
	    maximizable:false,
	    collapsible:false,
	    modal:true,
	    href: getRootName() + '/monitor/deviceManager/showDeviceTask?taskid='+taskid+'&moid='+moid+'&deviceip='+deviceip+'&moClassId='+moClassId+'&port='+port+'&dbName='+dbName
	});
}

function toSet(){
	var moid = $("#moidForDb2").val();
	var deviceip = $("#deviceipForDb2").val();
	var moClassId = $("#moClassIdForDb2").val();
	var port = $("#portForDb2").val();
	var dbName = $("#selectDbName").combobox("getValue");
	var moAlias = $("#moAlias").val();
	if(dbName == "请选择" || dbName == "" || dbName == ''){
		$.messager.alert("提示", "数据库名不能为空，请选择！", "info");
	}else{
		$.messager.confirm("提示","确定选择该数据库名进行采集配置?",function(r){
			if (r == true) {
				$('#popWin2').window('close');
				parent.parent.$('#popWin').window({
					title:'采集设备配置',
					width:800,
					height:500,
					minimizable:false,
					maximizable:false,
					collapsible:false,
					modal:true,
					href: getRootName() + '/monitor/configObjMgr/toSetMonitor?moid='+moid+'&deviceip='+deviceip+'&moClassId='+moClassId+'&port='+port+'&dbName='+dbName+"&moAlias="+moAlias
				});
			}
		});
	}
}