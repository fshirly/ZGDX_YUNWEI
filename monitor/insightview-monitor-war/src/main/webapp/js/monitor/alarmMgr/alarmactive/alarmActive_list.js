$(document).ready(function() {
	var filtername = $("#filtername").val();
	var filterId = $("#filterId").val();
	// console.log("init======== filtername = "+filtername+", filterId
	// ==="+filterId);
	if (filtername == "level") {
		$("#alarmLevel").combobox('setValue', filterId);
	} else if (filtername == "type") {
		$("#alarmType").combobox('setValue', filterId);
	}
	doInitTable();
});

/**
 * 加载页面并初始化表格
 * 
 * @return
 */
function doInitTable() {
	var path = getRootName();
	var host = $("#host").val();
	var tzType = $("#tzType").val();
	var tzResType = $("#tzResType").val();
	var tzDeviceIP = $("#tzDeviceIP").val();
	var tzTimeBegin = $("#tzTimeBegin").val();
	var tzTimeEnd = $("#tzTimeEnd").val();
	var moclassID = $("#moclassID").val();
	if (moclassID == "59,60") {
		moclassID = 6;
	}
	var uri = path + "/monitor/alarmActive/listAlarmActive";
	if (moclassID != null && moclassID != "") {
		uri += "?moclassID=" + moclassID;
	}
	if (tzType == "tz") {
		uri += "?sourceMOIPAddress=" + tzDeviceIP + "&timeBegin=" + tzTimeBegin
				+ "&timeEnd=" + tzTimeEnd;
	}
	var neManufacturerID = $("#neManufacturerID").val();
	if (neManufacturerID != null && neManufacturerID != "") {
		uri += "?neManufacturerID=" + neManufacturerID;
	}
	if ((neManufacturerID != null && neManufacturerID != "")
			&& (host === "snapsHost") && (moclassID != null && moclassID != "")) {
		uri = path + "/monitor/alarmActive/queryAlarmActivelist?moclassID="
				+ moclassID + "&neManufacturerID=" + neManufacturerID;
	}
	if ((host === "snapshot" || host === "allSnapshost")
			&& (moclassID != null && moclassID != "")) {
		uri = path + "/monitor/alarmActive/queryAlarmActivelist?moclassID="
				+ moclassID;
	}
	if ((tzResType === "TZallSnapshost")
			&& (moclassID != null && moclassID != "")) {
		uri = path + "/monitor/tzManage/queryTZAlarmActivelist?moclassID="
				+ moclassID;
	}
	var moid = $("#moid").val();
	var alarmLevel = $("#alarmLevel").combobox("getValue");
	var alarmType = $("#alarmType").combobox("getValue");
	// console.log("alarmLevel==="+alarmLevel+" ,alarmType==="+alarmType);
	$('#tblDataList')
			.datagrid(
					{
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fit : true,// 自动大小
						fitColumns : true,
						url : uri,
						remoteSort : true,
						onSortColumn : function(sort, order) {
							// alert("sort:"+sort+",order："+order+"");
						},
						idField : '',
						singleSelect : true,// 是否单选
						checkOnSelect : false,
						selectOnCheck : false,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						queryParams : {
							"alarmLevel" : alarmLevel == '' ? 0 : alarmLevel,
							"alarmType" : alarmType == '' ? 0 : alarmType,
							"moid" : moid
						},
						onDblClickRow:function(rowIndex, rowData){
							toView(rowData.alarmID);
						},
						onLoadSuccess : function() {
						},
						toolbar : [ {
							'text' : '确认',
							'iconCls' : 'icon-ok',
							handler : function() {
								toBathConfirm();
							}
						}, /*
						 * { 'text' : '取消确认', 'iconCls' : 'icon-add',
						 * handler : function() { toBathCalcel(); } },
						 */
						{
							'text' : '清除',
							'iconCls' : 'icon-broom',
							handler : function() {
								toBathClear();
							}
						}/*, 
						 * { 'text' : '删除', 'iconCls' : 'icon-cancel',
						 * handler : function() { toBathDel(); } },'-',
						 */
						/*{
							'text' : '派单',
							'iconCls' : 'icon-hand',
							handler : function() {
								alarmSendSingle.toBathSendSingle();
							}
						} */],
						columns : [ [
								{
									field : 'alarmID',
									checkbox : true
								},
								{
									field : 'alarmIDs',
									title : '告警序号',
									hidden:true,
									width : 40,
									align : 'left',
									formatter : function(value, row) {
										return '<a style="cursor: pointer;" title="查看" onclick="javascript:toView('
												+ row.alarmID
												+ ');">'
												+ row.alarmID + '</a>';
									}
								},
								{
									field : 'operateStatusName',
									title : '告警状态',
									width : 70,
									align : 'left'
								},
								{
									field : 'alarmOperateStatus',
									title : '状态',
									hidden : true,// 隐藏
									width : .80
								},
								{
									field : 'alarmLevelName',
									title : '告警级别',
									width : 40,
									align : 'center',
									formatter : function(value, row) {
										var t = row.levelColor;
										return "<div style='background:" + t
												+ ";'>" + value + "</div>";
									}
								},
								{
									field : 'alarmTitle',
									title : '告警标题',
									width : 80,
									align : 'center'
								},
								{
									field : 'sourceMOName',
									title : '告警源名称',
									width : 100,
									align : 'center'
								},
								{
									field : 'moName',
									title : '告警名称',
									width : 100,
									align : 'center'
								},
								{
									field : 'startTime',
									title : '首次发生时间',
									width : 80,
									align : 'center',
									formatter : function(value, row, index) {
										if (value != null && value != "") {
											return formatDate(new Date(value),
													"yyyy-MM-dd hh:mm:ss");
										} else {
											return "";
										}
									}
								},
								{
									field : 'alarmTypeName',
									title : '告警类型',
									width : 60,
									align : 'center'
								},
								{
									field : 'repeatCount',
									title : '重复次数',
									width : 35,
									align : 'right'
								},
								{
									field : 'lastTime',
									title : '最近发生时间',
									width : 80,
									align : 'center',
									formatter : function(value, row, index) {
										if (value != null && value != "") {
											return formatDate(new Date(value),
													"yyyy-MM-dd hh:mm:ss");
										} else {
											return "";
										}
									}
								},
								{
									field : 'sourceMOIPAddress',
									title : '告警源IP',
									width : 80,
									align : 'center'
								}/*,
								{
									field : 'dispatchUser',
									title : '派单人',
									width : 50,
									align : 'center'
								},
								{
									field : 'dispatchTime',
									title : '派单时间',
									width : 80,
									align : 'center',
									formatter : function(value, row, index) {
										if (value != null && value != "") {
											return formatDate(new Date(value),
													"yyyy-MM-dd hh:mm:ss");
										} else {
											return "";
										}
									}
								}*/ /*
						 * ,{ field : 'ids', title : '操作', width :
						 * 55, align : 'center', formatter :
						 * function(value, row, index) { var
						 * isConfirm=""; var isClearDel = "";
						 * if(row.alarmStatus==1){ isConfirm='<a
						 * style="cursor: pointer;" title="确认"
						 * onclick="javascript:toConfirm(' +
						 * row.alarmID + ');"><img src="' + path +
						 * '/style/images/icon/icon_lock.png"
						 * alt="确认" /></a>'; } else
						 * if(row.alarmStatus==2){ isConfirm='<a
						 * style="cursor: pointer;" title="取消确认"
						 * onclick="javascript:toCalcel(' +
						 * row.alarmID + ');"><img src="' + path +
						 * '/style/images/icon/icon_unlock.png"
						 * alt="取消确认" /></a>'; }
						 * if(row.alarmStatus!=1){ isClearDel
						 * ='&nbsp;<a style="cursor: pointer;"
						 * title="清除" onclick="javascript:toClear(' +
						 * row.alarmID + ');"><img src="' + path +
						 * '/style/images/icon/icon_password.png"
						 * alt="清除" /></a>&nbsp;' + ' <a
						 * style="cursor: pointer;" title="删除"
						 * onclick="javascript:toDel(' + row.alarmID +
						 * ');"><img src="' + path +
						 * '/style/images/icon/icon_delete.png"
						 * alt="删除" /></a>'; } return '<a
						 * style="cursor: pointer;" title="查看"
						 * onclick="javascript:toView(' +
						 * row.alarmID + ');"><img src="' + path +
						 * '/style/images/icon/icon_view.png"
						 * alt="查看" /></a>&nbsp;'+isConfirm+isClearDel; } }
						 */
						] ]
					});
	$(window).resize(function() {
		$('#tblDataList').resizeDataGrid(0, 0, 0, 0);
	});
}

