/**
 * 告警确认处理
 * 
 * @return
 */
function doProConfirm() {
	var path = getRootName();
	var uri = path + "/monitor/alarmActive/doAlarmActiveConfirm";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"alarmID" : $("#alarmID").val(),
			"confirmInfo" : $("#confirmInfo").val()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				// $.messager.alert("提示", "告警确认成功！", "info");
				$('#popWin').window('close');
				if (window.frames['component_2'].reloadTable) {
					window.frames['component_2'].reloadTable();
				}
				// 网络设备视图进入告警列表，对其进行操作后刷新
				else if (window.frames['component_2'].window.frames['alarmsInfo'] != undefined) {

					window.frames['component_2'].window.frames['alarmsInfo'].window
							.reloadTable();
				}
				// 首页设备快照进入告警列表，对其进行操作后刷新
				else if (window.frames['component_2'].window.frames['alarmsFirst'] != undefined) {

					window.frames['component_2'].window.frames['alarmsFirst'].window
							.reloadTable();

				}// 主机设备视图进入告警列表，对其进行操作后刷新
				else if (window.frames['component_2'].window.frames['alarmHost'] != undefined) {

					window.frames['component_2'].window.frames['alarmHost'].window
							.reloadTable();
				}
			} else {
				$.messager.alert("提示", "告警确认失败！", "error");
			}
		}
	};
	ajax_(ajax_param);
}

/**
 * 告警清除处理
 * 
 * @return
 */
function doProClear() {
	var path = getRootName();
	var uri = path + "/monitor/alarmActive/doClearAlarmActive";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"alarmID" : $("#alarmID").val(),
			"cleanInfo" : $("#cleanInfo").val()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				// $.messager.alert("提示", "告警清除成功！", "info");
				$('#popWin').window('close');
				if (window.frames['component_2'].reloadTable) {
					window.frames['component_2'].reloadTable();
				}
				// 网络设备视图进入告警列表，对其进行操作后刷新
				else if (window.frames['component_2'].window.frames['alarmsInfo'] != undefined) {

					window.frames['component_2'].window.frames['alarmsInfo'].window
							.reloadTable();
				}
				// 首页设备快照进入告警列表，对其进行操作后刷新
				else if (window.frames['component_2'].window.frames['alarmsFirst'] != undefined) {

					window.frames['component_2'].window.frames['alarmsFirst'].window
							.reloadTable();

				}// 主机设备视图进入告警列表，对其进行操作后刷新
				else if (window.frames['component_2'].window.frames['alarmHost'] != undefined) {

					window.frames['component_2'].window.frames['alarmHost'].window
							.reloadTable();
				}
			} else {
				$.messager.alert("提示", "告警清除失败！", "error");
			}
		}
	};
	ajax_(ajax_param);
}

/**
 * 批量告警确认处理
 * 
 * @return
 */
function doProBathConfirm() {
	var flag = $("#flag").val();
	var path = getRootName();
	var uri = path + "/monitor/alarmActive/doBathAlarmActiveConfirm";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"id" : $("#id").val(),
			"confirmInfo" : $("#confirmInfo").val()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				// $.messager.alert("提示", "告警确认成功！", "info");
				$('#popWin').window('close');

				if (flag != null && flag != "") {
					parent.window.reloadTable();
				} else {
					if (window.frames['component_2'].reloadTable) {
						window.frames['component_2'].reloadTable();
					}
					// 网络设备视图进入告警列表，对其进行操作后刷新
					else if (window.frames['component_2'].window.frames['alarmsInfo'] != undefined) {

						window.frames['component_2'].window.frames['alarmsInfo'].window
								.reloadTable();
					}
					// 首页设备快照进入告警列表，对其进行操作后刷新
					else if (window.frames['component_2'].window.frames['alarmsFirst'] != undefined) {

						window.frames['component_2'].window.frames['alarmsFirst'].window
								.reloadTable();

					}// 主机设备视图进入告警列表，对其进行操作后刷新
					else if (window.frames['component_2'].window.frames['alarmHost'] != undefined) {

						window.frames['component_2'].window.frames['alarmHost'].window
								.reloadTable();
					}
				}
			} else {
				$.messager.alert("提示", "告警确认失败！", "error");
			}
		}
	};
	ajax_(ajax_param);
}

/**
 * 批量告警清除处理
 * 
 * @return
 */
