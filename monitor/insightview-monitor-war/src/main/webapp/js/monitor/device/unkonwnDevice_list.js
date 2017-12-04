$(document).ready(function() {
	var flag=$('#flag').val();
	doInitTable();
	if(flag !=null && flag !=""){ 
		$('#tblDataList').datagrid('hideColumn','moids');
	}else{
		$('#tblDataList').datagrid('hideColumn','moid');
	}
	initOrgTree();//初始化下拉树（管理域）
});
   
/**
 *  加载页面并初始化表格
 * @return
 */
function doInitTable() {
	var mOClassID = $("#mOClassID").val();
	var flag=$('#flag').val();
	var path = getRootName();
	var uri = path + '/monitor/deviceManager/listUnknownDevice?classID=' + mOClassID;
	$('#tblDataList')
			.datagrid(
					{
						width : 'auto',
						height : 'auto',
						nowrap : true,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : uri,
						remoteSort : false,
						onSortColumn:function(sort,order){
//						 alert("sort:"+sort+",order："+order+"");
						},						
						idField : '',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [{
								'text' : '删除',
								'iconCls' : 'icon-cancel',
								handler : function() {
									doDeleUnkonwn();
								}
							}],
						columns : [ [
								{
										field : 'id',
										checkbox : true
								},
						         {
						        	 field : 'moid',
						        	 title : '',
						        	 width : 40,
						        	 align : 'center',
						        	 formatter : function(value, row, index) {
						        	 	return '<a style="cursor: pointer;" onclick="javascript:sel('+ value+');">选择</a>';
						         	}
						         },
						         {
										field : 'operstatusdetail',
										title : '可用/持续时间',
										width : 50,
										sortable:true,
										align : 'left',
										formatter:function(value,row){
											return '<img title="' + row.operaTip + '" src="' + path
											+ '/style/images/levelIcon/' + row.operstatusdetail +'"/>'+row.durationTime;
											}
								},
								{  
									field : 'deviceip',
									title : '设备IP',
									width : 80,
									sortable:true,
									order:'asc',
									align : 'center'
								},
								{
									field : 'domainName',
									title : '管理域',
									width : 80,
									align : 'center'
								},
								{
									field : 'moids',
									title : '操作',
									width : 80,
									align : 'center',
									formatter : function(value,row,index){
									 var to = "&quot;" + row.moid
										+ "&quot;,&quot;" + row.deviceip
										+ "&quot;"
									return '<a style="cursor:pointer;" title="配置" onclick="javascript:toSet('
									+ to
									+ ');"><img src =" '+path+'/style/images/icon/icon_setting.png" alt="配置" /></a>';	
							    }
								}
								] ]
					});
	$(window).resize(function() {
        $('#tblDataList').resizeDataGrid(0, 0, 0, 0);
    });
}


function toSet(moid,deviceip,moClassId,nemanufacturername,taskId){
	parent.parent.$('#popWin').window({
	title:'采集设备配置',
    width:800,
    height:500,
    minimizable:false,
    maximizable:false,
    collapsible:false,
    modal:true,
    href: getRootName() + '/monitor/configObjMgr/toSetMonitor?moid='+moid+'&deviceip='+deviceip+'&moClassId=-1'
});
}
function resetFormFilter(controlId) {
	resetForm(controlId);
}


