/**
 * 查看更多--我的关注列表命名空间
 */
f.namespace('platform.dashboard');
/**
 * 我的关注列表API
 */
platform.dashboard.myConcernList = (function() {

	/**
	 * 暴露给页面的API
	 */
	var myConcernList = {
		showDetail : function(ciID) {
			showDetail(ciID);
		},
		reloadTable : function(ciID) {
			reloadTable(ciID);
		}
	};

	$(document).ready(function() {
		$(window).resize(function() {
			$('#tblMyConcernList').datagrid('resize');
		});
		doInitTable();
	});

	function showDetail(id) {
		parent.parent
				.$('#popWin')
				.window(
						{
							title : '配置项变更信息',
							width : 800,
							height : 540,
							minimizable : false,
							maximizable : false,
							collapsible : false,
							modal : true,
							href : getRootName()
									+ '/dashboardPageManage/myConcern/detail?id='
									+ id
						});
	}

	function reloadTable(){
		reloadTableCommon('tblMyConcernList');
	}
	
	function doInitTable() {
		var path = getRootName();

		$('#tblMyConcernList')
				.datagrid(
						{
							iconCls : 'icon-edit',// 图标
							width : 'auto',
							height : '550',
							fitColumns : true,
							nowrap : false,
							striped : true,
							border : true,
							collapsible : false,// 是否可折叠的
							// fit : true,// 自动大小
							url : '../dashboardPageManage/myConcernList/latest',
							// sortName: 'code',
							// sortOrder: 'desc',
							remoteSort : false,
							idField : 'ciID',
							singleSelect : true,// 是否单选
							pageSize : 5,
							rownumbers : true,
							pagination : true,// 分页控件
							columns : [ [
									{
										field : 'name',
										title : '资源名称',
										align : 'center',
										width : 120,
									},
									{
										field : 'resTypeName',
										title : '资源类型',
										align : 'center',
										width : 120,
									},
									{
										field : 'status',
										title : '状态',
										align : 'center',
										width : 120,
										formatter : function(value, row, index) {
											if (value == 0) {
												return '库存';
											}
											if (value == 1) {
												return '正在使用';
											}
											if (value == 2) {
												return '故障';
											}
											if (value == 3) {
												return '移除/	清理';
											}
										}
									},
									{
										field : 'ciID',
										title : '操作',
										width : 60,
										align : 'center',
										formatter : function(value, row, index) {
											return "<a style='cursor:pointer' onclick=javascript:platform.dashboard.myConcernList.showDetail('" + row.ciID + "');><img src='" + path + "/style/images/icon/icon_view.png' alt='查看变更明细'/></a>";
										}
									} ] ]
						});
	}

	return myConcernList;

})();
