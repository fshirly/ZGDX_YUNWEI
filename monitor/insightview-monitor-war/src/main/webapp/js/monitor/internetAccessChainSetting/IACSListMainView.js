$(document).ready(function() {
	doInitTable();
});
/**
 * 刷新表格数据
 */
function reloadTable(){
	var deviceIP = $("#deviceIp").val();
	$('#tblIACSettingList').datagrid('options').queryParams = {
		"deviceIP" : deviceIP
	};
	reloadTableCommon('tblIACSettingList');
}

function reloadTableCommon(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/**
 * @return
 */
function doInitTable(){
	var path = getRootName();
	$('#tblIACSettingList')
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
					url : path + '/monitor/internetAccessChainSetting/listIACSettings',
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
										case "3":
											return "互联网接入";
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
			$('#tblIACSettingList').resizeDataGrid(0, 0, 0, 0);
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
    	title:'新增互联网链路配置',
        width:800,
        height:800,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/internetAccessChainSetting/toAddIACSettingView',
        onClose:function(){tableReload()}
    });
}

function toOpenModify(row){
	//编辑配置项页面
	parent.$('#popWin').window({
    	title:'编辑互联网链路配置',
        width:800,
        height:800,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/internetAccessChainSetting/toModifyIACSettingView?selectedRow='+JSON.stringify(row),
        onClose:function(){tableReload()}
    });
}

/**
 * 批量删除
 *
 */
function doBatchDel(){
	var rows = $("#tblIACSettingList").datagrid('getChecked');
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
    	title:'查看互联网链路配置',
        width:800,
        height:800,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/internetAccessChainSetting/toShowIACSettingView?selectedRow='+JSON.stringify(row)
    });
}

/**
 * 重载表格
 *
 */
function tableReload(){
	$('#tblIACSettingList').datagrid('reload');
	$('#tblIACSettingList').datagrid('uncheckAll');
	$('#tblIACSettingList').datagrid('unselectAll');
}
