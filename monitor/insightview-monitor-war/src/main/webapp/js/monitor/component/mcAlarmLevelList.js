$(document).ready(function() {
	doInitTable();
});

/**
 * 回车查询
 */
function globelQueryAlarmLevel(e) {
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
	var levelName = $("#textAlarmLevelName").val();
	$('#tblAlarmLevelList').datagrid('options').queryParams = {
		"alarmLevelName" : levelName
	};
	reloadTableCommon_1('tblAlarmLevelList');
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
	$('#tblAlarmLevelList')
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
					idField : '',
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
						        	field : 'alarmLevelID',
						        	checkbox : true
						        },{
									field : 'alarmLevelName',
									title : '等级名称',
									width : 100,
									align : 'center'
								},{
									field : 'levelColorName',
									title : '等级颜色',
									width : 100,
									align : 'center',
									formatter:function(value,row){ 
										var t = row.levelColor;
										return "<div style='background:"+t+";'>"+value+"</div>"; 
									} 
								}]]
					});
//		$(window).resize(function() {
//			$('#tblAlarmLevelList').resizeDataGrid(0, 0, 0, 0);
//		});
}


function toConfirmSelect(){
	var checkedItems = $('[name=alarmLevelID]:checked');
	var ids = null;
	$.each(checkedItems, function(index, item) {
		if (null == ids) {
			ids = $(item).val();
		} else {
			ids += ',' + $(item).val();
		}
	});
	if (null != ids) {
    		doSelect("AlarmLevel",ids);
    		$('#event_select_dlg').window('close');
	}else{
		$.messager.alert('提示', '没有任何选中项', 'error');
	} 
}