$(document).ready(function() {
	doInitTable();
});

/*
 * 更新表格 @author 武林
 */
function reloadTable() {
	$('#tblEmploymentGrade').datagrid('reload');
}

/*
 * 页面加载初始化表格 @author 武林
 */
function doInitTable() {
	var path = getRootName();
	$('#tblEmploymentGrade').datagrid({
		iconCls : 'icon-edit',// 图标
		width : 700,
		height : 'auto',
		nowrap : false,
		striped : true,
		border : true,
		collapsible : false,// 是否可折叠的
		fit : true,// 自动大小
		url : path + '/permissionEmploymentGrade/listEmploymentGrade',
		// sortName: 'code',
		// sortOrder: 'desc',
		remoteSort : false,
		idField : 'fldId',
		singleSelect : true,// 是否单选
		pagination : true,// 分页控件
		rownumbers : true,// 行号
		columns : [ [ {
			field : 'gradeName',
			title : '所属组织',
			width : 80
		}, {
			field : 'gradeName',
			title : '职位级别名称',
			width : 80
		}, {
			field : 'gradeName',
			title : '操作',
			width : 80,
		} ] ]
	});
}
