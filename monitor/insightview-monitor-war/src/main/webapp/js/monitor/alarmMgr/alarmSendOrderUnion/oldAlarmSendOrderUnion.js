/**
 * 
 */
// 是否发送短信默认设置
$(document).ready(function() {
	$.ajax({
		url : getRootName() + "/materialApply/isSendMessage",
		success : function(data) {
			if (data == "false") {
				$("#sendMsg").attr("checked", false);
			} else {
				$("#sendMsg").attr("checked", true);
			}                         
		},
		error : function() {
			$("#sendMsg").attr("checked", true);
		}
	});
});

$(function(){
	var tab = $("#alarmSendSingleTableId"), f1 = $("#winAlarmSendSingleForm1"), f2 = $("#winAlarmSendSingleForm2");
	initWindowAlarmSendSignleStep1();
	initWindowAlarmSendSignleStep2();
	$("#selectCondition input[type='radio']:first").trigger("change");
})

function closeAllWindow(){
	  $('#alarm_Old_Version').window('close');
      $("#winAlarmSendSingleStep2").window('close');
}

function initWindowAlarmSendSignleStep1(){
	$("#alarmSendSingleTableId")
			.css("height", "200px")
			.datagrid(
					{
						title : "告警派单列表",
						fitColumns : true,
						scrollbarSize : 0,
						striped : true,
						rownumbers : true,
						width: 'auto',
						url: getRootName() + '/monitor/alarmSendOrderUnion/alarmSendOrderUnionConfig?alarmIds=' + $('#alarmIds').val(),
						columns : [ [
								{
									field : "alarmTitle",
									title : "告警标题",
									width : 100,
									align : "center"
								},
								{
									field : "alarmID",
									title : "告警序号",
									width : 100,
									align : "center",
									formatter : function(value, row) {
										return '<a style="cursor: pointer;" title="查看" onclick="javascript:toView('
												+ row.alarmID
												+ ');">'
												+ row.alarmID + '</a>';
									}
								}, {
									field : "sourceMOName",
									title : "告警源名称",
									width : 100,
									align : "center"
								}, {
									field : "sourceMOIPAddress",
									title : "告警源IP",
									width : 100,
									align : "center"
								}, {
									field : "alarmLevelName",
									title : "告警等级",
									width : 100,
									align : "center"
								}, {
									field : "statusName",
									title : "告警状态",
									width : 100,
									align : "center"
								} ] ]
					}).end();
	$("#winAlarmSendSingleStep1 a:first").click(function() {
		var rows = $("#alarmSendSingleTableId").datagrid("getRows");
		if (0 == rows.length) {
			$.messager.alert("警告", "请选择要派发的告警");
			return;
		}
		if (checkInfo('.formtable')) {
			$("#winAlarmSendSingleStep2").window("open");
		}
	});

}

