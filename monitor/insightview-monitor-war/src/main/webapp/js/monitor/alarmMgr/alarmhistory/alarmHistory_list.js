$(document).ready(function() {	
	doInitTable();
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
	var path = getRootName();
	$('#tblDataList')
			.datagrid(
					{
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : path + '/monitor/alarmHistory/listAlarmHistory',
						remoteSort : true,
						onSortColumn:function(sort,order){
//						 alert("sort:"+sort+",order："+order+"");
						},	
						onDblClickRow:function(rowIndex, rowData){
							toShowInfo(rowData.alarmID);
						},					
						idField : 'fldId',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
						        {
						        	field : 'alarmIDs',
						        	title : '告警序号',
						        	width : 50,
						        	hidden:true,
						        	align : 'left',
						        	formatter:function(value,row){ 										
						        		return '<a style="cursor: pointer;" title="查看" onclick="javascript:toShowInfo('
										+ row.alarmID
										+ ');">'
										+ row.alarmID + '</a>';
						        	} 
						        },     
								{
									field : 'operateStatusName',
									title : '告警操作状态',
									width : 70,
									align : 'left'
								},
								{
									field : 'alarmLevelName',
									title : '告警级别',
									width : 50,
									align : 'center',
									formatter:function(value,row){ 
										var t = row.levelColor;
										return "<div style='background:"+t+";'>"+value+"</div>"; 
									} 
								},
								{
									field : 'alarmTitle',
									title : '告警标题',
									width : 100,
									align : 'center'									
								},
								{
									field : 'sourceMOName',
									title : '告警源名称',
									width : 100,
									align : 'center'									
								},
								{
									field : 'moName',
									title : '告警名称',
									width : 100,
									align : 'center'									
								},
								{
									field : 'sourceMOIPAddress',
									title : '告警源IP',
									width : 100,
									align : 'center'									
								},
								{
									field : 'startTime',
									title : '首次发生时间',
									width : 80,
									align : 'center',
									formatter:function(value,row,index){
										if(value!=null && value!="" ){
											return formatDate(new Date(value), "yyyy-MM-dd hh:mm:ss"); 
										}else{
											return "";
										}	
	                        		}
								},	
								{
									field : 'lastTime',
									title : '最近发生时间',
									width : 80,
									align : 'center',
									formatter:function(value,row,index){
										if(value!=null && value!="" ){
											return formatDate(new Date(value), "yyyy-MM-dd hh:mm:ss"); 
										}else{
											return "";
										}	
	                        		}
								},	
								{
									field : 'alarmTypeName',
									title : '告警类型',
									width : 80,
									align : 'center'
								},
								{
									field : 'repeatCount',
									title : '重复次数',
									width : 35,
									align : 'right'									
								},
								{
									field : 'deleteTime',
									title : '删除时间',
									width : 80,
									align : 'center',
									formatter:function(value,row,index){
										if(value!=null && value!="" ){
											return formatDate(new Date(value), "yyyy-MM-dd hh:mm:ss");
										}else{
											return "";
										}	
		                         		
		                        	}
								} ] ]
					});
	$(window).resize(function() {
        $('#tblDataList').resizeDataGrid(0, 0, 0, 0);
    });
}


/**
 * 刷新表格数据
 */
function reloadTable() {
	var timeBegin=$('#timeBegin').datetimebox('getValue');
	var timeEnd=$('#timeEnd').datetimebox('getValue');
	
	var start = new Date(timeBegin.replace("-", "/").replace("-", "/"));
	var end = new Date(timeEnd.replace("-", "/").replace("-", "/"));
	if (end < start) {
		$.messager.alert('提示', '开始有效时间不得早于结束时间', 'error');
	}else{	
		var moName=$("#moName").val();
		var alarmLevel=$("#alarmLevel").combobox("getValue");
		var alarmTitle=$("#alarmTitle").val();
		var sourceMOIPAddress = $("#sourceMOIPAddress").val();
		var alarmOperateStatus=$("#alarmOperateStatus").combobox("getValue");
		var alarmType=$("#alarmType").combobox("getValue");	
		$('#tblDataList').datagrid('options').queryParams = {
			"alarmLevel" : alarmLevel==''?0:alarmLevel,
			"alarmOperateStatus" : alarmOperateStatus==''?0:alarmOperateStatus,
			"alarmType" : alarmType==''?0:alarmType,
			"timeBegin" : timeBegin,
			"timeEnd" : timeEnd,
			"moName" : moName,
			"sourceMOIPAddress": sourceMOIPAddress,
			"alarmTitle" : alarmTitle
		};
		reloadTableCommon_1('tblDataList');
	}	
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/**
 * 打开告警详情页面
 * @return
 */
function toShowInfo(id){	
	 parent.$('#popWin').window({
    	title:'历史告警详情',
        width:800,
        height:640,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmHistory/toAlarmHistoryDetail?alarmID='+id
     });		
}

function resetFilterForm(divFilter) {
	resetForm(divFilter);
	$("#alarmOperateStatus").combobox("setValue", "");
	$("#alarmLevel").combobox("setValue", "");
	$("#alarmType").combobox("setValue", "");
}