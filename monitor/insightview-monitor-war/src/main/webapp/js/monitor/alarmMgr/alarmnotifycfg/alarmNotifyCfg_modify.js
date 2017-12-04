var policyID=$('#policyID').val();
$(document).ready(function() {
	f('#ipt_emailAlarmContent').richtext({
		height:80
	});
	f('#ipt_emailClearAlarmContent').richtext({
		height:80
	});
	f('#ipt_emailDeleteAlarmContent').richtext({
		height:80
	});
	var smsFlag=$('#smsFlag').val();
	var emailFlag=$('#emailFlag').val();
	if(smsFlag == 0){
		$("#sms1").hide(); 
		$("#sms2").hide(); 
		$("#sms3").hide(); 
	}else{
		$("#sms1").show(); 
		$("#sms2").show(); 
		$("#sms3").show(); 
	}
    if(emailFlag == 0){
		$("#email1").hide(); 
		$("#email2").hide(); 
		$("#email3").hide(); 
	}else{
		$("#email1").show(); 
		$("#email2").show(); 
		$("#email3").show(); 
	}
    toShowAlarmNotifyCfg(policyID);
    toShowNotifyToUsers(policyID);
    toShowNotifyFilter(policyID);
    doInitUserTable();
    getLevel();
    
});

function editSms(){
	var isSmsChecked  = $('input[name="isSms"]:checked').val();
	if(isSmsChecked == '0'){
		$("#sms1").hide(); 
		$("#sms2").hide(); 
		$("#sms3").hide(); 
	}else{
		$("#sms1").show(); 
		$("#sms2").show(); 
		$("#sms3").show(); 
	}
}
	
function editEmail(){
	var isEmailChecked =  $('input[name="isEmail"]:checked').val();
	if(isEmailChecked == '0'){
		$("#email1").hide(); 
		$("#email2").hide(); 
		$("#email3").hide(); 
	}else{
		$("#email1").show(); 
		$("#email2").show(); 
		$("#email3").show();
	}
}

/*
 * 用户列表
 */
function doInitUserTable() {
	var path = getRootName();
	$('#tblSysUser')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : 320,
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
//						fit : true,// 自动大小
						fitColumns:true,
						url : path + '/monitor/alarmNotifyCfg/listSysUser',
						// sortName: 'code',
						// sortOrder: 'desc',
						queryParams : {
							"status" : -1,
							"isAutoLock" : -1,
							"userType" : -1,
							"notifyToUserFilter" : $('#policyID').val()
						},
						remoteSort : false,
						idField : 'fldId',
						singleSelect : false,// 是否单选
						checkOnSelect : false, 
						selectOnCheck : true,
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
						        {
						        	field : 'userID',
						        	checkbox : true,
						        },
								{
									field : 'userName',
									title : '用户姓名',
									width : 130,
									align : 'center'
								},
								{
									field : 'mobilePhone',
									title : '手机号码',
									width : 180,
									align : 'center'
								},
								{
									field : 'email',
									title : '邮箱',
									width : 180,									
									align : 'center'
								},
								 ] ]
					});
}

function reloadUserTable() {
	var userName =  $("#txtFilterUserName").val();
//	alert(userName);
	$('#tblSysUser').datagrid('options').queryParams = {
		"userName" : userName,
		"userType" : -1,
		"isAutoLock" : -1,
		"status" : -1,
		"notifyToUserFilter" : $("#policyID").val()
	};
	reloadTableCommon_1('tblSysUser')
}

function reloadTableCommon_1(dataGridId) {
	$('#' + dataGridId).datagrid('load');
	$('#' + dataGridId).datagrid('uncheckAll');
}

/*
 * 通知策略定义
 */
