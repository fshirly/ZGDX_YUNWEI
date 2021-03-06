$(document).ready(function() {
	doInitTable();
});

/*
 * 页面加载初始化表格
 */
function doInitTable() {
	var path = getRootName();
	$('#tblRoomCommunity')
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
						url : path + '/monitor/roomCommunity/listRoomCommunity',
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
									field : 'ipAddress',
									title : 'IP',
									width : 180,
									sortable : true
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
												+ "&quot;,&quot;" + row.ip
												+ "&quot;";
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
		$('#tblRoomCommunity').resizeDataGrid(0, 0, 0, 0);
	});
}

/*
 * 刷新表格数据
 */
function reloadTable() {
	var ip = $("#txtIP").val();
	var userName = $("#txtUserName").val();
	$('#tblRoomCommunity').datagrid('options').queryParams = {
		"ipAddress" : ip,
		"userName" : userName
	};
	reloadTableCommon_1('tblRoomCommunity');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

function doDel(id) {
	var path = getRootName();
	$.messager.confirm("提示", "确定删除此信息?", function(r) {
		if (r == true) {
			var uri = path + "/monitor/roomCommunity/delRoomCommunity";
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
						//						$.messager.alert("提示", "删除成功！", "info");
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

/*
 * 批量删除
 */
function doBatchDel() {
	var path = getRootName();
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
		$.messager.confirm("提示", "确定删除所选中项？", function(r) {
			if (r == true) {
				var uri = path
						+ "/monitor/roomCommunity/delRoomCommunitys?delIDs="
						+ ids;
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
						if (true == data || "true" == data) {
							//							$.messager.alert("提示", "删除成功！", "info");
					reloadTable();
				} else {
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

/*
 * 新增页面
 */
function doOpenAdd() {
	parent.$('#popWin').window( {
		title : '新增动环系统凭证',
		width : 600,
		height : 300,
		minimizable : false,
		maximizable : false,
		collapsible : false,
		modal : true,
		href : getRootName() + '/monitor/roomCommunity/toRoomCommunityAdd'
	});
}

/*
 * 打开更新界面
 */
function toOpenModify(id) {
	parent.$('#popWin').window(
			{
				title : '编辑动环系统凭证',
				width : 600,
				height : 300,
				minimizable : false,
				maximizable : false,
				collapsible : false,
				modal : true,
				href : getRootName()
						+ '/monitor/roomCommunity/toRoomCommunityModify?id='
						+ id
			});
}

/*
 * 打开查看详情界面
 */
function toShowInfo(id) {
	parent.$('#popWin').window(
			{
				title : '动环系统凭证详情',
				width : 800,
				height : 300,
				minimizable : false,
				maximizable : false,
				collapsible : false,
				modal : true,
				href : getRootName()
						+ '/monitor/roomCommunity/toShowCommunity?id=' + id
			});
}