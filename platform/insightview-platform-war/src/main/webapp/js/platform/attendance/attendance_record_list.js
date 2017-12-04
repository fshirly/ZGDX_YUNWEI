f.namespace('platform.attendanceRecord');
platform.attendanceRecord = (function(){
var attendanceRecordInfo={
	reloadTable:function(){
	  reloadTable();
	},
	doCheckIn : function(attPeopleId,attPeriodId) {
		doCheckIn(attPeopleId,attPeriodId);
	}
};
$(function() {
	var path = getRootName();
	getTime();
	
	doInitTable();
	$("#currAttPlanConf").combobox({
		url : path + '/attendanceRecord/toAttendancePlanList',
		valueField : 'attendanceId',
		textField : 'planName',
		onSelect : function(d) {
			var queryParams = {
				    "attPlanId" : d.attendanceId
			};
			$('#tblCurAttRecordInfo').datagrid('options').queryParams=queryParams;
			$('#tblCurAttRecordInfo').datagrid('load');
			$('#tblCurAttRecordInfo').datagrid('uncheckAll');
			toCountUnchkAttRecInfo();
		},
		onLoadSuccess : function(){
			var data = $("#currAttPlanConf").combobox("getData");
			if(data.length > 0){
				$("#currAttPlanConf").combobox("select",data[0].attendanceId);
			}
			reloadTable();
			toCountUnchkAttRecInfo();
		}
	});
	
	$("#signPlan").combobox({
	   url : path + '/attendanceRecord/toAttendancePlanList',
	   valueField : 'attendanceId',
	   textField : 'planName',
	   onSelect : function(d) {
		    var signPlan = $("#signPlan").combobox("getValue");
			var startTime = $("#startTime").datebox("getValue");
			var endTime = $("#endTime").datebox("getValue");
			var queryParams = {
				    "AttPlanId" : signPlan,
				    "AttStartTime" : startTime,
				    "AttEndTime" : endTime
			};
			$('#tblHisAttRecordInfo').datagrid('options').queryParams=queryParams;
			$('#tblHisAttRecordInfo').datagrid('load');
			$('#tblHisAttRecordInfo').datagrid('uncheckAll');
		},
	   onLoadSuccess : function(){
			var data = $("#signPlan").combobox("getData");
			if(data.length > 0){
				$("#signPlan").combobox("select",data[0].attendanceId);
			}
			
			toCountUnchkAttRecInfo();
			reloadHisTable();
	   }
	});
	initHisTable();
	
	hisTableReload();
	resetButton();
	
});

function toCountUnchkAttRecInfo() {
	var attPlanId = $("#currAttPlanConf").combobox("getValue");
	var queryParams = {
		"AttPlanId" : attPlanId	
	};
	var uri = getRootName() + "/attendanceRecord/toCountUnchkAttRecInfo";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : queryParams,
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if(data) {
				$('#unchkCount').text(data.uncheckedCount);
			}
			
		}
	}
	ajax_(ajax_param);
}

function compareTime(nowTime,startTime,endTime){
	var nT = parseInt(nowTime);
	var sT = parseInt(startTime);
	var eT = parseInt(endTime);
	var flag = 2;
	if(nT < sT){
		flag = 1;
	}else if(nT > eT){
		flag = 3;
	}
	return flag;
}
/*
 * 初始化表格
 */
