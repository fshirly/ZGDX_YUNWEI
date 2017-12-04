var timerId;
var timerId2;
var timerId3;
$(document).ready(function() {
	doInitTable();
	//每隔0.5秒自动调用方法，实现操作进度的实时更新
	timerId = window.setInterval(getOpProcess, 5000);
	//每隔0.5秒自动调用方法，实时更新是否配置采集任务
	timerId2 = window.setInterval(getIsCongig, 5000);
	timerId3 = window.setInterval(getOpCollector, 5000);
	var flag = $("#flag").val();
	if(flag == "true"){
		$("#location").hide();
	}else{
		$("#location").show();
	}
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(){
	var path = getRootName(); 
	$('#tblDeviceTask')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 700,
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						url : path + '/monitor/addDevice/listTasks',
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
						toolbar : [ {
							'text' : '新增',
							'iconCls' : 'icon-add',
							handler : function() {
							doOpenAdd();
							}
						}],
						columns : [ [
						        {
									field : 'ipaddress1',
									title : 'IP地址',
									width : 150,
									align : 'center',
									sortable : true,
								},
								{
									field : 'createtimeStr',
									title : '任务创建时间',
									width : 110,
									align : 'center',
									sortable : true
								},
								{
									field : 'moClassLable',
									title : '对象类型',
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
									sortable : true
								},
								{
									field : 'taskIds',
									title : '操作',
									width : 100,  
									align : 'center',
									formatter : function(value, row, index) {
									var doUrl = "";
									 var dis = "&quot;" + row.ipaddress1
										+ "&quot;,&quot;" + row.classID
										+ "&quot;,&quot;" + row.port
										+ "&quot;"
									if(row.progressstatus>=1 && row.progressstatus<5){
											return '<a style="cursor: pointer;" onclick="javascript:doOpenDetail('
											+ row.taskid
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_view.png" title="查看" /></a>'
											+' &nbsp;<a href="javascript:doResend('+row.taskid+');" id="btnResend" class="fltrt"><img src =" '+path+'/style/images/icon/icon_sent.png" title="重发" /></a>';
											
										}else if(row.isConfig==0){
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
											+ '/style/images/icon/icon_view.png" title="查看" /></a>'
											+' &nbsp;<a style="cursor: pointer;" title="重发" onclick="javascript:doResend('+row.taskid+');" id="btnResend" class="fltrt"><img src =" '+path+'/style/images/icon/icon_sent.png"/></a>'
											+' &nbsp;<a style="cursor: pointer;" title="配置" onclick="javascript:toSet('+ dis+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_setting.png" title="配置"/></a>';
										}
									
								}
								},
								{
									field : 'progressstatus',
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
									field : 'errorinfo',
									title : '错误信息',
									width : 230,
									align : 'center',
									sortable : true
								}
								] ]
					});
	$(window).resize(function() {
        $('#tblDeviceTask').resizeDataGrid(0, 0, 0, 0);
    });
}

/*
 * 更新表格
 */
function reloadTable() {
	var ipaddress1=$("#txtIPaddress1").val();
	var collectorName=$("#txtCollectorName").val();
	$('#tblDeviceTask').datagrid('options').queryParams = {
		"ipaddress1" : ipaddress1,
		"collectorName" : collectorName,
	};
	
	reloadTableCommon('tblDeviceTask');
}

/**
 * 实时更新错误信息和操作状态
 */
