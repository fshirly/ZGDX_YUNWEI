$(document).ready(function() {
	doInitTable();
});
/**
 * 刷新表格数据
 */
function reloadTable(){
	var deviceIP = $("#deviceIp").val();
	$('#tblWBSettingList').datagrid('options').queryParams = {
		"deviceIP" : deviceIP
	};
	reloadTableCommon_1('tblWBSettingList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/**
 * @return
 */
function doInitTable(){
	var path = getRootName();
	$('#tblWBSettingList')
		.datagrid(
				{
					iconCls : 'icon-edit', //图标
					height : 'auto',
					nowrap : false,
					striped : true,
					border : true,
					collapsible : false,// 是否可折叠的
					fit : true,// 自动大小
					fitColumns : true,
					url : path + '/monitor/deviceWBListSetting/listDeviceWBSettings',
					idField : 'blackID',
					singleSelect : false,// 是否单选
					checkOnSelect : false,
					selectOnCheck : true,
					pagination : true,// 分页控件
					rownumbers : true,// 行号
					toolbar : [{
						'text' : '新增',
						'iconCls' : 'icon-add',
						handler : function(){
							doOpenAdd();
						}
					},
					{
						'text' : '删除',
						'iconCls' : 'icon-cancel',
						handler : function() {
							doBatchDel();
						}
					},
					{
						'text' : '批量恢复',
						'iconCls' : 'icon-start',
						handler : function() {
							doBatchStart();
						}
					},
					{
						'text' : '批量暂停',
						'iconCls' : 'icon-stop',
						handler : function() {
							doBatchStop();
						}
					}],

					columns : [[
								{
									field : 'blackID',
									checkbox : true
								},{
									field : 'deviceIP',
									title : '设备IP',
									width : 100,
									align : 'center'
								},{
									field : 'oprateStatus',
									title : '是否启用',
									width : 100,
									align : 'center',
									formatter:function(value,row,index){
										switch (row.oprateStatus){
										case "0":
											return "未启用";
											break;
										case "1":
											return "启用";
											break;
										default:
											break;
										}
									}
								},{
									field : 'portType',
									title : '类型',
									width : 100,
									align : 'center',
									formatter:function(value,row,index){
										switch (row.portType){
										case "1":
											return "接口";
											break;
										case "2":
											return "服务端口";
											break;
										default:
											break;
										}
									}
								},{
									field : 'alarmTypeIDs',
									title : '操作',
									width : 50,
									align : 'center',
									formatter : function(value, row, index) {
										var rowString = JSON.stringify([].concat(row)).replace(/\"/g,"'");
										return '<a style="cursor: pointer;" title="查看" onclick="javascript:toShowInfo('
												+ rowString
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_view.png" alt="查看" /></a> &nbsp;'
												+ ' <a style="cursor: pointer;" title="修改" onclick="javascript:toOpenModify('
												+ rowString
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_modify.png" alt="修改" /></a> &nbsp;'
												+ '<a style="cursor: pointer;" title="删除" onclick="javascript:doDel('
												+ rowString
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_delete.png" alt="删除" /></a>';
									}
								}]]
					});
		$(window).resize(function() {
			$('#tblWBSettingList').resizeDataGrid(0, 0, 0, 0);
		});
}

function doDel(row){
	$.messager.confirm("提示", "确定删除此信息?", function(r) {
		if (r == true) {
			var path = getRootName();
			var uri = path + "/monitor/deviceWBListSetting/deleteWBListSetting";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"rowsToBeDeleted" : JSON.stringify(row),
					"t" : Math.random()
				},
				error : function(){
					$.messager.alert("删除失败","ajax_error","error");
				},
				success : function(data){
					if(data.success=="true"){
						$.messager.alert("提示","删除成功","info");
						tableReload();
					}else{
						$.messager.alert("错误","删除失败","error");
						tableReload();
					}
				}
			};
			$.ajax(ajax_param);
		}
	});
}

/**
 * 新增类型页面
 * @return
 */
function doOpenAdd(){
		parent.$('#popWin').window({
    	title:'新增设备黑白名单',
        width:800,
        height:540,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/deviceWBListSetting/toAddWBListView',
        onClose:function(){tableReload()}
    });
}

function toOpenModify(row){
	//查看配置项页面
	parent.$('#popWin').window({
    	title:'编辑设备黑白名单',
        width:800,
        height:540,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/deviceWBListSetting/toEditWBListSetting?selectedRow='+JSON.stringify(row),
        onClose:function(){tableReload()}
    });
}

/**
 * 批量删除
 *
 */
function doBatchDel(){
	var rows = $("#tblWBSettingList").datagrid('getChecked');
	if(rows.length==0){
		$.messager.alert("提示", "没有选中项", "info");
		return;
	}
	doDel(rows);
}

/**
 * 告警类型详情
 * @return
 */
function toShowInfo(row){
		parent.$('#popWin').window({
    	title:'查看设备黑白名单',
        width:800,
        height:540,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/deviceWBListSetting/toShowWBListSetting?selectedRow='+JSON.stringify(row)
    });
}

/**
 * 批量恢复
 *
 */
function doBatchStart(){
	var rows = $("#tblWBSettingList").datagrid('getChecked');
	if(rows.length==0){
		$.messager.alert("提示", "没有选中项", "info");
		return;
	}
	var ajaxParam={
		url:getRootName()+"/monitor/deviceWBListSetting/editWBSettings",
		data:{
			"selectedRows": JSON.stringify(rows),
			"operateStatusForDevice": 1,//启用
			"operateStatusChanged":true
		},
		dataType:"json",
		type:"post",
		success:function(data){
			if(data.success=="true"){
				$.messager.alert("提示", "恢复成功", "info");
				tableReload();
			}
		},
		error:function(){
			$.messager.alert("提示", "恢复失败", "error");
			tableReload();
		}
	};
	$.ajax(ajaxParam);
}

/**
 * 批量暂停
 *
 */
function doBatchStop(){
	var rows = $("#tblWBSettingList").datagrid('getChecked');
	if(rows.length==0){
		$.messager.alert("提示", "没有选中项", "info");
		return;
	}
	var ajaxParam={
		url:getRootName()+"/monitor/deviceWBListSetting/editWBSettings",
		data:{
			"selectedRows": JSON.stringify(rows),
			"operateStatusForDevice": 0,//不启用
			"operateStatusChanged":true
		},
		dataType:"json",
		type:"post",
		success:function(data){
			if(data.success=="true"){
				$.messager.alert("提示", "暂停成功", "info");
				tableReload();
			}
		},
		error:function(){
			$.messager.alert("提示", "暂停失败", "error");
			tableReload();
		}
	};
	$.ajax(ajaxParam);
}

/**
 * 重载表格
 *
 */
function tableReload(){
	$('#tblWBSettingList').datagrid('reload');
	$('#tblWBSettingList').datagrid('uncheckAll');
	$('#tblWBSettingList').datagrid('unselectAll');
}
