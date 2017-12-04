var noticeperson = noticeperson || {};

/* 系统根名称 */
noticeperson.basePath = getRootName();

/* 搜索列表 */
noticeperson.search = function() {
	$('#noticeperson_datagrid').datagrid('load');
};

/* 当前页列表刷新 */
noticeperson.refresh = function() {
	$('#noticeperson_datagrid').datagrid('reload');
};

/* 重置 */
noticeperson.reset = function() {
	$("#title").val("");
	$("#createBegin").datebox("clear");
	$("#createEnd").datebox("clear");
	$("#confirmBegin").datebox("clear");
	$("#confirmEnd").datebox("clear");
	$("#status").combobox('setValue', '-1');
	$("#creator").combobox('setValue', '-1');
	this.search();
};

/* 获取查询条件 */
noticeperson.getParams = function() {
	try {
		return {
			title : $("#title").val(),
			createBegin : $("#createBegin").datebox("getValue"),
			createEnd : $("#createEnd").datebox("getValue"),
			creator : $("#creator").combobox('getValue'),
			status : $("#status").combobox('getValue'),
			confirmBegin : $("#confirmBegin").datebox("getValue"),
			confirmEnd : $("#confirmEnd").datebox("getValue")
		};
	} catch (e) {
		return {};
	}
};

/* 跳转确认页面 */
noticeperson.toConfirm = function(personId) {
	var that = this;
	$('<div id="toConfirm"/>').dialog({
		title : '提醒确认',
		width : 720,
		height : 500,
		minimizable : false,
		maximizable : false,
		collapsible : false,
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		},
		href : that.basePath + '/platform/noticePerson/toConfirm?id=' + personId
	});
};

/* 跳转详情页面 */
noticeperson.toView = function(personId) {
	var that = this;
	$('<div id="toConfirm"/>').dialog({
		title : '提醒详情',
		width : 720,
		height : 500,
		minimizable : false,
		maximizable : false,
		collapsible : false,
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		},
		href : that.basePath + '/platform/noticePerson/toView?id=' + personId
	});
};

/* 提醒信息确认 */
noticeperson.confirm = function() {
	var that = this;
	if (checkInfo('#confirm_form')) {
		$.ajax({
			url : that.basePath + '/platform/noticePerson/confirm',
			data : $('#confirm_form').serialize() + '&isNote=' + ($('#isNote').is(':checked') ? 2 : 1),
			success : function(data) {
				if (data) {
					that.refresh();
					$('#toConfirm').window('close');
				} else {
					$.messager.alert('提示', '提醒信息确认失败！', 'info');
				}
			}
		});
	}
};

$(function() {
	var path = getRootName();
	$('#creator').combobox({
		url : path + '/platform/notification/users',
		valueField : 'id',
		textField : 'name',
		editable : false
	});
	$('#noticeperson_datagrid').datagrid(
			{
				iconCls : 'icon-edit',// 图标
				fit : true,// 自动大小
				fitColumns : true,
				url : path + '/platform/noticePerson/list',
				idField : 'id',
				pagination : true,// 分页控件
				rownumbers : true,// 行号
				singleSelect : true,
				scrollbarSize : 0,
				striped : true,
				onBeforeLoad : function() {
					return $.extend(arguments[0], noticeperson.getParams());
				},
				columns : [ [
						{
							title : '提醒主题',
							field : 'title',
							align : 'center',
							width : 160,
							formatter : function(value, row, index) {
								return row.notifications.title;
							}
						},
						{
							title : '创建时间',
							field : 'formatCreateTime',
							align : 'center',
							width : 120,
							formatter : function(value, row, index) {
								return row.notifications.formatCreateTime;
							}
						},
						{
							title : '确认状态',
							field : 'status',
							align : 'center',
							width : 160,
							formatter : function(value, row, index) {
								if (value === 1) {
									return '<span style="color:red">未确认</span>';
								} else {
									return '已确认';
								}
							}
						},
						{
							title : '确认时间',
							field : 'formateConfigeTime',
							align : 'center',
							width : 160
						},
						{
							title : '创建人',
							field : 'planEnd',
							align : 'center',
							width : 120,
							formatter : function(value, row, index) {
								return row.notifications.userName;
							}
						},
						{
							title : '操作',
							field : 'id',
							align : 'center',
							width : 140,
							formatter : function(value, row, index) {
								return '<a style="cursor: pointer;" href="javascript:noticeperson.toConfirm(' + row.id
										+ ');");">确认</a> &nbsp;' + '<a style="cursor: pointer;" href="javascript:noticeperson.toView(' + row.id + ');">查看</a> ';
							} 
						} ] ]
			});
});