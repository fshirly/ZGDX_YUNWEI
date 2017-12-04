$(document).ready(function() {
	doInitTable();

});

/**
 * 回车查询
 */
function globelQueryAlarmType(e) {
	if(!e){
		e = window.event;
	}
	if ((e.keyCode || e.keyCode) == 13) {
		reloadTable();
	}
} 


/**
 * 刷新表格数据
 */
function reloadTable(){
	var tpyeName = $("#textAlarmTypeName").val();
	$('#tblAlarmTypeList').datagrid('options').queryParams = {
		"alarmTypeName" : tpyeName
	};
	reloadTableCommon_1('tblAlarmTypeList');
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
	var uri = $("#proUrl").val();
	$('#tblAlarmTypeList')
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
					idField : '',
					fitColumns:true,
					singleSelect : true,// 是否单选
					checkOnSelect : false,
					selectOnCheck : false,
					pagination : true,// 分页控件
					rownumbers : true,// 行号
//					toolbar : [ {
//						'text' : '确认',
//						'iconCls' : 'icon-add',
//						handler : function() {
//							toConfirmSelect();
//						}
//					} ],				
					columns : [[
								{
									field : 'alarmTypeID',
									checkbox : true
								},{
									field : 'alarmTypeName',
									title : '类型名称',
									width : 120,
									align : 'center'
								}]]
					});
}

function toConfirmSelect(){
	var checkedItems = $('[name=alarmTypeID]:checked');
	var ids = null;
	$.each(checkedItems, function(index, item) {
		if (null == ids) {
			ids = $(item).val();
		} else {
			ids += ',' + $(item).val();
		}
	});
	if (null != ids) {
    		doSelect("AlarmType",ids);
    		$('#event_select_dlg').window('close');
	}else{
		$.messager.alert('提示', '没有任何选中项', 'error');
	} 
}