function doInitTable(){
	var path = getRootName();
	var uri=path+'/attendanceRecord/toAttendanceRecordList';
	$('#tblCurAttRecordInfo').datagrid({
		width : 'auto',
		height : 'auto',
	    nowrap : true,
		striped : true,
		border : true,
		collapsible : false,// 是否可折叠的
		fit : true,// 自动大小
		fitColumns:true,
		url : uri,
		remoteSort : true,					
		idField : 'id',
		singleSelect : false,// 是否单选
		checkOnSelect : false,
		selectOnCheck : true,
		pagination : true,// 分页控件
		rownumbers : false,// 行号
		columns : [ [
		   {
			 field : 'attOrderId',
		     title : '<strong>签到次序</strong>',
			 width : 120,
			 align:'center',
			 formatter :function(value,row,index){
				 var inx = ++index;
				 return "第"+ inx +"次签到";
			 }
		   },{
			 field : 'startTime',
			 title : '<strong>规定时间（起）</strong>',
		     width : 120, 
		     align:'center'
		     
		   },{
		   	 field : 'endTime',
		   	 title : '<strong>规定时间（止）</strong>',
		   	 width : 120,
		   	 align : 'center'
		   },{
		   	 field : 'attTime',
		   	 title : '<strong>签到时间</strong>',
		   	 width : 120,
		   	 align : 'center',
		   	 formatter : function(value, row, index) {
		   	 	if(value) {
		   	 		return formatDate(new Date(value), "hh:mm");
		   	 	}else{
		   	 	   if(row.attTime =="null" || row.attTime ==null){
             	       var nowTime = $("#nowTime_hidden").val();
					   var startTime = row.startTime.replace(/[^0-9]/ig,"")+"00";
					   var endTime = row.endTime.replace(/[^0-9]/ig,"")+"00";
					   if(compareTime(nowTime,startTime,endTime)==3){
		   	 	           return '<font color="red">未签到</font>';
		   	 	       }else{
		   	 	    	   return '未签到';
		   	 	    	}
		   	 	   }
		   	 	}
		   	 }
		   },{
				field : 'ids',
				title : '<strong>操作</strong>',
				width : 180,
				align : 'center',
				formatter : function(value, row, index) {
                   if(row.attTime =="null" || row.attTime ==null){
                	   var nowTime = $("#nowTime_hidden").val();
   					   var startTime = row.startTime.replace(/[^0-9]/ig,"")+"00";
   					   var endTime = row.endTime.replace(/[^0-9]/ig,"")+"00";
   					   if(compareTime(nowTime,startTime,endTime)==2){
   						  return '<a style="cursor: pointer;" onclick="javascript:platform.attendanceRecord.doCheckIn('
   						  + row.attPeopleId
   						  +','
   						  + row.attPeriodId
   						  +');">签到</a>';
   					  }else if(compareTime(nowTime,startTime,endTime)==1){
   						return '不在签到时间段';
   					}	
                   }
					
				}
			} 
	    ]]
		
	});
	// 浏览器窗口大小变化时，datagrid表格自适应窗口大小变化
	$(window).resize(function() {
		$('#tblCurAttRecordInfo').resizeDataGrid(0, 0, 0, 0);
	});
}

function initHisTable(){
	var path = getRootName();
	var uri = path+"/attendanceRecord/toHisAttRecordList";
	$("#tblHisAttRecordInfo").datagrid({
		width : 'auto',
		height : 'auto',
	    nowrap : true,
		striped : true,
		border : true,
		collapsible : false,
		fit : true,
		fitColumns:true,
		url : uri,
		remoteSort : true,					
		idField : 'id',
		singleSelect : false,
		checkOnSelect : false,
		selectOnCheck : true,
		pagination : true,
		rownumbers : true,
		columns : [ [
		   {
			 field : 'attDate',
		     title : '<strong>签到日期</strong>',
			 width : 120,
			 align:'center',
			 formatter : function(value, row, index) {
				 if(value) {
					 return formatDate(new Date(value), "yyyy-MM-dd");
				 }
				 
			 }
		   },{
			 field : 'signTimeSlot',
			 title : '<strong>签到时间段</strong>',
		     width : 300, 
		     align:'center',
		     formatter : function(value,row,index){
		    	 return getSignTable(row);
		     }
		   }
	    ]]
	});
	// 浏览器窗口大小变化时，datagrid表格自适应窗口大小变化
	$(window).resize(function() {
		$('#tblHisAttRecordInfo').resizeDataGrid(0, 0, 0, 0);
	});
}

/**
 *二维数组 row.signTimeSlot
 * [["11:20-12:00","9:30"],["11:20-12:00","未签到"],[],[]]
 * 
 */
function getSignTable(row){
	var signJsonArray = row.signTimeSlot;//二维数组
	var newTableHeader = "<table id='signTimeSlot' style='width:100%;height:100%'><tr>";
	var newTableContent = "</tr><tr>";
	var newTableEnd = "</tr></table>";
	var newTable = "";
	$('#signTimeSlot').closest('div').css('width','100%');
	for(var i=0;i < signJsonArray.length;i++){
	   var newArr =  signJsonArray[i];
	   if( i == signJsonArray.length-1){
		   newTableHeader+= "<td style='border-right:0;'>"+newArr[0]+"</td>";
		   if(newArr[1] =="未签到"){
			   newTableContent+="<td style='border-right:0;'><font color='red'>"+newArr[1]+"</font></td>";
		   }else{
			   newTableContent+="<td style='border-right:0;'>"+newArr[1]+"</td>";
		   }   
	   }else{
		   newTableHeader+= "<td>"+newArr[0]+"</td>";
		   if(newArr[1] =="未签到"){
			   newTableContent+="<td><font color='red'>"+newArr[1]+"</font></td>";
		   }else{
			   newTableContent+="<td>"+newArr[1]+"</td>";
		   }
		  
	   }
	   
	}
	newTable = newTableHeader + newTableContent + newTableEnd;
	return newTable;
}

