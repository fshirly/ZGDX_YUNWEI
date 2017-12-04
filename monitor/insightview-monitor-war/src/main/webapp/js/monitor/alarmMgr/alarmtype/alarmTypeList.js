$(document).ready(function() {
	doInitTable();

});

/**
 * 刷新表格数据
 */
function reloadTable(){
	var tpyeName = $("#textAlarmTypeName").val();
	$('#tblAlarmTypeList').datagrid('options').queryParams = {
		"alarmTypeName" : tpyeName
	};
	reloadTableCommon_1('tblAlarmTypeList');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/**
 * @return
 */
function doInitTable(){
	var path = getRootName();
	$('#tblAlarmTypeList')
		.datagrid(
				{
					iconCls : 'icon-edit', //图标
					height : 'auto',
					nowrap : false,
					striped : true,
					border : true,
					collapsible : false,// 是否可折叠的
					fit : true,// 自动大小
					fitColumns : true,
					url : path + '/monitor/alarmMgr/alarmtype/listAlarmType',
					
					idField : 'fldId',
					singleSelect : false,// 是否单选
					checkOnSelect : false,
					selectOnCheck : true,
					pagination : true,// 分页控件
					rownumbers : true,// 行号
					toolbar : [{
						'text' : '新增',
						'iconCls' : 'icon-add',
						handler : function(){
							doOpenAdd();
						}
					},
					{
						'text' : '删除',
						'iconCls' : 'icon-cancel',
						handler : function() {
							doBatchDel();
						}
					}],
					
					columns : [[
								{
									field : 'alarmTypeID',
									checkbox : true
								},{
									field : 'alarmTypeName',
									title : '类型名称',
									width : 100,
									align : 'center'
								},{
									field : 'alarmTypeIDs',
									title : '操作',
									width : 50,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" title="查看" onclick="javascript:toShowInfo('
												+ row.alarmTypeID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_view.png" alt="查看" /></a> &nbsp;'
												+ ' <a style="cursor: pointer;" title="修改" onclick="javascript:toOpenModify('
												+ row.alarmTypeID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_modify.png" alt="修改" /></a> &nbsp;'
												+ ' <a style="cursor: pointer;" title="删除" onclick="javascript:toDel('
												+ row.alarmTypeID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_delete.png" alt="删除" /></a>';
									}
								}]]
					});
		$(window).resize(function() {
			$('#tblAlarmTypeList').resizeDataGrid(0, 0, 0, 0);
		});
}

function toDel(alarmTypeID){
	$.messager.confirm("提示", "确定删除此信息?", function(r) {
		if (r == true) {
			checkIsUsed(alarmTypeID);
		}
	});
}

function checkIsUsed(alarmTypeID){
	var path = getRootName();
	var uri = path + "/monitor/alarmMgr/alarmtype/checkIsUsed";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"alarmTypeID" : alarmTypeID,
			"t" : Math.random()
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success : function(data){
			if(false == data || "false" == data){
				$.messager.alert("提示","该告警类型已经被引用,删除失败！","error");
			}else{
				doDel(alarmTypeID);
			}
		}
	};
	ajax_(ajax_param);
}

/**
 * 删除自定义告警类型
 * @param alarmTypeId
 * @return
 */
function doDel(alarmTypeID){
	var path = getRootName();
			var uri = path + "/monitor/alarmMgr/alarmtype/delAlarmType";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"alarmTypeID" : alarmTypeID,
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
						$.messager.alert("", "该告警类型为系统定义,删除失败！", "error");
					}
				}
			};
			ajax_(ajax_param);
}

/**
 * 新增类型页面
 * @return
 */
function doOpenAdd(){
		parent.$('#popWin').window({
    	title:'新增告警类型',
        width:600,
        height:200,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmMgr/alarmtype/toAlarmTypeAdd'
    });
}

function toOpenModify(alarmTypeID){
	//查看配置项页面
	parent.$('#popWin').window({
    	title:'编辑告警类型',
        width:600,
        height:200,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmMgr/alarmtype/toAlarmTypeModify?alarmTypeID='+alarmTypeID
    });
}

/**
 * 批量删除
 * 
 */
function doBatchDel(){
	var path=getRootName();
	var checkedItems = $('[name=alarmTypeID]:checked');
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
				var uri = path+ "/monitor/alarmMgr/alarmtype/doBatchDel?alarmTypeIDs="+ids;
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
						if (1== data.flag ) {
							$.messager.alert("错误", "被删告警类型为系统定义，不能删除", "error");
							reloadTable();
						}else if(2 == data.flag){
							$.messager.alert("错误", "被删告警类型已被引用，不能删除", "error");
							reloadTable();
						} else {
							$.messager.alert("提示", "删除成功！", "info");
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
 * 告警类型详情
 * @return
 */
function toShowInfo(id){
	 parent.$('#popWin').window({
    	title:'告警类型详情',
        width:600,
        height:300,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmMgr/alarmtype/toAlarmTypeDetail?alarmTypeID='+id
     });
	
}