function doProBathClear() {
	var flag = $("#flag").val();
	var path = getRootName();
	var uri = path + "/monitor/alarmActive/doBathClearAlarmActive";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"id" : $("#id").val(),
			"cleanInfo" : $("#cleanInfo").val()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				// $.messager.alert("提示", "告警清除成功！", "info");
				$('#popWin').window('close');
				if (flag != null && flag != "") {
					parent.window.reloadTable();
				} else {
					if (window.frames['component_2'].reloadTable) {
						window.frames['component_2'].reloadTable();
					}
					// 网络设备视图进入告警列表，对其进行操作后刷新
					else if (window.frames['component_2'].window.frames['alarmsInfo'] != undefined) {

						window.frames['component_2'].window.frames['alarmsInfo'].window
								.reloadTable();
					}
					// 首页设备快照进入告警列表，对其进行操作后刷新
					else if (window.frames['component_2'].window.frames['alarmsFirst'] != undefined) {

						window.frames['component_2'].window.frames['alarmsFirst'].window
								.reloadTable();

					}// 主机设备视图进入告警列表，对其进行操作后刷新
					else if (window.frames['component_2'].window.frames['alarmHost'] != undefined) {

						window.frames['component_2'].window.frames['alarmHost'].window
								.reloadTable();
					}
					//首页最近告警，点击查看更多页面的活动告警，执行清除操作后的刷新
					else if (window.frames['component_2'].window.frames['RecentAlarm'] != undefined) {
						window.frames['component_2'].window.frames['RecentAlarm'].window.reloadTable()
					}
				}
			} else {
				$.messager.alert("提示", "告警清除失败！", "error");
			}
		}
	};
	ajax_(ajax_param);
}

function toQueryInfo(workOrderId) {
	var path = getRootName();
	parent.$('#popWin').window(
			{
				title : '派单信息',
				width : 885,
				height : 550,
				minimizable : false,
				maximizable : false,
				collapsible : false,
				modal : true,
				href : getRootName()
						+ '/alarmSendOrder/alarmSendOrderDetail?id='
						+ workOrderId
			});
}

/**
 * 首页告警中新增确认、清除、派单（开始）
 */

/**
 * 关闭
 */
function closeWind() {
	$('#popWin').window('close');
	/*$('#winAlarmSendSingleStep1').panel('destroy');
	$('#winAlarmSendSingleStep2').panel('destroy');*/
}

/**
 * 打开批量告警确认页面
 * 
 * @return
 */
function toBathConfirm() {
	var ids = $("#alarmID").val();
	var userName = $("#userName").val();
	var operateStatus = $("#operateStatus").val();
	var flag = null;
	if (operateStatus != 21) {
		flag = "1";
	}
	if (null != ids) {
		if (null == flag) {
			parent.parent
					.$('#popWin3')
					.window(
							{
								title : '告警确认',
								width : 650,
								height : 400,
								minimizable : false,
								maximizable : false,
								collapsible : false,
								modal : true,
								onClose : function() {
									$('#popWin').panel('open').panel('refresh');
									$('#winAlarmSendSingleStep1').panel(
											'destroy');
									$('#winAlarmSendSingleStep2').panel(
											'destroy');
									// 确认告警信息后刷新首页最近告警iframe
									if (parent.window.frames['component_2'] != undefined) {
										var framLen;
										var iframes;
										if (userName != 'admin') {
											iframes = parent.window.frames['component_2'].window.document
													.getElementById('ifrhomepage'
															+ userName + '');
										} else {
											iframes = parent.window.frames['component_2'].window.document
													.getElementById('ifrhomepage');
										}
										framLen = iframes.contentWindow.frames.length;
										var frameId;
										for (var i = 0; i < framLen; i++) {
											frameId = iframes.contentWindow.frames[i].frameElement.id;
											if (frameId.indexOf("AlarmActive") === 0) {
												iframes.contentWindow
														.refreshIfrView(""
																+ frameId + "");
												return;
											}
										}
									}
								},
								href : getRootName()
										+ '/monitor/alarmActive/toAlarmConfirm?alarmID='
										+ ids
							});
		} else {
			$.messager.alert('提示', '只允许确认告警状态为未确认的告警!', 'info');
		}
	} else {
		$.messager.alert('提示', '没有任何选中项', 'info');
	}
}

/**
 * 打开批量告警清除页面
 * 
 * @return
 */
