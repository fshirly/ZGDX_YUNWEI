<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>签到统计打印</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/base.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/css/reset.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugin/select2/select2.css"/>
<!-- mainframe -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonUtil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/base64.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/plugin/select2/select2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/commonPlugins.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/printarea/jquery.PrintArea.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/permission/LRSelect.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/platform/attendance/attendance_record_print.js"></script>


<script type="text/javascript">
var attPlanId = "${attPlanId}";
var userId = "${userId}";
var hasUncheckedIn = "${hasUncheckedIn}";
var checkInStartTime = "${checkInStartTime}";
var checkInEndTime = "${checkInEndTime}";
var title = "${title}";
var pageSize = "${pageSize}";
var pageNo = "${pageNo}";
var contextPath = "${pageContext.request.contextPath}"; 
</script>

</head>
<body>
<div id="divPrintArea">
<div id="printTitle" style="width:800px;height:20px;position:relative;text-align:center;">
<span id="title"></span>
</div>
<div id="divData" class="datas" style="top:25px;width:800px;">
	<table id="printDataGrid"></table>
</div>
</div>

<div id="divPrintBottom" >
	<table id="printDataGrid" ></table>
	<div class="conditionsBtn">
		<button type="button" onclick="doPrint()">打印</button>
	</div>
</div>

<script>
function initPrintDg() {
	var that = this;
	var uri = getRootName() + "/attendanceRecord/toAttRecordPrint";
	
	var queryParams = {
		attPlanId : attPlanId,
		userId : userId,
		hasUncheckedIn : hasUncheckedIn,
		checkInStartTime : checkInStartTime,
		checkInEndTime : checkInEndTime
	};
	
	$('#printDataGrid').datagrid({
		width : screen.availWidth,
		height : screen.availHeight,
	    nowrap : true,
		striped : true,
		border : true,
		collapsible : false,
		fit : true,
		fitColumns:true,
		url : uri,
		queryParams : queryParams,
		remoteSort : true,					
		idField : 'id',
		singleSelect : false,
		checkOnSelect : false,
		selectOnCheck : true,
		pagination : false,
		pageSize : pageSize,
		pageNumber : pageNo,
		rownumbers : true,
		scrollbarSize: 0,
		columns : [ [
            {
              field : 'userName',
              title : '签到人姓名',
              width : 35,
              align:'center'
           },
		   {
			 field : 'attDate',
		     title : '签到日期',
			 width : 35,
			 align:'center',
			 formatter : function(value, row, index) {
			   	 if(value) {
			   	 	return formatDate(new Date(value), "yyyy-MM-dd");
			   	 }
			 }
		   },{
			 field : 'signTimeSlot',
			 title : '签到时间段',
		     width : 300, 
		     align:'center',
		     formatter : function(value,row,index){
		    	 return that.getSignTable(row);
		     }
		   },{
              field : 'statistics',
              title : '统计',
              width : 15,
              align:'center'
           }
	    ]] 
	});
	
}

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

function doPrint() {
	var extraArgs = {
		attPlanId : attPlanId,
		userId : userId,
		hasUncheckedIn : hasUncheckedIn,
		checkInStartTime : checkInStartTime,
		checkInEndTime : checkInEndTime
	};
	$('#divPrintArea').printArea({
	    mode : 'popup',
		popHt : screen.availHeight-60,
	    popWd : screen.availWidth,
	    popTitle : '签到统计信息打印',
	    popClose : true,
	    extraScript : contextPath + '/js/common/jquery-1.9.1.min.js,' + contextPath + '/js/common/commonUtil.js,'
	    +contextPath+'/plugin/easyui/jquery.easyui.min.js,'+contextPath+'/js/common/easyui-lang-zh_CN.js,'
	    +contextPath + '/js/platform/attendance/attendance_record_print.js',
	    extraArgs : extraArgs
	});
}

$(function(){
	initPrintDg();
	$('#title').text(title);
});
</script>
</body>
</html>