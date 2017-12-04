var timerId;
var timerId2;
$(document).ready(function() {
	doInitTable();
	//每隔0.5秒自动调用方法，实现操作进度的实时更新
	timerId = window.setInterval(getOpProcess, 5000);
	timerId2 = window.setInterval(getOpCollector, 5000);
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(){
	var path = getRootName(); 
	$('#tblTrapTask')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 700,
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						url : path + '/monitor/trapTask/listOfflineTrapTasks',
						remoteSort : true,
						onSortColumn:function(sort,order){
//						 alert("sort:"+sort+",order："+order+"");
						},
						idField : 'fldId',
						singleSelect : true,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						fit : true,// 自动大小
						fitColumns:true,
						checkOnSelect:false,
						
						columns : [ [
								{
									field : 'serverIP',
									title : 'trap服务器地址',
									width : 150,
									align : 'center',
									formatter : function(value, row, index) {
										if(value == "*"){
											return '所有';
										}else{
											return value;
										}  
									},
									sortable : true
								},
								{
									field : 'alarmOID',
									title : 'trap事件',
									width : 180,
									align : 'center',
									sortable : true
								},
						        {
									field : 'filterExpression',
									title : 'trap过滤表达式',
									width : 260,
									align : 'center',
									sortable : true,
								},
								{
									field : 'createTime',
									title : '任务创建时间',
									width : 110,
									align : 'center',
									sortable : true
								},
								{
									field : 'collectorName',
									title : '任务执行采集机',
									width : 150,
									align : 'center',
									sortable : true
								},
								{
									field : 'taskIds',
									title : '操作',
									width : 100,  
									align : 'center',
									formatter : function(value, row, index) {
									if(row.progressStatus>=1 && row.progressStatus<5){
											return '<a style="cursor: pointer;" onclick="javascript:doOpenDetail('
											+ row.taskID
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_view.png" title="查看" /></a>'
											+' &nbsp;<a href="javascript:doResend('+row.taskID+');" id="btnResend" class="fltrt"><img src =" '+path+'/style/images/icon/icon_sent.png" title="重发" /></a>';
											
										}else{
											return '<a style="cursor: pointer;" onclick="javascript:doOpenDetail('
											+ row.taskID
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_view.png" title="查看" /></a>';
										}
										
								}
								},
								{
									field : 'progressStatus',
									title : '任务下发进度',
									width : 180,
									align : 'center',
									formatter : function(value, row, index) {
										if(value == 1){
											return '<img src =" '+path+'/style/images/icon/progress1.png" title="等待分发" />';
										}else if(value == 2){ 
											return '<img src =" '+path+'/style/images/icon/progress2.png" title="正在分发" />';
										}else if(value == 3){
											return '<img src =" '+path+'/style/images/icon/progress3.png" title="已分发" />';
										}else if(value == 4){
											return '<img src =" '+path+'/style/images/icon/progress4.png" title="已接收" />';
										}else{
											return '已完成';
										}  
									},
									sortable : true
								},
								{
									field : 'errorInfo',
									title : '错误信息',
									width : 200,
									align : 'center',
									sortable : true
								}
								] ]
					});
	$(window).resize(function() {
        $('#tblTrapTask').resizeDataGrid(0, 0, 0, 0);
    });
}

/*
 * 更新表格
 */
function reloadTable() {
	var alarmOID=$("#txtAlarmOID").val();
	var serverIP=$("#txtServerIP").val();
	if(serverIP=="所有"){
		serverIP="*";
	}
	var collectorName=$("#txtCollectorName").val();
	$('#tblTrapTask').datagrid('options').queryParams = {
		"alarmOID" : alarmOID,
		"serverIP" : serverIP,
		"collectorName" : collectorName,
	};
	
	reloadTableCommon('tblTrapTask');
}

/**
 * 实时更新操作进度
 */
