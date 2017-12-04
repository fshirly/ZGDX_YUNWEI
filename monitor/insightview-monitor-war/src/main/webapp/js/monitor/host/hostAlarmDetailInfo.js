$(document).ready(function() {
	var liInfo = $("#liInfo").val();
	doInitTable(liInfo);// 最新告警

});


// 最新告警
function doInitTable(liInfo) {
	 var path = getRootName();
	 var alarmNum=$("#alarmNum").val();
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
	      url : path + '/monitor/hostManage/findDetailAlarmInfo?alarmNum='+alarmNum,
	      idField : 'fldId',
	      onDblClickRow:function(rowIndex, rowData){
					doOpenDetail(rowData.alarmid);
				},
	      columns : [ [
	        {
	         field : 'levelicon',
	         title : '告警级别',
	         width : 80,
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
	         field : 'sourcemoname',
	         title : '告警源',
	         width : 100,
	         align : 'left'
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
	        },
	        {
	         field : 'sourcemoipaddress',
	         title : '告警IP',
	         width : 90,
	         align : 'center'
	        },
	        {
		         field : 'starttime',
		         title : '告警发生时间',
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
			onLoadSuccess: function(){   
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
	$('#tblAlarmActive').datagrid('options').queryParams = {
		"alarmNum" : alarmNum
	};
	reloadTableCommon_1('tblAlarmActive',alarmNum);
}

function reloadTableCommon_1(dataGridId,alarmNum) {
	$('#' + dataGridId).datagrid({
		url : getRootName() + '/monitor/hostManage/findDetailAlarmInfo?alarmNum='+alarmNum,
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
	        height:470,
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
	        height:470,
	        minimizable:false,
	        maximizable:false,
	        collapsible:false,
	        modal:true,
	        href:getRootName()+'/monitor/hostManage/toAlarmActiveDetail?flag=1&alarmID='+alarmid
	    });
	}
	
	
	
	
//	var url = getRootName()+'/monitor/hostManage/toAlarmActiveDetail?alarmID='+alarmid+'&flag=1';
//	window.open(url,"","height=500px,width=800px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
	
}
