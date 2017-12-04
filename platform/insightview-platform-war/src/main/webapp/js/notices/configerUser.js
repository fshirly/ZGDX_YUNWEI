var User = User || {};
User.configerUser = {
	search : function() {/* 查询值班人员信息 */
		var userIds = $.map($('#selmid').children('option'), function(user) {
			return user.value;
		});
		$.ajax({
			url : getRootName() + '/platform/notification/users',
			data : {
				userName : $('[name="userName"]').val(),
				deptId : $('#user_department').f_combotree('getValue'),
				userIds : userIds.join(',')
			},
			success : function(options) {
				var $sel = f('#selLeft');
				$sel.children('option').remove();
				$.each(options, function(index, option) {
					$sel[0].options.add(new Option(option.name, option.id));
				});
			}
		});
	},
	reset : function() {/* 重置查询条件 */
		$('[name="userName"]').val('');
		$('#user_department').val('').f_combotree('setValue', '');
		this.search();
	},
	confirm : function() {/* 添加值班信息人员 */
		var users = f('#selmid').children('option');
		var userIds = f.map(users, function(user) {
			return user.value + ":" + user.label;
		}).join(',');
		var userNames = f.map(users, function(user) {
			return user.label;
		}).join(',');
		$('#userIds').val(userIds);
		if (users.length > 10) {
			$('#userNames').attr('title', userNames).val(users[0].label + ',' + users[1].label + ',' + users[2].label + '...');
		} else {
			$('#userNames').val(userNames);
		}
		$('#configer').dialog('close');
	}
};
$(function() {
	/* 初始化部门树 */
	$('#user_department').f_combotree({
		rootName : '部门',
		height : 200,
		url : getRootName() + '/permissionDepartment/findDeptByOrgId?organizationId=' + $('#current_orgId').val(),
		titleField : 'deptName',
		idField : 'deptId',
		parentIdField : 'parentDeptID'
	});
	$.fn.LRSelect('selLeft', 'selmid', 'img_L_AllTo_M', 'img_L_To_M', 'img_M_To_L', 'img_M_AllTo_L');
});