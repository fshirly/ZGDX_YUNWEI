$(document).ready(function() {
	doInitTable();
});

/*
 * 页面加载初始化表格 @author 武林
 */
function doInitTable() {
//	checkBeforeCancle();
	var path = getRootName();
	$('#tblAlarmNotifyCfg')
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
						url : path + '/monitor/alarmNotifyCfg/listAlarmNotifyCfg',
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
									 field : 'policyID',
									 checkbox : true
								},     
								{
									field : 'policyName',
									title : '规则名称',
									width : 150,
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
									width : 200,
									align : 'center',
									formatter : function(value, row, index) {

										return '<a style="cursor: pointer;" onclick="javascript:checkForOpenDetail('
											+ row.policyID
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:checkForOpenUpdate('
											+ row.policyID
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_modify.png" title="修改" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:doDel('
											+ row.policyID
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
									}
								} ] ]
					});
	$(window).resize(function() {
        $('#tblAlarmNotifyCfg').resizeDataGrid(0, 0, 0, 0);
    });
}

/*
 * 更新表格
 */
function reloadTable(){
//	checkBeforeCancle();
	var policyName = $("#txtPolicyName").val();
	$('#tblAlarmNotifyCfg').datagrid('options').queryParams = {
		"policyName" : policyName
	};
	
	reloadTableCommon_1('tblAlarmNotifyCfg');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/*
 * 打开详情页面前的验证
 */
function checkForOpenDetail(policyID){
	var path = getRootName();
	var uri = path + "/monitor/alarmNotifyCfg/checkForOpenDetail";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"policyID" : policyID,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			doOpenDetail(data.policyID,data.smsFlag,data.emailFlag);
		}
	};
	ajax_(ajax_param);
}

/*
 * 打开查看页面
 */
function doOpenDetail(policyID,smsFlag,emailFlag){
	parent.$('#popWin').window({
    	title:'告警通知策略详情',
        width:850,
        height:530,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmNotifyCfg/toShowAlarmNotifyCfgDetail?policyID='+policyID+'&smsFlag='+smsFlag+'&emailFlag='+emailFlag
    });
}

function toDel(alarmDefineID){
	$.messager.confirm("提示","确定删除此告警事件?",function(r){
		if (r == true) {
			checkBeforeDel(alarmDefineID);
		}
	});
}

/*

/*
 * 删除告警通知策略
 */
function doDel(policyID){
	var path=getRootName();
	$.messager.confirm("提示","确定删除此条？",function(r){
		if (r == true) {
			var uri = path + "/monitor/alarmNotifyCfg/delNotifyCfg";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"policyID" : policyID,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (true == data || "true" == data) {
						$.messager.alert("提示", "该告警通知策略删除成功！", "info");
						reloadTable();
					} else {
						$.messager.alert("提示", "该告警通知策略删除失败！", "info");
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
	var checkedItems = $('[name=policyID]:checked');
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
				var path = getRootName();
				var uri = path + "/monitor/alarmNotifyCfg/delNotifyCfgs?policyIDs="+ids;
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
						if (false == data.flag || "false" == data.flag) {
							$.messager.alert("提示", "告警通知策略删除成功！", "error");
							reloadTable();
						} else {
							$.messager.alert("提示", "告警通知策略删除成功！", "info");
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
 * 打开新增页面
 */
function doOpenAdd(){
	try {
		parent.$("#divAddUserNow").window("destroy");
		parent.$("#divAddUser").window("destroy");
	} catch(e){
	}
	parent.$('#popWin').window({
    	title:'新增告警通知策略',
        width:850,
        height:530,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmNotifyCfg/toShowAlarmNotify',
//        onBeforeClose: function () { //当面板关闭之前触发的事件
//			checkBeforeCancle();
//		}
    });
}

function checkBeforeCancle(){
//	console.log("查询名称是否有为空的");
	var path = getRootName();
	var uri = path + "/monitor/alarmNotifyCfg/checkBeforeCancle";
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
			if(data.flag==false||data.flag=="false"){
				//console.log("为空id==="+data.policyIDs);
				toCancel(data.policyIDs);
			}
		}
	}
		ajax_(ajax_param);
}


/*
 * 关闭新增窗口的操作
 */
function toCancel(policyIDs){
		var path = getRootName();
		var uri = path + "/monitor/alarmNotifyCfg/toCancelAdd?policyIDs="+policyIDs;
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
				reloadTable();
			}
		}
			ajax_(ajax_param);
	
}

/*
 * 打开编辑页面前的验证
 */
function checkForOpenUpdate(policyID){
	var path = getRootName();
	var uri = path + "/monitor/alarmNotifyCfg/checkForOpenDetail";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"policyID" : policyID,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			doOpenModify(data.policyID,data.smsFlag,data.emailFlag);
		}
	};
	ajax_(ajax_param);
}

/*
 * 打开编辑页面
 */
function doOpenModify(policyID,smsFlag,emailFlag){
	try {
		parent.$("#divAddUserNow").window("destroy");
		parent.$("#divAddUser").window("destroy");
	} catch(e){
	}
	parent.$('#popWin').window({
    	title:'编辑告警通知策略',
        width:850,
        height:530,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmNotifyCfg/toShowAlarmNotifyCfgModify?policyID='+policyID+'&smsFlag='+smsFlag+'&emailFlag='+emailFlag
    });
}
