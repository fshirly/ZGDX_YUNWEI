$(document).ready(function() {
	doInitTable();
});

/**
 * 页面加载初始化表格 @author 武林
 */
function doInitTable() {
	var path = getRootName();
	$('#tblNotifyPolicyCfg')
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
						fitColumns:true,
						url : path + '/platform/notifyPolicyCfg/listNotifyPolicyCfg',
						remoteSort : false,
						idField : 'policyId',
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
									 field : 'policyId',
									 checkbox : true
								},     
								{
									field : 'policyName',
									title : '规则名称',
									width : 150,
									align : 'center',
								},
								{
									field : 'typeName',
									title : '策略类型',
									width : 150,
									align : 'center',
									formatter : function(value, row, index) {
										if(row.policyType==-1){
											return '通用';
										}
										return value;
									}
								},
								{
									field : 'isSms',
									title : '是否短信',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
										if(value==0){
											return '否';
										}else if(value==1){
											return '是';
										}else{
											return '';
										}
									}
								},
								{
									field : 'isEmail',
									title : '是否邮件',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
										if(value==0){
											return '否';
										}else if(value==1){
											return '是';
										}else{
											return '';
										}
									}
								},
								{
									field : 'isPhone',
									title : '是否电话语音',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
										if(value==0){
											return '否';
										}else if(value==1){
											return '是';
										}else{
											return '';
										}
									}
								},
								{
									field : 'policyIDs',
									title : '操作',
									width : 120,
									align : 'center',
									formatter : function(value, row, index) {

										return '<a style="cursor: pointer;" onclick="javascript:doOpenDetail('
											+ row.policyId
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:doOpenModify('
											+ row.policyId
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_modify.png" title="修改" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:doDel('
											+ row.policyId
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
									}
								} ] ]
					});
	$(window).resize(function() {
        $('#tblNotifyPolicyCfg').resizeDataGrid(0, 0, 0, 0);
    });
}

/**
 * 更新表格
 */
function reloadTable(){
	var policyName = $("#txtPolicyName").val();
	$('#tblNotifyPolicyCfg').datagrid('options').queryParams = {
		"policyName" : policyName
	};
	reloadTableCommon_1('tblNotifyPolicyCfg');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/**
 * 删除通知策略
 */
function doDel(policyId){
	var path=getRootName();
	$.messager.confirm("提示","确定删除所选中项？",function(r){
		if (r == true) {
			var uri = path + "/platform/notifyPolicyCfg/delNotifyCfg";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"policyId" : policyId,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (true == data || "true" == data) {
						reloadTable();
					} else {
						$.messager.alert("提示", "该通知策略删除失败！", "info");
					}
				}
			};
			ajax_(ajax_param);
		}
	});
}


/**
 * 批量删除
 */
function doBatchDel(){
	var path=getRootName();
	var checkedItems = $('#tblNotifyPolicyCfg').datagrid("getChecked");
	var ids = null;
	$.each(checkedItems, function(index, item) {
		if (null == ids) {
			ids = item.policyId;
		} else {
			ids += ',' + item.policyId;
		}
	});
	if (null != ids) {
		$.messager.confirm("提示","确定删除所选中项？",function(r){
			if (r == true) {
				var path = getRootName();
				var uri = path + "/platform/notifyPolicyCfg/delNotifyCfgs?policyIds="+ids;
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
						if (false == data || "false" == data) {
							$.messager.alert("提示", "通知策略删除失败！", "info");
						} 
						reloadTable();
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
 * 打开查看页面
 */
function doOpenDetail(policyId){
	parent.$('#popWin').window({
    	title:'通知策略详情',
        width:850,
        height:530,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/platform/notifyPolicyCfg/toShowNotifyCfgDetail?policyId='+policyId,
    });
}


/**
 * 打开新增页面
 */
function doOpenAdd(){
	try {
		parent.$("#divAddCasualUser").window("destroy");
		parent.$("#divAddUser").window("destroy");
		parent.$("#divAddDutier").window("destroy");
	} catch(e){
	}
	parent.$('#popWin').window({
    	title:'新增通知策略',
        width:850,
        height:530,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/platform/notifyPolicyCfg/toShowAddNotifyCfg?editFlag=add',
    });
}

/**
 * 打开编辑页面
 */
function doOpenModify(policyId){
	try {
		parent.$("#divAddCasualUser").window("destroy");
		parent.$("#divAddUser").window("destroy");
		parent.$("#divAddDutier").window("destroy");
	} catch(e){
	}
	parent.$('#popWin').window({
    	title:'编辑通知策略',
        width:850,
        height:530,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/platform/notifyPolicyCfg/toShowAddNotifyCfg?editFlag=update&policyId='+policyId
    });
}