function toBathClear() {
	var ids = $("#alarmID").val();
	var operateStatus = $("#operateStatus").val();
	// 为了与实时告警功能统一，暂时将（未确认不可以清除）的功能关闭
	if (null != ids) {
		parent.parent.$('#popWin3').window(
				{
					title : '告警清除',
					width : 650,
					height : 400,
					minimizable : false,
					maximizable : false,
					collapsible : false,
					modal : true,
					href : getRootName()
							+ '/monitor/alarmActive/toClearAlarm?id=' + ids
				});
	} else {
		$.messager.alert('提示', '没有任何选中项', 'error');
	}
}

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
alarmSendSingleShouye = {};
alarmSendSingleShouye.selectPerson = function() {
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
	           }
	        },
	        error:function(){
	        	$.messager.alert("提示", "未知错误！","error");
	        }
	    });
	    /*地市告警匹配end*/
	    return isalReadySend;
}
alarmSendSingleShouye.toBathSendSingle = function() {
	var userName = $("#userName").val();
	var ids = [];
	var rows = [];
	rows.push({
		"alarmTitle" : $("#title").text(),
		"alarmID" : $("#alarmID").val(),
		"sourceMOName" : $("#sourceMOName").text(),
		"sourceMOIPAddress" : $("#sourceMOIPAddress").text(),
		"alarmLevel" : $("#alarmLevel").val(),
		"alarmLevelName" : $("#alarmLevelName").text(),
		"operateStatusName" : $("#operateStatusName").text(),
		"startTime" : $("#startTime").text(),
		"dispatchID" : $("#dispatchID").val()
	});
	var flag = false;
	var isSend = [];
	var rows2 = $.map(rows, function(v) {
		if (v.dispatchID) {
			flag = true;
		}
		return v;
	});
	if (flag) {
		$.messager.alert("提示", "已派发的告警不允许再次派发！");
		return;
	}
	//如果匹配到告警为省厅已经派发，则提示退出
    if(machingAlarm($("#alarmID").val()) == 1){
    	return;
    }
	if (1 == $('#alarmOrderVersion').val()) {
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
			href: getRootName() + "/monitor/alarmSendOrderUnion/toOldAlarmSendOrderUnion?alarmIds="+$("#alarmID").val()
	}
    );
	
	} else if (2 == $('#alarmOrderVersion').val()) {
		$("#popWin").window({
			title: "登记并派单",
			width: 800,
			height: 480, 
			minimizable : false,
			maximizable : false,
			collapsible : false,
			modal : true,
			href: getRootName() + "/monitor/alarmSendOrderUnion/toNewAlarmSendOrderUnion?alarmIds="+$("#alarmID").val()
			
		});
		} else if(3 == $('#alarmOrderVersion').val()){
			parent.parent.$("#popWin4").window({
				title: "登记并派单",
				width: 800,
				height: 480,
				minimizable: false,
				maximizable: false,
				collapsible: false,
				modal: true,
				href: getRootName() + '/monitor/alarmSendOrderUnion/toThirdVersionSendOrderUnion?alarmIds='+$("#alarmID").val()
			}
			);
		
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

/**
 * 首页入口的确认告警
 * 
 * @return
 */
function doShouyeAlarm() {
	var flag = $("#shouyeflag").val();
	var path = getRootName();
	var uri = path + "/monitor/alarmActive/doAlarmConfirm";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"id" : $("#hiddenId").val(),
			"confirmInfo" : $("#shouyeconfirmInfo").val()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				$('#popWin3').window('close');
			} else {
				$.messager.alert("提示", "告警确认失败！", "error");
			}
		}
	};
	ajax_(ajax_param);
}

/**
 * 首页告警清除
 * 
 * @return
 */
function doAlarmClear() {
	var flag = $("#cleanflag").val();
	var path = getRootName();
	var uri = path + "/monitor/alarmActive/doBathClearAlarmActive";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"id" : $("#cleanid").val(),
			"cleanInfo" : $("#shouyecleanInfo").val()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				$('#popWin3').window('close');
				with (window.frames['component_2'].window) {
					closeWind();
				}
				var userName = $("#hiddenUserName").val();
				var iframes;
				if (userName != 'admin') {
					// 清除告警信息后刷新首页最近告警iframe
					iframes = window.frames['component_2'].window.document
							.getElementById('ifrhomepage' + userName + '');
					var frameslength = iframes.contentWindow.frames.length;
				} else {
					iframes = window.frames['component_2'].window.document
							.getElementById('ifrhomepage');
					var frameslength = iframes.contentWindow.frames.length;
				}
				var frameId;
				for (var i = 0; i < frameslength; i++) {
					frameId = iframes.contentWindow.frames[i].frameElement.id;
					if (frameId.indexOf("AlarmActive") === 0) {
						iframes.contentWindow.refreshIfrView("" + frameId + "");
						return;
					}
				}
			} else {
				$.messager.alert("提示", "告警清除失败！", "error");
			}
		}
	};
	ajax_(ajax_param);
}
/**
 * 首页告警中新增确认、清除、派单（结束）
 */
