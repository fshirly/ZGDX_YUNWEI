$(document).ready(function() {
	var flag=$('#flag').val();
	doInitTable();
	if(flag ==null || flag ==""){ 
		$('#tblRoomList').datagrid('hideColumn','moID');
	}else{
		$('#tblRoomList').datagrid('hideColumn','moIDs');
	}
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
	var flag=$('#flag').val();
	var path = getRootName();
	var uri = path + '/monitor/envManager/listRoom';
	$('#tblRoomList')
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
						url : uri,
						remoteSort : false,
//						idField : 'idField',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,						pagination : true,// 分页控件
						rownumbers : true,// 行号	
						toolbar : [{
							'text' : '同步到资源库',
							'iconCls' : 'icon_cmdb',
							handler : function(){
							showCMDB();
							}
						},
						{
							'text' : '删除',
							'iconCls' : 'icon-cancel',
							handler : function() {
								doDeleRoom();
							}
						}],
						columns : [ [
					         	{
									field : 'id',
									checkbox : true
								},
								{
									field : 'readerID',
									title : '阅读器编号',
									width : 50,
									align : 'center',
								 
								 },
								{
									field : 'readerLabel',
									title : '阅读器名称',
									width : 80,
									align : 'center',
								 
								 },
								{
									field : 'iPAddress',
									title : '阅读器IP',
									width : 100,
									sortable:true,
									align : 'center'
								/*	formatter : function(value, row, index) {
					        			if(row.moClassId != 0 && (flag ==null || flag =="")){
					        				var to = "&quot;" + row.deviceMoid
											+ "&quot;,&quot;" + value
											+ "&quot;"
					        				return '<a style="cursor: pointer;" onclick="javascript:toShowView('
											+ to
											+ ');">'+value+'</a>'; 
						        		}else{
						        			return value;
						        		}
								}*/
								},
								{
									field : 'port',
									title : '端口',
									width : 60,
									align : 'center'
								},
								{
									field : 'enabled',
									title : '是否启用',
									width : 80,
									align : 'center',
									formatter : function(value,row,index){
										if (value == "true") {
											return '是';
										} else {
											return '否';
										}
									}
								},
								{
									field : 'firmwareVersion',
									title : '固件版本',
									width : 80,
									align : 'center'
								},
								{
									field : 'deviceIP',
									title : '动环系统IP',
									width : 120,
									align : 'center'
								},
								{
									field : 'connectionEncrypted',
									title : '连接是否加密',
									width : 80,
									align : 'center',
									formatter : function(value,row,index){
										if (value == "true") {
											return '是';
										} else {
											return '否';
										}
									}
								},
								{
									field : 'moClassId',
									title : '类型',
									hidden:true,//隐藏
									width :.80
								},
								{
									field : 'moID',
									title : 'ID',
									hidden:true,//隐藏
									width :.80
								}/*,
								{
									field : 'moids',
									title : '操作',
									width : 80,
									align : 'center',
									formatter : function(value,row,index){
									 var to = "&quot;" + row.moID
										+ "&quot;,&quot;" + row.deviceIP
										+ "&quot;"
									 return ' <a href="javascript:doRediscover('+to+');"  class="fltrt"><img src =" '+path+'/style/images/icon/icon_refresh.png" title="重新发现" /></a>&nbsp;'
									 	+'<a style="cursor:pointer;" title="配置" onclick="javascript:toSet('
										+ to
										+ ');"><img src =" '+path+'/style/images/icon/icon_setting.png" alt="配置" /></a>';	
									}
								}*/
								] ]
					});
	$(window).resize(function() {
        $('#tblRoomList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblRoomList').datagrid('options').queryParams = {
		"readerName" : $("#readerName").val(),
		"iPAddress" : $("#iPAddress").val()
	};
	reloadTableCommon_1('tblRoomList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function sel(moid){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_oracleId"); 
	     fWindowText1.value = moid; 
	     window.opener.findOracleInfo();
	     window.close();
	} 
}  

/**
 * 重新发现
 * @param moid
 * @return
 */
function doRediscover(moid,deviceip){
//	console.log("deviceip==="+deviceip+")
	var moClassId = 45;
	$.messager.confirm("提示","确定要重新发现?",function(r){
		if (r == true) {
			var path = getRootPatch();
			var uri = path + "/monitor/deviceManager/doRediscover?moClassId="+moClassId+"&deviceip="+deviceip;
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
					if(true == data.flag || "true" == data.flag){
		//				console.log("data===="+data)
						var taskid = data.taskid;
						showDeviceTask(taskid,moid,deviceip);
					}
				}
			}
			ajax_(ajax_param);
		}
	});
}

