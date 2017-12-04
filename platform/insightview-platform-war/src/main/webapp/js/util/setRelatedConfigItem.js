
/**
 * 打开配置项div
 * 
 * @author Maowei
 */
function toAddCatelog() {
	// 先加载配置项列表
	doInitTable();

	resetForm('tblAddCateDef');
	$("#btnSaveCal").unbind("click");
	$("#btnSaveCal").bind("click", function() {
		doAddCatelog();
	});

	$('#divResCi').dialog('open');
}

/**
 * 选择相关配置项
 * 
 * @author Maowei
 */
function doAddCatelog() {
	var row = $('#tblResCiList').datagrid('getSelected');
	if (row) {
		$('#divResCi').dialog('close');
		$("#configurationItem").val(row.ciID); // 相关配置项隐藏域
		$("#configurationItemName").val(row.name); // 相关配置项展示区
	}
}

/**
 * 取消选择
 * 
 * @author Maowei
 */
function doCancel() {
	$('#divResCi').dialog('close');
}

/**
 * 查询
 */
function reloadCiTable() {
	var name = $("#ipt_name").val();
	$('#tblResCiList').datagrid('options').queryParams = {
		"name" : name
	};
	$('#tblResCiList').datagrid('reload');
	$('#tblResCiList').datagrid('unselectAll');
}

/**
 * 加载配置项列表
 * 
 * @author Maowei
 */
function doInitTable() {
	$('#tblResCiList').datagrid({
		iconCls : 'icon-edit',// 图标
		width : 'auto',
		height : 308,
		nowrap : false,
		striped : true,
		border : true,
		collapsible : false,// 是否可折叠的
		//fit : true,// 自动大小
		url : getRootName() + '/resCi/listResCiForService',
		remoteSort : false,
		idField : 'ciID',
		singleSelect : true,// 是否单选
		pagination : true,// 分页控件
		rownumbers : true,// 行号
//		toolbar : [ {
//			text : '确认',
//			iconCls : 'icon-add',
//			handler : function() {
//				doAddCatelog();
//			}
//		}, '-', {
//			text : '取消',
//			iconCls : 'icon-cancel',
//			handler : function() {
//				doCancel();
//			}
//		} ],
		columns : [ [ {
			field : 'name',
			title : '配置项名称',
			width : 150,
			align : 'center'
		}, 
		{
            field : 'resTypeName',
            title : '配置项类型',
            width : 120,
            align : 'center'
        }, 
        {
			field : 'description',
			title : '说明',
			width : 200,
			align : 'center'
		}, {
			field : 'status',
			title : '状态',
			width : 60,
			align : 'center',
			formatter : function(value, row, index) {
				if (0 == value) {
					return "库存";
				} else if (1 == value) {
					return "正在使用";
				} else if (2 == value) {
					return "故障";
				} else if (3 == value) {
					return "移除";
				} else if (4 == value) {
					return "锁定";
				} else {
					return "";
				}
			}
		} ] ]
	});
}
