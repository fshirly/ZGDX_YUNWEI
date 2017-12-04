var path = getRootName();
var tabsLst = [];
$(document).ready(function() {
		doInitTable();
	$('#tabs_window').tabs({   
		onClose:function(title){   
			tabsLst.remove(title);     
	      }   
	  });
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
	var MoClassID =$("#hiddenMoClassID").val();
	$('#tblStoneuList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : path + '/monitor/stoneu/stonuList?MOClassID='+MoClassID,
						remoteSort : false,
						idField : 'moID',
						singleSelect : false,// 是否单选
						checkOnSelect : false, 
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '新增',
							'iconCls' : 'icon-add',
							handler : function() {
								doOpenAdd(MoClassID);
							}
						},{
							'text' : '删除',
							'iconCls' : 'icon-cancel',
							handler : function() {
								doDeleStoneu();
							}
						}],
						columns : [ [
						        {
						        	field : 'MOid',
						        	checkbox : true
						        },
						        {
										field : 'operstatusdetail',
										title : '可用/持续时间',
										width : 50,
										align : 'left',
										formatter:function(value,row){
											return '<img title="' + row.operaTip + '" src="' + path
											+ '/style/images/levelIcon/' + row.operstatusdetail +'"/>'+row.durationTime;
											}
								},
								{
									field : 'alarmLevelName',
									title : '告警级别',
									width : 58,
									align : 'center',
									formatter:function(value,row){
										var val = value;
										var t = row.levelIcon;
										if(val==null || val==""){
											val="正常";
											t="right.png";
										}
										return '<img src="' + path
												+ '/style/images/levelIcon/' + t + '"/>' + val;
									} 
								},
						        {
									field : 'MOClassID',
									title : '设备类型',
									width : 60,
									align : 'center',
								   formatter: function(value, row, index){
									 if(row.moclassID==109){
										 value="斯特纽"
									 } 
							        	return  value;
						            }
								},
								{
									field : 'moName',
									title : '模块名称',
									width : 60,
									align : 'center' 
								},
								{
									field : 'deviceIp',
									title : 'IP地址',
									width : 60,
									align : 'center',
								   formatter: function(value, row, index){
								   	var deviceIp = "&quot;" + row.deviceIp + "&quot;"
						        		return '<a style="cursor: pointer;" onclick="javascript:viewPerf(' + deviceIp + ');">' + value + '</a>';
					            }
								},
								{
									field : 'moids',
									title : '操作',
									width : 60,
									align : 'center',
								 formatter : function(value,row,index){
								 	var to = "&quot;" + row.moid +
				                    "&quot;,&quot;" +row.moclassID +"&quot;";
									return '<a style="cursor:pointer;" title="配置" onclick="javascript:toUpdate('
											+ to
											+ ');"><img src =" '+path+'/style/images/icon/icon_setting.png" alt="配置" /></a>';	
							    }
								}
								 ] ]
					});
    $(window).resize(function() {
        $('#tblStoneuList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 刷新表格数据
 */
function reloadTable() {
	 
	$('#tblStoneuList').datagrid('options').queryParams = {
		"moName" : $("#moName").val()
	};
	reloadTableCommon_1('tblStoneuList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function viewPerf(deviceIp){
    var title = deviceIp +"_"+ '详情';
    var isPartentTabs = document.getElementById("tabs_window_stoneu");
	 if (null !=$('#tabs_window_stoneu').tabs('getTab',title)) {
		$.messager.alert("提示","该站点视图已经打开！","info");
	} else{
		if(isPartentTabs !=null){
			$('#tabs_window_stoneu').tabs('add', {
				title: title,
				href: path + "/monitor/stoneu/perfInfo?deviceIp="+deviceIp,
				closable: true
			});
		}
	}
}


/*
 * 打开新增页面
 */
function doOpenAdd(MoClassID){
	parent.$('#popWin').window({
    	title:'新建',
        width:800,
        height : 500,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/stoneu/toAdd?moClassID='+MoClassID,
    });
}

function toUpdate(moID,moClassID){
	parent.$('#popWin').window({
    	title:'编辑',
        width:800,
        height : 500,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/stoneu/toUpdate?moID='+moID+'&moClassID='+moClassID,
    });
}




function doDeleStoneu(){
	var ids=null;
	var rows  = $('#tblStoneuList').datagrid('getSelections');
	for(var i =0;i<rows.length;i++){
		if (null == ids) {
			ids = rows[i].moid+"_"+rows[i].moclassID;
		} else {
			ids += "," + rows[i].moid+"_"+rows[i].moclassID;
		}
	}
	toAffirmStoneu(ids);
}

function closeDlgUnkonwn(){
	$('#dlgAcUPS').panel('close');
	$('#dlgAcUPS').panel('destroy');
}
/**
 * 确认删除该设备是否有采集任务
 * @return
 */
function toAffirmStoneu(ids){
	if (null != ids) {	
	// 校验其是否有采集任务
	object = checkTaskStatus(ids);
	
	if(null !=object && object !=""){
		var dlgsb = "<div id='dlgAcUPS'><div class='conditionsBtn'>"
			+"<a href='javascript:void(0);' onclick='javascript:closeDlgUnkonwn()' id='btntaskSave'>关闭</a></div></div>";
		var dlgTask = $(dlgsb).appendTo(document.body).dialog({
			title:'提示',
			width: 400,
			height: 300,
			content: '',
			modal:true,
			closed: true,
			onClose:function(){
			$('#dlgAcUPS').panel('destroy');
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
			$('#dlgAcUPS').panel('close');
			$('#dlgAcUPS').panel('destroy');
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