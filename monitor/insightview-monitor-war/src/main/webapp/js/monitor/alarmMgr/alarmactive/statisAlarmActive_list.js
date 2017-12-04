$(document).ready(function() {
	var filtername = $("#filtername").val();
	var filterId = $("#filterId").val();
	// console.log("init======== filtername = "+filtername+", filterId
	// ==="+filterId);
	if (filtername == "level") {
		// console.log(">>>>");
		$("#alarmLevel").combobox('setValue', filterId);
	} else if (filtername == "type") {
		$("#alarmType").combobox('setValue', filterId);
	}
	var alarmStatus = $("#alarmStatus1").val();
	// console.log("alarmStatus=="+alarmStatus);
	if (alarmStatus == 0) {
		alarmStatus = "";
	}
	$("#alarmOperateStatus").combobox('setValue', alarmStatus);

	var timeBegin = $("#timeBegin1").val();
	// console.log("timeBegin=="+timeBegin);
	var timeEnd = $("#timeEnd1").val();
	// console.log("timeEnd=="+timeEnd);
	$('#timeBegin').datetimebox('setValue', timeBegin);
	$('#timeEnd').datetimebox('setValue', timeEnd);
	doInitTable();
});

/**
 * 加载页面并初始化表格
 * 
 * @return
 */
function doInitTable() {
	var path = getRootName();
	var ciId = $("#ciId").val();
	var proUrl = $("#proUrl").val();
	var alarmLevel = $("#alarmLevel").combobox("getValue");
	var alarmStatus = $("#alarmOperateStatus").combobox("getValue");
	var alarmType = $("#alarmType").combobox("getValue");
	var timeBegin = $('#timeBegin').datetimebox('getValue');
	var timeEnd = $('#timeEnd').datetimebox('getValue');
	var uri = path + proUrl + "?ciId=" + ciId;
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
						queryParams : {
							"alarmLevel" : alarmLevel == '' ? 0 : alarmLevel,
							"alarmStatus" : alarmStatus == '' ? 0 : alarmStatus,
							"alarmType" : alarmType == '' ? 0 : alarmType,
							"timeBegin" : timeBegin,
							"timeEnd" : timeEnd,
						},
						remoteSort : true,
						onSortColumn : function(sort, order) {
							// alert("sort:"+sort+",order："+order+"");
						},
						onDblClickRow:function(rowIndex, rowData){
							toView(rowData.alarmID);
						},	
						idField : '',
						singleSelect : false,// 是否单选
						// checkOnSelect : false,
						// selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '确认',
							'iconCls' : 'icon-ok',
							handler : function() {
								alarmtoBathConfirm();
							}
						},
						/*
						 * { 'text' : '取消确认', 'iconCls' : 'icon-add', handler :
						 * function() { toBathCalcel(); } },
						 */{
							'text' : '清除',
							'iconCls' : 'icon-broom',
							handler : function() {
								alarmtoBathClear();
							}
						}, {
							'text' : '派单',
							'iconCls' : 'icon-hand',
							handler : function() {
								alarmSendSingletoCount.toBathSendSingle();
							}
						} ],
						columns : [ [
								{
									field : 'alarmID',
									checkbox : true
								},
								{
									field : 'alarmIDs',
									title : '告警序号',
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
								},
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
								} ] ]
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
		var alarmStatus = $("#alarmOperateStatus").combobox("getValue");
		// console.log("reload alarmStatus=="+alarmStatus);
		var alarmType = $("#alarmType").combobox("getValue");
		var ciId = $("#ciId").val();
		if (ciId == null || ciId == "") {
			ciId = -1;
		}
		$('#tblDataList').datagrid('options').queryParams = {
			"alarmLevel" : alarmLevel == '' ? 0 : alarmLevel,
			"alarmStatus" : alarmStatus == '' ? 0 : alarmStatus,
			"alarmType" : alarmType == '' ? 0 : alarmType,
			"timeBegin" : timeBegin,
			"timeEnd" : timeEnd,
			"moName" : moName,
			"alarmTitle" : alarmTitle,
			"ciId" : ciId
		};
		reloadTableCommon('tblDataList');
	}
}

function resetFilterForm(divFilter) {
	resetForm(divFilter);
	$("#alarmOperateStatus").combobox("setValue", "");
	$("#alarmLevel").combobox("setValue", "");
	$("#alarmType").combobox("setValue", "");
}

/**
 * 打开批量告警确认页面
 * 
 * @return
 */
function alarmtoBathConfirm() {
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
								height : 400,
								minimizable : false,
								maximizable : false,
								collapsible : false,
								modal : true,
								href : getRootName()
										+ '/monitor/alarmActive/toBathAlarmActiveConfirm?id='
										+ ids + '&flag=statis'
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
function alarmtoBathClear() {
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
					height : 400,
					minimizable : false,
					maximizable : false,
					collapsible : false,
					modal : true,
					href : getRootName()
							+ '/monitor/alarmActive/toBathClearAlarmActive?id='
							+ ids + '&flag=statis'
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
				height : 550,
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
				height : 400,
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
				height : 400,
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
alarmSendSingletoCount = {};
alarmSendSingletoCount.selectPerson = function() {
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
alarmSendSingletoCount.toBathSendSingle = function() {
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
	/*告警派单匹配*/
	if (0 == rows.length) {
		$.messager.alert("警告", "请选择需要派单的告警！");
		return;
	} else if (1 < rows.length && '3' === $('#alarmOrderVersion').val()) {
		$.messager.alert("提示", "请单条告警派发！", "info");
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
	};
	if (rows2.length > 10) {
		$.messager.alert("提示", "最多可支持10条告警同时派发！");
		return;
	};
	var alarmIds = $.map(rows, function(obj){
		return obj.alarmID;
	});
	//如果匹配到告警为省厅已经派发，则提示退出
    if(machingAlarm(ids) == 1){
    	return;
    }
	if(1==$('#alarmOrderVersion').val()) {
		$("<div id='alarm_Old_Version'/>").window({
			title: "登记并派单",
			width: 800,
			height: 480, 
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
	else if(2==$('#alarmOrderVersion').val()) {
		$("#popWin").window({
			title: "登记并派单",
			width: 800,
			height: 480, 
			minimizable : false,
			maximizable : false,
			collapsible : false,
			modal : true,
			href: getRootName() + "/monitor/alarmSendOrderUnion/toNewAlarmSendOrderUnion?alarmIds="+alarmIds.join(",")
			
		});
	}
	else if(3 == $('#alarmOrderVersion').val()){
		$("<div id='popWin4'/>").window({
			title: "登记并派单",
			width: 800,
			height: 480,
			minimizable: false,
			maximizable: false,
			collapsible: false,
			modal: true,
			onClose: function(){
				$(this).window('destroy');
			},
			href: getRootName() + '/monitor/alarmSendOrderUnion/toThirdVersionSendOrderUnion?alarmIds='+alarmIds.join(",")
		}
		);
	}

}
