var tasklogconfig = tasklogconfig || {};

/* 搜索列表 */
tasklogconfig.search = function(id, type) {
	var params = {};
	if (type) {
		if ('O' === type) {
			params.orgId = id;
		} else {
			params.deptId = id;
		}
	}
	var $datagrid = $('#taskLogUsers');
	$datagrid.datagrid('options').queryParams = params;
	$datagrid.datagrid('load');
};

/* 保存配置信息 */
tasklogconfig.update = function() {
	var radio = $('input:radio[name="start"]:checked').val();
	var params = {
		paraValue : radio,
		paraKey : 'TaskLogEnable'
	};
	if (radio === 'true') {
		if (!checkInfo('#startTime')) {
			return;
		}
		params.paraKey += '|TaskLogTime';
		params.paraValue += '|' + $('#TaskLogTime').timespinner('getValue');
	}
	$.ajax({
		url : getRootName() + '/platform/taskLogConfig/updateConfig',
		data : $.param(params),
		success : function(data) {
			if (data) {
				f.messager.alert('提示', '任务日志系统配置修改成功！', 'info');
			} else {
				f.messager.alert('提示', '任务日志系统配置修改失败！', 'info');
			}
		}
	});
};

/* 控制配置时间显示 */
tasklogconfig.show = function(that) {
	if ($(that).val() === 'true') {
		$('#startTime').show();
	} else {
		$('#startTime').hide();
	}
};

/* 手工生成任务日志信息 */
tasklogconfig.manual = function() {
	var manualTime = $('#manualTime').datebox('getValue');
	if (manualTime) {
		f.ajax({
			url : getRootName() + '/platform/taskLogConfig/manual',
			data : {
				taskTime : manualTime,
				"t" : Math.random()
			},
			success : function(data) {
				if (data) {
					f.messager.alert('提示', '任务日志手动派发成功！', 'info');
				} else {
					f.messager.alert('提示', '任务日志手动派发失败！', 'info');
				}
			}
		});
	}
};

/*添加配置人员userIds用,分隔*/
tasklogconfig.addUser = function (userIds) {
	if (!userIds) {
		return;
	}
	$.ajax({
		url : getRootName() + '/platform/taskLogConfig/addUsers',
		data : {
			userIds : userIds,
			"t" : Math.random()
		},
		success : function(data) {
			if (data) {
				tasklogconfig.search();
			} else {
				f.messager.alert('提示', '添加接收人失败！', 'info');
			}
		}
	});
};

/* 删除用户 */
tasklogconfig.deleteUser = function(userId) {
	$.messager.confirm('提示', '确认删除选择的列表项吗？', function(r) {
		if (r) {
			$.ajax({
				url : getRootName() + '/platform/taskLogConfig/deleteUser',
				data : {
					userId : userId,
					"t" : Math.random()
				},
				success : function(data) {
					if (data) {
						tasklogconfig.search();
						var $userNode = $('#userIds'), userIds = $userNode.val();
						if (userIds) {
							userIds = userIds.replace(/\s/g, '');
							userIds = ',' + userIds + ',';
							userIds = userIds.replace(',' + userId + ',', ',');
							userIds = userIds.substring(1, userIds.length - 1);
							$userNode.val(userIds);
						} 
					} else {
						f.messager.alert('提示', '删除失败！', 'info');
					}
				}
			});
		}
	});
};

$(function() {
	$('#taskLogUsers').datagrid({
		iconCls : 'icon-edit',// 图标
		width : 'auto',
		height : 'auto',
		striped : true,
		border : true,
		fit : true,// 自动大小
		fitColumns : true,
		url : getRootName() + '/platform/taskLogConfig/queryUsers',
		idField : 'UserID',
		singleSelect : true,// 是否单选
		pagination : true,// 分页控件
		rownumbers : true,// 行号
		toolbar : '#addUser',
		columns : [ [ {
			field : 'UserName',
			title : '姓名',
			width : 30,
			align : 'center',
		}, {
			field : 'OrganizationName',
			title : '单位',
			width : 50,
			align : 'center'
		}, {
			field : 'DeptName',
			title : '部门',
			width : 30,
			align : 'center'
		}, {
			field : 'MobilePhone',
			title : '联系方式',
			width : 30,
			align : 'center'
		}, {
			field : 'userIDs',
			title : '操作',
			width : 10,
			align : 'center',
			formatter : function(value, row, index) {
				return '<a onclick="tasklogconfig.deleteUser(' + row.UserID + ');">删除</a>';
			}
		} ] ]
	});
	$('#addUser').f_selectPerson({
		selectType : 'more',
		getUserIds : function () {return $('#userIds').val();},
		onClosed : function (arr) {
			var ids = $.map(arr, function(user){return user.UserID;}).join(','), $userNode = $('#userIds'), userIds = $userNode.val();
			tasklogconfig.addUser(ids);
			if (userIds && ids) {
				$userNode.val(userIds + ',' + ids);
			} else if (!userIds && ids) {
				$userNode.val(ids);
			}
		}
	});
});
(function orgTreeInit() {
	var path = getRootPatch();
	var deptImg = path + "/plugin/dTree/img/tree_department.png";
	var orgImg = path + "/plugin/dTree/img/tree_company.png";
	$.ajax({
		url : path + '/platform/taskLogConfig/queryTree',
		type : "post",
		datdType : "json",
		data : {
			"t" : Math.random()
		},
		error : function() {
			$.messager.alert("错误", "ajax_error", "error");
		},
		success : function(data) {
			dataTree = new dTree("dataTree", path + "/plugin/dTree/img/");
			dataTree.add(0, -1, "单位部门", "javascript:tasklogconfig.search(0);");
			var depts, _id, _name, _parent, _dparent;
			for (var i = 0, osize = data.length; i < osize; i++) {
				_id = data[i].orgId;
				_dparent = _id;
				_name = data[i].orgName;
				_parent = 0;
				dataTree.add(_id, _parent, _name, "javascript:tasklogconfig.search('" + _id + "','O');", "单位", "",
						orgImg, orgImg);
				depts = data[i].depts;
				for (var j = 0, dsize = depts.length; j < dsize; j++) {
					_id = depts[j].deptId;
					_name = depts[j].deptName;
					dataTree.add(_id, _dparent, _name, "javascript:tasklogconfig.search('" + _id + "','D');", "部门", "",
							deptImg, deptImg);
				}
			}
			$('#orgTree').empty().append(dataTree + "");
		}
	});
})();