/**
 * 跳转至设备任务界面
 * @return
 */
function showDeviceTask(taskid,moid,deviceip){
	var moClassId = 45;
	parent.parent.$('#popWin').window({
		title:'设备任务',
	    width:800,
	    height:300,
	    minimizable:false,
	    maximizable:false,
	    collapsible:false,
	    modal:true,
	    href: getRootName() + '/monitor/deviceManager/showDeviceTask?taskid='+taskid+'&moid='+moid+'&deviceip='+deviceip+'&moClassId='+moClassId
	});
}

/**
 * 打开采集配置
 * @param moid
 * @param deviceip
 * @param moClassId
 * @param nemanufacturername
 * @param taskId
 * @return
 */
function toSet(moid,deviceip,dbmstype){
	var moClassId = 45;
	parent.parent.$('#popWin').window({
	title:'采集设备配置',
    width:800,
    height:500,
    minimizable:false,
    maximizable:false,
    collapsible:false,
    modal:true,
    href: getRootName() + '/monitor/configObjMgr/toSetMonitor?moid='+moid+'&deviceip='+deviceip+'&moClassId='+moClassId
});
}
//查看设备视图
function toShowView(moid,moname){
	var path = getRootPatch();
	var uri = path + "/monitor/discover/selectMoClass";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"moid" : moid,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error"); 
		},
		success : function(data) {
//			viewDevicePortal(moid,data);
			toShowTabs(moid,data,moname);
		}
	}
	ajax_(ajax_param);
	
}

function toShowTabs(moid,moClassName,moName){
	var path = getRootPatch();
	var uri = path + "/monitor/gridster/viewDevicePortal";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"portalName" : moClassName,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error"); 
		},
		success : function(data) {
			if(data == true){
				var moClass="host";
				var title = '主机'+moName+'视图';
				if(moClassName=="VHost"){
					moClass="vhost";
					title = '宿主机'+moName+'视图';
				}
				if(moClassName=="VM"){
					moClass="vm";
					title = '虚拟机'+moName+'视图';
				}
				if(moClassName=="Router"){
					moClass="router";
					title = '路由器'+moName+'视图';
				}
				if(moClassName=="Switcher"){
					moClass="switcher";
					title = '交换机'+moName+'视图';
				}
				var urlParams="?moID="+moid+"&moClass="+moClass+"&flag=device";
				var uri=path+"/monitor/gridster/showPortalView"+urlParams;
				var content = '<iframe scrolling="auto" frameborder="0"  src="'+uri+'" style="width:100%;height:100%;"></iframe>';
				parent.$('#tabs_window').tabs('add',{
		            title:title,
		            content:content,
		            closable:true
		        });
			}else{
				$.messager.alert("提示","视图加载失败！","error");
			}
			
		}
	}
	ajax_(ajax_param);
	
}
/**
 * 跳转至CMDB界面
 * @return
 */
