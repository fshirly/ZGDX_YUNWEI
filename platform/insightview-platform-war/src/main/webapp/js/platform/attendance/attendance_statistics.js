/** 
 * 签到统计javascript功能
 * creat by zhaoyp
 * 2015-05-20 
 */
var Sys = Sys || {};
Sys.attendance={
		path : getRootName(),
		
		/**
		 *初始化表 
		 */
        initASTable : function(){
        	var that = this;
        	var attPlanId = $("#signPlan").combobox("getValue");
        	var uri = that.path + "/attendanceRecord/toStatisAttendanceRecord";//url
        	
        	var attPlanId = $('#signPlan').combobox('getValue');
        	var userId = $('#name').select2("val");
        	var hasUncheckedIn = 0;
        	if($("#isShow").is(":checked")==true){
        		hasUncheckedIn = 1;
        	}
        	var checkInStartTime = $('#startTime').datebox('getValue');
        	var checkInEndTime =$('#endTime').datebox('getValue');
        	var queryParams = {
        		attPlanId : attPlanId,
        		userId : userId,
        		hasUncheckedIn : hasUncheckedIn,
        		checkInStartTime : checkInStartTime,
        		checkInEndTime : checkInEndTime
        	};
        	$("#ASTable").datagrid({
        		width : 'auto',
        		height : 'auto',
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
        		pagination : true,
        		rownumbers : true,
        		columns : [ [
                    {
	                  field : 'userName',
                      title : '签到人姓名',
	                  width : 120,
	                  align:'center'
                   },
        		   {
        			 field : 'attDate',
        		     title : '签到日期',
        			 width : 120,
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
	                  width : 120,
	                  align:'center'
                   }
        	    ]],
        	    toolbar :[{
        			text : '打印',
        			iconCls : 'icon-print',
        			handler : function(){
        				
        				var qryParams = getQueryParam();
        				var url = getRootName() + '/attendanceRecord/viewAttRecordPrint';
        				var title = $('#signPlan').combobox('getText');
        				var pageNo = $('#ASTable').datagrid('options').pageNumber;
        				var pageSize = $('#ASTable').datagrid('options').pageSize;
        				if(qryParams.attPlanId) {
        					url += "?attPlanId=" +qryParams.attPlanId;
        				}
        				if(qryParams.userId) {
        					url += "&userId=" + qryParams.userId;
        				}
        				if(qryParams.hasUncheckedIn) {
        					url += "&hasUncheckedIn=" + qryParams.hasUncheckedIn;
        				}
        				if(qryParams.checkInStartTime) {
        					url += "&checkInStartTime=" + qryParams.checkInStartTime;
        				}
        				if(qryParams.checkInEndTime) {
        					url += "&checkInEndTime=" + qryParams.checkInEndTime;
        				}
        				if(title) {
        					url += "&title=" + title;
        				}
        				url+= "&pageSize=" + pageSize;
        				url+= "&pageNo=" + pageNo;
        				var h = screen.availHeight -60;
        				window.open(url,"_blank","toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width="+ screen.availWidth+", height="+ h);
        				
        			}	
        		},{
        		   text:'导出',
        		   iconCls : 'icon-execl',
        		   handler : function(){
        			   toExport();
        		   }	
        			
        		}] 
        	});
        	
        	uri = getRootName() + "/attendanceRecord/toStatisAttendanceRecordCount";
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
						$('#totalCount').text(data.totalCount);
						$('#checkedCount').text(data.checkedCount);
						$('#uncheckedCount').text(data.uncheckedCount);
					}
					
				}
			}
			ajax_(ajax_param);
			
        	$(window).resize(function() {
        		$('#ASTable').resizeDataGrid(0, 0, 0, 0);
        	});
        },
        
        /**
         *签到时间段布局函数 
         */
        getSignTable : function(row){
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
        },
        
        /**
         *查询重置表单 
         */
        reloadTable : function(){
        	var attPlanId = $("#signPlan").combobox("getValue");
        	var startTime = $("#startTime").datebox("getValue");
        	var endTime = $("#endTime").datebox("getValue");
        	var userId = $("#name").select2("val");
        	var isShow = 0;
        	if($("#isShow").is(":checked")==true){
        		isShow = 1;
        	}
        	if(this.compareDay(startTime,endTime)===true){
        		var qryObj = {
            			"attPlanId" : attPlanId,
            			"userId" : userId,
            			"checkInStartTime" : startTime,
            			"checkInEndTime": endTime,
            			"hasUncheckedIn":isShow
                	};
                	
                	$('#ASTable').datagrid('options').queryParams = qryObj;
            		$('#ASTable').datagrid('load');
            	    $('#ASTable').datagrid('uncheckAll');
            	    
            	    var uri = getRootName() + "/attendanceRecord/toStatisAttendanceRecordCount";
        			var ajax_param = {
        				url : uri,
        				type : "post",
        				datdType : "json",
        				data : qryObj,
        				error : function() {
        					$.messager.alert("错误", "ajax_error", "error");
        				},
        				success : function(data) {
        					if(data) {
        						$('#totalCount').text(data.totalCount);
        						$('#checkedCount').text(data.checkedCount);
        						$('#uncheckedCount').text(data.uncheckedCount);
        					}
        					
        				}
        			}
        			ajax_(ajax_param);
        	}  
        },
        
        /**
         *重置查询区域 
         */
        cancelButton : function(){
        	$("#startTime").datebox("setValue","");
        	$("#endTime").datebox("setValue","");
        	//$("#signPlan").combobox("setValue","");
        	$("#isShow").attr("checked",false);
        	$("#name").select2("val","-1");
        	$("#signPlan").combobox({
    		   url : getRootName() + '/attendanceRecord/toAllAttPlanList',
    		   valueField : 'attendanceId',
    		   textField : 'planName',
    		   onLoadSuccess : function(d) {
    			   if(d && d[0] && d[0].attendanceId) {
    				   $("#signPlan").combobox('select',d[0].attendanceId);
    	    		   //initAttUser();
    	    		   Sys.attendance.initASTable();
    			   }
    		   	
    		   },
    		   onSelect : function(d) {
    			   var selectObj = document.getElementById("name");
    			   for(var i = 0,len = selectObj.options.length; i < len; i++){
    			       selectObj.options[0] = null;
    			   }
    			   selectObj.options[0] = new Option("请选择...", -1);
    			   initAttUser();
    		   }
        	});
        },
        
        
        
        /**
         *日期大小比较 
         */
        compareDay : function(startDay,endDay){
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
};

