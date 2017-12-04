$(document).ready(function() {
	doInitTable();
});

/*
 * 页面加载初始化表格 @author 王淑平
 */
function doInitTable() {
	var path = getRootName();
	$('#tblSysparam')
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
						url : path + '/permissionSystemParam/listSysParam',
						remoteSort : false,
						idField : 'fldId',
						singleSelect : true,// 是否单选
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
								{
									field : 'paramClass',
									title : '参数分类',
									width : 250
								},
								{
									field : 'paramName',
									title : '参数名称',
									width : 250
								},
								{
									field : 'paramValue',
									title : '参数值',
									width : 250
								},
								{
									field : 'paramDescr',
									title : '参数说明',
									width : 250
								},
								{
									field : 'paramID',
									title : '操作',
									width : 200,
									align : 'center',
									formatter : function(value, row, index) {

										var rt= '<a style="cursor: pointer;" onclick="javascript:toShowInfo('
												+ row.paramID
												+ ','
												+ "'"+row.paramValue+"'"
												+ ','
												+ "'"+row.paramName+"'"
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:toUpdate('
												+ row.paramID
												+ ','
												+ "'"+row.paramValue+"'"
												+ ','
												+ "'"+row.paramName+"'"
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_modify.png" alt="" title="修改" /></a>';
										return rt;
									}
								} ] ]
					});
	// 浏览器窗口大小变化时，datagrid表格自适应窗口大小变化
	$(window).resize(function() {
		$('#tblSysparam').resizeDataGrid(0, 0, 0, 0);
	});
}
// 查看详情
function toShowInfo(paramId,paramValue,paramName) {
	parent.$('#popWin').window({
		title : '运行参数详情',
		width : 700,
		height : 350,
		minimizable : false,
		maximizable : false,
		collapsible : false,
		modal : true,
		draggable : true,
		href : getRootName() + '/permissionSystemParam/toShowInfo?paramId='+paramId+'&paramValue='+paramValue+'&paramName='+paramName
	});
}

//修改
function toUpdate(paramId,paramValue,paramName) {
	parent.$('#popWin').window({
		title : '运行参数信息',
		width : 700,
		height : 350,
		minimizable : false,
		maximizable : false,
		collapsible : false,
		modal : true,
		draggable : true,
		href : getRootName() + '/permissionSystemParam/toUpdate?paramId='+paramId+'&paramValue='+paramValue+'&paramName='+paramName
	});
}

function reloadTable() {
	var paramClass = $("#txtFilterParamClass").val();
	var paramName = $("#txtFilterParamName").val();
	$('#tblSysparam').datagrid('options').queryParams = {
		"paramClass" : paramClass,
		"paramName" : paramName
	};
	reloadTableCommon('tblSysparam');
}