/**
 * 刷新表格数据
 */
function reloadTable() {
	var timeBegin = $('#timeBegin').datetimebox('getValue');
	var timeEnd = $('#timeEnd').datetimebox('getValue');

	var start = new Date(timeBegin.replace("-", "/").replace("-", "/"));
	var end = new Date(timeEnd.replace("-", "/").replace("-", "/"));
	if (end < start) {
		$.messager.alert('提示', '结束时间不得早于开始有效时间', 'error');
	} else {
		var moName = $("#moName").val();
		var alarmLevel = $("#alarmLevel").combobox("getValue");
		var alarmTitle = $("#alarmTitle").val();
		var sourceMOIPAddress = $("#sourceMOIPAddress").val();
		var alarmOperateStatus = $("#alarmOperateStatus").combobox("getValue");
		var alarmType = $("#alarmType").combobox("getValue");
		var moclassID = $("#moclassID").val();
		var dispatchUser = $("#dispatchUser").val();
		var dispatchTime = $("#dispatchTime").val();

		var moid = $("#moid").val();

		if (moclassID == "59,60") {
			moclassID = 6;
		}
		$('#tblDataList').datagrid('options').queryParams = {
			"alarmLevel" : alarmLevel == '' ? 0 : alarmLevel,
			"alarmOperateStatus" : alarmOperateStatus == '' ? 0
					: alarmOperateStatus,
			"alarmType" : alarmType == '' ? 0 : alarmType,
			"timeBegin" : timeBegin,
			"timeEnd" : timeEnd,
			"moName" : moName,
			"alarmTitle" : alarmTitle,
			"sourceMOIPAddress" : sourceMOIPAddress,
			"moclassID" : moclassID == '' ? 0 : moclassID,
			"dispatchUser" : dispatchUser,
			"dispatchTime" : dispatchTime,
			"moid" : moid
		};
		reloadTableCommon_1('tblDataList');
	}
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/**
 * 打开批量告警确认页面
 * 
 * @return
 */
function toBathConfirm() {
	var ids = null;
	var rows = $('#tblDataList').datagrid('getChecked');
	var flag = null;
	for (var i = 0; i < rows.length; i++) {
		if (rows[i].alarmOperateStatus != 21) {
			flag = "1";
		}
		if (null == ids) {
			ids = rows[i].alarmID;
		} else {
			ids += ',' + rows[i].alarmID;
		}
	}
	if (null != ids) {
		if (null == flag) {
			parent.parent
					.$('#popWin')
					.window(
							{
								title : '告警确认',
								width : 650,
								height : 450,
								minimizable : false,
								maximizable : false,
								collapsible : false,
								modal : true,
								href : getRootName()
										+ '/monitor/alarmActive/toBathAlarmActiveConfirm?id='
										+ ids
							});
		} else {
			$.messager.alert('提示', '选择的告警记录中,状态不是未确认,请重新选择!', 'info');
		}
	} else {
		$.messager.alert('提示', '没有任何选中项', 'info');
	}
}

/**
 * 取消确认告警
 */
function toBathCalcel() {
	var ids = null;
	var rows = $('#tblDataList').datagrid('getChecked');
	var flag = null;
	for (var i = 0; i < rows.length; i++) {
		if (rows[i].alarmOperateStatus != 22) {
			flag = "1";
		}
		if (null == ids) {
			ids = rows[i].alarmID;
		} else {
			ids += ',' + rows[i].alarmID;
		}
	}
	if (null != ids) {
		if (null == flag) {
			$.messager
					.confirm(
							"提示",
							"确定要取消确认告警？",
							function(r) {
								if (r == true) {
									var uri = getRootName()
											+ "/monitor/alarmActive/bathCancelAlarmActiveConfirm";
									var ajax_param = {
										url : uri,
										type : "post",
										datdType : "json",
										data : {
											"id" : ids
										},
										success : function(data) {
											if (true == data || "true" == data) {
												$.messager.alert("提示",
														"取消确认告警成功！", "info");
												reloadTable();
											} else {
												$.messager.alert("提示",
														"取消确认告警失败！", "error");
											}
										}
									};
									ajax_(ajax_param);
								}
							});
		} else {
			$.messager.alert('提示', '当前状态无法批量取消确认告警', 'error');
		}
	} else {
		$.messager.alert('提示', '没有任何选中项', 'error');
	}
}

/**
 * 打开批量告警清除页面
 * 
 * @return
 */
function toBathClear() {
	var ids = null;
	var rows = $('#tblDataList').datagrid('getChecked');
	var flag = null;
	for (var i = 0; i < rows.length; i++) {
		if (null == ids) {
			ids = rows[i].alarmID;
		} else {
			ids += ',' + rows[i].alarmID;
		}
	}
	// 为了与实时告警功能统一，暂时将（未确认不可以清除）的功能关闭
	if (null != ids) {
		// if (null == flag){
		parent.parent.$('#popWin').window(
				{
					title : '告警清除',
					width : 650,
					height : 450,
					minimizable : false,
					maximizable : false,
					collapsible : false,
					modal : true,
					href : getRootName()
							+ '/monitor/alarmActive/toBathClearAlarmActive?id='
							+ ids
				});
		// }else{
		// $.messager.alert('提示', '当前状态无法批量清除告警', 'error');
		// }
	} else {
		$.messager.alert('提示', '没有任何选中项', 'error');
	}
}

/**
 * 批量删除告警
 * 
 * @return
 */
function toBathDel() {
	var ids = null;
	var rows = $('#tblDataList').datagrid('getChecked');
	var flag = null;
	for (var i = 0; i < rows.length; i++) {
		if (rows[i].alarmOperateStatus == 21) {
			flag = "1";
		}
		if (null == ids) {
			ids = rows[i].alarmID;
		} else {
			ids += ',' + rows[i].alarmID;
		}
	}
	// 为了与实时告警功能统一，暂时将（未确认不可以删除）的功能关闭
	if (null != ids) {
		// if (null == flag){
		$.messager.confirm("提示", "确定要删除告警？", function(r) {
			if (r == true) {
				var uri = getRootName()
						+ "/monitor/alarmActive/bathDeleteAlarmActive";
				var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"id" : ids
					},
					success : function(data) {
						if (true == data || "true" == data) {
							$.messager.alert("提示", "告警删除成功！", "info");
							reloadTable();
						} else {
							$.messager.alert("提示", "告警删除失败！", "error");
						}
					}
				};
				ajax_(ajax_param);
			}
		});
		// }else{
		// $.messager.alert('提示', '当前状态无法批量删除告警', 'error');
		// }
	} else {
		$.messager.alert('提示', '没有任何选中项', 'error');
	}
}