function toExport() {
	$('#popWin').window({
		   title : '签到统计信息导出',
		   width : 420,
		   height : 350,
		   minimizable : false,
		   maximizable : false,
		   collapsible : false,
		   modal : true,
		   href : getRootName() + '/attendanceRecord/attendancePop'
   });
}


function doPrint() {
	$('#divPrintArea').printArea({
	    mode : 'iframe',
	    popHt : 600,
	    popWd : 800,
	    popTitle : '签到统计信息打印',
	    popClose : true
	});
	
}

function getQueryParam() {
	var attPlanId = $("#signPlan").combobox("getValue");
	
	var attPlanId = $('#signPlan').combobox('getValue');
	var userId = $('#name').select2("val");
	var hasUncheckedIn = 0;
	if($("#isShow").is(":checked")==true){
		hasUncheckedIn = 1;
	}
	var checkInStartTime = $('#startTime').datebox('getValue');
	var checkInEndTime =$('#endTime').datebox('getValue');
	var queryParams = {
		attPlanId : attPlanId,
		userId : userId,
		hasUncheckedIn : hasUncheckedIn,
		checkInStartTime : checkInStartTime,
		checkInEndTime : checkInEndTime
	};
	return queryParams;
}

function initAttUser() {
	var attPlanId = $('#signPlan').combobox('getValue');
	$("#name").createSelect2({
		uri : '/attendanceRecord/toPlanPeopleList?attPlanId='+attPlanId,// 获取数据
        name : 'userName',// 显示名称
        id : 'userID', // 对应值值
        initVal :{creater:$("#creater").attr("value")}
	});
}

$(function(){
	$("#startTime").datebox();
	$("#endTime").datebox();
	$("#signPlan").combobox({
	   url : getRootName() + '/attendanceRecord/toAllAttPlanList',
	   valueField : 'attendanceId',
	   textField : 'planName',
	   onLoadSuccess : function(d) {
		 if(d && d[0] && d[0].attendanceId) {
			 $("#signPlan").combobox('select',d[0].attendanceId);
			 Sys.attendance.initASTable();
		 }
	   	
	   },
	   onSelect : function(d) {
		   var selectObj = document.getElementById("name");
		   for(var i = 0,len = selectObj.options.length; i < len; i++){
		       selectObj.options[0] = null;
		   }
		   selectObj.options[0] = new Option("请选择...", -1);
		   initAttUser();
	   }
	});
	initAttUser();
	Sys.attendance.initASTable();
	
});