function toShowAlarmNotifyCfg(policyID){
	var path=getRootName();
	var uri=path+"/monitor/alarmNotifyCfg/viewAlarmNotifyCfg";
	var ajax_param={
			url:uri,
			type:"post",
			datdType:"json",
			data:{
				"policyID":policyID,
				"t":Math.random()
			},
			error:function(){
				$.messager.alert("错误","ajax_error","error");
			},
			success:function(data){
				iterObj(data,"ipt");
				if(data.isSms == 0){
					$("input:radio[name='isSms'][value=0]").attr("checked",'checked');
				}else if(data.isSms == 1){
					$("input:radio[name='isSms'][value=1]").attr("checked",'checked');
				}
				if(data.isEmail == 0){
					$("input:radio[name='isEmail'][value=0]").attr("checked",'checked');
					$("#email").hide();
				}else if(data.isEmail == 1){
					$("input:radio[name='isEmail'][value=1]").attr("checked",'checked');
				}
				var isPhone = data.isPhone;
				var voiceMessageType = data.voiceMessageType;
				if(isPhone == 0){
					$("input:radio[name='isPhone'][value=0]").attr("checked",'checked');
					$("#voiceTitle").hide();
					$("#voiceTd").hide();
					$('#phone1').hide();
					$('#phone2').hide();
					$('#phone3').hide();
					$('#voice1').hide();
					$('#voice2').hide();
				}else if(voiceMessageType == 1){
					$("input:radio[name='isPhone'][value=1]").attr("checked",'checked');
					$("input:radio[name='voiceMessageType'][value=1]").attr("checked",'checked');
					$("#voiceTitle").show();
					$("#voiceTd").show();
					$('#phone1').show();
					$('#phone2').show();
					$('#phone3').show();
					$('#voice1').hide();
					$('#voice2').hide();
				}else{
					$("input:radio[name='isPhone'][value=1]").attr("checked",'checked');
					$("input:radio[name='voiceMessageType'][value=2]").attr("checked",'checked');
					$("#voiceTitle").show();
					$("#voiceTd").show();
					$('#phone1').hide();
					$('#phone2').hide();
					$('#phone3').hide();
					$('#voice1').show();
					$('#voice2').show();
					$('#ipt_alarmVoiceID').combobox('setValue',data.alarmVoiceID);
					$('#ipt_clearAlarmVoiceID').combobox('setValue',data.clearAlarmVoiceID);
					$('#ipt_deleteAlarmVoiceID').combobox('setValue',data.deleteAlarmVoiceID);
				}
				$('#ipt_maxTimes').val(data.maxTimes);
				$('#ipt_smsAlarmContent').val(data.smsAlarmContent);
				$('#ipt_smsClearAlarmContent').val(data.smsClearAlarmContent);
				$('#ipt_smsDeleteAlarmContent').val(data.smsDeleteAlarmContent);
				$('#ipt_emailAlarmContent').richtext('setValue',data.emailAlarmContent);
				$('#ipt_emailClearAlarmContent').richtext('setValue',data.emailClearAlarmContent);
				$('#ipt_emailDeleteAlarmContent').richtext('setValue',data.emailDeleteAlarmContent);
			}
		};
	ajax_(ajax_param);
}

/*
 * 通知用户
 */
function toShowNotifyToUsers(policyID){
	var path = getRootName();
	$('#tblNotifyToUser')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : '360',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fitColumns:true,
						url : path + '/monitor/alarmNotifyToUsers/viewNotifyToUsers',
						queryParams : {
							"policyID":policyID,
						},
						remoteSort : false,
						idField : 'fldId',
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
								{
									field : 'userName',
									title : '用户姓名',
									width : 100,
									formatter : function(value, row, index){
										if(row.userID == -100){
											return "值班负责人";
										}else{
											return value;
										}
									}
								},
								{
									field : 'mobileCode',
									title : '手机',
									width : 130
								},
								{
									field : 'email',
									title : '邮箱',
									width : 150,
								},
								{
									field : 'filterID',
									title : '操作',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:doDelNotifyToUser('
												+ row.filterID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_delete.png" title="删除"/></a>';
									}
								}
								 ] ]
					});
}

function reloadToUserTable() {
	$('#tblNotifyToUser').datagrid('options').queryParams = {
		"policyID" : policyID,
	};
	reloadTableCommon_1('tblNotifyToUser')
}

/*
 * 通知过滤详情
 */