function getOpProcess(){
	var grid = $('#tblDeviceTask');  
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
		var processValue=$('#tblDeviceTask').datagrid('getRows')[i]['progressstatus'];
		if(processValue < 5){
			var taskId=$('#tblDeviceTask').datagrid('getRows')[i]['taskid'];
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
			var errorinfoLst = data.errorinfoLst;
			var processLst = data.processLst;
			//更新错误信息
			for ( var i = 0; i < errorinfoLst.length; i++) { 
				var indexValue = + errorinfoLst[i].split(":")[1];
				var errorinfo = "";
				var errorinfo2 = (errorinfoLst[i].split(":")[0]);
				if(errorinfo2!=null && errorinfo2!="" && errorinfo2!="null"){
					errorinfo=(errorinfoLst[i].split(":")[0]);
				}
				$('#tblDeviceTask').datagrid('updateRow', { 
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
				$('#tblDeviceTask').datagrid('updateRow', { 
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
    	title:'设备任务',
        width:800,
        height : 480,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/addDevice/toShowTaskDetail?taskId='+taskId+'&flag=popWin'
    });
}

/*
 * 打开新增页面
 */
function doOpenAdd(taskId){
	try {
		parent.$("#divAddSnmp").window("destroy");
	} catch(e){
	}
	parent.$('#popWin').window({
    	title:'新增监测对象',
        width:800,
        height : 530,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/addDevice/toShowTaskAdd'
    });
}


/**
 * 实时更新,是否需要配置采集任务
 */
function getIsCongig(){
	var grid = $('#tblDeviceTask');  
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
		var processValue=$('#tblDeviceTask').datagrid('getRows')[i]['progressstatus'];
		var isconfig =$('#tblDeviceTask').datagrid('getRows')[i]['isConfig'];
		if(processValue == 5 && isconfig==0){
			var taskId=$('#tblDeviceTask').datagrid('getRows')[i]['taskid'];
			taskIds+=taskId+":"+i+",";
			}
		
	}  
	if(taskIds != ''){
		if(taskIds.indexOf(",")>=0){ 
			taskIds = taskIds.substring(0,taskIds.lastIndexOf(','));
		}
		var path=getRootName();
		var uri=path+"/monitor/addDevice/getIsConfig";
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
			var indexLst = data.indexLst;
			for ( var i = 0; i < indexLst.length; i++) { 
				var indexValue =  indexLst[i].split(":")[0];
				var isConfig = indexLst[i].split(":")[1];
//				console.log("后台传入的index的数据类型："+typeof(indexValue));
//				console.log("处理后的数据类型："+typeof(parseInt(indexValue)));
//				console.log("indexValue=="+indexValue+",  isConfig="+isConfig)
				$('#tblDeviceTask').datagrid('updateRow', { 
					index:parseInt(indexValue), 
					row:{
						isConfig:isConfig,
					}
				} );
				
				$('#tblDeviceTask').datagrid('refreshRow', parseInt(indexValue) );
			}
		}
		};	
		ajax_(ajax_param);	
	}

}


function toSet(deviceip,moClassId,port){
	//console.log("moClassId=="+moClassId);
	var path = getRootName();
	var uri = path + "/monitor/addDevice/doBeforeSet?deviceip="+deviceip+"&moClassId="+moClassId+"&port="+port;
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
		success:function(data) {
			toSetMonitor(data.moid,deviceip,data.moClassId,data.nemanufacturername,port);
		}
	}
	ajax_(ajax_param);
}
/**
 * 配置采集任务
 */
function toSetMonitor(moid,deviceip,moClassId,nemanufacturername,port){
	if(moClassId != 54){
		parent.$('#popWin').window({
			title:'采集设备配置',
			width:800,
			height:500,
			minimizable:false,
			maximizable:false,
			collapsible:false,
			modal:true,
			href: getRootName() + '/monitor/configObjMgr/toSetMonitor?moid='+moid+'&deviceip='+deviceip+'&moClassId='+moClassId+'&nemanufacturername='+nemanufacturername+'&port='+port
		});
	}else{
		var dbmstype = "db2"
		parent.parent.$('#popWin2').window({
			title:'选择数据库名',
		    width:400,
		    height:300,
		    minimizable:false,
		    maximizable:false,
		    collapsible:false,
		    modal:true,
		    href: getRootName() + "/monitor/deviceManager/toGetDbNames?deviceip="+deviceip+"&port="+port+"&dbmstype="+dbmstype+"&moClassId="+moClassId+"&moid="+moid+"&isForPerfSet=true"
		});
	}
}
/**
 * 更新采集机
 * @return
 */
function getOpCollector(){
	var grid = $('#tblDeviceTask');  
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
		var collectorName=$('#tblDeviceTask').datagrid('getRows')[i]['collectorName'];
//		console.log(i+"====="+serverName);
		if(collectorName=="" || collectorName==null){
			var taskId=$('#tblDeviceTask').datagrid('getRows')[i]['taskid'];
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
				$('#tblDeviceTask').datagrid('updateRow', { 
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
