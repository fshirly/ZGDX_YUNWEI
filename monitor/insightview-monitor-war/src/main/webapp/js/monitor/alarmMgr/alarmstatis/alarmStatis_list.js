$(document).ready(function () {	
	selectViewPic();
	$("#viewPic").combobox({
		onChange: function (n,o) {
			var  viewPic=n;
			if(viewPic==1){
				$("#barId").hide();		
				$("#pieId").show();
			}else{
				$("#pieId").hide();	
				$("#barId").show();
			}
		}
	});
});



/**
 * 查询
 * @return
 */
function reloadTable(){
	var timeBegin=$('#timeBegin').datetimebox('getValue');
	var timeEnd=$('#timeEnd').datetimebox('getValue');
	var start = new Date(timeBegin.replace("-", "/").replace("-", "/"));
	var end = new Date(timeEnd.replace("-", "/").replace("-", "/"));
	if (end < start) {
		$.messager.alert('提示', '结束时间不得早于开始有效时间', 'error');
	}else{
		var  viewPic=$("#viewPic").val();
		var  alarmStatus=$("#alarmStatus").combobox("getValue");
		alarmStatus = (alarmStatus==''?0:alarmStatus);
		var  param = "?timeBegin="+timeBegin+"&timeEnd="+timeEnd+"&viewPic="+viewPic+"&alarmStatus="+alarmStatus;
		window.location.href=getRootName()+"/monitor/alarmStatis/toAlarmStatisList"+param;
	}	
}
/**
 * 选择显示方式
 * @return
 */
function selectViewPic(){
	var  viewPic=$("#viewPic").val();
	if(viewPic==1){
		$("#barId").hide();		
		$("#pieId").show();
	}else{
		$("#pieId").hide();	
		$("#barId").show();
	}	
}

function resetFilterForm(divFilter) {
	resetForm(divFilter);
	$("#alarmStatus").combobox("setValue", "");
	$("#alarmLevel").combobox("setValue", "");
	$("#alarmType").combobox("setValue", "");
}

/**
 * 跳转至活动告警界面 
 */
function toAlarm(filtername,id){
	var timeBegin=$('#timeBegin').datetimebox('getValue');
	var timeEnd=$('#timeEnd').datetimebox('getValue');
	var start = new Date(timeBegin.replace("-", "/").replace("-", "/"));
	var end = new Date(timeEnd.replace("-", "/").replace("-", "/"));
	if (end < start) {
		$.messager.alert('提示', '结束时间不得早于开始有效时间', 'error');
	}else{
		var  alarmStatus=$("#alarmStatus").combobox("getValue");
		alarmStatus = (alarmStatus==''?0:alarmStatus);
		var  param = "&timeBegin="+timeBegin+"&timeEnd="+timeEnd+"&alarmStatus="+alarmStatus;
		window.open(getRootName() + '/monitor/alarmActive/toStatisAlarmActiveList?filtername='+filtername+'&id='+id+param);
	}
	
//	console.log("filtername===="+filtername+" , id ==="+id);
//	parent.$('#popWin').window({
//    	title:'活动告警事件',
//        width:800,
//        height:500,
//        minimizable:false,
//        maximizable:false,
//        collapsible:false,
//        modal:true,
//        href: getRootName() + '/monitor/alarmActive/toAlarmActiveList?filtername='+filtername+'&id='+id,
//    });
	//window.location.href=getRootName() + '/monitor/alarmActive/toAlarmActiveList?filtername='+filtername+'&id='+id;
//	var uri=  getRootName() + '/monitor/alarmActive/toAlarmActiveList?filtername='+filtername+'&id='+id;
//	window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
}