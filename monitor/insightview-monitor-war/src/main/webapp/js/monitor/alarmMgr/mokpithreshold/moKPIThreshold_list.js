$(document).ready(function() {
	doInitTable();
});

/*
 * 页面加载初始化表格 @author 武林
 */
function doInitTable() {
	var path = getRootName();
	$('#tblthresholdList')
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
						url : path + '/monitor/alarmmgr/moKPIThreshold/listMOKPIThreshold',
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
									 field : 'ruleID',
									 checkbox : true
								}, 
								{
									field : 'className',
									title : '对象类型',
									width : 120,
								},
								{
									field : 'sourceMOName',
									title : '源对象',
									width : 180,
									align : 'center',
									formatter : function(value, row, index) {
						        		if(value && value.length > 20){
						        		value2 = value.substring(0,20) + "...";
										 return '<span title="' + value + '" style="cursor: pointer;" >' +value2+'</span>';
							        	}else{
											return value;
										}
									}
								},
								{
									field : 'moName',
									title : '管理对象',
									width : 180,
									align : 'center',
								},
								{
									field : 'kpiName',
									title : '指标名称',
									width : 150,
									align : 'center',
								},
								{
									field : 'upThreshold',
									title : '上限阈值',
									align : 'center',
									width : 100,
								},
								{
									field : 'upRecursiveThreshold',
									title : '上限回归阈值',
									align : 'center',
									width : 100,
								},
								{
									field : 'downThreshold',
									title : '下限阈值',
									align : 'center',
									width : 100,
									align : 'center'
								},
								{
									field : 'downRecursiveThreshold',
									title : '下限回归阈值',
									align : 'center',
									width : 100,
								},
								{
									field : 'descr',
									title : '单位',
									width : 150,
									align : 'center'
								},
								{
									field : 'alarmDefineIDs',
									title : '操作',
									width : 180,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:doOpenDetail('
											+ row.ruleID
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:doOpenModify('
											+ row.ruleID
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_modify.png" title="修改" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:toDel('
											+ row.ruleID
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
									}
								} ] ]
					});
	 $(window).resize(function() {
	        $('#tblthresholdList').resizeDataGrid(0, 0, 0, 0);
	    });
}

/*
 * 更新表格
 */
function reloadTable(){
	var className = $("#txtClassName").val();
	$('#tblthresholdList').datagrid('options').queryParams = {
		"className" : className
	};
	reloadTableCommon_1('tblthresholdList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/*
 * 打开查看页面
 */
function doOpenDetail(ruleID){
	parent.$('#popWin').window({
    	title:'阈值规则定义',
        width:600,
        height : 540,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmmgr/moKPIThreshold/toShowThresholdDetail?ruleID='+ruleID
    });
}

/*
 * 删除阈值规则定义
 */
function toDel(ruleID){
	$.messager.confirm("提示","确定删除此阈值规则定义?",function(r){
		if (r == true) {
			var path = getRootName();
			var uri = path + "/monitor/alarmmgr/moKPIThreshold/delThreshold";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"ruleID" : ruleID,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (true == data || "true" == data) {
						$.messager.alert("提示", "阈值规则定义删除成功！", "info");
						reloadTable();
					} else {
						$.messager.alert("提示", "阈值规则定义删除失败！", "error");
						reloadTable();
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
	var checkedItems = $('[name=ruleID]:checked');
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
				var uri = path + "/monitor/alarmmgr/moKPIThreshold/delThresholds?ruleIDs="+ids;
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
							$.messager.alert("提示", "阈值规则定义删除失败！", "error");
							reloadTable();
						} else {
							$.messager.alert("提示", "阈值规则定义删除成功！", "info");
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
	parent.$('#popWin').window({
    	title:'新增阈值定义规则',
        width:600,
        height : 500,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmmgr/moKPIThreshold/toShowThresholdAdd',
    });
}

/*
 * 打开编辑页面
 */
function doOpenModify(ruleID){
	parent.$('#popWin').window({
    	title:'编辑阈值规则定义',
        width:600,
        height : 500,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmmgr/moKPIThreshold/toShowThresholdModify?ruleID='+ruleID
    });
}

function checkBeforeCancle(){
	
//	console.log("查询名称是否有为空的");
	var path = getRootName();
	var uri = path + "/monitor/alarmEventDefine/checkBeforeCancle";
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
				toCancel(data.alarmDefineIDs);
			}
		}
	}
		ajax_(ajax_param);
}


/*
 * 关闭新增窗口的操作
 */
function toCancel(alarmDefineIDs){
		var path = getRootName();
		var uri = path + "/monitor/alarmEventDefine/toCancelAdd?alarmDefineIDs="+alarmDefineIDs;
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
			}
		}
			ajax_(ajax_param);
	
}