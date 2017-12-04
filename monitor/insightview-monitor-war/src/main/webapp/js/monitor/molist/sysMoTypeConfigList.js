$(document).ready(function() {
	var moClassID = $("#moClassID").val();
	doInitTable(moClassID);
});

var monitorTypesList = [];
/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(moClassID) {
	var path = getRootName();
	$('#tblSysMoTypeList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 320,
						nowrap : true,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fitColumns:true,
						url : path + '/monitor/sysMo/listSysMoType?moClassID='+moClassID,
						// sortName: 'code',
						// sortOrder: 'desc',
						remoteSort : true,
						idField : 'monitorTypeID',
						singleSelect : false,// 是否单选
						checkOnSelect : false, 
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
						        {
						        	field : 'monitorTypeID',
						        	checkbox : true
						        },
								{
									field : 'monitorTypeName',
									title : '监测器类型',
									width : 100,
									align : 'center'
								},
								{
									field : 'monitorTypeIds', 
									title : '操作',
									width : 50,
									align : 'center',
									formatter : function(value, row, index) {
									return '<a style="cursor: pointer;" onclick="javascript:doAdd('
									+ row.monitorTypeID
									+ ');">选择</a>';
						}
								} ] ]
					});
//    $(window).resize(function() {
//        $('#tblSysMoTypeList').resizeDataGrid(0, 0, 0, 0);
//    });
}

/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblSysMoTypeList').datagrid('options').queryParams = {
		"monitorTypeName" : $("#txtMonitorTypeName").val()
	};
	reloadTableCommon_1('tblSysMoTypeList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}



/*
 * 删除
 */
function doAdd(monitorTypeID) {
	var moClassID = $("#moClassID").val();
	var uri=getRootName()+"/monitor/sysMoTemplate/addMoTypeOfMoClass";
	var ajax_param={
		url:uri,
		type:"post",
		dateType:"text",
		data:{                
			"monitorTypeID":monitorTypeID,
			"moClassID":moClassID,
			"t" : Math.random()
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			if (data == true) {
				parent.parent.$('#popWin').window('close');
				window.frames['component_2'].frames['component_2'].doInitTable(moClassID);
			} else {
				$.messager.alert("提示","新增失败！","info");
			}
		}
	};	
	ajax_(ajax_param);
}


function toAddTypes(){
	var checkedItems = $('[name=monitorTypeID]:checked');
	var ids = null;
	if (checkedItems.length == 0) {
		$.messager.alert('提示', '没有任何选中项!', 'info');
	} else {
		$.each(checkedItems, function(index, item) {
			if (null == ids) {
				ids = $(item).val();
			} else {
				ids += ',' + $(item).val();
			}
		});
		if(null != ids){
			var moClassID = $("#moClassID").val();
			var uri=getRootPatch()+"/monitor/sysMoTemplate/addMultiMoTypesOfMoClass";
			var ajax_param={
				url:uri,
				type:"post",
				dateType:"text",
				data:{                
					"monitorTypeIDs":ids,
					"moClassID":moClassID,
					"t" : Math.random()
				},
				error : function(){
					$.messager.alert("错误","ajax_error","error");
				},
				success:function(data){
					if (data == true) {
						parent.parent.$('#popWin').window('close');
						window.frames['component_2'].frames['component_2'].doInitTable(moClassID);
					} else {
						$.messager.alert("提示","新增失败！","info");
					}
				}
			};	
			ajax_(ajax_param);
		}
	}
}

function doClose(){
	$('#popWin').window('close');
//	console.log(window.frames['component_2']);
	var moClassID = $("#moClassID").val();
//	console.log("moClassID=="+moClassID)
	window.frames['component_2'].treeClickAction(moClassID,"",-1,"");
}