function toShowNotifyFilter(policyID){
	var path = getRootName();
	$('#viewNotifyFilter')
			.datagrid(
					{
						iconCls : 'icon-edit',// 图标
						width : 'auto',
						height : '380',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fitColumns:true,
						url : path + '/monitor/alarmNotifyFilter/viewAlarmNotifyFilter',
						queryParams : {
							"policyID":policyID,
						},
						remoteSort : false,
						idField : 'fldId',
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						toolbar : [ {
							'text' : '告警等级',
							'iconCls' : 'icon-add',
							handler : function() {
								toSelectAlarmLevel();
							}
						}, {
							'text' : '告警类型',
							'iconCls' : 'icon-add',
							handler : function() {
								toSelectAlarmType();
							}
						}, {
							'text' : '告警源对象',
							'iconCls' : 'icon-add',
							handler : function() {
								toSelectMOSource();
							}
						}, {
							'text' : '告警事件',
							'iconCls' : 'icon-add',
							handler : function() {
								toSelectAlarmEvent();
							}
						} ],
						columns : [ [
								{
									field : 'filterKey',
									title : '过滤关键字',
									width : 200,
									align : 'left',
									formatter : function(value, row, index) {
										var rtnVal = "";
										if(value=="AlarmLevel"){
											rtnVal = "告警等级";
										}else if(value=="AlarmType"){
											rtnVal = "告警类型";
										}else if(value=="AlarmSourceMOID"){ 
											rtnVal = "告警源对象";
										}else if(value=="AlarmDefineID"){
											rtnVal = "告警事件";
										}
										return rtnVal;
									}
								},
								{
									field : 'filterValeName',
									title : '过滤值',
									width : 150,
									
								},
								{
									field : 'filterID',
									title : '操作',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
										return '<a style="cursor: pointer;" onclick="javascript:doDelNotifyFilter('
												+ row.filterID
												+ ');"><img src="'
												+ path
												+ '/style/images/icon/icon_delete.png" title="删除"/></a>';
									}
								} 
								 ] ]
					});
}

/*
 * 更新
 */
function toUpdate(){
	checkNameBeforeUpdate();
}

/*
 * 校验邮件模板的长度（富文本框）
 */
function checkEmailAlarmContent(){
	var emailAlarmContent=f('#ipt_emailAlarmContent').richtext('getValue');
	var length = emailAlarmContent.length;
	if(length>2000){
		$.messager.alert("提示", "邮件模板内容过长，请修改！", "error");
		return false;
	}else{
		return true;
	}
}

/*
 * 验证规则名称是否存在
 */
function checkNameBeforeUpdate(){
	var path = getRootName();
	var uri = path + "/monitor/alarmNotifyCfg/checkNamebeforeUpdate";
	var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
		"policyName" :$("#ipt_policyName").val(),
		"policyID":policyID,
		"t" : Math.random()
	},
	error : function() {
		$.messager.alert("错误", "ajax_error", "error");
	},
	success : function(data) {
		if (false == data || "false" == data) {
			$.messager.alert("提示", "该规则名称已存在！", "info");
		} else {
			doAddAlarmNotifyCfg();
			return;
		}
	}
	};
	ajax_(ajax_param);
}

/*
 * 更新通知策略
 */
