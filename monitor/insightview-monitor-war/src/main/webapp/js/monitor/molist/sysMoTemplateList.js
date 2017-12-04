$(document).ready(function() {
	doInitTable();
});

/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable() {
	var path = getRootName();
	$('#tblSysMoTemplateList')
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
						url : path + '/monitor/sysMoTemplate/listSysMoTemplate',
						remoteSort : true,
						idField : 'templateID',
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
						        	field : 'templateID',
						        	checkbox : true
						        },
								{
									field : 'templateName',
									title : '模板名称',
									width : 100,
									align : 'center'
								},
								{
									field : 'moClassLable',
									title : '适用对象',
									width : 100,
									align : 'center'
								},
								{
									field : 'templateIDs', 
									title : '操作',
									width : 50,
									align : 'center',
									formatter : function(value, row, index) {
									return '<a style="cursor: pointer;" onclick="javascript:doOpenDetail('
									+ row.templateID
									+ ');"><img src="'
									+ path
									+ '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:doOpenModify('
									+ row.templateID
									+ ');"><img src="'
									+ path
									+ '/style/images/icon/icon_modify.png" title="修改" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:doDel('
									+ row.templateID 
									+ ');"><img src="'
									+ path
									+ '/style/images/icon/icon_delete.png" title="删除" /></a>&nbsp;<a style="cursor: pointer;" onclick="javascript:doOpenDevice('
									+ row.templateID 
									+ ');"><img src="'
									+ path
									+ '/style/images/icon/icon_setting.png" title="查看绑定的设备" /></a>';
						}
								} ] ]
					});
    $(window).resize(function() {
        $('#tblSysMoTemplateList').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 刷新表格数据
 */
function reloadTable() {
	$('#tblSysMoTemplateList').datagrid('options').queryParams = {
		"templateName" : $("#txtTemplateName").val()
	};
	reloadTableCommon_1('tblSysMoTemplateList');
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
    	title:'监测器模板定义',
        width:750,
        height:550,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/sysMoTemplate/toShowAddView'
    });
}


function doOpenModify(templateID){
	var uri=getRootName()+"/monitor/sysMoTemplate/existUsedTemplate";
	var ajax_param={
			url:uri,
			type:"post",
			dateType:"json",
			data:{
				"templateIDs":templateID,
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		if(data == true){
			$.messager.alert("提示", "该模板已经被套用，不能编辑修改！", "info");
		}else{
			// 编辑配置项页面
			parent.$('#popWin').window({
		    	title:'修改监测器模板',
		        width:750,
		        height:550,
		        minimizable:false,
		        maximizable:false,
		        collapsible:false,
		        modal:true,
		        href: getRootName() + '/monitor/sysMoTemplate/toShowModifyView?templateID='+templateID
		    });
		
		}
	}
	};
	ajax_(ajax_param);
}


function doOpenDetail(templateID){
	// 查看配置项页面
	parent.$('#popWin').window({
    	title:'监测器模板详情',
        width:750,
        height:550,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/sysMoTemplate/toShowDetailView?templateID='+templateID
    });
}

/*
 * 删除
 */
function doDel(templateID) {
	$.messager.confirm("提示","确定删除此监测模板？",function(r){
		if (r == true) {
			var uri=getRootName()+"/monitor/sysMoTemplate/existUsedTemplate";
			var ajax_param={
					url:uri,
					type:"post",
					dateType:"json",
					data:{
						"templateIDs":templateID,
						"t" : Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				if(data == true){
					$.messager.alert("提示", "该模板已经被套用，不能删除！", "info");
				}else{
					var uri=getRootName()+"/monitor/sysMoTemplate/delTemplateByID";
					var ajax_param={
							url:uri,
							type:"post",
							dateType:"json",
							data:{
								"templateID":templateID,
								"t" : Math.random()
					},
					error:function(){
						$.messager.alert("错误","ajax_error","error");
					},
					success:function(data){
						if(data == true){
							$.messager.alert("提示", "监测模板删除成功！", "info");
							reloadTable();
						}else{
							$.messager.alert("提示", "监测模板删除失败！", "info");
						}
					}
					};
					ajax_(ajax_param);
				
				}
			}
			};
			ajax_(ajax_param);
		}
	});
	
}

function doBatchDel(){
	var checkedItems = $('[name=templateID]:checked');
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
			var uri=getRootName()+"/monitor/sysMoTemplate/existUsedTemplate";
			var ajax_param={
					url:uri,
					type:"post",
					dateType:"json",
					data:{
						"templateIDs":ids,
						"t" : Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				if(data == true){
					$.messager.alert("提示", "模板已经被套用，不能删除！", "info");
				}else{
					var uri=getRootName()+"/monitor/sysMoTemplate/delTemplatesByIDs?templateIDs="+ids;
					var ajax_param={
							url:uri,
							type:"post",
							dateType:"json",
							data:{
								"t" : Math.random()
					},
					error:function(){
						$.messager.alert("错误","ajax_error","error");
					},
					success:function(data){
						if(data == true){
							$.messager.alert("提示","删除成功！","info");
							reloadTable();
						}else{
							$.messager.alert("提示","删除失败！","info");
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
				"monitorTypeID":monitorTypeId,
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
						"monitorTypeID":monitorTypeId,
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
				"monitorTypeID":monitorTypeId,
				"t" : Math.random()
	},
	error:function(){
		$.messager.alert("错误","ajax_error","error");
	},
	success:function(data){
		if(data > 0){
			$.messager.alert("提示","该监测器类型已经被使用，不能删除！","info");
		}else{
			deleteMoClass(monitorTypeId);
		}
	}
	};
	ajax_(ajax_param);
}

/**
 * 查看套用该模板的设备
 * @param {Object} templateID
 */
function doOpenDevice(templateID){
	// 查看配置项页面
	parent.$('#popWin').window({
    	title:'查看绑定模板的设备',
        width:750,
        height:550,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/sysMoTemplate/doOpenDevice?templateID='+templateID
    });
}