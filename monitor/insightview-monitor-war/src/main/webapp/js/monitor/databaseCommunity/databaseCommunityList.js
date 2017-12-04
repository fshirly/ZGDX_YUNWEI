$(document).ready(function() {
	doInitTable();
});

/**
 * 刷新表格数据
 */
function reloadTable(){
	var ip = $("#txtDeviceIP").val();
	var dbName = $("#txtDBName").val();
	var dbmsType = $("#txtDBMSType").val();
	var userName = $("#txtUserName").val();
	$('#tblDatabaseCommunity').datagrid('options').queryParams = {
		"ip" : ip,
		"dbName" : dbName,
		"dbmsType" : dbmsType,
		"userName" : userName
	};
	reloadTableCommon_1('tblDatabaseCommunity');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/**
 * 页面加载初始化表格
 */
function doInitTable() {
	var path = getRootName();
	$('#tblDatabaseCommunity')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 700,
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns : true,
						url : path + '/monitor/databaseCommunity/listDatabaseCommunity',
						remoteSort : false,
						idField : 'fldId',
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
						}, {
							text : '删除',
							iconCls : 'icon-cancel',
							handler : function() {
								doBatchDel();
							}
						} ],
						columns : [ [
								{
									field : 'id',
									checkbox : true
								},
								{
									field : 'ip',
									title : '设备IP',
									width : 180,
									sortable : true
								},
								{
									field : 'dbName',
									title : '数据库名',
									width : 180,
									align : 'center'
								},
								{
									field : 'dbmsType',
									title : '数据库类型',
									width : 160,
									align : 'center',
								},
								{
									field : 'userName',
									title : '用户名',
									width : 160,
									align : 'center'
								},
								{
									field : 'port',
									title : '端口',
									width : 160,
									align : 'center'
								},
								{
									field : 'ids',
									title : '操作',
									width : 160,
									align : 'center',
									formatter : function(value, row, index) {
										var param = "&quot;" + row.id
												+ "&quot;,&quot;"
												+ row.ip + "&quot;";
										return '<a style="cursor: pointer;" onclick="javascript:toShowInfo('
												+ row.id
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:toOpenModify('
												+ param
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_modify.png" alt="" title="修改"/></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:doDel('
												+ row.id
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_delete.png" alt="" title="删除" /></a>';
									}
								} ] ]
					});
	// 浏览器窗口大小变化时，datagrid表格自适应窗口大小变化
	$(window).resize(function() {
		$('#tblDatabaseCommunity').resizeDataGrid(0, 0, 0, 0);
	});
}

function doDel(id){
	var path = getRootName();
	$.messager.confirm("提示", "确定删除此信息?", function(r) {
		if (r == true) {
			var uri = path + "/monitor/databaseCommunity/delDatabaseCommunity";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"id" : id,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (true == data || "true" == data) {
						$.messager.alert("提示", "删除成功！", "info");
						reloadTable();
					} else {
						$.messager.alert("", "删除失败！", "error");
					}
				}
			}
			ajax_(ajax_param);
		}
	});
}

/**
 * 批量删除
 * 
 */

function doBatchDel(){
	var path=getRootName();
	var checkedItems = $('[name=id]:checked');
	var ids = null;
	$.each(checkedItems, function(index, item) {
		if (null == ids) {
			ids = $(item).val();
		} else {
			ids += ',' + $(item).val();
		}
	});
	if (null != ids) {
		$.messager.confirm("提示","确定删除所选中项？",function(r){
			if (r == true) {
				var uri = path+ "/monitor/databaseCommunity/doBatchDel?delIDs="+ids;
				var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"t" : Math.random()
					},
					error : function() {
						$.messager.alert("错误", "ajax_error", "error");
					},
					success : function(data) {
						if (1 == data.flag ) {
							$.messager.alert("提示", "删除成功！", "info");
							reloadTable();
						}else{
							$.messager.alert("错误", "删除失败！", "error");
							reloadTable();
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



/**
 * 新增页面
 * @return
 */
function doOpenAdd(){
		parent.$('#popWin').window({
    	title:'新增数据库认证',
        width:600,
        height:350,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/databaseCommunity/toAddDatabaseCommunity'
    });
}

function toOpenModify(id){
	//查看配置项页面
	parent.$('#popWin').window({
    	title:'编辑数据库认证',
        width:600,
        height:350,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/databaseCommunity/toDatabaseCommunityModify?id='+id
    });
}

function toShowInfo(id){
	parent.$('#popWin').window({
    	title:'数据库认证详情',
        width:600,
        height:350,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/databaseCommunity/toShowDatabaseCommunity?id='+id
    });
}