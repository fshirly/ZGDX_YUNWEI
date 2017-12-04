$(document).ready(function() {
	doInitTable();

});

/**
 * 刷新表格数据
 */
function reloadTable(){
	var levelName = $("#textAlarmLevelName").val();
	$('#tblAlarmLevelList').datagrid('options').queryParams = {
		"alarmLevelName" : levelName
	};
	reloadTableCommon_1('tblAlarmLevelList');
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
	$('#tblAlarmLevelList')
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
					url : path + '/monitor/alarmMgr/alarmlevel/listAlarmLevel',
					
					idField : 'fldId',
					singleSelect : false,// 是否单选
					checkOnSelect : false,
					selectOnCheck : true,
					pagination : true,// 分页控件
					rownumbers : true,// 行号
//					toolbar : [{
//						'text' : '新增',
//						'iconCls' : 'icon-add',
//						handler : function(){
//							doOpenAdd();
//						}
//					},
//					{
//						'text' : '删除',
//						'iconCls' : 'icon-cancel',
//						handler : function() {
//							doBatchDel();
//						}
//					}],
					
					columns : [[
								{
									field : 'alarmLevelID',
									checkbox : true
								},{
									field : 'alarmLevelName',
									title : '等级名称',
									width : 100,
									align : 'center'
								},{
									field : 'levelColorName',
									title : '等级颜色',
									width : 100,
									align : 'center',
									formatter:function(value,row){ 
									var t = row.levelColor;
									return "<span style='background:"+t+";'>"+value+"</span>"; 
								} 
								},{
									field : 'alarmLevelIDs',
									title : '操作',
									width : 50,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" title="查看" onclick="javascript:toShowInfo('
												+ row.alarmLevelID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_view.png" alt="查看" /></a> &nbsp;'
//												+ ' <a style="cursor: pointer;" title="修改" onclick="javascript:toOpenModify('
//												+ row.alarmLevelID
//												+ ');"><img src="'
//												+ path
//												+ '/style/images/icon/icon_modify.png" alt="修改" /></a> &nbsp;'
//												+ ' <a style="cursor: pointer;" title="删除" onclick="javascript:toDel('
//												+ row.alarmLevelID
//												+ ');"><img src="'
//												+ path
//												+ '/style/images/icon/icon_delete.png" alt="删除" /></a>';
									}
								}]]
					});
		$(window).resize(function() {
			$('#tblAlarmLevelList').resizeDataGrid(0, 0, 0, 0);
		});
}

function toDel(alarmLevelID){
	$.messager.confirm("提示", "确定删除此信息?", function(r) {
		if (r == true) {
			checkIsUsed(alarmLevelID);
		}
	});
}

function checkIsUsed(alarmLevelID){
	var path = getRootName();
	var uri = path + "/monitor/alarmMgr/alarmlevel/checkIsUsed";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"alarmLevelID" : alarmLevelID,
			"t" : Math.random()
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success : function(data){
			if(false == data || "false" == data){
				$.messager.alert("提示","该告警等级已经被引用,删除失败！","error");
			}else{
				doDel(alarmLevelID);
			}
		}
	};
	ajax_(ajax_param);
}

/**
 * 删除自定义告警类型
 * @param alarmLevelID
 * @return
 */
function doDel(alarmLevelID){
	var path = getRootName();
			var uri = path + "/monitor/alarmMgr/alarmlevel/delAlarmLevel";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"alarmLevelID" : alarmLevelID,
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
						$.messager.alert("错误", "该告警等级为系统定义，删除失败！", "error");
					}
				}
			};
			ajax_(ajax_param);
}

function doBatchDel(){
	var path=getRootName();
	var checkedItems = $('[name=alarmLevelID]:checked');
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
				var uri = path+ "/monitor/alarmMgr/alarmlevel/doBatchDel?alarmLevelIDs="+ids;
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
						if (1 == data.flag ) {
							$.messager.alert("提示", "被删告警等级为系统定义类型，不能删除", "error");
							reloadTable();
						}else if(2 == data.flag){
							$.messager.alert("错误", "被删告警等级已被引用，不能删除", "error");
							reloadTable();
						}else {
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
 * 新增类型页面
 * @return
 */
function doOpenAdd(){
		parent.$('#popWin').window({
    	title:'新增告警等级',
        width:600,
        height:300,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmMgr/alarmlevel/toAlarmLevelAdd'
    });
}

function toOpenModify(alarmLevelID){
	//查看配置项页面
	parent.$('#popWin').window({
    	title:'编辑告警等级',
        width:600,
        height:200,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmMgr/alarmlevel/toAlarmLevelModify?alarmLevelID='+alarmLevelID
    });
}

/**
 * 告警类型详情
 * @return
 */
function toShowInfo(id){
	//获取id
	 parent.$('#popWin').window({
    	title:'告警等级详情',
        width:600,
        height:300,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmMgr/alarmlevel/toAlarmLevelDetail?alarmLevelID='+id
	 });
}