$(document).ready(function() {
	doInitTable();
});

var monitorTypesList = [];
/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
	var path = getRootName();
	$('#tblSysMoTypeList')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 500,
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns:true,
						url : path + '/monitor/sysMo/listSysMoType',
						// sortName: 'code',
						// sortOrder: 'desc',
						remoteSort : true,
						idField : 'monitorTypeID',
						singleSelect : false,// 是否单选
						checkOnSelect : false, 
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '新增',
							'iconCls' : 'icon-add',
							handler : function() {
								doOpenAdd();
							}
						},
						{
							'text' : '删除',
							'iconCls' : 'icon-cancel',
							handler : function() {
								doBatchDel();
							}
						}
						],
						
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
									return '<a style="cursor: pointer;" onclick="javascript:doOpenModify('
									+ row.monitorTypeID
									+ ');"><img src="'
									+ path
									+ '/style/images/icon/icon_modify.png" title="修改" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:doDel('
									+ row.monitorTypeID 
									+ ');"><img src="'
									+ path
									+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
						}
								} ] ]
					});
    $(window).resize(function() {
        $('#tblSysMoTypeList').resizeDataGrid(0, 0, 0, 0);
    });
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


/**
 * 打开编辑页面
 * @return
 */
function doOpenAdd(){
	// 查看配置项页面
	parent.$('#popWin').window({
    	title:'新增监测器类型',
        width:600,
        height:200,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/sysMo/toShowAddView'
    });
}


function doOpenModify(monitorTypeId){
	// 查看配置项页面
	parent.$('#popWin').window({
    	title:'修改监测器类型',
        width:600,
        height:200,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/sysMo/toShowModifyView?monitorTypeID='+monitorTypeId
    });
}



/*
 * 删除
 */
function doDel(monitorTypeId) {
	$.messager.confirm("提示","确定删除此监测类型？",function(r){
		if (r == true) {
			isExistMonitors(monitorTypeId);
		}
	});
	
}

function doBatchDel(){
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
			var uri=getRootName()+"/monitor/sysMo/toGetMonitorsByMonitorTypeId";
			var ajax_param={
					url:uri,
					type:"post",
					dateType:"json",
					data:{
						"monitorTypeIDs":ids,
						"t" : Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				if(data > 0){
					$.messager.alert("提示","该监测器类型已经被使用，不能删除！","info");
				}else{
					var uri=getRootName()+"/monitor/sysMo/toGetUsedTemplateByTypeId";
					var ajax_param={
							url:uri,
							type:"post",
							dateType:"json",
							data:{
								"monitorTypeIDs":ids,
								"t" : Math.random()
					},
					error:function(){
						$.messager.alert("错误","ajax_error","error");
					},
					success:function(data){
						if(data > 0){
							$.messager.alert("提示","监测器类型关联的模板已经被套用，不能删除！","info");
						}else{
							deleteMoClass(ids);
						}
					}
					};
					ajax_(ajax_param);
				}
			}
			};
			ajax_(ajax_param);
}
	}
}

function deleteMoClass(monitorTypeId){
	var uri=getRootName()+"/monitor/sysMo/toDelMoClassByMonitorTypeId";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"monitorTypeIDs":monitorTypeId,
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
//		console.log("data = "+data);
		if(data >= 0){
			var uri=getRootName()+"/monitor/sysMo/toDelByMonitorTypeId";
			var ajax_param={
					url:uri,
					type:"post",
					dateType:"json",
					data:{
						"monitorTypeIDs":monitorTypeId,
						"t" : Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
//				console.log("data2 = "+data);
				if(data == true){
					$.messager.alert("提示", "监测对象类型删除成功！", "info");
					reloadTable();
				}else{
					$.messager.alert("提示", "监测对象类型删除失败！", "info");
				}
			}
			};
			ajax_(ajax_param);
		}else{
			$.messager.alert("提示", "监测对象类型删除失败！", "info");
		}
	}
	};
	ajax_(ajax_param);

}

function isExistMonitors(monitorTypeId){
	var uri=getRootName()+"/monitor/sysMo/toGetMonitorsByMonitorTypeId";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"monitorTypeIDs":monitorTypeId,
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		if(data > 0){
			$.messager.alert("提示","该监测器类型已经被使用，不能删除！","info");
		}else{
			var uri=getRootName()+"/monitor/sysMo/toGetUsedTemplateByTypeId";
			var ajax_param={
					url:uri,
					type:"post",
					dateType:"json",
					data:{
						"monitorTypeIDs":monitorTypeId,
						"t" : Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				if(data > 0){
					$.messager.alert("提示","该监测器类型关联的模板已经被套用，不能删除！","info");
				}else{
					deleteMoClass(monitorTypeId);
				}
			}
			};
			ajax_(ajax_param);
		}
	}
	};
	ajax_(ajax_param);
}