/**
 * 查询配置项信息 条件(根据查询条件查询配置项信息)
 */

function reloadTable() {
	var queryParams = {
		"attPlanId" : $('#currAttPlanConf').combobox('getValue')
	};
	$('#tblCurAttRecordInfo').datagrid('options').queryParams=queryParams;
	$('#tblCurAttRecordInfo').datagrid('load');
	$('#tblCurAttRecordInfo').datagrid('uncheckAll');
}

function reloadHisTable() {
	var signPlan = $("#signPlan").combobox("getValue");
	var startTime = $("#startTime").datebox("getValue");
	var endTime = $("#endTime").datebox("getValue");
	var queryParams = {
		"AttPlanId" : signPlan,
		"AttStartTime" : startTime,
		"AttEndTime" : endTime
	};
	$('#tblHisAttRecordInfo').datagrid('options').queryParams=queryParams;
	$('#tblHisAttRecordInfo').datagrid('load');
	$('#tblHisAttRecordInfo').datagrid('uncheckAll');
}

function hisTableReload(){
	$("#reloadTable").click(function(){
		var signPlan = $("#signPlan").combobox("getValue");
		var startTime = $("#startTime").datebox("getValue");
		var endTime = $("#endTime").datebox("getValue");
		if(compareDay(startTime,endTime) ===true){
			var queryParams = {
				    "AttPlanId" : signPlan,
				    "AttStartTime" : startTime,
				    "AttEndTime" : endTime
			};
			$('#tblHisAttRecordInfo').datagrid('options').queryParams=queryParams;
			$('#tblHisAttRecordInfo').datagrid('load');
			$('#tblHisAttRecordInfo').datagrid('uncheckAll');
		}
	});
}

function compareDay(startDay,endDay){
	var sD = parseInt(startDay.split("-")[0] + startDay.split("-")[1] + startDay.split("-")[2]);
	var eD = parseInt(endDay.split("-")[0] + endDay.split("-")[1] + endDay.split("-")[2]);
	if(sD > eD){
		$.messager.alert("提示","起始时间不能大于结束时间，请重新设置时间段","info");
		return false;
	}
	else{
		return true;
	}
}
function resetButton(){
	$('#resetButton').click(function(){
		//$("#signPlan").combobox("setValue","-1");
		$('#signPlan').combobox({
			   url : getRootName() + '/attendanceRecord/toAttendancePlanList',
			   valueField : 'attendanceId',
			   textField : 'planName',
			   onSelect : function(d) {
				    var signPlan = $("#signPlan").combobox("getValue");
					var startTime = $("#startTime").datebox("getValue");
					var endTime = $("#endTime").datebox("getValue");
					var queryParams = {
						    "AttPlanId" : signPlan,
						    "AttStartTime" : startTime,
						    "AttEndTime" : endTime
					};
					$('#tblHisAttRecordInfo').datagrid('options').queryParams=queryParams;
					$('#tblHisAttRecordInfo').datagrid('load');
					$('#tblHisAttRecordInfo').datagrid('uncheckAll');
				},
			   onLoadSuccess : function(){
					var data = $('#signPlan').combobox('getData');
					if(data.length > 0){
						$('#signPlan').combobox('select',data[0].attendanceId);
					}
					
					toCountUnchkAttRecInfo();
					reloadHisTable();
			   }
		});
		$('#startTime').datebox('setValue','');
		$('#endTime').datebox('setValue','');
	});
}
/*
 * 签到
 */
function doCheckIn(attPeopleId,attPeriodId){
	var path = getRootName();
	$.messager.confirm("提示", "确定签到?", function(r) {
		if (r == true) {
			var uri = path + "/attendanceRecord/toCheckInAttendanceRecord?attPeopleId="+attPeopleId+"&attPeriodId="+attPeriodId;
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
					if (true == data || "true" == data) {
						$.messager.alert("提示", "签到成功！", "info");
						reloadTable();
						initHisTable();
					} 
				}
			}
			ajax_(ajax_param);
		}
	});
}
return attendanceRecordInfo;
})();

function getTime(){
	var paddNum = function(num) {
		num += "";
		return num.replace(/^(\d)$/, "0$1");
	};
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	var day = date.getDate();
	var hour = paddNum(date.getHours()+0);
	var minute = paddNum(date.getMinutes()+0);
	var second = paddNum(date.getSeconds()+0);
	var nowTime = "当前时间："+year +"年"+month+"月"+day+"日"+" "+hour+":"+minute+":"+second;
	var hiddenTime = hour+minute+second;
	$("#nowTime").text(nowTime);
	$("#nowTime_hidden").val(hiddenTime);
	setTimeout("getTime()",1000)
}