/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblDataList').datagrid('options').queryParams = {
		"classID" : $("#mOClassID").val(),
		"deviceip" : $("#deviceip").val(),
		"domainName" : $("#domainName").val()		
	};	
	reloadTableCommon_1('tblDataList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}





function toShowCollector(necollectorid){
		var path = getRootPatch();
		var uri=path+"/monitor/perfTask/toPerfTaskList?serverId="+necollectorid;
		var content = '<iframe scrolling="auto" frameborder="0"  src="'+uri+'" style="width:100%;height:100%;"></iframe>';
		var isExistTabs = parent.document.getElementById("tabs_window");
		parent.$('#tabs_window').tabs('add',{
		         title:'采集任务',
		         content:content,
		         closable:true
		});	
}

function sel(moid){
	if(window.opener) { 
	     fWindowText1 = window.opener.document.getElementById("ipt_deviceId"); 
	     fWindowText1.value = moid; 
	     window.opener.findDeviceInfo();
	     window.close();
	} 
}


/**
 * 初始化下拉树（管理域）
 */
var _orgTreeData = "";
function initOrgTree() {
	var path = getRootPatch();
	var uri = path + "/platform/managedDomain/findManagedDomainTreeVal";
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
			dataOrgTree = new dTree("dataOrgTree", path + "/plugin/dTree/img/");
			dataOrgTree.add(0, -1, "根管理域", "");

			// 得到树的json数据源
			var datas = eval('(' + data.menuLstJson + ')');
			_orgTreeData = datas;
			// 遍历数据
			var gtmdlToolList = datas;
			for (var i = 0; i < gtmdlToolList.length; i++) {
				var _id = gtmdlToolList[i].domainId;
				var _nameTemp = gtmdlToolList[i].domainName;
				var _parent = gtmdlToolList[i].parentId;

				dataOrgTree.add(_id, _parent, _nameTemp,
						"javascript:hiddenDTreeSetVal('domainName','"
								+ _id + "','" + _nameTemp + "');");
			}
			$('#dataOrgTreeDiv').append(dataOrgTree + "");
		}
	}
	ajax_(ajax_param);
}

/**
 * 重新发现
 * @param moid
 * @return
 */					
function doRediscover(moid,deviceip,moClassId,nemanufacturername,taskId){
//	console.log("deviceip==="+deviceip+",  moClassId===="+moClassId)
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
						showDeviceTask(taskid,moid,deviceip,moClassId,nemanufacturername,taskId);
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
function showDeviceTask(taskid,moid,deviceip,moClassId,nemanufacturername,taskId){
	parent.parent.$('#popWin').window({
		title:'设备任务',
	    width:800,
	    height:300,
	    minimizable:false,
	    maximizable:false,
	    collapsible:false,
	    modal:true,
	    href: getRootName() + '/monitor/deviceManager/showDeviceTask?taskid='+taskid+'&moid='+moid+'&deviceip='+deviceip+'&moClassId='+moClassId+'&nemanufacturername='+nemanufacturername
	});
}








function doDeleUnkonwn(){
	var ids=null;
	var rows  = $('#tblDataList').datagrid('getSelections');
	for(var i =0;i<rows.length;i++){
		if (null == ids) {
			ids = rows[i].moid+"_"+rows[i].moClassId;
		} else {
			ids += "," + rows[i].moid+"_"+rows[i].moClassId;
		}
	}
	toAffirmUnkonwn(ids);
}

function closeDlgUnkonwn(){
	$('#dlgUnkonwn').panel('close');
	$('#dlgUnkonwn').panel('destroy');
}
/**
 * 确认删除该设备是否有采集任务
 * @return
 */
function toAffirmUnkonwn(ids){
	if (null != ids) {	
	// 校验其是否有采集任务
	object = checkTaskStatus(ids);
	
	if(null !=object && object !=""){
		var dlgsb = "<div id='dlgUnkonwn'><div class='conditionsBtn'>"
			+"<a href='javascript:void(0);' onclick='javascript:closeDlgUnkonwn()' id='btntaskSave'>关闭</a></div></div>";
		var dlgTask = $(dlgsb).appendTo(document.body).dialog({
			title:'提示',
			width: 400,
			height: 300,
			content: '',
			modal:true,
			closed: true,
			onClose:function(){
			$('#dlgUnkonwn').panel('destroy');
			}
		});
	var serviceTab =$('<table id="unkonwnData"></table>').prependTo(dlgTask.dialog("body")).datagrid({
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
		$('#unkonwnData').datagrid('loadData',object);
		dlgTask.dialog("open");
		return ;
	}
		$.messager.confirm("提示", "确定要删除该设备？", function(r) {
		if (r == true) {	
			$('#dlgUnkonwn').panel('close');
			$('#dlgUnkonwn').panel('destroy');
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