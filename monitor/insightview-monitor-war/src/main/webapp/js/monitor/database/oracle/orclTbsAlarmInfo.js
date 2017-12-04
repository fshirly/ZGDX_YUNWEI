$(document).ready(function() {
	var liInfo = $("#liInfo").val();
	doAlarmInitTables(liInfo);// 最新告警
	$('#tblAlarmActive').datagrid('hideColumn','moId');

});


// 最新告警
function doAlarmInitTables(liInfo) {
	 var path = getRootName();
	 var alarmNum=$("#alarmNum").val();
	 var moID=$("#moId").val();
	 $('#tblAlarmActive')
	   .datagrid(
	     {
	      iconCls : 'icon-edit',// 图标
	      width : 520,
	      height : 'auto',
	      nowrap : false,
	      striped : true,
	      border : true,
	      singleSelect : false,// 是否单选
	      checkOnSelect : false,
	      selectOnCheck : true,
	      collapsible : false,// 是否可折叠的
	      fit : true,// 自动大小
	      fitColumns:true,
	      url : path + '/monitor/orclManage/getTbsAlarmInfo?alarmNum='+alarmNum+'&moID='+moID,
	      idField : 'fldId',
	       onDblClickRow:function(rowIndex, rowData){
					doOpenDetail(rowData.alarmid);
				},
	      columns : [ [
	        {
	         field : 'levelicon',
	         title : '告警级别',
	         width : 40,
	         align : 'center',
	         formatter : function(value, row, index) {
		          return '<img src="'
					+ path
					+ '/style/images/levelIcon/'
					+ value
					+ '"/>'+row.alarmLevelName;
		         }
	         
	        }
	        ,
	         {
	         field : 'alarmtitle',
	         title : '告警标题',
	         width : 120,
	         align : 'left',
	         formatter : function(value, row, index) {
		 			if(value && value.length > 16){
	        		value2 = value.substring(0,16) + "...";
					 return '<a onclick="javascript:doOpenDetail('+ row.alarmid+ ');" title="' + value +  '" >'+value2+"</a>";
	        	}else{
					return '<a onclick="javascript:doOpenDetail('+ row.alarmid+ ');" >'+value+"</a>";
				}
				}
	        }
	        ,
	        {
	         field : 'sourcemoname',
	         title : '告警源',
	         width : 80,
	         align : 'left'
	        }
	        ,
	        {
	         field : 'sourcemoipaddress',
	         title : '告警源IP',
	         width : 110,
	         align : 'left'
			/** ,
			  formatter : function(value, row, index) {
		 		if(value && value.length > 10){
	        		value2 = value.substring(0,25) + "...";
					return '<a onclick="javascript:doOpenDetail('+ row.alarmid+ ');" title="' + value +  '" >'+value2+"</a>";
	        	}else{
					 return '<a onclick="javascript:doOpenDetail('+ row.alarmid+ ');" >'+value+"</a>";
				}
				}*/
	        },
	        {
	         field : 'repeatcount',
	         title : '重复次数',
	         width : 50,
	         align : 'center'
	        }
	        ,
	        {
	         field : 'upgradecount',
	         title : '升级次数',
	         width : 50,
	         align : 'center'
	        },
	        {
		         field : 'starttime',
		         title : '发生时间',
		         width : 100,
		         align : 'center',
		         formatter:function(value,row,index){
				if(value!=null && value!="" ){
					return formatDate(new Date(value), "yyyy-MM-dd hh:mm:ss");
				}else{
					return "";
				}	
        	}
		        }
	        ,
	        {
		         field : 'statusname',
		         title : '告警状态',
		         width : 70,
		         align : 'center'
		        }
	        ] ],
			onLoadSuccess : function() {
				//自适应部件大小
		 		window.parent.resizeWidgetByParams(liInfo);
			}
	       
	     });
	 $(window).resize(function() {
		    $('#tblAlarmActive').resizeDataGrid(0, 0, 0, 0);
		});
	 }

/**
 * 刷新表格数据
 */
function reloadTable() {
	var alarmNum =  $("#alarmNum").val();
	 var moID=$("#moId").val();
	$('#tblAlarmActive').datagrid('options').queryParams = {
		"alarmNum" : alarmNum,
		"moID" : moID
	};
	reloadTableCommon_1('tblAlarmActive',alarmNum,moID);
}

function reloadTableCommon_1(dataGridId,alarmNum,moID) {
	$('#' + dataGridId).datagrid({
		url : getRootName() + '/monitor/orclManage/getTbsAlarmInfo?alarmNum='+alarmNum+'&moID='+moID,
	});
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}
function doOpenDetail(alarmid){
	//查看配置项页面
	var isExistPop = parent.parent.parent.document.getElementById("popWin");
	if(isExistPop != null){
		parent.parent.parent.$('#popWin').window({
	    	title:'告警详情',
	        width:800,
	        height:550,
	        minimizable:false,
	        maximizable:false,
	        collapsible:false,
	        modal:true,
	        href:getRootName()+'/monitor/hostManage/toAlarmActiveDetail?alarmID='+alarmid
	    });
	}else{
		parent.$('#popView').window({
	    	title:'告警详情',
	        width:800,
	        height:550,
	        minimizable:false,
	        maximizable:false,
	        collapsible:false,
	        modal:true,
	        href:getRootName()+'/monitor/hostManage/toAlarmActiveDetail?flag=1&alarmID='+alarmid
	    });
	}
}