function doAddAlarmNotifyCfg(){
	var result = checkInfo('#alarmNotifyTabs');
	var isSms = $('input[name="isSms"]:checked').val();
	var isEmail = $('input[name="isEmail"]:checked').val();
	var isPhone = $('input[name="isPhone"]:checked').val();
	if(isSms== 1 || isEmail == 1 || isPhone == 1){
		var maxTimes = $("#ipt_maxTimes").val();
		if(maxTimes == ""){
			$.messager.alert("提示", "设置短信、邮件或者电话语音通知时，最大发送次数不能为空！", "info");
			return;
		}
		if(maxTimes > 5){
			$.messager.alert("提示", "最大发送次数不能超过5次！", "info");
			return;
		}
	}
	var smsAlarmContent=$("#ipt_smsAlarmContent").val();
	var smsClearAlarmContent=$("#ipt_smsClearAlarmContent").val();
	var smsDeleteAlarmContent=$("#ipt_smsDeleteAlarmContent").val();
	var emailAlarmContent=f('#ipt_emailAlarmContent').richtext('getValue');
	var emailClearAlarmContent=f('#ipt_emailClearAlarmContent').richtext('getValue');
	var emailDeleteAlarmContent=f('#ipt_emailDeleteAlarmContent').richtext('getValue');
	var path = getRootName();
	var uri=path+"/monitor/alarmNotifyCfg/addNotify";
	var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
		"notifyCfg.policyID" : $("#policyID").val(),
		"notifyCfg.policyName" :$("#ipt_policyName").val(),
		"notifyCfg.alarmOnCount" :$("#ipt_alarmOnCount").val(),
		"notifyCfg.isSms":  $('input[name="isSms"]:checked').val(),
		"notifyCfg.smsAlarmContent":smsAlarmContent,
		"notifyCfg.smsClearAlarmContent":smsClearAlarmContent,
		"notifyCfg.smsDeleteAlarmContent":smsDeleteAlarmContent,
		"notifyCfg.isEmail":$('input[name="isEmail"]:checked').val(),
		"notifyCfg.emailAlarmContent":emailAlarmContent,
		"notifyCfg.emailClearAlarmContent":emailClearAlarmContent,
		"notifyCfg.emailDeleteAlarmContent":emailDeleteAlarmContent,
		"notifyCfg.maxTimes":$("#ipt_maxTimes").val(),
		"notifyCfg.isPhone":$('input[name="isPhone"]:checked').val(),
		"notifyCfg.voiceMessageType":$('input[name="voiceMessageType"]:checked').val(),
		"notifyCfg.phoneAlarmContent":$("#ipt_phoneAlarmContent").val(),
		"notifyCfg.phoneClearAlarmContent":$("#ipt_phoneClearAlarmContent").val(),
		"notifyCfg.phoneDeleteAlarmContent":$("#ipt_phoneDeleteAlarmContent").val(),
		"notifyCfg.alarmVoiceID":$("#ipt_alarmVoiceID").combobox("getValue"),
		"notifyCfg.clearAlarmVoiceID":$("#ipt_clearAlarmVoiceID").combobox("getValue"),
		"notifyCfg.deleteAlarmVoiceID":$("#ipt_deleteAlarmVoiceID").combobox("getValue"),
		"notifyCfg.descr":$("#ipt_descr").val(),
		"t" : Math.random()
		},
		error : function(){
			$.messager.alert("错误","ajax_error","error");
		},
		success:function(data){
			if (data == true) {
				$.messager.alert("提示", "告警通知策略更新成功！", "info");
				$('#popWin').window('close');
				window.frames['component_2'].reloadTable();
			} else {
				$.messager.alert("提示", "告警通知策略更新失败！", "error");
			}
		}
	};	
	if(result == true){
		var checkEmailRS = checkEmailAlarmContent();
		if(checkEmailRS == true){
			ajax_(ajax_param);
		}
	}
}

/*
 * 从用户列表选择用户
 */
function getFromUsers(){
	resetFormFilter();
	reloadUserTable();
	$("#btnAdduser").unbind();
	$("#btnAdduser").bind("click", function() {
		addUser();
	});
	$("#btnCloseUser").unbind();
	$("#btnCloseUser").bind("click", function() {
		$('#divAddUser').dialog('close');
	});
	$('#divAddUser').dialog('open');
}
function resetFormFilter(){
	$("#txtFilterUserName").val("");
}
/*
 * 添加用户
 */