function initWindowAlarmSendSignleStep2(){

	var root = getRootName();
	var submiting = false;
	// 确定
	$("#winAlarmSendSingleStep2 a:eq(1)").unbind()
			.click(
					function() {
						if (submiting) {
							return;
						}
						submiting = true;
						// 两个表单数据
						var param = $
								.extend({},util.serializeForm("#winAlarmSendSingleForm1"),util.serializeForm("#winAlarmSendSingleForm2"));
						if ("" == param.selectPeopleId
								|| null == param.selectPeopleId) {
							$.messager.alert("警告", "处理人必须选择");
							return;
						}
						var rows = $("#alarmSendSingleTableId").datagrid(
								"getRows");
						var rs = [];
						var ids = $.map(rows, function(v) {
							var r = {};
							r.alarmID = v.alarmID;
							r.sourceMOName = v.sourceMOName;
							r.sourceMOIpAdress = v.sourceMOIPAddress;
							r.alarmLevel = v.alarmLevel;
							r.alarmLevelName = v.alarmLevelName;
							r.alarmTitle = v.alarmTitle;
							r.moClassID = v.moclassID
							r.moClassName = v.moClassName;
							r.alarmDescription = v.alarmContent;
							r.sourceMoClassID = v.sourceMOClassID;
							r.startTime = formatDate(new Date(v.startTime),
									"yyyy-MM-dd hh:mm:ss");
							r.alarmStatus = v.alarmStatus;
							r.statusName = v.statusName;
							r.repeatCount = v.repeatCount;
							// 新增的
							r.alarmOperateStatus = v.alarmOperateStatus;
							r.operateStatusName = v.operateStatusName;
							rs.push(r);

							return v.alarmID;
						});
						param.content = JSON.stringify(rs);
						param.ids = ids.join();
						$.ajax({
									url : getRootName()
											+ "/monitor/AlarmSendSingle/ajaxSendSingleAlarm",
									type : "post",
									data : param,
									dataType : "json",
									success : function(d) {
										var msg = '派发成功.';
										if (!d) {
											msg = '派发失败，请稍后再试。';
							        	} 
										$.messager.alert("提示", msg, "info");
										closeAllWindow();
										if(typeof reloadTable === 'function'){
											reloadTable();	
										}else{
											var iframes = parent.frames['component_2'].frames['ifrhomepage'];
											var frameId;
											for (var i = 0; i < iframes.contentWindow.frames.length; i++) {
												frameId = iframes.contentWindow.frames[i].frameElement.id;
												if (frameId.indexOf("AlarmActive") === 0) {
													iframes.contentWindow.refreshIfrView("" + frameId + "");
													return;
												}
											}
										}
										submiting = false;
										
									},
									error : function() {
										$.messager.alert("提示",
												"派发失败，请稍后再试。");
										submiting = false;
									}
								});
					});
	// 上一步
	$("#winAlarmSendSingleStep2 a:eq(0)").click(function() {
		$("#winAlarmSendSingleStep2").window("close");
		$("#summary").val("");
	});
	var t = $("#tree")
			.tree(
					{
						onBeforeExpand : function(node) {
							var url = root
									+ "/monitor/AlarmSendSingle/ajaxFindTreeDepartments.do";
							if ($("input[name='selectCondition']:checked")
									.val() == "groupList") {
								url = root
										+ "/monitor/AlarmSendSingle/ajaxFindAllGroups.do";
							}
							$('#tree').tree('options').url = url + "?pid="
									+ node.id;
						},
						onClick : function(n) {
							$("#selectPeopleId").val("");
							$("#selectPeopleName").val("");
							var url = root
									+ "/monitor/AlarmSendSingle/ajaxFindStaffInfo.do";
							if ($("input[name='selectCondition']:checked")
									.val() == "groupList") {
								url = root
										+ "/monitor/AlarmSendSingle/ajaxFindStaffInfo2.do";
							}
							$.ajax({
										url : url,
										data : {
											id : n.id
										},
										dataType : "json",
										success : function(data) {
											var s = "";
											for (var i = 0; i < data.length; i++) {
												var temp = data[i];
												s += "<input id='r"
														+ temp.key
														+ "' name='"
														+ temp.val
														+ "' type='checkbox' onclick='alarmSendSingletoCount_Old.selectPerson()' value='"
														+ temp.key
														+ "'><label for='r"
														+ temp.key + "'>"
														+ temp.val
														+ "</label><br/>";
											}
											$("#departmentMan").html(s);
										}
									});
						}
					});
	$("#selectCondition input[type='radio']")
			.change(
					function() {
						var self = $(this);
						if (!self.is(':checked')) {
							return;
						}
						$("#selectPeopleId").val("");
						$("#selectPeopleName").val("");
						var url = root
								+ "/monitor/AlarmSendSingle/ajaxFindTreeDepartments.do";
						if ($("input[name='selectCondition']:checked")
								.val() == "groupList") {
							url = root
									+ "/monitor/AlarmSendSingle/ajaxFindAllGroups.do";
						}
						$.ajax({
							url : url,
							data : {
								pid : 0
							},
							dataType : "json",
							success : function(temp) {
								t.tree("loadData", []).tree("loadData",
										temp);

								var f = t.tree('getRoots')[0];
								f && t.tree('select', f.target);
								f && $(f.target).trigger("click");
							}
						});
					});

}

//告警派发工具
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

//告警派发功能对象
alarmSendSingletoCount_Old = {};
alarmSendSingletoCount_Old.selectPerson = function() {
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

