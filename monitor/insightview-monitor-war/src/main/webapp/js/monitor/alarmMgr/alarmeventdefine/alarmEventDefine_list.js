$(document).ready(function() {
	doInitTable();
});

/*
 * 页面加载初始化表格 @author 武林
 */
function doInitTable() {
	checkBeforeCancle();
	var path = getRootName();
	$('#tblAlarmEventDefine')
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
						url : path + '/monitor/alarmEventDefine/listAlarmEventDefine',
						remoteSort : false,
						idField : 'alarmDefineID',
						singleSelect : false,// 是否单选
						checkOnSelect : false,
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '新增',
							'iconCls' : 'icon-add',
							handler : function() {
								toAdd();
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
									 field : 'alarmDefineID',
									 checkbox : true
								},     
								{
									field : 'alarmName',
									title : '告警名称',
									width : 150,
								},
								{
									field : 'categoryName',
									title : '分类',
									width : 100,
								},
								{
									field : 'alarmOID',
									title : '标识',
									width : 180,
								},
								{
									field : 'alarmLevelName',
									title : '级别',
									width : 100,
									align : 'center'
								},
								{
									field : 'alarmDefineIDs',
									title : '操作',
									width : 200,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:checkForOpenDetail('
											+ row.alarmDefineID
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_view.png" title="查看" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:checkForOpenUpdate('
											+ row.alarmDefineID
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_modify.png" title="修改" /></a> &nbsp;<a style="cursor: pointer;" onclick="javascript:toDel('
											+ row.alarmDefineID
											+ ');"><img src="'
											+ path
											+ '/style/images/icon/icon_delete.png" title="删除" /></a>';
									}
								} ] ]
					});
	 $(window).resize(function() {
	        $('#tblAlarmEventDefine').resizeDataGrid(0, 0, 0, 0);
	    });
}

/*
 * 更新表格
 */
function reloadTable(){
	checkBeforeCancle();
	var alarmName = $("#txtAlarmName").val();
	$('#tblAlarmEventDefine').datagrid('options').queryParams = {
		"alarmName" : alarmName
	};
	
	reloadTableCommon_1('tblAlarmEventDefine');
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/*
 * 打开详情页面前的验证
 */
function checkForOpenDetail(alarmDefineID){
//	alert(alarmDefineID);
	var path = getRootName();
	var uri = path + "/monitor/alarmEventDefine/checkForOpenDetail";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"alarmDefineID" : alarmDefineID,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
//			alert( "index======="+data.index+",alarmDefineID========="+data.alarmDefineID+",recoverAlarmDefineID======="+data.recoverAlarmDefineID);
			doOpenDetail(data.alarmDefineID,data.recoverAlarmDefineID,data.index)
		}
	};
	ajax_(ajax_param);
}

/*
 * 打开查看页面
 */
function doOpenDetail(alarmDefineID,recoverAlarmDefineID,index){
	parent.$('#popWin').window({
    	title:'告警定义',
        width:800,
        top:70,
//        height:450,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmEventDefine/toShowAlarmEventDefineDetail?alarmDefineID='+alarmDefineID+'&recoverAlarmDefineID='+recoverAlarmDefineID+'&index='+index
    });
}

function toDel(alarmDefineID){
	$.messager.confirm("提示","确定删除此告警事件?",function(r){
		if (r == true) {
			checkIsSystem(alarmDefineID);
		}
	});
}

/*
 * 删除前的验证是否为系统自定义事件
 */
function checkIsSystem(alarmDefineID){
	var path = getRootName();
	var uri = path + "/monitor/alarmEventDefine/checkIsSystem";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"alarmDefineID" : alarmDefineID,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (false == data || "false" == data) {
				$.messager.alert("提示", "该告警事件为系统定义事件，不能删除！", "error");
			} else {
				checkBeforeDel(alarmDefineID);
			}
		}
	};
	ajax_(ajax_param);
}

/*
 * 删除前验证是否被使用
 * 
 */