function addUser(){
	var policyID = $('#policyID').val();
	var path=getRootName();
	var checkedItems = $('[name=userID]:checked');
	var ids = null;
	$.each(checkedItems, function(index, item) {
		if (null == ids) {
			ids = $(item).val();
		} else {
			ids += ',' + $(item).val();
		}
	});
	if (null != ids) {
		$.messager.confirm("提示","确定添加所选中项？",function(r){
			if (r == true) {
				var uri = path+"/monitor/alarmNotifyToUsers/addUserFromList?userIds=" + ids+"&policyID="+policyID;
				var ajax_param = {
					url : uri,
					type : "post",
					datdType : "json",
					data : {
						"t" : Math.random()
					},
					success : function(data) {
						if(data.flag == false) {
			            	$.messager.alert("错误", "用户添加失败！", "error");
						} else {
							reloadToUserTable();
							$('#divAddUser').dialog('close');
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
 * 打开临时添加用户页面
 */
function getNow(){
	resetFormPrivate('tblUserNow');
	$("#btnAddNow").unbind();
	$("#btnAddNow").bind("click", function() {
		checkForAddNow();
	});
	$("#btnColseNow").unbind();
	$("#btnColseNow").bind("click", function() {
		$('#divAddUserNow').dialog('close');
	});
	$('#divAddUserNow').dialog('open');
}

/**
 * 重置表单
 * 
 * @param pControlId:表单容器ID
 */
function resetFormPrivate(pControlId) {
	resetForm(pControlId);
}

/*
 * 临时添加用户的验证
 */
function checkForAddNow(){
	var path = getRootName();
	var uri = path + "/monitor/alarmNotifyToUsers/checkForAddNow";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"policyID":$("#policyID").val(),
			"mobileCode" : $("#ipt_mobileNow").val(),
			"email" : $("#ipt_EmailNow").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				addNow();
			} else {
				$.messager.alert("提示", "该临时用户已存在", "error");
			}
		}
	}
		ajax_(ajax_param);
}
/*
 * 临时添加用户
 */
function addNow(){
	var result = checkInfo('#divAddUserNow');
	var path = getRootName();
	var uri = path + "/monitor/alarmNotifyToUsers/addNow";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"policyID":$("#policyID").val(),
			"mobileCode" : $("#ipt_mobileNow").val(),
			"email" : $("#ipt_EmailNow").val(),
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				$.messager.alert("提示", "临时添加用户成功！", "info");
				reloadToUserTable();
				$('#divAddUserNow').dialog('close');
			} else {
				$.messager.alert("提示", "临时添加用户失败！", "error");
			}
		}
	}
	if(result == true){
		ajax_(ajax_param);
	}
}

/*
 * 删除通知策略通知人
 */
function doDelNotifyToUser(filterID){
//	alert("删除的filterID=="+filterID);
	var path=getRootName();
	$.messager.confirm("提示","确定删除此条？",function(r){
		if (r == true) {
			var uri = path + "/monitor/alarmNotifyToUsers/delNotifyToUsesrs";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"filterID" : filterID,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (true == data || "true" == data) {
						$.messager.alert("提示", "该通知用户删除成功！", "info");
						reloadToUserTable();
					} else {
						$.messager.alert("提示", "该通知用户删除失败！", "info");
					}
				}
			};
			ajax_(ajax_param);
		}
	});
}


/*
 * 更新通知过滤表数据
 */
function reloadFilterTable() {
	$('#viewNotifyFilter').datagrid('options').queryParams = {
		"policyID" :$("#policyID").val()
	};
	reloadTableCommon_1('viewNotifyFilter')
}

/*
 * 获得过滤策略的单选key
 */
function keySelected(){
	var keyChecked =  $('input[name="key"]:checked').val();
//	alert(keyChecked);
	if(keyChecked == '告警等级'){
		getLevel();
	}else if(keyChecked == '告警类型'){
		getType();
	}else if(keyChecked == '告警源对象'){
		getMOSource();
	}else{
		getEvent();
	}
}

/*
 * 关键字为告警等级
 */
function getLevel() {
	$("#selLeft").empty();
	var path = getRootName();
	var uri = path + "/monitor/alarmNotifyFilter/getNotSelelctLevel";
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
			var levelLst = eval('(' + data.levelLstJson + ')');
			for (var i = 0; i < levelLst.length; i++) {
				var level = levelLst[i];
				$("#selLeft").append(
						"<option value='" + level.alarmLevelID + "'>"
								+ level.alarmLevelName + "</option>");
			}
		}
	};
	ajax_(ajax_param);
}

