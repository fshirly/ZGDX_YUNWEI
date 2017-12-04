function initPrintDg() {
	var that = this;
	var uri = getRootName() + "/attendanceRecord/toAttRecordPrint";
	
	var queryParams = {
		attPlanId : window.extraArgs.attPlanId,
		userId : window.extraArgs.userId,
		hasUncheckedIn : window.extraArgs.hasUncheckedIn,
		checkInStartTime : window.extraArgs.checkInStartTime,
		checkInEndTime : window.extraArgs.checkInEndTime
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

$(function(){
	initPrintDg();
	$('#title').text(title);
});