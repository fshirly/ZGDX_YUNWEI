/**
 * 业务部门用户登录，只能查询通知公告
 */
var i;
$(document).ready(function() {
	$('#divFilter').show();
	var tab = $('#noticeListTab').tabs('getSelected');
	var index = $('#noticeListTab').tabs('getTabIndex', tab);
	// 判断第一次加载页面时调用初始化方法
	if (index == 0) {
		doInitTable('tblAnnouncementList', 'Reserved');
	}
	$('#noticeListTab').tabs({
		onSelect : function(title) {
			if (title == '当前通知公告') {
				i = 0;
				doInitTable('tblAnnouncementList', 'Reserved');
			} else {
				i = 1;
				doInitTable('tblAnnouncementList1', 'Completed');
			}
		}
	});
	$('#ipt_createTime').datetimebox({
		required : false,
		showSeconds : true,
		formatter : formatDateText
	});
	$('#ipt_deadLine').datetimebox({
		required : false,
		showSeconds : true,
		formatter : formatDateText
	});
	// 页面初始化的时候初始化控件
	doGetUserByName('', 'ipt_creator');
});

function doInitTable(tblAnnouncementList, workType) {
	var path = getRootName();
	$('#' + tblAnnouncementList)
			.datagrid(
					{
						width : 'auto',
						height : 'auto',
						nowrap : false,
						striped : true,
						border : true,
						collapsible : false,// 是否可折叠的
						fitColumns : true,
						fit : true,// 自动大小
						url : path + '/announcement/queryBlock?workType='
								+ workType,
						// sortName: 'code',
						// sortOrder: 'desc',
						remoteSort : false,
						idField : 'id',
						singleSelect : false,// 是否单选
						pagination : true,// 分页控件
						rownumbers : true,// 行号
						columns : [ [
								{
									field : 'title',
									title : '标题',
									width : 240,
									align : 'center',
								},
								{
									field : 'createTime',
									title : '创建时间',
									width : 100,
									align : 'center'
								},
								{
									field : 'creator',
									title : '发布人',
									width : 140,
									align : 'center'
								},
								{
									field : 'summary',
									title : '状态',
									width : 140,
									align : 'center'
								},
								{
									field : 'deadLine',
									title : '有效期',
									width : 140,
									align : 'center'
								},
								{
									field : 'id',
									title : '查看',
									width : 100,
									align : 'center',
									formatter : function(value, row, index) {
										if (workType == 'Reserved') {
											return '<a style="cursor: pointer;" title="查看" class="easyui-tooltip" onclick="javascript:toLook('
													+ row.id
													+ ');"><img src="'
													+ path
													+ '/style/images/icon/icon_view.png" alt="查看" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a style="cursor: pointer;" title="修改" class="easyui-tooltip" onclick="javascript:toUpdate('
													+ row.id
													+ ');"><img src="'
													+ path
													+ '/style/images/icon/icon_modify.png" alt="修改" /></a>';
										} else {
											return '<a style=\'cursor:pointer\' title="查看" class="easyui-tooltip" onclick="javascript:toLook('
													+ row.id
													+ ');"><img src="'
													+ path
													+ '/style/images/icon/icon_view.png" alt="查看" /></a>';
										}
									}
								} ] ]
					});
	// 重载列表后全不选
	$('#' + tblAnnouncementList).datagrid('unselectAll');
}
function toLook(id) {
	initUpdateVal(id);
	$('#divAddTableyy').dialog('open');
	$("#sr").css('display', 'none');
}
function toUpdate(id) {
	initUpdateVal(id);
	$("#btnSave").unbind("click");
	$("#btnSave").bind("click", function() {
		doUpdate();
	});
	$("#btnUpdate").unbind();
	$("#btnUpdate").bind("click", function() {
		initUpdateVal(id);
	});
	$("#sr").css('display', 'block');
	$('#divAddTableyy').dialog('open');
}
function doUpdate() {
	var path = getRootName();
	if (checkForm()) {
		var path = getRootName();
		var uri = path + "/announcement/updateItsmAnn";
		var ajax_param = {
			url : uri,
			type : "post",
			datdType : "json",
			data : {
				"id" : $("#ipt_id").val(),
				"title" : $("#ipt_title").val(),
				"summary" : $("#ipt_summary").val(),
				"typeId" : $("#ipt_typeId").val(),
				"creater" : $("#creater").val(),
				"createTime" : $("#ipt_createTime").val(),
				"deadLine" : $("#ipt_deadLine").val(),
				"t" : Math.random()
			},
			error : function() {
				$.messager.alert("错误", "ajax_error", "error");
			},
			success : function(data) {
				if (true == data || "true" == data) {
//					$.messager.alert("提示", "信息修改成功！", "info");
					$('#divAddTableyy').dialog('close');
				} else {
					$.messager.alert("提示", "信息修改失败！", "error");
				}
			}
		}
		ajax_(ajax_param);
	}
}
function initUpdateVal(id) {
	resetForm('tblAnnouncementInfo');
	var path = getRootName();
	var uri = path + "/announcement/getAnnouncementById?id=" + id;
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"id" : id,
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			iterObj(data, "ipt");
			$('#ipt_createTime').datetimebox('setValue', data.createTime);
			$('#ipt_deadLine').datetimebox('setValue', data.deadLine);
		}
	}
	ajax_(ajax_param);
}
function checkForm() {
	var checkControlAttr = new Array('ipt_title');
	var checkMessagerAttr = new Array('请输入标题！');
	return checkFormCommon(checkControlAttr, checkMessagerAttr);
}
function reloadTable() {
	var title = $("#txtFilterTitle").val();
	var deadLineInt = $("#deadLineFilter").val();
	if (i == 0 || i == undefined) {
		$('#tblAnnouncementList').datagrid('options').queryParams = {
			"title" : title,
			"deadLineInt" : deadLineInt
		};
		reloadTableCommon('tblAnnouncementList');
	}
	if (i == 1 || i == undefined) {
		$('#tblAnnouncementList1').datagrid('options').queryParams = {
			"title" : title,
			"deadLineInt" : deadLineInt
		};
		reloadTableCommon('tblAnnouncementList1');
	}

}
/**
 * 输入框自动显示输入
 */
function doGetUserByName(userAccount, idAttr) {
	var path = getRootPatch();
	var uri = path + "/permissionSysUser/queryUserByAuto";
	var ajax_param = {
		url : uri,
		type : "post",
		datdType : "json",
		data : {
			"userAccount" : userAccount,
			"t" : Math.random()
		},
		error : function() {
			alert("ajax_error");
		},
		success : function(data) {
			if (data != "") {
				var userList = eval('(' + data.userListJson + ')');
				var idAttrArr = idAttr.split(',');
				$.each(idAttrArr, function(i, v) {
					doAutoComplete(v, userList);
				});
			}
		}
	};
	ajax_(ajax_param);
}

function doAutoComplete(idval, userLstJson) {
	$("#" + idval).autocomplete(userLstJson, {
		minChars : 1,
		max : 9000000,
		autoFill : false,
		mustMatch : false,
		matchContains : true,
		scrollHeight : 220,
		formatItem : function(row, i, max) {
			return row.userName;
		},
		formatMatch : function(row, i, max) {
			return row.userAccount;
		},
		formatResult : function(row) {
			return row.userName + ":" + row.userID;
		}
	});
}

// 输入联想用户的点击事件
function autoEventClick(uSelValTemp, $input) {
	var uSelValTempArr = uSelValTemp.split(':');
	$input.val(uSelValTempArr[0]);
	$("#creater").val(uSelValTempArr[1]);
}