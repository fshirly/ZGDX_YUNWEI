$(document).ready(function() {
	doInitTable();

});

/**
 * 回车查询
 */
function globelQueryAlarmEvent(e) {
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
	var alarmName = $("#alarmName").val();
	$('#tblAlarmEventList').datagrid('options').queryParams = {
		"alarmName" : alarmName
	};
	reloadTableCommon_1('tblAlarmEventList');
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
	$('#tblAlarmEventList')
		.datagrid(
				{
					iconCls : 'icon-edit',// 图标
					width : 'auto',
					height : 260,
					nowrap : false,
					striped : true,
					border : true,
					collapsible : false,// 是否可折叠的
					fitColumns:true,
					url : path + uri,
					remoteSort : false,
					idField : 'alarmDefineID',
					singleSelect : false,// 是否单选
					checkOnSelect : true,
					selectOnCheck : true,
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
						        	field : 'alarmDefineID',
						        	checkbox : true
						        },{
									field : 'alarmName',
									title : '告警名称',
									width : 100,
									align : 'center'
								},{
									field : 'alarmTitle',
									title : '告警标题',
									width : 100,
									align : 'center'
								},{
									field : 'isSystem',
									title : '是否系统定义',
									width : 100,
									align : 'center',
									formatter:function(value,row){ 
										if(value=="1"){
											return "是";
										}else{
											return "否";
										}	
									} 
								},{
									field : 'description',
									title : '告警描述',
									width : 100,
									align : 'center'	
								}]]
					});
}


function toConfirmSelect(){
	var checkedItems = $('#tblAlarmEventList').datagrid('getSelections');
	var ids = null;
	$.each(checkedItems, function(index, item) {
		if (null == ids) {
			ids = item.alarmDefineID;
		} else {
			ids += ',' + item.alarmDefineID;
		}
	});
	if (null != ids) {
    		doSelect("AlarmDefineID",ids);
    		$('#event_select_dlg').window('close');
	}else{
		$.messager.alert('提示', '没有任何选中项', 'error');
	} 
}