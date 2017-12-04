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
	$('#tblDiscoverTask')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 700,
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						url : path + '/monitor/discoverTask/listDiscoverTasks',
						remoteSort : true,
						onSortColumn:function(sort,order){
//						 alert("sort:"+sort+",order："+order+"");
						},
						queryParams : {
							"status" : -2,
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
									field : 'discoverRange',
									title : '发现范围',
									width : 220,
									align : 'center',
									sortable : true,
								},
								{
									field : 'tasktype',
									title : '发现类型',
									width : 80,
									align : 'center',
									formatter : function(value, row, index) {
									if(value==1){
										return '地址段';
									}else if(value==2){
										return '子网';
									}else if(value==3){
										return '路由表';
									}else{
										return '单对象';
									}
								},
								},
								{
									field : 'createtimeStr',
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
									field : 'webIPAddress',
									title : '下发任务WEB应用',
									width : 150,
									align : 'center',
									sortable : true,
								},
								{
									field : 'taskIds',
									title : '操作',
									width : 100,  
									align : 'center',
									formatter : function(value, row, index) {
									if(row.progressstatus>=1 && row.progressstatus<5){
											return '<a style="cursor: pointer;" onclick="javascript:doOpenDetail('
											+ row.taskid
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_view.png" title="查看" /></a>'
											+' &nbsp;<a href="javascript:doResend('+row.taskid+');" id="btnResend" class="fltrt"><img src =" '+path+'/style/images/icon/icon_sent.png" title="重发" /></a>';
											
										}else{
											return '<a style="cursor: pointer;" onclick="javascript:doOpenDetail('
											+ row.taskid
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_view.png" title="查看" /></a>';
										}
										
								}
								},
								{
									field : 'progressstatus',
									title : '任务下发进度',
									width : 150,
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
									field : 'errorinfo',
									title : '错误信息',
									width : 180,
									align : 'center',
									sortable : true
								}
								] ]
					});
	$(window).resize(function() {
        $('#tblDiscoverTask').resizeDataGrid(0, 0, 0, 0);
    });
}

/*
 * 更新表格
 */
function reloadTable() {
	var ipaddress1=$("#txtIPaddress1").val();
	var collectorName=$("#txtCollectorName").val();
	var tasktype=$("#txtTaskType").combobox("getValue");
	$('#tblDiscoverTask').datagrid('options').queryParams = {
		"status":-2,
		"tasktype" :tasktype,
		"ipaddress1" : ipaddress1,
		"collectorName" : collectorName,
	};
	
	reloadTableCommon('tblDiscoverTask');
}

/**
 * 实时更新任务状态和操作状态
 */
function getOpProcess(){
	var grid = $('#tblDiscoverTask');  
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
		var processValue=$('#tblDiscoverTask').datagrid('getRows')[i]['progressstatus'];
		if(processValue < 5){
			var taskId=$('#tblDiscoverTask').datagrid('getRows')[i]['taskid'];
			taskIds+=taskId+":"+i+",";
			}
		
	}  
	if(taskIds != ''){
		if(taskIds.indexOf(",")>=0){ 
			taskIds = taskIds.substring(0,taskIds.lastIndexOf(','));
		}
	}
	var path=getRootName();
	var uri=path+"/monitor/discoverTask/getProcessByTaskId";
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
			var processLst = data.processLst;
			var errorinfoLst = data.errorinfoLst;
			//更新错误信息
			for ( var i = 0; i < errorinfoLst.length; i++) { 
				var indexValue = + errorinfoLst[i].split(":")[1];
				var errorinfo = "";
				var errorinfo2 = (errorinfoLst[i].split(":")[0]);
				if(errorinfo2!=null && errorinfo2!="" && errorinfo2!="null"){
					errorinfo=(errorinfoLst[i].split(":")[0]);
				}
				$('#tblDiscoverTask').datagrid('updateRow', { 
					index:indexValue, 
					row:{
						errorinfo:errorinfo
					}
				} );
			}
			//更新操作进度
			for ( var i = 0; i < processLst.length; i++) { 
				var indexValue = + processLst[i].split(":")[1];
				var process = (processLst[i].split(":")[0]);
				$('#tblDiscoverTask').datagrid('updateRow', { 
					index:indexValue, 
					row:{
					progressstatus:process
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
function doResend(taskId){
	var path = getRootName();
	var uri = path + "/monitor/discoverTask/resendTask";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"progressstatus":1,
			"taskid":taskId,
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
function doOpenDetail(taskId){
	parent.$('#popWin').window({
    	title:'任务发现',
        width:800,
        height : 580,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/discoverTask/toShowTaskDetail?taskId='+taskId
    });
}

/**
 * 更新采集机
 * @return
 */
function getOpCollector(){
	var grid = $('#tblDiscoverTask');  
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
		var collectorName=$('#tblDiscoverTask').datagrid('getRows')[i]['collectorName'];
//		console.log(i+"====="+serverName);
		if(collectorName=="" || collectorName==null){
			var taskId=$('#tblDiscoverTask').datagrid('getRows')[i]['taskid'];
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
		var uri=path+"/monitor/discoverTask/getCollectorNameByTaskId";
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
				$('#tblDiscoverTask').datagrid('updateRow', { 
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