/**
 * 打开告警详情页面
 * 
 * @return
 */

function toView(id) {
	parent.parent.$('#popWin').window(
			{
				title : '告警详情',
				width : 800,
				height : 600,
				minimizable : false,
				maximizable : false,
				collapsible : false,
				modal : true,
				href : getRootName()
						+ '/monitor/alarmActive/toAlarmActiveDetail?alarmID='
						+ id
			});
}

function toConfirm(id) {
	parent.$('#popWin').window(
			{
				title : '告警确认',
				width : 650,
				height : 450,
				minimizable : false,
				maximizable : false,
				collapsible : false,
				modal : true,
				href : getRootName()
						+ '/monitor/alarmActive/toAlarmActiveConfirm?alarmID='
						+ id
			});
}

function toCalcel(id) {
	$.messager.confirm("提示", "确定要取消确认告警？", function(r) {
		if (r == true) {
			var uri = getRootName()
					+ "/monitor/alarmActive/cancelAlarmActiveConfirm";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"alarmID" : id
				},
				success : function(data) {
					if (true == data || "true" == data) {
						$.messager.alert("提示", "取消确认告警成功！", "info");
						reloadTable();
					} else {
						$.messager.alert("提示", "取消确认告警失败！", "error");
					}
				}
			};
			ajax_(ajax_param);
		}
	});
}

