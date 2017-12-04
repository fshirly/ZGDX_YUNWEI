$(document).ready(function() {
	doInitTable();
});


/**
 * 加载页面并初始化表格
 * @return
 */
function doInitTable(){
	var path = getRootName();
	$('#tblOfflineCollector')
			.datagrid(
					{ 
						iconCls : 'icon-edit',// 图标
						width : 700,
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit:true,// 自动大小
						fitColumns:true,
						url : path + '/monitor/offlineCollector/getOfflineCollectorList',
						remoteSort : true,
						idField : 'id',
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
						} ],
						columns : [ [
								{
									 field : 'id',
									 checkbox : true,
								}, 
								{  
									field : 'ipaddress',
									title : '采集机IP',  
									width : 100,
									align : 'center',
								},
								{
									field : 'servicename',
									title : '服务名称',
									width : 150,
									align : 'center'
								},
								{
									field : 'ids',
									title : '操作',
									width : 120,
									align : 'center',
									formatter : function(value, row, index) {
										var param = "&quot;" + row.id
										+ "&quot;,&quot;" + row.serverid
										+ "&quot;,&quot;" + row.installServiceId
										+ "&quot;"
										return '<a style="cursor: pointer;" onclick="javascript:doOpenModify('
											+ param
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_modify.png" title="修改" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:toDel('
											+ param
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
									}
								} 
								]]
								
					});
	$(window).resize(function() {
        $('#tblOfflineCollector').resizeDataGrid(0, 0, 0, 0);
    });
}
/*
 * 更新表格
 */
function reloadTable(){
	var ip = $("#txtIP").val();
	var serviceName = $("#txtServiceName").val();
	$('#tblOfflineCollector').datagrid('options').queryParams = {
		"ipaddress" : ip,
		"servicename" :serviceName,
	};
	reloadTableCommon_1('tblOfflineCollector');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/*
 * 删除服务
 */
function toDel(id,serverid,installServiceId){
	$.messager.confirm("提示","确定删除此服务?",function(r){
		if (r == true) {
			var path = getRootName();
			var uri = path + "/monitor/offlineCollector/delHostService";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"id" : id,
					"serverid" : serverid,
					"installServiceId" : installServiceId,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if(true == data.isUsed || "true" == data.isUsed){
						$.messager.alert("提示", "该采集机服务正在被使用，不能删除！", "info");
					}else if (true == data.delService || "true" == data.delService) {
						$.messager.alert("提示", "该服务删除成功！", "info");
						reloadTable();
					} else{
						$.messager.alert("提示", "数据库操作异常，该服务删除失败！", "error");
					}
				}
			};
			ajax_(ajax_param);
		}
	});
}

/*
 * 批量删除
 */
function doBatchDel(){
	var path=getRootName();
	var checkedItems = $('#tblOfflineCollector').datagrid("getChecked");
	var ids = null;
	var serverIds = null;
	var idArray = [];
	var serverIdArray = [];
	$.each(checkedItems, function(index, item) {
		var id = item.id;
		var installServiceId = item.installServiceId;
		var serverId = item.serverid;
		idArray.push(id);
		if($.inArray(serverId, serverIdArray) == -1){
			serverIdArray.push(serverId);
		}
	});
	ids = idArray.join(",");
	serverIds = serverIdArray.join(",");
	if (null != ids) {
		$.messager.confirm("提示","确定删除所选中项？",function(r){
			if (r == true) {
				var path = getRootName();
				var uri = path + "/monitor/offlineCollector/batchDelHostService?ids="+ids+"&serverIds="+serverIds;
				var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					error : function() {
						$.messager.alert("错误", "ajax_error", "error");
					},
					success : function(data) {
						if (true == data.delResult || "true" == data.delResult) {
							$.messager.alert("提示", "服务删除成功！", "info");
							reloadTable();
						} else if("" !=data.unDelIds && null != data.unDelIds) {
							$.messager.alert("提示", "ID为：【"+data.unDelIds+"】的采集机服务正在被使用，不能删除！", "info");
							reloadTable();
						} else{
							$.messager.alert("提示", "数据库操作异常，服务删除失败！", "error");
						}
					}
				};
				ajax_(ajax_param);
			}
		});
	} else {
		$.messager.alert('提示', '没有任何选中项', 'error');
	}
}

/*
 * 打开新增页面
 */
function doOpenAdd(){
	parent.$('#popWin').window({
    	title:'新增离线采集机服务',
        width:800,
        height : 400,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/offlineCollector/toAddOfflineCollector',
    });
}


/*
 * 打开编辑页面页面
 */
function doOpenModify(id,serverid,installServiceId){
	parent.$('#popWin').window({
    	title:'编辑离线采集机服务',
        width:800,
        height : 400,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/offlineCollector/toAddOfflineCollector?id='+id+'&serverid='+serverid+'&installServiceId='+installServiceId,
    });
}