/*
 * 关键字为告警类型
 */
function getType() {
	$("#selLeft").empty();
	var path = getRootName();
	var uri = path + "/monitor/alarmNotifyFilter/getType";
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
			var typeLst = eval('(' + data.typeLstJson + ')');
			for (var i = 0; i < typeLst.length; i++) {
				var type = typeLst[i];
				$("#selLeft").append(
						"<option value='" + type.alarmTypeID + "'>"
								+ type.alarmTypeName + "</option>");
			}
		}
	};
	ajax_(ajax_param);
}

/*
 * 关键字为告警源
 */
function getMOSource() {
	$("#selLeft").empty();
	var path = getRootName();
	var uri = path + "/monitor/alarmNotifyFilter/getMOSource";
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
			var moSourceLst = eval('(' + data.moSourceLstJson + ')');
			for (var i = 0; i < moSourceLst.length; i++) {
				var moSource = moSourceLst[i];
				$("#selLeft").append(
						"<option value='" + moSource.moID + "'>"
								+ moSource.moName + "</option>");
			}
		}
	};
	ajax_(ajax_param);
}

/*
 * 关键字为告警事件
 */
function getEvent() {
	$("#selLeft").empty();
	var path = getRootName();
	var uri = path + "/monitor/alarmNotifyFilter/getEvent";
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
			var alarmEventLst = eval('(' + data.alarmEventLstJson + ')');
			for (var i = 0; i < alarmEventLst.length; i++) {
				var alarmEvent = alarmEventLst[i];
				$("#selLeft").append(
						"<option value='" + alarmEvent.alarmDefineID + "'>"
								+ alarmEvent.alarmName + "</option>");
			}
		}
	};
	ajax_(ajax_param);
}

/**
 * 新增通知过滤前的验证
 * @return
 */
function checkForAddFilter(){
	var options=$("#selLeft option:selected");
	var filterFlag = $('input[name="key"]:checked').val();
	var filterKey = "";
	if(filterFlag == "告警等级"){
		filterKey = "AlarmLevel";//表示告警等级
	}else if(filterFlag == "告警类型"){
		filterKey = "AlarmType";//表示告警类型
	}else if(filterFlag == "告警源对象"){
		filterKey = "AlarmSourceMOID";//表示告警源对象
	}else if(filterFlag == "告警事件"){
		filterKey = "AlarmDefineID";//表示告警事件
	}
	var filterKeyValue = options.val();
	var path = getRootName();
	var uri = path + "/monitor/alarmNotifyFilter/checkForAddFilter";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"policyID":$("#policyID").val(),
			"filterKey":filterKey,
			"filterKeyValue" : filterKeyValue,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				selectKeyValue();
			} else {
				$.messager.alert("提示", "该过滤条件已经存在！", "error");
			}
		}
	}
		ajax_(ajax_param);
}
/*
 * 选择过滤条件
 */
function selectKeyValue(){
	var options=$("#selLeft option:selected");  //获取选中的项
//	alert("options.val()=="+options.val());   //拿到选中项的值
//	alert("options.text()=="+options.text());   //拿到选中项的文本
	var filterFlag = $('input[name="key"]:checked').val();
	var filterKey = "";
	if(filterFlag == "告警等级"){
		filterKey = "AlarmLevel";//表示告警等级
	}else if(filterFlag == "告警类型"){
		filterKey = "AlarmType";//表示告警类型
	}else if(filterFlag == "告警源对象"){
		filterKey = "AlarmSourceMOID";//表示告警源对象
	}else if(filterFlag == "告警事件"){
		filterKey = "AlarmDefineID";//表示告警事件
	}
	var filterKeyValue = options.val();
	var path = getRootName();
	var uri = path + "/monitor/alarmNotifyFilter/addAlarmNotifyFilter";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"policyID":$("#policyID").val(),
			"filterKey":filterKey,
			"filterKeyValue" : filterKeyValue,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				reloadFilterTable();
			} else {
				$.messager.alert("提示", "添加通知过滤失败！", "error");
			}
		}
	}
		ajax_(ajax_param);
}