function toClear(id) {
	parent.$('#popWin').window(
			{
				title : '告警清除',
				width : 650,
				height : 450,
				minimizable : false,
				maximizable : false,
				collapsible : false,
				modal : true,
				href : getRootName()
						+ '/monitor/alarmActive/toClearAlarmActive?alarmID='
						+ id
			});
}

function toDel(id) {
	$.messager.confirm("提示", "确定要删除该告警？", function(r) {
		if (r == true) {
			var uri = getRootName() + "/monitor/alarmActive/deleteAlarmActive";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"alarmID" : id
				},
				success : function(data) {
					if (true == data || "true" == data) {
						$.messager.alert("提示", "该告警删除成功！", "info");
						reloadTable();
					} else {
						$.messager.alert("提示", "该告警删除失败！", "error");
					}
				}
			};
			ajax_(ajax_param);
		}
	});
}

function resetFilterForm(divFilter) {
	resetForm(divFilter);
	$("#alarmOperateStatus").combobox("setValue", "");
	$("#alarmLevel").combobox("setValue", "");
	$("#alarmType").combobox("setValue", "");
}

// 告警派发虚拟请求
// TODO 开发测试，实际环境删除
$(function() {
	// TODO 接口调试
	return;
	var root = getRootName();
	$.mockjax({
		url : root + "/monitor/AlarmSendSingle/ajaxFindTreeDepartments.do",
		response : function(settings) {
			this.responseText = [ {
				"id" : 1,
				"text" : "部门",
				"iconCls" : "icon-save",
				"children" : [ {
					"text" : "产品一部",
				}, {
					"text" : "产品二部",
				} ]
			} ];
		}
	});
	$.mockjax({
		url : root + "/monitor/AlarmSendSingle/ajaxFindAllGroups.do",
		response : function(settings) {
			this.responseText = [ {
				"id" : 1,
				"text" : "管理员",
				"iconCls" : "icon-save",
				"children" : [ {
					"text" : "超级管理员",
				}, {
					"text" : "一般管理员",
				} ]
			} ];
		}
	});
	$.mockjax({
		url : root + "/monitor/AlarmSendSingle/ajaxFindStaffInfo.do",
		response : function(settings) {
			this.responseText = [ {
				"id" : 1,
				"text" : "黄振骁"
			} ];
		}
	});
	$.mockjax({
		url : root + "/monitor/AlarmSendSingle/ajaxFindStaffInfo2.do",
		response : function(settings) {
			this.responseText = [ {
				"id" : 1,
				"text" : "哦呵呵"
			} ];
		}
	});

});
// 告警派发工具
// huangzx
// 2015-05-19
util = {};
util.serializeForm = function(form, enablenull) {
	var o = {};
	$ && $.each($(form).serializeArray(), function(index) {
		if (!this['value']) {
			return;
		}
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"H+" : this.getHours(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S" : this.getMilliseconds()
	};
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	for ( var i in o) {
		var reg = new RegExp("(" + i + ")");
		if (reg.test(format)) {
			format = format.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[i]).substr(("" + o[i]).length)));
		}
	}
	return format;
}
// 告警派发功能对象
alarmSendSingle = {};
alarmSendSingle.selectPerson = function() {
	var names = [];
	var ids = [];
	var checked = $('#departmentMan :checkbox:checked');
	$.each(checked, function(k, v) {
		ids.push($(this).val());
		names.push($(this).attr("name"));
	});
	$("#selectPeopleId").val(ids.join());
	$("#selectPeopleName").val(names.join());
}
//告警匹配（告警派发之前匹配是否已经由省厅派发）
function machingAlarm(ids){
	    var uri = getRootName() + "/monitor/AlarmSendSingle/alarmMaching";
	    var isalReadySend = 0;
	    $.ajax({
	        url: uri,
	        type: "post",
	        datdType: "json",
	        async : false,
	        data: {
	            "ids": ids
	        },
	        success: function(msg){
	           if(msg == "pass"){
	        	   isalReadySend = 0;
	           }else{
	        	   $.messager.alert("提示", "选择的告警记录中，有省厅已派发的告警，请重新选择！","info");
	        	   isalReadySend = 1;
	        	   $('#tblDataList').datagrid('reload');
	           }
	        },
	        error:function(){
	        	$.messager.alert("提示", "未知错误！","error");
	        }
	    });
	    /*地市告警匹配end*/
	    return isalReadySend;
}

