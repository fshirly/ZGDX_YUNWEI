/**
 * 运维人员登录，可以进行公告的查询和创建
 */
var i;
$(document).ready(function() {
	$('#divFilter').show();
	
	$('#rapidTab').tabs({
		onSelect : function(title) {
			if (title == '快速创建') {
				$("#errorText").empty();
			}
		}
		
	});
	
	$('#noticeListTab').tabs({
		width : 1158,
		height : 600,
		boder : false,
		modal : true,
		onSelect : function(title) {
			if (title == '当前通知公告') {
				i = 0;
				doInitTable('tblAnnouncementList', 'Reserved');
			} else {
				i = 1;
				doInitTable('tblAnnouncementList1', 'Completed');
			}
		}
	});
	var tab = $('#noticeListTab').tabs('getSelected');
	// 判断第一次加载页面时调用初始化方法
	if ($('#noticeListTab').tabs('getTabIndex', tab) == 0) {
		doInitTable('tblAnnouncementList', 'Reserved');
	}
	
	$('#ipt_deadLine1').datetimebox({
		required : false,
		showSeconds : true,
		editable : false,
		formatter : formatDateText
	});
	$('#deadLineFilter').combobox({
		panelHeight : '120',
		url : getRootName() + '/dict/readItems?id=3067',
		valueField : 'key',
		textField : 'val',
		editable : false
	});
	$('#deadLineFilter').combobox('setValue', '0');
});

/**
 * 重置
 */
function resetForm1() {
	$('#txtFilterTitle').val('');
	$('#deadLineFilter').combobox('setValue', '0');
}

function doInitTable(tblAnnouncementList, workType) {
	var path = getRootName();
	$('#' + tblAnnouncementList)
			.datagrid(
					{
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fitColumns : true,
						fit : true,// 自动大小
						url : path + '/announcement/queryBlock?workType=' + workType,
						remoteSort : false,
						idField : 'id',
						singleSelect : true,// 是否单选
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
								{
									field : 'title',
									title : '标题',
									width : 240,
									align : 'center',
								},
								{
									field : 'createTime',
									title : '创建时间',
									width : 100,
									align : 'center'
								},
								{
									field : 'creator',
									title : '发布人',
									width : 140,
									align : 'center'
								},
								{
									field : 'deadLine',
									title : '有效期',
									width : 140,
									align : 'center'
								},
								{
									field : 'id',
									title : '查看',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
										if (workType == 'Reserved') {
											return '<a style="cursor: pointer;" title="查看" class="easyui-tooltip" onclick="javascript:toLook('
													+ row.id
													+ ');"><img src="'
													+ path
													+ '/style/images/icon/icon_view.png" alt="查看" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a style="cursor: pointer;" title="修改" class="easyui-tooltip" onclick="javascript:toUpdate('
													+ row.id
													+ ');"><img src="'
													+ path
													+ '/style/images/icon/icon_modify.png" alt="修改" /></a>';
										} else {
											return '<a style=\'cursor:pointer\' title="查看" class="easyui-tooltip" onclick="javascript:toLook('
													+ row.id
													+ ');"><img src="'
													+ path
													+ '/style/images/icon/icon_view.png" alt="查看" /></a>';
										}
									}
								} ] ]
					});
	// 重载列表后全不选
	$('#' + tblAnnouncementList).datagrid('unselectAll');
}

function toLook(id) {
	parent.$('#popWin').window({
    	title : '查看通知公告' ,
        width : 760 ,
        height : 355 ,
        minimizable : false ,
        maximizable : false ,
        collapsible : false ,
        modal : true ,
        href: getRootName() + '/announcement/announcementDetailView?id=' + id
    });
}

function toUpdate(id) {
	parent.$('#popWin').window({
    	title : '编辑通知公告' ,
        width : 760 ,
        height : 355 ,
        minimizable : false ,
        maximizable : false ,
        collapsible : false ,
        modal : true ,
        href: getRootName() + '/announcement/getAnnouncementById?id=' + id
    });
}

function reloadTable() {
	var title = $("#txtFilterTitle").val();
	var deadLineInt = $("#deadLineFilter").combobox("getValue");
	if (i == 0 || i == undefined) {
		$('#tblAnnouncementList').datagrid('options').queryParams = {
			"title" : title,
			"deadLineInt" : deadLineInt
		};
		reloadTableCommon('tblAnnouncementList');
	}
	if (i == 1 || i == undefined) {
		$('#tblAnnouncementList1').datagrid('options').queryParams = {
			"title" : title,
			"deadLineInt" : deadLineInt
		};
		reloadTableCommon('tblAnnouncementList1');
	}

}