function showCMDB(){
	var rows  = $('#tblRoomList').datagrid('getSelections');
	var flag = null;
	if(rows.length>0){
		var moClassId = rows[0].moClassId;
		var ids=rows[0].moID;
		for(var i=1; i<rows.length; i++){
			ids+=',' +rows[i].moID;
			if( moClassId != rows[i].moClassId){
				flag = "1";
			}		
		}
		if(flag == null){
				parent.parent.$('#popWin').window({
				title:'资源分类',
			    width:700,
			    height:400,
			    minimizable:false,
			    maximizable:false,
			    collapsible:false,
			    modal:true,
			    href: getRootName() + '/monitor/envManager/showCmdb?moClassId='+moClassId+"&moids="+ids
			});
		}else{
			$.messager.alert('提示', '设备类型不一致', 'error');
		}
	}else{
		$.messager.alert('提示', '没有任何选中项', 'error');
	}	
}

function doDeleRoom(){
	var ids=null;
	var rows  = $('#tblRoomList').datagrid('getChecked');
	for(var i =0;i<rows.length;i++){
		if (null == ids) {
			ids = rows[i].moID+"_"+rows[i].moClassId; 
		} else {
			ids += "," + rows[i].moID+"_"+rows[i].moClassId;
		}
	}
	toAffirmWebSite(ids);
}

function closephydlgTask(){
	$('#dlgRoom').panel('close');
	$('#dlgRoom').panel('destroy');
}
/**
 * 确认删除该设备是否有采集任务
 * @return
 */
function toAffirmWebSite(ids){
	if (null != ids) {	
	// 校验其是否有采集任务
	object = checkTaskStatus(ids);
	
	if(null !=object && object !=""){
		var dlgdb = "<div id='dlgRoom'><div class='conditionsBtn'>"
			+"<a href='javascript:void(0);' onclick='javascript:closephydlgTask()' id='btntaskSave'>关闭</a></div></div>";
		var dlgTask = $(dlgdb).appendTo(document.body).dialog({
			title:'提示',
			width: 400,
			height: 300,
			content: '',
			modal:true,
			closed: true,
			onClose:function(){
			$('#dlgRoom').panel('destroy');
			}
		});
	var serviceTab =$('<table id="roomData"></table>').prependTo(dlgTask.dialog("body")).datagrid({
			title:'删除的设备中含有以下关联任务，请先将关联任务删除',
			nowrap : false,
			fit : true,// 自动大小
			fitColumns:true,
		    columns:[[    
		        {field:'type',title:'关联任务类型', width:100},
		        {field:'className',title:'对象类型/任务名称', width:100},    
		        {field:'deviceIp',title:'设备IP/源对象名称', width:100}    
		    ]] 
		}); 
		$('#roomData').datagrid('loadData',object);
		dlgTask.dialog("open");
		return ;
	}
		$.messager.confirm("提示", "确定要删除该设备？", function(r) {
		if (r == true) {	
			$('#dlgRoom').panel('close');
			$('#dlgRoom').panel('destroy');
			var uri =getRootName()+"/monitor/deleteMonitorObject/deleteMonitor";
			var load ="<div id='loading' style='margin-top: 30px; margin-left: 20px;'><img src =' "+getRootName()+"/style/images/loading2.gif' /></div>";
			var loadlog = $(load).dialog({
				title:'正在删除中，请稍后....',
				width: 200,
				height: 120,
				modal:true,
				closable:false
			});
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"MOID":ids
				},
				success : function(data) {
					loadlog.panel("close");
					if (true === data) {
						$.messager.alert("提示", "删除成功！", "info");
						reloadTable();
					} else {
						$.messager.alert("提示", "删除失败！", "error");
					}
				}
			};
			ajax_(ajax_param);
		}
	});
	}else{
		$.messager.alert('提示', '没有任何选中项', 'error');
	}	
}
/**
 * 查询该设备是否有采集任务
 * @param ip
 * @return
 */
function checkTaskStatus(ids){
	var objectTaskData="";
		var ajax_param = {
				url : getRootName()+"/monitor/deleteMonitorObject/checkTask",
				type : "post",
				datdType : "json",
				async:false,
				data : {
				"MOID":ids
				},
		success : function(data) {
					objectTaskData =data;
				}
		};
		ajax_(ajax_param);
	return objectTaskData;
}