/*
 * 删除通知过滤
 */
function doDelNotifyFilter(filterID){
	var path=getRootName();
	$.messager.confirm("提示","确定删除此条？",function(r){
		if (r == true) {
			var uri = path + "/monitor/alarmNotifyFilter/delNotifyFilter";
			var ajax_param = {
				url : uri,
				type : "post",
				datdType : "json",
				data : {
					"filterID" : filterID,
					"t" : Math.random()
				},
				error : function() {
					$.messager.alert("错误", "ajax_error", "error");
				},
				success : function(data) {
					if (true == data || "true" == data) {
						reloadFilterTable();
					} else {
						$.messager.alert("提示", "该通知策略删除失败！", "info");
					}
				}
			};
			ajax_(ajax_param);
		}
	});
}

//告警级别选择
function toSelectAlarmLevel(){
	var path=getRootName();
	var policyID = $("#policyID").val();
	if(policyID==""||policyID==-1){
		doMainAdd("toSelectAlarmLevel()");
	}else{
		var uri=path+"/monitor/alarmNotifyCfg/toSelectAlarmLevel?policyID="+policyID;
//		window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
		$('#event_select_dlg').window({
		    title: '告警级别选择',
		    width: 620,
		    height: 450,
		    modal: true,
  		    onBeforeOpen : function(){
                if($(".event_select_dlg").size() > 1){
                    $('.event_select_dlg:first').parent().remove();
                }
            },
		    href: uri
		});	
	}	
}

//告警类型选择
function toSelectAlarmType(){
	var path=getRootName();
	var policyID = $("#policyID").val();
	if(policyID==""||policyID==-1){
		doMainAdd("toSelectAlarmType()");
	}else{
		var uri=path+"/monitor/alarmNotifyCfg/toSelectAlarmType?policyID="+policyID;
//		window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
		$('#event_select_dlg').window({
		    title: '告警类型选择',
		    width: 620,
		    height: 450,
		    modal: true,
			    onBeforeOpen : function(){
	            if($(".event_select_dlg").size() > 1){
	                $('.event_select_dlg:first').parent().remove();
	            }
	        },
		    href: uri
		});
	}	
}

//告警源对象选择
function toSelectMOSource(){
	var path=getRootName();
	var policyID = $("#policyID").val();
	if(policyID==""||policyID==-1){
		doMainAdd("toSelectMOSource()");
	}else{
		var uri=path+"/monitor/alarmNotifyCfg/toSelectMOSource?policyID="+policyID;
//		window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
		$('#event_select_dlg').window({
		    title: '告警源对象选择',
		    width: 660,
		    height: 470,
		    modal: true,
			    onBeforeOpen : function(){
	            if($(".event_select_dlg").size() > 1){
	                $('.event_select_dlg:first').parent().remove();
	            }
	        },
		    href: uri
		});
	}	
}

//告警事件选择
function toSelectAlarmEvent(){
	var path=getRootName();
	var policyID = $("#policyID").val();
	if(policyID==""||policyID==-1){
		doMainAdd("toSelectAlarmEvent()");
	}else{
		var uri=path+"/monitor/alarmNotifyCfg/toSelectAlarmEvent?policyID="+policyID;
//		window.open(uri,"","height=480px,width=780px,left=400,top=200,resizable=no,scrollings=no,status=no,toolbar=no,menubar=no,location=no");
		$('#event_select_dlg').window({
		    title: '告警事件选择',
		    width: 620,
		    height: 450,
		    modal: true,
			    onBeforeOpen : function(){
	            if($(".event_select_dlg").size() > 1){
	                $('.event_select_dlg:first').parent().remove();
	            }
	        },
		    href: uri
		});
	}	
}

function doSelect(filterKey,filterVal){
	var path=getRootName();
	var policyID = $("#policyID").val();
	//保存数据		
	 var uri=path+"/monitor/alarmNotifyFilter/addAlarmNotifyFilter?filterValeName="+filterVal;
	 var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
		 	"policyID" : policyID,
			"filterKey" : filterKey
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				reloadFilterTable();
			} else {
				$.messager.alert("提示", "信息增加失败！", "error");
			}
		}
	};
	ajax_(ajax_param);
}