alarmSendSingle.toBathSendSingle = function() {
	var ids = null;
	var rows = $('#tblDataList').datagrid('getChecked');
	var alarmOrderVersion = $('#alarmOrderVersion').val();
	/*告警派单二期匹配*/
	for (var i = 0; i < rows.length; i++) {
        if (null == ids) {
            ids = rows[i].alarmID;
        } else {
            ids += ',' + rows[i].alarmID;
        }
    }

	/*告警派单二期匹配end*/
	if (0 == rows.length) {
			$.messager.alert("提示", "请选择需要派单的告警！", "info");
		return;
	} else if (1 < rows.length && '3' === alarmOrderVersion) {
		$.messager.alert("提示", "请单条告警派发！", "info");
		return;
	}
	var alarmIds = $.map(rows, function(obj){
		return obj.alarmID;
	});
	var contiu = true;
	$.ajax({
		url: getRootName() + '/monitor/alarmSendOrderUnion/queryAlarmStatusById',
		dataType: 'json',
		async : false,
		type: 'post',
		data: {
			"alarmIds" : alarmIds.join(",")
		},
		success: function(data){
			if(!data){
				$.messager.alert("提示","您选择的单子存在重复派单", "info");
				contiu = false;
			}
		},
		error: function(){
			contiu = false;
			$.messager.alert("错误", "ajax_error", "error");
		}
	});
	
	if (!contiu) {
		return;
	}
	var flag = false;
	var isSend = [];
	var rows2 = $.map(rows, function(v) {
		if (v.dispatchID) {
			flag = true;
		}
		return v;
	});
	if (flag) {
		$.messager.alert("提示", "选择的告警记录中，存在重复派单，请重新选择！");
		return;
	}
	if (rows2.length > 10) {
		$.messager.alert("提示", "最多可支持10条告警同时派发！");
		return;
	}
	//如果匹配到告警为省厅已经派发，则提示退出
    if(machingAlarm(ids) == 1){
    	return;
    }
	//新版本告警派单入口
	if(2 == alarmOrderVersion){
	$("#popWin").window({
		title: "登记并派单",
		width: 800,
		height: 530, 
		minimizable : false,
		maximizable : false,
		collapsible : false,
		modal : true,
		href: getRootName() + "/monitor/alarmSendOrderUnion/toNewAlarmSendOrderUnion?alarmIds="+alarmIds.join(",")
		
	});
	}
	//老版本告警派单入口
	else if(1 == alarmOrderVersion){
		$("<div id='alarm_Old_Version'/>").window({
			title: "登记并派单",
			width: 800,
			height: 530, 
			minimizable : false,
			maximizable : false,
			collapsible : false,
			modal : true,
			onClose : function () {
				$(this).window('destroy');
			},
			href: getRootName() + "/monitor/alarmSendOrderUnion/toOldAlarmSendOrderUnion?alarmIds="+alarmIds.join(",")
	}
    );
	}
	else if(3 == alarmOrderVersion){
		parent.parent.$("#popWin4").window({
			title: "登记并派单",
			width: 800,
			height: 530,
			minimizable: false,
			maximizable: false,
			collapsible: false,
			modal: true,
			href: getRootName() + '/monitor/alarmSendOrderUnion/toThirdVersionSendOrderUnion?alarmIds='+alarmIds.join(",")
		}
		);
	}

}