function checkBeforeDel(alarmDefineID){
	var path = getRootName();
	var uri = path + "/monitor/alarmEventDefine/checkBeforeDel";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"alarmDefineID" : alarmDefineID,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (false == data || "false" == data) {
				$.messager.alert("提示", "该告警事件正在被使用，删除失败！", "error");
			} else {
				doDel(alarmDefineID);
			}
		}
	};
	ajax_(ajax_param);
}

/*
 * 删除告警事件
 */
function doDel(alarmDefineID){
	var path = getRootName();
	var uri = path + "/monitor/alarmEventDefine/delAlarmDefine";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"alarmDefineID" : alarmDefineID,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				$.messager.alert("提示", "告警事件删除成功！", "info");
				reloadTable();
			} else {
				$.messager.alert("提示", "告警事件删除失败！", "error");
				reloadTable();
			}
		}
	};
	ajax_(ajax_param);
}


/*
 * 批量删除
 */
function doBatchDel(){
	var path=getRootName();
	var checkedItems = $('[name=alarmDefineID]:checked');
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
				var uri = path + "/monitor/alarmEventDefine/delAlarmDefines?alarmDefineIDs="+ids;
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
							if (true == data.mark || "true" == data.mark) {
								if (false == data.isSystem || "false" == data.isSystem){
									$.messager.alert("提示", "被删告警事件"+data.defineName+"为系统定义事件，不能删除！", "error");
								}
								if (false == data.isUse || "false" == data.isUse){
									$.messager.alert("提示", "被删告警事件"+data.useName+"正在为使用，不能删除！", "error");
								}
							}else{
								$.messager.alert("提示", "告警事件删除失败！", "info");
							}
							reloadTable();
						} else {
							$.messager.alert("提示", "告警事件删除成功！", "info");
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

function toAdd(){
//	getAlarmDefineID();
	doOpenAdd();
}

function getAlarmDefineID(){
	var path = getRootName();
	var uri = path + "/monitor/alarmEventDefine/getAlarmDefineID";
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
			if(data.flag == true){
				doOpenAdd(data.alarmDefineID);
			}
		}
	};
	ajax_(ajax_param);
}
/*
 * 打开新增页面
 */
function doOpenAdd(){
	parent.$('#popWin').window({
    	title:'新增告警事件',
        width:800,
        height:530,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmEventDefine/toShowAlarm',
//        onBeforeClose: function () { //当面板关闭之前触发的事件
//			checkBeforeCancle();
//		}
    });
}

function toOpenModify(alarmDefineID){
	checkForOpenModify(alarmDefineID)
}
function checkForOpenModify(alarmDefineID){
	var path = getRootName();
	var uri = path + "/monitor/alarmEventDefine/checkForOpenDetail";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"alarmDefineID" : alarmDefineID,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			doOpenModify(data.alarmDefineID,data.recoverAlarmDefineID,data.index)
		}
	};
	ajax_(ajax_param);
}

/*
 * 打开详情页面前的验证
 */
function checkForOpenUpdate(alarmDefineID){
	var path = getRootName();
	var uri = path + "/monitor/alarmEventDefine/checkForOpenDetail";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"alarmDefineID" : alarmDefineID,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
//			alert( "编辑：index======="+data.index+",alarmDefineID========="+data.alarmDefineID+",recoverAlarmDefineID======="+data.recoverAlarmDefineID);
			doOpenModify(data.alarmDefineID,data.recoverAlarmDefineID,data.index)
		}
	};
	ajax_(ajax_param);
}

/*
 * 打开编辑页面
 */
function doOpenModify(alarmDefineID,recoverAlarmDefineID,index){
	parent.$('#popWin').window({
    	title:'编辑告警事件',
        width:800,
        height:530,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        modal:true,
        href: getRootName() + '/monitor/alarmEventDefine/toShowAlarmEventDefineModify?alarmDefineID='+alarmDefineID+'&recoverAlarmDefineID='+recoverAlarmDefineID+'&index='+index
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
				reloadTable();
			}
		}
			ajax_(ajax_param);
	
}