/*
 * 添加值班负责人的验证
 */
function checkForAddDutier(){
	var path = getRootName();
	var uri = path + "/monitor/alarmNotifyToUsers/checkForAddDutier";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"policyID": $("#policyID").val(),
            "userID": -100,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				doAddDutier();
			} else {
				$.messager.alert("提示", "该临时用户已存在", "error");
			}
		}
	}
	ajax_(ajax_param);
}

/**
 * 添加值班负责人
 */
function doAddDutier(){
	 var path = getRootName();
        var uri = path + "/monitor/alarmNotifyToUsers/addNow";
        var ajax_param = {
            url: uri,
            type: "post",
            datdType: "json",
            data: {
                "policyID": $("#policyID").val(),
                "userID": -100,
                "t": Math.random()
            },
            error: function(){
                $.messager.alert("错误", "ajax_error", "error");
            },
            success: function(data){
                if (true == data || "true" == data) {
                    $.messager.alert("提示", "添加值班负责人成功！", "info");
                    reloadToUserTable();
                }
                else {
                    $.messager.alert("提示", "添加值班负责人失败！", "error");
                }
            }
        }
        ajax_(ajax_param);
}

/**
 * 添加值班负责人
 */
function getDutier(){
   checkForAddDutier();
}

/*
 * 添加值班负责人的验证
 */
function checkForAddDutier(){
	var path = getRootName();
	var uri = path + "/monitor/alarmNotifyToUsers/checkForAddDutier";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"policyID": $("#policyID").val(),
            "userID": -100,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			if (true == data || "true" == data) {
				doAddDutier();
			} else {
				$.messager.alert("提示", "值班负责人已经添加！", "error");
			}
		}
	}
	ajax_(ajax_param);
}

/**
 * 添加值班负责人
 */
function doAddDutier(){
	 var path = getRootName();
        var uri = path + "/monitor/alarmNotifyToUsers/addNow";
        var ajax_param = {
            url: uri,
            type: "post",
            datdType: "json",
            data: {
                "policyID": $("#policyID").val(),
                "userID": -100,
                "t": Math.random()
            },
            error: function(){
                $.messager.alert("错误", "ajax_error", "error");
            },
            success: function(data){
                if (true == data || "true" == data) {
                    $.messager.alert("提示", "添加值班负责人成功！", "info");
                    reloadToUserTable();
                }
                else {
                    $.messager.alert("提示", "添加值班负责人失败！", "error");
                }
            }
        }
        ajax_(ajax_param);
}

/**
 * 选择语音通知类型
 */
function editVoice() {
	var voiceMessageType  = $('input[name="voiceMessageType"]:checked').val();
	if(voiceMessageType == 1){
		$('#phone1').show();
		$('#phone2').show();
		$('#phone3').show();
		$('#voice1').hide();
		$('#voice2').hide();
		$("#ipt_alarmVoiceID").combobox('setValue','-1');
		$("#ipt_clearAlarmVoiceID").combobox('setValue','-1');
		$("#ipt_deleteAlarmVoiceID").combobox('setValue','-1');
	}else{
		$('#phone1').hide();
		$('#phone2').hide();
		$('#phone3').hide();
		$('#voice1').show();
		$('#voice2').show();
	} 
}

/**
 * 是否语音通知
 */
function editPhone(){
	var isPhone  = $('input[name="isPhone"]:checked').val();
	if(isPhone == 1){
		$("input[type='radio'][name='voiceMessageType']").get(0).checked = true;
		$("#voiceTitle").show();
		$("#voiceTd").show();
		editVoice();
	}else{
		$("#voiceTitle").hide();
		$("#voiceTd").hide();
		$('#phone1').hide();
		$('#phone2').hide();
		$('#phone3').hide();
		$('#voice1').hide();
		$('#voice2').hide();
	}
}
