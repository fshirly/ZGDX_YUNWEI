var notification = notification || {};

/* 系统根名称 */
notification.basePath = getRootName();

/* 搜索列表 */
notification.search = function() {
	$('#notifications_datagrid').datagrid('load');
};

/* 当前页面列表刷新 */
notification.refresh = function() {
	$('#notifications_datagrid').datagrid('reload');
};

/* 重置 */
notification.reset = function() {
	$("#title").val("");
	$("#createBegin").datebox("clear");
	$("#createEnd").datebox("clear");
	$("#recipient").combobox('setValue', '-1');
	this.search();
};

/* 获取查询条件 */
notification.getParams = function() {
	try {
		return {
			title : $("#title").val(),
			createBegin : $("#createBegin").datebox("getValue"),
			createEnd : $("#createEnd").datebox("getValue"),
			recipient : $("#recipient").combobox('getValue')
		};
	} catch (e) {
		return {};
	}
};

/* 跳转创建页面 */
notification.toAdd = function() {
	var that = this;
	$('<div id="notifications"/>').dialog({
		title : '提醒创建',
		width : 720,
		height : 500,
		minimizable : false,
		maximizable : false,
		collapsible : false,
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		},
		href : that.basePath + '/platform/notification/toAdd'
	});
};

/* 跳转编辑页面 */
notification.toEdit = function(notifiId) {
	var that = this;
	$('<div id="notifications"/>').dialog({
		title : '提醒编辑',
		width : 720,
		height : 500,
		minimizable : false,
		maximizable : false,
		collapsible : false,
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		},
		href : that.basePath + '/platform/notification/toEdit?notifiId=' + notifiId
	});
};

/* 跳转查看页面 */
notification.toView = function(notifiId) {
	var that = this;
	$('<div id="notifications"/>').dialog({
		title : '提醒详细',
		width : 720,
		height : 500,
		minimizable : false,
		maximizable : false,
		collapsible : false,
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		},
		href : that.basePath + '/platform/notification/toView?notifiId=' + notifiId
	});
};

/* 跳转创建页面 */
notification.toUser = function() {
	var that = this;
	$('<div id="configer"/>').dialog({
		title : '提醒创建',
		width : 660,
		height : 460,
		minimizable : false,
		maximizable : false,
		collapsible : false,
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		},
		href : that.basePath + '/platform/notification/toUser?userInfo=' + $('#userIds').val()
	});
};

/* 创建提醒 */
notification.add = function() {
	var that = this, userInfo = $('#userIds').val(), userO, id = $('#notifiId').val(), uri = that.basePath
			+ '/platform/notification/insert';
	if (checkInfo('#notifications_form')) {
		if (userInfo) {
			userO = $.map(userInfo.split(','), function(user) {
				return user.split(':')[0];
			});
		}
		if (id) {
			uri = that.basePath + '/platform/notification/update';
		}
		$.ajax({
			url : uri,
			data : $('#notifications_form').serialize() + '&isNote=' + ($('#isNote').is(':checked') ? 2 : 1)
					+ '&userIds=' + userO.join(','),
			success : function(data) {
				if (data) {
					if (id) {
						that.refresh();
					} else {
						that.search();
					}
					$('#notifications').window('close');
				} else {
					$.messager.alert('提示', '提醒信息处理失败！', 'info');
				}
			}
		});
	}
};

/* 删除消息提醒 */
notification.deleteNotice = function(noticeId, status) {
	var that = this, isConfirm = '确定删除选择的列表项?';
	var checked = $('#notifications_datagrid').datagrid('getChecked');
	if (!noticeId) {
		if (checked.length === 0) {
			$.messager.alert('提示', '请选择需要删除的列表项！', 'info');
			return;
		}
		noticeId = $.map(checked, function(ck) {
			if (ck.notifiStatus != '未确定') {
				isConfirm = '选择列表项中包含确认或部分确认的提醒信息,确定一并删除？';
			}
			return ck.id;
		}).join(',');
	} else if (status != '未确定') {
		isConfirm = '该条消息提醒已确认或部分确认,确定删除吗？';
	}
	$.messager.confirm('提示', isConfirm, function(r) {
		if (r) {
			$.ajax({
				url : that.basePath + '/platform/notification/delete',
				data : {
					ids : noticeId
				},
				success : function(data) {
					if (data) {
						that.search();
					} else {
						$.messager.alert('提示', '提醒信息删除失败！', 'info');
					}
				}
			});
		}
	});
};

$(function() {
	var path = getRootName();
	$('#recipient').combobox({
		url : getRootName() + '/platform/notification/users',
		valueField : 'id',
		textField : 'name',
		editable : false
	});
	$('#notifications_datagrid').datagrid(
			{
				iconCls : 'icon-edit',// 图标
				fit : true,// 自动大小
				fitColumns : true,
				url : path + '/platform/notification/list',
				idField : 'id',
				singleSelect : false,// 是否单选
				checkOnSelect : false,
				selectOnCheck : false,
				pagination : true,// 分页控件
				rownumbers : true,// 行号
				scrollbarSize : 0,
				striped : true,
				onBeforeLoad : function() {
					return $.extend(arguments[0], notification.getParams());
				},
				toolbar : [ {
					text : "新增",
					iconCls : "icon-add",
					handler : function() {
						notification.toAdd();
					}
				}, {
					text : "删除",
					iconCls : "icon-cancel",
					handler : function() {
						notification.deleteNotice();
					}
				} ],
				columns : [ [
						{
							title : 'ck',
							checkbox : true,
						},
						{
							title : '提醒主题',
							field : 'title',
							align : 'center',
							width : 160,
						},
						{
							title : '创建时间',
							field : 'formatCreateTime',
							align : 'center',
							width : 120
						},
						{
							title : '接收人',
							field : 'planEnd',
							align : 'center',
							width : 120,
							formatter : function(value, row, index) {
								var names = $.map(row.persons, function(person) {
									return person.userName;
								}).join(',');
								if (row.persons && row.persons.length > 3) {
									var name = row.persons[0].userName + ',' + row.persons[1].userName + ','
											+ row.persons[2].userName + '...';
									return '<span title="' + names + '">' + name + '</span>';
								}
								return names;
							}
						},
						{
							title : '确认状态',
							field : 'notifiStatus',
							align : 'center',
							width : 160,
							formatter : function(value, row, index) {
								if ('未确定' === value) {
									return '<font style="color:red">' + value + '</font>';
								}
								return value;
							}
						},
						{
							title : '操作',
							field : 'id',
							align : 'center',
							width : 140,
							formatter : function(value, row, index) {
								var status = row.notifiStatus;
								if ('未确定' === status) {
									return '<a style="cursor: pointer;");" href="javascript:notification.toView('+ row.id + ');">查看</a> &nbsp;'
											+ '<a style="cursor: pointer;" href="javascript:notification.toEdit('
											+ row.id + ');">编辑</a> &nbsp;'
											+ '<a style="cursor: pointer;" href="javascript:notification.deleteNotice('
											+ row.id + ',\'' + row.notifiStatus + '\');">删除</a>';
								} else {
									return '<a style="cursor: pointer;");" href="javascript:notification.toView('+ row.id + ');">查看</a> &nbsp;'
											+ '<a style="cursor: pointer;" href="javascript:notification.deleteNotice('
											+ row.id + ',\'' + row.notifiStatus + '\');">删除</a>';
									;
								}
							}
						} ] ]
			});
});