$(document).ready(function() {
	doInitTable();
});

/**
 * 回车查询
 */
function globelQueryAlarmSource(e) {
	if(!e){
		e = window.event;
	}
	if ((e.keyCode || e.keyCode) == 13) {
		reloadTable();
	}
} 

/*
 * 更新表格
 */
function reloadTable(){
	var moName = $("#txtMOName").val();
	var deviceIP = $("#txtIP").val();
	$('#tblMOSource').datagrid('options').queryParams = {
		"moName" : moName,
		"deviceIP":deviceIP
	};
	reloadTableCommon_1('tblMOSource');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(){
	var path = getRootName();
	var uri = $("#proUrl").val();
	$('#tblMOSource')
			.datagrid(
					{ 
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 260,
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						url : path + uri,
						remoteSort : false,
						idField : 'moID',
						fitColumns:true,
						singleSelect: false,// 是否单选
				        checkOnSelect: true,
				        selectOnCheck: true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
//						toolbar : [ {
//							'text' : '确认',
//							'iconCls' : 'icon-add',
//							handler : function() {
//								toConfirmSelect();
//							}
//						} ],
						columns : [ [
						        {
						        	field : 'moID',
						        	checkbox : true
						        },
								{  
									field : 'moName',
									title : '告警源名称',  
									width : 120,
									align : 'center'
								},
								/**{
									field : 'moAlias',
									title : '告警源别名',
									width : 100,
									align : 'center'
								},
								{
									field : 'operStatus',
									title : '可用状态', 
									width : 80,
									align : 'center',
									formatter : function(value, row, index) {
										if (value == 1) {
											return 'UP';
										}else if (value == 2) {
											return 'DOWN';
										}else{
											return '未知';
										}
									}
								},**/
								{
									field : 'deviceIP',
									title : 'IP',
									width : 80,
									align : 'center'
								},
								{
									field : 'className',
									title : '对象类型',
									width : 80,
									align : 'center'
								},
								{
									field : 'domainName',
									title : '管理域', 
									width : 100,
									align : 'center'
								}]]
					});
}

function toConfirmSelect(){
	var checkedItems = $('#tblMOSource').datagrid('getSelections');
	var ids = null;
	$.each(checkedItems, function(index, item) {
		if (null == ids) {
			ids = item.moID;
		} else {
			ids += ',' + item.moID;
		}
	});
	if (null != ids) {
    	doSelect("AlarmSourceMOID",ids);
		$('#event_select_dlg').window('close');
	}else{
		$.messager.alert('提示', '没有任何选中项', 'error');
	} 
}