function getOpProcess(){
	var grid = $('#tblTrapTask');  
	var options = grid.datagrid('getPager').data("pagination").options;  
	//当前第几页
	var curr = options.pageNumber;  
	//数据总数
	var total = options.total;  
	//总共几页
	var max = Math.ceil(total/options.pageSize); 
	//当前页的行数
	var rows=10;
	if(curr < max){
		rows = 10;
	}else{
		rows = total-(curr-1)*10;
	}
	var taskIds='';
	for ( var i = 0; i < rows; i++) {
		var processValue=$('#tblTrapTask').datagrid('getRows')[i]['progressStatus'];
		if(processValue < 5){
			var taskId=$('#tblTrapTask').datagrid('getRows')[i]['taskID'];
			taskIds+=taskId+":"+i+",";
			}
		
	}  
	if(taskIds != ''){
		if(taskIds.indexOf(",")>=0){ 
			taskIds = taskIds.substring(0,taskIds.lastIndexOf(','));
		}
	}
	var path=getRootName();
	var uri=path+"/monitor/trapTask/getProcessByTaskId";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"text",
		data:{
			"taskIds":taskIds,
			"t" : Math.random()
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			var errorInfoLst = data.errorInfoLst;
			var processLst = data.processLst;
			//更新错误信息
			for ( var i = 0; i < errorInfoLst.length; i++) { 
				var indexValue = + errorInfoLst[i].split(":")[1];
				var errorInfo = "";
				var errorInfo2 = (errorInfoLst[i].split(":")[0]);
				if(errorInfo2!=null && errorInfo2!="" && errorInfo2!="null"){
					errorInfo = (errorInfoLst[i].split(":")[0]);
				}
				$('#tblTrapTask').datagrid('updateRow', { 
					index:indexValue, 
					row:{
					errorInfo:errorInfo
				}
				} );
			}
			
			// 更新操作进度
			for ( var i = 0; i < processLst.length; i++) { 
				var indexValue = + processLst[i].split(":")[1];
				var process = (processLst[i].split(":")[0]);
				$('#tblTrapTask').datagrid('updateRow', { 
					index:indexValue, 
					row:{
						progressStatus:process
					}
				} );
			}
		}
	};	
	ajax_(ajax_param);	

}


/**
 * 任务重发
 * @param taskId
 * @return
 */
function doResend(taskID){
	var path = getRootName();
	var uri = path + "/monitor/trapTask/resendTask";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"progressStatus":1,
			"taskID":taskID,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success:function(data) {
			if (true == data || "true" == data) {
				$.messager.alert("提示", "任务已重发！", "info");
				reloadTable();
			} else {
				$.messager.alert("提示", "任务重发失败！", "error");
			}
		}
	}
	ajax_(ajax_param);
}

/*
 * 打开查看页面
 */
function doOpenDetail(taskID){
	parent.$('#popWin').window({
    	title:'任务发现',
        width:800,
        height : 400,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/trapTask/toShowTaskDetail?taskID='+taskID
    });
}


/**
 * 更新采集机
 * @return
 */
function getOpCollector(){
	var grid = $('#tblTrapTask');  
	var options = grid.datagrid('getPager').data("pagination").options;  
	//当前第几页
	var curr = options.pageNumber;  
//	console.log("curr==="+curr);
	//数据总数
	var total = options.total;  
//	console.log("total==="+total);
	//总共几页
	var max = Math.ceil(total/options.pageSize); 
//	console.log("max==="+max);
	//当前页的行数
	var rows=10;
	if(curr < max){
		rows = 10;
	}else{
		rows = total-(curr-1)*10;
	}
//	console.log("rows===="+rows);
	var taskIds='';
	for ( var i = 0; i < rows; i++) {
		var collectorName=$('#tblTrapTask').datagrid('getRows')[i]['collectorName'];
//		console.log(i+"====="+serverName);
		if(collectorName=="" || collectorName==null){
			var taskId=$('#tblTrapTask').datagrid('getRows')[i]['taskID'];
			taskIds+=taskId+":"+i+",";
			}
		
	}  
	if(taskIds != ''){
		if(taskIds.indexOf(",")>=0){ 
			taskIds = taskIds.substring(0,taskIds.lastIndexOf(','));
		}
	}
	if(taskIds != null && taskIds != ""){
		var path=getRootName();
		var uri=path+"/monitor/trapTask/getCollectorNameByTaskId";
		var ajax_param={
				url:uri,
				type:"post",
				dateType:"text",
				data:{
			"taskIds":taskIds,
			"t" : Math.random()
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			for ( var j = 0; j < data.length; j++) { 
				var indexValue = + data[j].split(":")[1];
				var collectorName="";
				var name = (data[j].split(":")[0]);
				if(name!=null &&name!=""&&name!="null"){
					collectorName = name;
				}
				$('#tblTrapTask').datagrid('updateRow', { 
					index:indexValue, 
					row:{
					collectorName:collectorName
				}
				} );
			}
		}
		};	
		ajax_(ajax_param);	
	}
}